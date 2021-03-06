${HEADER}

${CODE_LINK}

This example demonstrates how storing a HyperLogLogPlus object on each vertex in a graph allows us to quickly estimate its degree (i.e. the number of edges it is involved in). The estimate can be obtained without scanning through all the edges involving a vertex and so is very quick, even if the degree is very large.

To add properties to vertices we need to add an Entity to our schema. Entities are associated with a vertex and contain a set of properties about that vertex.

### Elements schema

This is our new elements schema. You can see we have added a Cardinality Entity. This will be added to every vertex in the Graph. This Entity has a 'hllp' property that will hold the HyperLogLogPlus cardinality value.

${ELEMENTS_JSON}

### Types schema

We have added a new type - hllp. This is a HyperLogLogPlus object. The HyperLogLogPlus object will be used to estimate the cardinality of a vertex.
We also added in the serialiser and aggregator for the HyperLogLogPlus object. Gaffer will automatically aggregate the cardinalities, using the provided aggregator, so they will keep up to date as new elements are added to the graph.

${TYPES_JSON}

Here are all the edges loaded into the graph (unfortunately the HyperLogLogPlus class we are using for the cardinality doesn't have a toString method, so just ignore that for now):

```
${GET_ALL_EDGES_RESULT}
```

We can fetch all cardinalities for all the vertices using the following operation:

${START_JAVA_CODE}
${GET_ALL_CARDINALITIES_SNIPPET}
${END_CODE}

If we look at the cardinality value of the HyperLogLogPlus property the values are:

```
${ALL_CARDINALITIES_RESULT}
```

You can see we get a cardinality value of 1 for each junction for RoadUse edges as each junction is only connected to 1 other junction.
The cardinality value for the M5 Road is 7 as there are 7 junctions on this road in our data set. 
Remember that the HyperLogLogPlus cardinality value is just an estimate.  

If we want to merge these cardinalities together we can add 'groupBy=[]' to the operation view to override the groupBy defined in the schema.

${START_JAVA_CODE}
${GET_ALL_SUMMARISED_CARDINALITIES_SNIPPET}
${END_CODE}

Now you can see the cardinality values have been merged together at each vertex:

```
${ALL_SUMMARISED_CARDINALITIES_RESULT}
```

This next snippet shows you how you can query for a single cardinality value.

${START_JAVA_CODE}
${GET_ROADUSE_EDGE_CARDINALITY_10_SNIPPET}
${END_CODE}

As you can see the query just simply asks for an entities at vertex '10' and filters for only 'Cardinality' entities that have an edgeGroup property equal to 'RoadUse'. 

The result is:

```
${CARDINALITY_OF_10_RESULT}
```

One of the main uses of Cardinalities is to avoid busy vertices whilst traversing the graph. 
For example if you want to do a 2 hop query (traverse along an edge then another edge) you may want to only go down edges where the source vertex has a low cardinality to avoid returning too many edges.
Here is the java code:

${START_JAVA_CODE}
${GET_2_HOPS_WITH_A_CARDINALITY_FILTER_SNIPPET}
${END_CODE}
