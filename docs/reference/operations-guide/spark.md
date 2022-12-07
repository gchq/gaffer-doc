# Spark Operations

These are special Spark Operations which need to be added/enabled before they can be used. _TBD link to Spark store page_.

There is an option to read the RFiles directly rather than the usual approach of obtaining them from Accumulo's tablet servers. This requires the Hadoop user, running the Spark job, to have read access to the RFiles in the Accumulo tablet. Note, however, that data which has not been minor compacted will not be read if this option is used. This functionality is enabled using the option: `gaffer.accumulo.spark.directrdd.use_rfile_reader=true`.

This directed graph is used in all the examples on this page:

``` mermaid
graph TD
  1(1, count=3) -- count=3 --> 2
  1 -- count=1 --> 4
  2(2, count=1) -- count=2 --> 3
  2 -- count=1 --> 4(4, count=1)
  2 -- count=1 --> 5(5, count=3)
  3(3, count=2) -- count=4 --> 4
```

## GetDataFrameOfElements

Operation that returns an Apache Spark DataFrame consisting of Elements converted to Rows, fields are ordered according to the ordering of the groups in the view, with Entities first, followed by Edges. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/spark/operation/dataframe/GetDataFrameOfElements.html)

??? example "Example getting data frame of elements with entity group"

    === "Java"

        ``` java
        final GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("entity")
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetDataFrameOfElements",
          "view" : {
            "entities" : {
              "entity" : { }
            }
          }
        }
        ```

    Results:

    | group|vertex|count|
    |------|------|-----|
    |entity|     1|    3|
    |entity|     2|    1|
    |entity|     3|    2|
    |entity|     4|    1|
    |entity|     5|    3|


    Using filter:

    ```java
    df.filter("vertex = 1 OR vertex = 2").show();
    ```

    The results are:

    | group|vertex|count|
    |------|------|-----|
    |entity|     1|    3|
    |entity|     2|    1|


    Using filter:

    ```java
    df.filter("count > 1").show();
    ```

    The results are:

    | group|vertex|count|
    |------|------|-----|
    |entity|     1|    3|
    |entity|     3|    2|
    |entity|     5|    3|

??? example "Example getting data frame of elements with edge group"

    === "Java"

        ``` java
        final GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetDataFrameOfElements",
          "view" : {
            "edges" : {
              "edge" : { }
            }
          }
        }
        ```

    Results:

    |group|src|dst|directed|matchedVertex|count|
    |-----|---|---|--------|-------------|-----|
    | edge|  1|  2|    true|         null|    3|
    | edge|  1|  4|    true|         null|    1|
    | edge|  2|  3|    true|         null|    2|
    | edge|  2|  4|    true|         null|    1|
    | edge|  2|  5|    true|         null|    1|
    | edge|  3|  4|    true|         null|    4|

    Using filter:

    ```java
    df.filter("src = 1 OR src = 3").show();
    ```

    The results are:

    |group|src|dst|directed|matchedVertex|count|
    |-----|---|---|--------|-------------|-----|
    | edge|  1|  2|    true|         null|    3|
    | edge|  1|  4|    true|         null|    1|
    | edge|  3|  4|    true|         null|    4|

    Using filter:

    ```java
    df.filter("count > 1").show();
    ```

    The results are:

    |group|src|dst|directed|matchedVertex|count|
    |-----|---|---|--------|-------------|-----|
    | edge|  1|  2|    true|         null|    3|
    | edge|  2|  3|    true|         null|    2|
    | edge|  3|  4|    true|         null|    4|

## GetGraphFrameOfElements

Operation which returns an Apache Spark GraphFrame consisting of Elements converted to rows. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/spark/operation/graphframe/GetGraphFrameOfElements.html)

??? example "Example getting graph frame of elements"

    === "Java"

        ``` java
        final GetGraphFrameOfElements operation = new GetGraphFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("entity")
                        .edge("edge")
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
        ```

    Then with the graphFrame result you can execute for example:

    ```java
    graphFrame.vertices().filter("vertex = 1 OR vertex = 2").showString(100, 20)
    ```

    and the results are: 
    
    |matchedVertex|directed| id| dst| src|count| group|
    |-------------|--------|---|----|----|-----|------|
    |         null|    null|  1|null|null| null|  null|
    |         null|    null|  2|null|null| null|  null|
    |         null|    null|  1|null|null|    3|entity|
    |         null|    null|  2|null|null|    1|entity|
    
    Or you can inspect the edges:

    ```java
    graphFrame.edges().filter("count > 1").showString(100, 20)
    ```

    and the results are: 
    
    |group|vertex|count|src|dst|directed|matchedVertex| id|
    |-----|------|-----|---|---|--------|-------------|---|
    | edge|  null|    3|  1|  2|    true|         null|  1|
    | edge|  null|    2|  2|  3|    true|         null|  3|
    | edge|  null|    4|  3|  4|    true|         null|  6|
    
    GraphFrames also include operations such as PageRank.

## GetJavaRDDOfAllElements

Operation which retrieves all Elements, and returns them inside a JavaRDD. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/spark/operation/javardd/GetJavaRDDOfAllElements.html)

??? example "Example getting JavaRDD of all elements"

    === "Java"

        ``` java
        final GetJavaRDDOfAllElements operation = new GetJavaRDDOfAllElements();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetJavaRDDOfAllElements"
        }
        ```

    Results:

    === "Java"

        ``` java
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

??? example "Example getting JavaRDD of all elements returning edges only"

    === "Java"

        ``` java
        final GetJavaRDDOfAllElements operation = new GetJavaRDDOfAllElements.Builder()
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetJavaRDDOfAllElements",
          "view" : {
            "edges" : {
              "edge" : { }
            }
          }
        }
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        ```

## GetJavaRDDOfElements

Operation which retrieves all the Elements for input seeds, and returns them inside a JavaRDD. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/spark/operation/javardd/GetJavaRDDOfElements.html)

??? example "Example getting JavaRDD of elements with seeds"

    === "Java"

        ``` java
        final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetJavaRDDOfElements",
          "input" : [ {
            "class" : "EdgeSeed",
            "source" : 1,
            "destination" : 2,
            "matchedVertex" : "SOURCE",
            "directedType" : "DIRECTED"
          }, {
            "class" : "EdgeSeed",
            "source" : 2,
            "destination" : 3,
            "matchedVertex" : "SOURCE",
            "directedType" : "DIRECTED"
          } ]
        }
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        ```

??? example "Example getting JavaRDD of elements with Hadoop conf"

    === "Java"

        ``` java
        final Configuration conf = new Configuration();
        conf.set("AN_OPTION", "A_VALUE");

        final String encodedConf;
        try {
            encodedConf = AbstractGetRDDHandler.convertConfigurationToString(conf);
        } catch (final IOException e) {
            throw new RuntimeException("Unable to convert conf to string", e);
        }

        final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .option(AbstractGetRDDHandler.HADOOP_CONFIGURATION_KEY, encodedConf)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetJavaRDDOfElements",
          "input" : [ {
            "class" : "EdgeSeed",
            "source" : 1,
            "destination" : 2,
            "matchedVertex" : "SOURCE",
            "directedType" : "DIRECTED"
          }, {
            "class" : "EdgeSeed",
            "source" : 2,
            "destination" : 3,
            "matchedVertex" : "SOURCE",
            "directedType" : "DIRECTED"
          } ],
          "options" : {
            "Hadoop_Configuration_Key" : "config removed for readability"
          }
        }
        ```

??? example "Example getting JavaRDD of elements with seeds, returning edges only"

    === "Java"

        ``` java
        final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetJavaRDDOfElements",
          "input" : [ {
            "class" : "EdgeSeed",
            "source" : 1,
            "destination" : 2,
            "matchedVertex" : "SOURCE",
            "directedType" : "DIRECTED"
          }, {
            "class" : "EdgeSeed",
            "source" : 2,
            "destination" : 3,
            "matchedVertex" : "SOURCE",
            "directedType" : "DIRECTED"
          } ],
          "view" : {
            "edges" : {
              "edge" : { }
            }
          }
        }
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        ```