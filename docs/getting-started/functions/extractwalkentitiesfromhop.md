# ExtractWalkEntitiesFromHop
See javadoc - [uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEntitiesFromHop](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkEntitiesFromHop.html)

Available since Gaffer version 1.2.0

Extracts the set of entities from a single hop in a Walk

## Examples

### Extract single set of entities from walk


{% codetabs name="Java", type="java" -%}
final ExtractWalkEntitiesFromHop function = new ExtractWalkEntitiesFromHop(1);

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractWalkEntitiesFromHop",
  "hop" : 1
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEntitiesFromHop",
  "hop" : 1
}

{%- language name="Python", type="py" -%}
g.ExtractWalkEntitiesFromHop( 
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
<tr><td>uk.gov.gchq.gaffer.data.graph.Walk</td><td>[[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]]</td><td>java.util.HashSet</td><td>[Entity[vertex=B,group=BasicEntity,properties=Properties[]], Entity[vertex=B,group=EnhancedEntity,properties=Properties[]]]</td></tr>
</table>

-----------------------------------------------

