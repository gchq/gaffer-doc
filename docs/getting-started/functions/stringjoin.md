# StringJoin
See javadoc - [uk.gov.gchq.koryphe.impl.function.StringJoin](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/StringJoin.html)

Available since Koryphe version 1.9.0

Joins together all strings in an iterable using the supplied delimiter.

## Examples

### Without a delmiter


{% codetabs name="Java", type="java" -%}
final StringJoin function = new StringJoin();

{%- language name="JSON", type="json" -%}
{
  "class" : "StringJoin"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringJoin"
}

{%- language name="Python", type="py" -%}
g.StringJoin()

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[here, are, my, strings]</td><td>java.lang.String</td><td>herearemystrings</td></tr>
<tr><td>java.util.ArrayList</td><td>[single]</td><td>java.lang.String</td><td>single</td></tr>
<tr><td>java.util.ArrayList</td><td>[]</td><td>java.lang.String</td><td></td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### With a delimiter


{% codetabs name="Java", type="java" -%}
final StringJoin function = new StringJoin("-");

{%- language name="JSON", type="json" -%}
{
  "class" : "StringJoin",
  "delimiter" : "-"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringJoin",
  "delimiter" : "-"
}

{%- language name="Python", type="py" -%}
g.StringJoin( 
  delimiter="-" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[here, are, my, strings]</td><td>java.lang.String</td><td>here-are-my-strings</td></tr>
<tr><td>java.util.ArrayList</td><td>[single]</td><td>java.lang.String</td><td>single</td></tr>
<tr><td>java.util.ArrayList</td><td>[]</td><td>java.lang.String</td><td></td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

