# AreIn
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.AreIn](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/AreIn.html)

Available since Koryphe version 1.0.0

Checks if a provided collection contains all the provided input values

## Examples

### Are in set


{% codetabs name="Java", type="java" -%}
final AreIn function = new AreIn(1, 2, 3);

{%- language name="JSON", type="json" -%}
{
  "class" : "AreIn",
  "values" : [ 1, 2, 3 ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.AreIn",
  "values" : [ 1, 2, 3 ]
}

{%- language name="Python", type="py" -%}
g.AreIn( 
  values=[ 
    1, 
    2, 
    3 
  ] 
)

{%- endcodetabs %}

Input type:

```
java.util.Collection
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashSet</td><td>[1, 2, 3]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[1, 2, 3, 4]</td><td>false</td></tr>
<tr><td>java.util.HashSet</td><td>[4, 1]</td><td>false</td></tr>
<tr><td>java.util.HashSet</td><td>[1, 2]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[]</td><td>true</td></tr>
</table>

-----------------------------------------------

