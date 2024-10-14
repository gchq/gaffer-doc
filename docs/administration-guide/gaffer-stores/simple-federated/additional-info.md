# Additional Information on Simple Federation

This page contains additional information and considerations
an admin may need to know when using the federated store type.

## How are Operations Handled?

Gaffer operations are handled quite differently when using the federated store.
The general usage is that the operation submitted to the store will be forwarded
to the sub graph for execution. This means a user can typically use a federated
store like they would a normal store by submitting the same operation chains you
would use on any other store.

A user has control of some aspects of federation using the options passed to the
operation. These can be used to do things like pick graphs or control the
merging, a full list of the available options are outlined in the following
table:

| Option | Description |
| --- | --- |
| `federated.graphIds` | List of graph IDs to submit the operation to, formatted as a comma separated string e.g. `"graph1,graph2"` |
| `federated.excludedGraphIds` | List of graph IDs to exclude from the query. If this is set any graph IDs on a `federated.graphIds` option are ignored and instead, all graphs are executed on except the ones specified e.g. `"graph1,graph2"` |
| `federated.aggregateElements` | Should the element aggregator be used when merging element results. |
| `federated.forwardChain` | Should the whole operation chain be sent to the sub graph or not. If set to `false` each operation will inside the chain will be sent separately, so merging from each graph will happen after each operation instead of at the end of the chain. This will be inherently slower if turned off so is `true` by default. |

Along with the options above, all merge classes can be overridden per query
using the same property key as you would via the store properties. Please see
the table [here](./configuration.md#store-properties) for more information.

If you wish to submit different operations to different graphs in the same query
you can do this using the `federate.forwardChain` option. By setting this to
false on the outer operation chain the options on the operations inside it will
be honoured. An example of this can be seen below:

!!! note
    This will turn off any merging of the results at the end of the chain, the
    operation chain will act like a standard chain where each operations output
    is now the input of the next operation. However, merging will still happen
    on each operation if more than one graph is specified for it.

!!! example ""
    This seeds for an entity from one graph and adds it into another graph.

    ```json
    {
        "class": "OperationChain",
        "options": {
            "federated.forwardChain": false
        },
        "operations": [
            {
                "class": "GetElements",
                "options": {
                    "federated.graphIds": "graph1"
                },
                "input": [
                    {
                        "class": "EntitySeed",
                        "vertex": "1"
                    }
                ]
            },
            {
                "class": "AddElements",
                "options": {
                    "federated.graphIds": "graph2"
                }
            }
        ]
    }
    ```

## Cache Considerations

The federated store utilises the [Gaffer cache](../store-guide.md#caches) to store
graphs that have been added to the store. This means all features available to
normal caches are also available to the graph storage, allowing the sharing and
persisting of graphs between instances.

The federated store will use the default cache service to store graphs in. It
will also add a standard suffix meaning if you want to share graphs you will
need to set this to something other than the graph ID (see [here](../store-guide.md#cache-service)).

## Schema Compatibility

When querying multiple graphs, the federated store will attempt to merge each graph's schema together. This means the schemas will need to be
compatible in order to query across them. Generally you will need to ensure
any shared groups can be merged correctly, a few examples of criteria to
consider are:

- Any properties in a shared group defined in both schemas need to have the same
  type and aggregation function.
- Any visibility properties need to be compatible or they will be removed from the
  schema.
- Groups with different properties in each schema will be merged so the group has
  all the properties in the merged schema.
- Any groupBy definitions need to be compatible or will be removed.
