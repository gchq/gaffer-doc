# ExtractValue
See javadoc - [uk.gov.gchq.koryphe.impl.function.ExtractValue](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ExtractValue.html)

Available since Koryphe version 1.2.0

An ExtractValue will return the value associated with the pre-configured key, from a supplied Java Map.

## Examples

### Extract value from map


{% codetabs name="Java", type="java" -%}
final ExtractValue<String, Integer> function = new ExtractValue<>("blueKey");

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractValue",
  "key" : "blueKey"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ExtractValue",
  "key" : "blueKey"
}

{%- language name="Python", type="py" -%}
g.ExtractValue( 
  key="blueKey" 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{yellowKey=4, greenKey=9, redKey=5, blueKey=25}</td><td>java.lang.Integer</td><td>25</td></tr>
</table>

-----------------------------------------------

