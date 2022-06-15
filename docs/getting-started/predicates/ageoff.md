# AgeOff
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.AgeOff](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/AgeOff.html)

Available since Koryphe version 1.0.0

Checks if a timestamp is recent based on a provided age off time

## Examples

### Age off in milliseconds


{% codetabs name="Java", type="java" -%}
final AgeOff function = new AgeOff(100000L);

{%- language name="JSON", type="json" -%}
{
  "class" : "AgeOff",
  "ageOffTime" : 100000
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.AgeOff",
  "ageOffTime" : 100000
}

{%- language name="Python", type="py" -%}
g.AgeOff( 
  age_off_time=100000 
)

{%- endcodetabs %}

Input type:

```
java.lang.Long
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Long</td></tr>
<tr><td>java.lang.Long</td><td>1632404065730</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1632403965730</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1632404165730</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>1632404065730</td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Long</td></tr>
</table>

-----------------------------------------------

