# FAQs
Some frequently asked questions and useful tips. There are also some other Operation FAQs
[here](https://github.com/gchq/Gaffer/blob/master/core/operation/README.md#faqs).

## Query Summarisation
A common thing to do in Gaffer is to summarise elements (Edges and Entities) at query time. When we say summarise, the proper Gaffer term is Aggregation.

Elements in Gaffer are often stored in daily time buckets, this means you can query for a summary of the elements that occurred on a particular day, for example the count of edge A->B on Monday. The daily time buckets are controlled by using groupBy properties (see [User Guide Aggregation](../getting-started/user-guide/aggregation.html)).

However, at query time you may want to see the summary of the elements over multiple days, for example the count of edge A->B summarised from Monday to Friday. This can be done by overriding the groupBy properties and just setting the value to and empty array - telling Gaffer not to group by any properties and just summarise all A->B edges.

This is how to specify the empty groupBy array for a Edge with group name "Group1":

```java
GetElements op1 = new GetElements.Builder()
        .input(new EntitySeed("A"))
        .view(new View.Builder()
                .edge("Group1", new ViewElementDefinition.Builder()
                        .groupBy()
                        .build())
                .build())
        .build();
```

or in json
```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "edges" : {
      "Group1" : {
        "groupBy" : [ ]
      }
    }
  },
  "input" : [ {
    "vertex" : "A",
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```


More commonly you will want to set the groupBy array to empty for all groups in your schema. This can be done using the globalElements section in the view:

```java
GetElements op1 = new GetElements.Builder()
        .input(new EntitySeed("A"))
        .view(new View.Builder()
                .globalElements(new GlobalViewElementDefinition.Builder()
                        .groupBy()
                        .build())
                .build())
        .build();
```

or in json:

```json
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "view" : {
    "globalElements" : [ {
      "groupBy" : [ ]
    } ]
  },
  "input" : [ {
    "vertex" : "A",
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed"
  } ]
}
```


