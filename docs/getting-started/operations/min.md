# Min
See javadoc - [uk.gov.gchq.gaffer.operation.impl.compare.Min](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Min.html)

Available since Gaffer version 1.0.0

Extracts the minimum element based on provided Comparators

## Required fields
The following fields are required: 
- comparators


## Examples

### Min count

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
        .then(new Min.Builder()
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
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
    g.Min( 
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
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}
{%- endcodetabs %}

-----------------------------------------------

### Min count and transient property

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
        .then(new Min.Builder()
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
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
    g.Min( 
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
Entity[vertex=2,group=entity,properties=Properties[score=<java.lang.Integer>2,count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "score" : 2,
    "count" : 1
  }
}
{%- endcodetabs %}

-----------------------------------------------

