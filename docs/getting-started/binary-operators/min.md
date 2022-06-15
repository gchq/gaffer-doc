# Min
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.Min](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)

Available since Koryphe version 1.0.0

Returns the min value.

## Examples

### Min


{% codetabs name="Java", type="java" -%}
final Min function = new Min();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
}

{%- language name="Python", type="py" -%}
g.Min()

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5 and 6</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>inputString and anotherInputString</td><td>java.lang.String</td><td>anotherInputString</td></tr>
<tr><td></td><td>null and 1</td><td>java.lang.Integer</td><td>1</td></tr>
</table>

-----------------------------------------------

