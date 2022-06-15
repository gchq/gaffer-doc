# ToCsv
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToCsv](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToCsv.html)

Available since Gaffer version 1.0.0

Converts elements to CSV Strings

## Required fields
The following fields are required: 
- elementGenerator


## Examples

### To csv example

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
final OperationChain<Iterable<? extends String>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToCsv.Builder()
                .includeHeader(true)
                .generator(new CsvGenerator.Builder()
                        .group("Edge group")
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
    "class" : "ToCsv",
    "elementGenerator" : {
      "class" : "CsvGenerator",
      "fields" : {
        "GROUP" : "Edge group",
        "VERTEX" : "vertex",
        "SOURCE" : "source",
        "count" : "total count"
      },
      "constants" : { },
      "quoted" : false,
      "commaReplacement" : " "
    },
    "includeHeader" : true
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToCsv",
    "elementGenerator" : {
      "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
      "fields" : {
        "GROUP" : "Edge group",
        "VERTEX" : "vertex",
        "SOURCE" : "source",
        "count" : "total count"
      },
      "constants" : { },
      "quoted" : false,
      "commaReplacement" : " "
    },
    "includeHeader" : true
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
    g.ToCsv( 
      element_generator=g.CsvGenerator( 
        fields={'GROUP': 'Edge group', 'VERTEX': 'vertex', 'SOURCE': 'source', 'count': 'total count'}, 
        constants={}, 
        quoted=False, 
        comma_replacement=" " 
      ), 
      include_header=True 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge group,vertex,source,total count
entity,2,,1
edge,,2,2
edge,,2,1
edge,,2,1
edge,,1,3
entity,1,,3
edge,,1,3
edge,,1,1

{%- language name="JSON", type="json" -%}
[ "Edge group,vertex,source,total count", "entity,2,,1", "edge,,2,2", "edge,,2,1", "edge,,2,1", "edge,,1,3", "entity,1,,3", "edge,,1,3", "edge,,1,1" ]
{%- endcodetabs %}

-----------------------------------------------

