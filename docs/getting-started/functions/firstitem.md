# FirstItem
See javadoc - [uk.gov.gchq.koryphe.impl.function.FirstItem](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/FirstItem.html)

Available since Koryphe version 1.1.0

For a given Iterable, a FirstItem will extract the first item.

## Examples

### Extract first item


{% codetabs name="Java", type="java" -%}
final FirstItem<Integer> function = new FirstItem<>();

{%- language name="JSON", type="json" -%}
{
  "class" : "FirstItem"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.FirstItem"
}

{%- language name="Python", type="py" -%}
g.FirstItem()

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[2, 3, 5]</td><td>java.lang.Integer</td><td>2</td></tr>
<tr><td>java.util.ArrayList</td><td>[7, 11, 13]</td><td>java.lang.Integer</td><td>7</td></tr>
<tr><td>java.util.ArrayList</td><td>[17, 19, null]</td><td>java.lang.Integer</td><td>17</td></tr>
<tr><td>java.util.ArrayList</td><td>[null, 19, 27]</td><td></td><td>null</td></tr>
<tr><td></td><td>null</td><td></td><td>IllegalArgumentException: Input cannot be null</td></tr>
</table>

-----------------------------------------------

