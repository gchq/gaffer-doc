# StringPrepend
See javadoc - [uk.gov.gchq.koryphe.impl.function.StringPrepend](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/StringPrepend.html)

Available since Koryphe version 1.9.0

Prepends a string with the provided prefix.

## Examples

### Prepend to strings


{% codetabs name="Java", type="java" -%}
final StringPrepend function = new StringPrepend("myPrefix");

{%- language name="JSON", type="json" -%}
{
  "class" : "StringPrepend",
  "prefix" : "myPrefix"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringPrepend",
  "prefix" : "myPrefix"
}

{%- language name="Python", type="py" -%}
g.StringPrepend( 
  prefix="myPrefix" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a string of some kind</td><td>java.lang.String</td><td>myPrefixa string of some kind</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.String</td><td>myPrefix</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Long</td><td>54</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

