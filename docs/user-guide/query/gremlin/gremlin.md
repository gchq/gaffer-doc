# Gremlin in Gaffer

!!! warning
    The functionality released in Gaffer 2.1 is experimental and may not be as
    performant as traditional Gaffer querying.

[Gremlin](https://tinkerpop.apache.org/gremlin.html) is a query language that can be used in graph traversals.
It is a core component of the Apache Tinkerpop library and can allow users to easily express more complex Gaffer queries.

Gremlin is founded on the "write once, run anywhere" philosophy; meaning that users no longer need to learn
Java or Python alongside Gaffer terminology to run Gaffer queries.
Therefore, the addition of Gremlin as query language in Gaffer 2 allows users to represent complex graph queries in a simpler language akin to other querying languages used in traditional and NoSQL databases.

A Gremlin query is composed of a number of steps that atomically affect the data stream step by step.
These steps are separated into 3 fundamental types: map (translation), filter (removal) and side-effect (observations etc.).

## Interfacing with Gremlin

One of the great features of Gremlin is it's versatility of use.
There are a large number of supported language libraries that allow you to write queries in whichever coding language you prefer.
For example, there is a [Python Gremlin](https://pypi.org/project/gremlinpython/) interface.
This means your tooling won't have to change to write these queries which is a nice bonus for Gremlin.

## Imperative & Declarative Queries

Gremlin supports 3 main methods of querying methods that gives us an element of flexibility using the library.
Imperative queries are procedural and describe what's happening at each sequential step whereas Declarative queries describe what should happen but lets the query compiler decide how and which order steps should be ran in.
Choosing the right method here allows for a lot of control over how our queries get ran or allows the controller to optimise.

## OTLP and OLAP

Gremlin queries are flexible in that they can be evaluated in a realtime (OLTP) or batch (OLAP) format, this allows us a lot of flexibility in use, especially when querying over a multi machine or federated graph.

!!! tip
    Information on Gremlin as a query language, its associated libraries and more in-depth tutorials can be found in the [Apache Tinkerpop Gremlin docs](https://tinkerpop.apache.org/gremlin.html).

## Gremlin in Gaffer

Gremlin was added to Gaffer as a new graph query language in release 2.1.
There is a small demo on the [gaffer-docker repo](https://github.com/gchq/gaffer-docker/tree/develop/docker/gremlin-gaffer) using the "TinkerPop Modern" [demo graph](https://tinkerpop.apache.org/docs/current/images/tinkerpop-modern.png).
This container contains a Python notebook that allows users to see how to set up the Gremlin console and to run simple queries.

There are more example queries using this graph to be found in the [Gremlin Getting Started](https://tinkerpop.apache.org/docs/current/tutorials/getting-started/) docs.

!!! note
    The [administration guide](../../../administration-guide/gaffer-deployment/gremlin.md) contains further details on how you can add Gremlin querying to your own Graph instance.