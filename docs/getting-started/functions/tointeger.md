# ToInteger
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToInteger](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToInteger.html)

Available since Koryphe version 1.5.0

Returns any input as Integer.

## Examples

### To integer


{% codetabs name="Java", type="java" -%}
final ToInteger function = new ToInteger();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToInteger"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToInteger"
}

{%- language name="Python", type="py" -%}
g.ToInteger()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>4</td><td>java.lang.Integer</td><td>4</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>aString</td><td></td><td>NumberFormatException: For input string: "aString"</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[6, 3]</td><td></td><td>IllegalArgumentException: Could not convert value to Integer: [6, 3]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

