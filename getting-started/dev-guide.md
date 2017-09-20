# Developer Guide
1. [Introduction](#introduction)
2. [Walkthroughs](#walkthroughs)
   1. [Visibilities](#visibilities)
   2. [Schemas](#schemas)
   3. [Users](#users)
   4. [Jobs](#jobs)
   5. [NamedOperations](#namedoperations)
   6. [FederatedStore](#federatedstore)


## Introduction 

These developer examples will assume you have already read the user walkthroughs.
They aim to provide developers with a bit more information about how to configure a Gaffer Graph.

### Running the Examples

The example can be run in a similar way to the user examples. 

You can download the doc-jar-with-dependencies.jar from [maven central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22uk.gov.gchq.gaffer%22%20AND%20a%3A%22doc%22). Select the latest version and download the jar-with-dependencies.jar file.
Alternatively you can compile the code yourself by running:

```
mvn clean install -pl doc -am -Pquick
```

The doc-jar-with-dependencies.jar file will be located here: doc/target/doc-jar-with-dependencies.jar.

```bash
# Replace <Visibilities> with your example name.
java -cp doc-jar-with-dependencies.jar uk.gov.gchq.gaffer.doc.dev.walkthrough.Visibilities
```
## Walkthroughs
### Visibilities

The code for this example is [Visibilities](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/Visibilities.java).

Another one of Gaffer's key features is visibility filtering, fine grained data access and query execution controls. 

In this example we'll add a visibility property to our edges so that we can control access to them.

Let's assume that any road use information about junctions greater than 20 is private and only users that have the `private` data access authorization are allowed to view them.

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


We've added the new `"visibility"` property to the RoadUse edge. We have also told Gaffer that whenever it sees a property called 'visibility' that this is a special property and should be used for restricting a user's visibility of the data.

We've defined a new `"visibility"` type in our Types, which is a Java String and must be non-null in order for the related edge to be loaded into the Graph.
We also specified that the visibility property is serialised using the custom [VisibilitySerialiser](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/serialiser/VisibilitySerialiser.java) and aggregated using the [VisibilityAggregator](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/aggregator/VisibilityAggregator.java) binary operator.
In our example, the serialiser is responsible for writing the visibility property into the store. This includes the logic which determines any hierarchy associated with the visibility properties (for example, `public` edges may be viewed by users with the `public` or `private` visibility label).
The aggregator is responsible for implementing the logic which ensures that edges maintain the correct visibility as they are combined (i.e that if a `public` edge is combined with a `private` edge, the result is also `private`).


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

After creating a Graph and adding our edges to it we run a simple query to get back all RoadUse edges containing vertex `"20"`... and we get nothing back.
This is because the user we ran the query as was not allowed to see edges with a visibility of `public` or `private`, so no edges were returned.

We can create a user that can see `public` edges only (and not `private` edges) and then run the query as this user.


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

If we rerun the query with a public user, we just get back the `public` edges:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[visibility=<java.lang.String>public,count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[visibility=<java.lang.String>public,count=<java.lang.Long>1]]

```

We can also create a user that can see `private` edges (and therefore `public` ones as well):


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

```
Edge[source=23,destination=24,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[visibility=<java.lang.String>private,count=<java.lang.Long>2]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[visibility=<java.lang.String>public,count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[visibility=<java.lang.String>public,count=<java.lang.Long>1]]

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





### Schemas

The code for this example is [Schemas](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/Schemas.java).

This example delves into more detail on Schemas. As we have seen previously there are 2 main components to schemas: the Elements schema and the Types schema.

It is worth remembering that your schema can be broken up further if required. 
For example if you have a Graph that is made up of 5 different data sources, you may find it easier to develop and maintain by splitting your schema into multiple parts, for example 5 Element Schemas and 1 Types schema.
When you construct your Gaffer graph you must provide all the Schema parts. These will then be merged together to form a 'master' schema for the Graph.

We will talk through some more of the features you can add to your Schemas to make them more readable and to model your data more accurately.


#### Elements schema
The elements schema is designed to be a high level document describing what information your Graph contains - like the different types of edges and entities and the list of properties associated with each.
Essentially this part of the schema should just be a list of all the entities and edges in the graph. 
Edges describe the relationship between a source vertex and a destination vertex. 
Entities describe a vertex. We use the term "element" to mean either and edge or and entity.

When defining an element we must provide a "group". This is a unique string that defines the type of an element.
Groups must be completely unique and cannot be shared between edges and entities.

Edges must have the following:
- source - this is the type of object that will be used as the source vertex in your graph. The value here is a string similar to a property type (it could be the same type as a property type).
- destination - similar to source, it can either be the same or a different type.
- directed - we need to tell Gaffer if the edge is directed or undirected. Currently, the easiest way to do this is to create a type called "true", "false" and define that type in the Type schema as being a boolean with a filter predicate to check the boolean is true or false

Entities must have a vertex field - this is similar to the source and destination fields on an edge.


Edges and Entities can optionally have the following fields:
- description - this is a simple string that should provide some context and explain what the element is.
- parents - this should be an array of parent group names. Note - the parent groups must relate to the same element type as the child, for example an edge cannot have an entity as a parent. Elements can inherit any information from multiple parent elements - fields will be merged/overridden so the order that you defined your parents is important. Any fields that are defined in the child element will also merge or override information taken from the parents.
- properties - Properties are be defined by a map of key-value pairs of property names to property types. The property type should be described in the Types schema.
- groupBy - by default Gaffer will use the element group and it's vertices to group similar elements together in order to aggregate and summarise the elements. This groupBy field allows you to specify extra properties that should be used in addition to the element group and vertices to control when similar elements should be grouped together and summarised.
- visibilityProperty - if you are using visibility properties in your graph, then ensure the sensitive elements have a visibility property and then set this visibilityProperty field to that property name so Gaffer knows to restrict access to this element.
- timestampProperty - if you are using timestamp property in your graph, then set this timestampProperty field to that property name so Gaffer knows to treat that property specially.
- aggregate - this is true by default. If you would like to disable aggregation for this element group set this to false.

These 2 optional fields are for advanced users. They can go in the Elements Schema however, we have split them out into separate Schema files Validation and Aggregation so the logic doesn't complicate the Elements schema.
- validateFunctions - an array of selections and predicates to be applied to an element. This allows you to validate based on multiple properties at once - like check a timestamp property together with a time to live property to check if the element should be aged off. Individual property validation is best done as a validateFunction in the property type definition in Types schema.
- aggregateFunctions - an array of selections and binary operators to be applied to an element. This allows you to aggregate based on multiple properties at once. It is important to note that types of properties (groupBy, non-groupBy, visibility) cannot be aggregated in the same aggregate function. The timestamp property is treated as a non-groupBy property. Individual property aggregation is best done as a aggregateFunction in the property type definition in the Types schema.

Here is an example of an Elements schema


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
  },
  "entities": {
    "Cardinality": {
      "description": "An entity that is added to every vertex representing the connectivity of the vertex.",
      "vertex": "anyVertex",
      "properties": {
        "edgeGroup": "set",
        "hllp": "hllp",
        "count": "count.long"
      },
      "groupBy": [
        "edgeGroup"
      ]
    }
  }
}
```


Here is the Validation Schema. It contains advanced validation, that is applied to multiple properties within an Element group.


```json
{
  "edges": {
    "RoadUse": {
      "validateFunctions": [
        {
          "selection": [
            "startDate",
            "endDate"
          ],
          "predicate": {
            "class": "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          }
        }
      ]
    }
  }
}
```


Here is the Aggregation Schema. It contains advanced aggregation, that is applied to multiple properties within an Element group.
The multi property aggregate function defined here overrides the relevant single property aggregate functions defined in the Types schema.


```json
{
  "edges": {
    "RoadUse": {
      "aggregateFunctions": [
        {
          "selection": [
            "startDate",
            "endDate"
          ],
          "binaryOperator": {
            "class": "uk.gov.gchq.gaffer.doc.dev.aggregator.ExampleTuple2BinaryOperator"
          }
        }
      ]
    }
  }
}
```



#### Types
All types used in the elements schema must be defined in the types parts of the schema. These Types explain to Gaffer what types of properties to expect and how to deal with them.

For each type you must provide the following information:

- class - this is the Java class of the type

You can optionally provide the following:

- description - a string containing a description of the type
- validateFunctions - an array of predicates that will be executed against every type value to validate it. To improve performance put quicker/low cost functions first in the array.
- aggregateFunction - the aggregate binary operator to use to aggregate/summarise/merge property values of the same type together.
- serialiser - an object that contains a field class which represents the java class of the serialiser to use, and potentially arguments depending on the serialiser. If this is not provided Gaffer will attempt to select an appropriate one for you - this is only available for simple Java types.

Here are some example Types

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
    "anyVertex": {
      "description": "An String vertex - used for cardinalities",
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
    },
    "set": {
      "class": "java.util.TreeSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      }
    },
    "hllp": {
      "class": "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
      }
    }
  }
}
```



##### Serialisers
Gaffer will automatically choose serialisers for you for some core types.
Where possible you should let Gaffer choose for you, as it will choose the optimal
serialiser for the type and your usage.

For custom types you will need to write your own serialiser. When manually
choosing a serialiser for your schema you will need to take the following into
consideration.

For vertex serialisation and groupBy properties you must choose serialisers that
are consistent. A consistent serialiser will serialise the equal objects into
exactly the same values (bytes). For example the JavaSerialiser and
FreqMapSerialiser are not consistent.

When using an ordered store (a store that implements the ORDERED StoreTrait),
this includes Accumulo and HBase, you need to check whether the serialisers are
ordered.

 - for vertex serialisation you must use a serialiser that is ordered
 - for groupBy properties we recommend using a serialiser that is ordered, however it is not essential. In fact it will not cause any problems at present. In the future we plan to add features that would only be available to you if your groupBy properties are serialised using ordered serialisers.
 - all other properties can be serialised with ordered/unordered serialisers.

#### Full Schema
Once the schema has been loaded into a graph the parent elements are merged into the children for performance reasons. This is what the full schema created from the above example schema parts looks like:

```json
{
  "edges" : {
    "RoadUse" : {
      "properties" : {
        "startDate" : "date.earliest",
        "endDate" : "date.latest",
        "count" : "count.long"
      },
      "groupBy" : [ "startDate", "endDate" ],
      "description" : "A directed edge representing vehicles moving from junction A to junction B.",
      "source" : "junction",
      "destination" : "junction",
      "directed" : "true",
      "aggregateFunctions" : [ {
        "binaryOperator" : {
          "class" : "uk.gov.gchq.gaffer.doc.dev.aggregator.ExampleTuple2BinaryOperator"
        },
        "selection" : [ "startDate", "endDate" ]
      } ],
      "validateFunctions" : [ {
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
        },
        "selection" : [ "startDate", "endDate" ]
      } ]
    },
    "RoadHasJunction" : {
      "properties" : { },
      "groupBy" : [ ],
      "description" : "A directed edge from each road to all the junctions on that road.",
      "source" : "road",
      "destination" : "junction",
      "directed" : "true"
    }
  },
  "entities" : {
    "Cardinality" : {
      "properties" : {
        "edgeGroup" : "set",
        "hllp" : "hllp",
        "count" : "count.long"
      },
      "groupBy" : [ "edgeGroup" ],
      "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
      "vertex" : "anyVertex"
    }
  },
  "types" : {
    "junction" : {
      "description" : "A road junction represented by a String.",
      "class" : "java.lang.String"
    },
    "road" : {
      "description" : "A road represented by a String.",
      "class" : "java.lang.String"
    },
    "anyVertex" : {
      "description" : "An String vertex - used for cardinalities",
      "class" : "java.lang.String"
    },
    "count.long" : {
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
        "orEqualTo" : true,
        "value" : {
          "java.lang.Long" : 0
        }
      } ],
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
      },
      "description" : "A long count that must be greater than or equal to 0.",
      "class" : "java.lang.Long"
    },
    "true" : {
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
      } ],
      "description" : "A simple boolean that must always be true.",
      "class" : "java.lang.Boolean"
    },
    "date.earliest" : {
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
      } ],
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
      },
      "description" : "A Date that when aggregated together will be the earliest date.",
      "class" : "java.util.Date"
    },
    "date.latest" : {
      "validateFunctions" : [ {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
      } ],
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
      },
      "description" : "A Date that when aggregated together will be the latest date.",
      "class" : "java.util.Date"
    },
    "set" : {
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      },
      "class" : "java.util.TreeSet"
    },
    "hllp" : {
      "serialiser" : {
        "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
      },
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
      },
      "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus"
    }
  }
}

```




### Users

The code for this example is [Users](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/Users.java).

When interacting with Gaffer you need to use a [User](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/user/User.html). The user object
contains:
- userId - unique identifier for the user
- dataAuths - user authorisations for accessing data
- opAuths - user authorisations for running different Gaffer operations

The execute methods on Graph requires you to pass in an instance of User.
This relies on you creating an instance that represents you, with your unique id
and authorisations.

For users of Gaffer, interacting in Java is insecure as you are able to set
any authorisations you want to. However, as you are writing Java it is assumed
that you have access to the store.properties file so you have access to the
connection details (including password) for your Accumulo/HBase cluster. This
means that if you wanted to you could by-pass Gaffer and read all the data
directly on disk if you wanted to.

The security layer for Gaffer is currently only enforced by a REST API. We
recommend restricting users so they do not have access to the cluster (Accumulo, HBase, etc.)
and only allowing users to connect to Gaffer via the REST API. In the REST API
the User object is constructed via a UserFactory. Currently we only provide one
implementation of this, the UnknownUserFactory. This UnknownUserFactory will
always just return 'new User()'.

To authenticate your users, you will need to extend the REST API and add your
chosen authentication mechanism. This will probably involve updating the web.xml to include
your security roles. Based on that mechanism, you will then need to
implement your own UserFactory class that creates a new User instance based on
the user making the REST API request. This could may involve making a call to your LDAP server.
For example you could use the authorization header in the request:

```java
public class LdapUserFactory implements UserFactory {

    @Context
    private HttpHeaders httpHeaders;

    public User createUser() {
        final String authHeaderValue = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION); // add logic to fetch userId
        final String userId = null; // extract from authHeaderValue
        final List<String> opAuths = null; // fetch op auths for userId
        final List<String> dataAuths = null; // fetch op auths for userId
        return new User.Builder()
                .userId(userId)
                .opAuths(opAuths)
                .dataAuths(dataAuths)
                .build();
    }
}
```




### Jobs

The code for this example is [Jobs](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/Jobs.java).

This example explains how to configure your Gaffer Graph to allow Jobs to be executed.
This includes a Job Tracker to store the status of current and historic jobs and a cache to store job results in.
Jobs are useful if an operation chain takes a long time to return results or if you wish to cache the results of an operation chain.
When we refer to a 'Job' we are really just talking about an Operation Chain containing a long-running sequence of operations that is executed asynchronously.


#### Configuration

By default the Job Tracker is disabled. To enable the job tracker set this store.property:

```
gaffer.store.job.tracker.enabled=true
```

You will also need to configure what cache to use for the job tracker. The same cache is used for named operations and the job tracker.
For example, to use the JCS cache service, add a dependency on the jcs-cache-service and set these store.properties:

```xml
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>jcs-cache-service</artifactId>
    <version>[gaffer.version]</version>
</dependency>
```

```
gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.JcsCacheService

# Optionally provide custom cache properties
gaffer.cache.config.file=/path/to/config/file
```

In addition to the job tracker, it is recommended that you enable a cache to store the job results in. The caching mechanism is implemented as operations and operation handlers. By default these are disabled.
The job result cache is simply a second Gaffer Graph. So, if you are running on Accumulo, this can just be a separate table in your existing Accumulo cluster.

Two operations are required for exporting and getting results from a Gaffer cache - ExportToGafferResultCache and GetGafferResultCacheExport.
These two operations need to be registered by providing an Operations Declarations JSON file in your store.properties file.
To use the Accumulo store as your Gaffer cache the operations declarations JSON file would need to look something like:


```json
{
  "operations": [
    {
      "operation": "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
      "handler": {
        "class": "uk.gov.gchq.gaffer.operation.export.resultcache.handler.ExportToGafferResultCacheHandler",
        "graphId": "resultCacheGraph",
        "timeToLive": 86400000,
        "storePropertiesPath": "cache-store.properties"
      }
    },
    {
      "operation": "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "handler": {
        "class": "uk.gov.gchq.gaffer.operation.export.resultcache.handler.GetGafferResultCacheExportHandler",
        "graphId": "resultCacheGraph",
        "timeToLive": 86400000,
        "storePropertiesPath": "cache-store.properties"
      }
    }
  ]
}
```


Here we are simply registering the fact that ExportToGafferResultCache operation should be handled by the ExportToGafferResultCacheHandler handler. We also provide a path to the Gaffer cache store properties for the cache handler to create a Gaffer graph.
Then to register this file in your store.properties file you will need to add the following:

```
gaffer.store.operation.declarations=/path/to/ResultCacheExportOperations.json
```

If you are also registering other operations you can just supply a comma separated list of operation declaration files:

```
gaffer.store.operation.declarations=/path/to/operations1.json,/path/to/ResultCacheExportOperations.json
```

The JSON files can either be placed on your file system or bundled as a resource in your JAR or WAR archive.

For this example the cache-store.properties just references another MockAccumuloStore table:


```
gaffer.store.class=uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.accumulostore.AccumuloProperties
accumulo.instance=someInstanceName
accumulo.zookeepers=aZookeeper
accumulo.user=user01
accumulo.password=password
gaffer.store.job.executor.threads=1

```



#### Using Jobs
OK, now for some examples of using Jobs.

We will use the same basic schema and data from the first developer walkthrough.

Start by creating your user instance and graph as you will have done previously:


```java
final User user = new User("user01");
```


```java
final Graph graph = new Graph.Builder()
        .config(StreamUtil.graphConfig(getClass()))
        .addSchemas(StreamUtil.openStreams(getClass(), "RoadAndRoadUseWithTimesAndCardinalities/schema"))
        .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
        .build();
```

Then create a job, otherwise known as an operation chain:


```java
final OperationChain<CloseableIterable<? extends Element>> job = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .edge("RoadUse")
                        .build())
                .build())
        .build();
```

When you execute your job, instead of using the normal execute method on Graph you will need to use the executeJob method.


```java
final JobDetail initialJobDetail = graph.executeJob(job, user);
final String jobId = initialJobDetail.getJobId();
```

and the results is:

```
JobDetail[jobId=8dde7044-d9ad-47a1-8500-46105996905a,userId=user01,status=RUNNING,startTime=1505920361946,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetElements@17a1e4ca, uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache@10ded6a9]]]

```

As you can see this returns a JobDetail object containing your job id. The GetJobDetails operation allows you to check the status of your Job, e.g:


```java
final JobDetail jobDetail = graph.execute(
        new GetJobDetails.Builder()
                .jobId(jobId)
                .build(),
        user);
```

and now you can see the job has finished:

```
JobDetail[jobId=8dde7044-d9ad-47a1-8500-46105996905a,userId=user01,status=FINISHED,startTime=1505920361946,endTime=1505920362191,opChain=OperationChain[operations=[uk.gov.gchq.gaffer.operation.impl.get.GetElements@17a1e4ca, uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache@10ded6a9]]]

```

You can actually get the details of all running and completed jobs using the GetAllJobDetails operation:


```java
final CloseableIterable<JobDetail> jobDetails = graph.execute(new GetAllJobDetails(), user);
```

Then finally you can get the results of your job using the GetJobResults operation:


```java
final CloseableIterable<?> jobResults = graph.execute(new GetJobResults.Builder()
        .jobId(jobId)
        .build(), user);
```

and the results were:

```
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]

```




### NamedOperations

The code for this example is [NamedOperations](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/NamedOperations.java).

This example explains how to configure your Gaffer Graph to allow named operations to be executed. 
Named operations enable encapsulation of an OperationChain into a new single NamedOperation.
The NamedOperation can be added to OperationChains and executed, just like
any other Operation. When run it executes the encapsulated OperationChain.
There are various possible uses for NamedOperations:
 * Making it simpler to run frequently used OperationChains
 * In a controlled way, allowing specific OperationChains to be run by a user that would not normally have permission to run them.

In addition to the NamedOperation there are a set of operations which manage named operations (AddNamedOperation, GetAllNamedOperations, DeleteNamedOperation).

#### Configuration
You will need to configure what cache to use for storing NamedOperations. 
There is one central cache service for Gaffer, so the same cache is used for named operations and the job tracker.
For example, to use the JCS cache service, add a dependency on the jcs-cache-service and set these store.properties:

```xml
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>jcs-cache-service</artifactId>
    <version>[gaffer.version]</version>
</dependency>
```

```
gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.JcsCacheService

# Optionally provide custom cache properties
gaffer.cache.config.file=/path/to/config/file
```


#### Using Named Operations
OK, now for some examples of using NamedOperations.

We will use the same basic schema and data from the first developer walkthrough.

Start by creating your user instance and graph as you will have done previously:


```java
final User user = new User("user01");
```


```java
final Graph graph = new Graph.Builder()
        .config(StreamUtil.graphConfig(getClass()))
        .addSchemas(StreamUtil.openStreams(getClass(), "RoadAndRoadUseWithTimesAndCardinalities/schema"))
        .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
        .build();
```

Then add a named operation to the cache with the AddNamedOperation operation:


```java
final AddNamedOperation addOperation = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("RoadUse")
                                .build())
                        .build())
                .then(new Limit.Builder<>().resultLimit(10).build())
                .build())
        .description("named operation limit query")
        .name("2-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .overwrite()
        .build();

graph.execute(addOperation, user);
```

Then create a NamedOperation and execute it


```java
final NamedOperation<EntityId, CloseableIterable<? extends Element>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                .name("2-limit")
                .input(new EntitySeed("10"))
                .build();
```


```java
final CloseableIterable<? extends Element> results = graph.execute(operation, user);
```

The results are:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]

```

NamedOperations can take parameters, to allow the OperationChain executed to be configured. The parameter could be as
simple as specifying the resultLimit on a Limit operation, but specify a custom view to use in an operation, or the input to an operation.
When adding a NamedOperation with parameters the operation chain must be specified as a JSON string, with
parameter names enclosed '${' and '}'. For each parameter, a ParameterDetail object must be created which gives a description, a class type
and an optional default for the Parameter, and also indicates whether the parameter must be provided (ie. there is no default).

The following code adds a NamedOperation with a 'limitParam' parameter that allows the result limit for the OperationChain to be set:


```java
String opChainString = "{" +
        "  \"operations\" : [ {" +
        "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\"," +
        "    \"view\" : {" +
        "      \"edges\" : {" +
        "        \"RoadUse\" : { }" +
        "      }," +
        "      \"entities\" : { }" +
        "    }" +
        "  }, {" +
        "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\"," +
        "    \"resultLimit\" : \"${limitParam}\"" +
        "  } ]" +
        "}";

ParameterDetail param = new ParameterDetail.Builder()
        .defaultValue(1L)
        .description("Limit param")
        .valueClass(Long.class)
        .build();
Map<String, ParameterDetail> paramDetailMap = Maps.newHashMap();
paramDetailMap.put("limitParam", param);

final AddNamedOperation addOperationWithParams = new AddNamedOperation.Builder()
        .operationChain(opChainString)
        .description("named operation limit query")
        .name("custom-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .parameters(paramDetailMap)
        .overwrite()
        .build();

graph.execute(addOperationWithParams, user);
```

A NamedOperation can then be created, with a value provided for the 'limitParam' parameter:


```java
Map<String, Object> paramMap = Maps.newHashMap();
paramMap.put("limitParam", 3L);

final NamedOperation<EntityId, CloseableIterable<? extends Element>> operationWithParams =
        new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                .name("custom-limit")
                .input(new EntitySeed("10"))
                .parameters(paramMap)
                .build();
```

and executed:


```java
final CloseableIterable<? extends Element> namedOperationResults = graph.execute(operationWithParams, user);
```

giving these results:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]

```

Details of all available NamedOperations can be fetched using the GetAllNamedOperations operation:


```java
final CloseableIterable<NamedOperationDetail> details = graph.execute(new GetAllNamedOperations(), user);
```

That gives the following results:

NamedOperationDetail[creatorId=user01,operations={"operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetElements","view":{"edges":{"RoadUse":{}},"entities":{}}},{"class":"uk.gov.gchq.gaffer.operation.impl.Limit","resultLimit":10,"truncate":true}]},readAccessRoles=[read-user],writeAccessRoles=[write-user]]












### FederatedStore

The code for this example is [FederatedStoreWalkThrough](https://github.com/gchq/Gaffer/blob/master/doc/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/FederatedStoreWalkThrough.java).

The `FederatedStore` is simply a Gaffer store which forwards operations to a collection of sub-graphs and returns a single response as though it was a single graph.

This example explains how to create a `FederatedStore`, add/remove additional graphs, with/without authentication and call operations against the `FederatedStore`.

#### Configuration

To create a `FederatedStore` you need to initialise the store with a graphId and a properties file.


```java
final Graph federatedGraph = new Graph.Builder()
        .config(new GraphConfig.Builder()
                .graphId("federatedRoadUse")
                .library(new HashMapGraphLibrary())
                .build())
        .storeProperties(StreamUtil.openStream(getClass(), "federatedStore.properties"))
        .build();
```

#### Adding Graphs

You can't add a graph using a graphId already in use, you will need to explicitly remove the old GraphId first.
You can limit access to the sub-graphs when adding to FederatedStore, see [Limiting Access](#limiting-access).

To load a graph into the `FederatedStore` you need to provide:
 * GraphId
 * Graph Schema
 * Graph Properties

**Note** Schema & Properties are not required if GraphId is known by the GraphLibrary

Using either the `FederatedStore` properties file...
```
gaffer.store.class=uk.gov.gchq.gaffer.federatedstore.FederatedStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.store.StoreProperties

gaffer.federatedstore.customPropertiesAuths=<auths1>,<auths2>

gaffer.federatedstore.graphIds=<graphId1>, <graphId2>
gaffer.federatedstore.<GraphId1>.properties.file=<path/to/properties>
gaffer.federatedstore.<GraphId1>.schema.file=<path/to/schema>

gaffer.federatedstore.<GraphId2>.properties.id=<Id name in graphLibrary>
gaffer.federatedstore.<GraphId2>.schema.id=<Id name in graphLibrary>
```

or through the `AddGraph` operation. However this operation maybe limited for some
users if the `FederatedStore` property "gaffer.federatedstore.customPropertiesAuths" is defined.
Without matching authorisation users can only specify adding a graph with `StoreProperties` from the `GraphLibrary`.


```java
AddGraph addAnotherGraph = new AddGraph.Builder()
        .setGraphId("AnotherGraph")
        .schema(new Schema.Builder()
                .json(StreamUtil.openStreams(getClass(), "RoadAndRoadUseWithTimesAndCardinalitiesForFederatedStore/schema"))
                .build())
        .storeProperties(StoreProperties.loadStoreProperties("mockmapstore.properties"))
        .build();
federatedGraph.execute(addAnotherGraph, user);
```

or through the rest service with json.

```json
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.AddGraph",
  "graphId" : "AnotherGraph",
  "storeProperties" : {
    "gaffer.store.class" : "uk.gov.gchq.gaffer.mapstore.SingleUseMapStore",
    "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.mapstore.MapStoreProperties"
  },
  "schema" : {
    "edges" : {
      "RoadUse" : {
        "properties" : {
          "startDate" : "date.earliest",
          "endDate" : "date.latest",
          "count" : "count.long"
        },
        "groupBy" : [ "startDate", "endDate" ],
        "description" : "A directed edge representing vehicles moving from junction A to junction B.",
        "source" : "junction",
        "destination" : "junction",
        "directed" : "true",
        "validateFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          },
          "selection" : [ "startDate", "endDate" ]
        }, {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          },
          "selection" : [ "startDate", "endDate" ]
        } ]
      },
      "RoadHasJunction" : {
        "properties" : { },
        "groupBy" : [ ],
        "description" : "A directed edge from each road to all the junctions on that road.",
        "source" : "road",
        "destination" : "junction",
        "directed" : "true"
      }
    },
    "entities" : {
      "Cardinality" : {
        "properties" : {
          "edgeGroup" : "set",
          "hllp" : "hllp",
          "count" : "count.long"
        },
        "groupBy" : [ "edgeGroup" ],
        "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
        "vertex" : "anyVertex"
      }
    },
    "types" : {
      "junction" : {
        "description" : "A road junction represented by a String.",
        "class" : "java.lang.String"
      },
      "road" : {
        "description" : "A road represented by a String.",
        "class" : "java.lang.String"
      },
      "anyVertex" : {
        "description" : "An String vertex - used for cardinalities",
        "class" : "java.lang.String"
      },
      "count.long" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : true,
          "value" : {
            "java.lang.Long" : 0
          }
        } ],
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
        },
        "description" : "A long count that must be greater than or equal to 0.",
        "class" : "java.lang.Long"
      },
      "true" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
        } ],
        "description" : "A simple boolean that must always be true.",
        "class" : "java.lang.Boolean"
      },
      "date.earliest" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
        } ],
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
        },
        "description" : "A Date that when aggregated together will be the earliest date.",
        "class" : "java.util.Date"
      },
      "date.latest" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
        } ],
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
        },
        "description" : "A Date that when aggregated together will be the latest date.",
        "class" : "java.util.Date"
      },
      "set" : {
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
        },
        "class" : "java.util.TreeSet"
      },
      "hllp" : {
        "serialiser" : {
          "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
        },
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
        },
        "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus"
      }
    }
  }
}

```

#### Removing Graphs

To remove a graph from the `FederatedStore` is even easier, you only need to know the graphId. This does not delete the graph only removes it from the scope.


```java
RemoveGraph removeGraph = new RemoveGraph.Builder()
        .setGraphId("AnotherGraph")
        .build();
federatedGraph.execute(removeGraph, user);
```

or through the rest service with json.

```
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.RemoveGraph",
  "graphId" : "AnotherGraph"
}

```

#### Getting all the GraphId's

To get a list of all the sub-graphs within the `FederatedStore` you can perform the following `GetAllGraphId` operation.


```java
final GetAllGraphIds getAllGraphIDs = new GetAllGraphIds();
Iterable<? extends String> graphIds = federatedGraph.execute(getAllGraphIDs, user);
```

or through the rest service with json.

```json
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.GetAllGraphIds"
}

```


and the result is:

```
[accumuloGraph, mapGraph]

```

#### Performing Operations

Running operations against the `FederatedStore` is exactly same as running operations against any other store.
Behind the scenes the `FederatedStore` sends out operations/operation chains to the sub-graphs to be executed and returns back a single response.
`AddElements` operations is a special case, and only adds elements to sub-graphs when the edge or entity groupId is known by the sub-graph.

**Warning** When adding elements, if 2 sub-graphs contain the same group in the schema then the elements will be added to both of the sub-graphs.
A following `GetElements` operation would then return the same element from both sub-graph, resulting in duplicates.
It is advised to keep groups to within one sub-graph.


```java
final OperationChain<Void> addOpChain = new OperationChain.Builder()
        .first(new GenerateElements.Builder<String>()
                .generator(new RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator())
                .input(IOUtils.readLines(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalities/data.txt")))
                .build())
        .then(new AddElements())
        .build();

federatedGraph.execute(addOpChain, user);
```


```java
final OperationChain<CloseableIterable<? extends Element>> getOpChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("10"))
                .build())
        .build();

CloseableIterable<? extends Element> elements = federatedGraph.execute(getOpChain, user);
```

and the results are:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@58c34bb3,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@56a4479a,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]

```


```java
final OperationChain<CloseableIterable<? extends Element>> getOpChainOnAccumuloGraph = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("10"))
                .option(FederatedStoreConstants.GRAPH_IDS, "accumuloGraph")
                .build())
        .build();

CloseableIterable<? extends Element> elementsFromAccumuloGraph = federatedGraph.execute(getOpChainOnAccumuloGraph, user);
```

and the results are:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]

```

#### Failed Execution
If the execution against a graph fails, that graph is skipped and the `FederatedStore` continues with the remaining graphs. Unless the operation has the option flag "skipFailedFederatedStoreExecute" set to `false`, in that situation a `OperationException` is thrown.

#### Limiting Access
It is possible to have a `FederatedStore` with many sub-graphs, however you may wish to limit the users access. This is possible by using authorisations at the time of adding a graph to the FederatedStore, this limits the graphs users can send operations to via the FederatedStore.
Explicitly setting authorisations to an empty list grants access to all users.

This can be done at `FederatedStore` Initialisation, by default if authorisations is not specified it will be public to all.

```
gaffer.federatedstore.<GraphId1>.auths=public,private

```

or via the `AddGraph` operation, by default if authorisations is not specified it is private to the user that added it to FederatedStore.


```java
AddGraph addSecureGraph = new AddGraph.Builder()
        .setGraphId("SecureGraph")
        .schema(new Schema.Builder()
                .json(StreamUtil.openStreams(getClass(), "RoadAndRoadUseWithTimesAndCardinalities/schema"))
                .build())
        .storeProperties(StoreProperties.loadStoreProperties("mockmapstore.properties"))
        .graphAuths("public", "private")
        .build();
federatedGraph.execute(addSecureGraph, user);
```

or through the rest service with json, by default if authorisations is not specified it is private to the user that added it to FederatedStore.

```json
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.AddGraph",
  "graphId" : "SecureGraph",
  "storeProperties" : {
    "gaffer.store.class" : "uk.gov.gchq.gaffer.mapstore.SingleUseMapStore",
    "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.mapstore.MapStoreProperties"
  },
  "schema" : {
    "edges" : {
      "RoadUse" : {
        "properties" : {
          "startDate" : "date.earliest",
          "endDate" : "date.latest",
          "count" : "count.long"
        },
        "groupBy" : [ "startDate", "endDate" ],
        "description" : "A directed edge representing vehicles moving from junction A to junction B.",
        "source" : "junction",
        "destination" : "junction",
        "directed" : "true",
        "validateFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          },
          "selection" : [ "startDate", "endDate" ]
        } ]
      },
      "RoadHasJunction" : {
        "properties" : { },
        "groupBy" : [ ],
        "description" : "A directed edge from each road to all the junctions on that road.",
        "source" : "road",
        "destination" : "junction",
        "directed" : "true"
      }
    },
    "entities" : {
      "Cardinality" : {
        "properties" : {
          "edgeGroup" : "set",
          "hllp" : "hllp",
          "count" : "count.long"
        },
        "groupBy" : [ "edgeGroup" ],
        "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
        "vertex" : "anyVertex"
      }
    },
    "types" : {
      "junction" : {
        "description" : "A road junction represented by a String.",
        "class" : "java.lang.String"
      },
      "road" : {
        "description" : "A road represented by a String.",
        "class" : "java.lang.String"
      },
      "anyVertex" : {
        "description" : "An String vertex - used for cardinalities",
        "class" : "java.lang.String"
      },
      "count.long" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : true,
          "value" : {
            "java.lang.Long" : 0
          }
        } ],
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
        },
        "description" : "A long count that must be greater than or equal to 0.",
        "class" : "java.lang.Long"
      },
      "true" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
        } ],
        "description" : "A simple boolean that must always be true.",
        "class" : "java.lang.Boolean"
      },
      "date.earliest" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
        } ],
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
        },
        "description" : "A Date that when aggregated together will be the earliest date.",
        "class" : "java.util.Date"
      },
      "date.latest" : {
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
        } ],
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
        },
        "description" : "A Date that when aggregated together will be the latest date.",
        "class" : "java.util.Date"
      },
      "set" : {
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
        },
        "class" : "java.util.TreeSet"
      },
      "hllp" : {
        "serialiser" : {
          "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
        },
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
        },
        "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus"
      }
    }
  },
  "graphAuths" : [ "public", "private" ]
}

```



