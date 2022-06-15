# ReverseString
See javadoc - [uk.gov.gchq.koryphe.impl.function.ReverseString](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ReverseString.html)

Available since Koryphe version 1.9.0

Reverse characters in string

## Examples

### Reverse strings


{% codetabs name="Java", type="java" -%}
final ReverseString function = new ReverseString();

{%- language name="JSON", type="json" -%}
{
  "class" : "ReverseString"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ReverseString"
}

{%- language name="Python", type="py" -%}
g.ReverseString()

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>reverse</td><td>java.lang.String</td><td>esrever</td></tr>
<tr><td>java.lang.String</td><td>esrever</td><td>java.lang.String</td><td>reverse</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Long</td><td>54</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

