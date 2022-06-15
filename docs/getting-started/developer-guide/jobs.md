# Jobs

The code for this example is [Jobs](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/Jobs.java).

This example explains how to configure your Gaffer Graph to allow Jobs to be executed.
This includes a Job Tracker to store the status of current and historic jobs and a cache to store job results in.
Jobs are useful if an operation chain takes a long time to return results or if you wish to cache the results of an operation chain.
When we refer to a 'Job' we are really just talking about an Operation Chain containing a long-running sequence of operations that is executed asynchronously.


## Configuration

By default the Job Tracker is disabled. To enable the job tracker set this store.property:

```
gaffer.store.job.tracker.enabled=true
```

You will also need to configure what cache to use for the job tracker.  For more information on this please see [Cache](cache.md).

In addition to the job tracker, it is recommended that you enable a `GafferResultCache` to store the job results in. The caching mechanism is implemented as operations and operation handlers. By default these are disabled.
The job result cache is simply a second Gaffer Graph. So, if you are running on Accumulo, this can just be a separate table in your existing Accumulo cluster.

Two operations are required for exporting and getting results from a Gaffer cache - ExportToGafferResultCache and GetGafferResultCacheExport.
These two operations need to be registered by providing an Operations Declarations JSON file in your store.properties file.
To use the Accumulo store as your Gaffer cache the operations declarations JSON file would need to look something like:


```json
{
  "operations": [
    {
      "operation": "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
      "handler": {
        "class": "uk.gov.gchq.gaffer.operation.export.resultcache.handler.ExportToGafferResultCacheHandler",
        "graphId": "resultCacheGraph",
        "timeToLive": 86400000,
        "storePropertiesPath": "cache-store.properties"
      }
    },
    {
      "operation": "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "handler": {
        "class": "uk.gov.gchq.gaffer.operation.export.resultcache.handler.GetGafferResultCacheExportHandler",
        "graphId": "resultCacheGraph",
        "timeToLive": 86400000,
        "storePropertiesPath": "cache-store.properties"
      }
    }
  ]
}
```


Here we are simply registering the fact that ExportToGafferResultCache operation should be handled by the ExportToGafferResultCacheHandler handler. We also provide a path to the Gaffer cache store properties for the cache handler to create a Gaffer graph.
Then to register this file in your store.properties file you will need to add the following:

```
gaffer.store.operation.declarations=/path/to/ResultCacheExportOperations.json
```

If you are also registering other operations you can just supply a comma separated list of operation declaration files:

```
gaffer.store.operation.declarations=/path/to/operations1.json,/path/to/ResultCacheExportOperations.json
```

The JSON files can either be placed on your file system or bundled as a resource in your JAR or WAR archive.

For this example the cache-store.properties just references another MockAccumuloStore table:


```properties
gaffer.store.class=uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore
accumulo.instance=someInstanceName
accumulo.zookeepers=aZookeeper
accumulo.user=user01
accumulo.password=password
gaffer.store.job.executor.threads=1

```



## Using Jobs
OK, now for some examples of using Jobs.

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


Then create a job, otherwise known as an operation chain:


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<? extends Element>> job = new OperationChain.Builder()
        .first(new GetElements.Builder()
                .input(new EntitySeed("10"))
                .view(new View.Builder()
                        .edge("RoadUse")
                        .build())
                .build())
        .build();
{%- endcodetabs %}


When you execute your job, instead of using the normal execute method on Graph you will need to use the executeJob method.


{% codetabs name="Java", type="java" -%}
final JobDetail initialJobDetail = graph.executeJob(job, user);
final String jobId = initialJobDetail.getJobId();
{%- endcodetabs %}


and the results is:

```
JobDetail[jobId=a20f6b51-30cb-4d20-a5ad-69eb45f86a00,user=User[userId=user01,dataAuths=[],opAuths=[]],status=RUNNING,startTime=1632404080714,opChain=OperationChain[GetElements]]

```

As you can see this returns a JobDetail object containing your job id. The GetJobDetails operation allows you to check the status of your Job, e.g:


{% codetabs name="Java", type="java" -%}
final JobDetail jobDetail = graph.execute(
        new GetJobDetails.Builder()
                .jobId(jobId)
                .build(),
        user);
{%- endcodetabs %}


and now you can see the job has finished:

```
JobDetail[jobId=a20f6b51-30cb-4d20-a5ad-69eb45f86a00,user=User[userId=user01,dataAuths=[],opAuths=[]],status=FINISHED,startTime=1632404080714,endTime=1632404080744,opChain=OperationChain[GetElements->ExportToGafferResultCache]]

```

You can actually get the details of all running and completed jobs using the GetAllJobDetails operation:


{% codetabs name="Java", type="java" -%}
final CloseableIterable<JobDetail> jobDetails = graph.execute(new GetAllJobDetails(), user);
{%- endcodetabs %}


Then finally you can get the results of your job using the GetJobResults operation:


{% codetabs name="Java", type="java" -%}
final CloseableIterable<?> jobResults = graph.execute(new GetJobResults.Builder()
        .jobId(jobId)
        .build(), user);
{%- endcodetabs %}


and the results were:

```
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 PDT 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 PDT 2000]]

```
