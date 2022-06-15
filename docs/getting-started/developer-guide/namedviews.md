# NamedViews

The code for this example is [NamedViews](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/dev/walkthrough/NamedViews.java).

This example explains how to configure your Gaffer Graph to allow NamedViews to be used. 
Named views enable encapsulation of a View, or multiple Views, into a new single NamedView.
A NamedView can be used in conjunction with other Views.
There are various possible uses for NamedViews, including:
 * Making it simpler to reuse frequently used Views.
 * Share commonly used Views with other users.
 
In addition to the NamedView there are a set of operations which manage named views (AddNamedView, GetAllNamedViews, DeleteNamedView).

## Configuration
You will need to configure what cache to use for storing NamedViews. For more information on the cache service, see [Cache](cache.md).

Please note; If you want to change the region for NamedViews you can do so by specifying it within the cache.ccf file using region jcs.region.namedViewsRegion.

## Using Named Views
We will now go through some examples using NamedViews.

We will use the same basic schema and data from the first developer walkthrough.

Start by creating your user instance and graph:


{% codetabs name="Java", type="java" -%}
final User user = new User("user01");
{%- endcodetabs %}



{% codetabs name="Java", type="java" -%}
final Graph graph = new Graph.Builder()
        .config(getDefaultGraphConfig())
        .addSchemas(StreamUtil.openStreams(getClass(), schemaPath))
        .storeProperties(getDefaultStoreProperties())
        .build();
{%- endcodetabs %}



Then add a NamedView to the cache with the AddNamedView operation:


{% codetabs name="Java", type="java" -%}
final AddNamedView addNamedView = new AddNamedView.Builder()
        .name("RoadUse edges")
        .description("NamedView to get only RoadUse edges")
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .overwrite(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedView",
  "name" : "RoadUse edges",
  "description" : "NamedView to get only RoadUse edges",
  "view" : {
    "edges" : {
      "RoadUse" : { }
    }
  },
  "overwriteFlag" : false
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.view.AddNamedView",
  "name" : "RoadUse edges",
  "description" : "NamedView to get only RoadUse edges",
  "view" : {
    "edges" : {
      "RoadUse" : { }
    }
  },
  "overwriteFlag" : false
}


{%- language name="Python", type="py" -%}
g.AddNamedView( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="RoadUse" 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  name="RoadUse edges", 
  description="NamedView to get only RoadUse edges", 
  overwrite_flag=False 
)


{%- endcodetabs %}


For the next step we need to create a GetElements operation and use
the NamedView previously added to the cache, using the name specified.


{% codetabs name="Java", type="java" -%}
final GetElements operation = new GetElements.Builder()
        .view(new NamedView.Builder()
                .name("RoadUse edges")
                .build())
        .input(new EntitySeed("10"))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "class" : "uk.gov.gchq.gaffer.data.elementdefinition.view.NamedView",
    "name" : "RoadUse edges"
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "class" : "uk.gov.gchq.gaffer.data.elementdefinition.view.NamedView",
    "name" : "RoadUse edges"
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.NamedView( 
    all_edges=False, 
    all_entities=False, 
    name="RoadUse edges" 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex="10" 
    ) 
  ] 
)


{%- endcodetabs %}



The results are:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Tue May 02 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Tue May 02 00:00:00 PDT 2000]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[endDate=<java.util.Date>Wed May 03 23:59:59 PDT 2000,count=<java.lang.Long>1,startDate=<java.util.Date>Wed May 03 00:00:00 PDT 2000]]

```

NamedViews can also take parameters, allowing values to be configured by the user when the NamedView is used.
When adding a NamedView with parameters the View must be specified as a JSON string, with
parameter names enclosed with '${' and '}'. For each parameter, a ViewParameterDetail object must be created which gives a description, a class type
and an optional default for the Parameter, and also indicates whether the parameter must be provided (ie. there is no default).

The following code adds a NamedView with a 'countIsMoreThan' parameter that allows the minimum edge count for the View to be set, along
with a 'selectionParam' parameter that allows the user to set the selection for the View:


{% codetabs name="Java", type="java" -%}
final String viewJson = "{" +
        "      \"edges\" : {" +
        "        \"RoadUse\" : {  " +
        "           \"preAggregationFilterFunctions\" : [ {" +
        "               \"predicate\" : {" +
        "                   \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsMoreThan\"," +
        "                   \"orEqualTo\" : false," +
        "                     \"value\": {" +
        "                         \"java.lang.Long\" : \"${isMoreThan}\"" +
        "                       }" +
        "                 }," +
        "             \"selection\" : [ \"${property}\" ]" +
        "         } ] }" +
        "      }," +
        "      \"entities\" : { }" +
        "}";
final ViewParameterDetail propertyParam = new ViewParameterDetail.Builder()
        .description("Property to select")
        .valueClass(String.class)
        .required(true)
        .build();

final ViewParameterDetail valueParam = new ViewParameterDetail.Builder()
        .description("Value for the is more than predicate")
        .defaultValue(0L)
        .valueClass(Long.class)
        .build();

final Map<String, ViewParameterDetail> paramDetailMap = Maps.newHashMap();
paramDetailMap.put("property", propertyParam);
paramDetailMap.put("isMoreThan", valueParam);

final AddNamedView addNamedViewWithParams = new AddNamedView.Builder()
        .name("customCountView")
        .description("named View with count param")
        .view(viewJson)
        .parameters(paramDetailMap)
        .overwrite(true)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedView",
  "name" : "customCountView",
  "description" : "named View with count param",
  "view" : {
    "edges" : {
      "RoadUse" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : {
              "java.lang.Long" : "${isMoreThan}"
            }
          },
          "selection" : [ "${property}" ]
        } ]
      }
    },
    "entities" : { }
  },
  "overwriteFlag" : true,
  "parameters" : {
    "property" : {
      "description" : "Property to select",
      "valueClass" : "String",
      "required" : true
    },
    "isMoreThan" : {
      "description" : "Value for the is more than predicate",
      "defaultValue" : 0,
      "valueClass" : "Long",
      "required" : false
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.view.AddNamedView",
  "name" : "customCountView",
  "description" : "named View with count param",
  "view" : {
    "edges" : {
      "RoadUse" : {
        "preAggregationFilterFunctions" : [ {
          "predicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
            "orEqualTo" : false,
            "value" : {
              "java.lang.Long" : "${isMoreThan}"
            }
          },
          "selection" : [ "${property}" ]
        } ]
      }
    },
    "entities" : { }
  },
  "overwriteFlag" : true,
  "parameters" : {
    "property" : {
      "description" : "Property to select",
      "valueClass" : "java.lang.String",
      "required" : true
    },
    "isMoreThan" : {
      "description" : "Value for the is more than predicate",
      "defaultValue" : 0,
      "valueClass" : "java.lang.Long",
      "required" : false
    }
  }
}


{%- language name="Python", type="py" -%}
g.AddNamedView( 
  view=g.View( 
    entities=[ 
    ], 
    edges=[ 
      g.ElementDefinition( 
        group="RoadUse", 
        pre_aggregation_filter_functions=[ 
          g.PredicateContext( 
            selection=[ 
              "${property}" 
            ], 
            predicate=g.IsMoreThan( 
              value={'java.lang.Long': '${isMoreThan}'}, 
              or_equal_to=False 
            ) 
          ) 
        ] 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  name="customCountView", 
  description="named View with count param", 
  overwrite_flag=True, 
  parameters=[ 
    g.NamedViewParameter( 
      name="property", 
      value_class="java.lang.String", 
      description="Property to select", 
      required=True 
    ), 
    g.NamedViewParameter( 
      name="isMoreThan", 
      value_class="java.lang.Long", 
      description="Value for the is more than predicate", 
      default_value=0, 
      required=False 
    ) 
  ] 
)


{%- endcodetabs %}



A view can then be created, with a value provided for the 'countIsMoreThan' and 'selectionParam' parameter:


{% codetabs name="Java", type="java" -%}
final Map<String, Object> paramMap = Maps.newHashMap();
paramMap.put("property", "count");
paramMap.put("isMoreThan", 1L);

final GetElements operationUsingParams = new GetElements.Builder()
        .view(new NamedView.Builder()
                .name("customCountView")
                .parameters(paramMap)
                .build())
        .input(new EntitySeed("10"))
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "class" : "uk.gov.gchq.gaffer.data.elementdefinition.view.NamedView",
    "name" : "customCountView",
    "parameters" : {
      "property" : "count",
      "isMoreThan" : 1
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "class" : "uk.gov.gchq.gaffer.data.elementdefinition.view.NamedView",
    "name" : "customCountView",
    "parameters" : {
      "property" : "count",
      "isMoreThan" : 1
    }
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.NamedView( 
    all_edges=False, 
    all_entities=False, 
    name="customCountView", 
    parameters={'property': 'count', 'isMoreThan': 1} 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex="10" 
    ) 
  ] 
)


{%- endcodetabs %}


giving these results:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[endDate=<java.util.Date>Mon May 01 23:59:59 PDT 2000,count=<java.lang.Long>2,startDate=<java.util.Date>Mon May 01 00:00:00 PDT 2000]]

```

## Security
By default, read access to Named Views is unrestricted and write access is limited to administrators and the Named View creator. More fine-grained controls can be configured using the following options.

### Write Access Roles
Write access to Named Views can be locked down to users who have at least one of the auths listed in the "writeAccessRoles" setting.
This example ensures that writers have the "write-user" auth.


{% codetabs name="Java", type="java" -%}
final AddNamedView addNamedViewWriteAccessRoles = new AddNamedView.Builder()
        .name("RoadUse edges")
        .description("NamedView to get only RoadUse edges")
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .overwrite(true)
        .writeAccessRoles("write-user")
        .build();
{%- endcodetabs %}


### Access Controlled Resource
Named Views implement the AccessControlledResource interface allowing configuration of a custom Predicate which is tested against the User to determine whether they can access the resource.
This example ensures readers of the Named View have both the "read-access-auth-1" and "read-access-auth-2" auths and users attempting to remove the Named View have both the "write-access-auth-1" and "write-access-auth-2" auths.
Note that the "writeAccessPredicate" field is mutually exclusive with the "writeAccessRoles" setting described in the [Write Access Roles](#write-access-roles) section.


{% codetabs name="Java", type="java" -%}
final AddNamedView addNamedViewAccessControlledResource = new AddNamedView.Builder()
        .name("RoadUse edges")
        .description("NamedView to get only RoadUse edges")
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .overwrite(true)
        .readAccessPredicate(
                new AccessPredicate(
                        new AdaptedPredicate(
                                new CallMethod("getOpAuths"),
                                new And(
                                        new CollectionContains("read-access-auth-1"),
                                        new CollectionContains("read-access-auth-2")))))
        .writeAccessPredicate(
                new AccessPredicate(
                        new AdaptedPredicate(
                                new CallMethod("getOpAuths"),
                                new And(
                                        new CollectionContains("write-access-auth-1"),
                                        new CollectionContains("write-access-auth-2")))))
        .build();
{%- endcodetabs %}

