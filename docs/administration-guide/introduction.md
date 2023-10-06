# Administration Guide

This guide is aimed at administrators of a Gaffer deployment and more
advanced users. It is recommended to read the [user guide](../user-guide/introduction.md)
and be familiar with Gaffer beforehand as many of the core concepts are
expanded on and covered in more detail.

## How to Run Gaffer?

There are detailed guides in this section on how to set up a Gaffer instance,
covering both containerised deployments via standard Docker/Podman along with
Kubernetes deployment via Helm.

- [Kubernetes Guide](./gaffer-deployment/kubernetes-guide/running-on-kubernetes.md)
- [Docker Guide](./gaffer-deployment/gaffer-docker/gaffer-images.md)

## Graph Configuration

There are many aspects of a Gaffer graph deployment that can be configured.
If you wish to know how and what can be configured along with a more in-depth
guide of Gaffers schemas you can see the section on configuring a graph.

- [Configuration Overview](./gaffer-config/config.md)

## Gaffer Stores

The store selection for the Gaffer backend is also covered in this guide to
provide an overview of the different types and features each provides.

- [Store Guide](./gaffer-stores/store-guide.md)
- [Map Store](./gaffer-stores/map-store.md)
- [Accumulo Store](./gaffer-stores/accumulo-store.md)
- [Proxy Store](./gaffer-stores/proxy-store.md)
- [Federated Store](./gaffer-stores/federated-store.md)
