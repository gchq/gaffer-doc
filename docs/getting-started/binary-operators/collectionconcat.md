# CollectionConcat
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/CollectionConcat.html)

Available since Koryphe version 1.0.0

Concatenates two collections together

## Examples

### Collection concat


{% codetabs name="Java", type="java" -%}
final CollectionConcat collectionConcat = new CollectionConcat();

{%- language name="JSON", type="json" -%}
{
  "class" : "CollectionConcat"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
}
{%- endcodetabs %}

Input type:

```
java.util.Collection
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[test1] and [test2, test3]</td><td>java.util.ArrayList</td><td>[test1, test2, test3]</td></tr>
<tr><td>java.util.ArrayList</td><td>[1] and [test2, test3]</td><td>java.util.ArrayList</td><td>[1, test2, test3]</td></tr>
<tr><td>java.util.ArrayList</td><td>[] and [abc, cde]</td><td>java.util.ArrayList</td><td>[abc, cde]</td></tr>
<tr><td>java.util.ArrayList</td><td>[test1] and null</td><td>java.util.ArrayList</td><td>[test1]</td></tr>
<tr><td>java.util.HashSet</td><td>[a, b] and [b, c]</td><td>java.util.HashSet</td><td>[a, b, c]</td></tr>
</table>

-----------------------------------------------

