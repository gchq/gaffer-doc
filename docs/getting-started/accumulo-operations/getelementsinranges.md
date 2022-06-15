# GetElementsInRanges
See javadoc - [uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsInRanges](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/accumulostore/operation/impl/GetElementsInRanges.html)

Available since Gaffer version 1.0.0

Gets elements that have vertices within a given range

## Required fields
No required fields


## Examples

### Get all elements in the range from entity 1 to entity 4

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
final GetElementsInRanges operation = new GetElementsInRanges.Builder()
        .input(new Pair<>(new EntitySeed(1), new EntitySeed(4)))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElementsInRanges",
  "input" : [ {
    "class" : "Pair",
    "first" : {
      "EntitySeed" : {
        "class" : "EntitySeed",
        "vertex" : 1
      }
    },
    "second" : {
      "EntitySeed" : {
        "class" : "EntitySeed",
        "vertex" : 4
      }
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsInRanges",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.commonutil.pair.Pair",
    "first" : {
      "uk.gov.gchq.gaffer.operation.data.EntitySeed" : {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 1
      }
    },
    "second" : {
      "uk.gov.gchq.gaffer.operation.data.EntitySeed" : {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 4
      }
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElementsInRanges( 
  input=[ 
    g.SeedPair( 
      first=g.EntitySeed( 
        vertex=1 
      ), 
      second=g.EntitySeed( 
        vertex=4 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Edge[source=2,destination=3,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>4]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 4
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 4,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 4
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get all elements in the range from entity 4 to edge 4_5

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
final GetElementsInRanges operation = new GetElementsInRanges.Builder()
        .input(new Pair<>(new EntitySeed(4), new EdgeSeed(4, 5, DirectedType.EITHER)))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElementsInRanges",
  "input" : [ {
    "class" : "Pair",
    "first" : {
      "EntitySeed" : {
        "class" : "EntitySeed",
        "vertex" : 4
      }
    },
    "second" : {
      "EdgeSeed" : {
        "class" : "EdgeSeed",
        "source" : 4,
        "destination" : 5,
        "matchedVertex" : "SOURCE",
        "directedType" : "EITHER"
      }
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsInRanges",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.commonutil.pair.Pair",
    "first" : {
      "uk.gov.gchq.gaffer.operation.data.EntitySeed" : {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 4
      }
    },
    "second" : {
      "uk.gov.gchq.gaffer.operation.data.EdgeSeed" : {
        "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
        "source" : 4,
        "destination" : 5,
        "matchedVertex" : "SOURCE",
        "directedType" : "EITHER"
      }
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.GetElementsInRanges( 
  input=[ 
    g.SeedPair( 
      first=g.EntitySeed( 
        vertex=4 
      ), 
      second=g.EdgeSeed( 
        source=4, 
        destination=5, 
        directed_type="EITHER", 
        matched_vertex="SOURCE" 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 4,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 4
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 5,
  "properties" : {
    "count" : 3
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

