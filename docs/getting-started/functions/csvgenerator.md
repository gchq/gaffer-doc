# CsvGenerator
See javadoc - [uk.gov.gchq.gaffer.data.generator.CsvGenerator](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/CsvGenerator.html)

Available since Gaffer version 1.0.0

Converts an iterable of elements into an iterable of csvs

## Examples

### Elements to csv


{% codetabs name="Java", type="java" -%}
final CsvGenerator function = new CsvGenerator.Builder()
        .group("Group Label")
        .vertex("Vertex Label")
        .source("Source Label")
        .property("count", "Count Label")
        .constant("A Constant", "Some constant value")
        .quoted(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "CsvGenerator",
  "fields" : {
    "GROUP" : "Group Label",
    "VERTEX" : "Vertex Label",
    "SOURCE" : "Source Label",
    "count" : "Count Label"
  },
  "constants" : {
    "A Constant" : "Some constant value"
  },
  "quoted" : false,
  "commaReplacement" : " "
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
  "fields" : {
    "GROUP" : "Group Label",
    "VERTEX" : "Vertex Label",
    "SOURCE" : "Source Label",
    "count" : "Count Label"
  },
  "constants" : {
    "A Constant" : "Some constant value"
  },
  "quoted" : false,
  "commaReplacement" : " "
}

{%- language name="Python", type="py" -%}
g.CsvGenerator( 
  fields={'GROUP': 'Group Label', 'VERTEX': 'Vertex Label', 'SOURCE': 'Source Label', 'count': 'Count Label'}, 
  constants={'A Constant': 'Some constant value'}, 
  quoted=False, 
  comma_replacement=" " 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[Entity[vertex=vertex1,group=Foo,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Entity[vertex=vertex2,group=Foo,properties=Properties[]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[]]]</td><td>uk.gov.gchq.gaffer.data.generator.OneToOneObjectGenerator$1</td><td>[Foo,vertex1,,1,A Constant, Foo,vertex2,,,A Constant, Bar,,dest1,1,A Constant, Bar,,dest1,,A Constant]</td></tr>
</table>

-----------------------------------------------

### Elements to quoted csv


{% codetabs name="Java", type="java" -%}
final CsvGenerator function = new CsvGenerator.Builder()
        .group("Group Label")
        .vertex("Vertex Label")
        .source("Source Label")
        .property("count", "Count Label")
        .constant("A Constant", "Some constant value")
        .quoted(true)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "CsvGenerator",
  "fields" : {
    "GROUP" : "Group Label",
    "VERTEX" : "Vertex Label",
    "SOURCE" : "Source Label",
    "count" : "Count Label"
  },
  "constants" : {
    "A Constant" : "Some constant value"
  },
  "quoted" : true,
  "commaReplacement" : " "
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
  "fields" : {
    "GROUP" : "Group Label",
    "VERTEX" : "Vertex Label",
    "SOURCE" : "Source Label",
    "count" : "Count Label"
  },
  "constants" : {
    "A Constant" : "Some constant value"
  },
  "quoted" : true,
  "commaReplacement" : " "
}

{%- language name="Python", type="py" -%}
g.CsvGenerator( 
  fields={'GROUP': 'Group Label', 'VERTEX': 'Vertex Label', 'SOURCE': 'Source Label', 'count': 'Count Label'}, 
  constants={'A Constant': 'Some constant value'}, 
  quoted=True, 
  comma_replacement=" " 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[Entity[vertex=vertex1,group=Foo,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Entity[vertex=vertex2,group=Foo,properties=Properties[]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[]]]</td><td>uk.gov.gchq.gaffer.data.generator.OneToOneObjectGenerator$1</td><td>[&quot;Foo&quot;,&quot;vertex1&quot;,,&quot;1&quot;,&quot;A Constant&quot;, &quot;Foo&quot;,&quot;vertex2&quot;,,,&quot;A Constant&quot;, &quot;Bar&quot;,,&quot;dest1&quot;,&quot;1&quot;,&quot;A Constant&quot;, &quot;Bar&quot;,,&quot;dest1&quot;,,&quot;A Constant&quot;]</td></tr>
</table>

-----------------------------------------------

