# Filtering

The code for this example is [Filtering](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/Filtering.java).

Filtering in Gaffer is designed so it can be applied server side and distributed across a cluster for performance.

In this example we’ll query for some Edges and filter the results based on the aggregated value of a property. 
We will use the same schema and data as the previous example.

If we want to query for the RoadUse Edges containing vertex `”10”` the operation would look like this:


{% codetabs name="Java", type="java" -%}
final GetElements getElements = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(new View.Builder()
                .edge("RoadUse")
                .build())
        .build();
final CloseableIterable<? extends Element> results = graph.execute(getElements, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
    "edges" : {
      "RoadUse" : { }
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
    "edges" : {
      "RoadUse" : { }
    }
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="RoadUse" 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex="10" 
    ) 
  ] 
)


{%- endcodetabs %}


Here are the result Edges with the counts aggregated:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]
Edge[source=11,destination=10,directed=true,matchedVertex=DESTINATION,group=RoadUse,properties=Properties[count=<java.lang.Long>1]]

```

Now let’s look at how to filter which Edges are returned based on the aggregated value of their count.
For example, only return Edges containing vertex `”10”` where the `”count”` > 2.

We do this using a [View](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/View.html) and [ViewElementDefinition](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/data/elementdefinition/view/ViewElementDefinition.html) like this:


{% codetabs name="Java", type="java" -%}
final GetElements getEdgesWithCountMoreThan2 = new GetElements.Builder()
        .input(new EntitySeed("10"))
        .view(new View.Builder()
                .edge("RoadUse", new ViewElementDefinition.Builder()
                        .postAggregationFilter(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2L))
                                .build())
                        .build())
                .build())
        .build();
final CloseableIterable<? extends Element> filteredResults = graph.execute(getEdgesWithCountMoreThan2, user);

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "10"
  } ],
  "view" : {
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
    }
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
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
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex="10" 
    ) 
  ] 
)


{%- endcodetabs %}


Our ViewElementDefinition allows us to perform post Aggregation filtering using an IsMoreThan Predicate.

Querying with our view, we now get only those vertex `”10”` Edges where the `”count”` > 2:

```
Edge[source=10,destination=11,directed=true,matchedVertex=SOURCE,group=RoadUse,properties=Properties[count=<java.lang.Long>3]]

```

In the filter, we selected the `count` property. This extracts the value of the `count` property and passes it to the IsMoreThan Predicate.
We can choose to select any property or one of the following identifiers:
- VERTEX - this is the vertex on an Entity
- SOURCE - this is the source vertex on an Edge
- DESTINATION - this is the destination vertex on an Edge
- DIRECTED - this is the directed field on an Edge
- MATCHED_VERTEX - this is the vertex that was matched in the query, either the SOURCE or the DESTINATION
- ADJACENT_MATCHED_VERTEX - this is the adjacent vertex that was matched in the query, either the SOURCE or the DESTINATION. I.e if your seed matches the source of the edge this would resolve to the DESTINATION value.

We chose to use the IsMoreThan Predicate, however the full list of our core Predicates are documented in [Predicates](../predicates/contents.md).
You can also write your own Predicate implementations and include them on the class path.
When choosing a Predicate you must ensure your input selection (the property and identifier types) match the Predicate input types.
For example the IsMoreThan Predicate accepts a single Comparable value. Whereas the IsXMoreThanY Predicate accepts 2 Comparable values.
The Predicate inputs are also documented within the Predicate examples documentation.

For more information on Views and filtering, see [Views](views.md).

## Additional Filtering
In addition to filtering using a View, there are extra filters that can be applied to specific operations.

### directedType
GetElements, GetAllElements and GetAdjacentIds have a 'directedType' field that you can configure,
telling Gaffer that you only want edges that are DIRECTED, UNDIRECTED or EITHER. 

The default value is EITHER.

### includeIncomingOutGoing
GetElements and GetAdjacentIds have an 'includeIncomingOutGoing' field that you can configure,
telling Gaffer that you only want edges that are OUTGOING, INCOMING, or EITHER, in relation to your seed.
This is only applicable to directed edges.

The default value is EITHER.

For example if you have edges:
- A - B
- B - C
- D -> B
- B -> E
- F - G
- H -> I

and you provide a seed B, then:

- OUTGOING would only get back A - B, B - C and B -> E.
- INCOMING would only get back A - B, B - C and D -> B.
- EITHER would get back all edges that have a B vertex.


### seedMatching
GetElements has a 'seedMatching' field that you can configure,
telling Gaffer that you only want edges that are EQUAL or RELATED to your seeds.

The default value is RELATED.

EQUAL will only return Entities and Edges with identifiers that match the seed exactly.
- if you provide an Entity seed, you will only get back Entities have that the same vertex value.
- if you provide an Edge seed, you will only get back Edges that have the same source, destination and directed values.

RELATED will return the EQUAL results (as above) and additional Entities and Edges:
- if you provide an Entity seed, you will also get back Edges that have have the same vertex value as source or destination.
- if you provide an Edge seed, you will also get back Entities that have have the same source or destination vertices.

As the seedMatching flag has now been deprecated, to run equivalent Operations, there are some examples below.  As the default for 
seedMatching is RELATED, if that is currently used nothing will need to change.
There is one limitation however, if you have a seedMatching = EQUAL and specify both Edges and Entities that will have to now be
done under 2 Operations in a chain as there can only be one View applied globally to all input.

OLD:

{% codetabs name="Java", type="java" -%}
final GetElements getEdgesWithSeedMatching = new GetElements.Builder()
        .input(new EdgeSeed("source", "dest", true))
        .seedMatching(SeedMatching.SeedMatchingType.EQUAL)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EdgeSeed",
    "source" : "source",
    "destination" : "dest",
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  } ],
  "seedMatching" : "EQUAL"
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : "source",
    "destination" : "dest",
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  } ],
  "seedMatching" : "EQUAL"
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EdgeSeed( 
      source="source", 
      destination="dest", 
      directed_type="DIRECTED", 
      matched_vertex="SOURCE" 
    ) 
  ], 
  seed_matching="EQUAL" 
)


{%- endcodetabs %}


NEW:

{% codetabs name="Java", type="java" -%}
final GetElements getEdgesWithoutSeedMatching = new GetElements.Builder()
        .input(new EdgeSeed("source", "dest", true))
        .view(new View.Builder()
                .edge("group1")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EdgeSeed",
    "source" : "source",
    "destination" : "dest",
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  } ],
  "view" : {
    "edges" : {
      "group1" : { }
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EdgeSeed",
    "source" : "source",
    "destination" : "dest",
    "matchedVertex" : "SOURCE",
    "directedType" : "DIRECTED"
  } ],
  "view" : {
    "edges" : {
      "group1" : { }
    }
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    edges=[ 
      g.ElementDefinition( 
        group="group1" 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EdgeSeed( 
      source="source", 
      destination="dest", 
      directed_type="DIRECTED", 
      matched_vertex="SOURCE" 
    ) 
  ] 
)


{%- endcodetabs %}


For entities it should now be written:

OLD:

{% codetabs name="Java", type="java" -%}
final GetElements getEntitiesWithSeedMatching = new GetElements.Builder()
        .input(new EntitySeed("vertex"))
        .seedMatching(SeedMatching.SeedMatchingType.EQUAL)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "vertex"
  } ],
  "seedMatching" : "EQUAL"
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : "vertex"
  } ],
  "seedMatching" : "EQUAL"
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  input=[ 
    g.EntitySeed( 
      vertex="vertex" 
    ) 
  ], 
  seed_matching="EQUAL" 
)


{%- endcodetabs %}


NEW:

{% codetabs name="Java", type="java" -%}
final GetElements getEntitiesWithoutSeedMatching = new GetElements.Builder()
        .input(new EntitySeed("vertex"))
        .view(new View.Builder()
                .entity("group1")
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetElements",
  "input" : [ {
    "class" : "EntitySeed",
    "vertex" : "vertex"
  } ],
  "view" : {
    "entities" : {
      "group1" : { }
    }
  }
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : "vertex"
  } ],
  "view" : {
    "entities" : {
      "group1" : { }
    }
  }
}


{%- language name="Python", type="py" -%}
g.GetElements( 
  view=g.View( 
    entities=[ 
      g.ElementDefinition( 
        group="group1" 
      ) 
    ], 
    all_edges=False, 
    all_entities=False 
  ), 
  input=[ 
    g.EntitySeed( 
      vertex="vertex" 
    ) 
  ] 
)


{%- endcodetabs %}


