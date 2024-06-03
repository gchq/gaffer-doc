# Running Gaffer on Kubernetes

!!! warning
    Gaffer Kubernetes is not currently up to date. You will need to use a
    version of Kubernetes <= 1.25 for deployments.
    The information here is subject to change in future releases.

Gaffer's Open Container Initiative (OCI) images mean it is also possible to
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

## Using Custom Images

You may wish to create custom images that have configuration or additional
libraries baked in.

The [Docker deployment guide](../gaffer-docker/gaffer-images.md#custom-images)
has information on how to create new images but you will need a way of making
the custom images visible to the Kubernetes cluster. Once visible you can switch
them out.

Create a `custom-images.yaml` file with the following contents:

```yaml
# Add custom REST API image
api:
  image:
    repository: custom-rest
    tag: latest

# Add custom Accumulo image
accumulo:
  image:
    repository: custom-gaffer-accumulo
    tag: latest
```

To switch them run:

```bash
helm upgrade my-graph gaffer-docker/gaffer -f custom-images.yaml --reuse-values
```
