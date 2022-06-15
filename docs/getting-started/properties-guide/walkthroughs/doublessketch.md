# DoublesSketch

The code for this example is [DoublesSketchWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/DoublesSketchWalkthrough.java).

This example demonstrates how the [DoublesSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/quantiles/DoublesSketch.java) sketch from the Data Sketches library can be used to maintain estimates of the quantiles of a distribution of doubles. Suppose that every time an edge is observed, there is a double value associated with it, for example a value between 0 and 1 giving the score of the edge. Instead of storing a property that contains all the doubles observed, we can store a DoublesSketch which will allow us to estimate the median double, the 99th percentile, etc.

##### Elements schema
This is our new elements schema. The edge has a property called 'doublesSketch'. This will store the DoublesSketch object.

```json
{
  "edges": {
    "red": {
      "source": "vertex.string",
      "destination": "vertex.string",
      "directed": "false",
      "properties": {
        "doublesSketch": "doubles.sketch"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'doubles.sketch'. This is a [com.yahoo.sketches.quantiles.DoublesSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/quantiles/DoublesSketch.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/quantiles/serialisation/DoublesSketchSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/quantiles/binaryoperator/DoublesSketchAggregator.java) for the DoublesSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.


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
    "doubles.sketch": {
      "class": "com.yahoo.sketches.quantiles.DoublesSketch",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.DoublesSketchAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.sketches.datasketches.quantiles.serialisation.DoublesSketchSerialiser"
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


```
Edge[source=A,destination=B,directed=false,group=red,properties=Properties[doublesSketch=<com.yahoo.sketches.quantiles.DirectUpdateDoublesSketchR>
### Quantiles DirectUpdateDoublesSketchR SUMMARY: 
   Empty                        : false
   Direct, Capacity bytes       : true, 4128
   Estimation Mode              : true
   K                            : 128
   N                            : 1,000
   Levels (Needed, Total, Valid): 2, 2, 2
   Level Bit Pattern            : 11
   BaseBufferCount              : 232
   Combined Buffer Capacity     : 512
   Retained Items               : 488
   Compact Storage Bytes        : 3,936
   Updatable Storage Bytes      : 4,128
   Normalized Rank Error        : 1.406%
   Normalized Rank Error (PMF)  : 1.711%
   Min Value                    : -3.148
   Max Value                    : 3.112
### END SKETCH SUMMARY
]]

```

This is not very illuminating as this just shows the default `toString()` method on the sketch. To get value from it we need to call methods on the DoublesSketch object. We can get an estimate for the 25th, 50th and 75th percentiles on edge A-B using the following code:


{% codetabs name="Java", type="java" -%}
final GetElements query = new GetElements.Builder()
        .input(new EdgeSeed("A", "B", DirectedType.UNDIRECTED))
        .build();
final Element edge;
try (final CloseableIterable<? extends Element> edges = graph.execute(query, user)) {
    edge = edges.iterator().next();
}
final DoublesSketch doublesSketch = (DoublesSketch) edge.getProperty("doublesSketch");
final double[] quantiles = doublesSketch.getQuantiles(new double[]{0.25D, 0.5D, 0.75D});
final String quantilesEstimate = "Edge A-B with percentiles of double property - 25th percentile: " + quantiles[0]
        + ", 50th percentile: " + quantiles[1]
        + ", 75th percentile: " + quantiles[2];
{%- endcodetabs %}


The results are as follows. This means that 25% of all the doubles on edge A-B had value less than -0.66, 50% had value less than -0.01 and 75% had value less than 0.64 (the results of the estimation are not deterministic so there may be small differences between the values below and those just quoted).

```
Edge A-B with percentiles of double property - 25th percentile: -0.6630847714290219, 50th percentile: -0.009776218111167738, 75th percentile: 0.6311663168517678

```

We can also get the cumulative density predicate of the distribution of the doubles:


{% codetabs name="Java", type="java" -%}
final GetElements query2 = new GetElements.Builder()
        .input(new EdgeSeed("A", "B", DirectedType.UNDIRECTED))
        .build();
final Element edge2;
try (final CloseableIterable<? extends Element> edges2 = graph.execute(query2, user)) {
    edge2 = edges2.iterator().next();
}
final DoublesSketch doublesSketch2 = (DoublesSketch) edge2.getProperty("doublesSketch");
final double[] cdf = doublesSketch2.getCDF(new double[]{0.0D, 1.0D, 2.0D});
final String cdfEstimate = "Edge A-B with CDF values at 0: " + cdf[0]
        + ", at 1: " + cdf[1]
        + ", at 2: " + cdf[2];
{%- endcodetabs %}


The results are:

```
Edge A-B with CDF values at 0: 0.511, at 1: 0.843, at 2: 0.987

```
