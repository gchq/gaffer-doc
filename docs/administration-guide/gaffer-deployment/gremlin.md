# Using Gremlin in Gaffer

It is possible to use Gremlin as an alternative querying language in Gaffer. To
make Gremlin available however, there are some additional steps that need to be
taken to ensure it is setup correctly.

## Overview

Gremlin serves as a query layer for a graph that implements the Tinkerpop graph
structure. As of v2.3.0 Gremlin is in the [Gaffer REST API](./gaffer-docker/gaffer-images.md)
which provides a Websocket based traversal source similar to using a normal
[Gremlin server](https://tinkerpop.apache.org/docs/current/reference/#connecting-gremlin-server).
This is the recommended approach and the easiest way to start using Gremlin on
Gaffer.

If you wish to connect via the Java API, you can utilise the underlying
'GafferPop' library that can be utilised to enable Gremlin queries. This
library can be included via maven in any project using the following dependency
definition:

```xml
<dependency>
    <groupId>uk.gov.gchq.gaffer</groupId>
    <artifactId>tinkerpop</artifactId>
    <version>${gaffer.version}</version>
</dependency>
```

Both methods (REST API and Java API) utilise the same library that allows
Tinkerpop to talk to a Gaffer graph. To actually spawn a Gremlin query a
reference to a `GraphTraversalSource` is required, the following sections
outline how to obtain this reference using the REST API.

## Connecting Gremlin

As mentioned previously the recommended way to use Gremlin queries is via the
Websocket in the Gaffer REST API. To do this you will need to provide a config
file that sets up the Gaffer Tinkerpop library (a.k.a 'GafferPop'). The file can
either be added to `/gaffer/gafferpop.properties` in the container, or at a
 custom path by setting the `gaffer.gafferpop.properties` key in the
`store.properties` file. This file can be blank but it is still recommended to
setup some default values.

!!! tip
    Please see the [section below](#configuring-the-gafferpop-library) on how to
    configure the GafferPop properties file.

Once the GafferPop properties file has been added, if you start the REST API a
Gremlin websocket will be available at `localhost:8080/gremlin` by default.
To connect to this socket you must use the [GraphSON v3](https://tinkerpop.apache.org/docs/current/dev/io/#graphson)
format. Most standard Gremlin tools already default to this however, if
connecting using `gremlinpython` you must set it in the driver connection like:

```python
from gremlin_python.driver.serializer import GraphSONSerializersV3d0

g = traversal().with_remote(DriverRemoteConnection('ws://localhost:8080/gremlin', 'g', message_serializer=GraphSONSerializersV3d0()))
```

## Configuring the GafferPop Library

The `gafferpop.properties`, file is the configuration for GafferPop. If using
the REST API there is no mandatory properties you need to set since you already
will have configured the Graph in the existing `store.properties` file. However,
adding some default values in for operation modifiers, such as a limit for
`GetAllElement` operations, is good practise.

```properties
# Default operation config
gaffer.elements.getalllimit=5000
gaffer.elements.hasstepfilterstage=PRE_AGGREGATION
```

A full breakdown of of the available properties is as follows:

!!! note
    Many of these are for standalone GafferPop Graphs so may be ignored if using
    the REST API.

| Property Key | Description | Used in REST API |
| --- | --- | --- |
| `gremlin.graph` | The Tinkerpop graph class we should use for construction. | No |
| `gaffer.graphId` | The graph ID of the Tinkerpop graph. | No |
| `gaffer.storeproperties` | The path to the store properties file. | No |
| `gaffer.schemas` | The path to the directory containing the graph schema files. | No |
| `gaffer.userId` | The default user ID for the Tinkerpop graph. | No (User is always set via the [`UserFactory`](../security/user-control.md).) |
| `gaffer.dataAuths` | The default data auths for the user to specify what operations can be performed | No |
| `gaffer.operation.options` | Default `Operation` options in the form `key:value` (this can be overridden per query see [here](../../user-guide/query/gremlin/custom-features.md)) | Yes |
| `gaffer.elements.getalllimit` | The default limit for unseeded queries e.g. `g.V()`. | Yes |
| `gaffer.elements.hasstepfilterstage` | The default stage to apply any `has()` steps e.g. `PRE_AGGREGATION` | Yes |
