# Creating a Simple Deployment

This guide will describe how to deploy a simple graph on a Kubernetes cluster
with the minimum configuration. It is assumed you have read the [previous page](./running-on-kubernetes.md)
to get an overview of running Gaffer on Kubernetes.

To start with, you should add the Gaffer Docker repo to your Helm repos. This
will save the need for cloning this Git repository. If you have already done
this you can skip this step.

```bash
helm repo add gaffer-docker https://gchq.github.io/gaffer-docker
```

## Choose the Gaffer Store

Gaffer can be backed with a number of different technologies to back its store.
Which one you want depends on the use case but as a rule of thumb:

- If you want just something to spin up quickly at small scale and are not
  worried about persistence, use the [Map Store](../../gaffer-stores/map-store.md).
- If you want to back it with a key value datastore, you can deploy the [Accumulo Store](../../gaffer-stores/accumulo-store.md).
- If you want to join two or more graphs together to query them as one, you will
  want to use the [Federated Store](../../gaffer-stores/federated-store.md).

## Deploy using a Map Store

A Map Store is just an in-memory store that can be used for demos or if you need
something small scale short-term. It is our default store so there is no need
for any extra configuration.

You can install a Map Store by just running:

```bash
helm install my-graph gaffer-docker/gaffer
```

## Deploy using an Accumulo Store

If you want to deploy an Accumulo Store with your graph, it is relatively easy
to do so with some small additional configuration. Create a file called
`accumulo.yaml` and add the following:

```yaml
accumulo:
  enabled: true
```

By default, the Gaffer user is created with a password of "gaffer" the
`CREATE_TABLE` system permission with full access to the simpleGraph table which
is coupled to the `graphId`.

!!! warning
    All the default Accumulo passwords are in place so if you were to deploy this
    in production, you should consider changing the [default Accumulo passwords](change-accumulo-passwords.md).

You can stand up an Accumulo store by running:

```bash
helm install my-graph gaffer-docker/gaffer -f accumulo.yaml
```

## Deploy using a Federated Store

If you want to deploy a Federated Store, all that you really need to do is set
the `store.properties`. To do this add the following to a `federated.yaml` file:

```yaml
graph:
  storeProperties:
    gaffer.store.class: uk.gov.gchq.gaffer.federatedstore.FederatedStore
    gaffer.store.properties.class: uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties
    gaffer.serialiser.json.modules: uk.gov.gchq.gaffer.sketches.serialisation.json.SketchesJsonModules
```

The addition of the `SketchesJsonModules` is just to ensure that if the
Federated Store was connecting to a store which used sketches, they could be
rendered nicely in json.

We can create the graph with:

```bash
helm install federated gaffer-docker/gaffer -f federated.yaml
```

!!! note
    For information on how to configure the deployed graph further please
    see the [Gaffer configuration guides](../../gaffer-config/config.md).
