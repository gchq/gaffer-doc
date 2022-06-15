# IsFalse
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsFalse](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsFalse.html)

Available since Koryphe version 1.0.0

Checks if an input is false

## Examples

### Is false


{% codetabs name="Java", type="java" -%}
final IsFalse function = new IsFalse();

{%- language name="JSON", type="json" -%}
{
  "class" : "IsFalse"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsFalse"
}

{%- language name="Python", type="py" -%}
g.IsFalse()

{%- endcodetabs %}

Input type:

```
java.lang.Boolean
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Boolean</td><td>true</td><td>false</td></tr>
<tr><td>java.lang.Boolean</td><td>false</td><td>true</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>true</td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Boolean</td></tr>
</table>

-----------------------------------------------

