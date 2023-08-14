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

```
kind create cluster --image kindest/node:v1.24.4
```

## Container Images

If the versions of the container images you would like to deploy are not available in [Docker Hub](https://hub.docker.com/u/gchq) then you will need to build them yourself and import them into your kind cluster.

To import the images, run this from the kubernetes directory:

```
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

```
INGRESS_NGINX_VERSION="nginx-0.30.0"
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/${INGRESS_NGINX_VERSION}/deploy/static/mandatory.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/${INGRESS_NGINX_VERSION}/deploy/static/provider/baremetal/service-nodeport.yaml
```

## Deploy Helm Charts

TODO: Link or copy different md files here which show how to deploy different apps.

## Uninstall

```
kind delete cluster
```
