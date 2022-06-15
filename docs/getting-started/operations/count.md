# Count
See javadoc - [uk.gov.gchq.gaffer.operation.impl.Count](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/Count.html)

Available since Gaffer version 1.0.0

Counts the number of items in an iterable

## Required fields
No required fields


## Examples

### Count all elements

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
OperationChain<Long> countAllElements = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new Count<>())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "Count"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.Count"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.Count() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
11

{%- language name="JSON", type="json" -%}
11
{%- endcodetabs %}

-----------------------------------------------

