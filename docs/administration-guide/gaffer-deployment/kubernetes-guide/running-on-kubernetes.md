# Running Gaffer on Kubernetes

Gaffers Open Container Initiative (OCI) images mean it is also possible to
deploy via kubernetes to give an alternative scalable deployment. This guide
will assume the reader is familiar with general usage of kubernetes, further
reading is available in the [official documentation](https://kubernetes.io/docs/home/).

!!! note
    All the files needed to get started using Gaffer in Kubernetes are contained
    in the [`kubernetes`](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes)
    sub-folder of the [`gaffer-docker`](https://github.com/gchq/gaffer-docker)
    repository.

## Pre-requisites

To deploy container images on a kubernetes cluster, you'll need the following:

- A Kubernetes cluster (local or remote)
- [kubectl](https://kubernetes.io/docs/reference/kubectl/)
- [helm](https://helm.sh/docs/intro/install/)
- An ingress controller running (e.g. NGINX)

You will also need to install a container management engine such as, `containerd`
via Docker or Podman, to run and manage your containers.

## Adding the Gaffer Helm Charts

Helm is a package manager for Kubernetes which uses a format called *charts*.
A chart is a collection of files that describe a set of Kubernetes resources,
essentially what images to run where and how much resources they can access.

The Helm charts for Gaffer can be found in the following places in the
`gaffer-docker` repository:

- [Gaffer](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer)
- [Accumulo](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/accumulo)
- [HDFS](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/hdfs)
- [JupyterHub with Gaffer Integrations](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer-jhub)
- [Example Gaffer Graph of Road Traffic Data](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer-road-traffic)

These charts can be accessed by cloning our repository or by using Helm to add
the `gaffer-docker` repo:

```bash
helm repo add gaffer-docker https://gchq.github.io/gaffer-docker
```

## How to Guides

There are a number of guides to help you deploy Gaffer on Kubernetes. It is important you look at these before you get started as they provide the initial steps for running these applications.

* [Deploy a simple empty graph](deploy-empty-graph.md)
* [Add your schema](deploy-schema.md)
* [Change the graph ID and description](change-graph-metadata.md)
* [Adding your own libraries and functions](add-libraries.md)
* [Changing passwords for the Accumulo store](change-accumulo-passwords.md)