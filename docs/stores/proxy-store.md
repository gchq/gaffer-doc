

Proxy Store
============

The `ProxyStore` implementation is simply a Gaffer store which delegates all
operations to a Gaffer REST API.

To create a `ProxyStore` you just need to provide a host, port and context
root. This can be done via the `ProxyStore.Builder`:

```java
Graph graph = new Graph.Builder()
    .store(new ProxyStore.Builder()
            .graphId(uniqueNameOfYourGraph)
            .host("localhost")
            .port(8080)
            .contextRoot("rest/v1")
            .build())
    .build();
```

You can then write your queries in Java and the `ProxyStore` will convert
them into JSON and execute them over the REST API.

These are the full set of configurable properties:

```properties
gaffer.host
gaffer.port
gaffer.context-root
gaffer.jsonserialiser.class

# Timeouts specified in milliseconds
gaffer.connect-timeout
gaffer.read-timeout
```
