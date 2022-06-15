# IterableFunction
See javadoc - [uk.gov.gchq.koryphe.impl.function.IterableFunction](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/IterableFunction.html)

Available since Koryphe version 1.1.0

An IterableFunction is useful for applying a provided function, or functions, to each entry of a supplied Iterable.

## Examples

### Apply function iteratively


{% codetabs name="Java", type="java" -%}
final IterableFunction<Integer, Integer> function = new IterableFunction<>(new MultiplyBy(2));

{%- language name="JSON", type="json" -%}
{
  "class" : "IterableFunction",
  "functions" : [ {
    "class" : "MultiplyBy",
    "by" : 2
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.IterableFunction",
  "functions" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.function.MultiplyBy",
    "by" : 2
  } ]
}

{%- language name="Python", type="py" -%}
g.IterableFunction( 
  functions=[ 
    g.MultiplyBy( 
      by=2 
    ) 
  ] 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[2, 4, 6]</td></tr>
<tr><td>java.util.ArrayList</td><td>[5, 10, 15]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[10, 20, 30]</td></tr>
<tr><td>java.util.ArrayList</td><td>[7, 9, 11]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[14, 18, 22]</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, null, 3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[2, null, 6]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### Apply multiple functions

Here we build a chain of functions using the IterableFunction's Builder, whereby the output of one function is the input to the next.


{% codetabs name="Java", type="java" -%}
final IterableFunction<Integer, Integer> function = new IterableFunction.Builder<Integer>()
        .first(new MultiplyBy(2))
        .then(new MultiplyBy(4))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "IterableFunction",
  "functions" : [ {
    "class" : "MultiplyBy",
    "by" : 2
  }, {
    "class" : "MultiplyBy",
    "by" : 4
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.IterableFunction",
  "functions" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.function.MultiplyBy",
    "by" : 2
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.function.MultiplyBy",
    "by" : 4
  } ]
}

{%- language name="Python", type="py" -%}
g.IterableFunction( 
  functions=[ 
    g.MultiplyBy( 
      by=2 
    ), 
    g.MultiplyBy( 
      by=4 
    ) 
  ] 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[2, 4, 10]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[16, 32, 80]</td></tr>
<tr><td>java.util.ArrayList</td><td>[3, 9, 11]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[24, 72, 88]</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, null, 3]</td><td>uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable</td><td>[8, null, 24]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

