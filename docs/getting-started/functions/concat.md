# Concat
See javadoc - [uk.gov.gchq.koryphe.impl.function.Concat](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Concat.html)

Available since Koryphe version 1.0.0

Objects are concatenated by concatenating the outputs from calling toString on each object.

## Examples

### Concat objects


{% codetabs name="Java", type="java" -%}
final Concat function = new Concat();

{%- language name="JSON", type="json" -%}
{
  "class" : "Concat",
  "separator" : ","
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Concat",
  "separator" : ","
}

{%- language name="Python", type="py" -%}
g.Concat( 
  separator="," 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object, java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[foo, bar]</td><td>java.lang.String</td><td>foo,bar</td></tr>
<tr><td>[java.lang.String, ]</td><td>[foo, null]</td><td>java.lang.String</td><td>foo</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[foo, ]</td><td>java.lang.String</td><td>foo,</td></tr>
<tr><td>[java.lang.String, java.lang.Double]</td><td>[foo, 1.2]</td><td>java.lang.String</td><td>foo,1.2</td></tr>
<tr><td>[ ,java.lang.String]</td><td>[null, bar]</td><td>java.lang.String</td><td>bar</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td><td>java.lang.String</td><td>1,2</td></tr>
</table>

-----------------------------------------------

