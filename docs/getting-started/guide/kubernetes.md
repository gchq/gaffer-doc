# Gaffer in Kubernetes

The [gaffer-docker](https://github.com/gchq/gaffer-docker) repository contains all code needed to run Gaffer using Docker.

All the files needed to get started using Gaffer in Kubernetes are contained in the ['kubernetes'](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes) sub-folder. In this directory you can find the Helm charts required to deploy various applications onto Kubernetes clusters.

The Helm charts and associated information for each application can be found in the following places:

- [Gaffer](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer)
- [Example Gaffer Graph of Road Traffic Data](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer-road-traffic)
- [JupyterHub with Gaffer Integrations](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer-jhub)
- [HFDS](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/hdfs)
- [Accumulo](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/accumulo)

These charts can be accessed by cloning our repository or by using our Helm repo hosted on our [Github Pages Site](https://gchq.github.io/gaffer-docker/).

## Requirements

Before you can deploy any of these applications you need to have installed Kubernetes. Information on how to do this can be found in the [Kubernetes docs](https://kubernetes.io/docs/setup/)

You will also need to install Docker along with the compose plugin. Information on how to do this can be found in the [Docker docs](https://docs.docker.com/get-docker/).

## Adding this repo to Helm

To add the gaffer-docker repo to helm run:

```
helm repo add gaffer-docker https://gchq.github.io/gaffer-docker
```
