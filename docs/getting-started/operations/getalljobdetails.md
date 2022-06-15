# GetAllJobDetails
See javadoc - [uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/job/GetAllJobDetails.html)

Available since Gaffer version 1.0.0

Gets all running and historic job details

## Required fields
No required fields


## Examples

### Get all job details

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
final GetAllJobDetails operation = new GetAllJobDetails();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllJobDetails"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetAllJobDetails"
}

{%- language name="Python", type="py" -%}
g.GetAllJobDetails()

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
JobDetail[jobId=980de8bb-a4fd-48ed-bc60-9ac452d16293,user=User[userId=user01,dataAuths=[],opAuths=[]],status=RUNNING,startTime=1632404084426,opChain=OperationChain[GetAllJobDetails]]

{%- language name="JSON", type="json" -%}
[ {
  "jobId" : "980de8bb-a4fd-48ed-bc60-9ac452d16293",
  "user" : {
    "userId" : "user01",
    "dataAuths" : [ ],
    "opAuths" : [ ]
  },
  "status" : "RUNNING",
  "startTime" : 1632404084426,
  "opChain" : "OperationChain[GetAllJobDetails]"
} ]
{%- endcodetabs %}

-----------------------------------------------

