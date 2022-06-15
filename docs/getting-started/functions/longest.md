# Longest
See javadoc - [uk.gov.gchq.koryphe.impl.function.Longest](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Longest.html)

Available since Koryphe version 1.9.0

Determines which of two input objects is the longest.

## Examples

### Get longest


{% codetabs name="Java", type="java" -%}
final Longest function = new Longest();

{%- language name="JSON", type="json" -%}
{
  "class" : "Longest"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Longest"
}

{%- language name="Python", type="py" -%}
g.Longest()

{%- endcodetabs %}

Input type:

```
java.lang.Object, java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[smaller, long string]</td><td>java.lang.String</td><td>long string</td></tr>
<tr><td>[java.util.ArrayList, java.util.HashSet]</td><td>[[1, 2], [1.5]]</td><td>java.util.ArrayList</td><td>[1, 2]</td></tr>
<tr><td>[ ,java.lang.String]</td><td>[null, value]</td><td>java.lang.String</td><td>value</td></tr>
<tr><td></td><td>null</td><td></td><td>IllegalArgumentException: Input tuple is required</td></tr>
</table>

-----------------------------------------------

