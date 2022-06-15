# ExtractGroup
See javadoc - [uk.gov.gchq.gaffer.data.element.function.ExtractGroup](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/function/ExtractGroup.html)

Available since Gaffer version 1.4.0

Extracts a group from an element

## Examples

### Extract group

The function will simply extract the group from a given Element.


{% codetabs name="Java", type="java" -%}
final ExtractGroup function = new ExtractGroup();

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractGroup"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.function.ExtractGroup"
}

{%- language name="Python", type="py" -%}
g.ExtractGroup()

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.data.element.Element
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Edge</td><td>Edge[source=src,destination=dest,directed=true,group=EdgeGroup,properties=Properties[]]</td><td>java.lang.String</td><td>EdgeGroup</td></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Entity</td><td>Entity[vertex=vertex,group=EntityGroup,properties=Properties[]]</td><td>java.lang.String</td><td>EntityGroup</td></tr>
<tr><td>uk.gov.gchq.gaffer.operation.data.EntitySeed</td><td>EntitySeed[vertex=vertex]</td><td></td><td>ClassCastException: uk.gov.gchq.gaffer.operation.data.EntitySeed cannot be cast to uk.gov.gchq.gaffer.data.element.Element</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

