# HyperLogLogPlus

The code for this example is [HyperLogLogPlusWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/HyperLogLogPlusWalkthrough.java).

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
Entity[vertex=A,group=cardinality,properties=Properties[approxCardinality=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@2c040bc]]

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
final HyperLogLogPlus hyperLogLogPlus = (HyperLogLogPlus) element.getProperty("approxCardinality");
final double approxDegree = hyperLogLogPlus.cardinality();
final String degreeEstimate = "Entity A has approximate degree " + approxDegree;
{%- endcodetabs %}


The results are as follows. As an Entity was added 1000 times, each time with a different vertex, then we would expect the degree to be approximately 1000.

```
Entity A has approximate degree 1113.0

```
