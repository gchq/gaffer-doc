# AddElementsFromHdfs
See javadoc - [uk.gov.gchq.gaffer.hdfs.operation.AddElementsFromHdfs](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/hdfs/operation/AddElementsFromHdfs.html)

Available since Gaffer version 1.0.0

This operation must be run as a Hadoop Job. So you will need to package up a shaded jar containing a main method that creates an instance of Graph and executes the operation. It can then be run with: 

```bash
hadoop jar custom-shaded-jar.jar
```

When running an AddElementsFromHdfs on Accumulo, if you do not specify useProvidedSplits and the Accumulo table does not have a full set of split points then this operation will first sample the input data, generate split points and set them on the Accumulo table. It does this by delegating to SampleDataForSplitPoints and class uk.gov.gchq.gaffer.operation.impl.SplitStoreFromFile.

Specifying the number of reducers within the Job has now been deprecated, and instead it is preferred to set the minimum and/or maximum number of reducers. Most users should not need to set the min or max number of reducers and simply leave the Store to pick the optimal number. The Accumulo Store does this by using the number of tablet servers. If you choose to set a min or max number of reducers then the Store will try to use a number within that range. If there is no optimal number within the provided range an exception is thrown.

## Required fields
The following fields are required: 
- failurePath
- inputMapperPairs
- outputPath
- jobInitialiser


## Examples

### Add elements from hdfs



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

### Add elements from hdfs main method

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

### Add elements from hdfs with multiple input



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

