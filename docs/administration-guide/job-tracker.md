# Job Tracker

This section covers how to configure your Gaffer graph to allow jobs to be executed.

'Job' refers to an operation chain with a long-running sequence of operations that is executed
asynchronously. 
Jobs are, therefore, useful for operation chains which take a long time to return results or if 
you want to cache the results of your operation chain. 

Jobs are only available when the job tracker has been enabled. 
The job tracker can then be used to store the status of current and historic jobs 
while a 'cache' is used to store any job results. 

## Configuration

By default, the job tracker is disabled. 
It can be enabled by setting its associated [store property](../administration-guide/gaffer-stores/store-guide.md#store-properties) to true.
There must also be a [cache configured](../administration-guide/gaffer-stores/store-guide.md#caches) for the job
tracker to work.

### Configuring the Results Cache
To store any results using the job tracker, a `GafferResultCache` needs to enabled. 
This job results cache can then store your results using operations and operation handlers 
(these are disabled by default). 
Note that this is not the same as your initial cache, as this results 'cache' is simply a second Gaffer graph
which will store your results.

To store results using the job tracker a few elements must be set up. 
Firstly, you need to add two operations to an [operations declarations JSON file](../administration-guide/gaffer-config/config.md#operations-declarations-json).
These operations are [`ExportToGafferResultCache` and `GetGafferResultCacheExport`](../reference/operations-guide/export.md#exporttogafferresultcache).
Together these allow you to store your results in a seperate Gaffer graph, your 'cache'.

!!! example "Adding the operations to your Operations Declarations file"
    
    ``` json
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

As the results 'cache' is a separate Gaffer graph; an additional `cache-store.properties` file must be created
to configure the store properties for your results graph. 
This could be a MapStore or a separate table in your Accumulo cluster. 
The `storePropertiesPath` in your Operation Declarations JSON file is a path to this second store properties file
which then allows the operation handlers to store results in the second graph.

## Using Jobs 

Once configured, there are [several operations](../reference/operations-guide/job.md) available
to manage Jobs. 

Additionally, there are 5 endpoints in the REST API that can be used to manage Jobs. 

### Get Jobs

In addition to the [GetAllJobDetails operation](../reference/operations-guide/job.md#getalljobdetails) there
is an endpoint that gets the details of all asynchronous jobs.
This endpoint is simply `/rest/graph/jobs` and takes no parameters. 

### Execute a Job 

There is the `/rest/graph/jobs` POST endpoint which can be used to schedule an asynchronous job.
This endpoint takes an operation, this is either in the form of a single operation, an Operation Chain
stored as a variable and then passed to the endpoint or a Named Operation.

Additionally, you can execute a job using the Java, this requires a user to be configured.

??? example "Execute a job"
    ```java
    final User user = new User("user01");

    OperationChain<Long> operation = new OperationChain.Builder()
        .first(new GetElements.Builder().input(new EntitySeed("v1")).build())
        .then(new Count<>())
        .build();

    final Job job = graph.execute(operation, user);
    ```

When you execute a job the details of that job will be returned.

### Retrieving Job Details and Results

There are two more GET endpoints; one for getting the details of a job
and one for getting the results of a job.

`/rest/graph/jobs/{id}` can be used to get the details of a single job
using that job's id. 
When making a request to this endpoint you must specify the job id you want the details of,
similar to the [GetJobDetails operation](../reference/operations-guide/job.md#getjobdetails).

The `/rest/graph/jobs/{id}/results` endpoint is similar as it also 
requires a specific job id to return a specific job's results from the cache. 

### Schedule Jobs

The final endpoint is another POST endpoint which can be used to schedule an 
asynchronous job to run at a specified timepoint(s). 

??? example "Example of a job being scheduled"

    === "Java"
        ``` java
        final Job job = new Job(new Repeat(20L, 30L, TimeUnit.SECONDS), new GetAllElements());
        ```

    === "JSON"
    Currently this can only be executed at the /graph/jobs/schedule endpoint.
        ``` json
        {
            "repeat": {
                "initialDelay": 20,
                "repeatPeriod": 30,
                "timeUnit": "SECONDS"
            },
            "operation":{
                "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
            }
        }
        ```
