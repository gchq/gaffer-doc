# Example Deployment

This guide will run through the start up and deployment of a basic Gaffer instance. It will cover
how to write a basic Gaffer Schema from scratch along with using the premade containers to run the
Gaffer rest API and Accumulo based data store.

## The Example Graph

For this basic example we will attempt to recreate the graph in the following diagram consisting of
two nodes (vertexes) with one directed edge between them.

!!! note "diagram here"

This data describes one individual and a single piece of software that has been created by that
individual. The data will be loaded into the graph from a CSV file that follows the Neo4j export
syntax, this demonstrates how Gaffer can be used and how it can interact, model and query data from
other popular graph databases. Even with this basic graph we should be able to start building
queries to ask questions like "Who created the software called 'lop'?" and "How much did 'marko'
contribute to the software called 'lop'?" etc.

To go with the diagram above the following CSV file (both raw and rendered are provided) represents
the graph in Neo4j syntax.

!!! note "" Please note that Gaffer often requires additional information about the data, such as
    `:String` on the column headers, to help with typing of the values. This is demonstrated below
    in the raw file.

=== "Table"
    | _id | name  | age | lang | _labels  | _start | _end | _type   | weight |
    |-----|-------|-----|------|----------|--------|------|---------|--------|
    | v1  | marko | 29  |      | Person   |        |      |         |        |
    | v2  | lop   |     | java | Software |        |      |         |        |
    | e1  |       |     |      |          | v1     | v2   | Created | 0.4    |

=== "CSV"
    ```csv
    _id,name:String,age:Int,lang:String,_labels,_start,_end,_type,weight:Float
    v1,marko,29,,Person,,,,
    v2,lop,,java,Software,,,,
    e1,,,,,v1,v2,Created,0.4
    ```

## Project Setup

First you must set up the files and directories you will need for the instance. As it stands there
are a couple of different ways to run a Gaffer project this example will use a logical structure
that suites a stand alone deployment consisting of the following file structure:

!!! example "Example Gaffer project structure"

    !!! tip
        Click the plus symbols for a brief description of each file

    ```yaml
    ├── config
    │   ├── accumulo
    │   │   ├── accumulo-client.properties
    │   │   ├── accumulo-env.sh
    │   │   ├── accumulo.properties
    │   │   ├── core-site.xml
    │   │   └── log4j.properties
    │   ├── gaffer
    │   │   ├── application.properties #(1)!
    │   │   ├── data                   #(2)!
    │   │   │   ├── neo4jExport.csv
    │   │   ├── graph
    │   │   │   └── graphConfig.json #(3)!
    │   │   ├── schema
    │   │   │   ├── elements.json #(4)!
    │   │   │   └── types.json    #(5)!
    │   │   └── store
    │   │       ├── operationsDeclarations.json #(6)!
    │   │       └── store.properties            #(7)!
    │   └── hdfs
    │       ├── core-site.xml
    │       ├── hdfs-site.xml
    │       └── log4j.properties
    └── docker-compose.yaml #(8)!
    ```

    1. Properties file that generally sets the file locations of other Gaffer
        configs e.g. schemas (note these are the absolute paths inside the
        container).
    2. Any data files, e.g. CSV, to be made available to the Gaffer container.
    3. The main graph config file to set various properties of the overall graph.
    4. This file holds the schema outlining the elements in the graph, e.g. the
    nodes (aka entities) and edges.
    5. This file defines the different data types in the graph and how they are
    serialised to Java classes.
    6. Config file for additional Gaffer operations and set the class to handle
    them on the store.
    7. The General store properties, sets up what store to use and any additional
    configuration.
    8. This file controls which containers will be started up and the configuration
    of them to ensure correct ports and files are available.

All the files in the `config/accumulo/` and `config/hdfs/` directories will be copied directly from
the two locations in the Gaffer docker repo,
[here](https://github.com/gchq/gaffer-docker/tree/develop/docker/accumulo/conf-2.0.1) and
[here](https://github.com/gchq/gaffer-docker/tree/develop/docker/hdfs/conf). The configuration of
these are out of scope of this example but are covered in other sections of the documentation. The
main focus of this guide will be on the configuration files under the `config/gaffer/` directory.
