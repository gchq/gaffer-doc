# HyperLogLogPlusIsLessThan
See javadoc - [uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/predicate/HyperLogLogPlusIsLessThan.html)

Available since Gaffer version 1.0.0

Tests a HyperLogLogPlus cardinality is less than a provided value

## Examples

### Hyper log log plus is less than 2


{% codetabs name="Java", type="java" -%}
final HyperLogLogPlusIsLessThan function = new HyperLogLogPlusIsLessThan(2);

{%- language name="JSON", type="json" -%}
{
  "class" : "HyperLogLogPlusIsLessThan",
  "orEqualTo" : false,
  "value" : 2
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan",
  "orEqualTo" : false,
  "value" : 2
}

{%- language name="Python", type="py" -%}
g.HyperLogLogPlusIsLessThan( 
  value=2, 
  or_equal_to=False 
)

{%- endcodetabs %}

Input type:

```
com.clearspring.analytics.stream.cardinality.HyperLogLogPlus
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@275e45d8</td><td>true</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@14d29dd4</td><td>false</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@30c0f7eb</td><td>false</td></tr>
</table>

-----------------------------------------------

### Hyper log log plus is less than or equal to 2


{% codetabs name="Java", type="java" -%}
final HyperLogLogPlusIsLessThan function = new HyperLogLogPlusIsLessThan(2, true);

{%- language name="JSON", type="json" -%}
{
  "class" : "HyperLogLogPlusIsLessThan",
  "orEqualTo" : true,
  "value" : 2
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan",
  "orEqualTo" : true,
  "value" : 2
}

{%- language name="Python", type="py" -%}
g.HyperLogLogPlusIsLessThan( 
  value=2, 
  or_equal_to=True 
)

{%- endcodetabs %}

Input type:

```
com.clearspring.analytics.stream.cardinality.HyperLogLogPlus
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@275e45d8</td><td>true</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@14d29dd4</td><td>true</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@30c0f7eb</td><td>false</td></tr>
</table>

-----------------------------------------------

