# User Guide
1. [Introduction](#introduction)
2. [Walkthroughs](#walkthroughs)
   1. [The basics](#the-basics)
   2. [Multiple Edges](#multiple-edges)
   3. [Filtering](#filtering)
   4. [Transforms](#transforms)
   5. [Operation Chains](#operation-chains)
   6. [Aggregation](#aggregation)
   7. [Cardinalities](#cardinalities)
   8. [Subgraphs](#subgraphs)
   9. [Full Example](#full-example)


## Introduction 

Gaffer is built for very large graphs.

It's designed to be as flexible, scalable and extensible as possible, allowing for rapid prototyping and transition to production systems.

Gaffer does 

 - rapid query across very large numbers of entities and relationships,
 - versatile query-time summarisation, filtering and transformation of data,
 - in-database aggregation of rich statistical properties describing entities and relationships,
 - scalable ingest at very high data rates and volumes,
 - automated, rule-based data purge,
 - fine grained data access and query execution controls.

Gaffer can be run on various databases, including Accumulo and HBase. It is also integrated with Spark for fast and flexible data analysis.

Gaffer allows you to take data, convert it into a graph, store it in a database and then run queries and analytics on it.

To do this you need to do a few things:
 - Choose a database - called the Gaffer [Store](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/store/Store.html). We've provided a few for you and in the following examples we'll be using the [MockAccumuloStore](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/accumulostore/MockAccumuloStore.html). The MockAccumuloStore behaves the same as the full [AccumuloStore](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/accumulostore/AccumuloStore.html) but means that you can run the code on your local machine in memory without having to have a full Accumulo cluster.
 - Write a [Schema](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/store/schema/Schema.html). This is a JSON document that describes your graph and is made up of 3 parts:
   - Elements Schema - the Elements (Edges and Entities) in your Graph; what classes represent your vertices (nodes), what [Properties](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/Properties.html) your Elements have and so on.
   - Types Schema - list of all the types schema that are used in your elements schema. For each type it defines the java class and a list of validation functions, how they are aggregated and how they are serialised
 - Write an [ElementGenerator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/ElementGenerator.html) to convert your data into Gaffer Graph [Element](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/Element.html). We've provided some interfaces for you.
 - Write a Store Properties file. This contains information and settings about the specific instance of your store, for example hostnames, ports and so on.

When you've done these things you can write applications to load and query the data.

Gaffer is hosted on [Maven Central](https://mvnrepository.com/search?q=uk.gov.gchq.gaffer) and can easily be incorporated into your own projects.

To use Gaffer the only required dependencies are the Gaffer graph module and a store module which corresponds to the data storage framework to utilise:

```
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>graph</artifactId>
    <version>[gaffer.version]</version>
</dependency>
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>accumulo-store</artifactId>
    <version>[gaffer.version]</version>
</dependency>
```

This will include all other mandatory dependencies. Other (optional) components can be added to your project as required.

Alternatively, you can download the code and compile it yourself:

Start by cloning the gaffer GitHub project.

```bash
git clone https://github.com/gchq/Gaffer.git
```

Then from inside the Gaffer folder run the maven 'quick' profile. There are quite a few dependencies to download so it is not actually that quick.

```bash
mvn clean install -Pquick
```


We've written some [Examples](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/walkthrough) to show you how to get started.

## Running the Examples

The section below provides an overview of a number of examples of working with a Gaffer graph, including a description of the input and output at each stage.

Each example is backed by an example java class containing a main method that can be invoked by your IDE or using the doc-jar-with-dependencies.jar.

You can download the doc-jar-with-dependencies.jar from [maven central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22uk.gov.gchq.gaffer%22%20AND%20a%3A%22doc%22). Select the latest version and download the jar-with-dependencies.jar file.
Alternatively you can compile the code yourself by running a "mvn clean install -Pquick". The doc-jar-with-dependencies.jar file will be located here: doc/target/doc-jar-with-dependencies.jar.

```bash
# Replace <TheBasics> with your example name.
java -cp doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.user.walkthrough.TheBasics
```
## Walkthroughs
### The basics

The code for this example is [TheBasics](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/TheBasics.java).

We'll walk through it in some detail.

First, let’s do the most basic thing; take some data from a csv file, load it into a Gaffer graph and then run a very simple query to return some graph edges. 
We'll also look at specific examples of an [ElementGenerator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/ElementGenerator.html) and [Schema](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/store/schema/Schema.html).

We are going to base the following walkthroughs on Road Traffic data, a simplified version of the [Road Traffic Example](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/README.md). 
Throughout these walkthroughs we will gradually build up the graph, so as we learn about new features we will add them to our Graph schema. 

This is the data we will use, a simple csv in the format road,junctionA,junctionB.

```csv
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
So in this example we want to be able to ask questions like, how many vehicles have travelled from junction 10 to junction 11. So junction needs to be a vertex in the Graph.

So the Graph will look something like this (sorry it is not particularly exciting at this point). The number on the edge is the edge count property:

```
    --3->
10         11
    <-1--
 

23  --2->  24
    
    
27  <-1--  28
```

The Schema file can be broken down into small parts, we encourage at least 2 files:

- [elements.json](https://github.com/gchq/Gaffer/blob/master/doc/src/main/resources/RoadUse/schema/elements.json)
- [types.json](https://github.com/gchq/Gaffer/blob/master/doc/src/main/resources/RoadUse/schema/types.json)

Splitting the schema up into these 2 files helps to illustrate the different roles that the schemas fulfil.

##### The Elements Schema

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

##### The Types Schema

The Types Schema is a JSON document that describes the types of objects used by Elements


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


First we'll look at `"junction"`, a road junction represented by a String.

The property `"count"` on the `"RoadUse"` Edges is of type `"count.long"`. The definition here says that any object of type `"count.long"` is a an long that must be greater than or equal to 0.
We have added a validator that mandates that the count object's value must be greater than or equal to 0. If we have a `"RoadUse"` Edge with a count that's not a Long or is an Long but has a value less than 0 it will fail validation and won't be added to the Graph.
Gaffer validation is done using [Java Predicates](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)

We also supply an [Sum](ref://../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html) [BinaryOperator](https://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html) to aggregate the count.long type.
Gaffer allows Edges of the same Group to be aggregated together. This means that when different vehicles travel from junction 10 to junction 11 the edges will be aggregated together and the count property will represent the total number of vehicles that have travelled between the 2 junctions. 


#### Generating Graph Elements

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

The `_apply` method takes a line from the data file as a String and returns a Gaffer [Edge](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/Edge.html).

First we take a line from the file as a String and split on `","` to get 3 Strings: (Road,JunctionA,JunctionB).
Then we create a new Edge object with a group `"RoadUse"`. 
We set the source vertex of the Edge to be the first junction and the destination vertex to be the second junction.
Next we set the Edge's directed flag to true, indicating that in this case our Edges are directed.
Finally we add a property called 'count' to our Edge. This is an integer and is going to represent how many vehicles travel between junctionA and junctionB. We're initialising this to be 1, as a single line in our csv data represents 1 vehicle.

In a later dev.walkthrough you will see there is a quick way of adding data to Gaffer directly from CSVs but for now we will do it in 2 steps. First we convert the csv to Edges.


```java
final List<Element> elements = new ArrayList<>();
final RoadUseElementGenerator dataGenerator = new RoadUseElementGenerator();
for (final String line : IOUtils.readLines(StreamUtil.openStream(getClass(), "RoadUse/data.txt"))) {
    elements.add(dataGenerator._apply(line));
}
```

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

#### Creating a Graph Object

The next thing we do is create an instance of a Gaffer [Graph](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/graph/Graph.html), this is basically just a proxy for your chosen Gaffer Store.


```java
final Graph graph = new Graph.Builder()
        .config(StreamUtil.graphConfig(getClass()))
        .addSchemas(StreamUtil.openStreams(getClass(), "RoadUse/schema"))
        .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
        .build();
```

To do this we provide the schema files and a Store Properties file: [mockaccumulostore.properties](https://github.com/gchq/Gaffer/blob/master/doc/src/main/resources//mockaccumulostore.properties).

##### The StoreProperties

Here is the StoreProperties file:


```properties
gaffer.store.class=uk.gov.gchq.gaffer.accumulostore.SingleUseMockAccumuloStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.accumulostore.AccumuloProperties
accumulo.instance=someInstanceName
accumulo.zookeepers=aZookeeper
accumulo.user=user01
accumulo.password=password
gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.HashMapCacheService
gaffer.store.job.tracker.enabled=true
gaffer.serialiser.json.modules=uk.gov.gchq.gaffer.bitmap.serialisation.json.BitmapJsonModules
gaffer.store.operation.declarations=sparkAccumuloOperationsDeclarations.json,ResultCacheExportOperations.json,ExportToOtherGraphOperationDeclarations.json


```


This contains information specific to the actual instance of the Store you are using. Refer to the documentation for your chosen store for the configurable properties, e.g [Accumulo Store User Guide](https://github.com/gchq/Gaffer/wiki/Accumulo-Store-user-guide).
The important property is 'gaffer.store.class' this tells Gaffer the type of store you wish to use to store your data. 

#### Loading and Querying Data

Now we've generated some Graph Edges and created a Graph, let's put the Edges in the Graph.

Start by creating a user:


```java
final User user = new User("user01");
```

Then using that user we can add the elements:


```java
final AddElements addElements = new AddElements.Builder()
        .input(elements)
        .build();
graph.execute(addElements, user);
```

To do anything with the Elements in a Gaffer Graph we use an [Operation](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/Operation.html). In this case our Operation is [AddElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html).

Finally, we run a query to return all Edges in our Graph that contain the vertex "10". To do this we use a [GetElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) Operation.


```java
final GetElements query = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .build();
final CloseableIterable<? extends Element> results = graph.execute(query, user);
```

#### Summary

In this example we've taken some simple pairs of integers in a file and, using a ElementGenerator, converted them into Gaffer Graph Edges with a `”count”` property.

Then we loaded the Edges into a Gaffer Graph backed by a MockAccumuloStore and returned only the Edges containing the Vertex `”10”`. In our Schema we specified that we should sum the "count" property on Edges of the same Group between the same pair of Vertices. We get the following Edges returned, with their "counts" summed:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```



### Multiple Edges

The code for this example is [MultipleEdges](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/MultipleEdges.java).

This time we'll see at what happens when we have more than one Edge.

#### Schemas

We first need to add an additional Edge to our Schema. We will add a RoadHasJunction Edge. This will be a simple Edge from a new vertex 'Road' to the existing 'Junction' vertex. 

In the elements schema we need to define the new Road Edge:

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
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    }
  }
}
```


Here we have used a new 'road' type so we also have to define that in our elements schema. It is defined to be a simple java String.

#### Element Generator

Like the previous example, we use a ElementGenerator to generate Gaffer Edges from each line of the CSV, but in now each line will generate 3 Edges. 1 RoadUse edge and 2 RoadHasJunction edges. The generator for this example is:

```java
public class RoadAndRoadUseElementGenerator implements OneToManyElementGenerator<String> {
    @Override
    public Iterable<Element> _apply(final String line) {
        final String[] t = line.split(",");

        final String road = t[0];
        final String junctionA = t[1];
        final String junctionB = t[2];

        return Arrays.asList(
                new Edge.Builder()
                        .group("RoadHasJunction")
                        .source(road)
                        .dest(junctionA)
                        .directed(true)
                        .build(),

                new Edge.Builder()
                        .group("RoadHasJunction")
                        .source(road)
                        .dest(junctionB)
                        .directed(true)
                        .build(),

                new Edge.Builder()
                        .group("RoadUse")
                        .source(junctionA)
                        .dest(junctionB)
                        .directed(true)
                        .property("count", 1L)
                        .build()
        );
    }
}
```

Here are the generated edges:

```
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=23,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=23,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=28,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=27,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```

#### Loading and Querying the Data

We create a Graph and load the data using the [AddElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html) Operation exactly the same as in the previous example.

This time we'll run 2 queries using [GetElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html).

The first one is exactly the same as last time. We ask for all of the Edges containing the Vertex "10". The result is:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]

```

Our second query is to return all "RoadHasJunction" Edges. We still use the [GetElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) Operation like before but this time we add a [View](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/View.html) to it:


```java
final View view = new View.Builder()
        .edge("RoadHasJunction")
        .build();
final GetElements getRelatedRedEdges = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(view)
        .build();
final CloseableIterable<? extends Element> redResults = graph.execute(getRelatedRedEdges, user);
```

and only get the "RoadHasJunction" Edges containing Vertex "10":

```
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]

```

We'll explore the [View](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/View.html) in more detail over the next few examples. But for now think of it as you are requesting a particular View of you graph, filtering out unwanted elements.



### Filtering

The code for this example is [Filtering](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Filtering.java).

Filtering in Gaffer is designed so it can be applied server side and distributed across a cluster for performance.

In this example we’ll query for some Edges and filter the results based on the aggregated value of a property. 
We will use the same schema and data as the previous example.

If we query for the RoadUse Edges containing vertex `”10”` we get these Edges back with the counts aggregated:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```

Now let’s look at how to filter which Edges are returned based on the aggregated value of their count. For example, only return Edges containing vertex `”10”` where the `”count”` > 2.

We do this using a [View](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/View.html) and [ViewElementDefinition](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/ViewElementDefinition.html) like this:


```java
final View viewWithFilter = new View.Builder()
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(2L))
                        .build())
                .build())
        .build();
final GetElements getEdgesWithCountMoreThan2 = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(viewWithFilter)
        .build();
final CloseableIterable<? extends Element> filteredResults = graph.execute(getEdgesWithCountMoreThan2, user);
```

Our ViewElementDefinition allows us to perform post Aggregation filtering using a IsMoreThan Predicate.

If we run the query, we now get only those vertex `”10”` Edges where the `”count”` > 2:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]

```



### Transforms

The code for this example is [Transforms](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Transforms.java).

In this example we’ll look at how to query for Edges and then add a new transient property to the Edges in the result set.
Again, we will just use the same schema and data as in the previous walkthough. 

A transient property is just a property that is not persisted, simply created at query time by a transform function. We’ll create a 'description' transient property that will summarise the contents of the aggregated Edges.

To do this we need a [Function](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html). Here is our [DescriptionTransform](https://github.com/gchq/Gaffer/blob/master/example/road-traffic-model/src/main/java/uk/gov/gchq/gaffer/traffic/transform/DescriptionTransform.java).

This transform function takes 3 values, the `”SOURCE”` vertex, the `”DESTINATION”` vertex and `”count”` property and produces a description string.

This transform function then needs to be configured using an [ElementTransformer](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ElementTransformer.html):


```java
final ElementTransformer descriptionTransformer = new ElementTransformer.Builder()
        .select("SOURCE", "DESTINATION", "count")
        .execute(new DescriptionTransform())
        .project("description")
        .build();
```

Here you can see we `select` the `”SOURCE”` vertex, the `”DESTINATION”` vertex and `”count”` property and `project`, them into the new `”description”` transient property.

We add the new `”description”` property to the result Edge using a `View` and then execute the operation.


```java
final View view = new View.Builder()
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .transientProperty("description", String.class)
                .transformer(descriptionTransformer)
                .build())
        .build();
final GetElements getEdgesWithDescription = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(view)
        .build();
final CloseableIterable<? extends Element> resultsWithDescription = graph.execute(getEdgesWithDescription, user);
```

This produces the following result:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3,description=<java.lang.String>3 vehicles have travelled between junction 10 and junction 11]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1,description=<java.lang.String>1 vehicles have travelled between junction 11 and junction 10]]

```

As you can see we’ve now got a new `”description”` property on each Edge.

We can also limit what properties we want to be returned. We can either provide
a list of properties to include, using the 'properties' field, or properties
to exclude, using the 'excludeProperties' field. In this case once we have used
the count property to create our description we don't actually want the count
property to be returned. So we will exclude it using the following code:


```java
final View viewWithExcludedProperties = new View.Builder()
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .transientProperty("description", String.class)
                .transformer(descriptionTransformer)
                .excludeProperties("count")
                .build())
        .build();
final GetElements getEdgesWithDescriptionAndNoCount = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(viewWithExcludedProperties)
        .build();
final CloseableIterable<? extends Element> resultsWithDescriptionAndNoCount = graph.execute(getEdgesWithDescriptionAndNoCount, user);
```

and the result now does not contain the count property:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[description=<java.lang.String>3 vehicles have travelled between junction 10 and junction 11]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[description=<java.lang.String>1 vehicles have travelled between junction 11 and junction 10]]

```





### Operation Chains

The code for this example is [OperationChains](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/OperationChains.java).

In the previous examples we have had several steps to get data into Gaffer. As promised, we can now simplify this and use an operation chain.

We will show you a way of using an operation chain to both generate the elements from the data file and add them directly to the graph.
Operation chains are simply a list of operations in which the operations are executed sequentially, the output of one operation is passed in as the input to the next operation.

So adding elements from a CSV file can now be done as follows:


```java
final OperationChain<Void> addOpChain = new OperationChain.Builder()
        .first(new GenerateElements.Builder<String>()
                .generator(new RoadAndRoadUseElementGenerator())
                .input(IOUtils.readLines(StreamUtil.openStream(getClass(), "RoadAndRoadUse/data.txt")))
                .build())
        .then(new AddElements())
        .build();

graph.execute(addOpChain, user);
```

This chain consists of 2 operations.
The first, GenerateElements, which takes the data and an element generator and generates the Gaffer Edges.
The second, AddElements, simply takes the generated edges and adds them to the graph.
This operation chain can then be executed on the graph as before.


Another example of using an operation chain is when we are traversing the graph.


```java
final OperationChain<Iterable<? extends String>> opChain =
        new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed("M5"))
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .then(new GetElements.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .then(new GenerateObjects.Builder<String>()
                        .generator(new RoadUseCsvGenerator())
                        .build())
                .build();

final Iterable<? extends String> results = graph.execute(opChain, user);
```

This operation chain takes starts with a seed vertex traverses down all outgoing edges using the [GetAdjacentIds](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAdjacentIds.html) operation and then returns all the following connected edges using the [GetElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) operation. 
Before returning the results the edges are converted into a csv format (junctionA, junctionB, count) using the [GenerateObjects](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateObjects.html) operation.
In order to convert the edges back into the initial csv format we have implemented the [ObjectGenerator](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/ObjectGenerator.html). 


```java
public class RoadUseCsvGenerator implements OneToOneObjectGenerator<String> {
    @Override
    public String _apply(final Element element) {
        if (!(element instanceof Edge && "RoadUse".equals(element.getGroup()))) {
            throw new UnsupportedOperationException("Only RoadUse edges should be used");
        }

        final Edge edge = ((Edge) element);
        return edge.getSource() + "," + edge.getDestination() + "," + edge.getProperty("count");
    }
}
```

When we execute this query we get:

```csv
10,11,3
11,10,1
23,24,2
28,27,1

```

You can see the data has been converted back into csv.

Operation chains work with any combination of operations where sequential operations have compatible output/input formats.

For more examples of different types of operations see [Operation Examples](../operation-examples).



### Aggregation

The code for this example is [Aggregation](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Aggregation.java).

Aggregation is a key powerful feature in Gaffer and we will guide you through it in this walkthrough. 

Aggregation is applied at ingest and query time.

1. Ingest aggregation permanently aggregates similar elements together. Elements will be aggregated if:
    1. An entity has the same group, vertex, visibility and any specified groupBy property values are identical.
    2. An edge has the same group, source, destination, directed and any specified groupBy property values are identical.
2. Query aggregation allows additional summarisation depending on a user's visibility permissions and any overridden groupBy properties provided at query time.

For this dev.walkthrough we have added a timestamp to our csv data:

```csv
M5,10,11,2000-05-01 07:00:00
M5,10,11,2000-05-01 08:00:00
M5,10,11,2000-05-02 09:00:00
M5,11,10,2000-05-03 09:00:00
M5,23,24,2000-05-04 07:00:00
M5,23,24,2000-05-04 07:00:00
M5,28,27,2000-05-04 07:00:00
```


The schema is similar to what we have seen before, but we have added a start date and a end date property. 
These properties have been added to the groupBy field. 
Properties in the groupBy will be used to determine whether elements should be aggregated together at ingest. 
Property values in the groupBy are required to be identical for Gaffer to aggregate them at ingest.

##### Elements schema

```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    }
  }
}

```


##### Types schema

```json
{
  "types": {
    "junction": {
      "description": "A road junction represented by a String.",
      "class": "java.lang.String"
    },
    "road": {
      "description": "A road represented by a String.",
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
    },
    "date.earliest": {
      "description": "A Date that when aggregated together will be the earliest date.",
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
      "description": "A Date that when aggregated together will be the latest date.",
      "class": "java.util.Date",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
      }
    }
  }
}
```


Once we have loaded the data into Gaffer, we can fetch all the edges using a GetAllElements operation.
Note this operation is not recommended for large Graphs as it will do a full scan of your database and could take a while to finish.

```java
final GetAllElements getAllRoadUseEdges = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .build();

final CloseableIterable<? extends Element> roadUseElements = graph.execute(getAllRoadUseEdges, user);
```

All edges:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 BST 2000]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Thu May 04 00:00:00 BST 2000]]

```

You will see in the results, the timestamp has been converted into start and end dates - the midnight before the timestamp and a millisecond before the following midnight, i.e. the start and end of the day. This effectively creates a time bucket with day granularity. 
So all edges with a timestamp on the same day have been aggregated together (ingest aggregation).
These aggregated edges have had all their properties aggregated together using the aggregate functions specified in the schema. 

Next we will further demonstrate query time aggregation and get all the 'RoadUse' edges with the same source to the same destination to be aggregated together, regardless of the start and end dates. 
This is achieved by overriding the groupBy field with empty array so no properties are grouped on. Here is the operation:


```java
final GetAllElements edgesSummarisedOperation = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .groupBy() // set the group by properties to 'none'
                        .build())
                .build())
        .build();

final CloseableIterable<? extends Element> edgesSummarised = graph.execute(edgesSummarisedOperation, user);
```

The summarised edges are as follows:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>3,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 BST 2000]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Thu May 04 00:00:00 BST 2000]]

```

Now you can see the edges from 10 to 11 have been aggregated together and their counts have been summed together.

If we apply some pre-aggregation filtering, we can return a time window summary of the edges. The new operation looks like:

```java
final GetAllElements edgesSummarisedInTimeWindowOperation = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("startDate")
                                .execute(new IsMoreThan(MAY_01_2000, true))
                                .select("endDate")
                                .execute(new IsLessThan(MAY_02_2000, false))
                                .build()
                        )
                        .groupBy() // set the group by properties to 'none'
                        .build())
                .build())
        .build();

final CloseableIterable<? extends Element> edgesSummarisedInTimeWindow = graph.execute(edgesSummarisedInTimeWindowOperation, user);
```

The time window summaries are:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]

```

Now we have all the RoadUse edges that fall in the time window May 01 2000 to May 03 2000. This filtered out all edges apart from 2 occuring between junction 10 and 11. Therefore, the count is has been aggregated to just 2 (instead of 3 as seen previously).

Aggregation also works nicely alongside visibilities. Data at different visibility levels is stored separately then at query time, the query time aggregation will summarise just the data that a given user can see.

There is another more advanced feature to query time aggregation.
When executing a query you can override the logic for how Gaffer aggregates properties together. 
So by default the count property is aggregated with Sum. 
At query time we could change that, to force the count property is aggregated with the Min aggregator, therefore finding the minimum daily count.
This feature doesn't affect any of the persisted values and any store aggregation that has already occurred will not be modified.
So in this example the Edges have been summarised into daily time buckets and the counts have been aggregated with Sum.
Now at query time we are able to ask: What is the minimum daily count?

Here is the java code:

```java
final GetAllElements edgesSummarisedInTimeWindowWithMinCountOperation = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("startDate")
                                .execute(new IsMoreThan(MAY_01_2000, true))
                                .select("endDate")
                                .execute(new IsLessThan(MAY_03_2000, false))
                                .build()
                        )
                        .groupBy() // set the group by properties to 'none'
                        .aggregator(new ElementAggregator.Builder()
                                .select("count")
                                .execute(new Min())
                                .build())
                        .build())
                .build())
        .build();

log("GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_RESULT_WITH_MIN_COUNT_JSON", StringUtil.toString(JSONSerialiser.serialise(edgesSummarisedInTimeWindowWithMinCountOperation, true)));

final CloseableIterable<? extends Element> edgesSummarisedInTimeWindowWithMinCount = graph.execute(edgesSummarisedInTimeWindowWithMinCountOperation, user);
```

So, you can see we have just added an extra 'aggregator' block to the Operation view.
This can be written in json like this:

```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
  "view" : {
    "edges" : {
      "RoadUse" : {
        "aggregator" : {
          "operators" : [ {
            "binaryOperator" : {
              "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
            },
            "selection" : [ "count" ]
          } ]
        },
        "groupBy" : [ ],
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : true,
            "value" : {
              "java.util.Date" : 957135600000
            }
          },
          "selection" : [ "startDate" ]
        }, {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
            "orEqualTo" : false,
            "value" : {
              "java.util.Date" : 957308400000
            }
          },
          "selection" : [ "endDate" ]
        } ]
      }
    },
    "entities" : { }
  }
}

```
 
We have increased the time window to 3 days just so there are multiple edges to demonstrate the query time aggregation.
The result is:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]

```
 




### Cardinalities

The code for this example is [Cardinalities](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Cardinalities.java).

This example demonstrates how storing a HyperLogLogPlus object on each vertex in a graph allows us to quickly estimate its degree (i.e. the number of edges it is involved in). The estimate can be obtained without scanning through all the edges involving a node and so is very quick, even if the degree is very large.

To add properties to vertices we need to add an Entity to our schema. Entities are associated with a vertex and contain a set of properties about that vertex.

##### Elements schema
This is our new elements schema. You can we we have added a Cardinality Entity. This will be added to every vertex in the Graph. This Entity has a 'hllp' property that will hold the HyperLogLogPlus cardinality value.


```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ],
      "validateFunctions": [
        {
          "selection": [
            "startDate",
            "endDate"
          ],
          "predicate": {
            "class": "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          }
        }
      ]
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    }
  },
  "entities": {
    "Cardinality": {
      "description": "An entity that is added to every vertex representing the connectivity of the vertex.",
      "vertex": "anyVertex",
      "properties": {
        "edgeGroup": "set",
        "hllp": "hllp",
        "count": "count.long"
      },
      "groupBy": [
        "edgeGroup"
      ]
    }
  }
}
```


##### Types schema
We have added a new type - hllp. This is a HyperLogLogPlus object. The HyperLogLogPlus object will be used to estimate the cardinality of a vertex.
We also added in the serialiser and aggregator for the HyperLogLogPlus object. Gaffer will automatically aggregate the cardinalities, using the provided aggregator, so they will keep up to date as new elements are added to the graph.


```json
{
  "types": {
    "junction": {
      "description": "A road junction represented by a String.",
      "class": "java.lang.String"
    },
    "road": {
      "description": "A road represented by a String.",
      "class": "java.lang.String"
    },
    "anyVertex": {
      "description": "An String vertex - used for cardinalities",
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
    },
    "date.earliest": {
      "description": "A Date that when aggregated together will be the earliest date.",
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
      "description": "A Date that when aggregated together will be the latest date.",
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
    "set": {
      "class": "java.util.TreeSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      }
    },
    "hllp": {
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


Here are all the edges loaded into the graph (unfortunately the HyperLogLogPlus class we are using for the cardinality doesn't have a toString method, so just ignore that for now):

```
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3ecd267f,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@58ffcbd7,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Entity[vertex=11,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@555cf22,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=11,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@6bb2d00b,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Entity[vertex=23,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3c9bfddc,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=23,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@1a9c38eb,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 BST 2000]]
Entity[vertex=24,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@319bc845,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=24,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@4c5474f5,count=<java.lang.Long>2,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Entity[vertex=27,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@2f4205be,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=27,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@54e22bdd,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Entity[vertex=28,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3bd418e4,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=28,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@544820b7,count=<java.lang.Long>1,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Thu May 04 00:00:00 BST 2000]]
Entity[vertex=M5,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@6b98a075,count=<java.lang.Long>14,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=M5,destination=10,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=23,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=27,directed=true,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=28,directed=true,group=RoadHasJunction,properties=Properties[]]

```


We can fetch all cardinalities for all the vertices using the following operation:

```java
final GetAllElements getAllCardinalities =
        new GetAllElements.Builder()
                .view(new View.Builder()
                        .entity("Cardinality")
                        .build())
                .build();
```

If we look at the cardinality value of the HyperLogLogPlus property the values are:

```
Vertex 10 [RoadUse]: 1
Vertex 10 [RoadHasJunction]: 1
Vertex 11 [RoadUse]: 1
Vertex 11 [RoadHasJunction]: 1
Vertex 23 [RoadUse]: 1
Vertex 23 [RoadHasJunction]: 1
Vertex 24 [RoadUse]: 1
Vertex 24 [RoadHasJunction]: 1
Vertex 27 [RoadUse]: 1
Vertex 27 [RoadHasJunction]: 1
Vertex 28 [RoadUse]: 1
Vertex 28 [RoadHasJunction]: 1
Vertex M5 [RoadHasJunction]: 7

```

You can see we get a cardinality value of one for each junction for RoadUse edges as each junction is only connected to 1 other junction. 
The cardinality value for the M5 Road is 7 as there are 7 junctions on this road in our data set. 
Remember that the HyperLogLogPlus cardinality value is just an estimate.  

If we want to merge these cardinalities together we can add 'groupBy=[]' to the operation view to override the groupBy defined in the schema.

```java
final GetAllElements getAllSummarisedCardinalities =
        new GetAllElements.Builder()
                .view(new View.Builder()
                        .entity("Cardinality", new ViewElementDefinition.Builder()
                                .groupBy()
                                .build())
                        .build())
                .build();
```
Now you can see the cardinality values have been merged together at each vertex:

```
Vertex 10 [RoadHasJunction, RoadUse]: 2
Vertex 11 [RoadHasJunction, RoadUse]: 2
Vertex 23 [RoadHasJunction, RoadUse]: 2
Vertex 24 [RoadHasJunction, RoadUse]: 2
Vertex 27 [RoadHasJunction, RoadUse]: 2
Vertex 28 [RoadHasJunction, RoadUse]: 2
Vertex M5 [RoadHasJunction]: 7

```

This next snippet shows you how you can query for a single cardinality value.

```java
final GetElements getCardinalities =
        new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .entity("Cardinality", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("edgeGroup")
                                        .execute(new IsEqual(CollectionUtil.treeSet("RoadUse")))
                                        .build())
                                .build())
                        .build())
                .build();
```
As you can see the query just simply asks for an entities at vertex '10' and filters for only 'Cardinality' entities that have an edgeGroup property equal to 'RoadUse'. 

The result is:

```
Vertex 10 [RoadUse]: 1

```

One of the main uses of Cardinalities is to avoid busy vertices whilst traversing the graph. 
For example if you want to do a 2 hop query (traverse along an edge then another edge) you may want to only go down edges where the source vertex has a low cardinality to avoid returning too many edges.
Here is the java code:


```java
final OperationChain<CloseableIterable<? extends Element>> twoHopsWithCardinalityFilter = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("M5"))
                .view(new View.Builder()
                        .edge("RoadHasJunction")
                        .build())
                .build())
        .then(new GetElements.Builder()
                .view(new View.Builder()
                        .entity("Cardinality", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("edgeGroup")
                                        .execute(new IsEqual(CollectionUtil.treeSet("RoadUse")))
                                        .build())
                                .groupBy()
                                .postAggregationFilter(new ElementFilter.Builder()
                                        .select("hllp")
                                        .execute(new HyperLogLogPlusIsLessThan(5))
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new GetElements.Builder()
                .view(new View.Builder()
                        .edge("RoadUse")
                        .build())
                .build())
        .build();
```



### Subgraphs

The code for this example is [Subgraphs](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Subgraphs.java).

This example extends the previous dev.walkthrough and demonstrates how a subgraph could be created using a single operation chain.

We will start by loading the data into the graph as we have done previously.

The operation chain is built up using several [GetElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html) operations. 
In between each of these operations we cache the results in memory using a LinkedHashSet by executing the [ExportToSet](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/ExportToSet.html) operation. 
For larger graphs we could simply swap the ExportToSet to [ExportToGafferResultCache](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/ExportToGafferResultCache.html) operation.

In order to chain GetElements operations together we need to extract the destination vertex of each result Edge and wrap the destination vertex in an EntitySeed so that it can be used as a seed for the next operation.

We can repeat this combination of operations for extract a subgraph contain 'x' number of hops around the graph. In this example we will just do 2 hops as our graph is quite basic. 
We finish off by using a [GetSetExport](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/GetSetExport.html) operation to return the set of edges.

Although this results several operations in chain, each operation is quite simple and this demonstrates the flexibility of the operations. 

The full chain looks like:


```java
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("M5"))
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("RoadHasJunction")
                        .build())
                .build())
        .then(new ExportToSet<>())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.DESTINATION)
                .build())
        .then(new ToEntitySeeds())
        .then(new GetElements.Builder()
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("RoadUse", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(1L))
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport())
        .build();
```

For each 'hop' we use a different View, to specify the edges we wish to hop down and different filters to apply. 
The export operations will export the currently result and pass the result onto the next operation. This is why we have the slightly strange DiscardOutput operation before we return the final results. 

The result is the full set of traversed Edges:

```csv
Edge[source=M5,destination=10,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=11,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=23,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=24,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=27,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=M5,destination=28,directed=true,matchedVertex=SOURCE,group=RoadHasJunction,properties=Properties[]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=23,destination=24,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 BST 2000]]

```

Here are some further export [Operation Examples](../operation-examples) that demonstrate some more advanced features of exporting. 



### Full Example

The code for this example is [FullExample](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/FullExample.java).

Finally this example introduces the full Road Traffic schema. This uses the sample data taken from the Department for Transport [GB Road Traffic Counts](http://data.dft.gov.uk/gb-traffic-matrix/Raw_count_data_major_roads.zip), which is licensed under the [Open Government Licence](http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/).

The data is now in a slightly different format. Each row now represents multiple vehicles of different types travelling between 2 junctions. We also have a bit of extra information in the data file. This has allow us to create some extra edges: RegionContainsLocation, LocationContainsRoad and JunctionLocatedAt.

As we now have multiple roads in our sample data, we will include the name of the road in the junction name, e.g. M5:23 represents junction 23 on the M5.

We have also add in a frequency map for the counts of each vehicle time. This will allow us to perform queries such as to find out which roads have a large number of buses. 
Here are the updated schema files:

##### Elements schema

```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long",
        "countByVehicleType": "counts.freqmap"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    },
    "RegionContainsLocation": {
      "description": "A directed edge from each region to location.",
      "source": "region",
      "destination": "location",
      "directed": "true"
    },
    "LocationContainsRoad": {
      "description": "A directed edge from each location to road.",
      "source": "location",
      "destination": "road",
      "directed": "true"
    },
    "JunctionLocatedAt": {
      "description": "A directed edge from each junction to its coordinates",
      "source": "junction",
      "destination": "coordinates",
      "directed": "true"
    }
  },
  "entities": {
    "Cardinality": {
      "description": "An entity that is added to every vertex representing the connectivity of the vertex.",
      "vertex": "anyVertex",
      "properties": {
        "edgeGroup": "set",
        "hllp": "hllp",
        "count": "count.long"
      },
      "groupBy": [
        "edgeGroup"
      ]
    },
    "JunctionUse": {
      "description": "An entity on the junction vertex representing the counts of vehicles moving from junction A to junction B.",
      "vertex": "junction",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long",
        "countByVehicleType": "counts.freqmap"
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

```json
{
  "types": {
    "junction": {
      "description": "A road junction represented by a String.",
      "class": "java.lang.String"
    },
    "road": {
      "description": "A road represented by a String.",
      "class": "java.lang.String"
    },
    "anyVertex": {
      "description": "An String vertex - used for cardinalities",
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
    },
    "date.earliest": {
      "description": "A Date that when aggregated together will be the earliest date.",
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
      "description": "A Date that when aggregated together will be the latest date.",
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
    "set": {
      "class": "java.util.TreeSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      }
    },
    "hllp": {
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



#### Example complex query
Now we have a the full schema we can load in our [Road Traffic Sample ](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/road-traffic-demo/src/main/resources/roadTrafficSampleData.csv) data and run a more complex query.

For this example, the question we want to ask is: "In the year 2000, which junctions in the South West were heavily used by buses".

There may be different and probably more efficient ways of doing this but we have tried to create an operation chain that demonstrates several features from the previous walkthroughs. 

The query is form a follows:

- We will start at the "South West" vertex, follow RegionContainsLocation edge, then LocationContainsRoad edge. 
- We may get duplicates at this point so we will add all the road vertices to a Set using ToSet (this is not recommended for a large number of results).
- Then we will continue on down RoadHasJunction edges.
- At this point we now have all the Junctions in the South West.
- We will then query for the JunctionUse entity to find out if the junction was heavily used by buses in the year 2000.
- Finally, just to demonstrate another operation, we will convert the results into a simple CSV of junction and bus count.

and here is the code:


```java
final OperationChain<Iterable<? extends String>> opChain = new OperationChain.Builder()
        .first(new GetAdjacentIds.Builder()
                .input(new EntitySeed("South West"))
                .view(new View.Builder()
                        .edge("RegionContainsLocation")
                        .build())
                .build())
        .then(new GetAdjacentIds.Builder()
                .view(new View.Builder()
                        .edge("LocationContainsRoad")
                        .build())
                .build())
        .then(new ToSet<>())
        .then(new GetAdjacentIds.Builder()
                .view(new View.Builder()
                        .edge("RoadHasJunction")
                        .build())
                .build())
        .then(new GetElements.Builder()
                .view(new View.Builder()
                        .globalElements(new GlobalViewElementDefinition.Builder()
                                .groupBy()
                                .build())
                        .entity("JunctionUse", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("startDate")
                                        .execute(new IsMoreThan(JAN_01_2000, true))
                                        .select("endDate")
                                        .execute(new IsLessThan(JAN_01_2001, false))
                                        .build())
                                .postAggregationFilter(new ElementFilter.Builder()
                                        .select("countByVehicleType")
                                        .execute(new PredicateMap<>("BUS", new IsMoreThan(1000L)))
                                        .build())

                                        // Extract the bus count out of the frequency map and store in transient property "busCount"
                                .transientProperty("busCount", Long.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("countByVehicleType")
                                        .execute(new FreqMapExtractor("BUS"))
                                        .project("busCount")
                                        .build())
                                .build())
                        .build())
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .build())
                // Convert the result entities to a simple CSV in format: Junction,busCount.
        .then(new ToCsv.Builder()
                .generator(new CsvGenerator.Builder()
                        .vertex("Junction")
                        .property("busCount", "Bus Count")
                        .build())
                .build())
        .build();
```

This can also be written in JSON for performing the query via the REST API:

```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "view" : {
      "edges" : {
        "RegionContainsLocation" : { }
      },
      "entities" : { }
    },
    "input" : [ {
      "vertex" : "South West",
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "view" : {
      "edges" : {
        "LocationContainsRoad" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "view" : {
      "edges" : {
        "RoadHasJunction" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : { },
      "entities" : {
        "JunctionUse" : {
          "transientProperties" : {
            "busCount" : "java.lang.Long"
          },
          "preAggregationFilterFunctions" : [ {
            "predicate" : {
              "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
              "orEqualTo" : true,
              "value" : {
                "java.util.Date" : 946684800000
              }
            },
            "selection" : [ "startDate" ]
          }, {
            "predicate" : {
              "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
              "orEqualTo" : false,
              "value" : {
                "java.util.Date" : 978307200000
              }
            },
            "selection" : [ "endDate" ]
          } ],
          "postAggregationFilterFunctions" : [ {
            "predicate" : {
              "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                "orEqualTo" : false,
                "value" : {
                  "java.lang.Long" : 1000
                }
              },
              "key" : "BUS"
            },
            "selection" : [ "countByVehicleType" ]
          } ],
          "transformFunctions" : [ {
            "function" : {
              "class" : "uk.gov.gchq.gaffer.types.function.FreqMapExtractor",
              "key" : "BUS"
            },
            "selection" : [ "countByVehicleType" ],
            "projection" : [ "busCount" ]
          } ]
        }
      },
      "globalElements" : [ {
        "groupBy" : [ ]
      } ]
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToCsv",
    "elementGenerator" : {
      "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
      "fields" : {
        "VERTEX" : "Junction",
        "busCount" : "Bus Count"
      },
      "constants" : { },
      "quoted" : false
    },
    "includeHeader" : true
  } ]
}

```

We also have a python shell for connecting to the Gaffer REST API. You can 
get the python shell from [here](https://github.com/gchq/gaffer-tools/tree/master/python-shell).
Then you can import the gaffer modules using:

```python
from gafferpy import gaffer as g
from gafferpy import gaffer_connector
```

Here is the previous operation written in Python.

```python
g.OperationChain( 
  operations=[ 
    g.GetAdjacentIds( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="RegionContainsLocation" 
          ) 
        ], 
        entities=[ 
        ] 
      ), 
      input=[ 
        g.EntitySeed( 
          vertex="South West" 
        ) 
      ] 
    ), 
    g.GetAdjacentIds( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="LocationContainsRoad" 
          ) 
        ], 
        entities=[ 
        ] 
      ) 
    ), 
    g.ToSet(), 
    g.GetAdjacentIds( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="RoadHasJunction" 
          ) 
        ], 
        entities=[ 
        ] 
      ) 
    ), 
    g.GetElements( 
      view=g.View( 
        global_elements=[ 
          g.GlobalElementDefinition( 
            group_by=[ 
            ] 
          ) 
        ], 
        edges=[ 
        ], 
        entities=[ 
          g.ElementDefinition( 
            pre_aggregation_filter_functions=[ 
              g.PredicateContext( 
                selection=[ 
                  "startDate" 
                ], 
                predicate=g.IsMoreThan( 
                  value={'java.util.Date': 946684800000}, 
                  or_equal_to=True 
                ) 
              ), 
              g.PredicateContext( 
                selection=[ 
                  "endDate" 
                ], 
                predicate=g.IsLessThan( 
                  value={'java.util.Date': 978307200000}, 
                  or_equal_to=False 
                ) 
              ) 
            ], 
            group="JunctionUse", 
            transient_properties={'busCount': 'java.lang.Long'}, 
            transform_functions=[ 
              g.FunctionContext( 
                projection=[ 
                  "busCount" 
                ], 
                function=g.Function( 
                  class_name="uk.gov.gchq.gaffer.types.function.FreqMapExtractor", 
                  fields={'key': 'BUS'} 
                ), 
                selection=[ 
                  "countByVehicleType" 
                ] 
              ) 
            ], 
            post_aggregation_filter_functions=[ 
              g.PredicateContext( 
                selection=[ 
                  "countByVehicleType" 
                ], 
                predicate=g.PredicateMap( 
                  key="BUS", 
                  predicate=g.IsMoreThan( 
                    value={'java.lang.Long': 1000}, 
                    or_equal_to=False 
                  ) 
                ) 
              ) 
            ] 
          ) 
        ] 
      ), 
      include_incoming_out_going="OUTGOING" 
    ), 
    g.ToCsv( 
      include_header=True, 
      element_generator=g.ElementGenerator( 
        class_name="uk.gov.gchq.gaffer.data.generator.CsvGenerator", 
        fields={'fields': {'busCount': 'Bus Count', 'VERTEX': 'Junction'}, 'constants': {}, 'quoted': False} 
      ) 
    ) 
  ] 
)


```


When executed on the graph, the result is:

```
Junction,Bus Count
M5:LA Boundary,1067
M4:LA Boundary,1958
M32:2,1411

```

The full road traffic example project can be found in [Road Traffic Example](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/README.md). 
At this point it might be a good idea to follow the documentation in that README to start up a Gaffer REST API and UI backed with the road traffic graph.



