## How to configure a Gaffer instance
There are various configuration files for Gaffer, this page gives an overview of
the commonly used files and how to tweak them for your project needs.

Here is an example file structure which suites a stand alone deployment using
docker:

!!! example "Example Gaffer project structure"

    !!! tip ""
        Click the plus symbols for a brief description of each file

    ```yaml
    ├── config
    │   ├── gaffer
    │       ├── application.properties #(1)!
    │       ├── data                   #(2)!
    │       │   ├── neo4jExport.csv
    │       ├── graph
    │       │   └── graphConfig.json #(3)!
    │       ├── schema
    │       │   ├── elements.json #(4)!
    │       │   └── types.json    #(5)!
    │       └── store
    │           ├── operationsDeclarations.json #(6)!
    │           └── store.properties            #(7)!
    │   
    └── docker-compose.yaml #(8)!
    ```

    1. Properties file that generally sets the file locations of other Gaffer
        configs e.g. schemas (note these are the absolute paths inside the
        container).
    2. Any data files, e.g. CSV, to be made available to the Gaffer container.
    3. The graph config file to set id, description and other values for the graph.
    4. This file holds the schema outlining the elements in the graph, e.g. the
    entities and edges.
    5. This file defines the different data types in the graph and how they are
    serialised to Java classes. An example of the schema files can be found [here](../../user-guide/schema.md)
    6. Config file for additional Gaffer operations and set the class to handle
    them on the store.
    7. The General store properties, sets up what store to use and any additional
    configuration.
    8. This file controls which containers will be started up and the configuration
    of them to ensure correct ports and files are available.

## Configuration Files

### application.properties

Within the application.properties file we set the file location properties of
where the other config files are. In general it borrows a concept from [Spring
Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
to allow any properties related to Gaffer.

```properties title="application.properties"
gaffer.schemas=/gaffer/schema
gaffer.storeProperties=/gaffer/store/store.properties
gaffer.graph.config=/gaffer/graph/graphConfig.json
```

### graphConfig.json

Within the graphCongfig.json file you can set multiple properties. Here you define the `graphId` and `description` of the graph. You can also define any additonal `hooks` to run when operations are perfomed, a `view` you want to be applied when operations are perfomed and any addtional libraries with the `library` property.

The example below shows how we would configure each of these.

```json title="graphConfig.json"
{
    "graphId": "ExampleGraph",
    "description": "An example graph",
    "view": {
        "globalElements": [
        {
            "postAggregationFilterFunctions": [
            {
                "predicate": {
                "class": "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
                "orEqualTo": false,
                "value": "10"
                },
                "selection": ["ExamplePropertyName"]
            }
            ]
        }
        ]
    },
    "hooks": [
        {
            "class": "uk.gov.gchq.gaffer.graph.hook.FunctionAuthoriser"
        }
    ],
    "library": {
      "class": "uk.gov.gchq.gaffer.store.library.FileGraphLibrary"
  }
}
```

#### Changing Values

For a standard Gaffer deployment you can easily change the values within your
`graphConfig.json`. The new key value pairs will be updated upon restarting the
graph (assuming the file is loaded correctly).

However be aware, if you are using the Accumulo store, updating the `graphId` is a
little more complicated since the `graphId` corresponds to an Accumulo table. This
means that if you change the ID then a new Accumulo table will be used and any
existing data would be lost.

### store.properties

Within the store.properties file we configure the Gaffer store which is used by
the Graph. By default you must provide a store class and a store properties
class as seen below. There are several different stores which can be configured
and require additional properties which can be found in the [Store Guide
Section](../../administration-guide/gaffer-stores/store-guide.md).

Below is an example of a store.properties file for a Graph using an Accumulo store.

```properties title="store.properties"
# Default properties
gaffer.store.class=uk.gov.gchq.gaffer.accumulostore.AccumuloStore
gaffer.store.properties.class=uk.gov.gchq.gaffer.accumulostore.AccumuloProperties

# Accumulo specific config
accumulo.instance=accumulo
accumulo.zookeepers=zookeeper
accumulo.user=root
accumulo.password=secret
```

### operationsDeclarations.json

Within the operationsDeclarations.json you can enable additional operations in Gaffer. By default Gaffer already includes most operations (please refer to the [Operations Guide pages](../../reference/operations-guide/operations.md)), however you may want to enable other operations or even add your own custom ones.

The example below shows how to enable the `ImportFromLocalFile` operation which already [exists in the code base](https://github.com/gchq/Gaffer/blob/develop/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/impl/export/localfile/ImportFromLocalFile.java) but isn't included by default.

```json title="operationsDeclarations.json"
{
    "operations": [
        {
            "operation": "uk.gov.gchq.gaffer.operation.impl.export.localfile.ImportFromLocalFile",
            "handler": {
                "class": "uk.gov.gchq.gaffer.store.operation.handler.export.localfile.ImportFromLocalFileHandler"
            }
        }
    ]
}
```