# ExtractId
See javadoc - [uk.gov.gchq.gaffer.data.element.function.ExtractId](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/element/function/ExtractId.html)

Available since Gaffer version 1.4.0

Extracts an identifier from an element

## Examples

### Extract source from edge

The function will simply extract the value of the provided Id, for a given Element. This Id can either be an IdentifierType, or a String representation, eg "SOURCE".


{% codetabs name="Java", type="java" -%}
final ExtractId function = new ExtractId(IdentifierType.SOURCE);

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractId",
  "id" : "SOURCE"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.function.ExtractId",
  "id" : "SOURCE"
}

{%- language name="Python", type="py" -%}
g.ExtractId( 
  id="SOURCE" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.data.element.Element
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Edge</td><td>Edge[source=src,destination=dest,directed=true,group=edge,properties=Properties[]]</td><td>java.lang.String</td><td>src</td></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Edge</td><td>Edge[source=13.2,destination=15.642,directed=true,group=otherEdge,properties=Properties[]]</td><td>java.lang.Double</td><td>13.2</td></tr>
</table>

-----------------------------------------------

### Extract vertex from entity

This example simply demonstrates the same functionality but on an Entity.


{% codetabs name="Java", type="java" -%}
final ExtractId function = new ExtractId(IdentifierType.VERTEX);

{%- language name="JSON", type="json" -%}
{
  "class" : "ExtractId",
  "id" : "VERTEX"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.element.function.ExtractId",
  "id" : "VERTEX"
}

{%- language name="Python", type="py" -%}
g.ExtractId( 
  id="VERTEX" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.data.element.Element
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.data.element.Entity</td><td>Entity[vertex=v1,group=entity,properties=Properties[]]</td><td>java.lang.String</td><td>v1</td></tr>
</table>

-----------------------------------------------

