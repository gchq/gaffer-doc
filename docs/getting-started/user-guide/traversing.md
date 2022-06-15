# Traversing

The code for this example is [Traversing](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Traversing.java).

Traversing in Gaffer is a powerful feature, allowing a user to move around a graph, finding relationships between elements.

The main Operations that can be used to traverse a graph are described below using this graph:

```

                 --> 7 <--
               /           \
              /             \
             6  -->  3  -->  4
 ___        ^         \
|   |       /          /
 -> 8 -->  5  <--  2  <
          ^        ^
         /        /
    1 --         /
     \          /
       --------
```

## GetAdjacentIds

A GetAdjacentIds operation will return the vertex at the opposite end of connected edges to a provided seed vertex, 
meaning you can easily chain multiple GetAdjacentIds operations together and you will simply traverse around the graph, 
performing one hop at a time.

If you wish to return the properties of the final Edges you traverse down you can replace the last 
GetAdjacentIds operation in your chain with a GetElements operation.

A simple example of this can be seen below:


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<? extends Element>> getAdjacentIdsOpChain = new OperationChain.Builder()
        .first(new GetAdjacentIds.Builder()
                .input(new EntitySeed(2))
                .build())
        .then(new GetElements())
        .build();

final CloseableIterable<? extends Element> getAdjacentIdsResults = graph.execute(getAdjacentIdsOpChain, user);

printJsonAndPython("GET_ADJACENT_IDS_SIMPLE", getAdjacentIdsOpChain);

for (final Element result : getAdjacentIdsResults) {
    print("GET_ADJACENT_IDS_SIMPLE_RESULT", result.toString());
}

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAdjacentIds",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "GetElements"
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAdjacentIds",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "GetElements"
  } ]
}


{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAdjacentIds( 
      input=[ 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.GetElements() 
  ] 
)


{%- endcodetabs %}


Here are the results:

```
Entity[vertex=5,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@2a322cdc,count=<java.lang.Integer>1,edgeGroup=<java.util.TreeSet>[edge]]]
Entity[vertex=5,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3870b896,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=5,group=entity1,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=5,destination=6,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>11]]
Edge[source=1,destination=5,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>6]]
Edge[source=2,destination=5,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>7]]
Edge[source=8,destination=5,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>13]]
Entity[vertex=3,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@4935eabe,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=2,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>5]]
Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>7]]
Edge[source=6,destination=3,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>9]]
Entity[vertex=1,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@fa00bd0,count=<java.lang.Integer>1,edgeGroup=<java.util.TreeSet>[edge]]]
Entity[vertex=1,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@34cf9234,count=<java.lang.Integer>1,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=1,group=entity1,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>6]]

```

For more details and for more detailed examples of the GetAdjacentIds operation please see [GetAdjacentIds](operations/getadjacentids.md)

## GetWalks(GetElements, GetElements)

A GetWalks operation is used to retrieve all of the walks in a graph starting from a set of vertices.

In addition, you need to supply the list of GetElements operations you wish to execute - this allows you to control how many hops you want to do, 
which Edges you wish to traverse down in each hop and any filters you want to apply as you walk through the graph.

A simple example of this can be seen below:


{% codetabs name="Java", type="java" -%}
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
        .input(new EntitySeed(1))
        .build();

final Iterable<Walk> getWalksResults = graph.execute(getWalks, user);

printJsonAndPython("GET_WALKS_SIMPLE", getWalks);

for (final String walk : makeWalksPrintable(getWalksResults)) {
    print("GET_WALKS_SIMPLE_RESULT", walk);
}

{%- language name="JSON", type="json" -%}
{
  "class" : "GetWalks",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 1
  } ],
  "operations" : [ {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  } ],
  "resultsLimit" : 1000000
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.GetWalks",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ],
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  } ],
  "resultsLimit" : 1000000
}


{%- language name="Python", type="py" -%}
g.GetWalks( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  operations=[ 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)


{%- endcodetabs %}


Here are the results:

```
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 ]

```

For more details and for more detailed examples of the GetWalks operation please see [GetWalks](operations/getwalks.md)

## GetElements -> ToVertices -> ToEntitySeeds -> GetElements

It is valid to use GetElements -> GetElements to traverse a graph but you probably will not get the results you want.
The first GetElements hop (starting at 1) would return Edges 1 -> 5 and 1 -> 2.  The second hop would return Entities at 1, 5, 1 and 2 
followed by the Edges again 1 -> 5 and 1 -> 2.

Therefore, in order to get GetElements and GetElements to chain together you need to use ToVertices(UseMatchedVertex=OPPOSITE) to extract 2 and 5, 
then ToEntitySeeds to convert it into new  and EntitySeed(2) EntitySeed(5) then you can do GetElements again.

This example will be done in 4 stages shown below:

GetElements to return Edges.
Use ToVertices to extract the destination of the edges.
Wrap the destination vertices in EntitySeeds.
GetElements again.

Sample code can be seen below:


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EdgeSeed(2, 3, DirectedType.EITHER), new EdgeSeed(2, 5, DirectedType.EITHER))
                .build())
        .then(new ToVertices.Builder()
                .useMatchedVertex(ToVertices.UseMatchedVertex.OPPOSITE)
                .edgeVertices(ToVertices.EdgeVertices.NONE)
                .build())
        .then(new ToEntitySeeds())
        .then(new GetElements())
        .build();

final CloseableIterable<? extends Element> toVerticesResults = graph.execute(opChain, user);

printJsonAndPython("TO_VERTICES_SIMPLE", opChain);

for (final Element result : toVerticesResults) {
    print("TO_VERTICES_SIMPLE_RESULT", result.toString());
}

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetElements",
    "input" : [ {
      "class" : "EdgeSeed",
      "source" : 2,
      "destination" : 3,
      "matchedVertex" : "SOURCE",
      "directedType" : "EITHER"
    }, {
      "class" : "EdgeSeed",
      "source" : 2,
      "destination" : 5,
      "matchedVertex" : "SOURCE",
      "directedType" : "EITHER"
    } ]
  }, {
    "class" : "ToVertices",
    "edgeVertices" : "NONE",
    "useMatchedVertex" : "OPPOSITE"
  }, {
    "class" : "ToEntitySeeds"
  }, {
    "class" : "GetElements"
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetElements",
    "input" : [ {
      "class" : "EdgeSeed",
      "source" : 2,
      "destination" : 3,
      "matchedVertex" : "SOURCE",
      "directedType" : "EITHER"
    }, {
      "class" : "EdgeSeed",
      "source" : 2,
      "destination" : 5,
      "matchedVertex" : "SOURCE",
      "directedType" : "EITHER"
    } ]
  }, {
    "class" : "ToVertices",
    "edgeVertices" : "NONE",
    "useMatchedVertex" : "OPPOSITE"
  }, {
    "class" : "ToEntitySeeds"
  }, {
    "class" : "GetElements"
  } ]
}


{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EdgeSeed( 
          source=2, 
          destination=3, 
          directed_type="EITHER", 
          matched_vertex="SOURCE" 
        ), 
        g.EdgeSeed( 
          source=2, 
          destination=5, 
          directed_type="EITHER", 
          matched_vertex="SOURCE" 
        ) 
      ] 
    ), 
    g.ToVertices( 
      edge_vertices="NONE", 
      use_matched_vertex="OPPOSITE" 
    ), 
    g.ToEntitySeeds(), 
    g.GetElements() 
  ] 
)


{%- endcodetabs %}


Here are the results:

```
Entity[vertex=5,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@6f4053bf,count=<java.lang.Integer>1,edgeGroup=<java.util.TreeSet>[edge]]]
Entity[vertex=5,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@60b12b0c,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=5,group=entity1,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=5,destination=6,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>11]]
Edge[source=1,destination=5,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>6]]
Edge[source=2,destination=5,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>7]]
Edge[source=8,destination=5,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>13]]
Entity[vertex=2,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@28a74b1c,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=2,group=entity1,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>7]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=3,destination=2,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>5]]
Entity[vertex=3,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@1098f98b,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=2,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>5]]
Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>7]]
Edge[source=6,destination=3,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>9]]

```

This however is quite wasteful and you would be better using GetAdjacentIds, shown above.  One reason it would be useful to use this chained method
would be if you need to cache the results from the first hop, for example GetElements -> ExportToSet -> ToVertices -> ToEntitySeeds -> GetElements.

For more details and for more detailed examples of the ToVertices operation please see [ToVertices](operations/tovertices.md)
