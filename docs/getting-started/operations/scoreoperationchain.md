# ScoreOperationChain
See javadoc - [uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/ScoreOperationChain.html)

Available since Gaffer version 1.0.0

Note - ScoreOperationChain is not a core operation.

## Required fields
No required fields


## Examples

### Score operation chain

This demonstrates a simple example for constructing a ScoreOperationChain, with Operations and a NamedOperation.

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
final ScoreOperationChain scoreOpChain = new ScoreOperationChain.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetElements())
                .then(new NamedOperation.Builder<Element, Iterable<? extends Element>>()
                        .name("namedOp")
                        .build())
                .then(new Limit<>(3))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "ScoreOperationChain",
  "operationChain" : {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements"
    }, {
      "class" : "NamedOperation",
      "operationName" : "namedOp"
    }, {
      "class" : "Limit",
      "resultLimit" : 3,
      "truncate" : true
    } ]
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain",
  "operationChain" : {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements"
    }, {
      "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
      "operationName" : "namedOp"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
      "resultLimit" : 3,
      "truncate" : true
    } ]
  }
}

{%- language name="Python", type="py" -%}
g.ScoreOperationChain( 
  operation_chain=g.OperationChain( 
    operations=[ 
      g.GetElements(), 
      g.NamedOperation( 
        operation_name="namedOp" 
      ), 
      g.Limit( 
        result_limit=3, 
        truncate=True 
      ) 
    ] 
  ) 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
5

{%- language name="JSON", type="json" -%}
5
{%- endcodetabs %}

-----------------------------------------------

### Score operation chain with custom named score

Here we have added a NamedOperation to the NamedOperationCache, with a custom score of 3. In our ScoreOperationChainDeclaration.json file, we have also declared that this should be resolved with a NamedOperationScoreResolver. With Limit declared as having a score of 2, the above chain correctly has a score of 5.

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
final ScoreOperationChain scoreOperationChain = new ScoreOperationChain.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                        .name("1-hop")
                        .input(new EntitySeed(1))
                        .build())
                .then(new Limit<>(3))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "ScoreOperationChain",
  "operationChain" : {
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
      "class" : "Limit",
      "resultLimit" : 3,
      "truncate" : true
    } ]
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain",
  "operationChain" : {
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
      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
      "resultLimit" : 3,
      "truncate" : true
    } ]
  }
}

{%- language name="Python", type="py" -%}
g.ScoreOperationChain( 
  operation_chain=g.OperationChain( 
    operations=[ 
      g.NamedOperation( 
        input=[ 
          g.EntitySeed( 
            vertex=1 
          ) 
        ], 
        operation_name="1-hop" 
      ), 
      g.Limit( 
        result_limit=3, 
        truncate=True 
      ) 
    ] 
  ) 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
5

{%- language name="JSON", type="json" -%}
5
{%- endcodetabs %}

-----------------------------------------------

