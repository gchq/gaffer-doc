# GetJavaRDDOfAllElements
See javadoc - [uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfAllElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/spark/operation/javardd/GetJavaRDDOfAllElements.html)

Available since Gaffer version 1.0.0

All the elements in a graph can be returned as a JavaRDD by using the operation GetJavaRDDOfAllElements. Some examples follow. 
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

### Get java RDD of all elements

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
final GetJavaRDDOfAllElements operation = new GetJavaRDDOfAllElements();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetJavaRDDOfAllElements"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfAllElements"
}
{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
{
  "storageLevel" : {
    "valid" : false
  },
  "numPartitions" : 1,
  "checkpointed" : false,
  "checkpointFile" : {
    "present" : false
  },
  "empty" : false
}
{%- endcodetabs %}

-----------------------------------------------

### Get java RDD of all elements returning edges only

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
final GetJavaRDDOfAllElements operation = new GetJavaRDDOfAllElements.Builder()
        .view(new View.Builder()
                .edge("edge")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetJavaRDDOfAllElements",
  "view" : {
    "edges" : {
      "edge" : { }
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfAllElements",
  "view" : {
    "edges" : {
      "edge" : { }
    }
  }
}
{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]

{%- language name="JSON", type="json" -%}
{
  "storageLevel" : {
    "valid" : false
  },
  "numPartitions" : 1,
  "checkpointed" : false,
  "checkpointFile" : {
    "present" : false
  },
  "empty" : false
}
{%- endcodetabs %}

-----------------------------------------------

