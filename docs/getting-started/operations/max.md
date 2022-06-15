# Max
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Max](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Max.html)

Available since Gaffer version 1.0.0

Extracts the maximum element based on provided Comparators

## Required fields
The following fields are required: 
- comparators


## Examples

### Max count

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<Element> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new Max.Builder()
                .comparators(new ElementPropertyComparator.Builder()
                        .groups("entity", "edge")
                        .property("count")
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetElements",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 1
    }, {
      "class" : "EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
    "comparators" : [ {
      "class" : "ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Max( 
      comparators=[ 
        g.ElementPropertyComparator( 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          property="count", 
          reversed=False 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 3
  }
}
{%- endcodetabs %}

-----------------------------------------------

### Max count and transient property

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<Element> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("VERTEX", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .transientProperty("score", Integer.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("DESTINATION", "count")
                                        .execute(new ExampleScoreFunction())
                                        .project("score")
                                        .build())
                                .build())
                        .build())
                .build())
        .then(new Max.Builder()
                .comparators(
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .reverse(false)
                                .build(),
                        new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("score")
                                .reverse(false)
                                .build()
                )
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetElements",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 1
    }, {
      "class" : "EntitySeed",
      "vertex" : 2
    } ],
    "view" : {
      "edges" : {
        "edge" : {
          "transientProperties" : {
            "score" : "Integer"
          },
          "transformFunctions" : [ {
            "selection" : [ "DESTINATION", "count" ],
            "function" : {
              "class" : "ExampleScoreFunction"
            },
            "projection" : [ "score" ]
          } ]
        }
      },
      "entities" : {
        "entity" : {
          "transientProperties" : {
            "score" : "Integer"
          },
          "transformFunctions" : [ {
            "selection" : [ "VERTEX", "count" ],
            "function" : {
              "class" : "ExampleScoreFunction"
            },
            "projection" : [ "score" ]
          } ]
        }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
    "comparators" : [ {
      "class" : "ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    }, {
      "class" : "ElementPropertyComparator",
      "property" : "score",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 2
    } ],
    "view" : {
      "edges" : {
        "edge" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "selection" : [ "DESTINATION", "count" ],
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ]
          } ]
        }
      },
      "entities" : {
        "entity" : {
          "transientProperties" : {
            "score" : "java.lang.Integer"
          },
          "transformFunctions" : [ {
            "selection" : [ "VERTEX", "count" ],
            "function" : {
              "class" : "uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction"
            },
            "projection" : [ "score" ]
          } ]
        }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "count",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "score",
      "groups" : [ "entity", "edge" ],
      "reversed" : false
    } ]
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      view=g.View( 
        entities=[ 
          g.ElementDefinition( 
            group="entity", 
            transient_properties={'score': 'java.lang.Integer'}, 
            transform_functions=[ 
              g.FunctionContext( 
                selection=[ 
                  "VERTEX", 
                  "count" 
                ], 
                function=g.Function( 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction", 
                  fields={} 
                ), 
                projection=[ 
                  "score" 
                ] 
              ) 
            ] 
          ) 
        ], 
        edges=[ 
          g.ElementDefinition( 
            group="edge", 
            transient_properties={'score': 'java.lang.Integer'}, 
            transform_functions=[ 
              g.FunctionContext( 
                selection=[ 
                  "DESTINATION", 
                  "count" 
                ], 
                function=g.Function( 
                  class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction", 
                  fields={} 
                ), 
                projection=[ 
                  "score" 
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
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.Max( 
      comparators=[ 
        g.ElementPropertyComparator( 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          property="count", 
          reversed=False 
        ), 
        g.ElementPropertyComparator( 
          groups=[ 
            "entity", 
            "edge" 
          ], 
          property="score", 
          reversed=False 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[score=<java.lang.Integer>6,count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "score" : 6,
    "count" : 3
  }
}
{%- endcodetabs %}

-----------------------------------------------

