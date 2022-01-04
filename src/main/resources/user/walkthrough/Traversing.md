${HEADER}

${CODE_LINK}

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

${START_JAVA_CODE}
${GET_ADJACENT_IDS_SIMPLE_SNIPPET}
${JSON_CODE}
${GET_ADJACENT_IDS_SIMPLE_JSON}
${FULL_JSON_CODE}
${GET_ADJACENT_IDS_SIMPLE_JSON}
${PYTHON_CODE}
${GET_ADJACENT_IDS_SIMPLE_PYTHON}
${END_CODE}

Here are the results:

```
${GET_ADJACENT_IDS_SIMPLE_RESULT}
```

For more details and for more detailed examples of the GetAdjacentIds operation please see [GetAdjacentIds](../operations/getadjacentids.md)

## GetWalks(GetElements, GetElements)

A GetWalks operation is used to retrieve all of the walks in a graph starting from a set of vertices.

In addition, you need to supply the list of GetElements operations you wish to execute - this allows you to control how many hops you want to do, 
which Edges you wish to traverse down in each hop and any filters you want to apply as you walk through the graph.

A simple example of this can be seen below:

${START_JAVA_CODE}
${GET_WALKS_SIMPLE_SNIPPET}
${JSON_CODE}
${GET_WALKS_SIMPLE_JSON}
${FULL_JSON_CODE}
${GET_WALKS_SIMPLE_FULL_JSON}
${PYTHON_CODE}
${GET_WALKS_SIMPLE_PYTHON}
${END_CODE}

Here are the results:

```
${GET_WALKS_SIMPLE_RESULT}
```

For more details and for more detailed examples of the GetWalks operation please see [GetWalks](../operations/getwalks.md)

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

${START_JAVA_CODE}
${TO_VERTICES_SIMPLE_SNIPPET}
${JSON_CODE}
${TO_VERTICES_SIMPLE_JSON}
${FULL_JSON_CODE}
${TO_VERTICES_SIMPLE_JSON}
${PYTHON_CODE}
${TO_VERTICES_SIMPLE_PYTHON}
${END_CODE}

Here are the results:

```
${TO_VERTICES_SIMPLE_RESULT}
```

This however is quite wasteful and you would be better using GetAdjacentIds, shown above.  One reason it would be useful to use this chained method
would be if you need to cache the results from the first hop, for example GetElements -> ExportToSet -> ToVertices -> ToEntitySeeds -> GetElements.

For more details and for more detailed examples of the ToVertices operation please see [ToVertices](../operations/tovertices.md)
