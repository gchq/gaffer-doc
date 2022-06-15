# If
See javadoc - [uk.gov.gchq.koryphe.impl.function.If](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/If.html)

Available since Koryphe version 1.5.0

Conditionally applies a function

## Examples

### Apply functions to input

This example tests first whether the input contains 'upper'. If so, then it is converted to upper case. Otherwise, it is converted to lower case


{% codetabs name="Java", type="java" -%}
final If<String, String> predicate = new If<String, String>()
        .predicate(new StringContains("upper"))
        .then(new ToUpperCase())
        .otherwise(new ToLowerCase());

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.If",
  "predicate" : {
    "class" : "StringContains",
    "value" : "upper",
    "ignoreCase" : false
  },
  "then" : {
    "class" : "ToUpperCase"
  },
  "otherwise" : {
    "class" : "ToLowerCase"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.If",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.StringContains",
    "value" : "upper",
    "ignoreCase" : false
  },
  "then" : {
    "class" : "uk.gov.gchq.koryphe.impl.function.ToUpperCase"
  },
  "otherwise" : {
    "class" : "uk.gov.gchq.koryphe.impl.function.ToLowerCase"
  }
}

{%- language name="Python", type="py" -%}
g.If( 
  predicate=g.StringContains( 
    value="upper", 
    ignore_case=False 
  ), 
  then=g.ToUpperCase(), 
  otherwise=g.ToLowerCase() 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
<tr><td>java.lang.String</td><td>Convert me to upper case</td><td>java.lang.String</td><td>CONVERT ME TO UPPER CASE</td></tr>
<tr><td>java.lang.String</td><td>Convert me to lower case</td><td>java.lang.String</td><td>convert me to lower case</td></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.String</td><td></td></tr>
</table>

-----------------------------------------------

