# ToEntitySeeds
See javadoc - [uk.gov.gchq.gaffer.operation.impl.output.ToEntitySeeds](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToEntitySeeds.html)

Available since Gaffer version 1.0.0

Converts an objects into EntitySeeds

## Required fields
No required fields


## Examples

### To stream example

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
final OperationChain<Iterable<? extends EntitySeed>> opChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed(1), new EntitySeed(2))
                .build())
        .then(new ToEntitySeeds())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetElements",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 1
    }, {
      "class" : "EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "ToEntitySeeds"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToEntitySeeds"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex=1 
        ), 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.ToEntitySeeds() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]]
EntitySeed[vertex=Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]]
EntitySeed[vertex=Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
EntitySeed[vertex=Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
EntitySeed[vertex=Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]]
EntitySeed[vertex=Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]]
EntitySeed[vertex=Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]]
EntitySeed[vertex=Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Entity" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Edge" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 3,
      "directed" : true,
      "matchedVertex" : "SOURCE",
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Edge" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 4,
      "directed" : true,
      "matchedVertex" : "SOURCE",
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Edge" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 5,
      "directed" : true,
      "matchedVertex" : "SOURCE",
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Edge" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 2,
      "directed" : true,
      "matchedVertex" : "DESTINATION",
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Entity" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Edge" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 2,
      "directed" : true,
      "matchedVertex" : "SOURCE",
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : {
    "uk.gov.gchq.gaffer.data.element.Edge" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 4,
      "directed" : true,
      "matchedVertex" : "SOURCE",
      "properties" : {
        "count" : 1
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

