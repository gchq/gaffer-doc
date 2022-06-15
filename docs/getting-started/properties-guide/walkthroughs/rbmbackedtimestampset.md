# RBMBackedTimestampSet

The code for this example is [TimestampSetWalkthrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/properties/walkthrough/TimestampSetWalkthrough.java).

This example demonstrates how the [TimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/TimestampSet.java) property can be used to maintain a set of the timestamps at which an element was seen active. In this example we record the timestamps to minute level accuracy, i.e. the seconds are ignored.

##### Elements schema
This is our new elements schema. The edge has a property called 'timestampSet'. This will store the [TimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/TimestampSet.java) object, which is actually a [RBMBackedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/RBMBackedTimestampSet.java).


```json
{
  "edges": {
    "red": {
      "source": "vertex.string",
      "destination": "vertex.string",
      "directed": "false",
      "properties": {
        "timestampSet": "timestamp.set"
      }
    }
  }
}
```


##### Types schema
We have added a new type - 'timestamp.set'. This is a [uk.gov.gchq.gaffer.time.RBMBackedTimestampSet](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/RBMBackedTimestampSet.java) object.
We also added in the [serialiser](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/serialisation/RBMBackedTimestampSetSerialiser.java) and [aggregator](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/binaryoperator/RBMBackedTimestampSetAggregator.java) for the RBMBackedTimestampSet object. Gaffer will automatically aggregate these sets together to maintain a set of all the times the element was active.


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
    "timestamp.set": {
      "class": "uk.gov.gchq.gaffer.time.RBMBackedTimestampSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.time.binaryoperator.RBMBackedTimestampSetAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.time.serialisation.RBMBackedTimestampSetSerialiser"
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


Only one edge is in the graph. This was added 25 times, and each time it had the 'timestampSet' property containing a randomly generated timestamp from 2017. Here is the Edge:

```
Edge[source=A,destination=B,directed=false,group=red,properties=Properties[timestampSet=<uk.gov.gchq.gaffer.time.RBMBackedTimestampSet>RBMBackedTimestampSet[timeBucket=MINUTE,timestamps=2017-01-08T07:29:00Z,2017-01-18T10:41:00Z,2017-01-19T01:36:00Z,2017-01-31T16:16:00Z,2017-02-02T08:06:00Z,2017-02-12T14:21:00Z,2017-02-15T22:01:00Z,2017-03-06T09:03:00Z,2017-03-21T18:09:00Z,2017-05-08T15:34:00Z,2017-05-10T19:39:00Z,2017-05-16T10:44:00Z,2017-05-23T10:02:00Z,2017-05-28T01:52:00Z,2017-06-24T23:50:00Z,2017-07-27T09:34:00Z,2017-08-05T02:11:00Z,2017-09-07T07:35:00Z,2017-10-01T12:52:00Z,2017-10-23T22:02:00Z,2017-10-27T04:12:00Z,2017-11-01T02:45:00Z,2017-12-11T16:38:00Z,2017-12-22T14:40:00Z,2017-12-24T08:00:00Z]]]

```

You can see the list of timestamps on the edge. We can also get just the earliest, latest and total number of timestamps using methods on the TimestampSet object to get the following results:

```
Edge A-B was first seen at 2017-01-08T07:29:00Z, last seen at 2017-12-24T08:00:00Z, and there were 25 timestamps it was active.

```
