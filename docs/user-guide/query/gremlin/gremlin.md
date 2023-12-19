# Gremlin in Gaffer

[Gremlin](https://tinkerpop.apache.org/gremlin.html) is a query language for traversing graphs.
It is a core component of the Apache Tinkerpop library and allows users to easily express more complex graph queries.

GafferPop is a lightweight Gaffer implementation of the [TinkerPop framework](https://tinkerpop.apache.org/), where TinkerPop methods are delegated to Gaffer graph operations.

!!! warning
    GafferPop is still in development and has some [limitations](gremlin-limits.md).
    The implementation is basic and its performance is unknown in comparison to using standard Gaffer `OperationChains`.

The addition of Gremlin as query language in Gaffer allows users to represent complex graph queries in a simpler language akin to other querying languages used in traditional and NoSQL databases.

## Gremlin Features

### Interfacing with Gremlin

One of the great features of Gremlin is its versatility of use.
There are a large number of supported language libraries that allow you to write queries in whichever coding language you prefer.
For example, there is a [Python Gremlin](https://pypi.org/project/gremlinpython/) interface.
This means your tooling won't have to change to write these queries which is a nice bonus for Gremlin.

### Imperative & Declarative Queries

Gremlin supports 3 main methods of querying methods that gives us an element of flexibility using the library.
Imperative queries are procedural and describe what's happening at each sequential step whereas declarative queries describe what should happen but lets the query compiler decide how and which order steps should be ran in.
Choosing the right method here allows for a lot of control over how our queries get ran or allows the controller to optimise.

### OTLP and OLAP

Gremlin queries are flexible in that they can be evaluated in a realtime (OLTP) or batch (OLAP) format, this allows us a lot of flexibility in use, especially when querying over a multi machine or federated graph.

!!! tip
    Information on Gremlin as a query language, its associated libraries and more in-depth tutorials can be found in the [Apache Tinkerpop Gremlin docs](https://tinkerpop.apache.org/gremlin.html).

## Gremlin in Gaffer

Gremlin was added to Gaffer as a new graph query language in version 2.1.
There is a small demo on the [gaffer-docker repo](https://github.com/gchq/gaffer-docker/tree/develop/docker/gremlin-gaffer) using the "TinkerPop Modern" [demo graph](https://tinkerpop.apache.org/docs/current/images/tinkerpop-modern.png).

## Basic Queries

Via the Gremlin console, you can load a standard TinkerPop dataset like below:

```groovy
    graph = GraphFactory.open('conf/gafferpop/gafferpop-tinkerpop-modern.properties')
    graph.io(graphml()).readGraph('data/tinkerpop-modern.xml')
    g = graph.traversal()
```

Some basic queries can be carried out on the data:

```groovy
    g.V('1').hasLabel('person')
    g.V('1', '2').hasLabel('person').outE('knows').values().is(lt(1))
```

The following example calculates the paths from 1 to 3 (max 6 loops):

```groovy
    start = '1';
    end = '3';
    g.V(start).repeat(bothE().otherV().simplePath()).until(hasId(end).or().loops().is(6)).path()
```

There are more example queries using this graph to be found in the [Gremlin Getting Started](https://tinkerpop.apache.org/docs/current/tutorials/getting-started/) docs.

## Mapping Gaffer to TinkerPop

Some of the terminology is slightly different between TinkerPop and Gaffer,
a table of how different parts are mapped is as follows:

| Gaffer | TinkerPop |
| --- | --- |
| Group | Label |
| Vertex | Vertex with default label of `id` |
| Entity | Vertex |
| Edge | Edge |
| Edge ID | A list with the source and destination of the Edge e.g. `[dest, source]` |

!!! note
    The [Gremlin administration guide](../../../administration-guide/gaffer-deployment/gremlin.md) contains further details on how you can add Gremlin querying to your own Graph instance.

