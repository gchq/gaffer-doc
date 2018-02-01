${HEADER}

${CODE_LINK}

Aggregation is a key powerful feature in Gaffer and we will guide you through it in this example.

Aggregation is applied at ingest and query time.

1. Ingest aggregation permanently aggregates similar elements together. Elements will be aggregated if:
    1. An entity has the same group, vertex, visibility and any specified groupBy property values are identical.
    2. An edge has the same group, source, destination, directed and any specified groupBy property values are identical.
2. Query aggregation allows additional aggregation depending on a user's visibility permissions and any overridden groupBy properties provided at query time.

For this example we have added a timestamp to our csv data:

${DATA}

### Ingest Aggregation

#### The Elements schema

The schema is similar to what we have seen before, but we are adding 'startDate' and 'endDate' properties to the 'RoadUse' edges. We will use the timestamp from the input data to generate the start and end dates - the midnight before the timestamp and a millisecond before the following midnight, i.e. the start and end of the day. This effectively creates a time bucket with day granularity.
These properties have been added to the groupBy field, which will be used to determine whether elements should be aggregated together at ingest. Adding these properties to the groupBy field means that Gaffer will aggregate data for the same day at ingest.

${ELEMENTS_JSON}

#### The Types schema

We have introduced 2 new types ('date.earliest' and 'date.latest') that we need to define in the types schema:

${TYPES_JSON}

Both of the types added have a validator that ensures the properties exist (are not null). The 'date.earliest' type is aggregated by the 'Min' operator, and 'date.latest' is aggregated by the 'Max' operator.

We need to update our generator to create the date properties from the timestamp in the csv data. This is where we create the time bucket:

${ELEMENT_GENERATOR_JAVA}

Once we have loaded the data into Gaffer, we can fetch all the edges using a GetAllElements operation.
Note this operation is not recommended for large Graphs as it will do a full scan of your database and could take a while to finish.

${START_JAVA_CODE}
${GET_SNIPPET}
${END_CODE}

All edges:

```
${GET_ALL_EDGES_RESULT}
```

You can see in the results that the timestamp has been converted into start and end dates and that all edges with a timestamp on the same day have been aggregated together (ingest aggregation).
These aggregated edges have had all of their properties aggregated together using the aggregate functions specified in the schema.

#### Query Time Aggregation

Next we will demonstrate query time aggregation and get all the 'RoadUse' edges with the same source to the same destination to be aggregated together, regardless of the start and end dates.
This is achieved by overriding the groupBy field with an empty array so no properties are grouped on. Here is the operation:

${START_JAVA_CODE}
${GET_ALL_EDGES_SUMMARISED_SNIPPET}
${END_CODE}

The aggregated edges are as follows:

```
${GET_ALL_EDGES_SUMMARISED_RESULT}
```

Now you can see all of the daily edges from 10 to 11 have been aggregated together and their counts have been summed.

If we apply some pre-aggregation filtering, we can choose a time window to aggregate over. The new operation looks like this:

${START_JAVA_CODE}
${GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_SNIPPET}
${END_CODE}

The time window aggregated results are:

```
${GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_RESULT}
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

${START_JAVA_CODE}
${GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_WITH_MIN_COUNT_SNIPPET}
${JSON_CODE}
${GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_RESULT_WITH_MIN_COUNT_JSON}
${FULL_JSON_CODE}
${GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_RESULT_WITH_MIN_COUNT_FULL_JSON}
${END_CODE}

So, you can see we have just added an extra 'aggregator' block to the Operation view.

We have increased the time window to 3 days just so there are multiple edges to demonstrate the query time aggregation.
The result is:

```
${GET_ALL_EDGES_SUMMARISED_IN_TIME_WINDOW_RESULT_WITH_MIN_COUNT}
```
 
For more information on Views and aggregating, see [Views](views.md).