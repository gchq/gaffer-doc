# openCypher API

!!! warning
    The openCypher API is still experimental it is provided by a
    translation layer to Gremlin from the [OpenCypher project](https://github.com/opencypher/cypher-for-gremlin).
    Due to this, the implementation may experience the same [limitations](../query/gremlin/gremlin-limits.md)
    as the Gremlin API. It's performance is unknown but likely slower than
    Gremlin or Standard Gaffer Operations.

## What is openCypher?

openCypher is an open source implementation of Cypher - the most widely
adopted, fully-specified, and open query language for property graph databases.
The original Cypher language was developed by Neo4j®.

Cypher is a declarative graph query language that allows for expressive and
efficient querying and updating of the graph store. Cypher is a relatively
simple but still very powerful language. Complicated database queries can easily
be expressed through Cypher.

!!! tip
    Please see the [full reference guide](https://s3.amazonaws.com/artifacts.opencypher.org/openCypher9.pdf)
    from the openCypher org for more details.

## How to Query a Graph

There are two main methods of using openCypher in Gaffer, these are via Gremlin
using a websocket by wrapping the query in a Gremlin `with()` step. Or by
submitting queries via the REST Endpoints like standard Gaffer Operations. More
details on setting up the Gremlin side can be found on its [respective page](./gremlin-api.md).

!!! note
    Both methods require a running [Gaffer REST API](./rest-api.md) instance.

### Using the `with()` Step

The most full featured way to use openCypher is to simply add it into a Gremlin
query. This is done using the options interface, known in Gremlin as a `with()`
step. More information on how to run a Gaffer option in Gremlin is available in
the [reference guide](../../reference/gremlin-guide/gaffer-options.md) but
general usage is outlined below:

```groovy
g.with("cypher", "MATCH (n) WHERE ID(n) = '1' RETURN n").call().toList()
```

### REST API Endpoints

The endpoints provide a similar interface to running Gaffer Operations. They
accept a plaintext OpenCypher query and will return the results in
[GraphSONv3](https://tinkerpop.apache.org/docs/current/dev/io/#graphson-3d0)
format.

The two endpoints for openCypher are:

- `/rest/gremlin/cypher/execute` - Translates a Cypher query to Gremlin and
  executes it returning a GraphSONv3 JSON result. Note will always append a
  `.toList()` to the translation.
- `/rest/gremlin/cypher/explain` - Translates a Cypher query to Gremlin,
  executes it and returns an explanation of what Gremlin query and Gaffer
  operations it ran.

A query can be submitted via the Swagger UI or simple POST request such as:

```bash
curl -X 'POST' \
  'http://localhost:8080/rest/gremlin/cypher/execute' \
  -H 'accept: application/x-ndjson' \
  -H 'Content-Type: text/plain' \
  -d 'MATCH (n:'\''something'\'') RETURN n'
```

You can also utilise [Gafferpy](./python-api.md) to connect and run queries
using the endpoints.

```python
from gafferpy import gaffer_connector

gc = gaffer_connector.GafferConnector("http://localhost:8080/rest")

# Execute and return cypher
cypher_result = gc.execute_cypher("MATCH (n) WHERE ID(n) = '1' RETURN n")
```
