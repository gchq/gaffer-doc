# GetGafferResultCacheExport
See javadoc - [uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/export/resultcache/GetGafferResultCacheExport.html)

Available since Gaffer version 1.0.0

Fetches data from a Gaffer result cache

## Required fields
No required fields


## Examples

### Simple export and get

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetGafferResultCacheExport())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToGafferResultCache"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetGafferResultCacheExport",
    "key" : "ALL"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "key" : "ALL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache(), 
    g.DiscardOutput(), 
    g.GetGafferResultCacheExport( 
      key="ALL" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 4,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 5,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 4
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Export and get job details

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<JobDetail> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache<>())
        .then(new DiscardOutput())
        .then(new GetJobDetails())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToGafferResultCache"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetJobDetails"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.job.GetJobDetails"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache(), 
    g.DiscardOutput(), 
    g.GetJobDetails() 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
JobDetail[jobId=69e2899a-2b17-4288-86a6-187a2dbc7d08,user=User[userId=user01,dataAuths=[],opAuths=[]],status=RUNNING,startTime=1632404084983,opChain=OperationChain[GetAllElements->ExportToGafferResultCache->DiscardOutput->GetJobDetails]]

{%- language name="JSON", type="json" -%}
{
  "jobId" : "69e2899a-2b17-4288-86a6-187a2dbc7d08",
  "user" : {
    "userId" : "user01",
    "dataAuths" : [ ],
    "opAuths" : [ ]
  },
  "status" : "RUNNING",
  "startTime" : 1632404084983,
  "opChain" : "OperationChain[GetAllElements->ExportToGafferResultCache->DiscardOutput->GetJobDetails]"
}
{%- endcodetabs %}

-----------------------------------------------

### Get export

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<CloseableIterable<?>> opChain = new OperationChain.Builder()
        .first(new GetGafferResultCacheExport.Builder()
                .jobId(jobDetail.getJobId())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetGafferResultCacheExport",
    "jobId" : "69e2899a-2b17-4288-86a6-187a2dbc7d08",
    "key" : "ALL"
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
    "jobId" : "69e2899a-2b17-4288-86a6-187a2dbc7d08",
    "key" : "ALL"
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetGafferResultCacheExport( 
      job_id="69e2899a-2b17-4288-86a6-187a2dbc7d08", 
      key="ALL" 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 5,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 3,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 4
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 3,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 5,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 2,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 3,
  "directed" : true,
  "properties" : {
    "count" : 2
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 1,
  "destination" : 2,
  "directed" : true,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 1,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 4,
  "properties" : {
    "count" : 1
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Edge",
  "group" : "edge",
  "source" : 2,
  "destination" : 4,
  "directed" : true,
  "properties" : {
    "count" : 1
  }
} ]
{%- endcodetabs %}

-----------------------------------------------

### Export multiple results to gaffer result cache and get all results

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final OperationChain<Map<String, CloseableIterable<?>>> opChain = new OperationChain.Builder()
        .first(new GetAllElements())
        .then(new ExportToGafferResultCache.Builder<>()
                .key("edges")
                .build())
        .then(new DiscardOutput())
        .then(new GetAllElements())
        .then(new ExportToGafferResultCache.Builder<>()
                .key("entities")
                .build())
        .then(new DiscardOutput())
        .then(new GetExports.Builder()
                .exports(new GetGafferResultCacheExport.Builder()
                                .key("edges")
                                .build(),
                        new GetGafferResultCacheExport.Builder()
                                .key("entities")
                                .build())
                .build())
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "OperationChain",
  "operations" : [ {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToGafferResultCache",
    "key" : "edges"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetAllElements"
  }, {
    "class" : "ExportToGafferResultCache",
    "key" : "entities"
  }, {
    "class" : "DiscardOutput"
  }, {
    "class" : "GetExports",
    "getExports" : [ {
      "class" : "GetGafferResultCacheExport",
      "key" : "edges"
    }, {
      "class" : "GetGafferResultCacheExport",
      "key" : "entities"
    } ]
  } ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.OperationChain",
  "operations" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
    "key" : "edges"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache",
    "key" : "entities"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.DiscardOutput"
  }, {
    "class" : "uk.gov.gchq.gaffer.operation.impl.export.GetExports",
    "getExports" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "key" : "edges"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport",
      "key" : "entities"
    } ]
  } ]
}

{%- language name="Python", type="py" -%}
g.OperationChain( 
  operations=[ 
    g.GetAllElements(), 
    g.ExportToGafferResultCache( 
      key="edges" 
    ), 
    g.DiscardOutput(), 
    g.GetAllElements(), 
    g.ExportToGafferResultCache( 
      key="entities" 
    ), 
    g.DiscardOutput(), 
    g.GetExports( 
      get_exports=[ 
        g.GetGafferResultCacheExport( 
          key="edges" 
        ), 
        g.GetGafferResultCacheExport( 
          key="entities" 
        ) 
      ] 
    ) 
  ] 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: edges:
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: entities:
    Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]
    Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]
    Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]
    Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]
    Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]
    Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]
    Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]

{%- language name="JSON", type="json" -%}
{
  "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: edges" : [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 4,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 5,
    "properties" : {
      "count" : 3
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 3,
    "properties" : {
      "count" : 2
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 3,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 4
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 3,
    "directed" : true,
    "properties" : {
      "count" : 2
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 1,
    "properties" : {
      "count" : 3
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 2,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 2,
    "directed" : true,
    "properties" : {
      "count" : 3
    }
  } ],
  "uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport: entities" : [ {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 2,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 3,
    "destination" : 4,
    "directed" : true,
    "properties" : {
      "count" : 4
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 5,
    "directed" : true,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 5,
    "properties" : {
      "count" : 3
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 4,
    "properties" : {
      "count" : 1
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 1,
    "destination" : 2,
    "directed" : true,
    "properties" : {
      "count" : 3
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 1,
    "properties" : {
      "count" : 3
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Edge",
    "group" : "edge",
    "source" : 2,
    "destination" : 3,
    "directed" : true,
    "properties" : {
      "count" : 2
    }
  }, {
    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
    "group" : "entity",
    "vertex" : 3,
    "properties" : {
      "count" : 2
    }
  } ]
}
{%- endcodetabs %}

-----------------------------------------------

