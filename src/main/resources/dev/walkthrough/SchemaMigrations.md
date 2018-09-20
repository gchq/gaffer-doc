${HEADER}

${CODE_LINK}


Changing schemas and migrating data is hard and can be computationally expensive to carry out.
It should be avoided whenever possible. Each Gaffer Store will have its
only complexities and mechanisms around schema migration. Some stores may
be able to handle full schema changes easily, but the majority will have
a set of limitations for what changes are allowed. For more details
on the types of schema changes allowed for the different Gaffer Stores
please see the specific store [documentation](../../summaries/stores.md).

When adding new groups and properties to your schema, you won't normally
be changing the existing data and this can be done without needing
to apply a migration. 

The rest of this guide will discuss 4 different mechanisms for applying schema migrations where changes to existing data is involved.
There is no perfect solution, they all have their limitations. It really depends on your use case.

1 - Delete the Graph, including all the data, and re-ingest the data in the new format.
This is the simplest approach and will guarantee a full migration. Obviously this doesn't work very well for large data sets or if you no longer have the raw data available.
     
2 - Run a job to migrate your data
This could be a custom MapReduce or Spark job to apply the migrations required to the data within the Gaffer store.
For Accumulo, this job would need to rewrite the R-files in HDFS.
If you have a large amount of data this could take a long time and may require your system to be offline while the job is carried out.
It also means users will need to update their queries to work against the new schema.
 
3 - Dual load new data in both the old and the new format.
For Graphs where you are continuously adding data, then this option may work well. 
The ideal solution is your old data will age off leaving you will a Graph full of data in the new format.
This will mean processing and storing your data twice. Users can continue querying the data in the old format until you are ready to fully switch across to the new format.

4 - Use a GraphHook to manipulate/convert user's queries and results into a new format.
We have a SchemaMigration GraphHook that will help to achieve this migration.
This mechanism does not require a one of large migration of your data.
It will transform the data as it is requested at query time, it doesn't rewrite the data on disk.
So if a user requests a single Edge, it will be transformed. 
Then if they request the same Edge again, it will be transformed again.
This can be configured to make the migration seamless for users, but it requires a significant amount of extra processing at query time. 
Some of which will be carried out in a single JVM so it will add a cost to queries.
This SchemaMigration GraphHook is currently marked as experimental.


## SchemaMigration GraphHook

The SchemaMigration GraphHook offers a basic method of migrating an old schema to a new.  An admin user can configure the 
GraphHook through json and then whenever a query it run the required migration will be applied.

Before using this GraphHook you will need to manually update the Schema for your Graph and update your ingest mechanisms to load the data into the new format.
To configure the SchemaMigration GraphHook, you need to specify the following
 - An outputType (NEW or OLD)
Whether you want the results of queries to be transformed into the new or the old format. 
This will normally initially be set to OLD, to give users time to update their queries to work against the new format.
 - The transform functions required to convert Edges/Entities to/from the old and new formats.
 - Whether you want to apply aggregation after the transforms are completed.
This is controlled by the aggregateAfter field. 
It is disabled by default as it is quite expensive to run due to need to bring the results into memory and run a map/reduce style operation.
However if it is disabled, new and old results will not be aggregated and post aggregation filters will be applied to the pre aggregated results.

Below is a simple example of this SchemaMigration.

NOTE - this will impact performance and is only experimental at this stage.

### SchemaMigration simple example
This example shows how the SchemaMigration GraphHook can be configured to update a simple property of an Element.

In this example we need to update the count property from a Long to an Integer (a strange thing to do - but it is just an example).

We cannot change the serialiser for the existing property as the existing data would then fail to deserialise.
Instead we create a new group RoadUseNew with the count property defined as a Long.

The updated schema now has both the old (RoadUse) and the new (RoadUseNew) groups in it. Here it is:
${START_JSON_CODE}
${MIGRATED_SCHEMA_JSON}
${END_CODE}

The SchemaMigration configuration is:
${START_JSON_CODE}
${MIGRATION_CONFIG_JSON}
${END_CODE}

We can see from this graph without migration how the Elements will be returned with the count property as a ```Long```.
Without the migration a simple query returns these Edges (with counts as ```Long```s):
```
${GET_ELEMENTS_NO_MIGRATION_RESULT}
```

Now using a json SchemaMigration GraphHook we can update the count property to an ```Integer```.
The query is the same as before, but the results are now:

```
${GET_ELEMENTS_MIGRATION_RESULT}
```
