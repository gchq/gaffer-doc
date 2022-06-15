# ToVertices
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToVertices](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToVertices.html)

Available since Gaffer version 1.0.0

In these examples we use a ToSet operation after the ToVertices operation to deduplicate the results.

## Required fields
No required fields


## Examples

### Extract entity vertices

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
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .view(new View.Builder()
                        .entity("entity")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.NONE)
                .build())
        .then(new ToSet<>())
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
    "class" : "ToVertices",
    "edgeVertices" : "NONE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "edgeVertices" : "NONE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
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
    g.ToVertices( 
      edge_vertices="NONE" 
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

### Extract destination vertex

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
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.DESTINATION)
                .build())
        .then(new ToSet<>())
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "ToVertices",
    "edgeVertices" : "DESTINATION"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "edgeVertices" : "DESTINATION"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}

{%- language name="Python", type="py" -%}
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
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      include_incoming_out_going="OUTGOING" 
    ), 
    g.ToVertices( 
      edge_vertices="DESTINATION" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
2
4
3
5

{%- language name="JSON", type="json" -%}
[ 2, 4, 3, 5 ]
{%- endcodetabs %}

-----------------------------------------------

### Extract both source and destination vertices

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
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .edgeVertices(ToVertices.EdgeVertices.BOTH)
                .build())
        .then(new ToSet<>())
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "ToVertices",
    "edgeVertices" : "BOTH"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "edgeVertices" : "BOTH"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}

{%- language name="Python", type="py" -%}
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
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      include_incoming_out_going="OUTGOING" 
    ), 
    g.ToVertices( 
      edge_vertices="BOTH" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
1
2
4
3
5

{%- language name="JSON", type="json" -%}
[ 1, 2, 4, 3, 5 ]
{%- endcodetabs %}

-----------------------------------------------

### Extract matched vertices

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
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .useMatchedVertex(ToVertices.UseMatchedVertex.EQUAL)
                .build())
        .then(new ToSet<>())
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "ToVertices",
    "useMatchedVertex" : "EQUAL"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "useMatchedVertex" : "EQUAL"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}

{%- language name="Python", type="py" -%}
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
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      include_incoming_out_going="OUTGOING" 
    ), 
    g.ToVertices( 
      use_matched_vertex="EQUAL" 
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

### Extract opposite matched vertices

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
final OperationChain<Set<?>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build())
        .then(new ToVertices.Builder()
                .useMatchedVertex(ToVertices.UseMatchedVertex.OPPOSITE)
                .build())
        .then(new ToSet<>())
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "ToVertices",
    "useMatchedVertex" : "OPPOSITE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
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
        "edge" : { }
      }
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices",
    "useMatchedVertex" : "OPPOSITE"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  } ]
}

{%- language name="Python", type="py" -%}
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
      ), 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ], 
      include_incoming_out_going="OUTGOING" 
    ), 
    g.ToVertices( 
      use_matched_vertex="OPPOSITE" 
    ), 
    g.ToSet() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
2
4
3
5

{%- language name="JSON", type="json" -%}
[ 2, 4, 3, 5 ]
{%- endcodetabs %}

-----------------------------------------------

