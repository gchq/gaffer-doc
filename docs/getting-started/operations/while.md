# While
See javadoc - [uk.gov.gchq.gaffer.operation.impl.While](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/While.html)

Available since Gaffer version 1.5.0

Examples for the While Operation. These examples use a modified, more complex graph.

## Required fields
No required fields


## Examples

### Run 3 times

This example will run the GetAdjacentIds operation 3 times

Using this complex directed graph:

```

                 --> 7 <--
               /           \
              /             \
             6  -->  3  -->  4
 ___        ^         \
|   |       /          /
 -> 8 -->  5  <--  2  <
          ^        ^
         /        /
    1 --         /
     \          /
       --------
```


{% codetabs name="Java", type="java" -%}
final While<Iterable<EntityId>, CloseableIterable<? extends EntityId>> operation = new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
        .input(Lists.newArrayList(new EntitySeed(1)))
        .condition(true)
        .maxRepeats(3)
        .operation(new GetAdjacentIds.Builder()
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "While",
  "operation" : {
    "class" : "GetAdjacentIds",
    "includeIncomingOutGoing" : "OUTGOING"
  },
  "maxRepeats" : 3,
  "condition" : true,
  "input" : [ {
    "class" : "EntitySeed",
    "class" : "EntitySeed",
    "vertex" : 1
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.While",
  "operation" : {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "includeIncomingOutGoing" : "OUTGOING"
  },
  "maxRepeats" : 3,
  "condition" : true,
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ]
}

{%- language name="Python", type="py" -%}
g.While( 
  max_repeats=3, 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  condition=True, 
  operation=g.GetAdjacentIds( 
    include_incoming_out_going="OUTGOING" 
  ) 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=6]
EntitySeed[vertex=3]
EntitySeed[vertex=7]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 6
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 3
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 7
} ]
{%- endcodetabs %}

-----------------------------------------------

### Run a while operation within a get walks

This example will run a GetWalks operation with 3 hops

Using this complex directed graph:

```

                 --> 7 <--
               /           \
              /             \
             6  -->  3  -->  4
 ___        ^         \
|   |       /          /
 -> 8 -->  5  <--  2  <
          ^        ^
         /        /
    1 --         /
     \          /
       --------
```


{% codetabs name="Java", type="java" -%}
final GetWalks operation = new Builder()
        .input(new EntitySeed(1))
        .operations(new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
                .condition(true)
                .maxRepeats(3)
                .operation(new GetElements.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetWalks",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 1
  } ],
  "operations" : [ {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "While",
      "operation" : {
        "class" : "GetElements",
        "includeIncomingOutGoing" : "OUTGOING"
      },
      "maxRepeats" : 3,
      "condition" : true
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.GetWalks",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ],
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.While",
      "operation" : {
        "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
        "includeIncomingOutGoing" : "OUTGOING"
      },
      "maxRepeats" : 3,
      "condition" : true
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Python", type="py" -%}
g.GetWalks( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  operations=[ 
    g.OperationChain( 
      operations=[ 
        g.While( 
          max_repeats=3, 
          condition=True, 
          operation=g.GetElements( 
            include_incoming_out_going="OUTGOING" 
          ) 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 --> 3 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 --> 7 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 --> 6 ]

{%- language name="JSON", type="json" -%}
[ {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 6
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 11
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 6,
    "destination" : 3,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 9
    }
  } ] ],
  "entities" : [ {
    "1" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 1,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH9Fg==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 1,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7Cw==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 5,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7FA==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 5,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9A/4HgBI=",
              "cardinality" : 3
            }
          }
        },
        "count" : 3,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "6" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 6,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9CPoDhgo=",
              "cardinality" : 3
            }
          }
        },
        "count" : 3,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 6,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "3" : [ ]
  } ]
}, {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 6
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 11
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 6,
    "destination" : 7,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 13
    }
  } ] ],
  "entities" : [ {
    "1" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 1,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH9Fg==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 1,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7Cw==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 5,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7FA==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 5,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9A/4HgBI=",
              "cardinality" : 3
            }
          }
        },
        "count" : 3,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "6" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 6,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9CPoDhgo=",
              "cardinality" : 3
            }
          }
        },
        "count" : 3,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 6,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "7" : [ ]
  } ]
}, {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 1,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 3
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 7
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 5,
    "destination" : 6,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 11
    }
  } ] ],
  "entities" : [ {
    "1" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 1,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH9Fg==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 1,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7Cw==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "2" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 2,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP3DIQIggI=",
              "cardinality" : 3
            }
          }
        },
        "count" : 3,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    } ]
  }, {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 5,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9A/4HgBI=",
              "cardinality" : 3
            }
          }
        },
        "count" : 3,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 5,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7FA==",
              "cardinality" : 1
            }
          }
        },
        "count" : 1,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    } ]
  }, {
    "6" : [ ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Run until an end result is found

This example will keep running GetAdjacentIds until the results contain a vertex with value 7

Using this complex directed graph:

```

                 --> 7 <--
               /           \
              /             \
             6  -->  3  -->  4
 ___        ^         \
|   |       /          /
 -> 8 -->  5  <--  2  <
          ^        ^
         /        /
    1 --         /
     \          /
       --------
```


{% codetabs name="Java", type="java" -%}
final While<Iterable<EntityId>, CloseableIterable<? extends EntityId>> operation = new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
        .input(Lists.newArrayList(new EntitySeed(1)))
        .conditional(new Not<>(new CollectionContains(new EntitySeed(7))), new ToSet<>())
        .maxRepeats(20)
        .operation(new GetAdjacentIds.Builder()
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "While",
  "conditional" : {
    "transform" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
    },
    "predicate" : {
      "class" : "Not",
      "predicate" : {
        "class" : "CollectionContains",
        "value" : {
          "class" : "EntitySeed",
          "class" : "EntitySeed",
          "vertex" : 7
        }
      }
    }
  },
  "operation" : {
    "class" : "GetAdjacentIds",
    "includeIncomingOutGoing" : "OUTGOING"
  },
  "maxRepeats" : 20,
  "input" : [ {
    "class" : "EntitySeed",
    "class" : "EntitySeed",
    "vertex" : 1
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.While",
  "conditional" : {
    "transform" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
    },
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.Not",
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
        "value" : {
          "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
          "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
          "vertex" : 7
        }
      }
    }
  },
  "operation" : {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "includeIncomingOutGoing" : "OUTGOING"
  },
  "maxRepeats" : 20,
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ]
}

{%- language name="Python", type="py" -%}
g.While( 
  max_repeats=20, 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  operation=g.GetAdjacentIds( 
    include_incoming_out_going="OUTGOING" 
  ), 
  conditional=g.Conditional( 
    predicate=g.Not( 
      predicate=g.CollectionContains( 
        value=g.EntitySeed( 
          vertex=7 
        ) 
      ) 
    ), 
    transform=g.ToSet() 
  ) 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=6]
EntitySeed[vertex=3]
EntitySeed[vertex=7]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 6
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 3
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 7
} ]
{%- endcodetabs %}

-----------------------------------------------

