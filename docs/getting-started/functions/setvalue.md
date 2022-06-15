# SetValue
See javadoc - [uk.gov.gchq.koryphe.impl.function.SetValue](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/SetValue.html)

Available since Koryphe version 1.5.0

Returns a set value from any input.

## Examples

### Set value


{% codetabs name="Java", type="java" -%}
final SetValue function = new SetValue(5);

{%- language name="JSON", type="json" -%}
{
  "class" : "SetValue",
  "value" : 5
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.SetValue",
  "value" : 5
}

{%- language name="Python", type="py" -%}
g.SetValue( 
  value=5 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>4</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>aString</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[4, 5]</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td></td><td>null</td><td>java.lang.Integer</td><td>5</td></tr>
</table>

-----------------------------------------------

