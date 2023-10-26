# Advanced Properties

These properties use advanced sketch structures from the Clearspring and Datasketches libraries.

## Introduction

A sketch is a compact data structure that gives an approximate answer to a question. For example, a Hll sketch can estimate the cardinality of a set with billions of elements with a small relative error, using orders of magnitude less storage than storing the full set.

Gaffer allows sketches to be stored on Entities and Edges. These sketches can be continually updated as new data arrives. Here are some example applications of sketches in Gaffer:

- Using a Hll sketch to provide a very quick estimate of the degree of a node.
- Using a quantiles sketch to estimate the median score associated to an edge, or the 99th percentile of the scores seen on an edge.
- Using a reservoir items sketch to store a sample of all the distinct labels associated to an edge.
- Using theta sketches to estimate the number of distinct edges seen on a particular day, the number seen on the previous day and the overlap between the two days.

Gaffer provides serialisers and aggregators for sketches from two different libraries: the Apache version of the [Datasketches](https://datasketches.apache.org/) library and the [Clearspring](https://github.com/addthis/stream-lib). The Clearspring HyperLogLogPlus has been **deprecated in Gaffer** and we recommend the Datasketches HllSketch to users for the reasons described [below](#hyperloglogplus).  

For the Datasketches library, serialisers and aggregators are provided for several sketches. These sketches include:

- [HyperLogLog sketches](https://datasketches.apache.org/docs/HLL/HLL.html) for estimating the cardinality of a set (see class [org.apache.datasketches.hll.HllSketch](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/hll/HllSketch.java));
- [Frequency sketches](https://datasketches.apache.org/docs/Frequency/FrequencySketchesOverview.html) for estimating the frequencies of items such as longs and strings respectively (see for example class [org.apache.datasketches.frequencies.LongsSketch](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/frequencies/LongsSketch.java));
- [Quantile sketches](https://datasketches.apache.org/docs/Quantiles/QuantilesOverview.html) for estimating the quantiles of doubles or strings seen on an element (see for example class [org.apache.datasketches.quantiles.DoublesSketch](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/quantiles/DoublesSketch.java));
- [Sampling sketches](https://datasketches.apache.org/docs/Sampling/ReservoirSampling.html) for maintaining samples of items seen on an element (see for example class [org.apache.datasketches.sampling.ReservoirItemsSketch](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/sampling/ReservoirItemsSketch.java));
- [Theta sketches](https://datasketches.apache.org/docs/Theta/ThetaSketchFramework.html) for estimating the union and intersection of sets (see for example class [org.apache.datasketches.theta.Sketch](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/theta/Sketch.java)).

Most of the Datasketches sketches come in two forms: a standard sketch form and a "union" form. The latter is technically not a sketch. It is an operator that allows efficient union operations of two sketches. It also allows updating the sketch with individual items. In order to obtain estimates from it, it is necessary to first obtain a sketch from it, using a method called `getResult()`. There are some interesting trade-offs in the serialisation and aggregation speeds between the sketches and the unions. If in doubt, use the standard sketches. Examples are provided for the standard sketches, but not for the unions.

For the deprecated Clearspring library, a serialiser and an aggregator is provided for the [`HyperLogLogPlus`](https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/stream/cardinality/HyperLogLogPlus.java) sketch. This is an implementation of the HyperLogLog++ algorithm described [in this paper](https://static.googleusercontent.com/external_content/untrusted_dlcp/research.google.com/en/us/pubs/archive/40671.pdf).

### Class List

Property | Full Class
-------- | ----------
`HyperLogLogPlus` | `com.clearspring.analytics.stream.cardinality.HyperLogLogPlus`
`HllSketch` | `org.apache.datasketches.hll.HllSketch`
`LongsSketch` | `org.apache.datasketches.frequencies.LongsSketch`
`DoublesSketch` | `org.apache.datasketches.quantiles.DoublesSketch`
`ReservoirItemsSketch` | `org.apache.datasketches.sampling.ReservoirItemsSketch`
`ThetaSketch` | `org.apache.datasketches.theta.Sketch`

## Predicate Support

The advanced properties all support these predicates:

- `And`
- `Or`
- `Not`
- `If`
- `Exists`
- `IsA`
- `IsIn`
- `IsEqual`
- `PropertiesFilter`

Some advanced properties also have specilaised predicates:

Property | Predicate
-------- | ---------
`HyperLogLogPlus` | [`uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan`](../predicates-guide/gaffer-predicates.md#hyperloglogplusislessthan)
`HllSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.cardinality.predicate.HllSketchIsLessThan`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/predicate/HllSketchIsLessThan.html)


## Aggregator Support

The `First` and `Last` binary operators are supported by all advanced properties.

Each advanced property has a specilaised aggregator:

Property | Binary Operator
-------- | ---------------
`HyperLogLogPlus` | [`uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/binaryoperator/HyperLogLogPlusAggregator.html)
`HllSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.cardinality.binaryoperator.HllSketchAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/binaryoperator/HllSketchAggregator.html)
`LongsSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.frequencies.binaryoperator.LongsSketchAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/frequencies/binaryoperator/LongsSketchAggregator.html)
`DoublesSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.DoublesSketchAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/quantiles/binaryoperator/DoublesSketchAggregator.html)
`ReservoirItemsSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.sampling.binaryoperator.ReservoirItemsSketchAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/sampling/binaryoperator/ReservoirItemsSketchAggregator.html)
`ThetaSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.theta.binaryoperator.SketchAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/theta/binaryoperator/SketchAggregator.html)

## Serialiser Support

All advanced properties support the [`NullSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/NullSerialiser.html) and each has (at least one of) their own specialised serialiser(s):

Property | Serialiser
-------- | ---------------
`HyperLogLogPlus` | [`uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/HyperLogLogPlusSerialiser.html)
`HllSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.cardinality.serialisation.HllSketchSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/serialisation/HllSketchSerialiser.html)
`LongsSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.frequencies.serialisation.LongsSketchSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/frequencies/serialisation/LongsSketchSerialiser.html)
`DoublesSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.quantiles.serialisation.DoublesSketchSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/quantiles/serialisation/DoublesSketchSerialiser.html)
`ReservoirItemsSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.sampling.serialisation.ReservoirNumbersSketchSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/sampling/serialisation/ReservoirNumbersSketchSerialiser.html)
`ReservoirItemsSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.sampling.serialisation.ReservoirStringsSketchSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/sampling/serialisation/ReservoirStringsSketchSerialiser.html)
`ThetaSketch` | [`uk.gov.gchq.gaffer.sketches.datasketches.theta.serialisation.SketchSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/datasketches/theta/serialisation/SketchSerialiser.html)

## Examples

This section contains examples for how to use the advanced properties.

### HyperLogLogPlus

This example demonstrates how the HyperLogLogPlus sketch property from the Clearspring library can be used to maintain an estimate of the degree of a vertex.

!!! warning
    As of 2.1.0, we have **deprecated** the use of Clearspring's `HyperLogLogPlus` within Gaffer and recommend using Datasketches' [`HllSketch`](#hllsketch) for approximate cardinality instead. This is because `HllSketch` has better performance as shown in the Datasketches [documentation](https://datasketches.apache.org/docs/HLL/Hll_vs_CS_Hllpp.html).

??? example "Example storing an estimate of the degree of a vertex using HyperLogLogPlus"

    Every time an Edge `A -> B` is added to graph, we also add an Entity for `A` with a property of type HyperLogLogPlus containing `B`, and an Entity for `B` with a property of type HyperLogLogPlus containing `A`. The aggregator for the HyperLogLogPluses merges the new data with the pre-existing cardinality, so that after querying for the Entity the HyperLogLogPlus property gives us an estimate of the degree.

    #### Elements schema
    This is our new elements schema. The entity type `cardinality` has a property called 'approxCardinality'. This will store the HyperLogLogPlus object.

    ```json
    {
      "entities": {
        "cardinality": {
          "vertex": "vertex.string",
          "properties": {
            "approxCardinality": "hyperLogLogPlus"
          }
        }
      }
    }
    ```

    #### Types schema
    We have added a new type - 'hyperLogLogPlus'. This is a [`com.clearspring.analytics.stream.cardinality.HyperLogLogPlus`](https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/stream/cardinality/HyperLogLogPlus.java) object.
    We also added in the serialiser and aggregator for the HyperLogLogPlus object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new entities are added to the graph.

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
        "hyperLogLogPlus": {
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

    Only one entity is in the graph. This was added 1000 times, and each time it had the 'approxCardinality' property containing a unique vertex. Here is the Entity:

    ```
    Entity[vertex=A,group=cardinality,properties=Properties[approxCardinality=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@39075f64]]
    ```

    This is not very illuminating as this just shows the default `toString()` method on the sketch. We can fetch the cardinality for the vertex using the following code:

    ```java
    final GetElements query = new GetElements.Builder()
            .input(new EntitySeed("A"))
            .build();
    final Element element;
    try (final Iterable<? extends Element> elements = graph.execute(query, user)) {
        element = elements.iterator().next();
    }
    final HyperLogLogPlus hyperLogLogPlus = (HyperLogLogPlus) element.getProperty("approxCardinality");
    final double approxDegree = hyperLogLogPlus.cardinality();
    final String degreeEstimate = "Entity A has approximate degree " + approxDegree;
    ```

    The results are as follows. As an Entity was added 1000 times, each time with a different vertex, then we would expect the degree to be approximately 1000.

    ```
    Entity A has approximate degree 1113.0
    ```

### HllSketch

This example demonstrates how the HllSketch sketch property from the Datasketches library can be used to maintain an estimate of the degree of a vertex.


??? example "Example storing an estimate of the degree of a vertex using HllSketch"

    Every time an Edge `A -> B` is added to graph, we also add an Entity for `A` with a property of type HllSketch containing `B`, and an Entity for `B` with a property of type HllSketch containing `A`. The aggregator for the HllSketches merges the new data with the pre-existing cardinality, so that after querying for the Entity the HllSketch property gives us an estimate of the degree.

    #### Elements schema
    This is our new elements schema. The entity type `cardinality` has a property called 'approxCardinality'. This will store the HllSketch object.

    ```json
    {
      "entities": {
        "cardinality": {
          "vertex": "vertex.string",
          "properties": {
            "approxCardinality": "hllSketch"
          }
        }
      }
    }
    ```

    #### Types schema
    We have added a new type - `hllSketch`. This is a [`org.apache.datasketches.hll.HllSketch`](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/hll/HllSketch.java) object.
    We also added in the serialiser and aggregator for the HllSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new entities are added to the graph.

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

    Only one entity is in the graph. This was added 1000 times, and each time it had the 'approxCardinality' property containing a unique vertex. Here is the Entity:

    ```
    Entity[vertex=A,group=cardinality,properties=Properties[approxCardinality=<org.apache.datasketches.hll.HllSketch>### HLL SKETCH SUMMARY: 
      Log Config K   : 10
      Hll Target     : HLL_4
      Current Mode   : HLL
      LB             : 986.8136434119266
      Estimate       : 1018.8398354963819
      UB             : 1052.991638617674
      OutOfOrder Flag: true
      CurMin         : 0
      NumAtCurMin    : 374
      HipAccum       : 1045.0654080765041
      KxQ0           : 562.4995727539062
      KxQ1           : 0.0
    ]]
    ```

    This is not very illuminating as this just shows the default `toString()` method on the sketch. We can fetch the cardinality for the vertex using the following code:

    ```java
    final GetElements query = new GetElements.Builder()
            .input(new EntitySeed("A"))
            .build();
    final Element element;
    try (final Iterable<? extends Element> elements = graph.execute(query, user)) {
        element = elements.iterator().next();
    }
    final HllSketch hllSketch = (HllSketch) element.getProperty("approxCardinality");
    final double approxDegree = hllSketch.getEstimate();
    final String degreeEstimate = "Entity A has approximate degree " + approxDegree;
    ```

    The results are as follows. As an Entity was added 1000 times, each time with a different vertex, then we would expect the degree to be approximately 1000.

    ```
    Entity A has approximate degree 1018.8398354963819
    ```

### LongsSketch

This example demonstrates how the LongsSketch sketch property from the Datasketches library can be used to maintain estimates of the frequencies of Longs stored on vertices and edges.

??? example "Example storing an estimate of frequencies of Longs using LongsSketch"

    For example suppose every time an edge is observed there is a long value associated with it which specifies the size of the interaction. Storing all the different longs on the edge could be expensive in storage. Instead we can use a LongsSketch which will give us approximate counts of the number of times a particular long was observed.

    #### Elements schema
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

    #### Types schema
    We have added a new type - 'longs.sketch'. This is a [`org.apache.datasketches.frequencies.LongsSketch`](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/frequencies/LongsSketch.java) object.
    We also added in the serialiser and aggregator for the LongsSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.

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
          "class": "org.apache.datasketches.frequencies.LongsSketch",
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
    Edge[source=A,destination=B,directed=false,matchedVertex=SOURCE,group=red,properties=Properties[longsSketch=<org.apache.datasketches.frequencies.LongsSketch>FrequentLongsSketch:
      Stream Length    : 1000
      Max Error Offset : 0
    ReversePurgeLongHashMap:
            Index:     States              Values Keys
                0:          1                 112 0
                3:          1                  96 6
                5:          1                  92 4
                6:          2                 103 5
                7:          1                  91 2
                8:          2                  98 8
                9:          5                  98 9
                12:          1                 106 1
                13:          1                  99 7
                14:          1                 105 3
    ]]
    ```

    This is not very illuminating as this just shows the default `toString()` method on the sketch. To get value from it we need to call methods on the LongsSketch object. Let's get estimates of the frequencies of the values 1 and 9. We can fetch all cardinalities for all the vertices using the following code:

    ```java
    final GetElements query = new GetElements.Builder()
            .input(new EdgeSeed("A", "B", DirectedType.UNDIRECTED))
            .build();
    final Element edge;
    try (final Iterable<? extends Element> edges = graph.execute(query, user)) {
        edge = edges.iterator().next();
    }
    final LongsSketch longsSketch = (LongsSketch) edge.getProperty("longsSketch");
    final String estimates = "Edge A-B: 1L seen approximately " + longsSketch.getEstimate(1L)
            + " times, 9L seen approximately " + longsSketch.getEstimate(9L) + " times.";
    ```

    The results are as follows. As 1000 edges were generated with a long randomly sampled from 0 to 9 then the occurrence of each is approximately 100.

    ```
    Edge A-B: 1L seen approximately 106 times, 9L seen approximately 98 times.
    ```

### DoublesSketch

This example demonstrates how the DoublesSketch sketch property from the Datasketches library can be used to maintain estimates of the quantiles of a distribution of Doubles.

??? example "Example storing an estimate of quantiles of a distribution of Doubles using LongsSketch"

    Suppose that every time an edge is observed, there is a double value associated with it, for example a value between 0 and 1 giving the score of the edge. Instead of storing a property that contains all the doubles observed, we can store a DoublesSketch which will allow us to estimate the median double, the 99th percentile, etc.

    #### Elements schema
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

    #### Types schema
    We have added a new type - 'doubles.sketch'. This is a [`org.apache.datasketches.quantiles.DoublesSketch`](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/quantiles/DoublesSketch.java) object.
    We also added in the serialiser and aggregator for the DoublesSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.

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
          "class": "org.apache.datasketches.quantiles.DoublesSketch",
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

    Here is the Edge:

    ```
    Edge[source=A,destination=B,directed=false,matchedVertex=SOURCE,group=red,properties=Properties[doublesSketch=<org.apache.datasketches.quantiles.DirectUpdateDoublesSketchR>
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

    ```java
    final GetElements query = new GetElements.Builder()
            .input(new EdgeSeed("A", "B", DirectedType.UNDIRECTED))
            .build();
    final Element edge;
    try (final Iterable<? extends Element> edges = graph.execute(query, user)) {
        edge = edges.iterator().next();
    }
    final DoublesSketch doublesSketch = (DoublesSketch) edge.getProperty("doublesSketch");
    final double[] quantiles = doublesSketch.getQuantiles(new double[]{0.25D, 0.5D, 0.75D});
    final String quantilesEstimate = "Edge A-B with percentiles of double property - 25th percentile: " + quantiles[0]
            + ", 50th percentile: " + quantiles[1]
            + ", 75th percentile: " + quantiles[2];
    ```

    The results are as follows. This means that 25% of all the doubles on edge A-B had value less than -0.66, 50% had value less than -0.01 and 75% had value less than 0.64 (the results of the estimation are not deterministic so there may be small differences between the values below and those just quoted).

    ```
    Edge A-B with percentiles of double property - 25th percentile: -0.6630847714290219, 50th percentile: -0.01745655491115906, 75th percentile: 0.6186156511540916
    ```

    We can also get the cumulative density predicate of the distribution of the doubles:

    ```java
    final GetElements query2 = new GetElements.Builder()
            .input(new EdgeSeed("A", "B", DirectedType.UNDIRECTED))
            .build();
    final Element edge2;
    try (final Iterable<? extends Element> edges2 = graph.execute(query2, user)) {
        edge2 = edges2.iterator().next();
    }
    final DoublesSketch doublesSketch2 = (DoublesSketch) edge2.getProperty("doublesSketch");
    final double[] cdf = doublesSketch2.getCDF(new double[]{0.0D, 1.0D, 2.0D});
    final String cdfEstimate = "Edge A-B with CDF values at 0: " + cdf[0]
            + ", at 1: " + cdf[1]
            + ", at 2: " + cdf[2];
    ```

    The results are:

    ```
    Edge A-B with CDF values at 0: 0.51, at 1: 0.844, at 2: 0.986

    ```

### ReservoirItemsSketch

This example demonstrates how the ReservoirItemsSketch<String> sketch property from the Datasketches library can be used to maintain estimates of properties on vertices and edges.

??? example "Example storing estimates of properties using ReservoirItemsSketch"

    The ReservoirItemsSketch<String> sketch allows a sample of a set of strings to be maintained. We give two examples of this. The first is if when an edge is observed there is a string property associated to it, and there are a lot of different values of that string. We may not want to store all the different values of the string, but we may want to see a random sample of them. The second example is to store on an Entity a sketch which gives a sample of the vertices that are connected to the vertex. Even if we are storing all the edges then producing a random sample of the vertices attached to a vertex may not be quick (for example if a vertex has degree 10,000 then producing a sample of a random 10 neighbours would require scanning all the edges - storing the sketch on the Entity means that the sample will be precomputed and can be returned without scanning the edges).

    #### Elements schema
    This is our new elements schema. The edge has a property called 'stringsSample'. This will store the ReservoirItemsSketch<String> object. The entity has a property called 'neighboursSample'. This will also store a ReservoirItemsSketch<String> object.

    ```json
    {
      "entities": {
        "blueEntity": {
          "vertex": "vertex.string",
          "properties": {
            "neighboursSample": "reservoir.strings.sketch"
          }
        }
      },
      "edges": {
        "red": {
          "source": "vertex.string",
          "destination": "vertex.string",
          "directed": "false",
          "properties": {
            "stringsSample": "reservoir.strings.sketch"
          }
        },
        "blue": {
          "source": "vertex.string",
          "destination": "vertex.string",
          "directed": "false"
        }
      }
    }
    ```

    #### Types schema
    We have added a new type - 'reservoir.strings.sketch'. This is a [`org.apache.datasketches.sampling.ReservoirItemsSketch`](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/sampling/ReservoirItemsSketch.java) object.
    We also added in the serialiser and aggregator for the ReservoirItemsSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.


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
        "reservoir.strings.sketch": {
          "class": "org.apache.datasketches.sampling.ReservoirItemsSketch",
          "aggregateFunction": {
            "class": "uk.gov.gchq.gaffer.sketches.datasketches.sampling.binaryoperator.ReservoirItemsSketchAggregator"
          },
          "serialiser": {
            "class": "uk.gov.gchq.gaffer.sketches.datasketches.sampling.serialisation.ReservoirStringsSketchSerialiser"
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

    An edge A-B of group "red" was added to the graph 1000 times. Each time it had the stringsSample property containing a randomly generated string. Here is the edge:
    ```
    Edge[source=A,destination=B,directed=false,matchedVertex=SOURCE,group=red,properties=Properties[stringsSample=<org.apache.datasketches.sampling.ReservoirItemsSketch>
    ### ReservoirItemsSketch SUMMARY: 
      k            : 20
      n            : 1000
      Current size : 20
      Resize factor: X8
    ### END SKETCH SUMMARY
    ]]
    ```

    This is not very illuminating as this just shows the default `toString()` method on the sketch. To get value from it we need to call a method on the ReservoirItemsSketch object:

    ```java
    final GetElements query = new GetElements.Builder()
            .input(new EdgeSeed("A", "B", DirectedType.UNDIRECTED))
            .build();
    final Element edge;
    try (final Iterable<? extends Element> edges = graph.execute(query, user)) {
        edge = edges.iterator().next();
    }
    final ReservoirItemsSketch<String> stringsSketch = ((ReservoirItemsSketch<String>) edge.getProperty("stringsSample"));
    final String[] samples = stringsSketch.getSamples();
    final StringBuilder sb = new StringBuilder("10 samples: ");
    for (int i = 0; i < 10 && i < samples.length; i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append(samples[i]);
    }
    ```

    The results contain a random sample of the strings added to the edge:
    ```
    10 samples: BGCAJGHCHI, ICHBADBEGE, JBHJAEDCBC, GEFEEDCIFF, BJHCHJDIBG, ECEEICCGJI, GHCIFGFGHH, BIFHFEBADF, GACEICJBFH, GAGJCCDHFB
    ```

    500 edges of group "blue" were also added to the graph (edges X-Y0, X-Y1, ..., X-Y499). For each of these edges, an Entity was created for both the source and destination. Each Entity contained a 'neighboursSample' property that contains the vertex at the other end of the edge. We now get the Entity for the vertex X and display the sample of its neighbours:

    ```java
    final GetElements query2 = new GetElements.Builder()
            .input(new EntitySeed("X"))
            .view(new View.Builder()
                    .entity("blueEntity")
                    .build())
            .build();
    final Element entity;
    try (final Iterable<? extends Element> entities = graph.execute(query2, user)) {
        entity = entities.iterator().next();
    }
    final ReservoirItemsSketch<String> neighboursSketch = ((ReservoirItemsSketch<String>) entity.getProperty("neighboursSample"));
    final String[] neighboursSample = neighboursSketch.getSamples();
    sb.setLength(0);
    sb.append("10 samples: ");
    for (int i = 0; i < 10 && i < neighboursSample.length; i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append(neighboursSample[i]);
    }
    ```

    The results are:

    ```
    10 samples: Y315, Y66, Y212, Y109, Y262, Y161, Y296, Y213, Y95, Y174
    ```

### ThetaSketch

This example demonstrates how the ThetaSketch (`org.apache.datasketches.theta.Sketch`) sketch property from the Datasketches library can be used to maintain estimates of the cardinalities of sets.

??? example "Example storing estimates of the cardinalities of sets using ThetaSketch"

    This sketch is similar to a HyperLogLogPlusPlus, but it can also be used to estimate the size of the intersections of sets. We give an example of how this can be used to monitor the changes to the number of edges in the graph over time.

    #### Elements schema
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

    #### Types schema
    We have added a new type - 'thetasketch'. This is a [`org.apache.datasketches.theta.Sketch`](https://github.com/apache/datasketches-java/blob/4.0.0/src/main/java/org/apache/datasketches/theta/Sketch.java) object.
    We also added in the serialiser and aggregator for the Union object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.

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
          "class": "org.apache.datasketches.theta.Sketch",
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
    Entity[vertex=graph,group=size,properties=Properties[size=<org.apache.datasketches.theta.DirectCompactOrderedSketch>
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
    ,endDate=<java.util.Date>Wed Jan 11 00:00:00 GMT 2017,startDate=<java.util.Date>Tue Jan 10 00:00:00 GMT 2017]]
    Entity[vertex=graph,group=size,properties=Properties[size=<org.apache.datasketches.theta.DirectCompactOrderedSketch>
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
    ,endDate=<java.util.Date>Tue Jan 10 00:00:00 GMT 2017,startDate=<java.util.Date>Mon Jan 09 00:00:00 GMT 2017]]
    ```

    This is not very illuminating as this just shows the default `toString()` method on the sketch. To get value from it we need to call a method on the Sketch object:

    ```java
    final GetAllElements getAllEntities2 = new GetAllElements.Builder()
            .view(new View.Builder()
                    .entity("size")
                    .build())
            .build();
    final Iterable<? extends Element> allEntities2 = graph.execute(getAllEntities2, user);
    final Iterable<? extends Element> it = allEntities2.iterator();
    final Element entityDay1 = it.next();
    final Sketch sketchDay1 = ((Sketch) entityDay1.getProperty("size"));
    final Element entityDay2 = it.next();
    final Sketch sketchDay2 = ((Sketch) entityDay2.getProperty("size"));
    final double estimateDay1 = sketchDay1.getEstimate();
    final double estimateDay2 = sketchDay2.getEstimate();
    ```

    The result is:
    ```
    500.0
    1000.0
    ```

    Now we can get an estimate for the number of edges in common across the two days:

    ```java
    final Intersection intersection = Sketches.setOperationBuilder().buildIntersection();
    intersection.update(sketchDay1);
    intersection.update(sketchDay2);
    final double intersectionSizeEstimate = intersection.getResult().getEstimate();
    ```

    The result is:
    ```
    250.0
    ```

    We now get an estimate for the number of edges in total across the two days, by simply aggregating overall the properties:

    ```java
    final GetAllElements getAllEntities = new GetAllElements.Builder()
            .view(new View.Builder()
                    .entity("size", new ViewElementDefinition.Builder()
                            .groupBy() // set the group by properties to 'none'
                            .build())
                    .build())
            .build();
    final Element entity;
    try (final Iterable<? extends Element> allEntities = graph.execute(getAllEntities, user)) {
        entity = allEntities.iterator().next();
    }
    final double unionSizeEstimate = ((Sketch) entity.getProperty("size")).getEstimate();
    ```

    The result is:

    ```
    1250.0
    ```