# Gaffer in Kubernetes

The [gaffer-docker](https://github.com/gchq/gaffer-docker) repository contains all code needed to run Gaffer using Docker and Kubernetes.

All the files needed to get started using Gaffer in Kubernetes are contained in the ['kubernetes'](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes) sub-folder of the [gaffer-docker](https://github.com/gchq/gaffer-docker) repository.
In this directory you can find the Helm charts required to deploy various applications onto Kubernetes clusters.

The Helm charts and associated information for each application can be found in the following places:

- [Gaffer](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer)
- [Example Gaffer Graph of Road Traffic Data](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer-road-traffic)
- [JupyterHub with Gaffer Integrations](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/gaffer-jhub)
- [HFDS](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/hdfs)
- [Accumulo](https://github.com/gchq/gaffer-docker/tree/develop/kubernetes/accumulo)

These charts can be accessed by cloning our repository or by using our Helm repo hosted on our [Github Pages Site](https://gchq.github.io/gaffer-docker/).

## Requirements

To deploy these applications, you'll need access to a suitable Kubernetes distribution. 

You will also need to install a container management engine, for example Docker or Podman, to build, run and manage your containers.

## Adding this repo to Helm

To add the gaffer-docker repo to helm run:

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