# GetJavaRDDOfElements
See javadoc - [uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/spark/operation/javardd/GetJavaRDDOfElements.html)

Available since Gaffer version 1.0.0

When executing a spark operation you can either let Gaffer create a SparkSession for you or you can add it yourself to the Context object and provide it when you execute the operation.
e.g:
```java
Context context = SparkContextUtil.createContext(new User("User01"), sparkSession);
graph.execute(operation, context);
```


## Required fields
No required fields


## Examples

### Get java RDD of elements

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
final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
        .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 1,
    "destination" : 2,
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  } ]
}
{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]

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

### Get java RDD of elements with hadoop conf


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 1,
    "destination" : 2,
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  } ],
  "options" : {
    "Hadoop_Configuration_Key" : "config removed for readability"
  }
}
{%- endcodetabs %}

-----------------------------------------------

### Get java RDD of elements returning edges only

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
final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
        .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
        .view(new View.Builder()
                .edge("edge")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 1,
    "destination" : 2,
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
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
{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]

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

