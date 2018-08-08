${HEADER}

${CODE_LINK}

Filtering in Gaffer is designed so it can be applied server side and distributed across a cluster for performance.

In this example we’ll query for some Edges and filter the results based on the aggregated value of a property. 
We will use the same schema and data as the previous example.

If we want to query for the RoadUse Edges containing vertex `”10”` the operation would look like this:

${START_JAVA_CODE}
${GET_SIMPLE_SNIPPET}
${JSON_CODE}
${GET_SIMPLE_JSON}
${FULL_JSON_CODE}
${GET_SIMPLE_FULL_JSON}
${PYTHON_CODE}
${GET_SIMPLE_PYTHON}
${END_CODE}

Here are the result Edges with the counts aggregated:

```
${GET_ELEMENTS_RESULT}
```

Now let’s look at how to filter which Edges are returned based on the aggregated value of their count.
For example, only return Edges containing vertex `”10”` where the `”count”` > 2.

We do this using a ${VIEW_JAVADOC2} and ${VIEW_ELEMENT_DEFINITION_JAVADOC2} like this:

${START_JAVA_CODE}
${GET_SNIPPET}
${JSON_CODE}
${GET_JSON}
${FULL_JSON_CODE}
${GET_FULL_JSON}
${PYTHON_CODE}
${GET_PYTHON}
${END_CODE}

Our ViewElementDefinition allows us to perform post Aggregation filtering using an IsMoreThan Predicate.

Querying with our view, we now get only those vertex `”10”` Edges where the `”count”` > 2:

```
${GET_ELEMENTS_WITH_COUNT_MORE_THAN_2_RESULT}
```

In the filter, we selected the `count` property. This extracts the value of the `count` property and passes it to the IsMoreThan Predicate.
We can choose to select any property or one of the following identifiers:
- VERTEX - this is the vertex on an Entity
- SOURCE - this is the source vertex on an Edge
- DESTINATION - this is the destination vertex on an Edge
- DIRECTED - this is the directed field on an Edge
- MATCHED_VERTEX - this is the vertex that was matched in the query, either the SOURCE or the DESTINATION
- ADJACENT_MATCHED_VERTEX - this is the adjacent vertex that was matched in the query, either the SOURCE or the DESTINATION. I.e if your seed matches the source of the edge this would resolve to the DESTINATION value.

We chose to use the IsMoreThan Predicate, however the full list of our core Predicates are documented in [Predicates](../predicates/contents.md).
You can also write your own Predicate implementations and include them on the class path.
When choosing a Predicate you must ensure your input selection (the property and identifier types) match the Predicate input types.
For example the IsMoreThan Predicate accepts a single Comparable value. Whereas the IsXMoreThanY Predicate accepts 2 Comparable values.
The Predicate inputs are also documented within the Predicate examples documentation.

For more information on Views and filtering, see [Views](views.md).

## Additional Filtering
In addition to filtering using a View, there are extra filters that can be applied to specific operations.

### directedType
GetElements, GetAllElements and GetAdjacentIds have a 'directedType' field that you can configure,
telling Gaffer that you only want edges that are DIRECTED, UNDIRECTED or EITHER. 

The default value is EITHER.

### includeIncomingOutGoing
GetElements and GetAdjacentIds have an 'includeIncomingOutGoing' field that you can configure,
telling Gaffer that you only want edges that are OUTGOING, INCOMING, or EITHER, in relation to your seed.
This is only applicable to directed edges.

The default value is EITHER.

For example if you have edges:
- A - B
- B - C
- D -> B
- B -> E
- F - G
- H -> I

and you provide a seed B, then:

- OUTGOING would only get back A - B, B - C and B -> E.
- INCOMING would only get back A - B, B - C and D -> B.
- EITHER would get back all edges that have a B vertex.


### seedMatching
GetElements has a 'seedMatching' field that you can configure,
telling Gaffer that you only want edges that are EQUAL or RELATED to your seeds.

The default value is RELATED.

EQUAL will only return Entities and Edges with identifiers that match the seed exactly.
- if you provide an Entity seed, you will only get back Entities have that the same vertex value.
- if you provide an Edge seed, you will only get back Edges that have the same source, destination and directed values.

RELATED will return the EQUAL results (as above) and additional Entities and Edges:
- if you provide an Entity seed, you will also get back Edges that have have the same vertex value as source or destination.
- if you provide an Edge seed, you will also get back Entities that have have the same source or destination vertices.

As the seedMatching flag has now been deprecated, to run equivalent Operations, there are some examples below.  As the default for 
seedMatching is RELATED, if that is currently used nothing will need to change.
There is one limitation however, if you have a seedMatching = EQUAL and specify both Edges and Entities that will have to now be
done under 2 Operations in a chain as there can only be one View applied globally to all input.

OLD:
${START_JAVA_CODE}
${GET_EDGES_WITH_SEEDMATCHING_SNIPPET}
${JSON_CODE}
${GET_EDGES_WITH_SEEDMATCHING_JSON}
${FULL_JSON_CODE}
${GET_EDGES_WITH_SEEDMATCHING_FULL_JSON}
${PYTHON_CODE}
${GET_EDGES_WITH_SEEDMATCHING_PYTHON}
${END_CODE}

NEW:
${START_JAVA_CODE}
${GET_EDGES_WITHOUT_SEEDMATCHING_SNIPPET}
${JSON_CODE}
${GET_EDGES_WITHOUT_SEEDMATCHING_JSON}
${FULL_JSON_CODE}
${GET_EDGES_WITHOUT_SEEDMATCHING_FULL_JSON}
${PYTHON_CODE}
${GET_EDGES_WITHOUT_SEEDMATCHING_PYTHON}
${END_CODE}

For entities it should now be written:

OLD:
${START_JAVA_CODE}
${GET_ENTITIES_WITH_SEEDMATCHING_SNIPPET}
${JSON_CODE}
${GET_ENTITIES_WITH_SEEDMATCHING_JSON}
${FULL_JSON_CODE}
${GET_ENTITIES_WITH_SEEDMATCHING_FULL_JSON}
${PYTHON_CODE}
${GET_ENTITIES_WITH_SEEDMATCHING_PYTHON}
${END_CODE}

NEW:
${START_JAVA_CODE}
${GET_ENTITIES_WITHOUT_SEEDMATCHING_SNIPPET}
${JSON_CODE}
${GET_ENTITIES_WITHOUT_SEEDMATCHING_JSON}
${FULL_JSON_CODE}
${GET_ENTITIES_WITHOUT_SEEDMATCHING_FULL_JSON}
${PYTHON_CODE}
${GET_ENTITIES_WITHOUT_SEEDMATCHING_PYTHON}
${END_CODE}

