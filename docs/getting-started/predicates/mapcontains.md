# MapContains
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.MapContains](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContains.html)

Available since Koryphe version 1.0.0

Checks if a map contains a given key

## Examples

### Map contains


{% codetabs name="Java", type="java" -%}
final MapContains function = new MapContains("a");

{%- language name="JSON", type="json" -%}
{
  "class" : "MapContains",
  "key" : "a"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.MapContains",
  "key" : "a"
}

{%- language name="Python", type="py" -%}
g.MapContains( 
  key="a" 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{a=1, b=2, c=3}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{b=2, c=3}</td><td>false</td></tr>
<tr><td>java.util.HashMap</td><td>{a=null, b=2, c=3}</td><td>true</td></tr>
</table>

-----------------------------------------------

