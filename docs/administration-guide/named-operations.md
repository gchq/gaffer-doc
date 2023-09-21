# Named Operations

The code for this example is [NamedOperations](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/NamedOperations.java).

This example explains how to configure your Gaffer Graph to allow named operations to be executed. 
Named operations enable encapsulation of an OperationChain into a new single NamedOperation.
The NamedOperation can be added to OperationChains and executed, just like
any other Operation. When run it executes the encapsulated OperationChain.
There are various possible uses for NamedOperations:
 * Making it simpler to run frequently used OperationChains
 * In a controlled way, allowing specific OperationChains to be run by a user that would not normally have permission to run them.

In addition to the NamedOperation there are a set of operations which manage named operations (AddNamedOperation, GetAllNamedOperations, DeleteNamedOperation).

## Configuration
You will need to configure what cache to use for storing NamedOperations. For more information on the cache service, see [Cache](cache.md).

Once you have configured the cache service, if you are using the OperationChainLimiter GraphHook then you will also need to configure
that GraphHook to use the NamedOperationScoreResolver, this will allow you to have custom scores for each named operation.
The hook configuration should look something like:


{% codetabs name="JSON", type="json" -%}
{
    "class": "uk.gov.gchq.gaffer.graph.hook.OperationChainLimiter",
    "opScores": {
      "uk.gov.gchq.gaffer.operation.Operation": 1,
      "uk.gov.gchq.gaffer.operation.impl.add.AddElements": 2,
      "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements": 5,
      "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects": 0
    },
    "authScores": {
      "User": 2,
      "SuperUser": 5
    },
    "scoreResolvers": {
      "uk.gov.gchq.gaffer.named.operation.NamedOperation": {
        "class": "uk.gov.gchq.gaffer.store.operation.resolver.named.NamedOperationScoreResolver"
      }
    }
}
{%- endcodetabs %}


and the operation declarations file for registering the ScoreOperationChain operation would then look like:

{% codetabs name="JSON", type="json" -%}
{
  "operations": [
    {
      "operation": "uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain",
      "handler": {
        "opScores": {
          "uk.gov.gchq.gaffer.operation.Operation": 1,
          "uk.gov.gchq.gaffer.operation.impl.add.AddElements": 2,
          "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements": 5,
          "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects": 0
        },
        "authScores": {
          "User": 2,
          "SuperUser": 5
        },
        "scoreResolvers": {
          "uk.gov.gchq.gaffer.named.operation.NamedOperation": {
            "class": "uk.gov.gchq.gaffer.store.operation.resolver.named.NamedOperationScoreResolver"
          }
        }
      }
    }
  ]
}
{%- endcodetabs %}




## Using Named Operations
OK, now for some examples of using NamedOperations.

We will use the same basic schema and data from the first developer walkthrough.

Start by creating your user instance and graph as you will have done previously:


{% codetabs name="Java", type="java" -%}
final User user = new User("user01");
{%- endcodetabs %}



{% codetabs name="Java", type="java" -%}
final Graph graph = new Graph.Builder()
        .config(getDefaultGraphConfig())
        .addSchemas(StreamUtil.openStreams(getClass(), schemaPath))
        .storeProperties(getDefaultStoreProperties())
        .build();
{%- endcodetabs %}


Then add a named operation to the cache with the AddNamedOperation operation:


{% codetabs name="Java", type="java" -%}
final AddNamedOperation addOperation = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("RoadUse")
                                .build())
                        .build())
                .then(new Limit.Builder<>().resultLimit(10).build())
                .build())
        .description("named operation limit query")
        .name("2-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .score(2)
        .overwrite()
        .build();

graph.execute(addOperation, user);
{%- endcodetabs %}


The above named operation has been configured to have a score of 2. If you have
the OperationChainLimiter GraphHook configured then this score will be used by
the hook to limit operation chains.

Then create a NamedOperation and execute it


{% codetabs name="Java", type="java" -%}
final NamedOperation<EntityId, CloseableIterable<? extends Element>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                .name("2-limit")
                .input(new EntitySeed("10"))
                .build();
{%- endcodetabs %}



{% codetabs name="Java", type="java" -%}
final CloseableIterable<? extends Element> results = graph.execute(operation, user);
{%- endcodetabs %}


The results are:

```
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]

```

NamedOperations can take parameters, to allow the OperationChain executed to be configured. The parameter could be as
simple as specifying the resultLimit on a Limit operation, but specify a custom view to use in an operation, or the input to an operation.
When adding a NamedOperation with parameters the operation chain must be specified as a JSON string, with
parameter names enclosed '${' and '}'. For each parameter, a ParameterDetail object must be created which gives a description, a class type
and an optional default for the Parameter, and also indicates whether the parameter must be provided (ie. there is no default).

The following code adds a NamedOperation with a 'limitParam' parameter that allows the result limit for the OperationChain to be set:


{% codetabs name="Java", type="java" -%}
String opChainString = "{" +
        "  \"operations\" : [ {" +
        "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\"," +
        "    \"view\" : {" +
        "      \"edges\" : {" +
        "        \"RoadUse\" : { }" +
        "      }," +
        "      \"entities\" : { }" +
        "    }" +
        "  }, {" +
        "    \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\"," +
        "    \"resultLimit\" : \"${limitParam}\"" +
        "  } ]" +
        "}";

ParameterDetail param = new ParameterDetail.Builder()
        .defaultValue(1L)
        .description("Limit param")
        .valueClass(Long.class)
        .build();
Map<String, ParameterDetail> paramDetailMap = Maps.newHashMap();
paramDetailMap.put("limitParam", param);

final AddNamedOperation addOperationWithParams = new AddNamedOperation.Builder()
        .operationChain(opChainString)
        .description("named operation limit query")
        .name("custom-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .parameters(paramDetailMap)
        .overwrite()
        .build();

graph.execute(addOperationWithParams, user);
{%- endcodetabs %}


A NamedOperation can then be created, with a value provided for the 'limitParam' parameter:


{% codetabs name="Java", type="java" -%}
Map<String, Object> paramMap = Maps.newHashMap();
paramMap.put("limitParam", 3L);

final NamedOperation<EntityId, CloseableIterable<? extends Element>> operationWithParams =
        new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                .name("custom-limit")
                .input(new EntitySeed("10"))
                .parameters(paramMap)
                .build();
{%- endcodetabs %}


and executed:


{% codetabs name="Java", type="java" -%}
final CloseableIterable<? extends Element> namedOperationResults = graph.execute(operationWithParams, user);
{%- endcodetabs %}


giving these results:

```
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 BST 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 BST 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 BST 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 BST 2000]]

```

Details of all available NamedOperations can be fetched using the GetAllNamedOperations operation:


{% codetabs name="Java", type="java" -%}
final CloseableIterable<NamedOperationDetail> details = graph.execute(new GetAllNamedOperations(), user);
{%- endcodetabs %}


That gives the following result:

```
NamedOperationDetail[inputType=java.lang.Object[],creatorId=user01,operations={"operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetElements","view":{"edges":{"RoadUse":{}}}},{"class":"uk.gov.gchq.gaffer.operation.impl.Limit","resultLimit":10,"truncate":true}]},readAccessRoles=[read-user],writeAccessRoles=[write-user],score=2]

```

## Security
By default, read access to Named Operations is unrestricted and write access is limited to administrators and the Named Operation creator. More fine-grained controls can be configured using the following options.

### Read and Write Access Roles
Read and write access to Named Operations can be locked down to users who have at least one of the auths listed in the "readAccessRoles" and "writeAccessRoles" settings.
This example ensures that readers have the "read-user" auth and writers the "write-user" auth.


{% codetabs name="Java", type="java" -%}
final AddNamedOperation addOperation = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("RoadUse")
                                .build())
                        .build())
                .then(new Limit.Builder<>().resultLimit(10).build())
                .build())
        .description("named operation limit query")
        .name("2-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .score(2)
        .overwrite()
        .build();

graph.execute(addOperation, user);
{%- endcodetabs %}


### Access Controlled Resource
Named Operations implement the AccessControlledResource interface allowing configuration of a custom Predicate which is tested against the User to determine whether they can access the Named Operation.
This example ensures readers of the Named Operation have both the "read-access-auth-1" and "read-access-auth-2" auths and users attempting to remove the Named Operation have both the "write-access-auth-1" and "write-access-auth-2" auths.
Note that the "readAccessPredicate" and "writeAccessPredicate" fields are mutually exclusive with the "readAccessRoles" and "writeAccessRoles" settings respectively as described in the [Read and Write Access Roles](#read-and-write-access-roles) section.


{% codetabs name="Java", type="java" -%}
final AddNamedOperation addNamedOperationAccessControlledResource = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("RoadUse")
                                .build())
                        .build())
                .then(new Limit.Builder<>().resultLimit(10).build())
                .build())
        .description("named operation limit query")
        .name("access-controlled-2-limit")
        .score(2)
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

graph.execute(addNamedOperationAccessControlledResource, user);
{%- endcodetabs %}


## Full Example
The [complex example](../user-guide/full-example.md#example-complex-query) described in the User Guide where junctions heavily used by buses were extracted can also be written as a Named Operation.
To make it more useful vehicle type and the number of results to return have been parameterised.
We have also wrapped the ToCsv operation in an If operation so it can be conditionally enabled/disabled.


{% codetabs name="Java", type="java" -%}
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

{%- language name="JSON", type="json" -%}
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


{%- language name="Full JSON", type="json" -%}
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


{%- language name="Python", type="py" -%}
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


{%- endcodetabs %}



For other named operation examples see [NamedOperation](../operations/namedoperation.md).
