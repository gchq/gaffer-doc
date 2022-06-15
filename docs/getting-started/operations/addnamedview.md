# AddNamedView
See javadoc - [uk.gov.gchq.gaffer.named.view.AddNamedView](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/named/view/AddNamedView.html)

Available since Gaffer version 1.3.0

See [NamedViews](../developer-guide/namedviews.md) for information on configuring NamedViews for your Gaffer graph, along with working examples.

## Required fields
The following fields are required: 
- name
- view


## Examples

### Add named view


{% codetabs name="Java", type="java" -%}
final AddNamedView op = new AddNamedView.Builder()
        .name("isMoreThan10")
        .description("example test NamedView")
        .overwrite(true)
        .view(new View.Builder()
                .edge("testEdge", new ViewElementDefinition.Builder()
                        .preAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(10))
                                .build())
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedView",
  "name" : "isMoreThan10",
  "description" : "example test NamedView",
  "view" : {
    "edges" : {
      "testEdge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 10
          }
        } ]
      }
    }
  },
  "overwriteFlag" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.view.AddNamedView",
  "name" : "isMoreThan10",
  "description" : "example test NamedView",
  "view" : {
    "edges" : {
      "testEdge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : 10
          }
        } ]
      }
    }
  },
  "overwriteFlag" : true
}

{%- language name="Python", type="py" -%}
g.AddNamedView( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="testEdge", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value=10, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  name="isMoreThan10", 
  description="example test NamedView", 
  overwrite_flag=True 
)

{%- endcodetabs %}

-----------------------------------------------

### Add named view with parameter


{% codetabs name="Java", type="java" -%}
final String viewJson = "{\"edges\" : {\n" +
        "  \"testEdge\" : {\n" +
        "    \"preAggregationFilterFunctions\" : [ {\n" +
        "      \"selection\" : [ \"count\" ],\n" +
        "      \"predicate\" : {\n" +
        "        \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsMoreThan\",\n" +
        "        \"orEqualTo\" : false,\n" +
        "        \"value\" : \"${countThreshold}\"\n" +
        "      }\n" +
        "    } ]\n" +
        "  }\n" +
        "}}";
final ViewParameterDetail param = new ViewParameterDetail.Builder()
        .defaultValue(1L)
        .description("count threshold")
        .valueClass(Long.class)
        .build();
final Map<String, ViewParameterDetail> paramMap = Maps.newHashMap();
paramMap.put("countThreshold", param);

final AddNamedView op = new AddNamedView.Builder()
        .name("isMoreThan")
        .description("example test NamedView")
        .overwrite(true)
        .view(viewJson)
        .parameters(paramMap)
        .writeAccessRoles("auth1")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedView",
  "name" : "isMoreThan",
  "description" : "example test NamedView",
  "view" : {
    "edges" : {
      "testEdge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : "${countThreshold}"
          }
        } ]
      }
    }
  },
  "overwriteFlag" : true,
  "parameters" : {
    "countThreshold" : {
      "description" : "count threshold",
      "defaultValue" : 1,
      "valueClass" : "Long",
      "required" : false
    }
  },
  "writeAccessRoles" : [ "auth1" ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.view.AddNamedView",
  "name" : "isMoreThan",
  "description" : "example test NamedView",
  "view" : {
    "edges" : {
      "testEdge" : {
        "preAggregationFilterFunctions" : [ {
          "selection" : [ "count" ],
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : "${countThreshold}"
          }
        } ]
      }
    }
  },
  "overwriteFlag" : true,
  "parameters" : {
    "countThreshold" : {
      "description" : "count threshold",
      "defaultValue" : 1,
      "valueClass" : "java.lang.Long",
      "required" : false
    }
  },
  "writeAccessRoles" : [ "auth1" ]
}

{%- language name="Python", type="py" -%}
g.AddNamedView( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="testEdge", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "count" 
            ], 
            predicate=g.IsMoreThan( 
              value="${countThreshold}", 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  name="isMoreThan", 
  description="example test NamedView", 
  overwrite_flag=True, 
  write_access_roles=[ 
    "auth1" 
  ], 
  parameters=[ 
    g.NamedViewParameter( 
      name="countThreshold", 
      value_class="java.lang.Long", 
      description="count threshold", 
      default_value=1, 
      required=False 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

