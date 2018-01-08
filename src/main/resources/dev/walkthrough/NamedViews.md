${HEADER}

${CODE_LINK}

This example explains how to configure your Gaffer Graph to allow named views to be used. 
Named views enable encapsulation of a View into a new single NamedView.
The NamedView can be used in conjunction with other Views.
There are various possible uses for NamedViews, including:
 * Making it simpler to reuse frequently used Views.
 * Share commonly used same Views with other users.
 
In addition to the NamedView there are a set of operations which manage named views (AddNamedView, GetAllNamedViews, DeleteNamedView).

## Configuration
You will need to configure what cache to use for storing NamedViews. For more information on the cache service, see [Cache](cache.md).

## Using Named Views
We will now go through some examples using NamedViews.

We will use the same basic schema and data from the first developer walkthrough.

Start by creating your user instance and graph:

${START_JAVA_CODE}
${USER_SNIPPET}
${END_CODE}

${START_JAVA_CODE}
${GRAPH_SNIPPET}
${END_CODE}


Then add a NamedView to the cache with the AddNamedView operation:

${START_JAVA_CODE}
${ADD_NAMED_VIEW_SNIPPET}
${END_CODE}

For the next step we need to create a GetElements operation and use
the NamedView previously added to the cache, using the name specified.
After this we can execute it:

${START_JAVA_CODE}
${CREATE_GET_ELEMENTS_OPERATION_SNIPPET}
${END_CODE}

${START_JAVA_CODE}
${EXECUTE_GET_ELEMENTS_OPERATION_SNIPPET}
${END_CODE}

The results are:

```
${OPERATION_USING_NAMED_VIEW_RESULTS}
```

NamedViews can also take parameters, allowing values to be configured by the user when the NamedView is used.
When adding a NamedView with parameters the View must be specified as a JSON string, with
parameter names enclosed with '${' and '}'. For each parameter, a ViewParameterDetail object must be created which gives a description, a class type
and an optional default for the Parameter, and also indicates whether the parameter must be provided (ie. there is no default).

The following code adds a NamedView with a 'countIsMoreThan' parameter that allows the minimum edge count for the View to be set:

${START_JAVA_CODE}
${ADD_NAMED_VIEW_WITH_PARAMETERS_SNIPPET}
${END_CODE}

A view can then be created, with a value provided for the 'countIsMoreThan' parameter:

${START_JAVA_CODE}
${CREATE_NAMED_VIEW_WITH_PARAMETERS_SNIPPET}
${END_CODE}

and executed:

${START_JAVA_CODE}
${EXECUTE_GET_ELEMENTS_OPERATION_WITH_PARAMETERS_SNIPPET}
${END_CODE}

giving these results:

```
${OPERATION_USING_NAMED_VIEW_WITH_PARAMETERS_RESULTS}
```