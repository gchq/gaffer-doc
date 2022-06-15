# MultiRegex
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.MultiRegex](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MultiRegex.html)

Available since Koryphe version 1.0.0

Checks if a string matches at least one pattern

## Examples

### Multi regex with pattern


{% codetabs name="Java", type="java" -%}
final MultiRegex function = new MultiRegex(new Pattern[]{Pattern.compile("[a-d]"), Pattern.compile("[0-4]")});

{%- language name="JSON", type="json" -%}
{
  "class" : "MultiRegex",
  "value" : [ {
    "java.util.regex.Pattern" : "[a-d]"
  }, {
    "java.util.regex.Pattern" : "[0-4]"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.MultiRegex",
  "value" : [ {
    "java.util.regex.Pattern" : "[a-d]"
  }, {
    "java.util.regex.Pattern" : "[0-4]"
  } ]
}

{%- language name="Python", type="py" -%}
g.MultiRegex( 
  value=[ 
    {'java.util.regex.Pattern': '[a-d]'}, 
    {'java.util.regex.Pattern': '[0-4]'} 
  ] 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>z</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>az</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>a</td><td>ClassCastException: java.lang.Character cannot be cast to java.lang.String</td></tr>
<tr><td>java.lang.String</td><td>2</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>ClassCastException: java.lang.Integer cannot be cast to java.lang.String</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

