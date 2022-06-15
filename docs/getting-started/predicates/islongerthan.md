# IsLongerThan
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsLongerThan](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsLongerThan.html)

Available since Koryphe version 1.3.0

Checks if the length of an input is more than a value

## Examples

### Test inputs

This will test whether each input has a size/length attribute greater than 5.


{% codetabs name="Java", type="java" -%}
final IsLongerThan predicate = new IsLongerThan(5);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsLongerThan",
  "minLength" : 5,
  "orEqualTo" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLongerThan",
  "minLength" : 5,
  "orEqualTo" : false
}

{%- language name="Python", type="py" -%}
g.IsLongerThan( 
  min_length=5, 
  or_equal_to=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>testString</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>aTest</td><td>false</td></tr>
<tr><td>[Ljava.lang.String;</td><td>[null, null, null, null, null]</td><td>false</td></tr>
<tr><td>[Ljava.lang.String;</td><td>[null, null, null, null, null, null, null, null, null, null]</td><td>true</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[0, 1, 2, 3, 4, 5]</td><td>true</td></tr>
</table>

-----------------------------------------------

### Test inputs with equal to set

This will test whether each input has a size/length attribute greater than, OR equal to 5.


{% codetabs name="Java", type="java" -%}
final IsLongerThan predicate = new IsLongerThan(5, true);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsLongerThan",
  "minLength" : 5,
  "orEqualTo" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLongerThan",
  "minLength" : 5,
  "orEqualTo" : true
}

{%- language name="Python", type="py" -%}
g.IsLongerThan( 
  min_length=5, 
  or_equal_to=True 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>test</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>testString</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>aTest</td><td>true</td></tr>
<tr><td>[Ljava.lang.String;</td><td>[null, null, null, null, null]</td><td>true</td></tr>
<tr><td>[Ljava.lang.String;</td><td>[null, null, null, null, null, null, null, null, null, null]</td><td>true</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[0, 1, 2, 3, 4, 5]</td><td>true</td></tr>
</table>

-----------------------------------------------

