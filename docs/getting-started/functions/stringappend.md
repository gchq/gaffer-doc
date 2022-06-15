# StringAppend
See javadoc - [uk.gov.gchq.koryphe.impl.function.StringAppend](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/StringAppend.html)

Available since Koryphe version 1.9.0

Appends a provided suffix to a string.

## Examples

### Prepend to strings


{% codetabs name="Java", type="java" -%}
final StringAppend function = new StringAppend("mySuffix");

{%- language name="JSON", type="json" -%}
{
  "class" : "StringAppend",
  "suffix" : "mySuffix"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringAppend",
  "suffix" : "mySuffix"
}

{%- language name="Python", type="py" -%}
g.StringAppend( 
  suffix="mySuffix" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a string of some kind</td><td>java.lang.String</td><td>a string of some kindmySuffix</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.String</td><td>mySuffix</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Long</td><td>54</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

