# Identity
See javadoc - [uk.gov.gchq.koryphe.impl.function.Identity](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Identity.html)

Available since Koryphe version 1.0.0

Just returns the input.

## Examples

### Identity 2


{% codetabs name="Java", type="java" -%}
final Identity function = new Identity();

{%- language name="JSON", type="json" -%}
{
  "class" : "Identity"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Identity"
}

{%- language name="Python", type="py" -%}
g.Identity()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>6</td><td>java.lang.Integer</td><td>6</td></tr>
<tr><td>java.lang.Double</td><td>6.1</td><td>java.lang.Double</td><td>6.1</td></tr>
<tr><td>java.lang.String</td><td>input1</td><td>java.lang.String</td><td>input1</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td>java.util.ArrayList</td><td>[1, 2, 3]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

