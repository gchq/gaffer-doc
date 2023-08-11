# Proxy Store

The proxy store is simply a Gaffer store implementation which delegates all operations to a Gaffer REST API. See [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/proxystore/package-summary.html).

## Using a proxy store with Gaffer REST

These are the full set of configurable properties which can be used in `.properties` config file with Gaffer REST:

- `gaffer.host`: Hostname or IP Address of the Gaffer REST API
- `gaffer.port`: Port for the Gaffer REST API
- `gaffer.context-root`: The path to use as the context root
- `gaffer.connect-timeout`: Connect timeout in milliseconds
- `gaffer.read-timeout`: Read timeout in milliseconds

For more information on the timeouts, see the docs for the [Jersey client](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/appendix-properties.html#d0e22288) which is used internally.

??? example "Example `store.properties` config file"

    ```properties
    gaffer.store.class=uk.gov.gchq.gaffer.proxystore.ProxyStore
    gaffer.host=example.hostname
    gaffer.port=8080
    gaffer.context-root=/rest/v2
    ```

## Using a `ProxyStore` from Java

To create a `ProxyStore` you just need to provide a host, port and context root. This can be done via the `ProxyStore.Builder`:
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

You can then write your queries in Java and the `ProxyStore` will convert them into JSON and execute them over the REST API.