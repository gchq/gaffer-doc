# Export Operations

These Operations are used for exporting results, either to a temporary cache used within an Operation Chain or to a result cache. Operations using a result cache require this to be enabled for the operation to also be available.

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

## ExportToSet

This operation exports results from an Operation Chain to a Set export.
This export is held in a temporary, in-memory cache which is only maintained per
individual job or Operation Chain. 
This means that you cannot use the Set export across multiple,
individual operation requests.

No additional configuration is needed for this operation as it is always available. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/ExportToSet.html)

??? example "Example of export and get"

    === "Java"

        ``` java
        final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ExportToSet<>())
                .then(new DiscardOutput())
                .then(new GetSetExport())
                .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements"
            }, {
                "class" : "ExportToSet"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetSetExport",
                "start" : 0
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements(), 
                g.ExportToSet(), 
                g.DiscardOutput(), 
                g.GetSetExport( 
                    start=0 
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

??? example "Example of export and get with pagination"

    === "Java"

        ``` java
        final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ExportToSet<>())
                .then(new DiscardOutput())
                .then(new GetSetExport.Builder()
                        .start(2)
                        .end(4)
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
                "class" : "ExportToSet"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetSetExport",
                "start" : 2,
                "end" : 4
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements(), 
                g.ExportToSet(), 
                g.DiscardOutput(), 
                g.GetSetExport( 
                    start=2, 
                    end=4 
                ) 
            ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        ```

    === "JSON"

        ``` json
        [ {
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
        } ]
        ```

??? example "Example exporting multiple results to set and getting all results"

    === "Java"

        ``` java
        final OperationChain<Map<String, Iterable<?>>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ExportToSet.Builder<>()
                        .key("edges")
                        .build())
                .then(new DiscardOutput())
                .then(new GetAllElements())
                .then(new ExportToSet.Builder<>()
                        .key("entities")
                        .build())
                .then(new DiscardOutput())
                .then(new GetExports.Builder()
                        .exports(new GetSetExport.Builder()
                                        .key("edges")
                                        .build(),
                                new GetSetExport.Builder()
                                        .key("entities")
                                        .build())
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
                "class" : "ExportToSet",
                "key" : "edges"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetAllElements"
            }, {
                "class" : "ExportToSet",
                "key" : "entities"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetExports",
                "getExports" : [ {
                    "class" : "GetSetExport",
                    "start" : 0,
                    "key" : "edges"
                }, {
                    "class" : "GetSetExport",
                    "start" : 0,
                    "key" : "entities"
                } ]
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements(), 
                g.ExportToSet( 
                    key="edges" 
                ), 
                g.DiscardOutput(), 
                g.GetAllElements(), 
                g.ExportToSet( 
                    key="entities" 
                ), 
                g.DiscardOutput(), 
                g.GetExports( 
                    get_exports=[ 
                        g.GetSetExport( 
                            key="edges", 
                            start=0 
                        ), 
                        g.GetSetExport( 
                            key="entities", 
                            start=0 
                        ) 
                    ] 
                ) 
            ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: edges:
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
        uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: entities:
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
        {
        "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: edges" : [ {
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
        } ],
        "uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport: entities" : [ {
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
        }
        ```

## GetSetExport

Fetches data from a Set cache, always available. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/set/GetSetExport.html)

Note that as the Set exported by ExportToSet is temporary to access the data 
ExportToSet and GetSetExport should be used within a single Operation Chain.

!!! example
    See examples for ExportToSet above for usage.

## ExportToGafferResultCache

This operation exports results to a cache which is backed by a simple Gaffer graph. This requires a cache to be [configured](../../administration-guide/job-tracker.md#configuring-the-results-cache) and results must be of a type that is JSON serialisable. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/ExportToGafferResultCache.html)

??? example "Example of export and get with result cache"

    === "Java"

        ``` java
        final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ExportToGafferResultCache<>())
                .then(new DiscardOutput())
                .then(new GetGafferResultCacheExport())
                .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements"
            }, {
                "class" : "ExportToGafferResultCache"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetGafferResultCacheExport",
                "key" : "ALL"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements(), 
                g.ExportToGafferResultCache(), 
                g.DiscardOutput(), 
                g.GetGafferResultCacheExport( 
                    key="ALL" 
                ) 
            ] 
            )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
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
            "vertex" : 4,
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
            "destination" : 5,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
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
            "vertex" : 2,
            "properties" : {
                "count" : 1
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
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
        } ]
        ```

??? example "Example exporting to result cache and getting job details"

    === "Java"

        ``` java
        final OperationChain<JobDetail> exportOpChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ExportToGafferResultCache<>())
                .then(new DiscardOutput())
                .then(new GetJobDetails())
                .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements"
            }, {
                "class" : "ExportToGafferResultCache"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetJobDetails"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements(), 
                g.ExportToGafferResultCache(), 
                g.DiscardOutput(), 
                g.GetJobDetails() 
            ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        JobDetail[jobId=af0a2efe-5f3c-458d-8fa2-93d0f28cbd82,user=User[userId=user01,dataAuths=[],opAuths=[]],status=RUNNING,startTime=1667818800114,opChain=OperationChain[GetAllElements->ExportToGafferResultCache->DiscardOutput->GetJobDetails]]
        ```

    === "JSON"

        ``` json
        {
            "jobId" : "af0a2efe-5f3c-458d-8fa2-93d0f28cbd82",
            "user" : {
                "userId" : "user01",
                "dataAuths" : [ ],
                "opAuths" : [ ]
            },
            "status" : "RUNNING",
            "startTime" : 1667818800114,
            "opChain" : "OperationChain[GetAllElements->ExportToGafferResultCache->DiscardOutput->GetJobDetails]"
        }
        ```

??? example "Example getting elements from result cache"

    === "Java"

        ``` java
        final OperationChain<Iterable<?>> opChain = new OperationChain.Builder()
                .first(new GetGafferResultCacheExport.Builder()
                        .jobId(jobDetail.getJobId())
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetGafferResultCacheExport",
                "jobId" : "af0a2efe-5f3c-458d-8fa2-93d0f28cbd82",
                "key" : "ALL"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetGafferResultCacheExport( 
                    job_id="af0a2efe-5f3c-458d-8fa2-93d0f28cbd82", 
                    key="ALL" 
                ) 
            ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
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
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
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
            "vertex" : 3,
            "properties" : {
                "count" : 2
            }
        } ]
        ```

??? example "Example exporting multiple results to result cache and then getting all results"

    === "Java"

        ``` java
        final OperationChain<Map<String, Iterable<?>>> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
                .then(new ExportToGafferResultCache.Builder<>()
                        .key("edges")
                        .build())
                .then(new DiscardOutput())
                .then(new GetAllElements())
                .then(new ExportToGafferResultCache.Builder<>()
                        .key("entities")
                        .build())
                .then(new DiscardOutput())
                .then(new GetExports.Builder()
                        .exports(new GetGafferResultCacheExport.Builder()
                                        .key("edges")
                                        .build(),
                                new GetGafferResultCacheExport.Builder()
                                        .key("entities")
                                        .build())
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
                "class" : "ExportToGafferResultCache",
                "key" : "edges"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetAllElements"
            }, {
                "class" : "ExportToGafferResultCache",
                "key" : "entities"
            }, {
                "class" : "DiscardOutput"
            }, {
                "class" : "GetExports",
                "getExports" : [ {
                    "class" : "GetGafferResultCacheExport",
                    "key" : "edges"
                }, {
                    "class" : "GetGafferResultCacheExport",
                    "key" : "entities"
                } ]
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements(), 
                g.ExportToGafferResultCache( 
                    key="edges" 
                ), 
                g.DiscardOutput(), 
                g.GetAllElements(), 
                g.ExportToGafferResultCache( 
                    key="entities" 
                ), 
                g.DiscardOutput(), 
                g.GetExports( 
                    get_exports=[ 
                        g.GetGafferResultCacheExport( 
                            key="edges" 
                        ), 
                        g.GetGafferResultCacheExport( 
                            key="entities" 
                        ) 
                    ] 
                ) 
            ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: edges:
            Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
            Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
            Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
            Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
            Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
            Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
            Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
            Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
            Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
            Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
            Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: entities:
            Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
            Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
            Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
            Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
            Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
            Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
            Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
            Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
            Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
            Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
            Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        ```

    === "JSON"

        ``` json
        {
            "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: edges" : [ {
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
                "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                "group" : "entity",
                "vertex" : 5,
                "properties" : {
                    "count" : 3
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
                "vertex" : 4,
                "properties" : {
                    "count" : 1
                }
            } ],
            "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: entities" : [ {
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
                "source" : 3,
                "destination" : 4,
                "directed" : true,
                "matchedVertex" : "SOURCE",
                "properties" : {
                    "count" : 4
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
                "vertex" : 4,
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
                "matchedVertex" : "SOURCE",
                "properties" : {
                    "count" : 3
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
                "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                "group" : "entity",
                "vertex" : 5,
                "properties" : {
                    "count" : 3
                }
            }, {
                "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                "group" : "entity",
                "vertex" : 1,
                "properties" : {
                    "count" : 3
                }
            } ]
        }
        ```

## GetGafferResultCacheExport

Fetches data from a Gaffer result cache. Requires a cache to be [configured](../../../administration-guide/gaffer-stores/store-guide/#caches). [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/GetGafferResultCacheExport.html)

!!! example
    See examples for ExportToGafferResultCache above for usage.

## GetExports

Fetches multiple export results and returns them in a map. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/export/GetExports.html)

!!! example
    See final example for ExportToGafferResultCache above for usage.


## ExportToOtherAuthorisedGraph

These export examples export all edges in the example graph to another Gaffer instance using Operation Auths against the user. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherAuthorisedGraph.html)

To add this operation to your Gaffer graph you will need to write your own version of [`ExportToOtherAuthorisedGraphOperationDeclarations.json`](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/road-traffic-rest/src/main/resources/ExportToOtherAuthorisedGraphOperationDeclarations.json) containing the user auths, and then set this property:

```
gaffer.store.operation.declarations=/path/to/ExportToOtherAuthorisedGraphOperationDeclarations.json
```

??? example "Example export to preconfigured graph"

    This example will export all Edges with group 'edge' to another Gaffer graph with ID 'graph2'. The graph will be loaded from the configured GraphLibrary, so it must already exist. In order to export to graph2 the user must have the required user authorisations that were configured for this operation.
    
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAllElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .then(new ExportToOtherAuthorisedGraph.Builder()
                                .graphId("graph2")
                                .build())
                        .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements",
                "view" : {
                "edges" : {
                    "edge" : { }
                }
                }
            }, {
                "class" : "ExportToOtherAuthorisedGraph",
                "graphId" : "graph2"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements( 
                view=g.View( 
                    edges=[ 
                        g.ElementDefinition( 
                            group="edge" 
                        ) 
                    ], 
                    all_edges=False, 
                    all_entities=False 
                    ) 
                ), 
                g.ExportToOtherAuthorisedGraph( 
                graph_id="graph2" 
                ) 
            ] 
        )
        ```

??? example "Example exporting to new graph using preconfigured schema and properties"

    This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have a parent Schema and Store Properties within the graph library specified by the ID's schemaId1 and storePropsId1. In order to export to newGraphId with storePropsId1 and schemaId1 the user must have the required user authorisations that were configured for this operation to use each of these 3 ids.
    
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAllElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .then(new ExportToOtherAuthorisedGraph.Builder()
                                .graphId("newGraphId")
                                .parentStorePropertiesId("storePropsId1")
                                .parentSchemaIds("schemaId1")
                                .build())
                        .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements",
                "view" : {
                "edges" : {
                    "edge" : { }
                }
                }
            }, {
                "class" : "ExportToOtherAuthorisedGraph",
                "graphId" : "newGraphId",
                "parentSchemaIds" : [ "schemaId1" ],
                "parentStorePropertiesId" : "storePropsId1"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements( 
                view=g.View( 
                    edges=[ 
                        g.ElementDefinition( 
                            group="edge" 
                        ) 
                    ], 
                    all_edges=False, 
                    all_entities=False 
                    ) 
                ), 
                g.ExportToOtherAuthorisedGraph( 
                    graph_id="newGraphId", 
                    parent_schema_ids=[ 
                        "schemaId1" 
                    ], 
                    parent_store_properties_id="storePropsId1" 
                ) 
            ] 
        )
        ```

## ExportToOtherGraph

These export examples export all edges in the example graph to another Gaffer instance. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherGraph.html)

To add this operation to your Gaffer graph you will need to include the `ExportToOtherGraphOperationDeclarations.json` in your store properties, i.e. set this property:

```
gaffer.store.operation.declarations=ExportToOtherGraphOperationDeclarations.json
```

??? example "Example export"

    This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the same schema and same store properties as the current graph. In this case it will just create another table in accumulo called 'newGraphId'.
    
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAllElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .then(new ExportToOtherGraph.Builder()
                                .graphId("newGraphId")
                                .build())
                        .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements",
                "view" : {
                "edges" : {
                    "edge" : { }
                }
                }
            }, {
                "class" : "ExportToOtherGraph",
                "graphId" : "newGraphId"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements( 
                view=g.View( 
                    edges=[ 
                        g.ElementDefinition( 
                            group="edge" 
                        ) 
                    ], 
                    all_edges=False, 
                    all_entities=False 
                    ) 
                ), 
                g.ExportToOtherGraph( 
                    graph_id="newGraphId" 
                ) 
            ] 
        )
        ```

??? example "Example export with custom graph"

    This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the custom provided schema (note it must contain the same Edge group 'edge' otherwise the exported edges will be invalid') and custom store properties. The store properties could be any store properties e.g. Accumulo, Map, Proxy store properties.
    
    === "Java"

        ``` java
        final Schema schema = Schema.fromJson(StreamUtil.openStreams(getClass(), "operations/schema"));
        final StoreProperties storeProperties = new AccumuloProperties();
        final OperationChain<Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAllElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .then(new ExportToOtherGraph.Builder()
                                .graphId("newGraphId")
                                .schema(schema)
                                .storeProperties(storeProperties)
                                .build())
                        .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements",
                "view" : {
                "edges" : {
                    "edge" : { }
                }
                }
            }, {
                "class" : "ExportToOtherGraph",
                "graphId" : "newGraphId",
                "schema" : {
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
                },
                "storeProperties" : {
                "gaffer.store.class" : "uk.gov.gchq.gaffer.accumulostore.AccumuloStore",
                "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.accumulostore.AccumuloProperties"
                }
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements( 
                view=g.View( 
                    edges=[ 
                        g.ElementDefinition( 
                            group="edge" 
                        ) 
                        ], 
                        all_edges=False, 
                        all_entities=False 
                    ) 
                ), 
                g.ExportToOtherGraph( 
                graph_id="newGraphId", 
                schema={'edges': {'edge': {'description': 'test edge', 'source': 'int', 'destination': 'int', 'directed': 'true', 'properties': {'count': 'count'}}, 'edge1': {'source': 'int', 'destination': 'int', 'directed': 'true', 'properties': {'count': 'count'}}}, 'entities': {'entity1': {'vertex': 'int', 'properties': {'count': 'count'}}, 'entity': {'description': 'test entity', 'vertex': 'int', 'properties': {'count': 'count'}}, 'cardinality': {'description': 'An entity that is added to every vertex representing the connectivity of the vertex.', 'vertex': 'int', 'properties': {'edgeGroup': 'set', 'hllp': 'hllp', 'count': 'count'}, 'groupBy': ['edgeGroup']}}, 'types': {'int': {'class': 'java.lang.Integer', 'aggregateFunction': {"class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"}}, 'true': {'class': 'java.lang.Boolean', 'validateFunctions': [{"class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"}]}, 'count': {'class': 'java.lang.Integer', 'aggregateFunction': {"class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"}}, 'set': {'class': 'java.util.TreeSet', 'aggregateFunction': {"class": "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"}}, 'hllp': {'class': 'com.clearspring.analytics.stream.cardinality.HyperLogLogPlus', 'aggregateFunction': {'class': 'uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator'}, 'serialiser': {'class': 'uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser'}}}}, 
                store_properties={'gaffer.store.class': 'uk.gov.gchq.gaffer.accumulostore.AccumuloStore', 'gaffer.store.properties.class': 'uk.gov.gchq.gaffer.accumulostore.AccumuloProperties'} 
                ) 
            ] 
        )
        ```

??? example "Example export to other Gaffer REST API"

    This example will export all Edges with group 'edge' to another Gaffer REST API.To export to another Gaffer REST API, we go via a Gaffer Proxy Store. We just need to tell the proxy store the host, port and context root of the REST API. Note that you will need to include the proxy-store module as a Maven dependency to do this.
    
    === "Java"

        ``` java
        final ProxyProperties proxyProperties = new ProxyProperties();
        proxyProperties.setStoreClass(ProxyStore.class);
        proxyProperties.setStorePropertiesClass(ProxyProperties.class);
        proxyProperties.setGafferHost("localhost");
        proxyProperties.setGafferPort(8081);
        proxyProperties.setGafferContextRoot("/rest/v1");

        final OperationChain<Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAllElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .then(new ExportToOtherGraph.Builder()
                                .graphId("otherGafferRestApiGraphId")
                                .storeProperties(proxyProperties)
                                .build())
                        .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements",
                "view" : {
                "edges" : {
                    "edge" : { }
                }
                }
            }, {
                "class" : "ExportToOtherGraph",
                "graphId" : "otherGafferRestApiGraphId",
                "storeProperties" : {
                "gaffer.host" : "localhost",
                "gaffer.context-root" : "/rest/v1",
                "gaffer.store.class" : "uk.gov.gchq.gaffer.proxystore.ProxyStore",
                "gaffer.port" : "8081",
                "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.proxystore.ProxyProperties"
                }
            } ]
            }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements( 
                view=g.View( 
                    edges=[ 
                        g.ElementDefinition( 
                            group="edge" 
                        ) 
                        ], 
                        all_edges=False, 
                        all_entities=False 
                    ) 
                ), 
                g.ExportToOtherGraph( 
                    graph_id="otherGafferRestApiGraphId", 
                    store_properties={'gaffer.host': 'localhost', 'gaffer.context-root': '/rest/v1', 'gaffer.store.class': 'uk.gov.gchq.gaffer.proxystore.ProxyStore', 'gaffer.port': '8081', 'gaffer.store.properties.class': 'uk.gov.gchq.gaffer.proxystore.ProxyProperties'} 
                ) 
            ] 
        )
        ```

??? example "Example export using graph from graph library"

    This example will export all Edges with group 'edge' to another existing graph 'exportGraphId' using a GraphLibrary. We demonstrate here that if we use a GraphLibrary, we can register a graph ID and reference it from the export operation. This means the user does not have to proxy all the schema and store properties when they configure the export operation, they can just provide the ID.
    
    === "Java"

        ``` java
        // Setup the graphLibrary with an export graph
        final GraphLibrary graphLibrary = new FileGraphLibrary("target/graphLibrary");

        final AccumuloProperties exportStoreProperties = new AccumuloProperties();
        // set other store property config here.

        final Schema exportSchema = new Schema.Builder()
                .edge("edge", new SchemaEdgeDefinition.Builder()
                        .source("int")
                        .destination("int")
                        .directed("true")
                        .property("count", "int")
                        .aggregate(false)
                        .build())
                .type("int", Integer.class)
                .type("true", new TypeDefinition.Builder()
                        .clazz(Boolean.class)
                        .validateFunctions(new IsTrue())
                        .build())
                .build();

        graphLibrary.addOrUpdate("exportGraphId", exportSchema, exportStoreProperties);

        final Graph graph = new Graph.Builder()
                .config(StreamUtil.openStream(getClass(), "graphConfigWithLibrary.json"))
                .addSchemas(StreamUtil.openStreams(getClass(), "operations/schema"))
                .storeProperties(new MapStoreProperties())
                .build();

        final OperationChain<Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAllElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .then(new ExportToOtherGraph.Builder()
                                .graphId("exportGraphId")
                                .build())
                        .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements",
                "view" : {
                "edges" : {
                    "edge" : { }
                }
                }
            }, {
                "class" : "ExportToOtherGraph",
                "graphId" : "exportGraphId"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements( 
                view=g.View( 
                    edges=[ 
                        g.ElementDefinition( 
                            group="edge" 
                        ) 
                        ], 
                        all_edges=False, 
                        all_entities=False 
                    ) 
                ), 
                g.ExportToOtherGraph( 
                    graph_id="exportGraphId" 
                ) 
            ] 
        )
        ```

??? example "Example export to new graph based on config from graph library"

    Similar to the previous example, this example will export all Edges with group 'edge' to another graph using a GraphLibrary. But in this example we show that you can export to a new graph with id newGraphId by choosing any combination of schema and store properties registered in the GraphLibrary. This is useful as a system administrator could register various different store properties, of different Accumulo clusters and a user could them just select which one to use by referring to the relevant store properties ID.
    
    === "Java"

        ``` java
        // Setup the graphLibrary with a schema and store properties for exporting
        final GraphLibrary graphLibrary = new FileGraphLibrary("target/graphLibrary");

        final AccumuloProperties exportStoreProperties = new AccumuloProperties();
        // set other store property config here.
        graphLibrary.addProperties("exportStorePropertiesId", exportStoreProperties);

        final Schema exportSchema = new Schema.Builder()
                .edge("edge", new SchemaEdgeDefinition.Builder()
                        .source("int")
                        .destination("int")
                        .directed("true")
                        .property("count", "int")
                        .aggregate(false)
                        .build())
                .type("int", Integer.class)
                .type("true", new TypeDefinition.Builder()
                        .clazz(Boolean.class)
                        .validateFunctions(new IsTrue())
                        .build())
                .build();
        graphLibrary.addSchema("exportSchemaId", exportSchema);

        final Graph graph = new Graph.Builder()
                .config(StreamUtil.openStream(getClass(), "graphConfigWithLibrary.json"))
                .addSchemas(StreamUtil.openStreams(getClass(), "operations/schema"))
                .storeProperties(new MapStoreProperties())
                .build();

        final OperationChain<Iterable<? extends Element>> opChain =
                new OperationChain.Builder()
                        .first(new GetAllElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .then(new ExportToOtherGraph.Builder()
                                .graphId("newGraphId")
                                .parentSchemaIds("exportSchemaId")
                                .parentStorePropertiesId("exportStorePropertiesId")
                                .build())
                        .build();
        ```

    === "JSON"

        ``` json
        {
            "class" : "OperationChain",
            "operations" : [ {
                "class" : "GetAllElements",
                "view" : {
                "edges" : {
                    "edge" : { }
                }
                }
            }, {
                "class" : "ExportToOtherGraph",
                "graphId" : "newGraphId",
                "parentSchemaIds" : [ "exportSchemaId" ],
                "parentStorePropertiesId" : "exportStorePropertiesId"
            } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
            operations=[ 
                g.GetAllElements( 
                view=g.View( 
                    edges=[ 
                        g.ElementDefinition( 
                            group="edge" 
                        ) 
                        ], 
                        all_edges=False, 
                        all_entities=False 
                    ) 
                ), 
                g.ExportToOtherGraph( 
                    graph_id="newGraphId", 
                    parent_schema_ids=[ 
                        "exportSchemaId" 
                    ], 
                    parent_store_properties_id="exportStorePropertiesId" 
                ) 
            ] 
        )
        ```

