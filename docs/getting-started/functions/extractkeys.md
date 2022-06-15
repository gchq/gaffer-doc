# ExtractKeys
See javadoc - [uk.gov.gchq.koryphe.impl.function.ExtractKeys](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ExtractKeys.html)

Available since Koryphe version 1.1.0

An ExtractKeys will return the Set of keys from the provided Java Map.

## Examples

### Extract keys from map


{% codetabs name="Java", type="java" -%}
final ExtractKeys<String, Integer> function = new ExtractKeys<>();

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractKeys"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ExtractKeys"
}

{%- language name="Python", type="py" -%}
g.ExtractKeys()

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{firstKey=2, thirdKey=9, secondKey=4}</td><td>java.util.HashMap$KeySet</td><td>[firstKey, thirdKey, secondKey]</td></tr>
</table>

-----------------------------------------------

