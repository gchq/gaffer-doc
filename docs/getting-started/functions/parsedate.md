# ParseDate
See javadoc - [uk.gov.gchq.koryphe.impl.function.ParseDate](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ParseDate.html)

Available since Koryphe version 1.8.0

Parses a date string

## Examples

### Parse date greenwich mean time plus 4 hours

date string


{% codetabs name="Java", type="java" -%}
final ParseDate parseDate = new ParseDate();
parseDate.setFormat("yyyy-MM-dd HH:mm:ss.SSS");
parseDate.setTimeZone("Etc/GMT+4");

{%- language name="JSON", type="json" -%}
{
  "class" : "ParseDate",
  "format" : "yyyy-MM-dd HH:mm:ss.SSS",
  "timeZone" : "Etc/GMT+4"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ParseDate",
  "format" : "yyyy-MM-dd HH:mm:ss.SSS",
  "timeZone" : "Etc/GMT+4"
}

{%- language name="Python", type="py" -%}
g.ParseDate( 
  time_zone="Etc/GMT+4", 
  format="yyyy-MM-dd HH:mm:ss.SSS" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>2015-10-21 16:29:00.000</td><td>java.util.Date</td><td>Wed Oct 21 13:29:00 PDT 2015</td></tr>
<tr><td>java.lang.String</td><td>1985-10-26 09:00:00.000</td><td>java.util.Date</td><td>Sat Oct 26 06:00:00 PDT 1985</td></tr>
<tr><td>java.lang.String</td><td>1885-01-01 12:00:00.000</td><td>java.util.Date</td><td>Thu Jan 01 08:00:00 PST 1885</td></tr>
</table>

-----------------------------------------------

### Parse date greenwich mean time plus 0 hours

date string


{% codetabs name="Java", type="java" -%}
final ParseDate parseDate = new ParseDate();
parseDate.setFormat("yyyy-MM-dd HH:mm");
parseDate.setTimeZone("Etc/GMT+0");

{%- language name="JSON", type="json" -%}
{
  "class" : "ParseDate",
  "format" : "yyyy-MM-dd HH:mm",
  "timeZone" : "Etc/GMT+0"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ParseDate",
  "format" : "yyyy-MM-dd HH:mm",
  "timeZone" : "Etc/GMT+0"
}

{%- language name="Python", type="py" -%}
g.ParseDate( 
  time_zone="Etc/GMT+0", 
  format="yyyy-MM-dd HH:mm" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>2015-10-21 16:29</td><td>java.util.Date</td><td>Wed Oct 21 09:29:00 PDT 2015</td></tr>
<tr><td>java.lang.String</td><td>1985-10-26 09:00</td><td>java.util.Date</td><td>Sat Oct 26 02:00:00 PDT 1985</td></tr>
<tr><td>java.lang.String</td><td>1885-01-01 12:00</td><td>java.util.Date</td><td>Thu Jan 01 04:00:00 PST 1885</td></tr>
</table>

-----------------------------------------------

