# Flink Library

The [flink library](https://github.com/gchq/Gaffer/tree/master/library/flink-library) module contains various libraries for using Apache Flink with Gaffer.

In order to make use of the flink libraries you will need to include this library as a dependency:
```xml
 <dependency>
  <groupId>uk.gov.gchq.gaffer</groupId>
  <artifactId>flink-library</artifactId>
  <version>${gaffer.version}</version>
</dependency>
```

For information on registering and using flink operations, see the [Flink Operations guide](../../../../reference/operations-guide/flink.md).

## I am getting errors when running the Flink operations on a cluster
This could be to do with the way the Gaffer Store class is serialised and distributed around the cluster. To distribute the job, Flink requires all of the components of the job to be Serializable.
The Gaffer Store class is not Serializable so instead we just Serialialize the graphId, Schema and Properties. Then when we require an instance of the Store class again we recreate it again with these parts.
This means that any files that are referenced in your StoreProperties must be available on all your nodes in your cluster.
