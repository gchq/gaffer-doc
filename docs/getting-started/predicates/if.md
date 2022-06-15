# If
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.If](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/If.html)

Available since Koryphe version 1.3.0

Conditionally applies a predicate

## Examples

### Apply predicates to input

This example tests first whether the input is an Integer. If so, it is then tested to see if the value is greater than 3. Otherwise, since it is not an Integer, we then test to see if it is NOT a String.


{% codetabs name="Java", type="java" -%}
final If<Comparable> predicate = new If<>(new IsA(Integer.class), new IsMoreThan(3), new Not<>(new IsA(String.class)));

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.If",
  "predicate" : {
    "class" : "IsA",
    "type" : "java.lang.Integer"
  },
  "then" : {
    "class" : "IsMoreThan",
    "orEqualTo" : false,
    "value" : 3
  },
  "otherwise" : {
    "class" : "Not",
    "predicate" : {
      "class" : "IsA",
      "type" : "java.lang.String"
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.If",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsA",
    "type" : "java.lang.Integer"
  },
  "then" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 3
  },
  "otherwise" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.Not",
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsA",
      "type" : "java.lang.String"
    }
  }
}

{%- language name="Python", type="py" -%}
g.If( 
  predicate=g.IsA( 
    type="java.lang.Integer" 
  ), 
  then=g.IsMoreThan( 
    value=3, 
    or_equal_to=False 
  ), 
  otherwise=g.Not( 
    predicate=g.IsA( 
      type="java.lang.String" 
    ) 
  ) 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>test</td><td>false</td></tr>
<tr><td>java.util.HashMap</td><td>{}</td><td>true</td></tr>
<tr><td>java.util.ArrayList</td><td>[]</td><td>true</td></tr>
</table>

-----------------------------------------------

