${HEADER}

${CODE_LINK}

The `FederatedStore` is simply a Gaffer store which forwards operations to a collection of sub-graphs and returns a single response as though it was a single graph.

This walkthrough explains how to:
 * [Create a `FederatedStore`](#create-a-federatedstore)
 * [Add Graphs](#add-graphs)
 * [Remove Graphs](#remove-graphs)
 * [Get GraphIds](#get-graphids)
 * [Perform Operations](#perform-operations)
 * [Select Graphs for Operations](#select-graphs-for-operations)
 * [Skip Failed Execution](#do-not-skip-failed-execution)
 * [Limit Access with Authentication](#limit-access-with-authentication)
   * [Public Access](#public-access)
   * [Private Access](#private-access)
   * [Secure Access](#secure-access)
     * [Graph Auths](#graph-auths)
     * [Access Controlled Resource](#access-controlled-resource)
 * [Disallow Public Access](#disallow-public-access)
 * [Limit Custom Properties](#limit-custom-properties)

## Create a FederatedStore

To create a `FederatedStore` you need to initialise the store with a graphId and a properties file.

Optionally you can add a `GraphLibrary` this will store all the `Schema` and `StoreProperties` associated with your `FederatedStore` and sub-graphs, otherwise a `NoGraphLibrary` is used which does nothing.
Optionally you can add a `CacheService` within the `StoreProperties`.
In this example we are using a non-persistent library `HashMapGraphLibrary` and cache `HashMapCacheService`.  For more information on the cache service please see [Cache](cache.md).

${START_JAVA_CODE}
${CREATING_A_FEDERATEDSTORE_SNIPPET}
${END_CODE}

### FederatedStore Properties

${START_JAVA_CODE}
${FEDERATEDSTORE_PROPERTIES_SNIPPET}
${JSON_CODE}
${FED_PROP_JSON}
${FULL_JSON_CODE}
${FED_PROP_FULL_JSON}
${END_CODE}

## Add Graphs

A graph is added into the `FederatedStore` using the `AddGraph` operation. To load a graph you need to provide:
 * GraphId
 * Graph Schema and/or parentSchemaId
 * Graph Properties and/or parentPropertiesId

**Note**
* You can't add a graph using a graphId already in use, you will need to explicitly remove the old GraphId first.
* You can limit access to the sub-graphs when adding to FederatedStore, see [Limiting Access](#limit-access-with-authentication).
* Schema & Properties are not required if the GraphId is known by the GraphLibrary.
* Custom properties defined by the user can be disallowed, see [limit custom properties](#limit-custom-properties).

${START_JAVA_CODE}
${ADD_ANOTHER_GRAPH_SNIPPET}
${JSON_CODE}
${ADD_GRAPH_JSON}
${FULL_JSON_CODE}
${ADD_GRAPH_FULL_JSON}
${END_CODE}

## Remove Graphs

To remove a graph from the `FederatedStore` it is even easier, you only need to know the graphId. This does not delete the graph only removes it from the scope.
However the user can only delete graphs they have access to view.

${START_JAVA_CODE}
${REMOVE_GRAPH_SNIPPET}
${JSON_CODE}
${REMOVE_GRAPH_JSON}
${FULL_JSON_CODE}
${REMOVE_GRAPH_FULL_JSON}
${END_CODE}

## Get GraphIds

To get a list of all the sub-graphs within the `FederatedStore` you can perform the following `GetAllGraphId` operation.

${START_JAVA_CODE}
${GET_ALL_GRAPH_IDS_SNIPPET}
${JSON_CODE}
${GET_ALL_GRAPH_IDS_JSON}
${FULL_JSON_CODE}
${GET_ALL_GRAPH_IDS_FULL_JSON}
${END_CODE}


and the result is:

```
${GRAPH_IDS}
```

## Perform Operations

Running operations against the `FederatedStore` is exactly same as running operations against any other store.
Behind the scenes the `FederatedStore` sends out operation chains to the sub-graphs to be executed and returns back a single response.

**Note**
`AddElements` operations is a special case, and only adds elements to sub-graphs when the edge or entity groupId is known by that sub-graph.

**Warning** When adding elements, if 2 sub-graphs contain the same group in the schema then the elements will be added to both of the sub-graphs.
A following `GetElements` operation would then return the same element from both sub-graph, resulting in duplicates.
It is advised to keep groups to within one sub-graph or limit queries to one of the graphs with the option "gaffer.federatedstore.operation.graphIds".

${START_JAVA_CODE}
${ADD_ELEMENTS_SNIPPET}
${JSON_CODE}
${ADD_ELEMENTS_JSON}
${FULL_JSON_CODE}
${ADD_ELEMENTS_FULL_JSON}
${PYTHON_CODE}
${ADD_ELEMENTS_PYTHON}
${END_CODE}

${START_JAVA_CODE}
${GET_ELEMENTS_SNIPPET}
${JSON_CODE}
${GET_ELEMENTS_JSON}
${FULL_JSON_CODE}
${GET_ELEMENTS_FULL_JSON}
${PYTHON_CODE}
${GET_ELEMENTS_PYTHON}
${END_CODE}

and the results are:

```
${ELEMENTS}
```

${START_JAVA_CODE}
${GET_ELEMENTS_FROM_ACCUMULO_GRAPH_SNIPPET}
${END_CODE}

and the results are:

```
${ELEMENTS_FROM_ACCUMULO_GRAPH}
```

## Select Graphs for Operations
Operations can be performed against specific sub-graphs by settings the option "gaffer.federatedstore.operation.graphIds".

${START_JAVA_CODE}
${SELECT_GRAPHS_FOR_OPERATIONS_SNIPPET}
${JSON_CODE}
${SELECT_GRAPHS_FOR_OPERATIONS_JSON}
${FULL_JSON_CODE}
${SELECT_GRAPHS_FOR_OPERATIONS_FULL_JSON}
${END_CODE}

## Skip Failed Execution
If the execution against one of the graphs fails an `OperationException` is thrown,
unless the operation has the option `gaffer.federatedstore.operation.skipFailedFederatedStoreExecute`
set to `true`. In that situation that graph is skipped and the
`FederatedStore` continues with the remaining graphs.

${START_JAVA_CODE}
${SKIP_FAILED_EXECUTION_SNIPPET}
${JSON_CODE}
${SKIP_FAILED_EXECUTION_JSON}
${FULL_JSON_CODE}
${SKIP_FAILED_EXECUTION_FULL_JSON}
${END_CODE}


## Limit Access with Authentication
It is possible to have a `FederatedStore` with many sub-graphs, however you
 may wish to limit user access to some sub-graphs. This is possible by either using authorisations
  or configurable read and write access predicates at the time of adding the graph to the FederatedStore, this limits the graphs users
   can perform operations on.

### Public Access
Within the `AddGraph` operation, explicitly setting the parameter "isPublic" as
true grants access to all users, regardless of authorisations that may be used.
By default the "isPublic" parameter is false.

${START_JAVA_CODE}
${ADD_PUBLIC_GRAPH_SNIPPET}
${JSON_CODE}
${ADD_PUBLIC_GRAPH_JSON}
${FULL_JSON_CODE}
${ADD_PUBLIC_GRAPH_FULL_JSON}
${END_CODE}

### Private Access
Within the `AddGraph` operation, by default the "isPublic" parameter is false.
If authorisations is not specified it is private to the user that added it to FederatedStore.

${START_JAVA_CODE}
${ADD_PRIVATE_GRAPH_SNIPPET}
${JSON_CODE}
${ADD_PRIVATE_GRAPH_JSON}
${FULL_JSON_CODE}
${ADD_PRIVATE_GRAPH_FULL_JSON}
${END_CODE}

### Secure Access
Within the `AddGraph` operation, do not assign the "isPublic" parameter or assign it to false, this ensures the settings described in this section are not ignored.

#### Graph Auths
By assigning the parameter "graphAuths", all users that have one of the listed authorisations will have access to that graph. Note that "graphAuths" is mutually exclusive with the "readAccessPredicate" setting described in the [Access Controlled Resource](#access-controlled-resource) section.

${START_JAVA_CODE}
${ADD_SECURE_GRAPH_SNIPPET}
${JSON_CODE}
${ADD_SECURE_GRAPH_JSON}
${FULL_JSON_CODE}
${ADD_SECURE_GRAPH_FULL_JSON}
${END_CODE}

#### Access Controlled Resource
Graphs in the Federated Store implement the AccessControlledResource interface allowing configuration of a custom Predicate which is tested against the User to determine whether they can access the graph.
This example ensures readers of the graph have both the "read-access-auth-1" and "read-access-auth-2" auths and users attempting to remove the graph have both the "write-access-auth-1" and "write-access-auth-2" auths.
Note that the "readAccessPredicate" field is mutually exclusive with the "graphAuths" setting described in the [Graph Auths](#graph-auths) section.

${START_JAVA_CODE}
${ADD_ACCESS_CONTROLLED_RESOURCE_SECURE_GRAPH_SNIPPET}
${JSON_CODE}
${ADD_ACCESS_CONTROLLED_RESOURCE_SECURE_GRAPH_JSON}
${FULL_JSON_CODE}
${ADD_ACCESS_CONTROLLED_RESOURCE_SECURE_GRAPH_FULL_JSON}
${END_CODE}

## Disallow Public Access
By default the `FederatedStore` will allow graphs to be added with public access.
Public access can be prevented by setting the property `gaffer.federatedstore.isPublicAllowed` to false.

${START_JAVA_CODE}
${DISALLOW_PUBLIC_ACCESS_SNIPPET}
${JSON_CODE}
${DISALLOW_PUBLIC_ACCESS_JSON}
${FULL_JSON_CODE}
${DISALLOW_PUBLIC_ACCESS_FULL_JSON}
${END_CODE}

## Limit Custom Properties
You may not want all users to be able to specify their own properties for graphs they are creating and adding, and to only use properties defined within the library.
To limit the user that use custom properties set the authorisation required by users with "gaffer.federatedstore.customPropertiesAuths" properties key.
By default this key is null, so all users can use custom properties.
Users that do not match this authorisation can only specify using `StoreProperties` from the `GraphLibrary`.

${START_JAVA_CODE}
${LIMIT_CUSTOM_PROPERTIES_SNIPPET}
${JSON_CODE}
${LIMIT_CUSTOM_PROPERTIES_JSON}
${FULL_JSON_CODE}
${LIMIT_CUSTOM_PROPERTIES_FULL_JSON}
${END_CODE}

