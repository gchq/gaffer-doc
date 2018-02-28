${HEADER}

${CODE_LINK}

In previous sections we have talked about Filtering, Aggregation and Transformations.
This section just goes into a bit more information about what a View actually is and some of the other features of a View.

When running an Operation, for example GetElements, you can use a ${VIEW_JAVADOC} to filter, aggregate, transform and just generally manipulate the results.
As with all parts of Gaffer, the View is quite generic and quite powerful, but can be tricky to get going with.
You can use any of the predefined Functions and Predicates or reference you own (as long as they are on the classpath).

There are quite a few different parts to a View, these are:

- Filtering - for filtering entire elements out based on predicates. Filtering can be applied: pre aggregation, post aggregation and post transformation.

- Aggregation - for controlling if and how similar elements are aggregated together. You can provide a subset of the schema groupBy properties and override the aggregation functions.

- Transformation - for transforming elements. This is applied by providing Functions to transform properties and vertex values. You can override the existing values or you can transform and project the new value into a new transient property.

- Removing properties - for defining which properties you want to be returned. You can use either 'properties' or 'excludeProperties' to define the list of properties to be included or excluded.


If you don't provide a View (or you provide an empty view with no groups) when
executing an operation you will just get all elements back without any modifications.
When creating a View the first thing to do is decide which Edges and Entities you would like to be returned.
Let's assume you want RoadUse, RoadHasJunction, Cardinality. If you provide a view it would look like this:

${START_JAVA_CODE}
${VIEW_WITH_GROUPS_SNIPPET}
${JSON_CODE}
${VIEW_WITH_GROUPS_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_GROUPS_FULL_JSON}
${PYTHON_CODE}
${VIEW_WITH_GROUPS_PYTHON}
${END_CODE}

It is important to use the correct element type when listing the elements.
RoadUse is an Edge group, so we include it in the View as an Edge. Whereas Cardinality
is an Entity so we include it as an Entity.

As seen in the Filtering walkthrough we can then apply filters to the different element groups using a ${VIEW_ELEMENT_DEF_JAVADOC}.
Below you can see we have added a post aggregation filter to the element groups.
This demonstrates how you can add different filters to different groups and how to add multiple filters.

${START_JAVA_CODE}
${VIEW_WITH_FILTERS_SNIPPET}
${JSON_CODE}
${VIEW_WITH_FILTERS_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_FILTERS_FULL_JSON}
${PYTHON_CODE}
${END_CODE}

We could just as easily add pre aggregation filters using "preAggregationFilter" instead.
It is important to think about the stage at which you are applying your filtering.
Pre aggregation filters are useful when you want to apply a filter to a property
before it is aggregated, like time window filtering. However, post aggregation
filtering is required for properties like a count, when you want to filter based
on the fully summarised value after query time aggregation has happened.


If you are only interested in specific properties then it is more efficient to tell Gaffer to only return those properties.
This can be easily achieved using the 'properties' or 'excludeProperties' field.
For example:

${START_JAVA_CODE}
${VIEW_WITH_REMOVED_PROPERTIES_SNIPPET}
${JSON_CODE}
${VIEW_WITH_REMOVED_PROPERTIES_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_REMOVED_PROPERTIES_FULL_JSON}
${PYTHON_CODE}
${END_CODE}

## Global view definitions

If you want to set the same filters on multiple groups you can use a global view definition.
When specifying global view definitions you can choose from:

- globalElements - these are applied to all edges and entities
- globalEdges - these are applied to all edges
- globalEntities - these are applied to all entities

If we want to return all elements that have a count more than 2 we can do this:

${START_JAVA_CODE}
${VIEW_WITH_GLOBAL_FILTER_SNIPPET}
${JSON_CODE}
${VIEW_WITH_GLOBAL_FILTER_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_GLOBAL_FILTER_FULL_JSON}
${PYTHON_CODE}
${VIEW_WITH_GLOBAL_FILTER_PYTHON}
${END_CODE}

In addition to global definitions you can add specific view definitions to different element groups as we have done previously.
The global definitions will just get merged with each of the element definitions.
Filters are merged together using an AND operator. For example:

${START_JAVA_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_SNIPPET}
${JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_FULL_JSON}
${PYTHON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_PYTHON}
${END_CODE}

when this is expanded out by Gaffer automatically it would become:

${START_JAVA_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_EXPANDED_SNIPPET}
${JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_EXPANDED_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_EXPANDED_FULL_JSON}
${PYTHON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_FILTERS_EXPANDED_PYTHON}
${END_CODE}

Global aggregations and transformations work in a similar way to filtering.

Properties can also be included/excluded globally, using a similar method to the above for filtering.

If we only want to return the `"count"` property for all elements this can be done like:

${START_JAVA_CODE}
${VIEW_WITH_GLOBAL_REMOVED_PROPERTIES_SNIPPET}
${JSON_CODE}
${VIEW_WITH_GLOBAL_REMOVED_PROPERTIES_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_GLOBAL_REMOVED_PROPERTIES_FULL_JSON}
${PYTHON_CODE}
${VIEW_WITH_GLOBAL_REMOVED_PROPERTIES_PYTHON}
${END_CODE}

Again, you can override the global included properties field for a specific element group:

${START_JAVA_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_SNIPPET}
${JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_FULL_JSON}
${PYTHON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_PYTHON}
${END_CODE}

when this is expanded out by Gaffer automatically it would become:

${START_JAVA_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_EXPANDED_SNIPPET}
${JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_EXPANDED_JSON}
${FULL_JSON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_EXPANDED_FULL_JSON}
${PYTHON_CODE}
${VIEW_WITH_GLOBAL_AND_SPECIFIC_REMOVED_PROPERTIES_EXPANDED_PYTHON}
${END_CODE}

Global exclude properties work in the same way as global properties.
