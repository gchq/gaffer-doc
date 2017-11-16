${HEADER}

${CODE_LINK}

In Gaffer there is one central caching service, meaning the same cache is used across all Gaffer internals, namely named operations, the job tracker and FederatedStore.

There are three types of cache we can use, JCS (Java Caching System), HazelCast or HashMap. 

To implement a cache service, first we must specify the cache service class in our store.properties:
```
gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.JcsCacheService

# Optionally provide custom cache properties
gaffer.cache.config.file=/path/to/config/file
```

If using an external cache service (anything found in the cache library) be sure to include the library as a dependency:
```xml
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>jcs-cache-service</artifactId>
    <version>{gaffer.version}</version>
</dependency>
```

Once we have specified the the type of cache to use, Gaffer can use this cache service to store and get data to serve it more efficiently.

Please note, by default no service is loaded so if you want to make use of the cache, be sure to specify the service class in your store.properties file.

If using the Hazelcast instance of the Cache service be aware that once the last node shuts down, all data will be lost. This is due to the data being held in
memory in a distributed system.