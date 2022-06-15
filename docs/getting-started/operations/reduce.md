# Reduce
See javadoc - [uk.gov.gchq.gaffer.operation.impl.Reduce](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/Reduce.html)

Available since Gaffer version 1.7.0

Reduces an input to an output with a single value using provided function

## Required fields
The following fields are required: 
- aggregateFunction


## Examples

### Reduce example

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
                        .then(new Reduce.Builder<>()
                                .aggregateFunction(new Sum())
                                .build())
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
      }, {
        "class" : "Reduce",
        "aggregateFunction" : {
          "class" : "Sum"
        }
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
      }, {
        "class" : "uk.gov.gchq.gaffer.operation.impl.Reduce",
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
        }
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
          g.ToVertices(), 
          g.Reduce( 
            aggregate_function=g.Sum() 
          ) 
        ] 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
13
6

{%- language name="JSON", type="json" -%}
[ 13, 6 ]
{%- endcodetabs %}

-----------------------------------------------

