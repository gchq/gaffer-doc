# Aggregation Guide

A basic introduction to the concept of Aggregation in Gaffer can be found in the
[User Guide](../../user-guide/gaffer-basics/what-is-aggregation.md). This guide is
an extension of the introduction to demonstrate more advanced usage of
Aggregation and how it can be applied.

## Ingest Aggregation

Ingest aggregation permanently aggregates similar elements together in the Graph
as they are loaded. The application of ingest aggregation is done via the Graph
schema which will apply the aggregation if one of the following conditions are
met:

- An entity has the same `group`, `vertex` (e.g. ID), `visibility` and all `groupBy`
  property values are the same.
- An edge has the same `group`, `source`, `destination`, and all `groupBy`
  property values are the same.

There are a few different use cases for applying ingest aggregation but it is
largely driven by the data you have and the analysis you wish to perform. As an
example, say you were expecting multiple connections of the same edge between
two nodes but each instance of the edge may have differing values on it's
properties this could be a place to apply aggregation to sum the values etc.

Please see the [following page](ingest-example.md) for some common use cases and
how you could apply ingest aggregation.

## Query-time Aggregation

Query-time aggregation as the name suggests is adding aggregation to
elements from within the graph query. This differs from ingest aggregation
as only the results of the query will have been aggregated the data stored
in the graph remains unchanged.

Generally to apply aggregation at query-time you must override the `groupBy`
property to prevent the default grouping taking place. It is then possible
to create your own aggregator in the query which can force the use of a
different aggregation function on a property.

A simple example demonstrating query-time aggregation can be found in the
[user guide on filtering](../../user-guide/query/gaffer-syntax/filtering.md#query-time-aggregation).

!!! tip
    Most of the time you will want to couple query-time aggregation with a `View`
    to allow more targeted queries on the data in your graph.
