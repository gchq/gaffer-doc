# First
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.First](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)

Available since Koryphe version 1.0.0

Returns the first non-null value

## Examples

### First


{% codetabs name="Java", type="java" -%}
final First first = new First();

{%- language name="JSON", type="json" -%}
{
  "class" : "First"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.First"
}
{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>first and second</td><td>java.lang.String</td><td>first</td></tr>
<tr><td>java.lang.String</td><td>first and null</td><td>java.lang.String</td><td>first</td></tr>
<tr><td></td><td>null and second</td><td>java.lang.String</td><td>second</td></tr>
<tr><td></td><td>null and null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

