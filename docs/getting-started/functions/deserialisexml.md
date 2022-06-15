# DeserialiseXml
See javadoc - [uk.gov.gchq.koryphe.impl.function.DeserialiseXml](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/DeserialiseXml.html)

Available since Koryphe version 1.8.0

Parses an XML document into multiple Maps

## Examples

### Parse xml


{% codetabs name="Java", type="java" -%}
final DeserialiseXml function = new DeserialiseXml();

{%- language name="JSON", type="json" -%}
{
  "class" : "DeserialiseXml"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.DeserialiseXml"
}

{%- language name="Python", type="py" -%}
g.DeserialiseXml()

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>&lt;element1&gt;value&lt;/element1&gt;</td><td>java.util.HashMap</td><td>{element1=value}</td></tr>
<tr><td>java.lang.String</td><td>&lt;root&gt;&lt;element1&gt;value1&lt;/element1&gt;&lt;element2&gt;value2&lt;/element2&gt;&lt;/root&gt;</td><td>java.util.HashMap</td><td>{root={element1=value1, element2=value2}}</td></tr>
<tr><td>java.lang.String</td><td>&lt;root&gt;&lt;element1&gt;&lt;element2&gt;value1&lt;/element2&gt;&lt;/element1&gt;&lt;element1&gt;&lt;element2&gt;value2&lt;/element2&gt;&lt;/element1&gt;&lt;/root&gt;</td><td>java.util.HashMap</td><td>{root={element1=[{element2=value1}, {element2=value2}]}}</td></tr>
</table>

-----------------------------------------------

