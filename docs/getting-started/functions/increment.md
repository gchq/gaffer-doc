# Increment
See javadoc - [uk.gov.gchq.koryphe.impl.function.Increment](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Increment.html)

Available since Koryphe version 1.8.0

Adds a given number to the input

## Examples

### Add to int

 returned value type will match the input type int


{% codetabs name="Java", type="java" -%}
final Increment increment = new Increment(3);

{%- language name="JSON", type="json" -%}
{
  "class" : "Increment",
  "increment" : 3
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Increment",
  "increment" : 3
}

{%- language name="Python", type="py" -%}
g.Increment( 
  increment=3 
)

{%- endcodetabs %}

Input type:

```
java.lang.Number
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.Double</td><td>2.0</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.Float</td><td>2.0</td><td>java.lang.Integer</td><td>5</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>java.lang.Integer</td><td>5</td></tr>
</table>

-----------------------------------------------

### Add to double

 returned value type will match the input type double


{% codetabs name="Java", type="java" -%}
final Increment increment = new Increment(3.0);

{%- language name="JSON", type="json" -%}
{
  "class" : "Increment",
  "increment" : 3.0
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Increment",
  "increment" : 3.0
}

{%- language name="Python", type="py" -%}
g.Increment( 
  increment=3.0 
)

{%- endcodetabs %}

Input type:

```
java.lang.Number
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>java.lang.Double</td><td>5.0</td></tr>
<tr><td>java.lang.Double</td><td>2.0</td><td>java.lang.Double</td><td>5.0</td></tr>
<tr><td>java.lang.Float</td><td>2.0</td><td>java.lang.Double</td><td>5.0</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>java.lang.Double</td><td>5.0</td></tr>
<tr><td>java.lang.String</td><td>33</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Number</td></tr>
<tr><td>java.lang.String</td><td>three</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Number</td></tr>
<tr><td></td><td>null</td><td>java.lang.Double</td><td>3.0</td></tr>
</table>

-----------------------------------------------

### Add to float

 returned value type will match the input type float


{% codetabs name="Java", type="java" -%}
final Increment increment = new Increment(3.0f);

{%- language name="JSON", type="json" -%}
{
  "class" : "Increment",
  "increment" : {
    "Float" : 3.0
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Increment",
  "increment" : {
    "java.lang.Float" : 3.0
  }
}

{%- language name="Python", type="py" -%}
g.Increment( 
  increment={'java.lang.Float': 3.0} 
)

{%- endcodetabs %}

Input type:

```
java.lang.Number
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>java.lang.Float</td><td>5.0</td></tr>
<tr><td>java.lang.Double</td><td>2.0</td><td>java.lang.Float</td><td>5.0</td></tr>
<tr><td>java.lang.Float</td><td>2.0</td><td>java.lang.Float</td><td>5.0</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>java.lang.Float</td><td>5.0</td></tr>
<tr><td>java.lang.String</td><td>33</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Number</td></tr>
<tr><td>java.lang.String</td><td>three</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Number</td></tr>
<tr><td></td><td>null</td><td>java.lang.Float</td><td>3.0</td></tr>
</table>

-----------------------------------------------

### Add to long

 returned value type will match the input type long


{% codetabs name="Java", type="java" -%}
final Increment increment = new Increment(3L);

{%- language name="JSON", type="json" -%}
{
  "class" : "Increment",
  "increment" : {
    "Long" : 3
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Increment",
  "increment" : {
    "java.lang.Long" : 3
  }
}

{%- language name="Python", type="py" -%}
g.Increment( 
  increment={'java.lang.Long': 3} 
)

{%- endcodetabs %}

Input type:

```
java.lang.Number
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td>java.lang.Double</td><td>2.0</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td>java.lang.Float</td><td>2.0</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>java.lang.Long</td><td>5</td></tr>
<tr><td>java.lang.String</td><td>33</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Number</td></tr>
<tr><td>java.lang.String</td><td>three</td><td></td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Number</td></tr>
<tr><td></td><td>null</td><td>java.lang.Long</td><td>3</td></tr>
</table>

-----------------------------------------------

