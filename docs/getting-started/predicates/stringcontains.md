# StringContains
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.StringContains](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/StringContains.html)

Available since Koryphe version 1.0.0

Checks if a string contains some value

## Examples

### String contains value

Note - the StringContains predicate is case sensitive by default, hence only exact matches are found.


{% codetabs name="Java", type="java" -%}
final StringContains function = new StringContains("test");

{%- language name="JSON", type="json" -%}
{
  "class" : "StringContains",
  "value" : "test",
  "ignoreCase" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.StringContains",
  "value" : "test",
  "ignoreCase" : false
}

{%- language name="Python", type="py" -%}
g.StringContains( 
  value="test", 
  ignore_case=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>This is a Test</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>Test</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>test</td><td>true</td></tr>
</table>

-----------------------------------------------

### String contains value ignore case

Here the optional flag is set to true - this disables case sensitivity.


{% codetabs name="Java", type="java" -%}
final StringContains function = new StringContains("test", true);

{%- language name="JSON", type="json" -%}
{
  "class" : "StringContains",
  "value" : "test",
  "ignoreCase" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.StringContains",
  "value" : "test",
  "ignoreCase" : true
}

{%- language name="Python", type="py" -%}
g.StringContains( 
  value="test", 
  ignore_case=True 
)

{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>This is a Test</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>Test</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>test</td><td>true</td></tr>
</table>

-----------------------------------------------

