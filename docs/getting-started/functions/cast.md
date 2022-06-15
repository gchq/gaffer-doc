# Cast
See javadoc - [uk.gov.gchq.koryphe.impl.function.Cast](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Cast.html)

Available since Koryphe version 1.5.0

Casts input to specified class.

## Examples

### Cast


{% codetabs name="Java", type="java" -%}
final Cast function = new Cast(String.class);

{%- language name="JSON", type="json" -%}
{
  "class" : "Cast",
  "outputClass" : "String"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Cast",
  "outputClass" : "java.lang.String"
}

{%- language name="Python", type="py" -%}
g.Cast( 
  output_class="java.lang.String" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td></td><td>ClassCastException: Cannot cast java.lang.Integer to java.lang.String</td></tr>
<tr><td>java.lang.String</td><td>inputString</td><td>java.lang.String</td><td>inputString</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

