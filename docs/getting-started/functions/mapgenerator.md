# MapGenerator
See javadoc - [uk.gov.gchq.gaffer.data.generator.MapGenerator](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/generator/MapGenerator.html)

Available since Gaffer version 1.0.0

Converts an iterable of elements into an iterable of maps

## Examples

### Elements to map


{% codetabs name="Java", type="java" -%}
final MapGenerator function = new MapGenerator.Builder()
        .group("Group Label")
        .vertex("Vertex Label")
        .source("Source Label")
        .property("count", "Count Label")
        .constant("A Constant", "Some constant value")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "MapGenerator",
  "fields" : {
    "GROUP" : "Group Label",
    "VERTEX" : "Vertex Label",
    "SOURCE" : "Source Label",
    "count" : "Count Label"
  },
  "constants" : {
    "A Constant" : "Some constant value"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.data.generator.MapGenerator",
  "fields" : {
    "GROUP" : "Group Label",
    "VERTEX" : "Vertex Label",
    "SOURCE" : "Source Label",
    "count" : "Count Label"
  },
  "constants" : {
    "A Constant" : "Some constant value"
  }
}

{%- language name="Python", type="py" -%}
g.MapGenerator( 
  fields={'GROUP': 'Group Label', 'VERTEX': 'Vertex Label', 'SOURCE': 'Source Label', 'count': 'Count Label'}, 
  constants={'A Constant': 'Some constant value'} 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[Entity[vertex=vertex1,group=Foo,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Entity[vertex=vertex2,group=Foo,properties=Properties[]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[]]]</td><td>uk.gov.gchq.gaffer.data.generator.OneToOneObjectGenerator$1</td><td>[{Group Label=Foo, Vertex Label=vertex1, Count Label=1, A Constant=Some constant value}, {Group Label=Foo, Vertex Label=vertex2, A Constant=Some constant value}, {Group Label=Bar, Source Label=dest1, Count Label=1, A Constant=Some constant value}, {Group Label=Bar, Source Label=dest1, A Constant=Some constant value}]</td></tr>
</table>

-----------------------------------------------

