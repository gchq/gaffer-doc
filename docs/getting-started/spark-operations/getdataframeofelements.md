# GetDataFrameOfElements
See javadoc - [uk.gov.gchq.gaffer.spark.operation.dataframe.GetDataFrameOfElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/spark/operation/dataframe/GetDataFrameOfElements.html)

Available since Gaffer version 1.0.0

Note - there is an option to read the RFiles directly rather than the usual approach of obtaining them from Accumulo's tablet servers. This requires the Hadoop user, running the Spark job, to have read access to the RFiles in the Accumulo tablet. Note, however, that data which has not been minor compacted will not be read if this option is used. This functionality is enabled using the option: "gaffer.accumulo.spark.directrdd.use_rfile_reader=true"

When executing a spark operation you can either let Gaffer create a SparkSession for you or you can add it yourself to the Context object and provide it when you execute the operation.
e.g:
```java
Context context = SparkContextUtil.createContext(new User("User01"), sparkSession);
graph.execute(operation, context);
```


## Required fields
No required fields


## Examples

### Get data frame of elements with entity group

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
        .view(new View.Builder()
                .entity("entity")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetDataFrameOfElements",
  "view" : {
    "entities" : {
      "entity" : { }
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.dataframe.GetDataFrameOfElements",
  "view" : {
    "entities" : {
      "entity" : { }
    }
  }
}
{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
+------+------+-----+
| group|vertex|count|
+------+------+-----+
|entity|     1|    3|
|entity|     2|    1|
|entity|     3|    2|
|entity|     4|    1|
|entity|     5|    3|
+------+------+-----

{%- language name="JSON", type="json" -%}
{
  "org$apache$spark$sql$Dataset$$encoder" : { },
  "local" : false,
  "streaming" : false
}
{%- endcodetabs %}



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
-----------------------------------------------

### Get data frame of elements with edge group

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
        .view(new View.Builder()
                .edge("edge")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetDataFrameOfElements",
  "view" : {
    "edges" : {
      "edge" : { }
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.dataframe.GetDataFrameOfElements",
  "view" : {
    "edges" : {
      "edge" : { }
    }
  }
}
{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
+-----+---+---+--------+-------------+-----+
|group|src|dst|directed|matchedVertex|count|
+-----+---+---+--------+-------------+-----+
| edge|  1|  2|    true|         null|    3|
| edge|  1|  4|    true|         null|    1|
| edge|  2|  3|    true|         null|    2|
| edge|  2|  4|    true|         null|    1|
| edge|  2|  5|    true|         null|    1|
| edge|  3|  4|    true|         null|    4|
+-----+---+---+--------+-------------+-----

{%- language name="JSON", type="json" -%}
{
  "org$apache$spark$sql$Dataset$$encoder" : { },
  "local" : false,
  "streaming" : false
}
{%- endcodetabs %}



```java
df.filter("src = 1 OR src = 3").show();
```

The results are:

```
+-----+---+---+--------+-------------+-----+
|group|src|dst|directed|matchedVertex|count|
+-----+---+---+--------+-------------+-----+
| edge|  1|  2|    true|         null|    3|
| edge|  1|  4|    true|         null|    1|
| edge|  3|  4|    true|         null|    4|
+-----+---+---+--------+-------------+-----
```


```java
df.filter("count > 1").show();
```

The results are:

```
+-----+---+---+--------+-------------+-----+
|group|src|dst|directed|matchedVertex|count|
+-----+---+---+--------+-------------+-----+
| edge|  1|  2|    true|         null|    3|
| edge|  2|  3|    true|         null|    2|
| edge|  3|  4|    true|         null|    4|
+-----+---+---+--------+-------------+-----
```
-----------------------------------------------

