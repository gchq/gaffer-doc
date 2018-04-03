${HEADER}

${CODE_LINK}

In this example we’ll look at how to query for Edges and then add a new transient property to the Edges in the result set.
Again, we will just use the same schema and data as in the previous example.

A transient property is just a property that is not persisted, simply created at query time by a transform function. We’ll create a 'description' transient property that will summarise the contents of the aggregated Edges.

To do this we need a [Function](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html). This is our ${DESCRIPTION_TRANSFORM_LINK} that we will use.

This transform function takes 3 values, the source junction vertex, the destination junction vertex and the count property. It produces a description string.

We configure the function using an [ElementTransformer](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ElementTransformer.html):

${START_JAVA_CODE}
${TRANSFORM_SNIPPET}
${JSON_CODE}
${TRANSFORM_JSON}
${FULL_JSON_CODE}
${TRANSFORM_FULL_JSON}
${END_CODE}

Here you can see we `select` the inputs for the function; the `”SOURCE”` vertex, the `”DESTINATION”` vertex and `”count”` property. We `project` the result into a transient property named `”description”`.
The selection here is similar to the way we select properties and identifiers in a filter,
remember you can select (and also project) any property name or any of these identifiers:

- VERTEX - this is the vertex on an Entity
- SOURCE - this is the source vertex on an Edge
- DESTINATION - this is the destination vertex on an Edge
- DIRECTED - this is the directed field on an Edge
- MATCHED_VERTEX - this is the vertex that was matched in the query, either the SOURCE or the DESTINATION
- ADJACENT_MATCHED_VERTEX - this is the adjacent vertex that was matched in the query, either the SOURCE or the DESTINATION. I.e if your seed matches the source of the edge this would resolve to the DESTINATION value.

We add the new `”description”` property to the result Edge using a `View` and then execute the operation:

${START_JAVA_CODE}
${GET_SNIPPET}
${JSON_CODE}
${GET_JSON}
${FULL_JSON_CODE}
${GET_FULL_JSON}
${PYTHON_CODE}
${GET_PYTHON}
${END_CODE}

This produces the following result:

```
${GET_ELEMENTS_WITH_DESCRIPTION_RESULT}
```

As you can see we’ve now got a new `”description”` property on each Edge.

The view also allows us to limit what properties we want to be returned. We can either provide a list of properties to include
using the 'properties' field, or properties to exclude using the 'excludeProperties' field. We can modify the View from the
previous query so that once we have used the count property to create our description we exclude it from the returned results:

${START_JAVA_CODE}
${GET_WITH_NO_COUNT_SNIPPET}
${JSON_CODE}
${GET_WITH_NO_COUNT_JSON}
${FULL_JSON_CODE}
${GET_WITH_NO_COUNT_FULL_JSON}
${PYTHON_CODE}
${GET_WITH_NO_COUNT_PYTHON}
${END_CODE}

The count property has been removed from the results:

```
${GET_ELEMENTS_WITH_DESCRIPTION_AND_NO_COUNT_RESULT}
```

For more information on Views and transforms, see [Views](views.md).
