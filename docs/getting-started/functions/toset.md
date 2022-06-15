# ToSet
See javadoc - [uk.gov.gchq.koryphe.impl.function.ToSet](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ToSet.html)

Available since Koryphe version 1.6.0

Converts an Object to a Set

## Examples

### To list


{% codetabs name="Java", type="java" -%}
final ToSet function = new ToSet();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToSet"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ToSet"
}

{%- language name="Python", type="py" -%}
g.ToSet()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>test</td><td>java.util.HashSet</td><td>[test]</td></tr>
<tr><td></td><td>null</td><td>java.util.HashSet</td><td>[null]</td></tr>
<tr><td>java.lang.Long</td><td>30</td><td>java.util.HashSet</td><td>[30]</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.TypeSubTypeValue</td><td>TypeSubTypeValue[type=t,subType=st,value=v]</td><td>java.util.HashSet</td><td>[TypeSubTypeValue[type=t,subType=st,value=v]]</td></tr>
<tr><td>[Ljava.lang.String;</td><td>[a, b, c]</td><td>java.util.HashSet</td><td>[a, b, c]</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[test1, test2]</td><td>java.util.HashSet</td><td>[test2, test1]</td></tr>
</table>

-----------------------------------------------

