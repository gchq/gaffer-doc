# Map & Set Properties

Gaffer supports the storage of some Java Map and Set objects as properties on entities and edges. Serialisers for these will automatically be added to your schema when you create a graph using a schema that uses these properties.

There are also some more advanced properties which allow sets of timestamps to be stored on entities and edges - these do require their serialisers to be specified in a schema. More information on these are given in a [dedicated section below](map-set.md#timestamp-properties).

## Class List

Property | Full Class
-------- | ----------
`HashMap` | `java.util.HashMap`
`TreeSet` | `java.util.TreeSet`
`FreqMap` | [`uk.gov.gchq.gaffer.types.FreqMap`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/FreqMap.html)
`BoundedTimestampSet` | [`uk.gov.gchq.gaffer.time.BoundedTimestampSet`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/BoundedTimestampSet.html)
`RBMBackedTimestampSet` | [`uk.gov.gchq.gaffer.time.RBMBackedTimestampSet`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/RBMBackedTimestampSet.html)

## Predicate Support

The properties above all support these predicates:

- `And`
- `Or`
- `Not`
- `If`
- `Exists`
- `IsA`
- `IsIn`
- `IsEqual`
- `PropertiesFilter`

The FreqMap and HashMap properties also support these predicates:

- `IsLongerThan`
- `IsShorterThan`
- `MapContains`
- `MapContainsPredicate`
- `PredicateMap`

The TreeSet property additionally supports these predicates:

- `IsLongerThan`
- `IsShorterThan`
- `CollectionContains`
- `AreIn`

The RBMBackedTimestampSet property has a specilaised predicate [`uk.gov.gchq.gaffer.time.predicate.RBMBackedTimestampSetInRange`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/predicate/RBMBackedTimestampSetInRange.html).

## Aggregator Support

The `First` and `Last` binary operators are supported by all advanced properties. FreqMap and HashMap also both support the `BinaryOperatorMap` binary operator.

Some of the other properties have (one or more of) their own specialised aggregator(s):

Property | Binary Operator
-------- | ---------------
`FreqMap` | [`uk.gov.gchq.gaffer.types.function.FreqMapAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/function/FreqMapAggregator.html)
`TreeSet` | `CollectionConcat`
`TreeSet` | `CollectionIntersect`
`BoundedTimestampSet` | [`uk.gov.gchq.gaffer.time.binaryoperator.BoundedTimestampSetAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/binaryoperator/BoundedTimestampSetAggregator.html)
`RBMBackedTimestampSet` | [`uk.gov.gchq.gaffer.time.binaryoperator.RBMBackedTimestampSetAggregator`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/binaryoperator/RBMBackedTimestampSetAggregator.html)

## Serialiser Support

All advanced properties support the [`NullSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/NullSerialiser.html). FreqMap and HashMap also both support the [`MapSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/MapSerialiser.html) serialiser.

Some of the other properties have (one or more of) their own specialised serialiser(s):

Property | Serialiser
-------- | ---------------
`FreqMap` | [`uk.gov.gchq.gaffer.serialisation.FreqMapSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/FreqMapSerialiser.html)
`TreeSet` | [`uk.gov.gchq.gaffer.serialisation.implementation.SetSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/SetSerialiser.html)
`TreeSet` | [`uk.gov.gchq.gaffer.serialisation.implementation.TreeSetStringSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/TreeSetStringSerialiser.html)
`BoundedTimestampSet` | [`uk.gov.gchq.gaffer.time.serialisation.BoundedTimestampSetSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/serialisation/BoundedTimestampSetSerialiser.html)
`RBMBackedTimestampSet` | [`uk.gov.gchq.gaffer.time.serialisation.RBMBackedTimestampSetSerialiser`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/serialisation/RBMBackedTimestampSetSerialiser.html)

## Timestamp Properties

There are two timestamp properties:

- RBMBackedTimestampSet: When this is created, a [TimeBucket](https://github.com/gchq/Gaffer/blob/master/library/time-library/src/main/java/uk/gov/gchq/gaffer/time/CommonTimeUtil.java) is specified, e.g. minute. When a timestamp is added, it is truncated to the nearest start of a bucket (e.g. if the bucket is a minute then the seconds are removed) and then added to the set. Internally the timestamps are stored in a [Roaring Bitmap](http://roaringbitmap.org/).
- BoundedTimestampSet: This is similar to the above set, except that when it is created a maximum size is specified. If the set grows beyond the maximum size, then a random sample of the timestamps of that size is created. This is useful to avoid the size of the set for entities or edges that are very busy growing too large. By retaining a sample, we maintain an indication of when the entity or edge was active, without retaining all the information. The sample is implemented using a [ReservoirLongsUnion](https://github.com/apache/datasketches-java/blob/sketches-core-0.13.4/src/main/java/com/yahoo/sketches/sampling/ReservoirLongsUnion.java) from the Datasketches library.

### RBMBackedTimestampSet Example

This example demonstrates how the RBMBackedTimestampSet property can be used to maintain a set of the timestamps at which an element was seen active.

??? example "Example a set of the timestamps using RBMBackedTimestampSet"

    In this example we record the timestamps to minute level accuracy, i.e. the seconds are ignored.

    #### Elements schema

    This is our new elements schema. The edge has a property called 'timestampSet'. This will store the RBMBackedTimestampSet object.

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

    #### Types schema
    We have added a new type - 'timestamp.set'. This is a `uk.gov.gchq.gaffer.time.RBMBackedTimestampSet` object.
    We also added in the serialiser and aggregator for the RBMBackedTimestampSet object. Gaffer will automatically aggregate these sets together to maintain a set of all the times the element was active.

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
    Edge[source=A,destination=B,directed=false,matchedVertex=SOURCE,group=red,properties=Properties[timestampSet=<uk.gov.gchq.gaffer.time.RBMBackedTimestampSet>RBMBackedTimestampSet[timeBucket=MINUTE,timestamps=2017-01-08T07:29:00Z,2017-01-18T10:41:00Z,2017-01-19T01:36:00Z,2017-01-31T16:16:00Z,2017-02-02T08:06:00Z,2017-02-12T14:21:00Z,2017-02-15T22:01:00Z,2017-03-06T09:03:00Z,2017-03-21T18:09:00Z,2017-05-08T15:34:00Z,2017-05-10T19:39:00Z,2017-05-16T10:44:00Z,2017-05-23T10:02:00Z,2017-05-28T01:52:00Z,2017-06-24T23:50:00Z,2017-07-27T09:34:00Z,2017-08-05T02:11:00Z,2017-09-07T07:35:00Z,2017-10-01T12:52:00Z,2017-10-23T22:02:00Z,2017-10-27T04:12:00Z,2017-11-01T02:45:00Z,2017-12-11T16:38:00Z,2017-12-22T14:40:00Z,2017-12-24T08:00:00Z]]]
    ```

    You can see the list of timestamps on the edge. We can also get just the earliest, latest and total number of timestamps using methods on the TimestampSet object to get the following results:

    ```
    Edge A-B was first seen at 2017-01-08T07:29:00Z, last seen at 2017-12-24T08:00:00Z, and there were 25 timestamps it was active.
    ```

### BoundedTimestampSet Example

This example demonstrates how the BoundedTimestampSet property can be used to maintain a set of the timestamps at which an element was seen active.

??? example "Example a set of the timestamps using BoundedTimestampSet"

    If this set becomes larger than a size specified by the user then a uniform random sample of the timestamps is maintained. In this example we record the timestamps to minute level accuracy, i.e. the seconds are ignored, and specify that at most 25 timestamps should be retained.

    #### Elements schema
    This is our new schema. The edge has a property called 'boundedTimestampSet'. This will store the BoundedTimestampSet object.

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

    #### Types schema
    We have added a new type - 'bounded.timestamp.set'. This is a `uk.gov.gchq.gaffer.time.BoundedTimestampSet` object. We have added in the serialiser and aggregator for the BoundedTimestampSet object. Gaffer will automatically aggregate these sets together to maintain a set of all the times the element was active. Once the size of the set becomes larger than 25 then a uniform random sample of size at most 25 of the timestamps is maintained.

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
    Edge[source=A,destination=C,directed=false,matchedVertex=SOURCE,group=red,properties=Properties[boundedTimestampSet=<uk.gov.gchq.gaffer.time.BoundedTimestampSet>BoundedTimestampSet[timeBucket=MINUTE,state=SAMPLE,maxSize=25,timestamps=2017-01-01T15:49:00Z,2017-01-19T19:48:00Z,2017-02-02T08:42:00Z,2017-02-11T00:54:00Z,2017-04-02T17:27:00Z,2017-04-07T06:12:00Z,2017-04-23T02:28:00Z,2017-05-19T13:54:00Z,2017-05-25T04:20:00Z,2017-05-28T01:52:00Z,2017-05-28T23:07:00Z,2017-06-11T00:02:00Z,2017-06-13T16:47:00Z,2017-06-16T14:14:00Z,2017-06-17T21:39:00Z,2017-06-25T15:48:00Z,2017-06-26T02:46:00Z,2017-07-15T14:55:00Z,2017-07-15T19:58:00Z,2017-08-15T16:34:00Z,2017-08-16T08:39:00Z,2017-09-05T02:05:00Z,2017-12-09T13:55:00Z,2017-12-22T12:40:00Z,2017-12-24T11:12:00Z]]]
    Edge[source=A,destination=B,directed=false,matchedVertex=SOURCE,group=red,properties=Properties[boundedTimestampSet=<uk.gov.gchq.gaffer.time.BoundedTimestampSet>BoundedTimestampSet[timeBucket=MINUTE,state=NOT_FULL,maxSize=25,timestamps=2017-02-12T14:21:00Z,2017-03-21T18:09:00Z,2017-12-24T08:00:00Z]]]
    ```

    You can see that edge A-B has the full list of timestamps on the edge, but edge A-C has a sample of the timestamps.
