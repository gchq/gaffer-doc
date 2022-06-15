# ExtractWalkEdgesFromHop
See javadoc - [uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdgesFromHop](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkEdgesFromHop.html)

Available since Gaffer version 1.2.0

An ExtractWalkEdgesFromHop will extract the Set of Edges at a given hop, from a provided Walk

## Examples

### Extract single set of edges from walk


{% codetabs name="Java", type="java" -%}
final ExtractWalkEdgesFromHop function = new ExtractWalkEdgesFromHop(1);

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractWalkEdgesFromHop",
  "hop" : 1
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdgesFromHop",
  "hop" : 1
}

{%- language name="Python", type="py" -%}
g.ExtractWalkEdgesFromHop( 
  hop=1 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.data.graph.Walk
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.data.graph.Walk</td><td>[[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]]</td><td>java.util.HashSet</td><td>[Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]]</td></tr>
</table>

-----------------------------------------------

