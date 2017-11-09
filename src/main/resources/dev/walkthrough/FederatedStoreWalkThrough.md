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
 * [Do Not Skip Failed Execution](#do-not-skip-failed-execution)
 * [Limit Access with Authentication](#limit-access-with-authentication)
   * [Public Access](#public-access)
   * [Private Access](#private-access)
   * [Secure Access](#secure-access)
 * [Disallow Public Access](#disallow-public-access)
 * [Limit Custom Properties](#limit-custom-properties)

#### Create a FederatedStore

To create a `FederatedStore` you need to initialise the store with a graphId and a properties file.

Optionally you can add a `GraphLibrary` this will store all the `Schema` and `StoreProperties` associated with your `FederatedStore` and sub-graphs, otherwise a `NoGraphLibrary` is used which does nothing.
Optionally you can add a `CacheService` within the `StoreProperties`.
In this example we are using a non-persistent library `HashMapGraphLibrary` and cache `HashMapCacheService`.

${CREATING_A_FEDERATEDSTORE_SNIPPET}

##### FederatedStore Properties

${FEDERATEDSTORE_PROPERTIES_SNIPPET}

```json
${fedPropJson}
```

#### Add Graphs

A graph is added into the `FederatedStore` using the `AddGraph` operation. To load a graph you need to provide:
 * GraphId
 * Graph Schema and/or parentSchemaId
 * Graph Properties and/or parentPropertiesId

**Note**
* You can't add a graph using a graphId already in use, you will need to explicitly remove the old GraphId first.
* You can limit access to the sub-graphs when adding to FederatedStore, see [Limiting Access](#limit-access-with-authentication).
* Schema & Properties are not required if the GraphId is known by the GraphLibrary.
* Custom properties defined by the user can be disallowed, see [limit custom properties](#limit-custom-properties).

${ADD_ANOTHER_GRAPH_SNIPPET}

or through the rest service with json.

```json
${addGraphJson}
```

#### Remove Graphs

To remove a graph from the `FederatedStore` it is even easier, you only need to know the graphId. This does not delete the graph only removes it from the scope.
However the user can only delete graphs they have access to view.

${REMOVE_GRAPH_SNIPPET}

or through the rest service with json.

```json
${removeGraphJson}
```

#### Get GraphIds

To get a list of all the sub-graphs within the `FederatedStore` you can perform the following `GetAllGraphId` operation.

${GET_ALL_GRAPH_IDS_SNIPPET}

or through the rest service with json.

```json
${getAllGraphIdsJson}
```


and the result is:

```
${graphIds}
```

#### Perform Operations

Running operations against the `FederatedStore` is exactly same as running operations against any other store.
Behind the scenes the `FederatedStore` sends out operation chains to the sub-graphs to be executed and returns back a single response.

**Note**
`AddElements` operations is a special case, and only adds elements to sub-graphs when the edge or entity groupId is known by that sub-graph.

**Warning** When adding elements, if 2 sub-graphs contain the same group in the schema then the elements will be added to both of the sub-graphs.
A following `GetElements` operation would then return the same element from both sub-graph, resulting in duplicates.
It is advised to keep groups to within one sub-graph or limit queries to one of the graphs with the option "gaffer.federatedstore.operation.graphIds".

${ADD_ELEMENTS_SNIPPET}

${GET_ELEMENTS_SNIPPET}

or through the rest service with json.

```json
${addElementsJson}
```

```json
${getElementsJson}
```

and the results are:

```
${elements}
```

${GET_ELEMENTS_FROM_ACCUMULO_GRAPH_SNIPPET}

and the results are:

```
${elementsFromAccumuloGraph}
```

#### Select Graphs for Operations
Operations can be performed against specific sub-graphs by settings the option "gaffer.federatedstore.operation.graphIds".

${SELECT_GRAPHS_FOR_OPERATIONS_SNIPPET}

or through the rest service with json.
```json
${selectGraphsForOperationsJson}
```

#### Do Not Skip Failed Execution
If the execution against a graph fails, that graph is skipped and the
`FederatedStore` continues with the remaining graphs. Unless the operation
has the option "gaffer.federatedstore.operation.skipFailedFederatedStoreExecute"
 set to `false`, in that situation a `OperationException` is thrown.

${DO_NOT_SKIP_FAILED_EXECUTION_SNIPPET}

or through the rest service with json.

```json
${doNotSkipFailedExecutionJson}
```


#### Limit Access with Authentication
It is possible to have a `FederatedStore` with many sub-graphs, however you
 may wish to limit the users access. This is possible by using authorisations
  at the time of adding a graph to the FederatedStore, this limits the graphs users
   can perform operations on.

##### Public Access
Within the `AddGraph` operation, explicitly setting the parameter "isPublic" as
true grants access to all users, regardless of authorisations that may be used.
By default the "isPublic" parameter is false.

${ADD_PUBLIC_GRAPH_SNIPPET}

or through the rest service with json.

```json
${addPublicGraphJson}
```

##### Private Access
Within the `AddGraph` operation, by default the "isPublic" parameter is false.
If authorisations is not specified it is private to the user that added it to FederatedStore.

${ADD_PRIVATE_GRAPH_SNIPPET}

or through the rest service with json.

```
${addPrivateGraphJson}
```

##### Secure Access
Within the `AddGraph` operation, do not assign the "isPublic" parameter or assign it to false.
By assigning the parameter "graphAuths", all users that has one of the listed authorisations will have access to that graph.

${ADD_SECURE_GRAPH_SNIPPET}

or through the rest service with json

```json
${addSecureGraphJson}
```

#### Disallow Public Access
By default the `FederatedStore` will allow graphs to be added with public access.
However public access can be disallow by setting the property `gaffer.federatedstore.isPublicAllowed` to false.

${DISALLOW_PUBLIC_ACCESS_SNIPPET}

or through the rest service with json

```json
${disallowPublicAccessJson}
```

#### Limit Custom Properties
You may not want all users to be able to specify their own properties for graphs they are creating and adding, and to only use properties defined within the library.
To limit the user that use custom properties set the authorisation required by users with "gaffer.federatedstore.customPropertiesAuths" properties key.
By default this key is null, so all users can use custom properties.
Users that do not match this authorisation can only specify using `StoreProperties` from the `GraphLibrary`.

${LIMIT_CUSTOM_PROPERTIES_SNIPPET}
or through the rest service with json

```json
${limitCustomPropertiesJson}
```

