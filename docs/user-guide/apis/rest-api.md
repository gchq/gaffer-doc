# Using the Rest API

These sections will cover the usage of the Gaffer rest API to perform queries
and operations on a graph. This guide should cover a lot of the use cases a user
may face; however please refer to the [reference guide](../../reference/intro.md)
for a full list of whats possible.

## What is the Rest API?

When a graph is deployed, a REST (or RESTful) API will be available at a
predefined address. This provides an application programming interface (API)
that a user or computer can interact with to send and receive data between them
and the application.

In Gaffer, the Rest API consists of various predefined HTTP requests known as
endpoints that can be used to interact with a running graph instance. These
endpoints are accessed either by sending a crafted HTTP request to them e.g.
with a tool like [`curl`](https://curl.se/docs/httpscripting.html) or more
commonly by the provided [Swagger UI](https://swagger.io/).

## Querying a Graph

If you wish to just query to get some information about the graph instance such
as, what schema it is using or what available Operations it has then, there
should already be `GET` endpoints to do that. Executing any of these `GET`
requests will simply 'get' you some information, however they may be of limited
use for a user.

The main endpoint a user will interact with is `/graph/operations/execute`. This
is a `POST` request as it allows you to 'post' some data to it and get a
response back. From here is where you can do querying and run operations on the
graph to and extract data and do analysis with the graph.

In Gaffer, JSON is the main interchange language which means you can post JSON
and get response back in it.

!!! tip
    See the [what is JSON guide](../gaffer-basics/what-is-json.md) for a
    short introduction to the language.

Generally a query consists of two parts an `Operation` or `OperationChain` and
optionally some sort of filtering provided by a `View`. These parts are
discussed in more detail further in the guide so please see [the page covering
Operations](../query/gaffer-syntax/operations.md) and this page on
[filtering using views](../query/gaffer-syntax/filtering.md).
