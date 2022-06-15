# ExtractValues
See javadoc - [uk.gov.gchq.koryphe.impl.function.ExtractValues](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ExtractValues.html)

Available since Koryphe version 1.1.0

An ExtractValues will return a Collection of the values from a provided Java Map.

## Examples

### Extract values from map


{% codetabs name="Java", type="java" -%}
final ExtractValues<String, Integer> function = new ExtractValues<>();

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractValues"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ExtractValues"
}

{%- language name="Python", type="py" -%}
g.ExtractValues()

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{theKey=1, theWholeKey=2, nothingButTheKey=3}</td><td>java.util.HashMap$Values</td><td>[1, 2, 3]</td></tr>
</table>

-----------------------------------------------

