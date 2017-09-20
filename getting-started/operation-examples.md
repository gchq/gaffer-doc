# Operation Examples
_This page has been generated from code. To make any changes please update the example doc in the [doc](https://github.com/gchq/Gaffer/tree/master/doc/src/main/java/uk/gov/gchq/gaffer/doc) module, run it and replace the content of this page with the output._


1. [AddElements](#addelements-example)
2. [AddElementsFromFile](#addelementsfromfile-example)
3. [AddElementsFromHdfs](#addelementsfromhdfs-example)
4. [AddElementsFromKafka](#addelementsfromkafka-example)
5. [AddElementsFromSocket](#addelementsfromsocket-example)
6. [CountGroups](#countgroups-example)
7. [ExportToGafferResultCache](#exporttogafferresultcache-example)
8. [ExportToOtherAuthorisedGraph](#exporttootherauthorisedgraph-example)
9. [ExportToOtherGraph](#exporttoothergraph-example)
10. [ExportToSet](#exporttoset-example)
11. [GenerateElements](#generateelements-example)
12. [GenerateObjects](#generateobjects-example)
13. [GetAdjacentIds](#getadjacentids-example)
14. [GetAllElements](#getallelements-example)
15. [GetAllJobDetails](#getalljobdetails-example)
16. [GetElements](#getelements-example)
17. [GetGafferResultCacheExport](#getgafferresultcacheexport-example)
18. [GetJobDetails](#getjobdetails-example)
19. [GetJobResults](#getjobresults-example)
20. [GetSetExport](#getsetexport-example)
21. [Limit](#limit-example)
22. [Max](#max-example)
23. [Min](#min-example)
24. [NamedOperation](#namedoperation-example)
25. [Sort](#sort-example)
26. [ToArray](#toarray-example)
27. [ToCsv](#tocsv-example)
28. [ToEntitySeeds](#toentityseeds-example)
29. [ToList](#tolist-example)
30. [ToMap](#tomap-example)
31. [ToSet](#toset-example)
32. [ToStream](#tostream-example)
33. [ToVertices](#tovertices-example)


In addition to these core operations, stores can implement their own specific operations. See [Accumulo operation examples](accumulo-operation-examples.md).

AddElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElements](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html).

### Required fields
No required fields


#### Add elements

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
new AddElements.Builder()
                .input(new Entity.Builder()
                                .group("entity")
                                .vertex(6)
                                .property("count", 1)
                                .build(),
                        new Edge.Builder()
                                .group("edge")
                                .source(5).dest(6).directed(true)
                                .property("count", 1)
                                .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElements",
  "validate" : true,
  "skipInvalidElements" : false,
  "input" : [ {
    "group" : "entity",
    "vertex" : 6,
    "properties" : {
      "count" : 1
    },
    "class" : "uk.gov.gchq.gaffer.data.element.Entity"
  }, {
    "group" : "edge",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "properties" : {
      "count" : 1
    },
    "class" : "uk.gov.gchq.gaffer.data.element.Edge"
  } ]
}
```

Updated graph:
```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5  -->  6
```



AddElementsFromFile example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromFile.html).

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

### Required fields
The following fields are required: 
- filename
- elementGenerator


#### Add elements from file

As Java:


```java
final AddElementsFromFile op = new AddElementsFromFile.Builder()
        .filename("filename")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile",
  "filename" : "filename",
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "parallelism" : 1,
  "validate" : true,
  "skipInvalidElements" : false
}
```

-----------------------------------------------




AddElementsFromHdfs example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.hdfs.operation.AddElementsFromHdfs](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/hdfs/operation/AddElementsFromHdfs.html).

This operation must be run as a Hadoop Job. So you will need to package up a shaded jar containing a main method that creates an instance of Graph and executes the operation. It can then be run with: 

```bash
hadoop jar custom-shaded-jar.jar
```

When running an AddElementsFromHdfs on Accumulo, if you do not specify useProvidedSplits and the Accumulo table does not have a full set of split points then this operation will first sample the input data, generate split points and set them on the Accumulo table. It does this by delegating to SampleDataForSplitPoints and class uk.gov.gchq.gaffer.operation.impl.SplitStore.

### Required fields
The following fields are required: 
- failurePath
- inputMapperPairs
- outputPath
- jobInitialiser


#### Add elements from hdfs

As Java:


```java
final AddElementsFromHdfs operation = new AddElementsFromHdfs.Builder()
        .addInputMapperPair("/path/to/input/fileOrFolder", TextMapperGeneratorImpl.class.getName())
        .outputPath("/path/to/output/folder")
        .failurePath("/path/to/failure/folder")
        .splitsFilePath("/path/to/splits/file")
        .workingPath("/tmp/workingDir")
        .useProvidedSplits(false)
        .jobInitialiser(new TextJobInitialiser())
        .minReducers(10)
        .maxReducers(100)
        .build();
```

-----------------------------------------------

#### Add elements from hdfs main method

Example content for a main method that takes 5 arguments and runs an AddElementsFromHdfs

As Java:


```java
if (5 != args.length) {
    System.err.println("Usage: hadoop jar custom-hdfs-import-<version>-shaded.jar <inputPath> <outputPath> <failurePath> <schemaPath> <storePropertiesPath>");
    System.exit(1);
}

final String inputPath = args[0];
final String outputPath = args[1];
final String failurePath = args[2];
final String schemaPath = args[3];
final String storePropertiesPath = args[4];

final Graph graph = new Graph.Builder()
        .storeProperties(storePropertiesPath)
        .addSchemas(Paths.get(schemaPath))
        .build();

final AddElementsFromHdfs operation = new AddElementsFromHdfs.Builder()
        .addInputMapperPair(inputPath, TextMapperGeneratorImpl.class.getName())
        .outputPath(outputPath)
        .failurePath(failurePath)
        .splitsFilePath("/tmp/splits")
        .workingPath("/tmp/workingDir")
        .useProvidedSplits(false)
        .jobInitialiser(new TextJobInitialiser())
        .minReducers(10)
        .maxReducers(100)
        .build();

graph.execute(operation, new User());
```

-----------------------------------------------

#### Add elements from hdfs with multiple input

As Java:


```java
final Map<String, String> inputMapperMap = new HashMap<>();
inputMapperMap.put("/path/to/first/inputFileOrFolder", TextMapperGeneratorImpl.class.getName());
inputMapperMap.put("/path/to/second/inputFileOrFolder", TextMapperGeneratorImpl.class.getName());

final AddElementsFromHdfs operation = new AddElementsFromHdfs.Builder()
        .inputMapperPairs(inputMapperMap)
        .addInputMapperPair("/path/to/third/inputFileOrFolder", TextMapperGeneratorImpl.class.getName())
        .outputPath("/path/to/output/folder")
        .failurePath("/path/to/failure/folder")
        .splitsFilePath("/path/to/splits/file")
        .workingPath("/tmp/workingDir")
        .useProvidedSplits(false)
        .jobInitialiser(new TextJobInitialiser())
        .minReducers(10)
        .maxReducers(100)
        .build();
```

-----------------------------------------------




AddElementsFromKafka example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromKafka](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromKafka.html).

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

### Required fields
The following fields are required: 
- topic
- groupId
- bootstrapServers
- elementGenerator


#### Add elements from kafka

As Java:


```java
final AddElementsFromKafka op = new AddElementsFromKafka.Builder()
        .bootstrapServers("hostname1:8080,hostname2:8080")
        .groupId("groupId1")
        .topic("topic1")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromKafka",
  "topic" : "topic1",
  "groupId" : "groupId1",
  "bootstrapServers" : [ "hostname1:8080,hostname2:8080" ],
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "parallelism" : 1,
  "validate" : true,
  "skipInvalidElements" : false
}
```

-----------------------------------------------




AddElementsFromSocket example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromSocket](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromSocket.html).

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

### Required fields
The following fields are required: 
- hostname
- port
- elementGenerator


#### Add elements from socket

As Java:


```java
final AddElementsFromSocket op = new AddElementsFromSocket.Builder()
        .hostname("localhost")
        .port(8080)
        .delimiter("\n")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromSocket",
  "hostname" : "localhost",
  "port" : 8080,
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "parallelism" : 1,
  "validate" : true,
  "skipInvalidElements" : false,
  "delimiter" : "\n"
}
```

-----------------------------------------------




CountGroups example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.CountGroups](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/CountGroups.html).

### Required fields
No required fields


#### Count all element groups

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new CountGroups())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.CountGroups"
  } ]
}
```

Result:

```
GroupCounts[entityGroups={entity=5},edgeGroups={edge=6},limitHit=false]
```
-----------------------------------------------

#### Count all element groups with limit

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new CountGroups(5))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.CountGroups",
    "limit" : 5
  } ]
}
```

Result:

```
GroupCounts[entityGroups={entity=2},edgeGroups={edge=3},limitHit=true]
```
-----------------------------------------------




ExportToGafferResultCache example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/ExportToGafferResultCache.html).

### Required fields
No required fields


#### Simple export and get

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetGafferResultCacheExport())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "key" : "ALL"
  } ]
}
```

Result:

```
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Export and get job details

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<JobDetail> exportOpChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails"
  } ]
}
```

Result:

```
JobDetail[jobId=dd29cba4-5741-4ad8-8b30-6d7f4dede6de,userId=user01,status=RUNNING,startTime=1505915443969,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@2ef1b8e4, uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache@499c48a6, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@5b0b937b, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@3dc6d401]]]
```
-----------------------------------------------

#### Get export

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetGafferResultCacheExport.Builder()
                .jobId(jobDetail.getJobId())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "jobId" : "dd29cba4-5741-4ad8-8b30-6d7f4dede6de",
    "key" : "ALL"
  } ]
}
```

Result:

```
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Export multiple results to gaffer result cache and get all results

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Map<String, CloseableIterable<?>>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache.Builder<>()
                .key("edges")
                .build())
        .then(new DiscardOutput())
        .then(new GetAllElements())
        .then(new ExportToGafferResultCache.Builder<>()
                .key("entities")
                .build())
        .then(new DiscardOutput())
        .then(new GetExports.Builder()
                .exports(new GetGafferResultCacheExport.Builder()
                                .key("edges")
                                .build(),
                        new GetGafferResultCacheExport.Builder()
                                .key("entities")
                                .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
    "key" : "edges"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
    "key" : "entities"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.GetExports",
    "getExports" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "key" : "edges"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "key" : "entities"
    } ]
  } ]
}
```

Result:

```
uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: edges:
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: entities:
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------




ExportToOtherAuthorisedGraph example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherAuthorisedGraph](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherAuthorisedGraph.html).

These export examples export all edges in the example graph to another Gaffer instance using Operation Auths against the user. 

To add this operation to your Gaffer graph you will need to write your own version of [ExportToOtherAuthorisedGraphOperationDeclarations.json](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/road-traffic-rest/src/main/resources/ExportToOtherAuthorisedGraphOperationDeclarations.json) containing the user auths, and then set this property: gaffer.store.operation.declarations=/path/to/ExportToOtherAuthorisedGraphOperationDeclarations.json


### Required fields
The following fields are required: 
- graphId


#### Export to preconfigured graph

This example will export all Edges with group 'edge' to another Gaffer graph with ID 'graph2'. The graph will be loaded from the configured GraphLibrary, so it must already exist. In order to export to graph2 the user must have the required user authorisations that were configured for this operation.

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherAuthorisedGraph.Builder()
                        .graphId("graph2")
                        .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherAuthorisedGraph",
    "graphId" : "graph2"
  } ]
}
```

-----------------------------------------------

#### Export to new graph using preconfigured schema and properties

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have a parent Schema and Store Properties within the graph library specified by the ID's schemaId1 and storePropsId1. In order to export to newGraphId with storePropsId1 and schemaId1 the user must have the required user authorisations that were configured for this operation to use each of these 3 ids.

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherAuthorisedGraph.Builder()
                        .graphId("newGraphId")
                        .parentStorePropertiesId("storePropsId1")
                        .parentSchemaIds("schemaId1")
                        .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherAuthorisedGraph",
    "graphId" : "newGraphId",
    "parentSchemaIds" : [ "schemaId1" ],
    "parentStorePropertiesId" : "storePropsId1"
  } ]
}
```

-----------------------------------------------




ExportToOtherGraph example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherGraph.html).

These export examples export all edges in the example graph to another Gaffer instance. 

To add this operation to your Gaffer graph you will need to include the ExportToOtherGraphOperationDeclarations.json in your store properties, i.e. set this property: gaffer.store.operation.declarations=ExportToOtherGraphOperationDeclarations.json


### Required fields
The following fields are required: 
- graphId


#### Simple export

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the same schema and same store properties as the current graph. In this case it will just create another table in accumulo called 'newGraphId'.

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("newGraphId")
                        .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "newGraphId"
  } ]
}
```

-----------------------------------------------

#### Simple export with custom graph

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the custom provided schema (note it must contain the same Edge group 'edge' otherwise the exported edges will be invalid') and custom store properties. The store properties could be any store properties e.g. Accumulo, HBase, Map, Proxy store properties.

As Java:


```java
final Schema schema = Schema.fromJson(StreamUtil.openStreams(getClass(), "operation/schema"));
final StoreProperties storeProperties = StoreProperties.loadStoreProperties(StreamUtil.openStream(getClass(), "othermockaccumulostore.properties"));
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("newGraphId")
                        .schema(schema)
                        .storeProperties(storeProperties)
                        .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "newGraphId",
    "schema" : {
      "edges" : {
        "edge" : {
          "properties" : {
            "count" : "int"
          },
          "groupBy" : [ ],
          "destination" : "int",
          "directed" : "true",
          "source" : "int"
        }
      },
      "entities" : {
        "entity" : {
          "properties" : {
            "count" : "int"
          },
          "groupBy" : [ ],
          "vertex" : "int"
        }
      },
      "types" : {
        "int" : {
          "aggregateFunction" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
          },
          "class" : "java.lang.Integer"
        },
        "true" : {
          "validateFunctions" : [ {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
          } ],
          "class" : "java.lang.Boolean"
        }
      }
    },
    "storeProperties" : {
      "accumulo.instance" : "someInstanceName",
      "gaffer.cache.service.class" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService",
      "accumulo.password" : "password",
      "accumulo.zookeepers" : "aZookeeper",
      "gaffer.store.class" : "uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore",
      "gaffer.store.job.tracker.enabled" : "true",
      "gaffer.store.operation.declarations" : "ExportToOtherGraphOperationDeclarations.json",
      "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.accumulostore.AccumuloProperties",
      "accumulo.user" : "user01"
    }
  } ]
}
```

-----------------------------------------------

#### Simple to other gaffer rest api

This example will export all Edges with group 'edge' to another Gaffer REST API.To export to another Gaffer REST API, we go via a Gaffer Proxy Store. We just need to tell the proxy store the host, port and context root of the REST API.Note that you will need to include the proxy-store module as a maven dependency to do this.

As Java:


```java
final ProxyProperties proxyProperties = new ProxyProperties();
proxyProperties.setStoreClass(ProxyStore.class);
proxyProperties.setStorePropertiesClass(ProxyProperties.class);
proxyProperties.setGafferHost("localhost");
proxyProperties.setGafferPort(8081);
proxyProperties.setGafferContextRoot("/rest/v1");

final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("otherGafferRestApiGraphId")
                        .storeProperties(proxyProperties)
                        .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "otherGafferRestApiGraphId",
    "storeProperties" : {
      "gaffer.host" : "localhost",
      "gaffer.context-root" : "/rest/v1",
      "gaffer.store.class" : "uk.gov.gchq.gaffer.proxystore.ProxyStore",
      "gaffer.port" : "8081",
      "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.proxystore.ProxyProperties"
    }
  } ]
}
```

-----------------------------------------------

#### Simple export using graph from graph library

This example will export all Edges with group 'edge' to another existing graph 'exportGraphId' using a GraphLibrary.We demonstrate here that if we use a GraphLibrary, we can register a graph ID and reference it from the export operation. This means the user does not have to proxy all the schema and store properties when they configure the export operation, they can just provide the ID.

As Java:


```java
// Setup the graphLibrary with an export graph
final GraphLibrary graphLibrary = new FileGraphLibrary("target/graphLibrary");

final AccumuloProperties exportStoreProperties = new AccumuloProperties();
exportStoreProperties.setId("exportStorePropertiesId");
// set other store property config here.

final Schema exportSchema = new Schema.Builder()
        .id("exportSchemaId")
        .edge("edge", new SchemaEdgeDefinition.Builder()
                .source("int")
                .destination("int")
                .directed("true")
                .property("count", "int")
                .aggregate(false)
                .build())
        .type("int", Integer.class)
        .type("true", new TypeDefinition.Builder()
                .clazz(Boolean.class)
                .validateFunctions(new IsTrue())
                .build())
        .build();

graphLibrary.addOrUpdate("exportGraphId", exportSchema, exportStoreProperties);

final Graph graph = new Graph.Builder()
        .config(StreamUtil.openStream(getClass(), "graphConfigWithLibrary.json"))
        .addSchemas(StreamUtil.openStreams(getClass(), "operation/schema"))
        .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
        .build();

final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("exportGraphId")
                        .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "exportGraphId"
  } ]
}
```

-----------------------------------------------

#### Export to new graph based on config from graph library

Similar to the previous example, this example will export all Edges with group 'edge' to another graph using a GraphLibrary. But in this example we show that you can export to a new graph with id newGraphId by choosing any combination of schema and store properties registered in the GraphLibrary. This is useful as a system administrator could register various different store properties, of different Accumulo/HBase clusters and a user could them just select which one to use by referring to the relevant store properties ID.

As Java:


```java
// Setup the graphLibrary with a schema and store properties for exporting
final GraphLibrary graphLibrary = new FileGraphLibrary("target/graphLibrary");

final AccumuloProperties exportStoreProperties = new AccumuloProperties();
exportStoreProperties.setId("exportStorePropertiesId");
// set other store property config here.
graphLibrary.addProperties("exportStorePropertiesId", exportStoreProperties);

final Schema exportSchema = new Schema.Builder()
        .id("exportSchemaId")
        .edge("edge", new SchemaEdgeDefinition.Builder()
                .source("int")
                .destination("int")
                .directed("true")
                .property("count", "int")
                .aggregate(false)
                .build())
        .type("int", Integer.class)
        .type("true", new TypeDefinition.Builder()
                .clazz(Boolean.class)
                .validateFunctions(new IsTrue())
                .build())
        .build();
graphLibrary.addSchema("exportSchemaId", exportSchema);

final Graph graph = new Graph.Builder()
        .config(StreamUtil.openStream(getClass(), "graphConfigWithLibrary.json"))
        .addSchemas(StreamUtil.openStreams(getClass(), "operation/schema"))
        .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
        .build();

final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("newGraphId")
                        .parentSchemaIds("exportSchemaId")
                        .parentStorePropertiesId("exportStorePropertiesId")
                        .build())
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "newGraphId",
    "parentSchemaIds" : [ "exportSchemaId" ],
    "parentStorePropertiesId" : "exportStorePropertiesId"
  } ]
}
```

-----------------------------------------------




ExportToSet example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/ExportToSet.html).

### Required fields
No required fields


#### Simple export and get

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
    "start" : 0
  } ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Simple export and get with pagination

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport.Builder()
                .start(2)
                .end(4)
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
    "start" : 2,
    "end" : 4
  } ]
}
```

Result:

```
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### Export multiple results to set and get all results

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Map<String, CloseableIterable<?>>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet.Builder<>()
                .key("edges")
                .build())
        .then(new DiscardOutput())
        .then(new GetAllElements())
        .then(new ExportToSet.Builder<>()
                .key("entities")
                .build())
        .then(new DiscardOutput())
        .then(new GetExports.Builder()
                .exports(new GetSetExport.Builder()
                                .key("edges")
                                .build(),
                        new GetSetExport.Builder()
                                .key("entities")
                                .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet",
    "key" : "edges"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet",
    "key" : "entities"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.GetExports",
    "getExports" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
      "key" : "edges",
      "start" : 0
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
      "key" : "entities",
      "start" : 0
    } ]
  } ]
}
```

Result:

```
uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: edges:
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: entities:
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------




GenerateElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateElements.html).

### Required fields
The following fields are required: 
- elementGenerator


#### Generate elements from strings

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GenerateElements<String> operation = new GenerateElements.Builder<String>()
        .input("1,1", "1,2,1")
        .generator(new ElementGenerator())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements",
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator"
  },
  "input" : [ "1,1", "1,2,1" ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### Generate elements from domain objects

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GenerateElements<Object> operation = new GenerateElements.Builder<>()
        .input(new DomainObject1(1, 1),
                new DomainObject2(1, 2, 1))
        .generator(new DomainObjectGenerator())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements",
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObjectGenerator"
  },
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject1",
    "a" : 1,
    "c" : 1
  }, {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject2",
    "a" : 1,
    "b" : 2,
    "c" : 1
  } ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




GenerateObjects example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateObjects.html).

### Required fields
The following fields are required: 
- elementGenerator


#### Generate strings from elements

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GenerateObjects<String> operation = new GenerateObjects.Builder<String>()
        .input(new Entity.Builder()
                        .group("entity")
                        .vertex(6)
                        .property("count", 1)
                        .build(),
                new Edge.Builder()
                        .group("edge")
                        .source(5).dest(6).directed(true)
                        .property("count", 1)
                        .build())
        .generator(new ObjectGenerator())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects",
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.generator.ObjectGenerator"
  },
  "input" : [ {
    "group" : "entity",
    "vertex" : 6,
    "properties" : {
      "count" : 1
    },
    "class" : "uk.gov.gchq.gaffer.data.element.Entity"
  }, {
    "group" : "edge",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "properties" : {
      "count" : 1
    },
    "class" : "uk.gov.gchq.gaffer.data.element.Edge"
  } ]
}
```

Result:

```
6,1
5,6,1
```
-----------------------------------------------

#### Generate domain objects from elements

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GenerateObjects<Object> operation = new GenerateObjects.Builder<>()
        .input(new Entity.Builder()
                        .group("entity")
                        .vertex(6)
                        .property("count", 1)
                        .build(),
                new Edge.Builder()
                        .group("edge")
                        .source(5).dest(6).directed(true)
                        .property("count", 1)
                        .build())
        .generator(new DomainObjectGenerator())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects",
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateObjectsExample$DomainObjectGenerator"
  },
  "input" : [ {
    "group" : "entity",
    "vertex" : 6,
    "properties" : {
      "count" : 1
    },
    "class" : "uk.gov.gchq.gaffer.data.element.Entity"
  }, {
    "group" : "edge",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "properties" : {
      "count" : 1
    },
    "class" : "uk.gov.gchq.gaffer.data.element.Edge"
  } ]
}
```

Result:

```
GenerateObjectsExample.DomainObject1[a=6,c=1]
GenerateObjectsExample.DomainObject2[a=5,b=6,c=1]
```
-----------------------------------------------




GetAdjacentIds example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAdjacentIds.html).

### Required fields
No required fields


#### Get adjacent ids from vertex 2

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
EntitySeed[vertex=3]
EntitySeed[vertex=4]
EntitySeed[vertex=5]
EntitySeed[vertex=1]
```
-----------------------------------------------

#### Get adjacent ids along outbound edges from vertex 2

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "includeIncomingOutGoing" : "OUTGOING",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
EntitySeed[vertex=3]
EntitySeed[vertex=4]
EntitySeed[vertex=5]
```
-----------------------------------------------

#### Get adjacent ids along outbound edges from vertex 2 with count greater than 1

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          },
          "selection" : [ "count" ]
        } ]
      }
    },
    "entities" : { }
  },
  "includeIncomingOutGoing" : "OUTGOING",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
EntitySeed[vertex=3]
```
-----------------------------------------------




GetAllElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetAllElements](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAllElements.html).

### Required fields
No required fields


#### Get all elements

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetAllElements operation = new GetAllElements();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Get all elements with count greater than 2

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetAllElements operation = new GetAllElements.Builder()
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 2
          },
          "selection" : [ "count" ]
        } ]
      }
    },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 2
          },
          "selection" : [ "count" ]
        } ]
      }
    }
  }
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------




GetAllJobDetails example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetAllJobDetails.html).

### Required fields
No required fields


#### Get all job details

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetAllJobDetails operation = new GetAllJobDetails();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails"
}
```

Result:

```
JobDetail[jobId=d4de5ebe-b5cf-4c95-957b-18137c20977b,userId=UNKNOWN,status=FINISHED,startTime=1505915447172,endTime=1505915447172,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements@56e6ea1a, AddElements[validate=true,skipInvalidElements=false,elements=uk.gov.gchq.gaffer.data.generator.OneToOneElementGenerator$1@4575d7ad]]]]
JobDetail[jobId=4d8f932a-92fb-4e51-ac32-7134382f421d,userId=user01,status=RUNNING,startTime=1505915447174,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails@4e89da03]]]
```
-----------------------------------------------




GetElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetElements](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html).

### Required fields
No required fields


#### Get entities and edges by entity id 2 and edge id 2 to 3

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  }, {
    "source" : 2,
    "destination" : 3,
    "directedType" : "EITHER",
    "matchedVertex" : "SOURCE",
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed"
  } ]
}
```

Result:

```
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
```
-----------------------------------------------

#### Get entities and edges by entity id 2 and edge id 2 to 3 with count more than 1

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          },
          "selection" : [ "count" ]
        } ]
      }
    },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          },
          "selection" : [ "count" ]
        } ]
      }
    }
  },
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  }, {
    "source" : 2,
    "destination" : 3,
    "directedType" : "EITHER",
    "matchedVertex" : "SOURCE",
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed"
  } ]
}
```

Result:

```
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
```
-----------------------------------------------

#### Get entities and edges that are related to vertex 2

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Get all entities and edges that are related to edge 1 to 2

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EdgeSeed(1, 2, DirectedType.EITHER))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "source" : 1,
    "destination" : 2,
    "directedType" : "EITHER",
    "matchedVertex" : "SOURCE",
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed"
  } ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Get all entities and edges that are related to edge 1 to 2 with count more than 1

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EdgeSeed(1, 2, DirectedType.EITHER))
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          },
          "selection" : [ "count" ]
        } ]
      }
    },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          },
          "selection" : [ "count" ]
        } ]
      }
    }
  },
  "input" : [ {
    "source" : 1,
    "destination" : 2,
    "directedType" : "EITHER",
    "matchedVertex" : "SOURCE",
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed"
  } ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Get entities related to 2 with count less than 2 or more than 5

When using an Or predicate with a single selected value you can just do 'select(propertyName)' then 'execute(new Or(predicates))'

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(
                                new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new Or<>(new IsLessThan(2), new IsMoreThan(5)))
                                        .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "edges" : { },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
            "predicates" : [ {
              "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
              "orEqualTo" : false,
              "value" : 2
            }, {
              "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
              "orEqualTo" : false,
              "value" : 5
            } ]
          },
          "selection" : [ "count" ]
        } ]
      }
    }
  },
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  }, {
    "source" : 2,
    "destination" : 3,
    "directedType" : "EITHER",
    "matchedVertex" : "SOURCE",
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed"
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### Get edges related to 2 when source is less than 2 or destination is more than 3

When using an Or predicate with a multiple selected values, it is more complicated. First, you need to select all the values you want: 'select(a, b, c)'. This will create an array of the selected values, [a, b, c]. You then need to use the Or.Builder to build your Or predicate, using .select() then .execute(). When selecting values in the Or.Builder you need to refer to the position in the [a,b,c] array. So to use property 'a', use position 0 - select(0).

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .view(new View.Builder()
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(
                                new ElementFilter.Builder()
                                        .select(IdentifierType.SOURCE.name(), IdentifierType.DESTINATION.name())
                                        .execute(new Or.Builder<>()
                                                .select(0)
                                                .execute(new IsLessThan(2))
                                                .select(1)
                                                .execute(new IsMoreThan(3))
                                                .build())
                                        .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
            "predicates" : [ {
              "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
                "orEqualTo" : false,
                "value" : 2
              },
              "selection" : [ 0 ]
            }, {
              "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                "orEqualTo" : false,
                "value" : 3
              },
              "selection" : [ 1 ]
            } ]
          },
          "selection" : [ "SOURCE", "DESTINATION" ]
        } ]
      }
    },
    "entities" : { }
  },
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Get entities and return only some properties

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final Concat concat = new Concat();
concat.setSeparator("|");
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .view(new View.Builder()
                .edge("edge", new ViewElementDefinition.Builder()
                        .transientProperty("vertex|count", String.class)
                        .properties("vertex|count")
                        .transformer(new ElementTransformer.Builder()
                                .select(IdentifierType.SOURCE.name(), "count")
                                .execute(concat)
                                .project("vertex|count")
                                .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "edges" : {
      "edge" : {
        "properties" : [ "vertex|count" ],
        "transientProperties" : {
          "vertex|count" : "java.lang.String"
        },
        "transformFunctions" : [ {
          "function" : {
            "class" : "uk.gov.gchq.koryphe.impl.function.Concat",
            "separator" : "|"
          },
          "projection" : [ "vertex|count" ],
          "selection" : [ "SOURCE", "count" ]
        } ]
      }
    },
    "entities" : { }
  },
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[vertex|count=<java.lang.String>1|3]]
```
-----------------------------------------------

#### Get entities and exclude properties

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final Concat concat = new Concat();
concat.setSeparator("|");
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .view(new View.Builder()
                .edge("edge", new ViewElementDefinition.Builder()
                        .transientProperty("vertex|count", String.class)
                        .excludeProperties("count")
                        .transformer(new ElementTransformer.Builder()
                                .select(IdentifierType.SOURCE.name(), "count")
                                .execute(concat)
                                .project("vertex|count")
                                .build())
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "edges" : {
      "edge" : {
        "excludeProperties" : [ "count" ],
        "transientProperties" : {
          "vertex|count" : "java.lang.String"
        },
        "transformFunctions" : [ {
          "function" : {
            "class" : "uk.gov.gchq.koryphe.impl.function.Concat",
            "separator" : "|"
          },
          "projection" : [ "vertex|count" ],
          "selection" : [ "SOURCE", "count" ]
        } ]
      }
    },
    "entities" : { }
  },
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[vertex|count=<java.lang.String>1|3]]
```
-----------------------------------------------




GetGafferResultCacheExport example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/GetGafferResultCacheExport.html).

### Required fields
No required fields


#### Simple export and get

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetGafferResultCacheExport())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "key" : "ALL"
  } ]
}
```

Result:

```
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Export and get job details

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<JobDetail> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails"
  } ]
}
```

Result:

```
JobDetail[jobId=ffb303fa-f48f-4c39-8991-381f2f2d4b0a,userId=user01,status=RUNNING,startTime=1505915447887,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@781bf81d, uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache@5c281f5f, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@545ddd3, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@d07c7c9]]]
```
-----------------------------------------------

#### Get export

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetGafferResultCacheExport.Builder()
                .jobId(jobDetail.getJobId())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "jobId" : "ffb303fa-f48f-4c39-8991-381f2f2d4b0a",
    "key" : "ALL"
  } ]
}
```

Result:

```
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Export multiple results to gaffer result cache and get all results

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Map<String, CloseableIterable<?>>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache.Builder<>()
                .key("edges")
                .build())
        .then(new DiscardOutput())
        .then(new GetAllElements())
        .then(new ExportToGafferResultCache.Builder<>()
                .key("entities")
                .build())
        .then(new DiscardOutput())
        .then(new GetExports.Builder()
                .exports(new GetGafferResultCacheExport.Builder()
                                .key("edges")
                                .build(),
                        new GetGafferResultCacheExport.Builder()
                                .key("entities")
                                .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
    "key" : "edges"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
    "key" : "entities"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.GetExports",
    "getExports" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "key" : "edges"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "key" : "entities"
    } ]
  } ]
}
```

Result:

```
uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: edges:
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: entities:
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------




GetJobDetails example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetJobDetails.html).

### Required fields
No required fields


#### Get job details in operation chain

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<JobDetail> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails"
  } ]
}
```

Result:

```
JobDetail[jobId=fb56cf0a-25f4-49a0-ab1d-6ed36aa1b250,userId=user01,status=RUNNING,startTime=1505915448782,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@4ffb0b, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@249121af, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@48ca9243]]]
```
-----------------------------------------------

#### Get job details

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetJobDetails operation = new GetJobDetails.Builder()
        .jobId(jobId)
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails",
  "jobId" : "fb56cf0a-25f4-49a0-ab1d-6ed36aa1b250"
}
```

Result:

```
JobDetail[jobId=fb56cf0a-25f4-49a0-ab1d-6ed36aa1b250,userId=user01,status=FINISHED,startTime=1505915448782,endTime=1505915448782,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@4ffb0b, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@249121af, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@48ca9243]]]
```
-----------------------------------------------




GetJobResults example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetJobResults](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetJobResults.html).

### Required fields
No required fields


#### Get job results

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetJobResults operation = new GetJobResults.Builder()
        .jobId(jobId)
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobResults",
  "jobId" : "d257d1ba-e952-4391-948c-3563f3155923"
}
```

Result:

```
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------




GetSetExport example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/GetSetExport.html).

### Required fields
No required fields


#### Simple export and get

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
    "start" : 0
  } ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Simple export and get with pagination

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport.Builder()
                .start(2)
                .end(4)
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
    "start" : 2,
    "end" : 4
  } ]
}
```

Result:

```
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### Export multiple results to set and get all results

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Map<String, CloseableIterable<?>>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet.Builder<>()
                .key("edges")
                .build())
        .then(new DiscardOutput())
        .then(new GetAllElements())
        .then(new ExportToSet.Builder<>()
                .key("entities")
                .build())
        .then(new DiscardOutput())
        .then(new GetExports.Builder()
                .exports(new GetSetExport.Builder()
                                .key("edges")
                                .build(),
                        new GetSetExport.Builder()
                                .key("entities")
                                .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet",
    "key" : "edges"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet",
    "key" : "entities"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.GetExports",
    "getExports" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
      "key" : "edges",
      "start" : 0
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
      "key" : "entities",
      "start" : 0
    } ]
  } ]
}
```

Result:

```
uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: edges:
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: entities:
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------




Limit example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.Limit](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/Limit.html).

### Required fields
The following fields are required: 
- resultLimit


#### Limit elements to 3

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Limit<>(3))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
    "resultLimit" : 3,
    "truncate" : true
  } ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### Limit elements to 3 without truncation

Setting this flag to false will throw an error instead of truncating the iterable. In this case there are more than 3 elements, so when executed a LimitExceededException would be thrown.

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Limit<>(3, false))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
    "resultLimit" : 3,
    "truncate" : false
  } ]
}
```

-----------------------------------------------

#### Limit elements to 3 with builder

A builder can also be used to create the limit - note that truncate is set to true by default, so in this case it is redundant, but simply shown for demonstration.
Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Limit.Builder<Element>()
                .resultLimit(3)
                .truncate(true)
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
    "resultLimit" : 3,
    "truncate" : true
  } ]
}
```

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




Max example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Max](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Max.html).

### Required fields
The following fields are required: 
- comparators


#### Max count

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Element> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new Max.Builder()
                .comparators(new ElementPropertyComparator.Builder()
                        .groups("entity", "edge")
                        .property("count")
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}
```

Result:

```
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Max count and transient property

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Element> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("VERTEX", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("DESTINATION", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new Max.Builder()
                .comparators(
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .reverse(false)
                                .build(),
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("score")
                                .reverse(false)
                                .build()
                )
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : {
        "edge" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ],
            "selection" : [ "DESTINATION", "count" ]
          } ]
        }
      },
      "entities" : {
        "entity" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ],
            "selection" : [ "VERTEX", "count" ]
          } ]
        }
      }
    },
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "score",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}
```

Result:

```
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[score=<java.lang.Integer>6,count=<java.lang.Integer>3]]
```
-----------------------------------------------




Min example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Min](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Min.html).

### Required fields
The following fields are required: 
- comparators


#### Min count

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Element> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new Min.Builder()
                .comparators(new ElementPropertyComparator.Builder()
                        .groups("entity", "edge")
                        .property("count")
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### Min count and transient property

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Element> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("VERTEX", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("DESTINATION", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new Min.Builder()
                .comparators(
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .reverse(false)
                                .build(),
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("score")
                                .reverse(false)
                                .build()
                )
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : {
        "edge" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ],
            "selection" : [ "DESTINATION", "count" ]
          } ]
        }
      },
      "entities" : {
        "entity" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ],
            "selection" : [ "VERTEX", "count" ]
          } ]
        }
      }
    },
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "score",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[score=<java.lang.Integer>2,count=<java.lang.Integer>1]]
```
-----------------------------------------------




NamedOperation example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.named.operation.NamedOperation](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/operation/NamedOperation.html).

See [Named Operations](https://github.com/gchq/Gaffer/wiki/dev-guide#namedoperations) for information on configuring named operations for your Gaffer graph.

### Required fields
The following fields are required: 
- operationName


#### Add named operation

As Java:


```java
final AddNamedOperation operation = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .then(new GetAdjacentIds.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build())
        .description("2 hop query")
        .name("2-hop")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .overwrite()
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.named.operation.AddNamedOperation",
  "operationName" : "2-hop",
  "description" : "2 hop query",
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ],
  "overwriteFlag" : true,
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }
}
```

-----------------------------------------------

#### Add named operation with parameter

As Java:


```java
final String opChainString = "{" +
        "    \"operations\" : [ {" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"," +
        "      \"includeIncomingOutGoing\" : \"OUTGOING\"" +
        "    }, {" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"," +
        "      \"includeIncomingOutGoing\" : \"OUTGOING\"" +
        "    }, {" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\"," +
        "      \"resultLimit\" : \"${param1}\"" +
        "    }" +
        " ]" +
        "}";

ParameterDetail param = new ParameterDetail.Builder()
        .defaultValue(1L)
        .description("Limit param")
        .valueClass(Long.class)
        .build();
Map<String, ParameterDetail> paramMap = Maps.newHashMap();
paramMap.put("param1", param);

final AddNamedOperation operation = new AddNamedOperation.Builder()
        .operationChain(opChainString)
        .description("2 hop query with settable limit")
        .name("2-hop-with-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .parameters(paramMap)
        .overwrite()
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.named.operation.AddNamedOperation",
  "operationName" : "2-hop-with-limit",
  "description" : "2 hop query with settable limit",
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ],
  "overwriteFlag" : true,
  "parameters" : {
    "param1" : {
      "description" : "Limit param",
      "defaultValue" : 1,
      "valueClass" : "java.lang.Long",
      "required" : false
    }
  },
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
      "resultLimit" : "${param1}"
    } ]
  }
}
```

-----------------------------------------------

#### Get all named operations

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetAllNamedOperations operation = new GetAllNamedOperations();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.named.operation.GetAllNamedOperations"
}
```

Result:

```
NamedOperationDetail[creatorId=user01,operations={"operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"},{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"}]},readAccessRoles=[read-user],writeAccessRoles=[write-user]]
NamedOperationDetail[creatorId=user01,operations={    "operations" : [ {      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",      "includeIncomingOutGoing" : "OUTGOING"    }, {      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",      "includeIncomingOutGoing" : "OUTGOING"    }, {      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",      "resultLimit" : "${param1}"    } ]},readAccessRoles=[read-user],writeAccessRoles=[write-user],parameters={param1=ParameterDetail[description=Limit param,valueClass=class java.lang.Long,required=false,defaultValue=1]}]
```
-----------------------------------------------

#### Run named operation

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                .name("2-hop")
                .input(new EntitySeed(1))
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
  "operationName" : "2-hop",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
EntitySeed[vertex=3]
EntitySeed[vertex=4]
EntitySeed[vertex=5]
```
-----------------------------------------------

#### Run named operation with parameter

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
Map<String, Object> paramMap = Maps.newHashMap();
paramMap.put("param1", 2L);

final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                .name("2-hop-with-limit")
                .input(new EntitySeed(1))
                .parameters(paramMap)
                .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
  "operationName" : "2-hop-with-limit",
  "parameters" : {
    "param1" : 2
  },
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
EntitySeed[vertex=3]
EntitySeed[vertex=4]
```
-----------------------------------------------

#### Delete named operation

As Java:


```java
final DeleteNamedOperation operation = new DeleteNamedOperation.Builder()
        .name("2-hop")
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.named.operation.DeleteNamedOperation",
  "operationName" : "2-hop"
}
```

-----------------------------------------------




Sort example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Sort](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Sort.html).

### Required fields
The following fields are required: 
- comparators


#### Sort on count

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new Sort.Builder()
                .comparators(new ElementPropertyComparator.Builder()
                        .groups("entity", "edge")
                        .property("count")
                        .reverse(false)
                        .build())
                .resultLimit(10)
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Sort",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ],
    "resultLimit" : 10,
    "deduplicate" : true
  } ]
}
```

Result:

```
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Sort on count without deduplicating

Deduplication is true by default.
Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new Sort.Builder()
                .comparators(new ElementPropertyComparator.Builder()
                        .groups("entity", "edge")
                        .property("count")
                        .reverse(false)
                        .build())
                .resultLimit(10)
                .deduplicate(false)
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Sort",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ],
    "resultLimit" : 10,
    "deduplicate" : false
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
```
-----------------------------------------------

#### Sort on count and transient property

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("VERTEX", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("DESTINATION", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new Sort.Builder()
                .comparators(
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .reverse(false)
                                .build(),
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("score")
                                .reverse(false)
                                .build()
                )
                .resultLimit(4)
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : {
        "edge" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ],
            "selection" : [ "DESTINATION", "count" ]
          } ]
        }
      },
      "entities" : {
        "entity" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ],
            "selection" : [ "VERTEX", "count" ]
          } ]
        }
      }
    },
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Sort",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "score",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ],
    "resultLimit" : 4,
    "deduplicate" : true
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[score=<java.lang.Integer>2,count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[score=<java.lang.Integer>4,count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[score=<java.lang.Integer>4,count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[score=<java.lang.Integer>5,count=<java.lang.Integer>1]]
```
-----------------------------------------------




ToArray example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToArray](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToArray.html).

Note - conversion into an array is done in memory, so it is not advised for a large number of results.

### Required fields
No required fields


#### To array example

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<? extends Element[]> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToArray<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToArray"
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




ToCsv example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToCsv](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToCsv.html).

### Required fields
The following fields are required: 
- elementGenerator


#### To csv example

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends String>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToCsv.Builder()
                .includeHeader(true)
                .generator(new CsvGenerator.Builder()
                        .group("Edge group")
                        .vertex("vertex")
                        .source("source")
                        .property("count", "total count")
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToCsv",
    "elementGenerator" : {
      "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
      "fields" : {
        "GROUP" : "Edge group",
        "VERTEX" : "vertex",
        "SOURCE" : "source",
        "count" : "total count"
      },
      "constants" : { },
      "quoted" : false
    },
    "includeHeader" : true
  } ]
}
```

Result:

```
Edge group,vertex,source,total count
entity,2,1
edge,2,2
edge,2,1
edge,2,1
edge,1,3
entity,1,3
edge,1,3
edge,1,1
```
-----------------------------------------------




ToEntitySeeds example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToEntitySeeds](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToEntitySeeds.html).

### Required fields
No required fields


#### To stream example

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends EntitySeed>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToEntitySeeds())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToEntitySeeds"
  } ]
}
```

Result:

```
EntitySeed[vertex=Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]]
EntitySeed[vertex=Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]]
EntitySeed[vertex=Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
EntitySeed[vertex=Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
EntitySeed[vertex=Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]]
EntitySeed[vertex=Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]]
EntitySeed[vertex=Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]]
EntitySeed[vertex=Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
```
-----------------------------------------------




ToList example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToList](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToList.html).

Note - conversion into a List is done using an in memory ArrayList, so it is not advised for a large number of results.

### Required fields
No required fields


#### To list example

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<List<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToList<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToList"
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




ToMap example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToMap](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToMap.html).

### Required fields
The following fields are required: 
- elementGenerator


#### To map example

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Iterable<? extends Map<String, Object>>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToMap.Builder()
                .generator(new MapGenerator.Builder()
                        .group("group")
                        .vertex("vertex")
                        .source("source")
                        .property("count", "total count")
                        .build())
                .build())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToMap",
    "elementGenerator" : {
      "class" : "uk.gov.gchq.gaffer.data.generator.MapGenerator",
      "fields" : {
        "GROUP" : "group",
        "VERTEX" : "vertex",
        "SOURCE" : "source",
        "count" : "total count"
      },
      "constants" : { }
    }
  } ]
}
```

Result:

```
{group=entity, vertex=2, total count=1}
{group=edge, source=2, total count=2}
{group=edge, source=2, total count=1}
{group=edge, source=2, total count=1}
{group=edge, source=1, total count=3}
{group=entity, vertex=1, total count=3}
{group=edge, source=1, total count=3}
{group=edge, source=1, total count=1}
```
-----------------------------------------------




ToSet example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToSet](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToSet.html).

Note - conversion into a Set is done using an in memory LinkedHashSet, so it is not advised for a large number of results.

### Required fields
No required fields


#### Without to set operation

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(1), new EntitySeed(2))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "vertex" : 1,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  }, {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### With to set operation

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Set<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToSet<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




ToStream example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToStream](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToStream.html).

Note - conversion into a Stream is done in memory, so it is not advised for a large number of results.

### Required fields
No required fields


#### To stream example

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Stream<? extends Element>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToStream<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToStream"
  } ]
}
```

Result:

```
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




ToVertices example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToVertices](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToVertices.html).

In these examples we use a ToSet operation after the ToVertices operation to deduplicate the results.

### Required fields
No required fields


#### Extract entity vertices

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .view(new View.Builder()
                        .entity("entity")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.NONE)
                .build())
        .then(new ToSet<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : { },
      "entities" : {
        "entity" : { }
      }
    },
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "edgeVertices" : "NONE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}
```

Result:

```
1
2
```
-----------------------------------------------

#### Extract destination vertex

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.DESTINATION)
                .build())
        .then(new ToSet<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    },
    "includeIncomingOutGoing" : "OUTGOING",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "edgeVertices" : "DESTINATION"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}
```

Result:

```
2
4
3
5
```
-----------------------------------------------

#### Extract both source and destination vertices

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.BOTH)
                .build())
        .then(new ToSet<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    },
    "includeIncomingOutGoing" : "OUTGOING",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "edgeVertices" : "BOTH"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}
```

Result:

```
1
2
4
3
5
```
-----------------------------------------------

#### Extract matched vertices

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .useMatchedVertex(ToVertices.UseMatchedVertex.EQUAL)
                .build())
        .then(new ToSet<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    },
    "includeIncomingOutGoing" : "OUTGOING",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "useMatchedVertex" : "EQUAL"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}
```

Result:

```
1
2
```
-----------------------------------------------

#### Extract opposite matched vertices

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```

As Java:


```java
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .useMatchedVertex(ToVertices.UseMatchedVertex.OPPOSITE)
                .build())
        .then(new ToSet<>())
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "edges" : {
        "edge" : { }
      },
      "entities" : { }
    },
    "includeIncomingOutGoing" : "OUTGOING",
    "input" : [ {
      "vertex" : 1,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    }, {
      "vertex" : 2,
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "useMatchedVertex" : "OPPOSITE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}
```

Result:

```
2
4
3
5
```
-----------------------------------------------




