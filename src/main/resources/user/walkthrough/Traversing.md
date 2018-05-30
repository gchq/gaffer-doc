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

## GetWalks(GetElements, GetElements)

A GetWalks operation is used to retrieve all of the walks in a graph starting from one of a set of provided EntityIds, with a maximum length.

A GetWalks operation is configured using a user-supplied list of GetElements operations. These are executed sequentially, with the output of one operation providing the input EntityIds for the next.

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

For more details and for more detailed examples of the GetWalks operation please see [GetWalks](operations/getwalks.md)

## GetAdjacentIds

A GetAdjacentIds operation will return the vertex at the opposite end of connected edges to a provided seed vertex.

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

For more details and for more detailed examples of the GetAdjacentIds operation please see [GetAdjacentIds](operations/getadjacentids.md)

## GetElements -> ToVertices -> ToEntitySeeds -> ToSet

Using a ToVertices operation, within a chain, it is possible to traverse a graph.  This example will be done in 3 stages shown below:

GetElements to return Edges.
Use ToVertices to extract the destination of the edges.
Wrap the destination vertices in EntitySeeds.

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

For more details and for more detailed examples of the ToVertices operation please see [ToVertices](operations/tovertices.md)