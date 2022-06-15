# ToLong
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToLong](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToLong.html)

Available since Koryphe version 1.5.0

Returns any input as Long.

## Examples

### To long


{% codetabs name="Java", type="java" -%}
final ToLong function = new ToLong();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToLong"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToLong"
}

{%- language name="Python", type="py" -%}
g.ToLong()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>4</td><td>java.lang.Long</td><td>4</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>aString</td><td></td><td>NumberFormatException: For input string: "aString"</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[6, 3]</td><td></td><td>IllegalArgumentException: Could not convert value to Long: [6, 3]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

