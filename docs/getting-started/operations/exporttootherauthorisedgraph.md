# ExportToOtherAuthorisedGraph
See javadoc - [uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherAuthorisedGraph](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/export/graph/ExportToOtherAuthorisedGraph.html)

Available since Gaffer version 1.0.0

These export examples export all edges in the example graph to another Gaffer instance using Operation Auths against the user. 

To add this operation to your Gaffer graph you will need to write your own version of [ExportToOtherAuthorisedGraphOperationDeclarations.json](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/road-traffic-rest/src/main/resources/ExportToOtherAuthorisedGraphOperationDeclarations.json) containing the user auths, and then set this property: gaffer.store.operation.declarations=/path/to/ExportToOtherAuthorisedGraphOperationDeclarations.json


## Required fields
The following fields are required: 
- graphId


## Examples

### Export to preconfigured graph

This example will export all Edges with group 'edge' to another Gaffer graph with ID 'graph2'. The graph will be loaded from the configured GraphLibrary, so it must already exist. In order to export to graph2 the user must have the required user authorisations that were configured for this operation.


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherAuthorisedGraph.Builder()
                        .graphId("graph2")
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
    "class" : "ExportToOtherAuthorisedGraph",
    "graphId" : "graph2"
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
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherAuthorisedGraph",
    "graphId" : "graph2"
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
    g.ExportToOtherAuthorisedGraph( 
      graph_id="graph2" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Export to new graph using preconfigured schema and properties

This example will export all Edges with group 'edge' to another Gaffer graph with new ID 'newGraphId'. The new graph will have a parent Schema and Store Properties within the graph library specified by the ID's schemaId1 and storePropsId1. In order to export to newGraphId with storePropsId1 and schemaId1 the user must have the required user authorisations that were configured for this operation to use each of these 3 ids.


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends Element>> opChain =
        new OperationChain.Builder()
                .first(new GetAllElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new ExportToOtherAuthorisedGraph.Builder()
                        .graphId("newGraphId")
                        .parentStorePropertiesId("storePropsId1")
                        .parentSchemaIds("schemaId1")
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
    "class" : "ExportToOtherAuthorisedGraph",
    "graphId" : "newGraphId",
    "parentSchemaIds" : [ "schemaId1" ],
    "parentStorePropertiesId" : "storePropsId1"
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
    "class" : "uk.gov.gchq.gaffer.operation.export.graph.ExportToOtherAuthorisedGraph",
    "graphId" : "newGraphId",
    "parentSchemaIds" : [ "schemaId1" ],
    "parentStorePropertiesId" : "storePropsId1"
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
    g.ExportToOtherAuthorisedGraph( 
      graph_id="newGraphId", 
      parent_schema_ids=[ 
        "schemaId1" 
      ], 
      parent_store_properties_id="storePropsId1" 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

