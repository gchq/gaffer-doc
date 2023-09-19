# Using the Python API

This section covers an overview of the python API extension for Gaffer to
demonstrate how to get up and running to perform queries from Python code on an
existing running graph.

!!! tip
    Please see the handy introduction to [Python](../gaffer-basics/what-is-python.md)
    if you are new to the language.

## What is the Python Extension?

Commonly referred to as `gafferpy` this is an API to gaffer that provides
similar querying capabilities to the rest API but from Python. Fundamentally it
wraps the rest API to use the same JSON under the hood this means you should be
able to access almost any features or end points available in the main rest API.

## Installation

Currently there isn't a release of `gafferpy` on pypi or other pip repository;
however, the source code can still be cloned from the [git repository](https://github.com/gchq/gaffer-tools/tree/develop/python-shell)
and installed via pip. Please see the readme in the `gafferpy` repository for
full instructions.

## How to Query a Graph

To get started with `gafferpy` you will need to import the module and connect to
an existing graph, the connection should be the same address as where the rest
API is running.

```python
from gafferpy import gaffer
from gafferpy import gaffer_connector
g_connector = gaffer_connector.GafferConnector("http://localhost:8080/rest/latest")
```

Once connected you can access and run the same endpoints and operations as you
would via the usual rest API but via their python classes. The endpoints are
accessed via the `GafferConnector` to allow you executing Operation chains to
perform queries on the graph.

!!! note
    There may be of the features of the full rest API not be present in
    `gafferpy` as always check the [reference guide](../../reference/intro.md)
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
    An Operation chain can be ran using the `execute_operation_chain()` function.
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
provided by a `View`. There are more detail on both [filtering](../query/gaffer-syntax/filtering.md)
and [operations](../query/gaffer-syntax/operations.md) on their respective pages.
