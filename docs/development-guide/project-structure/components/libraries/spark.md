# Spark Library

The [spark library](https://github.com/gchq/Gaffer/tree/master/library/spark/spark-library) contains various libraries for using Apache Spark with Gaffer.

In order to make use of the spark libraries you will need to include this library as a dependency:
```xml
 <dependency>
  <groupId>uk.gov.gchq.gaffer</groupId>
  <artifactId>spark-library</artifactId>
  <version>${gaffer.version}</version>
</dependency>
```

To use spark with Accumulo you will need to include this dependency:
```xml
 <dependency>
  <groupId>uk.gov.gchq.gaffer</groupId>
  <artifactId>spark-accumulo-library</artifactId>
  <version>${gaffer.version}</version>
</dependency>
```

For information on registering and using spark operations, see the [Spark Operations guide](../../../../reference/operations-guide/spark.md).
