# GetSchema
See javadoc - [uk.gov.gchq.gaffer.store.operation.GetSchema](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/store/operation/GetSchema.html)

Available since Gaffer version 1.1.0

Gets the Schema of a Graph

## Required fields
No required fields


## Examples

### Get full schema

This operation defaults the compact field to false, thereby returning the full Schema.

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
final GetSchema operation = new GetSchema();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetSchema",
  "compact" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.store.operation.GetSchema",
  "compact" : false
}

{%- language name="Python", type="py" -%}
g.GetSchema( 
  compact=False 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
{
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
}

{%- language name="JSON", type="json" -%}
{
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
}
{%- endcodetabs %}

-----------------------------------------------

### Get compact schema

This operation will retrieve the compact Schema from the store, rather than the full schema.

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
final GetSchema operation = new GetSchema.Builder()
        .compact(true)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetSchema",
  "compact" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.store.operation.GetSchema",
  "compact" : true
}

{%- language name="Python", type="py" -%}
g.GetSchema( 
  compact=True 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
{
  "edges" : {
    "edge" : {
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
      "vertex" : "int",
      "properties" : {
        "count" : "count"
      }
    },
    "cardinality" : {
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
      },
      "serialiser" : {
        "class" : "OrderedIntegerSerialiser"
      }
    },
    "set" : {
      "class" : "TreeSet",
      "aggregateFunction" : {
        "class" : "CollectionConcat"
      },
      "serialiser" : {
        "class" : "TreeSetStringSerialiser"
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
  },
  "vertexSerialiser" : {
    "class" : "OrderedIntegerSerialiser"
  }
}

{%- language name="JSON", type="json" -%}
{
  "edges" : {
    "edge" : {
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
      "vertex" : "int",
      "properties" : {
        "count" : "count"
      }
    },
    "cardinality" : {
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
      },
      "serialiser" : {
        "class" : "uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedIntegerSerialiser"
      }
    },
    "set" : {
      "class" : "java.util.TreeSet",
      "aggregateFunction" : {
        "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
      },
      "serialiser" : {
        "class" : "uk.gov.gchq.gaffer.serialisation.implementation.TreeSetStringSerialiser"
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
  },
  "vertexSerialiser" : {
    "class" : "uk.gov.gchq.gaffer.serialisation.implementation.ordered.OrderedIntegerSerialiser"
  }
}
{%- endcodetabs %}

-----------------------------------------------

