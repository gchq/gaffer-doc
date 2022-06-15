# IsIn
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsIn](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)

Available since Koryphe version 1.0.0

Checks if an input is in a set of allowed values

## Examples

### Is in set


{% codetabs name="Java", type="java" -%}
final IsIn function = new IsIn(5, 5L, "5", '5');

{%- language name="JSON", type="json" -%}
{
  "class" : "IsIn",
  "values" : [ 5, {
    "Long" : 5
  }, "5", {
    "Character" : "5"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsIn",
  "values" : [ 5, {
    "java.lang.Long" : 5
  }, "5", {
    "java.lang.Character" : "5"
  } ]
}

{%- language name="Python", type="py" -%}
g.IsIn( 
  values=[ 
    5, 
    {'java.lang.Long': 5}, 
    "5", 
    {'java.lang.Character': '5'} 
  ] 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------

