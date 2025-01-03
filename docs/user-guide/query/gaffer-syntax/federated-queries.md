# Federated Querying Guide

When interacting with a Gaffer instance that is backed by a federated store
there are some additional query considerations a user may need to be aware of.

## How are Operations Handled?

Gaffer operations are handled quite differently when using the federated store.
The general usage is that the operation submitted to the store will be forwarded
to the sub graph for execution. This means a user can typically use a federated
store like they would a normal store by submitting the same operation chains you
would use on any other store.

A user has control over some aspects of federation using the options passed to the
operation. These can be used to do things like pick graphs or control the
merging, a full list of the available options are outlined in the [reference guide](../../../reference/operation-options/federated-options.md).

If you wish to submit different operations to different graphs in the same query
you can do this by omitting any graph ID options on the outer operation chain.
You can then specify the graph IDs on the individual operations in the chain
instead. An example of this can be seen below:

!!! note
    This will turn off any merging of the results at the end of the chain, the
    operation chain will act like a standard chain where each operations output
    is now the input of the next operation. However, merging will still happen
    on each operation if more than one graph is specified for it.

!!! example ""
    This seeds for an entity from one graph and adds it into another graph.

    ```json
    {
        "class": "OperationChain",
        "operations": [
            {
                "class": "GetElements",
                "options": {
                    "federated.graphIds": "graph1"
                },
                "input": [
                    {
                        "class": "EntitySeed",
                        "vertex": "1"
                    }
                ]
            },
            {
                "class": "AddElements",
                "options": {
                    "federated.graphIds": "graph2"
                }
            }
        ]
    }
    ```

## How are Results Merged?

A key part to the federated store are its merge operators. These control how
results from multiple graphs are reduced to one result so can greatly effect the
results returned by the store.

Sensible defaults are in place if not specified however, you may wish to chose your
own operators to be used. As mentioned previously you can control aspects of federation
using operation options. This extends to the merge operators as well meaning you can
pick a different merge operator for your specific query. To do this you simply use the
same key as the store properties which are all outlined in the [reference guide](../../../reference/store-properties/federated-store.md#merge-operators)
and then specify the class you want to use instead, an example might be:

!!! example ""
    Use different operator to get the maximum value, in this case this would
    return the amount of elements in the largest graph.

    ```json
    {
        "class": "OperationChain",
        "options": {
            "federated.useDefaultGraphIds": true,
            "gaffer.store.federated.merge.number.class": "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
        },
        "operations": [
            {
                "class": "GetAllElements"
            },
            {
                "class": "Count"
            }
        ]
    }
    ```

!!! note
    Please note you currently can't chose a merge operator for operations that
    return an `Iterable` type, they will always just be chained together (an
    iterable of `Element`s is an obvious exception).

### The Default Element Merge Operator

The default operator used to merge Gaffer elements is unique compared to the
other operators. This operator will only be used if element aggregating is set
to "true", either by default, using the store properties, or for just the query
using the operation option `federated.aggregateElements`.

When enabled, the default merge operator attempts to use the aggregation
functions from the merged schema of the graphs that were executed on. This
attempts to emulate how the data would have been stored in a single Gaffer graph
as entities or edges that are the same (e.g. same group and vertices) will be
merged together with their properties aggregated using the functions defined in
the schema.

#### Considerations

There are some considerations you may wish to know when using the element merge
operator:

- This type of merging will be inherently slower than simply returning a chained
  iterable of elements.
- The results must fit in the available memory of the federated store to be
  merged. If the returned result size is too big you may experience significant
  performance issues.
- The results will be deduplicated as part of this process e.g. two identical
  entities or edges will be merged into one.
- Any filtering you might have specified in the `View` will **only** be applied
  to the individual graph results, this means two results separately will
  satisfy the `View` but once aggregated they may not.
- If you wish to write or use your own operator for merging elements the class
  must extend the [`ElementAggregateOperator`](https://github.com/gchq/Gaffer/blob/develop/store-implementation/simple-federated-store/src/main/java/uk/gov/gchq/gaffer/federated/simple/merge/operator/ElementAggregateOperator.java).
- If the schema uses a time sensitive aggregation function
  (e.g. [`First`](../../../reference/binary-operators-guide/koryphe-operators.md#first))
  for a property that is in multiple sub graphs, you may end up with duplicates
  in the result as the aggregator does not know which sub graph is first or
  last. This means you may get duplicates of the same vertex but with different
  properties in the result.
