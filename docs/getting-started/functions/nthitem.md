# NthItem
See javadoc - [uk.gov.gchq.koryphe.impl.function.NthItem](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/NthItem.html)

Available since Koryphe version 1.1.0

For a given Iterable, an NthItem will extract the item at the Nth index, where n is a user-provided selection. (Consider that this is array-backed, so a selection of "1" will extract the item at index 1, ie the 2nd item)"

## Examples

### Extract nth item


{% codetabs name="Java", type="java" -%}
final NthItem<Integer> function = new NthItem<>(2);

{%- language name="JSON", type="json" -%}
{
  "class" : "NthItem",
  "selection" : 2
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.NthItem",
  "selection" : 2
}

{%- language name="Python", type="py" -%}
g.NthItem( 
  selection=2 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[3, 1, 4]</td><td>java.lang.Integer</td><td>4</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 5, 9]</td><td>java.lang.Integer</td><td>9</td></tr>
<tr><td>java.util.ArrayList</td><td>[2, 6, 5]</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.util.ArrayList</td><td>[2, null, 5]</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.util.ArrayList</td><td>[2, 6, null]</td><td></td><td>null</td></tr>
<tr><td></td><td>null</td><td></td><td>IllegalArgumentException: Input cannot be null</td></tr>
</table>

-----------------------------------------------

