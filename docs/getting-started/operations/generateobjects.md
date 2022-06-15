# GenerateObjects
See javadoc - [uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateObjects.html)

Available since Gaffer version 1.0.0

Generates objects from elements using provided generators

## Required fields
The following fields are required: 
- elementGenerator


## Examples

### Generate strings from elements

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
final GenerateObjects<String> operation = new GenerateObjects.Builder<String>()
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
        .generator(new ObjectGenerator())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GenerateObjects",
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
  "elementGenerator" : {
    "class" : "ObjectGenerator"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects",
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
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.generator.ObjectGenerator"
  }
}

{%- language name="Python", type="py" -%}
g.GenerateObjects( 
  element_generator=g.ElementGenerator( 
    class_name="uk.gov.gchq.gaffer.doc.operation.generator.ObjectGenerator", 
    fields={'class': 'uk.gov.gchq.gaffer.doc.operation.generator.ObjectGenerator'} 
  ), 
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
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
6,1
5,6,1

{%- language name="JSON", type="json" -%}
[ "6,1", "5,6,1" ]
{%- endcodetabs %}

-----------------------------------------------

### Generate domain objects from elements

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
final GenerateObjects<Object> operation = new GenerateObjects.Builder<>()
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
        .generator(new DomainObjectGenerator())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GenerateObjects",
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
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateObjectsExample$DomainObjectGenerator"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects",
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
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateObjectsExample$DomainObjectGenerator"
  }
}

{%- language name="Python", type="py" -%}
g.GenerateObjects( 
  element_generator=g.ElementGenerator( 
    class_name="uk.gov.gchq.gaffer.doc.operation.GenerateObjectsExample$DomainObjectGenerator", 
    fields={'class': 'uk.gov.gchq.gaffer.doc.operation.GenerateObjectsExample$DomainObjectGenerator'} 
  ), 
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
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
GenerateObjectsExample.DomainObject1[a=6,c=1]
GenerateObjectsExample.DomainObject2[a=5,b=6,c=1]

{%- language name="JSON", type="json" -%}
[ {
  "a" : 6,
  "c" : 1
}, {
  "a" : 5,
  "b" : 6,
  "c" : 1
} ]
{%- endcodetabs %}

-----------------------------------------------

