# Cardinalities

The code for this example is [Cardinalities](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Cardinalities.java).

This example demonstrates how storing a HyperLogLogPlus object on each vertex in a graph allows us to quickly estimate its degree (i.e. the number of edges it is involved in). The estimate can be obtained without scanning through all the edges involving a vertex and so is very quick, even if the degree is very large.

To add properties to vertices we need to add an Entity to our schema. Entities are associated with a vertex and contain a set of properties about that vertex.

### Elements schema

This is our new elements schema. You can see we have added a Cardinality Entity. This will be added to every vertex in the Graph. This Entity has a 'hllp' property that will hold the HyperLogLogPlus cardinality value.


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
      ],
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
        "hllp": "hllp",
        "count": "count.long"
      },
      "groupBy": [
        "edgeGroup"
      ]
    }
  }
}
```


### Types schema

We have added a new type - hllp. This is a HyperLogLogPlus object. The HyperLogLogPlus object will be used to estimate the cardinality of a vertex.
We also added in the serialiser and aggregator for the HyperLogLogPlus object. Gaffer will automatically aggregate the cardinalities, using the provided aggregator, so they will keep up to date as new elements are added to the graph.


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
    "hllp": {
      "class": "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
      }
    }
  }
}
```


Here are all the edges loaded into the graph (unfortunately the HyperLogLogPlus class we are using for the cardinality doesn't have a toString method, so just ignore that for now):

```
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@c079719,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@1d2e826f,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 PDT 2000]]
Entity[vertex=11,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@76e23bbd,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=11,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@29e8b8b4,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 PDT 2000]]
Entity[vertex=23,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@19e75dcf,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=23,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@70338966,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 PDT 2000]]
Entity[vertex=24,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@17eb6b0e,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=24,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@7baa4c5f,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Entity[vertex=27,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@58007fc0,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=27,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3494b51f,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Entity[vertex=28,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@78eb0b98,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=28,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@518a786e,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Thu May 04 00:00:00 PDT 2000]]
Entity[vertex=M5,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@a2fe3e0,count=<java.lang.Long>14,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=23,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=27,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=28,directed=true,group=RoadHasJunction,properties=Properties[]]

```

We can fetch all cardinalities for all the vertices using the following operation:


{% codetabs name="Java", type="java" -%}
final GetAllElements getAllCardinalities =
        new GetAllElements.Builder()
                .view(new View.Builder()
                        .entity("Cardinality")
                        .build())
                .build();
{%- endcodetabs %}


If we look at the cardinality value of the HyperLogLogPlus property the values are:

```
Vertex 10 [RoadUse]: 1
Vertex 10 [RoadHasJunction]: 1
Vertex 11 [RoadUse]: 1
Vertex 11 [RoadHasJunction]: 1
Vertex 23 [RoadUse]: 1
Vertex 23 [RoadHasJunction]: 1
Vertex 24 [RoadUse]: 1
Vertex 24 [RoadHasJunction]: 1
Vertex 27 [RoadUse]: 1
Vertex 27 [RoadHasJunction]: 1
Vertex 28 [RoadUse]: 1
Vertex 28 [RoadHasJunction]: 1
Vertex M5 [RoadHasJunction]: 7

```

You can see we get a cardinality value of 1 for each junction for RoadUse edges as each junction is only connected to 1 other junction.
The cardinality value for the M5 Road is 7 as there are 7 junctions on this road in our data set. 
Remember that the HyperLogLogPlus cardinality value is just an estimate.  

If we want to merge these cardinalities together we can add 'groupBy=[]' to the operation view to override the groupBy defined in the schema.


{% codetabs name="Java", type="java" -%}
final GetAllElements getAllSummarisedCardinalities =
        new GetAllElements.Builder()
                .view(new View.Builder()
                        .entity("Cardinality", new ViewElementDefinition.Builder()
                                .groupBy()
                                .build())
                        .build())
                .build();
{%- endcodetabs %}


Now you can see the cardinality values have been merged together at each vertex:

```
Vertex 10 [RoadHasJunction, RoadUse]: 2
Vertex 11 [RoadHasJunction, RoadUse]: 2
Vertex 23 [RoadHasJunction, RoadUse]: 2
Vertex 24 [RoadHasJunction, RoadUse]: 2
Vertex 27 [RoadHasJunction, RoadUse]: 2
Vertex 28 [RoadHasJunction, RoadUse]: 2
Vertex M5 [RoadHasJunction]: 7

```

This next snippet shows you how you can query for a single cardinality value.


{% codetabs name="Java", type="java" -%}
final GetElements getCardinalities =
        new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .entity("Cardinality", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("edgeGroup")
                                        .execute(new IsEqual(CollectionUtil.treeSet("RoadUse")))
                                        .build())
                                .build())
                        .build())
                .build();
{%- endcodetabs %}


As you can see the query just simply asks for an entities at vertex '10' and filters for only 'Cardinality' entities that have an edgeGroup property equal to 'RoadUse'. 

The result is:

```
Vertex 10 [RoadUse]: 1

```

One of the main uses of Cardinalities is to avoid busy vertices whilst traversing the graph. 
For example if you want to do a 2 hop query (traverse along an edge then another edge) you may want to only go down edges where the source vertex has a low cardinality to avoid returning too many edges.
Here is the java code:


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<? extends Element>> twoHopsWithCardinalityFilter = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("M5"))
                .view(new View.Builder()
                        .edge("RoadHasJunction")
                        .build())
                .build())
        .then(new GetElements.Builder()
                .view(new View.Builder()
                        .entity("Cardinality", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("edgeGroup")
                                        .execute(new IsEqual(CollectionUtil.treeSet("RoadUse")))
                                        .build())
                                .groupBy()
                                .postAggregationFilter(new ElementFilter.Builder()
                                        .select("hllp")
                                        .execute(new HyperLogLogPlusIsLessThan(5))
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new GetElements.Builder()
                .view(new View.Builder()
                        .edge("RoadUse")
                        .build())
                .build())
        .build();
{%- endcodetabs %}

