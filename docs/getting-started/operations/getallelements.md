# GetAllElements
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetAllElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAllElements.html)

Available since Gaffer version 1.0.0

Gets all elements compatible with a provided View

## Required fields
No required fields


## Examples

### Get all elements

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
final GetAllElements operation = new GetAllElements();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllElements"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
}

{%- language name="Python", type="py" -%}
g.GetAllElements()

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]

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
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
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
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 4
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 4,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 5,
  "properties" : {
    "count" : 3
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get all elements with count greater than 2

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
final GetAllElements operation = new GetAllElements.Builder()
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
  "class" : "GetAllElements",
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
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
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
g.GetAllElements( 
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
  ) 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]

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
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 4
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 5,
  "properties" : {
    "count" : 3
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

