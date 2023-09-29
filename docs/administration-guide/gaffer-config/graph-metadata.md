# Graph Metadata Configuration

The graph configuration file is a JSON file that configures few bits of the
Gaffer graph. Primarily it is used to set the name and description along with
any additional hooks to run before an operation chain e.g. to impose limits on
max results etc. For example, a simple graph configuration file may look like:

```json title="graphConfig.json"
{
    "graphId": "ExampleGraph",
    "description": "An example graph"
}
```

## Configuring a Standard Deployment

To change any of the values for a standard Gaffer deployment all that's needed
is to configure the JSON file for the `graphConfig`. The key value pairs in
the file can then be configured as you wished and upon restarting the graph
the values will be updated (assuming the file is loaded correctly).

The standard file location in the gaffer images for the file is `/gaffer/graph/graphConfig.json`

## Configuring a Helm Deployment

Configuring the graph metadata via Helm follows a similar principal to the JSON
files however, you must use the YAML format instead for the key value pairs. The
following gives an example of how the description value can be updated via Helm.

Create a file called `graph-meta.yaml`. We will use this file to add our
description and graph ID. Changing the description is as easy as changing the
`graph.config.description` value.

```yaml
graph:
  config:
    description: "My graph description"
```

Upgrade your deployment using Helm to load the new file:

```bash
helm upgrade my-graph gaffer-docker/gaffer -f graph-metadata.yaml --reuse-values
```

The `--reuse-values` argument means we do not override any passwords that we set
in the initial construction.

!!! tip
    You can see you new description if you to the Swagger UI and call the
    `/graph/config/description` endpoint.

## Updating the Graph ID

This may be simple or complicated depending on your store type. If you are using
a Map or Federated store, you can just set the `graph.config.graphId` value in
the same way. Though if you are using a Map Store, the graph will be emptied as
a result.

However, if you are using the Accumulo store, updating the `graphId` is a little
more complicated since the Graph Id corresponds to an Accumulo table. We have to
change the gaffer users permissions to read and write to that table.To do that
update the graph-meta.yaml file with the following contents:

=== "JSON"
    Configure the `graphConfig.json` file.

    ```json
    {
        "graphId": "MyGraph",
        "description": "My Graph description"
    }
    ```

=== "YAML"
    Add to a `graph-meta.yaml` or similar file and load via Helm.

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
