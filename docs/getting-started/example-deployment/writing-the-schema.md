# Writing the Schema

In Gaffer JSON based schemas need to be written upfront to model and understand how to load and
treat the data in the graph. These schemas define all aspects of the nodes and edges in the graph,
and can even be used to automatically do basic analysis or aggregation on queries and ingested data.

For reference, this guide will use the same CSV data set from the [project
setup](project-setup.md/#the-example-graph) page.

=== "Table"
    | _id | name  | age | lang | _labels  | _start | _end | _type   | weight |
    |-----|-------|-----|------|----------|--------|------|---------|--------|
    | v1  | marko | 29  |      | Person   |        |      |         |        |
    | v2  | lop   |     | java | Software |        |      |         |        |
    | e1  |       |     |      |          | v1     | v2   | Created | 0.4    |

=== "CSV"
    ```csv
    _id,name:String,age:Int,lang:String,_labels,_start,_end,_type,weight:Float
    v1,marko,29,,Person,,,,
    v2,lop,,java,Software,,,,
    e1,,,,,v1,v2,Created,0.4
    ```

## Elements Schema

In Gaffer an element refers to any object in the graph, i.e. your nodes (vertexes) and edges. To set
up a graph we need to tell Gaffer what objects are in the graph and the properties they have. The
standard way to do this is a JSON config file in the schema directory. The filename can just be
called something like `elements.json`, the name is not special as all files under the `schema`
directory will be merged into a master schema but its still recommended to use an appropriate name.

As covered in the [Gaffer basics](../basics.md), to write a schema you can see that there are some
required fields, but largely a schema is highly specific to your input data. To give an idea of how
a data set could be modelled we will continue with the example CSV data.

Starting with the `entities` from the example, we can see there will be two distict types of nodes
in the graph, one representing a `Person` and another for `Software`. These can be added into the
schema to give something like the the following:

!!! info

    The types here such as `id.person.string` will be covered later in the
    guide.

```json
{
    "entities": {
        "Person": {
            "description": "Entity representing a person vertex",
            "vertex": "id.person.string"
        },
        "Software": {
            "description": "Entity representing a software vertex",
            "vertex": "id.software.string"
        }
    }
}
```

From the basic schema you can see that we have added two entity types for the graph. For now, each
`entity` just contains a short description and a type associated to the `vertex` key. The type here
is just a place holder but it has been named appropriately as it's assumed that we will just use the
string representation of the node's id (this will be defined in the `types.json` later in the
guide).

Expanding on the basic schema we will now add the `edges` to the graph. As the example graph is
small we only need to add one edge - the `Created` edge. This is a directed edge that connects a
`Person` to a `Software` and can be defined as the following.

```json
{
    "edges": {
        "Created": {
            "source": "id.person.string",
            "destination": "id.software.string",
            "directed": "true"
        }
    },
    "entities": {
        "Person": {
            "description": "Entity representing a person vertex",
            "vertex": "id.person.string"
        },
        "Software": {
            "description": "Entity representing a software vertex",
            "vertex": "id.software.string"
        }
    }
}
```

As discussed in the [basics guide](../basics.md), edges have some mandatory fields. Starting with
the `source` and `destination` fields, these must match the types associated with the vertex field
in the relevant entities. From the example, we can see that the source of a `Created` edge is a
`Person` so we will use the placeholder type we set as the `vertex` field which is
`id.person.string`. Similarly the destination is a `Software` node so we will use its placeholder of
`id.software.string`.

We must also set whether an edge is directed or not, in this case it is as, only a person can create
software not the other way around. To set this we will use the `true` type, but note that this is a
placeholder and must still be defined in the types.json.

Continuing with the example, the nodes and edges also have some properties associated with each such
as name, age etc. These can also be added to the schema using a properties map to result in the
extended schema below.

```json
{
    "edges": {
        "Created": {
            "source": "id.person.string",
            "destination": "id.software.string",
            "directed": "true",
            "properties": {
                "weight": "property.float"
            }
        }
    },
    "entities": {
        "Person": {
            "description": "Entity representing a person vertex",
            "vertex": "id.person.string",
            "properties": {
                "name": "property.string",
                "age": "property.integer"
            }
        },
        "Software": {
            "description": "Entity representing a software vertex",
            "vertex": "id.software.string",
            "properties": {
                "name": "property.string",
                "lang": "property.string"
            }
        }
    }
}
```
