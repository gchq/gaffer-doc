# Writing the Schema

In Gaffer JSON based schemas need to be written upfront to model and understand how to load and
treat the data in the graph. These schemas define all aspects of the entities and edges in the graph,
and can even be used to automatically do basic analysis or aggregation on queries and ingested data.

For reference, this guide will use the same CSV data set from the [project setup](./project-setup.md#the-example-graph) page.

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

In Gaffer, an element refers to any object in the graph, i.e. your entities and edges. To set
up a graph we need to tell Gaffer what objects are in the graph and the properties they have. The
standard way to do this is a JSON config file in the schema directory. The filename can just be
called something like `elements.json`, the name is not special as all files under the `schema`
directory will be [merged into a master schema](../../administration-guide/gaffer-config/schema.md), but we recommended
using an appropriate name.

As covered in the [Getting Started Schema page](../../user-guide/schema.md), to write a schema you can see that there are some
required fields, but largely a schema is highly specific to your input data.

Starting with the `entities` from the example, we can see there will be two distinct types of entity
in the graph; one representing a `Person` and another for `Software`. These can be added into the
schema to give something like the following:

!!! info ""

    The types here such as `id.person.string` are covered in the [next section](#types-schema).

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
is just a placeholder, but it has been named appropriately as it's assumed that we will just use the
string representation of the entities id (this will be defined in the `types.json` later in the
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

As discussed in the [user schema guide](../../user-guide/schema.md), edges have some mandatory fields. Starting with
the `source` and `destination` fields, these must match the types associated with the vertex field
in the relevant entities. From the example, we can see that the source of a `Created` edge is a
`Person` so we will use the placeholder type we set as the `vertex` field which is
`id.person.string`. Similarly the destination is a `Software` vertex so we will use its placeholder of
`id.software.string`.

We must also set whether an edge is directed or not, in this case it is as only a person can create
software not the other way around. To set this we will use the `true` type, but note that this is a
placeholder and must still be defined in the types.json.

Continuing with the example, the entities and edges also have some properties associated with each such
as name, age etc. These can also be added to the schema using a properties map to result in the
extended schema below.

```json
{
    "edges": {
        "Created": {
            "source": "id.person.string",
            "destination": "id.software.string",
            "directed": "true",
            "aggregate": "false",
            "properties": {
                "weight": "property.float"
            }
        }
    },
    "entities": {
        "Person": {
            "description": "Entity representing a person vertex",
            "vertex": "id.person.string",
            "aggregate": "false",
            "properties": {
                "name": "property.string",
                "age": "property.integer"
            }
        },
        "Software": {
            "description": "Entity representing a software vertex",
            "vertex": "id.software.string",
            "aggregate": "false",
            "properties": {
                "name": "property.string",
                "lang": "property.string"
            }
        }
    }
}
```

!!! note
    Take note of the `"aggregate": "false"` setting, this skips any ingest aggregation as it is not
    required and out of scope of this example. All entity property types must have an aggregation
    function in Gaffer unless this option is added. Aggregation is fairly advanced topic in Gaffer
    but very powerful it is covered in more depth later in the documentation.

## Types Schema

The other schema that now needs to be written is the types [schema](../../administration-guide/gaffer-config/schema.md). As you have seen in the elements
schema there are some placeholder types added as the values for many of the keys. These types work
similarly to if you have ever programmed in a strongly typed language, they are essentially the
wrapper for the value to encapsulate it.

Now starting with the types for the entities, we used two placeholder types, one for the
`Person` entity and one for the `Software` entity. From the example CSV you can see there is a `_id`
column that uses a string identifier that is used for the ID of the entity (this will also be used by
the `edge` to identify the source and destination). We will define a type for each entity ID using the
standard java `String` class to encapsulate it, this leads to a basic `type.json` like the
following.

```json
{
    "types": {
        "id.person.string": {
            "description": "A basic type to hold the string id of a person entity",
            "class": "java.lang.String"
        },
        "id.software.string": {
            "description": "A basic type to hold the string id of a person entity",
            "class": "java.lang.String"
        }
    }
}
```

The next set of types that need defining are, the ones used for the properties that are attached to
the entities. Again we need to take a look back at what our input data looks like, in the CSV
file we can see there are three different types that are used for the properties which are analogous
to a `String`, an `Integer` and a `Float`.

!!! tip
    Of course technically, all of these properties could be encapsulated in a string but assigning
    a relevant type allows some additional type specific features often used in grouping
    and aggregation.

If we make a type for each of the possible properties using the standard Java classes we end up with
the following:

```json
{
    "types": {
        "id.person.string": {
            "description": "A basic type to hold the string id of a person entity",
            "class": "java.lang.String"
        },
        "id.software.string": {
            "description": "A basic type to hold the string id of a person entity",
            "class": "java.lang.String"
        },
        "property.string": {
            "description": "A type to hold string properties of entities",
            "class": "java.lang.String"
        },
        "property.integer": {
            "description": "A basic type to hold integer properties of entities",
            "class": "java.lang.Integer"
        },
        "property.float": {
            "description": "A basic type to hold float properties of entities",
            "class": "java.lang.Float"
        }
    }
}
```

The final thing that we need to add to the schema is a type for the `true` Boolean value that's used
by the directed field of the edge element. This leaves us with the complete list of types for this
example.

```json
{
    "types": {
        "id.person.string": {
            "description": "A basic type to hold the string id of a person entity",
            "class": "java.lang.String"
        },
        "id.software.string": {
            "description": "A basic type to hold the string id of a person entity",
            "class": "java.lang.String"
        },
        "property.string": {
            "description": "A type to hold string properties of entities",
            "class": "java.lang.String"
        },
        "property.integer": {
            "description": "A basic type to hold integer properties of entities",
            "class": "java.lang.Integer"
        },
        "property.float": {
            "description": "A basic type to hold float properties of entities",
            "class": "java.lang.Float"
        },
        "true": {
            "description": "A simple boolean that must always be true.",
            "class": "java.lang.Boolean",
            "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
                }
            ]
        }
    }
}
```

As you can see the Boolean value also demonstrates the validation feature which allows for
validation of any values using the type. In this example it verifies its true but you could also
check it exists, see if its less than another value etc. or even run your own custom validator
class.

!!! tip
    The Koryphe module provides lots of default functions that can be used to validate and aggregate
    data, see the [predicate reference guide](../../reference/predicates-guide/koryphe-predicates.md)
    for more information.
