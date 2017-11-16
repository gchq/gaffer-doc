${HEADER}

${CODE_LINK}

In Gaffer there is one central cache service, meaning the same cache is used across all Gaffer internals, namely named operations, the job tracker and FederatedStore.

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
