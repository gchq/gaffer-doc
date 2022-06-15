# IsEqual
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsEqual](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)

Available since Koryphe version 1.0.0

Checks if an input is equal to a provided value

## Examples

### Is equal to 5


{% codetabs name="Java", type="java" -%}
final IsEqual function = new IsEqual(5);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsEqual",
  "value" : 5
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
  "value" : 5
}

{%- language name="Python", type="py" -%}
g.IsEqual( 
  value=5 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

### Is equal to string 5


{% codetabs name="Java", type="java" -%}
final IsEqual function = new IsEqual("5");

{%- language name="JSON", type="json" -%}
{
  "class" : "IsEqual",
  "value" : "5"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
  "value" : "5"
}

{%- language name="Python", type="py" -%}
g.IsEqual( 
  value="5" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

### Is equal long 5


{% codetabs name="Java", type="java" -%}
final IsEqual function = new IsEqual(5L);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsEqual",
  "value" : {
    "Long" : 5
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
  "value" : {
    "java.lang.Long" : 5
  }
}

{%- language name="Python", type="py" -%}
g.IsEqual( 
  value={'java.lang.Long': 5} 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

