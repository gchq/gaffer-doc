# ToTypeSubTypeValue
See javadoc - [uk.gov.gchq.gaffer.types.function.ToTypeSubTypeValue](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/types/function/ToTypeSubTypeValue.html)

Available since Gaffer version 1.8.0

Converts a value into a TypeSubTypeValue

## Examples

### To type sub type value


{% codetabs name="Java", type="java" -%}
Function toTypeSubTypeValue = new ToTypeSubTypeValue();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToTypeSubTypeValue"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.types.function.ToTypeSubTypeValue"
}

{%- language name="Python", type="py" -%}
g.ToTypeSubTypeValue()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>aString</td><td>uk.gov.gchq.gaffer.types.TypeSubTypeValue</td><td>TypeSubTypeValue[value=aString]</td></tr>
<tr><td>java.lang.Long</td><td>100</td><td>uk.gov.gchq.gaffer.types.TypeSubTypeValue</td><td>TypeSubTypeValue[value=100]</td></tr>
<tr><td>java.lang.Integer</td><td>25</td><td>uk.gov.gchq.gaffer.types.TypeSubTypeValue</td><td>TypeSubTypeValue[value=25]</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.TypeValue</td><td>TypeValue[type=type1,value=value1]</td><td>uk.gov.gchq.gaffer.types.TypeSubTypeValue</td><td>TypeSubTypeValue[value=TypeValue[type=type1,value=value1]]</td></tr>
<tr><td></td><td>null</td><td>uk.gov.gchq.gaffer.types.TypeSubTypeValue</td><td>TypeSubTypeValue[]</td></tr>
</table>

-----------------------------------------------

