# StringTruncate
See javadoc - [uk.gov.gchq.koryphe.impl.function.StringTruncate](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/StringTruncate.html)

Available since Koryphe version 1.9.0

Truncates a string, with optional ellipses.

## Examples

### With no ellipses


{% codetabs name="Java", type="java" -%}
final StringTruncate function = new StringTruncate(5, false);

{%- language name="JSON", type="json" -%}
{
  "class" : "StringTruncate",
  "length" : 5,
  "ellipses" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringTruncate",
  "length" : 5,
  "ellipses" : false
}

{%- language name="Python", type="py" -%}
g.StringTruncate( 
  length=5, 
  ellipses=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>no more than five</td><td>java.lang.String</td><td>no mo</td></tr>
<tr><td>java.lang.String</td><td>four</td><td>java.lang.String</td><td>four</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.String</td><td></td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Long</td><td>54</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

### With ellipses


{% codetabs name="Java", type="java" -%}
final StringTruncate function = new StringTruncate(5, true);

{%- language name="JSON", type="json" -%}
{
  "class" : "StringTruncate",
  "length" : 5,
  "ellipses" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.StringTruncate",
  "length" : 5,
  "ellipses" : true
}

{%- language name="Python", type="py" -%}
g.StringTruncate( 
  length=5, 
  ellipses=True 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>no more than five</td><td>java.lang.String</td><td>no mo...</td></tr>
<tr><td>java.lang.String</td><td>four</td><td>java.lang.String</td><td>four</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.String</td><td></td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Long</td><td>54</td><td></td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------

