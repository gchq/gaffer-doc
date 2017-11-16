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

If we wanted to use the JCS cache service for example, we would also need to add the dependency on the jcs-cache-service in our pom:
```xml
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>jcs-cache-service</artifactId>
    <version>[gaffer.version]</version>
</dependency>
```

Once we have specified the the type of cache to use, Gaffer can use this cache service to store and get data to serve it more efficiently.
