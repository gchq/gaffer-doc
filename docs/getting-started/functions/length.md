# Length
See javadoc - [uk.gov.gchq.koryphe.impl.function.Length](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Length.html)

Available since Koryphe version 1.3.0

Attempts to returns the length of an object

## Examples

### Get length


{% codetabs name="Java", type="java" -%}
final Length function = new Length();

{%- language name="JSON", type="json" -%}
{
  "class" : "Length"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Length"
}

{%- language name="Python", type="py" -%}
g.Length()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[Entity[vertex=1,group=entity,properties=Properties[]], Entity[vertex=2,group=entity,properties=Properties[]], Entity[vertex=3,group=entity,properties=Properties[]], Entity[vertex=4,group=entity,properties=Properties[]], Entity[vertex=5,group=entity,properties=Properties[]]]</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.util.HashMap</td><td>{option3=value3, option1=value1, option2=value2}</td><td>java.lang.Integer</td><td>3</td></tr>
<tr><td>uk.gov.gchq.gaffer.data.graph.Walk</td><td>[[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=A,destination=E,directed=true,group=BasicEdge,properties=Properties[]]]]</td><td>java.lang.Integer</td><td>4</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td></td><td>IllegalArgumentException: Could not determine the size of the provided value</td></tr>
<tr><td>java.lang.String</td><td>some string</td><td>java.lang.Integer</td><td>11</td></tr>
<tr><td>[Ljava.lang.String;</td><td>[1, 2]</td><td>java.lang.Integer</td><td>2</td></tr>
<tr><td></td><td>null</td><td>java.lang.Integer</td><td>0</td></tr>
</table>

-----------------------------------------------

