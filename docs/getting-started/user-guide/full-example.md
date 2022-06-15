# Full Example

The code for this example is [FullExample](https://github.com/gchq/gaffer-doc/blob/master/src/main/java/uk/gov/gchq/gaffer/doc/user/walkthrough/FullExample.java).

Finally this example introduces the full Road Traffic schema. This uses the sample data taken from the Department for Transport [GB Road Traffic Counts](http://data.dft.gov.uk/gb-traffic-matrix/Raw_count_data_major_roads.zip), which is licensed under the [Open Government Licence](http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/).

The data is now in a slightly different format. Each row now represents multiple vehicles of different types travelling between 2 junctions. We also have a bit of extra information in the data file. This has allow us to create some extra edges: RegionContainsLocation, LocationContainsRoad and JunctionLocatedAt.

As we now have multiple roads in our sample data, we will include the name of the road in the junction name, e.g. M5:23 represents junction 23 on the M5.

We have also add in a frequency map for the counts of each vehicle time. This will allow us to perform queries such as to find out which roads have a large number of buses. 
Here are the updated schema files:

### Elements schema

```json
{
  "edges": {
    "RoadUse": {
      "description": "A directed edge representing vehicles moving from junction A to junction B.",
      "source": "junction",
      "destination": "junction",
      "directed": "true",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long",
        "countByVehicleType": "counts.freqmap"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    },
    "RoadHasJunction": {
      "description": "A directed edge from each road to all the junctions on that road.",
      "source": "road",
      "destination": "junction",
      "directed": "true"
    },
    "RegionContainsLocation": {
      "description": "A directed edge from each region to location.",
      "source": "region",
      "destination": "location",
      "directed": "true"
    },
    "LocationContainsRoad": {
      "description": "A directed edge from each location to road.",
      "source": "location",
      "destination": "road",
      "directed": "true"
    },
    "JunctionLocatedAt": {
      "description": "A directed edge from each junction to its coordinates",
      "source": "junction",
      "destination": "coordinates",
      "directed": "true"
    }
  },
  "entities": {
    "Cardinality": {
      "description": "An entity that is added to every vertex representing the connectivity of the vertex.",
      "vertex": "anyVertex",
      "properties": {
        "edgeGroup": "set",
        "hllp": "hllp",
        "count": "count.long"
      },
      "groupBy": [
        "edgeGroup"
      ]
    },
    "JunctionUse": {
      "description": "An entity on the junction vertex representing the counts of vehicles moving from junction A to junction B.",
      "vertex": "junction",
      "properties": {
        "startDate": "date.earliest",
        "endDate": "date.latest",
        "count": "count.long",
        "countByVehicleType": "counts.freqmap"
      },
      "groupBy": [
        "startDate",
        "endDate"
      ]
    }
  }
}
```



### Types schema

```json
{
  "types": {
    "junction": {
      "description": "A road junction represented by a String.",
      "class": "java.lang.String"
    },
    "road": {
      "description": "A road represented by a String.",
      "class": "java.lang.String"
    },
    "location": {
      "description": "A location represented by a String.",
      "class": "java.lang.String",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ]
    },
    "anyVertex": {
      "description": "An String vertex - used for cardinalities",
      "class": "java.lang.String",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ]
    },
    "coordinates": {
      "description": "Coordinates represented by a String in the format 'Eastings,Northings'.",
      "class": "java.lang.String",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ]
    },
    "region": {
      "description": "A region represented by a String.",
      "class": "java.lang.String",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ]
    },
    "count.long": {
      "description": "A long count that must be greater than or equal to 0.",
      "class": "java.lang.Long",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
          "orEqualTo": true,
          "value": {
            "java.lang.Long": 0
          }
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
      }
    },
    "true": {
      "description": "A simple boolean that must always be true.",
      "class": "java.lang.Boolean",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
        }
      ]
    },
    "date.earliest": {
      "description": "A Date that when aggregated together will be the earliest date.",
      "class": "java.util.Date",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
      }
    },
    "date.latest": {
      "description": "A Date that when aggregated together will be the latest date.",
      "class": "java.util.Date",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
      }
    },
    "set": {
      "class": "java.util.TreeSet",
      "aggregateFunction": {
        "class": "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      }
    },
    "hllp": {
      "class": "com.clearspring.analytics.stream.cardinality.HyperLogLogPlus",
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator"
      },
      "serialiser": {
        "class": "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.serialisation.HyperLogLogPlusSerialiser"
      }
    },
    "counts.freqmap": {
      "class": "uk.gov.gchq.gaffer.types.FreqMap",
      "validateFunctions": [
        {
          "class": "uk.gov.gchq.koryphe.impl.predicate.Exists"
        }
      ],
      "aggregateFunction": {
        "class": "uk.gov.gchq.gaffer.types.function.FreqMapAggregator"
      }
    }
  }
}
```



## Example complex query
Now we have a the full schema we can load in our Road Traffic Sample [data.txt](https://github.com/gchq/gaffer-doc/blob/master/src/main/resources/FullExample/data.txt) and run a more complex query.

For this example, the question we want to ask is: "In the year 2000, which junctions in the South West were heavily used by buses".

There may be different and probably more efficient ways of doing this but we have tried to create an operation chain that demonstrates several features from the previous walkthroughs. 

The query is form a follows:

- We will start at the "South West" vertex, follow RegionContainsLocation edge, then LocationContainsRoad edge. 
- We may get duplicates at this point so we will add all the road vertices to a Set using ToSet (this is not recommended for a large number of results).
- Then we will continue on down RoadHasJunction edges.
- At this point we now have all the Junctions in the South West.
- We will then query for the JunctionUse entity to find out the number of buses.
- Next we will sort the entities based on the number of buses and we will just keep the top 2 results.
- Finally, just to demonstrate another operation, we will convert the results into a simple CSV of junction and bus count.

and here is the code in Java, JSON and Python:


{% codetabs name="Java", type="java" -%}
final OperationChain<Iterable<? extends String>> opChain = new OperationChain.Builder()
        .first(new GetAdjacentIds.Builder()
                .input(new EntitySeed("South West"))
                .view(new View.Builder()
                        .edge("RegionContainsLocation")
                        .build())
                .build())
        .then(new GetAdjacentIds.Builder()
                .view(new View.Builder()
                        .edge("LocationContainsRoad")
                        .build())
                .build())
        .then(new ToSet<>())
        .then(new GetAdjacentIds.Builder()
                .view(new View.Builder()
                        .edge("RoadHasJunction")
                        .build())
                .build())
        .then(new GetElements.Builder()
                .view(new View.Builder()
                        .globalElements(new GlobalViewElementDefinition.Builder()
                                .groupBy()
                                .build())
                        .entity("JunctionUse", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("startDate", "endDate")
                                        .execute(new InDateRangeDual.Builder()
                                                .start("2000/01/01")
                                                .end("2001/01/01")
                                                .build())
                                        .build())
                                .postAggregationFilter(new ElementFilter.Builder()
                                        .select("countByVehicleType")
                                        .execute(new PredicateMap<>("BUS", new IsMoreThan(1000L)))
                                        .build())

                                // Extract the bus count out of the frequency map and store in transient property "busCount"
                                .transientProperty("busCount", Long.class)
                                .transformer(new ElementTransformer.Builder()
                                        .select("countByVehicleType")
                                        .execute(new FreqMapExtractor("BUS"))
                                        .project("busCount")
                                        .build())
                                .build())
                        .build())
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                .build())
        .then(new Sort.Builder()
                .comparators(new ElementPropertyComparator.Builder()
                        .groups("JunctionUse")
                        .property("busCount")
                        .reverse(true)
                        .build())
                .resultLimit(2)
                .deduplicate(true)
                .build())
        // Convert the result entities to a simple CSV in format: Junction,busCount.
        .then(new ToCsv.Builder()
                .generator(new CsvGenerator.Builder()
                        .vertex("Junction")
                        .property("busCount", "Bus Count")
                        .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAdjacentIds",
    "input" : [ {
      "class" : "EntitySeed",
      "vertex" : "South West"
    } ],
    "view" : {
      "edges" : {
        "RegionContainsLocation" : { }
      }
    }
  }, {
    "class" : "GetAdjacentIds",
    "view" : {
      "edges" : {
        "LocationContainsRoad" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  }, {
    "class" : "GetAdjacentIds",
    "view" : {
      "edges" : {
        "RoadHasJunction" : { }
      }
    }
  }, {
    "class" : "GetElements",
    "view" : {
      "entities" : {
        "JunctionUse" : {
          "preAggregationFilterFunctions" : [ {
            "selection" : [ "startDate", "endDate" ],
            "predicate" : {
              "class" : "InDateRangeDual",
              "start" : "2000/01/01",
              "end" : "2001/01/01"
            }
          } ],
          "postAggregationFilterFunctions" : [ {
            "selection" : [ "countByVehicleType" ],
            "predicate" : {
              "class" : "PredicateMap",
              "predicate" : {
                "class" : "IsMoreThan",
                "orEqualTo" : false,
                "value" : {
                  "Long" : 1000
                }
              },
              "key" : "BUS"
            }
          } ],
          "transientProperties" : {
            "busCount" : "Long"
          },
          "transformFunctions" : [ {
            "selection" : [ "countByVehicleType" ],
            "function" : {
              "class" : "FreqMapExtractor",
              "key" : "BUS"
            },
            "projection" : [ "busCount" ]
          } ]
        }
      },
      "globalElements" : [ {
        "groupBy" : [ ]
      } ]
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "Sort",
    "comparators" : [ {
      "class" : "ElementPropertyComparator",
      "property" : "busCount",
      "groups" : [ "JunctionUse" ],
      "reversed" : true
    } ],
    "deduplicate" : true,
    "resultLimit" : 2
  }, {
    "class" : "ToCsv",
    "elementGenerator" : {
      "class" : "CsvGenerator",
      "fields" : {
        "VERTEX" : "Junction",
        "busCount" : "Bus Count"
      },
      "constants" : { },
      "quoted" : false,
      "commaReplacement" : " "
    },
    "includeHeader" : true
  } ]
}


{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "input" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : "South West"
    } ],
    "view" : {
      "edges" : {
        "RegionContainsLocation" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "view" : {
      "edges" : {
        "LocationContainsRoad" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToSet"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
    "view" : {
      "edges" : {
        "RoadHasJunction" : { }
      }
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetElements",
    "view" : {
      "entities" : {
        "JunctionUse" : {
          "preAggregationFilterFunctions" : [ {
            "selection" : [ "startDate", "endDate" ],
            "predicate" : {
              "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InDateRangeDual",
              "start" : "2000/01/01",
              "end" : "2001/01/01"
            }
          } ],
          "postAggregationFilterFunctions" : [ {
            "selection" : [ "countByVehicleType" ],
            "predicate" : {
              "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
              "predicate" : {
                "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                "orEqualTo" : false,
                "value" : {
                  "java.lang.Long" : 1000
                }
              },
              "key" : "BUS"
            }
          } ],
          "transientProperties" : {
            "busCount" : "java.lang.Long"
          },
          "transformFunctions" : [ {
            "selection" : [ "countByVehicleType" ],
            "function" : {
              "class" : "uk.gov.gchq.gaffer.types.function.FreqMapExtractor",
              "key" : "BUS"
            },
            "projection" : [ "busCount" ]
          } ]
        }
      },
      "globalElements" : [ {
        "groupBy" : [ ]
      } ]
    },
    "includeIncomingOutGoing" : "OUTGOING"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.compare.Sort",
    "comparators" : [ {
      "class" : "uk.gov.gchq.gaffer.data.element.comparison.ElementPropertyComparator",
      "property" : "busCount",
      "groups" : [ "JunctionUse" ],
      "reversed" : true
    } ],
    "deduplicate" : true,
    "resultLimit" : 2
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.output.ToCsv",
    "elementGenerator" : {
      "class" : "uk.gov.gchq.gaffer.data.generator.CsvGenerator",
      "fields" : {
        "VERTEX" : "Junction",
        "busCount" : "Bus Count"
      },
      "constants" : { },
      "quoted" : false,
      "commaReplacement" : " "
    },
    "includeHeader" : true
  } ]
}


{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAdjacentIds( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="RegionContainsLocation" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ), 
      input=[ 
        g.EntitySeed( 
          vertex="South West" 
        ) 
      ] 
    ), 
    g.GetAdjacentIds( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="LocationContainsRoad" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ) 
    ), 
    g.ToSet(), 
    g.GetAdjacentIds( 
      view=g.View( 
        edges=[ 
          g.ElementDefinition( 
            group="RoadHasJunction" 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ) 
    ), 
    g.GetElements( 
      view=g.View( 
        entities=[ 
          g.ElementDefinition( 
            group="JunctionUse", 
            transient_properties={'busCount': 'java.lang.Long'}, 
            pre_aggregation_filter_functions=[ 
              g.PredicateContext( 
                selection=[ 
                  "startDate", 
                  "endDate" 
                ], 
                predicate=g.InDateRangeDual( 
                  start="2000/01/01", 
                  end="2001/01/01" 
                ) 
              ) 
            ], 
            post_aggregation_filter_functions=[ 
              g.PredicateContext( 
                selection=[ 
                  "countByVehicleType" 
                ], 
                predicate=g.PredicateMap( 
                  key="BUS", 
                  predicate=g.IsMoreThan( 
                    value={'java.lang.Long': 1000}, 
                    or_equal_to=False 
                  ) 
                ) 
              ) 
            ], 
            transform_functions=[ 
              g.FunctionContext( 
                selection=[ 
                  "countByVehicleType" 
                ], 
                function=g.FreqMapExtractor( 
                  key="BUS" 
                ), 
                projection=[ 
                  "busCount" 
                ] 
              ) 
            ] 
          ) 
        ], 
        global_elements=[ 
          g.GlobalElementDefinition( 
            group_by=[ 
            ] 
          ) 
        ], 
        all_edges=False, 
        all_entities=False 
      ), 
      include_incoming_out_going="OUTGOING" 
    ), 
    g.Sort( 
      comparators=[ 
        g.ElementPropertyComparator( 
          groups=[ 
            "JunctionUse" 
          ], 
          property="busCount", 
          reversed=True 
        ) 
      ], 
      result_limit=2, 
      deduplicate=True 
    ), 
    g.ToCsv( 
      element_generator=g.CsvGenerator( 
        fields={'VERTEX': 'Junction', 'busCount': 'Bus Count'}, 
        constants={}, 
        quoted=False, 
        comma_replacement=" " 
      ), 
      include_header=True 
    ) 
  ] 
)


{%- endcodetabs %}


We have a python shell for connecting to the Gaffer REST API. You can
get the python shell from [here](https://github.com/gchq/gaffer-tools/tree/master/python-shell).
To execute the python code above you will need to import the folowing:

```python
from gafferpy import gaffer as g
from gafferpy import gaffer_connector
```


When executed on the graph, the result is:

```
Junction,Bus Count
M4:LA Boundary,1958
M32:2,1411

```

The full road traffic example project can be found in [Road Traffic Example](https://github.com/gchq/Gaffer/blob/master/example/road-traffic/README.md). 
At this point it might be a good idea to follow the documentation in that README to start up a Gaffer REST API and UI backed with the road traffic graph.
