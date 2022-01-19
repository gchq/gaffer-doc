# Deprecations

This page describes deprecated code which has been removed in Gaffer 2 and how to migrate to better equivalents. Each heading for a section below refers to a classname from `uk.gov.gchq.gaffer` where there have been changes or where that class has been removed entirely. The section headings link to the code on GitHub for that class (as of the Gaffer 1.21.1 release).

Deprecations impacting the serialisers used in schemas are listed first, followed by [changes to Seed Matching and changes to Traits](#changes-to-seed-matching-and-traits). Other deprecations are then [listed in alphabetical order](#all-other-deprecations).

## Serialisers

#### Preservation of ordering
When using an ordered store (such as Accumulo), all serialisers used on vertices must preserve order. As such, `compactRaw` serialisers (which do not preserve order) cannot be used on vertices in ordered stores.

However, when preserving order is not required, such as for properties, `compactRaw` serialisers are the most effective solution and should always be used. Using an ordered serialiser on a property would reduce performance without providing any benefit. [See the schemas documentation for more detail](https://gchq.github.io/gaffer-doc/v1docs/getting-started/developer-guide/schemas.html#serialisers).

#### [`serialisation.implementation.raw.RawDateSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawDateSerialiser.java)
- This class has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedDateSerialiser` instead - note that this will preserve order.

#### [`serialisation.DateSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/DateSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedDateSerialiser` instead - note that this will preserve order. This doesn't implement `.deserialiseString(String)`, instead use `new Date(Long.parseLong(value))` in place of this.

#### [`serialisation.implementation.raw.RawDoubleSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawDoubleSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedDoubleSerialiser` instead - note that this will preserve order.

#### [`serialisation.DoubleSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/DoubleSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedDoubleSerialiser` instead - note that this will preserve order. This doesn't implement `.deserialiseString(String)`, instead use `Double.parseDouble(value)` in place of this.

#### [`serialisation.implementation.raw.RawFloatSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawFloatSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedFloatSerialiser` instead - note that this will preserve order.

#### [`serialisation.FloatSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/FloatSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedFloatSerialiser` instead - note that this will preserve order.

#### [`serialisation.IntegerSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/IntegerSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedIntegerSerialiser` instead, this will preserve order.
- If object ordering does **not** need to be preserved, `uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawIntegerSerialiser` could also be used instead.
- Neither of these implement `.deserialiseString(String)`, instead use `Integer.parseInt(value)` in place of this.

#### [`serialisation.implementation.raw.RawIntegerSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawIntegerSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedIntegerSerialiser` instead, this will preserve order.
- If object ordering does **not** need to be preserved, `uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawIntegerSerialiser` should instead be used.

#### [`serialisation.LongSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/LongSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedLongSerialiser` instead, this will preserve order.
- If object ordering does **not** need to be preserved, `uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawLongSerialiser` could also be used instead.

#### [`serialisation.implementation.raw.RawLongSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawLongSerialiser.java)
- This has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedLongSerialiser` instead, this will preserve order.
- If object ordering does **not** need to be preserved, `uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawLongSerialiser` should instead be used.

#### [`serialisation.ToBytesSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/ToBytesSerialiser.java)
- The method `deserialise(byte[])` has been marked as deprecated. It cannot be deleted as it is needed to implement the Serialiser interface.
- It is recommended for speed/performance to use the other implementation with an offset and a length - `deserialise(byte[], int, int)`.

#### [`serialisation.ToBytesViaStringDeserialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/serialisation/ToBytesViaStringDeserialiser.java)
- The method `deserialise(byte[])` has been marked as deprecated. It cannot be deleted as it is needed to implement the Serialiser interface.
- It is recommended for speed/performance to use the other implementation with an offset and a length - `deserialise(byte[], int, int)`.

## Changes to Seed Matching and Traits

### [`operation.SeedMatching`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/SeedMatching.java)
- This class has been removed.
- Use a view instead. See the [Gaffer docs for more detail](https://gchq.github.io/gaffer-doc/v1docs/getting-started/user-guide/filtering.html#seedmatching).

### [`store.Store`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/Store.java)
- The method `getTraits()` has been removed. Use `Store.execute(Operation, Context)` with the `GetTraits` operation instead.
- The method `hasTrait(StoreTrait)` has been removed. Use `Store.execute(Operation, Context)` with the `HasTrait` operation instead.

### [`federatedstore.FederatedGraphStorage`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/federated-store/src/main/java/uk/gov/gchq/gaffer/federatedstore/FederatedGraphStorage.java)
- The method `getTraits(GetTraits, Context)` has been removed. Use `Store.execute(Operation, Context)` with the `GetTraits` operation instead.

### [`federatedstore.FederatedStore`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/federated-store/src/main/java/uk/gov/gchq/gaffer/federatedstore/FederatedStore.java)
- The methods `getTraits()` and `getTraits(GetTraits, Context)` have been removed. Use `Store.execute(Operation, Context)` with the `GetTraits` operation instead.

## All other Deprecations

### [`accumulostore.AccumuloProperties`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/accumulo-store/src/main/java/uk/gov/gchq/gaffer/accumulostore/AccumuloProperties.java)
- The `TABLE` setting/variable plus the methods `getTable()` and `setTable(String)` have been removed. For `getTable()`, [uk.gov.gchq.gaffer.accumulostore.getTableName()](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/accumulostore/AccumuloStore.html#getTableName--) could be used instead.
- A `graphId` should be supplied instead of setting `TABLE` directly.

### [`accumulostore.MockAccumuloStore`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/accumulo-store/src/main/java/uk/gov/gchq/gaffer/accumulostore/MockAccumuloStore.java)
- This class has been removed.
- For in memory graphs, use `uk.gov.gchq.gaffer.mapstore.MapStore` instead.
- For tests use `uk.gov.gchq.gaffer.accumulostore.MiniAccumuloStore` instead.

### [`commonutil.TestTypes`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/common-util/src/test/java/uk/gov/gchq/gaffer/commonutil/TestTypes.java)
- This class has been removed.
- Use the equivalent `TestTypes` class in the store module `uk.gov.gchq.gaffer.store.TestTypes` instead.

### [`data.elementdefinition.view.NamedViewDetail`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/data/src/main/java/uk/gov/gchq/gaffer/data/elementdefinition/view/NamedViewDetail.java)
- The method `hasWriteAccess(final String userId, final Set<String> opAuths, final String adminAuth)` has been removed.
- Use `hasWriteAccess(final User user, final String adminAuth)` instead.

### [`data.elementdefinition.view.ViewElementDefinition`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/data/src/main/java/uk/gov/gchq/gaffer/data/elementdefinition/view/ViewElementDefinition.java)
- The method `setAggregator(final ElementAggregator aggregator)` has been removed.
- A `ViewElementDefinition` should be constructed using the builder `uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition.Builder` instead.

### [`federatedstore.FederatedAccess`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/federated-store/src/main/java/uk/gov/gchq/gaffer/federatedstore/FederatedAccess.java)
- The method `isAddingUser(User)` has been removed.
- Use `hasReadAccess(User user, String adminAuth)`/`hasWriteAccess(User user, String adminAuth)` instead.

### [`federatedstore.FederatedGraphStorage`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/federated-store/src/main/java/uk/gov/gchq/gaffer/federatedstore/FederatedGraphStorage.java)
- The methods `getAllIdsAsAdmin()`, `getAllGraphAndAccessAsAdmin(List<String>)` and `changeGraphAccessAsAdmin(String, FederatedAccess)` have all been removed.
- The method `remove(String graphId)` has been removed. The following can be used instead:
    - `remove(String graphId, User user)`
    - `remove(String graphId, User user, String adminAuth)`
    - `remove(String graphId, Predicate<Entry<FederatedAccess, Set<Graph>>> entryPredicateForGraphRemoval)`

### [`federatedstore.FederatedStore`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/federated-store/src/main/java/uk/gov/gchq/gaffer/federatedstore/FederatedStore.java)
- The method `updateOperationForGraph(Operation, Graph)` has been removed. Use `FederatedStoreUtil.updateOperationForGraph(Operation, Graph)` instead.
- The method `addGraphs(Set<String> graphAuths, String addingUserId, GraphSerialisable... graphs)` has been removed. The following can be used instead:
    - `addGraphs(Set<String> graphAuths, String addingUserId, boolean isPublic, GraphSerialisable... graphs)`
    - `addGraphs(Set<String> graphAuths, String addingUserId, boolean isPublic, boolean disabledByDefault, GraphSerialisable... graphs)`
    - `addGraphs(Set<String> graphAuths, String addingUserId, boolean isPublic, boolean disabledByDefault, AccessPredicate readAccessPredicate, AccessPredicate writeAccessPredicate, GraphSerialisable... graphs)`
    - `addGraphs(FederatedAccess access, GraphSerialisable... graphs)`

### [`federatedstore.operation.RemoveGraph`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/store-implementation/federated-store/src/main/java/uk/gov/gchq/gaffer/federatedstore/operation/RemoveGraph.java)
- The method `Builder.setGraphId(final String graphId)` has been removed.
- Use `Builder.graphId(final String graphId)` which has identical behaviour instead.

### [`graph.Graph`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/graph/src/main/java/uk/gov/gchq/gaffer/graph/Graph.java)
- The methods `Builder.graphId`, `Builder.library`, `Builder.view`, `Builder.addHook`, `Builder.addHooks` have all been removed in all forms.
- Instead of using these methods, use `.config()` to set the `graphConfig`.

### [`hdfs.operation.MapReduce`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/library/hdfs-library/src/main/java/uk/gov/gchq/gaffer/hdfs/operation/MapReduce.java)
- The methods `getNumReduceTasks()` and `setNumReduceTasks(Integer)` have been removed.
- Gaffer’s operations that inherit `MapReduce` did not make use of `numReduceTasks`, either setting it to a constant number in the `JobFactory` or using Accumulo to automatically set the number (recommended for performance) and using min/max to keep it within a range. Therefore, `numReduceTasks`, `getNumReduceTasks` and `setNumReduceTasks` have been removed from this interface.

### [`hdfs.operation.AddElementsFromHdfs`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/library/hdfs-library/src/main/java/uk/gov/gchq/gaffer/hdfs/operation/AddElementsFromHdfs.java)
- The methods `getNumReduceTasks()` and `setNumReduceTasks(Integer)` have been removed.
- The number of reduce tasks should not be set.  By default the number of reduce tasks should match the number of tablets.  Use minimum and maximum reduce tasks to specify boundaries for the number of reduce tasks.

### [`hdfs.operation.SampleDataForSplitPoints`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/library/hdfs-library/src/main/java/uk/gov/gchq/gaffer/hdfs/operation/SampleDataForSplitPoints.java)
- The methods `getNumReduceTasks()` and `setNumReduceTasks(Integer)` have been removed.
- These methods were not required as `NumReduceTasks` was always set to 1 in any case.

### [`jobtracker.JobDetail`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/operation/src/main/java/uk/gov/gchq/gaffer/jobtracker/JobDetail.java)
- The constructors which took `userId` as a `String` have been removed.
- Instead, a `User` (`uk.gov.gchq.gaffer.user.User`) should be used in its place. See the [Builder for User](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/user/User.Builder.html).
- `getUserId` and `setUserId` have also been removed. For getting the `UserId`, `getUser().getUserId()` can be used instead. See the [Javadoc for User](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/user/User.html#getUserId--).

### [`jsonserialisation.JSONSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/serialisation/src/main/java/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.java)
- The method `update(final String jsonSerialiserClass, final String jsonSerialiserModules)` has been removed.
- Use `update(final String jsonSerialiserClass, final String jsonSerialiserModules, final Boolean strictJson)` instead. Passing `strictJson` as `null` will result in the same behaviour.

### [`operation.Operation`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/Operation.java)
- The method `asOperationChain(final Operation operation)` has been removed.
- Use `OperationChain.wrap` with the `Operation` instead.

### [`operation.impl.GetWalks`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/impl/GetWalks.java)
- The method `Builder.operation` has been removed.
- Use the vararg method `Builder.addOperations` instead.

### [`operation.impl.SplitStore`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/impl/SplitStore.java)
- This class has been removed.
- It is replaced by `SplitStoreFromFile` which is identical except in name.

### [`operation.impl.join.methods.JoinFunction`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/impl/join/methods/JoinFunction.java)
- The method `join(final Iterable keys, final String keyName, final String matchingValuesName, final Match match, final Boolean flatten)` which was not implemented has been removed.
 
### [`rest.SystemProperty`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/rest-api/common-rest/src/main/java/uk/gov/gchq/gaffer/rest/SystemProperty.java)
- `GRAPH_ID`, `GRAPH_HOOKS_PATH`, `GRAPH_LIBRARY_PATH` and `GRAPH_LIBRARY_CONFIG` have been removed.
- These config options have been removed in favour of providing a `graphConfig` JSON and using `GRAPH_CONFIG_PATH` instead.

### [`rest.service.v2.example.ExamplesFactory`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/rest-api/core-rest/src/main/java/uk/gov/gchq/gaffer/rest/service/v2/example/ExamplesFactory.java)
- This class has been removed.
- It is replaced by `uk.gov.gchq.gaffer.rest.factory.ExamplesFactory`, which can be used instead.

### [`store.StoreProperties`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/StoreProperties.java)
- Store ID (`gaffer.store.id`) and related methods (`getId()` + `setId(String)`) have been removed.
- The ID is instead set in `GraphLibrary` when adding (with `add`) the `StoreProperties`.
- See the [Javadoc for GraphLibrary](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/library/GraphLibrary.html) for more detail.

### [`store.Context`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/Context.java)
- The private constructor `Context(final User user, final Map<String, Object> config, final String jobId)` has been removed; along with the `jobId(String)` method.
- Use `Context(final User user, final Map<String, Object> config)` instead. This does not support supplying the Job ID, this will be set automatically. To get the Job ID use `.getJobId()`.

### [`store.schema.TypeDefinition`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/schema/TypeDefinition.java)
- The method `getSerialiserClass()` has been removed. Instead, use `getSerialiser()` with `.getClass()` and related methods.
- The method `setSerialiserClass(String)` has been removed. Instead, set the Serialiser directly using `setSerialiser(Serialiser)`.

### [`store.schema.Schema`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/schema/Schema.java)
- Schema ID (`gaffer.store.id`) and related methods have been removed. The ID is now defined in `GraphLibrary` when adding the schema.
- `timestampProperty` and related methods have been removed. Instead, this is specified by setting `"config": {"timestampProperty": "timestamp"}` (where `"timestamp"` is the property name to use as a time stamp) in the Schema. _TODO - add link to example schema or more info._
- The method `getVertexSerialiserClass()` has been removed. It can be replaced by calling `vertexSerialiser.getClass()` and converting the result as appropriate, e.g. `getVertexSerialiserClass()` used `SimpleClassNameIdResolver.getSimpleClassName(vertexSerialiser.getClass())`.

### [`store.library.GraphLibrary`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/library/GraphLibrary.java)
- The method `addSchema(final Schema schema)` has been removed. Use `addSchema(final String id, final Schema schema)` instead.
- The method `addProperties(final StoreProperties properties)` has been removed. Use `addProperties(final String id, final StoreProperties properties)` instead.
- Both of these now require the schema ID to be supplied.

### [`store.operation.OperationChainValidator`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/operation/OperationChainValidator.java)
- The method `validateViews(final Operation op, final ValidationResult validationResult, final Schema schemaNotUsed, final Store store)` has been removed. Use `validateViews(final Operation op, final User user, final Store store, final ValidationResult validationResult)` instead, passing `user` as `null` will result in the same behaviour.
- The method `validateComparables(final Operation op, final ValidationResult validationResult, final Schema schemaNotUsed, final Store store)` has been removed. Use `validateComparables(final Operation op, final User user, final Store store, final ValidationResult validationResult)` instead, passing `user` as `null` will result in the same behaviour.

### [`store.operation.handler.named.cache.NamedViewCache`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/store/src/main/java/uk/gov/gchq/gaffer/store/operation/handler/named/cache/NamedViewCache.java)
- The method `deleteNamedView(final String name)` has been removed. Use `deleteNamedView(final String name, final User user)` instead, passing `user` as `null` will result in the same behaviour.
- The method `getNamedView(final String name)` has been removed. Use `getNamedView(final String name, final User user)` instead.
- The method `getAllNamedViews()` has been removed. Use `getAllNamedViews(final User user)` instead.

### [`types.IntegerFreqMap`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/type/src/main/java/uk/gov/gchq/gaffer/types/IntegerFreqMap.java)
- This class has been removed.
- Use `uk.gov.gchq.gaffer.types.FreqMap` instead, this is identical except for using Long rather than Integer.

#### [`types.function.IntegerFreqMapAggregator`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/type/src/main/java/uk/gov/gchq/gaffer/types/function/IntegerFreqMapAggregator.java)
- This class has been removed.
- Use `uk.gov.gchq.gaffer.types.function.FreqMapAggregator` instead.

#### [`serialisation.IntegerFreqMapSerialiser`](https://github.com/gchq/Gaffer/blob/gaffer2-1.21.1/core/type/src/main/java/uk/gov/gchq/gaffer/serialisation/IntegerFreqMapSerialiser.java)
- This class has been removed.
- Use `uk.gov.gchq.gaffer.serialisation.FreqMapSerialiser` instead.
