# Additional Information on Simple Federation

This page contains additional information and considerations
a admin may need to know when using the federated store type.

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
| `federated.graphIds` | List of graph IDs to submit the operation to, formatted the same as properties e.g. `"graph1,graph2"` |
| `federated.excludedGraphIds` | List of graph IDs to exclude from the query. If this is set any graph IDs on a `federated.graphIds` option are ignored and instead, all graphs are executed on except the ones specified e.g. `"graph1,graph2"` |
| `federated.aggregateElements` | Should the element aggregator be used when merging element results. |
| `federated.forwardChain` | Should the whole operation chain be sent to the sub graph or not. If set to `false` each operation will inside the chain will be sent separately, so merging from each graph will happen after each operation instead of at the end of the chain. This will be inherently slower if turned off so is `true` by default. |

## Cache Considerations

## Schema Compatibility
