# Or
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.Or](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Or.html)

Available since Koryphe version 1.0.0

Applies the logical OR operation to 2 booleans

## Examples

### Or with booleans


{% codetabs name="Java", type="java" -%}
final Or or = new Or();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Or"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Or"
}
{%- endcodetabs %}

Input type:

```
java.lang.Boolean
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Boolean</td><td>true and true</td><td>java.lang.Boolean</td><td>true</td></tr>
<tr><td>java.lang.Boolean</td><td>true and false</td><td>java.lang.Boolean</td><td>true</td></tr>
<tr><td>java.lang.Boolean</td><td>false and false</td><td>java.lang.Boolean</td><td>false</td></tr>
</table>

-----------------------------------------------

### Or with nulls


{% codetabs name="Java", type="java" -%}
final Or or = new Or();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Or"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Or"
}
{%- endcodetabs %}

Input type:

```
java.lang.Boolean
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Boolean</td><td>false and null</td><td>java.lang.Boolean</td><td>false</td></tr>
<tr><td>java.lang.Boolean</td><td>true and null</td><td>java.lang.Boolean</td><td>true</td></tr>
<tr><td></td><td>null and null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### Or with non boolean values


{% codetabs name="Java", type="java" -%}
final Or or = new Or();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Or"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Or"
}
{%- endcodetabs %}

Input type:

```
java.lang.Boolean
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>test and 3</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Boolean</td></tr>
<tr><td>java.lang.Integer</td><td>0 and 0</td><td></td><td>ClassCastException: java.lang.Integer cannot be cast to java.lang.Boolean</td></tr>
<tr><td>java.lang.Integer</td><td>1 and 0</td><td></td><td>ClassCastException: java.lang.Integer cannot be cast to java.lang.Boolean</td></tr>
</table>

-----------------------------------------------

