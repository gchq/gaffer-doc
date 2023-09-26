# Gaffer Schemas

One of the main differences between Gaffer and other graph database tools are its schemas. In Gaffer
JSON based schemas need to be written upfront for it to understand how to load and treat the data in
the graph. These schemas define all aspects of the nodes and edges in the graph, and can even be
used to automatically do basic analysis or aggregation on queries and ingested data.

You can kind of think of the schema as sort of a filter or validator for the incoming data. A
given bit of data must conform with part of the schema or it will simply be ignored as it doesn't
fit the structure of the graph.

## Elements Schema

All distinct bits of data in a Gaffer graph (e.g. nodes and edges) are referred to as 'elements'.
The structure and properties of these graph elements are defined in the elements schema. The general
format of an element schema are two lists; one of the `"edges"` and the other of the `"entities"`
like the following:

!!! example "Basic elements syntax"

    ```json
    {
        "edges": {
            "Edge": {
                "source": "type",
                "destination": "type",
                "directed": "true",
                "properties": {
                    "property": "type"
                }
            }
        },
        "entities": {
            "Node": {
                "description": "A Node",
                "vertex": "type",
                "properties": {
                    "property": "type"
                }
            }
        }
    }
    ```

As you can see there are a few fields for both the example `"Edge"` and `"Node"`, many of these
require a type as their value (discussed in the [next section](#types-schema)) which are
essentially handlers or object types for the value associated with the field.

For an `edge` the following fields are required:

- `source` - A user defined type for the source node the edge originated from.

- `directed` - Boolean true or false to define if the edge is directed or not. When an Edge is
    undirected in Gaffer, it is treated as if the relationship was bidirectional and the vertices of
    the edge do not have an authoritative source and destination.

    !!! note ""
        The type here, `"true"` or `"false"` needs to be defined in the types schema using a class
        that evaluates to it. This is demonstrated in the [example
        deployment](../development-guide/example-deployment/writing-the-schema.md) document.

- `destination` - A user defined type for the destination node the edge goes to.

For an `entity` only one field is required:

- `vertex` - A user defined type for the node/vertex.

!!! note ""
    The example includes some of the common optional fields too such as a `"properties"` list and
    `"description"`.

## Types Schema

Following on from the elements schema, the other necessary schema needed for a Gaffer deployment is
the types schema.  The types schema allows user defined types for all elements in the graph. It can
also demonstrate the power of Gaffer as it allows for custom functions classes to be used on the
types; however, this can make it quite complex to write a full schema for a graph.

!!! example "Example types syntax"

    ```json
    {
        "types": {
            "type.string": {
                "description": "A basic type to hold the string value of an element",
                "class": "java.lang.String"
            },
            "type.int": {
                "description": "A basic type to hold the int value of an element",
                "class": "java.lang.Integer"
            }
        }
    }
    ```