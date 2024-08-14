# Custom Features in GafferPop

The GafferPop implementation provides some extra features on top of the
standard Tinkerpop framework that you can utilise in your queries. These
are likely specific to how a Gaffer graph operates and may not be available
in other graph technologies that support Gremlin queries.

## Adding Options to Queries

In standard Gremlin syntax it is possible to add additional key value variables
into a query via a [`with()`](https://tinkerpop.apache.org/docs/current/reference/#with-step)
step. This feature is utilised to allow some custom properties to be passed in
for Gaffer specific options.

!!! tip
    Please see the [reference guide](../../../reference/gremlin-guide/gaffer-options.md)
    for a full list of available options.

## NamedOperations in Gremlin

The [GafferPopNamedOperationService](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/tinkerpop/service/GafferPopNamedOperationService.html)
allows for the running of Gaffer [Named Operations](../../../administration-guide/named-operations.md)
using Tinkerpop. Users can run Named Operations and add new Named Operations,
deleting Named Operations is not currently possible with Tinkerpop.

!!! example ""
    Add a simple Named Operation that returns a count of all elements in your graph.

    === "Gremlin"

        ```python
        operation = gc.OperationChain(
            operations=[
                gc.GetAllElements(),
                gc.Count()
            ]
        ).to_json_str()

        params = {"add": {"name": "CountAllElements", "opChain": operation}}

        g.call("namedoperation", params)
        ```

    === "Java"

        ```java
        final AddNamedOperation operation = new AddNamedOperation.Builder()
            .operationChain(new OperationChain.Builder()
                .first(new GetAllElements()
                        .then(new Count<>())
                        .build())
                .build())
            .name("CountAllElements")
            .build();

        Map<String, String> addParams = new HashMap<>();
        addParams.put("name", "CountAllElements");
        addParams.put("opChain", operation.getOperationChainAsString());
        Map<String, Map <String, String>> params = Collections.singletonMap("add", addParams);

        g.call("namedoperation", params);
        ```

Users can also run any existing or added Named Operations that are stored in the cache.

!!! example ""

    === "Gremlin"

        ```python
        g.call("namedoperation", {"execute": "CountAllElements"}).to_list()
        ```

    === "Java"

        ```java
        Map<String, String> params = Collections.singletonMap("execute", "CountAllElements")
        g.call("namedoperation", params).toList();
        ```

## Custom Types

In Gaffer there are various [additional types](../../../reference/properties-guide/properties.md)
you may want to use to represent properties. These types may not be compatible
with the [GraphSON standard](https://tinkerpop.apache.org/docs/current/dev/io/#graphson-3d0)
used by Tinkerpop meaning they cannot be serialised or parsed by a Gremlin
client. To resolve this, measures have been added to GafferPop so that all custom
Gaffer types will be converted to their string representation when output via
this interface.

This conversion to string works both ways for some types too. The current
supported two way conversions are outlined in the table, all other types will
**only** be converted from custom type to string so can not be submitted in a
Gremlin query.

| Type | Input | Output |
| --- | --- | --- |
| [`TypeSubTypeValue`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/TypeSubTypeValue.html) | `g.V("[type=thetype,subType=thesubtype,value=thevalue]")` | `"TypeSubTypeValue[type=thetype,subType=thesubtype,value=thevalue]"` |

## Custom Functions and Predicates

Some additional functions and predicates are available in GafferPop that are
not present in standard Gremlin syntax. Currently these only include the
extensions provided by [`OpenCypher`](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-extensions)
but may be expanded in the future. Please see the reference guide for a full
list of [predicates](../../../reference/gremlin-guide/custom-predicates.md)
and [functions](../../../reference/gremlin-guide/custom-functions.md)
available.

!!! note
    To use custom functions and predicates via Gremlin python you must submit
    the query as a Groovy script as they likely will not have python bindings
    e.g.

    ```python
    from gremlin_python.driver import client
    from gremlin_python.driver.serializer import GraphSONSerializersV3d0
    from gremlin_python.driver.driver_remote_connection import DriverRemoteConnection

    # Establish client connection
    client = client.Client('ws://localhost:8080/gremlin', 'g', message_serializer=GraphSONSerializersV3d0())

    results =  client.submit("g.V().values('count').map(cypherToString()).toList()")
    results.all().result()
    ```

    See the [Tinkerpop documentation](https://tinkerpop.apache.org/docs/current/reference/#gremlin-python-scripts)
    for more information.

## Gaffer Gremlin Explain

With the Gaffer REST API there are additional endpoints to provide explanations
of how a given Gremlin query maps to Gaffer operation chains. The two endpoints are:

- `/rest/gremlin/explain` - Accepts plain string Gremlin Groovy script and will
  return JSON explain.
- `/rest/gremlin/cypher/explain` - Accepts plain string OpenCypher querys and
  will return JSON explain.

!!! warning
    Please be aware that in order to provide an explanation your submitted query will
    be run against the Graph.

An example of the input and output of a Gremlin explain is as follows:

!!! example ""
    Using endpoint: `/rest/gremlin/explain`.

    === "Gremlin Query"

        ```groovy
        g.V().hasLabel('person').toList()
        ```

    === "Gaffer Explain"

        ```JSON
        {
            "overview": "OperationChain[GetAllElements->Limit]",
            "chain": {
                "operations": [
                    {
                        "view": {
                            "entities": {
                                "person": {}
                            }
                        },
                        "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
                    },
                    {
                        "resultLimit": 5000,
                        "truncate": true,
                        "class": "uk.gov.gchq.gaffer.operation.impl.Limit"
                    }
                ],
                "class": "uk.gov.gchq.gaffer.operation.OperationChain"
            }
        }
        ```

An example of the input and output of a Cypher explain is as follows:

!!! example ""
    Using endpoint: `/rest/gremlin/cypher/explain`.

    === "Gremlin Query"

        ```cypher
        MATCH (p:person) WHERE ID(p) = '1' RETURN p
        ```

    === "Gaffer Explain"

        ```JSON
        {
            "overview": "OperationChain[GetElements]",
            "chain": {
                "operations": [
                {
                    "input": [
                    {
                        "vertex": "1",
                        "class": "uk.gov.gchq.gaffer.operation.data.EntitySeed"
                    }
                    ],
                    "view": {
                    "entities": {
                        "person": {}
                    }
                    },
                    "class": "uk.gov.gchq.gaffer.operation.impl.get.GetElements"
                }
                ],
                "class": "uk.gov.gchq.gaffer.operation.OperationChain"
            },
            "gremlin": "g.V().hasLabel('person').has('~id', eq('1')).project('p').by(__.valueMap().with('~tinkerpop.valueMap.tokens')).toList()"
        }
        ```
Note there are some limitations to be aware of when using these endpoints.

- The mapping of Gremlin to Gaffer operations is not one to one, this means that some
predicates may appear missing from the operation chain in the explanation. If this
is the case it means filtering may have occurred in Tinkerpop rather than in the
Gaffer view.
- Each Gremlin step is 'lazy' meaning if there is no output from the previous step it
will not execute the next step. This in essence means if your graph does not have
data in that reaches the last step then that step will be missing from the explanation.
- The query needs to be relevant for the data e.g. can't query using a group that isn't
in the Graph schema.
- All submitted Cypher explains will be translated to Gremlin first and have a `.toList()`
appended to the translation so it is actually executed.
