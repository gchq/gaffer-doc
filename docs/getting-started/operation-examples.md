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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.AddElements( 
  skip_invalid_elements=False, 
  input=[ 
    g.Entity( 
      vertex=6, 
      group="entity", 
      properties={'count': 1} 
    ), 
    g.Edge( 
      directed=True, 
      destination=6, 
      group="edge", 
      properties={'count': 1}, 
      source=5 
    ) 
  ], 
  validate=True 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromFile.html).

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

### Required fields
The following fields are required: 
- filename
- elementGenerator


#### Add elements from file


{% codetabs name="Java", type="java" -%}
final AddElementsFromFile op = new AddElementsFromFile.Builder()
        .filename("filename")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile",
  "filename" : "filename",
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "parallelism" : 1,
  "validate" : true,
  "skipInvalidElements" : false
}

{%- language name="Python", type="py" -%}
g.AddElementsFromFile( 
  parallelism=1, 
  element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
  validate=True, 
  filename="filename", 
  skip_invalid_elements=False 
)

{%- endcodetabs %}

-----------------------------------------------




AddElementsFromHdfs example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.hdfs.operation.AddElementsFromHdfs](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/hdfs/operation/AddElementsFromHdfs.html).

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

#### Add elements from hdfs main method

Example content for a main method that takes 5 arguments and runs an AddElementsFromHdfs



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




AddElementsFromKafka example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromKafka](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromKafka.html).

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

### Required fields
The following fields are required: 
- topic
- groupId
- bootstrapServers
- elementGenerator


#### Add elements from kafka


{% codetabs name="Java", type="java" -%}
final AddElementsFromKafka op = new AddElementsFromKafka.Builder()
        .bootstrapServers("hostname1:8080,hostname2:8080")
        .groupId("groupId1")
        .topic("topic1")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.AddElementsFromKafka( 
  topic="topic1", 
  parallelism=1, 
  group_id="groupId1", 
  bootstrap_servers=[ 
    "hostname1:8080,hostname2:8080" 
  ], 
  element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
  validate=True, 
  skip_invalid_elements=False 
)

{%- endcodetabs %}

-----------------------------------------------




AddElementsFromSocket example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromSocket](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromSocket.html).

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

### Required fields
The following fields are required: 
- hostname
- port
- elementGenerator


#### Add elements from socket


{% codetabs name="Java", type="java" -%}
final AddElementsFromSocket op = new AddElementsFromSocket.Builder()
        .hostname("localhost")
        .port(8080)
        .delimiter(",")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromSocket",
  "hostname" : "localhost",
  "port" : 8080,
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "parallelism" : 1,
  "validate" : true,
  "skipInvalidElements" : false,
  "delimiter" : ","
}

{%- language name="Python", type="py" -%}
g.AddElementsFromSocket( 
  parallelism=1, 
  element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
  skip_invalid_elements=False, 
  delimiter=",", 
  validate=True, 
  hostname="localhost", 
  port=8080 
)

{%- endcodetabs %}

-----------------------------------------------




CountGroups example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.CountGroups](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/CountGroups.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new CountGroups())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.CountGroups"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.CountGroups() 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new CountGroups(5))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.CountGroups",
    "limit" : 5
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.CountGroups( 
      limit=5 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

```
GroupCounts[entityGroups={entity=2},edgeGroups={edge=3},limitHit=true]
```
-----------------------------------------------




ExportToGafferResultCache example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/ExportToGafferResultCache.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetGafferResultCacheExport())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache(), 
    g.DiscardOutput(), 
    g.GetGafferResultCacheExport( 
      key="ALL" 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final OperationChain<JobDetail> exportOpChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache(), 
    g.DiscardOutput(), 
    g.GetJobDetails() 
  ] 
)

{%- endcodetabs %}

Result:

```
JobDetail[jobId=31fddcda-3f4b-454c-827c-65fa8f1ed9ef,userId=user01,status=RUNNING,startTime=1506500161870,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@6ab862dc, uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache@71ad1d56, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@1f7a8807, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@307918ae]]]
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


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetGafferResultCacheExport.Builder()
                .jobId(jobDetail.getJobId())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "jobId" : "31fddcda-3f4b-454c-827c-65fa8f1ed9ef",
    "key" : "ALL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetGafferResultCacheExport( 
      job_id="31fddcda-3f4b-454c-827c-65fa8f1ed9ef", 
      key="ALL" 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache( 
      key="edges" 
    ), 
    g.DiscardOutput(), 
    g.GetAllElements(), 
    g.ExportToGafferResultCache( 
      key="entities" 
    ), 
    g.DiscardOutput(), 
    g.GetExports( 
      get_exports=[ 
        g.GetGafferResultCacheExport( 
          key="edges" 
        ), 
        g.GetGafferResultCacheExport( 
          key="entities" 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherAuthorisedGraph](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherAuthorisedGraph.html).

These export examples export all edges in the example graph to another Gaffer instance using Operation Auths against the user. 

To add this operation to your Gaffer graph you will need to write your own version of [ExportToOtherAuthorisedGraphOperationDeclarations.json](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/road-traffic-rest/src/main/resources/ExportToOtherAuthorisedGraphOperationDeclarations.json) containing the user auths, and then set this property: gaffer.store.operation.declarations=/path/to/ExportToOtherAuthorisedGraphOperationDeclarations.json


### Required fields
The following fields are required: 
- graphId


#### Export to preconfigured graph

This example will export all Edges with group 'edge' to another Gaffer graph with ID 'graph2'. The graph will be loaded from the configured GraphLibrary, so it must already exist. In order to export to graph2 the user must have the required user authorisations that were configured for this operation.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        entities=[ 
        ] 
      ) 
    ), 
    g.ExportToOtherAuthorisedGraph( 
      graph_id="graph2" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

#### Export to new graph using preconfigured schema and properties

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have a parent Schema and Store Properties within the graph library specified by the ID's schemaId1 and storePropsId1. In order to export to newGraphId with storePropsId1 and schemaId1 the user must have the required user authorisations that were configured for this operation to use each of these 3 ids.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        entities=[ 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ] 
      ) 
    ), 
    g.ExportToOtherAuthorisedGraph( 
      parent_schema_ids=[ 
        "schemaId1" 
      ], 
      parent_store_properties_id="storePropsId1", 
      graph_id="newGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------




ExportToOtherGraph example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherGraph.html).

These export examples export all edges in the example graph to another Gaffer instance. 

To add this operation to your Gaffer graph you will need to include the ExportToOtherGraphOperationDeclarations.json in your store properties, i.e. set this property: gaffer.store.operation.declarations=ExportToOtherGraphOperationDeclarations.json


### Required fields
The following fields are required: 
- graphId


#### Simple export

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the same schema and same store properties as the current graph. In this case it will just create another table in accumulo called 'newGraphId'.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        entities=[ 
        ] 
      ) 
    ), 
    g.ExportToOtherGraph( 
      graph_id="newGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

#### Simple export with custom graph

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the custom provided schema (note it must contain the same Edge group 'edge' otherwise the exported edges will be invalid') and custom store properties. The store properties could be any store properties e.g. Accumulo, HBase, Map, Proxy store properties.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        entities=[ 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ] 
      ) 
    ), 
    g.ExportToOtherGraph( 
      store_properties={'gaffer.cache.service.class': 'uk.gov.gchq.gaffer.cache.impl.HashMapCacheService', 'accumulo.user': 'user01', 'accumulo.zookeepers': 'aZookeeper', 'gaffer.store.job.tracker.enabled': 'true', 'gaffer.store.operation.declarations': 'ExportToOtherGraphOperationDeclarations.json', 'gaffer.store.properties.class': 'uk.gov.gchq.gaffer.accumulostore.AccumuloProperties', 'gaffer.store.class': 'uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore', 'accumulo.password': 'password', 'accumulo.instance': 'someInstanceName'}, 
      schema={'entities': {'entity': {'vertex': 'int', 'groupBy': [], 'properties': {'count': 'int'}}}, 'types': {'int': {'aggregateFunction': {'class': 'uk.gov.gchq.koryphe.impl.binaryoperator.Sum'}, 'class': 'java.lang.Integer'}, 'true': {'class': 'java.lang.Boolean', 'validateFunctions': [{"class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"}]}}, 'edges': {'edge': {'directed': 'true', 'groupBy': [], 'properties': {'count': 'int'}, 'source': 'int', 'destination': 'int'}}}, 
      graph_id="newGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

#### Simple to other gaffer rest api

This example will export all Edges with group 'edge' to another Gaffer REST API.To export to another Gaffer REST API, we go via a Gaffer Proxy Store. We just need to tell the proxy store the host, port and context root of the REST API.Note that you will need to include the proxy-store module as a maven dependency to do this.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        entities=[ 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ] 
      ) 
    ), 
    g.ExportToOtherGraph( 
      store_properties={'gaffer.store.properties.class': 'uk.gov.gchq.gaffer.proxystore.ProxyProperties', 'gaffer.port': '8081', 'gaffer.host': 'localhost', 'gaffer.store.class': 'uk.gov.gchq.gaffer.proxystore.ProxyStore', 'gaffer.context-root': '/rest/v1'}, 
      graph_id="otherGafferRestApiGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

#### Simple export using graph from graph library

This example will export all Edges with group 'edge' to another existing graph 'exportGraphId' using a GraphLibrary.We demonstrate here that if we use a GraphLibrary, we can register a graph ID and reference it from the export operation. This means the user does not have to proxy all the schema and store properties when they configure the export operation, they can just provide the ID.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        entities=[ 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ] 
      ) 
    ), 
    g.ExportToOtherGraph( 
      graph_id="exportGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

#### Export to new graph based on config from graph library

Similar to the previous example, this example will export all Edges with group 'edge' to another graph using a GraphLibrary. But in this example we show that you can export to a new graph with id newGraphId by choosing any combination of schema and store properties registered in the GraphLibrary. This is useful as a system administrator could register various different store properties, of different Accumulo/HBase clusters and a user could them just select which one to use by referring to the relevant store properties ID.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        entities=[ 
        ] 
      ) 
    ), 
    g.ExportToOtherGraph( 
      parent_schema_ids=[ 
        "exportSchemaId" 
      ], 
      parent_store_properties_id="exportStorePropertiesId", 
      graph_id="newGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------




ExportToSet example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/ExportToSet.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet(), 
    g.DiscardOutput(), 
    g.GetSetExport( 
      start=0 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport.Builder()
                .start(2)
                .end(4)
                .build())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet(), 
    g.DiscardOutput(), 
    g.GetSetExport( 
      end=4, 
      start=2 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet( 
      key="edges" 
    ), 
    g.DiscardOutput(), 
    g.GetAllElements(), 
    g.ExportToSet( 
      key="entities" 
    ), 
    g.DiscardOutput(), 
    g.GetExports( 
      get_exports=[ 
        g.GetSetExport( 
          start=0, 
          key="edges" 
        ), 
        g.GetSetExport( 
          start=0, 
          key="entities" 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateElements.html).

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


{% codetabs name="Java", type="java" -%}
final GenerateElements<String> operation = new GenerateElements.Builder<String>()
        .input("1,1", "1,2,1")
        .generator(new ElementGenerator())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements",
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator"
  },
  "input" : [ "1,1", "1,2,1" ]
}

{%- language name="Python", type="py" -%}
g.GenerateElements( 
  element_generator=g.ElementGenerator( 
    fields={}, 
    class_name="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator" 
  ), 
  input=[ 
    "1,1", 
    "1,2,1" 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final GenerateElements<Object> operation = new GenerateElements.Builder<>()
        .input(new DomainObject1(1, 1),
                new DomainObject2(1, 2, 1))
        .generator(new DomainObjectGenerator())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GenerateElements( 
  input=[ 
    {'a': 1, 'c': 1, 'class': 'uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject1'}, 
    {'b': 2, 'a': 1, 'c': 1, 'class': 'uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject2'} 
  ], 
  element_generator=g.ElementGenerator( 
    class_name="uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObjectGenerator", 
    fields={} 
  ) 
)

{%- endcodetabs %}

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




GenerateObjects example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateObjects.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GenerateObjects( 
  input=[ 
    g.Entity( 
      properties={'count': 1}, 
      vertex=6, 
      group="entity" 
    ), 
    g.Edge( 
      properties={'count': 1}, 
      group="edge", 
      destination=6, 
      source=5, 
      directed=True 
    ) 
  ], 
  element_generator=g.ElementGenerator( 
    class_name="uk.gov.gchq.gaffer.doc.operation.generator.ObjectGenerator", 
    fields={} 
  ) 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GenerateObjects( 
  element_generator=g.ElementGenerator( 
    class_name="uk.gov.gchq.gaffer.doc.operation.GenerateObjectsExample$DomainObjectGenerator", 
    fields={} 
  ), 
  input=[ 
    g.Entity( 
      vertex=6, 
      properties={'count': 1}, 
      group="entity" 
    ), 
    g.Edge( 
      source=5, 
      properties={'count': 1}, 
      destination=6, 
      group="edge", 
      directed=True 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

```
GenerateObjectsExample.DomainObject1[a=6,c=1]
GenerateObjectsExample.DomainObject2[a=5,b=6,c=1]
```
-----------------------------------------------




GetAdjacentIds example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAdjacentIds.html).

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


{% codetabs name="Java", type="java" -%}
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}

{%- language name="Python", type="py" -%}
g.GetAdjacentIds( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "includeIncomingOutGoing" : "OUTGOING",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}

{%- language name="Python", type="py" -%}
g.GetAdjacentIds( 
  include_incoming_out_going="OUTGOING", 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
        .view(new View.Builder()
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetAdjacentIds( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            predicate=g.IsMoreThan( 
              or_equal_to=False, 
              value=1 
            ), 
            selection=[ 
              "count" 
            ] 
          ) 
        ], 
        group="edge" 
      ) 
    ], 
    entities=[ 
    ] 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ], 
  include_incoming_out_going="OUTGOING" 
)

{%- endcodetabs %}

Result:

```
EntitySeed[vertex=3]
```
-----------------------------------------------




GetAllElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetAllElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAllElements.html).

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


{% codetabs name="Java", type="java" -%}
final GetAllElements operation = new GetAllElements();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
}

{%- language name="Python", type="py" -%}
g.GetAllElements()

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetAllElements( 
  view=g.View( 
    entities=[ 
      g.ElementDefinition( 
        group="entity", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value=2, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    edges=[ 
      g.ElementDefinition( 
        group="edge", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value=2, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ] 
  ) 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetAllJobDetails.html).

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


{% codetabs name="Java", type="java" -%}
final GetAllJobDetails operation = new GetAllJobDetails();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails"
}

{%- language name="Python", type="py" -%}
g.GetAllJobDetails()

{%- endcodetabs %}

Result:

```
JobDetail[jobId=4955716b-3c40-4d39-b35b-04b705894810,userId=UNKNOWN,status=FINISHED,startTime=1506500166474,endTime=1506500166475,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements@20c70d20, AddElements[validate=true,skipInvalidElements=false,elements=uk.gov.gchq.gaffer.data.generator.OneToOneElementGenerator$1@11fca628]]]]
JobDetail[jobId=a46cb093-9b14-45c9-b300-ce2e91926a94,userId=user01,status=RUNNING,startTime=1506500166527,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails@7df28863]]]
```
-----------------------------------------------




GetElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html).

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


{% codetabs name="Java", type="java" -%}
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EdgeSeed( 
      source=2, 
      matched_vertex="SOURCE", 
      destination=3, 
      directed_type="EITHER" 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    entities=[ 
      g.ElementDefinition( 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            predicate=g.IsMoreThan( 
              value=1, 
              or_equal_to=False 
            ), 
            selection=[ 
              "count" 
            ] 
          ) 
        ], 
        group="entity" 
      ) 
    ], 
    edges=[ 
      g.ElementDefinition( 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            predicate=g.IsMoreThan( 
              value=1, 
              or_equal_to=False 
            ), 
            selection=[ 
              "count" 
            ] 
          ) 
        ], 
        group="edge" 
      ) 
    ] 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EdgeSeed( 
      source=2, 
      matched_vertex="SOURCE", 
      directed_type="EITHER", 
      destination=3 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "vertex" : 2,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final GetElements operation = new GetElements.Builder()
        .input(new EdgeSeed(1, 2, DirectedType.EITHER))
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EdgeSeed( 
      directed_type="EITHER", 
      matched_vertex="SOURCE", 
      destination=2, 
      source=1 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EdgeSeed( 
      source=1, 
      directed_type="EITHER", 
      matched_vertex="SOURCE", 
      destination=2 
    ) 
  ], 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              or_equal_to=False, 
              value=1 
            ) 
          ) 
        ], 
        group="edge" 
      ) 
    ], 
    entities=[ 
      g.ElementDefinition( 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              or_equal_to=False, 
              value=1 
            ) 
          ) 
        ], 
        group="entity" 
      ) 
    ] 
  ) 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EdgeSeed( 
      matched_vertex="SOURCE", 
      destination=3, 
      directed_type="EITHER", 
      source=2 
    ) 
  ], 
  view=g.View( 
    edges=[ 
    ], 
    entities=[ 
      g.ElementDefinition( 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            predicate=g.Or( 
              predicates=[ 
                g.IsLessThan( 
                  value=2, 
                  or_equal_to=False 
                ), 
                g.IsMoreThan( 
                  value=5, 
                  or_equal_to=False 
                ) 
              ] 
            ), 
            selection=[ 
              "count" 
            ] 
          ) 
        ], 
        group="entity" 
      ) 
    ] 
  ) 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    entities=[ 
    ], 
    edges=[ 
      g.ElementDefinition( 
        group="edge", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            predicate=g.Or( 
              predicates=[ 
                g.NestedPredicate( 
                  predicate=g.IsLessThan( 
                    or_equal_to=False, 
                    value=2 
                  ), 
                  selection=[ 
                    0 
                  ] 
                ), 
                g.NestedPredicate( 
                  predicate=g.IsMoreThan( 
                    or_equal_to=False, 
                    value=3 
                  ), 
                  selection=[ 
                    1 
                  ] 
                ) 
              ] 
            ), 
            selection=[ 
              "SOURCE", 
              "DESTINATION" 
            ] 
          ) 
        ] 
      ) 
    ] 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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
          "selection" : [ "SOURCE", "count" ],
          "projection" : [ "vertex|count" ]
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ], 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        properties=[ 
          "vertex|count" 
        ], 
        transform_functions=[ 
          g.FunctionContext( 
            selection=[ 
              "SOURCE", 
              "count" 
            ], 
            function=g.Function( 
              class_name="uk.gov.gchq.koryphe.impl.function.Concat", 
              fields={'separator': '|'} 
            ), 
            projection=[ 
              "vertex|count" 
            ] 
          ) 
        ], 
        group="edge", 
        transient_properties={'vertex|count': 'java.lang.String'} 
      ) 
    ], 
    entities=[ 
    ] 
  ) 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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
          "selection" : [ "SOURCE", "count" ],
          "projection" : [ "vertex|count" ]
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="edge", 
        transform_functions=[ 
          g.FunctionContext( 
            selection=[ 
              "SOURCE", 
              "count" 
            ], 
            projection=[ 
              "vertex|count" 
            ], 
            function=g.Function( 
              class_name="uk.gov.gchq.koryphe.impl.function.Concat", 
              fields={'separator': '|'} 
            ) 
          ) 
        ], 
        exclude_properties=[ 
          "count" 
        ], 
        transient_properties={'vertex|count': 'java.lang.String'} 
      ) 
    ], 
    entities=[ 
    ] 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/GetGafferResultCacheExport.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetGafferResultCacheExport())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache(), 
    g.DiscardOutput(), 
    g.GetGafferResultCacheExport( 
      key="ALL" 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final OperationChain<JobDetail> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache(), 
    g.DiscardOutput(), 
    g.GetJobDetails() 
  ] 
)

{%- endcodetabs %}

Result:

```
JobDetail[jobId=afed94c3-89a5-4f15-9e8b-93ff06df2379,userId=user01,status=RUNNING,startTime=1506500167842,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@b460b1e, uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache@5e785ae3, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@3723bdb, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@56949cf6]]]
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


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetGafferResultCacheExport.Builder()
                .jobId(jobDetail.getJobId())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "jobId" : "afed94c3-89a5-4f15-9e8b-93ff06df2379",
    "key" : "ALL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetGafferResultCacheExport( 
      key="ALL", 
      job_id="afed94c3-89a5-4f15-9e8b-93ff06df2379" 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache( 
      key="edges" 
    ), 
    g.DiscardOutput(), 
    g.GetAllElements(), 
    g.ExportToGafferResultCache( 
      key="entities" 
    ), 
    g.DiscardOutput(), 
    g.GetExports( 
      get_exports=[ 
        g.GetGafferResultCacheExport( 
          key="edges" 
        ), 
        g.GetGafferResultCacheExport( 
          key="entities" 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetJobDetails.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<JobDetail> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.DiscardOutput(), 
    g.GetJobDetails() 
  ] 
)

{%- endcodetabs %}

Result:

```
JobDetail[jobId=3d04cb83-85fc-41a9-9009-0202e932ef4e,userId=user01,status=RUNNING,startTime=1506500168986,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@680ff429, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@4f34bf31, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@536c2a51]]]
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


{% codetabs name="Java", type="java" -%}
final GetJobDetails operation = new GetJobDetails.Builder()
        .jobId(jobId)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails",
  "jobId" : "3d04cb83-85fc-41a9-9009-0202e932ef4e"
}

{%- language name="Python", type="py" -%}
g.GetJobDetails( 
  job_id="3d04cb83-85fc-41a9-9009-0202e932ef4e" 
)

{%- endcodetabs %}

Result:

```
JobDetail[jobId=3d04cb83-85fc-41a9-9009-0202e932ef4e,userId=user01,status=FINISHED,startTime=1506500168986,endTime=1506500168986,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetAllElements@680ff429, uk.gov.gchq.gaffer.operation.impl.DiscardOutput@4f34bf31, uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails@536c2a51]]]
```
-----------------------------------------------




GetJobResults example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetJobResults](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetJobResults.html).

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


{% codetabs name="Java", type="java" -%}
final GetJobResults operation = new GetJobResults.Builder()
        .jobId(jobId)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobResults",
  "jobId" : "f0008059-ca24-4c76-97c1-522a953374e7"
}

{%- language name="Python", type="py" -%}
g.GetJobResults( 
  job_id="f0008059-ca24-4c76-97c1-522a953374e7" 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/GetSetExport.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet(), 
    g.DiscardOutput(), 
    g.GetSetExport( 
      start=0 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport.Builder()
                .start(2)
                .end(4)
                .build())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet(), 
    g.DiscardOutput(), 
    g.GetSetExport( 
      end=4, 
      start=2 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet( 
      key="edges" 
    ), 
    g.DiscardOutput(), 
    g.GetAllElements(), 
    g.ExportToSet( 
      key="entities" 
    ), 
    g.DiscardOutput(), 
    g.GetExports( 
      get_exports=[ 
        g.GetSetExport( 
          key="edges", 
          start=0 
        ), 
        g.GetSetExport( 
          key="entities", 
          start=0 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.Limit](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/Limit.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Limit<>(3))
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.Limit( 
      truncate=True, 
      result_limit=3 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------

#### Limit elements to 3 without truncation

Setting this flag to false will throw an error instead of truncating the iterable. In this case there are more than 3 elements, so when executed a LimitExceededException would be thrown.


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Limit<>(3, false))
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.Limit( 
      truncate=False, 
      result_limit=3 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Limit.Builder<Element>()
                .resultLimit(3)
                .truncate(true)
                .build())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.Limit( 
      result_limit=3, 
      truncate=True 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
```
-----------------------------------------------




Max example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Max](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Max.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Max( 
      comparators=[ 
        g.ElementPropertyComparator( 
          property="count", 
          reversed=False, 
          groups=[ 
            "entity", 
            "edge" 
          ] 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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
            "selection" : [ "DESTINATION", "count" ],
            "projection" : [ "score" ]
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
            "selection" : [ "VERTEX", "count" ],
            "projection" : [ "score" ]
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      view=g.View( 
        entities=[ 
          g.ElementDefinition( 
            group="entity", 
            transient_properties={'score': 'java.lang.Integer'}, 
            transform_functions=[ 
              g.FunctionContext( 
                projection=[ 
                  "score" 
                ], 
                function=g.Function( 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction", 
                  fields={} 
                ), 
                selection=[ 
                  "VERTEX", 
                  "count" 
                ] 
              ) 
            ] 
          ) 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge", 
            transient_properties={'score': 'java.lang.Integer'}, 
            transform_functions=[ 
              g.FunctionContext( 
                projection=[ 
                  "score" 
                ], 
                function=g.Function( 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction", 
                  fields={} 
                ), 
                selection=[ 
                  "DESTINATION", 
                  "count" 
                ] 
              ) 
            ] 
          ) 
        ] 
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Max( 
      comparators=[ 
        g.ElementPropertyComparator( 
          property="count", 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False 
        ), 
        g.ElementPropertyComparator( 
          property="score", 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

```
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[score=<java.lang.Integer>6,count=<java.lang.Integer>3]]
```
-----------------------------------------------




Min example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Min](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Min.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Min( 
      comparators=[ 
        g.ElementPropertyComparator( 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False, 
          property="count" 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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
            "selection" : [ "DESTINATION", "count" ],
            "projection" : [ "score" ]
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
            "selection" : [ "VERTEX", "count" ],
            "projection" : [ "score" ]
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      view=g.View( 
        entities=[ 
          g.ElementDefinition( 
            transient_properties={'score': 'java.lang.Integer'}, 
            group="entity", 
            transform_functions=[ 
              g.FunctionContext( 
                function=g.Function( 
                  fields={}, 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction" 
                ), 
                selection=[ 
                  "VERTEX", 
                  "count" 
                ], 
                projection=[ 
                  "score" 
                ] 
              ) 
            ] 
          ) 
        ], 
        edges=[ 
          g.ElementDefinition( 
            transient_properties={'score': 'java.lang.Integer'}, 
            group="edge", 
            transform_functions=[ 
              g.FunctionContext( 
                function=g.Function( 
                  fields={}, 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction" 
                ), 
                selection=[ 
                  "DESTINATION", 
                  "count" 
                ], 
                projection=[ 
                  "score" 
                ] 
              ) 
            ] 
          ) 
        ] 
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Min( 
      comparators=[ 
        g.ElementPropertyComparator( 
          property="count", 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False 
        ), 
        g.ElementPropertyComparator( 
          property="score", 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

```
Entity[vertex=2,group=entity,properties=Properties[score=<java.lang.Integer>2,count=<java.lang.Integer>1]]
```
-----------------------------------------------




NamedOperation example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.named.operation.NamedOperation](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/named/operation/NamedOperation.html).

See [Named Operations](https://github.com/gchq/Gaffer/wiki/dev-guide#namedoperations) for information on configuring named operations for your Gaffer graph.

### Required fields
The following fields are required: 
- operationName


#### Add named operation


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.AddNamedOperation( 
  operation_name="2-hop", 
  write_access_roles=[ 
    "write-user" 
  ], 
  operation_chain=g.OperationChainDAO( 
    operations=[ 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ), 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ) 
    ] 
  ), 
  overwrite_flag=True, 
  description="2 hop query", 
  read_access_roles=[ 
    "read-user" 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

#### Add named operation with parameter


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.AddNamedOperation( 
  operation_name="2-hop-with-limit", 
  operation_chain=g.OperationChainDAO( 
    operations=[ 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ), 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ), 
      g.Limit( 
        result_limit="${param1}" 
      ) 
    ] 
  ), 
  read_access_roles=[ 
    "read-user" 
  ], 
  write_access_roles=[ 
    "write-user" 
  ], 
  description="2 hop query with settable limit", 
  overwrite_flag=True, 
  parameters=[ 
    g.NamedOperationParameter( 
      required=False, 
      value_class="java.lang.Long", 
      description="Limit param", 
      default_value=1, 
      name="param1" 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final GetAllNamedOperations operation = new GetAllNamedOperations();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.GetAllNamedOperations"
}

{%- language name="Python", type="py" -%}
g.GetAllNamedOperations()

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                .name("2-hop")
                .input(new EntitySeed(1))
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
  "operationName" : "2-hop",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1,
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}

{%- language name="Python", type="py" -%}
g.NamedOperation( 
  operation_name="2-hop", 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
Map<String, Object> paramMap = Maps.newHashMap();
paramMap.put("param1", 2L);

final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                .name("2-hop-with-limit")
                .input(new EntitySeed(1))
                .parameters(paramMap)
                .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.NamedOperation( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  parameters={'param1': 2}, 
  operation_name="2-hop-with-limit" 
)

{%- endcodetabs %}

Result:

```
EntitySeed[vertex=3]
EntitySeed[vertex=4]
```
-----------------------------------------------

#### Delete named operation


{% codetabs name="Java", type="java" -%}
final DeleteNamedOperation operation = new DeleteNamedOperation.Builder()
        .name("2-hop")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.DeleteNamedOperation",
  "operationName" : "2-hop"
}

{%- language name="Python", type="py" -%}
g.DeleteNamedOperation( 
  operation_name="2-hop" 
)

{%- endcodetabs %}

-----------------------------------------------




Sort example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Sort](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Sort.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Sort( 
      comparators=[ 
        g.ElementPropertyComparator( 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          property="count", 
          reversed=False 
        ) 
      ], 
      deduplicate=True, 
      result_limit=10 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Sort( 
      comparators=[ 
        g.ElementPropertyComparator( 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False, 
          property="count" 
        ) 
      ], 
      result_limit=10, 
      deduplicate=False 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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
            "selection" : [ "DESTINATION", "count" ],
            "projection" : [ "score" ]
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
            "selection" : [ "VERTEX", "count" ],
            "projection" : [ "score" ]
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            transform_functions=[ 
              g.FunctionContext( 
                selection=[ 
                  "DESTINATION", 
                  "count" 
                ], 
                projection=[ 
                  "score" 
                ], 
                function=g.Function( 
                  fields={}, 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction" 
                ) 
              ) 
            ], 
            transient_properties={'score': 'java.lang.Integer'}, 
            group="edge" 
          ) 
        ], 
        entities=[ 
          g.ElementDefinition( 
            transform_functions=[ 
              g.FunctionContext( 
                selection=[ 
                  "VERTEX", 
                  "count" 
                ], 
                projection=[ 
                  "score" 
                ], 
                function=g.Function( 
                  fields={}, 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction" 
                ) 
              ) 
            ], 
            transient_properties={'score': 'java.lang.Integer'}, 
            group="entity" 
          ) 
        ] 
      ) 
    ), 
    g.Sort( 
      deduplicate=True, 
      result_limit=4, 
      comparators=[ 
        g.ElementPropertyComparator( 
          property="count", 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False 
        ), 
        g.ElementPropertyComparator( 
          property="score", 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          reversed=False 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToArray](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToArray.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<? extends Element[]> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToArray<>())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToArray() 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToCsv](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToCsv.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToCsv( 
      include_header=True, 
      element_generator=g.ElementGenerator( 
        fields={'fields': {'GROUP': 'Edge group', 'count': 'total count', 'VERTEX': 'vertex', 'SOURCE': 'source'}, 'constants': {}, 'quoted': False}, 
        class_name="uk.gov.gchq.gaffer.data.generator.CsvGenerator" 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToEntitySeeds](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToEntitySeeds.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends EntitySeed>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToEntitySeeds())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToEntitySeeds() 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToList](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToList.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<List<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToList<>())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToList() 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToMap](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToMap.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToMapCsv( 
      element_generator=g.ElementGenerator( 
        fields={'constants': {}, 'fields': {'VERTEX': 'vertex', 'SOURCE': 'source', 'GROUP': 'group', 'count': 'total count'}}, 
        class_name="uk.gov.gchq.gaffer.data.generator.MapGenerator" 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToSet](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToSet.html).

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


{% codetabs name="Java", type="java" -%}
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(1), new EntitySeed(2))
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ), 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Set<? extends Element>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToSet<>())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToStream](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToStream.html).

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


{% codetabs name="Java", type="java" -%}
final OperationChain<Stream<? extends Element>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToStream<>())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToStream() 
  ] 
)

{%- endcodetabs %}

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
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToVertices](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToVertices.html).

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      view=g.View( 
        edges=[ 
        ], 
        entities=[ 
          g.ElementDefinition( 
            group="entity" 
          ) 
        ] 
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToVertices( 
      edge_vertices="NONE" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        entities=[ 
        ] 
      ), 
      include_incoming_out_going="OUTGOING" 
    ), 
    g.ToVertices( 
      edge_vertices="DESTINATION" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      include_incoming_out_going="OUTGOING", 
      view=g.View( 
        entities=[ 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ] 
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToVertices( 
      edge_vertices="BOTH" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        entities=[ 
        ] 
      ), 
      include_incoming_out_going="OUTGOING", 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToVertices( 
      use_matched_vertex="EQUAL" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

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


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      include_incoming_out_going="OUTGOING", 
      view=g.View( 
        entities=[ 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ] 
      ) 
    ), 
    g.ToVertices( 
      use_matched_vertex="OPPOSITE" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

Result:

```
2
4
3
5
```
-----------------------------------------------




