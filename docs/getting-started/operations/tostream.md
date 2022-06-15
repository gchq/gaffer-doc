# ToStream
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToStream](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToStream.html)

Available since Gaffer version 1.0.0

Note - conversion into a Stream is done in memory, so it is not advised for a large number of results.

## Required fields
No required fields


## Examples

### To stream example

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
final OperationChain<Stream<? extends Element>> opChain = new Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToStream<>())
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
    "class" : "ToStream"
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
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToStream"
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
    g.ToStream() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
{
  "parallel" : false
}
{%- endcodetabs %}

-----------------------------------------------

