# ToString
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToString](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToString.html)

Available since Koryphe version 1.0.0

toString is simply called on each input. If the input is null, null is returned.

## Examples

### Object to string


{% codetabs name="Java", type="java" -%}
final ToString function = new ToString();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToString"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToString"
}

{%- language name="Python", type="py" -%}
g.ToString()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>java.lang.String</td><td>1</td></tr>
<tr><td>java.lang.Double</td><td>2.5</td><td>java.lang.String</td><td>2.5</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>java.lang.String</td><td>abc</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

