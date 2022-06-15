# StringConcat
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/StringConcat.html)

Available since Koryphe version 1.0.0

Concatenates 2 strings

## Examples

### String concat with separator


{% codetabs name="Java", type="java" -%}
final StringConcat stringConcat = new StringConcat();
stringConcat.setSeparator(" ");

{%- language name="JSON", type="json" -%}
{
  "class" : "StringConcat",
  "separator" : " "
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat",
  "separator" : " "
}
{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>hello and world</td><td>java.lang.String</td><td>hello world</td></tr>
<tr><td>java.lang.String</td><td>abc and null</td><td>java.lang.String</td><td>abc</td></tr>
<tr><td></td><td>null and null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### String concat with default separator


{% codetabs name="Java", type="java" -%}
final StringConcat stringConcat = new StringConcat();

{%- language name="JSON", type="json" -%}
{
  "class" : "StringConcat",
  "separator" : ","
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat",
  "separator" : ","
}
{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>hello and world</td><td>java.lang.String</td><td>hello,world</td></tr>
<tr><td>java.lang.String</td><td>abc and null</td><td>java.lang.String</td><td>abc</td></tr>
<tr><td></td><td>null and null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

