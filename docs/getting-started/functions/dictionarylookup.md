# DictionaryLookup
See javadoc - [uk.gov.gchq.koryphe.impl.function.DictionaryLookup](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/DictionaryLookup.html)

Available since Koryphe version 1.7.0

looks up a value in a Map

## Examples

### Dictionary lookup example


{% codetabs name="Java", type="java" -%}
final DictionaryLookup<Integer, String> dictionaryLookup = new DictionaryLookup<>(map);

{%- language name="JSON", type="json" -%}
{
  "class" : "DictionaryLookup",
  "dictionary" : {
    "1" : "one",
    "2" : "two",
    "3" : "three"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.DictionaryLookup",
  "dictionary" : {
    "1" : "one",
    "2" : "two",
    "3" : "three"
  }
}

{%- language name="Python", type="py" -%}
g.DictionaryLookup( 
  dictionary={'1': 'one', '2': 'two', '3': 'three'} 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>java.lang.String</td><td>one</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.Integer</td><td>4</td><td></td><td>null</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

