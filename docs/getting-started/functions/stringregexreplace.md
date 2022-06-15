# StringRegexReplace
See javadoc - [uk.gov.gchq.koryphe.impl.function.StringRegexReplace](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/StringRegexReplace.html)

Available since Koryphe version 1.9.0

Replace all portions of a string which match a regular expression.

## Examples

### Replace strings


{% codetabs name="Java", type="java" -%}
final StringRegexReplace function = new StringRegexReplace("[tT]ea", "cake");

{%- language name="JSON", type="json" -%}
{
  "class" : "StringRegexReplace",
  "regex" : "[tT]ea",
  "replacement" : "cake"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringRegexReplace",
  "regex" : "[tT]ea",
  "replacement" : "cake"
}

{%- language name="Python", type="py" -%}
g.StringRegexReplace( 
  regex="[tT]ea", 
  replacement="cake" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>tea</td><td>java.lang.String</td><td>cake</td></tr>
<tr><td>java.lang.String</td><td>Tea</td><td>java.lang.String</td><td>cake</td></tr>
<tr><td>java.lang.String</td><td>TEA</td><td>java.lang.String</td><td>TEA</td></tr>
<tr><td>java.lang.String</td><td>brainteaser</td><td>java.lang.String</td><td>braincakeser</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.String</td><td>coffee</td><td>java.lang.String</td><td>coffee</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

