# Sum
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.Sum](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html)

Available since Koryphe version 1.0.0

Calculates the sum of 2 numbers

## Examples

### Sum


{% codetabs name="Java", type="java" -%}
final Sum sum = new Sum();

{%- language name="JSON", type="json" -%}
{
  "class" : "Sum"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
}

{%- language name="Python", type="py" -%}
g.Sum()

{%- endcodetabs %}

Input type:

```
java.lang.Number
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>20 and 3</td><td>java.lang.Long</td><td>23</td></tr>
<tr><td>java.lang.Integer</td><td>300 and 400</td><td>java.lang.Integer</td><td>700</td></tr>
<tr><td>java.lang.Double</td><td>0.0 and 3.0</td><td>java.lang.Double</td><td>3.0</td></tr>
<tr><td>java.lang.Short</td><td>50 and 50</td><td>java.lang.Short</td><td>100</td></tr>
<tr><td>java.lang.Short</td><td>30000 and 10000</td><td>java.lang.Short</td><td>32767</td></tr>
<tr><td>java.lang.Integer</td><td>-5 and 5</td><td>java.lang.Integer</td><td>0</td></tr>
<tr><td>java.lang.Long</td><td>20 and null</td><td>java.lang.Long</td><td>20</td></tr>
</table>

-----------------------------------------------

