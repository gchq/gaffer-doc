# Properties Guide
1. [Introduction](#introduction)
2. [Running the Examples](#runningtheexamples)
3. [Simple properties](#simpleproperties)
4. [Sketches](#sketches)
5. [Timestamps](#timestamps)
6. [Walkthroughs](#walkthroughs)
   1. [HyperLogLogPlus](#hyperloglogplus)
   2. [HllSketch](#hllsketch)
   3. [LongsSketch](#longssketch)
   4. [DoublesSketch](#doublessketch)
   5. [ReservoirItemsSketch](#reservoiritemssketch)
   6. [ThetaSketch](#thetasketch)
   7. [RBMBackedTimestampSet](#rbmbackedtimestampset)
   8. [BoundedTimestampSet](#boundedtimestampset)
7. [Predicates, aggregators and serialisers](#predicatesaggregatorsserialisers)
   1. [String](#string)
   2. [Long](#long)
   3. [Integer](#integer)
   4. [Double](#double)
   5. [Float](#float)
   6. [Byte[]](#byte[])
   7. [Boolean](#boolean)
   8. [Date](#date)
   9. [TypeValue](#typevalue)
   10. [TypeSubTypeValue](#typesubtypevalue)
   11. [FreqMap](#freqmap)
   12. [HashMap](#hashmap)
   13. [TreeSet](#treeset)
   14. [HyperLogLogPlus](#hyperloglogplus)
   15. [HllSketch](#hllsketch)
   16. [LongsSketch](#longssketch)
   17. [DoublesSketch](#doublessketch)
   18. [ReservoirItemsSketch](#reservoiritemssketch)
   19. [Sketch](#sketch)
   20. [RBMBackedTimestampSet](#rbmbackedtimestampset)
   21. [BoundedTimestampSet](#boundedtimestampset)


## Introduction 

Gaffer allows properties to be stored on Entities and Edges. As well as simple properties, such as a String or Integer, Gaffer allows rich properties such as sketches and sets of timestamps to be stored on Elements. Gaffer's ability to continuously aggregate properties on elements allows interesting, dynamic data structures to be stored within the graph. Examples include storing a HyperLogLog sketch on an Entity to give a very quick estimate of the degree of a node or storing a uniform random sample of the timestamps that an edge was seen active.

Gaffer allows any Java object to be used as a property. If the property is not natively supported by Gaffer, then you will need to provide a serialiser, and possibly an aggregator.

The properties that Gaffer natively supports can be divided into three categories:

- Standard simple Java properties.
- Sketches from the clearspring and datasketches libraries.
- Sets of timestamps.

This documentation gives some examples of how to use all of the above types of property.

## Running the Examples

The example can be run in a similar way to the user and developer examples. 

You can download the doc-jar-with-dependencies.jar from [maven central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22uk.gov.gchq.gaffer%22%20AND%20a%3A%22doc%22). Select the latest version and download the jar-with-dependencies.jar file.
Alternatively you can compile the code yourself by running a "mvn clean install -Pquick". The doc-jar-with-dependencies.jar file will be located here: doc/target/doc-jar-with-dependencies.jar.

```bash
# Replace <DoublesUnion> with your example name.
java -cp doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.properties.walkthrough.DoublesSketchWalkthrough
```
## Simple properties

Gaffer supports the storage of some common Java objects as properties on entities and edges. These include Integer, Long, Double, Float, Boolean, Date, String, byte[] and TreeSet<String>. Serialisers for these will automatically be added to your schema when you create a graph using a schema that uses these properties. Aggregators for these properties are provided by the [Koryphe](https://github.com/gchq/koryphe) library and include all the standard functions such as minimum, maximum, sum, etc.

Gaffer also provides a `FreqMap` property. This is a map from string to long.

The [Getting started](https://github.com/gchq/Gaffer/wiki/Getting-Started) documentation includes examples of how to use these properties.
## Sketches

A sketch is a compact data structure that gives an approximate answer to a question. For example, a HyperLogLog sketch can estimate the cardinality of a set with billions of elements with a small relative error, using orders of magnitude less storage than storing the full set.

Gaffer allows sketches to be stored on Entities and Edges. These sketches can be continually updated as new data arrives. Here are some example applications of sketches in Gaffer:

- Using a HyperLogLogPlusPlus sketch to provide a very quick estimate of the degree of a node.
- Using a quantiles sketch to estimate the median score associated to an edge, or the 99th percentile of the scores seen on an edge.
- Using a reservoir items sketch to store a sample of all the distinct labels associated to an edge.
- Using theta sketches to estimate the number of distinct edges seen on a particular day, the number seen on the previous day and the overlap between the two days.

Gaffer provides serialisers and aggregators for sketches from two different libraries: the [Clearspring](https://github.com/addthis/stream-lib) library and the [Datasketches](https://datasketches.github.io/) library.

For the Clearspring library, a serialiser and an aggregator is provided for the [`HyperLogLogPlus`](https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/stream/cardinality/HyperLogLogPlus.java) sketch. This is an implementation of the HyperLogLog++ algorithm described [in this paper](http://static.googleusercontent.com/external_content/untrusted_dlcp/research.google.com/en/us/pubs/archive/40671.pdf).

For the Datasketches library, serialisers and aggregators are provided for several sketches. These sketches include:

- [HyperLogLog sketches](https://datasketches.github.io/docs/HLL/HLL.html) for estimating the cardinality of a set (see class [com.yahoo.sketches.hll.HllSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/hll/HllSketch.java));
- [Frequency sketches](https://datasketches.github.io/docs/FrequentItems/FrequentItemsOverview.html) for estimating the frequencies of items such as longs and strings respectively (see for example class [com.yahoo.sketches.frequencies.LongsSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/frequencies/LongsSketch.java));
- [Quantile sketches](https://datasketches.github.io/docs/Quantiles/QuantilesOverview.html) for estimating the quantiles of doubles or strings seen on an element (see for example class [com.yahoo.sketches.quantiles.DoublesSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/quantiles/DoublesSketch.java));
- [Sampling sketches](https://datasketches.github.io/docs/Sampling/ReservoirSampling.html) for maintaining samples of items seen on an element (see for example class [com.yahoo.sketches.sampling.ReservoirItemsSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/sampling/ReservoirItemsSketch.java));
- [Theta sketches](https://datasketches.github.io/docs/Theta/ThetaSketchFramework.html) for estimating the union and intersection of sets (see for example class [com.yahoo.sketches.theta.Sketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/theta/Sketch.java)).

Most of the Datasketches sketches come in two forms: a standard sketch form and a "union" form. The latter is technically not a sketch. It is an operator that allows efficient union operations of two sketches. It also allows updating the sketch with individual items. In order to obtain estimates from it, it is necessary to first obtain a sketch from it, using a method called `getResult()`. There are some interesting trade-offs in the serialisation and aggregation speeds between the sketches and the unions. If in doubt, use the standard sketches. Examples are provided for the standard sketches, but not for the unions.

## Timestamps

Gaffer contains a [time-library](https://github.com/gchq/Gaffer/tree/master/library/time-library) containing some simple properties which allow sets of timestamps to be stored on entities and edges. There are two properties:

- [`RBMBackedTimestampSet`](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/RBMBackedTimestampSet.java): When this is created, a [TimeBucket](https://github.com/gchq/Gaffer/blob/master/core/common-util/src/main/java/uk/gov/gchq/gaffer/commonutil/CommonTimeUtil.java) is specified, e.g. minute. When a timestamp is added, it is truncated to the nearest start of a bucket (e.g. if the bucket is a minute then the seconds are removed) and then added to the set. Internally the timestamps are stored in a [Roaring Bitmap](http://roaringbitmap.org/).
- [`BoundedTimestampSet`](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/BoundedTimestampSet.java): This is similar to the above set, except that when it is created a maximum size is specified. If the set grows beyond the maximum size, then a random sample of the timestamps of that size is created. This is useful to avoid the size of the set for entities or edges that are very busy growing too large. By retaining a sample, we maintain an indication of when the entity or edge was active, without retaining all the information. The sample is implemented using a [ReservoirLongsUnion](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/sampling/ReservoirLongsUnion.java) from the [Datasketches](https://datasketches.github.io/) library.

## Walkthroughs

This section contains examples that show how to use some of the properties described above.

### HyperLogLogPlus

The code for this example is [HyperLogLogPlusWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/HyperLogLogPlusWalkthrough.java).

This example demonstrates how the [HyperLogLogPlus](https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/stream/cardinality/HyperLogLogPlus.java) sketch from the Clearspring library can be used to maintain an estimate of the degree of a vertex. Every time an edge A -> B is added to graph, we also add an Entity for A with a property of a HyperLogLogPlus containing B, and an Entity for B with a property of a HyperLogLogPlus containing A. The aggregator for the HyperLogLogPluses merges them together so that after querying for the Entity for vertex X the HyperLogLogPlus property gives us an estimate of the approximate degree.

##### Elements schema
This is our new elements schema. The edge has a property called 'approx_cardinality'. This will store the HyperLogLogPlus object.


```json
{
  "entities": {
    "cardinality": {
      "vertex": "vertex.string",
      "properties": {
        "approxCardinality": "hyperloglogplus"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'hyperloglogplus'. This is a [com.clearspring.analytics.stream.cardinality.HyperLogLogPlus](https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/stream/cardinality/HyperLogLogPlus.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/HyperLogLogPlusSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/binaryoperator/HyperLogLogPlusAggregator.java) for the HyperLogLogPlus object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new entities are added to the graph.


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
    "hyperloglogplus": {
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


Only one entity is in the graph. This was added 1000 times, and each time it had the 'approxCardinality' property containing a vertex that A had been seen in an Edge with. Here is the Entity:

```
Entity[vertex=A,group=cardinality,properties=Properties[approxCardinality=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@65cc5252]]

```

This is not very illuminating as this just shows the default `toString()` method on the sketch.

We can fetch the cardinality for the vertex using the following code:

```java
final GetElements query = new GetElements.Builder()
        .input(new EntitySeed("A"))
        .build();
final Element element;
try (final CloseableIterable<? extends Element> elements = graph.execute(query, user)) {
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

The code for this example is [HllSketchWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/HllSketchWalkthrough.java).

This example demonstrates how the [HllSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/hll/HllSketch.java) sketch from the Data Sketches library can be used to maintain an estimate of the degree of a vertex. Every time an edge A -> B is added to graph, we also add an Entity for A with a property of a HllSketch containing B, and an Entity for B with a property of a HllSketch containing A. The aggregator for the HllSketches merges them together so that after querying for the Entity for vertex X the HllSketch property would give us an estimate of the approximate degree.

##### Elements schema
This is our new elements schema. The edge has a property called 'approx_cardinality'. This will store the HllSketch object.


```json
{
  "entities": {
    "cardinality": {
      "vertex": "vertex.string",
      "properties": {
        "approxCardinality": "hllsketch"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'hllsketch'. This is a [com.yahoo.sketches.hll.HllSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/hll/HllSketch.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/serialisation/HllSketchSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/develop/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/binaryoperator/HllSketchAggregator.java) for the HllSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new entities are added to the graph.


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
    "hllsketch": {
      "class": "com.yahoo.sketches.hll.HllSketch",
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


Only one entity is in the graph. This was added 1000 times, and each time it had the 'approxCardinality' property containing a vertex that A had been seen in an Edge with. Here is the Entity:

```
Entity[vertex=A,group=cardinality,properties=Properties[approxCardinality=<com.yahoo.sketches.hll.HllSketch>### HLL SKETCH SUMMARY: 
  Log Config K   : 10
  Hll Target     : HLL_8
  Current Mode   : HLL
  LB             : 986.7698164613868
  Estimate       : 1018.8398354963819
  UB             : 1053.0644294536246
  OutOfOrder Flag: true
  CurMin         : 0
  NumAtCurMin    : 374
  HipAccum       : 1007.7235730289277
]]

```

This is not very illuminating as this just shows the default `toString()` method on the sketch.

We can fetch the cardinality for the vertex using the following code:

```java
final GetElements query = new GetElements.Builder()
        .input(new EntitySeed("A"))
        .build();
final Element element;
try (final CloseableIterable<? extends Element> elements = graph.execute(query, user)) {
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

The code for this example is [LongsSketchWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/LongsSketchWalkthrough.java).

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

```java
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
```

The results are as follows. As 1000 edges were generated with a long randomly sampled from 0 to 9 then the occurrence of each is approximately 100.

```
Edge A-B: 1L seen approximately 106 times, 9L seen approximately 98 times.

```



### DoublesSketch

The code for this example is [DoublesSketchWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/DoublesSketchWalkthrough.java).

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
   Normalized Rank Error        : 1.725%
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
try (final CloseableIterable<? extends Element> edges = graph.execute(query, user)) {
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
Edge A-B with percentiles of double property - 25th percentile: -0.6630847714290219, 50th percentile: -0.009261398929964838, 75th percentile: 0.6341803995604817

```

We can also get the cumulative density predicate of the distribution of the doubles:

```java
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
```

The results are:

```
Edge A-B with CDF values at 0: 0.511, at 1: 0.841, at 2: 0.987

```



### ReservoirItemsSketch

The code for this example is [ReservoirItemsSketchWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/ReservoirItemsSketchWalkthrough.java).

This example demonstrates how the [ReservoirItemsSketch<String>](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/sampling/ReservoirItemsSketch.java) sketch from the Data Sketches library can be used to maintain estimates of properties on vertices and edges. The ReservoirItemsSketch<String> sketch allows a sample of a set of strings to be maintained. We give two examples of this. The first is if when an edge is observed there is a string property associated to it, and there are a lot of different values of that string. We may not want to store all the different values of the string, but we may want to see a random sample of them. The second example is to store on an Entity a sketch which gives a sample of the vertices that are connected to the vertex. Even if we are storing all the edges then producing a random sample of the vertices attached to a vertex may not be quick (for example if a vertex has degree 10,000 then producing a sample of a random 10 neighbours would require scanning all the edges - storing the sketch on the Entity means that the sample will be precomputed and can be returned without scanning the edges).

##### Elements schema
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


##### Types schema
We have added a new type - 'reservoir.strings.sketch'. This is a [com.yahoo.sketches.sampling.ReservoirItemsSketch](https://github.com/DataSketches/sketches-core/blob/master/src/main/java/com/yahoo/sketches/sampling/ReservoirItemsSketch.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/sampling/serialisation/ReservoirStringsSketchSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/sampling/binaryoperator/ReservoirItemsSketchAggregator.java) for the ReservoirItemsSketch object. Gaffer will automatically aggregate these sketches, using the provided aggregator, so they will keep up to date as new edges are added to the graph.


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
      "class": "com.yahoo.sketches.sampling.ReservoirItemsSketch",
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
Edge[source=A,destination=B,directed=false,group=red,properties=Properties[stringsSample=<com.yahoo.sketches.sampling.ReservoirItemsSketch>
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
try (final CloseableIterable<? extends Element> edges = graph.execute(query, user)) {
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
10 samples: HFBDHCCAHE, BIIDBDFFEC, AIJFFADCDD, ACJGDCIIHG, BDDGGACGEI, CBCDJDCFFD, EFFGFEFEDH, FAGIEBEGIJ, GAJIBECFGF, IBBGCHGGDG

```

500 edges of group "blue" were also added to the graph (edges X-Y0, X-Y1, ..., X-Y499). For each of these edges, an Entity was created for both the source and destination. Each Entity contained a 'neighboursSample' property that contains the vertex at the other end of the edge. We now get the Entity for the vertex X and display the sample of its neighbours:

```java
final GetElements query2 = new GetElements.Builder()
        .input(new EntitySeed("X"))
        .build();
final Element entity;
try (final CloseableIterable<? extends Element> entities = graph.execute(query2, user)) {
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
10 samples: Y120, Y267, Y246, Y448, Y244, Y55, Y357, Y256, Y220, Y247

```



### ThetaSketch

The code for this example is [ThetaSketchWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/ThetaSketchWalkthrough.java).

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
,endDate=<java.util.Date>Tue Jan 10 00:00:00 GMT 2017,startDate=<java.util.Date>Mon Jan 09 00:00:00 GMT 2017]]
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
,endDate=<java.util.Date>Wed Jan 11 00:00:00 GMT 2017,startDate=<java.util.Date>Tue Jan 10 00:00:00 GMT 2017]]

```

This is not very illuminating as this just shows the default `toString()` method on the sketch. To get value from it we need to call a method on the Sketch object:

```java
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
```
The result is:
```
1000.0
500.0

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
try (final CloseableIterable<? extends Element> allEntities = graph.execute(getAllEntities, user)) {
    entity = allEntities.iterator().next();
}
final double unionSizeEstimate = ((Sketch) entity.getProperty("size")).getEstimate();
```

The result is:

```
1250.0

```



### RBMBackedTimestampSet

The code for this example is [TimestampSetWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/TimestampSetWalkthrough.java).

This example demonstrates how the [TimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/TimestampSet.java) property can be used to maintain a set of the timestamps at which an element was seen active. In this example we record the timestamps to minute level accuracy, i.e. the seconds are ignored.

##### Elements schema
This is our new elements schema. The edge has a property called 'timestampSet'. This will store the [TimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/TimestampSet.java) object, which is actually a [RBMBackedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/RBMBackedTimestampSet.java).


```json
{
  "edges": {
    "red": {
      "source": "vertex.string",
      "destination": "vertex.string",
      "directed": "false",
      "properties": {
        "timestampSet": "timestamp.set"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'timestamp.set'. This is a [uk.gov.gchq.gaffer.time.RBMBackedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/RBMBackedTimestampSet.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/serialisation/RBMBackedTimestampSetSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/binaryoperator/RBMBackedTimestampSetAggregator.java) for the RBMBackedTimestampSet object. Gaffer will automatically aggregate these sets together to maintain a set of all the times the element was active.


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
    "timestamp.set": {
      "class": "uk.gov.gchq.gaffer.time.RBMBackedTimestampSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.time.binaryoperator.RBMBackedTimestampSetAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.time.serialisation.RBMBackedTimestampSetSerialiser"
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


Only one edge is in the graph. This was added 25 times, and each time it had the 'timestampSet' property containing a randomly generated timestamp from 2017. Here is the Edge:

```
Edge[source=A,destination=B,directed=false,group=red,properties=Properties[timestampSet=<uk.gov.gchq.gaffer.time.RBMBackedTimestampSet>RBMBackedTimestampSet[timeBucket=MINUTE,timestamps=2017-01-08T07:29:00Z,2017-01-18T10:41:00Z,2017-01-19T01:36:00Z,2017-01-31T16:16:00Z,2017-02-02T08:06:00Z,2017-02-12T14:21:00Z,2017-02-15T22:01:00Z,2017-03-06T09:03:00Z,2017-03-21T18:09:00Z,2017-05-08T15:34:00Z,2017-05-10T19:39:00Z,2017-05-16T10:44:00Z,2017-05-23T10:02:00Z,2017-05-28T01:52:00Z,2017-06-24T23:50:00Z,2017-07-27T09:34:00Z,2017-08-05T02:11:00Z,2017-09-07T07:35:00Z,2017-10-01T12:52:00Z,2017-10-23T22:02:00Z,2017-10-27T04:12:00Z,2017-11-01T02:45:00Z,2017-12-11T16:38:00Z,2017-12-22T14:40:00Z,2017-12-24T08:00:00Z]]]

```

You can see the list of timestamps on the edge. We can also get just the earliest, latest and total number of timestamps using methods on the TimestampSet object to get the following results:

```
Edge A-B was first seen at 2017-01-08T07:29:00Z, last seen at 2017-12-24T08:00:00Z, and there were 25 timestamps it was active.

```




### BoundedTimestampSet

The code for this example is [BoundedTimestampSetWalkthrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/BoundedTimestampSetWalkthrough.java).

This example demonstrates how the [BoundedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/BoundedTimestampSet.java) property can be used to maintain a set of the timestamps at which an element was seen active. If this set becomes larger than a size specified by the user then a uniform random sample of the timestamps is maintained. In this example we record the timestamps to minute level accuracy, i.e. the seconds are ignored, and specify that at most 25 timestamps should be retained.

##### Elements schema
This is our new schema. The edge has a property called 'boundedTimestampSet'. This will store the [BoundedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/BoundedTimestampSet.java) object, which is actually a 'BoundedTimestampSet'.

```json
{
  "edges": {
    "red": {
      "source": "vertex.string",
      "destination": "vertex.string",
      "directed": "false",
      "properties": {
        "boundedTimestampSet": "bounded.timestamp.set"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'bounded.timestamp.set'. This is a uk.gov.gchq.gaffer.time.BoundedTimestampSet object. We have added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/serialisation/BoundedTimestampSetSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/binaryoperator/BoundedTimestampSetAggregator.java) for the BoundedTimestampSet object. Gaffer will automatically aggregate these sets together to maintain a set of all the times the element was active. Once the size of the set becomes larger than 25 then a uniform random sample of size at most 25 of the timestamps is maintained.


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
    "bounded.timestamp.set": {
      "class": "uk.gov.gchq.gaffer.time.BoundedTimestampSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.time.binaryoperator.BoundedTimestampSetAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.time.serialisation.BoundedTimestampSetSerialiser"
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


There are two edges in the graph. Edge A-B was added 3 times, and each time it had the 'boundedTimestampSet' property containing a randomly generated timestamp from 2017. Edge A-C was added 1000 times, and each time it also had the 'boundedTimestampSet' property containing a randomly generated timestamp from 2017. Here are the edges:

```
Edge[source=A,destination=B,directed=false,group=red,properties=Properties[boundedTimestampSet=<uk.gov.gchq.gaffer.time.BoundedTimestampSet>BoundedTimestampSet[timeBucket=MINUTE,state=NOT_FULL,maxSize=25,timestamps=2017-02-12T14:21:00Z,2017-03-21T18:09:00Z,2017-12-24T08:00:00Z]]]
Edge[source=A,destination=C,directed=false,group=red,properties=Properties[boundedTimestampSet=<uk.gov.gchq.gaffer.time.BoundedTimestampSet>BoundedTimestampSet[timeBucket=MINUTE,state=SAMPLE,maxSize=25,timestamps=2017-01-17T14:36:00Z,2017-01-29T10:30:00Z,2017-02-03T18:42:00Z,2017-02-15T22:01:00Z,2017-02-27T09:59:00Z,2017-02-28T11:57:00Z,2017-03-16T21:21:00Z,2017-03-28T19:09:00Z,2017-04-14T08:18:00Z,2017-04-16T10:13:00Z,2017-04-19T23:54:00Z,2017-06-19T10:47:00Z,2017-06-25T15:48:00Z,2017-08-12T10:07:00Z,2017-08-17T12:17:00Z,2017-08-27T07:05:00Z,2017-08-28T08:08:00Z,2017-09-11T20:07:00Z,2017-10-20T01:23:00Z,2017-10-30T02:15:00Z,2017-11-01T16:59:00Z,2017-11-10T07:55:00Z,2017-11-12T05:21:00Z,2017-11-30T23:18:00Z,2017-12-03T01:26:00Z]]]

```

You can see that edge A-B has the full list of timestamps on the edge, but edge A-C has a sample of the timestamps.




## Predicates, aggregators and serialisers
### String

Properties class: java.lang.String


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsShorterThan](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html)
- [uk.gov.gchq.koryphe.impl.predicate.MultiRegex](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MultiRegex.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)
- [uk.gov.gchq.koryphe.impl.predicate.Regex](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Regex.html)
- [uk.gov.gchq.koryphe.impl.predicate.StringContains](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/StringContains.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/StringConcat.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.StringDeduplicateConcat](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/StringDeduplicateConcat.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.implementation.StringSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/StringSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.StringParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/StringParquetSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.tostring.StringToStringSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/tostring/StringToStringSerialiser.html)





### Long

Properties class: java.lang.Long


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.AgeOff](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/AgeOff.html)
- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Product](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Product.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Sum](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.LongSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/LongSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedLongSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/ordered/OrderedLongSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawLongSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/CompactRawLongSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.raw.RawLongSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawLongSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.LongParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/LongParquetSerialiser.html)





### Integer

Properties class: java.lang.Integer


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Product](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Product.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Sum](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.IntegerSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/IntegerSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedIntegerSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/ordered/OrderedIntegerSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawIntegerSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/CompactRawIntegerSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.raw.RawIntegerSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawIntegerSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.IntegerParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/IntegerParquetSerialiser.html)





### Double

Properties class: java.lang.Double


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Product](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Product.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Sum](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.DoubleSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/DoubleSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedDoubleSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/ordered/OrderedDoubleSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.raw.RawDoubleSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawDoubleSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.DoubleParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/DoubleParquetSerialiser.html)





### Float

Properties class: java.lang.Float


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Product](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Product.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Sum](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.FloatSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/FloatSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedFloatSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/ordered/OrderedFloatSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.raw.RawFloatSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawFloatSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.FloatParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/FloatParquetSerialiser.html)





### Byte[]

Properties class: [Ljava.lang.Byte;


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsShorterThan](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.implementation.BytesSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/BytesSerialiser.html)





### Boolean

Properties class: java.lang.Boolean


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsFalse](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsFalse.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsTrue](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsTrue.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/And.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Or.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.implementation.BooleanSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/BooleanSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.BooleanParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/BooleanParquetSerialiser.html)





### Date

Properties class: java.util.Date


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.DateSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/DateSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedDateSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/ordered/OrderedDateSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.raw.RawDateSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/RawDateSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.DateParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/DateParquetSerialiser.html)





### TypeValue

Properties class: uk.gov.gchq.gaffer.types.TypeValue


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.TypeValueSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/TypeValueSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.TypeValueParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/TypeValueParquetSerialiser.html)





### TypeSubTypeValue

Properties class: uk.gov.gchq.gaffer.types.TypeSubTypeValue


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Max](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.TypeSubTypeValueSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/TypeSubTypeValueSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.TypeSubTypeValueParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/TypeSubTypeValueParquetSerialiser.html)





### FreqMap

Properties class: uk.gov.gchq.gaffer.types.FreqMap


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsShorterThan](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html)
- [uk.gov.gchq.koryphe.impl.predicate.MapContains](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContains.html)
- [uk.gov.gchq.koryphe.impl.predicate.MapContainsPredicate](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContainsPredicate.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)
- [uk.gov.gchq.koryphe.predicate.PredicateMap](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/predicate/PredicateMap.html)


Aggregators:

- [uk.gov.gchq.gaffer.types.function.FreqMapAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/types/function/FreqMapAggregator.html)
- [uk.gov.gchq.koryphe.binaryoperator.BinaryOperatorMap](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/binaryoperator/BinaryOperatorMap.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.FreqMapSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/FreqMapSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.MapSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/MapSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.FreqMapParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/FreqMapParquetSerialiser.html)





### HashMap

Properties class: java.util.HashMap


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsShorterThan](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html)
- [uk.gov.gchq.koryphe.impl.predicate.MapContains](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContains.html)
- [uk.gov.gchq.koryphe.impl.predicate.MapContainsPredicate](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContainsPredicate.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)
- [uk.gov.gchq.koryphe.predicate.PredicateMap](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/predicate/PredicateMap.html)


Aggregators:

- [uk.gov.gchq.koryphe.binaryoperator.BinaryOperatorMap](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/binaryoperator/BinaryOperatorMap.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.implementation.MapSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/MapSerialiser.html)





### TreeSet

Properties class: java.util.TreeSet


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.AreIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/AreIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.CollectionContains](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/CollectionContains.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsShorterThan](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/CollectionConcat.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.serialisation.implementation.SetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/SetSerialiser.html)
- [uk.gov.gchq.gaffer.serialisation.implementation.TreeSetStringSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/serialisation/implementation/TreeSetStringSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.TreeSetStringParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/TreeSetStringParquetSerialiser.html)





### HyperLogLogPlus

Properties class: com.clearspring.analytics.stream.cardinality.HyperLogLogPlus


Predicates:

- [uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/predicate/HyperLogLogPlusIsLessThan.html)
- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/binaryoperator/HyperLogLogPlusAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/HyperLogLogPlusSerialiser.html)

Other Serialisers:

- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.InLineHyperLogLogPlusParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/InLineHyperLogLogPlusParquetSerialiser.html)
- [uk.gov.gchq.gaffer.parquetstore.serialisation.impl.NestedHyperLogLogPlusParquetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/parquetstore/serialisation/impl/NestedHyperLogLogPlusParquetSerialiser.html)





### HllSketch

Properties class: com.yahoo.sketches.hll.HllSketch


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.sketches.datasketches.cardinality.binaryoperator.HllSketchAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/binaryoperator/HllSketchAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.sketches.datasketches.cardinality.serialisation.HllSketchSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/serialisation/HllSketchSerialiser.html)





### LongsSketch

Properties class: com.yahoo.sketches.frequencies.LongsSketch


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.sketches.datasketches.frequencies.binaryoperator.LongsSketchAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/frequencies/binaryoperator/LongsSketchAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.sketches.datasketches.frequencies.serialisation.LongsSketchSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/frequencies/serialisation/LongsSketchSerialiser.html)





### DoublesSketch

Properties class: com.yahoo.sketches.quantiles.DoublesSketch


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.DoublesSketchAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/quantiles/binaryoperator/DoublesSketchAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.sketches.datasketches.quantiles.serialisation.DoublesSketchSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/quantiles/serialisation/DoublesSketchSerialiser.html)





### ReservoirItemsSketch

Properties class: com.yahoo.sketches.sampling.ReservoirItemsSketch


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.sketches.datasketches.sampling.binaryoperator.ReservoirItemsSketchAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/sampling/binaryoperator/ReservoirItemsSketchAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.sketches.datasketches.sampling.serialisation.ReservoirNumbersSketchSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/sampling/serialisation/ReservoirNumbersSketchSerialiser.html)
- [uk.gov.gchq.gaffer.sketches.datasketches.sampling.serialisation.ReservoirStringsSketchSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/sampling/serialisation/ReservoirStringsSketchSerialiser.html)





### Sketch

Properties class: com.yahoo.sketches.theta.Sketch


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.sketches.datasketches.theta.binaryoperator.SketchAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/theta/binaryoperator/SketchAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.sketches.datasketches.theta.serialisation.SketchSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/datasketches/theta/serialisation/SketchSerialiser.html)





### RBMBackedTimestampSet

Properties class: uk.gov.gchq.gaffer.time.RBMBackedTimestampSet


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.time.binaryoperator.RBMBackedTimestampSetAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/time/binaryoperator/RBMBackedTimestampSetAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.time.serialisation.RBMBackedTimestampSetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/time/serialisation/RBMBackedTimestampSetSerialiser.html)





### BoundedTimestampSet

Properties class: uk.gov.gchq.gaffer.time.BoundedTimestampSet


Predicates:

- [uk.gov.gchq.koryphe.impl.predicate.And](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)
- [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)
- [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)
- [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)
- [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)


Aggregators:

- [uk.gov.gchq.gaffer.time.binaryoperator.BoundedTimestampSetAggregator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/time/binaryoperator/BoundedTimestampSetAggregator.html)
- [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)


To Bytes Serialisers:

- [uk.gov.gchq.gaffer.time.serialisation.BoundedTimestampSetSerialiser](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/time/serialisation/BoundedTimestampSetSerialiser.html)


