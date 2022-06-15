# ToDateString
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToDateString](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToDateString.html)

Available since Koryphe version 1.8.0

Converts a date to a string

## Examples

### To date string micro formatted


{% codetabs name="Java", type="java" -%}
final ToDateString function = new ToDateString("yyyy-MM-dd HH:mm:ss.SSS");

{%- language name="JSON", type="json" -%}
{
  "class" : "ToDateString",
  "format" : "yyyy-MM-dd HH:mm:ss.SSS"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToDateString",
  "format" : "yyyy-MM-dd HH:mm:ss.SSS"
}

{%- language name="Python", type="py" -%}
g.ToDateString( 
  format="yyyy-MM-dd HH:mm:ss.SSS" 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Tue Jan 06 10:39:25 PST 1970</td><td>java.lang.String</td><td>1970-01-06 10:39:25.200</td></tr>
<tr><td>java.util.Date</td><td>Wed Dec 31 16:00:00 PST 1969</td><td>java.lang.String</td><td>1969-12-31 16:00:00.000</td></tr>
<tr><td>java.lang.Long</td><td>1632404107238</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.util.Date</td></tr>
<tr><td>java.util.Date</td><td>Thu Dec 25 21:20:34 PST 1969</td><td>java.lang.String</td><td>1969-12-25 21:20:34.800</td></tr>
</table>

-----------------------------------------------

### To date string short formatted to min


{% codetabs name="Java", type="java" -%}
final ToDateString function = new ToDateString("yy-MM-dd HH:mm");

{%- language name="JSON", type="json" -%}
{
  "class" : "ToDateString",
  "format" : "yy-MM-dd HH:mm"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToDateString",
  "format" : "yy-MM-dd HH:mm"
}

{%- language name="Python", type="py" -%}
g.ToDateString( 
  format="yy-MM-dd HH:mm" 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Tue Jan 06 10:39:25 PST 1970</td><td>java.lang.String</td><td>70-01-06 10:39</td></tr>
<tr><td>java.util.Date</td><td>Wed Dec 31 16:00:00 PST 1969</td><td>java.lang.String</td><td>69-12-31 16:00</td></tr>
<tr><td>java.lang.Long</td><td>1632404107269</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.util.Date</td></tr>
<tr><td>java.util.Date</td><td>Thu Dec 25 21:20:34 PST 1969</td><td>java.lang.String</td><td>69-12-25 21:20</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### To date string short formatted to day


{% codetabs name="Java", type="java" -%}
final ToDateString function = new ToDateString("yy-MM-dd");

{%- language name="JSON", type="json" -%}
{
  "class" : "ToDateString",
  "format" : "yy-MM-dd"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToDateString",
  "format" : "yy-MM-dd"
}

{%- language name="Python", type="py" -%}
g.ToDateString( 
  format="yy-MM-dd" 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Tue Jan 06 10:39:25 PST 1970</td><td>java.lang.String</td><td>70-01-06</td></tr>
<tr><td>java.util.Date</td><td>Wed Dec 31 16:00:00 PST 1969</td><td>java.lang.String</td><td>69-12-31</td></tr>
<tr><td>java.lang.Long</td><td>1632404107300</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.util.Date</td></tr>
<tr><td>java.util.Date</td><td>Thu Dec 25 21:20:34 PST 1969</td><td>java.lang.String</td><td>69-12-25</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

