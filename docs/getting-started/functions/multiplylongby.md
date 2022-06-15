# MultiplyLongBy
See javadoc - [uk.gov.gchq.koryphe.impl.function.MultiplyLongBy](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/MultiplyLongBy.html)

Available since Koryphe version 1.9.0

Multiply the input Long by the provide number.

## Examples

### Multiply long by 2


{% codetabs name="Java", type="java" -%}
final MultiplyLongBy function = new MultiplyLongBy(2L);

{%- language name="JSON", type="json" -%}
{
  "class" : "MultiplyLongBy",
  "by" : 2
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.MultiplyLongBy",
  "by" : 2
}

{%- language name="Python", type="py" -%}
g.MultiplyLongBy( 
  by=2 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>6</td><td>java.lang.Long</td><td>12</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>java.lang.Long</td><td>10</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Double</td><td>6.1</td><td></td><td>ClassCastException: java.lang.Double cannot be cast to java.lang.Long</td></tr>
</table>

-----------------------------------------------

