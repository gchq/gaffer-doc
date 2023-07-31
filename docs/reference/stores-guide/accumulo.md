# Accumulo Store

## Introduction

Gaffer contains a store implemented using Apache Accumulo. This offers the following functionality:

- Scalability to large volumes of data
- Resilience to failures of hardware
- The ability to store any properties (subject to serialisers being provided if Gaffer is not aware of the objects)
- User-configured persistent aggregation of properties for the same vertices and edges
- Flexibly query-time filtering, aggregation and transformation
- Integration with Apache Spark to allow Gaffer data stored in Accumulo to be analysed as either an RDD or a Dataframe

## Use cases

Gaffer's `AccumuloStore` is particularly well-suited to graphs where the properties on vertices and edges are formed by aggregating interactions over time windows.

For example, suppose that we want to produce daily summaries of the interactions between vertices, e.g. on January 1st 2016, 25 interactions between A and B were observed, on January 2nd, 10 interactions between A and B were observed. Gaffer allows this data to be continually updated (e.g. if a new interaction between A and B is observed on January 2nd then an edge for January 2nd between A and B with a count of 1 can be inserted and this will automatically be merged with the existing edge and the count updated). This ability to update the properties without having to perform a query, then an update, then a put, is important for scaling to large volumes.

## Accumulo set up

As of Gaffer 2.0, Accumulo 2.0.1 is supported. Accumulo 1.x.x versions can also be used (by using artifacts with the "legacy" classifier). Gaffer has been tested with Accumulo version 1.9.0 & previously 1.8.1. It should also work with any of the 1.8.x & 1.9.x versions of Accumulo as well.

Gaffer can also be used with a `MiniAccumuloCluster`. This is an Accumulo cluster that runs in one JVM. To set up a `MiniAccumuloCluster` with Gaffer support, see the [mini-accumulo-cluster](https://github.com/gchq/gaffer-tools/tree/master/mini-accumulo-cluster) project in the Gaffer tools repository.

All real applications of Gaffer's `AccumuloStore` will use an Accumulo cluster running on a real Hadoop cluster consisting of multiple servers. Instructions on setting up an Accumulo cluster can be found in [Accumulo's User Manual](https://accumulo.apache.org/docs/2.x/getting-started/quickstart).

To use Gaffer's Accumulo store, it is necessary to add a jar file to the class path of all of Accumulo's tablet servers. This jar contains Gaffer code that runs inside Accumulo's tablet servers to provide functionality such as aggregation and filtering at ingest and query time. 

The Accumulo store iterators.jar required can be downloaded from [maven central](https://central.sonatype.com/search?namespace=uk.gov.gchq.gaffer&name=accumulo-store). It follows the naming scheme `accumulo-store-{version}-iterators.jar`, e.g. `accumulo-store-2.0.0-iterators.jar`.
This jar file will then need to be installed on Accumulo's tablet servers by adding it to the classpath. For Accumulo 1.x.x it can be placed in the `lib/ext` folder within the Accumulo distribution on each tablet server, Accumulo should load this jar file without needing to be restarted. For Accumulo 2.x.x [this dynamic reloading classpath directory functionality has been deprecated](https://accumulo.apache.org/release/accumulo-2.0.0/#removed-default-dynamic-reloading-classpath-directory-libext). The jar can instead be put into the `lib` directory and Accumulo restarted. The `lib` directory can also be used with Accumulo 1.x.x and may be useful if you see error messages due to classes not being found and restarting Accumulo doesn't fix the problem.

In addition, if you are using custom serialisers, properties or functions, then a jar of these should be created and installed into the same location as the Gaffer iterators.

At this stage you have installed Gaffer into your Accumulo cluster. It is now ready for loading data.

## Properties file

The next stage is to create a properties file that Gaffer will use to instantiate a connection to your Accumulo cluster. This requires the following properties:

- `gaffer.store.class`: The name of the Gaffer class that implements this store. For a full or pre-existing mini Accumulo cluster this should be `uk.gov.gchq.gaffer.accumulostore.AccumuloStore`. To use the `MiniAccumuloStore` in unit tests, it should be `uk.gov.gchq.gaffer.accumulostore.MiniAccumuloStore`.
- `gaffer.store.properties.class`: This is the name of the Gaffer class that contains the properties for this store. This should always be `uk.gov.gchq.gaffer.accumulostore.AccumuloProperties`.
- `accumulo.instance`: The instance name of your Accumulo cluster.
- `accumulo.zookeepers`: A comma separated list of the Zookeeper servers that your Accumulo cluster is using. Each server should specify the hostname and port separated by a colon, i.e. host:port.
- `accumulo.user`: The name of your Accumulo user.
- `accumulo.password`: The password for the above Accumulo user.

A typical properties file will look like:

```sh
gaffer.store.class=gaffer.accumulostore.AccumuloStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.accumulostore.AccumuloProperties
accumulo.instance=myInstance
accumulo.zookeepers=server1.com:2181,server2.com:2181,server3.com:2181
accumulo.user=myUser
accumulo.password=myPassword
```

When using Kerberos authentication, the username and password are not used, alternative properties are used to configure Kerberos. See the [Accumulo Kerberos guide for more information](../../gaffer2.0/accumulo-kerberos.md).

Note that if the graph does not exist, it will be created when a `Graph` object is instantiated using these properties, the schema and the graph ID (given when the graph is created in Java or via a `graphConfig.json`). In this case the user must have permission to create a table. If the graph already exists (based on the graph ID) then the user simply needs permission to read the table. For information about protecting data via setting the visibility, see [Visibilty](#visibility).

Other properties can be specified in this file. For details see [Advanced Properties](#advanced-properties). To improve query performance, see the property `accumulo.batchScannerThreads`. Increasing this from the default value of 10 can significantly increase the rate at which data is returned from queries.

## Inserting data

There are two ways of inserting data into a Gaffer `AccumuloStore`: continuous load, and bulk import. To ingest large volumes of data, it is recommended to first set appropriate split points on the table. The split points represent how Accumulo partitions the data into multiple tablets. This is best done for continuous load, and essential for bulk imports.

### Setting appropriate split points

The `SampleDataForSplitPoints` operation can be used to produce a file of split points that can then be used to set the split points on the table. It runs a MapReduce job that samples a percentage of the data, which is sent to a single reducer. That reducer simply writes the sorted data to file. This file is then read and sampled to produce split points that split the data into approximately equally sized partitions.

To run this operation, use:

```java
SampleDataForSplitPoints sample = new SampleDataForSplitPoints.Builder()
        .addInputPath(inputPath)
        .splitsFile(splitsFilePath)
        .outputPath(outputPath)
        .jobInitialiser(jobInitialiser)
        .validate(true)
        .proportionToSample(0.01F)
        .mapperGenerator(myMapperGeneratorClass)
        .build();
graph.execute(sample, new User());
```
where:

- `inputPath` is a string giving a directory in HDFS containing your data
- `splitsFilePath` is a string giving a file in HDFS where the output of the operation will be stored (this file should not exist before the operation is run)
- `outputPath` is a string giving a directory in HDFS where the output of the MapReduce job will be stored
- `jobInitialiser` is an instance of the `JobInitialiser` interface that is used to initialise the MapReduce job. If your data is in text files then you can use the built-in `TextJobInitialiser`. An `AvroJobInitialiser` is also provided
- The `true` option in the `validate` method indicates that every element will be validated
- The `0.01F` option in the `proportionToSample` method causes 1% of the data to be sampled. This is the amount of data that will be sent to the single reducer, so it should be small enough for a single reducer to handle
- `myMapperGeneratorClass` is a `Class` that extends the `MapperGenerator` interface. This is used to generate a `Mapper` class that is used to convert your data into `Element`s. Gaffer contains two built-in generators: `TextMapperGenerator` and `AvroMapperGenerator`. The former requires your data to be stored in text files in HDFS; the latter requires your data to be stored in Avro files

To apply these split points to the table, run:

```java
SplitStoreFromFile splitStore = new SplitStoreFromFile.Builder()
        .inputPath(splitsFilePath)
        .build();
graph.execute(splitStore, new User());
```

or from an Iterable:

```java
SplitStoreFromIterable splitStore = new SplitStoreFromIterable.Builder()
        .input(splits) // Base64 encoded strings
        .build();
graph.execute(splitStore, new User());
```

### Continuous load

This is done by using the `AddElements` operation and is as simple as the following where `elements` is a Java `Iterable` of Gaffer `Element`s that match the schema specified when the graph was created:

```java
AddElements addElements = new AddElements.Builder()
        .elements(elements)
        .build();
graph.execute(addElements, new User());
```

Note that here `elements` could be a never-ending stream of `Element`s and the above command will continuously ingest the data until it is cancelled or the stream stops.

### Bulk import

To ingest data via bulk import, a MapReduce job is used to convert your data into files of Accumulo key-value pairs that are pre-sorted to match the distribution of data in Accumulo. Once these files are created, Accumulo moves them from their current location in HDFS to the correct directory within Accumulo's data directory. The data in them is then available for query immediately.

Gaffer provides code to make this as simple as possible. The `AddElementsFromHdfs` operation is used to bulk import data. See [AddElementsFromHdfs](../operations-guide/hdfs.md#addelementsfromhdfs) for examples.

## Visibility

Gaffer can take advantage of Accumulo's built-in fine-grained security to ensure that users only see data that they have authorisation to. This is done by specifying a "visibilityProperty" in the schema. This is a string that tells Accumulo which key in the `Properties` on each `Element` should be used for the visibility. The value of this property will be placed in the column visibility in each Accumulo key. This means that a user must have authorisations corresponding to that visibility in order to see the data.

If no "visibilityProperty" is specified then the column visibility is empty which means that anyone who has read access to the table can view it.

See [the visibility walkthrough](https://gchq.github.io/gaffer-doc/v1docs/getting-started/developer-guide/visibilities.html) in the [Dev Guide](https://gchq.github.io/gaffer-doc/v1docs/getting-started/developer-guide/contents.html) for an example of how properties can be aggregated over different visibilities at query time.

## Timestamp

Accumulo keys have a timestamp field. The user can specify which property is used for this by setting "timestampProperty" in the schema's config to the name of the property.
If the timestamp is not set then it will be populated automatically:

- Constant value - aggregated groups
- Random number - non-aggregated groups

Setting the timestamp yourself is an advanced feature and is not recommended as it can cause significant issues if it is not populated correctly.

If you choose to set timestampProperty, the property will be aggregated with 'Max' - you cannot override this.

## Validation and age-off of data

In production systems where data is continually being ingested, it is necessary to periodically remove data so that the total size of the graph does not become too large. The `AccumuloStore` allows the creator of the graph to specify custom logic to decide what data should be removed. This logic is applied during compactions, so that the data is permanently deleted. It is also applied during queries so that even if a compaction has not happened recently, the data that should be removed is still hidden from the user.

A common approach is simply to delete data that is older than a certain date. In this case, within the properties on each element there will be a time window specified. For example, the properties may contain a "day" property, and the store may be configured so that once the day is more than one year ago, it will be deleted. This can be implemented as follows:

- Each element has a property called, for example, "day", which is a `Long` which contains the start of the time window. Every time an element is observed this property is set to the previous midnight expressed in milliseconds since the epoch.
- In the schema the validation of this property is expressed as follows:
```sh
"long": {
    "class": "java.lang.Long",
    "validateFunctions": [
        {
            "function": {
                "class": "gaffer.function.simple.filter.AgeOff",
                "ageOffDays": "100"
              }
        }
    ]
}
```
- Then data will be aged-off whenever it is more than 100 days old.

## Key-packages

In Gaffer's `AccumuloStore` a key-package contains all the logic for:

- Converting `Element`s into Accumulo key-value pairs, and vice-versa
- Generating ranges of Accumulo keys that correspond to the seeds of a query
- Creating the iterator settings necessary to perform the persistent aggregation that happens at compaction time, and the filtering and aggregation that happens during queries
- Creating the `KeyFunctor` used to configure the Bloom filters in Accumulo

A key-package is an implementation of the `AccumuloKeyPackage` interface. Gaffer provides two implementations: `ByteEntityKeyPackage` and `ClassicKeyPackage`. These names are essentially meaningless. The "classic" in `ClassicKeyPackage` refers to the fact that it is similar to the implementation in the first version of Gaffer (known as "Gaffer1").

Both key-packages should provide good performance for most use-cases. There will be slight differences in performance between the two for different types of query. The `ByteEntityKeyPackage` will be slightly faster if the query specifies that only out-going or in-coming edges are required. The `ClassicKeyPackage` will be faster when querying for all edges involving a pair of vertices. See the Key-Packages part of the [Accumulo Store Implementation page](../../dev/accumulo-dev.md) for more information about these key-packages.

## Advanced properties

The following properties can also be specified in the properties file. If they are not specified, then sensible defaults are used.

- `gaffer.store.accumulo.keypackage.class`: The full name of the class to be used as the key-package. By default `ByteEntityKeyPackage` will be used.
- `accumulo.batchScannerThreads`: The number of threads to use when `BatchScanner`s are created to query Accumulo. The default value is 10.
- `accumulo.entriesForBatchScanner`: The maximum number of ranges that should be given to an Accumulo `BatchScanner` at any one time. The default value is  50000.
- `accumulo.clientSideBloomFilterSize`: The size in bits of the Bloom filter used in the client during operations such as `GetElementsBetweenSets`. The default value is 838860800, i.e. 100MB.
- `accumulo.falsePositiveRate`: The desired rate of false positives for Bloom filters that are passed to an iterator in operations such as `GetElementsBetweenSets`. The default value is 0.0002.
- `accumulo.maxBloomFilterToPassToAnIterator`: The maximum size in bits of Bloom filters that will be created in an iterator on Accumulo's tablet server during operations such as `GetElementsBetweenSets`. By default this will be 8388608, i.e. 1MB.
- `accumulo.maxBufferSizeForBatchWriterInBytes`: The size of the buffer in bytes used in Accumulo `BatchWriter`s when data is being ingested. The default value is 1000000.
- `accumulo.maxTimeOutForBatchWriterInMilliseconds`: The maximum latency used in Accumulo `BatchWriter`s when data is being ingested. Th default value is 1000, i.e. 1 second.
- `accumulo.numThreadsForBatchWriter`: The number of threads used in Accumulo `BatchWriter`s when data is being ingested. The default value is 10.
- `accumulo.file.replication`: The number of replicas of each file in tables created by Gaffer. If this is not set then your general Accumulo setting will apply, which is normally the same as the default on your HDFS instance.
- `gaffer.store.accumulo.enable.validator.iterator`: This specifies whether the validation iterator is applied. The default value is true.
- `accumulo.namespace`: The namespace to use for the table in Accumulo. The default is to use the default Accumulo namespace, which is the empty string.

## Migration

The Accumulo Store also provides a utility [AddUpdateTableIterator](https://github.com/gchq/Gaffer/blob/master/store-implementation/accumulo-store/src/main/java/uk/gov/gchq/gaffer/accumulostore/utils/AddUpdateTableIterator.java)
to help with migrations - updating to new versions of Gaffer or updating your schema.

The following changes to your schema are allowed:
- add new groups
- add new non-groupBy properties (including visibility and timestamp), but they must go after the other properties
- rename properties
- change aggregators (your data may become inconsistent as any elements that were aggregated on ingest will not be updated.)
- change validators
- change descriptions

But, you cannot do the following:
- rename groups
- remove any properties (groupBy, non-groupBy, visibility or timestamp)
- add new groupBy properties
- reorder any of the properties. In the Accumulo store we don't use any property names, we just rely on the order the properties are defined in the schema.
- change the way properties or vertices are serialised - i.e don't change the serialisers.
- change which properties are groupBy

Please note, that the validation functions in the schema can be a bit dangerous. If an element is found to be invalid then the element will be permanently deleted from the table. So, be very careful when making changes to your schema that you don't accidentally make all your existing elements invalid as you will quickly realise all your data has been deleted. For example, if you add a new property 'prop1' and set the validateFunctions to be a single Exists predicate. Then when that Exists predicate is applied to all of your existing elements, those old elements will fail validation and be removed.

To carry out the migration you will need the following:

- your schema in 1 or more json files.
- `store.properties` file contain your accumulo store properties
- a jar-with-dependencies containing the Accumulo Store classes and any of your custom classes. 
If you don't have any custom classes then you can just use the `accumulo-store-[version]-utility.jar`. 
Otherwise you can create one by adding a build profile to your maven pom:
```xml
<build>
    <plugins>
        <plugin>
            <artifactId>maven-shade-plugin</artifactId>
            <version>${shade.plugin.version}</version>
            <executions>
                <execution>
                    <id>utility</id>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <shadedArtifactAttached>true
                        </shadedArtifactAttached>
                        <shadedClassifierName>utility
                        </shadedClassifierName>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```


If you have existing data, then before doing any form of upgrade or change to your table we strongly recommend using the accumulo shell to clone the table so you have a backup so you can easily restore to that version if things go wrong. Even if you have an error like the one above where all your data is deleted in your table you will still be able to quickly revert back to your backup. Cloning a table in Accumulo is very simple and fast (it doesn't actually copy the data). If you have a table called 'table1', you can do something like the following in the Accumulo shell:

```bash
> offline -t table1
> clone table table1 table1-backup
> offline -t table1-backup

# Do your upgrades
#   - deploy new gaffer jars to Accumulo's class path on each node in your cluster
#   - run the AddUpdateTableIterator class to update table1

> online -t table1

# Check table1 is still healthy:
#   - run a query and check the iterators are successfully aggregating and filtering elements correctly.

> droptable -t table1-backup
```

You will need to run the AddUpdateTableIterator utility providing it with your graphId, schema and store properties.
If you run it without any arguments it will tell you how to use it.

```bash
java -cp [path to your jar-with-dependencies].jar uk.gov.gchq.gaffer.accumulostore.utils.AddUpdateTableIterator
```

## Troubleshooting

**Data hasn't appeared after I performed a bulk import**

Accumulo's UI often shows that there are zero entries in a table after a bulk import. This is generally because Accumulo does not know how many entries have been added until it has performed a major compaction. Open the Accumulo shell, change to the table you specified in your Accumulo properties file, and type `compact`. You should see compactions starting for that table in Accumulo's UI, and the number of entries should then start to increase.

If this has not solved the problem, look at the logs of your bulk import MapReduce job and check that the number of entries output by both the Mapper and the Reducer was positive.

Next check that the elements you generate pass your validation checks.

If all the above fails, try inserting a small amount of data using `AddElements` to see whether the problem is your bulk import job or your data generation.

**Queries result in no data**

Check that you have the correct authorisations to see the data you inserted. Check with the administrator of your Accumulo cluster.

**Spark operations are slow**

Try using a batch scanner to read the data from the tablet server. To enable this for the `GetRDDOfAllElements` or `GetJavaRDDOfAllElements` operation, set the `gaffer.accumulo.spark.rdd.use_batch_scanner` option to true. `GetRDDOfElements` and `GetJavaRDDOfElements` use a batch scanner by default.

If you still don't see a significant improvement, try increasing the value of the `table.scan.max.memory` setting in Accumulo for your table.

**Running accumulo-store Integration Tests and getting error: Error BAD_AUTHORIZATIONS for user root on table integrationTestGraph(ID:1l)**

This means you have correctly set your user (in this case user 'root') in store.properties as ```accumulo.user=root``` however you have not set the correct scan authorisations for the user 'root' required by the integration tests.

If you have the accumulo cluster shell running, you can set these scan auths directly from the shell by entering the following command:

```
root@instance> setauths -u root -s vis1,vis2,publicVisibility,privateVisibility,public,private
```