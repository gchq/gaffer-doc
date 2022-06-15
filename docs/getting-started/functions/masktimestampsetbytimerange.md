# MaskTimestampSetByTimeRange
See javadoc - [uk.gov.gchq.gaffer.time.function.MaskTimestampSetByTimeRange](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/time/function/MaskTimestampSetByTimeRange.html)

Available since Gaffer version 1.9.0

Applies a mask to a timestamp set based on a start and end date

## Examples

### Mask with start date


{% codetabs name="Java", type="java" -%}
MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10000L, null);

{%- language name="JSON", type="json" -%}
{
  "class" : "MaskTimestampSetByTimeRange",
  "startTime" : 10000,
  "timeUnit" : "MILLISECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.time.function.MaskTimestampSetByTimeRange",
  "startTime" : 10000,
  "timeUnit" : "MILLISECOND"
}

{%- language name="Python", type="py" -%}
g.MaskTimestampSetByTimeRange( 
  start_time=10000, 
  time_unit="MILLISECOND" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.time.RBMBackedTimestampSet
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]</td><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]</td></tr>
</table>

-----------------------------------------------

### Mask with end date


{% codetabs name="Java", type="java" -%}
MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(null, 20000L);

{%- language name="JSON", type="json" -%}
{
  "class" : "MaskTimestampSetByTimeRange",
  "endTime" : 20000,
  "timeUnit" : "MILLISECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.time.function.MaskTimestampSetByTimeRange",
  "endTime" : 20000,
  "timeUnit" : "MILLISECOND"
}

{%- language name="Python", type="py" -%}
g.MaskTimestampSetByTimeRange( 
  end_time=20000, 
  time_unit="MILLISECOND" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.time.RBMBackedTimestampSet
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]</td><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z]</td></tr>
</table>

-----------------------------------------------

### Mask with start and end date


{% codetabs name="Java", type="java" -%}
MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10000L, 20000L);

{%- language name="JSON", type="json" -%}
{
  "class" : "MaskTimestampSetByTimeRange",
  "startTime" : 10000,
  "endTime" : 20000,
  "timeUnit" : "MILLISECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.time.function.MaskTimestampSetByTimeRange",
  "startTime" : 10000,
  "endTime" : 20000,
  "timeUnit" : "MILLISECOND"
}

{%- language name="Python", type="py" -%}
g.MaskTimestampSetByTimeRange( 
  start_time=10000, 
  end_time=20000, 
  time_unit="MILLISECOND" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.time.RBMBackedTimestampSet
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]</td><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:10Z,1970-01-01T00:00:20Z]</td></tr>
</table>

-----------------------------------------------

### Mask with no start or end dates


{% codetabs name="Java", type="java" -%}
MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange();

{%- language name="JSON", type="json" -%}
{
  "class" : "MaskTimestampSetByTimeRange",
  "timeUnit" : "MILLISECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.time.function.MaskTimestampSetByTimeRange",
  "timeUnit" : "MILLISECOND"
}

{%- language name="Python", type="py" -%}
g.MaskTimestampSetByTimeRange( 
  time_unit="MILLISECOND" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.time.RBMBackedTimestampSet
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]</td><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]</td></tr>
</table>

-----------------------------------------------

### Mask with time unit


{% codetabs name="Java", type="java" -%}
MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10L, 25L, TimeUnit.SECOND);

{%- language name="JSON", type="json" -%}
{
  "class" : "MaskTimestampSetByTimeRange",
  "startTime" : 10,
  "endTime" : 25,
  "timeUnit" : "SECOND"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.time.function.MaskTimestampSetByTimeRange",
  "startTime" : 10,
  "endTime" : 25,
  "timeUnit" : "SECOND"
}

{%- language name="Python", type="py" -%}
g.MaskTimestampSetByTimeRange( 
  start_time=10, 
  end_time=25, 
  time_unit="SECOND" 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.gaffer.time.RBMBackedTimestampSet
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]</td><td>uk.gov.gchq.gaffer.time.RBMBackedTimestampSet</td><td>RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:10Z,1970-01-01T00:00:20Z]</td></tr>
</table>

-----------------------------------------------

