# What is Python and how is it used in Gaffer?

Python is a popular high level programming language that's seen massive popularity specifically in the data science and educational programming areas. Python is an interpreted general purpose language that's dynamically typed and garbage collected. For more info on Python please reference the official Python docs.

## Python in Gaffer

Whilst Gaffer is written primarily in Java a Python interface has been provided so that you can programmatically access Gaffer functionality with Python, this can be accessed via the gafferpy library located in the gaffer-tools repository. This provides a Python 3.6+ compatible import that will allow you to speak directly to the Gaffer REST API, it supports persistent connections to Gaffer, connection via SSL and the associated python functionality to interact and extend Gaffer operations.

Inside the gaffer-tools library you'll find a set of examples that show how you can interact with Gaffer, here is an example taken from that set:

```py
def get_schema(gc):
    # Get Schema
    result = gc.execute_get(
        g.GetSchema()
    )

    print('Schema:')
    print(result)
    print()
```

In this simple example gc passed in as a parameter is the gaffer_connector which orchestrates connection into Gaffer and g is the gafferpy's representation of the gaffer operations. This executes a get request into Gaffer to retrieve the current schema.

A link to the gaffer tools repository can be found here: [GCHQ/Gaffer-Doc](https://github.com/gchq/gaffer-doc)