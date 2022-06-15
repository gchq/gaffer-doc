# StringTrim
See javadoc - [uk.gov.gchq.koryphe.impl.function.StringTrim](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/StringTrim.html)

Available since Koryphe version 1.9.0

Trims all whitespace around a string.

## Examples

### Trim strings


{% codetabs name="Java", type="java" -%}
final StringTrim function = new StringTrim();

{%- language name="JSON", type="json" -%}
{
  "class" : "StringTrim"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringTrim"
}

{%- language name="Python", type="py" -%}
g.StringTrim()

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>trailing spaces   </td><td>java.lang.String</td><td>trailing spaces</td></tr>
<tr><td>java.lang.String</td><td>    leading spaces</td><td>java.lang.String</td><td>leading spaces</td></tr>
<tr><td>java.lang.String</td><td>noSpaces</td><td>java.lang.String</td><td>noSpaces</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Long</td><td>54</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

