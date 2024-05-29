# Visibilities

## Using Visibilities for fine-grained security

Another one of Gaffer's key features is visibility filtering, fine grained data access and query execution controls.

The code for this example is [Visibilities](https://github.com/gchq/gaffer-doc/blob/v1docs/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/Visibilities.java)

In this example we'll add a visibility property to our edges so that we can control access to them.

Let's assume that any road use information about junctions greater than 20 is private and only users that have the private data access authorization are allowed to view them.

We will use the same data as before but we need to modify the schema to add the new visibility property.

Here is the new elements schema:

```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "visibility": "visibility",
        "count": "count.long"
      }
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    }
  },
  "visibilityProperty": "visibility"
}
```

We've added the new "visibility" property to the RoadUse edge. We have also told Gaffer that whenever it sees a property called 'visibility' that this is a special property and should be used for restricting a user's visibility of the data.

We've defined a new "visibility" type in our Types, which is a Java String and must be non-null in order for the related edge to be loaded into the Graph. We also specified that the visibility property is serialised using the custom [Visibility Serialiser](https://github.com/gchq/gaffer-doc/blob/v1docs/src/main/java/uk/gov/gchq/gaffer/doc/dev/serialiser/VisibilitySerialiser.java) and aggregated using the [VisibilityAggregator](https://github.com/gchq/gaffer-doc/blob/v1docs/src/main/java/uk/gov/gchq/gaffer/doc/dev/aggregator/VisibilityAggregator.java) binary operator. In our example, the serialiser is responsible for writing the visibility property into the store. This includes the logic which determines any hierarchy associated with the visibility properties (for example, public edges may be viewed by users with the public or private visibility label). The aggregator is responsible for implementing the logic which ensures that edges maintain the correct visibility as they are combined (i.e that if a public edge is combined with a private edge, the result is also private).

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
    "visibility": {
      "description": "A visibility string, either 'public' or 'private'. When a public and private visibility is aggregated together it will result in a private visibility.",
      "class": "java.lang.String",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.doc.dev.serialiser.VisibilitySerialiser"
      },
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.doc.dev.aggregator.VisibilityAggregator"
      }
    }
  }
}

```

We have updated the generator to add the visibility label as a new property (a Java String) on the edges:

```java
public class RoadAndRoadUseWithSecurityElementGenerator implements OneToManyElementGenerator<String> {
    @Override
    public Iterable<Element> _apply(final String line) {
        final String[] t = line.split(",");

        final String road = t[0];
        final String junctionA = t[1];
        final String junctionB = t[2];

        final int junctionAInt = Integer.parseInt(junctionA);
        final int junctionBInt = Integer.parseInt(junctionA);
        final String visibility;
        if (junctionAInt >= 20 || junctionBInt >= 20) {
            visibility = "private";
        } else {
            visibility = "public";
        }

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
                        .property("visibility", visibility)
                        .build()
        );
    }
}
```
After creating a Graph and adding our edges to it we run a simple query to get back all RoadUse edges containing vertex "20"... and we get nothing back. This is because the user we ran the query as was not allowed to see edges with a visibility of public or private, so no edges were returned.

We can create a user that can see public edges only (and not private edges) and then run the query as this user.

```java
final User publicUser = new User.Builder()
        .userId("publicUser")
        .dataAuth("public")
        .build();

final GetElements getPublicRelatedEdges = new GetElements.Builder()
        .input(new EntitySeed("10"), new EntitySeed("23"))
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .build();

final CloseableIterable<? extends Element> publicResults = graph.execute(getPublicRelatedEdges, publicUser);

```

If we rerun the query with a public user, we just get back the public edges:

```java
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[visibility=<java.lang.String>public,count=<java.lang.Long>1]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[visibility=<java.lang.String>public,count=<java.lang.Long>3]]
```
We can also create a user that can see private edges (and therefore public ones as well):

```java
final User privateUser = new User.Builder()
        .userId("privateUser")
        .dataAuth("private")
        .build();

final GetElements getPrivateRelatedEdges = new GetElements.Builder()
        .input(new EntitySeed("10"), new EntitySeed("23"))
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .build();

final CloseableIterable<? extends Element> privateResults = graph.execute(getPrivateRelatedEdges, privateUser);
```

If we rerun the query as the private user, we get back all of the edges:

```java
Edge[source=23,destination=24,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[visibility=<java.lang.String>private,count=<java.lang.Long>2]]
```

Here we performed a query with just 2 seeds. You can provide as many seeds here as you like and the Gaffer store will handle it for you, batching them up if required.

The visibility property as defined by the visibilityProperty field in the Schema is special case of a groupBy property.

- When ingest aggregation is carried out the visibilityProperty is treated as groupBy property.
- When query aggregation is carried out the visibilityProperty is no longer treated as a groupBy property.

To further demonstrate this here is another example:

You add these Edges:

```
1 -> 2   count = 1, visibility = "public"
1 -> 2   count = 2, visibility = "public"
1 -> 2   count = 10, visibility = "private"
1 -> 2   count = 20, visibility = "private"
```

These are persisted keeping the Edges with different visibilities separate, you can see the counts have been aggregated as they are not a groupBy property:

```
1 -> 2   count = 3, visibility = "public"
1 -> 2   count = 30, visibility = "private"
```

Then if a user with just "public" access to the system does a query they will just get back:

```
1 -> 2   count = 3, visibility = "public"
```

A user with "private" access, who by definition can also see "public" data, will get back both edges aggregated together:

```
1 -> 2   count = 33, visibility = "private"
```

