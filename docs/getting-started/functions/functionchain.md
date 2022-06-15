# FunctionChain
See javadoc - [uk.gov.gchq.koryphe.impl.function.FunctionChain](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/FunctionChain.html)

Available since Koryphe version 1.8.0

Applies the given functions consecutively

## Examples

### Function chain using tuple adapted functions


{% codetabs name="Java", type="java" -%}
final FunctionChain function = new FunctionChain.Builder<>()
        .execute(new Integer[]{0}, new ToUpperCase(), new Integer[]{1})
        .execute(new Integer[]{1}, new ToSet(), new Integer[]{2})
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "FunctionChain",
  "functions" : [ {
    "class" : "TupleAdaptedFunction",
    "selection" : [ 0 ],
    "function" : {
      "class" : "ToUpperCase"
    },
    "projection" : [ 1 ]
  }, {
    "class" : "TupleAdaptedFunction",
    "selection" : [ 1 ],
    "function" : {
      "class" : "uk.gov.gchq.koryphe.impl.function.ToSet"
    },
    "projection" : [ 2 ]
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.FunctionChain",
  "functions" : [ {
    "class" : "uk.gov.gchq.koryphe.tuple.function.TupleAdaptedFunction",
    "selection" : [ 0 ],
    "function" : {
      "class" : "uk.gov.gchq.koryphe.impl.function.ToUpperCase"
    },
    "projection" : [ 1 ]
  }, {
    "class" : "uk.gov.gchq.koryphe.tuple.function.TupleAdaptedFunction",
    "selection" : [ 1 ],
    "function" : {
      "class" : "uk.gov.gchq.koryphe.impl.function.ToSet"
    },
    "projection" : [ 2 ]
  } ]
}

{%- language name="Python", type="py" -%}
g.FunctionChain( 
  functions=[ 
    g.TupleAdaptedFunction( 
      selection=[ 
        0 
      ], 
      function=g.ToUpperCase(), 
      projection=[ 
        1 
      ] 
    ), 
    g.TupleAdaptedFunction( 
      selection=[ 
        1 
      ], 
      function=g.ToSet(), 
      projection=[ 
        2 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>[java.lang.String,  ,]</td><td>[someString, null, null]</td><td>[java.lang.String, java.lang.String, java.util.HashSet]</td><td>[someString, SOMESTRING, [SOMESTRING]]</td></tr>
<tr><td>[java.lang.String,  ,]</td><td>[SOMESTRING, null, null]</td><td>[java.lang.String, java.lang.String, java.util.HashSet]</td><td>[SOMESTRING, SOMESTRING, [SOMESTRING]]</td></tr>
<tr><td>[java.lang.String,  ,]</td><td>[somestring, null, null]</td><td>[java.lang.String, java.lang.String, java.util.HashSet]</td><td>[somestring, SOMESTRING, [SOMESTRING]]</td></tr>
<tr><td>[java.lang.String,  ,]</td><td>[@&pound;$%, null, null]</td><td>[java.lang.String, java.lang.String, java.util.HashSet]</td><td>[@&pound;$%, @&pound;$%, [@&pound;$%]]</td></tr>
<tr><td>[java.lang.String,  ,]</td><td>[1234, null, null]</td><td>[java.lang.String, java.lang.String, java.util.HashSet]</td><td>[1234, 1234, [1234]]</td></tr>
<tr><td>[java.lang.String,  ,]</td><td>[, null, null]</td><td>[java.lang.String, java.lang.String, java.util.HashSet]</td><td>[, , []]</td></tr>
<tr><td>[ , ,]</td><td>[null, null, null]</td><td>[ , ,java.util.HashSet]</td><td>[null, null, [null]]</td></tr>
<tr><td>[java.lang.Integer,  ,]</td><td>[1234, null, null]</td><td>[java.lang.Integer, java.lang.String, java.util.HashSet]</td><td>[1234, 1234, [1234]]</td></tr>
</table>

-----------------------------------------------

### Function chain using standard functions


{% codetabs name="Java", type="java" -%}
final FunctionChain function = new FunctionChain.Builder<>()
        .execute(new ToLowerCase())
        .execute(new ToTypeSubTypeValue())
        .execute(new ToEntityId())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "FunctionChain",
  "functions" : [ {
    "class" : "ToLowerCase"
  }, {
    "class" : "ToTypeSubTypeValue"
  }, {
    "class" : "ToEntityId"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.FunctionChain",
  "functions" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.function.ToLowerCase"
  }, {
    "class" : "uk.gov.gchq.gaffer.types.function.ToTypeSubTypeValue"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.function.ToEntityId"
  } ]
}

{%- language name="Python", type="py" -%}
g.FunctionChain( 
  functions=[ 
    g.ToLowerCase(), 
    g.ToTypeSubTypeValue(), 
    g.ToEntityId() 
  ] 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a string</td><td>uk.gov.gchq.gaffer.operation.data.EntitySeed</td><td>EntitySeed[vertex=TypeSubTypeValue[value=a string]]</td></tr>
<tr><td>java.lang.String</td><td>UPPER</td><td>uk.gov.gchq.gaffer.operation.data.EntitySeed</td><td>EntitySeed[vertex=TypeSubTypeValue[value=upper]]</td></tr>
<tr><td></td><td>null</td><td>uk.gov.gchq.gaffer.operation.data.EntitySeed</td><td>EntitySeed[vertex=TypeSubTypeValue[]]</td></tr>
<tr><td>java.lang.Integer</td><td>12</td><td>uk.gov.gchq.gaffer.operation.data.EntitySeed</td><td>EntitySeed[vertex=TypeSubTypeValue[value=12]]</td></tr>
</table>

-----------------------------------------------

