# ToUpperCase
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToUpperCase](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToUpperCase.html)

Available since Koryphe version 1.5.0

Returns null on any input object.

## Examples

### To upper case


{% codetabs name="Java", type="java" -%}
final ToUpperCase function = new ToUpperCase();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToUpperCase"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToUpperCase"
}

{%- language name="Python", type="py" -%}
g.ToUpperCase()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>4</td><td>java.lang.String</td><td>4</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>java.lang.String</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>ACAPTIALISEDSTRING</td><td>java.lang.String</td><td>ACAPTIALISEDSTRING</td></tr>
<tr><td>java.lang.String</td><td>alowercasestring</td><td>java.lang.String</td><td>ALOWERCASESTRING</td></tr>
<tr><td>java.lang.String</td><td>aString</td><td>java.lang.String</td><td>ASTRING</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[6, 3]</td><td>java.lang.String</td><td>[6, 3]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

