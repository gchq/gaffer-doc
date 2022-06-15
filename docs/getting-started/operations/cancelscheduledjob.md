# CancelScheduledJob
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.CancelScheduledJob](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/job/CancelScheduledJob.html)

Available since Gaffer version 1.9.0

Cancels a scheduled job specified by the job id

## Required fields
The following fields are required: 
- jobId


## Examples

### Scheduled job before being cancelled

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<JobDetail> operationChain = new OperationChain.Builder()
        .first(new GetJobDetails.Builder().jobId(jobId).build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetJobDetails",
    "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails",
    "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetJobDetails( 
      job_id="92d1f08a-a070-473c-b585-2a03422df0c2" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
JobDetail[jobId=92d1f08a-a070-473c-b585-2a03422df0c2,user=User[userId=user01,dataAuths=[],opAuths=[]],status=SCHEDULED_PARENT,startTime=1632404083100,endTime=1632404083101,opChain=OperationChain[GetAllElements],serialisedOperationChain={"class":"uk.gov.gchq.gaffer.operation.OperationChain","operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAllElements","view":{"edges":{"edge":{},"edge1":{}},"entities":{"entity1":{},"entity":{},"cardinality":{}}}}]},repeat=Repeat[initialDelay=1,repeatPeriod=1,timeUnit=MINUTES]]

{%- language name="JSON", type="json" -%}
{
  "repeat" : {
    "initialDelay" : 1,
    "repeatPeriod" : 1,
    "timeUnit" : "MINUTES"
  },
  "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2",
  "user" : {
    "userId" : "user01",
    "dataAuths" : [ ],
    "opAuths" : [ ]
  },
  "status" : "SCHEDULED_PARENT",
  "startTime" : 1632404083100,
  "endTime" : 1632404083101,
  "opChain" : "OperationChain[GetAllElements]"
}
{%- endcodetabs %}

-----------------------------------------------

### Cancel scheduled job


{% codetabs name="Java", type="java" -%}
final OperationChain chain = new OperationChain.Builder()
        .first(new CancelScheduledJob.Builder()
                .jobId(jobId)
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "CancelScheduledJob",
    "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.CancelScheduledJob",
    "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.CancelScheduledJob( 
      job_id="92d1f08a-a070-473c-b585-2a03422df0c2" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Scheduled job after being cancelled

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<JobDetail> operationChain = new OperationChain.Builder()
        .first(new GetJobDetails.Builder().jobId(jobId).build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetJobDetails",
    "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails",
    "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetJobDetails( 
      job_id="92d1f08a-a070-473c-b585-2a03422df0c2" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
JobDetail[jobId=92d1f08a-a070-473c-b585-2a03422df0c2,user=User[userId=user01,dataAuths=[],opAuths=[]],status=CANCELLED,startTime=1632404083100,endTime=1632404083101,opChain=OperationChain[GetAllElements],serialisedOperationChain={"class":"uk.gov.gchq.gaffer.operation.OperationChain","operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAllElements","view":{"edges":{"edge":{},"edge1":{}},"entities":{"entity1":{},"entity":{},"cardinality":{}}}}]},repeat=Repeat[initialDelay=1,repeatPeriod=1,timeUnit=MINUTES]]

{%- language name="JSON", type="json" -%}
{
  "repeat" : {
    "initialDelay" : 1,
    "repeatPeriod" : 1,
    "timeUnit" : "MINUTES"
  },
  "jobId" : "92d1f08a-a070-473c-b585-2a03422df0c2",
  "user" : {
    "userId" : "user01",
    "dataAuths" : [ ],
    "opAuths" : [ ]
  },
  "status" : "CANCELLED",
  "startTime" : 1632404083100,
  "endTime" : 1632404083101,
  "opChain" : "OperationChain[GetAllElements]"
}
{%- endcodetabs %}

-----------------------------------------------

