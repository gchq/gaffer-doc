# InTimeRange
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InTimeRange.html)

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

### In time range with day precision


{% codetabs name="Java", type="java" -%}
final InTimeRange function = new InTimeRange.Builder()
        .start("2017/01/01")
        .end("2017/02/01")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01"
}

{%- language name="Python", type="py" -%}
g.InTimeRange( 
  start="2017/01/01", 
  end="2017/02/01" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>1451635200000</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1483257600000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483261200000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483343999000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1485936000000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1485936001000</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In time range with second precision


{% codetabs name="Java", type="java" -%}
final InTimeRange function = new InTimeRange.Builder()
        .start("2017/01/01 01:30:10")
        .end("2017/01/01 01:30:50")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRange",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50"
}

{%- language name="Python", type="py" -%}
g.InTimeRange( 
  start="2017/01/01 01:30:10", 
  end="2017/01/01 01:30:50" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>1483263009000</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1483263010000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483263020000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483263050000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483263051000</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In time range with timestamps


{% codetabs name="Java", type="java" -%}
final InTimeRange function = new InTimeRange.Builder()
        .start("1483315200")
        .end("1485907200")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRange",
  "start" : "1483315200",
  "end" : "1485907200"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange",
  "start" : "1483315200",
  "end" : "1485907200"
}

{%- language name="Python", type="py" -%}
g.InTimeRange( 
  start="1483315200", 
  end="1485907200" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>1483315199</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1483315200</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483316200</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1485907200</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1485907201</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In time range exclusive


{% codetabs name="Java", type="java" -%}
final InTimeRange function = new InTimeRange.Builder()
        .start("2017/01/01")
        .end("2017/02/01")
        .startInclusive(false)
        .endInclusive(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Python", type="py" -%}
g.InTimeRange( 
  start="2017/01/01", 
  end="2017/02/01", 
  start_inclusive=False, 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>1451635200000</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1483257600000</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1483261200000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483343999000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1485936000000</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1485936001000</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### Within the last week

If the end of the range is not specified then the end of the range is unbounded.


{% codetabs name="Java", type="java" -%}
final InTimeRange function = new InTimeRange.Builder()
        .startOffset(-7L)
                // end is not set - it is unbounded
        .offsetUnit(TimeUnit.DAY)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRange",
  "startOffset" : -7,
  "offsetUnit" : "DAY"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange",
  "startOffset" : -7,
  "offsetUnit" : "DAY"
}

{%- language name="Python", type="py" -%}
g.InTimeRange( 
  start_offset=-7, 
  offset_unit="DAY" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>1631712902978</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1631885702978</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1632317702978</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1632404102978</td><td>true</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### Exactly 7 hours ago


{% codetabs name="Java", type="java" -%}
final InTimeRange function = new InTimeRange.Builder()
        .startOffset(-7L)
        .endOffset(-6L)
        .endInclusive(false)
        .offsetUnit(TimeUnit.HOUR)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRange",
  "startOffset" : -7,
  "endOffset" : -6,
  "endInclusive" : false,
  "offsetUnit" : "HOUR"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange",
  "startOffset" : -7,
  "endOffset" : -6,
  "endInclusive" : false,
  "offsetUnit" : "HOUR"
}

{%- language name="Python", type="py" -%}
g.InTimeRange( 
  start_offset=-7, 
  end_offset=-6, 
  offset_unit="HOUR", 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>1632375303008</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1632378913008</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1632382493008</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1632382513008</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1632404103008</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In date range with time unit microseconds


{% codetabs name="Java", type="java" -%}
final InTimeRange function = new InTimeRange.Builder()
        .start("2017/01/01 01:30:10")
        .end("2017/01/01 01:30:50")
        .timeUnit(TimeUnit.MICROSECOND)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRange",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50",
  "timeUnit" : "MICROSECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRange",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50",
  "timeUnit" : "MICROSECOND"
}

{%- language name="Python", type="py" -%}
g.InTimeRange( 
  start="2017/01/01 01:30:10", 
  end="2017/01/01 01:30:50", 
  time_unit="MICROSECOND" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>1483263009000000</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1483263010000000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483263020000000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483263050000000</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1483263051000000</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

