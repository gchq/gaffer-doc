# Generate Operations

These Operations are used for generating edges, elements and objects. They are always available.

This directed graph is used in all the examples on this page, except for GetWalks:

``` mermaid
graph TD
  1(1, count=3) -- count=3 --> 2
  1 -- count=1 --> 4
  2(2, count=1) -- count=2 --> 3
  2 -- count=1 --> 4(4, count=1)
  2 -- count=1 --> 5(5, count=3)
  3(3, count=2) -- count=4 --> 4
```

## GenerateElements

Generates elements from objects using provided generators. [Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateElements.html)

??? example "Example generating elements from Strings"

    === "Java"

        ``` java
        final GenerateElements<String> operation = new GenerateElements.Builder<String>()
                .input("1,1", "1,2,1")
                .generator(new ElementGenerator())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GenerateElements",
        "input" : [ "1,1", "1,2,1" ],
        "elementGenerator" : {
            "class" : "ElementGenerator"
        }
        }
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
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
        ```

??? example "Example generating elements from domain objects"

    === "Java"

        ``` java
        final GenerateElements<Object> operation = new GenerateElements.Builder<>()
                .input(new DomainObject1(1, 1),
                        new DomainObject2(1, 2, 1))
                .generator(new DomainObjectGenerator())
                .build();
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
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
        ```

## GenerateObjects

Generates objects from elements using provided generators. [Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/generate/GenerateObjects.html)

??? example "Example generating Strings from elements"

    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        6,1
        5,6,1
        ```

    === "JSON"

        ``` json
        [ "6,1", "5,6,1" ]
        ```

??? example "Example generating domain objects from elements"

    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        GenerateObjectsExample.DomainObject1[a=6,c=1]
        GenerateObjectsExample.DomainObject2[a=5,b=6,c=1]
        ```

    === "JSON"

        ``` json
        [ {
        "a" : 6,
        "c" : 1
        }, {
        "a" : 5,
        "b" : 6,
        "c" : 1
        } ]
        ```