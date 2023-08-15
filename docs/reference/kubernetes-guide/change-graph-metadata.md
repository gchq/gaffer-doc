# Changing the Graph ID and Description

By default, the default Gaffer deployment ships with the Graph name "simpleGraph" and description "A graph for demo purposes" These are just placeholders and can be overwritten. This guide will show you how.

The first thing you will need to do is [deply an empty graph](TODO: LINK NEW DOCS).

## Changing the description

Create a file called `graph-meta.yaml`. We will use this file to add our description and graph ID. Changing the description is as easy as changing the `graph.config.description` value.

```yaml
graph:
  config:
    description: "My graph description"
```

## Deploy the new description

Upgrade your deployment using helm:

```bash
helm upgrade my-graph gaffer-docker/gaffer -f graph-metadata.yaml --reuse-values
```

The `--reuse-values` argument means we do not override any passwords that we set in the initial construction.

You can see you new description if you to the Swagger UI and call the `/graph/config/description` endpoint.

## Updating the Graph ID

This may be simple or complicated depending on your store type. If you are using the Map or Federated store, you can just set the `graph.config.graphId` value in the same way. Though if you are using a MapStore, the graph will be emptied as a result.

However, if you are using the Accumulo store, updating the graph Id is a little more complicated since the Graph Id corresponds to an Accumulo table. We have to change the gaffer users permissions to read and write to that table. To do that update the graph-meta.yaml file with the following contents:

```yaml
graph:
  config:
    graphId: "MyGraph"
    description: "My Graph description"

accumulo:
  config:
    userManagement:
      users:
        gaffer:
          permissions:
            table:
              MyGraph:
                - READ
                - WRITE
                - BULK_IMPORT
                - ALTER_TABLE
```

## Deploy your changes

Upgrade your deployment using Helm.

```bash
helm upgrade my-graph gaffer-docker/gaffer -f graph-metadata.yaml --reuse-values
```

If you take a look at Accumulo monitor, you will see your new Accumulo table.
