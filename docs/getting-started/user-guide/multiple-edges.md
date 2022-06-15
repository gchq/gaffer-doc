# Multiple Edges

The code for this example is [MultipleEdges](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/MultipleEdges.java).

This time we'll see at what happens when we have more than one Edge.

### Schemas

We first need to add an additional Edge to our Schema. This will be a simple Edge from a new vertex 'road' to the existing 'junction' vertex. It doesn't need any additional properties.

We will define the 'RoadHasJunction' edge and add it to our elements schema:


```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "count": "count.long"
      }
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    }
  }
}
```


The source vertex uses a new 'road' type so we also have to define that in our types schema. It is defined to be a simple java String, similar to the junction type:


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
    }
  }
}
```


### Element Generator

Like the previous example, we use an ElementGenerator to generate Gaffer Edges from each line of the CSV, but now each line will generate 3 Edges. 1 RoadUse edge and 2 RoadHasJunction edges. The generator for this example is:


```java
public class RoadAndRoadUseElementGenerator implements OneToManyElementGenerator<String> {
    @Override
    public Iterable<Element> _apply(final String line) {
        final String[] t = line.split(",");

        final String road = t[0];
        final String junctionA = t[1];
        final String junctionB = t[2];

        return Arrays.asList(
                new Edge.Builder()
                        .group("RoadHasJunction")
                        .source(road)
                        .dest(junctionA)
                        .directed(true)
                        .build(),

                new Edge.Builder()
                        .group("RoadHasJunction")
                        .source(road)
                        .dest(junctionB)
                        .directed(true)
                        .build(),

                new Edge.Builder()
                        .group("RoadUse")
                        .source(junctionA)
                        .dest(junctionB)
                        .directed(true)
                        .property("count", 1L)
                        .build()
        );
    }
}
```

Here are the generated edges:

```
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=23,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=23,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=28,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=27,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```

### Loading and Querying the Data

We create a Graph and load the data using the [AddElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html) Operation exactly the same as in the previous example.

This time we'll run 2 queries using [GetElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html).

The first one is exactly the same as last time. We ask for all of the Edges containing the Vertex "10". The result is:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]

```

Our second query is to return just the "RoadHasJunction" Edges. We still use the [GetElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) Operation like before but this time we add a [View](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/View.html) to it:


{% codetabs name="Java", type="java" -%}
final View view = new View.Builder()
        .edge("RoadHasJunction")
        .build();
final GetElements getRoadHasJunctionEdges = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(view)
        .build();
final CloseableIterable<? extends Element> filteredResults = graph.execute(getRoadHasJunctionEdges, user);
{%- endcodetabs %}


The view has restricted the results so that only the "RoadHasJunction" Edge containing Vertex "10" was returned:

```
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]

```

We'll explore the [View](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/View.html) in more detail over the next few examples. But for now think of it as you are requesting a particular view of your graph, filtering out unwanted elements.
