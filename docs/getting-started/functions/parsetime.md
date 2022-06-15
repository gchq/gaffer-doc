# ParseTime
See javadoc - [uk.gov.gchq.koryphe.impl.function.ParseTime](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ParseTime.html)

Available since Koryphe version 1.8.0

Parses a date string into a timestamp

## Examples

### Parse time


{% codetabs name="Java", type="java" -%}
final ParseTime parseTime = new ParseTime();

{%- language name="JSON", type="json" -%}
{
  "class" : "ParseTime",
  "timeUnit" : "MILLISECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ParseTime",
  "timeUnit" : "MILLISECOND"
}

{%- language name="Python", type="py" -%}
g.ParseTime( 
  time_unit="MILLISECOND" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>2015-10-21 16:29:00.000</td><td>java.lang.Long</td><td>1445470140000</td></tr>
<tr><td>java.lang.String</td><td>1985-10-26 09:00:00.000</td><td>java.lang.Long</td><td>499190400000</td></tr>
<tr><td>java.lang.String</td><td>1885-01-01 12:00:00.000</td><td>java.lang.Long</td><td>-2682216000000</td></tr>
</table>

-----------------------------------------------

### Parse formatted time


{% codetabs name="Java", type="java" -%}
final ParseTime parseTime = new ParseTime().format("yyyy-MM hh:mm");

{%- language name="JSON", type="json" -%}
{
  "class" : "ParseTime",
  "format" : "yyyy-MM hh:mm",
  "timeUnit" : "MILLISECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ParseTime",
  "format" : "yyyy-MM hh:mm",
  "timeUnit" : "MILLISECOND"
}

{%- language name="Python", type="py" -%}
g.ParseTime( 
  format="yyyy-MM hh:mm", 
  time_unit="MILLISECOND" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>2015-10 16:29</td><td>java.lang.Long</td><td>1443742140000</td></tr>
<tr><td>java.lang.String</td><td>1985-10 09:00</td><td>java.lang.Long</td><td>497030400000</td></tr>
<tr><td>java.lang.String</td><td>1885-01 12:00</td><td>java.lang.Long</td><td>-2682259200000</td></tr>
<tr><td>java.lang.String</td><td>2015-10-21 16:29</td><td></td><td>IllegalArgumentException: Date string could not be parsed: 2015-10-21 16:29</td></tr>
</table>

-----------------------------------------------

### Parse formatted greenwich mean time


{% codetabs name="Java", type="java" -%}
final ParseTime parseTime = new ParseTime()
        .format("yyyy-MM-dd")
        .timeUnit("SECOND")
        .timeZone("GMT");

{%- language name="JSON", type="json" -%}
{
  "class" : "ParseTime",
  "format" : "yyyy-MM-dd",
  "timeZone" : "GMT",
  "timeUnit" : "SECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ParseTime",
  "format" : "yyyy-MM-dd",
  "timeZone" : "GMT",
  "timeUnit" : "SECOND"
}

{%- language name="Python", type="py" -%}
g.ParseTime( 
  time_zone="GMT", 
  format="yyyy-MM-dd", 
  time_unit="SECOND" 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>2015-10-21 16:29:00.000</td><td>java.lang.Long</td><td>1445385600</td></tr>
<tr><td>java.lang.String</td><td>1985-10-26 09:00:00.000</td><td>java.lang.Long</td><td>499132800</td></tr>
<tr><td>java.lang.String</td><td>1885-01-01 12:00:00.000</td><td>java.lang.Long</td><td>-2682288000</td></tr>
</table>

-----------------------------------------------

