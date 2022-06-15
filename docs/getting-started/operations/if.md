# If
See javadoc - [uk.gov.gchq.gaffer.operation.impl.If](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/If.html)

Available since Gaffer version 1.4.0

Examples for the If Operation. These examples use a modified, more complex graph.

## Required fields
No required fields


## Examples

### Conditionally get elements or limit current results

This example will take the vertices adjacent to the Entity with id 2, and if there are fewer than 5 results, they will be passed into a GetElements operation.Otherwise, there will simply only be 5 results returned.

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
final OperationChain<Object> opChain = new OperationChain.Builder()
        .first(new GetAdjacentIds.Builder()
                .input(new EntitySeed(2))
                .build())
        .then(new If.Builder<>()
                .conditional(new IsShorterThan(5))
                .then(new OperationChain.Builder()
                        .first(new GetElements())
                        .then(new Limit<>(5))
                        .build())
                .otherwise(new Limit<>(5))
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAdjacentIds",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.If",
    "conditional" : {
      "predicate" : {
        "class" : "IsShorterThan",
        "maxLength" : 5,
        "orEqualTo" : false
      }
    },
    "then" : {
      "class" : "OperationChain",
      "operations" : [ {
        "class" : "GetElements"
      }, {
        "class" : "Limit",
        "resultLimit" : 5,
        "truncate" : true
      } ]
    },
    "otherwise" : {
      "class" : "Limit",
      "resultLimit" : 5,
      "truncate" : true
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 2
    } ]
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.If",
    "conditional" : {
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.IsShorterThan",
        "maxLength" : 5,
        "orEqualTo" : false
      }
    },
    "then" : {
      "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
      "operations" : [ {
        "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements"
      }, {
        "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
        "resultLimit" : 5,
        "truncate" : true
      } ]
    },
    "otherwise" : {
      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
      "resultLimit" : 5,
      "truncate" : true
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAdjacentIds( 
      input=[ 
        g.EntitySeed( 
          vertex=2 
        ) 
      ] 
    ), 
    g.If( 
      conditional=g.Conditional( 
        predicate=g.IsShorterThan( 
          max_length=5, 
          or_equal_to=False 
        ) 
      ), 
      then=g.OperationChain( 
        operations=[ 
          g.GetElements(), 
          g.Limit( 
            result_limit=5, 
            truncate=True 
          ) 
        ] 
      ), 
      otherwise=g.Limit( 
        result_limit=5, 
        truncate=True 
      ) 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=5,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3f45da57,count=<java.lang.Integer>1,edgeGroup=<java.util.TreeSet>[edge]]]
Entity[vertex=5,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@2cb78c87,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=5,group=entity1,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=5,destination=6,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>11]]
Edge[source=1,destination=5,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>6]]

{%- language name="JSON", type="json" -%}
[ {
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
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity1",
  "vertex" : 5,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge1",
  "source" : 5,
  "destination" : 6,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 11
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 5,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 6
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Add named operation containing if operation with parameter

Here we create and add a NamedOperation, containing an If operation with a parameter. This parameter can be configured so that the optional GetElements with the filter can be executed, otherwise it will just continue to the next GetElements.


{% codetabs name="Java", type="java" -%}
final String opChainString = "{" +
        "\"operations\" : [ {\n" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"\n" +
        "    }, {\n" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.If\",\n" +
        "      \"condition\" : \"${enableFiltering}\",\n" +
        "      \"then\" : {\n" +
        "        \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\",\n" +
        "        \"view\" : {\n" +
        "          \"entities\" : {\n" +
        "            \"entity\" : {\n" +
        "              \"preAggregationFilterFunctions\" : [ {\n" +
        "                \"selection\" : [ \"count\" ],\n" +
        "                \"predicate\" : {\n" +
        "                  \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsLessThan\",\n" +
        "                  \"orEqualTo\" : true,\n" +
        "                  \"value\" : 10\n" +
        "                }\n" +
        "              } ]\n" +
        "            }\n" +
        "          }\n" +
        "        }\n" +
        "      }\n" +
        "    }, {\n" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\"\n" +
        "    } ]" +
        "}";

ParameterDetail param = new ParameterDetail.Builder()
        .defaultValue(true)
        .description("Flag for enabling filtering")
        .valueClass(boolean.class)
        .build();
java.util.Map<String, ParameterDetail> parameterMap = Maps.newHashMap();
parameterMap.put("enableFiltering", param);

final AddNamedOperation operation = new AddNamedOperation.Builder()
        .operationChain(opChainString)
        .description("2 hop query with optional filtering by count")
        .name("2-hop-with-optional-filtering")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .parameters(parameterMap)
        .overwrite()
        .score(4)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedOperation",
  "operationName" : "2-hop-with-optional-filtering",
  "description" : "2 hop query with optional filtering by count",
  "score" : 4,
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.If",
      "condition" : "${enableFiltering}",
      "then" : {
        "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
        "view" : {
          "entities" : {
            "entity" : {
              "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
                  "orEqualTo" : true,
                  "value" : 10
                }
              } ]
            }
          }
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements"
    } ]
  },
  "overwriteFlag" : true,
  "parameters" : {
    "enableFiltering" : {
      "description" : "Flag for enabling filtering",
      "defaultValue" : true,
      "valueClass" : "boolean",
      "required" : false
    }
  },
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.AddNamedOperation",
  "operationName" : "2-hop-with-optional-filtering",
  "description" : "2 hop query with optional filtering by count",
  "score" : 4,
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.If",
      "condition" : "${enableFiltering}",
      "then" : {
        "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
        "view" : {
          "entities" : {
            "entity" : {
              "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
                  "orEqualTo" : true,
                  "value" : 10
                }
              } ]
            }
          }
        }
      }
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements"
    } ]
  },
  "overwriteFlag" : true,
  "parameters" : {
    "enableFiltering" : {
      "description" : "Flag for enabling filtering",
      "defaultValue" : true,
      "valueClass" : "boolean",
      "required" : false
    }
  },
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Python", type="py" -%}
g.AddNamedOperation( 
  operation_chain=g.OperationChainDAO( 
    operations=[ 
      g.GetAdjacentIds(), 
      g.If( 
        condition="${enableFiltering}", 
        then=g.GetElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity", 
                pre_aggregation_filter_functions=[ 
                  g.PredicateContext( 
                    selection=[ 
                      "count" 
                    ], 
                    predicate=g.IsLessThan( 
                      value=10, 
                      or_equal_to=True 
                    ) 
                  ) 
                ] 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ) 
        ) 
      ), 
      g.GetElements() 
    ] 
  ), 
  operation_name="2-hop-with-optional-filtering", 
  description="2 hop query with optional filtering by count", 
  read_access_roles=[ 
    "read-user" 
  ], 
  write_access_roles=[ 
    "write-user" 
  ], 
  overwrite_flag=True, 
  score=4, 
  parameters=[ 
    g.NamedOperationParameter( 
      name="enableFiltering", 
      value_class="boolean", 
      description="Flag for enabling filtering", 
      default_value=True, 
      required=False 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Run parameterised named operation containing if operation

This example then runs the NamedOperation, providing both the input, and the value of the parameter via a Map.

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
final java.util.Map<String, Object> parameterValues = Maps.newHashMap();
parameterValues.put("enableFiltering", true);

final NamedOperation<EntityId, CloseableIterable<? extends Element>> namedOp =
        new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                .name("2-hop-with-optional-filtering")
                .input(new EntitySeed(6))
                .parameters(parameterValues)
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "NamedOperation",
  "input" : [ {
    "class" : "EntitySeed",
    "class" : "EntitySeed",
    "vertex" : 6
  } ],
  "operationName" : "2-hop-with-optional-filtering",
  "parameters" : {
    "enableFiltering" : true
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 6
  } ],
  "operationName" : "2-hop-with-optional-filtering",
  "parameters" : {
    "enableFiltering" : true
  }
}

{%- language name="Python", type="py" -%}
g.NamedOperation( 
  input=[ 
    g.EntitySeed( 
      vertex=6 
    ) 
  ], 
  operation_name="2-hop-with-optional-filtering", 
  parameters={'enableFiltering': True} 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=7,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@5de31894,count=<java.lang.Integer>2,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=7,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=4,destination=7,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>11]]
Edge[source=6,destination=7,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>13]]
Entity[vertex=3,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3f7dfe46,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=3,destination=2,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>5]]
Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>7]]
Edge[source=6,destination=3,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>9]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "cardinality",
  "vertex" : 7,
  "properties" : {
    "hllp" : {
      "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus" : {
        "hyperLogLogPlus" : {
          "hyperLogLogPlusSketchBytes" : "/////gUFAQL9A/oZ",
          "cardinality" : 2
        }
      }
    },
    "count" : 2,
    "edgeGroup" : {
      "java.util.TreeSet" : [ "edge1" ]
    }
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 7,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge1",
  "source" : 4,
  "destination" : 7,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 11
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge1",
  "source" : 6,
  "destination" : 7,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 13
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
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge1",
  "source" : 3,
  "destination" : 2,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 5
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge1",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "matchedVertex" : "SOURCE",
  "properties" : {
    "count" : 7
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge1",
  "source" : 6,
  "destination" : 3,
  "directed" : true,
  "matchedVertex" : "DESTINATION",
  "properties" : {
    "count" : 9
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

