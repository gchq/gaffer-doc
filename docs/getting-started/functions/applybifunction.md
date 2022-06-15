# ApplyBiFunction
See javadoc - [uk.gov.gchq.koryphe.impl.function.ApplyBiFunction](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/ApplyBiFunction.html)

Available since Koryphe version 1.8.0

Applies the given BiFunction

## Examples

### Apply bi function using sum


{% codetabs name="Java", type="java" -%}
final ApplyBiFunction<Number, Number, Number> function = new ApplyBiFunction<>(new Sum());

{%- language name="JSON", type="json" -%}
{
  "class" : "ApplyBiFunction",
  "function" : {
    "class" : "Sum"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ApplyBiFunction",
  "function" : {
    "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
  }
}

{%- language name="Python", type="py" -%}
g.ApplyBiFunction( 
  function=g.Sum() 
)

{%- endcodetabs %}

Input type:

```
java.lang.Number, java.lang.Number
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td><td>java.lang.Integer</td><td>3</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]</td><td>[1, 2, 3, 4]</td><td>java.lang.Integer</td><td>3</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[1.1, 2.2]</td><td>java.lang.Double</td><td>3.3000000000000003</td></tr>
</table>

-----------------------------------------------

### Apply bi function using max


{% codetabs name="Java", type="java" -%}
final ApplyBiFunction<Comparable, Comparable, Comparable> function = new ApplyBiFunction<>(new Max());

{%- language name="JSON", type="json" -%}
{
  "class" : "ApplyBiFunction",
  "function" : {
    "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ApplyBiFunction",
  "function" : {
    "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
  }
}

{%- language name="Python", type="py" -%}
g.ApplyBiFunction( 
  function=g.Max() 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td><td>java.lang.Integer</td><td>2</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]</td><td>[1, 2, 3, 4]</td><td>java.lang.Integer</td><td>2</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[1.1, 2.2]</td><td>java.lang.Double</td><td>2.2</td></tr>
</table>

-----------------------------------------------

### Apply bi function using min


{% codetabs name="Java", type="java" -%}
final ApplyBiFunction<Comparable, Comparable, Comparable> function = new ApplyBiFunction<>(new Min());

{%- language name="JSON", type="json" -%}
{
  "class" : "ApplyBiFunction",
  "function" : {
    "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.ApplyBiFunction",
  "function" : {
    "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
  }
}

{%- language name="Python", type="py" -%}
g.ApplyBiFunction( 
  function=g.Min() 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td><td>java.lang.Integer</td><td>1</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]</td><td>[1, 2, 3, 4]</td><td>java.lang.Integer</td><td>1</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[1.1, 2.2]</td><td>java.lang.Double</td><td>1.1</td></tr>
</table>

-----------------------------------------------

