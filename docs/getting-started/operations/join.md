# Join
See javadoc - [uk.gov.gchq.gaffer.operation.impl.join.Join](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/join/Join.html)

Available since Gaffer version 1.8.0

This operation joins two iterables together. There are three different types of Join:

* FULL - returns all objects in the key, along with any matched objects from the other side
* INNER - returns all keys which matched with objects on the other side
* OUTER - returns all keys which didn't match with objects from the other side

A Join operation can key by the left (input) or right hand side (output of the operation specified) and outputs an iterable of MapTuples. These Tuples contain the left and right outputs.

A join operation must be supplied with a match method. This tells the operation how to determine what is and what isn't a match. There are two built in match methods:

* ElementMatch - Matches elements of the same id(s), group and group by properties
* KeyFunctionMatch - Matches any objects based on two key functions. The first key function applies to whatever the join type is (the object on the left hand side for Left keyed join and vice versa for the right).

Once matched, the left and right sides are outputted as MapTuples keyed by "LEFT" and "RIGHT". The output is flattened by default (one left value for each right value) but this can be turned off using the flatten flag. Setting the flatten flag to false will cause the non keyed side to be summarised in a list.

## Required fields
No required fields


## Examples

Input elements for the following Join examples: 

```json
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 3
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
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 6,
  "properties" : {
    "count" : 30
  }
} ]
```
### Left key inner join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.INNER)
                .matchKey(MatchKey.LEFT)
                .flatten(false)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : false,
    "joinType" : "INNER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : false,
    "joinType" : "INNER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=False, 
      match_key="LEFT", 
      join_type="INNER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
[ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
[ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ]
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    },
    "RIGHT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    } ]
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ]
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Flattened left key inner join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.INNER)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "flatten" : true,
    "joinType" : "INNER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "flatten" : true,
    "joinType" : "INNER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=True, 
      join_type="INNER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
[ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Right key inner join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.INNER)
                .matchKey(MatchKey.RIGHT)
                .flatten(false)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : false,
    "joinType" : "INNER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : false,
    "joinType" : "INNER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=False, 
      match_key="RIGHT", 
      join_type="INNER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
[ [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    } ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Flattened right key inner join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .flatten(true)
                .matchKey(MatchKey.RIGHT)
                .joinType(JoinType.INNER)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : true,
    "joinType" : "INNER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : true,
    "joinType" : "INNER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=True, 
      match_key="RIGHT", 
      join_type="INNER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
[ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Left key full join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.FULL)
                .matchKey(MatchKey.LEFT)
                .flatten(false)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : false,
    "joinType" : "FULL"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : false,
    "joinType" : "FULL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=False, 
      match_key="LEFT", 
      join_type="FULL" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
[ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
[ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
[ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> [] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ]
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    },
    "RIGHT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    } ]
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ]
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    },
    "RIGHT" : [ ]
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Flattened left key full join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.FULL)
                .matchKey(MatchKey.LEFT)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : true,
    "joinType" : "FULL"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : true,
    "joinType" : "FULL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=True, 
      match_key="LEFT", 
      join_type="FULL" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
[ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
[ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> null ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    },
    "RIGHT" : null
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Right key full join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.FULL)
                .matchKey(MatchKey.RIGHT)
                .flatten(false)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : false,
    "joinType" : "FULL"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : false,
    "joinType" : "FULL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=False, 
      match_key="RIGHT", 
      join_type="FULL" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
[ [] --> Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
[ [] --> Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
[ [] --> Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
[ [] --> Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]
[ [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 2,
      "directed" : true,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 3,
      "directed" : true,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 5,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 3,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 4
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    } ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Flattened right key full join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.FULL)
                .matchKey(MatchKey.RIGHT)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : true,
    "joinType" : "FULL"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : true,
    "joinType" : "FULL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=True, 
      match_key="RIGHT", 
      join_type="FULL" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
[ null --> Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
[ null --> Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
[ null --> Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
[ null --> Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]
[ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 2,
      "directed" : true,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 3,
      "directed" : true,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 5,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 3,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 4
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    },
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Left key outer join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.OUTER)
                .matchKey(MatchKey.LEFT)
                .flatten(false)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : false,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : false,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=False, 
      match_key="LEFT", 
      join_type="OUTER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> [] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    },
    "RIGHT" : [ ]
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Flattened left key outer join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.OUTER)
                .matchKey(MatchKey.LEFT)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : true,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "LEFT",
    "flatten" : true,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=True, 
      match_key="LEFT", 
      join_type="OUTER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> null ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    },
    "RIGHT" : null
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Right key outer join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.OUTER)
                .matchKey(MatchKey.RIGHT)
                .flatten(false)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : false,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : false,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=False, 
      match_key="RIGHT", 
      join_type="OUTER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ [] --> Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
[ [] --> Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
[ [] --> Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ [] --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
[ [] --> Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 2,
      "directed" : true,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 3,
      "directed" : true,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 5,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : [ ],
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 3,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 4
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Flattened right key outer join

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
final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
        .first(new Join.Builder<>()
                .input(inputElements)
                .operation(new GetAllElements())
                .joinType(JoinType.OUTER)
                .matchKey(MatchKey.RIGHT)
                .matchMethod(new ElementMatch("count"))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "Join",
    "input" : [ {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "GetAllElements"
    },
    "matchMethod" : {
      "class" : "ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : true,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.join.Join",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 1,
      "properties" : {
        "count" : 3
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
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 6,
      "properties" : {
        "count" : 30
      }
    } ],
    "operation" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
    },
    "matchMethod" : {
      "class" : "uk.gov.gchq.gaffer.store.operation.handler.join.match.ElementMatch"
    },
    "matchKey" : "RIGHT",
    "flatten" : true,
    "joinType" : "OUTER"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.Join( 
      operation=g.GetAllElements(), 
      match_method=g.ElementMatch(), 
      input=[ 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=1 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 1}, 
          vertex=4 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 3}, 
          vertex=5 
        ), 
        g.Entity( 
          group="entity", 
          properties={'count': 30}, 
          vertex=6 
        ) 
      ], 
      flatten=True, 
      match_key="RIGHT", 
      join_type="OUTER" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
[ null --> Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
[ null --> Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
[ null --> Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
[ null --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
[ null --> Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]

{%- language name="JSON", type="json" -%}
[ {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 2,
      "directed" : true,
      "properties" : {
        "count" : 3
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 1,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 3,
      "directed" : true,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 2,
      "destination" : 5,
      "directed" : true,
      "properties" : {
        "count" : 1
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
      }
    }
  }
}, {
  "values" : {
    "LEFT" : null,
    "RIGHT" : {
      "class" : "uk.gov.gchq.gaffer.data.element.Edge",
      "group" : "edge",
      "source" : 3,
      "destination" : 4,
      "directed" : true,
      "properties" : {
        "count" : 4
      }
    }
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

