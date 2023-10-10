# Aggregation Guide

A basic introduction to the concept of Aggregation in Gaffer can be found in the
[User Guide](../user-guide/gaffer-basics/what-is-aggregation.md). This guide is
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

## Query-time Aggregation

...
