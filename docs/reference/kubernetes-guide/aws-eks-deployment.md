# Deploying Gaffer on AWS EKS

The following instructions will guide you through provisioning and configuring an [AWS EKS](https://aws.amazon.com/eks/) cluster that our Helm Charts can be deployed on.

## Install CLI Tools

- [docker compose](https://github.com/docker/compose/releases/latest)
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- [helm](https://github.com/helm/helm/releases)
- [aws-cli](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html)
- [eksctl](https://github.com/weaveworks/eksctl/releases/latest)

## Container Images

If the versions of the container images you would like to deploy are not available in [Docker Hub](https://hub.docker.com/u/gchq) then you will need to host them in a registry yourself.

The following instructions build all the container images and host them in AWS ECR when run from the ./kubernetes folder:

```bash
export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}
export GAFFER_VERSION=${GAFFER_VERSION:-2.0.0}

docker compose --project-directory ../docker/accumulo/ -f ../docker/accumulo/docker-compose.yaml build
docker compose --project-directory ../docker/gaffer-road-traffic-loader/ -f ../docker/gaffer-road-traffic-loader/docker-compose.yaml build

HADOOP_IMAGES="hdfs"
GAFFER_IMAGES="gaffer gaffer-rest gaffer-road-traffic-loader"

ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
[ "${REGION}" = "" ] && REGION=$(aws configure get region)
[ "${REGION}" = "" ] && REGION=$(curl --silent -m 5 http://169.254.169.254/latest/dynamic/instance-identity/document | grep region | cut -d'"' -f 4)
REPO_PREFIX="${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com/gchq"

for repo in ${HADOOP_IMAGES} ${GAFFER_IMAGES}; do
  aws ecr create-repository --repository-name gchq/${repo}
done

echo $(aws ecr get-login-password) | docker login -u AWS --password-stdin https://${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com

for repo in ${HADOOP_IMAGES}; do
  docker image tag gchq/${repo}:${HADOOP_VERSION} ${REPO_PREFIX}/${repo}:${HADOOP_VERSION}
  docker image push ${REPO_PREFIX}/${repo}:${HADOOP_VERSION}
done

for repo in ${GAFFER_IMAGES}; do
  docker image tag gchq/${repo}:${GAFFER_VERSION} ${REPO_PREFIX}/${repo}:${GAFFER_VERSION}
  docker image push ${REPO_PREFIX}/${repo}:${GAFFER_VERSION}
done
```

## EKS Cluster

There are a number of ways to provision an AWS EKS cluster. This guide uses a cli tool called `eksctl`. [Documentation](https://docs.aws.amazon.com/eks/latest/userguide/getting-started.html) is available for some of the other methods.

Before issuing any commands, the subnets that will be used by your EKS cluster need to be tagged accordingly:
Subnet Type | Tag Key | Tag Value
-----------| ------- | ---------
Public | kubernetes.io/role/elb | 1
Private | kubernetes.io/role/internal-elb | 1

If you want the cluser to spin up a VPC that is not the default, then set `$VPC_ID`.

```bash
EKS_CLUSTER_NAME=${EKS_CLUSTER_NAME:-gaffer}
KUBERNETES_VERSION=${KUBERNETES_VERSION:-1.15}

[ "${VPC_ID}" = "" ] && VPC_ID=$(aws ec2 describe-vpcs --filters Name=isDefault,Values=true --query Vpcs[0].VpcId --output text)
[ "${VPC_ID}" = "" ] && echo "Unable to detect default VPC ID, please set \$VPC_ID" && exit 1

# Obtain a list of public and private subnets that the cluster will be deployed into by querying for the required 'elb' tags
PUBLIC_SUBNET_IDS=$(aws ec2 describe-subnets --filters Name=vpc-id,Values=${VPC_ID} Name=tag-key,Values=kubernetes.io/role/elb --query Subnets[].SubnetId --output text | tr -s '[:blank:]' ',')
PRIVATE_SUBNET_IDS=$(aws ec2 describe-subnets --filters Name=vpc-id,Values=${VPC_ID} Name=tag-key,Values=kubernetes.io/role/internal-elb --query Subnets[].SubnetId --output text | tr -s '[:blank:]' ',')
[ "${PUBLIC_SUBNET_IDS}" = "" ] && echo "Unable to detect any public subnets. Make sure they are tagged: kubernetes.io/role/elb=1" && exit 1
[ "${PRIVATE_SUBNET_IDS}" = "" ] && echo "Unable to detect any private subnets. Make sure they are tagged: kubernetes.io/role/internal-elb=1" && exit 1

eksctl create cluster \
  -n "${EKS_CLUSTER_NAME}" \
  --version "${KUBERNETES_VERSION}" \
  --managed \
  --nodes 3 \
  --nodes-min 3 \
  --nodes-max 12 \
  --node-volume-size 20 \
  --full-ecr-access \
  --alb-ingress-access \
  --vpc-private-subnets "${PRIVATE_SUBNET_IDS}" \
  --vpc-public-subnets "${PUBLIC_SUBNET_IDS}"

aws eks update-kubeconfig --name ${EKS_CLUSTER_NAME}
```

## Ingress

Deploy the AWS ALB Ingress Controller, using the [docs](https://docs.aws.amazon.com/eks/latest/userguide/alb-ingress.html).

At the time of writing, this involves issuing the following commands:

```bash
EKS_CLUSTER_NAME=${EKS_CLUSTER_NAME:-gaffer}

[ "${ACCOUNT}" = "" ] && ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
[ "${REGION}" = "" ] && REGION=$(aws configure get region)
[ "${REGION}" = "" ] && REGION=$(curl --silent -m 5 http://169.254.169.254/latest/dynamic/instance-identity/document | grep region | cut -d'"' -f 4)
[ "${REGION}" = "" ] && echo "Unable to detect AWS region, please set \$REGION" && exit 1

eksctl utils associate-iam-oidc-provider \
  --region "${REGION}" \
  --cluster "${EKS_CLUSTER_NAME}" \
  --approve

aws iam create-policy \
  --policy-name ALBIngressControllerIAMPolicy \
  --policy-document https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/v1.1.4/docs/examples/iam-policy.json

kubectl apply -f https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/v1.1.4/docs/examples/rbac-role.yaml

eksctl create iamserviceaccount \
  --region "${REGION}" \
  --name alb-ingress-controller \
  --namespace kube-system \
  --cluster "${EKS_CLUSTER_NAME}" \
  --attach-policy-arn arn:aws:iam::${ACCOUNT}:policy/ALBIngressControllerIAMPolicy \
  --override-existing-serviceaccounts \
  --approve

curl https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/v1.1.4/docs/examples/alb-ingress-controller.yaml | sed "s/# - --cluster-name=devCluster/- --cluster-name=${EKS_CLUSTER_NAME}/" | kubectl apply -f -

```

## Deploying Helm Charts

Below are instructions on deploying the different Helm Charts.

??? example "Gaffer"

    All scripts listed here are intended to be run from the kubernetes/gaffer folder.

    #### Using ECR
    If you are hosting the container images in your AWS account, using ECR, then run the following commands to configure the Helm Charts to use them:

    ```bash
    ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
    [ "${REGION}" = "" ] && REGION=$(aws configure get region)
    [ "${REGION}" = "" ] && REGION=$(curl --silent -m 5 http://169.254.169.254/latest/dynamic/instance-identity/document | grep region | cut -d'"' -f 4)
    if [ "${REGION}" = "" ]; then
        echo "Unable to detect AWS region, please set \$REGION"
    else
        REPO_PREFIX="${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com/gchq"

        EXTRA_HELM_ARGS=""
        EXTRA_HELM_ARGS+="--set gaffer.hdfs.namenode.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set gaffer.hdfs.datanode.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set gaffer.hdfs.shell.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set gaffer.accumulo.image.repository=${REPO_PREFIX}/gaffer "
        EXTRA_HELM_ARGS+="--set gaffer.api.image.repository=${REPO_PREFIX}/gaffer-rest "
        EXTRA_HELM_ARGS+="--set loader.image.repository=${REPO_PREFIX}/gaffer-road-traffic-loader "
    fi
    ```

    #### Deploy Helm Chart
    By default the Gaffer graph uses the in-memory MapStore. If you want to use an alternative store, we have a guide for that [here](TODO:link).

    ```bash
    export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}
    export GAFFER_VERSION=${GAFFER_VERSION:-2.0.0}

    helm dependency update ../accumulo/
    helm dependency update ../gaffer/
    helm dependency update

    helm install gaffer . -f ./values-eks-alb.yaml \
    ${EXTRA_HELM_ARGS} \
        --set gaffer.accumulo.hdfs.namenode.tag=${HADOOP_VERSION} \
        --set gaffer.accumulo.hdfs.datanode.tag=${HADOOP_VERSION} \
        --set gaffer.accumulo.hdfs.shell.tag=${HADOOP_VERSION} \
        --set gaffer.accumulo.image.tag=${GAFFER_VERSION} \
        --set gaffer.api.image.tag=${GAFFER_VERSION} \
        --set loader.image.tag=${GAFFER_VERSION}

    helm test road-traffic
    ```

??? example "Gaffer Road Traffic Dataset"

    All scripts listed here are intended to be run from the kubernetes/gaffer-road-traffic folder.

    #### Using ECR
    If you are hosting the container images in your AWS account, using ECR, then run the following commands to configure the Helm Chart to use them:

    ```bash
    ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
    [ "${REGION}" = "" ] && REGION=$(aws configure get region)
    [ "${REGION}" = "" ] && REGION=$(curl --silent -m 5 http://169.254.169.254/latest/dynamic/instance-identity/document | grep region | cut -d'"' -f 4)
    if [ "${REGION}" = "" ]; then
        echo "Unable to detect AWS region, please set \$REGION"
    else
        REPO_PREFIX="${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com/gchq"

        EXTRA_HELM_ARGS=""
        EXTRA_HELM_ARGS+="--set gaffer.accumulo.hdfs.namenode.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set gaffer.accumulo.hdfs.datanode.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set gaffer.accumulo.hdfs.shell.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set gaffer.accumulo.image.repository=${REPO_PREFIX}/gaffer "
        EXTRA_HELM_ARGS+="--set gaffer.api.image.repository=${REPO_PREFIX}/gaffer-rest "
        EXTRA_HELM_ARGS+="--set loader.image.repository=${REPO_PREFIX}/gaffer-road-traffic-loader "
    fi
    ```

    #### Deploy Helm Chart
    The last thing before deploying is to set the passwords for the various accumulo users in the values.yaml file. These are found under `accumulo.config.userManagement`.

    Finally, deploy the Helm Chart by running this from the kubernetes/gaffer-road-traffic folder:

    ```bash
    export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}
    export GAFFER_VERSION=${GAFFER_VERSION:-2.0.0}

    helm dependency update ../accumulo/
    helm dependency update ../gaffer/
    helm dependency update

    helm install road-traffic . -f ./values-eks-alb.yaml \
    ${EXTRA_HELM_ARGS} \
        --set gaffer.hdfs.namenode.tag=${HADOOP_VERSION} \
        --set gaffer.hdfs.datanode.tag=${HADOOP_VERSION} \
        --set gaffer.hdfs.shell.tag=${HADOOP_VERSION} \
        --set gaffer.accumulo.image.tag=${GAFFER_VERSION} \
        --set gaffer.api.image.tag=${GAFFER_VERSION} \
        --set loader.image.tag=${GAFFER_VERSION}

    helm test road-traffic
    ```

??? example "HDFS"

    All scipts listed here are intended to be run from the kubernetes/hdfs folder.

    #### Using ECR
    If you are hosting the container images in your AWS account, using ECR, then run the following commands to configure the Helm Chart to use them:

    ```bash
    ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
    [ "${REGION}" = "" ] && REGION=$(aws configure get region)
    [ "${REGION}" = "" ] && REGION=$(curl --silent -m 5 http://169.254.169.254/latest/dynamic/instance-identity/document | grep region | cut -d'"' -f 4)
    if [ "${REGION}" = "" ]; then
        echo "Unable to detect AWS region, please set \$REGION"
    else
        REPO_PREFIX="${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com/gchq"

        EXTRA_HELM_ARGS=""
        EXTRA_HELM_ARGS+="--set namenode.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set datanode.repository=${REPO_PREFIX}/hdfs "
        EXTRA_HELM_ARGS+="--set shell.repository=${REPO_PREFIX}/hdfs "
    fi
    ```

    #### Deploy Helm Chart
    Finally, deploy the Helm Chart by running this from the kubernetes/hdfs folder:

    ```bash
    export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}

    helm install hdfs . -f ./values-eks-alb.yaml \
    ${EXTRA_HELM_ARGS} \
        --set hdfs.namenode.tag=${HADOOP_VERSION} \
        --set hdfs.datanode.tag=${HADOOP_VERSION} \
        --set hdfs.shell.tag=${HADOOP_VERSION}

    helm test hdf
    ```

## Access Web UIs

The AWS ALB Ingress Controller will create an application load balancer (ALB) for each ingress resource deployed into the EKS cluster.

You can find the URL that you can use to access each ingress with `kubectl get ing`.

!!! warning

    By default, the security group assigned to the ALBs will allow anyone to access them. We highly recommend attaching a combination of the [other annotations available](https://kubernetes-sigs.github.io/aws-alb-ingress-controller/guide/ingress/annotation/#security-groups) to each of your ingress resources to control access to them.

## Uninstall

To uninstall the deployment run:

```bash
EKS_CLUSTER_NAME=${EKS_CLUSTER_NAME:-gaffer}

// Use helm to uninstall any deployed charts
for release in $(helm ls --short); do
  helm uninstall ${release}
done

// Ensure EBS volumes are deleted
kubectl get pvc --output name | xargs kubectl delete

// Delete the EKS cluster
eksctl delete cluster --name "${EKS_CLUSTER_NAME}"
```
