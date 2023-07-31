# Stores Guide

A Gaffer Store represents the backing database responsible for storing (or facilitating access to) a graph. Ordinarily a Store provides backing for a single graph. Stores which provide access to other stores can support multiple graphs. So far only the [Federated Store](federated.md) supports this.

Gaffer currently supplies the following store implementations:

- [Map Store](map.md) - Simple in-memory store
- [Accumulo Store](accumulo.md) - [Apache Accumulo](https://accumulo.apache.org/) backed store
- [Proxy Store](proxy.md) - Delegates/forwards queries to another Gaffer REST
- [Federated Store](federated.md) - Federates queries across multiple graphs

## Caches

Gaffer comes with three cache implementations:

- `HashMapCacheService` - Uses a Java `HashMap` as the cache data store. [See Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/cache/impl/HashMapCacheService.html).
- `JcsCacheService` - Uses Apache Commons JCS for the cache data store. [See Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/cache/impl/JcsCacheService.html).
- `HazelcastCacheService` - Uses Hazelcast for the cache data store. [See Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/cache/impl/HazelcastCacheService.html).

The `HashMap` cache is not persistent. If using the Hazelcast instance of the Cache service be aware that once the last node shuts down, all data will be lost. This is due to the data being held in memory in a distributed system.

For information on implementing caches, see [the cache developer docs page](../../dev/cache.md).

### Cache configuration

In order for the cache service to run you must select your desired implementation. You do this by adding a line to the `store.properties` file:
```
gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.HashMapCacheService
```

If needed, you can specify a configuration file with properties for the cache itself:
```
gaffer.cache.config.file=/path/to/file
```


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