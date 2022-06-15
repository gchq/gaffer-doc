# And
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.And](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)

Available since Koryphe version 1.0.0

Returns true if all of its predicates are true

## Examples

### Is less than 3 and is more than 0


{% codetabs name="Java", type="java" -%}
final And function = new And<>(
        new IsLessThan(3),
        new IsMoreThan(0)
);

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
  "predicates" : [ {
    "class" : "IsLessThan",
    "orEqualTo" : false,
    "value" : 3
  }, {
    "class" : "IsMoreThan",
    "orEqualTo" : false,
    "value" : 0
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
    "orEqualTo" : false,
    "value" : 3
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 0
  } ]
}

{%- language name="Python", type="py" -%}
g.And( 
  predicates=[ 
    g.IsLessThan( 
      value=3, 
      or_equal_to=False 
    ), 
    g.IsMoreThan( 
      value=0, 
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
<tr><td>java.lang.Integer</td><td>0</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>false</td></tr>
</table>

-----------------------------------------------

### First item is less than 2 and second item is more than 5


{% codetabs name="Java", type="java" -%}
final And function = new And.Builder()
        .select(0)
        .execute(new IsLessThan(2))
        .select(1)
        .execute(new IsMoreThan(5))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
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
      "value" : 5
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
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
      "value" : 5
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.And( 
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
        value=5, 
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
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 10]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 1]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 10]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 1]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 10]</td><td>false</td></tr>
<tr><td>[java.lang.Integer]</td><td>[1]</td><td>false</td></tr>
</table>

-----------------------------------------------

