${HEADER}

${CODE_LINK}

This example explains how to configure your Gaffer Graph to allow NamedViews to be used. 
Named views enable encapsulation of a View, or multiple Views, into a new single NamedView.
A NamedView can be used in conjunction with other Views.
There are various possible uses for NamedViews, including:
 * Making it simpler to reuse frequently used Views.
 * Share commonly used Views with other users.
 
In addition to the NamedView there are a set of operations which manage named views (AddNamedView, GetAllNamedViews, DeleteNamedView).

## Configuration
You will need to configure what cache to use for storing NamedViews. For more information on the cache service, see [Cache](cache.md).

Please note; If you want to change the region for NamedViews you can do so by specifying it within the cache.ccf file using region jcs.region.namedViewsRegion.

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
${JSON_CODE}
${ADD_NAMED_VIEW_JSON}
${FULL_JSON_CODE}
${ADD_NAMED_VIEW_FULL_JSON}
${PYTHON_CODE}
${ADD_NAMED_VIEW_PYTHON}
${END_CODE}

For the next step we need to create a GetElements operation and use
the NamedView previously added to the cache, using the name specified.

${START_JAVA_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_SNIPPET}
${JSON_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_JSON}
${FULL_JSON_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_FULL_JSON}
${PYTHON_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_PYTHON}
${END_CODE}


The results are:

```
${GET_ELEMENTS_WITH_NAMED_VIEW_RESULTS}
```

NamedViews can also take parameters, allowing values to be configured by the user when the NamedView is used.
When adding a NamedView with parameters the View must be specified as a JSON string, with
parameter names enclosed with '${' and '}'. For each parameter, a ViewParameterDetail object must be created which gives a description, a class type
and an optional default for the Parameter, and also indicates whether the parameter must be provided (ie. there is no default).

The following code adds a NamedView with a 'countIsMoreThan' parameter that allows the minimum edge count for the View to be set, along
with a 'selectionParam' parameter that allows the user to set the selection for the View:

${START_JAVA_CODE}
${ADD_NAMED_VIEW_WITH_PARAMETERS_SNIPPET}
${JSON_CODE}
${ADD_NAMED_VIEW_WITH_PARAMETERS_JSON}
${FULL_JSON_CODE}
${ADD_NAMED_VIEW_WITH_PARAMETERS_FULL_JSON}
${PYTHON_CODE}
${ADD_NAMED_VIEW_WITH_PARAMETERS_PYTHON}
${END_CODE}


A view can then be created, with a value provided for the 'countIsMoreThan' and 'selectionParam' parameter:

${START_JAVA_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_WITH_PARAMETERS_SNIPPET}
${JSON_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_WITH_PARAMETERS_JSON}
${FULL_JSON_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_WITH_PARAMETERS_FULL_JSON}
${PYTHON_CODE}
${GET_ELEMENTS_WITH_NAMED_VIEW_WITH_PARAMETERS_PYTHON}
${END_CODE}

giving these results:

```
${GET_ELEMENTS_WITH_NAMED_VIEW_WITH_PARAMETERS_RESULTS}
```

## Security
By default, read access to Named Views is unrestricted and write access is limited to administrators and the Named View creator. More fine-grained controls can be configured using the following options.

### Write Access Roles
Write access to Named Views can be locked down to users who have at least one of the auths listed in the "writeAccessRoles" setting.
This example ensures that writers have the "write-user" auth.

${START_JAVA_CODE}
${ADD_NAMED_VIEW_WRITE_ACCESS_ROLES_SNIPPET}
${END_CODE}

### Access Controlled Resource
Named Views implement the AccessControlledResource interface allowing configuration of a custom Predicate which is tested against the User to determine whether they can access the resource.
This example ensures readers of the Named View have both the "read-access-auth-1" and "read-access-auth-2" auths and users attempting to remove the Named View have both the "write-access-auth-1" and "write-access-auth-2" auths.
Note that the "writeAccessPredicate" field is mutually exclusive with the "writeAccessRoles" setting described in the [Write Access Roles](#write-access-roles) section.

${START_JAVA_CODE}
${ADD_NAMED_VIEW_ACCESS_CONTROLLED_RESOURCE_SNIPPET}
${END_CODE}
