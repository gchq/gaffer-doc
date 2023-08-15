# How to deploy a simple graph

This guide will describe how to deploy a simple empty graph with the minimum configuration.

You will need:

- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- [helm](https://github.com/helm/helm/releases)
- A Kubernetes cluster (local or remote)
- An ingress controller running (for accessing UIs)

## Add the Gaffer Docker repo

To start with, you should add the Gaffer Docker repo to your helm repos. This will save the need for cloning this Git repository. If you have already done this you can skip this step.

```bash
helm repo add gaffer-docker https://gchq.github.io/gaffer-docker
```

## Choose the store

Gaffer can be backed with a number of different technologies to back its store. Which one you want depends on the use case but as a rule of thumb:

- If you want just something to spin up quickly at small scale and are not worried about persistence, use the MapStore.
- If you want to back it with a key value datastore, you can deploy the Accumulo Store.
- If you want to join two or more graphs together to query them as one, you will want to use the Federated Store.

### Deploy the MapStore

The [MapStore](TODO: link) is just an in-memory store that can be used for demos or if you need something small scale short-term. It is our default store so there is no need for any extra configuration.

You can install a MapStore by just running:

```
helm install my-graph gaffer-docker/gaffer
```

### Deploy the Accumulo Store

If you want to deploy an [Accumulo Store](TODO: link) with your graph, it is relatively easy to do so with some small additional configuration. Create a file called `accumulo.yaml` and add the following:

```yaml
accumulo:
  enabled: true
```

By default, the Gaffer user is created with a password of "gaffer" the CREATE_TABLE system permission with full access to the simpleGraph table which is coupled to the graphId. All the default Accumulo passwords are in place so if you were to deploy this in production, you should consider changing the [default accumulo passwords](TODO: LINK NEW DOC).

You can stand up the accumulo store by running:

```bash
helm install my-graph gaffer-docker/gaffer -f accumulo.yaml
```

### Deploy the Federated Store

If you want to deploy the [Federated Store](TODO: Link), all that you really need to do is set the `store.properties`. To do this add the following to a `federated.yaml` file:

```yaml
graph:
  storeProperties:
    gaffer.store.class: uk.gov.gchq.gaffer.federatedstore.FederatedStore
    gaffer.store.properties.class: uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties
    gaffer.serialiser.json.modules: uk.gov.gchq.gaffer.sketches.serialisation.json.SketchesJsonModules
```

The addition of the `SketchesJsonModules` is just to ensure that if the FederatedStore was connecting to a store which used sketches, they could be rendered nicely in json.

We can create the graph with:

```bash
helm install federated gaffer-docker/gaffer -f federated.yaml
```
