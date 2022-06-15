# CsvLinesToMaps
See javadoc - [uk.gov.gchq.koryphe.impl.function.CsvLinesToMaps](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/CsvLinesToMaps.html)

Available since Koryphe version 1.8.0

Parses CSV lines into Maps

## Examples

### Cst to map deliminator


{% codetabs name="Java", type="java" -%}
final CsvLinesToMaps function = new CsvLinesToMaps()
        .header("header1", "header2", "header3")
        .firstRow(1)
        .delimiter('|');

{%- language name="JSON", type="json" -%}
{
  "class" : "CsvLinesToMaps",
  "header" : [ "header1", "header2", "header3" ],
  "firstRow" : 1,
  "delimiter" : "|"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CsvLinesToMaps",
  "header" : [ "header1", "header2", "header3" ],
  "firstRow" : 1,
  "delimiter" : "|"
}

{%- language name="Python", type="py" -%}
g.CsvLinesToMaps( 
  delimiter="|", 
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
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1|header2|header3, value1|value2|value3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}]</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1|header2|header3, value1||value3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=, header1=value1}]</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1|header2|header3, value1|value2]</td><td></td><td>IllegalArgumentException: CSV has 2 columns, but there are 3 provided column names</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1||header3, value1|value2|value3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}]</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1|header2, value1|value2|value3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}]</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1|header2|header3, value1|value2|value3, value4|value5|value6]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}, {header3=value6, header2=value5, header1=value4}]</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1|header2|header3, , value4|value5|value6]</td><td></td><td>NoSuchElementException: No more CSV records available</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[header1|header2|header3, null, value4|value5|value6]</td><td></td><td>NullPointerException: null</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[value1|value2|value3, value4|value5|value6]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}, {header3=value6, header2=value5, header1=value4}]</td></tr>
<tr><td>java.util.Arrays$ArrayList</td><td>[null, value1|value2|value3, value4|value5|value6]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[{header3=value3, header2=value2, header1=value1}, {header3=value6, header2=value5, header1=value4}]</td></tr>
</table>

-----------------------------------------------

