# MapToTuple
See javadoc - [uk.gov.gchq.koryphe.impl.function.MapToTuple](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/MapToTuple.html)

Available since Koryphe version 1.8.0

Converts a Map to a Tuple

## Examples

### Map to tuple


{% codetabs name="Java", type="java" -%}
final MapToTuple<String> function = new MapToTuple<>();

{%- language name="JSON", type="json" -%}
{
  "class" : "MapToTuple"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.MapToTuple"
}

{%- language name="Python", type="py" -%}
g.MapToTuple()

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{A=1, B=2, C=3}</td><td>uk.gov.gchq.koryphe.tuple.MapTuple</td><td>[1, 2, 3]</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{value2=1, value1=2}</td><td>uk.gov.gchq.koryphe.tuple.MapTuple</td><td>[1, 2]</td></tr>
</table>

-----------------------------------------------

