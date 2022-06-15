# DefaultIfNull
See javadoc - [uk.gov.gchq.koryphe.impl.function.DefaultIfNull](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/DefaultIfNull.html)

Available since Koryphe version 1.9.0

Provides a default value if the input is null.

## Examples

### With no default value set


{% codetabs name="Java", type="java" -%}
final DefaultIfNull function = new DefaultIfNull();

{%- language name="JSON", type="json" -%}
{
  "class" : "DefaultIfNull"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.DefaultIfNull"
}

{%- language name="Python", type="py" -%}
g.DefaultIfNull()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>String input</td><td>java.lang.String</td><td>String input</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.String</td><td></td></tr>
</table>

-----------------------------------------------

### With a default value set


{% codetabs name="Java", type="java" -%}
final DefaultIfNull function = new DefaultIfNull("DEFAULT");

{%- language name="JSON", type="json" -%}
{
  "class" : "DefaultIfNull",
  "defaultValue" : "DEFAULT"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.DefaultIfNull",
  "defaultValue" : "DEFAULT"
}

{%- language name="Python", type="py" -%}
g.DefaultIfNull( 
  default_value="DEFAULT" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>String input</td><td>java.lang.String</td><td>String input</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td></td><td>null</td><td>java.lang.String</td><td>DEFAULT</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.String</td><td></td></tr>
</table>

-----------------------------------------------

