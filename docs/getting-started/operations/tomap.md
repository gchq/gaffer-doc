# ToMap
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToMap](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToMap.html)

Available since Gaffer version 1.0.0

Converts elements to a Map of key-value pairs

## Required fields
The following fields are required: 
- elementGenerator


## Examples

### To map example

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
final OperationChain<Iterable<? extends Map<String, Object>>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToMap.Builder()
                .generator(new MapGenerator.Builder()
                        .group("group")
                        .vertex("vertex")
                        .source("source")
                        .property("count", "total count")
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
    "class" : "ToMap",
    "elementGenerator" : {
      "class" : "MapGenerator",
      "fields" : {
        "GROUP" : "group",
        "VERTEX" : "vertex",
        "SOURCE" : "source",
        "count" : "total count"
      },
      "constants" : { }
    }
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToMap",
    "elementGenerator" : {
      "class" : "uk.gov.gchq.gaffer.data.generator.MapGenerator",
      "fields" : {
        "GROUP" : "group",
        "VERTEX" : "vertex",
        "SOURCE" : "source",
        "count" : "total count"
      },
      "constants" : { }
    }
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
    g.ToMap( 
      element_generator=g.MapGenerator( 
        fields={'GROUP': 'group', 'VERTEX': 'vertex', 'SOURCE': 'source', 'count': 'total count'}, 
        constants={} 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
{group=entity, vertex=2, total count=1}
{group=edge, source=2, total count=2}
{group=edge, source=2, total count=1}
{group=edge, source=2, total count=1}
{group=edge, source=1, total count=3}
{group=entity, vertex=1, total count=3}
{group=edge, source=1, total count=3}
{group=edge, source=1, total count=1}

{%- language name="JSON", type="json" -%}
[ {
  "group" : "entity",
  "vertex" : 2,
  "total count" : 1
}, {
  "group" : "edge",
  "source" : 2,
  "total count" : 2
}, {
  "group" : "edge",
  "source" : 2,
  "total count" : 1
}, {
  "group" : "edge",
  "source" : 2,
  "total count" : 1
}, {
  "group" : "edge",
  "source" : 1,
  "total count" : 3
}, {
  "group" : "entity",
  "vertex" : 1,
  "total count" : 3
}, {
  "group" : "edge",
  "source" : 1,
  "total count" : 3
}, {
  "group" : "edge",
  "source" : 1,
  "total count" : 1
} ]
{%- endcodetabs %}

-----------------------------------------------

