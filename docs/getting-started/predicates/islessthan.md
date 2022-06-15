# IsLessThan
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsLessThan](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsLessThan.html)

Available since Koryphe version 1.0.0

Checks if a comparable is less than a provided value

## Examples

### Is less than 5


{% codetabs name="Java", type="java" -%}
final IsLessThan function = new IsLessThan(5);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsLessThan",
  "orEqualTo" : false,
  "value" : 5
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : false,
  "value" : 5
}

{%- language name="Python", type="py" -%}
g.IsLessThan( 
  value=5, 
  or_equal_to=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------

### Is less than or equal to 5


{% codetabs name="Java", type="java" -%}
final IsLessThan function = new IsLessThan(5, true);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsLessThan",
  "orEqualTo" : true,
  "value" : 5
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : true,
  "value" : 5
}

{%- language name="Python", type="py" -%}
g.IsLessThan( 
  value=5, 
  or_equal_to=True 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------

### Is less than a long 5


{% codetabs name="Java", type="java" -%}
final IsLessThan function = new IsLessThan(5L);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsLessThan",
  "orEqualTo" : false,
  "value" : {
    "Long" : 5
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : false,
  "value" : {
    "java.lang.Long" : 5
  }
}

{%- language name="Python", type="py" -%}
g.IsLessThan( 
  value={'java.lang.Long': 5}, 
  or_equal_to=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------

### Is less than a string


{% codetabs name="Java", type="java" -%}
final IsLessThan function = new IsLessThan("B");

{%- language name="JSON", type="json" -%}
{
  "class" : "IsLessThan",
  "orEqualTo" : false,
  "value" : "B"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : false,
  "value" : "B"
}

{%- language name="Python", type="py" -%}
g.IsLessThan( 
  value="B", 
  or_equal_to=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>A</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>B</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>C</td><td>false</td></tr>
</table>

-----------------------------------------------

