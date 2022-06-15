# Product
See javadoc - [uk.gov.gchq.koryphe.impl.binaryoperator.Product](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Product.html)

Available since Koryphe version 1.0.0

Calculates the product of 2 numbers

## Examples

### Product


{% codetabs name="Java", type="java" -%}
final Product product = new Product();

{%- language name="JSON", type="json" -%}
{
  "class" : "Product"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Product"
}
{%- endcodetabs %}

Input type:

```
java.lang.Number
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Inputs</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>20 and 3</td><td>java.lang.Long</td><td>60</td></tr>
<tr><td>java.lang.Integer</td><td>300 and 400</td><td>java.lang.Integer</td><td>120000</td></tr>
<tr><td>java.lang.Double</td><td>0.0 and 3.0</td><td>java.lang.Double</td><td>0.0</td></tr>
<tr><td>java.lang.Short</td><td>50 and 50</td><td>java.lang.Short</td><td>2500</td></tr>
<tr><td>java.lang.Short</td><td>500 and 500</td><td>java.lang.Short</td><td>32767</td></tr>
<tr><td>java.lang.Integer</td><td>-5 and 5</td><td>java.lang.Integer</td><td>-25</td></tr>
<tr><td>java.lang.Long</td><td>20 and null</td><td>java.lang.Long</td><td>20</td></tr>
</table>

-----------------------------------------------

