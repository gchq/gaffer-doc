# Security

## Using Visibilities for fine-grained security

Another one of Gaffer's key features is visibility filtering, fine grained data access and query execution controls.

[Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/commonutil/elementvisibilityutil/VisibilityEvaluator.html)

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

We've defined a new "visibility" type in our Types, which is a Java String and must be non-null in order for the related edge to be loaded into the Graph. Custom visibility serialiser and aggregator are not required, Gaffer will use the optimal methods by default. However, if custom serialisers and aggregators are required, they can be specificed as below.

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
### Adding elements with visibility

??? example "Adding new edges with visibilities"

    === "Java"

        ```java
          new AddElements.Builder()
                      .input(new Edge.Builder()
                        .group("RoadHasJunction")
                        .source("1").dest("2").directed(true)
                        .property("count", 1)
                        .property("visibility", "private")
                        .build(),
                        new Edge.Builder()
                        .group("RoadHasJunction")
                        .source("1").dest("2").directed(true)
                        .property("count", 2)
                        .property("visibility", "public")
                        .build())
                      .build();
        ```

    === "JSON"

        ``` JSON
          {
            "class" : "AddElements",
            "input" : [ {
              "class" : "Edge",
              "group" : "RoadHasJunction",
              "source" : "1",
              "destination" : "2",
              "directed" : true,
              "properties" : {
                "count" : 1,
                "visibility": "private"
              }
            },
            {
              "class" : "Edge",
              "group" : "RoadHasJunction",
              "source" : "1",
              "destination" : "2",
              "directed" : true,
              "properties" : {
                "count" : 2,
                "visibility": "public"
              }
            } ],
            "skipInvalidElements" : false,
            "validate" : true
          }
        ```

    === "Python"

        ``` Python
          g.AddElements( 
            input=[ 
              g.Edge( 
                group="RoadHasJunction", 
                properties={"count": 1, "visibility": "private"}, 
                source="1", 
                destination="2", 
                directed=True 
              ),
              g.Edge( 
              group="RoadHasJunction", 
              properties={"count": 2, "visibility": "public"}, 
              source="1", 
              destination="2", 
              directed=True 
              )
            ], 
            skip_invalid_elements=False, 
            validate=True 
          )
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

To further demonstrate this, here is another example:

Add the following Edges:

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

