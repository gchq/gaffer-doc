# GetJobDetails
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetJobDetails.html)

Available since Gaffer version 1.0.0

Gets the details of a single job

## Required fields
No required fields


## Examples

### Get job details in operation chain

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
final OperationChain<JobDetail> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();

{%- language name="JSON", type="json" -%}
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

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.DiscardOutput(), 
    g.GetJobDetails() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
JobDetail[jobId=80c4ce88-08b5-4dcb-a546-03c43f8a26b9,user=User[userId=user01,dataAuths=[],opAuths=[]],status=RUNNING,startTime=1632404085132,opChain=OperationChain[GetAllElements->DiscardOutput->GetJobDetails]]

{%- language name="JSON", type="json" -%}
{
  "jobId" : "80c4ce88-08b5-4dcb-a546-03c43f8a26b9",
  "user" : {
    "userId" : "user01",
    "dataAuths" : [ ],
    "opAuths" : [ ]
  },
  "status" : "RUNNING",
  "startTime" : 1632404085132,
  "opChain" : "OperationChain[GetAllElements->DiscardOutput->GetJobDetails]"
}
{%- endcodetabs %}

-----------------------------------------------

### Get job details

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
final GetJobDetails operation = new GetJobDetails.Builder()
        .jobId(jobId)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetJobDetails",
  "jobId" : "80c4ce88-08b5-4dcb-a546-03c43f8a26b9"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails",
  "jobId" : "80c4ce88-08b5-4dcb-a546-03c43f8a26b9"
}

{%- language name="Python", type="py" -%}
g.GetJobDetails( 
  job_id="80c4ce88-08b5-4dcb-a546-03c43f8a26b9" 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
JobDetail[jobId=80c4ce88-08b5-4dcb-a546-03c43f8a26b9,user=User[userId=user01,dataAuths=[],opAuths=[]],status=FINISHED,startTime=1632404085132,endTime=1632404085133,opChain=OperationChain[GetAllElements->DiscardOutput->GetJobDetails]]

{%- language name="JSON", type="json" -%}
{
  "jobId" : "80c4ce88-08b5-4dcb-a546-03c43f8a26b9",
  "user" : {
    "userId" : "user01",
    "dataAuths" : [ ],
    "opAuths" : [ ]
  },
  "status" : "FINISHED",
  "startTime" : 1632404085132,
  "endTime" : 1632404085133,
  "opChain" : "OperationChain[GetAllElements->DiscardOutput->GetJobDetails]"
}
{%- endcodetabs %}

-----------------------------------------------

