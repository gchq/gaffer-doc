# Subgraphs

The code for this example is [Subgraphs](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Subgraphs.java).

This example extends the previous dev.walkthrough and demonstrates how a subgraph could be created using a single operation chain.

We will start by loading the data into the graph as we have done previously.

The operation chain is built up using several [GetElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) operations. 
In between each of these operations we cache the results in memory using a LinkedHashSet by executing the [ExportToSet](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/ExportToSet.html) operation. 
For larger graphs we could simply swap the ExportToSet to [ExportToGafferResultCache](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/ExportToGafferResultCache.html) operation.

In order to chain GetElements operations together we need to extract the destination vertex of each result Edge and wrap the destination vertex in an EntitySeed so that it can be used as a seed for the next operation.

We can repeat this combination of operations for extract a subgraph contain 'x' number of hops around the graph. In this example we will just do 2 hops as our graph is quite basic. 
We finish off by using a [GetSetExport](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/GetSetExport.html) operation to return the set of edges.

Although this results several operations in chain, each operation is quite simple and this demonstrates the flexibility of the operations. 

The full chain looks like:


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("M5"))
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("RoadHasJunction")
                        .build())
                .build())
        .then(new ExportToSet<>())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.DESTINATION)
                .build())
        .then(new ToEntitySeeds())
        .then(new GetElements.Builder()
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("RoadUse", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(1L))
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport())
        .build();
{%- endcodetabs %}


For each 'hop' we use a different View, to specify the edges we wish to hop down and different filters to apply. 
The export operations will export the currently result and pass the result onto the next operation. This is why we have the slightly strange DiscardOutput operation before we return the final results. 

The result is the full set of traversed Edges:

```
Edge[source=M5,destination=10,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=23,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=27,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=28,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=23,destination=24,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 PDT 2000]]

```

Here are some further export [Operation Examples](../operations/contents) that demonstrate some more advanced features of exporting. 
