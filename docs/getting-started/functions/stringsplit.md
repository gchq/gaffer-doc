# StringSplit
See javadoc - [uk.gov.gchq.koryphe.impl.function.StringSplit](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/StringSplit.html)

Available since Koryphe version 1.9.0

Split a string using the provided regular expression.

## Examples

### Split strings with regex


{% codetabs name="Java", type="java" -%}
final StringSplit function = new StringSplit(" ");

{%- language name="JSON", type="json" -%}
{
  "class" : "StringSplit",
  "delimiter" : " "
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringSplit",
  "delimiter" : " "
}

{%- language name="Python", type="py" -%}
g.StringSplit( 
  delimiter=" " 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>no-delimiters-in-this-string</td><td>java.util.Arrays$ArrayList</td><td>[no-delimiters-in-this-string]</td></tr>
<tr><td>java.lang.String</td><td>string  with  two  spaces</td><td>java.util.Arrays$ArrayList</td><td>[string, with, two, spaces]</td></tr>
<tr><td>java.lang.String</td><td>string with one space</td><td>java.util.Arrays$ArrayList</td><td>[string, with, one, space]</td></tr>
<tr><td>java.lang.String</td><td>tab	delimited	string</td><td>java.util.Arrays$ArrayList</td><td>[tab	delimited	string]</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.util.Arrays$ArrayList</td><td>[]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

