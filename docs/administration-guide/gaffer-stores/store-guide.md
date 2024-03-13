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

### All General Store Properties

The properties in bold are set based on the type of Gaffer Store, for how to configure these see the [respective page for each store type](#stores-guide).

| Property                 | Default | Description                             |
| ------------------------ | ------- | --------------------------------------- |
| **`gaffer.store.class`** | None | Class Name String to set Gaffer Store class |
| `gaffer.store.schema.class` | `gaffer.store.schema.Schema` | Class Name String to set class to use for serialising Schemas |
| **`gaffer.store.properties.class`** | `uk.gov.gchq.gaffer.store.StoreProperties` | Class Name String to set Gaffer Store Properties class |
| `gaffer.store.operation.declarations` | None | Path to [Operation Declarations](../../development-guide/example-deployment/project-setup.md#operations-declarations) files (separate multiple files with commas) |
| `gaffer.store.operation.declarations.json` | None | JSON String containing [Operation Declarations](../../development-guide/example-deployment/project-setup.md#operations-declarations) |
| `gaffer.store.job.tracker.enabled` | False | Controls if the Job Tracker is to be used |
| `gaffer.store.job.executor.threads` | 50 | Number of threads to be used by the Job Tracker [ExecutorService](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/commonutil/ExecutorService.html) |
| `gaffer.store.admin.auth` | None | String for Auth to associate with Administrator Users |
| `gaffer.store.reflection.packages` | None | Reflection Packages to add to Koryphe [ReflectionUtil](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/util/ReflectionUtil.html) |
| `gaffer.named.operation.nested` | False | Controls if NamedOperations are allowed to reference/nest other NamedOperations |
| `gaffer.serialiser.json.class` | `uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser` | Class Name String for setting a custom class extending [JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html) |
| `gaffer.serialiser.json.modules` | None | Class Name String for registering classes implementing [JSONSerialiserModules](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiserModules.html) (separate multiple modules with commas) |
| `gaffer.serialiser.json.strict` | False | Controls if unknown fields should be ignored when serialising JSON (sets [Jackson FAIL_ON_UNKNOWN_PROPERTIES](https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/DeserializationFeature.html#FAIL_ON_UNKNOWN_PROPERTIES) internally) |
| `gaffer.error-mode.debug` | False | Controls technical debugging by methods calling [`DebugUtil`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/commonutil/DebugUtil.html) |
| `gaffer.cache.service.class` | None | Fully-qualified class name of a [Gaffer cache](#cache-service) implementation |
| `gaffer.cache.config.file` | None | Config file to use with a [Gaffer cache](#cache-service) implementation |
| `gaffer.cache.service.default.suffix` | `graphId` | String to use as the default [cache suffix](#suffixes) |
| `gaffer.cache.service.federated.store.suffix` | None | String to override the default [suffix](#suffixes) used by Federated Store graph cache |
| `gaffer.cache.service.named.operation.suffix` | None | String to override the default [suffix](#suffixes) used by Named Operation cache |
| `gaffer.cache.service.job.tracker.suffix` | None | String to override the default [suffix](#suffixes) used by Job Tracker cache |
| `gaffer.cache.service.named.view.suffix` | None | String to override the default [suffix](#suffixes) used by Named View cache |

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

In order for the cache service to run you must select your desired implementation. You do this by adding a line to the `store.properties` file:
```
gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.HashMapCacheService
```

Both the JCS and Hazelcast caches require configuration files.
In the case of a JCS file this is a [ccf file](https://commons.apache.org/proper/commons-jcs/BasicJCSConfiguration.html) 
while for Hazelcast this is commonly a [XML/YAML file](https://docs.hazelcast.com/imdg/4.2/configuration/understanding-configuration#static-configuration).

You should then specify the location of any configuration file(s) in your store.properties file as follows:
```
gaffer.cache.config.file=/path/to/file
```

#### Suffixes

To prevent conflicts between different graphs which share the same cache service, by default the cache entries for each graph are appended with a suffix. The default value of this suffix is the Graph's ID.
You can manually specify the default suffix to use for all types of cache by setting the store property `gaffer.cache.service.default.suffix` to the desired String.

!!! info
    By default the cache entry is named the type of cache followed by `_` and the `graphId`. For example, when using a Federated Store with multiple sub-graphs, named `graphA`, `graphB` and `graphC`, for Named Operations there will be three cache entries called `NamedOperationCache_graphA`, `NamedOperationCache_graphB` and `NamedOperationCache_graphC`.

In the past (Gaffer versions `1.x`) these suffixes did not exist, and all graphs used the same cache entries. If you want two or more graphs to share the same cache entry, then configure them to use the same suffix.

An example where you might want to share the same cache entry is when using [Named Operations](../named-operations.md) and a [Federated Store](federated-store.md).
Adding a Named Operation to a Federated Store won't make it available to sub-graphs (when using a `FederatedOperation` to execute it) unless the sub-graphs share the same Named Operation cache as the Federated Store.

If you only want a certain kind of cache entry to be shared, e.g. only share Named Operations, then set the suffix specific to that cache entry. See the [table above for all the properties for this](#all-general-store-properties).
You could also set the default cache suffix to share everything and set a specific suffix to be different and therefore not shared.

## Configuring customisable Operations

Some operations are not available by default and you will need to manually configure them.

These customisable operations can be added to your Gaffer graph by providing config in one or more operation declaration JSON files.

### Named Operations
Named Operations depends on the Cache service being active at runtime. See [Caches](#caches) above for how to enable these.

### ScoreOperationChain

Variables:

- opScores - required map of operation scores. These are the operation score values.
- authScores - required map of operation authorisation scores. These are the maximum scores allowed for a user with a given role.
- scoreResolvers - required (if using NamedOperations) list of score resolvers. These map operation class to its respective score resolver.


??? example "Example operation scores map"

    ```json
    { 
      "opScores": {
        "uk.gov.gchq.gaffer.operation.Operation": 1,
        "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects": 0,
        "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements": 3
      }
    }
    ```

??? example "Example operation authorisation scores map"

    ```json
    {
      "authScores": {
        "User": 4,
        "EnhancedUser": 10,
        "OtherUser": 6
      }
    }
    ```

??? example "Example operation declarations JSON file"

    ```json
    {
      "operations": [
        {
          "operation": "uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain",
          "handler": {
            "class": "uk.gov.gchq.gaffer.store.operation.handler.ScoreOperationChainHandler",
            "opScores": {
              "uk.gov.gchq.gaffer.operation.Operation": 2,
              "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects": 0
            },
            "authScores": {
              "User": 4,
              "EnhancedUser": 10
            },
            "scoreResolvers": {
              "uk.gov.gchq.gaffer.named.operation.NamedOperation": {
                "class": "uk.gov.gchq.gaffer.store.operation.resolver.named.NamedOperationScoreResolver"
              }
            }
          }
        }
      ]
    }
    ```