# CollectionIntersect
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.CollectionIntersect](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/CollectionIntersect.html)

Available since Koryphe version 1.6.0

Returns items common to two collections

## Examples

### Collection intersect


{% codetabs name="Java", type="java" -%}
final CollectionIntersect collectionIntersect = new CollectionIntersect();

{%- language name="JSON", type="json" -%}
{
  "class" : "CollectionIntersect"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionIntersect"
}
{%- endcodetabs %}

Input type:

```
java.util.Collection
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[test1] and [test2, test3]</td><td>java.util.ArrayList</td><td>[]</td></tr>
<tr><td>java.util.ArrayList</td><td>[1] and [1, 2]</td><td>java.util.ArrayList</td><td>[1]</td></tr>
<tr><td>java.util.ArrayList</td><td>[] and [abc, cde]</td><td>java.util.ArrayList</td><td>[]</td></tr>
<tr><td>java.util.ArrayList</td><td>[test1] and null</td><td>java.util.ArrayList</td><td>[test1]</td></tr>
<tr><td>java.util.HashSet</td><td>[a, b] and [b, c]</td><td>java.util.HashSet</td><td>[b]</td></tr>
</table>

-----------------------------------------------

