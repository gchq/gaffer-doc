# Named Operations

Named Operations enable encapsulation of an OperationChain into a new single NamedOperation. The NamedOperation can be added to OperationChains and executed, just like any other Operation. They are available when a cache has been configured. [Overview Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/operation/NamedOperation.html)

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

## AddNamedOperation

Adds a new NamedOperation to a Graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/operation/AddNamedOperation.html)

??? example "Example adding a new named operation"

    === "Java"

        ``` java
        final AddNamedOperation operation = new AddNamedOperation.Builder()
                .operationChain(new OperationChain.Builder()
                        .first(new GetAdjacentIds.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .then(new GetAdjacentIds.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .build())
                .description("2 hop query")
                .name("2-hop")
                .readAccessRoles("read-user")
                .writeAccessRoles("write-user")
                .overwrite()
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "AddNamedOperation",
        "operationName" : "2-hop",
        "description" : "2 hop query",
        "operationChain" : {
            "operations" : [ {
            "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
            "includeIncomingOutGoing" : "OUTGOING"
            }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
            "includeIncomingOutGoing" : "OUTGOING"
            } ]
        },
        "overwriteFlag" : true,
        "readAccessRoles" : [ "read-user" ],
        "writeAccessRoles" : [ "write-user" ]
        }
        ```

    === "Python"

        ``` python
        g.AddNamedOperation( 
        operation_chain=g.OperationChainDAO( 
            operations=[ 
            g.GetAdjacentIds( 
                include_incoming_out_going="OUTGOING" 
            ), 
            g.GetAdjacentIds( 
                include_incoming_out_going="OUTGOING" 
            ) 
            ] 
        ), 
        operation_name="2-hop", 
        description="2 hop query", 
        read_access_roles=[ 
            "read-user" 
        ], 
        write_access_roles=[ 
            "write-user" 
        ], 
        overwrite_flag=True 
        )
        ```

??? example "Example adding a new named operation with score"

    === "Java"

        ``` java
        final AddNamedOperation operation = new AddNamedOperation.Builder()
                .operationChain(new OperationChain.Builder()
                        .first(new GetAdjacentIds.Builder()
                                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .build())
                .description("1 hop query")
                .name("1-hop")
                .readAccessRoles("read-user")
                .writeAccessRoles("write-user")
                .overwrite()
                .score(2)
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "AddNamedOperation",
        "operationName" : "1-hop",
        "description" : "1 hop query",
        "score" : 2,
        "operationChain" : {
            "operations" : [ {
            "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
            "includeIncomingOutGoing" : "OUTGOING"
            } ]
        },
        "overwriteFlag" : true,
        "readAccessRoles" : [ "read-user" ],
        "writeAccessRoles" : [ "write-user" ]
        }
        ```

    === "Python"

        ``` python
        g.AddNamedOperation( 
        operation_chain=g.OperationChainDAO( 
            operations=[ 
            g.GetAdjacentIds( 
                include_incoming_out_going="OUTGOING" 
            ) 
            ] 
        ), 
        operation_name="1-hop", 
        description="1 hop query", 
        read_access_roles=[ 
            "read-user" 
        ], 
        write_access_roles=[ 
            "write-user" 
        ], 
        overwrite_flag=True, 
        score=2 
        )
        ```

??? example "Example adding a new named operation with parameter"

    === "Java"

        ``` java
        final String opChainString = "{" +
                "    \"operations\" : [ {" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"," +
                "      \"includeIncomingOutGoing\" : \"OUTGOING\"" +
                "    }, {" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"," +
                "      \"includeIncomingOutGoing\" : \"OUTGOING\"" +
                "    }, {" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\"," +
                "      \"resultLimit\" : \"${param1}\"" +
                "    }" +
                " ]" +
                "}";

        ParameterDetail param = new ParameterDetail.Builder()
                .defaultValue(1L)
                .description("Limit param")
                .valueClass(Long.class)
                .build();
        Map<String, ParameterDetail> paramMap = Maps.newHashMap();
        paramMap.put("param1", param);

        final AddNamedOperation operation = new AddNamedOperation.Builder()
                .operationChain(opChainString)
                .description("2 hop query with settable limit")
                .name("2-hop-with-limit")
                .readAccessRoles("read-user")
                .writeAccessRoles("write-user")
                .parameters(paramMap)
                .overwrite()
                .score(3)
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "AddNamedOperation",
        "operationName" : "2-hop-with-limit",
        "description" : "2 hop query with settable limit",
        "score" : 3,
        "operationChain" : {
            "operations" : [ {
            "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
            "includeIncomingOutGoing" : "OUTGOING"
            }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
            "includeIncomingOutGoing" : "OUTGOING"
            }, {
            "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
            "resultLimit" : "${param1}"
            } ]
        },
        "overwriteFlag" : true,
        "parameters" : {
            "param1" : {
            "description" : "Limit param",
            "defaultValue" : 1,
            "valueClass" : "Long",
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
            g.GetAdjacentIds( 
                include_incoming_out_going="OUTGOING" 
            ), 
            g.GetAdjacentIds( 
                include_incoming_out_going="OUTGOING" 
            ), 
            g.Limit( 
                result_limit="${param1}" 
            ) 
            ] 
        ), 
        operation_name="2-hop-with-limit", 
        description="2 hop query with settable limit", 
        read_access_roles=[ 
            "read-user" 
        ], 
        write_access_roles=[ 
            "write-user" 
        ], 
        overwrite_flag=True, 
        score=3, 
        parameters=[ 
            g.NamedOperationParameter( 
            name="param1", 
            value_class="java.lang.Long", 
            description="Limit param", 
            default_value=1, 
            required=False 
            ) 
        ] 
        )
        ```

## GetAllNamedOperations

Retrieves all NamedOperations associated with a Graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/operation/GetAllNamedOperations.html)

??? example "Example getting all NamedOperations"

    === "Java"

        ``` java
        final GetAllNamedOperations operation = new GetAllNamedOperations();
        ```

    === "JSON"

        ``` json
        {
        "class" : "uk.gov.gchq.gaffer.named.operation.GetAllNamedOperations"
        }
        ```

    === "Python"

        ``` python
        g.GetAllNamedOperations()
        ```

    Results:

    === "Java"

        ``` java
        NamedOperationDetail[inputType=java.lang.Object[],creatorId=user01,operations={"operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"}]},readAccessRoles=[read-user],writeAccessRoles=[write-user],score=2]
        NamedOperationDetail[inputType=java.lang.Object[],creatorId=user01,operations={"operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"},{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"}]},readAccessRoles=[read-user],writeAccessRoles=[write-user]]
        NamedOperationDetail[inputType=java.lang.Object[],creatorId=user01,operations={    "operations" : [ {      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",      "includeIncomingOutGoing" : "OUTGOING"    }, {      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",      "includeIncomingOutGoing" : "OUTGOING"    }, {      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",      "resultLimit" : "${param1}"    } ]},readAccessRoles=[read-user],writeAccessRoles=[write-user],parameters={param1=ParameterDetail[description=Limit param,valueClass=class java.lang.Long,required=false,defaultValue=1]},score=3]
        ```

    === "JSON"

        ``` json
        [ {
        "operationName" : "1-hop",
        "inputType" : "java.lang.Object[]",
        "description" : "1 hop query",
        "creatorId" : "user01",
        "operations" : "{\"operations\":[{\"class\":\"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\"includeIncomingOutGoing\":\"OUTGOING\"}]}",
        "readAccessRoles" : [ "read-user" ],
        "writeAccessRoles" : [ "write-user" ],
        "parameters" : null,
        "score" : 2
        }, {
        "operationName" : "2-hop",
        "inputType" : "java.lang.Object[]",
        "description" : "2 hop query",
        "creatorId" : "user01",
        "operations" : "{\"operations\":[{\"class\":\"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\"includeIncomingOutGoing\":\"OUTGOING\"},{\"class\":\"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\"includeIncomingOutGoing\":\"OUTGOING\"}]}",
        "readAccessRoles" : [ "read-user" ],
        "writeAccessRoles" : [ "write-user" ],
        "parameters" : null
        }, {
        "operationName" : "2-hop-with-limit",
        "inputType" : "java.lang.Object[]",
        "description" : "2 hop query with settable limit",
        "creatorId" : "user01",
        "operations" : "{    \"operations\" : [ {      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",      \"includeIncomingOutGoing\" : \"OUTGOING\"    }, {      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",      \"includeIncomingOutGoing\" : \"OUTGOING\"    }, {      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\",      \"resultLimit\" : \"${param1}\"    } ]}",
        "readAccessRoles" : [ "read-user" ],
        "writeAccessRoles" : [ "write-user" ],
        "parameters" : {
            "param1" : {
            "description" : "Limit param",
            "defaultValue" : 1,
            "valueClass" : "java.lang.Long",
            "required" : false
            }
        },
        "score" : 3
        } ]
        ```

## NamedOperation

Runs a pre-existing NamedOperation, i.e. one that has already been added. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/operation/NamedOperation.html)

??? example "Example running a NamedOperation"

    === "Java"

        ``` java
        final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
                new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                        .name("2-hop")
                        .input(new EntitySeed(1))
                        .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "NamedOperation",
        "input" : [ {
            "class" : "EntitySeed",
            "class" : "EntitySeed",
            "vertex" : 1
        } ],
        "operationName" : "2-hop"
        }
        ```

    === "Python"

        ``` python
        g.NamedOperation( 
        input=[ 
            g.EntitySeed( 
            vertex=1 
            ) 
        ], 
        operation_name="2-hop" 
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

??? example "Example running a NamedOperation with parameter"

    === "Java"

        ``` java
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("param1", 2L);

        final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
                new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                        .name("2-hop-with-limit")
                        .input(new EntitySeed(1))
                        .parameters(paramMap)
                        .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "NamedOperation",
        "input" : [ {
            "class" : "EntitySeed",
            "class" : "EntitySeed",
            "vertex" : 1
        } ],
        "operationName" : "2-hop-with-limit",
        "parameters" : {
            "param1" : 2
        }
        }
        ```

    === "Python"

        ``` python
        g.NamedOperation( 
        input=[ 
            g.EntitySeed( 
            vertex=1 
            ) 
        ], 
        operation_name="2-hop-with-limit", 
        parameters={'param1': 2} 
        )
        ```

    Results:

    === "Java"

        ``` java
        EntitySeed[vertex=3]
        EntitySeed[vertex=4]
        ```

    === "JSON"

        ``` json
        [ {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 3
        }, {
        "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
        "vertex" : 4
        } ]
        ```

## DeleteNamedOperation

Removes an existing NamedOperation from a Graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/operation/DeleteNamedOperation.html)

??? example "Example deleting a NamedOperation"

    === "Java"

        ``` java
        final DeleteNamedOperation operation = new DeleteNamedOperation.Builder()
                .name("2-hop")
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "DeleteNamedOperation",
        "operationName" : "2-hop"
        }
        ```

    === "Python"

        ``` python
        g.DeleteNamedOperation( 
        operation_name="2-hop" 
        )
        ```

## AddNamedView

Adds a NamedView to a Graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/view/AddNamedView.html)

??? example "Example adding a NamedView"

    === "Java"

        ``` java
        final AddNamedView op = new AddNamedView.Builder()
                .name("isMoreThan10")
                .description("example test NamedView")
                .overwrite(true)
                .view(new View.Builder()
                        .edge("testEdge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(10))
                                        .build())
                                .build())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "AddNamedView",
        "name" : "isMoreThan10",
        "description" : "example test NamedView",
        "view" : {
            "edges" : {
            "testEdge" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                    "orEqualTo" : false,
                    "value" : 10
                }
                } ]
            }
            }
        },
        "overwriteFlag" : true
        }
        ```

    === "Python"

        ``` python
        g.AddNamedView( 
        view=g.View( 
            edges=[ 
            g.ElementDefinition( 
                group="testEdge", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                    value=10, 
                    or_equal_to=False 
                    ) 
                ) 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        name="isMoreThan10", 
        description="example test NamedView", 
        overwrite_flag=True 
        )
        ```

??? example "Example adding a NamedView with parameter"

    === "Java"

        ``` java
        final String viewJson = "{\"edges\" : {\n" +
                "  \"testEdge\" : {\n" +
                "    \"preAggregationFilterFunctions\" : [ {\n" +
                "      \"selection\" : [ \"count\" ],\n" +
                "      \"predicate\" : {\n" +
                "        \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsMoreThan\",\n" +
                "        \"orEqualTo\" : false,\n" +
                "        \"value\" : \"${countThreshold}\"\n" +
                "      }\n" +
                "    } ]\n" +
                "  }\n" +
                "}}";
        final ViewParameterDetail param = new ViewParameterDetail.Builder()
                .defaultValue(1L)
                .description("count threshold")
                .valueClass(Long.class)
                .build();
        final Map<String, ViewParameterDetail> paramMap = Maps.newHashMap();
        paramMap.put("countThreshold", param);

        final AddNamedView op = new AddNamedView.Builder()
                .name("isMoreThan")
                .description("example test NamedView")
                .overwrite(true)
                .view(viewJson)
                .parameters(paramMap)
                .writeAccessRoles("auth1")
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "AddNamedView",
        "name" : "isMoreThan",
        "description" : "example test NamedView",
        "view" : {
            "edges" : {
            "testEdge" : {
                "preAggregationFilterFunctions" : [ {
                "selection" : [ "count" ],
                "predicate" : {
                    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                    "orEqualTo" : false,
                    "value" : "${countThreshold}"
                }
                } ]
            }
            }
        },
        "overwriteFlag" : true,
        "parameters" : {
            "countThreshold" : {
            "description" : "count threshold",
            "defaultValue" : 1,
            "valueClass" : "Long",
            "required" : false
            }
        },
        "writeAccessRoles" : [ "auth1" ]
        }
        ```

    === "Python"

        ``` python
        g.AddNamedView( 
        view=g.View( 
            edges=[ 
            g.ElementDefinition( 
                group="testEdge", 
                pre_aggregation_filter_functions=[ 
                g.PredicateContext( 
                    selection=[ 
                    "count" 
                    ], 
                    predicate=g.IsMoreThan( 
                    value="${countThreshold}", 
                    or_equal_to=False 
                    ) 
                ) 
                ] 
            ) 
            ], 
            all_edges=False, 
            all_entities=False 
        ), 
        name="isMoreThan", 
        description="example test NamedView", 
        overwrite_flag=True, 
        write_access_roles=[ 
            "auth1" 
        ], 
        parameters=[ 
            g.NamedViewParameter( 
            name="countThreshold", 
            value_class="java.lang.Long", 
            description="count threshold", 
            default_value=1, 
            required=False 
            ) 
        ] 
        )
        ```

## DeleteNamedView

Removes an existing NamedView from a Graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/view/DeleteNamedView.html)

??? example "Example deleting a NamedView"

    === "Java"

        ``` java
        final DeleteNamedView op = new DeleteNamedView.Builder()
                .name("testNamedView")
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "DeleteNamedView",
        "name" : "testNamedView"
        }
        ```

    === "Python"

        ``` python
        g.DeleteNamedView( 
        name="testNamedView" 
        )
        ```

## GetAllNamedViews

Retrieves all NamedViews associated with a Graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/named/operation/GetAllNamedViews.html)

??? example "Example getting all NamedViews"

    === "Java"

        ``` java
        final GetAllNamedViews op = new GetAllNamedViews();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetAllNamedViews"
        }
        ```

    === "Python"

        ``` python
        g.GetAllNamedViews()
        ```