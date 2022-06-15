# IsShorterThan
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsShorterThan](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html)

Available since Koryphe version 1.0.0

Checks if the length of an input is more than than a value

## Examples

### Is shorter than 4


{% codetabs name="Java", type="java" -%}
final IsShorterThan function = new IsShorterThan(4);

{%- language name="JSON", type="json" -%}
{
  "class" : "IsShorterThan",
  "maxLength" : 4,
  "orEqualTo" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsShorterThan",
  "maxLength" : 4,
  "orEqualTo" : false
}

{%- language name="Python", type="py" -%}
g.IsShorterThan( 
  max_length=4, 
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
<tr><td>java.lang.String</td><td>123</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>1234</td><td>false</td></tr>
<tr><td>[Ljava.lang.Integer;</td><td>[1, 2, 3]</td><td>true</td></tr>
<tr><td>[Ljava.lang.Integer;</td><td>[1, 2, 3, 4]</td><td>false</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td>true</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3, 4]</td><td>false</td></tr>
<tr><td>java.util.HashMap</td><td>{1=a, 2=b, 3=c}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{4=d}</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>10000</td><td>IllegalArgumentException: Could not determine the size of the provided value</td></tr>
<tr><td>java.lang.Long</td><td>10000</td><td>IllegalArgumentException: Could not determine the size of the provided value</td></tr>
</table>

-----------------------------------------------

