# GetAdjacentIds
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAdjacentIds.html)

Available since Gaffer version 1.0.0

Performs a single hop down related edges

## Required fields
No required fields


## Examples

### Get adjacent ids from vertex 2

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
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAdjacentIds",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  } ]
}

{%- language name="Python", type="py" -%}
g.GetAdjacentIds( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=3]
EntitySeed[vertex=4]
EntitySeed[vertex=5]
EntitySeed[vertex=1]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 3
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 4
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 5
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 1
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get adjacent ids along outbound edges from vertex 2

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
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAdjacentIds",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
  } ],
  "includeIncomingOutGoing" : "OUTGOING"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
  } ],
  "includeIncomingOutGoing" : "OUTGOING"
}

{%- language name="Python", type="py" -%}
g.GetAdjacentIds( 
  input=[ 
    g.EntitySeed( 
      vertex=2 
    ) 
  ], 
  include_incoming_out_going="OUTGOING" 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=3]
EntitySeed[vertex=4]
EntitySeed[vertex=5]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 3
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 4
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 5
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get adjacent ids along outbound edges from vertex 2 with count greater than 1

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
final GetAdjacentIds operation = new GetAdjacentIds.Builder()
        .input(new EntitySeed(2))
        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
        .view(new View.Builder()
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
  "class" : "GetAdjacentIds",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 2
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
    }
  },
  "includeIncomingOutGoing" : "OUTGOING"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 2
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
    }
  },
  "includeIncomingOutGoing" : "OUTGOING"
}

{%- language name="Python", type="py" -%}
g.GetAdjacentIds( 
  view=g.View( 
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
    ) 
  ], 
  include_incoming_out_going="OUTGOING" 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=3]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 3
} ]
{%- endcodetabs %}

-----------------------------------------------

