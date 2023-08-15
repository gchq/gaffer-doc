# Additional Configuration

This section will cover the smaller additional config files that go along side the main Gaffer
schema to tweak other aspects of the Gaffer graph. The location of these files will need to be
volume mounted into the container for them to be included in the deployment, the [next
section](./running-the-deployment.md) on running the deployment discusses this in more detail.

!!! note
    Many of these files have defaults already in the standard `gaffer-rest` container image, but its
    useful to still include them in the project to allow easy configuration.

## Application Properties

This is probably the simplest configuration file in the Gaffer deployment. In general it borrows a
concept from spring boot to allow changing the context root and any properties related to Gaffer. In
the example that follows we use it to set the file location properties of where the other config
files are (inside the container).

```properties title="application.properties"
gaffer.schemas=/gaffer/schema
gaffer.storeProperties=/gaffer/store/store.properties
gaffer.graph.config=/gaffer/graph/graphConfig.json
```

## Graph Configuration

The graph configuration file is a JSON file that configures few bits of the Gaffer graph. Primarily
its used to set the name and description along with any additional hooks to run before an operation
chain e.g. to impose limits on max results etc. For the example as, it is a very basic graph we just
set the name and short description.

```json title="graphConfig.json"
{
    "graphId": "ExampleGraph",
    "description": "An exmaple graph"
}
```

## Store Properties

The store properties file is used to configure how Gaffer will store its data. There are a few
different stores available for Gaffer, these are explained in more detail in the reference
documentation, but by default you must provide a store class and a store properties class. For this
example we are using an Accumulo store as it is recommended for efficient storage and retrieval of
large data volumes. It's set up requires a few custom properties which are outlined in the following
file.

```properties title="store.properties"
gaffer.store.class=uk.gov.gchq.gaffer.accumulostore.AccumuloStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.accumulostore.AccumuloProperties

# Accumulo specific config
accumulo.instance=accumulo
accumulo.zookeepers=zookeeper
accumulo.user=root
accumulo.password=secret

# General store config
gaffer.cache.service.class=uk.gov.gchq.gaffer.cache.impl.HashMapCacheService
gaffer.store.job.tracker.enabled=true
gaffer.store.operation.declarations=/gaffer/store/operationsDeclarations.json
```

## Operations Declarations

The operation declarations file is a way of enabling additional operations in Gaffer. By default
there are some built in operations already available (the rest API has a get all operations request
to see a list), but its likely you might want to enable others or add your own custom ones. As the
example will load its data from a local CSV file we can activate a couple of additional operations
using the following file.

```json title="operationsDeclarations.json"
{
    "operations": [
        {
            "operation": "uk.gov.gchq.gaffer.operation.impl.export.localfile.ImportFromLocalFile",
            "handler": {
                "class": "uk.gov.gchq.gaffer.store.operation.handler.export.localfile.ImportFromLocalFileHandler"
            }
        },
        {
            "operation": "uk.gov.gchq.gaffer.operation.impl.export.localfile.ExportToLocalFile",
            "handler": {
                "class": "uk.gov.gchq.gaffer.store.operation.handler.export.localfile.ExportToLocalFileHandler"
            }
        }
    ]
}
```

The two additional operations already exist in in Gaffer (you can find them in the code base
[here](https://github.com/gchq/Gaffer/blob/develop/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/impl/export/localfile/ImportFromLocalFile.java)
and
[here](https://github.com/gchq/Gaffer/blob/develop/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/impl/export/localfile/ExportToLocalFile.java)),
what this file is doing is essentially activating them and setting the handler class for them. The
`ImportFromLocalFile` usage is demonstrated in the [using the API](./using-the-api.md) section to
load some data.

This operation allows us to pass a local (to the container) CSV file which will be read line by line
and get a stream of the line strings. This is very useful when we start using Operation Chains as,
we can pass this stream of data as the input to the next operation in the chain similar to shell
pipes.

!!! note
    The location of the file needs to be set via the store properties file using the
    `gaffer.store.operation.declarations` property (see [previous section](#store-properties)).
