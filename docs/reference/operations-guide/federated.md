# Federated Operations

This page detials the Operations specific only to a [federated store](../../administration-guide/gaffer-stores/federated/configuration.md)
backed instance of Gaffer.

## AddGraph

Adds a new graph with optional access controls to the federated store. More
details on the exact use can be found in the [admin guide](../../administration-guide/gaffer-stores/federated/configuration.md#adding-a-new-graph),
but general usage requires the same information as setting up a standard graph
including the `graphConfig`, `schema` and `storeProperties` to be set.

??? example "Add a graph with default access restrictions"
    === "Java"

        ```java
        // Choose a graph ID for your graph
        final String graphId = "myGraph";

        // Replace the graph config, schema and properties for your use case
        final AddGraph operation = new AddGraph.Builder()
            .graphConfig(new GraphConfig(graphId))
            .schema(new Schema())
            .properties(new Properties())
            .build();
        ```

    === "JSON"
        Replace the graph config, schema and properties for your use case.

        ```json
        {
            "class": "AddGraph",
            "graphConfig": {
                "graphId": "myGraph"
            },
            "schema": {
                "entities": {},
                "edges": {},
                "types": {}
            },
            "properties": {
                "gaffer.store.class": "uk.gov.gchq.gaffer.accumulostore.AccumuloStore",
                "gaffer.store.properties.class": "uk.gov.gchq.gaffer.accumulostore.AccumuloProperties",
                "gaffer.cache.service.class": "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService"
            }
        }
        ```

??? example "Add a graph with custom access restrictions"
    === "Java"

        ```java
        final String graphOwner = "graphOwner";

        final AddGraph operation = new AddGraph.Builder()
            .graphConfig(new GraphConfig(graphId))
            .schema(new Schema())
            .properties(new Properties())
            .owner(graphOwner)
            .isPublic(true)
            .readPredicate(new AccessPredicate(
                new DefaultUserPredicate(graphOwner, Arrays.asList("readAuth1", "readAuth2"))))
            .writePredicate(new AccessPredicate(
                new DefaultUserPredicate(graphOwner, Arrays.asList("writeAuth1", "writeAuth2"))))
            .build();
        ```

    === "JSON"

        ```json
        {
            "class": "AddGraph",
            "graphConfig": {
                "graphId": "myGraph"
            },
            "schema": {
                "entities": {},
                "edges": {},
                "types": {}
            },
            "properties": {
                "gaffer.store.class": "uk.gov.gchq.gaffer.accumulostore.AccumuloStore",
                "gaffer.store.properties.class": "uk.gov.gchq.gaffer.accumulostore.AccumuloProperties",
                "gaffer.cache.service.class": "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService"
            },
            "owner": "graphOwner",
            "isPublic": true,
            "readPredicate": {
                "class": "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
                "userPredicate": {
                    "class": "uk.gov.gchq.gaffer.access.predicate.user.DefaultUserPredicate",
                    "creatingUserId": "graphOwner",
                    "auths": [ "readAuth1", "readAuth2" ]
                }
            },
            "writePredicate": {
                "class": "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
                "userPredicate": {
                    "class": "uk.gov.gchq.gaffer.access.predicate.user.DefaultUserPredicate",
                    "creatingUserId": "graphOwner",
                    "auths": [ "writeAuth1", "writeAuth2" ]
                }
            }
        }
        ```

## ChangeGraphAccess

Changes the access restrictions on an existing graph to allow setting things
like a different `writePredicate` or to make it `private`.

??? example "Example changing all restrictions"
    === "Java"

        ```java
        final ChangeGraphAccess operation = new ChangeGraphAccess.Builder()
            .graphId("myGraph")
            .owner("newGraphOwner")
            .isPublic(false)
            .readPredicate(new AccessPredicate(
                new DefaultUserPredicate(graphOwner, Arrays.asList("readAuth1", "readAuth2"))))
            .writePredicate(new AccessPredicate(
                new DefaultUserPredicate(graphOwner, Arrays.asList("writeAuth1", "writeAuth2"))))
            .build();
        ```

    === "JSON"

        ```json
        {
            "class": "ChangeGraphAccess",
            "graphId": "myGraph",
            "owner": "newGraphOwner",
            "isPublic": false,
            "readPredicate": {
                "class": "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
                "userPredicate": {
                    "class": "uk.gov.gchq.gaffer.access.predicate.user.DefaultUserPredicate",
                    "creatingUserId": "graphOwner",
                    "auths": [ "readAuth1", "readAuth2" ]
                }
            },
            "writePredicate": {
                "class": "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
                "userPredicate": {
                    "class": "uk.gov.gchq.gaffer.access.predicate.user.DefaultUserPredicate",
                    "creatingUserId": "graphOwner",
                    "auths": [ "writeAuth1", "writeAuth2" ]
                }
            }
        }
        ```

## ChangeGraphId

Changes the graph ID of the specified graph.

!!! warning
    If the graph is Accumulo backed then this will modify the table name making
    it unreadable to other instances unless they update the reference to it with
    the new ID.

??? example "Example changing a Graph ID"
    === "Java"

        ```java
        final ChangeGraphId operation = new ChangeGraphId.Builder()
            .graphId("myGraph")
            .newGraphId("newGraphId")
            .build();
        ```

    === "JSON"

        ```json
        {
            "class": "ChangeGraphId",
            "graphId": "myGraph",
            "newGraphId": "newGraphId"
        }
        ```

## GetAllGraphIds

Returns a list of all graph IDs available to the federated store.

!!! example ""
    === "Java"

        ```java
        final GetAllGraphIds operation = new GetAllGraphIds();
        ```

    === "JSON"

        ```json
        {
            "class": "GetAllGraphIds"
        }
        ```

## GetAllGraphInfo

Get all the graph IDs and information about the graphs available to the
federated store. This will include the full store properties if the user is an
admin or has write access on the respective graph.

??? example "Get all available graph information as admin"
    === "Java"

        ```java
        final GetAllGraphInfo operation = new GetAllGraphInfo();
        ```

    === "JSON"

        ```json
        {
            "class": "GetAllGraphInfo"
        }
        ```

    Example Result:

    ```json
    {
        "myGraph": {
            "graphDescription": "...",
            "graphHooks": { ... },
            "storeClass": "...",
            "storeProperties": { ... },
            "operationDeclarations": { ... },
            "owner": "...",
            "isPublic": true
        }
    }
    ```

## RemoveGraph

Allows removing a graph from the federated store. This will not delete all the
data unless `deleteAllData` option is specified.

!!! example ""
    === "Java"
        Remove a graph leaving the data untouched (if persistent).

        ```java
        final RemoveGraph removeGraph = new RemoveGraph.Builder()
            .graphId(graphId)
            .build();
        ```

        Remove a graph and delete all the data.

        ```java
        final RemoveGraph removeGraph = new RemoveGraph.Builder()
            .graphId(graphId)
            .deleteAllData(true)
            .build();
        ```

    === "JSON"
        Remove a graph leaving the data untouched (if persistent).

        ```json
            {
                "class": "RemoveGraph",
                "graphId": "myGraph"
            }
        ```

        Remove a graph and delete all the data.

        ```json
            {
                "class": "RemoveGraph",
                "graphId": "myGraph",
                "deleteAllData": true
            }
        ```
