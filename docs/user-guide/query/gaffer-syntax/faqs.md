# FAQs

These are some previously asked questions about querying in Gaffer.

## Iterable results

**If I do queries such as `GetElements` or `GetAdjacentIds` the response type is an `Iterable` - why?**

To avoid loading all query results into memory, [Gaffer Stores](../../../administration-guide/gaffer-stores/store-guide.md)
should return an [`Iterable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html) which lazily loads and
returns the data as a user iterates through the results. In the case of Accumulo, this means a connection to Accumulo must
remain open whilst this iteration takes place. This should be closed automatically when the end of the results is reached.
However, if you decide not to read all the results, i.e. you just want to check if the results are not empty -
`!results.iterator().hasNext()` - or an exception is thrown whilst iterating, then the results iterable will not be closed
and the connection to Accumulo will remain open. Therefore, to be safe results should always be consumed in a
[try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html) block.

**Following on from the previous question, why can't I iterate through results in parallel?**

As mentioned above, the results iterable holds a connection open to Accumulo. To avoid opening multiple connections and
accidentally leaving the connections open, the Accumulo Store only allows one iterator to be active at any time. When you
call `.iterator()` the connection is opened. If you call `.iterator()` again, the original connection is closed and a new
connection is opened. This means you can't process the iterable in parallel using Java's streaming API if this involves
making multiple calls to `.iterator()`. If your results fit in memory you could add them to a Set/List and then process
that collection in parallel.

## Filtering Results

**How do I return all my results summarised?**

You need to provide a View to override the `groupBy` fields for all element groups defined in the Schema. If you set the
`groupBy` field to an empty array, this will mean no properties will be included in the element key and all the properties
will be summarised. You can do this by providing a View:

```json
"view": {
    "globalElements" : [{
        "groupBy" : []
    }]
}
```

**My queries are returning duplicate results - why and how can I deduplicate them?**

For example, if you have a Graph containing the Edge A-B and you do a `GetElements` with a large number of seeds, with A
as the first seed and B as the last seed, then you will get the Edge A-B back twice. This is because Gaffer stores lazily
return the results for your query to avoid loading all the results into memory, so it will not realise that A-B has been
queried for twice.

You can deduplicate your results in memory using the [`ToSet`](../../../reference/operations-guide/core.md#toset) operation.
But, be careful to only use this when you have a small number of results. It might also be worth using the
[`Limit`](../../../reference/operations-guide/core.md#limit) operation prior to `ToSet` to ensure you don't run out of
memory. E.g:

```java
new OperationChain.Builder()
    .first(new GetAllElements())
    .then(new Limit<>(1000000))
    .then(new ToSet<>())
    .build();
```

**I want to filter the results of my query based on the destination of the result Edges**

There are several ways of doing this, you will need to choose the most appropriate way for your needs.

If you are querying with just a single EntitySeed with a vertex value of X and require the destination to be Y, then you should
change your query to use an EdgeSeed with `source = X` and `destination = Y` and `directedType = EITHER`.

If you are querying with multiple EntitySeeds, then just change each seed into an EdgeSeed, as described above.
 
If you require your destination to match a provided regex, than you will need to use a regex filter, [Regex](../../../reference/predicates-guide/koryphe-predicates.md#regex)
or [MultiRegex](../../../reference/predicates-guide/koryphe-predicates.md#multiregex). The predicate can then be used in your
Operation View to filter out elements which don't match the regex.

When the query is run and a seed matches an edge vertex, your seed may match the source or the destination vertex. So, you need
to tell the filter to apply to the opposite end of the edge. If you are running against a store that implements the `MATCHED_VERTEX`
trait (e.g Accumulo) then it is easy. The edges returned from the store will have a `matchedVertex` field, so you know which end
of the edge your seed matched. This means you can select the vertex at the other end of the edge using the `ADJACENT_MATCHED_VERTEX`
keyword. For example:

```java
GetElements results = new GetElements.Builder()
    .input(new EntitySeed("X"))
    .view(new View.Builder()
        .edge("yourEdge", new ViewElementDefinition.Builder()
            .preAggregationFilter(
                new ElementFilter.Builder()
                    .select(IdentifierType.ADJACENT_MATCHED_VERTEX.name())
                    .execute(new Regex("[yY]"))
                    .build())
            .build())
        .build())
    .build();
```

Without the `matchedVertex` field it is a bit more difficult. If you are using directed edges and you know what you seed will always
match the source, then you can select the 'DESTINATION' in the filter. Otherwise, you will need to provide a filter that checks the
`SOURCE` or the `DESTINATION` matches the regex. For example:

```java
GetElements results = new GetElements.Builder()
    .input(new EntitySeed("X"))
    .view(new View.Builder()
        .edge("yourEdge", new ViewElementDefinition.Builder()
            .preAggregationFilter(
                new ElementFilter.Builder()
                    .select(IdentifierType.SOURCE.name(), IdentifierType.DESTINATION.name())
                    .execute(new Or.Builder<>()
                            .select(0)
                            .execute(new Regex("[yY]"))
                            .select(1)
                            .execute(new Regex("[yY]"))
                            .build())
                    .build())
            .build())
        .build())
    .build();
```

For more related information on filtering, see the [Filtering Guide](filtering.md).

## Second Hop

**I've just done a `GetElements` and want to do another hop, but I get strange results doing two sequential `GetElements`.**

You can seed a get related elements operation with vertices (EntityIds) or edges (EdgeIds). If you seed the operation with
edges you will get back the Entities at the source and destination of the provided edges, in addition to the edges which
match your seed.

You may get a lot of duplicates and unwanted results. What you really want to do is to use the [`GetAdjacentIds`](../../../reference/operations-guide/get.md#getadjacentids)
operation to hop down the first edges and return just the vertices at the opposite end of the related edges. You can still
provide a `View` and apply filters to the edges you traverse down. In addition, it is useful to add a direction to the query,
so you don't go back down the original edges. You can continue doing multiple `GetAdjacentIds` to traverse around the Graph
further. If you want the properties on the edges to be returned you can use `GetElements` as the final operation in your chain.

See [the old version of this question](https://gchq.github.io/gaffer-doc/v1docs/components/core/operation.html#i-have-just-done-a-getelements-and-now-i-want-to-do-a-second-hop-around-the-graph-but-when-i-do-a-getelements-followed-by-another-getelements-i-get-strange-results)
for an example.

## Optimising Queries

**Any tips for optimising my queries?**

Limit the number of groups you query for using a View - this could result in a
big improvement.

When defining filters in your View try and use the `preAggregationFilter` for all your filters as this will be run before
aggregation and will mean less work has to be done to aggregate properties that you will later just discard. On Accumulo,
`postTransformFilters` are not distributed, they are computed on a single node and so they can be slow.
 
Some stores (e.g. Accumulo) store the properties in different columns and lazily deserialise a column as properties in that
column are requested. So if you limit your filters to a single column, then less data needs to be deserialised. For Accumulo
the columns are split up depending on whether the property is a groupBy property. So if you want to execute a time window query
and your timestamp is a groupBy property, then depending on the store you are running against this may be optimised. On Accumulo
this will be fast as it doesn't need to deserialise the entire Value, just the column qualifier containing your timestamp property.

Also, when defining the order of Predicates in a Filter, the order is important. It will run the predicates in the order you
provide, so order them so that the first ones are the more efficient and will filter out the most data. It is generally more
efficient to load/deserialise the groupBy properties than the non-groupBy properties, as there are usually less of them. So if your
filter applies to two properties, a groupBy and a non-groupBy property, then we recommend putting the groupBy property filter first
as that will normally be more efficient.

When doing queries, if you don't specify Pre or Post Aggregation filters then this means the entire filter can be skipped. When
running on Accumulo stores this means entire iterators can be skipped and this will save a lot of time. So, if applicable, you will
save time if you put all your filtering in either the Pre or Post section (in some cases this isn't possible).

Gaffer lets you specify validation predicates in your Schema to validate your data when added and continuously in the background for
age off. You can optimise this validation, by removing any unnecessary validation. You can do most of the validation you require in
your `ElementGenerator` class when you generate your elements. The validation you provide in the schema should be just the validation
which you must have, because *this may be run a lot*. On Accumulo - it is run in major/minor compactions and for every query. If you
can, just validate properties that are in the groupBy, this will mean that the store may not need to deserialise all other properties
just to perform the validation.

**How can I optimise the `GetAdjacentIds` query?**

When doing `GetAdjacentIds`, try and avoid using PostTransformFilters. If you don't specify these then the final part of the query
won't need to deserialise the properties, it can just extract the destination off the edge. Also see the answer above for general
query optimisation.

**How can I optimise `AddElementsFromHdfs`?**

Try using the `SampleDataForSplitPoints` and `SplitStoreFromFile` operations to calculate splits points. These can then be used to
partition your data in the map reduce job used to import the data. If adding elements into an empty Accumulo table or a table without
any splits then the `SampleDataForSplitPoints` and `SplitStoreFromFile` operations will be executed automatically for you. You can
also optionally provide your own splits points for your `AddElementsFromHdfs` operation.
