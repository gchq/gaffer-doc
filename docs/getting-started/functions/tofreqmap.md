# ToFreqMap
See javadoc - [uk.gov.gchq.gaffer.types.function.ToFreqMap](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/types/function/ToFreqMap.html)

Available since Gaffer version 1.8.0

Creates a new FreqMap and upserts a given value

## Examples

### To freq map


{% codetabs name="Java", type="java" -%}
Function toFreqMap = new ToFreqMap();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToFreqMap"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.types.function.ToFreqMap"
}

{%- language name="Python", type="py" -%}
g.ToFreqMap()

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>aString</td><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{aString=1}</td></tr>
<tr><td>java.lang.Long</td><td>100</td><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{100=1}</td></tr>
<tr><td>java.lang.Integer</td><td>20</td><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{20=1}</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.TypeValue</td><td>TypeValue[type=type1,value=value1]</td><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{TypeValue[type=type1,value=value1]=1}</td></tr>
<tr><td></td><td>null</td><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{null=1}</td></tr>
</table>

-----------------------------------------------

