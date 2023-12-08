# What is Python and how is it used in Gaffer?

Python is a popular high level programming language that's seen massive popularity specifically in the data science and educational programming areas. Python is an interpreted general purpose language that's dynamically typed and garbage collected. For more info on Python please reference the [official Python docs](https://www.python.org/).

## Python in Gaffer

Whilst Gaffer is written primarily in Java a Python interface has been provided so that you can programmatically access Gaffer functionality with Python, this can be accessed via the gafferpy library located in the [gafferpy repository](https://github.com/gchq/gafferpy).
This provides a Python 3.6+ compatible import that will allow you to speak directly to the Gaffer REST API, it supports persistent connections to Gaffer, connection via SSL and the associated Python functionality to interact with available Gaffer operations.

!!! note
    See the page on [using the Python API](../apis/python-api.md) in gaffer for further information.

Inside the gafferpy library you'll find a set of examples that show how you can interact with Gaffer, here is a basic example of using gafferpy:

!!! example ""
    This executes a get request into Gaffer to retrieve the current schema.

    ```py
    from gafferpy import gaffer as g
    from gafferpy import gaffer_connector

    # Establish connection
    gc = gaffer_connector.GafferConnector("http://localhost:8080/rest/latest")

    # Get Schema
    schema = gc.execute_get(g.GetSchema())

    # Print Schema
    print("Schema:\n{0}\n".format(schema))
    ```

In this simple example you can see the use of a `gaffer_connector`; the purpose of this is to orchestrate the connection to a Gaffer REST endpoint.
The main `gaffer` Python module (usually imported as `g`) allows access to various functions to run Gaffer operations. This connection works by serialising the Python code into JSON and then transferring this to be deserialised and ran in Gaffer.

!!! tip
    A link to the gafferpy repository can be found here: [gchq/gafferpy](https://github.com/gchq/gafferpy)
