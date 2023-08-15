# How to deploy a Kubernetes cluster using Kind

The following instructions will guide you through provisioning and configuring a local Kubernetes cluster, using [kind](https://kind.sigs.k8s.io/) (Kubernetes IN Docker), that our Helm Charts can be deployed on.

## Requirements

You will need to install certain CLI tools:

- [docker compose](https://github.com/docker/compose/releases/latest)
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- [helm](https://github.com/helm/helm/releases)
- [kind](https://kind.sigs.k8s.io/docs/user/quick-start/)

## Kubernetes Cluster

Simply run the following command to spin up a local Kubernetes cluster, running inside a Docker container:

```bash
kind create cluster --image kindest/node:v1.24.4
```

## Container Images

If the versions of the container images you would like to deploy are not available in [Docker Hub](https://hub.docker.com/u/gchq) then you will need to build them yourself and import them into your kind cluster.

To import the images, run this from the kubernetes directory:

```bash
export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}
export GAFFER_VERSION=${GAFFER_VERSION:-2.0.0}

docker compose --project-directory ../docker/accumulo/ -f ../docker/accumulo/docker-compose.yaml build
docker compose --project-directory ../docker/gaffer-road-traffic-loader/ -f ../docker/gaffer-road-traffic-loader/docker-compose.yaml build

kind load docker-image gchq/hdfs:${HADOOP_VERSION}
kind load docker-image gchq/gaffer:${GAFFER_VERSION}-accumulo-${ACCUMULO_VERSION}
kind load docker-image gchq/gaffer-rest:${GAFFER_VERSION}-accumulo-${ACCUMULO_VERSION}
kind load docker-image gchq/gaffer-road-traffic-loader:${GAFFER_VERSION}
```

From here you should be able to follow the respective kind-deployment files for the services you would like to run.

## Ingress

Deploy the Nginx Ingress Controller:

```bash
INGRESS_NGINX_VERSION="nginx-0.30.0"
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/${INGRESS_NGINX_VERSION}/deploy/static/mandatory.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/${INGRESS_NGINX_VERSION}/deploy/static/provider/baremetal/service-nodeport.yaml
```

## Deploy Helm Charts

Below are the instructions for deploying the different Helm Charts.

### Gaffer

This runs through how to deploy a standard Gaffer instance using kind. This will give you an in-memory store, to change this see the [guide]() to change the store type.

All the scripts found here are designed to be run from the kubernetes/gaffer folder.

??? example "Deploying Gaffer using kind"

    #### Deploy Helm Charts
    ```bash
    export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}
    export GAFFER_VERSION=${GAFFER_VERSION:-2.0.0}

    helm dependency update

    helm install gaffer . \
        --set hdfs.namenode.tag=${HADOOP_VERSION} \
        --set hdfs.datanode.tag=${HADOOP_VERSION} \
        --set hdfs.shell.tag=${HADOOP_VERSION} \
        --set accumulo.image.tag=${GAFFER_VERSION} \
        --set api.image.tag=${GAFFER_VERSION}

    helm test gaffer
    ```
    #### Accessing Web UIs (via `kubectl port-forward`)
    Run the following in the command line:
    ```bash
    kubectl port-forward svc/gaffer-api 8080:80
    ```

    Access the following URL in your browser which will take you to the Gaffer REST: [http://localhost:8080/rest/](http://localhost:8080/rest/)

    #### Accessing Web UIs (via [Nginx Ingress Controller](https://github.com/kubernetes/ingress-nginx))
    Register the FQDNs for each component in DNS, e.g.
    ```bash
    echo "127.0.0.1 gaffer.k8s.local accumulo.k8s.local hdfs.k8s.local" | sudo tee -a /etc/hosts
    ```

    Update the Gaffer deployment to route ingress based on FQDNs:
    ```bash
    helm upgrade gaffer . -f ./values-host-based-ingress.yaml --reuse-values
    ```

    Set up port forwarding to the nginx ingress controller:
    ```bash
    sudo KUBECONFIG=$HOME/.kube/config kubectl port-forward -n ingress-nginx svc/ingress-nginx 80:80
    ```

    Access the web UIs using the following URLs:

    Component | URL
    --------- | ---
    Gaffer REST | [http://gaffer.k8s.local/rest/](http://gaffer.k8s.local/rest/)

### Gaffer Road Traffic Dataset

This runs through how to deploy the example Gaffer Road Traffic Dataset instance using kind. All scripts listed here are intended to be run from the kubernetes/gaffer-road-traffic folder.

??? example "Deploying Gaffer Road Traffic Dataset using kind"

    After the cluster is provisioned, update the values.yaml with the passwords for the various Accumulo users.

    #### Deploy Helm Charts
    ```bash
    export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}
    export GAFFER_VERSION=${GAFFER_VERSION:-2.0.0}

    helm dependency update ../accumulo/
    helm dependency update ../gaffer/
    helm dependency update

    helm install road-traffic . \
        --set gaffer.hdfs.namenode.tag=${HADOOP_VERSION} \
        --set gaffer.hdfs.datanode.tag=${HADOOP_VERSION} \
        --set gaffer.hdfs.shell.tag=${HADOOP_VERSION} \
        --set gaffer.accumulo.image.tag=${GAFFER_VERSION} \
        --set gaffer.api.image.tag=${GAFFER_VERSION} \
        --set loader.image.tag=${GAFFER_VERSION}

    helm test road-traffic
    ```
    #### Accessing Web UIs (via `kubectl port-forward`)
    Component | Command | URL
    --------- | ------- | ----
    Gaffer REST | `kubectl port-forward svc/gaffer-api 8080:80` | [http://localhost:8080/rest/](http://localhost:8080/rest/)
    Accumulo | `kubectl port-forward svc/road-traffic-gaffer-monitor 9995:80` | [http://localhost:9995/](http://localhost:9995/)
    HDFS | `kubectl port-forward svc/road-traffic-hdfs-namenodes 9870:9870` | [http://localhost:9870/](http://localhost:9870/)

    #### Accessing Web UIs (via [Nginx Ingress Controller](https://github.com/kubernetes/ingress-nginx))
    Register the FQDNs for each component in DNS, e.g.
    ```bash
    echo "127.0.0.1 gaffer.k8s.local accumulo.k8s.local hdfs.k8s.local" | sudo tee -a /etc/hosts
    ```

    Update the Gaffer deployment to route ingress based on FQDNs:
    ```bash
    helm upgrade road-traffic . -f ./values-host-based-ingress.yaml --reuse-values
    ```

    Set up port forwarding to the nginx ingress controller:
    ```bash
    sudo KUBECONFIG=$HOME/.kube/config kubectl port-forward -n ingress-nginx svc/ingress-nginx 80:80
    ```

    Access the web UIs using the following URLs:

    Component | URL
    --------- | ---
    Gaffer REST | [http://gaffer.k8s.local/rest/](http://gaffer.k8s.local/rest/)
    HDFS | [http://hdfs.k8s.local/](http://hdfs.k8s.local/)
    Accumulo | [http://accumulo.k8s.local/](http://accumulo.k8s.local/)

### JupyterHub

This runs through how to deploy JupyterHub for Gaffer using kind. All the scripts found here are designed to be run from the kubernetes/gaffer-jhub folder.

??? example "Deploying JupyterHub for Gaffer using kind"

    #### Container Images
    Use the following commands to build and deploy the extra containers used by JupyterHub:
    ```bash
    source ../../docker/gaffer-pyspark-notebook/.env
    source ../../docker/gaffer-jhub-options-server/get-version.sh

    # Build Container Images
    docker compose --project-directory ../../docker/gaffer-pyspark-notebook/ -f ../../docker/gaffer-pyspark-notebook/docker-compose.yaml build notebook
    docker compose --project-directory ../../docker/spark-py/ -f ../../docker/spark-py/docker-compose.yaml build
    docker compose --project-directory ../../docker/gaffer-jhub-options-server/ -f ../../docker/gaffer-jhub-options-server/docker-compose.yaml build

    # Deploy Images to Kind
    kind load docker-image gchq/gaffer-pyspark-notebook:${GAFFER_VERSION}
    kind load docker-image gchq/spark-py:${SPARK_VERSION}
    kind load docker-image gchq/gaffer-jhub-options-server:${JHUB_OPTIONS_SERVER_VERSION}
    ```
    #### Deploy Helm Chart
    Once that is done, use the following commands to deploy a JupyterHub instance with Gaffer extensions:
    ```bash
    helm dependency update
    helm install jhub . -f ./values-insecure.yaml

    helm test jhub
    ```

    #### Accessing JupyterHub Web UIs (via `kubectl port-forward`)
    Run the following in the command line:
    ```bash
    kubectl port-forward svc/proxy-public 8080:80
    ```

    Access the following URL in your browser: [http://localhost:8080](http://localhost:8080)

    By default, JupyterHub's Dummy Authenticator is used so you can login using any username and password.

    #### Accessing example notebooks
    There are some example notebooks that demonstrate how to interact with HDFS, Gaffer and Spark. Copy them into your working directory, to make them easier to view and execute, by starting a Terminal tab and submitting the following command:
    ```bash
    $ cp -r /examples .
    ```

### Accumulo

This runs through how to deploy Accumulo using kind. All the scripts found here are designed to be run from the kubernetes/accumulo folder.

??? example "Deploying Accumulo using kind"

    #### Deploy Helm Charts
    ```bash
    export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}
    export GAFFER_VERSION=${GAFFER_VERSION:-2.0.0}

    helm dependency update

    helm install accumulo . \
        --set hdfs.namenode.tag=${HADOOP_VERSION} \
        --set hdfs.datanode.tag=${HADOOP_VERSION} \
        --set hdfs.shell.tag=${HADOOP_VERSION} \
        --set accumulo.image.tag=${GAFFER_VERSION}

    helm test accumulo
    ```

    #### Accessing Web UIs (via `kubectl port-forward`)
    Run the following in the command line:
    ```bash
    kubectl port-forward svc/road-traffic-gaffer-monitor 9995:80
    ```

    Access the following URL in your browser: [http://localhost:9995/](http://localhost:9995/)

    #### Accessing Web UIs (via [Nginx Ingress Controller](https://github.com/kubernetes/ingress-nginx))
    Register the FQDNs for each component in DNS, e.g.
    ```bash
    echo "127.0.0.1 gaffer.k8s.local accumulo.k8s.local hdfs.k8s.local" | sudo tee -a /etc/hosts
    ```

    Update the Gaffer deployment to route ingress based on FQDNs:
    ```bash
    helm upgrade accumulo . -f ./values-host-based-ingress.yaml --reuse-values
    ```

    Set up port forwarding to the nginx ingress controller:
    ```bash
    sudo KUBECONFIG=$HOME/.kube/config kubectl port-forward -n ingress-nginx svc/ingress-nginx 80:80
    ```

    Access the web UIs using the following URLs:

    Component | URL
    --------- | ---
    Accumulo | [http://accumulo.k8s.local/](http://accumulo.k8s.local/)

### HDFS

This runs through how to deploy HDFS using kind. All the scripts found here are designed to be run from the kubernetes/hdfs folder.

??? example "Deploying HDFS using kind"

    #### Deploy Helm Charts
    ```bash
    export HADOOP_VERSION=${HADOOP_VERSION:-3.3.3}

    helm install hdfs . \
        --set hdfs.namenode.tag=${HADOOP_VERSION} \
        --set hdfs.datanode.tag=${HADOOP_VERSION} \
        --set hdfs.shell.tag=${HADOOP_VERSION}

    helm test hdfs
    ```

    #### Accessing Web UIs (via `kubectl port-forward`)
    Run the following in the command line:
    ```bash
    kubectl port-forward svc/hdfs-namenodes 9870:9870
    ```

    Access the following URL in your browser: [http://localhost:9870/](http://localhost:9870/)

    #### Accessing Web UIs (via [Nginx Ingress Controller](https://github.com/kubernetes/ingress-nginx))
    Register the FQDNs for each component in DNS, e.g.
    ```bash
    echo "127.0.0.1 hdfs.k8s.local" | sudo tee -a /etc/hosts
    ```

    Update the Gaffer deployment to route ingress based on FQDNs:
    ```bash
    helm upgrade hdfs . -f ./values-host-based-ingress.yaml --reuse-values
    ```

    Set up port forwarding to the nginx ingress controller:
    ```bash
    sudo KUBECONFIG=$HOME/.kube/config kubectl port-forward -n ingress-nginx svc/ingress-nginx 80:80
    ```

    Access the web UIs using the following URLs:

    Component | URL
    --------- | ---
    HDFS | [http://hdfs.k8s.local/](http://hdfs.k8s.local/)

## Uninstall

```bash
kind delete cluster
```
