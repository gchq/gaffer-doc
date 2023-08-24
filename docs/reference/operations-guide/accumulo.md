# HDFS Operations

These are special Operations for use with Accumulo Stores and will not work with other stores.

This directed graph is used in all the examples on this page:

``` mermaid
graph TD
  1(1, count=3) -- count=3 --> 2
  1 -- count=1 --> 4
  2(2, count=1) -- count=2 --> 3
  2 -- count=1 --> 4(4, count=1)
  2 -- count=1 --> 5(5, count=3)
  3(3, count=2) -- count=4 --> 4
```

## GetElementsBetweenSets

!!! warning "Deprecated"
    Operation has been deprecated in favour of [`GetElementsBetweenSetPairs`](#getelementsbetweensetpairs)

Gets edges that exist between 2 sets and entities in the first set. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/accumulostore/operation/impl/GetElementsBetweenSets.html)

??? example "Example getting elements within set of vertices 1 and 2 and 4"

    === "Java"

        ``` java
        final GetElementsBetweenSets operation = new GetElementsBetweenSets.Builder()
                .input(new EntitySeed(1))
                .inputB(new EntitySeed(2), new EntitySeed(4))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetElementsBetweenSets",
          "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 1
          } ],
          "inputB" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
          }, {
            "class" : "EntitySeed",
            "vertex" : 4
          } ]
        }
        ```

    === "Python"

        ``` python
        g.GetElementsBetweenSets(
          input=[
            g.EntitySeed(
              vertex=1
            )
          ],
          input_b=[
            g.EntitySeed(
              vertex=2
            ),
            g.EntitySeed(
              vertex=4
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        ```

??? example "Example getting elements within set of vertices 1 and 2 and 4 with count greater than 2"

    === "Java"

        ``` java
        final GetElementsBetweenSets operation = new GetElementsBetweenSets.Builder()
                .input(new EntitySeed(1))
                .inputB(new EntitySeed(2), new EntitySeed(4))
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
          "class" : "GetElementsBetweenSets",
          "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 1
          } ],
          "inputB" : [ {
            "class" : "EntitySeed",
            "vertex" : 2
          }, {
            "class" : "EntitySeed",
            "vertex" : 4
          } ],
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
        g.GetElementsBetweenSets(
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
          ),
          input=[
            g.EntitySeed(
              vertex=1
            )
          ],
          input_b=[
            g.EntitySeed(
              vertex=2
            ),
            g.EntitySeed(
              vertex=4
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

## GetElementsBetweenSetsPairs

Returns all the edges that exist between two Sets of Entities.

This operation has been introduced as a replacement to the `GetElementsBetweenSets` operation as that could not be used in operation chains due to it requiring two inputs. Instead this operation requires a single [`Pair`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/commonutil/pair/Pair.html) input containing two Sets which have the Entity IDs you wish to get the edges between. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/accumulostore/operation/impl/GetElementsBetweenSetsPairs.html)

??? example "Example getting edges between a Set of node 1 and a Set of node 2 and 4"

    === "Java"

        ```java
        // Create and populate first input set
        final Set inputSet1 = new HashSet();
        inputSet1.add(new EntitySeed(1));

        // Create and populate second input set
        final Set inputSet2 = new HashSet();
        inputSet2.add(new EntitySeed(2));
        inputSet2.add(new EntitySeed(4));

        // Create operation using the Sets wrapped in a Pair object
        final GetElementsBetweenSetsPairs operation = new GetElementsBetweenSetsPairs.Builder()
                .input(new Pair(inputSet1, inputSet2))
                .build();
        ```

    === "JSON"

        ```json
        {
            "class" : "GetElementsBetweenSetsPairs",
            "input" : {
                "class" : "Pair",
                "first" : {
                    "Iterable": [
                        {
                            "class" : "EntitySeed",
                            "vertex" : 1
                        }
                    ]
                },
                "second" : {
                    "Iterable": [
                        {
                            "class" : "EntitySeed",
                            "vertex" : 2
                        },
                        {
                            "class" : "EntitySeed",
                            "vertex" : 4
                        }
                    ]
                }
            }
        }
        ```
    === "Python"

        !!! warning "Currently Unavailable"

            The python API for this operation is currently unavailable [see this issue](https://github.com/gchq/gaffer-tools/issues/1082).

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ```json
        [
            {
                "class": "uk.gov.gchq.gaffer.data.element.Entity",
                "group": "entity",
                "vertex": 1,
                "properties": {
                    "count": 3
                }
            },
            {
                "class": "uk.gov.gchq.gaffer.data.element.Edge",
                "group": "edge",
                "source": 1,
                "destination": 2,
                "directed": true,
                "matchedVertex": "SOURCE",
                "properties": {
                    "count": 3
                }
            }
        ]
        ```

## GetElementsWithinSet

Gets edges with both vertices in a given set and entities with vertices in a given set. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/accumulostore/operation/impl/GetElementsWithinSet.html)

??? example "Example getting elements within set of vertices 1 and 2 and 3"

    === "Java"

        ``` java
        final GetElementsWithinSet operation = new GetElementsWithinSet.Builder()
                .input(new EntitySeed(1), new EntitySeed(2), new EntitySeed(3))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "GetElementsWithinSet",
          "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 1
          }, {
            "class" : "EntitySeed",
            "vertex" : 2
          }, {
            "class" : "EntitySeed",
            "vertex" : 3
          } ]
        }
        ```

    === "Python"

        ``` python
        g.GetElementsWithinSet(
          input=[
            g.EntitySeed(
              vertex=1
            ),
            g.EntitySeed(
              vertex=2
            ),
            g.EntitySeed(
              vertex=3
            )
          ]
        )
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        ```

??? example "Example getting elements within set of vertices 1 and 2 and 3 with count greater than 2"

    === "Java"

        ``` java
        final GetElementsWithinSet operation = new GetElementsWithinSet.Builder()
                .input(new EntitySeed(1), new EntitySeed(2), new EntitySeed(3))
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
          "class" : "GetElementsWithinSet",
          "input" : [ {
            "class" : "EntitySeed",
            "vertex" : 1
          }, {
            "class" : "EntitySeed",
            "vertex" : 2
          }, {
            "class" : "EntitySeed",
            "vertex" : 3
          } ],
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
        g.GetElementsWithinSet(
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
          ),
          input=[
            g.EntitySeed(
              vertex=1
            ),
            g.EntitySeed(
              vertex=2
            ),
            g.EntitySeed(
              vertex=3
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

## GetElementsInRanges

Gets elements that have vertices within a given range. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/accumulostore/operation/impl/GetElementsInRanges.html)

??? example "Example getting all elements in the range from entity 1 to entity 4"

    === "Java"

        ``` java
        final GetElementsInRanges operation = new GetElementsInRanges.Builder()
                .input(new Pair<>(new EntitySeed(1), new EntitySeed(4)))
                .build();
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
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
        ```

??? example "Example getting all elements in the range from entity 4 to edge 4->5"

    === "Java"

        ``` java
        final GetElementsInRanges operation = new GetElementsInRanges.Builder()
                .input(new Pair<>(new EntitySeed(4), new EdgeSeed(4, 5, DirectedType.EITHER)))
                .build();
        ```

    === "JSON"

        ``` json
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
        ```

    === "Python"

        ``` python
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
        ```

    Results:

    === "Java"

        ``` java
        Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=3,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        ```