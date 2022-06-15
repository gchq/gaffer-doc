# NamedOperation
See javadoc - [uk.gov.gchq.gaffer.named.operation.NamedOperation](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/named/operation/NamedOperation.html)

Available since Gaffer version 1.0.0

See [Named Operations](../developer-guide/namedoperations.md) for information on configuring named operations for your Gaffer graph.

## Required fields
The following fields are required: 
- operationName


## Examples

### Add named operation


{% codetabs name="Java", type="java" -%}
final AddNamedOperation operation = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .then(new GetAdjacentIds.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build())
        .description("2 hop query")
        .name("2-hop")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .overwrite()
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedOperation",
  "operationName" : "2-hop",
  "description" : "2 hop query",
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  },
  "overwriteFlag" : true,
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.AddNamedOperation",
  "operationName" : "2-hop",
  "description" : "2 hop query",
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  },
  "overwriteFlag" : true,
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Python", type="py" -%}
g.AddNamedOperation( 
  operation_chain=g.OperationChainDAO( 
    operations=[ 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ), 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ) 
    ] 
  ), 
  operation_name="2-hop", 
  description="2 hop query", 
  read_access_roles=[ 
    "read-user" 
  ], 
  write_access_roles=[ 
    "write-user" 
  ], 
  overwrite_flag=True 
)

{%- endcodetabs %}

-----------------------------------------------

### Add named operation with score


{% codetabs name="Java", type="java" -%}
final AddNamedOperation operation = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build())
        .description("1 hop query")
        .name("1-hop")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .overwrite()
        .score(2)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedOperation",
  "operationName" : "1-hop",
  "description" : "1 hop query",
  "score" : 2,
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  },
  "overwriteFlag" : true,
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.AddNamedOperation",
  "operationName" : "1-hop",
  "description" : "1 hop query",
  "score" : 2,
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    } ]
  },
  "overwriteFlag" : true,
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Python", type="py" -%}
g.AddNamedOperation( 
  operation_chain=g.OperationChainDAO( 
    operations=[ 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ) 
    ] 
  ), 
  operation_name="1-hop", 
  description="1 hop query", 
  read_access_roles=[ 
    "read-user" 
  ], 
  write_access_roles=[ 
    "write-user" 
  ], 
  overwrite_flag=True, 
  score=2 
)

{%- endcodetabs %}

-----------------------------------------------

### Add named operation with parameter


{% codetabs name="Java", type="java" -%}
final String opChainString = "{" +
        "    \"operations\" : [ {" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"," +
        "      \"includeIncomingOutGoing\" : \"OUTGOING\"" +
        "    }, {" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"," +
        "      \"includeIncomingOutGoing\" : \"OUTGOING\"" +
        "    }, {" +
        "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\"," +
        "      \"resultLimit\" : \"${param1}\"" +
        "    }" +
        " ]" +
        "}";

ParameterDetail param = new ParameterDetail.Builder()
        .defaultValue(1L)
        .description("Limit param")
        .valueClass(Long.class)
        .build();
Map<String, ParameterDetail> paramMap = Maps.newHashMap();
paramMap.put("param1", param);

final AddNamedOperation operation = new AddNamedOperation.Builder()
        .operationChain(opChainString)
        .description("2 hop query with settable limit")
        .name("2-hop-with-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .parameters(paramMap)
        .overwrite()
        .score(3)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddNamedOperation",
  "operationName" : "2-hop-with-limit",
  "description" : "2 hop query with settable limit",
  "score" : 3,
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
      "resultLimit" : "${param1}"
    } ]
  },
  "overwriteFlag" : true,
  "parameters" : {
    "param1" : {
      "description" : "Limit param",
      "defaultValue" : 1,
      "valueClass" : "Long",
      "required" : false
    }
  },
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.AddNamedOperation",
  "operationName" : "2-hop-with-limit",
  "description" : "2 hop query with settable limit",
  "score" : 3,
  "operationChain" : {
    "operations" : [ {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",
      "includeIncomingOutGoing" : "OUTGOING"
    }, {
      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",
      "resultLimit" : "${param1}"
    } ]
  },
  "overwriteFlag" : true,
  "parameters" : {
    "param1" : {
      "description" : "Limit param",
      "defaultValue" : 1,
      "valueClass" : "java.lang.Long",
      "required" : false
    }
  },
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ]
}

{%- language name="Python", type="py" -%}
g.AddNamedOperation( 
  operation_chain=g.OperationChainDAO( 
    operations=[ 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ), 
      g.GetAdjacentIds( 
        include_incoming_out_going="OUTGOING" 
      ), 
      g.Limit( 
        result_limit="${param1}" 
      ) 
    ] 
  ), 
  operation_name="2-hop-with-limit", 
  description="2 hop query with settable limit", 
  read_access_roles=[ 
    "read-user" 
  ], 
  write_access_roles=[ 
    "write-user" 
  ], 
  overwrite_flag=True, 
  score=3, 
  parameters=[ 
    g.NamedOperationParameter( 
      name="param1", 
      value_class="java.lang.Long", 
      description="Limit param", 
      default_value=1, 
      required=False 
    ) 
  ] 
)

{%- endcodetabs %}

-----------------------------------------------

### Get all named operations

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
final GetAllNamedOperations operation = new GetAllNamedOperations();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllNamedOperations"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.GetAllNamedOperations"
}

{%- language name="Python", type="py" -%}
g.GetAllNamedOperations()

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
NamedOperationDetail[inputType=java.lang.Object[],creatorId=user01,operations={"operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"}]},readAccessRoles=[read-user],writeAccessRoles=[write-user],score=2]
NamedOperationDetail[inputType=java.lang.Object[],creatorId=user01,operations={"operations":[{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"},{"class":"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds","includeIncomingOutGoing":"OUTGOING"}]},readAccessRoles=[read-user],writeAccessRoles=[write-user]]
NamedOperationDetail[inputType=java.lang.Object[],creatorId=user01,operations={    "operations" : [ {      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",      "includeIncomingOutGoing" : "OUTGOING"    }, {      "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds",      "includeIncomingOutGoing" : "OUTGOING"    }, {      "class" : "uk.gov.gchq.gaffer.operation.impl.Limit",      "resultLimit" : "${param1}"    } ]},readAccessRoles=[read-user],writeAccessRoles=[write-user],parameters={param1=ParameterDetail[description=Limit param,valueClass=class java.lang.Long,required=false,defaultValue=1]},score=3]

{%- language name="JSON", type="json" -%}
[ {
  "operationName" : "1-hop",
  "inputType" : "java.lang.Object[]",
  "description" : "1 hop query",
  "creatorId" : "user01",
  "operations" : "{\"operations\":[{\"class\":\"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\"includeIncomingOutGoing\":\"OUTGOING\"}]}",
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ],
  "parameters" : null,
  "score" : 2
}, {
  "operationName" : "2-hop",
  "inputType" : "java.lang.Object[]",
  "description" : "2 hop query",
  "creatorId" : "user01",
  "operations" : "{\"operations\":[{\"class\":\"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\"includeIncomingOutGoing\":\"OUTGOING\"},{\"class\":\"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",\"includeIncomingOutGoing\":\"OUTGOING\"}]}",
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ],
  "parameters" : null
}, {
  "operationName" : "2-hop-with-limit",
  "inputType" : "java.lang.Object[]",
  "description" : "2 hop query with settable limit",
  "creatorId" : "user01",
  "operations" : "{    \"operations\" : [ {      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",      \"includeIncomingOutGoing\" : \"OUTGOING\"    }, {      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\",      \"includeIncomingOutGoing\" : \"OUTGOING\"    }, {      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.Limit\",      \"resultLimit\" : \"${param1}\"    } ]}",
  "readAccessRoles" : [ "read-user" ],
  "writeAccessRoles" : [ "write-user" ],
  "parameters" : {
    "param1" : {
      "description" : "Limit param",
      "defaultValue" : 1,
      "valueClass" : "java.lang.Long",
      "required" : false
    }
  },
  "score" : 3
} ]
{%- endcodetabs %}

-----------------------------------------------

### Run named operation

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
final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                .name("2-hop")
                .input(new EntitySeed(1))
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "NamedOperation",
  "input" : [ {
    "class" : "EntitySeed",
    "class" : "EntitySeed",
    "vertex" : 1
  } ],
  "operationName" : "2-hop"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ],
  "operationName" : "2-hop"
}

{%- language name="Python", type="py" -%}
g.NamedOperation( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  operation_name="2-hop" 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=3]
EntitySeed[vertex=4]
EntitySeed[vertex=5]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 3
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 4
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 5
} ]
{%- endcodetabs %}

-----------------------------------------------

### Run named operation with parameter

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
Map<String, Object> paramMap = Maps.newHashMap();
paramMap.put("param1", 2L);

final NamedOperation<EntityId, CloseableIterable<EntityId>> operation =
        new NamedOperation.Builder<EntityId, CloseableIterable<EntityId>>()
                .name("2-hop-with-limit")
                .input(new EntitySeed(1))
                .parameters(paramMap)
                .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "NamedOperation",
  "input" : [ {
    "class" : "EntitySeed",
    "class" : "EntitySeed",
    "vertex" : 1
  } ],
  "operationName" : "2-hop-with-limit",
  "parameters" : {
    "param1" : 2
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.NamedOperation",
  "input" : [ {
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
    "vertex" : 1
  } ],
  "operationName" : "2-hop-with-limit",
  "parameters" : {
    "param1" : 2
  }
}

{%- language name="Python", type="py" -%}
g.NamedOperation( 
  input=[ 
    g.EntitySeed( 
      vertex=1 
    ) 
  ], 
  operation_name="2-hop-with-limit", 
  parameters={'param1': 2} 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
EntitySeed[vertex=3]
EntitySeed[vertex=4]

{%- language name="JSON", type="json" -%}
[ {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 3
}, {
  "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
  "vertex" : 4
} ]
{%- endcodetabs %}

-----------------------------------------------

### Delete named operation


{% codetabs name="Java", type="java" -%}
final DeleteNamedOperation operation = new DeleteNamedOperation.Builder()
        .name("2-hop")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "DeleteNamedOperation",
  "operationName" : "2-hop"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.operation.DeleteNamedOperation",
  "operationName" : "2-hop"
}

{%- language name="Python", type="py" -%}
g.DeleteNamedOperation( 
  operation_name="2-hop" 
)

{%- endcodetabs %}

-----------------------------------------------

