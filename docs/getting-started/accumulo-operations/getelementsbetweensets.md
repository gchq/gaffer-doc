# GetElementsBetweenSets
See javadoc - [uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsBetweenSets](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/accumulostore/operation/impl/GetElementsBetweenSets.html)

Available since Gaffer version 1.0.0

Gets edges that exist between 2 sets and entities in the first set

## Required fields
No required fields


## Examples

### Get elements within set of vertices 1 and 2 and 4

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
final GetElementsBetweenSets operation = new GetElementsBetweenSets.Builder()
        .input(new EntitySeed(1))
        .inputB(new EntitySeed(2), new EntitySeed(4))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElementsBetweenSets",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 1
  } ],
  "inputB" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  }, {
    "class" : "EntitySeed",
    "vertex" : 4
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsBetweenSets",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ],
  "inputB" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 4
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElementsBetweenSets( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  input_b=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EntitySeed( 
      vertex=4 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]

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
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get elements within set of vertices 1 and 2 and 4 with count greater than 2

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
final GetElementsBetweenSets operation = new GetElementsBetweenSets.Builder()
        .input(new EntitySeed(1))
        .inputB(new EntitySeed(2), new EntitySeed(4))
        .view(new View.Builder()
                .entity("entity", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .edge("edge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElementsBetweenSets",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 1
  } ],
  "inputB" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  }, {
    "class" : "EntitySeed",
    "vertex" : 4
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 2
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
            "value" : 2
          }
        } ]
      }
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsBetweenSets",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ],
  "inputB" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 4
  } ],
  "view" : {
    "edges" : {
      "edge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 2
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
            "value" : 2
          }
        } ]
      }
    }
  }
}

{%- language name="Python", type="py" -%}
g.GetElementsBetweenSets( 
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
              value=2, 
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
              value=2, 
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
      vertex=1 
    ) 
  ], 
  input_b=[ 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EntitySeed( 
      vertex=4 
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

