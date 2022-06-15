# GetSetExport
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/GetSetExport.html)

Available since Gaffer version 1.0.0

Fetches data from a Set cache

## Required fields
No required fields


## Examples

### Simple export and get

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
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToSet"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetSetExport",
    "start" : 0
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
    "start" : 0
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet(), 
    g.DiscardOutput(), 
    g.GetSetExport( 
      start=0 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]

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
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
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
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "properties" : {
    "count" : 1
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
  "properties" : {
    "count" : 4
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 4,
  "properties" : {
    "count" : 1
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

### Simple export and get with pagination

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
final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet<>())
        .then(new DiscardOutput())
        .then(new GetSetExport.Builder()
                .start(2)
                .end(4)
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToSet"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetSetExport",
    "start" : 2,
    "end" : 4
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
    "start" : 2,
    "end" : 4
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet(), 
    g.DiscardOutput(), 
    g.GetSetExport( 
      start=2, 
      end=4 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
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
} ]
{%- endcodetabs %}

-----------------------------------------------

### Export multiple results to set and get all results

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
final OperationChain<Map<String, CloseableIterable<?>>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToSet.Builder<>()
                .key("edges")
                .build())
        .then(new DiscardOutput())
        .then(new GetAllElements())
        .then(new ExportToSet.Builder<>()
                .key("entities")
                .build())
        .then(new DiscardOutput())
        .then(new GetExports.Builder()
                .exports(new GetSetExport.Builder()
                                .key("edges")
                                .build(),
                        new GetSetExport.Builder()
                                .key("entities")
                                .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToSet",
    "key" : "edges"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToSet",
    "key" : "entities"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetExports",
    "getExports" : [ {
      "class" : "GetSetExport",
      "start" : 0,
      "key" : "edges"
    }, {
      "class" : "GetSetExport",
      "start" : 0,
      "key" : "entities"
    } ]
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet",
    "key" : "edges"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet",
    "key" : "entities"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.GetExports",
    "getExports" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
      "start" : 0,
      "key" : "edges"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport",
      "start" : 0,
      "key" : "entities"
    } ]
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToSet( 
      key="edges" 
    ), 
    g.DiscardOutput(), 
    g.GetAllElements(), 
    g.ExportToSet( 
      key="entities" 
    ), 
    g.DiscardOutput(), 
    g.GetExports( 
      get_exports=[ 
        g.GetSetExport( 
          key="edges", 
          start=0 
        ), 
        g.GetSetExport( 
          key="entities", 
          start=0 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: edges:
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: entities:
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]

{%- language name="JSON", type="json" -%}
{
  "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: edges" : [ {
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
    "properties" : {
      "count" : 3
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 4,
    "directed" : true,
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
    "properties" : {
      "count" : 2
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "properties" : {
      "count" : 1
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
    "properties" : {
      "count" : 4
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 4,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 5,
    "properties" : {
      "count" : 3
    }
  } ],
  "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: entities" : [ {
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
    "properties" : {
      "count" : 3
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 4,
    "directed" : true,
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
    "properties" : {
      "count" : 2
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "properties" : {
      "count" : 1
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
    "properties" : {
      "count" : 4
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 4,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 5,
    "properties" : {
      "count" : 3
    }
  } ]
}
{%- endcodetabs %}

-----------------------------------------------

