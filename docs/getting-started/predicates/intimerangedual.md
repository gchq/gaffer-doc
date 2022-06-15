# InTimeRangeDual
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InTimeRangeDual.html)

Available since Koryphe version 1.1.0

The predicate tests 2 timestamp (long) inputs (a start timestamp and an end timestamp) are within a defined range. You can configure the start and end time strings using the following formats:
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
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("2017/01/01")
        .end("2017/02/01")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "2017/01/01",
  "end" : "2017/02/01"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "2017/01/01",
  "end" : "2017/02/01"
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="2017/01/01", 
  end="2017/02/01" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1451635200000, 1454313600000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1483257600000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1483261200000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1483343999000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1485936000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1485936001000]</td><td>true</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In time range with second precision


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("2017/01/01 01:30:10")
        .end("2017/01/01 01:30:50")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "2017/01/01 01:30:10",
  "end" : "2017/01/01 01:30:50"
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="2017/01/01 01:30:10", 
  end="2017/01/01 01:30:50" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483263008000, 1483263009000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483263010000, 1483263010000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483263010000, 1483263020000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483263010000, 1483263050000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483263010000, 1483263051000]</td><td>true</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In time range with timestamps


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("1483315200")
        .end("1485907200")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "1483315200",
  "end" : "1485907200"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "1483315200",
  "end" : "1485907200"
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="1483315200", 
  end="1485907200" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483315198, 1483315199]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483315198, 1483315200]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483315200, 1483315201]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483316200, 1485907200]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483316200, 1485907201]</td><td>true</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In time range exclusive


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("2017/01/01")
        .end("2017/02/01")
        .startInclusive(false)
        .endInclusive(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="2017/01/01", 
  end="2017/02/01", 
  start_inclusive=False, 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1451635200000, 1454313600000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1483257600000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1483261200000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1483343999000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1485936000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000, 1485936001000]</td><td>true</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### Within the last week

If the end of the range is not specified then the end of the range is unbounded.


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .startOffset(-7L)
                // end is not set - it is unbounded
        .offsetUnit(TimeUnit.DAY)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "startOffset" : -7,
  "offsetUnit" : "DAY"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "startOffset" : -7,
  "offsetUnit" : "DAY"
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start_offset=-7, 
  offset_unit="DAY" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1631626502650, 1631712902650]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1631885702650, 1631885702651]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1631885702650, 1632317702650]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1632404102640, 1632404102650]</td><td>true</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### Exactly 7 hours ago


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .startOffset(-7L)
        .endOffset(-6L)
        .endInclusive(false)
        .offsetUnit(TimeUnit.HOUR)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "startOffset" : -7,
  "endOffset" : -6,
  "endInclusive" : false,
  "offsetUnit" : "HOUR"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "startOffset" : -7,
  "endOffset" : -6,
  "endInclusive" : false,
  "offsetUnit" : "HOUR"
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start_offset=-7, 
  end_offset=-6, 
  offset_unit="HOUR", 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1632375302681, 1632378902671]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1632378912681, 1632378922681]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1632378912681, 1632382492681]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1632378912681, 1632382512681]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1632378912681, 1632404102681]</td><td>true</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In date range with time unit microseconds


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("2017/01/01")
        .end("2017/02/01")
        .timeUnit(TimeUnit.MICROSECOND)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "timeUnit" : "MICROSECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "2017/01/01",
  "end" : "2017/02/01",
  "timeUnit" : "MICROSECOND"
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="2017/01/01", 
  end="2017/02/01", 
  time_unit="MICROSECOND" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1451635200000000, 1454313600000000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000000, 1483257600000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000000, 1483261200000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000000, 1483343999000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000000, 1485936000000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483261200000000, 1485936001000000]</td><td>true</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### Fully uncontained range


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("2017/03/01")
        .end("2017/08/01")
        .startFullyContained(false)
        .endFullyContained(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "2017/03/01",
  "end" : "2017/08/01",
  "endFullyContained" : false,
  "startFullyContained" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "2017/03/01",
  "end" : "2017/08/01",
  "endFullyContained" : false,
  "startFullyContained" : false
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="2017/03/01", 
  end="2017/08/01", 
  start_fully_contained=False, 
  end_fully_contained=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1485936000000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1491030000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1491030000000, 1493622000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1491030000000, 1504249200000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1504249200000, 1506841200000]</td><td>false</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### Start contained range


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("2017/03/01")
        .end("2017/08/01")
        .startFullyContained(true)
        .endFullyContained(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "2017/03/01",
  "end" : "2017/08/01",
  "endFullyContained" : false,
  "startFullyContained" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "2017/03/01",
  "end" : "2017/08/01",
  "endFullyContained" : false,
  "startFullyContained" : true
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="2017/03/01", 
  end="2017/08/01", 
  start_fully_contained=True, 
  end_fully_contained=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1485936000000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1491030000000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1491030000000, 1493622000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1491030000000, 1504249200000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1504249200000, 1506841200000]</td><td>false</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### Fully contained range


{% codetabs name="Java", type="java" -%}
final InTimeRangeDual function = new InTimeRangeDual.Builder()
        .start("2017/03/01")
        .end("2017/08/01")
        .startFullyContained(true)
        .endFullyContained(true)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InTimeRangeDual",
  "start" : "2017/03/01",
  "end" : "2017/08/01",
  "endFullyContained" : true,
  "startFullyContained" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InTimeRangeDual",
  "start" : "2017/03/01",
  "end" : "2017/08/01",
  "endFullyContained" : true,
  "startFullyContained" : true
}

{%- language name="Python", type="py" -%}
g.InTimeRangeDual( 
  start="2017/03/01", 
  end="2017/08/01", 
  start_fully_contained=True, 
  end_fully_contained=True 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1485936000000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1483257600000, 1491030000000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1491030000000, 1493622000000]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1491030000000, 1504249200000]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1504249200000, 1506841200000]</td><td>false</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

