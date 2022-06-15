# Exists
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.Exists](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)

Available since Koryphe version 1.0.0

Checks the input exists

## Examples

### Exists


{% codetabs name="Java", type="java" -%}
final Exists function = new Exists();

{%- language name="JSON", type="json" -%}
{
  "class" : "Exists"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
}

{%- language name="Python", type="py" -%}
g.Exists()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
<tr><td>java.lang.String</td><td></td><td>true</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>true</td></tr>
</table>

-----------------------------------------------

