# Transforms

The code for this example is [Transforms](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Transforms.java).

In this example we’ll look at how to query for Edges and then add a new transient property to the Edges in the result set.
Again, we will just use the same schema and data as in the previous example.

A transient property is just a property that is not persisted, simply created at query time by a transform function. We’ll create a 'description' transient property that will summarise the contents of the aggregated Edges.

To do this we need a [Function](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html). This is our [DescriptionTransform](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/road-traffic-model/src/main/java/uk/gov/gchq/gaffer/traffic/transform/DescriptionTransform.java) that we will use.

This transform function takes 3 values, the source junction vertex, the destination junction vertex and the count property. It produces a description string.

We configure the function using an [ElementTransformer](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ElementTransformer.html):


{% codetabs name="Java", type="java" -%}
final ElementTransformer descriptionTransformer = new ElementTransformer.Builder()
        .select("SOURCE", "DESTINATION", "count")
        .execute(new DescriptionTransform())
        .project("description")
        .build();

{%- language name="JSON", type="json" -%}
{
  "functions" : [ {
    "selection" : [ "SOURCE", "DESTINATION", "count" ],
    "function" : {
      "class" : "DescriptionTransform"
    },
    "projection" : [ "description" ]
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "functions" : [ {
    "selection" : [ "SOURCE", "DESTINATION", "count" ],
    "function" : {
      "class" : "uk.gov.gchq.gaffer.traffic.transform.DescriptionTransform"
    },
    "projection" : [ "description" ]
  } ]
}

{%- endcodetabs %}


Here you can see we `select` the inputs for the function; the `”SOURCE”` vertex, the `”DESTINATION”` vertex and `”count”` property. We `project` the result into a transient property named `”description”`.
The selection here is similar to the way we select properties and identifiers in a filter,
remember you can select (and also project) any property name or any of these identifiers:

- VERTEX - this is the vertex on an Entity
- SOURCE - this is the source vertex on an Edge
- DESTINATION - this is the destination vertex on an Edge
- DIRECTED - this is the directed field on an Edge
- MATCHED_VERTEX - this is the vertex that was matched in the query, either the SOURCE or the DESTINATION
- ADJACENT_MATCHED_VERTEX - this is the adjacent vertex that was matched in the query, either the SOURCE or the DESTINATION. I.e if your seed matches the source of the edge this would resolve to the DESTINATION value.

We add the new `”description”` property to the result Edge using a `View` and then execute the operation:


{% codetabs name="Java", type="java" -%}
final GetElements getEdgesWithDescription = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .transientProperty("description", String.class)
                        .transformer(descriptionTransformer)
                        .build())
                .build())
        .build();
final CloseableIterable<? extends Element> resultsWithDescription = graph.execute(getEdgesWithDescription, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "edges" : {
      "RoadUse" : {
        "transientProperties" : {
          "description" : "String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "DESTINATION", "count" ],
          "function" : {
            "class" : "DescriptionTransform"
          },
          "projection" : [ "description" ]
        } ]
      }
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "edges" : {
      "RoadUse" : {
        "transientProperties" : {
          "description" : "java.lang.String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "DESTINATION", "count" ],
          "function" : {
            "class" : "uk.gov.gchq.gaffer.traffic.transform.DescriptionTransform"
          },
          "projection" : [ "description" ]
        } ]
      }
    }
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="RoadUse", 
        transient_properties={'description': 'java.lang.String'}, 
        transform_functions=[ 
          g.FunctionContext( 
            selection=[ 
              "SOURCE", 
              "DESTINATION", 
              "count" 
            ], 
            function=g.Function( 
              class_name="uk.gov.gchq.gaffer.traffic.transform.DescriptionTransform", 
              fields={} 
            ), 
            projection=[ 
              "description" 
            ] 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex="10" 
    ) 
  ] 
)


{%- endcodetabs %}


This produces the following result:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3,description=<java.lang.String>3 vehicles have travelled between junction 10 and junction 11]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1,description=<java.lang.String>1 vehicles have travelled between junction 11 and junction 10]]

```

As you can see we’ve now got a new `”description”` property on each Edge.

The view also allows us to limit what properties we want to be returned. We can either provide a list of properties to include
using the 'properties' field, or properties to exclude using the 'excludeProperties' field. We can modify the View from the
previous query so that once we have used the count property to create our description we exclude it from the returned results:


{% codetabs name="Java", type="java" -%}
final GetElements getEdgesWithDescriptionAndNoCount = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .transientProperty("description", String.class)
                        .transformer(descriptionTransformer)
                        .excludeProperties("count")
                        .build())
                .build())
        .build();
final CloseableIterable<? extends Element> resultsWithDescriptionAndNoCount = graph.execute(getEdgesWithDescriptionAndNoCount, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "edges" : {
      "RoadUse" : {
        "transientProperties" : {
          "description" : "String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "DESTINATION", "count" ],
          "function" : {
            "class" : "DescriptionTransform"
          },
          "projection" : [ "description" ]
        } ],
        "excludeProperties" : [ "count" ]
      }
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "edges" : {
      "RoadUse" : {
        "transientProperties" : {
          "description" : "java.lang.String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "DESTINATION", "count" ],
          "function" : {
            "class" : "uk.gov.gchq.gaffer.traffic.transform.DescriptionTransform"
          },
          "projection" : [ "description" ]
        } ],
        "excludeProperties" : [ "count" ]
      }
    }
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="RoadUse", 
        transient_properties={'description': 'java.lang.String'}, 
        transform_functions=[ 
          g.FunctionContext( 
            selection=[ 
              "SOURCE", 
              "DESTINATION", 
              "count" 
            ], 
            function=g.Function( 
              class_name="uk.gov.gchq.gaffer.traffic.transform.DescriptionTransform", 
              fields={} 
            ), 
            projection=[ 
              "description" 
            ] 
          ) 
        ], 
        exclude_properties=[ 
          "count" 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex="10" 
    ) 
  ] 
)


{%- endcodetabs %}


The count property has been removed from the results:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[description=<java.lang.String>3 vehicles have travelled between junction 10 and junction 11]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[description=<java.lang.String>1 vehicles have travelled between junction 11 and junction 10]]

```

For more information on Views and transforms, see [Views](views.md).
