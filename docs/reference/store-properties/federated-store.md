# Federated Store Properties

!!! warning
    The federated store was overhauled in version 2.4.0 please read the
    [change notes](../../change-notes/changelist/v2.4.0-changes.md) for details.

This page details all properties that are specific to [federated stores](../../administration-guide/gaffer-stores/federated/configuration.md).
All properties are specified in the `store.properties` file alongside
any [common properties](./common.md).

## General Properties

### `gaffer.store.federated.default.graphIds`

Default: `None`

The list of default graph IDs for if a user does not specify what graph(s) to
run their query on. Takes a comma separated list of graph IDs e.g.
`"graphID1,graphID2"`

### `gaffer.store.federated.allowPublicGraphs`

Default: `true`

Are graphs with public access allowed to be added to this store.

### `gaffer.store.federated.default.aggregateElements`

Default: `false`

Should queries aggregate returned Gaffer elements together using the binary
operator for merging elements. False by default as it can be slower, meaning
results are just chained into one big list instead.

### `gaffer.store.federated.graphCache.name`

Default: `"federatedGraphCache_<graphId>"`

The name of the cache that the federated store will store its graphs in. This
allows sharing of graphs between different federated stores if the cache name is
the same (and same default implementation).

---

## Merge Operators

These properties are used to configure the default merge operators used for
merging data from multiple graphs together. These can also be overridden on a
per query basis using operation options if the user requires.

### `gaffer.store.federated.merge.number.class`

Default: [`uk.gov.gchq.koryphe.impl.binaryoperator.Sum`](../binary-operators-guide/koryphe-operators.md#sum)

Default binary operator for merging [`Number`](https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html)
results (e.g. from a `Count` operation) from multiple graphs.

### `gaffer.store.federated.merge.string.class`

Default: [`uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat`](../binary-operators-guide/koryphe-operators.md#stringconcat)

Default binary operator for merging [`String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)
results from multiple graphs.

### `gaffer.store.federated.merge.boolean.class`

Default: [`uk.gov.gchq.koryphe.impl.binaryoperator.And`](../binary-operators-guide/koryphe-operators.md#and)

Default binary operator for merging [`Boolean`](https://docs.oracle.com/javase/8/docs/api/java/lang/Boolean.html)
results from multiple graphs.

### `gaffer.store.federated.merge.collection.class`

Default: [`uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat`](../binary-operators-guide/koryphe-operators.md#collectionconcat)

Default binary operator for merging [`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html)
results from multiple graphs.

### `gaffer.store.federated.merge.map.class`

Default: [`uk.gov.gchq.koryphe.impl.binaryoperator.Last`](../binary-operators-guide/koryphe-operators.md)

Default binary operator for merging the values of [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)
results when two of the same keys exist from multiple graphs.

### `gaffer.store.federated.merge.element.class`

Default: `uk.gov.gchq.gaffer.federated.simple.merge.operator.ElementAggregateOperator`

Default binary operator for merging Iterables of Gaffer elements from multiple
graphs. The default class here will attempt to aggregate elements based on the
merged schema of the sub graphs.
