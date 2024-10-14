# Simple Federation Graph Access Control

Graphs added to a federated store can have restrictions placed on them in
addition to the standard user controls that may be in place on the data itself.

## Restricting Graph Access

To restrict access to a graph you must add the access controls when the graph
is added to the federated store. Once added to a store a graph's access cannot
be altered without removing and re-adding it.

The available restrictions you can apply when adding a graph are as follows,
additional sections on this page provide more detail where needed:

- `owner` - The user ID of the graphs owner. If not specified this will be the
ID of the user who added the graph. The owner by default does not affect the
restrictions on the graph, the user ID has no additional privileges.
- `isPublic` - Is the graph public or not, a public graph can be read by any
  user.
- `readPredicate` - This is an access control predicate that is checked when
operations are performed to see if the user running the operations can read the
graph.
- `writePredicate` - This is an access control predicate that is checked when a
user is trying to modify the graph. Modification in this case refers to editing
the configured graph such as, changing the graph ID or deleting the graph from
the store; it does not effect adding or deleting data inside the graph.

A full example of adding a graph with all these restrictions would look like:

!!! example ""
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
            "class": "uk.gov.gchq.gaffer.federated.simple.operation.AddGraph",
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

## Public and Private Graphs

Graphs added to a federated store can have a `isPublic` field added to them.
This field controls if the added graph is public which means all users can
submit requests to this graph from the federated store. A public graph will
essentially ignore any read predicate applied to it assuming all users can
see at least some data in the graph. Even if a graph is public restrictions
on the data inside it will still apply.

If `isPublic` has been set to `false` the graph will be added as private.
A private graph will check the specified read predicate to ensure the user
has access before running a query.

!!! note
    A federated store can be configured to disallow any public graphs from being
    added, please see the [store properties](./configuration.md#store-properties)
    for more details.

## Read and Write Access

As previously mentioned read/write access can be applied to graphs added to
federated stores. Reading from a graph is assumed to be running any operation on
the respective graph, this includes operations such as, `AddElements` etc. Write
access to the graph is required for modifying how it is stored in the federated
store, for example, deleting or renaming the graph.

### Access Control Predicates

To determine if a user has access to read or write, a predicate can be
specified that will be checked before any operation related to the graph is
executed.

All predicates are passed through by specifying them as the `userPredicate` in
the constructor of an `AccessPredicate`. Some default predicates are available
and are as follows however, if you wish to write your own predicate it must
implement Java's [`Predicate<User>`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)
interface.

- `DefaultUserPredicate` - Can be used to define a list of auth strings a user
must have to satisfy the predicate. This will also pass if the user is the owner, e.g.
matches the `creatingUserId` the predicate was initialised with.
- `NoAccessUserPredicate` - Will always deny any access if used.
- `UnrestrictedAccessUserPredicate` - Will always permit access if used.
