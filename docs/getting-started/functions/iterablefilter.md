# IterableFilter
See javadoc - [uk.gov.gchq.koryphe.impl.function.IterableFilter](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/IterableFilter.html)

Available since Koryphe version 1.6.0

An IterableFilter applies a given predicate to each element in an Iterable and returns the filtered iterable

## Examples

### Iterable filter


{% codetabs name="Java", type="java" -%}
final IterableFilter<Integer> function = new IterableFilter<>(new IsMoreThan(5));

{%- language name="JSON", type="json" -%}
{
  "class" : "IterableFilter",
  "predicate" : {
    "class" : "IsMoreThan",
    "orEqualTo" : false,
    "value" : 5
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.IterableFilter",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 5
  }
}

{%- language name="Python", type="py" -%}
g.IterableFilter( 
  predicate=g.IsMoreThan( 
    value=5, 
    or_equal_to=False 
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
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable</td><td>[]</td></tr>
<tr><td>java.util.ArrayList</td><td>[5, 10, 15]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable</td><td>[10, 15]</td></tr>
<tr><td>java.util.ArrayList</td><td>[7, 9, 11]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable</td><td>[7, 9, 11]</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, null, 3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable</td><td>[]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

