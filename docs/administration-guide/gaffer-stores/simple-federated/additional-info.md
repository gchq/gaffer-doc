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
| `federated.useDefaultGraphIds` | Explicitly specifies that the default Graph IDs from the store.properties file should be used. By default if no graph ID options are specified the default graph IDs will still be used where applicable. However, specifying this on an operation chain means the whole chain will be sent to the sub graph, and so merging from each graph will happen at the end of the chain instead of after each operation, hopefully increasing performance.
| `federated.separateResults` | A boolean option to specify if the results from each graph should be kept separate. If set, this will return a map where each key value is the graph ID and its respective result. |
| `federated.skipGraphOnFail` | A boolean option to specify if the operation should continue even if it fails on one or more of the sub graphs. |

Along with the options above, all merge classes can be overridden per query
using the same property key as you would via the store properties. Please see
the table [here](./configuration.md#store-properties) for more information.

If you wish to submit different operations to different graphs in the same query
you can do this by omitting any graph ID options on the outer operation chain.
You can then specify the graph IDs on the individual operations in the chain
instead. An example of this can be seen below:

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

The federated store will use the default cache service to store graphs in. It will
also store graphs in a cache named `"federatedGraphCache_"` followed by the graph
ID of the federated store. You may wish to change this to have common storage
of graphs between stores using the `gaffer.store.federated.graphCache.name`
store property.

### Named Operations and Views

Named Operations and Views can be added to different caches if specified. By
passing graph IDs in the add operation (e.g. `AddNamedOperation`) you can make
the Named Operation or View specific to the graph(s) you specified. However,
this will mean if you try to run it on another graph it will not be available.

If you do not specify any graph IDs in the add operation. This will instead add
the Operation/View to the federated store's cache. By doing this anything Named
will be resolved before forwarding to sub graphs meaning in essence it is
available to all sub graphs.

!!! example ""
    === "Add to a sub graph"
        ```java
        final AddNamedOperation addNamedOp = new AddNamedOperation.Builder()
            .option(FederatedOperationHandler.OPT_GRAPH_IDS, "subGraph")
            .name("NamedOperation")
            .operationChain(new OperationChain.Builder()
                    .first(new GetAllElements())
                    .build())
            .build();
        ```

    === "Add to a federated store"
        ```java
        final AddNamedOperation addNamedOp = new AddNamedOperation.Builder()
            .name("NamedOperation")
            .operationChain(new OperationChain.Builder()
                    .first(new GetAllElements())
                    .build())
            .build();
        ```

## Schema Compatibility

When querying multiple graphs, the federated store will attempt to merge each
graph's schema together. This means the schemas will need to be compatible in
order to query across them. Generally you will need to ensure any shared groups
can be merged correctly, a few examples of criteria to consider are:

- Any properties in a shared group defined in both schemas need to have the same
  type and aggregation function.
- If the visibility property has been defined differently in each schema it will
  be removed from the merged schema. This does not effect the actual visibility
  of the data as that will still be applied at the sub graph level.
- Groups with different properties in each schema will be merged so the group has
  all the properties in the merged schema.
- Any groupBy definitions need to be compatible or will be removed.
- If the vertex serialiser has been defined differently in each schema it will
  be removed from the merged schema.
