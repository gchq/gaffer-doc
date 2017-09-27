# Spark Operation Examples
_This page has been generated from code. To make any changes please update the examples in the [spark-doc](https://github.com/gchq/Gaffer/tree/master/library/spark/spark-doc/src/main/java/uk/gov/gchq/gaffer/spark/examples) module, run it and replace the content of this page with the output._


1. [GetDataFrameOfElements](#getdataframeofelements-example)
2. [GetJavaRDDOfAllElements](#getjavarddofallelements-example)
3. [GetJavaRDDOfElements](#getjavarddofelements-example)


GetDataFrameOfElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.spark.operation.dataframe.GetDataFrameOfElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/spark/operation/dataframe/GetDataFrameOfElements.html).

### Required fields
The following fields are required: 
- sparkSession


#### Get data frame of elements with entity group

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```



```java
GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("entity")
                        .build()).
                .sqlContext(sqlc)
                .build();
Dataset<Row> df = getGraph().execute(operation, new User("user01"));
df.show();
```

The results are:

```
+------+------+-----+
| group|vertex|count|
+------+------+-----+
|entity|     1|    3|
|entity|     2|    1|
|entity|     3|    2|
|entity|     4|    1|
|entity|     5|    3|
+------+------+-----
```


```java
df.filter("vertex = 1 OR vertex = 2").show();
```

The results are:

```
+------+------+-----+
| group|vertex|count|
+------+------+-----+
|entity|     1|    3|
|entity|     2|    1|
+------+------+-----
```


```java
df.filter("count > 1").show();
```

The results are:

```
+------+------+-----+
| group|vertex|count|
+------+------+-----+
|entity|     1|    3|
|entity|     3|    2|
|entity|     5|    3|
+------+------+-----
```
#### Get data frame of elements with edge group

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```



```java
GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("edge")
                        .build()).
                .sqlContext(sqlc)
                .build();
Dataset<Row> df = getGraph().execute(operation, new User("user01"));
df.show();
```

The results are:

```
+-----+---+---+-----+
|group|src|dst|count|
+-----+---+---+-----+
| edge|  1|  2|    3|
| edge|  1|  4|    1|
| edge|  2|  3|    2|
| edge|  2|  4|    1|
| edge|  2|  5|    1|
| edge|  3|  4|    4|
+-----+---+---+-----
```


```java
df.filter("src = 1 OR src = 3").show();
```

The results are:

```
+-----+---+---+-----+
|group|src|dst|count|
+-----+---+---+-----+
| edge|  1|  2|    3|
| edge|  1|  4|    1|
| edge|  3|  4|    4|
+-----+---+---+-----
```


```java
df.filter("count > 1").show();
```

The results are:

```
+-----+---+---+-----+
|group|src|dst|count|
+-----+---+---+-----+
| edge|  1|  2|    3|
| edge|  2|  3|    2|
| edge|  3|  4|    4|
+-----+---+---+-----
```



GetJavaRDDOfAllElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfAllElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/spark/operation/javardd/GetJavaRDDOfAllElements.html).

All the elements in a graph can be returned as a JavaRDD by using the operation GetJavaRDDOfAllElements. Some examples follow. Note that there is an option to read the Rfiles directly rather than the usual approach of obtaining them from Accumulo's tablet servers. This requires the Hadoop user running the Spark job to have read access to the RFiles in the Accumulo tablet. Note that data that has not been minor compacted will not be read if this option is used. This option is enabled using the option gaffer.accumulo.spark.directrdd.use_rfile_reader=true

### Required fields
The following fields are required: 
- sparkContext


#### get Java RDD of elements

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```



```java
GetJavaRDDOfAllElements<ElementId> operation = new GetJavaRDDOfAllElements.Builder<>()
                .javaSparkContext(sc)
                .build();
JavaRDD<Element> rdd = graph.execute(operation, new User("user01"));
List<Element> elements = rdd.collect();
```

The results are:

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
#### get Java RDD of elements returning edges only

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```



```java
GetJavaRDDOfAllElements<ElementId> operation = new GetJavaRDDOfAllElements.Builder<>()
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .javaSparkContext(sc)
                .build();
JavaRDD<Element> rdd = graph.execute(operation, new User("user01"));
List<Element> elements = rdd.collect();
```

The results are:

```
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
```



GetJavaRDDOfElements example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfElements](ref://../javadoc/gaffer/uk/gov/gchq/gaffer/spark/operation/javardd/GetJavaRDDOfElements.html).

### Required fields
The following fields are required: 
- sparkContext


#### get Java RDD of elements

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```



```java
GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .javaSparkContext(sc)
                .build();
JavaRDD<Element> rdd = graph.execute(operation, new User("user01"));
List<Element> elements = rdd.collect();
```

The results are:

```
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
```
#### get Java RDD of elements returning edges only

Using this simple directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```



```java
GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .javaSparkContext(sc)
                .build();
JavaRDD<Element> rdd = graph.execute(operation, new User("user01"));
List<Element> elements = rdd.collect();
```

The results are:

```
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
```



