# DeserialiseJson
See javadoc - [uk.gov.gchq.koryphe.impl.function.DeserialiseJson](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/DeserialiseJson.html)

Available since Koryphe version 1.8.0

Parses a JSON string in java objects

## Examples

### Parse json


{% codetabs name="Java", type="java" -%}
final DeserialiseJson function = new DeserialiseJson();

{%- language name="JSON", type="json" -%}
{
  "class" : "DeserialiseJson"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.DeserialiseJson"
}

{%- language name="Python", type="py" -%}
g.DeserialiseJson()

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>{&quot;elements&quot;: [{&quot;value&quot;: &quot;value1&quot;}, {&quot;value&quot;: &quot;value2&quot;}]}</td><td>java.util.LinkedHashMap</td><td>{elements=[{value=value1}, {value=value2}]}</td></tr>
<tr><td>java.lang.String</td><td>[ &quot;ListValue1&quot;, &quot;ListValue2&quot;, &quot;ListValue3&quot; ]</td><td>java.util.ArrayList</td><td>[ListValue1, ListValue2, ListValue3]</td></tr>
<tr><td>java.lang.String</td><td>{ &quot;number&quot;:30 }</td><td>java.util.LinkedHashMap</td><td>{number=30}</td></tr>
<tr><td>java.lang.String</td><td>{ &quot;false&quot;:true }</td><td>java.util.LinkedHashMap</td><td>{false=true}</td></tr>
<tr><td>java.lang.String</td><td>{
  &quot;class&quot; : &quot;uk.gov.gchq.gaffer.operation.data.EntitySeed&quot;,
  &quot;vertex&quot; : 1
}</td><td>java.util.LinkedHashMap</td><td>{class=uk.gov.gchq.gaffer.operation.data.EntitySeed, vertex=1}</td></tr>
<tr><td>java.lang.String</td><td>[ &quot;listValue1&quot;, &quot;listValue1&quot;, &quot;listValue1&quot; ]</td><td>java.util.ArrayList</td><td>[listValue1, listValue1, listValue1]</td></tr>
<tr><td>java.lang.String</td><td>{
  &quot;key1&quot; : 1.0,
  &quot;key2&quot; : 2.2,
  &quot;key3&quot; : 3.3
}</td><td>java.util.LinkedHashMap</td><td>{key1=1.0, key2=2.2, key3=3.3}</td></tr>
</table>

-----------------------------------------------

