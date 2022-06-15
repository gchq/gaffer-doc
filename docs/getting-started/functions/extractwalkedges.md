# ExtractWalkEdges
See javadoc - [uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdges](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkEdges.html)

Available since Gaffer version 1.2.0

An ExtractWalkEdges will extract a List of ALL Sets of Edges, from a given Walk.

## Examples

### Extract edges from walk


{% codetabs name="Java", type="java" -%}
final ExtractWalkEdges function = new ExtractWalkEdges();

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractWalkEdges"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdges"
}

{%- language name="Python", type="py" -%}
g.ExtractWalkEdges()

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.data.graph.Walk
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.data.graph.Walk</td><td>[[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]]</td><td>java.util.LinkedList</td><td>[[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]]</td></tr>
</table>

-----------------------------------------------

