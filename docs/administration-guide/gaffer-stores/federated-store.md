# Federated Store

The Federated Store is a Gaffer store which forwards operations to a collection of sub-graphs and returns a single response as though a single graph were queried.

## Introduction

The Federated Store facilitates querying of multiple Gaffer graphs through a single endpoint/instance, and the federation of queries across many graphs at once - with results automatically merged.
Unlike other Stores, the Federated Store doesn't use a schema of its own. This is because it represents a collection of other graphs, these can be referred to as sub-graphs.
The Federated Store 'graph' represents a way to query these sub-graphs and is not an actual graph.

Federated Store sub-graphs can be any kind of Gaffer graph, including further federated graphs. It's important to remember that it doesn't store data on its own.
There are [special Gaffer Operations](#performing-operations) for adding and removing graphs to/from the collection. It's also possible to include access restrictions on sub-graphs.
When adding a new sub-graph, the schema and store properties for this new graph are supplied to the Federated Store directly.

The Federated Store can be used as a single entry point to a set of multiple different Gaffer graphs, using different store types and across different instances/hosts (via Proxy Stores).

## Create a Federated Store

To create a Federated Store, you need to initialise the store/graph with a `graphId` and a properties file.
See [the Graph page for detailed info](../../development-guide/project-structure/components/graph.md#creating-a-graph) on this.

Optionally you can add a [Graph Library](../../development-guide/project-structure/components/graph.md#graph-configuration) to store Schemas and Store Properties associated with the Federated Store sub-graphs.
You can also add a [Cache Service](store-guide.md#caches) within the Store Properties, for example this will be required if you want to use Named Operations.

### Using the REST API

When using the REST API, configuration is done with the files `graphConfig.json` and `store.properties`. Minimal examples are shown below.

```json title="graphConfig.json"
{
  "graphId": "federatedGraph",
  "library": {
    "class": "uk.gov.gchq.gaffer.store.library.HashMapGraphLibrary"
  }
}
```

```properties title="store.properties"
gaffer.store.class=uk.gov.gchq.gaffer.federatedstore.FederatedStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties
```

### Using the Java API

A `Graph` Object containing this Federated Store graph can be created as follows:

```java
final Graph federatedGraph = new Graph.Builder()
        .config(new GraphConfig.Builder()
                .graphId("federatedGraph")
                .library(graphLibrary) // (1)!
                .build())
        .storeProperties(new FederatedStoreProperties())
        .build();
```

1.  [See GraphLibrary Javadoc for more info](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/library/GraphLibrary.html).


### Federated Store Properties

In addition to the [standard Store Properties](store-guide.md#store-properties), there are some additional properties specific to this store:

- `gaffer.federatedstore.isPublicAllowed`: Controls if adding graphs with [public access](#publicprivate-access) is allowed. True by default.
- `gaffer.federatedstore.customPropertiesAuths`: String containing auths for [allowing users to use store properties other than those contained in a graph library](#limit-custom-properties). Unset by default, allowing all users to do this.
- `gaffer.federatedstore.storeConfiguredMergeFunctions`: Path to file containing [merge function definitions to use to override the default merge functions](#setting-default-merge-functions).
- `gaffer.federatedstore.storeConfiguredGraphIds`: Path to file containing [Graph IDs to be queried when a user doesn't target specific graphs](#setting-default-query-graph-ids). By default, all graphs a user can access are queried.
- `gaffer.cache.service.federatedstore.class`: Class named of a [Gaffer cache](store-guide.md#cache-service) implementation to use for the Federated Store.

??? example "Setting these properties with the Java API"

    ```java
    final FederatedStoreProperties exampleFederatedStoreProperties = new FederatedStoreProperties();
    exampleFederatedStoreProperties.setGraphsCanHavePublicAccess(false);
    exampleFederatedStoreProperties.setCustomPropertyAuths("Auth1, Auth2, Auth3");
    exampleFederatedStoreProperties.setStoreConfiguredMergeFunctions("path/to/mergefunctionDefinitions.json");
    exampleFederatedStoreProperties.setStoreConfiguredGraphIds("path/to/defaultGraphIds.json");
    exampleFederatedStoreProperties.setFederatedStoreCacheServiceClass("uk.gov.gchq.gaffer.cache.impl.HashMapCacheService");
    ```

#### Setting default Merge Functions

[Merge Functions](../../reference/operations-guide/federated-operation.md#the-merge-function) control how results from different sub-graphs in a Federated Store are merged together.
If you want to override the default behaviour and use different merge functions, then you can do this using `storeConfiguredMergeFunctions`.
This property takes a path to a JSON file containing definitions of how to override the default merge functions.

The example file given below configures the `GetElements` and `GetAllElements` Operations to use [`ConcatenateMergeFunction`](../../reference/operations-guide/federated-operation.md#concatenatemergefunction)
instead of the default [`ApplyViewToElementsFunction`](../../reference/operations-guide/federated-operation.md#applyviewtoelementsfunction).

```json title="mergefunctionDefinitions.json"
{
  "storeConfiguredMergeFunctions": {
    "uk.gov.gchq.gaffer.operation.impl.get.GetElements": {
      "class": "uk.gov.gchq.gaffer.federatedstore.util.ConcatenateMergeFunction"
    },
    "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements": {
      "class": "uk.gov.gchq.gaffer.federatedstore.util.ConcatenateMergeFunction"
    }
  }
}
```

#### Setting default query Graph IDs

By default, when a user submits a query to a Federated Store without using a [Federated Operation](#performing-operations), their query is federated across all graphs they are permitted to read (or write, depending on the query).
If the Federated Store contains many graphs, this might not be desirable as a default - especially if the graphs do not have compatible schemas.
It might also be useful for an administrator to configure a defined list of graph IDs to use with these kinds of query, e.g. where a certain primary graph should always be treated as the default for users.

The default graphs to query when a Federated Operation is not used can be set using `storeConfiguredGraphIds`.
This property takes a path to a JSON file containing a list of Graph IDs to use as the defaults.

The example file given below configures the Federated Store to federate queries across the `GraphA` and `GraphB` graphs by default when a Federated Operation is not used.

```json title="defaultGraphIds.json"
[
  "GraphA", "GraphB"
]
```

## Cache Considerations

### Federated Store Cache

The Federated Store requires a [Gaffer cache](store-guide.md#cache-service) service in order to function.
The service/implementation to use can be specified specifically for the Federated Store (using the property given above), or the default service will be used.
If a default service is not defined, the `HashMapCacheService` service will be set as the default and a warning message logged.

### Sub graph Caches

When adding graphs to a Federated Store you can supply store properties (directly or via a graph library) containing [cache class configuration](store-guide.md#cache-service).
However, you should be careful to ensure there are no conflicts between the cache properties used for the Federated Store itself and those associated with graphs to add.

For example, if the Federated Store is configured with the default cache service property, then this property cannot be given on graphs to add. If it is given, it will be ignored with a message logged.
This limitation exists because only one instance of each cache service (the default, plus optional services specific to the Job Tracker, Named Views and Named Operations) can exist within the JVM.
If you need sub graphs with different caches, it is recommended to create these as separate Gaffer instances and connect to them using a [proxy store](proxy-store.md) sub graph.

## Adding Graphs

To add a graph to a Federated Store you use the `AddGraph` operation. To add a graph you need to provide:

- Graph ID
- Graph Schema
- Graph Properties

!!! warning "Duplicate Graph IDs"
    You can't add a graph with an ID which is already in use, you will need to explicitly [remove the old graph first](#removing-graphs).

A Schema & Properties are not required if `parentSchemaId`/`parentPropertiesId` are instead used to retrieve this information from a Graph Library.

You can limit user access when adding sub-graphs, see [Restricting Graph Access](#restricting-graph-access).
User specified store properties are allowed by default, but this can be disallowed if desired, see [limit custom properties](#limit-custom-properties).

```json title="AddGraph Operation"
{
  "class" : "AddGraph",
  "graphId" : "AnotherGraph",
  "schema" : {
    ... Schema in JSON format
  },
  "storeProperties" : {
    ... Store Properties in JSON format
  }
}
```

??? example "AddGraph with the Java API"

    ```java
    AddGraph addAnotherGraph = new AddGraph.Builder()
            .graphId("AnotherGraph")
            .schema(mySchema)
            .storeProperties(myStoreProperties)
            .build();
    federatedGraph.execute(addAnotherGraph, user);
    ```

## Removing Graphs

To remove a graph from a Federated Store, you only need to know the graph's ID. Removing a graph does not delete it, it only removes it from the scope of the store.
A removed graph can be re-added again later, assuming the same graph ID is used and the configuration for the underlying store and any associated storage is unchanged.
Graphs can only be removed by users with write access or by an administrator (user with admin auths).

```json title="RemoveGraph Operation"
{
  "class" : "RemoveGraph",
  "graphId" : "AnotherGraph"
}
```

??? example "RemoveGraph with the Java API"

    ```java
    RemoveGraph removeGraph = new RemoveGraph.Builder()
            .graphId("AnotherGraph")
            .build();
    federatedGraph.execute(removeGraph, user);
    ```

## Removing Graphs and Deleting Data

Removing a graph and deleting the underlying data is done with the `RemoveGraphAndDeleteAllData` operation.
Other than also deleting data, this operation is identical to `RemoveGraph`.
For this to work, the underlying Gaffer Store must support the `DeleteAllData` Operation.
If this is not supported or not enabled then an error will be raised.

```json title="RemoveGraphAndDeleteAllData Operation"
{
  "class" : "RemoveGraphAndDeleteAllData",
  "graphId" : "AnotherGraph"
}
```

??? example "RemoveGraphAndDeleteAllData with the Java API"

    ```java
    RemoveGraph removeGraphAndDeleteAllData = new RemoveGraphAndDeleteAllData.Builder()
            .graphId("AnotherGraph")
            .build();
    federatedGraph.execute(removeGraphAndDeleteAllData, user);
    ```

## Get GraphIds

To get a list of all the sub-graphs within a Federated Store you can perform the following `GetAllGraphIds` operation.

```json title="GetAllGraphIds Operation"
{
  "class" : "GetAllGraphIds"
}
```

??? example "GetAllGraphIds with the Java API"

    ```java
    final GetAllGraphIds getAllGraphIDs = new GetAllGraphIds();
    Iterable<? extends String> graphIds = federatedGraph.execute(getAllGraphIDs, user);
    ```

## Performing Operations

Running operations against a Federated Store is much the same as running operations against any other store.
Behind the scenes operation chains are sent to the sub-graphs to be executed, with the responses then merged together inside the Federated Store.

!!! note "Special Case"
    `AddElements` operations are a special case, these only add elements to sub-graphs where the edge or entity groupId is known by that sub-graph.

!!! warning "Potential Duplication"
    When adding elements, if 2 sub-graphs contain the same group in the schema then the elements will be added to both of the sub-graphs.
    Subsequently, running a `GetElements` operation would then return the same element from both sub-graph, resulting in duplicates.
    It is advised to keep groups to within one sub-graph or limit federated queries to one of the graphs.

Selecting which sub-graph(s) to federate operations across is done using the `FederatedOperation`.
Running operations against a Federated Store without using this automatically runs them over all graphs the user can view, unless the [`storeConfiguredGraphIds` property](#setting-default-query-graph-ids) has been set.

See [the `FederatedOperation` page](../../reference/operations-guide/federated-operation.md) for details and examples of using this operation.

### Skipping Failed Execution

If operation execution against one of the graphs fails, an `OperationException` will be thrown.
Unless the operation has the option `gaffer.federatedstore.operation.skipFailedFederatedStoreExecute` set to `true`.
In this situation that graph is skipped and the Federated Store continues with the remaining graphs.

```json title="Skipping failed execution example"
{
  "class" : "GetAllElements",
  "options" : {
    "gaffer.federatedstore.operation.skipFailedFederatedStoreExecute" : "true"
  }
}
```

??? example "Skipping failed execution example with the Java API"

    ```java
    GetAllElements skipFailedExecution = new GetAllElements.Builder()
            .option(FederatedStoreConstants.KEY_SKIP_FAILED_FEDERATED_STORE_EXECUTE, "true")
            .build();
    ```

## Restricting Graph Access

If you have many graphs and different groups of users, then you may wish to limit user access to some sub-graphs.
This can be done by either using authorisations, or configurable read and write access predicates.
These are specified at the time of adding the graph to the Federated Store.

### Public/Private Access

Within the `AddGraph` operation, explicitly setting the parameter `isPublic` as true grants access to all users, regardless of any authorisations used.
By default, the `isPublic` parameter is false.

If `isPublic` is false and authorisations are not specified, then the graph will be private to the user who added it and no other users will be able to access it.
Adding authorisations will open the graph to all users with those authorisations, not just the user who create it.

```json title="AddGraph Operation allowing public access"
{
  "class" : "AddGraph",
  "graphId" : "publicGraph",
  "graphAuths" : [ "Auth1" ],
  "isPublic" : true,
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "exampleId" ]
}
```

??? example "AddGraph allowing public access with the Java API"

    ```java
    AddGraph publicGraph = new AddGraph.Builder()
            .graphId("publicGraph")
            .parentSchemaIds(Lists.newArrayList("exampleId"))
            .parentPropertiesId("mapStore")
            .isPublic(true) //<-- public access
            .graphAuths("Auth1") //<-- used but irrelevant as graph has public access
            .build();
    federatedGraph.execute(addAnotherGraph, user);
    ```

#### Disallow Public Access

By default the Federated Store will allow graphs to be added which have public access enabled.
If you don't want graphs with public access to be added, this can be prevented by setting the `gaffer.federatedstore.isPublicAllowed` [store property (or Java equivalent)](#federated-store-properties) to false.
When public access is disallowed, the `isPublic` parameter will be ignored and requests to add a graph with public access will be treated as if public access was not specified.

### Limit Custom Properties

You may not want all users to be able to specify their own properties for graphs they are creating and adding, and to only use properties defined within a graph library.
To limit the users which can use custom properties, use the `gaffer.federatedstore.customPropertiesAuths` [store property (or Java equivalent)](#federated-store-properties) to require authorisation(s) for this.
When this is set, users without a required authorisation can only specify `StoreProperties` which exist in the `GraphLibrary` associated with the Federated Store.
This limitation is not enabled by default, allowing all users to use custom properties when adding graphs.

### Access Control

Graph access control is done through the use of a simple [Authorisations](#authorisations) approach or a more complex [Access Controlled Resource](#access-controlled-resource) approach.
Note that these two access control methods are mutually exclusive, attempting to use both will result in an error.

Within the `AddGraph` operation, do not assign the `isPublic` parameter or assign it to false, this ensures the settings described in this section are not ignored.

#### Authorisations

By assigning the parameter `graphAuths`, all users which have one of the specified authorisations will have access to that graph.
Earlier examples show how to include this when using the `AddGraph` Operation.
Graph authorisations work using OR logic, which means access is tied to a user having just one of the specified auths.
Using authorisations you cannot control access based on more complex logic (such as AND). See the next section for an approach which allows this.

#### Access Controlled Resource

Graphs in the Federated Store implement the `AccessControlledResource` interface allowing configuration of a custom Predicate which is tested against the user to determine whether they can access the graph.
This allows for complex access control logic, incorporating combinations of AND & OR.

The example below ensures readers of the graph have both the `read-access-auth-1` and `read-access-auth-2` auths, and users attempting to write to (or remove) the graph have both the `write-access-auth-1` and `write-access-auth-2` auths.

```json title="AddGraph Operation using Access Controlled Resource"
{
  "class" : "AddGraph",
  "graphId" : "AccessControlledResourceSecureGraph",
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "exampleId" ],
  "readAccessPredicate" : {
    "class" : "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
    "userPredicate" : {
      "class" : "AdaptedPredicate",
      "inputAdapter" : {
        "class" : "CallMethod",
        "method" : "getOpAuths"
      },
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
        "predicates" : [ {
          "class" : "CollectionContains",
          "value" : "read-access-auth-1"
        }, {
          "class" : "CollectionContains",
          "value" : "read-access-auth-2"
        } ]
      }
    }
  },
  "writeAccessPredicate" : {
    "class" : "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
    "userPredicate" : {
      "class" : "AdaptedPredicate",
      "inputAdapter" : {
        "class" : "CallMethod",
        "method" : "getOpAuths"
      },
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
        "predicates" : [ {
          "class" : "CollectionContains",
          "value" : "write-access-auth-1"
        }, {
          "class" : "CollectionContains",
          "value" : "write-access-auth-2"
        } ]
      }
    }
  }
}
```

??? example "AddGraph using Access Controlled Resource with the Java API"

    ```java
    AddGraph addAccessControlledResourceSecureGraph = new AddGraph.Builder()
            .graphId("AccessControlledResourceSecureGraph")
            .parentSchemaIds(Lists.newArrayList("ExampleId"))
            .parentPropertiesId("mapStore")
            .readAccessPredicate(
                    new AccessPredicate(
                            new AdaptedPredicate(
                                    new CallMethod("getOpAuths"),
                                    new And(
                                            new CollectionContains("read-access-auth-1"),
                                            new CollectionContains("read-access-auth-2")))))
            .writeAccessPredicate(
                    new AccessPredicate(
                            new AdaptedPredicate(
                                    new CallMethod("getOpAuths"),
                                    new And(
                                            new CollectionContains("write-access-auth-1"),
                                            new CollectionContains("write-access-auth-2")))))
            .build();
    federatedGraph.execute(addAccessControlledResourceSecureGraph, user);
    ```

## Merging results with different schemas

When using the FederatedStore, the schemas of your subgraphs need to be able to merge. In order for the schemas the merge, the shared groups need to be defined in a compatible way.

### Shared group differences that can be merged

??? info "Conflicting visibility property"

    === "Schema 1"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    }
                }
            },
            "visibilityProperty": "visibility"
        }
        ```

    === "Schema 2"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    }
                }
            },
            "visibilityProperty": "property1"
        }
        ```

    === "Merged Schema"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    }
                }
            }
        }
        ```

??? info "Conflicting vertex serialiser"

    === "Schema 1"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    }
                }
            },
            "vertexSerialiser": {
                "class": "uk.gov.gchq.gaffer.serialisation.implementation.StringSerialiser"
            }
        }
        ```

    === "Schema 2"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    }
                }
            },
            "vertexSerialiser": {
                "class": "uk.gov.gchq.gaffer.serialisation.implementation.MultiSerialiser"
            }
        }
        ```

    === "Merged Schema"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    }
                }
            }
        }
        ```

??? info "Conflicting group by"

    === "Schema 1"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    },
                    "groupBy": [
                        "property1"
                    ]
                }
            }
        }
        ```

    === "Schema 2"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    },
                    "groupBy": [
                        "property1",
                        "visibility"
                    ]
                }
            }
        }
        ```

    === "Merged Schema"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    },
                    "groupBy": [
                        "property1"
                    ]
                }
            }
        }
        ```

??? info "Properties not present in other graph"

    === "Schema 1"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "visibility": "string"
                    }
                }
            }
        }
        ```

    === "Schema 2"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property2": "string",
                        "visibility": "string"
                    }
                }
            }
        }
        ```

    === "Merged Schema"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string",
                        "property2": "string",
                        "visibility": "string"
                    }
                }
            }
        }
        ```

### Shared group differences that cannot be merged

??? failure "No shared properties"

    === "Schema 1"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "string"
                    }
                }
            }
        }
        ```

    === "Schema 2"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property2": "string"
                    }
                }
            }
        }
        ```

??? failure "Conflicting property type definition"

    For a successful merge, everything in the type definiton has to be the same: class, serialiser, aggregation and validation.

    === "Schema 1"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "type1"
                    }
                }
            },
            "types": {
                "type1": {
                    "class": "java.lang.String",
                    "serialiser": {
                        "class": "uk.gov.gchq.gaffer.serialisation.implementation.StringSerialiser"
                    },
                    "validateFunctions": [
                        {
                            "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
                        }
                    ],
                    "aggregateFunction": {
                        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat"
                    }
                }
            }
        }
        ```

    === "Schema 2"

        ``` json
        {
            "entities": {
                "group1": {
                    "vertex": "string",
                    "properties": {
                        "property1": "type1"
                    }
                }
            },
            "types": {
                "type1": {
                    "class": "java.lang.Long",
                    "serialiser": {
                        "class": "uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawLongSerialiser"
                    },
                    "validateFunctions": [
                        {
                            "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                            "value": {
                                "java.lang.Long": 0
                            }
                        }
                    ],
                    "aggregateFunction": {
                        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
                    }
                }
            }
        }
        ```
