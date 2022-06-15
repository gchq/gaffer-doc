# AreEqual
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.AreEqual](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/AreEqual.html)

Available since Koryphe version 1.0.0

Returns true if the two inputs are equal

## Examples

### Are equal


{% codetabs name="Java", type="java" -%}
final AreEqual function = new AreEqual();

{%- language name="JSON", type="json" -%}
{
  "class" : "AreEqual"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.AreEqual"
}

{%- language name="Python", type="py" -%}
g.AreEqual()

{%- endcodetabs %}

Input type:

```
java.lang.Object, java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Double]</td><td>[1, 1.0]</td><td>false</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[2.5, 2.5]</td><td>true</td></tr>
<tr><td>[java.lang.String, ]</td><td>[, null]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[abc, abc]</td><td>true</td></tr>
</table>

-----------------------------------------------

