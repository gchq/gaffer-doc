# Accumulo Store Implementation

The [accumulo-store module](https://github.com/gchq/Gaffer/tree/master/core/store) is an implementation of the Store API which uses Apache Accumulo.

This page contains brief details on the internal implementation of the `AccumuloStore`. For information on configuring and using this store, see [the Accumulo Store reference page](../../../administration-guide/gaffer-stores/accumulo-store.md).

## Introduction

It is assumed that the reader has some familiarity with the design of Accumulo (see the [Design page in Accumulo's Docs](https://accumulo.apache.org/docs/2.x/getting-started/design)).

The important features for Gaffer are:

- Accumulo stores data in key-value pairs. A key has multiple parts, namely a row ID, a column family, a column qualifier, a column visibility, and a timestamp. Each of these is simply a byte array, with the exception of the timestamp which is a long. A value is simply a byte array.
- Data in Accumulo is stored ordered by key. Keys are stored sorted by increasing row ID, then column family, then column qualifier, then column visibility, then by decreasing timestamp.
- Accumulo allows locality groups to be set which group together column families. This means that scans that only need to read certain column families can skip families they do not need to read.
- Accumulo allows data to be tagged with a visibility which restricts which users can view it.
- Accumulo allows the user to configure iterators that run at scan time, at compaction time or both. Gaffer adds iterators to scans to filter data. It uses compaction time iterators to persistently aggregate the properties of elements together, and to continually validate data.
- Accumulo provides an `InputFormat` that allows data to be retrieved via MapReduce jobs.

The core of the functionality is implemented in the key-packages, the iterators and the retrievers. Each of these is described in some detail below.

## Key-packages

As noted in the [Key-packages section of the Accumulo Store reference](../../../administration-guide/gaffer-stores/accumulo-store.md#key-packages), key-packages are responsible for converting `Element`s to and from key-value pairs, for creating ranges of keys containing all data relevant to a particular query, and for configuring the Iterators. Gaffer provides two key-packages: `ByteEntityKeyPackage` and `ClassicKeyPackage`. Advanced users are able to create their own key-packages if they wish - see [options for future key-packages](#options-for-future-key-packages) for some ideas.

Before these key-packages are described, we review the main design goals:

- To be able to retrieve all `Edge`s for a vertex by seeking to a single point in the table and scanning forwards.
- To be able to retrieve all `Entity`s for a vertex by seeking to a single point in the table, and reading only relevant key-value pairs, i.e. not reading any of the `Edge`s associated to the vertex.
- A vertex should be uniquely identified by its serialised value. It should not be necessary to consult an external source to find the value that identifies a vertex. In particular unlike most graph databases we do not use longs to identify vertices.
- To ensure that there are no "fat" rows, i.e. that there are not very large numbers of key-value pairs with the same row-key.
- To allow efficient aggregation of properties.

Both key-packages convert an `Entity` into a single Accumulo key-value pair and an `Edge` into two key-value pairs. The row ID (also known as the row-key) of the key-value formed from the `Entity` is the vertex serialised to a byte array, followed by a flag to indicate that this is an `Entity`. This allows the `Entity`s associated to a vertex to be quickly retrieved. It is necessary to store each `Edge` as two key-values so that it can found from both the source vertex and the destination vertex: one key-value has a row ID consisting of the source vertex serialised to a byte array, followed by a delimiter, followed by the destination vertex serialised to a byte array; the other key-value has the opposite, with the destination vertex followed by the source vertex. A flag is also stored to indicate which of these two versions the key is so that the original `Edge` can be recreated.

An important feature of the row IDs created by both key-packages is that it is possible to create ranges of keys that either only contain the `Entity`s or only contain the `Edge`s or contain both. This means that if, for example, a user states that they only want to retrieve the `Entity`s for a particular vertex then only relevant key-value pairs need to be read. In the case of a high-degree vertex, this means that queries for just the `Entity`s will still be very quick.

The two key-packages differ in subtle details of how the row ID is created. In the following descriptions the notation "(serialised_vertex)" refers to the vertex serialised to a byte array with any occurrences of the zero byte removed. This is necessary so that the zero byte delimiter can be used to separate different parts of the row-key. The zero bytes are removed in such a way that the original byte array can be recreated, and so that ordering is preserved.

### `ClassicKeyPackage` details

The `ClassicKeyPackage` constructs the following Accumulo key-value pair for an `Entity`:

| Row ID              | Column Family | Column Qualifier    | Visibility          | Timestamp | Value                |
| ------------------- | ------------- | ------------------- | ------------------- | --------- | -------------------- |
| (serialised_vertex) | group         | group by properties | visibility property | timestamp | all other properties |

The following Accumulo key-value pairs are created for an `Edge`:

| Row ID                                                       | Column Family | Column Qualifier    | Visibility          | Timestamp | Value                |
| ------------------------------------------------------------ | ------------- | ------------------- | ------------------- | --------- | -------------------- |
| (serialised_source_vertex)0(serialised_destination_vertex)0x | group         | group by properties | visibility property | timestamp | all other properties |
| (serialised_destination_vertex)0(serialised_source_vertex)0y | group         | group by properties | visibility property | timestamp | all other properties |

If the `Edge` is undirected then `x` and `y` are both 1 for both key-values. If the `Edge` is directed then `x` is 2 and `y` is 3.

This is very similar to the design of the key-value pairs in version 1 of Gaffer, with the exception that version 1 did not store a delimiter or flag at the end of the row-key for an `Entity`. This necessitated a scan of the row-key counting the number of delimiters to determine whether it was an `Entity` or `Edge`. If it is an `Entity` the vertex could be created directly from the row-key. For the `ClassicKeyPackage`, this scan is not needed but an array copy of the row-key minus the delimiter and flag is needed. In practice, the difference in performance between the two is likely to be negligible.

### `ByteEntityKeyPackage` details

The ByteEntity key-package constructs the following Accumulo key-value pair for an `Entity`:

| Row ID                | Column Family | Column Qualifier    | Visibility          | Timestamp | Value                |
| -------------------   | ------------- | ------------------- | ------------------- | --------- | -------------------- |
| (serialised_vertex)01 | group         | group by properties | visibility property | timestamp | all other properties |

In the row ID the 0 is a delimiter to split the serialised vertex from the 1. The 1 indicates that this is an `Entity`. By having this flag at the end of the row id it is easy to determine if the key relates to an `Entity` or an `Edge`.

The following Accumulo key-value pairs are created for an `Edge`:

| Row ID                                                         | Column Family | Column Qualifier    | Visibility          | Timestamp | Value                |
| -------------------------------------------------------------- | ------------- | ------------------- | ------------------- | --------- | -------------------- |
| (serialised_source_vertex)0x0(serialised_destination_vertex)0x | group         | group by properties | visibility property | timestamp | all other properties |
| (serialised_destination_vertex)0y0(serialised_source_vertex)0y | group         | group by properties | visibility property | timestamp | all other properties |

If the `Edge` is undirected then both `x` and `y` are 4. If the `Edge` is directed then `x` is 2 and `y` is 3.

The flag is repeated twice to allow filters that need to know whether the key corresponds to a `Entity` or an `Edge` to avoid having to fully deserialise the row ID. For a query such as find all out-going edges from this vertex, the flag that is directly after the source vertex can be used to restrict the range of row IDs queried for.

Note that in a range query filtering to restrict the results to say only out-going edges happens in an iterator.

### Options for future key-packages

Numerous variations on the above key-packages could be implemented. These would generally improve the performance for some types of query, at the expense of decreasing the performance for other types of query. Some examples are:

- The row-keys could be sharded. The current design is optimised for retrieving all `Edge`s for a given vertex, when there are relatively few such `Edge`s. If there are a million edges for a vertex then all of these have to be read by a small number of tablet servers (typically one, unless the range spans multiple tablets). This limits the query performance. An alternative approach is to introduce a shard key at the start of the row-key to cause different edges for the same vertex to be spread uniformly across the table. This would increase the parallelism for queries which would lead to better performance when large numbers of edges need to be retrieved for a vertex. The trade-off is that all queries would need to query all shards which would reduce the performance when a vertex has only a small number of edges.
- If there are a very large number of `Edge`s with the same source, destination and group-by properties then this could cause unbalanced tablets. A sharding scheme similar to the above would deal with this.
- Remove the flag at the end of the row-key that indicates whether it corresponds to an `Entity` or an `Edge`. This is used to quickly determine whether it is an `Entity` or an `Edge`. This is actually superfluous information as the group is stored in the column family and that indicates whether the key-value is an `Entity` or an `Edge`. Storing the flag there creates the need for an array copy when an `Entity` is created from the key-value. Instead of storing the group string in the column family, two bytes could be stored. The first would indicate whether this is an `Entity` or an `Edge`, and if an `Edge` whether it needs reversing or not; the second would indicate what group it is.
- Store each group in a separate table. This should slightly improve the performance of queries that only require a subset of the groups, especially if the query scans lots of data (as Accumulo's locality groups are set in the above key-packages the performance improvement will probably be minor). It would worsen the query performance when multiple groups are being retrieved.
- If the vertices serialise to a fixed length, or if a maximum length is known, then the row-keys could be of fixed length. This would eliminate the need for the use of delimiters which forces the escaping of the zero byte inside the serialised value. This would potentially provide a small increase in ingest and query speed.

## Iterators

Gaffer makes substantial use of Accumulo's iterator functionality to perform permanent aggregation and validation of data at compaction time, and filtering and aggregation at query time. See the [Iterators](https://accumulo.apache.org/docs/2.x/development/iterators) section of Accumulo's Docs for more information on iterators.

The following subsections describes the iterators that are used in Gaffer. They are listed in decreasing order of priority, i.e. the first iterator runs first. The text in brackets after the name of the iterator gives the scopes that the iterator is applied in. Some iterators that are only used for very specific operations are not listed here.

### `AggregatorIterator` (compaction, scan)

This iterator aggregates together all properties that are not group-by properties for `Element`s that are otherwise identical. As the non-group-by properties are stored in the `Value` this means that all `Value`s for identical keys are merged together.

### `ValidatorFilter` (compaction, scan)

The `ValidatorFilter` iterator validates every `Element` using the validation logic defined in the schema. When this is run during a compaction it causes invalid data to be deleted. This is typically used to delete data that is older than a certain date.

### `ClassicEdgeDirectedUndirectedFilterIterator` (scan)

!!! note
    This is only used in the `ClassicKeyPackage`.

This is used to filter out edges that are not required because the user has specified filters relating to edge direction (outgoing or incoming) and edge "directedness" (directed or undirected) in their query. Note that it is possible to ask for various combinations of these, e.g.:

- Directed edges only: if the seed is A then directed edges A->B and B->A would be returned, but an undirected edge A-B wouldn't be.
- Directed outgoing edges only: if the seed is A then a directed edge A->B would be returned, but a directed edge B->A wouldn't be, nor would an undirected edge A-B.
- Directed incoming edges only: if the seed is A then a directed edge B->A would be returned, but a directed edge A->B wouldn't be, nor would an undirected edge A-B.
- Undirected edges only: if the seed is A then an undirected edge A-B would be returned, but directed edges A->B and B->A wouldn't be.
- Undirected outgoing edges only: if the seed is A then an undirected edge A-B would be returned, but directed edges A->B and B->A wouldn't be.
- Undirected incoming edges only: if the seed is A then an undirected edge A-B would be returned, but directed edges A->B and B->A wouldn't be.

In the latter two examples, note that an undirected edge A-B is defined to be both outgoing from, and incoming to, both A and B.

### `ElementPreAggregationFilter` (scan)

This iterator filters out `Element`s that are not valid according to the `View`. This filtering happens before the aggregation.

### `CoreKeyGroupByAggregatorIterator` (scan)

This iterator aggregates together all properties according to the group-by in the view.

### `ElementPostAggregationFilter` (scan)

This iterator filters out `Element`s that are not valid according to the `View`. This filtering happens after the aggregation.

### Locality groups

Accumulo's ability to have a large number of different column families allows Gaffer to store lots of different types of data in the same table. Specifying the locality groups means that when a query for a particular group is made, graph elements from other groups do not need to be read.

## Tests

!!! warning
    This section might not be fully up to date for Gaffer 2.0.0. To easily run integration tests against a cluster, you can now [use docker compose](https://github.com/gchq/gaffer-docker/tree/master/docker/gaffer-integration-tests).

For the purposes of unit testing and small-scale examples, Gaffer offers the Store subclass [MiniAccumuloStore](https://github.com/gchq/Gaffer/blob/master/store-implementation/accumulo-store/src/test/java/uk/gov/gchq/gaffer/accumulostore/MiniAccumuloStore.java) and the `MiniAccumuloCluster`.

By default all our tests use the MiniAccumuloStore. The MiniAccumuloStore automatically sets up or uses an existing MiniAccumuloCluster according to your store properties.

Alongside the standard Accumulo properties, you also have the opportunity to add some extra ones for a MiniAccumuloStore:

```
accumulo.mini.directory=/path/to/directory
accumulo.mini.root.password=password
accumulo.mini.visibilities=vis1,vis2,publicVisibility,privateVisibility,public,private
```
These properties are optional. By default the MiniAccumuloStore creates the cluster in a temporary directory, uses "password" as the root password and adds no extra visibilities to a user.

Because the MiniAccumulo re-uses clusters to be efficient, if two tests use the same user with different visibilities, the second one will overwrite the first. Therefore it's advisable to use different users if you want a user with different visibilities.

### Running the integration tests

#### Running a Mini Accumulo Cluster manually

Follow this [README.md](https://github.com/gchq/gaffer-tools/tree/master/mini-accumulo-cluster) in gaffer-tools on how to run a Mini Accumulo Cluster (with a shell) on your local machine.

!!! note
    When running a Mini Accumulo Cluster locally a `store.properties` file is generated, this can help identify the values you need to replace in the store.properties used for the integration tests below (such as the username, password, instance name and Zookeeper location).

#### Setting up accumulo-store integration tests

Update the following store properties files in src/test/resources/ to point to the location of the Accumulo store to test against:

- [src/test/resources/store.properties](https://github.com/gchq/Gaffer/blob/develop/store-implementation/accumulo-store/src/test/resources/store.properties)
- [src/test/resources/store2.properties](https://github.com/gchq/Gaffer/blob/develop/store-implementation/accumulo-store/src/test/resources/store2.properties)
- [src/test/resources/accumuloStoreClassicKeys.properties](https://github.com/gchq/Gaffer/blob/develop/store-implementation/accumulo-store/src/test/resources/accumuloStoreClassicKeys.properties)

If you are running an Accumulo cluster locally, here is what an example test store.properties file should look like:

```text
gaffer.store.class=uk.gov.gchq.gaffer.accumulostore.SingleUseAccumuloStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.accumulostore.AccumuloProperties
accumulo.instance=instance
accumulo.user=root
accumulo.password=password
accumulo.zookeepers=localhost:58630

gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.HashMapCacheService
gaffer.store.job.tracker.enabled=true
gaffer.store.operation.declarations=ExportToOtherAuthorisedGraphOperationDeclarations.json,ExportToOtherGraphOperationDeclarations.json,ResultCacheExportOperations.json
```

Ensure that when running an Accumulo instance, the user specified by the `accumulo.user` property has the `System.CREATE_TABLE` and `System.CREATE_NAMESPACE` permissions ('root' user has these set by default) and the following scan authorisations:

| Authorisation     | Required by |
| ----------------- | ----------- |
| vis1              | [VisibilityIT](https://github.com/gchq/Gaffer/blob/develop/integration-test/src/test/java/uk/gov/gchq/gaffer/integration/impl/VisibilityIT.java) |
| vis2              | [VisibilityIT](https://github.com/gchq/Gaffer/blob/develop/integration-test/src/test/java/uk/gov/gchq/gaffer/integration/impl/VisibilityIT.java) |
| public            | [SchemaHidingIT](https://github.com/gchq/Gaffer/blob/develop/core/graph/src/test/java/uk/gov/gchq/gaffer/integration/graph/SchemaHidingIT.java) |
| publicVisibility  | [AccumuloAggregationIT](https://github.com/gchq/Gaffer/blob/develop/store-implementation/accumulo-store/src/test/java/uk/gov/gchq/gaffer/accumulostore/integration/AccumuloAggregationIT.java) |
| privateVisibility | [AccumuloAggregationIT](https://github.com/gchq/Gaffer/blob/develop/store-implementation/accumulo-store/src/test/java/uk/gov/gchq/gaffer/accumulostore/integration/AccumuloAggregationIT.java) |

You can set these scan authorisations via the Accumulo shell:

e.g. if your store.properties have: `accumulo.user=root`, `accumulo.instance=instance`

```sh
root@instance> setauths -u root -s vis1,vis2,publicVisibility,privateVisibility,public,private
```

Run the integration tests:

```sh
mvn verify
```
