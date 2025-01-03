# Simple Federated Store Configuration

!!! warning
    The simple federated store is still under development, with scope to replace
    the standard federated store in release 2.4.0. Some configuration options
    and features may be subject to change.

## Introduction

The Simple Federated Store enables a user to add and query multiple Gaffer
graphs through a single endpoint/instance. Queries submitted to a federated
store are forwarded to a select set of graphs that then execute the query
locally. The results from each graph are aggregated together to form the
final result to give the appearance of coming from one graph.

Due to its unique nature a federated store has various additional configuration
and features compared to a normal store. This page covers the different
configuration an admin can apply to this store type. Further information on
[controlling graph access](./access-control.md) and [additional considerations](./additional-info.md)
with a federated store can be found on thier respective pages.

To get started with a federated store simply set the store class and properties
like:

```properties
gaffer.store.class=uk.gov.gchq.gaffer.federated.simple.FederatedStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.federated.simple.FederatedStoreProperties
```

## Store Properties

As with a standard Gaffer graph the usual store properties are available to a
federated store; however, additional properties are available to configure the
the different aspects of federating. The [reference guide](../../../reference/store-properties/federated-store.md)
covers store properties specific to a federated store and their usage.

!!! note
    Many of the merge related properties are just defaults and can be overridden
    by the user on a per query basis.

## Merge Operators

A key part to the federated store are the merge operators. These control how
results from multiple graphs are reduced to one result so can greatly effect the
results returned by the store. As outlined in the [store properties section](#store-properties),
these operators can be configured with defaults or overridden for a query via the
operation options using the same properties.

Sensible defaults are in place if not specified however, you may wish to chose your
own operators to be used. The only requirement for an operator is for it to
satisfy Java's [`BinaryOperator`](https://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html)
interface, you can then specify it using the property key for the data type you
wish to use it for.

!!! note
    Please note you currently can't chose a merge operator for operations that
    return an `Iterable` type, they will always just be chained together (an
    iterable of `Element`s is an obvious exception, please see the [user guide](../../../user-guide/query/gaffer-syntax/federated-queries.md#how-are-results-merged)
    for more details).

## Adding and Removing Graphs

A federated stores main purpose is to hold a library of 'sub' graphs. These
graphs are stored in the Gaffer [cache](./additional-info.md#cache-considerations) so can be shared between multiple
federated stores.

You can think of a graph that has been added to a federated store as essentially
a pointer to the real graph. This generally means all the information required
to create the graph in the first place (e.g. schema, store properties etc.) are
required to add the graph to a federated store. Because of this, a common design
pattern you may wish to adopt is to have one running Accumulo cluster to which
you can add multiple Gaffer graphs through the federated store. This means you
do not need to setup multiple Gaffer instances and can query all of the graphs
through the federated store.

```mermaid
graph LR
    A(["Federated Store"])-->B(["Gaffer Graph 1"])
    A-->C(["Gaffer Graph 2"])
    B-->D(["Accumulo"])
    C-->D
```

### Adding a new Graph

To add a new graph to a federated store a unique operation is available to
federated stores called `AddGraph`. This operation lets you input the
graph config, schema and store properties for the graph letting you add a
new graph like so:

!!! example ""
    === "Java"
        ```java
        // Choose a graph ID for your graph
        final String graphId = "myGraph";

        // Replace the graph config, schema and properties for your use case
        final AddGraph operation = new AddGraph.Builder()
            .graphConfig(new GraphConfig(graphId))
            .schema(new Schema())
            .properties(new Properties())
            .build();
        ```

    === "JSON"
        Replace the graph config, schema and properties for your use case.

        ```json
        {
            "class": "uk.gov.gchq.gaffer.federated.simple.operation.AddGraph",
            "graphConfig": {
                "graphId": "myGraph"
            },
            "schema": {
                "entities": {},
                "edges": {},
                "types": {}
            },
            "properties": {
                "gaffer.store.class": "uk.gov.gchq.gaffer.accumulostore.AccumuloStore",
                "gaffer.store.properties.class": "uk.gov.gchq.gaffer.accumulostore.AccumuloProperties",
                "gaffer.cache.service.class": "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService"
            }
        }
        ```

Once a graph has been added the graph ID will become available to the store so
can be referenced when running an operation. More information on running an
operation on a sub graph and available operation options can be found in the
[user guide](../../../user-guide/query/gaffer-syntax/federated-queries.md).

!!! note
    Added graphs can also have access controls enforced on them, please see the
    [access control guide](./access-control.md) for more information.

### Removing a Graph

Along with adding a graph you can also remove a graph from a federated store. By
default this will simply dereference the graph, meaning if the graph had
persistent storage the data will be left untouched. The data can then be
re-accessed at a later date by simply adding the graph back to the store.

When removing a graph you can also opt to delete all the data as well. This
obviously means the data cannot be recovered by simply re-adding the graph at a
later date.

!!! note
    To remove a graph a user requires write access to graph, please see the
    [access control guide](./access-control.md) for more information.

To remove a graph you can use the following operation like so:

!!! example ""
    === "Java"
        Remove a graph leaving the data untouched (if persistent).

        ```java
        final RemoveGraph removeGraph = new RemoveGraph.Builder()
            .graphId(graphId)
            .build();
        ```

        Remove a graph and delete all the data.

        ```java
        final RemoveGraph removeGraph = new RemoveGraph.Builder()
            .graphId(graphId)
            .deleteAllData(true)
            .build();
        ```

    === "JSON"
        Remove a graph leaving the data untouched (if persistent).

        ```json
            {
                "class": "uk.gov.gchq.gaffer.federated.simple.operation.RemoveGraph",
                "graphId": "myGraph"
            }
        ```

        Remove a graph and delete all the data.

        ```json
            {
                "class": "uk.gov.gchq.gaffer.federated.simple.operation.RemoveGraph",
                "graphId": "myGraph",
                "deleteAllData": true
            }
        ```
