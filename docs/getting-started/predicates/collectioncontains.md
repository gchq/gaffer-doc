# CollectionContains
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.CollectionContains](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/CollectionContains.html)

Available since Koryphe version 1.0.0

Checks if a collection contains a provided value

## Examples

### Collection contains


{% codetabs name="Java", type="java" -%}
final CollectionContains function = new CollectionContains(1);

{%- language name="JSON", type="json" -%}
{
  "class" : "CollectionContains",
  "value" : 1
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
  "value" : 1
}

{%- language name="Python", type="py" -%}
g.CollectionContains( 
  value=1 
)

{%- endcodetabs %}

Input type:

```
java.util.Collection
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashSet</td><td>[1, 2, 3]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[1]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[2]</td><td>false</td></tr>
<tr><td>java.util.HashSet</td><td>[]</td><td>false</td></tr>
</table>

-----------------------------------------------

