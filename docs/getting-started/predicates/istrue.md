# IsTrue
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsTrue](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsTrue.html)

Available since Koryphe version 1.0.0

Checks if an input is true

## Examples

### Is true


{% codetabs name="Java", type="java" -%}
final IsTrue function = new IsTrue();

{%- language name="JSON", type="json" -%}
{
  "class" : "IsTrue"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
}

{%- language name="Python", type="py" -%}
g.IsTrue()

{%- endcodetabs %}

Input type:

```
java.lang.Boolean
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Boolean</td><td>true</td><td>true</td></tr>
<tr><td>java.lang.Boolean</td><td>false</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>true</td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Boolean</td></tr>
</table>

-----------------------------------------------

