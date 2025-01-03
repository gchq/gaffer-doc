# Additional Information on Simple Federation

This page contains additional information and considerations
an admin may need to know when using the federated store type.

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

If you do not specify any graph IDs in the add operation, any Named
Operations/Views will instead be added to the federated store's cache. By doing
this anything Named will be resolved before forwarding to sub graphs meaning in
essence it is available to all sub graphs.

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
