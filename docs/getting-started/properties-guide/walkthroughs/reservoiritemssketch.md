# ReservoirItemsSketch

The code for this example is [ReservoirItemsSketchWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/ReservoirItemsSketchWalkthrough.java).

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


{% codetabs name="Java", type="java" -%}
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
{%- endcodetabs %}


The results contain a random sample of the strings added to the edge:
```
10 samples: DHACBDGJDA, DDEIHBDICC, FDAJAJFFIE, FJEFHIDEFD, EEGHFHCEJG, IDGIEJGFIJ, DGEJBBHAJF, EBFJFIIJIA, GAGABEAIJC, EHDFJFCIHA

```

500 edges of group "blue" were also added to the graph (edges X-Y0, X-Y1, ..., X-Y499). For each of these edges, an Entity was created for both the source and destination. Each Entity contained a 'neighboursSample' property that contains the vertex at the other end of the edge. We now get the Entity for the vertex X and display the sample of its neighbours:


{% codetabs name="Java", type="java" -%}
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
{%- endcodetabs %}


The results are:

```
10 samples: Y50, Y81, Y247, Y267, Y286, Y0, Y295, Y375, Y462, Y47

```
