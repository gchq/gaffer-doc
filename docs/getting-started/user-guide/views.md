# Views

The code for this example is [Views](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Views.java).

In previous sections we have talked about Filtering, Aggregation and Transformations.
This section just goes into a bit more information about what a View actually is and some of the other features of a View.

When running an Operation, for example GetElements, you can use a [View](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/View.html) to filter, aggregate, transform and just generally manipulate the results.
As with all parts of Gaffer, the View is quite generic and quite powerful, but can be tricky to get going with.
You can use any of the predefined Functions and Predicates or reference you own (as long as they are on the classpath).

There are quite a few different parts to a View, these are:

- Filtering - for filtering entire elements out based on predicates. Filtering can be applied: pre aggregation, post aggregation and post transformation.

- Aggregation - for controlling if and how similar elements are aggregated together. You can provide a subset of the schema groupBy properties and override the aggregation functions.

- Transformation - for transforming elements. This is applied by providing Functions to transform properties and vertex values. You can override the existing values or you can transform and project the new value into a new transient property.

- Removing properties - for defining which properties you want to be returned. You can use either 'properties' or 'excludeProperties' to define the list of properties to be included or excluded.


If you don't provide a View (or you provide an empty view with no groups) when
executing an operation you will just get all elements back without any modifications.
When creating a View the first thing to do is decide which Edges and Entities you would like to be returned.
Let's assume you want RoadUse, RoadHasJunction, Cardinality. If you provide a view it would look like this:


{% codetabs name="Java", type="java" -%}
final View viewWithGroups = new View.Builder()
        .edge("RoadUse")
        .edge("RoadHasJunction")
        .entity("Cardinality")
        .build();

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "RoadHasJunction" : { },
    "RoadUse" : { }
  },
  "entities" : {
    "Cardinality" : { }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "edges" : {
    "RoadHasJunction" : { },
    "RoadUse" : { }
  },
  "entities" : {
    "Cardinality" : { }
  }
}


{%- language name="Python", type="py" -%}
g.View( 
  entities=[ 
    g.ElementDefinition( 
      group="Cardinality" 
    ) 
  ], 
  edges=[ 
    g.ElementDefinition( 
      group="RoadHasJunction" 
    ), 
    g.ElementDefinition( 
      group="RoadUse" 
    ) 
  ], 
  all_edges=False, 
  all_entities=False 
)


{%- endcodetabs %}


It is important to use the correct element type when listing the elements.
RoadUse is an Edge group, so we include it in the View as an Edge. Whereas Cardinality
is an Entity so we include it as an Entity.

As seen in the Filtering walkthrough we can then apply filters to the different element groups using a [ViewElementDefinition](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/ViewElementDefinition.html).
Below you can see we have added a post aggregation filter to the element groups.
This demonstrates how you can add different filters to different groups and how to add multiple filters.


{% codetabs name="Java", type="java" -%}
final View viewWithFilters = new View.Builder()
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(2L))
                        .build())
                .build())
        .entity("Cardinality", new ViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(5L))
                        .select("hllp")
                        .execute(new HyperLogLogPlusIsLessThan(10L))
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "Long" : 2
          }
        }
      } ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "Long" : 5
          }
        }
      }, {
        "selection" : [ "hllp" ],
        "predicate" : {
          "class" : "HyperLogLogPlusIsLessThan",
          "orEqualTo" : false,
          "value" : 10
        }
      } ]
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "java.lang.Long" : 2
          }
        }
      } ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "java.lang.Long" : 5
          }
        }
      }, {
        "selection" : [ "hllp" ],
        "predicate" : {
          "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan",
          "orEqualTo" : false,
          "value" : 10
        }
      } ]
    }
  }
}


{%- language name="Python", type="py" -%}
{%- endcodetabs %}


We could just as easily add pre aggregation filters using "preAggregationFilter" instead.
It is important to think about the stage at which you are applying your filtering.
Pre aggregation filters are useful when you want to apply a filter to a property
before it is aggregated, like time window filtering. However, post aggregation
filtering is required for properties like a count, when you want to filter based
on the fully summarised value after query time aggregation has happened.


If you are only interested in specific properties then it is more efficient to tell Gaffer to only return those properties.
This can be easily achieved using the 'properties' or 'excludeProperties' field.
For example:


{% codetabs name="Java", type="java" -%}
final View viewWithRemovedProperties = new View.Builder()
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .properties("count")
                .build())
        .entity("Cardinality", new ViewElementDefinition.Builder()
                .excludeProperties("hllp", "edgeGroups")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "properties" : [ "count" ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "excludeProperties" : [ "hllp", "edgeGroups" ]
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "properties" : [ "count" ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "excludeProperties" : [ "hllp", "edgeGroups" ]
    }
  }
}


{%- language name="Python", type="py" -%}
{%- endcodetabs %}


## Global view definitions

If you want to set the same filters on multiple groups you can use a global view definition.
When specifying global view definitions you can choose from:

- globalElements - these are applied to all edges and entities
- globalEdges - these are applied to all edges
- globalEntities - these are applied to all entities

If we want to return all elements that have a count more than 2 we can do this:


{% codetabs name="Java", type="java" -%}
final View viewWithGlobalFilter = new View.Builder()
        .globalElements(new GlobalViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(2L))
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "globalElements" : [ {
    "postAggregationFilterFunctions" : [ {
      "selection" : [ "count" ],
      "predicate" : {
        "class" : "IsMoreThan",
        "orEqualTo" : false,
        "value" : {
          "Long" : 2
        }
      }
    } ]
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "globalElements" : [ {
    "postAggregationFilterFunctions" : [ {
      "selection" : [ "count" ],
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
        "orEqualTo" : false,
        "value" : {
          "java.lang.Long" : 2
        }
      }
    } ]
  } ]
}


{%- language name="Python", type="py" -%}
g.View( 
  global_elements=[ 
    g.GlobalElementDefinition( 
      post_aggregation_filter_functions=[ 
        g.PredicateContext( 
          selection=[ 
            "count" 
          ], 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 2}, 
            or_equal_to=False 
          ) 
        ) 
      ] 
    ) 
  ], 
  all_edges=False, 
  all_entities=False 
)


{%- endcodetabs %}


In addition to global definitions you can add specific view definitions to different element groups as we have done previously.
The global definitions will just get merged with each of the element definitions.
Filters are merged together using an AND operator. For example:


{% codetabs name="Java", type="java" -%}
final View globalAndSpecificView = new View.Builder()
        .globalElements(new GlobalViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(0L))
                        .build())
                .build())
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(2L))
                        .build())
                .build())
        .entity("Cardinality")
        .build();

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "Long" : 2
          }
        }
      } ]
    }
  },
  "entities" : {
    "Cardinality" : { }
  },
  "globalElements" : [ {
    "postAggregationFilterFunctions" : [ {
      "selection" : [ "count" ],
      "predicate" : {
        "class" : "IsMoreThan",
        "orEqualTo" : false,
        "value" : {
          "Long" : 0
        }
      }
    } ]
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "java.lang.Long" : 2
          }
        }
      } ]
    }
  },
  "entities" : {
    "Cardinality" : { }
  },
  "globalElements" : [ {
    "postAggregationFilterFunctions" : [ {
      "selection" : [ "count" ],
      "predicate" : {
        "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
        "orEqualTo" : false,
        "value" : {
          "java.lang.Long" : 0
        }
      }
    } ]
  } ]
}


{%- language name="Python", type="py" -%}
g.View( 
  entities=[ 
    g.ElementDefinition( 
      group="Cardinality" 
    ) 
  ], 
  edges=[ 
    g.ElementDefinition( 
      group="RoadUse", 
      post_aggregation_filter_functions=[ 
        g.PredicateContext( 
          selection=[ 
            "count" 
          ], 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 2}, 
            or_equal_to=False 
          ) 
        ) 
      ] 
    ) 
  ], 
  global_elements=[ 
    g.GlobalElementDefinition( 
      post_aggregation_filter_functions=[ 
        g.PredicateContext( 
          selection=[ 
            "count" 
          ], 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 0}, 
            or_equal_to=False 
          ) 
        ) 
      ] 
    ) 
  ], 
  all_edges=False, 
  all_entities=False 
)


{%- endcodetabs %}


when this is expanded out by Gaffer automatically it would become:


{% codetabs name="Java", type="java" -%}
final View globalAndSpecificViewExpanded = new View.Builder()
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(2L))
                        .select("count")
                        .execute(new IsMoreThan(0L))
                        .build())
                .build())
        .entity("Cardinality", new ViewElementDefinition.Builder()
                .postAggregationFilter(new ElementFilter.Builder()
                        .select("count")
                        .execute(new IsMoreThan(0L))
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "Long" : 2
          }
        }
      }, {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "Long" : 0
          }
        }
      } ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "Long" : 0
          }
        }
      } ]
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "java.lang.Long" : 2
          }
        }
      }, {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "java.lang.Long" : 0
          }
        }
      } ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "postAggregationFilterFunctions" : [ {
        "selection" : [ "count" ],
        "predicate" : {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo" : false,
          "value" : {
            "java.lang.Long" : 0
          }
        }
      } ]
    }
  }
}


{%- language name="Python", type="py" -%}
g.View( 
  entities=[ 
    g.ElementDefinition( 
      group="Cardinality", 
      post_aggregation_filter_functions=[ 
        g.PredicateContext( 
          selection=[ 
            "count" 
          ], 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 0}, 
            or_equal_to=False 
          ) 
        ) 
      ] 
    ) 
  ], 
  edges=[ 
    g.ElementDefinition( 
      group="RoadUse", 
      post_aggregation_filter_functions=[ 
        g.PredicateContext( 
          selection=[ 
            "count" 
          ], 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 2}, 
            or_equal_to=False 
          ) 
        ), 
        g.PredicateContext( 
          selection=[ 
            "count" 
          ], 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 0}, 
            or_equal_to=False 
          ) 
        ) 
      ] 
    ) 
  ], 
  all_edges=False, 
  all_entities=False 
)


{%- endcodetabs %}


Global aggregations and transformations work in a similar way to filtering.

Properties can also be included/excluded globally, using a similar method to the above for filtering.

If we only want to return the `"count"` property for all elements this can be done like:


{% codetabs name="Java", type="java" -%}
final View viewWithGlobalRemovedProperties = new View.Builder()
        .globalElements(new GlobalViewElementDefinition.Builder()
                .properties("count")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "globalElements" : [ {
    "properties" : [ "count" ]
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "globalElements" : [ {
    "properties" : [ "count" ]
  } ]
}


{%- language name="Python", type="py" -%}
g.View( 
  global_elements=[ 
    g.GlobalElementDefinition( 
      properties=[ 
        "count" 
      ] 
    ) 
  ], 
  all_edges=False, 
  all_entities=False 
)


{%- endcodetabs %}


Again, you can override the global included properties field for a specific element group:


{% codetabs name="Java", type="java" -%}
final View viewWithGlobalAndSpecificRemovedProperties = new View.Builder()
        .globalElements(new GlobalViewElementDefinition.Builder()
                .properties("count")
                .build())
        .edge("RoadUse")
        .entity("Cardinality", new ViewElementDefinition.Builder()
                .properties("hllp")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : { }
  },
  "entities" : {
    "Cardinality" : {
      "properties" : [ "hllp" ]
    }
  },
  "globalElements" : [ {
    "properties" : [ "count" ]
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : { }
  },
  "entities" : {
    "Cardinality" : {
      "properties" : [ "hllp" ]
    }
  },
  "globalElements" : [ {
    "properties" : [ "count" ]
  } ]
}


{%- language name="Python", type="py" -%}
g.View( 
  entities=[ 
    g.ElementDefinition( 
      group="Cardinality", 
      properties=[ 
        "hllp" 
      ] 
    ) 
  ], 
  edges=[ 
    g.ElementDefinition( 
      group="RoadUse" 
    ) 
  ], 
  global_elements=[ 
    g.GlobalElementDefinition( 
      properties=[ 
        "count" 
      ] 
    ) 
  ], 
  all_edges=False, 
  all_entities=False 
)


{%- endcodetabs %}


when this is expanded out by Gaffer automatically it would become:


{% codetabs name="Java", type="java" -%}
final View viewWithGlobalAndSpecificRemovedPropertiesExpanded = new View.Builder()
        .edge("RoadUse", new ViewElementDefinition.Builder()
                .properties("count")
                .build())
        .entity("Cardinality", new ViewElementDefinition.Builder()
                .properties("hllp")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "properties" : [ "count" ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "properties" : [ "hllp" ]
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "edges" : {
    "RoadUse" : {
      "properties" : [ "count" ]
    }
  },
  "entities" : {
    "Cardinality" : {
      "properties" : [ "hllp" ]
    }
  }
}


{%- language name="Python", type="py" -%}
g.View( 
  entities=[ 
    g.ElementDefinition( 
      group="Cardinality", 
      properties=[ 
        "hllp" 
      ] 
    ) 
  ], 
  edges=[ 
    g.ElementDefinition( 
      group="RoadUse", 
      properties=[ 
        "count" 
      ] 
    ) 
  ], 
  all_edges=False, 
  all_entities=False 
)


{%- endcodetabs %}


Global exclude properties work in the same way as global properties.
