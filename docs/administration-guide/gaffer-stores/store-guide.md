# Stores Guide

A Gaffer Store represents the backing database responsible for storing (or facilitating access to) a graph. Ordinarily a Store provides backing for a single graph. Stores which provide access to other stores can support multiple graphs. So far only the [Federated Store](federated-store.md) supports this.

Gaffer currently supplies the following store implementations:

- [Map Store](map-store.md) - Simple in-memory store
- [Accumulo Store](accumulo-store.md) - [Apache Accumulo](https://accumulo.apache.org/) backed store
- [Proxy Store](proxy-store.md) - Delegates/forwards queries to another Gaffer REST
- [Federated Store](federated-store.md) - Federates queries across multiple graphs

## Store Properties

Stores are configured using `key=value` style properties stored in a `store.properties` file.
There are general properties which apply to all Stores and per Store properties for configuring specific behaviour.
Most properties are optional and don't need to be specified or configured, default values will be used.

!!! tip
    Please see the [reference guide](../../reference/store-properties/common.md) for a full list of properties.

## Caches

Gaffer comes with three cache implementations:

- `HashMapCacheService` - Uses a Java `HashMap` as the cache data store. [See Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/cache/impl/HashMapCacheService.html).
- `JcsCacheService` - Uses Apache Commons JCS for the cache data store. [See Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/cache/impl/JcsCacheService.html).
- `HazelcastCacheService` - Uses Hazelcast for the cache data store. [See Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/cache/impl/HazelcastCacheService.html).

The `HashMap` cache is not persistent. If using the Hazelcast instance of the Cache service be aware that once the last node shuts down, all data will be lost. This is due to the data being held in memory in a distributed system.

For information on implementing caches, see [the cache developer docs page](../../development-guide/project-structure/components/cache.md).

### Configuration

Cache configuration includes selecting which cache service to use and optionally specifying a cache suffix.

#### Cache Service

In order for the cache service to run you must select your desired implementation. You can set the default implementation by adding a line to the `store.properties` file:

```properties
gaffer.cache.service.default.class=uk.gov.gchq.gaffer.cache.impl.HashMapCacheService
```

Both the JCS and Hazelcast caches require configuration files.
In the case of a JCS file this is a [ccf file](https://commons.apache.org/proper/commons-jcs/BasicJCSConfiguration.html)
while for Hazelcast this is commonly a [XML/YAML file](https://docs.hazelcast.com/imdg/4.2/configuration/understanding-configuration#static-configuration).

You should then specify the location of any configuration file(s) in your store.properties file as follows:

```properties
gaffer.cache.config.file=/path/to/file
```

Additionally, the cache service implementation to use for the Job Tracker, Named Views and Named Operations can be set independently (see the [reference guide](../../reference/store-properties/common.md#cache-properties) for details).
The default service should still be specified, unless all optional cache class properties are given.
When cache service implementations have been set independently, but the same implementation class used, this will result in multiple caches of the same kind being created.
Setting the cache service independently is intended to allow different cache implementations to be used at the same time. Depending on the implementation, using multiple instances of the same implementation may not work correctly.

!!! note
    Currently it is not possible to specify different cache config files if multiple different cache implementations have been used. The same config file property will be passed to all implementations.

#### Suffixes

To prevent conflicts between different graphs which share the same cache service, by default the cache entries for each graph are appended with a suffix. The default value of this suffix is the Graph's ID.
You can manually specify the default suffix to use for all types of cache by setting the store property `gaffer.cache.service.default.suffix` to the desired String.

!!! info
    By default the cache entry is named the type of cache followed by `_` and the `graphId`. For example, when using a Federated Store with multiple sub-graphs, named `graphA`, `graphB` and `graphC`, for Named Operations there will be three cache entries called `NamedOperationCache_graphA`, `NamedOperationCache_graphB` and `NamedOperationCache_graphC`.

In the past (Gaffer versions `1.x`) these suffixes did not exist, and all graphs used the same cache entries. If you want two or more graphs to share the same cache entry, then configure them to use the same suffix.

An example where you might want to share the same cache entry is when using [Named Operations](../named-operations.md) and a [Federated Store](federated-store.md).
Adding a Named Operation to a Federated Store won't make it available to sub-graphs (when using a `FederatedOperation` to execute it) unless the sub-graphs share the same Named Operation cache as the Federated Store.

If you only want a certain kind of cache entry to be shared, e.g. only share Named Operations, then set the suffix specific to that cache entry. See the [reference guide](../../reference/store-properties/common.md#cache-properties) on store properties for how to configure these.
You could also set the default cache suffix to share everything and set a specific suffix to be different and therefore not shared.

## Configuring customisable Operations

Some operations are not available by default and you will need to manually configure them.

These customisable operations can be added to your Gaffer graph by providing config in one or more [operation declaration JSON files](../gaffer-config/config.md#operations-declarations-json).

### Named Operations

Named Operations depends on the Cache service being active at runtime. See [Caches](#caches) above for how to enable these.

### ScoreOperationChain

Operation scores determine whether a particular user has the required permissions to execute a given OperationChain. See [Operation Scores](../../administration-guide/operation-score.md) for how to enable and configure these.
