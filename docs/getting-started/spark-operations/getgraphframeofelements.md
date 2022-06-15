# GetGraphFrameOfElements
See javadoc - [uk.gov.gchq.gaffer.spark.operation.graphframe.GetGraphFrameOfElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/spark/operation/graphframe/GetGraphFrameOfElements.html)

Available since Gaffer version 1.3.0

When executing a spark operation you can either let Gaffer create a SparkSession for you or you can add it yourself to the Context object and provide it when you execute the operation.
e.g:
```java
Context context = SparkContextUtil.createContext(new User("User01"), sparkSession);
graph.execute(operation, context);
```


## Required fields
The following fields are required: 
- view


## Examples

### Get graph frame of elements

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
final GetGraphFrameOfElements operation = new GetGraphFrameOfElements.Builder()
        .view(new View.Builder()
                .entity("entity")
                .edge("edge")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetGraphFrameOfElements",
  "view" : {
    "edges" : {
      "edge" : { }
    },
    "entities" : {
      "entity" : { }
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.graphframe.GetGraphFrameOfElements",
  "view" : {
    "edges" : {
      "edge" : { }
    },
    "entities" : {
      "entity" : { }
    }
  }
}
{%- endcodetabs %}

Then with the graphFrame result you can execute things like:


```java
graphFrame.vertices().filter("vertex = 1 OR vertex = 2").showString(100, 20)
```

and results are: 


```java
+-------------+--------+---+----+----+-----+------+
|matchedVertex|directed| id| dst| src|count| group|
+-------------+--------+---+----+----+-----+------+
|         null|    null|  1|null|null| null|  null|
|         null|    null|  2|null|null| null|  null|
|         null|    null|  1|null|null|    3|entity|
|         null|    null|  2|null|null|    1|entity|
+-------------+--------+---+----+----+-----+------+

```

Or you can inspect the edges like:


```java
graphFrame.edges().filter("count > 1").showString(100, 20)
```

and results are: 


```java
+-----+------+-----+---+---+--------+-------------+---+
|group|vertex|count|src|dst|directed|matchedVertex| id|
+-----+------+-----+---+---+--------+-------------+---+
| edge|  null|    3|  1|  2|    true|         null|  1|
| edge|  null|    2|  2|  3|    true|         null|  3|
| edge|  null|    4|  3|  4|    true|         null|  6|
+-----+------+-----+---+---+--------+-------------+---+

```

There is a whole suite of nice things you can do with GraphFrames, including operations like PageRank.

-----------------------------------------------

