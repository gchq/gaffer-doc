# Job Tracker Operations

These Operations are used for managing Jobs. They are only available when the Job Tracker is enabled. By default, this is disabled.

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

## GetAllJobDetails

Gets all running and historic job details for the graph. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetAllJobDetails.html)

??? example "Example GetAllJobDetails"

    === "Java"

        ``` java
        final GetAllJobDetails operation = new GetAllJobDetails();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetAllJobDetails"
        }
        ```

    === "Python"

        ``` python
        g.GetAllJobDetails()
        ```

    Results:

    === "Java"

        ``` java
        JobDetail[jobId=b7084ad3-68ab-4a7b-879c-4c71813ac66f,user=User[userId=user01,dataAuths=[],opAuths=[]],status=RUNNING,startTime=1667818802286,opChain=OperationChain[GetAllJobDetails]]
        ```

    === "JSON"

        ``` json
        [ {
        "jobId" : "b7084ad3-68ab-4a7b-879c-4c71813ac66f",
        "user" : {
            "userId" : "user01",
            "dataAuths" : [ ],
            "opAuths" : [ ]
        },
        "status" : "RUNNING",
        "startTime" : 1667818802286,
        "opChain" : "OperationChain[GetAllJobDetails]"
        } ]
        ```

## GetJobDetails

Gets the details of a single job. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetJobDetails.html)

??? example "Example getting job details in an operation chain"

    === "Java"

        ``` java
        final OperationChain<JobDetail> opChain = new OperationChain.Builder()
                .first(new GetAllElements())
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
            g.DiscardOutput(), 
            g.GetJobDetails() 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        JobDetail[jobId=306e1208-62d2-47d5-b2c2-1005d3295011,user=User[userId=user01,dataAuths=[],opAuths=[]],status=RUNNING,startTime=1667818803505,opChain=OperationChain[GetAllElements->DiscardOutput->GetJobDetails]]
        ```

    === "JSON"

        ``` json
        {
        "jobId" : "306e1208-62d2-47d5-b2c2-1005d3295011",
        "user" : {
            "userId" : "user01",
            "dataAuths" : [ ],
            "opAuths" : [ ]
        },
        "status" : "RUNNING",
        "startTime" : 1667818803505,
        "opChain" : "OperationChain[GetAllElements->DiscardOutput->GetJobDetails]"
        }
        ```

??? example "Example getting job details with specific jobId"

    === "Java"

        ``` java
        final GetJobDetails operation = new GetJobDetails.Builder()
                .jobId(jobId)
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetJobDetails",
        "jobId" : "306e1208-62d2-47d5-b2c2-1005d3295011"
        }
        ```

    === "Python"

        ``` python
        g.GetJobDetails( 
        job_id="306e1208-62d2-47d5-b2c2-1005d3295011" 
        )
        ```

    Results:

    === "Java"

        ``` java
        JobDetail[jobId=306e1208-62d2-47d5-b2c2-1005d3295011,user=User[userId=user01,dataAuths=[],opAuths=[]],status=FINISHED,startTime=1667818803505,endTime=1667818803505,opChain=OperationChain[GetAllElements->DiscardOutput->GetJobDetails]]
        ```

    === "JSON"

        ``` json
        {
        "jobId" : "306e1208-62d2-47d5-b2c2-1005d3295011",
        "user" : {
            "userId" : "user01",
            "dataAuths" : [ ],
            "opAuths" : [ ]
        },
        "status" : "FINISHED",
        "startTime" : 1667818803505,
        "endTime" : 1667818803505,
        "opChain" : "OperationChain[GetAllElements->DiscardOutput->GetJobDetails]"
        }
        ```

## GetJobResults

Gets the results of a job. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetJobResults.html)

??? example "Example GetJobResults"

    === "Java"

        ``` java
        final GetJobResults operation = new GetJobResults.Builder()
                .jobId(jobId)
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "GetJobResults",
        "jobId" : "60d667eb-a20d-44c2-963f-fc1b6c9b3868"
        }
        ```

    === "Python"

        ``` python
        g.GetJobResults( 
        job_id="60d667eb-a20d-44c2-963f-fc1b6c9b3868" 
        )
        ```

    Results:

    === "Java"

        ``` java
        Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]
        Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
        Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
        Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
        Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
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
        } ]
        ```

## CancelScheduledJob

Cancels a scheduled job specified by the job id. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/job/CancelScheduledJob.html)

??? example "Example of a scheduled job before being cancelled"

    === "Java"

        ``` java
        final OperationChain<JobDetail> operationChain = new OperationChain.Builder()
                .first(new GetJobDetails.Builder().jobId(jobId).build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "GetJobDetails",
            "jobId" : "35c1bd84-1cd3-4609-8892-710e9d3c2d3f"
        } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
        operations=[ 
            g.GetJobDetails( 
            job_id="35c1bd84-1cd3-4609-8892-710e9d3c2d3f" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        JobDetail[jobId=35c1bd84-1cd3-4609-8892-710e9d3c2d3f,user=User[userId=user01,dataAuths=[],opAuths=[]],status=SCHEDULED_PARENT,startTime=1667818799343,endTime=1667818799344,opChain=OperationChain[GetAllElements],serialisedOperationChain={"class":"uk.gov.gchq.gaffer.operation.OperationChain","operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAllElements","view":{"edges":{"edge":{},"edge1":{}},"entities":{"entity1":{},"entity":{},"cardinality":{}}}}]},repeat=Repeat[initialDelay=1,repeatPeriod=1,timeUnit=MINUTES]]
        ```

    === "JSON"

        ``` json
        {
        "repeat" : {
            "initialDelay" : 1,
            "repeatPeriod" : 1,
            "timeUnit" : "MINUTES"
        },
        "jobId" : "35c1bd84-1cd3-4609-8892-710e9d3c2d3f",
        "user" : {
            "userId" : "user01",
            "dataAuths" : [ ],
            "opAuths" : [ ]
        },
        "status" : "SCHEDULED_PARENT",
        "startTime" : 1667818799343,
        "endTime" : 1667818799344,
        "opChain" : "OperationChain[GetAllElements]"
        }
        ```

??? example "Example cancelling a scheduled job"

    === "Java"

        ``` java
        final OperationChain chain = new OperationChain.Builder()
                .first(new CancelScheduledJob.Builder()
                        .jobId(jobId)
                        .build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "CancelScheduledJob",
            "jobId" : "35c1bd84-1cd3-4609-8892-710e9d3c2d3f"
        } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
        operations=[ 
            g.CancelScheduledJob( 
            job_id="35c1bd84-1cd3-4609-8892-710e9d3c2d3f" 
            ) 
        ] 
        )
        ```

??? example "Example of a scheduled job after being cancelled"

    === "Java"

        ``` java
        final OperationChain<JobDetail> operationChain = new OperationChain.Builder()
                .first(new GetJobDetails.Builder().jobId(jobId).build())
                .build();
        ```

    === "JSON"

        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "GetJobDetails",
            "jobId" : "35c1bd84-1cd3-4609-8892-710e9d3c2d3f"
        } ]
        }
        ```

    === "Python"

        ``` python
        g.OperationChain( 
        operations=[ 
            g.GetJobDetails( 
            job_id="35c1bd84-1cd3-4609-8892-710e9d3c2d3f" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"

        ``` java
        JobDetail[jobId=35c1bd84-1cd3-4609-8892-710e9d3c2d3f,user=User[userId=user01,dataAuths=[],opAuths=[]],status=CANCELLED,startTime=1667818799343,endTime=1667818799344,opChain=OperationChain[GetAllElements],serialisedOperationChain={"class":"uk.gov.gchq.gaffer.operation.OperationChain","operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAllElements","view":{"edges":{"edge":{},"edge1":{}},"entities":{"entity1":{},"entity":{},"cardinality":{}}}}]},repeat=Repeat[initialDelay=1,repeatPeriod=1,timeUnit=MINUTES]]

        ```

    === "JSON"

        ``` json
        {
        "repeat" : {
            "initialDelay" : 1,
            "repeatPeriod" : 1,
            "timeUnit" : "MINUTES"
        },
        "jobId" : "35c1bd84-1cd3-4609-8892-710e9d3c2d3f",
        "user" : {
            "userId" : "user01",
            "dataAuths" : [ ],
            "opAuths" : [ ]
        },
        "status" : "CANCELLED",
        "startTime" : 1667818799343,
        "endTime" : 1667818799344,
        "opChain" : "OperationChain[GetAllElements]"
        }
        ```