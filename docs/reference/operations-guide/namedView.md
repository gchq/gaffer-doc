# Named View

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
