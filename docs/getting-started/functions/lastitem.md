# LastItem
See javadoc - [uk.gov.gchq.koryphe.impl.function.LastItem](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/LastItem.html)

Available since Koryphe version 1.1.0

For a given Iterable, a LastItem will extract the last item.

## Examples

### Extract last item


{% codetabs name="Java", type="java" -%}
final LastItem<Integer> function = new LastItem<>();

{%- language name="JSON", type="json" -%}
{
  "class" : "LastItem"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.LastItem"
}

{%- language name="Python", type="py" -%}
g.LastItem()

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td>java.lang.Integer</td><td>3</td></tr>
<tr><td>java.util.ArrayList</td><td>[5, 8, 13]</td><td>java.lang.Integer</td><td>13</td></tr>
<tr><td>java.util.ArrayList</td><td>[21, 34, 55]</td><td>java.lang.Integer</td><td>55</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, null, 3]</td><td>java.lang.Integer</td><td>3</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, null]</td><td></td><td>null</td></tr>
<tr><td></td><td>null</td><td></td><td>IllegalArgumentException: Input cannot be null</td></tr>
</table>

-----------------------------------------------

