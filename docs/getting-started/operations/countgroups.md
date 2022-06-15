# CountGroups
See javadoc - [uk.gov.gchq.gaffer.operation.impl.CountGroups](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/CountGroups.html)

Available since Gaffer version 1.0.0

Counts the different element groups

## Required fields
No required fields


## Examples

### Count all element groups

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new CountGroups())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "CountGroups"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.CountGroups"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.CountGroups() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
GroupCounts[entityGroups={entity=5},edgeGroups={edge=6},limitHit=false]

{%- language name="JSON", type="json" -%}
{
  "entityGroups" : {
    "entity" : 5
  },
  "edgeGroups" : {
    "edge" : 6
  },
  "limitHit" : false
}
{%- endcodetabs %}

-----------------------------------------------

### Count all element groups with limit

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new CountGroups(5))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "CountGroups",
    "limit" : 5
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.CountGroups",
    "limit" : 5
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.CountGroups( 
      limit=5 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
GroupCounts[entityGroups={entity=2},edgeGroups={edge=3},limitHit=true]

{%- language name="JSON", type="json" -%}
{
  "entityGroups" : {
    "entity" : 2
  },
  "edgeGroups" : {
    "edge" : 3
  },
  "limitHit" : true
}
{%- endcodetabs %}

-----------------------------------------------

