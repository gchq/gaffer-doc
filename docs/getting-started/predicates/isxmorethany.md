# IsXMoreThanY
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsXMoreThanY](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsXMoreThanY.html)

Available since Koryphe version 1.0.0

Checks the first comparable is more than the second comparable

## Examples

### Is x more than y


{% codetabs name="Java", type="java" -%}
final IsXMoreThanY function = new IsXMoreThanY();

{%- language name="JSON", type="json" -%}
{
  "class" : "IsXMoreThanY"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXMoreThanY"
}

{%- language name="Python", type="py" -%}
g.IsXMoreThanY()

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[5, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 5]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Integer]</td><td>[10, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[5, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[10, 5]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Long]</td><td>[10, 5]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[bcd, cde]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[bcd, abc]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.Integer]</td><td>[10, 5]</td><td>false</td></tr>
</table>

-----------------------------------------------

