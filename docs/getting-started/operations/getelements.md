# GetElements
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html)

Available since Gaffer version 1.0.0

Gets elements related to provided seeds

## Required fields
No required fields


## Examples

### Get entities and edges by entity id 2 and edge id 2 to 3

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
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  }, {
    "class" : "EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EdgeSeed( 
      source=2, 
      destination=3, 
      directed_type="EITHER", 
      matched_vertex="SOURCE" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 2
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get entities and edges by entity id 2 and edge id 2 to 3 with count more than 1

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
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  }, {
    "class" : "EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
        } ]
      }
    },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
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
    "vertex" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
        } ]
      }
    },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
        } ]
      }
    }
  }
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    entities=[ 
      g.ElementDefinition( 
        group="entity", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value=1, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    edges=[ 
      g.ElementDefinition( 
        group="edge", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value=1, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EdgeSeed( 
      source=2, 
      destination=3, 
      directed_type="EITHER", 
      matched_vertex="SOURCE" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 2
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get entities and edges that are related to vertex 2

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
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 3
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get all entities and edges that are related to edge 1 to 2

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
final GetElements operation = new GetElements.Builder()
        .input(new EdgeSeed(1, 2, DirectedType.EITHER))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EdgeSeed",
    "source" : 1,
    "destination" : 2,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 1,
    "destination" : 2,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EdgeSeed( 
      source=1, 
      destination=2, 
      directed_type="EITHER", 
      matched_vertex="SOURCE" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 3
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get all entities and edges that are related to edge 1 to 2 with count more than 1

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
final GetElements operation = new GetElements.Builder()
        .input(new EdgeSeed(1, 2, DirectedType.EITHER))
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EdgeSeed",
    "source" : 1,
    "destination" : 2,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
        } ]
      }
    },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
        } ]
      }
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 1,
    "destination" : 2,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
        } ]
      }
    },
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 1
          }
        } ]
      }
    }
  }
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    entities=[ 
      g.ElementDefinition( 
        group="entity", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value=1, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    edges=[ 
      g.ElementDefinition( 
        group="edge", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value=1, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EdgeSeed( 
      source=1, 
      destination=2, 
      directed_type="EITHER", 
      matched_vertex="SOURCE" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 3
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get entities related to 2 with count less than 2 or more than 5

When using an Or predicate with a single selected value you can just do 'select(propertyName)' then 'execute(new Or(predicates))'

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
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(
                                new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new Or<>(new IsLessThan(2), new IsMoreThan(5)))
                                        .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  }, {
    "class" : "EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ],
  "view" : {
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
            "predicates" : [ {
              "class" : "IsLessThan",
              "orEqualTo" : false,
              "value" : 2
            }, {
              "class" : "IsMoreThan",
              "orEqualTo" : false,
              "value" : 5
            } ]
          }
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
    "vertex" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : 2,
    "destination" : 3,
    "matchedVertex" : "SOURCE",
    "directedType" : "EITHER"
  } ],
  "view" : {
    "entities" : {
      "entity" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
            "predicates" : [ {
              "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
              "orEqualTo" : false,
              "value" : 2
            }, {
              "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
              "orEqualTo" : false,
              "value" : 5
            } ]
          }
        } ]
      }
    }
  }
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    entities=[ 
      g.ElementDefinition( 
        group="entity", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.Or( 
              predicates=[ 
                g.IsLessThan( 
                  value=2, 
                  or_equal_to=False 
                ), 
                g.IsMoreThan( 
                  value=5, 
                  or_equal_to=False 
                ) 
              ] 
            ) 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EdgeSeed( 
      source=2, 
      destination=3, 
      directed_type="EITHER", 
      matched_vertex="SOURCE" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get edges related to 2 when source is less than 2 or destination is more than 3

When using an Or predicate with a multiple selected values, it is more complicated. First, you need to select all the values you want: 'select(a, b, c)'. This will create an array of the selected values, [a, b, c]. You then need to use the Or.Builder to build your Or predicate, using .select() then .execute(). When selecting values in the Or.Builder you need to refer to the position in the [a,b,c] array. So to use property 'a', use position 0 - select(0).

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
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .view(new View.Builder()
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(
                                new ElementFilter.Builder()
                                        .select(IdentifierType.SOURCE.name(), IdentifierType.DESTINATION.name())
                                        .execute(new Or.Builder<>()
                                                .select(0)
                                                .execute(new IsLessThan(2))
                                                .select(1)
                                                .execute(new IsMoreThan(3))
                                                .build())
                                        .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "SOURCE", "DESTINATION" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
            "predicates" : [ {
              "class" : "IntegerTupleAdaptedPredicate",
              "selection" : [ 0 ],
              "predicate" : {
                "class" : "IsLessThan",
                "orEqualTo" : false,
                "value" : 2
              }
            }, {
              "class" : "IntegerTupleAdaptedPredicate",
              "selection" : [ 1 ],
              "predicate" : {
                "class" : "IsMoreThan",
                "orEqualTo" : false,
                "value" : 3
              }
            } ]
          }
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
    "vertex" : 2
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "SOURCE", "DESTINATION" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
            "predicates" : [ {
              "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
              "selection" : [ 0 ],
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
                "orEqualTo" : false,
                "value" : 2
              }
            }, {
              "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
              "selection" : [ 1 ],
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                "orEqualTo" : false,
                "value" : 3
              }
            } ]
          }
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
        group="edge", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "SOURCE", 
              "DESTINATION" 
            ], 
            predicate=g.Or( 
              predicates=[ 
                g.NestedPredicate( 
                  selection=[ 
                    0 
                  ], 
                  predicate=g.IsLessThan( 
                    value=2, 
                    or_equal_to=False 
                  ) 
                ), 
                g.NestedPredicate( 
                  selection=[ 
                    1 
                  ], 
                  predicate=g.IsMoreThan( 
                    value=3, 
                    or_equal_to=False 
                  ) 
                ) 
              ] 
            ) 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 3
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get entities and return only some properties

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
final Concat concat = new Concat();
concat.setSeparator("|");
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .view(new View.Builder()
                .edge("edge", new ViewElementDefinition.Builder()
                        .transientProperty("vertex|count", String.class)
                        .properties("vertex|count")
                        .transformer(new ElementTransformer.Builder()
                                .select(IdentifierType.SOURCE.name(), "count")
                                .execute(concat)
                                .project("vertex|count")
                                .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "transientProperties" : {
          "vertex|count" : "String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "count" ],
          "function" : {
            "class" : "Concat",
            "separator" : "|"
          },
          "projection" : [ "vertex|count" ]
        } ],
        "properties" : [ "vertex|count" ]
      }
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "transientProperties" : {
          "vertex|count" : "java.lang.String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "count" ],
          "function" : {
            "class" : "uk.gov.gchq.koryphe.impl.function.Concat",
            "separator" : "|"
          },
          "projection" : [ "vertex|count" ]
        } ],
        "properties" : [ "vertex|count" ]
      }
    }
  }
}

{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="edge", 
        transient_properties={'vertex|count': 'java.lang.String'}, 
        transform_functions=[ 
          g.FunctionContext( 
            selection=[ 
              "SOURCE", 
              "count" 
            ], 
            function=g.Concat( 
              separator="|" 
            ), 
            projection=[ 
              "vertex|count" 
            ] 
          ) 
        ], 
        properties=[ 
          "vertex|count" 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[vertex|count=<java.lang.String>1|3]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "vertex|count" : "2|2"
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "vertex|count" : "2|1"
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "vertex|count" : "2|1"
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "vertex|count" : "1|3"
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get entities and exclude properties

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
final Concat concat = new Concat();
concat.setSeparator("|");
final GetElements operation = new GetElements.Builder()
        .input(new EntitySeed(2))
        .view(new View.Builder()
                .edge("edge", new ViewElementDefinition.Builder()
                        .transientProperty("vertex|count", String.class)
                        .excludeProperties("count")
                        .transformer(new ElementTransformer.Builder()
                                .select(IdentifierType.SOURCE.name(), "count")
                                .execute(concat)
                                .project("vertex|count")
                                .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "transientProperties" : {
          "vertex|count" : "String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "count" ],
          "function" : {
            "class" : "Concat",
            "separator" : "|"
          },
          "projection" : [ "vertex|count" ]
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
    "vertex" : 2
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "transientProperties" : {
          "vertex|count" : "java.lang.String"
        },
        "transformFunctions" : [ {
          "selection" : [ "SOURCE", "count" ],
          "function" : {
            "class" : "uk.gov.gchq.koryphe.impl.function.Concat",
            "separator" : "|"
          },
          "projection" : [ "vertex|count" ]
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
        group="edge", 
        transient_properties={'vertex|count': 'java.lang.String'}, 
        transform_functions=[ 
          g.FunctionContext( 
            selection=[ 
              "SOURCE", 
              "count" 
            ], 
            function=g.Concat( 
              separator="|" 
            ), 
            projection=[ 
              "vertex|count" 
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
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[vertex|count=<java.lang.String>1|3]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "vertex|count" : "2|2"
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "vertex|count" : "2|1"
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "vertex|count" : "2|1"
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "vertex|count" : "1|3"
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

