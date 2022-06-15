# FunctionMap
See javadoc - [uk.gov.gchq.koryphe.function.FunctionMap](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/function/FunctionMap.html)

Available since Koryphe version 1.0.0

Applies a function to all values in a map.

## Examples

### Multiply all map values by 10


{% codetabs name="Java", type="java" -%}
final FunctionMap<String, Integer, Integer> function = new FunctionMap<>(new MultiplyBy(10));

{%- language name="JSON", type="json" -%}
{
  "class" : "FunctionMap",
  "function" : {
    "class" : "MultiplyBy",
    "by" : 10
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.function.FunctionMap",
  "function" : {
    "class" : "uk.gov.gchq.koryphe.impl.function.MultiplyBy",
    "by" : 10
  }
}

{%- language name="Python", type="py" -%}
g.FunctionMap( 
  function=g.MultiplyBy( 
    by=10 
  ) 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{key1=1, key2=2, key3=3}</td><td>java.util.HashMap</td><td>{key1=10, key2=20, key3=30}</td></tr>
<tr><td>java.util.HashMap</td><td>{key1=null, key2=2, key3=3}</td><td>java.util.HashMap</td><td>{key1=null, key2=20, key3=30}</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

