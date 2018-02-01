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

We do this using a ${VIEW_JAVADOC} and ${VIEW_ELEMENT_DEF_JAVADOC} like this:

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

We chose to use the IsMoreThan Predicate, however the full list of our core Predicates are documented in [Predicates](../predicates/content.md).
You can also write your own Predicate implementations and include them on the class path.
When choosing a Predicate you must ensure your input selection (the property and identifier types) match the Predicate input types.
For example the IsMoreThan Predicate accepts a single Comparable value. Whereas the IsXMoreThanY Predicate accepts 2 Comparable values.
The Predicate inputs are also documented within the Predicate examples documentation.

For more information on Views and filtering, see [Views](views.md).