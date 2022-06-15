# Or
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.Or](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)

Available since Koryphe version 1.0.0

Returns true if any of the predicates are true

## Examples

### Is less than 2 equal to 5 or is more than 10

When using an Or predicate with a single selected value you can just use the constructor new Or(predicates))'


{% codetabs name="Java", type="java" -%}
final Or function = new Or<>(
        new IsLessThan(2),
        new IsEqual(5),
        new IsMoreThan(10)
);

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "IsLessThan",
    "orEqualTo" : false,
    "value" : 2
  }, {
    "class" : "IsEqual",
    "value" : 5
  }, {
    "class" : "IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
    "orEqualTo" : false,
    "value" : 2
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
    "value" : 5
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  } ]
}

{%- language name="Python", type="py" -%}
g.Or( 
  predicates=[ 
    g.IsLessThan( 
      value=2, 
      or_equal_to=False 
    ), 
    g.IsEqual( 
      value=5 
    ), 
    g.IsMoreThan( 
      value=10, 
      or_equal_to=False 
    ) 
  ] 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>15</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

### Is less than 2 equal to 5 or is more than 10

When using an Or predicate with a single selected value you can just use the constructor new Or(predicates))'


{% codetabs name="Java", type="java" -%}
final Or function = new Or<>(
        new IsLessThan(2),
        new IsEqual(5),
        new IsMoreThan(10)
);

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "IsLessThan",
    "orEqualTo" : false,
    "value" : 2
  }, {
    "class" : "IsEqual",
    "value" : 5
  }, {
    "class" : "IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
    "orEqualTo" : false,
    "value" : 2
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
    "value" : 5
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  } ]
}

{%- language name="Python", type="py" -%}
g.Or( 
  predicates=[ 
    g.IsLessThan( 
      value=2, 
      or_equal_to=False 
    ), 
    g.IsEqual( 
      value=5 
    ), 
    g.IsMoreThan( 
      value=10, 
      or_equal_to=False 
    ) 
  ] 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>15</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

### First item is less than 2 or second item is more than 10

When using an Or predicate with multiple selected values, you need to use the Or.Builder to build your Or predicate, using .select() then .execute(). When selecting values in the Or.Builder you need to refer to the position in the input array. I.e to use the first value use position 0 - select(0).You can select multiple values to give to a predicate like isXLessThanY, this is achieved by passing 2 positions to the select method - select(0, 1)


{% codetabs name="Java", type="java" -%}
final Or function = new Or.Builder()
        .select(0)
        .execute(new IsLessThan(2))
        .select(1)
        .execute(new IsMoreThan(10))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "IntegerTupleAdaptedPredicate",
    "selection" : [ 0 ],
    "predicate" : {
      "class" : "IsLessThan",
      "orEqualTo" : false,
      "value" : 2
    }
  }, {
    "class" : "IntegerTupleAdaptedPredicate",
    "selection" : [ 1 ],
    "predicate" : {
      "class" : "IsMoreThan",
      "orEqualTo" : false,
      "value" : 10
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
    "selection" : [ 0 ],
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
      "orEqualTo" : false,
      "value" : 2
    }
  }, {
    "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
    "selection" : [ 1 ],
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
      "orEqualTo" : false,
      "value" : 10
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.Or( 
  predicates=[ 
    g.NestedPredicate( 
      selection=[ 
        0 
      ], 
      predicate=g.IsLessThan( 
        value=2, 
        or_equal_to=False 
      ) 
    ), 
    g.NestedPredicate( 
      selection=[ 
        1 
      ], 
      predicate=g.IsMoreThan( 
        value=10, 
        or_equal_to=False 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 15]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 1]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[15, 15]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[15, 1]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 15]</td><td>false</td></tr>
<tr><td>[java.lang.Integer]</td><td>[1]</td><td>true</td></tr>
</table>

-----------------------------------------------

