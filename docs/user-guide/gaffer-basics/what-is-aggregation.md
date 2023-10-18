# What is Aggregation?

**Aggregation** - noun

*"The process of combining things or amounts into a single group or total"*

---

In a software context Aggregation can have a variety of interpretations, in
Gaffer this specifically refers to ingest or query aggregation.

## Why Aggregate?

Aggregation allows us to take a set of elements and group them and their
properties together to form a new result. This allows us to get quick insights
into our data and generate valuable outputs from our graphing queries.

There are also some key benefits when using aggregation with sketches as we
can store aggregated data in a compact format to reduce storage requirements
and improve throughput. Further reading on sketches is available in the
[reference guide](../../reference/properties-guide/advanced.md).

## How is Aggregation Applied?

The application of aggregation can be done at either data ingest time or
added to a specific query.

For ingest time the configuration is specified via the [graph schema](../schema.md)
so that as data is loaded into a graph it is aggregated and stored in that
state. To demonstrate what this would look like take the simple graph below
where we can apply aggregation to merge the multiple edges together by summing the
properties.

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
    Other aggregation functions are available, for an in-depth guide into ingest
    aggregation please see the [administration guide](../../administration-guide/aggregation/overview.md).

The other place you can apply aggregation is at query time. As a user this is
the most common use case, typically an administrator will have set up the ingest
time aggregation to summarise the input data into a more manageable size and
saving disk space in the process. Users can then use query time aggregation to
further summarise the data without having to edit the graph schema.

The main difference between query-time aggregation and ingest aggregation is
the aggregation applied on a query will only affect the elements in that
query so the overall graph data is left intact. There is more information on how
to apply query-time aggregation in the [querying guide on filtering](../query/gaffer-syntax/filtering.md#query-time-aggregation).
