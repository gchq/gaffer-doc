# Graph Metadata Configuration

The graph configuration file is a JSON file that configures a few bits of the
Gaffer graph. Primarily it is used to set the name and description along with
any additional hooks to run before an operation chain e.g. to impose limits on
max results etc. For example, a simple graph configuration file may look like:

```json title="graphConfig.json"
{
    "graphId": "ExampleGraph",
    "description": "An example graph"
}
```

## Changing Values

The standard file location in the gaffer images for the file is `/gaffer/graph/graphConfig.json`

To change any of the values for a standard Gaffer deployment all that's needed
is to configure the JSON file for the `graphConfig`. The key value pairs in
the file can then be configured as you wish and upon restarting the graph
the values will be updated (assuming the file is loaded correctly).

However be aware, if you are using the Accumulo store, updating the `graphId` is
a little more complicated since the `graphId` corresponds to an Accumulo table.
This means that if you change the ID then a new Accumulo table will be used and
any existing data would be lost.

!!! tip
    You can see you new description if you to the Swagger UI and call the
    `/graph/config/description` endpoint.
