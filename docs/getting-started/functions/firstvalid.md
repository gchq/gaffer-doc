# FirstValid
See javadoc - [uk.gov.gchq.koryphe.impl.function.FirstValid](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/FirstValid.html)

Available since Koryphe version 1.9.0

Provides the first valid item from an iterable based on a predicate.

## Examples

### With a predicate


{% codetabs name="Java", type="java" -%}
final FirstValid function = new FirstValid(new StringContains("my"));

{%- language name="JSON", type="json" -%}
{
  "class" : "FirstValid",
  "predicate" : {
    "class" : "StringContains",
    "value" : "my",
    "ignoreCase" : false
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.FirstValid",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.StringContains",
    "value" : "my",
    "ignoreCase" : false
  }
}

{%- language name="Python", type="py" -%}
g.FirstValid( 
  predicate=g.StringContains( 
    value="my", 
    ignore_case=False 
  ) 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td></td><td>ClassCastException: java.lang.Integer cannot be cast to java.lang.String</td></tr>
<tr><td>java.util.ArrayList</td><td>[Hello, my, value]</td><td>java.lang.String</td><td>my</td></tr>
<tr><td>java.util.ArrayList</td><td>[MY, tummy, my, My]</td><td>java.lang.String</td><td>tummy</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### With no predicate

FirstValid always returns null if no predicate is specified


{% codetabs name="Java", type="java" -%}
final FirstValid function = new FirstValid(null);

{%- language name="JSON", type="json" -%}
{
  "class" : "FirstValid"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.FirstValid"
}

{%- language name="Python", type="py" -%}
g.FirstValid()

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[a, b, c]</td><td></td><td>null</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td></td><td>null</td></tr>
<tr><td>java.util.ArrayList</td><td>[]</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

