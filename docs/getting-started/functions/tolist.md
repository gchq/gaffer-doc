# ToList
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToList](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToList.html)

Available since Koryphe version 1.6.0

Converts an Object to a List

## Examples

### To list


{% codetabs name="Java", type="java" -%}
final ToList function = new ToList();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToList"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToList"
}

{%- language name="Python", type="py" -%}
g.ToList()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>test</td><td>java.util.ArrayList</td><td>[test]</td></tr>
<tr><td></td><td>null</td><td>java.util.ArrayList</td><td>[null]</td></tr>
<tr><td>java.lang.Long</td><td>30</td><td>java.util.ArrayList</td><td>[30]</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.TypeSubTypeValue</td><td>TypeSubTypeValue[type=t,subType=st,value=v]</td><td>java.util.ArrayList</td><td>[TypeSubTypeValue[type=t,subType=st,value=v]]</td></tr>
<tr><td>[Ljava.lang.String;</td><td>[a, b, c]</td><td>java.util.ArrayList</td><td>[a, b, c]</td></tr>
<tr><td>java.util.HashSet</td><td>[1, 2]</td><td>java.util.ArrayList</td><td>[1, 2]</td></tr>
</table>

-----------------------------------------------

