# Using the Python API

This section covers an overview of the Python API extension for Gaffer.
Getting this extension up and running allows users to perform queries using Python code on existing graphs.

!!! tip
    Please see the handy introduction to [Python](../gaffer-basics/what-is-python.md)
    if you are new to the language.

## What is the Python Extension?

Commonly referred to as `gafferpy` this API provides
similar querying capabilities to the REST API using Python. Fundamentally, it
wraps the REST API allowing users to access almost all the features or end
points available in the main REST API using Python rather than JSON.

## Installation

Currently there isn't a release of `gafferpy` on pypi or other pip repository;
however, the source code can still be cloned from the [git repository](https://github.com/gchq/gafferpy/tree/main)
and installed via pip. Please see the [README](https://github.com/gchq/Gafferpy#readme) in the `gafferpy` repository for
full instructions.

## How to Query a Graph

To get started with `gafferpy` you will need to import the module and connect to
an existing graph. The connection should be the same address as where the REST
API is running.

```python
from gafferpy import gaffer
from gafferpy import gaffer_connector
g_connector = gaffer_connector.GafferConnector("http://localhost:8080/rest/latest")
```

Once connected you can access and run the same endpoints and operations as you
would using the usual REST API but via their Python classes. The endpoints are
accessed via the `GafferConnector` where users can then query graphs by executing Operation Chains.

!!! note
    Some of the features of the full REST API may not be present in
    `gafferpy` so always check the [reference guide](../../reference/intro.md)
    first.

!!! example ""
    A simple Operation can be called via the `execute_operation()` function. As
    an example, the following will get all the elements in a graph:

    ```python
    elements = g_connector.execute_operation(
        operation =  gaffer.GetAllElements()
    )
    ```

!!! example ""
    An Operation Chain can be run using the `execute_operation_chain()` function.
    As an example, the following will get all the elements in a graph then
    count them.

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

Along with running Operations you can of course optionally apply some filtering
using a `View`. There is more detail on both [filtering](../query/gaffer-syntax/filtering.md)
and [operations](../query/gaffer-syntax/operations.md) on their respective pages.
