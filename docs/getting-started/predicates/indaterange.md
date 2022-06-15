# InDateRange
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.range.InDateRange](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InDateRange.html)

Available since Koryphe version 1.1.0

You can configure the start and end time strings using the following formats:
* timestamp in milliseconds
* yyyy/MM
* yyyy/MM/dd
* yyyy/MM/dd HH
* yyyy/MM/dd HH:mm
* yyyy/MM/dd HH:mm:ss

You can use a space, '-', '/', '_', ':', '|', or '.' to separate the parts.

## Examples

### In date range with day precision


{% codetabs name="Java", type="java" -%}
final InDateRange function = new InDateRange.Builder()
        .start("2017/01/01")
        .end("2017/02/01")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InDateRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01"
}

{%- language name="Python", type="py" -%}
g.InDateRange( 
  start="2017/01/01", 
  end="2017/02/01" 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Fri Jan 01 00:00:00 PST 2016</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 00:00:00 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 01:00:00 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 23:59:59 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Wed Feb 01 00:00:00 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Wed Feb 01 00:00:01 PST 2017</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In date range with second precision


{% codetabs name="Java", type="java" -%}
final InDateRange function = new InDateRange.Builder()
        .start("2017/01/01 01:30:10")
        .end("2017/01/01 01:30:50")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InDateRange",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRange",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50"
}

{%- language name="Python", type="py" -%}
g.InDateRange( 
  start="2017/01/01 01:30:10", 
  end="2017/01/01 01:30:50" 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 01:30:09 PST 2017</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 01:30:10 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 01:30:20 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 01:30:50 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 01:30:51 PST 2017</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In date range with timestamps


{% codetabs name="Java", type="java" -%}
final InDateRange function = new InDateRange.Builder()
        .start("1483315200")
        .end("1485907200")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InDateRange",
  "start" : "1483315200",
  "end" : "1485907200"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRange",
  "start" : "1483315200",
  "end" : "1485907200"
}

{%- language name="Python", type="py" -%}
g.InDateRange( 
  start="1483315200", 
  end="1485907200" 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Sat Jan 17 20:01:55 PST 1970</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Sat Jan 17 20:01:55 PST 1970</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sat Jan 17 20:01:56 PST 1970</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sat Jan 17 20:45:07 PST 1970</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sat Jan 17 20:45:07 PST 1970</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In date range exclusive


{% codetabs name="Java", type="java" -%}
final InDateRange function = new InDateRange.Builder()
        .start("2017/01/01")
        .end("2017/02/01")
        .startInclusive(false)
        .endInclusive(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InDateRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Python", type="py" -%}
g.InDateRange( 
  start="2017/01/01", 
  end="2017/02/01", 
  start_inclusive=False, 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Fri Jan 01 00:00:00 PST 2016</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 00:00:00 PST 2017</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 01:00:00 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Sun Jan 01 23:59:59 PST 2017</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Wed Feb 01 00:00:00 PST 2017</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Wed Feb 01 00:00:01 PST 2017</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### Within the last week

If the end of the range is not specified then the end of the range is unbounded.


{% codetabs name="Java", type="java" -%}
final InDateRange function = new InDateRange.Builder()
        .startOffset(-7L)
                // end is not set - it is unbounded
        .offsetUnit(TimeUnit.DAY)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InDateRange",
  "startOffset" : -7,
  "offsetUnit" : "DAY"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRange",
  "startOffset" : -7,
  "offsetUnit" : "DAY"
}

{%- language name="Python", type="py" -%}
g.InDateRange( 
  start_offset=-7, 
  offset_unit="DAY" 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Wed Sep 15 06:35:02 PDT 2021</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Fri Sep 17 06:35:02 PDT 2021</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Wed Sep 22 06:35:02 PDT 2021</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Thu Sep 23 06:35:02 PDT 2021</td><td>true</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### Exactly 7 hours ago


{% codetabs name="Java", type="java" -%}
final InDateRange function = new InDateRange.Builder()
        .startOffset(-7L)
        .endOffset(-6L)
        .endInclusive(false)
        .offsetUnit(TimeUnit.HOUR)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InDateRange",
  "startOffset" : -7,
  "endOffset" : -6,
  "endInclusive" : false,
  "offsetUnit" : "HOUR"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRange",
  "startOffset" : -7,
  "endOffset" : -6,
  "endInclusive" : false,
  "offsetUnit" : "HOUR"
}

{%- language name="Python", type="py" -%}
g.InDateRange( 
  start_offset=-7, 
  end_offset=-6, 
  offset_unit="HOUR", 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.util.Date
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.Date</td><td>Wed Sep 22 22:35:02 PDT 2021</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Wed Sep 22 23:35:12 PDT 2021</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Thu Sep 23 00:34:52 PDT 2021</td><td>true</td></tr>
<tr><td>java.util.Date</td><td>Thu Sep 23 00:35:12 PDT 2021</td><td>false</td></tr>
<tr><td>java.util.Date</td><td>Thu Sep 23 06:35:02 PDT 2021</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

