# LongsSketch

The code for this example is [LongsSketchWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/LongsSketchWalkthrough.java).

This example demonstrates how the [LongsSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/frequencies/LongsSketch.java) sketch from the Data Sketches library can be used to maintain estimates of the frequencies of longs stored on on vertices and edges. For example suppose every time an edge is observed there is a long value associated with it which specifies the size of the interaction. Storing all the different longs on the edge could be expensive in storage. Instead we can use a LongsSketch which will give us approximate counts of the number of times a particular long was observed.

##### Elements schema
This is our new elements schema. The edge has a property called 'longsSketch'. This will store the LongsSketch object.


```json
{
  "edges": {
    "red": {
      "source": "vertex.string",
      "destination": "vertex.string",
      "directed": "false",
      "properties": {
        "longsSketch": "longs.sketch"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'longs.sketch'. This is a [com.yahoo.sketches.frequencies.LongsSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/frequencies/LongsSketch.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/frequencies/serialisation/LongsSketchSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/frequencies/binaryoperator/LongsSketchAggregator.java) for the LongsSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.


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
    "longs.sketch": {
      "class": "com.yahoo.sketches.frequencies.LongsSketch",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.frequencies.binaryoperator.LongsSketchAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.frequencies.serialisation.LongsSketchSerialiser"
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


Only one edge is in the graph. This was added 1000 times, and each time it had the 'longs.sketch' property containing a randomly generated long between 0 and 9 (inclusive). The sketch does not retain all the distinct occurrences of these long values, but allows one to estimate the number of occurrences of the different values. Here is the Edge:

```
Edge[source=A,destination=B,directed=false,group=red,properties=Properties[longsSketch=<com.yahoo.sketches.frequencies.LongsSketch>FrequentLongsSketch:
  Stream Length    : 1000
  Max Error Offset : 0
ReversePurgeLongHashMap:
         Index:     States              Values Keys
             0:          1                 112 0
             3:          1                  96 6
             5:          1                  92 4
             6:          2                 103 5
             7:          3                  98 9
             8:          2                  91 2
             9:          3                  98 8
            12:          1                 106 1
            13:          1                  99 7
            14:          1                 105 3
]]

```

This is not very illuminating as this just shows the default `toString()` method on the sketch. To get value from it we need to call methods on the LongsSketch object. Let's get estimates of the frequencies of the values 1 and 9.

We can fetch all cardinalities for all the vertices using the following code:


{% codetabs name="Java", type="java" -%}
final GetElements query = new GetElements.Builder()
        .input(new EdgeSeed("A", "B", DirectedType.UNDIRECTED))
        .build();
final Element edge;
try (final CloseableIterable<? extends Element> edges = graph.execute(query, user)) {
    edge = edges.iterator().next();
}
final LongsSketch longsSketch = (LongsSketch) edge.getProperty("longsSketch");
final String estimates = "Edge A-B: 1L seen approximately " + longsSketch.getEstimate(1L)
        + " times, 9L seen approximately " + longsSketch.getEstimate(9L) + " times.";
{%- endcodetabs %}


The results are as follows. As 1000 edges were generated with a long randomly sampled from 0 to 9 then the occurrence of each is approximately 100.

```
Edge A-B: 1L seen approximately 106 times, 9L seen approximately 98 times.

```
