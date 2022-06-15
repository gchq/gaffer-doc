# IterableConcat
See javadoc - [uk.gov.gchq.koryphe.impl.function.IterableConcat](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/IterableConcat.html)

Available since Koryphe version 1.1.0

For a given Iterable of Iterables, an IterableConcat will essentially perform a FlatMap on the input, by concatenating each of the nested iterables into a single flattened iterable.

## Examples

### Concatenate nested iterables


{% codetabs name="Java", type="java" -%}
final IterableConcat<Integer> function = new IterableConcat<>();

{%- language name="JSON", type="json" -%}
{
  "class" : "IterableConcat"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.IterableConcat"
}

{%- language name="Python", type="py" -%}
g.IterableConcat()

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[[2, 3, 5], [7, 11, 13], [17, 19, 23]]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$ChainedIterable</td><td>[2, 3, 5, 7, 11, 13, 17, 19, 23]</td></tr>
<tr><td>java.util.ArrayList</td><td>[[29, 31, 37]]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$ChainedIterable</td><td>[29, 31, 37]</td></tr>
<tr><td>java.util.ArrayList</td><td>[[2, 3, 5], [7, 11, 13], null]</td><td></td><td>NullPointerException: null</td></tr>
<tr><td></td><td>null</td><td></td><td>IllegalArgumentException: iterables are required</td></tr>
</table>

-----------------------------------------------

