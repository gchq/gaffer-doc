# CurrentTime
See javadoc - [uk.gov.gchq.koryphe.impl.function.CurrentTime](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/CurrentTime.html)

Available since Koryphe version 1.8.0

Returns the current time in milliseconds

## Examples

### Get current time


{% codetabs name="Java", type="java" -%}
final CurrentTime currentTime = new CurrentTime();

{%- language name="JSON", type="json" -%}
{
  "class" : "CurrentTime"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CurrentTime"
}

{%- language name="Python", type="py" -%}
g.CurrentTime()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td></td><td>null</td><td>java.lang.Long</td><td>1632404104742</td></tr>
</table>

-----------------------------------------------

