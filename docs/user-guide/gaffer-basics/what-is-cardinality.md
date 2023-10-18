# Cardinality

This page describes what cardinality is, how to add it to your Gaffer graph and how to use it in
Gaffer queries.

## What is Cardinality?

In Gaffer, cardinality represents the number of unique vertices that are connected to a given
vertex.

``` mermaid
graph TD
  1(1, cardinality=4) --> 2
  1 --> 3
  1 --> 4
  1 --> 5
  3(3, cardinality=2) --> 5
  3 --> 5
  3 --> 5
  3 --> 5
```

For very large graphs, updating this number accurately would be very costly in compute. This is
because for each new Edge that is added, we would have to check both connected Entities to see if
they are already connected to the other Entity, and this could be costly for Entities with a high
cardinality. Instead, Gaffer uses approximate cardinality making use of a [HyperLogLog
Sketch](https://datasketches.apache.org/docs/HLL/HLL.html), which estimates the cardinality with
relatively low error. In Gaffer, where you see the term "cardinality" used, it is referring to this
approximate cardinality backed by a sketch.

## How to add cardinality to your graph

You will need to add a property to your schema that will represent the approximate cardinality of an
Entity. This property is usually added to a specific Entity group that exists solely to represent
the Cardinality of a given vertex value. An example of the schema changes can be seen in the
[advanced properties guide](../../reference/properties-guide/advanced.md#hllsketch). If you are
using an Accumulo or Map store as your data store, this should be all that is needed. However, if
you are using a custom store, or a custom rest API, some additional config is needed.

!!! tip
    It is often useful keep track of cardinality per edge group. This is usually done with an edge
    group property which is group in the `groupBy`.

    ``` json
    {
      "entities": {
        "cardinality": {
          "vertex": "vertex.string",
          "properties": {
            "approxCardinality": "hll",
            "edgeGroup": "set"
          },
          "groupBy": [
            "edgeGroup"
          ]
        }
      }
    }
    ```

??? info "Additional config"
    If you are using a custom data store, you will need to make sure the `SketchesJsonModules` is
    added to your store properties. This can be easily done by changing the `store.properties` file,
    as shown below. Alternatively, it can be hardcoded into the store, like in the
    [AccumuloProperties](https://github.com/gchq/Gaffer/blob/a91ce4cd1e04dd0a2dfdf9633b513768fccd3507/store-implementation/accumulo-store/src/main/java/uk/gov/gchq/gaffer/accumulostore/AccumuloProperties.java#L468).

    ```properties
    gaffer.serialiser.json.modules=uk.gov.gchq.gaffer.sketches.serialisation.json.SketchesJsonModules
    ```

    If you are using a custom data store, or you not using the standard spring-rest Gaffer rest API,
    then you will also need to ensure that the `sketches-library` dependency is added to your
    `pom.xml` for the store and/or rest API.

    ```xml
    <dependency>
        <groupId>uk.gov.gchq.gaffer</groupId>
        <artifactId>sketches-library</artifactId>
        <version>${gaffer.version}</version>
    </dependency>
    ```

## How to add data with cardinality in your schema

There are two main methods of adding cardinality elements in Gaffer. The first is to do it manually
when you add your edges, the second is to use a generator that can do it for you.

### Manually adding cardinality entities

Adding a cardinality entity between vertex 'A' and 'B' using `AddElements`. For
more examples of different property types, see the sketches on the [developer
guide](../../development-guide/rest-api-sketches.md#primitive-data-types-over-json)
page.

=== "Java"

    ``` java
    final HllSketch hll = new HllSketch(10); //(1)!
    hll.update("B"); //(2)!
    new AddElements.Builder()
                .input(new Entity.Builder()
                                .group("cardinality")
                                .vertex("A")
                                .property("approxCardinality", hll) //(3)!
                                .build())
                .build();
    ```

    1. Create the HllSketch object and define the precision values. By default, logK = 10.
    2. Update the sketch with the connected entity's vertex value, this adds it to the HllSketch's
       bit hash.
    3. When adding the cardinality entity, set the property.

=== "JSON"

    ``` json
    {
        "class": "AddElements",
        "input": [{
            "class": "Entity",
            "vertex": "A",
            "group": "cardinality",
            "properties": {
                "approxCardinality": {
                    "org.apache.datasketches.hll.HllSketch": {
                        "values": ["B"] //(1)!
                    }
                }
            }
        }]
    }
    ```

    1. You can directly add the values in the json and the deserialiser and aggregator will ensure
       it is properly added to the object.

=== "Python"

    ```python
    g.AddElements(
        input=[
            g.Entity(
                vertex="A",
                group="cardinality",
                properties={
                    "hll": g.hll_sketch(["B"]) #(1)!
                }
            )
        ]
    )
    ```

    1. The `g.hll_sketch` helper function lets you directly add the values. The deserialiser and
       aggregator will ensure it is properly added to the object.

### Automatically adding cardinality entities

Rather than using the `AddElements` operation to manually add cardinality entities, you can add them
automatically when you add edges to the graph using the
[`HllSketchEntityGenerator`](https://github.com/gchq/Gaffer/blob/a91ce4cd1e04dd0a2dfdf9633b513768fccd3507/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/HllSketchEntityGenerator.java).
This will take an edge and return the same edge, as well as two cardinality entities, each
representing the additional cardinality link between the two vertices in the edge.

=== "Java"

    ```java
    new OperationChain.Builder()
            .first(new GenerateElements.Builder<Element>()
                    .input(new Edge("edgeGroup1", "A", "B", true)) //(1)!
                    .generator(new HllSketchEntityGenerator() //(2)!
                        .cardinalityPropertyName("approxCardinality") //(3)!
                        .group("cardinality") //(4)!
                        .edgeGroupProperty("edgeGroup") //(5)!
                        .propertiesToCopy(...) //(6)!
                    )
                    .build())
            .then(new AddElements()) //(7)!
            .build();
    ```

    1. The input of edges is added to the OperationChain. Here we make one Edge between "A" and "B",
       with group = "edgeGroup".
    2. The input is streamed into the `HllSketchEntityGenerator`, which will return the edge as well
       as the two cardinality entities.
    3. The name of the property where we should store the cardinality.
    4. The group which the cardinality entity should be put into.
    5. If you track cardinality per edge group using a set property (as described
       [above](#how-to-add-cardinality-to-your-graph)), which property should track this.
    6. Any properties from the edge to copy onto the cardinality entity.
    7. The results are streamed into `AddElements` to be added to the graph.

=== "JSON"

    ```json
    {
        "class": "OperationChain",
        "operations": [
            {
                "class": "GenerateElements",
                "input": [{
                    "class": "Edge",
                    "group": "edgeGroup1",
                    "source": "A",
                    "destination": "B",
                    "directed": true
                }],
                "elementGenerator": {
                    "class": "HllSketchEntityGenerator",
                    "cardinalityPropertyName": "approxCardinality",
                    "edgeGroupProperty": "edgeGroup",
                    "group": "cardinality"
                }
            },
            {
                "class": "AddElements"
            }
        ]
    }
    ```

=== "Python"

    ```python
    g.OperationChain([
        g.GenerateElements(
            input=[g.Edge("edgeGroup1", "A", "B", True)],
            element_generator=g.HllSketchEntityGenerator(
                cardinality_property_name="approxCardinality",
                group="cardinality",
                edge_group_property="edgeGroup"
            )
        ),
        g.AddElements()
    ])
    ```

## How to get cardinality back using Gaffer queries

Depending on how you query Gaffer, approximate cardinality will be displayed in results in different ways.

=== "Java"

    ```java
    final GetElements query = new GetElements.Builder()
            .input(new EntitySeed("A"))
            .build();

    final Element element;
    try (final CloseableIterable<? extends Element> elements = graph.execute(query, user)) {
        element = elements.iterator().next();
    }

    final HllSketch hllSketch = (HllSketch) element.getProperty("approxCardinality");
    final double approxCardinality = hllSketch.getEstimate();
    final String cardinalityEstimate = "Entity A has approximate cardinality " + approxCardinality;
    ```

    Result:

    ```txt
    Entity A has approximate cardinality 1.0
    ```

=== "JSON"

    ```json
    {
        "class": "GetElements",
        "input": [
            {
                "class": "EntitySeed",
                "vertex": "A"
            }
        ]
    }
    ```

    Result:

    ```json
    [{
        "class": "Entity",
        "group": "cardinality",
        "vertex": "A",
        "properties": {
            "approxCardinality": {
                "org.apache.datasketches.hll.HllSketch": {
                    "bytes": "AgEHCgMIAQBejtgF", "cardinality": 1.0
                }
            }
        }
    }]
    ```

=== "Python"

    ```python
    g.GetElements(g.EntitySeed("A"))
    ```

    Result:

    ```python
    [{
        'class': 'uk.gov.gchq.gaffer.data.element.Entity',
        'group': 'BasicEntity',
        'vertex': 'A',
        'properties': {
            'approxCardinality': {
                'org.apache.datasketches.hll.HllSketch': {
                    'bytes': 'AgEHCgMIAQBejtgF',
                    'cardinality': 1.0
                }
            }
        }
    }]
    ```
