# Map
See javadoc - [uk.gov.gchq.gaffer.operation.impl.Map](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/Map.html)

Available since Gaffer version 1.2.0

Maps an input to an output using provided functions

## Required fields
The following fields are required: 
- functions


## Examples

### Extract from get elements

This simple example demonstrates retrieving elements from the "entity" group, from which the first item is extracted.

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
final OperationChain<?> operationChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .view(new View.Builder()
                        .entity("entity")
                        .build())
                .build())
        .then(new Map.Builder<Iterable<? extends Element>>()
                .first(new FirstItem<>())
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
      "entities" : {
        "entity" : { }
      }
    }
  }, {
    "class" : "Map",
    "functions" : [ {
      "class" : "FirstItem"
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
      "entities" : {
        "entity" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.Map",
    "functions" : [ {
      "class" : "uk.gov.gchq.koryphe.impl.function.FirstItem"
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
            group="entity" 
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
    g.Map( 
      functions=[ 
        g.FirstItem() 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 3
  }
}
{%- endcodetabs %}

-----------------------------------------------

### Extract first items from walks

This example demonstrates the extraction of the input seeds to a GetWalks operation, using the Map operation with ExtractWalkEdgesFromHop, and FirstItem functions.

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
final OperationChain<Set<?>> opChain = new OperationChain.Builder()
        .first(new GetWalks.Builder()
                .operations(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .resultsLimit(100)
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new Map.Builder<Iterable<Walk>>()
                .first(new IterableFunction.Builder<Walk>()
                        .first(new ExtractWalkEdgesFromHop(0))
                        .then(new FirstItem<>())
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.SOURCE)
                .build())
        .then(new ToSet<>())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetWalks",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 1
    }, {
      "class" : "EntitySeed",
      "vertex" : 2
    } ],
    "operations" : [ {
      "class" : "OperationChain",
      "operations" : [ {
        "class" : "GetElements",
        "view" : {
          "edges" : {
            "edge" : { }
          }
        }
      } ]
    } ],
    "resultsLimit" : 100
  }, {
    "class" : "Map",
    "functions" : [ {
      "class" : "IterableFunction",
      "functions" : [ {
        "class" : "ExtractWalkEdgesFromHop",
        "hop" : 0
      }, {
        "class" : "FirstItem"
      } ]
    } ]
  }, {
    "class" : "ToVertices",
    "edgeVertices" : "SOURCE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.GetWalks",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 2
    } ],
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
      "operations" : [ {
        "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
        "view" : {
          "edges" : {
            "edge" : { }
          }
        }
      } ]
    } ],
    "resultsLimit" : 100
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.Map",
    "functions" : [ {
      "class" : "uk.gov.gchq.koryphe.impl.function.IterableFunction",
      "functions" : [ {
        "class" : "uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdgesFromHop",
        "hop" : 0
      }, {
        "class" : "uk.gov.gchq.koryphe.impl.function.FirstItem"
      } ]
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "edgeVertices" : "SOURCE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetWalks( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      operations=[ 
        g.OperationChain( 
          operations=[ 
            g.GetElements( 
              view=g.View( 
                edges=[ 
                  g.ElementDefinition( 
                    group="edge" 
                  ) 
                ], 
                all_edges=False, 
                all_entities=False 
              ) 
            ) 
          ] 
        ) 
      ], 
      results_limit=100 
    ), 
    g.Map( 
      functions=[ 
        g.IterableFunction( 
          functions=[ 
            g.ExtractWalkEdgesFromHop( 
              hop=0 
            ), 
            g.FirstItem() 
          ] 
        ) 
      ] 
    ), 
    g.ToVertices( 
      edge_vertices="SOURCE" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
1
2

{%- language name="JSON", type="json" -%}
[ 1, 2 ]
{%- endcodetabs %}

-----------------------------------------------

