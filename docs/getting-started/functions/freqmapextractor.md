# FreqMapExtractor
See javadoc - [uk.gov.gchq.gaffer.types.function.FreqMapExtractor](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/types/function/FreqMapExtractor.html)

Available since Gaffer version 1.0.0

Extracts a count from a frequency map for a given key.

## Examples

### Multiply all map values by 10


{% codetabs name="Java", type="java" -%}
final FreqMapExtractor function = new FreqMapExtractor("key1");

{%- language name="JSON", type="json" -%}
{
  "class" : "FreqMapExtractor",
  "key" : "key1"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.types.function.FreqMapExtractor",
  "key" : "key1"
}

{%- language name="Python", type="py" -%}
g.FreqMapExtractor( 
  key="key1" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.types.FreqMap
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=1, key2=2, key3=3}</td><td>java.lang.Long</td><td>1</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key2=2, key3=3}</td><td></td><td>null</td></tr>
<tr><td>java.util.HashMap</td><td>{key1=1, key2=2, key3=3}</td><td></td><td>ClassCastException: java.util.HashMap cannot be cast to uk.gov.gchq.gaffer.types.FreqMap</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

