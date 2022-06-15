# FederatedStore

The code for this example is [FederatedStoreWalkThrough](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/FederatedStoreWalkThrough.java).

The `FederatedStore` is simply a Gaffer store which forwards operations to a collection of sub-graphs and returns a single response as though it was a single graph.

This walkthrough explains how to:
 * [Create a `FederatedStore`](#create-a-federatedstore)
 * [Add Graphs](#add-graphs)
 * [Remove Graphs](#remove-graphs)
 * [Get GraphIds](#get-graphids)
 * [Perform Operations](#perform-operations)
 * [Select Graphs for Operations](#select-graphs-for-operations)
 * [Do Not Skip Failed Execution](#do-not-skip-failed-execution)
 * [Limit Access with Authentication](#limit-access-with-authentication)
   * [Public Access](#public-access)
   * [Private Access](#private-access)
   * [Secure Access](#secure-access)
     * [Graph Auths](#graph-auths)
     * [Access Controlled Resource](#access-controlled-resource)
 * [Disallow Public Access](#disallow-public-access)
 * [Limit Custom Properties](#limit-custom-properties)

## Create a FederatedStore

To create a `FederatedStore` you need to initialise the store with a graphId and a properties file.

Optionally you can add a `GraphLibrary` this will store all the `Schema` and `StoreProperties` associated with your `FederatedStore` and sub-graphs, otherwise a `NoGraphLibrary` is used which does nothing.
Optionally you can add a `CacheService` within the `StoreProperties`.
In this example we are using a non-persistent library `HashMapGraphLibrary` and cache `HashMapCacheService`.  For more information on the cache service please see [Cache](cache.md).


{% codetabs name="Java", type="java" -%}
final Graph federatedGraph = new Graph.Builder()
        .config(new GraphConfig.Builder()
                .graphId(getClass().getSimpleName())
                .library(library)
                .build())
        .storeProperties(getFederatedStoreProperties())
        .build();
{%- endcodetabs %}


### FederatedStore Properties


{% codetabs name="Java", type="java" -%}
final FederatedStoreProperties exampleFederatedProperty = new FederatedStoreProperties();
exampleFederatedProperty.setCacheProperties(HashMapCacheService.class.getName());

{%- language name="JSON", type="json" -%}
{
  "cacheProperties" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService",
  "isPublicAccessAllowed" : "true",
  "jobTrackerEnabled" : false,
  "schemaClassName" : "uk.gov.gchq.gaffer.store.schema.Schema",
  "schemaClass" : "uk.gov.gchq.gaffer.store.schema.Schema",
  "storePropertiesClassName" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
  "storePropertiesClass" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
  "jobExecutorThreadCount" : 50,
  "adminAuth" : "",
  "properties" : {
    "gaffer.store.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStore",
    "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
    "gaffer.cache.service.class" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService"
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "cacheProperties" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService",
  "isPublicAccessAllowed" : "true",
  "jobTrackerEnabled" : false,
  "schemaClassName" : "uk.gov.gchq.gaffer.store.schema.Schema",
  "schemaClass" : "uk.gov.gchq.gaffer.store.schema.Schema",
  "storePropertiesClassName" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
  "storePropertiesClass" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
  "jobExecutorThreadCount" : 50,
  "adminAuth" : "",
  "properties" : {
    "gaffer.store.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStore",
    "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
    "gaffer.cache.service.class" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService"
  }
}

{%- endcodetabs %}


## Add Graphs

A graph is added into the `FederatedStore` using the `AddGraph` operation. To load a graph you need to provide:
 * GraphId
 * Graph Schema and/or parentSchemaId
 * Graph Properties and/or parentPropertiesId

**Note**
* You can't add a graph using a graphId already in use, you will need to explicitly remove the old GraphId first.
* You can limit access to the sub-graphs when adding to FederatedStore, see [Limiting Access](#limit-access-with-authentication).
* Schema & Properties are not required if the GraphId is known by the GraphLibrary.
* Custom properties defined by the user can be disallowed, see [limit custom properties](#limit-custom-properties).


{% codetabs name="Java", type="java" -%}
AddGraph addAnotherGraph = new AddGraph.Builder()
        .graphId("AnotherGraph")
        .schema(schema)
        .storeProperties(getMapStoreProperties())
        .build();
federatedGraph.execute(addAnotherGraph, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "AddGraph",
  "graphId" : "AnotherGraph",
  "options" : null,
  "schema" : {
    "edges" : {
      "RoadUse" : {
        "description" : "A directed edge representing vehicles moving from junction A to junction B.",
        "source" : "junction",
        "destination" : "junction",
        "directed" : "true",
        "properties" : {
          "startDate" : "date.earliest",
          "endDate" : "date.latest",
          "count" : "count.long"
        },
        "groupBy" : [ "startDate", "endDate" ],
        "validateFunctions" : [ {
          "selection" : [ "startDate", "endDate" ],
          "predicate" : {
            "class" : "IsXLessThanY"
          }
        }, {
          "selection" : [ "startDate", "endDate" ],
          "predicate" : {
            "class" : "IsXLessThanY"
          }
        } ]
      },
      "RoadHasJunction" : {
        "description" : "A directed edge from each road to all the junctions on that road.",
        "source" : "road",
        "destination" : "junction",
        "directed" : "true"
      }
    },
    "entities" : {
      "Cardinality" : {
        "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
        "vertex" : "anyVertex",
        "properties" : {
          "edgeGroup" : "set",
          "hllp" : "hllp",
          "count" : "count.long"
        },
        "groupBy" : [ "edgeGroup" ]
      }
    },
    "types" : {
      "junction" : {
        "description" : "A road junction represented by a String.",
        "class" : "String"
      },
      "road" : {
        "description" : "A road represented by a String.",
        "class" : "String"
      },
      "anyVertex" : {
        "description" : "An String vertex - used for cardinalities",
        "class" : "String"
      },
      "count.long" : {
        "description" : "A long count that must be greater than or equal to 0.",
        "class" : "Long",
        "aggregateFunction" : {
          "class" : "Sum"
        },
        "validateFunctions" : [ {
          "class" : "IsMoreThan",
          "orEqualTo" : true,
          "value" : {
            "Long" : 0
          }
        } ]
      },
      "true" : {
        "description" : "A simple boolean that must always be true.",
        "class" : "Boolean",
        "validateFunctions" : [ {
          "class" : "IsTrue"
        } ]
      },
      "date.earliest" : {
        "description" : "A Date that when aggregated together will be the earliest date.",
        "class" : "Date",
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
        },
        "validateFunctions" : [ {
          "class" : "Exists"
        } ]
      },
      "date.latest" : {
        "description" : "A Date that when aggregated together will be the latest date.",
        "class" : "Date",
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
        },
        "validateFunctions" : [ {
          "class" : "Exists"
        } ]
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
  },
  "storeProperties" : {
    "gaffer.store.class" : "uk.gov.gchq.gaffer.mapstore.SingleUseMapStore",
    "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.mapstore.MapStoreProperties",
    "gaffer.cache.service.class" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService"
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.AddGraph",
  "graphId" : "AnotherGraph",
  "options" : null,
  "schema" : {
    "edges" : {
      "RoadUse" : {
        "description" : "A directed edge representing vehicles moving from junction A to junction B.",
        "source" : "junction",
        "destination" : "junction",
        "directed" : "true",
        "properties" : {
          "startDate" : "date.earliest",
          "endDate" : "date.latest",
          "count" : "count.long"
        },
        "groupBy" : [ "startDate", "endDate" ],
        "validateFunctions" : [ {
          "selection" : [ "startDate", "endDate" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          }
        }, {
          "selection" : [ "startDate", "endDate" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
          }
        } ]
      },
      "RoadHasJunction" : {
        "description" : "A directed edge from each road to all the junctions on that road.",
        "source" : "road",
        "destination" : "junction",
        "directed" : "true"
      }
    },
    "entities" : {
      "Cardinality" : {
        "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
        "vertex" : "anyVertex",
        "properties" : {
          "edgeGroup" : "set",
          "hllp" : "hllp",
          "count" : "count.long"
        },
        "groupBy" : [ "edgeGroup" ]
      }
    },
    "types" : {
      "junction" : {
        "description" : "A road junction represented by a String.",
        "class" : "java.lang.String"
      },
      "road" : {
        "description" : "A road represented by a String.",
        "class" : "java.lang.String"
      },
      "anyVertex" : {
        "description" : "An String vertex - used for cardinalities",
        "class" : "java.lang.String"
      },
      "count.long" : {
        "description" : "A long count that must be greater than or equal to 0.",
        "class" : "java.lang.Long",
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
        },
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : true,
          "value" : {
            "java.lang.Long" : 0
          }
        } ]
      },
      "true" : {
        "description" : "A simple boolean that must always be true.",
        "class" : "java.lang.Boolean",
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
        } ]
      },
      "date.earliest" : {
        "description" : "A Date that when aggregated together will be the earliest date.",
        "class" : "java.util.Date",
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
        },
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
        } ]
      },
      "date.latest" : {
        "description" : "A Date that when aggregated together will be the latest date.",
        "class" : "java.util.Date",
        "aggregateFunction" : {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
        },
        "validateFunctions" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
        } ]
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
  },
  "storeProperties" : {
    "gaffer.store.class" : "uk.gov.gchq.gaffer.mapstore.SingleUseMapStore",
    "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.mapstore.MapStoreProperties",
    "gaffer.cache.service.class" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService"
  }
}

{%- endcodetabs %}


## Remove Graphs

To remove a graph from the `FederatedStore` it is even easier, you only need to know the graphId. This does not delete the graph only removes it from the scope.
However the user can only delete graphs they have access to view.


{% codetabs name="Java", type="java" -%}
RemoveGraph removeGraph = new RemoveGraph.Builder()
        .graphId("AnotherGraph")
        .build();
federatedGraph.execute(removeGraph, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "RemoveGraph",
  "graphId" : "AnotherGraph"
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.RemoveGraph",
  "graphId" : "AnotherGraph"
}

{%- endcodetabs %}


## Get GraphIds

To get a list of all the sub-graphs within the `FederatedStore` you can perform the following `GetAllGraphId` operation.


{% codetabs name="Java", type="java" -%}
final GetAllGraphIds getAllGraphIDs = new GetAllGraphIds();
Iterable<? extends String> graphIds = federatedGraph.execute(getAllGraphIDs, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllGraphIds"
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.GetAllGraphIds"
}

{%- endcodetabs %}



and the result is:

```
[mapGraph, accumuloGraph]

```

## Perform Operations

Running operations against the `FederatedStore` is exactly same as running operations against any other store.
Behind the scenes the `FederatedStore` sends out operation chains to the sub-graphs to be executed and returns back a single response.

**Note**
`AddElements` operations is a special case, and only adds elements to sub-graphs when the edge or entity groupId is known by that sub-graph.

**Warning** When adding elements, if 2 sub-graphs contain the same group in the schema then the elements will be added to both of the sub-graphs.
A following `GetElements` operation would then return the same element from both sub-graph, resulting in duplicates.
It is advised to keep groups to within one sub-graph or limit queries to one of the graphs with the option "gaffer.federatedstore.operation.graphIds".


{% codetabs name="Java", type="java" -%}
final OperationChain<Void> addOpChain = new OperationChain.Builder()
        .first(new GenerateElements.Builder<String>()
                .generator(new RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator())
                .input(IOUtils.readLines(StreamUtil.openStream(getClass(), "RoadAndRoadUseWithTimesAndCardinalities/data.txt")))
                .build())
        .then(new AddElements())
        .build();

federatedGraph.execute(addOpChain, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GenerateElements",
    "input" : [ "M5,10,11,2000-05-01 07:00:00", "M5,10,11,2000-05-01 08:00:00", "M5,10,11,2000-05-02 09:00:00", "M5,11,10,2000-05-03 09:00:00", "M5,23,24,2000-05-04 07:00:00", "M5,23,24,2000-05-04 07:00:00", "M5,28,27,2000-05-04 07:00:00" ],
    "elementGenerator" : {
      "class" : "RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator"
    }
  }, {
    "class" : "AddElements",
    "skipInvalidElements" : false,
    "validate" : true
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.generate.GenerateElements",
    "input" : [ "M5,10,11,2000-05-01 07:00:00", "M5,10,11,2000-05-01 08:00:00", "M5,10,11,2000-05-02 09:00:00", "M5,11,10,2000-05-03 09:00:00", "M5,23,24,2000-05-04 07:00:00", "M5,23,24,2000-05-04 07:00:00", "M5,28,27,2000-05-04 07:00:00" ],
    "elementGenerator" : {
      "class" : "uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator"
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElements",
    "skipInvalidElements" : false,
    "validate" : true
  } ]
}


{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GenerateElements( 
      element_generator=g.ElementGenerator( 
        class_name="uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator", 
        fields={'class': 'uk.gov.gchq.gaffer.doc.user.generator.RoadAndRoadUseWithTimesAndCardinalitiesElementGenerator'} 
      ), 
      input=[ 
        "M5,10,11,2000-05-01 07:00:00", 
        "M5,10,11,2000-05-01 08:00:00", 
        "M5,10,11,2000-05-02 09:00:00", 
        "M5,11,10,2000-05-03 09:00:00", 
        "M5,23,24,2000-05-04 07:00:00", 
        "M5,23,24,2000-05-04 07:00:00", 
        "M5,28,27,2000-05-04 07:00:00" 
      ] 
    ), 
    g.AddElements( 
      skip_invalid_elements=False, 
      validate=True 
    ) 
  ] 
)


{%- endcodetabs %}



{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<? extends Element>> getOpChain = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("10"))
                .build())
        .build();

CloseableIterable<? extends Element> elements = federatedGraph.execute(getOpChain, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetElements",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : "10"
    } ]
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : "10"
    } ]
  } ]
}


{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetElements( 
      input=[ 
        g.EntitySeed( 
          vertex="10" 
        ) 
      ] 
    ) 
  ] 
)


{%- endcodetabs %}


and the results are:

```
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@1759c420,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadUse]]]
Entity[vertex=10,group=Cardinality,properties=Properties[hllp=<com.clearspring.analytics.stream.cardinality.HyperLogLogPlus>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@8f51ff7,count=<java.lang.Long>4,edgeGroup=<java.util.TreeSet>[RoadHasJunction]]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 PDT 2000]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 PDT 2000]]
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]

```


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<? extends Element>> getOpChainOnAccumuloGraph = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("10"))
                .option(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS, "accumuloGraph")
                .build())
        .build();

CloseableIterable<? extends Element> elementsFromAccumuloGraph = federatedGraph.execute(getOpChainOnAccumuloGraph, user);
{%- endcodetabs %}


and the results are:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 PDT 2000]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 PDT 2000]]
Edge[source=M5,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadHasJunction,properties=Properties[]]

```

## Select Graphs for Operations
Operations can be performed against specific sub-graphs by settings the option "gaffer.federatedstore.operation.graphIds".


{% codetabs name="Java", type="java" -%}
GetAllElements selectGraphsForOperations = new Builder()
        .option(FederatedStoreConstants.KEY_OPERATION_OPTIONS_GRAPH_IDS, "graphId1, graphId2")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllElements",
  "options" : {
    "gaffer.federatedstore.operation.graphIds" : "graphId1, graphId2"
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
  "options" : {
    "gaffer.federatedstore.operation.graphIds" : "graphId1, graphId2"
  }
}

{%- endcodetabs %}


## Do Not Skip Failed Execution
If the execution against a graph fails, that graph is skipped and the
`FederatedStore` continues with the remaining graphs. Unless the operation
has the option "gaffer.federatedstore.operation.skipFailedFederatedStoreExecute"
 set to `false`, in that situation a `OperationException` is thrown.


{% codetabs name="Java", type="java" -%}
GetAllElements doNotSkipFailedExecution = new Builder()
        .option(FederatedStoreConstants.KEY_SKIP_FAILED_FEDERATED_STORE_EXECUTE, "false")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllElements",
  "options" : {
    "gaffer.federatedstore.operation.skipFailedFederatedStoreExecute" : "false"
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
  "options" : {
    "gaffer.federatedstore.operation.skipFailedFederatedStoreExecute" : "false"
  }
}

{%- endcodetabs %}



## Limit Access with Authentication
It is possible to have a `FederatedStore` with many sub-graphs, however you
 may wish to limit user access to some sub-graphs. This is possible by either using authorisations
  or configurable read and write access predicates at the time of adding the graph to the FederatedStore, this limits the graphs users
   can perform operations on.

### Public Access
Within the `AddGraph` operation, explicitly setting the parameter "isPublic" as
true grants access to all users, regardless of authorisations that may be used.
By default the "isPublic" parameter is false.


{% codetabs name="Java", type="java" -%}
AddGraph publicGraph = new AddGraph.Builder()
        .graphId("publicGraph")
        .parentSchemaIds(Lists.newArrayList("roadTraffic"))
        .parentPropertiesId("mapStore")
        .isPublic(true) //<-- public access
        .graphAuths("Auth1") //<-- used but irrelevant as graph has public access
        .build();
federatedGraph.execute(addAnotherGraph, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "AddGraph",
  "graphId" : "publicGraph",
  "graphAuths" : [ "Auth1" ],
  "isPublic" : true,
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.AddGraph",
  "graphId" : "publicGraph",
  "graphAuths" : [ "Auth1" ],
  "isPublic" : true,
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ]
}

{%- endcodetabs %}


### Private Access
Within the `AddGraph` operation, by default the "isPublic" parameter is false.
If authorisations is not specified it is private to the user that added it to FederatedStore.


{% codetabs name="Java", type="java" -%}
AddGraph privateGraph = new AddGraph.Builder()
        .graphId("privateGraph")
        .parentSchemaIds(Lists.newArrayList("roadTraffic"))
        .parentPropertiesId("mapStore")
        //.isPublic(false) <-- not specifying also defaults to false.
        //.graphAuths() <-- leave blank/null or do no specify otherwise private access is lost.
        .build();
federatedGraph.execute(privateGraph, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "AddGraph",
  "graphId" : "privateGraph",
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.AddGraph",
  "graphId" : "privateGraph",
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ]
}

{%- endcodetabs %}


### Secure Access
Within the `AddGraph` operation, do not assign the "isPublic" parameter or assign it to false, this ensures the settings described in this section are not ignored.

#### Graph Auths
By assigning the parameter "graphAuths", all users that have one of the listed authorisations will have access to that graph. Note that "graphAuths" is mutually exclusive with the "readAccessPredicate" setting described in the [Access Controlled Resource](#access-controlled-resource) section.


{% codetabs name="Java", type="java" -%}
AddGraph addSecureGraph = new AddGraph.Builder()
        .graphId("SecureGraph")
        .parentSchemaIds(Lists.newArrayList("roadTraffic"))
        .parentPropertiesId("mapStore")
        .graphAuths("Auth1", "Auth2", "Auth3")
        //.isPublic(false) <-- not specifying also defaults to false.
        .build();
federatedGraph.execute(addSecureGraph, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "AddGraph",
  "graphId" : "SecureGraph",
  "graphAuths" : [ "Auth2", "Auth3", "Auth1" ],
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.AddGraph",
  "graphId" : "SecureGraph",
  "graphAuths" : [ "Auth2", "Auth3", "Auth1" ],
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ]
}

{%- endcodetabs %}


#### Access Controlled Resource
Graphs in the Federated Store implement the AccessControlledResource interface allowing configuration of a custom Predicate which is tested against the User to determine whether they can access the graph.
This example ensures readers of the graph have both the "read-access-auth-1" and "read-access-auth-2" auths and users attempting to remove the graph have both the "write-access-auth-1" and "write-access-auth-2" auths.
Note that the "readAccessPredicate" field is mutually exclusive with the "graphAuths" setting described in the [Graph Auths](#graph-auths) section.


{% codetabs name="Java", type="java" -%}
AddGraph addAccessControlledResourceSecureGraph = new AddGraph.Builder()
        .graphId("AccessControlledResourceSecureGraph")
        .parentSchemaIds(Lists.newArrayList("roadTraffic"))
        .parentPropertiesId("mapStore")
        .readAccessPredicate(
                new AccessPredicate(
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
federatedGraph.execute(addAccessControlledResourceSecureGraph, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "AddGraph",
  "graphId" : "AccessControlledResourceSecureGraph",
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ],
  "readAccessPredicate" : {
    "class" : "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
    "userPredicate" : {
      "class" : "AdaptedPredicate",
      "inputAdapter" : {
        "class" : "CallMethod",
        "method" : "getOpAuths"
      },
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
        "predicates" : [ {
          "class" : "CollectionContains",
          "value" : "read-access-auth-1"
        }, {
          "class" : "CollectionContains",
          "value" : "read-access-auth-2"
        } ]
      }
    }
  },
  "writeAccessPredicate" : {
    "class" : "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
    "userPredicate" : {
      "class" : "AdaptedPredicate",
      "inputAdapter" : {
        "class" : "CallMethod",
        "method" : "getOpAuths"
      },
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
        "predicates" : [ {
          "class" : "CollectionContains",
          "value" : "write-access-auth-1"
        }, {
          "class" : "CollectionContains",
          "value" : "write-access-auth-2"
        } ]
      }
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.federatedstore.operation.AddGraph",
  "graphId" : "AccessControlledResourceSecureGraph",
  "options" : null,
  "parentPropertiesId" : "mapStore",
  "parentSchemaIds" : [ "roadTraffic" ],
  "readAccessPredicate" : {
    "class" : "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
    "userPredicate" : {
      "class" : "uk.gov.gchq.koryphe.predicate.AdaptedPredicate",
      "inputAdapter" : {
        "class" : "uk.gov.gchq.koryphe.impl.function.CallMethod",
        "method" : "getOpAuths"
      },
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
        "predicates" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
          "value" : "read-access-auth-1"
        }, {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
          "value" : "read-access-auth-2"
        } ]
      }
    }
  },
  "writeAccessPredicate" : {
    "class" : "uk.gov.gchq.gaffer.access.predicate.AccessPredicate",
    "userPredicate" : {
      "class" : "uk.gov.gchq.koryphe.predicate.AdaptedPredicate",
      "inputAdapter" : {
        "class" : "uk.gov.gchq.koryphe.impl.function.CallMethod",
        "method" : "getOpAuths"
      },
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
        "predicates" : [ {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
          "value" : "write-access-auth-1"
        }, {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
          "value" : "write-access-auth-2"
        } ]
      }
    }
  }
}

{%- endcodetabs %}


## Disallow Public Access
By default the `FederatedStore` will allow graphs to be added with public access.
Public access can be prevented by setting the property `gaffer.federatedstore.isPublicAllowed` to false.


{% codetabs name="Java", type="java" -%}
FederatedStoreProperties disallowPublicProperties = new FederatedStoreProperties();
disallowPublicProperties.setGraphsCanHavePublicAccess(false);

{%- language name="JSON", type="json" -%}
{
  "gaffer.store.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStore",
  "gaffer.federatedstore.isPublicAllowed" : "false",
  "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties"
}


{%- language name="Full JSON", type="json" -%}
{
  "gaffer.store.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStore",
  "gaffer.federatedstore.isPublicAllowed" : "false",
  "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties"
}

{%- endcodetabs %}


## Limit Custom Properties
You may not want all users to be able to specify their own properties for graphs they are creating and adding, and to only use properties defined within the library.
To limit the user that use custom properties set the authorisation required by users with "gaffer.federatedstore.customPropertiesAuths" properties key.
By default this key is null, so all users can use custom properties.
Users that do not match this authorisation can only specify using `StoreProperties` from the `GraphLibrary`.


{% codetabs name="Java", type="java" -%}
FederatedStoreProperties limitCustomProperties = new FederatedStoreProperties();
limitCustomProperties.setCustomPropertyAuths("Auth1, Auth2, Auth3");

{%- language name="JSON", type="json" -%}
{
  "gaffer.store.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStore",
  "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
  "gaffer.federatedstore.customPropertiesAuths" : "Auth1, Auth2, Auth3"
}


{%- language name="Full JSON", type="json" -%}
{
  "gaffer.store.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStore",
  "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties",
  "gaffer.federatedstore.customPropertiesAuths" : "Auth1, Auth2, Auth3"
}

{%- endcodetabs %}


