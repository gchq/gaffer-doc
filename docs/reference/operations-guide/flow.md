# Control Flow Operations

These Operations are used for introducing logical control flow over other Operations. They are always available.

This directed graph is used in all the examples on this page, except for ForEach:

``` mermaid
graph LR
  1 --> 2
  1 --> 5
  2 --> 5
  3 --> 2
  3 --> 4
  4 --> 7
  5 --> 6
  6 --> 3
  6 --> 7
  8 --> 5
  8 --> 8
```

## If

[Conditional](https://en.wikipedia.org/wiki/Conditional_(computer_programming)) statement which executes a certain operation if the condition is satisfied and a different operation otherwise. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/If.html)

??? example "Conditionally get elements or limit current results"

    This example will take the vertices adjacent to the Entity with id 2, and if there are fewer than 5 results, they will be passed into a GetElements operation.Otherwise, there will simply only be 5 results returned.
    
    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=6,destination=3,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>9]]
        Edge[source=5,destination=6,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>11]]
        Edge[source=1,destination=5,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>6]]
        Entity[vertex=5,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@7c0a5be5,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
        Entity[vertex=1,group=entity1,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        [ {
          "class" : "uk.gov.gchq.gaffer.data.element.Edge",
          "group" : "edge1",
          "source" : 6,
          "destination" : 3,
          "directed" : true,
          "matchedVertex" : "DESTINATION",
          "properties" : {
            "count" : 9
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
          "vertex" : 1,
          "properties" : {
            "count" : 3
          }
        } ]
        ```

??? example "Add named operation containing if operation with parameter"

    Here we create and add a NamedOperation, containing an If operation with a parameter. This parameter can be configured so that the optional GetElements with the filter can be executed, otherwise it will just continue to the next GetElements.
    
    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

??? example "Parameterised named operation containing if operation"

    This example then runs the NamedOperation, providing both the input, and the value of the parameter via a Map.
    
    === "Java"

        ``` java
        final java.util.Map<String, Object> parameterValues = Maps.newHashMap();
        parameterValues.put("enableFiltering", true);

        final NamedOperation<EntityId, CloseableIterable<? extends Element>> namedOp =
                new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                        .name("2-hop-with-optional-filtering")
                        .input(new EntitySeed(6))
                        .parameters(parameterValues)
                        .build();
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
        g.NamedOperation( 
          input=[ 
            g.EntitySeed( 
              vertex=6 
            ) 
          ], 
          operation_name="2-hop-with-optional-filtering", 
          parameters={'enableFiltering': True} 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=6,destination=3,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>9]]
        Entity[vertex=7,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=4,destination=7,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>11]]
        Edge[source=6,destination=7,directed=true,matchedVertex=DESTINATION,group=edge1,properties=Properties[count=<java.lang.Integer>13]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=7,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@4fa6c1be,count=<java.lang.Integer>2,edgeGroup=<java.util.TreeSet>[edge1]]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>7]]
        Entity[vertex=3,group=cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@615d6edc,count=<java.lang.Integer>3,edgeGroup=<java.util.TreeSet>[edge1]]]
        Edge[source=3,destination=2,directed=true,matchedVertex=SOURCE,group=edge1,properties=Properties[count=<java.lang.Integer>5]]
        ```

    === "JSON"

        ``` json
        [ {
          "class" : "uk.gov.gchq.gaffer.data.element.Edge",
          "group" : "edge1",
          "source" : 6,
          "destination" : 3,
          "directed" : true,
          "matchedVertex" : "DESTINATION",
          "properties" : {
            "count" : 9
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
          "group" : "entity",
          "vertex" : 3,
          "properties" : {
            "count" : 2
          }
        }, {
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
          "class" : "uk.gov.gchq.gaffer.data.element.Edge",
          "group" : "edge1",
          "source" : 3,
          "destination" : 2,
          "directed" : true,
          "matchedVertex" : "SOURCE",
          "properties" : {
            "count" : 5
          }
        } ]
        ```

## While

Runs an operation repeatedly until a condition is no longer true or a maximum number of runs (repeats) has been reached. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/While.html)

??? example "Run an operation 3 times"

    This example will run the GetAdjacentIds operation 3 times.
    
    === "Java"

        ``` java
        final While<Iterable<EntityId>, CloseableIterable<? extends EntityId>> operation = new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
                .input(Lists.newArrayList(new EntitySeed(1)))
                .condition(true)
                .maxRepeats(3)
                .operation(new GetAdjacentIds.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        EntitySeed[vertex=6]
        EntitySeed[vertex=3]
        EntitySeed[vertex=7]
        ```

    === "JSON"

        ``` json
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
        ```

??? example "Run a while operation within a get walks"

    This example will run a GetWalks operation with 3 hops.
    
    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 --> 3 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 --> 7 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 --> 6 ]
        ```

    === "JSON"

        ``` json
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
            "6" : [ ]
          } ]
        } ]
        ```

??? example "Run until an end result is found"

    This example will keep running GetAdjacentIds until the results contain a vertex with value 7.
    
    === "Java"

        ``` java
        final While<Iterable<EntityId>, CloseableIterable<? extends EntityId>> operation = new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
                .input(Lists.newArrayList(new EntitySeed(1)))
                .conditional(new Not<>(new CollectionContains(new EntitySeed(7))), new ToSet<>())
                .maxRepeats(20)
                .operation(new GetAdjacentIds.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        EntitySeed[vertex=6]
        EntitySeed[vertex=3]
        EntitySeed[vertex=7]
        ```

    === "JSON"

        ``` json
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
        ```

## ForEach

Runs supplied operation on all items in an Iterable input. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/ForEach.html)

??? info "ForEach Graph"
    
    ``` mermaid
    graph TD
    1(1, count=3) -- count=3 --> 2
    1 -- count=1 --> 4
    2(2, count=1) -- count=2 --> 3
    2 -- count=1 --> 4(4, count=1)
    2 -- count=1 --> 5(5, count=3)
    3(3, count=2) -- count=4 --> 4
    ```

??? example "Example of ForEach in a chain"

    === "Java"

        ``` java
        final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed(1))
                        .build())
                .then(new ForEach.Builder<>()
                        .operation(new OperationChain.Builder()
                                .first(new ToSingletonList<EntitySeed>())
                                .then(new GetAdjacentIds())
                                .then(new ToVertices())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetAdjacentIds",
            "input" : [ {
              "class" : "EntitySeed",
              "vertex" : 1
            } ]
          }, {
            "class" : "ForEach",
            "operation" : {
              "class" : "OperationChain",
              "operations" : [ {
                "class" : "ToSingletonList"
              }, {
                "class" : "GetAdjacentIds"
              }, {
                "class" : "ToVertices"
              } ]
            }
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
          operations=[ 
            g.GetAdjacentIds( 
              input=[ 
                g.EntitySeed( 
                  vertex=1 
                ) 
              ] 
            ), 
            g.ForEach( 
              operation=g.OperationChain( 
                operations=[ 
                  g.ToSingletonList(), 
                  g.GetAdjacentIds(), 
                  g.ToVertices() 
                ] 
              ) 
            ) 
          ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        [ 2 --> 3 --> 1 ]
        [ 3 --> 4 --> 5 --> 1 ]
        ```

    === "JSON"

        ``` json
        [ [ 2, 3, 1 ], [ 3, 4, 5, 1 ] ]
        ```