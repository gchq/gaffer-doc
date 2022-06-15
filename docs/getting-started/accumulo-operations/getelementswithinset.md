# GetElementsWithinSet
See javadoc - [uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsWithinSet](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/accumulostore/operation/impl/GetElementsWithinSet.html)

Available since Gaffer version 1.0.0

Gets edges with both vertices in a given set and entities with vertices in a given set

## Required fields
No required fields


## Examples

### Get elements within set of vertices 1 and 2 and 3

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
final GetElementsWithinSet operation = new GetElementsWithinSet.Builder()
        .input(new EntitySeed(1), new EntitySeed(2), new EntitySeed(3))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElementsWithinSet",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 1
  }, {
    "class" : "EntitySeed",
    "vertex" : 2
  }, {
    "class" : "EntitySeed",
    "vertex" : 3
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsWithinSet",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 3
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElementsWithinSet( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ), 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EntitySeed( 
      vertex=3 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]

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
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get elements within set of vertices 1 and 2 and 3 with count greater than 2

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
final GetElementsWithinSet operation = new GetElementsWithinSet.Builder()
        .input(new EntitySeed(1), new EntitySeed(2), new EntitySeed(3))
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
  "class" : "GetElementsWithinSet",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 1
  }, {
    "class" : "EntitySeed",
    "vertex" : 2
  }, {
    "class" : "EntitySeed",
    "vertex" : 3
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
  "class" : "uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsWithinSet",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 3
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
g.GetElementsWithinSet( 
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
    ), 
    g.EntitySeed( 
      vertex=2 
    ), 
    g.EntitySeed( 
      vertex=3 
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

