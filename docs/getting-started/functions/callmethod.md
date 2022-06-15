# CallMethod
See javadoc - [uk.gov.gchq.koryphe.impl.function.CallMethod](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/CallMethod.html)

Available since Koryphe version 1.4.0

Allows you to call any public no-argument method on an object

## Examples

### Call to string


{% codetabs name="Java", type="java" -%}
final CallMethod function = new CallMethod("toString");

{%- language name="JSON", type="json" -%}
{
  "class" : "CallMethod",
  "method" : "toString"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CallMethod",
  "method" : "toString"
}

{%- language name="Python", type="py" -%}
g.CallMethod( 
  method="toString" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a string</td><td>java.lang.String</td><td>a string</td></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>java.lang.String</td><td>1</td></tr>
<tr><td>java.util.HashSet</td><td>[item2, item1]</td><td>java.lang.String</td><td>[item2, item1]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### Call to lower case


{% codetabs name="Java", type="java" -%}
final CallMethod function = new CallMethod("toLowerCase");

{%- language name="JSON", type="json" -%}
{
  "class" : "CallMethod",
  "method" : "toLowerCase"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CallMethod",
  "method" : "toLowerCase"
}

{%- language name="Python", type="py" -%}
g.CallMethod( 
  method="toLowerCase" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>STRING1</td><td>java.lang.String</td><td>string1</td></tr>
<tr><td>java.lang.String</td><td>String2</td><td>java.lang.String</td><td>string2</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td></td><td>RuntimeException: Unable to invoke toLowerCase on object class class java.lang.Integer</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

