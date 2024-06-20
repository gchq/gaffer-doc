# Core Operations

These Operations form the core of Gaffer. They are always available.

Unless otherwise specified, this directed graph is used in the examples on this page:

``` mermaid
graph TD
  1(1, count=3) -- count=3 --> 2
  1 -- count=1 --> 4
  2(2, count=1) -- count=2 --> 3
  2 -- count=1 --> 4(4, count=1)
  2 -- count=1 --> 5(5, count=3)
  3(3, count=2) -- count=4 --> 4
```

## AddElements

Adds elements to a graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElements.html)

??? example "Example adding a new entity and edge"

    Adding an additonal entity '6' and edge connecting it to '5'

    === "Java"

        ``` java
        new AddElements.Builder()
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
                    .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "AddElements",
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
          "skipInvalidElements" : false,
          "validate" : true
        }
        ```

    === "Python"

        ``` python
        g.AddElements(
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
          ],
          skip_invalid_elements=False,
          validate=True
        )
        ```

    Results:

    ``` mermaid
    graph TD
      1 --> 2
      1 --> 4
      2 --> 3
      2 --> 4
      2 --> 5
      3 --> 4
      5 --> 6
    ```

## DeleteElements

Deletes Elements from a graph. [Javadoc](....)

??? example "Example deleting an entity and edge"

    Deleting entity '6' and its edge to 5.

    === "Java"

        ``` java
        final OperationChain<Void> deleteElementsChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                    .input(new EntitySeed(6))
                    .build())
                .then(new DeleteElements())
                .build();

        graph.execute(deleteElementsChain, new User());
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [{
                "class": "GetElements",
                "input": [{
                    "class": "EntitySeed",
                    "vertex": 6,
                }]
            },
            {
                "class" : "DeleteElements"
            }]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
            operations=[
                g.GetElements(input=[g.EntitySeed(vertex=6)]),
                g.DeleteElements()
            ]
        )
        ```

    Results:

    ``` mermaid
    graph TD
      1 --> 2
      1 --> 4
      2 --> 3
      2 --> 4
      2 --> 5
      3 --> 4
    ```

## Aggregate

The Aggregate operation would normally be used in an Operation Chain to aggregate the results of a previous operation. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/function/Aggregate.html)

??? example "Example simple aggregate elements"

    === "Java"

        ``` java
        final Aggregate aggregate = new Aggregate();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Aggregate"
        }
        ```

    === "Python"

        ``` python
        g.Aggregate()
        ```

??? example "Example aggregate only edges of type edge with a transient property and provided aggregator"

    The groupBy has been set to an empty array. This will override the groupBy value in the schema.

    === "Java"

        ``` java
        final String[] groupBy = {};
        final Aggregate aggregate = new Aggregate.Builder()
                .edge("edge", new AggregatePair(
                        groupBy,
                        new ElementAggregator.Builder()
                                .select("transientProperty1")
                                .execute(new StringConcat())
                                .build()))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Aggregate",
          "edges" : {
            "edge" : {
              "elementAggregator" : {
                "operators" : [ {
                  "selection" : [ "transientProperty1" ],
                  "binaryOperator" : {
                    "class" : "StringConcat",
                    "separator" : ","
                  }
                } ]
              },
              "groupBy" : [ ]
            }
          }
        }
        ```

    === "Python"

        ``` python
        g.Aggregate(
          edges=[
            g.AggregatePair(
              group="edge",
              group_by=[
              ],
              element_aggregator=g.ElementAggregateDefinition(
                operators=[
                  g.BinaryOperatorContext(
                    selection=[
                      "transientProperty1"
                    ],
                    binary_operator=g.BinaryOperator(
                      class_name="uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat",
                      fields={'separator': ','}
                    )
                  )
                ]
              )
            )
          ]
        )
        ```

## Count

Counts the number of items in an iterable. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/Count.html)

??? example "Example counting all elements"

    === "Java"

        ``` java
        OperationChain<Long> countAllElements = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new Count<>())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetAllElements"
          }, {
            "class" : "Count"
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetAllElements(),
            g.Count()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        11
        ```

    === "JSON"

        ``` json
        11
        ```

## CountGroups

Counts the different element groups. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/CountGroups.html)

??? example "Example counting all element groups"

    === "Java"

        ``` java
        final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new CountGroups())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetAllElements"
          }, {
            "class" : "CountGroups"
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetAllElements(),
            g.CountGroups()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        GroupCounts[entityGroups={entity=5},edgeGroups={edge=6},limitHit=false]
        ```

    === "JSON"

        ``` json
        {
          "entityGroups" : {
            "entity" : 5
          },
          "edgeGroups" : {
            "edge" : 6
          },
          "limitHit" : false
        }
        ```

??? example "Example counting all element groups with limit"

    === "Java"

        ``` java
        final OperationChain<GroupCounts> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new CountGroups(5))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetAllElements"
          }, {
            "class" : "CountGroups",
            "limit" : 5
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetAllElements(),
            g.CountGroups(
              limit=5
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        GroupCounts[entityGroups={entity=3},edgeGroups={edge=2},limitHit=true]
        ```

    === "JSON"

        ``` json
        {
          "entityGroups" : {
            "entity" : 3
          },
          "edgeGroups" : {
            "edge" : 2
          },
          "limitHit" : true
        }
        ```

## Filter

Filters elements. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/function/Filter.html)

??? example "Example filtering for elements with a count more than 2"

    The filter will only return elements (Entities and Edges) with a count more than 2. The results show the Edge between 1 & 4 that has a count of 1 has been removed.

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                        .name("1-hop")
                        .input(new EntitySeed(1))
                        .build())
                .then(new Filter.Builder()
                        .globalElements(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "NamedOperation",
            "input" : [ {
              "class" : "EntitySeed",
              "class" : "EntitySeed",
              "vertex" : 1
            } ],
            "operationName" : "1-hop"
          }, {
            "class" : "Filter",
            "globalElements" : {
              "predicates" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                  "class" : "IsMoreThan",
                  "orEqualTo" : false,
                  "value" : 2
                }
              } ]
            }
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.NamedOperation(
              input=[
                g.EntitySeed(
                  vertex=1
                )
              ],
              operation_name="1-hop"
            ),
            g.Filter(
              global_elements=g.GlobalElementFilterDefinition(
                predicates=[
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

??? example "Example filtering for edges of type edge with count more than 2"

    Similar to the previous example but this will only return Edges with group 'edge' that have a count more than 2.

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                        .name("1-hop")
                        .input(new EntitySeed(1))
                        .build())
                .then(new Filter.Builder()
                        .edge("edge", new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "NamedOperation",
            "input" : [ {
              "class" : "EntitySeed",
              "class" : "EntitySeed",
              "vertex" : 1
            } ],
            "operationName" : "1-hop"
          }, {
            "class" : "Filter",
            "edges" : {
              "edge" : {
                "predicates" : [ {
                  "selection" : [ "count" ],
                  "predicate" : {
                    "class" : "IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 2
                  }
                } ]
              }
            }
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.NamedOperation(
              input=[
                g.EntitySeed(
                  vertex=1
                )
              ],
              operation_name="1-hop"
            ),
            g.Filter(
              edges=[
                g.ElementFilterDefinition(
                  group="edge",
                  predicates=[
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
              ]
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        [ {
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

## Limit

Limits the number of elements returned. This truncates output by default, but optionally an exception can be thrown instead of truncating. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/Limit.html)

??? example "Example limiting elements to 3"

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new Limit<>(3))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetAllElements"
          }, {
            "class" : "Limit",
            "resultLimit" : 3,
            "truncate" : true
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetAllElements(),
            g.Limit(
              result_limit=3,
              truncate=True
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
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
        } ]
        ```

??? example "Example limiting elements to 3 without truncation"

    Setting this flag to false will throw an error instead of truncating the iterable. In this case there are more than 3 elements, so when executed a LimitExceededException would be thrown.

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new Limit<>(3, false))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetAllElements"
          }, {
            "class" : "Limit",
            "resultLimit" : 3,
            "truncate" : false
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetAllElements(),
            g.Limit(
              result_limit=3,
              truncate=False
            )
          ]
        )
        ```

??? example "Example limiting elements to 3 with builder"

    A builder can also be used to create the limit - note that truncate is set to true by default, so in this case it is redundant, but simply shown for demonstration.

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new Limit.Builder<Element>()
                        .resultLimit(3)
                        .truncate(true)
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetAllElements"
          }, {
            "class" : "Limit",
            "resultLimit" : 3,
            "truncate" : true
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetAllElements(),
            g.Limit(
              result_limit=3,
              truncate=True
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
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
        } ]
        ```

## Min

Extracts the minimum element based on provided Comparators. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Min.html)

??? example "Example min count"

    === "Java"

        ``` java
        final OperationChain<Element> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new Min.Builder()
                        .comparators(new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
            "comparators" : [ {
              "class" : "ElementPropertyComparator",
              "property" : "count",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            } ]
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.Min(
              comparators=[
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="count",
                  reversed=False
                )
              ]
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        {
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
        ```

??? example "Example min count and transient property"

    === "Java"

        ``` java
        final OperationChain<Element> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .view(new View.Builder()
                                .entity("entity", new ViewElementDefinition.Builder()
                                        .transientProperty("score", Integer.class)
                                        .transformer(new ElementTransformer.Builder()
                                                .select("VERTEX", "count")
                                                .execute(new ExampleScoreFunction())
                                                .project("score")
                                                .build())
                                        .build())
                                .edge("edge", new ViewElementDefinition.Builder()
                                        .transientProperty("score", Integer.class)
                                        .transformer(new ElementTransformer.Builder()
                                                .select("DESTINATION", "count")
                                                .execute(new ExampleScoreFunction())
                                                .project("score")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .then(new Min.Builder()
                        .comparators(
                                new ElementPropertyComparator.Builder()
                                        .groups("entity", "edge")
                                        .property("count")
                                        .reverse(false)
                                        .build(),
                                new ElementPropertyComparator.Builder()
                                        .groups("entity", "edge")
                                        .property("score")
                                        .reverse(false)
                                        .build()
                        )
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "edges" : {
                "edge" : {
                  "transientProperties" : {
                    "score" : "Integer"
                  },
                  "transformFunctions" : [ {
                    "selection" : [ "DESTINATION", "count" ],
                    "function" : {
                      "class" : "ExampleScoreFunction"
                    },
                    "projection" : [ "score" ]
                  } ]
                }
              },
              "entities" : {
                "entity" : {
                  "transientProperties" : {
                    "score" : "Integer"
                  },
                  "transformFunctions" : [ {
                    "selection" : [ "VERTEX", "count" ],
                    "function" : {
                      "class" : "ExampleScoreFunction"
                    },
                    "projection" : [ "score" ]
                  } ]
                }
              }
            }
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Min",
            "comparators" : [ {
              "class" : "ElementPropertyComparator",
              "property" : "count",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            }, {
              "class" : "ElementPropertyComparator",
              "property" : "score",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            } ]
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetElements(
              view=g.View(
                entities=[
                  g.ElementDefinition(
                    group="entity",
                    transient_properties={'score': 'java.lang.Integer'},
                    transform_functions=[
                      g.FunctionContext(
                        selection=[
                          "VERTEX",
                          "count"
                        ],
                        function=g.Function(
                          class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction",
                          fields={}
                        ),
                        projection=[
                          "score"
                        ]
                      )
                    ]
                  )
                ],
                edges=[
                  g.ElementDefinition(
                    group="edge",
                    transient_properties={'score': 'java.lang.Integer'},
                    transform_functions=[
                      g.FunctionContext(
                        selection=[
                          "DESTINATION",
                          "count"
                        ],
                        function=g.Function(
                          class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction",
                          fields={}
                        ),
                        projection=[
                          "score"
                        ]
                      )
                    ]
                  )
                ],
                all_edges=False,
                all_entities=False
              ),
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ]
            ),
            g.Min(
              comparators=[
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="count",
                  reversed=False
                ),
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="score",
                  reversed=False
                )
              ]
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=2,group=entity,properties=Properties[score=<java.lang.Integer>2,count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.gaffer.data.element.Entity",
          "group" : "entity",
          "vertex" : 2,
          "properties" : {
            "score" : 2,
            "count" : 1
          }
        }
        ```

## Max

Extracts the maximum element based on provided Comparators. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Max.html)

??? example "Example max count"

    === "Java"

        ``` java
        final OperationChain<Element> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new Max.Builder()
                        .comparators(new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
            "comparators" : [ {
              "class" : "ElementPropertyComparator",
              "property" : "count",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            } ]
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.Max(
              comparators=[
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="count",
                  reversed=False
                )
              ]
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.gaffer.data.element.Entity",
          "group" : "entity",
          "vertex" : 1,
          "properties" : {
            "count" : 3
          }
        }
        ```

??? example "Example max count and transient property"

    === "Java"

        ``` java
        final OperationChain<Element> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .view(new View.Builder()
                                .entity("entity", new ViewElementDefinition.Builder()
                                        .transientProperty("score", Integer.class)
                                        .transformer(new ElementTransformer.Builder()
                                                .select("VERTEX", "count")
                                                .execute(new ExampleScoreFunction())
                                                .project("score")
                                                .build())
                                        .build())
                                .edge("edge", new ViewElementDefinition.Builder()
                                        .transientProperty("score", Integer.class)
                                        .transformer(new ElementTransformer.Builder()
                                                .select("DESTINATION", "count")
                                                .execute(new ExampleScoreFunction())
                                                .project("score")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .then(new Max.Builder()
                        .comparators(
                                new ElementPropertyComparator.Builder()
                                        .groups("entity", "edge")
                                        .property("count")
                                        .reverse(false)
                                        .build(),
                                new ElementPropertyComparator.Builder()
                                        .groups("entity", "edge")
                                        .property("score")
                                        .reverse(false)
                                        .build()
                        )
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "edges" : {
                "edge" : {
                  "transientProperties" : {
                    "score" : "Integer"
                  },
                  "transformFunctions" : [ {
                    "selection" : [ "DESTINATION", "count" ],
                    "function" : {
                      "class" : "ExampleScoreFunction"
                    },
                    "projection" : [ "score" ]
                  } ]
                }
              },
              "entities" : {
                "entity" : {
                  "transientProperties" : {
                    "score" : "Integer"
                  },
                  "transformFunctions" : [ {
                    "selection" : [ "VERTEX", "count" ],
                    "function" : {
                      "class" : "ExampleScoreFunction"
                    },
                    "projection" : [ "score" ]
                  } ]
                }
              }
            }
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Max",
            "comparators" : [ {
              "class" : "ElementPropertyComparator",
              "property" : "count",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            }, {
              "class" : "ElementPropertyComparator",
              "property" : "score",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            } ]
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetElements(
              view=g.View(
                entities=[
                  g.ElementDefinition(
                    group="entity",
                    transient_properties={'score': 'java.lang.Integer'},
                    transform_functions=[
                      g.FunctionContext(
                        selection=[
                          "VERTEX",
                          "count"
                        ],
                        function=g.Function(
                          class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction",
                          fields={}
                        ),
                        projection=[
                          "score"
                        ]
                      )
                    ]
                  )
                ],
                edges=[
                  g.ElementDefinition(
                    group="edge",
                    transient_properties={'score': 'java.lang.Integer'},
                    transform_functions=[
                      g.FunctionContext(
                        selection=[
                          "DESTINATION",
                          "count"
                        ],
                        function=g.Function(
                          class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction",
                          fields={}
                        ),
                        projection=[
                          "score"
                        ]
                      )
                    ]
                  )
                ],
                all_edges=False,
                all_entities=False
              ),
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ]
            ),
            g.Max(
              comparators=[
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="count",
                  reversed=False
                ),
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="score",
                  reversed=False
                )
              ]
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[score=<java.lang.Integer>6,count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.gaffer.data.element.Edge",
          "group" : "edge",
          "source" : 1,
          "destination" : 2,
          "directed" : true,
          "matchedVertex" : "SOURCE",
          "properties" : {
            "score" : 6,
            "count" : 3
          }
        }
        ```

## Sort

Sorts elements based on provided Comparators and can be used to extract the top 'n' elements. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/compare/Sort.html)

??? example "Example sorting on count"

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new Sort.Builder()
                        .comparators(new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .reverse(false)
                                .build())
                        .resultLimit(10)
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "Sort",
            "comparators" : [ {
              "class" : "ElementPropertyComparator",
              "property" : "count",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            } ],
            "deduplicate" : true,
            "resultLimit" : 10
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.Sort(
              comparators=[
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="count",
                  reversed=False
                )
              ],
              result_limit=10,
              deduplicate=True
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        [ {
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
          "source" : 2,
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
          "source" : 1,
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
          "destination" : 3,
          "directed" : true,
          "matchedVertex" : "SOURCE",
          "properties" : {
            "count" : 2
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

??? example "Example sorting on count without deduplicating"

    Deduplication is true by default.

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new Sort.Builder()
                        .comparators(new ElementPropertyComparator.Builder()
                                .groups("entity", "edge")
                                .property("count")
                                .reverse(false)
                                .build())
                        .resultLimit(10)
                        .deduplicate(false)
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "Sort",
            "comparators" : [ {
              "class" : "ElementPropertyComparator",
              "property" : "count",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            } ],
            "deduplicate" : false,
            "resultLimit" : 10
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.Sort(
              comparators=[
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="count",
                  reversed=False
                )
              ],
              result_limit=10,
              deduplicate=False
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
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
          "vertex" : 2,
          "properties" : {
            "count" : 1
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

??? example "Example sorting on count and transient property"

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .view(new View.Builder()
                                .entity("entity", new ViewElementDefinition.Builder()
                                        .transientProperty("score", Integer.class)
                                        .transformer(new ElementTransformer.Builder()
                                                .select("VERTEX", "count")
                                                .execute(new ExampleScoreFunction())
                                                .project("score")
                                                .build())
                                        .build())
                                .edge("edge", new ViewElementDefinition.Builder()
                                        .transientProperty("score", Integer.class)
                                        .transformer(new ElementTransformer.Builder()
                                                .select("DESTINATION", "count")
                                                .execute(new ExampleScoreFunction())
                                                .project("score")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .then(new Sort.Builder()
                        .comparators(
                                new ElementPropertyComparator.Builder()
                                        .groups("entity", "edge")
                                        .property("count")
                                        .reverse(false)
                                        .build(),
                                new ElementPropertyComparator.Builder()
                                        .groups("entity", "edge")
                                        .property("score")
                                        .reverse(false)
                                        .build()
                        )
                        .resultLimit(4)
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "edges" : {
                "edge" : {
                  "transientProperties" : {
                    "score" : "Integer"
                  },
                  "transformFunctions" : [ {
                    "selection" : [ "DESTINATION", "count" ],
                    "function" : {
                      "class" : "ExampleScoreFunction"
                    },
                    "projection" : [ "score" ]
                  } ]
                }
              },
              "entities" : {
                "entity" : {
                  "transientProperties" : {
                    "score" : "Integer"
                  },
                  "transformFunctions" : [ {
                    "selection" : [ "VERTEX", "count" ],
                    "function" : {
                      "class" : "ExampleScoreFunction"
                    },
                    "projection" : [ "score" ]
                  } ]
                }
              }
            }
          }, {
            "class" : "Sort",
            "comparators" : [ {
              "class" : "ElementPropertyComparator",
              "property" : "count",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            }, {
              "class" : "ElementPropertyComparator",
              "property" : "score",
              "groups" : [ "entity", "edge" ],
              "reversed" : false
            } ],
            "deduplicate" : true,
            "resultLimit" : 4
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetElements(
              view=g.View(
                entities=[
                  g.ElementDefinition(
                    group="entity",
                    transient_properties={'score': 'java.lang.Integer'},
                    transform_functions=[
                      g.FunctionContext(
                        selection=[
                          "VERTEX",
                          "count"
                        ],
                        function=g.Function(
                          class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction",
                          fields={}
                        ),
                        projection=[
                          "score"
                        ]
                      )
                    ]
                  )
                ],
                edges=[
                  g.ElementDefinition(
                    group="edge",
                    transient_properties={'score': 'java.lang.Integer'},
                    transform_functions=[
                      g.FunctionContext(
                        selection=[
                          "DESTINATION",
                          "count"
                        ],
                        function=g.Function(
                          class_name="uk.gov.gchq.gaffer.doc.operation.function.ExampleScoreFunction",
                          fields={}
                        ),
                        projection=[
                          "score"
                        ]
                      )
                    ]
                  )
                ],
                all_edges=False,
                all_entities=False
              ),
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ]
            ),
            g.Sort(
              comparators=[
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="count",
                  reversed=False
                ),
                g.ElementPropertyComparator(
                  groups=[
                    "entity",
                    "edge"
                  ],
                  property="score",
                  reversed=False
                )
              ],
              result_limit=4,
              deduplicate=True
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=2,group=entity,properties=Properties[score=<java.lang.Integer>2,count=<java.lang.Integer>1]]
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[score=<java.lang.Integer>4,count=<java.lang.Integer>1]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[score=<java.lang.Integer>4,count=<java.lang.Integer>1]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[score=<java.lang.Integer>5,count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        [ {
          "class" : "uk.gov.gchq.gaffer.data.element.Entity",
          "group" : "entity",
          "vertex" : 2,
          "properties" : {
            "score" : 2,
            "count" : 1
          }
        }, {
          "class" : "uk.gov.gchq.gaffer.data.element.Edge",
          "group" : "edge",
          "source" : 1,
          "destination" : 4,
          "directed" : true,
          "matchedVertex" : "SOURCE",
          "properties" : {
            "score" : 4,
            "count" : 1
          }
        }, {
          "class" : "uk.gov.gchq.gaffer.data.element.Edge",
          "group" : "edge",
          "source" : 2,
          "destination" : 4,
          "directed" : true,
          "matchedVertex" : "SOURCE",
          "properties" : {
            "score" : 4,
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
            "score" : 5,
            "count" : 1
          }
        } ]
        ```

## Reduce

Reduces an input to an output with a single value using provided function. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/Reduce.html)

??? example "Example of reduce"

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
                                .then(new Reduce.Builder<>()
                                        .aggregateFunction(new Sum())
                                        .build())
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
              }, {
                "class" : "Reduce",
                "aggregateFunction" : {
                  "class" : "Sum"
                }
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
                  g.ToVertices(),
                  g.Reduce(
                    aggregate_function=g.Sum()
                  )
                ]
              )
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        6
        13
        ```

    === "JSON"

        ``` json
        [ 6, 13 ]
        ```

## Map

Maps an input to an output using provided functions. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/Map.html)

??? example "Example extracting from get elements"

    This simple example demonstrates retrieving elements from the "entity" group, from which the first item is extracted.

    === "Java"

        ``` java
        final OperationChain<?> operationChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .view(new View.Builder()
                                .entity("entity")
                                .build())
                        .build())
                .then(new Map.Builder<Iterable<? extends Element>>()
                        .first(new FirstItem<>())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "entities" : {
                "entity" : { }
              }
            }
          }, {
            "class" : "Map",
            "functions" : [ {
              "class" : "FirstItem"
            } ]
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetElements(
              view=g.View(
                entities=[
                  g.ElementDefinition(
                    group="entity"
                  )
                ],
                all_edges=False,
                all_entities=False
              ),
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ]
            ),
            g.Map(
              functions=[
                g.FirstItem()
              ]
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
        {
          "class" : "uk.gov.gchq.gaffer.data.element.Entity",
          "group" : "entity",
          "vertex" : 2,
          "properties" : {
            "count" : 1
          }
        }
        ```

??? example "Example extracting first items from walks"

    This example demonstrates the extraction of the input seeds to a GetWalks operation, using the Map operation with ExtractWalkEdgesFromHop, and FirstItem functions.

    === "Java"

        ``` java
        final OperationChain<Set<?>> opChain = new OperationChain.Builder()
                .first(new GetWalks.Builder()
                        .operations(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .resultsLimit(100)
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new Map.Builder<Iterable<Walk>>()
                        .first(new IterableFunction.Builder<Walk>()
                                .first(new ExtractWalkEdgesFromHop(0))
                                .then(new FirstItem<>())
                                .build())
                        .build())
                .then(new ToVertices.Builder()
                        .edgeVertices(ToVertices.EdgeVertices.SOURCE)
                        .build())
                .then(new ToSet<>())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "OperationChain",
          "operations" : [ {
            "class" : "GetWalks",
            "input" : [ {
              "class" : "EntitySeed",
              "vertex" : 1
            }, {
              "class" : "EntitySeed",
              "vertex" : 2
            } ],
            "operations" : [ {
              "class" : "OperationChain",
              "operations" : [ {
                "class" : "GetElements",
                "view" : {
                  "edges" : {
                    "edge" : { }
                  }
                }
              } ]
            } ],
            "resultsLimit" : 100
          }, {
            "class" : "Map",
            "functions" : [ {
              "class" : "IterableFunction",
              "functions" : [ {
                "class" : "ExtractWalkEdgesFromHop",
                "hop" : 0
              }, {
                "class" : "FirstItem"
              } ]
            } ]
          }, {
            "class" : "ToVertices",
            "edgeVertices" : "SOURCE"
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetWalks(
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
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
                      )
                    )
                  ]
                )
              ],
              results_limit=100
            ),
            g.Map(
              functions=[
                g.IterableFunction(
                  functions=[
                    g.ExtractWalkEdgesFromHop(
                      hop=0
                    ),
                    g.FirstItem()
                  ]
                )
              ]
            ),
            g.ToVertices(
              edge_vertices="SOURCE"
            ),
            g.ToSet()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        1
        2
        ```

    === "JSON"

        ``` json
        [ 1, 2 ]
        ```

## Transform

The Transform operation would normally be used in an Operation Chain to transform the results of a previous operation. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/function/Transform.html)

??? example "Example transforming count property into count string property only for edges of type edge"

    === "Java"

        ``` java
        final Transform transform = new Transform.Builder()
                .edge("edge", new ElementTransformer.Builder()
                        .select("count")
                        .execute(new ToString())
                        .project("countString")
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Transform",
          "edges" : {
            "edge" : {
              "functions" : [ {
                "selection" : [ "count" ],
                "function" : {
                  "class" : "ToString"
                },
                "projection" : [ "countString" ]
              } ]
            }
          }
        }
        ```

    === "Python"

        ``` python
        g.Transform(
          edges=[
            g.ElementTransformDefinition(
              group="edge",
              functions=[
                g.FunctionContext(
                  selection=[
                    "count"
                  ],
                  function=g.ToString(),
                  projection=[
                    "countString"
                  ]
                )
              ]
            )
          ]
        )
        ```

## ToArray

Converts elements to Array. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToArray.html)

!!! note

    Conversion into an Array is done in memory, so it is not advised for a large number of results.

??? example "Example ToArray"

    === "Java"

        ``` java
        final OperationChain<? extends Element[]> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new ToArray<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToArray"
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.ToArray()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
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

## ToCsv

Converts elements to CSV Strings. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToCsv.html)

??? example "Example ToCsv"

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends String>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new ToCsv.Builder()
                        .includeHeader(true)
                        .generator(new CsvGenerator.Builder()
                                .group("Edge group")
                                .vertex("vertex")
                                .source("source")
                                .property("count", "total count")
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "ToCsv",
            "elementGenerator" : {
              "class" : "CsvGenerator",
              "fields" : {
                "GROUP" : "Edge group",
                "VERTEX" : "vertex",
                "SOURCE" : "source",
                "count" : "total count"
              },
              "constants" : { },
              "quoted" : false,
              "commaReplacement" : " "
            },
            "includeHeader" : true
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.ToCsv(
              element_generator=g.CsvGenerator(
                fields={'GROUP': 'Edge group', 'VERTEX': 'vertex', 'SOURCE': 'source', 'count': 'total count'},
                constants={},
                quoted=False,
                comma_replacement=" "
              ),
              include_header=True
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge group,vertex,source,total count
        edge,,1,1
        entity,2,,1
        entity,1,,3
        edge,,2,2
        edge,,2,1
        edge,,1,3
        edge,,2,1
        ```

    === "JSON"

        ``` json
        [ "Edge group,vertex,source,total count", "edge,,1,1", "entity,2,,1", "entity,1,,3", "edge,,2,2", "edge,,2,1", "edge,,1,3", "edge,,2,1" ]
        ```

## ToEntitySeeds

Converts object(s) into EntitySeeds. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToEntitySeeds.html)

??? example "Example ToEntitySeeds"

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends EntitySeed>> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new ToEntitySeeds())
                .build();
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        EntitySeed[vertex=Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
        EntitySeed[vertex=Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]]
        EntitySeed[vertex=Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]]
        EntitySeed[vertex=Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]]
        EntitySeed[vertex=Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
        EntitySeed[vertex=Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]]
        EntitySeed[vertex=Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]]
        ```

    === "JSON"

        ``` json
        [ {
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
        }, {
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
              "source" : 2,
              "destination" : 5,
              "directed" : true,
              "matchedVertex" : "SOURCE",
              "properties" : {
                "count" : 1
              }
            }
          }
        } ]
        ```

## ToList

Converts elements to a List. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToList.html)

!!! note

    Conversion into a List is done using an in memory ArrayList, so it is not advised for a large number of results.

??? example "Example ToList"

    === "Java"

        ``` java
        final OperationChain<List<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new ToList<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToList"
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.ToList()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
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

## ToMap

Converts elements to a Map of key-value pairs. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToMap.html)

??? example "Example ToMap"

    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Map<String, Object>>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new ToMap.Builder()
                        .generator(new MapGenerator.Builder()
                                .group("group")
                                .vertex("vertex")
                                .source("source")
                                .property("count", "total count")
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "ToMap",
            "elementGenerator" : {
              "class" : "MapGenerator",
              "fields" : {
                "GROUP" : "group",
                "VERTEX" : "vertex",
                "SOURCE" : "source",
                "count" : "total count"
              },
              "constants" : { }
            }
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.ToMap(
              element_generator=g.MapGenerator(
                fields={'GROUP': 'group', 'VERTEX': 'vertex', 'SOURCE': 'source', 'count': 'total count'},
                constants={}
              )
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        {group=edge, source=1, total count=1}
        {group=entity, vertex=2, total count=1}
        {group=entity, vertex=1, total count=3}
        {group=edge, source=2, total count=2}
        {group=edge, source=2, total count=1}
        {group=edge, source=1, total count=3}
        {group=edge, source=2, total count=1}
        ```

    === "JSON"

        ``` json
        [ {
          "group" : "edge",
          "source" : 1,
          "total count" : 1
        }, {
          "group" : "entity",
          "vertex" : 2,
          "total count" : 1
        }, {
          "group" : "entity",
          "vertex" : 1,
          "total count" : 3
        }, {
          "group" : "edge",
          "source" : 2,
          "total count" : 2
        }, {
          "group" : "edge",
          "source" : 2,
          "total count" : 1
        }, {
          "group" : "edge",
          "source" : 1,
          "total count" : 3
        }, {
          "group" : "edge",
          "source" : 2,
          "total count" : 1
        } ]
        ```

## ToSet

Converts elements to a Set. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToSet.html)

!!! note

    Conversion into a Set is done using an in memory LinkedHashSet, so it is not advised for a large number of results.

??? example "Example ToSet"

    === "Java"

        ``` java
        final OperationChain<Set<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new ToSet<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.ToSet()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
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

## ToSingletonList

Converts a single input of type T to a List. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToSingletonList.html)

??? example "Example ToSingletonList"

    === "Java"

        ``` java
        final ToSingletonList<Integer> opChain = new ToSingletonList.Builder<Integer>()
                .input(4)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToSingletonList",
          "input" : 4
        }
        ```

    === "Python"

        ``` python
        g.ToSingletonList(
          input=4
        )
        ```

    Results:

    === "Java"

        ``` java
        4
        ```

    === "JSON"

        ``` json
        [ 4 ]
        ```

??? example "Example of ToSingletonList in an Operation Chain"

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

## ToStream

Converts elements to a Stream. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToStream.html)

!!! note

    Conversion into a Stream is done in memory, so it is not advised for a large number of results.

??? example "Example ToStream"

    === "Java"

        ``` java
        final OperationChain<Stream<? extends Element>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new ToStream<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            "class" : "ToStream"
          } ]
        }
        ```

    === "Python"

        ``` python
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
            g.ToStream()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        {
          "parallel" : false
        }
        ```

## ToVertices

Converts ElementIds into vertices. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/output/ToVertices.html)

The examples use a ToSet operation after the ToVertices operation to deduplicate the results.

??? example "Example extracting entity vertices"

    === "Java"

        ``` java
        final OperationChain<Set<?>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .view(new View.Builder()
                                .entity("entity")
                                .build())
                        .build())
                .then(new ToVertices.Builder()
                        .edgeVertices(ToVertices.EdgeVertices.NONE)
                        .build())
                .then(new ToSet<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "entities" : {
                "entity" : { }
              }
            }
          }, {
            "class" : "ToVertices",
            "edgeVertices" : "NONE"
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
          } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain(
          operations=[
            g.GetElements(
              view=g.View(
                entities=[
                  g.ElementDefinition(
                    group="entity"
                  )
                ],
                all_edges=False,
                all_entities=False
              ),
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ]
            ),
            g.ToVertices(
              edge_vertices="NONE"
            ),
            g.ToSet()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        2
        1
        ```

    === "JSON"

        ``` json
        [ 2, 1 ]
        ```

??? example "Example extracting destination vertex"

    === "Java"

        ``` java
        final OperationChain<Set<?>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ToVertices.Builder()
                        .edgeVertices(ToVertices.EdgeVertices.DESTINATION)
                        .build())
                .then(new ToSet<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "edges" : {
                "edge" : { }
              }
            },
            "includeIncomingOutGoing" : "OUTGOING"
          }, {
            "class" : "ToVertices",
            "edgeVertices" : "DESTINATION"
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
          } ]
        }
        ```

    === "Python"

        ``` python
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
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ],
              include_incoming_out_going="OUTGOING"
            ),
            g.ToVertices(
              edge_vertices="DESTINATION"
            ),
            g.ToSet()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        4
        3
        2
        5
        ```

    === "JSON"

        ``` json
        [ 4, 3, 2, 5 ]
        ```

??? example "Example extracting both source and destination vertices"

    === "Java"

        ``` java
        final OperationChain<Set<?>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ToVertices.Builder()
                        .edgeVertices(ToVertices.EdgeVertices.BOTH)
                        .build())
                .then(new ToSet<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "edges" : {
                "edge" : { }
              }
            },
            "includeIncomingOutGoing" : "OUTGOING"
          }, {
            "class" : "ToVertices",
            "edgeVertices" : "BOTH"
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
          } ]
        }
        ```

    === "Python"

        ``` python
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
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ],
              include_incoming_out_going="OUTGOING"
            ),
            g.ToVertices(
              edge_vertices="BOTH"
            ),
            g.ToSet()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        1
        4
        2
        3
        5
        ```

    === "JSON"

        ``` json
        [ 1, 4, 2, 3, 5 ]
        ```

??? example "Example extracting matched vertices"

    === "Java"

        ``` java
        final OperationChain<Set<?>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ToVertices.Builder()
                        .useMatchedVertex(ToVertices.UseMatchedVertex.EQUAL)
                        .build())
                .then(new ToSet<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "edges" : {
                "edge" : { }
              }
            },
            "includeIncomingOutGoing" : "OUTGOING"
          }, {
            "class" : "ToVertices",
            "useMatchedVertex" : "EQUAL"
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
          } ]
        }
        ```

    === "Python"

        ``` python
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
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ],
              include_incoming_out_going="OUTGOING"
            ),
            g.ToVertices(
              use_matched_vertex="EQUAL"
            ),
            g.ToSet()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        1
        2
        ```

    === "JSON"

        ``` json
        [ 1, 2 ]
        ```

??? example "Example extracting opposite matched vertices"

    === "Java"

        ``` java
        final OperationChain<Set<?>> opChain = new Builder()
                .first(new GetElements.Builder()
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ToVertices.Builder()
                        .useMatchedVertex(ToVertices.UseMatchedVertex.OPPOSITE)
                        .build())
                .then(new ToSet<>())
                .build();
        ```

    === "JSON"

        ``` json
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
            } ],
            "view" : {
              "edges" : {
                "edge" : { }
              }
            },
            "includeIncomingOutGoing" : "OUTGOING"
          }, {
            "class" : "ToVertices",
            "useMatchedVertex" : "OPPOSITE"
          }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
          } ]
        }
        ```

    === "Python"

        ``` python
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
              input=[
                g.EntitySeed(
                  vertex=1
                ),
                g.EntitySeed(
                  vertex=2
                )
              ],
              include_incoming_out_going="OUTGOING"
            ),
            g.ToVertices(
              use_matched_vertex="OPPOSITE"
            ),
            g.ToSet()
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        4
        3
        2
        5
        ```

    === "JSON"

        ``` json
        [ 4, 3, 2, 5 ]
        ```

## GetSchema

Gets the Schema of a Graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/operation/GetSchema.html)

??? example "Example getting full schema"

    This operation defaults the compact field to false, thereby returning the full Schema.

    === "Java"

        ``` java
        final GetSchema operation = new GetSchema();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetSchema",
          "compact" : false
        }
        ```

    === "Python"

        ``` python
        g.GetSchema(
          compact=False
        )
        ```

    Results:

    === "Java"

        ``` java
        {
          "edges" : {
            "edge" : {
              "description" : "test edge",
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            },
            "edge1" : {
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            }
          },
          "entities" : {
            "entity1" : {
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "entity" : {
              "description" : "test entity",
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "cardinality" : {
              "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
              "vertex" : "int",
              "properties" : {
                "edgeGroup" : "set",
                "hllp" : "hllp",
                "count" : "count"
              },
              "groupBy" : [ "edgeGroup" ]
            }
          },
          "types" : {
            "int" : {
              "class" : "Integer",
              "aggregateFunction" : {
                "class" : "Sum"
              }
            },
            "true" : {
              "class" : "Boolean",
              "validateFunctions" : [ {
                "class" : "IsTrue"
              } ]
            },
            "count" : {
              "class" : "Integer",
              "aggregateFunction" : {
                "class" : "Sum"
              }
            },
            "set" : {
              "class" : "TreeSet",
              "aggregateFunction" : {
                "class" : "CollectionConcat"
              }
            },
            "hllp" : {
              "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
              "aggregateFunction" : {
                "class" : "HyperLogLogPlusAggregator"
              },
              "serialiser" : {
                "class" : "HyperLogLogPlusSerialiser"
              }
            }
          }
        }
        ```

    === "JSON"

        ``` json
        {
          "edges" : {
            "edge" : {
              "description" : "test edge",
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            },
            "edge1" : {
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            }
          },
          "entities" : {
            "entity1" : {
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "entity" : {
              "description" : "test entity",
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "cardinality" : {
              "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
              "vertex" : "int",
              "properties" : {
                "edgeGroup" : "set",
                "hllp" : "hllp",
                "count" : "count"
              },
              "groupBy" : [ "edgeGroup" ]
            }
          },
          "types" : {
            "int" : {
              "class" : "java.lang.Integer",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
              }
            },
            "true" : {
              "class" : "java.lang.Boolean",
              "validateFunctions" : [ {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
              } ]
            },
            "count" : {
              "class" : "java.lang.Integer",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
              }
            },
            "set" : {
              "class" : "java.util.TreeSet",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
              }
            },
            "hllp" : {
              "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
              },
              "serialiser" : {
                "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
              }
            }
          }
        }
        ```

??? example "Example getting compact schema"

    This operation will retrieve the compact Schema from the store, rather than the full schema.

    === "Java"

        ``` java
        final GetSchema operation = new GetSchema.Builder()
                .compact(true)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetSchema",
          "compact" : true
        }
        ```

    === "Python"

        ``` python
        g.GetSchema(
          compact=True
        )
        ```

    Results:

    === "Java"

        ``` java
        {
          "edges" : {
            "edge" : {
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            },
            "edge1" : {
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            }
          },
          "entities" : {
            "entity1" : {
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "entity" : {
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "cardinality" : {
              "vertex" : "int",
              "properties" : {
                "edgeGroup" : "set",
                "hllp" : "hllp",
                "count" : "count"
              },
              "groupBy" : [ "edgeGroup" ]
            }
          },
          "types" : {
            "int" : {
              "class" : "Integer",
              "aggregateFunction" : {
                "class" : "Sum"
              }
            },
            "true" : {
              "class" : "Boolean",
              "validateFunctions" : [ {
                "class" : "IsTrue"
              } ]
            },
            "count" : {
              "class" : "Integer",
              "aggregateFunction" : {
                "class" : "Sum"
              },
              "serialiser" : {
                "class" : "OrderedIntegerSerialiser"
              }
            },
            "set" : {
              "class" : "TreeSet",
              "aggregateFunction" : {
                "class" : "CollectionConcat"
              },
              "serialiser" : {
                "class" : "TreeSetStringSerialiser"
              }
            },
            "hllp" : {
              "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
              "aggregateFunction" : {
                "class" : "HyperLogLogPlusAggregator"
              },
              "serialiser" : {
                "class" : "HyperLogLogPlusSerialiser"
              }
            }
          },
          "vertexSerialiser" : {
            "class" : "OrderedIntegerSerialiser"
          }
        }
        ```

    === "JSON"

        ``` json
        {
          "edges" : {
            "edge" : {
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            },
            "edge1" : {
              "source" : "int",
              "destination" : "int",
              "directed" : "true",
              "properties" : {
                "count" : "count"
              }
            }
          },
          "entities" : {
            "entity1" : {
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "entity" : {
              "vertex" : "int",
              "properties" : {
                "count" : "count"
              }
            },
            "cardinality" : {
              "vertex" : "int",
              "properties" : {
                "edgeGroup" : "set",
                "hllp" : "hllp",
                "count" : "count"
              },
              "groupBy" : [ "edgeGroup" ]
            }
          },
          "types" : {
            "int" : {
              "class" : "java.lang.Integer",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
              }
            },
            "true" : {
              "class" : "java.lang.Boolean",
              "validateFunctions" : [ {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
              } ]
            },
            "count" : {
              "class" : "java.lang.Integer",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
              },
              "serialiser" : {
                "class" : "uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedIntegerSerialiser"
              }
            },
            "set" : {
              "class" : "java.util.TreeSet",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
              },
              "serialiser" : {
                "class" : "uk.gov.gchq.gaffer.serialisation.implementation.TreeSetStringSerialiser"
              }
            },
            "hllp" : {
              "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
              "aggregateFunction" : {
                "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
              },
              "serialiser" : {
                "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
              }
            }
          },
          "vertexSerialiser" : {
            "class" : "uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedIntegerSerialiser"
          }
        }
        ```

## GetTraits

Gets the traits of the current store. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/operation/GetTraits.html)

??? example "Example getting all traits"

    === "Java"

        ``` java
        final GetTraits operation = new GetTraits.Builder()
                .currentTraits(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetTraits",
          "currentTraits" : false
        }
        ```

    === "Python"

        ``` python
        g.GetTraits(
          current_traits=False
        )
        ```

    Results:

    === "Java"

        ``` java
        QUERY_AGGREGATION
        VISIBILITY
        MATCHED_VERTEX
        TRANSFORMATION
        INGEST_AGGREGATION
        PRE_AGGREGATION_FILTERING
        POST_TRANSFORMATION_FILTERING
        POST_AGGREGATION_FILTERING
        ```

    === "JSON"

        ``` json
        [ "QUERY_AGGREGATION", "VISIBILITY", "MATCHED_VERTEX", "TRANSFORMATION", "INGEST_AGGREGATION", "PRE_AGGREGATION_FILTERING", "POST_TRANSFORMATION_FILTERING", "POST_AGGREGATION_FILTERING" ]
        ```

??? example "Example getting current traits"

    This will only return traits that are applicable to your current schema. This schema doesn't have a visibility property, so the VISIBILITY trait is not returned.

    === "Java"

        ``` java
        final GetTraits operation = new GetTraits.Builder()
                .currentTraits(true)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetTraits",
          "currentTraits" : true
        }
        ```

    === "Python"

        ``` python
        g.GetTraits(
          current_traits=True
        )
        ```

    Results:

    === "Java"

        ``` java
        QUERY_AGGREGATION
        MATCHED_VERTEX
        TRANSFORMATION
        INGEST_AGGREGATION
        PRE_AGGREGATION_FILTERING
        POST_TRANSFORMATION_FILTERING
        POST_AGGREGATION_FILTERING
        ```

    === "JSON"

        ``` json
        [ "QUERY_AGGREGATION", "MATCHED_VERTEX", "TRANSFORMATION", "INGEST_AGGREGATION", "PRE_AGGREGATION_FILTERING", "POST_TRANSFORMATION_FILTERING", "POST_AGGREGATION_FILTERING" ]
        ```

## DeleteAllData

!!! warning
    This operation is currently only implemented for Accumulo stores.

Deletes all retained data including deleting the graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/operation/DeleteAllData.html)

To use this operation, it must be enabled via an [operations declarations JSON](../../administration-guide/gaffer-config/config.md#operations-declarations-json).

Note this operation does not return any response.

```json title="operationsDeclarations.json"
{
    "operations": [
        {
            "operation": "uk.gov.gchq.gaffer.store.operation.DeleteAllData",
            "handler": {
                "class": "uk.gov.gchq.gaffer.accumulostore.operation.handler.DeleteAllDataHandler"
            }
        }
    ]
}
```

??? example "Example deleting all data"

    === "Java"

        ``` java
        final DeleteAllData operation = new DeleteAllData()
        ```

    === "JSON"

        ``` json
        {
          "class" : "DeleteAllData"
        }
        ```

    === "Python"

        ``` python
        g.DeleteAllData()
        ```


