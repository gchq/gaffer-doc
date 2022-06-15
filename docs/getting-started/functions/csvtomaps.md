# CsvToMaps
See javadoc - [uk.gov.gchq.koryphe.impl.function.CsvToMaps](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/CsvToMaps.html)

Available since Koryphe version 1.8.0

Parses a CSV into Maps

## Examples

### Csv to map


{% codetabs name="Java", type="java" -%}
final CsvToMaps function = new CsvToMaps().header("header1", "header2", "header3").firstRow(1);

{%- language name="JSON", type="json" -%}
{
  "class" : "CsvToMaps",
  "header" : [ "header1", "header2", "header3" ],
  "firstRow" : 1
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CsvToMaps",
  "header" : [ "header1", "header2", "header3" ],
  "firstRow" : 1
}

{%- language name="Python", type="py" -%}
g.CsvToMaps( 
  header=[ 
    "header1", 
    "header2", 
    "header3" 
  ], 
  first_row=1 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>header1,header2,header3
value1,value2,value3</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}]</td></tr>
<tr><td>java.lang.String</td><td>header1,header2,header3
value1,value2,value3
value4,value5,value6&quot;</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}, {header3=value6&quot;, header2=value5, header1=value4}]</td></tr>
<tr><td>java.lang.String</td><td>header1,header2,header3
,,value3
value4,value5,value6&quot;</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=, header1=}, {header3=value6&quot;, header2=value5, header1=value4}]</td></tr>
<tr><td>java.lang.String</td><td>header1,header2,header3,header4
value1,value2,value3,value4
value5,value6,value7,value8&quot;</td><td></td><td>NoSuchElementException: null</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.String</td><td></td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[]</td></tr>
</table>

-----------------------------------------------

