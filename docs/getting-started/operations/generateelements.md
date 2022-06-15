# GenerateElements
See javadoc - [uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateElements.html)

Available since Gaffer version 1.0.0

Generates elements from objects using provided generators

## Required fields
The following fields are required: 
- elementGenerator


## Examples

### Generate elements from strings

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
final GenerateElements<String> operation = new GenerateElements.Builder<String>()
        .input("1,1", "1,2,1")
        .generator(new ElementGenerator())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GenerateElements",
  "input" : [ "1,1", "1,2,1" ],
  "elementGenerator" : {
    "class" : "ElementGenerator"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements",
  "input" : [ "1,1", "1,2,1" ],
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator"
  }
}

{%- language name="Python", type="py" -%}
g.GenerateElements( 
  element_generator=g.ElementGenerator( 
    class_name="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
    fields={'class': 'uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator'} 
  ), 
  input=[ 
    "1,1", 
    "1,2,1" 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Generate elements from domain objects

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
final GenerateElements<Object> operation = new GenerateElements.Builder<>()
        .input(new DomainObject1(1, 1),
                new DomainObject2(1, 2, 1))
        .generator(new DomainObjectGenerator())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GenerateElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject1",
    "a" : 1,
    "c" : 1
  }, {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject2",
    "a" : 1,
    "b" : 2,
    "c" : 1
  } ],
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObjectGenerator"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject1",
    "a" : 1,
    "c" : 1
  }, {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject2",
    "a" : 1,
    "b" : 2,
    "c" : 1
  } ],
  "elementGenerator" : {
    "class" : "uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObjectGenerator"
  }
}

{%- language name="Python", type="py" -%}
g.GenerateElements( 
  element_generator=g.ElementGenerator( 
    class_name="uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObjectGenerator", 
    fields={'class': 'uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObjectGenerator'} 
  ), 
  input=[ 
    {'class': 'uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject1', 'a': 1, 'c': 1}, 
    {'class': 'uk.gov.gchq.gaffer.doc.operation.GenerateElementsExample$DomainObject2', 'a': 1, 'b': 2, 'c': 1} 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

