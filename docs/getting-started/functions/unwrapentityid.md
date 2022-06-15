# UnwrapEntityId
See javadoc - [uk.gov.gchq.gaffer.data.element.function.UnwrapEntityId](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/function/UnwrapEntityId.html)

Available since Gaffer version 1.5.0

If the object is an EntityId, the vertex value will be unwrapped and returned, otherwise the original object will be returned.

## Examples

### Unwrap entity ids


{% codetabs name="Java", type="java" -%}
final UnwrapEntityId function = new UnwrapEntityId();

{%- language name="JSON", type="json" -%}
{
  "class" : "UnwrapEntityId"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.function.UnwrapEntityId"
}

{%- language name="Python", type="py" -%}
g.UnwrapEntityId()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.operation.data.EntitySeed</td><td>EntitySeed[vertex=vertex1]</td><td>java.lang.String</td><td>vertex1</td></tr>
<tr><td>uk.gov.gchq.gaffer.operation.data.EntitySeed</td><td>EntitySeed[vertex=vertex2]</td><td>java.lang.String</td><td>vertex2</td></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Entity</td><td>Entity[vertex=vertex2,group=group,properties=Properties[]]</td><td>java.lang.String</td><td>vertex2</td></tr>
<tr><td>java.lang.String</td><td>a string</td><td>java.lang.String</td><td>a string</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>java.lang.Integer</td><td>10</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

