# Get Operations

These Operations are used for fetching elements from the store backing a graph. They are available by default.

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

## GetElements

Gets elements related to provided seeds. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetElements.html)

??? example "Example fetching entities and edges by ID"

    Get entities and edges by entity id 2 and edge id 2 to 3.

    === "Java"

        ``` java
        final GetElements operation = new GetElements.Builder()
                .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        }, {
            "class" : "EdgeSeed",
            "source" : 2,
            "destination" : 3,
            "matchedVertex" : "SOURCE",
            "directedType" : "EITHER"
        } ]
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ), 
            g.EdgeSeed( 
            source=2, 
            destination=3, 
            directed_type="EITHER", 
            matched_vertex="SOURCE" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        [ {
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
        "class" : "uk.gov.gchq.gaffer.data.element.Entity",
        "group" : "entity",
        "vertex" : 3,
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
        "source" : 1,
        "destination" : 2,
        "directed" : true,
        "matchedVertex" : "DESTINATION",
        "properties" : {
            "count" : 3
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
        } ]
        ```

??? example "Example fetching entities and edges by ID and count"

    Get entities and edges by entity id 2 and edge id 2 to 3 with count more than 1.
    
    === "Java"

        ``` java
        final GetElements operation = new GetElements.Builder()
                .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(1))
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(1))
                                        .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        }, {
            "class" : "EdgeSeed",
            "source" : 2,
            "destination" : 3,
            "matchedVertex" : "SOURCE",
            "directedType" : "EITHER"
        } ],
        "view" : {
            "edges" : {
            "edge" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 1
                }
                } ]
            }
            },
            "entities" : {
            "entity" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 1
                }
                } ]
            }
            }
        }
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        view=g.View( 
            entities=[ 
            g.ElementDefinition( 
                group="entity", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                    value=1, 
                    or_equal_to=False 
                    ) 
                ) 
                ] 
            ) 
            ], 
            edges=[ 
            g.ElementDefinition( 
                group="edge", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                    value=1, 
                    or_equal_to=False 
                    ) 
                ) 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ), 
            g.EdgeSeed( 
            source=2, 
            destination=3, 
            directed_type="EITHER", 
            matched_vertex="SOURCE" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        [ {
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
        "class" : "uk.gov.gchq.gaffer.data.element.Entity",
        "group" : "entity",
        "vertex" : 3,
        "properties" : {
            "count" : 2
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
        } ]
        ```

??? example "Example fetching entities and edges related to vertex"

    Get entities and edges that are related to vertex 2.
    
    === "Java"

        ``` java
        final GetElements operation = new GetElements.Builder()
                .input(new EntitySeed(2))
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        } ]
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        [ {
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
        "source" : 1,
        "destination" : 2,
        "directed" : true,
        "matchedVertex" : "DESTINATION",
        "properties" : {
            "count" : 3
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
        } ]
        ```

??? example "Example fetching entities and edges related to edge"

    Get all entities and edges that are related to edge 1 to 2.
    
    === "Java"

        ``` java
        final GetElements operation = new GetElements.Builder()
                .input(new EdgeSeed(1, 2, DirectedType.EITHER))
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EdgeSeed",
            "source" : 1,
            "destination" : 2,
            "matchedVertex" : "SOURCE",
            "directedType" : "EITHER"
        } ]
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        input=[ 
            g.EdgeSeed( 
            source=1, 
            destination=2, 
            directed_type="EITHER", 
            matched_vertex="SOURCE" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.data.element.Entity",
        "group" : "entity",
        "vertex" : 2,
        "properties" : {
            "count" : 1
        }
        }, {
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
        } ]
        ```

??? example "Example fetching entities and edges related to edge with count"

    Get all entities and edges that are related to edge 1 to 2 with count more than 1.
    
    === "Java"

        ``` java
        final GetElements operation = new GetElements.Builder()
                .input(new EdgeSeed(1, 2, DirectedType.EITHER))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(1))
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(1))
                                        .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EdgeSeed",
            "source" : 1,
            "destination" : 2,
            "matchedVertex" : "SOURCE",
            "directedType" : "EITHER"
        } ],
        "view" : {
            "edges" : {
            "edge" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 1
                }
                } ]
            }
            },
            "entities" : {
            "entity" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 1
                }
                } ]
            }
            }
        }
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        view=g.View( 
            entities=[ 
            g.ElementDefinition( 
                group="entity", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                    value=1, 
                    or_equal_to=False 
                    ) 
                ) 
                ] 
            ) 
            ], 
            edges=[ 
            g.ElementDefinition( 
                group="edge", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                    value=1, 
                    or_equal_to=False 
                    ) 
                ) 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        input=[ 
            g.EdgeSeed( 
            source=1, 
            destination=2, 
            directed_type="EITHER", 
            matched_vertex="SOURCE" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
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
        } ]
        ```

??? example "Example fetching related entities with count"

    Get entities related to 2 with count less than 2 or more than 5.

    When using an `Or` predicate with a single selected value you can just do `select(propertyName)` then `execute(new Or(predicates))`.
    
    === "Java"

        ``` java
        final GetElements operation = new GetElements.Builder()
                .input(new EntitySeed(2), new EdgeSeed(2, 3, DirectedType.EITHER))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .preAggregationFilter(
                                        new ElementFilter.Builder()
                                                .select("count")
                                                .execute(new Or<>(new IsLessThan(2), new IsMoreThan(5)))
                                                .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        }, {
            "class" : "EdgeSeed",
            "source" : 2,
            "destination" : 3,
            "matchedVertex" : "SOURCE",
            "directedType" : "EITHER"
        } ],
        "view" : {
            "entities" : {
            "entity" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
                    "predicates" : [ {
                    "class" : "IsLessThan",
                    "orEqualTo" : false,
                    "value" : 2
                    }, {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 5
                    } ]
                }
                } ]
            }
            }
        }
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        view=g.View( 
            entities=[ 
            g.ElementDefinition( 
                group="entity", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "count" 
                    ], 
                    predicate=g.Or( 
                    predicates=[ 
                        g.IsLessThan( 
                        value=2, 
                        or_equal_to=False 
                        ), 
                        g.IsMoreThan( 
                        value=5, 
                        or_equal_to=False 
                        ) 
                    ] 
                    ) 
                ) 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ), 
            g.EdgeSeed( 
            source=2, 
            destination=3, 
            directed_type="EITHER", 
            matched_vertex="SOURCE" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.data.element.Entity",
        "group" : "entity",
        "vertex" : 2,
        "properties" : {
            "count" : 1
        }
        } ]
        ```

??? example "Example fetching related entities with multiple count"

    Get edges related to 2 when source is less than 2 or destination is more than 3.

    When using an `Or` predicate with a multiple selected values, it is more complicated. First, you need to select all the values you want: `select(a, b, c)`. This will create an array of the selected values, `[a, b, c]`. You then need to use the `Or.Builder` to build your `Or` predicate, using `.select()` then `.execute()`. When selecting values in the `Or.Builder` you need to refer to the position in the `[a,b,c]` array. So to use property `a`, use position 0 - `select(0)`.
    
    === "Java"

        ``` java
        final GetElements operation = new GetElements.Builder()
                .input(new EntitySeed(2))
                .view(new View.Builder()
                        .edge("edge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(
                                        new ElementFilter.Builder()
                                                .select(IdentifierType.SOURCE.name(), IdentifierType.DESTINATION.name())
                                                .execute(new Or.Builder<>()
                                                        .select(0)
                                                        .execute(new IsLessThan(2))
                                                        .select(1)
                                                        .execute(new IsMoreThan(3))
                                                        .build())
                                                .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        } ],
        "view" : {
            "edges" : {
            "edge" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "SOURCE", "DESTINATION" ],
                "predicate" : {
                    "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
                    "predicates" : [ {
                    "class" : "IntegerTupleAdaptedPredicate",
                    "selection" : [ 0 ],
                    "predicate" : {
                        "class" : "IsLessThan",
                        "orEqualTo" : false,
                        "value" : 2
                    }
                    }, {
                    "class" : "IntegerTupleAdaptedPredicate",
                    "selection" : [ 1 ],
                    "predicate" : {
                        "class" : "IsMoreThan",
                        "orEqualTo" : false,
                        "value" : 3
                    }
                    } ]
                }
                } ]
            }
            }
        }
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        view=g.View( 
            edges=[ 
            g.ElementDefinition( 
                group="edge", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "SOURCE", 
                    "DESTINATION" 
                    ], 
                    predicate=g.Or( 
                    predicates=[ 
                        g.NestedPredicate( 
                        selection=[ 
                            0 
                        ], 
                        predicate=g.IsLessThan( 
                            value=2, 
                            or_equal_to=False 
                        ) 
                        ), 
                        g.NestedPredicate( 
                        selection=[ 
                            1 
                        ], 
                        predicate=g.IsMoreThan( 
                            value=3, 
                            or_equal_to=False 
                        ) 
                        ) 
                    ] 
                    ) 
                ) 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        [ {
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
        "source" : 1,
        "destination" : 2,
        "directed" : true,
        "matchedVertex" : "DESTINATION",
        "properties" : {
            "count" : 3
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
        } ]
        ```

??? example "Example fetching edges and including only specified properties in the results"

    Get entities and return only some properties.

    Note that because there are only two properties prior to asking to include only `vertex|count`, this returns the same results as asking to exclude `count`.
    
    === "Java"

        ``` java
        final Concat concat = new Concat();
        concat.setSeparator("|");
        final GetElements operation = new GetElements.Builder()
                .input(new EntitySeed(2))
                .view(new View.Builder()
                        .edge("edge", new ViewElementDefinition.Builder()
                                .transientProperty("vertex|count", String.class)
                                .properties("vertex|count")
                                .transformer(new ElementTransformer.Builder()
                                        .select(IdentifierType.SOURCE.name(), "count")
                                        .execute(concat)
                                        .project("vertex|count")
                                        .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        } ],
        "view" : {
            "edges" : {
            "edge" : {
                "transientProperties" : {
                "vertex|count" : "String"
                },
                "transformFunctions" : [ {
                "selection" : [ "SOURCE", "count" ],
                "function" : {
                    "class" : "Concat",
                    "separator" : "|"
                },
                "projection" : [ "vertex|count" ]
                } ],
                "properties" : [ "vertex|count" ]
            }
            }
        }
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        view=g.View( 
            edges=[ 
            g.ElementDefinition( 
                group="edge", 
                transient_properties={'vertex|count': 'java.lang.String'}, 
                transform_functions=[ 
                g.FunctionContext( 
                    selection=[ 
                    "SOURCE", 
                    "count" 
                    ], 
                    function=g.Concat( 
                    separator="|" 
                    ), 
                    projection=[ 
                    "vertex|count" 
                    ] 
                ) 
                ], 
                properties=[ 
                "vertex|count" 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|2]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[vertex|count=<java.lang.String>1|3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 2,
        "destination" : 3,
        "directed" : true,
        "matchedVertex" : "SOURCE",
        "properties" : {
            "vertex|count" : "2|2"
        }
        }, {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 2,
        "destination" : 4,
        "directed" : true,
        "matchedVertex" : "SOURCE",
        "properties" : {
            "vertex|count" : "2|1"
        }
        }, {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 1,
        "destination" : 2,
        "directed" : true,
        "matchedVertex" : "DESTINATION",
        "properties" : {
            "vertex|count" : "1|3"
        }
        }, {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 2,
        "destination" : 5,
        "directed" : true,
        "matchedVertex" : "SOURCE",
        "properties" : {
            "vertex|count" : "2|1"
        }
        } ]
        ```

??? example "Example fetching edges and excluding specified properties from the results"

    Get entities and exclude properties.

    Note that because there are only two properties prior to the exclusion, this returns the same results as asking to include only `vertex|count`.
    
    === "Java"

        ``` java
        final Concat concat = new Concat();
        concat.setSeparator("|");
        final GetElements operation = new GetElements.Builder()
                .input(new EntitySeed(2))
                .view(new View.Builder()
                        .edge("edge", new ViewElementDefinition.Builder()
                                .transientProperty("vertex|count", String.class)
                                .excludeProperties("count")
                                .transformer(new ElementTransformer.Builder()
                                        .select(IdentifierType.SOURCE.name(), "count")
                                        .execute(concat)
                                        .project("vertex|count")
                                        .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetElements",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        } ],
        "view" : {
            "edges" : {
            "edge" : {
                "transientProperties" : {
                "vertex|count" : "String"
                },
                "transformFunctions" : [ {
                "selection" : [ "SOURCE", "count" ],
                "function" : {
                    "class" : "Concat",
                    "separator" : "|"
                },
                "projection" : [ "vertex|count" ]
                } ],
                "excludeProperties" : [ "count" ]
            }
            }
        }
        }
        ```

    === "Python"

        ``` python
        g.GetElements( 
        view=g.View( 
            edges=[ 
            g.ElementDefinition( 
                group="edge", 
                transient_properties={'vertex|count': 'java.lang.String'}, 
                transform_functions=[ 
                g.FunctionContext( 
                    selection=[ 
                    "SOURCE", 
                    "count" 
                    ], 
                    function=g.Concat( 
                    separator="|" 
                    ), 
                    projection=[ 
                    "vertex|count" 
                    ] 
                ) 
                ], 
                exclude_properties=[ 
                "count" 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|2]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[vertex|count=<java.lang.String>1|3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[vertex|count=<java.lang.String>2|1]]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 2,
        "destination" : 3,
        "directed" : true,
        "matchedVertex" : "SOURCE",
        "properties" : {
            "vertex|count" : "2|2"
        }
        }, {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 2,
        "destination" : 4,
        "directed" : true,
        "matchedVertex" : "SOURCE",
        "properties" : {
            "vertex|count" : "2|1"
        }
        }, {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 1,
        "destination" : 2,
        "directed" : true,
        "matchedVertex" : "DESTINATION",
        "properties" : {
            "vertex|count" : "1|3"
        }
        }, {
        "class" : "uk.gov.gchq.gaffer.data.element.Edge",
        "group" : "edge",
        "source" : 2,
        "destination" : 5,
        "directed" : true,
        "matchedVertex" : "SOURCE",
        "properties" : {
            "vertex|count" : "2|1"
        }
        } ]
        ```

## GetAdjacentIds

Performs a single hop down related edges. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAdjacentIds.html)

??? example "Example fetching adjacent IDs from specified vertex"

    Get adjacent ids from vertex 2.
    
    === "Java"

        ``` java
        final GetAdjacentIds operation = new GetAdjacentIds.Builder()
                .input(new EntitySeed(2))
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetAdjacentIds",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        } ]
        }
        ```

    === "Python"

        ``` python
        g.GetAdjacentIds( 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        EntitySeed[vertex=3]
        EntitySeed[vertex=4]
        EntitySeed[vertex=5]
        EntitySeed[vertex=1]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 3
        }, {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 4
        }, {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 5
        }, {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 1
        } ]
        ```

??? example "Example fetching adjacent IDs from along outbound edges of specified vertex"

    Get adjacent ids along outbound edges from vertex 2
    
    === "Java"

        ``` java
        final GetAdjacentIds operation = new GetAdjacentIds.Builder()
                .input(new EntitySeed(2))
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetAdjacentIds",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        } ],
        "includeIncomingOutGoing" : "OUTGOING"
        }
        ```

    === "Python"

        ``` python
        g.GetAdjacentIds( 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ) 
        ], 
        include_incoming_out_going="OUTGOING" 
        )
        ```

    Results:

    === "Java"

        ``` java
        EntitySeed[vertex=3]
        EntitySeed[vertex=4]
        EntitySeed[vertex=5]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 3
        }, {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 4
        }, {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 5
        } ]
        ```

??? example "Example fetching adjacent IDs from along outbound edges of specified vertex with count"

    Get adjacent ids along outbound edges from vertex 2 with count greater than 1.
    
    === "Java"

        ``` java
        final GetAdjacentIds operation = new GetAdjacentIds.Builder()
                .input(new EntitySeed(2))
                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                .view(new View.Builder()
                        .edge("edge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(1))
                                        .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetAdjacentIds",
        "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
        } ],
        "view" : {
            "edges" : {
            "edge" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 1
                }
                } ]
            }
            }
        },
        "includeIncomingOutGoing" : "OUTGOING"
        }
        ```

    === "Python"

        ``` python
        g.GetAdjacentIds( 
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
                    value=1, 
                    or_equal_to=False 
                    ) 
                ) 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        input=[ 
            g.EntitySeed( 
            vertex=2 
            ) 
        ], 
        include_incoming_out_going="OUTGOING" 
        )
        ```

    Results:

    === "Java"

        ``` java
        EntitySeed[vertex=3]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 3
        } ]
        ```

## GetAllElements

Gets all elements, optionally using a provided View. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetAllElements.html)

??? example "Example fetching everything in the graph"

    === "Java"

        ``` java
        final GetAllElements operation = new GetAllElements();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetAllElements"
        }
        ```

    === "Full JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
        }
        ```

    === "Python"

        ``` python
        g.GetAllElements()
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        [ {
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
          "vertex" : 5,
          "properties" : {
            "count" : 3
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
          "class" : "uk.gov.gchq.gaffer.data.element.Entity",
          "group" : "entity",
          "vertex" : 2,
          "properties" : {
            "count" : 1
          }
        }, {
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
          "class" : "uk.gov.gchq.gaffer.data.element.Entity",
          "group" : "entity",
          "vertex" : 3,
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
          "source" : 2,
          "destination" : 5,
          "directed" : true,
          "matchedVertex" : "SOURCE",
          "properties" : {
            "count" : 1
          }
        } ]
        ```

??? example "Example fetching everything with a count greater than 2"

    === "Java"

        ``` java
        final GetAllElements operation = new GetAllElements.Builder()
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(2))
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(2))
                                        .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetAllElements",
          "view" : {
            "edges" : {
              "edge" : {
                "preAggregationFilterFunctions" : [ {
                  "selection" : [ "count" ],
                  "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 2
                  }
                } ]
              }
            },
            "entities" : {
              "entity" : {
                "preAggregationFilterFunctions" : [ {
                  "selection" : [ "count" ],
                  "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 2
                  }
                } ]
              }
            }
          }
        }
        ```

    === "Python"

        ``` python
        g.GetAllElements( 
          view=g.View( 
            entities=[ 
              g.ElementDefinition( 
                group="entity", 
                pre_aggregation_filter_functions=[ 
                  g.PredicateContext( 
                    selection=[ 
                      "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                      value=2, 
                      or_equal_to=False 
                    ) 
                  ) 
                ] 
              ) 
            ], 
            edges=[ 
              g.ElementDefinition( 
                group="edge", 
                pre_aggregation_filter_functions=[ 
                  g.PredicateContext( 
                    selection=[ 
                      "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                      value=2, 
                      or_equal_to=False 
                    ) 
                  ) 
                ] 
              ) 
            ], 
            all_edges=False, 
            all_entities=False 
          ) 
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        [ {
          "class" : "uk.gov.gchq.gaffer.data.element.Entity",
          "group" : "entity",
          "vertex" : 5,
          "properties" : {
            "count" : 3
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
        } ]
        ```

## GetWalks

Gets the [walks/paths](https://proofwiki.org/wiki/Definition:Walk_(Graph_Theory)) for a given vertex. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/GetWalks.html)

??? info "GetWalks Graph"
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

The examples for GetWalks use a modified graph. This graph contains two different edge groups, each with a modified count property. The count is set to the sum of the source and destination vertices. Additionally, the edge group is determined by whether this count property is even (group edge) or odd (group edge1).

??? example "Example getting walks"

    Gets all of the Walks of length 2 which start from vertex 1, with the added restriction that all edges must be traversed using the source as the matched vertex.
    
    === "Java"

        ``` java
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                .input(new EntitySeed(1))
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 ]
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
            "5" : [ ]
          } ]
        } ]
        ```

??? example "Example getting walks with incoming outgoing flags"

    Gets all of the Walks of length 2 which start from vertex 1. The IncludeIncomingOutgoingType flag can be used to determine which edge direction the Walk follows for each hop.

    === "Java"

        ``` java
        final GetWalks getWalks = new GetWalks.Builder()
                .operations(new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build(),
                        new GetElements.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                                .build())
                .input(new EntitySeed(1))
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 1 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 2 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 1 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 3 ]
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
            "3" : [ ]
          } ]
        } ]
        ```

??? example "Example getting walks with filtering"

    Gets all of the Walks of length 2 which start from vertex 1. This example demonstrates the use of pre-aggregation filters to select which edges to traverse based on a property on the edge.

    === "Java"

        ``` java
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]
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
        ```

??? example "Example getting walks with entities"

    Gets all of the Walks of length 2 which start from vertex 1, with all of the entities which are attached to the vertices found along the way.
    
    === "Java"

        ``` java
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 2 ]
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
        ```

??? example "Example getting walks with filtering on cardinality entities from first hop"

    Gets all of the Walks of length 2 which start from vertex 5, where the results of the first hop are filtered based on the cardinality entities in the graph.
    
    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 1 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 3 ]
        ```

    === "JSON"

        ``` json
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
        ```

??? example "Example getting walks with filtering on cardinality entities contained in walk"

    Gets all of the Walks of length 2 which start from vertex 5, where each Walk returned in the results is filtered based on total of the count property on the walks' edges being less than 11.
    
    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 1 ]
        ```

    === "JSON"

        ``` json
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
        ```

??? example "Example getting walks with multiple groups"

    Gets all of the Walks of length 2 which start from vertex 1. The IncludeIncomingOutgoingType flag can be used to determine which edge direction the Walk follows for each hop. Additionally, the group set in the view is used to only travel down certain edges in each hop.
    
    === "Java"

        ``` java
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 8 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 2 ]
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
        ```

??? example "Example getting walks with loops"

    Gets all of the Walks of length 6 which start from vertex 1, with the added restriction that all edges must be traversed using the source as the matched vertex. This demonstrates the behaviour when previously traversed edges are encountered again.
    
    === "Java"

        ``` java
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 5 --> 6 --> 3 --> 2 --> 5 --> 6 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 --> 6 --> 3 --> 4 --> 7 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 1 --> 2 --> 5 --> 6 --> 3 --> 2 --> 5 ]
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
            "5" : [ ]
          } ]
        } ]
        ```

??? example "Example getting walks with self loops"

    Gets all of the Walks of length 3 which start from vertex 8, with the added restriction that all edges must be traversed using the source as the matched vertex. This demonstrates the behaviour when self loops exist in the graph.
    
    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 8 --> 8 --> 8 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 8 --> 8 --> 5 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 8 --> 5 --> 6 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 5 --> 6 --> 3 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 8 --> 5 --> 6 --> 7 ]
        ```

    === "JSON"

        ``` json
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
        } ]
        ```

??? example "Example getting walks with additional operations"

    Gets all of the Walks of length 2 which start from vertex 5, where an additional operation is inserted between the GetElements operations used to retrieve elements.
    
    === "Java"

        ``` java
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
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 1 ]
        uk.gov.gchq.gaffer.data.graph.Walk[ 5 --> 2 --> 3 ]
        ```

    === "JSON"

        ``` json
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
        ```
