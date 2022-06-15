# ForEach
See javadoc - [uk.gov.gchq.gaffer.operation.impl.ForEach](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/ForEach.html)

Available since Gaffer version 1.7.0

Runs supplied operation on Iterable of inputs

## Required fields
No required fields


## Examples

### For each in chain example

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
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAdjacentIds.Builder()
                .input(new EntitySeed(1))
                .build())
        .then(new ForEach.Builder<>()
                .operation(new OperationChain.Builder()
                        .first(new ToSingletonList<EntitySeed>())
                        .then(new GetAdjacentIds())
                        .then(new ToVertices())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAdjacentIds",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 1
    } ]
  }, {
    "class" : "ForEach",
    "operation" : {
      "class" : "OperationChain",
      "operations" : [ {
        "class" : "ToSingletonList"
      }, {
        "class" : "GetAdjacentIds"
      }, {
        "class" : "ToVertices"
      } ]
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.ForEach",
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
      "operations" : [ {
        "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSingletonList"
      }, {
        "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds"
      }, {
        "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToVertices"
      } ]
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAdjacentIds( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ) 
      ] 
    ), 
    g.ForEach( 
      operation=g.OperationChain( 
        operations=[ 
          g.ToSingletonList(), 
          g.GetAdjacentIds(), 
          g.ToVertices() 
        ] 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ 3 --> 4 --> 5 --> 1 ]
[ 1 --> 2 --> 3 ]

{%- language name="JSON", type="json" -%}
[ [ 3, 4, 5, 1 ], [ 1, 2, 3 ] ]
{%- endcodetabs %}

-----------------------------------------------

