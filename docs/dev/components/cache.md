# Cache

The [cache module](https://github.com/gchq/Gaffer/tree/master/core/cache) contains the `CacheServiceLoader` which is started when the store is initialised. The cache service loader can be called when a component needs access to short term data storage.
To get access to the cache service you need to call:
```java
CacheServiceLoader.getService();
```

By default, there is no service loaded so if you're using a component that makes use of the `CacheServiceLoader`, be sure to specify the service class in the `store.properties` file.
See the [Stores reference guide](../../reference/stores-guide/stores.md#cache-configuration) for configuration info.

If using an external cache service (anything found in the cache library) be sure to include the library as a dependency:
```xml
<dependency>
   <groupId>uk.gov.gchq.gaffer</groupId>
   <artifactId>jcs-cache-service</artifactId>
   <version>${gaffer.version}</version>
</dependency>
```

When run in a servlet context, the `CacheServiceLoader` should be shutdown gracefully by the `ServletLifecycleListener` found in the REST package. Do not trust the shutdown hook in a servlet context.
If running outside a servlet environment, you can either call shutdown on the cache service manually or use the shutdown hook upon initialisation of the cache service loader.

For information on Gaffer caches and cache configuration, see [the cache section of the Stores Guide](../../reference/stores-guide/stores.md#caches).
