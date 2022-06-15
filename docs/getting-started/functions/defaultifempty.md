# DefaultIfEmpty
See javadoc - [uk.gov.gchq.koryphe.impl.function.DefaultIfEmpty](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/DefaultIfEmpty.html)

Available since Koryphe version 1.9.0

Provides a default value if the input is empty.

## Examples

### Run default if empty examples


{% codetabs name="Java", type="java" -%}
final DefaultIfEmpty function = new DefaultIfEmpty();

{%- language name="JSON", type="json" -%}
{
  "class" : "DefaultIfEmpty"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.DefaultIfEmpty"
}

{%- language name="Python", type="py" -%}
g.DefaultIfEmpty()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>String input</td><td>java.lang.String</td><td>String input</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td></td><td>IllegalArgumentException: Could not determine the size of the provided value</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.String</td><td></td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

