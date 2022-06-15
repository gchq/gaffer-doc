# ThetaSketch

The code for this example is [ThetaSketchWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/ThetaSketchWalkthrough.java).

This example demonstrates how the [com.yahoo.sketches.theta.Sketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/theta/Sketch.java) sketch from the Data Sketches library can be used to maintain estimates of the cardinalities of sets. This sketch is similar to a HyperLogLogPlusPlus, but it can also be used to estimate the size of the intersections of sets. We give an example of how this can be used to monitor the changes to the number of edges in the graph over time.

##### Elements schema
This is our new elements schema. The edge has properties called 'startDate' and 'endDate'. These will be set to the midnight before the time of the occurrence of the edge and to midnight after the time of the occurrence of the edge. There is also a size property which will be a theta Sketch. This property will be aggregated over the 'groupBy' properties of startDate and endDate.


```json
{
  "entities": {
    "size": {
      "vertex": "vertex.string",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "size": "thetasketch"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    }
  },
  "edges": {
    "red": {
      "source": "vertex.string",
      "destination": "vertex.string",
      "directed": "false",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "long.count"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    }
  }
}
```


##### Types schema
We have added a new type - 'thetasketch'. This is a [com.yahoo.sketches.theta.Sketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/theta/Sketch.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/theta/serialisation/SketchSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/theta/binaryoperator/SketchAggregator.java) for the Union object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.


```json
{
  "types": {
    "vertex.string": {
      "class": "java.lang.String",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ]
    },
    "date.earliest": {
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
    "long.count": {
      "class": "java.lang.Long",
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
      }
    },
    "thetasketch": {
      "class": "com.yahoo.sketches.theta.Sketch",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.theta.binaryoperator.SketchAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.theta.serialisation.SketchSerialiser"
      }
    },
    "false": {
      "class": "java.lang.Boolean",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsFalse"
        }
      ]
    }
  }
}
```


1000 different edges were added to the graph for the day 09/01/2017 (i.e. the startDate was the midnight at the start of the 9th, and the endDate was the midnight at the end of the 9th). For each edge, an Entity was created, with a vertex called "graph". This contained a theta Sketch object to which a string consisting of the source and destination was added. 500 edges were added to the graph for the day 10/01/2017. Of these, 250 were the same as edges that had been added in the previous day, but 250 were new. Again, for each edge, an Entity was created for the vertex called "graph".

Here is the Entity for the different days:
```
Entity[vertex=graph,group=size,properties=Properties[size=<com.yahoo.sketches.theta.DirectCompactOrderedSketch>
### DirectCompactOrderedSketch SUMMARY: 
   Estimate                : 1000.0
   Upper Bound, 95% conf   : 1000.0
   Lower Bound, 95% conf   : 1000.0
   Theta (double)          : 1.0
   Theta (long)            : 9223372036854775807
   Theta (long) hex        : 7fffffffffffffff
   EstMode?                : false
   Empty?                  : false
   Array Size Entries      : 1000
   Retained Entries        : 1000
   Seed Hash               : 93cc
### END SKETCH SUMMARY
,endDate=<java.util.Date>Tue Jan 10 00:00:00 PST 2017,startDate=<java.util.Date>Mon Jan 09 00:00:00 PST 2017]]
Entity[vertex=graph,group=size,properties=Properties[size=<com.yahoo.sketches.theta.DirectCompactOrderedSketch>
### DirectCompactOrderedSketch SUMMARY: 
   Estimate                : 500.0
   Upper Bound, 95% conf   : 500.0
   Lower Bound, 95% conf   : 500.0
   Theta (double)          : 1.0
   Theta (long)            : 9223372036854775807
   Theta (long) hex        : 7fffffffffffffff
   EstMode?                : false
   Empty?                  : false
   Array Size Entries      : 500
   Retained Entries        : 500
   Seed Hash               : 93cc
### END SKETCH SUMMARY
,endDate=<java.util.Date>Wed Jan 11 00:00:00 PST 2017,startDate=<java.util.Date>Tue Jan 10 00:00:00 PST 2017]]

```

This is not very illuminating as this just shows the default `toString()` method on the sketch. To get value from it we need to call a method on the Sketch object:


{% codetabs name="Java", type="java" -%}
final GetAllElements getAllEntities2 = new GetAllElements.Builder()
        .view(new View.Builder()
                .entity("size")
                .build())
        .build();
final CloseableIterable<? extends Element> allEntities2 = graph.execute(getAllEntities2, user);
final CloseableIterator<? extends Element> it = allEntities2.iterator();
final Element entityDay1 = it.next();
final Sketch sketchDay1 = ((Sketch) entityDay1.getProperty("size"));
final Element entityDay2 = it.next();
final Sketch sketchDay2 = ((Sketch) entityDay2.getProperty("size"));
final double estimateDay1 = sketchDay1.getEstimate();
final double estimateDay2 = sketchDay2.getEstimate();
{%- endcodetabs %}


The result is:
```
1000.0
500.0

```

Now we can get an estimate for the number of edges in common across the two days:


{% codetabs name="Java", type="java" -%}
final Intersection intersection = Sketches.setOperationBuilder().buildIntersection();
intersection.update(sketchDay1);
intersection.update(sketchDay2);
final double intersectionSizeEstimate = intersection.getResult().getEstimate();
{%- endcodetabs %}


The result is:
```
250.0

```

We now get an estimate for the number of edges in total across the two days, by simply aggregating overall the properties:


{% codetabs name="Java", type="java" -%}
final GetAllElements getAllEntities = new GetAllElements.Builder()
        .view(new View.Builder()
                .entity("size", new ViewElementDefinition.Builder()
                        .groupBy() // set the group by properties to 'none'
                        .build())
                .build())
        .build();
final Element entity;
try (final CloseableIterable<? extends Element> allEntities = graph.execute(getAllEntities, user)) {
    entity = allEntities.iterator().next();
}
final double unionSizeEstimate = ((Sketch) entity.getProperty("size")).getEstimate();
{%- endcodetabs %}


The result is:

```
1250.0

```
