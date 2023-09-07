# The API

This section covers the currently available APIs that can be used to interact with a Gaffer graph.

## Swagger Rest API

Most of the interaction with a deployed Gaffer graph covered in this documentation will be through
the rest API. When deployed, the rest API will be available at a configurable address, accessing
this address via a browser brings up a [Swagger UI](https://swagger.io/) with various GET and POST
requests predefined for you to start using.

Most of the GET requests simply retrieve information about the graph which can be useful to ensure
your config files have been loaded correctly and the graph is operating normally. The POST requests
allow you to interact with the graph by loading data and running queries.

The main POST request end point you will use is `/graph/operations/execute`, this will ingest raw
JSON to carry out operations on the Gaffer graph. Gaffer provides many pre built operations that are
available to use and can be chained together for more complex use cases. However be aware, that
operation chains are usually highly specific to the data and results you wish to extract from the
graph so please refer to the reference guide on [Gaffer
operations](../../reference/operations-guide/operations.md)

!!! example "Example operation chain using rest API"

    The following operation chain gets all the elements in the graph then will count them and
    return the total.

    ```json
    {
        "class": "OperationChain",
        "operations": [
            {
                "class": "GetAllElements"
            },
            {
                "class": "Count"
            }
        ]
    }
    ```

## Python API

Along side the rest API there also exists a Python API. Commonly referred to as `gafferpy` this API
enables similar querying capabilities as the rest API but from Python code. Fundamentally it wraps
the rest API to use  the same JSON under the hood this means you should be able to access any
features or end points available in the main rest API.

To get started with `gafferpy` simply import the module and connect to an existing graph.

```python
from gafferpy import gaffer
from gafferpy import gaffer_connector
g_connector = gaffer_connector.GafferConnector("http://localhost:8080/rest/latest")
```

Then once connected you can access and run the same endpoints and operations as you would via the
usual rest API.

!!! example "Example operation chain using `gafferpy`"

    The following operation chain gets all the elements in the graph then will count them and
    store the total in the variable `count`.

    ```python
    count = g_connector.execute_operation_chain(
        operation_chain = gaffer.OperationChain(
            operations=[
                gaffer.GetAllElements(),
                gaffer.Count()
            ]
        )
    )
    ```

## Java API

As Gaffer is written in Java there is native support to allow use of all its public classes. Using
Gaffer via the Java interface does differ from the rest API and `gafferpy` but is fully featured
with extensive [Javadocs](https://gchq.github.io/Gaffer/overview-summary.html).

!!! example "Example operation chain using Java"

    The following operation chain gets all the elements in the graph then will count them and
    store the result in a `Long`.

    ```java
    OperationChain<Long> countAllElements = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Count<>())
        .build();

    Long result = graph.execute(countAllElements, user);
    ```
