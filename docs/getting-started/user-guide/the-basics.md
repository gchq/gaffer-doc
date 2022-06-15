# The Basics

The code for this example is [TheBasics](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/TheBasics.java).

We'll walk through it in some detail.

First, let’s do the most basic thing; take some data from a csv file, load it into a Gaffer graph and then run a very simple query to return some graph edges. 
We'll also look at specific examples of an [ElementGenerator](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/ElementGenerator.html) and [Schema](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/store/schema/Schema.html).

We are going to base the following walkthroughs on Road Traffic data, a simplified version of the [Road Traffic Example](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/README.md). 
Throughout these walkthroughs we will gradually build up the graph, so as we learn about new features we will add them to our Graph schema. 

This is the data we will use, a simple csv in the format road,junctionA,junctionB. Each row represents a vehicle travelling on the road between junctionA and junctionB.

```
M5,10,11
M5,10,11
M5,10,11
M5,11,10
M5,23,24
M5,23,24
M5,28,27

```


The first thing to do when creating a Gaffer Graph is to model your data and write your Gaffer Schema. 
When modelling data for Gaffer you really need to work out what questions you want to ask on the data. 
Gaffer queries are seeded by the vertices, essentially the vertices are indexed. 
For this example we want to be able to ask questions like 'How many vehicles have travelled from junction 10 to junction 11?', so junction needs to be a vertex in the Graph.

The Graph will look something like this (sorry it is not particularly exciting at this point). The number on the edge is the edge count property:

```
    --3->
10         11
    <-1--
 

23  --2->  24
    
    
27  <-1--  28
```

The Schema file can be broken down into small parts, we encourage at least 2 files:

- [elements.json](https://github.com/gchq/gaffer-doc/blob/master/user/src/main/resources/RoadUse/schema/elements.json)
- [types.json](https://github.com/gchq/gaffer-doc/blob/master/user/src/main/resources/RoadUse/schema/types.json)

Splitting the schema up into these 2 files helps to illustrate the different roles that the schemas fulfil.

## The Elements Schema

The Elements Schema is a JSON document that describes the Elements (Edges and Entities) in the Graph. We will start by using this very basic schema:


```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "count": "count.long"
      }
    }
  }
}
```


We have one Edge Group, `"RoadUse"`. The Group simply labels a particular type of Edge defined by its vertex types, directed flag and set of properties.

This edge is a directed edge representing vehicles moving from junction A to junction B.

You can see the `“RoadUse”` Edge has a source and a destination vertex of type `"junction"` and a single property called `"count"` of type `"count.long"`. 
These types are defined in the DataType file.

## The Types Schema

The Types Schema is a JSON document that describes the types of objects used by Elements. Here's the one we'll start with:


```json
{
  "types": {
    "junction": {
      "description": "A road junction represented by a String.",
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
    }
  }
}
```


First we'll look at `"junction"`, the type we specified for our source and destination vertices on the `"RoadUse"` edge. It's defined here as a java.lang.String.

The property `"count"` on the `"RoadUse"` Edges was specified as type `"count.long"`. The definition here says that any object of type `"count.long"` is a java.lang.Long, and we have added a validator that mandates that the count object's value must be greater than or equal to 0.
If we have a `"RoadUse"` Edge with a count that's not a Long or is a Long but has a value less than 0 it will fail validation and won't be added to the Graph.
Gaffer validation is done using [Java Predicates](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)

We also supply a [Sum](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html) [BinaryOperator](https://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html) to aggregate the count.long type.
Gaffer allows Edges of the same Group to be aggregated together. This means that when different vehicles travel from junction 10 to junction 11 the edges will be aggregated together and the count property will represent the total number of vehicles that have travelled between the 2 junctions. 


## Generating Graph Elements

So now we have modelled our data we need to write an Element Generator, an implementation of a [Java Function](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html) to convert each line of our csv file to a RoadUse edge.

Here is our simple Element Generator.


```java
public class RoadUseElementGenerator implements OneToOneElementGenerator<String> {
    @Override
    public Element _apply(final String line) {
        final String[] t = line.split(",");

        // Ignore the road name
        final String junctionA = t[1];
        final String junctionB = t[2];

        return new Edge.Builder()
                .group("RoadUse")
                .source(junctionA)
                .dest(junctionB)
                .directed(true)
                .property("count", 1L)
                .build();
    }
}
```

The `_apply` method takes a line from the data file as a String and returns a Gaffer [Edge](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/Edge.html).

First we take a line from the file as a String and split on `","` to get 3 Strings: (Road,JunctionA,JunctionB).
Then we create a new Edge object with a group `"RoadUse"`. 
We set the source vertex of the Edge to be the first junction and the destination vertex to be the second junction.
Next we set the Edge's directed flag to true, indicating that in this case our Edges are directed (JunctionA->JunctionB != JunctionB->JunctionA).
Finally we add a property called 'count' to our Edge. This is a Long and is going to represent how many vehicles travel between junctionA and junctionB. We're initialising this to be 1, as a single line in our csv data represents 1 vehicle.

In a later example you will see there is a quick way of adding data to Gaffer directly from CSVs but for now we will do it in 2 steps. First we convert the csv to Edges.


{% codetabs name="Java", type="java" -%}
final List<Element> elements = new ArrayList<>();
final RoadUseElementGenerator dataGenerator = new RoadUseElementGenerator();
for (final String line : IOUtils.readLines(StreamUtil.openStream(getClass(), dataPath))) {
    elements.add(dataGenerator._apply(line));
}
{%- endcodetabs %}


This produces these edges:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```

## Creating a Graph

The next thing we do is create an instance of a Gaffer [Graph](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/graph/Graph.html), this is basically just a proxy for your chosen Gaffer Store.
To do this we need to provide 3 things; the schema files we introduced in the previous section, a Graph Configuration and a Store Properties file.

### The Graph Configuration

The graph configuration identifies the graph you are connecting to.
In it's simplest form, this is just a JSON document containing a single String to identify your graph.
For more information please see [Graph Configuration](../../components/core/graph.md#graph-configuration).


```json
{
  "graphId": "graph1"
}
```


### The Store Properties

Here is the Store Properties file required to connect to the Mock Accumulo store:


```properties
gaffer.store.class=uk.gov.gchq.gaffer.accumulostore.SingleUseMockAccumuloStore
accumulo.instance=someInstanceName
accumulo.zookeepers=aZookeeper
accumulo.user=user01
accumulo.password=password

gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.HashMapCacheService

gaffer.store.job.tracker.enabled=true

gaffer.serialiser.json.modules=uk.gov.gchq.gaffer.bitmap.serialisation.json.BitmapJsonModules

gaffer.store.operation.declarations=sparkAccumuloOperationsDeclarations.json,ResultCacheExportOperations.json,ExportToOtherGraphOperationDeclarations.json,ScoreOperationChainDeclaration.json,GetFromEndpointOperationDeclarations.json


```


This contains information specific to the actual instance of the Store you are using. Refer to the documentation for your chosen store for the configurable properties, e.g [Accumulo Store User Guide](../../stores/accumulo-store).
The important property is 'gaffer.store.class' this tells Gaffer the type of store you wish to use to store your data.

### The Graph Object

Now we've got everything we need to create the Graph:


{% codetabs name="Java", type="java" -%}
graph = new Graph.Builder()
        .config(StreamUtil.openStream(getClass(), graphConfigPath))
        .addSchemas(StreamUtil.openStreams(getClass(), schemaPath))
        .storeProperties(StreamUtil.openStream(getClass(), storePropertiesPath))
        .build();
{%- endcodetabs %}


All of the graph configuration files can also be written directly in Java:


{% codetabs name="Java", type="java" -%}
final GraphConfig config = new GraphConfig.Builder()
        .graphId(getClass().getSimpleName())
        .build();

final Schema schema = new Schema.Builder()
        .edge("RoadUse", new SchemaEdgeDefinition.Builder()
                .description("A directed edge representing vehicles moving from junction A to junction B.")
                .source("junction")
                .destination("junction")
                .directed("true")
                .property("count", "count.long")
                .build())
        .type("junction", new TypeDefinition.Builder()
                .description("A road junction represented by a String.")
                .clazz(String.class)
                .build())
        .type("count.long", new TypeDefinition.Builder()
                .description("A long count that must be greater than or equal to 0.")
                .clazz(Long.class)
                .validateFunctions(new IsMoreThan(0L, true))
                .aggregateFunction(new Sum())
                .build())
        .type("true", new TypeDefinition.Builder()
                .description("A simple boolean that must always be true.")
                .clazz(Boolean.class)
                .validateFunctions(new IsTrue())
                .build())
        .build();

final AccumuloProperties properties = new AccumuloProperties();
properties.setStoreClass(SingleUseMockAccumuloStore.class);
properties.setInstance("instance1");
properties.setZookeepers("zookeeper1");
properties.setUser("user01");
properties.setPassword("password");

graph = new Graph.Builder()
        .config(config)
        .addSchema(schema)
        .storeProperties(properties)
        .build();
{%- endcodetabs %}


## Loading and Querying Data

Now we've generated some Graph Edges and created a Graph, let's put the Edges in the Graph.

Before we can do anything with our graph, we need to create a user. In production systems, it will be used to hold the security authorisations of the user, but for now, we just need a String to identify the user interacting with the Graph:


{% codetabs name="Java", type="java" -%}
final User user = new User("user01");
{%- endcodetabs %}


That user can then add our Edges to the Graph. To interact with a Gaffer Graph we use an [Operation](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/Operation.html). In this case our Operation is [AddElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html).


{% codetabs name="Java", type="java" -%}
final AddElements addElements = new AddElements.Builder()
        .input(elements)
        .build();
graph.execute(addElements, user);
{%- endcodetabs %}


Finally, we run a query to return all Edges in our Graph that contain the vertex "10". To do this we use a [GetElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) Operation.


{% codetabs name="Java", type="java" -%}
final GetElements query = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .build();
final CloseableIterable<? extends Element> results = graph.execute(query, user);
{%- endcodetabs %}


## Summary

In this example we've taken some simple pairs of integers in a file and, using an ElementGenerator, converted them into Gaffer Graph Edges with a `”count”` property.

Then we loaded the Edges into a Gaffer Graph backed by a MockAccumuloStore and returned only the Edges containing the Vertex `”10”`. In our Schema we specified that we should sum the "count" property on Edges of the same Group between the same pair of Vertices. We get the following Edges returned, with their "counts" summed:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```
