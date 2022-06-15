# MapFilter
See javadoc - [uk.gov.gchq.koryphe.impl.function.MapFilter](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/MapFilter.html)

Available since Koryphe version 1.6.0

A Function which applies the given predicates to the keys and/or values

## Examples

### Filter on keys

MapFilter with key predicate


{% codetabs name="Java", type="java" -%}
final MapFilter keyFilter = new MapFilter().keyPredicate(
        new StringContains("a")
);

{%- language name="JSON", type="json" -%}
{
  "class" : "MapFilter",
  "keyPredicate" : {
    "class" : "StringContains",
    "value" : "a",
    "ignoreCase" : false
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.MapFilter",
  "keyPredicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.StringContains",
    "value" : "a",
    "ignoreCase" : false
  }
}

{%- language name="Python", type="py" -%}
g.MapFilter( 
  key_predicate=g.StringContains( 
    value="a", 
    ignore_case=False 
  ) 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{giraffe=0, cat=3, dog=2}</td><td>java.util.HashMap</td><td>{giraffe=0, cat=3}</td></tr>
</table>

-----------------------------------------------

### Filter on values

MapFilter with value predicate


{% codetabs name="Java", type="java" -%}
final MapFilter valueFilter = new MapFilter().valuePredicate(
        new IsMoreThan(10)
);

{%- language name="JSON", type="json" -%}
{
  "class" : "MapFilter",
  "valuePredicate" : {
    "class" : "IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.MapFilter",
  "valuePredicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  }
}

{%- language name="Python", type="py" -%}
g.MapFilter( 
  value_predicate=g.IsMoreThan( 
    value=10, 
    or_equal_to=False 
  ) 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{Pizza=30, Casserole=4, Steak=12}</td><td>java.util.HashMap</td><td>{Pizza=30, Steak=12}</td></tr>
</table>

-----------------------------------------------

### Filter on both

MapFilter with key-value Predicate


{% codetabs name="Java", type="java" -%}
final MapFilter keyValueFilter = new MapFilter()
        .keyValuePredicate(new AreEqual());

{%- language name="JSON", type="json" -%}
{
  "class" : "MapFilter",
  "keyValuePredicate" : {
    "class" : "AreEqual"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.MapFilter",
  "keyValuePredicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.AreEqual"
  }
}

{%- language name="Python", type="py" -%}
g.MapFilter( 
  key_value_predicate=g.AreEqual() 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{1=2, 3=3, 6=4}</td><td>java.util.HashMap</td><td>{3=3}</td></tr>
</table>

-----------------------------------------------

