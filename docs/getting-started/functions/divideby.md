# DivideBy
See javadoc - [uk.gov.gchq.koryphe.impl.function.DivideBy](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/DivideBy.html)

Available since Koryphe version 1.0.0

Divide the input integer by the provide number. x -> [x/divideBy, remainder]

## Examples

### Divide by 2


{% codetabs name="Java", type="java" -%}
final DivideBy function = new DivideBy(2);

{%- language name="JSON", type="json" -%}
{
  "class" : "DivideBy",
  "by" : 2
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.DivideBy",
  "by" : 2
}

{%- language name="Python", type="py" -%}
g.DivideBy( 
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
<tr><td>java.lang.Integer</td><td>6</td><td>[java.lang.Integer, java.lang.Integer]</td><td>[3, 0]</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>[java.lang.Integer, java.lang.Integer]</td><td>[2, 1]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Double</td><td>6.1</td><td></td><td>ClassCastException: java.lang.Double cannot be cast to java.lang.Integer</td></tr>
</table>

-----------------------------------------------

