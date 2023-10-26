# Named Operations

This guide walks through configuring Gaffer to use Named Operations and how to run them.

Named Operations allow users to encapsulate an OperationChain into a new single NamedOperation.
Named Operations execute the encapsulated Operation Chain and are used just like any other Operation.

There are various possible uses for Named Operations:

- Making it simpler to store and run frequently used Operation Chains.
- In a controlled way, allowing specific Operation Chains to be run by a user that would not normally have permission to run them.

There are [three operations](../reference/operations-guide/named.md) which manage Named Operations. 
These are `AddNamedOperation`, `GetAllNamedOperations` and `DeleteNamedOperations`.

## Using Named Operations

All Named Operations are stored in a cache, so your first step should be to configure a suitable cache.
For details on potential caches and how to configure them, see the [Stores Guide](../administration-guide/gaffer-stores/store-guide.md/#caches).

!!! Note
    If you choose a non-persistent cache then any Named Operations will be lost when you shut down your instance of Gaffer.

This guide assumes that you have set up your graph, if not please refer to our [example deployment](../development-guide/example-deployment/project-setup.md) to get started.

This walkthrough will use this directed graph:

``` mermaid
graph TD
  1(1, count=3) -- count=3 --> 2
  1 -- count=1 --> 4
  2(2, count=1) -- count=2 --> 3
  2 -- count=1 --> 4(4, count=1)
  2 -- count=1 --> 5(5, count=3)
  3(3, count=2) -- count=4 --> 4
```

Once you have configured your cache, you can then add your first NamedOperation. 

You can then add a NamedOperation to the cache using `AddNamedOperation`.
Here you are specifying the OperationChain that you want to be used as a NamedOperation.

!!! example "Adding a new NamedOperation"

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

Following on from this, you can then run your new NamedOperation:

!!! example "Running your NamedOperation"

    === "Java"

        ``` java
        final NamedOperation<EntityId, Iterable<EntityId>> operation =
        new NamedOperation.Builder<EntityId, Iterable<EntityId>>()
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


This produces the following result:
```java
EntitySeed[vertex=4]
EntitySeed[vertex=3]
EntitySeed[vertex=5]
```

Named Operations are able to take parameters which allow the OperationChain that is being executed to be configured.
Parameters could be as simple as specifying `resultLimit` on a Limit Operation, they could specify a custom view to use in an Operation or even the input to an Operation.

When adding a NamedOperation with parameters to an OperationChain it must be specified as a JSON string, with the parameter names enclosed in `${` and `}`. 
For each parameter, a `ParameterDetail` object must be created which gives a description, a class type and an optional default for that parameter.
As the default is optional you can alternatively indicate that the parameter must be provided and that there is no default.

The following code adds a NamedOperation with a parameter that allows the result limit for the OperationChain to be set:

!!! example "Adding a NamedOperation with parameters"

    === "Java"

        ``` java
        final String opChainString = new JSONObject()
            .put("operations", new JSONArray()
                .put(new JSONObject()
                    .put("class", "GetAdjacentIds")
                    .put("includeIncomingOutGoing", "OUTGOING"))
                .put(new JSONObject()
                    .put("class", "GetAdjacentIds")
                    .put("includeIncomingOutGoing", "OUTGOING"))
                .put(new JSONObject()
                    .put("class", "Limit")
                    .put("resultLimit", "${param1}")))
            .toString();

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

A NamedOperation can then be run, with a value provided for the result limit parameter:
 
!!! example "Running your NamedOperation with parameters"

    === "Java"

        ``` java
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("param1", 2L);

        final NamedOperation<EntityId, Iterable<EntityId>> operation =
            new NamedOperation.Builder<EntityId, Iterable<EntityId>>()
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

This will produce these results:

```java
EntitySeed[vertex=4]
EntitySeed[vertex=3]
```
For more examples of Named Operations, please refer to the [Named Operations page in the Reference Guide](../reference/operations-guide/named.md).

## Security

By default, read access to Named Operations is unrestricted while write access is limited to administrators and the NamedOperation creator. 
More fine-grained controls can be configured using the following options.

### Read and Write Access Roles

Read and write access to Named Operations can be locked down to users who have at least one of the auths listed in the `readAccessRoles` and `writeAccessRoles` settings.
This example ensures that readers have the "read-user" auth and writers the "write-user" auth.

!!! example 
    ``` java
    final AddNamedOperation addOperation = new AddNamedOperation.Builder()
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

### Access Controlled Resource
Named Operations implement the `AccessControlledResource` interface allowing configuration of a custom Predicate which is tested against the User to determine whether they can access the Named Operation.

This example ensures readers of the NamedOperation have both the "read-access-auth-1" and "read-access-auth-2" auths and users attempting to remove the NamedOperation have both the "write-access-auth-1" and "write-access-auth-2" auths.

Note that the `readAccessPredicate` and `writeAccessPredicate` fields are mutually exclusive with the `readAccessRoles` and `writeAccessRoles` settings respectively as described in the [Read and Write Access Roles](#read-and-write-access-roles) section.

!!! example
    ``` java
    final AddNamedOperation addNamedOperationAccessControlledResource = new AddNamedOperation.Builder()
            .operationChain(new OperationChain.Builder()
                    .first(new GetAdjacentIds.Builder()
                            .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                            .build())
                    .then(new GetAdjacentIds.Builder()
                            .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                            .build())
                    .build())
            .description("access controlled 2 hop")
            .name("access-controlled-2-hop-query")
            .overwrite()
            .readAccessPredicate(new AccessPredicate(
                    new AdaptedPredicate(
                            new CallMethod("getOpAuths"),
                            new And(
                                    new CollectionContains("read-access-auth-1"),
                                    new CollectionContains("read-access-auth-2")))))

            .writeAccessPredicate(
                    new AccessPredicate(
                            new AdaptedPredicate(
                                    new CallMethod("getOpAuths"),
                                    new And(
                                            new CollectionContains("write-access-auth-1"),
                                            new CollectionContains("write-access-auth-2")))))

            .build();
    ```

## Full Example
The below example uses the Road Traffic Dataset and asks "In the year 2000, which junctions in the South West were heavily used by buses". 
This can be written as a NamedOperation and is also an example of a more complex query that a user may wish to run on a dataset.

To make it more useful vehicle type and the number of results to return have been parameterised.
We have also wrapped the `ToCsv` operation in an `If` operation so it can be conditionally enabled/disabled.

??? example 
    === "Java"
        ``` java
        final String fullExampleOpChain = "{\n" +
                "  \"operations\" : [ {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\n" +
                "    \"view\" : {\n" +
                "      \"edges\" : {\n" +
                "        \"RegionContainsLocation\" : { }\n" +
                "      }\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\n" +
                "    \"view\" : {\n" +
                "      \"edges\" : {\n" +
                "        \"LocationContainsRoad\" : { }\n" +
                "      }\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.output.ToSet\"\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\n" +
                "    \"view\" : {\n" +
                "      \"edges\" : {\n" +
                "        \"RoadHasJunction\" : { }\n" +
                "      }\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\",\n" +
                "    \"view\" : {\n" +
                "      \"entities\" : {\n" +
                "        \"JunctionUse\" : {\n" +
                "          \"properties\" : [\"${vehicle}\"],\n" +
                "          \"preAggregationFilterFunctions\" : [ {\n" +
                "            \"selection\" : [ \"startDate\", \"endDate\" ],\n" +
                "            \"predicate\" : {\n" +
                "              \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.range.InDateRangeDual\",\n" +
                "              \"start\" : \"2000/01/01\",\n" +
                "              \"end\" : \"2001/01/01\"\n" +
                "            }\n" +
                "          } ],\n" +
                "          \"transientProperties\" : {\n" +
                "            \"${vehicle}\" : \"Long\"\n" +
                "          },\n" +
                "          \"transformFunctions\" : [ {\n" +
                "            \"selection\" : [ \"countByVehicleType\" ],\n" +
                "            \"function\" : {\n" +
                "              \"class\" : \"uk.gov.gchq.gaffer.types.function.FreqMapExtractor\",\n" +
                "              \"key\" : \"${vehicle}\"\n" +
                "            },\n" +
                "            \"projection\" : [ \"${vehicle}\" ]\n" +
                "          } ]\n" +
                "        }\n" +
                "      },\n" +
                "      \"globalElements\" : [ {\n" +
                "        \"groupBy\" : [ ]\n" +
                "      } ]\n" +
                "    },\n" +
                "    \"includeIncomingOutGoing\" : \"OUTGOING\"\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.compare.Sort\",\n" +
                "    \"comparators\" : [ {\n" +
                "      \"class\" : \"uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator\",\n" +
                "      \"property\" : \"${vehicle}\",\n" +
                "      \"groups\" : [ \"JunctionUse\" ],\n" +
                "      \"reversed\" : true\n" +
                "    } ],\n" +
                "    \"deduplicate\" : true,\n" +
                "    \"resultLimit\" : \"${result-limit}\"\n" +
                "  }, {\n" +
                "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.If\",\n" +
                "    \"condition\" : \"${to-csv}\",\n" +
                "    \"then\" : {\n" +
                "        \"class\" : \"uk.gov.gchq.gaffer.operation.impl.output.ToCsv\",\n" +
                "        \"elementGenerator\" : {\n" +
                "          \"class\" : \"uk.gov.gchq.gaffer.data.generator.CsvGenerator\",\n" +
                "          \"fields\" : {\n" +
                "            \"VERTEX\" : \"Junction\",\n" +
                "            \"${vehicle}\" : \"${vehicle}\"\n" +
                "          },\n" +
                "          \"constants\" : { },\n" +
                "          \"quoted\" : false,\n" +
                "          \"commaReplacement\" : \" \"\n" +
                "        },\n" +
                "        \"includeHeader\" : true\n" +
                "    }\n" +
                "  } ]\n" +
                "}";

        final Map<String, ParameterDetail> fullExampleParams = Maps.newHashMap();

        fullExampleParams.put("vehicle", new ParameterDetail.Builder()
                .defaultValue("BUS")
                .description("The type of vehicle: HGVR3, BUS, HGVR4, AMV, HGVR2, HGVA3, PC, HGVA3, PC, HGCA5, HGVA6, CAR, HGV, WM2, LGV")
                .valueClass(String.class)
                .required(false)
                .build());

        fullExampleParams.put("result-limit", new ParameterDetail.Builder()
                .defaultValue(2)
                .description("The maximum number of junctions to return")
                .valueClass(Integer.class)
                .required(false)
                .build());

        fullExampleParams.put("to-csv", new ParameterDetail.Builder()
                .defaultValue(false)
                .description("Enable this parameter to convert the results to a simple CSV in the format: Junction, Count")
                .valueClass(Boolean.class)
                .required(false)
                .build());

        final AddNamedOperation addFullExampleNamedOperation = new AddNamedOperation.Builder()
                .name("frequent-vehicles-in-region")
                .description("Finds the junctions in a region with the most of an individual vehicle (e.g BUS, CAR) in the year 2000. The input is the region.")
                .overwrite(true)
                .parameters(fullExampleParams)
                .operationChain(fullExampleOpChain)
                .build();
        ```

    === "JSON"
        ``` json
        {
            "class" : "AddNamedOperation",
            "operationName" : "frequent-vehicles-in-region",
            "description" : "Finds the junctions in a region with the most of an individual vehicle (e.g BUS, CAR) in the year 2000. The input is the region.",
            "operationChain" : {
                "operations" : [ {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
                    "view" : {
                        "edges" : {
                            "RegionContainsLocation" : { }
                        }
                    }
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
                    "view" : {
                        "edges" : {
                            "LocationContainsRoad" : { }
                        }
                    }
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
                        "view" : {
                            "edges" : {
                                "RoadHasJunction" : { }
                            }
                        }
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
                    "view" : {
                        "entities" : {
                            "JunctionUse" : {
                                "properties" : [ "${vehicle}" ],
                                "preAggregationFilterFunctions" : [ {
                                    "selection" : [ "startDate", "endDate" ],
                                    "predicate" : {
                                        "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRangeDual",
                                        "start" : "2000/01/01",
                                        "end" : "2001/01/01"
                                    }
                                } ],
                                "transientProperties" : {
                                    "${vehicle}" : "Long"
                                },
                                "transformFunctions" : [ {
                                    "selection" : [ "countByVehicleType" ],
                                    "function" : {
                                        "class" : "uk.gov.gchq.gaffer.types.function.FreqMapExtractor",
                                        "key" : "${vehicle}"
                                    },
                                    "projection" : [ "${vehicle}" ]
                                } ]
                            }
                        },
                        "globalElements" : [ {
                            "groupBy" : [ ]
                        } ]
                    },
                    "includeIncomingOutGoing" : "OUTGOING"
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Sort",
                    "comparators" : [ {
                        "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
                        "property" : "${vehicle}",
                        "groups" : [ "JunctionUse" ],
                        "reversed" : true
                    } ],
                    "deduplicate" : true,
                    "resultLimit" : "${result-limit}"
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.If",
                    "condition" : "${to-csv}",
                    "then" : {
                        "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToCsv",
                        "elementGenerator" : {
                            "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
                            "fields" : {
                                "VERTEX" : "Junction",
                                "${vehicle}" : "${vehicle}"
                            },
                            "constants" : { },
                            "quoted" : false,
                            "commaReplacement" : " "
                            },
                        "includeHeader" : true
                    }
                } ]
            },
            "overwriteFlag" : true,
            "parameters" : {
                "to-csv" : {
                    "description" : "Enable this parameter to convert the results to a simple CSV in the format: Junction, Count",
                    "defaultValue" : false,
                    "valueClass" : "Boolean",
                    "required" : false
                },
                "result-limit" : {
                    "description" : "The maximum number of junctions to return",
                    "defaultValue" : 2,
                    "valueClass" : "Integer",
                    "required" : false
                },
                "vehicle" : {
                    "description" : "The type of vehicle: HGVR3, BUS, HGVR4, AMV, HGVR2, HGVA3, PC, HGVA3, PC, HGCA5, HGVA6, CAR, HGV, WM2, LGV",
                    "defaultValue" : "BUS",
                    "valueClass" : "String",
                    "required" : false
                }
            }
        }
        ```

    === "Full JSON"
        ``` json
        {
            "class" : "uk.gov.gchq.gaffer.named.operation.AddNamedOperation",
            "operationName" : "frequent-vehicles-in-region",
            "description" : "Finds the junctions in a region with the most of an individual vehicle (e.g BUS, CAR) in the year 2000. The input is the region.",
            "operationChain" : {
                "operations" : [ {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
                    "view" : {
                        "edges" : {
                            "RegionContainsLocation" : { }
                        }
                    }
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
                    "view" : {
                        "edges" : {
                            "LocationContainsRoad" : { }
                        }
                    }       
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
                    "view" : {
                        "edges" : {
                            "RoadHasJunction" : { }
                        }       
                    }
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
                    "view" : {
                        "entities" : {
                            "JunctionUse" : {
                                "properties" : [ "${vehicle}" ],
                                "preAggregationFilterFunctions" : [ {
                                    "selection" : [ "startDate", "endDate" ],
                                    "predicate" : {
                                        "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRangeDual",
                                        "start" : "2000/01/01",
                                        "end" : "2001/01/01"
                                    }
                                } ],
                                "transientProperties" : {
                                    "${vehicle}" : "Long"
                                },
                                "transformFunctions" : [ {
                                    "selection" : [ "countByVehicleType" ],
                                    "function" : {
                                        "class" : "uk.gov.gchq.gaffer.types.function.FreqMapExtractor",
                                        "key" : "${vehicle}"
                                    },
                                    "projection" : [ "${vehicle}" ]
                                } ]
                            }
                        },
                        "globalElements" : [ {
                            "groupBy" : [ ]
                        } ]
                    },
                    "includeIncomingOutGoing" : "OUTGOING"
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Sort",
                    "comparators" : [ {
                        "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
                        "property" : "${vehicle}",
                        "groups" : [ "JunctionUse" ],
                        "reversed" : true
                    } ],
                "deduplicate" : true,
                "resultLimit" : "${result-limit}"
                }, {
                    "class" : "uk.gov.gchq.gaffer.operation.impl.If",
                    "condition" : "${to-csv}",
                    "then" : {
                        "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToCsv",
                        "elementGenerator" : {
                        "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
                        "fields" : {
                            "VERTEX" : "Junction",
                            "${vehicle}" : "${vehicle}"
                    },
                        "constants" : { },
                        "quoted" : false,
                        "commaReplacement" : " "
                    },
                        "includeHeader" : true
                    }
                } ]
            },
            "overwriteFlag" : true,
            "parameters" : {
                "to-csv" : {
                    "description" : "Enable this parameter to convert the results to a simple CSV in the format: Junction, Count",
                    "defaultValue" : false,
                    "valueClass" : "java.lang.Boolean",
                    "required" : false
                },
                "result-limit" : {
                    "description" : "The maximum number of junctions to return",
                    "defaultValue" : 2,
                    "valueClass" : "java.lang.Integer",
                    "required" : false
                },
                "vehicle" : {
                    "description" : "The type of vehicle: HGVR3, BUS, HGVR4, AMV, HGVR2, HGVA3, PC, HGVA3, PC, HGCA5, HGVA6, CAR, HGV, WM2, LGV",
                    "defaultValue" : "BUS",
                    "valueClass" : "java.lang.String",
                    "required" : false
                }
            }
        }
        ```

    === "Python"
        ``` py
        g.AddNamedOperation( 
            operation_chain=g.OperationChainDAO( 
                operations=[ 
                    g.GetAdjacentIds( 
                        view=g.View( 
                            edges=[ 
                                g.ElementDefinition( 
                                    group="RegionContainsLocation" 
                                ) 
                            ], 
                            all_edges=False, 
                            all_entities=False 
                        ) 
                    ), 
                    g.GetAdjacentIds( 
                        view=g.View( 
                            edges=[ 
                                g.ElementDefinition( 
                                    group="LocationContainsRoad" 
                                ) 
                            ], 
                            all_edges=False, 
                            all_entities=False 
                        ) 
                    ), 
                    g.ToSet(), 
                    g.GetAdjacentIds( 
                        view=g.View( 
                            edges=[ 
                                g.ElementDefinition( 
                                    group="RoadHasJunction" 
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
                                    group="JunctionUse", 
                                    transient_properties={'${vehicle}': 'Long'}, 
                                    pre_aggregation_filter_functions=[ 
                                        g.PredicateContext( 
                                            selection=[ 
                                                "startDate", 
                                                "endDate" 
                                            ], 
                                            predicate=g.InDateRangeDual( 
                                                start="2000/01/01", 
                                                end="2001/01/01" 
                                            ) 
                                        ) 
                                    ], 
                                    transform_functions=[ 
                                        g.FunctionContext( 
                                            selection=[ 
                                                "countByVehicleType" 
                                            ], 
                                            function=g.FreqMapExtractor( 
                                                key="${vehicle}" 
                                            ), 
                                            projection=[ 
                                                "${vehicle}" 
                                            ] 
                                        ) 
                                    ], 
                                    properties=[ 
                                        "${vehicle}" 
                                    ] 
                                ) 
                            ], 
                            global_elements=[ 
                                g.GlobalElementDefinition( 
                                    group_by=[ 
                                    ] 
                                ) 
                            ], 
                            all_edges=False, 
                            all_entities=False 
                        ), 
                        include_incoming_out_going="OUTGOING" 
                    ), 
                    g.Sort( 
                        comparators=[ 
                            g.ElementPropertyComparator( 
                                groups=[ 
                                    "JunctionUse" 
                                ], 
                                property="${vehicle}", 
                                reversed=True 
                            ) 
                        ], 
                        result_limit="${result-limit}", 
                        deduplicate=True 
                    ), 
                    g.If( 
                        condition="${to-csv}", 
                        then=g.ToCsv( 
                            element_generator=g.CsvGenerator( 
                                fields={'VERTEX': 'Junction', '${vehicle}': '${vehicle}'}, 
                                constants={}, 
                                quoted=False, 
                                comma_replacement=" " 
                            ), 
                            include_header=True 
                        ) 
                    ) 
                ] 
            ), 
            operation_name="frequent-vehicles-in-region", 
            description="Finds the junctions in a region with the most of an individual vehicle (e.g BUS, CAR) in the year 2000. The input is the region.", 
            overwrite_flag=True, 
            parameters=[ 
                g.NamedOperationParameter( 
                    name="to-csv", 
                    value_class="java.lang.Boolean", 
                    description="Enable this parameter to convert the results to a simple CSV in the format: Junction, Count", 
                    default_value=False, 
                    required=False 
                ), 
                g.NamedOperationParameter( 
                    name="result-limit", 
                    value_class="java.lang.Integer", 
                    description="The maximum number of junctions to return", 
                    default_value=2, 
                    required=False 
                ), 
                g.NamedOperationParameter( 
                    name="vehicle", 
                    value_class="java.lang.String", 
                    description="The type of vehicle: HGVR3, BUS, HGVR4, AMV, HGVR2, HGVA3, PC, HGVA3, PC, HGCA5, HGVA6, CAR, HGV, WM2, LGV", 
                    default_value="BUS", 
                    required=False  
                ) 
            ] 
        )
        ```

