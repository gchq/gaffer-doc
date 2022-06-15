# ExportToOtherGraph
See javadoc - [uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherGraph.html)

Available since Gaffer version 1.0.0

These export examples export all edges in the example graph to another Gaffer instance. 

To add this operation to your Gaffer graph you will need to include the ExportToOtherGraphOperationDeclarations.json in your store properties, i.e. set this property: gaffer.store.operation.declarations=ExportToOtherGraphOperationDeclarations.json


## Required fields
The following fields are required: 
- graphId


## Examples

### Simple export

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the same schema and same store properties as the current graph. In this case it will just create another table in accumulo called 'newGraphId'.


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("newGraphId")
                        .build())
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "ExportToOtherGraph",
    "graphId" : "newGraphId"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "newGraphId"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ) 
    ), 
    g.ExportToOtherGraph( 
      graph_id="newGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Simple export with custom graph

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have the custom provided schema (note it must contain the same Edge group 'edge' otherwise the exported edges will be invalid') and custom store properties. The store properties could be any store properties e.g. Accumulo, HBase, Map, Proxy store properties.


{% codetabs name="Java", type="java" -%}
final Schema schema = Schema.fromJson(StreamUtil.openStreams(getClass(), "operations/schema"));
final StoreProperties storeProperties = StoreProperties.loadStoreProperties(StreamUtil.openStream(getClass(), "othermockaccumulostore.properties"));
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("newGraphId")
                        .schema(schema)
                        .storeProperties(storeProperties)
                        .build())
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "ExportToOtherGraph",
    "graphId" : "newGraphId",
    "schema" : {
      "edges" : {
        "edge" : {
          "description" : "test edge",
          "source" : "int",
          "destination" : "int",
          "directed" : "true",
          "properties" : {
            "count" : "count"
          }
        },
        "edge1" : {
          "source" : "int",
          "destination" : "int",
          "directed" : "true",
          "properties" : {
            "count" : "count"
          }
        }
      },
      "entities" : {
        "entity1" : {
          "vertex" : "int",
          "properties" : {
            "count" : "count"
          }
        },
        "entity" : {
          "description" : "test entity",
          "vertex" : "int",
          "properties" : {
            "count" : "count"
          }
        },
        "cardinality" : {
          "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
          "vertex" : "int",
          "properties" : {
            "edgeGroup" : "set",
            "hllp" : "hllp",
            "count" : "count"
          },
          "groupBy" : [ "edgeGroup" ]
        }
      },
      "types" : {
        "int" : {
          "class" : "Integer",
          "aggregateFunction" : {
            "class" : "Sum"
          }
        },
        "true" : {
          "class" : "Boolean",
          "validateFunctions" : [ {
            "class" : "IsTrue"
          } ]
        },
        "count" : {
          "class" : "Integer",
          "aggregateFunction" : {
            "class" : "Sum"
          }
        },
        "set" : {
          "class" : "TreeSet",
          "aggregateFunction" : {
            "class" : "CollectionConcat"
          }
        },
        "hllp" : {
          "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
          "aggregateFunction" : {
            "class" : "HyperLogLogPlusAggregator"
          },
          "serialiser" : {
            "class" : "HyperLogLogPlusSerialiser"
          }
        }
      }
    },
    "storeProperties" : {
      "accumulo.instance" : "someInstanceName",
      "gaffer.cache.service.class" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService",
      "accumulo.password" : "password",
      "accumulo.zookeepers" : "aZookeeper",
      "gaffer.store.class" : "uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore",
      "gaffer.store.job.tracker.enabled" : "true",
      "gaffer.store.operation.declarations" : "ExportToOtherGraphOperationDeclarations.json",
      "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.accumulostore.AccumuloProperties",
      "accumulo.user" : "user01"
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "newGraphId",
    "schema" : {
      "edges" : {
        "edge" : {
          "description" : "test edge",
          "source" : "int",
          "destination" : "int",
          "directed" : "true",
          "properties" : {
            "count" : "count"
          }
        },
        "edge1" : {
          "source" : "int",
          "destination" : "int",
          "directed" : "true",
          "properties" : {
            "count" : "count"
          }
        }
      },
      "entities" : {
        "entity1" : {
          "vertex" : "int",
          "properties" : {
            "count" : "count"
          }
        },
        "entity" : {
          "description" : "test entity",
          "vertex" : "int",
          "properties" : {
            "count" : "count"
          }
        },
        "cardinality" : {
          "description" : "An entity that is added to every vertex representing the connectivity of the vertex.",
          "vertex" : "int",
          "properties" : {
            "edgeGroup" : "set",
            "hllp" : "hllp",
            "count" : "count"
          },
          "groupBy" : [ "edgeGroup" ]
        }
      },
      "types" : {
        "int" : {
          "class" : "java.lang.Integer",
          "aggregateFunction" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
          }
        },
        "true" : {
          "class" : "java.lang.Boolean",
          "validateFunctions" : [ {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
          } ]
        },
        "count" : {
          "class" : "java.lang.Integer",
          "aggregateFunction" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
          }
        },
        "set" : {
          "class" : "java.util.TreeSet",
          "aggregateFunction" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
          }
        },
        "hllp" : {
          "class" : "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
          "aggregateFunction" : {
            "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
          },
          "serialiser" : {
            "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
          }
        }
      }
    },
    "storeProperties" : {
      "accumulo.instance" : "someInstanceName",
      "gaffer.cache.service.class" : "uk.gov.gchq.gaffer.cache.impl.HashMapCacheService",
      "accumulo.password" : "password",
      "accumulo.zookeepers" : "aZookeeper",
      "gaffer.store.class" : "uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore",
      "gaffer.store.job.tracker.enabled" : "true",
      "gaffer.store.operation.declarations" : "ExportToOtherGraphOperationDeclarations.json",
      "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.accumulostore.AccumuloProperties",
      "accumulo.user" : "user01"
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ) 
    ), 
    g.ExportToOtherGraph( 
      graph_id="newGraphId", 
      schema={'edges': {'edge': {'description': 'test edge', 'source': 'int', 'destination': 'int', 'directed': 'true', 'properties': {'count': 'count'}}, 'edge1': {'source': 'int', 'destination': 'int', 'directed': 'true', 'properties': {'count': 'count'}}}, 'entities': {'entity1': {'vertex': 'int', 'properties': {'count': 'count'}}, 'entity': {'description': 'test entity', 'vertex': 'int', 'properties': {'count': 'count'}}, 'cardinality': {'description': 'An entity that is added to every vertex representing the connectivity of the vertex.', 'vertex': 'int', 'properties': {'edgeGroup': 'set', 'hllp': 'hllp', 'count': 'count'}, 'groupBy': ['edgeGroup']}}, 'types': {'int': {'class': 'java.lang.Integer', 'aggregateFunction': {"class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"}}, 'true': {'class': 'java.lang.Boolean', 'validateFunctions': [{"class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"}]}, 'count': {'class': 'java.lang.Integer', 'aggregateFunction': {"class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"}}, 'set': {'class': 'java.util.TreeSet', 'aggregateFunction': {'class': 'uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat'}}, 'hllp': {'class': 'com.clearspring.analytics.stream.cardinality.HyperLogLogPlus', 'aggregateFunction': {'class': 'uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator'}, 'serialiser': {'class': 'uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser'}}}}, 
      store_properties={'accumulo.instance': 'someInstanceName', 'gaffer.cache.service.class': 'uk.gov.gchq.gaffer.cache.impl.HashMapCacheService', 'accumulo.password': 'password', 'accumulo.zookeepers': 'aZookeeper', 'gaffer.store.class': 'uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore', 'gaffer.store.job.tracker.enabled': 'true', 'gaffer.store.operation.declarations': 'ExportToOtherGraphOperationDeclarations.json', 'gaffer.store.properties.class': 'uk.gov.gchq.gaffer.accumulostore.AccumuloProperties', 'accumulo.user': 'user01'} 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Simple to other gaffer rest api

This example will export all Edges with group 'edge' to another Gaffer REST API.To export to another Gaffer REST API, we go via a Gaffer Proxy Store. We just need to tell the proxy store the host, port and context root of the REST API.Note that you will need to include the proxy-store module as a maven dependency to do this.


{% codetabs name="Java", type="java" -%}
final ProxyProperties proxyProperties = new ProxyProperties();
proxyProperties.setStoreClass(ProxyStore.class);
proxyProperties.setStorePropertiesClass(ProxyProperties.class);
proxyProperties.setGafferHost("localhost");
proxyProperties.setGafferPort(8081);
proxyProperties.setGafferContextRoot("/rest/v1");

final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("otherGafferRestApiGraphId")
                        .storeProperties(proxyProperties)
                        .build())
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "ExportToOtherGraph",
    "graphId" : "otherGafferRestApiGraphId",
    "storeProperties" : {
      "gaffer.host" : "localhost",
      "gaffer.context-root" : "/rest/v1",
      "gaffer.store.class" : "uk.gov.gchq.gaffer.proxystore.ProxyStore",
      "gaffer.port" : "8081",
      "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.proxystore.ProxyProperties"
    }
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "otherGafferRestApiGraphId",
    "storeProperties" : {
      "gaffer.host" : "localhost",
      "gaffer.context-root" : "/rest/v1",
      "gaffer.store.class" : "uk.gov.gchq.gaffer.proxystore.ProxyStore",
      "gaffer.port" : "8081",
      "gaffer.store.properties.class" : "uk.gov.gchq.gaffer.proxystore.ProxyProperties"
    }
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ) 
    ), 
    g.ExportToOtherGraph( 
      graph_id="otherGafferRestApiGraphId", 
      store_properties={'gaffer.host': 'localhost', 'gaffer.context-root': '/rest/v1', 'gaffer.store.class': 'uk.gov.gchq.gaffer.proxystore.ProxyStore', 'gaffer.port': '8081', 'gaffer.store.properties.class': 'uk.gov.gchq.gaffer.proxystore.ProxyProperties'} 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Simple export using graph from graph library

This example will export all Edges with group 'edge' to another existing graph 'exportGraphId' using a GraphLibrary.We demonstrate here that if we use a GraphLibrary, we can register a graph ID and reference it from the export operation. This means the user does not have to proxy all the schema and store properties when they configure the export operation, they can just provide the ID.


{% codetabs name="Java", type="java" -%}
// Setup the graphLibrary with an export graph
final GraphLibrary graphLibrary = new FileGraphLibrary("target/graphLibrary");

final AccumuloProperties exportStoreProperties = new AccumuloProperties();
// set other store property config here.

final Schema exportSchema = new Schema.Builder()
        .edge("edge", new SchemaEdgeDefinition.Builder()
                .source("int")
                .destination("int")
                .directed("true")
                .property("count", "int")
                .aggregate(false)
                .build())
        .type("int", Integer.class)
        .type("true", new TypeDefinition.Builder()
                .clazz(Boolean.class)
                .validateFunctions(new IsTrue())
                .build())
        .build();

graphLibrary.addOrUpdate("exportGraphId", exportSchema, exportStoreProperties);

final Graph graph = new Graph.Builder()
        .config(StreamUtil.openStream(getClass(), "graphConfigWithLibrary.json"))
        .addSchemas(StreamUtil.openStreams(getClass(), "operations/schema"))
        .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
        .build();

final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("exportGraphId")
                        .build())
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "ExportToOtherGraph",
    "graphId" : "exportGraphId"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "exportGraphId"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ) 
    ), 
    g.ExportToOtherGraph( 
      graph_id="exportGraphId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Export to new graph based on config from graph library

Similar to the previous example, this example will export all Edges with group 'edge' to another graph using a GraphLibrary. But in this example we show that you can export to a new graph with id newGraphId by choosing any combination of schema and store properties registered in the GraphLibrary. This is useful as a system administrator could register various different store properties, of different Accumulo/HBase clusters and a user could them just select which one to use by referring to the relevant store properties ID.


{% codetabs name="Java", type="java" -%}
// Setup the graphLibrary with a schema and store properties for exporting
final GraphLibrary graphLibrary = new FileGraphLibrary("target/graphLibrary");

final AccumuloProperties exportStoreProperties = new AccumuloProperties();
// set other store property config here.
graphLibrary.addProperties("exportStorePropertiesId", exportStoreProperties);

final Schema exportSchema = new Schema.Builder()
        .edge("edge", new SchemaEdgeDefinition.Builder()
                .source("int")
                .destination("int")
                .directed("true")
                .property("count", "int")
                .aggregate(false)
                .build())
        .type("int", Integer.class)
        .type("true", new TypeDefinition.Builder()
                .clazz(Boolean.class)
                .validateFunctions(new IsTrue())
                .build())
        .build();
graphLibrary.addSchema("exportSchemaId", exportSchema);

final Graph graph = new Graph.Builder()
        .config(StreamUtil.openStream(getClass(), "graphConfigWithLibrary.json"))
        .addSchemas(StreamUtil.openStreams(getClass(), "operations/schema"))
        .storeProperties(StreamUtil.openStream(getClass(), "mockaccumulostore.properties"))
        .build();

final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherGraph.Builder()
                        .graphId("newGraphId")
                        .parentSchemaIds("exportSchemaId")
                        .parentStorePropertiesId("exportStorePropertiesId")
                        .build())
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "ExportToOtherGraph",
    "graphId" : "newGraphId",
    "parentSchemaIds" : [ "exportSchemaId" ],
    "parentStorePropertiesId" : "exportStorePropertiesId"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements",
    "view" : {
      "edges" : {
        "edge" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherGraph",
    "graphId" : "newGraphId",
    "parentSchemaIds" : [ "exportSchemaId" ],
    "parentStorePropertiesId" : "exportStorePropertiesId"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="edge" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ) 
    ), 
    g.ExportToOtherGraph( 
      graph_id="newGraphId", 
      parent_schema_ids=[ 
        "exportSchemaId" 
      ], 
      parent_store_properties_id="exportStorePropertiesId" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

