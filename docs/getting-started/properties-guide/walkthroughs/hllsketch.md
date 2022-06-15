# HllSketch

The code for this example is [HllSketchWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/HllSketchWalkthrough.java).

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
  Hll Target     : HLL_4
  Current Mode   : HLL
  LB             : 986.8136434119266
  Estimate       : 1018.8398354963819
  UB             : 1052.991638617674
  OutOfOrder Flag: true
  CurMin         : 0
  NumAtCurMin    : 374
  HipAccum       : 1007.7235730289277
  KxQ0           : 562.4995727539062
  KxQ1           : 0.0
]]

```

This is not very illuminating as this just shows the default `toString()` method on the sketch.

We can fetch the cardinality for the vertex using the following code:


{% codetabs name="Java", type="java" -%}
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
{%- endcodetabs %}


The results are as follows. As an Entity was added 1000 times, each time with a different vertex, then we would expect the degree to be approximately 1000.

```
Entity A has approximate degree 1018.8398354963819

```
