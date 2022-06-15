# CurrentDate
See javadoc - [uk.gov.gchq.koryphe.impl.function.CurrentDate](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/CurrentDate.html)

Available since Koryphe version 1.8.0

Returns the current date

## Examples

### Get current date


{% codetabs name="Java", type="java" -%}
final CurrentDate currentDate = new CurrentDate();

{%- language name="JSON", type="json" -%}
{
  "class" : "CurrentDate"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CurrentDate"
}

{%- language name="Python", type="py" -%}
g.CurrentDate()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td></td><td>null</td><td>java.util.Date</td><td>Thu Sep 23 06:35:04 PDT 2021</td></tr>
</table>

-----------------------------------------------

