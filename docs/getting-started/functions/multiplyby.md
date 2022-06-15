# MultiplyBy
See javadoc - [uk.gov.gchq.koryphe.impl.function.MultiplyBy](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/MultiplyBy.html)

Available since Koryphe version 1.0.0

Multiply the input integer by the provide number.

## Examples

### Multiply by 2


{% codetabs name="Java", type="java" -%}
final MultiplyBy function = new MultiplyBy(2);

{%- language name="JSON", type="json" -%}
{
  "class" : "MultiplyBy",
  "by" : 2
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.MultiplyBy",
  "by" : 2
}

{%- language name="Python", type="py" -%}
g.MultiplyBy( 
  by=2 
)

{%- endcodetabs %}

Input type:

```
java.lang.Integer
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>6</td><td>java.lang.Integer</td><td>12</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>java.lang.Integer</td><td>10</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Double</td><td>6.1</td><td></td><td>ClassCastException: java.lang.Double cannot be cast to java.lang.Integer</td></tr>
</table>

-----------------------------------------------

