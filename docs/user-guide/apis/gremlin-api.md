# Gremlin API

!!! warning
    The Gremlin API is still under development and has some [limitations](../query/gremlin/gremlin-limits.md).
    The implementation may not allow some advanced features of Gremlin and it's
    performance is unknown in comparison to standard Gaffer `OperationChains`.

## What is Gremlin?

[Gremlin](https://tinkerpop.apache.org/gremlin.html) is a query language for
traversing graphs. It is a core component of the Apache Tinkerpop library and
allows users to easily express more complex graph queries.

GafferPop is a lightweight Gaffer implementation of the [TinkerPop framework](https://tinkerpop.apache.org/),
where TinkerPop methods are delegated to Gaffer graph operations.

The addition of Gremlin as query language in Gaffer allows users to represent
complex graph queries in a simpler language, akin to other querying languages
used in traditional and NoSQL databases. It also has wide support for various
languages so for example, you can write queries in Python via the [`gremlinpython`](https://pypi.org/project/gremlinpython/)
library.

!!! tip
    In-depth tutorials on Gremlin as a query language and its associated libraries
    can be found in the [Apache Tinkerpop Gremlin docs](https://tinkerpop.apache.org/gremlin.html).

## How to Query a Graph

There are two main methods of using Gremlin in Gaffer, these are via a websocket
similar to a typical [Gremlin Server](https://tinkerpop.apache.org/docs/current/reference/#connecting-gremlin-server)
or by submitting queries via the REST Endpoints like standard Gaffer Operations.
Once connected the [Gremlin in Gaffer](../query/gremlin/gremlin.md) page
provides a simple comparison of Gremlin compared to Gaffer Operations.

!!! note
    Both methods require a running [Gaffer REST API](./rest-api.md) instance.

### Websocket API

The websocket provides the most _standard_ way to use the Gremlin API. The
Gaffer REST API provides a Gremlin server like experience via a websocket at
`/gremlin`, this can be connected to provide a graph traversal source for
spawning queries.

The websocket should support all standard Gremlin tooling and uses GraphSONv3
serialisation for communication. To connect a tool like [`gremlinpython`](https://pypi.org/project/gremlinpython/)
we can do something similar to [`gafferpy`](./python-api.md). First import the
required libraries (many of these will be needed later for queries):

```python
from gremlin_python.process.anonymous_traversal import traversal
from gremlin_python.driver.driver_remote_connection import DriverRemoteConnection
from gremlin_python.driver.serializer import GraphSONSerializersV3d0
from gremlin_python.process.graph_traversal import __
```

We can then establish a connection to the Gremlin server and save the reference
(typically called `g`):

```python
# Setup a connection with the REST API running on localhost
g = traversal().with_remote(DriverRemoteConnection('ws://localhost:8080/gremlin', 'g', message_serializer=GraphSONSerializersV3d0()))
```

Now that we have the traversal reference this can be used to spawn graph traversals
and get results back.

### REST API Endpoints

The Gremlin endpoints provide a similar interface to running Gaffer Operations.
They accept a plain string Gremlin Groovy or OpenCypher query and will return
the results in [GraphSONv3](https://tinkerpop.apache.org/docs/current/dev/io/#graphson-3d0)
format.

The two endpoints are:

- `/rest/gremlin/execute` - Runs a Gremlin Groovy script and outputs the result
  as GraphSONv3 JSON.
- `/rest/gremlin/cypher/execute` - Translates a Cypher query to Gremlin and
  executes it returning a GraphSONv3 JSON result. Note will always append a
  `.toList()` to the translation.

A query can be submitted via the Swagger UI or simple POST request such as:

```bash
curl -X 'POST' \
  'http://localhost:8080/rest/gremlin/execute' \
  -H 'accept: application/x-ndjson' \
  -H 'Content-Type: text/plain' \
  -d 'g.V().hasLabel('\''something'\'').toList()'
```
