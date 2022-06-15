# MapContainsPredicate
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.MapContainsPredicate](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContainsPredicate.html)

Available since Koryphe version 1.0.0

Checks if a map contains a key that matches a predicate

## Examples

### Map contains predicate


{% codetabs name="Java", type="java" -%}
final MapContainsPredicate function = new MapContainsPredicate(new Regex("a.*"));

{%- language name="JSON", type="json" -%}
{
  "class" : "MapContainsPredicate",
  "keyPredicate" : {
    "class" : "Regex",
    "value" : {
      "java.util.regex.Pattern" : "a.*"
    }
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.MapContainsPredicate",
  "keyPredicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.Regex",
    "value" : {
      "java.util.regex.Pattern" : "a.*"
    }
  }
}

{%- language name="Python", type="py" -%}
g.MapContainsPredicate( 
  key_predicate=g.Regex( 
    value={'java.util.regex.Pattern': 'a.*'} 
  ) 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{a1=1, a2=2, b=2, c=3}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{b=2, c=3}</td><td>false</td></tr>
<tr><td>java.util.HashMap</td><td>{a=null, b=2, c=3}</td><td>true</td></tr>
</table>

-----------------------------------------------

