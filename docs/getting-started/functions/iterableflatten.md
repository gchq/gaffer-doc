# IterableFlatten
See javadoc - [uk.gov.gchq.koryphe.impl.function.IterableFlatten](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/IterableFlatten.html)

Available since Koryphe version 1.9.0

Combines the items in an iterable into a single item based on the supplied operator.

## Examples

### With no binary operator


{% codetabs name="Java", type="java" -%}
final IterableFlatten function = new IterableFlatten(null);

{%- language name="JSON", type="json" -%}
{
  "class" : "IterableFlatten"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.IterableFlatten"
}

{%- language name="Python", type="py" -%}
g.IterableFlatten()

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[a, b, c]</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

### With a binary operator


{% codetabs name="Java", type="java" -%}
final IterableFlatten function = new IterableFlatten<>(new Max());

{%- language name="JSON", type="json" -%}
{
  "class" : "IterableFlatten",
  "operator" : {
    "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.IterableFlatten",
  "operator" : {
    "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
  }
}

{%- language name="Python", type="py" -%}
g.IterableFlatten( 
  operator=g.Max() 
)

{%- endcodetabs %}

Input type:

```
java.lang.Iterable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3, 1]</td><td>java.lang.Integer</td><td>3</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, null, 6]</td><td>java.lang.Integer</td><td>6</td></tr>
<tr><td>java.util.ArrayList</td><td>[]</td><td></td><td>null</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

