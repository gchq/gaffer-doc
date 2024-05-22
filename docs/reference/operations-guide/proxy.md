# Proxy Store Operations

These Operations are used with the [Proxy Store](../../administration-guide/gaffer-stores/proxy-store.md).
They are not enabled by default and will need to be added as additional operations using an [Operations
Declarations](../../development-guide/example-deployment/project-setup.md#operations-declarations)
configuration file. An example file which could be used to enable both Proxy Store Operations is given
below:

```json title="proxyOperationsDeclarations.json"
{
    "operations": [
        {
            "operation": "uk.gov.gchq.gaffer.proxystore.operation.GetProxyUrl",
            "handler": {
                "class": "uk.gov.gchq.gaffer.proxystore.operation.handler.GetProxyUrlHandler"
            }
        },
        {
            "operation": "uk.gov.gchq.gaffer.proxystore.operation.GetProxyProperties",
            "handler": {
                "class": "uk.gov.gchq.gaffer.proxystore.operation.handler.GetProxyPropertiesHandler"
            }
        }
    ]
}
```

## GetProxyUrl

Returns the URL of the remote Gaffer REST API which the Proxy Store is configured to forward requests to.
[Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/proxystore/operation/GetProxyUrl.html)

### Example

=== "Java"

    ``` java
    final GetProxyUrl getProxyUrl = new GetProxyUrl.Builder()
            .build();
    ```

=== "JSON"

    ``` json
    {
    "class" : "GetProxyUrl"
    }
    ```

=== "Python"

    ``` python
    g.GetProxyUrl()
    ```

Results:

=== "Java"

    ``` java
    "http://gaffer-rest:8080/rest"
    ```

=== "JSON"

    !!! note "Raw result"

        This operation returns a raw result instead of JSON.

    ```
    http://gaffer-rest:8080/rest
    ```

## GetProxyProperties

Returns the proxy specific properties of a Proxy Store. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/proxystore/operation/GetProxyProperties.html)

### Example

=== "Java"

    ``` java
    final GetProxyProperties getProxyProperties = new GetProxyProperties.Builder()
            .build();
    ```

=== "JSON"

    ``` json
    {
    "class" : "GetProxyProperties"
    }
    ```

=== "Python"

    ``` python
    g.GetProxyProperties()
    ```

Results:

=== "Java"

    ``` java
    {
        "gaffer.context-root": "/rest",
        "gaffer.host": "gaffer-rest",
        "gaffer.port": "8080",
        "URL_inferred": "http://gaffer-rest:8080/rest",
        "gaffer.read-timeout": "10000",
        "gaffer.connect-timeout": "10000"
    }
    ```

=== "JSON"

    ``` json
    {
        "gaffer.context-root": "/rest",
        "gaffer.host": "gaffer-rest",
        "gaffer.port": "8080",
        "URL_inferred": "http://gaffer-rest:8080/rest",
        "gaffer.read-timeout": "10000",
        "gaffer.connect-timeout": "10000"
    }
    ```
