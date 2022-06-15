# ToTuple
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToTuple](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToTuple.html)

Available since Koryphe version 1.8.0

Converts an Object into a Tuple

## Examples

### To tuple


{% codetabs name="Java", type="java" -%}
final ToTuple function = new ToTuple();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToTuple"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToTuple"
}

{%- language name="Python", type="py" -%}
g.ToTuple()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3, 4]</td><td>[java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]</td><td>[1, 2, 3, 4]</td></tr>
<tr><td>[Ljava.lang.Integer;</td><td>[1, 2, 3, 4]</td><td>[java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]</td><td>[1, 2, 3, 4]</td></tr>
<tr><td>java.util.HashMap</td><td>{A=1, B=2, C=3}</td><td>uk.gov.gchq.koryphe.tuple.MapTuple</td><td>[1, 2, 3]</td></tr>
</table>

-----------------------------------------------

