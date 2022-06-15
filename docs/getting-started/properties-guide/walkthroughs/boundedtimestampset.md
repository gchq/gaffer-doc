# BoundedTimestampSet

The code for this example is [BoundedTimestampSetWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/BoundedTimestampSetWalkthrough.java).

This example demonstrates how the [BoundedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/BoundedTimestampSet.java) property can be used to maintain a set of the timestamps at which an element was seen active. If this set becomes larger than a size specified by the user then a uniform random sample of the timestamps is maintained. In this example we record the timestamps to minute level accuracy, i.e. the seconds are ignored, and specify that at most 25 timestamps should be retained.

##### Elements schema
This is our new schema. The edge has a property called 'boundedTimestampSet'. This will store the [BoundedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/BoundedTimestampSet.java) object, which is actually a 'BoundedTimestampSet'.

```json
{
  "edges": {
    "red": {
      "source": "vertex.string",
      "destination": "vertex.string",
      "directed": "false",
      "properties": {
        "boundedTimestampSet": "bounded.timestamp.set"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'bounded.timestamp.set'. This is a uk.gov.gchq.gaffer.time.BoundedTimestampSet object. We have added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/serialisation/BoundedTimestampSetSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/binaryoperator/BoundedTimestampSetAggregator.java) for the BoundedTimestampSet object. Gaffer will automatically aggregate these sets together to maintain a set of all the times the element was active. Once the size of the set becomes larger than 25 then a uniform random sample of size at most 25 of the timestamps is maintained.


```json
{
  "types": {
    "vertex.string": {
      "class": "java.lang.String",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ]
    },
    "bounded.timestamp.set": {
      "class": "uk.gov.gchq.gaffer.time.BoundedTimestampSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.time.binaryoperator.BoundedTimestampSetAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.time.serialisation.BoundedTimestampSetSerialiser"
      }
    },
    "false": {
      "class": "java.lang.Boolean",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsFalse"
        }
      ]
    }
  }
}
```


There are two edges in the graph. Edge A-B was added 3 times, and each time it had the 'boundedTimestampSet' property containing a randomly generated timestamp from 2017. Edge A-C was added 1000 times, and each time it also had the 'boundedTimestampSet' property containing a randomly generated timestamp from 2017. Here are the edges:

```
Edge[source=A,destination=B,directed=false,group=red,properties=Properties[boundedTimestampSet=<uk.gov.gchq.gaffer.time.BoundedTimestampSet>BoundedTimestampSet[timeBucket=MINUTE,state=NOT_FULL,maxSize=25,timestamps=2017-02-12T14:21:00Z,2017-03-21T18:09:00Z,2017-12-24T08:00:00Z]]]
Edge[source=A,destination=C,directed=false,group=red,properties=Properties[boundedTimestampSet=<uk.gov.gchq.gaffer.time.BoundedTimestampSet>BoundedTimestampSet[timeBucket=MINUTE,state=SAMPLE,maxSize=25,timestamps=2017-01-04T16:30:00Z,2017-01-09T23:31:00Z,2017-02-06T19:43:00Z,2017-02-10T21:52:00Z,2017-02-23T10:53:00Z,2017-03-12T21:07:00Z,2017-03-24T12:14:00Z,2017-04-28T13:22:00Z,2017-05-02T08:25:00Z,2017-05-09T13:59:00Z,2017-06-22T04:40:00Z,2017-07-01T14:52:00Z,2017-08-07T12:37:00Z,2017-08-20T09:49:00Z,2017-08-26T04:51:00Z,2017-09-07T10:10:00Z,2017-10-01T12:52:00Z,2017-10-01T20:02:00Z,2017-10-03T21:18:00Z,2017-10-26T14:45:00Z,2017-12-09T17:50:00Z,2017-12-11T16:38:00Z,2017-12-13T04:16:00Z,2017-12-13T12:03:00Z,2017-12-31T15:46:00Z]]]

```

You can see that edge A-B has the full list of timestamps on the edge, but edge A-C has a sample of the timestamps.
