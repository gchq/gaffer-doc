# Multiply
See javadoc - [uk.gov.gchq.koryphe.impl.function.Multiply](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Multiply.html)

Available since Koryphe version 1.0.0

The input integers are multiplied together.

## Examples

### Object multiply


{% codetabs name="Java", type="java" -%}
final Multiply function = new Multiply();

{%- language name="JSON", type="json" -%}
{
  "class" : "Multiply"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Multiply"
}

{%- language name="Python", type="py" -%}
g.Multiply()

{%- endcodetabs %}

Input type:

```
java.lang.Integer, java.lang.Integer
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[2, 3]</td><td>java.lang.Integer</td><td>6</td></tr>
<tr><td>[ ,java.lang.Integer]</td><td>[null, 3]</td><td></td><td>null</td></tr>
<tr><td>[java.lang.Integer, ]</td><td>[2, null]</td><td>java.lang.Integer</td><td>2</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[2.1, 3.1]</td><td></td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
</table>

-----------------------------------------------

