# ExtractWalkVertex
See javadoc - [uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkVertex](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkVertex.html)

Available since Gaffer version 1.3.0

Extracts the source vertex from a Walk

## Examples

### Extract vertex from walk


{% codetabs name="Java", type="java" -%}
final ExtractWalkVertex function = new ExtractWalkVertex();

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractWalkVertex"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkVertex"
}

{%- language name="Python", type="py" -%}
g.ExtractWalkVertex()

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.data.graph.Walk
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.data.graph.Walk</td><td>[[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]]</td><td>java.lang.String</td><td>A</td></tr>
</table>

-----------------------------------------------

