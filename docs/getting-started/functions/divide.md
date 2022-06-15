# Divide
See javadoc - [uk.gov.gchq.koryphe.impl.function.Divide](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Divide.html)

Available since Koryphe version 1.0.0

The input integers are divided. [x, y] -> [x/y, remainder]

## Examples

### Divide inputs


{% codetabs name="Java", type="java" -%}
final Divide function = new Divide();

{%- language name="JSON", type="json" -%}
{
  "class" : "Divide"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Divide"
}

{%- language name="Python", type="py" -%}
g.Divide()

{%- endcodetabs %}

Input type:

```
java.lang.Integer, java.lang.Integer
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 2]</td><td>[java.lang.Integer, java.lang.Integer]</td><td>[3, 0]</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 4]</td><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 8]</td><td>[java.lang.Integer, java.lang.Integer]</td><td>[0, 6]</td></tr>
<tr><td>[ ,java.lang.Integer]</td><td>[null, 2]</td><td></td><td>null</td></tr>
<tr><td>[java.lang.Integer, ]</td><td>[6, null]</td><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 0]</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[6.1, 2.1]</td><td></td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
</table>

-----------------------------------------------

