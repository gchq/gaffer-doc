# Aggregate
See javadoc - [uk.gov.gchq.gaffer.operation.impl.function.Aggregate](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/function/Aggregate.html)

Available since Gaffer version 1.0.0

The Aggregate operation would normally be used in an Operation Chain to aggregate the results of a previous operation.

## Required fields
No required fields


## Examples

### Simple aggregate elements example


{% codetabs name="Java", type="java" -%}
final Aggregate aggregate = new Aggregate();

{%- language name="JSON", type="json" -%}
{
  "class" : "Aggregate"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.function.Aggregate"
}

{%- language name="Python", type="py" -%}
g.Aggregate()

{%- endcodetabs %}

-----------------------------------------------

### Aggregate only edges of type edge with a transient property and provided aggregator

The groupBy has been set to an empty array. This will override the groupBy value in the schema.

{% codetabs name="Java", type="java" -%}
final String[] groupBy = {};
final Aggregate aggregate = new Aggregate.Builder()
        .edge("edge", new AggregatePair(
                groupBy,
                new ElementAggregator.Builder()
                        .select("transientProperty1")
                        .execute(new StringConcat())
                        .build()))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "Aggregate",
  "edges" : {
    "edge" : {
      "elementAggregator" : {
        "operators" : [ {
          "selection" : [ "transientProperty1" ],
          "binaryOperator" : {
            "class" : "StringConcat",
            "separator" : ","
          }
        } ]
      },
      "groupBy" : [ ]
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.function.Aggregate",
  "edges" : {
    "edge" : {
      "elementAggregator" : {
        "operators" : [ {
          "selection" : [ "transientProperty1" ],
          "binaryOperator" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat",
            "separator" : ","
          }
        } ]
      },
      "groupBy" : [ ]
    }
  }
}

{%- language name="Python", type="py" -%}
g.Aggregate( 
  edges=[ 
    g.AggregatePair( 
      group="edge", 
      group_by=[ 
      ], 
      element_aggregator=g.ElementAggregateDefinition( 
        operators=[ 
          g.BinaryOperatorContext( 
            selection=[ 
              "transientProperty1" 
            ], 
            binary_operator=g.BinaryOperator( 
              class_name="uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat", 
              fields={'separator': ','} 
            ) 
          ) 
        ] 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

