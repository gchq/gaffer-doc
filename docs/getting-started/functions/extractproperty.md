# ExtractProperty
See javadoc - [uk.gov.gchq.gaffer.data.element.function.ExtractProperty](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/function/ExtractProperty.html)

Available since Gaffer version 1.4.0

Extracts a property from an element

## Examples

### Extract property from element

If present, the function will extract the value of the specified property, otherwise returning null.


{% codetabs name="Java", type="java" -%}
final ExtractProperty function = new ExtractProperty("prop1");

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractProperty",
  "name" : "prop1"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.function.ExtractProperty",
  "name" : "prop1"
}

{%- language name="Python", type="py" -%}
g.ExtractProperty( 
  name="prop1" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.data.element.Element
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Edge</td><td>Edge[source=dest,destination=src,directed=false,group=edge,properties=Properties[prop2=&lt;java.lang.String&gt;test,prop1=&lt;java.lang.Integer&gt;3]]</td><td>java.lang.Integer</td><td>3</td></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Entity</td><td>Entity[vertex=vertex,group=entity,properties=Properties[prop2=&lt;java.lang.Integer&gt;2,prop1=&lt;java.lang.Integer&gt;12,prop3=&lt;java.lang.String&gt;test]]</td><td>java.lang.Integer</td><td>12</td></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Edge</td><td>Edge[directed=false,group=UNKNOWN,properties=Properties[]]</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

