# What is Aggregation?

**Aggregation** - noun

*"The formation of a number of things into a cluster"*

---

In a software context Aggregation can have a variety of interpretations, in
Gaffer this specifically refers to the aggregation function. This takes
a number of forms but the common factor between them is that they use the
underlying koryphe library to provide the
[`ElementAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ElementAggregator.html).

Aggregation allows us to take a set of elements and group them and their
properties together to form a new result. These allow us to get quick insights
into our data and generate valuable outputs from our graphing queries.

## How is Aggregation Applied?

The application of aggregation can be done at either data ingest time or
added to a specific query.

For ingest time the configuration is specified via the [graph schema](../schema.md)
so that as data is loaded into a graph it is aggregated and stored in that
state. To demonstrate what this would look like take the simple graph below
where we can apply aggregation to merge the multiple edges together.

=== "Before Aggregation"
    If we don't apply ingest aggregation all elements will be stored separately in
    graph.

    ```mermaid
    flowchart LR
        A(A)
        --
        "Edge
        prop: 1"
        --> B(B)
        A(A)
        --
        "Edge
        prop: 1"
        --> B(B)
    ```

=== "After Aggregation"
    Now if we have apply aggregation to sum the same properties on the `Edge`
    they now get stored together as one element.

    ```mermaid
    flowchart LR
        A(A)
        --
        "Edge
        prop: 2"
        --> B(B)
    ```

!!! tip
    For an in-depth guide into ingest aggregation please see the [administration guide](../../administration-guide/aggregation/overview.md).

The other place you can apply aggregation is at query time. As a user this is
the most common use case as it avoids the need to edit the graph schema but
enables almost all the same features.

The main difference between query-time aggregation and ingest aggregation is
that the aggregation applied on a query will only effect the elements in that
query so the overall graph data is left intact. There is more information on how
to apply query-time aggregation in the [querying guide on filtering](../query/gaffer-syntax/filtering.md#query-time-aggregation).
