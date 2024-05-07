# How to configure a Gaffer instance

There are various configuration files for Gaffer, this page gives an overview of
the commonly used files and how to tweak them for your project needs.

Here is an example file structure which suits a stand-alone deployment using
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

## Application Properties

The `application.properties` file is used for configuring the settings used by
a Gaffer REST endpoint. In general it borrows a concept from [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
to allow any properties related to Gaffer.

#### All REST Properties

The properties in bold are usually required for the REST endpoint to start.

| Property                 | Default | Description                             |
| ------------------------ | ------- | --------------------------------------- |
| **`gaffer.graph.config`** | None | Path String specifying the `graphConfig.json` file to use |
| **`gaffer.schemas`** | None | Path String specifying the directory to load schemas from |
| **`gaffer.storeProperties`** | None | Path String specifying the `store.properties` file to use |
| `gaffer.rest-api.basePath` | `rest` | String used to set the base path for the REST endpoint |
| `gaffer.rest-api.version` | `2.0.0` | String with version to use in REST response headers |
| `gaffer.graph.factory.class` | `uk.gov.gchq.gaffer.rest.factory.DefaultGraphFactory` | Class Name String for setting the Graph Factory to use |
| `gaffer.user.factory.class` | `uk.gov.gchq.gaffer.rest.factory.UnknownUserFactory` | Class Name String for setting the User Factory to use |
| `gaffer.package.prefixes` | `uk.gov.gchq` | Comma separated list of package prefixes to search for Functions and Operations |
| `gaffer.properties` | None | String CSV of properties to expose via the properties endpoint |
| `gaffer.properties.app.title` | `Gaffer REST` | String used as the page title for Swagger REST |
| `gaffer.properties.app.description` | `The Gaffer REST service.` | String used as the description for Swagger REST |
| `gaffer.properties.app.banner.colour` | `red` | String containing an HTML colour to use for page banner |
| `gaffer.properties.app.banner.description` | None | String to use for a single line page banner, not shown by default |
| `gaffer.properties.app.doc.url` | `https://gchq.github.io/gaffer-doc/latest/` | URL String used as the documentation link |
| `gaffer.properties.app.logo.link` | `https://github.com/gchq/Gaffer` | URL String used as the link when clicking on logo in Swagger |
| `gaffer.properties.app.logo.src` | `images/logo.png` | Path String for logo image used in Swagger |
| `gaffer.properties.app.logo.favicon.small` | `images/logo.png` | Path String for small favicon used by web server |
| `gaffer.properties.app.logo.favicon.large` | `images/logo.png` | Path String for large favicon used by web server |

The example below shows how we set the file location properties of where the other config files are.

```properties title="application.properties"
gaffer.schemas=/gaffer/schema
gaffer.storeProperties=/gaffer/store/store.properties
gaffer.graph.config=/gaffer/graph/graphConfig.json
```

## Graph Config JSON

Within the `graphConfig.json` file you can set various properties that relate
directly to the [`Graph` object](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/graph/Graph.html).

The file is in [JSON](../../user-guide/gaffer-basics/what-is-json.md) format,
a list of keys and their usage are outlined below:

| Key | Value Type | Description |
| --- | --- | --- |
| `graphId` | `String` | Sets the ID of the graph. |
| `description` | `String` | Sets the description of the graph. |
| `otelActive` | `Boolean` | Toggles if Open Telemetry data should be reported (`false` by default). |
| `view` | `JSON` | A default [`View`](../../user-guide/query/gaffer-syntax/filtering.md) that will be applied to `Operations`. |
| `library` | `JSON` | Any additional libraries you wish to load. |
| `hooks` | `List` | List of JSON objects containing any additional `GraphHooks` to load. |

The example below shows an example of how we could configure each of these:

??? example "Example `graphConfig.json`"

    ```json
    {
        "graphId": "ExampleGraph",
        "description": "An example graph",
        "otelActive": true,
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

If using a standard Map Store backed Gaffer deployment you can easily update the
values within your `graphConfig.json` later. The new key value pairs will be
updated upon restarting the graph (assuming the file is loaded correctly).

However, if you are using the Accumulo store, updating the `graphId` is a little
more complicated since the `graphId` corresponds to an Accumulo table. This
means that if you change the ID then a new Accumulo table will be used and any
existing data would be lost.

### Open Telemetry Reporting

As previously mentioned, you can activate Open Telemetry reporting on the graph
using the `otelActive` option. This will use an auto configured instance of
Open Telemetry that will start exporting reports.

It is recommended to configure the exporting so that you send reports to a
tool such as [Jaeger](https://www.jaegertracing.io/) to perform analysis later.
To configure Open Telemetry you can utilise environment variables, a subset of
commonly used variables are below:

| Variable | Description |
| --- | --- |
| `OTEL_SERVICE_NAME` | Sets the service name of the instance e.g. `gaffer`. |
| `OTEL_EXPORTER_OTLP_TRACES_ENDPOINT` | Sets the endpoint to export traces to, if using Jaeger this could be:` http://jaeger:4317` |
| `OTEL_TRACES_EXPORTER` | Allows setting a different exporter than the default of `otel`. |

!!! note
    Please see the [Open Telemetry documentation](https://opentelemetry.io/docs/languages/java/automatic/configuration/)
    for more information.

## Store Properties

Within the `store.properties` file we configure the Gaffer store which is used by
the Graph. By default you must provide a store class and a store properties
class as seen below. There are several different stores which can be configured
and require additional properties which can be found in the [Store Guide Section](../../administration-guide/gaffer-stores/store-guide.md).

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

## Operations Declarations JSON

Within the `operationsDeclarations.json` you can enable additional operations in
Gaffer. By default Gaffer already includes most operations (please refer to the
[Operations Guide pages](../../reference/operations-guide/operations.md)),
however you may want to enable other operations or even add your own custom
ones.

The example below shows how to enable the `ImportFromLocalFile` operation which
already exists in the [code base](https://github.com/gchq/Gaffer/blob/develop/core/operation/src/main/java/uk/gov/gchq/gaffer/operation/impl/export/localfile/ImportFromLocalFile.java)
but isn't included by default.

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
