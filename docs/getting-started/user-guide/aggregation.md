# Aggregation

The code for this example is [Aggregation](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Aggregation.java).

Aggregation is a key powerful feature in Gaffer and we will guide you through it in this example.

Aggregation is applied at ingest and query time.

1. Ingest aggregation permanently aggregates similar elements together. Elements will be aggregated if:
    1. An entity has the same group, vertex, visibility and any specified groupBy property values are identical.
    2. An edge has the same group, source, destination, directed and any specified groupBy property values are identical.
2. Query aggregation allows additional aggregation depending on a user's visibility permissions and any overridden groupBy properties provided at query time.

For this example we have added a timestamp to our csv data:


```
M5,10,11,2000-05-01 07:00:00
M5,10,11,2000-05-01 08:00:00
M5,10,11,2000-05-02 09:00:00
M5,11,10,2000-05-03 09:00:00
M5,23,24,2000-05-04 07:00:00
M5,23,24,2000-05-04 07:00:00
M5,28,27,2000-05-04 07:00:00

```


### Ingest Aggregation

#### The Elements schema

The schema is similar to what we have seen before, but we are adding 'startDate' and 'endDate' properties to the 'RoadUse' edges. We will use the timestamp from the input data to generate the start and end dates - the midnight before the timestamp and a millisecond before the following midnight, i.e. the start and end of the day. This effectively creates a time bucket with day granularity.
These properties have been added to the groupBy field, which will be used to determine whether elements should be aggregated together at ingest. Adding these properties to the groupBy field means that Gaffer will aggregate data for the same day at ingest.


```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    }
  }
}

```


#### The Types schema

We have introduced 2 new types ('date.earliest' and 'date.latest') that we need to define in the types schema:


```json
{
  "types": {
    "junction": {
      "description": "A road junction represented by a String.",
      "class": "java.lang.String"
    },
    "road": {
      "description": "A road represented by a String.",
      "class": "java.lang.String"
    },
    "count.long": {
      "description": "A long count that must be greater than or equal to 0.",
      "class": "java.lang.Long",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo": true,
          "value": {
            "java.lang.Long": 0
          }
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
      }
    },
    "true": {
      "description": "A simple boolean that must always be true.",
      "class": "java.lang.Boolean",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
        }
      ]
    },
    "date.earliest": {
      "description": "A Date that when aggregated together will be the earliest date.",
      "class": "java.util.Date",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
      }
    },
    "date.latest": {
      "description": "A Date that when aggregated together will be the latest date.",
      "class": "java.util.Date",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
      }
    }
  }
}
```


Both of the types added have a validator that ensures the properties exist (are not null). The 'date.earliest' type is aggregated by the 'Min' operator, and 'date.latest' is aggregated by the 'Max' operator.

We need to update our generator to create the date properties from the timestamp in the csv data. This is where we create the time bucket:


```java
public class RoadAndRoadUseWithTimesElementGenerator implements OneToManyElementGenerator<String> {
    @Override
    public Iterable<Element> _apply(final String line) {
        final String[] t = line.split(",");

        final String road = t[0];
        final String junctionA = t[1];
        final String junctionB = t[2];
        final Date timestamp;
        try {
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t[3]);
        } catch (final ParseException e) {
            throw new IllegalArgumentException("Invalid date: " + t[3]);
        }

        final Date startDate = DateUtils.truncate(timestamp, Calendar.DAY_OF_MONTH);
        final Date endDate = DateUtils.addMilliseconds(DateUtils.addDays(startDate, 1), -1);

        return Arrays.asList(
                new Edge.Builder()
                        .group("RoadHasJunction")
                        .source(road)
                        .dest(junctionA)
                        .directed(true)
                        .build(),

                new Edge.Builder()
                        .group("RoadHasJunction")
                        .source(road)
                        .dest(junctionB)
                        .directed(true)
                        .build(),

                new Edge.Builder()
                        .group("RoadUse")
                        .source(junctionA)
                        .dest(junctionB)
                        .directed(true)
                        .property("count", 1L)
                        .property("startDate", startDate)
                        .property("endDate", endDate)
                        .build()
        );
    }
}
```

Once we have loaded the data into Gaffer, we can fetch all the edges using a GetAllElements operation.
Note this operation is not recommended for large Graphs as it will do a full scan of your database and could take a while to finish.


{% codetabs name="Java", type="java" -%}
final GetAllElements getAllRoadUseEdges = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .build();

final CloseableIterable<? extends Element> roadUseElements = graph.execute(getAllRoadUseEdges, user);
{%- endcodetabs %}


All edges:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 PDT 2000]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 PDT 2000]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 PDT 2000]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Thu May 04 00:00:00 PDT 2000]]

```

You can see in the results that the timestamp has been converted into start and end dates and that all edges with a timestamp on the same day have been aggregated together (ingest aggregation).
These aggregated edges have had all of their properties aggregated together using the aggregate functions specified in the schema.

#### Query Time Aggregation

Next we will demonstrate query time aggregation and get all the 'RoadUse' edges with the same source to the same destination to be aggregated together, regardless of the start and end dates.
This is achieved by overriding the groupBy field with an empty array so no properties are grouped on. Here is the operation:


{% codetabs name="Java", type="java" -%}
final GetAllElements edgesSummarisedOperation = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .groupBy() // set the group by properties to 'none'
                        .build())
                .build())
        .build();

final CloseableIterable<? extends Element> edgesSummarised = graph.execute(edgesSummarisedOperation, user);
{%- endcodetabs %}


The aggregated edges are as follows:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>3,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=11,destination=10,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 PDT 2000]]
Edge[source=23,destination=24,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Thu May 04 00:00:00 PDT 2000]]
Edge[source=28,destination=27,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Thu May 04 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Thu May 04 00:00:00 PDT 2000]]

```

Now you can see all of the daily edges from 10 to 11 have been aggregated together and their counts have been summed.

If we apply some pre-aggregation filtering, we can choose a time window to aggregate over. The new operation looks like this:


{% codetabs name="Java", type="java" -%}
final GetAllElements edgesSummarisedInTimeWindowOperation = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("startDate")
                                .execute(new IsMoreThan(MAY_01_2000, true))
                                .select("endDate")
                                .execute(new IsLessThan(MAY_02_2000, false))
                                .build()
                        )
                        .groupBy() // set the group by properties to 'none'
                        .build())
                .build())
        .build();

final CloseableIterable<? extends Element> edgesSummarisedInTimeWindow = graph.execute(edgesSummarisedInTimeWindowOperation, user);
{%- endcodetabs %}


The time window aggregated results are:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]

```

Now we have all the RoadUse edges that fall in the time window May 01 2000 to May 03 2000. This filtered out all edges apart from 2 occurring between junction 10 and 11. Therefore, the count is has been aggregated to just 2 (instead of 3 as seen previously).

Aggregation also works nicely alongside visibilities. Data at different visibility levels is stored separately, then the query time aggregation will aggregate just the data that a given user can see (visibility is effectively part of the groupBy at ingest time, then automatically overridden at query time).

There is another more advanced feature with query time aggregation.
When executing a query you can override the logic for how Gaffer aggregates properties together. 
By default the count property is aggregated with Sum (as defined in the schema).
At query time we could change that, to force the count property to be aggregated with the Min operator, therefore finding the minimum daily count.
This feature doesn't affect any of the persisted values and any ingest aggregation that has already occurred will not be modified.
So in this example the Edges have been summarised into daily time buckets and the counts have been aggregated with Sum.
Now at query time we are able to ask: What is the minimum daily count?

Here is the java code:


{% codetabs name="Java", type="java" -%}
final GetAllElements edgesSummarisedInTimeWindowWithMinCountOperation = new GetAllElements.Builder()
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("startDate")
                                .execute(new IsMoreThan(MAY_01_2000, true))
                                .select("endDate")
                                .execute(new IsLessThan(MAY_03_2000, false))
                                .build()
                        )
                        .groupBy() // set the group by properties to 'none'
                        .aggregator(new ElementAggregator.Builder()
                                .select("count")
                                .execute(new Min())
                                .build())
                        .build())
                .build())
        .build();

printJson("GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_RESULT_WITH_MIN_COUNT", edgesSummarisedInTimeWindowWithMinCountOperation);

final CloseableIterable<? extends Element> edgesSummarisedInTimeWindowWithMinCount = graph.execute(edgesSummarisedInTimeWindowWithMinCountOperation, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllElements",
  "view" : {
    "edges" : {
      "RoadUse" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "startDate" ],
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : true,
            "value" : {
              "Date" : 957164400000
            }
          }
        }, {
          "selection" : [ "endDate" ],
          "predicate" : {
            "class" : "IsLessThan",
            "orEqualTo" : false,
            "value" : {
              "Date" : 957337200000
            }
          }
        } ],
        "groupBy" : [ ],
        "aggregator" : {
          "operators" : [ {
            "selection" : [ "count" ],
            "binaryOperator" : {
              "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
            }
          } ]
        }
      }
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
  "view" : {
    "edges" : {
      "RoadUse" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "startDate" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : true,
            "value" : {
              "java.util.Date" : 957164400000
            }
          }
        }, {
          "selection" : [ "endDate" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
            "orEqualTo" : false,
            "value" : {
              "java.util.Date" : 957337200000
            }
          }
        } ],
        "groupBy" : [ ],
        "aggregator" : {
          "operators" : [ {
            "selection" : [ "count" ],
            "binaryOperator" : {
              "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
            }
          } ]
        }
      }
    }
  }
}

{%- endcodetabs %}


So, you can see we have just added an extra 'aggregator' block to the Operation view.

We have increased the time window to 3 days just so there are multiple edges to demonstrate the query time aggregation.
The result is:

```
Edge[source=10,destination=11,directed=true,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]

```
 
For more information on Views and aggregating, see [Views](views.md).
