# IsA
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsA](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)

Available since Koryphe version 1.0.0

Checks if an input is an instance of a class

## Examples

### Is a string


{% codetabs name="Java", type="java" -%}
final IsA function = new IsA(String.class);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsA",
  "type" : "java.lang.String"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsA",
  "type" : "java.lang.String"
}

{%- language name="Python", type="py" -%}
g.IsA( 
  type="java.lang.String" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Double</td><td>2.5</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>true</td></tr>
</table>

-----------------------------------------------

### Is a number


{% codetabs name="Java", type="java" -%}
final IsA function = new IsA(Number.class);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsA",
  "type" : "java.lang.Number"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsA",
  "type" : "java.lang.Number"
}

{%- language name="Python", type="py" -%}
g.IsA( 
  type="java.lang.Number" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Double</td><td>2.5</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>false</td></tr>
</table>

-----------------------------------------------

