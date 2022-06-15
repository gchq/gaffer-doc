# Transform
See javadoc - [uk.gov.gchq.gaffer.operation.impl.function.Transform](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/function/Transform.html)

Available since Gaffer version 1.0.0

The Transform operation would normally be used in an Operation Chain to transform the results of a previous operation.

## Required fields
No required fields


## Examples

### Transform a count property into a count string property only for edges of type edge


{% codetabs name="Java", type="java" -%}
final Transform transform = new Transform.Builder()
        .edge("edge", new ElementTransformer.Builder()
                .select("count")
                .execute(new ToString())
                .project("countString")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "Transform",
  "edges" : {
    "edge" : {
      "functions" : [ {
        "selection" : [ "count" ],
        "function" : {
          "class" : "ToString"
        },
        "projection" : [ "countString" ]
      } ]
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.function.Transform",
  "edges" : {
    "edge" : {
      "functions" : [ {
        "selection" : [ "count" ],
        "function" : {
          "class" : "uk.gov.gchq.koryphe.impl.function.ToString"
        },
        "projection" : [ "countString" ]
      } ]
    }
  }
}

{%- language name="Python", type="py" -%}
g.Transform( 
  edges=[ 
    g.ElementTransformDefinition( 
      group="edge", 
      functions=[ 
        g.FunctionContext( 
          selection=[ 
            "count" 
          ], 
          function=g.ToString(), 
          projection=[ 
            "countString" 
          ] 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

