# Introduction

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
 - Choose a database - called the Gaffer [Store](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/store/Store.html). We've provided a few for you and in the following examples we'll be using the [MockAccumuloStore](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/accumulostore/MockAccumuloStore.html). The MockAccumuloStore behaves the same as the full [AccumuloStore](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/accumulostore/AccumuloStore.html) but means that you can run the code on your local machine in memory without having to have a full Accumulo cluster.
 - Write a [Schema](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/store/schema/Schema.html). This is a JSON document that describes your graph and is made up of 2 parts:
   - Elements Schema - the Elements (Edges and Entities) in your Graph; what classes represent your vertices (nodes), what [Properties](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/Properties.html) your Elements have and so on.
   - Types Schema - list of all the types schema that are used in your elements schema. For each type it defines the java class and a list of validation functions, how they are aggregated and how they are serialised
 - Write an [ElementGenerator](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/ElementGenerator.html) to convert your data into Gaffer Graph [Element](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/Element.html). We've provided some interfaces for you.
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

We've written some examples to show you how to get started. 
We have split them up into this user guide,
a [Developer Guide](../developer-guide/contents.md) and a [Properties Guide](../properties-guide/contents.md).
In addition to these guides we have examples for the different 
[Operations](../operations/contents.md) and [Predicates](../predicates/contents.md) we support.

