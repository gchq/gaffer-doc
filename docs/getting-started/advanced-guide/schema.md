# Schemas
This page delves into more detail on Schemas. As seen in the other guides, there are two main components to schemas: Elements and Types.

Schemas are often separated into two JSON files (`elements.json` & `types.json`), but it's worth remembering that your schema can be broken up further if required.

For example if you have a Graph that is made up of 5 different data sources, you may find it easier to develop and maintain by splitting your schema into multiple parts, for example 5 Element Schemas and 1 Types schema.
Alternatively, a single overall schema file could be used for Elements and Types. There's also no requirement to have a single Types schemas.
When you construct your Gaffer graph you must provide all the Schema parts. If provided separately, these will then be merged together to form an internal schema used for the Graph.

When using Java directly, you don't need to use JSON files to store you schemas - although this is still recommended. See the [Java API](#java-api) section for more about interacting with Schemas in Java.

The sections below walkthrough the features of Schemas in detail and explain how to accurately model your data in Gaffer.


## Elements schema
The Elements schema is designed to be a high level document describing what information your Graph contains, i.e. the different kinds of edges and entities and the list of properties associated with each.
Essentially this part of the schema should just be a list of all the entities and edges in the graph. 
Edges describe the relationship between a source vertex and a destination vertex. 
Entities describe a vertex. We use the term "element" to mean either and edge or and entity.

When defining an element we must provide a "group". This is a unique string identifier for each element.
Groups must be completely unique and cannot be shared between edges and entities.

### Edges
Edges must have the following:

- `source` - Type of object to use as the source vertex in your graph. Needs to be a type defined in the [Types schema](#types).
- `destination` - Type of object to use as the destination vertex in your graph. Can either be the same type as `source`, or a different type.
- `directed` - Tells Gaffer if the edge is directed or undirected. Needs to be a type which means true or false, see [Types](#true--false) for more info.

When an Edge is undirected in Gaffer (`directed` is `false`), it is treated as if the relationship was bidirectional, and the vertices of the edge do not have an authoritative source and destination.
Thus, the undirected edges `A -- B` and `B -- A` are equal, and will therefore be aggregated together.
Gaffer will present the undirected edges Vertices in natural ordering, so a client will also see the above edge presented as `A, B`.
It will be aggregated with any other edge that has the same source and destination and is also undirected.
For example an undirected Edge that is added, `A -> B`, will be aggregated with another Edge of `B -> A` if they are undirected.

### Entities
Entities must have a `vertex` field, which is similar to the `source` and `destination` fields on an edge.
For example, modelling `London -> Paris` requires an entity definition to represent the city and an edge definition to represent the relationship.
In the previous example the entity `vertex` field and the edge `source` and `destination` fields would all be of the same type.

### Optional Element fields
Edges and Entities can optionally have the following fields:

- `description` - A simple string which should provide some context and explain what the element is.
- `parents` - An array of parent group names. These must relate to the same sort of element as the child, for example an edge cannot have an entity as a parent. Elements can inherit any information from multiple parent elements. Fields will be merged/overridden, so the hierarchy of parents is important. Any fields that are defined in the child element will also merge or override information taken from the parents.
- `properties` - Properties are defined by a map of key-value pairs of property names to property types. Property types are described in the Types schema.
- `groupBy` - Allows you to specify extra properties (in addition to the element group and vertices) to use for controlling when similar elements should be grouped together and summarised. By default Gaffer uses the element group and its vertices to group similar elements together when aggregating and summarising elements.
- `visibilityProperty` - Used to specify the property to use as a visibility property when using visibility properties in your graph. If sensitive elements have a visibility property then set this field to that property name. This ensures Gaffer knows to restrict access to sensitive elements.
- `timestampProperty` - Used to specify timestamp property in your graph, so Gaffer Stores know to treat that property specially. Setting this is optional and does not affect the queries available to users. This property allows Store implementations like Accumulo to optimise the way the timestamp property is persisted. For these stores using it can have a very slight performance improvement due to the lazy loading of properties. For more information [see the timestamp section of the Accumulo Store Reference](../../reference/stores-guide/accumulo.md#timestamp).
- `aggregate` - Specifies if aggregation is enabled for this element group. True by default. If you would like to disable aggregation, set this to false.

These 2 optional fields are for advanced users. They can go in the Elements Schema, however we have split them out into separate Validation and Aggregation Schema files for this page, so the logic doesn't complicate the Elements schema.

- `validateFunctions` - An array of selections and predicates to be applied to an element. This allows you to validate based on multiple properties at once. E.g. check a timestamp property together with a time to live property to check if the element should be aged off. Individual property validation is best done as a validateFunction in the property type definition in Types schema.
- `aggregateFunctions` - An array of selections and binary operators to be applied to an element. This allows you to aggregate based on multiple properties at once. It is important to note that types of properties (groupBy, non-groupBy, visibility) cannot be aggregated in the same aggregate function. The timestamp property is treated as a non-groupBy property. Individual property aggregation is best done as a aggregateFunction in the property type definition in the Types schema.

### Example Elements Schema
Here is an example Elements schema:

```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    }
  },
  "entities": {
    "Cardinality": {
      "description": "An entity that is added to every vertex representing the connectivity of the vertex.",
      "vertex": "anyVertex",
      "properties": {
        "edgeGroup": "set",
        "hllSketch": "hllSketch",
        "count": "count.long"
      },
      "groupBy": [
        "edgeGroup"
      ]
    }
  }
}
```

Here is the Validation Schema. It contains advanced validation, that is applied to multiple properties within an Element group.

```json
{
  "edges": {
    "RoadUse": {
      "validateFunctions": [
        {
          "selection": [
            "startDate",
            "endDate"
          ],
          "predicate": {
            "class": "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          }
        }
      ]
    }
  }
}
```

Here is the Aggregation Schema. It contains advanced aggregation, that is applied to multiple properties within an Element group.
The multi property aggregate function defined here overrides the relevant single property aggregate functions defined in the Types schema.

```json
{
  "edges": {
    "RoadUse": {
      "aggregateFunctions": [
        {
          "selection": [
            "startDate",
            "endDate"
          ],
          "binaryOperator": {
            "class": "uk.gov.gchq.gaffer.doc.dev.aggregator.ExampleTuple2BinaryOperator"
          }
        }
      ]
    }
  }
}
```


## Types
All types used in the Elements schema must be defined in the Types parts of the schema. These Types explain to Gaffer what types of properties to expect and how to deal with them.

For each type you must provide the following information:

- `class` - The Java class of the type.

You can optionally provide the following:

- `description` - String which should provide a description of the type.
- `validateFunctions` - Array of predicates that will be executed against every type value to validate it. To improve performance, put quicker/low cost functions first in the array.
- `aggregateFunction` - The aggregate binary operator to use to aggregate/summarise/merge property values of the same type together.
- `serialiser` - Object which contains a field class which represents the Java class of the serialiser to use, and potentially arguments depending on the serialiser. If this is not provided Gaffer will attempt to select an appropriate one for you (only available for simple Java types).

### True & False
The `directed` field for edge elements is either "true" or "false". Currently, you need to manually define these as types in your Types schema.
The easiest way to do this is to create a type called "true", "false" and define it as being a boolean with a filter predicate to check the boolean is either true or false respectively.
There's an example of a "true" type in the example Types section below.

### Serialisers
Gaffer will automatically choose serialisers for you for some core types.
Where possible you should let Gaffer choose for you, as it will choose the optimal serialiser for the type and your usage.
For custom types you will need to write your own serialiser.

When manually choosing a serialiser for your schema you will need to take the following into consideration.

For vertex serialisation and groupBy properties you must choose serialisers that are consistent.
A consistent serialiser will serialise the equal objects into exactly the same values (bytes).
For example the JavaSerialiser and FreqMapSerialiser are not consistent.

When using an ordered store (a store that implements the ORDERED StoreTrait, such as Accumulo), you need to check whether the serialisers are ordered:

 - For vertex serialisation you must use a serialiser that is ordered.
 - For groupBy properties you must use a serialiser that is ordered.
 - All other properties can be serialised with ordered/unordered serialisers.

### Example Types Schema

Here is an example Types schema:

```json
{
  "types": {
    "junction": {
      "description": "A road junction represented by a String.",
      "class": "java.lang.String"
    },
    "road": {
      "description": "A road represented by a String.",
      "class": "java.lang.String"
    },
    "anyVertex": {
      "description": "An String vertex - used for cardinalities",
      "class": "java.lang.String"
    },
    "count.long": {
      "description": "A long count that must be greater than or equal to 0.",
      "class": "java.lang.Long",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo": true,
          "value": {
            "java.lang.Long": 0
          }
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
      }
    },
    "true": {
      "description": "A simple boolean that must always be true.",
      "class": "java.lang.Boolean",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
        }
      ]
    },
    "date.earliest": {
      "description": "A Date that when aggregated together will be the earliest date.",
      "class": "java.util.Date",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
      }
    },
    "date.latest": {
      "description": "A Date that when aggregated together will be the latest date.",
      "class": "java.util.Date",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
      }
    },
    "set": {
      "class": "java.util.TreeSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      }
    },
    "hllSketch": {
      "class": "org.apache.datasketches.hll.HllSketch",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.cardinality.binaryoperator.HllSketchAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.cardinality.serialisation.HllSketchSerialiser"
      }
    }
  }
}
```


## Full Schema Example
Once the schema has been loaded into a graph the parent elements are merged into the children for performance reasons. This is what the full schema created from the above example schema parts looks like:

```json
{
  "edges" : {
    "RoadUse" : {
      "description" : "A directed edge representing vehicles moving from junction A to junction B.",
      "source" : "junction",
      "destination" : "junction",
      "directed" : "true",
      "properties" : {
        "startDate" : "date.earliest",
        "endDate" : "date.latest",
        "count" : "count.long"
      },
      "groupBy" : [ "startDate", "endDate" ],
      "aggregateFunctions" : [ {
        "selection" : [ "startDate", "endDate" ],
        "binaryOperator" : {
          "class" : "uk.gov.gchq.gaffer.doc.dev.aggregator.ExampleTuple2BinaryOperator"
        }
      } ],
      "validateFunctions" : [ {
        "selection" : [ "startDate", "endDate" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
        }
      } ]
    },
    "RoadHasJunction" : {
      "description" : "A directed edge from each road to all the junctions on that road.",
      "source" : "road",
      "destination" : "junction",
      "directed" : "true"
    }
  },
  "entities" : {
    "Cardinality" : {
      "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
      "vertex" : "anyVertex",
      "properties" : {
        "edgeGroup" : "set",
        "hllSketch" : "hllSketch",
        "count" : "count.long"
      },
      "groupBy" : [ "edgeGroup" ]
    }
  },
  "types" : {
    "junction" : {
      "description" : "A road junction represented by a String.",
      "class" : "java.lang.String"
    },
    "road" : {
      "description" : "A road represented by a String.",
      "class" : "java.lang.String"
    },
    "anyVertex" : {
      "description" : "An String vertex - used for cardinalities",
      "class" : "java.lang.String"
    },
    "count.long" : {
      "description" : "A long count that must be greater than or equal to 0.",
      "class" : "java.lang.Long",
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
      },
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
        "orEqualTo" : true,
        "value" : {
          "java.lang.Long" : 0
        }
      } ]
    },
    "true" : {
      "description" : "A simple boolean that must always be true.",
      "class" : "java.lang.Boolean",
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
      } ]
    },
    "date.earliest" : {
      "description" : "A Date that when aggregated together will be the earliest date.",
      "class" : "java.util.Date",
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
      },
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
      } ]
    },
    "date.latest" : {
      "description" : "A Date that when aggregated together will be the latest date.",
      "class" : "java.util.Date",
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
      },
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
      } ]
    },
    "set" : {
      "class" : "java.util.TreeSet",
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      }
    },
    "hllSketch" : {
      "class" : "org.apache.datasketches.hll.HllSketch",
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.gaffer.sketches.datasketches.cardinality.binaryoperator.HllSketchAggregator"
      },
      "serialiser" : {
        "class" : "uk.gov.gchq.gaffer.sketches.datasketches.cardinality.serialisation.HllSketchSerialiser"
      }
    }
  }
}
```


## Java API

Schemas can be loaded from a JSON file directly using the [`fromJSON()` method of the `Schema` class](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/schema/Schema.html#fromJson(java.io.InputStream...)). This accepts `byte[]`, `InputStream` or `Path` types, for example:

```java
Schema mySchema = Schema.fromJson(Paths.get("path/to/schema.json"));
```

While it's easiest to create Schema objects using the JSON approach, you can create them directly from their component classes in Java.
Constructing a Schema from scratch this way would be tedious, but you could use this to create your own solutions for Schema generation by building on these components.

A Schema can be created using its `Builder()` method. [See Schema Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/schema/Schema.html).

```java
Schema mySchema = new Schema.Builder().edges(edges)
                                      .entities(entities)
                                      .types(types)
                                      .build();
```

The Edges, Entities and Types are supplied as Maps with String keys. See the Javadoc covering [`SchemaEdgeDefinition`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/schema/SchemaEdgeDefinition.html), [`SchemaEntityDefinition`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/schema/SchemaEntityDefinition.html) and [`TypeDefinition`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/schema/TypeDefinition.html) for information about their builders.

```java
Map<String, SchemaEdgeDefinition> myEdges = new HashMap<>();
myEdges.put("myEdge", new SchemaEdgeDefinition.Builder()...
                                                        .build());

Map<String, SchemaEntityDefinition> myEntities = new HashMap<>();
myEntities.put("myEntity", new SchemaEntityDefinition.Builder()...
                                                               .build());

Map<String, TypeDefinition> myTypes = new HashMap<>();
myTypes.put("myType", new TypeDefinition.Builder()...
                                                  .build());
```
