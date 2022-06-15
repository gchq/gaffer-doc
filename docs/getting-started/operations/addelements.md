# AddElements
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html)

Available since Gaffer version 1.0.0

Adds elements

## Required fields
No required fields


## Examples

### Add elements

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
new AddElements.Builder()
                .input(new Entity.Builder()
                                .group("entity")
                                .vertex(6)
                                .property("count", 1)
                                .build(),
                        new Edge.Builder()
                                .group("edge")
                                .source(5).dest(6).directed(true)
                                .property("count", 1)
                                .build())
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddElements",
  "input" : [ {
    "class" : "Entity",
    "group" : "entity",
    "vertex" : 6,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "Edge",
    "group" : "edge",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  } ],
  "skipInvalidElements" : false,
  "validate" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 6,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  } ],
  "skipInvalidElements" : false,
  "validate" : true
}

{%- language name="Python", type="py" -%}
g.AddElements( 
  input=[ 
    g.Entity( 
      group="entity", 
      properties={'count': 1}, 
      vertex=6 
    ), 
    g.Edge( 
      group="edge", 
      properties={'count': 1}, 
      source=5, 
      destination=6, 
      directed=True 
    ) 
  ], 
  skip_invalid_elements=False, 
  validate=True 
)

{%- endcodetabs %}

Updated graph:
```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5  -->  6
```
