# IterableLongest
See javadoc - [uk.gov.gchq.koryphe.impl.function.IterableLongest](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/IterableLongest.html)

Available since Koryphe version 1.9.0

Returns the longest item in the provided iterable.

## Examples

### Get longest in the iterable


{% codetabs name="Java", type="java" -%}
final IterableLongest function = new IterableLongest();

{%- language name="JSON", type="json" -%}
{
  "class" : "IterableLongest"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.IterableLongest"
}

{%- language name="Python", type="py" -%}
g.IterableLongest()

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[[1, 2], [1.5]]</td><td>java.util.ArrayList</td><td>[1, 2]</td></tr>
<tr><td>java.util.ArrayList</td><td>[which, is, the, longest, word]</td><td>java.lang.String</td><td>longest</td></tr>
<tr><td>java.util.ArrayList</td><td>[]</td><td></td><td>null</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

