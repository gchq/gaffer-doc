# GetWalks
See javadoc - [uk.gov.gchq.gaffer.operation.impl.GetWalks](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/GetWalks.html)

Available since Gaffer version 1.1.0

Examples for the GetWalks operation. This example uses a modified graph. This graph contains two different edge groups, each with an modified count property. The count is set to the sum of the source and destination vertices. Additionally the edge group is determined by whether this count property is even (group edge) or odd (group edge1).

## Required fields
No required fields


## Examples

### Get walks

Gets all of the Walks of length 2 which start from vertex 1, with the added restriction that all edges must be traversed using the source as the matched vertex.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
        .input(new EntitySeed(1))
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
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
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
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
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
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 ]

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
  } ] ],
  "entities" : [ {
    "1" : [ {
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
    }, {
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
  } ] ],
  "entities" : [ {
    "1" : [ {
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
    }, {
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
    "5" : [ ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with incoming outgoing flags

Gets all of the Walks of length 2 which start from vertex 1. The IncludeIncomingOutgoingType flag can be used to determine which edge direction the Walk follows for each hop.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build())
        .input(new EntitySeed(1))
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
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "INCOMING"
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
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "INCOMING"
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
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 1 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 2 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 1 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 3 ]

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
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
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
    "8" : [ ]
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
    "group" : "edge",
    "source" : 1,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 6
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
    "1" : [ ]
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
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
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
    "2" : [ ]
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
    "source" : 1,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 3
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
    "1" : [ ]
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
    "source" : 3,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 5
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
    "3" : [ ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with filtering

Gets all of the Walks of length 2 which start from vertex 1. This example demonstrates the use of pre-aggregation filters to select which edges to traverse based on a property on the edge.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .view(new View.Builder().edge("edge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(3))
                                        .build())
                                .build())
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder().edge("edge1", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(8))
                                        .build())
                                .build())
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build())
        .input(new EntitySeed(1))
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
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : {
            "preAggregationFilterFunctions" : [ {
              "selection" : [ "count" ],
              "predicate" : {
                "class" : "IsMoreThan",
                "orEqualTo" : false,
                "value" : 3
              }
            } ]
          }
        }
      },
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge1" : {
            "preAggregationFilterFunctions" : [ {
              "selection" : [ "count" ],
              "predicate" : {
                "class" : "IsMoreThan",
                "orEqualTo" : false,
                "value" : 8
              }
            } ]
          }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
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
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : {
            "preAggregationFilterFunctions" : [ {
              "selection" : [ "count" ],
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                "orEqualTo" : false,
                "value" : 3
              }
            } ]
          }
        }
      },
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge1" : {
            "preAggregationFilterFunctions" : [ {
              "selection" : [ "count" ],
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                "orEqualTo" : false,
                "value" : 8
              }
            } ]
          }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
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
        g.GetElements( 
          view=g.View( 
            edges=[ 
              g.ElementDefinition( 
                group="edge", 
                pre_aggregation_filter_functions=[ 
                  g.PredicateContext( 
                    selection=[ 
                      "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                      value=3, 
                      or_equal_to=False 
                    ) 
                  ) 
                ] 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            edges=[ 
              g.ElementDefinition( 
                group="edge1", 
                pre_aggregation_filter_functions=[ 
                  g.PredicateContext( 
                    selection=[ 
                      "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                      value=8, 
                      or_equal_to=False 
                    ) 
                  ) 
                ] 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]

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
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 13
    }
  } ] ],
  "entities" : [ {
    "1" : [ ]
  }, {
    "5" : [ ]
  }, {
    "8" : [ ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with entities

Gets all of the Walks of length 2 which start from vertex 1, with all of the entities which are attached to the vertices found along the way.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .view(new View.Builder().edge("edge")
                                .entities(Lists.newArrayList("entity", "entity1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder().edge("edge1")
                                .entity("entity1")
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder()
                                .entities(Lists.newArrayList("entity", "entity1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build())
        .input(new EntitySeed(1))
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
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
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
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
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
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            edges=[ 
              g.ElementDefinition( 
                group="edge" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ) 
            ], 
            edges=[ 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 2 ]

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
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 13
    }
  } ] ],
  "entities" : [ {
    "1" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    } ]
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
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
    }
  } ] ],
  "entities" : [ {
    "1" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "2" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    } ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with filtering on cardinality entities from first hop

Gets all of the Walks of length 2 which start from vertex 5, where the results of the first hop are filtered based on the cardinality entities in the graph.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new OperationChain.Builder()
                        .first(new GetElements.Builder()
                                .view(new View.Builder()
                                        .entity("cardinality", new ViewElementDefinition.Builder()
                                                .preAggregationFilter(new ElementFilter.Builder()
                                                        .select("edgeGroup")
                                                        .execute(new IsEqual(CollectionUtil.treeSet("edge")))
                                                        .build())
                                                .groupBy()
                                                .postAggregationFilter(new ElementFilter.Builder()
                                                        .select("hllp")
                                                        .execute(new HyperLogLogPlusIsLessThan(2))
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .then(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edges(Lists.newArrayList("edge", "edge1"))
                                        .entities(Lists.newArrayList("entity", "entity1"))
                                        .build())
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder()
                                .edges(Lists.newArrayList("edge", "edge1"))
                                .entities(Lists.newArrayList("entity", "entity1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder()
                                .entities(Lists.newArrayList("entity", "entity1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build())
        .input(new EntitySeed(5))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetWalks",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 5
  } ],
  "operations" : [ {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "entities" : {
          "cardinality" : {
            "preAggregationFilterFunctions" : [ {
              "selection" : [ "edgeGroup" ],
              "predicate" : {
                "class" : "IsEqual",
                "value" : {
                  "TreeSet" : [ "edge" ]
                }
              }
            } ],
            "groupBy" : [ ],
            "postAggregationFilterFunctions" : [ {
              "selection" : [ "hllp" ],
              "predicate" : {
                "class" : "HyperLogLogPlusIsLessThan",
                "orEqualTo" : false,
                "value" : 2
              }
            } ]
          }
        }
      }
    }, {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.GetWalks",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 5
  } ],
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "entities" : {
          "cardinality" : {
            "preAggregationFilterFunctions" : [ {
              "selection" : [ "edgeGroup" ],
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
                "value" : {
                  "java.util.TreeSet" : [ "edge" ]
                }
              }
            } ],
            "groupBy" : [ ],
            "postAggregationFilterFunctions" : [ {
              "selection" : [ "hllp" ],
              "predicate" : {
                "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan",
                "orEqualTo" : false,
                "value" : 2
              }
            } ]
          }
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Python", type="py" -%}
g.GetWalks( 
  input=[ 
    g.EntitySeed( 
      vertex=5 
    ) 
  ], 
  operations=[ 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="cardinality", 
                pre_aggregation_filter_functions=[ 
                  g.PredicateContext( 
                    selection=[ 
                      "edgeGroup" 
                    ], 
                    predicate=g.IsEqual( 
                      value={'java.util.TreeSet': ['edge']} 
                    ) 
                  ) 
                ], 
                post_aggregation_filter_functions=[ 
                  g.PredicateContext( 
                    selection=[ 
                      "hllp" 
                    ], 
                    predicate=g.HyperLogLogPlusIsLessThan( 
                      value=2, 
                      or_equal_to=False 
                    ) 
                  ) 
                ], 
                group_by=[ 
                ] 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ) 
        ), 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            edges=[ 
              g.ElementDefinition( 
                group="edge" 
              ), 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            edges=[ 
              g.ElementDefinition( 
                group="edge" 
              ), 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 1 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 3 ]

{%- language name="JSON", type="json" -%}
[ {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 1,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 3
    }
  } ] ],
  "entities" : [ {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "2" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    } ]
  }, {
    "1" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ]
  } ]
}, {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 3,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 5
    }
  } ] ],
  "entities" : [ {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "2" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    } ]
  }, {
    "3" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
      }
    } ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with filtering on cardinality entities contained in walk

Gets all of the Walks of length 2 which start from vertex 5, where each Walk returned in the results is filtered based on total of the count property on the walks' edges being less than 11.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .view(new View.Builder()
                                .edges(Lists.newArrayList("edge", "edge1"))
                                .entities(Lists.newArrayList("entity", "entity1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder()
                                .edges(Lists.newArrayList("edge", "edge1"))
                                .entities(Lists.newArrayList("entity", "entity1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder()
                                .entities(Lists.newArrayList("entity", "entity1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build())
        .conditional(new Conditional(
                new IsLessThan(11),
                new OperationChain.Builder()
                        .first(new Map.Builder<>()
                                .first(new ExtractWalkEdges())
                                .then(new IterableConcat())
                                .build())
                        .then(new ForEach.Builder<>()
                                .operation(new Map.Builder<>()
                                        .first(new ExtractProperty("count"))
                                        .build())
                                .build())
                        .then(new Reduce.Builder<>()
                                .aggregateFunction(new Sum())
                                .build())
                        .build()))
        .input(new EntitySeed(5))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetWalks",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 5
  } ],
  "operations" : [ {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  } ],
  "conditional" : {
    "transform" : {
      "class" : "OperationChain",
      "operations" : [ {
        "class" : "Map",
        "functions" : [ {
          "class" : "ExtractWalkEdges"
        }, {
          "class" : "IterableConcat"
        } ]
      }, {
        "class" : "ForEach",
        "operation" : {
          "class" : "Map",
          "functions" : [ {
            "class" : "ExtractProperty",
            "name" : "count"
          } ]
        }
      }, {
        "class" : "Reduce",
        "aggregateFunction" : {
          "class" : "Sum"
        }
      } ]
    },
    "predicate" : {
      "class" : "IsLessThan",
      "orEqualTo" : false,
      "value" : 11
    }
  },
  "resultsLimit" : 1000000
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.GetWalks",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 5
  } ],
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        },
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "entities" : {
          "entity1" : { },
          "entity" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  } ],
  "conditional" : {
    "transform" : {
      "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
      "operations" : [ {
        "class" : "uk.gov.gchq.gaffer.operation.impl.Map",
        "functions" : [ {
          "class" : "uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdges"
        }, {
          "class" : "uk.gov.gchq.koryphe.impl.function.IterableConcat"
        } ]
      }, {
        "class" : "uk.gov.gchq.gaffer.operation.impl.ForEach",
        "operation" : {
          "class" : "uk.gov.gchq.gaffer.operation.impl.Map",
          "functions" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.function.ExtractProperty",
            "name" : "count"
          } ]
        }
      }, {
        "class" : "uk.gov.gchq.gaffer.operation.impl.Reduce",
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
        }
      } ]
    },
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
      "orEqualTo" : false,
      "value" : 11
    }
  },
  "resultsLimit" : 1000000
}

{%- language name="Python", type="py" -%}
g.GetWalks( 
  input=[ 
    g.EntitySeed( 
      vertex=5 
    ) 
  ], 
  operations=[ 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            edges=[ 
              g.ElementDefinition( 
                group="edge" 
              ), 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            edges=[ 
              g.ElementDefinition( 
                group="edge" 
              ), 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity1" 
              ), 
              g.ElementDefinition( 
                group="entity" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000, 
  conditional=g.Conditional( 
    predicate=g.IsLessThan( 
      value=11, 
      or_equal_to=False 
    ), 
    transform=g.OperationChain( 
      operations=[ 
        g.Map( 
          functions=[ 
            g.ExtractWalkEdges(), 
            g.IterableConcat() 
          ] 
        ), 
        g.ForEach( 
          operation=g.Map( 
            functions=[ 
              g.ExtractProperty( 
                name="count" 
              ) 
            ] 
          ) 
        ), 
        g.Reduce( 
          aggregate_function=g.Sum() 
        ) 
      ] 
    ) 
  ) 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 1 ]

{%- language name="JSON", type="json" -%}
[ {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 1,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 3
    }
  } ] ],
  "entities" : [ {
    "5" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 5,
      "properties" : {
        "count" : 3
      }
    } ]
  }, {
    "2" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    } ]
  }, {
    "1" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 1,
      "properties" : {
        "count" : 3
      }
    } ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with multiple groups

Gets all of the Walks of length 2 which start from vertex 1. The IncludeIncomingOutgoingType flag can be used to determine which edge direction the Walk follows for each hop. Additionally, the group set in the view is used to only travel down certain edges in each hop.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("edge1")
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build())
        .input(new EntitySeed(1))
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
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : { }
        }
      },
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
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
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : { }
        }
      },
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
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
        g.GetElements( 
          view=g.View( 
            edges=[ 
              g.ElementDefinition( 
                group="edge" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            edges=[ 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 2 ]

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
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 13
    }
  } ] ],
  "entities" : [ {
    "1" : [ ]
  }, {
    "5" : [ ]
  }, {
    "8" : [ ]
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
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
    }
  } ] ],
  "entities" : [ {
    "1" : [ ]
  }, {
    "5" : [ ]
  }, {
    "2" : [ ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with loops

Gets all of the Walks of length 6 which start from vertex 1, with the added restriction that all edges must be traversed using the source as the matched vertex. This demonstrates the behaviour when previously traversed edges are encountered again.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
        .input(new EntitySeed(1))
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
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
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
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
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
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 --> 3 --> 2 --> 5 --> 6 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 --> 6 --> 3 --> 4 --> 7 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 --> 6 --> 3 --> 2 --> 5 ]

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
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 3,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 5
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
    "3" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 3,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9A/4H/BE=",
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
    "2" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }, {
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
    } ]
  }, {
    "5" : [ {
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
    "6" : [ ]
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
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 3,
    "destination" : 4,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 7
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 4,
    "destination" : 7,
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
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }, {
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
    "3" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 3,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9A/4H/BE=",
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
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
      }
    } ]
  }, {
    "4" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 4,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 4,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQL9CPoD",
              "cardinality" : 2
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge1" ]
        }
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
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 3,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 5
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
      "group" : "entity1",
      "vertex" : 2,
      "properties" : {
        "count" : 1
      }
    }, {
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
    "3" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 3,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQP9A/4H/BE=",
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
      "group" : "entity",
      "vertex" : 3,
      "properties" : {
        "count" : 2
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
    "5" : [ ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with self loops

Gets all of the Walks of length 3 which start from vertex 8, with the added restriction that all edges must be traversed using the source as the matched vertex. This demonstrates the behaviour when self loops exist in the graph.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build(),
                new GetElements.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
        .input(new EntitySeed(8))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetWalks",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 8
  } ],
  "operations" : [ {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.GetWalks",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 8
  } ],
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Python", type="py" -%}
g.GetWalks( 
  input=[ 
    g.EntitySeed( 
      vertex=8 
    ) 
  ], 
  operations=[ 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          include_incoming_out_going="OUTGOING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 8 --> 8 --> 8 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 8 --> 8 --> 5 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 8 --> 5 --> 6 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 5 --> 6 --> 3 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 5 --> 6 --> 7 ]

{%- language name="JSON", type="json" -%}
[ {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 8,
    "destination" : 8,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 16
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 8,
    "destination" : 8,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 16
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 8,
    "destination" : 8,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 16
    }
  } ] ],
  "entities" : [ {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "8" : [ ]
  } ]
}, {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 8,
    "destination" : 8,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 16
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 8,
    "destination" : 8,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 16
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 13
    }
  } ] ],
  "entities" : [ {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "5" : [ ]
  } ]
}, {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 8,
    "destination" : 8,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 16
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 13
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
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "5" : [ {
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
}, {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 13
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
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "5" : [ {
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
    "group" : "edge1",
    "source" : 8,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "SOURCE",
    "properties" : {
      "count" : 13
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
    "8" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "entity1",
      "vertex" : 8,
      "properties" : {
        "count" : 1
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
      "properties" : {
        "hllp" : {
          "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
            "hyperLogLogPlus" : {
              "hyperLogLogPlusSketchBytes" : "/////gUFAQH7HQ==",
              "cardinality" : 1
            }
          }
        },
        "count" : 2,
        "edgeGroup" : {
          "java.util.TreeSet" : [ "edge" ]
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.data.element.Entity",
      "group" : "cardinality",
      "vertex" : 8,
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
          "java.util.TreeSet" : [ "edge1" ]
        }
      }
    } ]
  }, {
    "5" : [ {
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
} ]
{%- endcodetabs %}

-----------------------------------------------

### Get walks with additional operations

Gets all of the Walks of length 2 which start from vertex 5, where an additional operation is inserted between the GetElements operations used to retrieve elements.

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
final GetWalks getWalks = new GetWalks.Builder()
        .operations(new OperationChain(new GetElements.Builder()
                        .view(new View.Builder()
                                .edges(Lists.newArrayList("edge", "edge1"))
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build(),
                        new Sort.Builder()
                                .comparators(new ElementPropertyComparator.Builder()
                                        .property("count")
                                        .build())
                                .build()),
                new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("edge1")
                                .build())
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                        .build())
        .input(new EntitySeed(5))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetWalks",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : 5
  } ],
  "operations" : [ {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    }, {
      "class" : "Sort",
      "comparators" : [ {
        "class" : "ElementPropertyComparator",
        "property" : "count",
        "groups" : [ ],
        "reversed" : false
      } ],
      "deduplicate" : true
    } ]
  }, {
    "class" : "OperationChain",
    "operations" : [ {
      "class" : "GetElements",
      "view" : {
        "edges" : {
          "edge1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.GetWalks",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 5
  } ],
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge" : { },
          "edge1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Sort",
      "comparators" : [ {
        "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
        "property" : "count",
        "groups" : [ ],
        "reversed" : false
      } ],
      "deduplicate" : true
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
      "view" : {
        "edges" : {
          "edge1" : { }
        }
      },
      "includeIncomingOutGoing" : "INCOMING"
    } ]
  } ],
  "resultsLimit" : 1000000
}

{%- language name="Python", type="py" -%}
g.GetWalks( 
  input=[ 
    g.EntitySeed( 
      vertex=5 
    ) 
  ], 
  operations=[ 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            edges=[ 
              g.ElementDefinition( 
                group="edge" 
              ), 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ), 
        g.Sort( 
          comparators=[ 
            g.ElementPropertyComparator( 
              groups=[ 
              ], 
              property="count", 
              reversed=False 
            ) 
          ], 
          deduplicate=True 
        ) 
      ] 
    ), 
    g.OperationChain( 
      operations=[ 
        g.GetElements( 
          view=g.View( 
            edges=[ 
              g.ElementDefinition( 
                group="edge1" 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ), 
          include_incoming_out_going="INCOMING" 
        ) 
      ] 
    ) 
  ], 
  results_limit=1000000 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 1 ]
uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 3 ]

{%- language name="JSON", type="json" -%}
[ {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 1,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 3
    }
  } ] ],
  "entities" : [ {
    "5" : [ ]
  }, {
    "2" : [ ]
  }, {
    "1" : [ ]
  } ]
}, {
  "edges" : [ [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 7
    }
  } ], [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge1",
    "source" : 3,
    "destination" : 2,
    "directed" : true,
    "matchedVertex" : "DESTINATION",
    "properties" : {
      "count" : 5
    }
  } ] ],
  "entities" : [ {
    "5" : [ ]
  }, {
    "2" : [ ]
  }, {
    "3" : [ ]
  } ]
} ]
{%- endcodetabs %}

-----------------------------------------------

