# Filter
See javadoc - [uk.gov.gchq.gaffer.operation.impl.function.Filter](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/function/Filter.html)

Available since Gaffer version 1.0.0

Filters elements

## Required fields
No required fields


## Examples

### All elements with a count more than 2

The filter will only return elements (Entities and Edges) with a count more than 2. The results show the Edge 1->4 that has a count of 1 has been removed.

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
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                .name("1-hop")
                .input(new EntitySeed(1))
                .build())
        .then(new Filter.Builder()
                .globalElements(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(2))
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "NamedOperation",
    "input" : [ {
      "class" : "EntitySeed",
      "class" : "EntitySeed",
      "vertex" : 1
    } ],
    "operationName" : "1-hop"
  }, {
    "class" : "Filter",
    "globalElements" : {
      "predicates" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : 2
        }
      } ]
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    } ],
    "operationName" : "1-hop"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.function.Filter",
    "globalElements" : {
      "predicates" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : false,
          "value" : 2
        }
      } ]
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.NamedOperation( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ) 
      ], 
      operation_name="1-hop" 
    ), 
    g.Filter( 
      global_elements=g.GlobalElementFilterDefinition( 
        predicates=[ 
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

### All edges of type edge with count more than 2

Similar to the previous example but this will only return Edges with group 'edge' that have a count more than 2.

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
final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
        .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                .name("1-hop")
                .input(new EntitySeed(1))
                .build())
        .then(new Filter.Builder()
                .edge("edge", new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(2))
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "NamedOperation",
    "input" : [ {
      "class" : "EntitySeed",
      "class" : "EntitySeed",
      "vertex" : 1
    } ],
    "operationName" : "1-hop"
  }, {
    "class" : "Filter",
    "edges" : {
      "edge" : {
        "predicates" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 2
          }
        } ]
      }
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    } ],
    "operationName" : "1-hop"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.function.Filter",
    "edges" : {
      "edge" : {
        "predicates" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 2
          }
        } ]
      }
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.NamedOperation( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ) 
      ], 
      operation_name="1-hop" 
    ), 
    g.Filter( 
      edges=[ 
        g.ElementFilterDefinition( 
          group="edge", 
          predicates=[ 
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
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
[ {
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

