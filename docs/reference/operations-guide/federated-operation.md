# Federated Operation

!!! note

    If you're upgrading from Gaffer 1.x.x you will need to [migrate from using the FederatedOperationChain to using the FederatedOperation](../../change-notes/migrating-from-v1-to-v2/federation-changes.md)

## The Federated Operation

The Federated Operation is an operation which can be used against a [Federated
Store](../../administration-guide/gaffer-stores/federated-store.md). The
operation is used to send a single or a chain of operations to one or more
graphs within a federated store. It can be configured to merge results
differently depending on the [`mergeFunction`](#the-merge-function) passed to it.

### Parameters
The Federated Operation has 3 key parameters: `operation`, `graphIds` and
`mergeFunction`:

!!! example ""
    ``` json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
        },
        "graphIds": [ "GraphA", "GraphB" ],
        "mergeFunction": {
            "class": "uk.gov.gchq.gaffer.federatedstore.util.ConcatenateMergeFunction"
        }
    }
    ```

#### Required parameter: operation

This is the Operation you wish to be federated to the subgraphs. This can be a
single Operation or [an
OperationChain](../../user-guide/query/gaffer-syntax/operations.md#operation-chains).
If you use an OperationChain, then the whole chain will be sent to the
subgraphs.  

#### Optional parameter: graphIds

This is a list of a single or multiple graph IDs which you want to send the operation to.  

If the user does not specify `graphIds` in the Operation, then the
`storeConfiguredGraphIds` for that store will be used. If the admin has not
configured the `storeConfiguredGraphIds` then all graphIds will be used. 

#### Optional parameter: mergeFunction

The `mergeFunction` parameter is the Function you want to use when merging the
results from the subgraphs.  

If you do not specify a `mergeFunction`, then the admin configured
`storeConfiguredMergeFunctions` are used, else the default `mergeFunctions` are
used as [shown in the table below.](#default-store-configured-merge-functions)

For example, when GetElements is used as the operation inside a
FederatedOperation and you haven't specified a `mergeFunction`, the
function `ApplyViewToElementsFunction` will be used by default, unless an
admin has configured a different function.

See the default mergeFunctions for the operations below.

##### Default Store Configured Merge Functions

| Operation         | Merge function                                             |
|-------------------|------------------------------------------------------------|
| GetElements       | [ApplyViewToElementsFunction](#applyviewtoelementsfunction)|
| GetAllElements    | [ApplyViewToElementsFunction](#applyviewtoelementsfunction)|
| GetSchema         | [MergeSchema](#mergeschema)                                |
| GetTraits         | [CollectionIntersect](#collectionintersect)                |
| others            | [ConcatenateMergeFunction](#concatenatemergefunction)      |

### Sending Operations To Federated Stores

In these examples we do not specify the `mergeFunction` parameter. This would
therefore use the default `mergeFunction` for the specific operation.

??? example "Sending a single operation to one subgraph in your Federated Store"
    ``` json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements"
        },
        "graphIds": [ "GraphA" ]
    }
    ```

??? example "Sending two different operations to two different subgraphs in your Federated Store"
    ```json
    {
        "class": "uk.gov.gchq.gaffer.operation.OperationChain",
        "operations": [
            {
                "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
                "operation": {
                    "class": "ExampleOperation1"
                },
                "graphIds": [ "GraphA" ]
            },
            {
                "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
                "operation": {
                    "class": "ExampleOperation2"
                },
                "graphIds": [ "GraphB" ]
            }
        ]
    }
    ```
??? example "Sending a single operation to one subgraph and a chain of operations to another subgraph in your Federated Store"
    ```json
    {
        "class": "uk.gov.gchq.gaffer.operation.OperationChain",
        "operations": [
            {
                "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
                "operation": {
                    "class": "uk.gov.gchq.gaffer.operation.OperationChain",
                    "operations": {
                        [
                            "class": "ExampleOperation1",
                            "class": "ExampleOperation2"
                        ]
                    }
                },
                "graphIds": [ "GraphA" ]
            },
            {
                "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
                "operation": {
                    "class": "ExampleOperation3"
                },
                "graphIds": [ "GraphB" ]
            }
        ]
    }
    ```

In the example above we've wrapped an Operation Chain inside a Federated Operation.

### The Merge Function

Merge functions dictate how the FederatedStore will merge results from
different subgraphs dependent on the Operation.

The examples below refer to this graph.

```mermaid
graph LR
    subgraph Federated Store
        subgraph Graph A
            direction TB
            A((Person<br>id: 1<br>name: bob<br>count: 1)) --works as--> B((Job<br>id: 2<br>title: builder))
            A --lives in--> C((Place<br>id: 3<br>name: bobsville))
        end
            subgraph Graph B
            direction TB
            D((Person<br>id: 1<br>name: bob<br>count: 1)) --drives--> E((Vehicle<br>id: 4<br>type: digger))
            E --make/model--> F((Manufacturer<br>id: 5<br>name: JCB))
        end
    end
```
??? Example "Graph A Schema and Data"
    Schema:
    ```json
    {
        "class": "AddGraph",
        "graphId": "GraphA",
        "schema": {
            "edges": {
            "WorksAs": {
                "source": "id.person.string",
                "destination": "id.job.string",
                "directed": "true"
            },
            "LivesIn": {
                "source": "id.person.string",
                "destination": "id.place.string",
                "directed": "true"
            }
            },
            "entities": {
            "Person": {
                "description": "Entity representing a person vertex",
                "vertex": "id.person.string",
                "aggregate": "true",
                "properties": {
                "name": "property.string",
                "count": "count.integer"
                }
            },
            "Job": {
                "description": "Entity representing a job vertex",
                "vertex": "id.job.string",
                "aggregate": "false",
                "properties": {
                "type": "property.string"
                }
            },
            "Place": {
                "description": "Entity representing a place vertex",
                "vertex": "id.place.string",
                "aggregate": "false",
                "properties": {
                "name": "property.string"
                }
            }
            },
            "types": {
            "id.person.string": {
                "description": "A basic type to hold the string id of a person entity",
                "class": "java.lang.String"
            },
            "id.place.string": {
                "description": "A basic type to hold the string id of a place entity",
                "class": "java.lang.String"
            },
            "id.job.string": {
                "description": "A basic type to hold the string id of a job entity",
                "class": "java.lang.String"
            },
            "property.string": {
                "description": "A type to hold string properties of entities",
                "class": "java.lang.String",
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.First"
                }
            },
            "count.integer": {
                "description": "A long count that must be greater than or equal to 0.",
                "class": "java.lang.Integer",
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                    "orEqualTo": true,
                    "value": {
                    "java.lang.Integer": 0
                    }
                }
                ],
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
                }
            },
            "true": {
                "class": "java.lang.Boolean",
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
                }
                ]
            }
            }
        },
        "storeProperties": {
            "gaffer.store.class": "uk.gov.gchq.gaffer.mapstore.MapStore"
        }
    }
    ```
    Data:
    ```json
    {
        "class": "AddElements",
        "input": [
            {
            "class": "Edge",
            "group": "WorksAs",
            "source": "1",
            "destination": "3",
            "directed": true
            },
            {
            "class": "Edge",
            "group": "LivesIn",
            "source": "1",
            "destination": "3",
            "directed": true
            },
            {
            "class": "Entity",
            "group": "Person",
            "vertex": "1",
            "properties": {
                "name": "Bob",
                "count": 1
            }
            },
            {
            "class": "Entity",
            "group": "Job",
            "vertex": "2",
            "properties": {
                "type": "Builder"
            }
            },
            {
            "class": "Entity",
            "group": "Place",
            "vertex": "3",
            "properties": {
                "name": "Bobsville"
            }
            }
        ],
        "options": {
            "gaffer.federatedstore.operation.graphIds": "GraphA"
        }
    }
    ```

??? Example "Graph B Schema and Data"
    Schema:
    ```json
    {
        "class": "AddGraph",
        "graphId": "GraphB",
        "schema": {
            "edges": {
            "Drives": {
                "source": "id.person.string",
                "destination": "id.vehicle.string",
                "directed": "true"
            },
            "MakeModel": {
                "source": "id.vehicle.string",
                "destination": "id.manufacturer.string",
                "directed": "true"
            }
            },
            "entities": {
            "Person": {
                "description": "Entity representing a person vertex",
                "vertex": "id.person.string",
                "aggregate": "true",
                "properties": {
                "name": "property.string",
                "count": "count.integer"
                }
            },
            "Vehicle": {
                "description": "Entity representing a vehicle vertex",
                "vertex": "id.vehicle.string",
                "aggregate": "false",
                "properties": {
                "type": "property.string"
                }
            },
            "Manufacturer": {
                "description": "Entity representing a manufacturer vertex",
                "vertex": "id.manufacturer.string",
                "aggregate": "false",
                "properties": {
                "name": "property.string"
                }
            }
            },
            "types": {
            "id.person.string": {
                "description": "A basic type to hold the string id of a person entity",
                "class": "java.lang.String"
            },
            "id.manufacturer.string": {
                "description": "A basic type to hold the string id of a manufacturer entity",
                "class": "java.lang.String"
            },
            "id.vehicle.string": {
                "description": "A basic type to hold the string id of a vehicle entity",
                "class": "java.lang.String"
            },
            "property.string": {
                "description": "A type to hold string properties of entities",
                "class": "java.lang.String",
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.First"
                }
            },
            "count.integer": {
                "description": "A long count that must be greater than or equal to 0.",
                "class": "java.lang.Integer",
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                    "orEqualTo": true,
                    "value": {
                    "java.lang.Integer": 0
                    }
                }
                ],
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
                }
            },
            "true": {
                "class": "java.lang.Boolean",
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
                }
                ]
            }
            }
        },
        "storeProperties": {
            "gaffer.store.class": "uk.gov.gchq.gaffer.mapstore.MapStore"
        }
    }
    ```
    Data:
    ```json
    {
        "class": "AddElements",
        "input": [
            {
            "class": "Edge",
            "group": "Drives",
            "source": "1",
            "destination": "4",
            "directed": true
            },
            {
            "class": "Edge",
            "group": "MakeModel",
            "source": "4",
            "destination": "5",
            "directed": true
            },
            {
            "class": "Entity",
            "group": "Person",
            "vertex": "1",
            "properties": {
                "name": "Bob",
                "count": 1
            }
            },
            {
            "class": "Entity",
            "group": "Vehicle",
            "vertex": "4",
            "properties": {
                "type": "Digger"
            }
            },
            {
            "class": "Entity",
            "group": "Manufacturer",
            "vertex": "5",
            "properties": {
                "name": "JCB"
            }
            }
        ],
        "options": {
            "gaffer.federatedstore.operation.graphIds": "GraphB"
        }
    }    
    ```

#### ApplyViewToElementsFunction

This merge function uses the View from the operation and applies it to all of
the results, meaning the results are now re-aggregated and re-filtered using the
Schema, locally in the FederatedStore. This makes the results look like they
came from one graph, rather than getting back an array of Elements from the
different subgraphs.


??? Example "Example using the GetAllElements operation"
    ```json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "GetAllElements"
        },
        "graphIds": ["GraphA","GraphB"],
        "mergeFunction": {
            "class": "uk.gov.gchq.gaffer.federatedstore.util.ApplyViewToElementsFunction"
        }
    }
    
    ```
    Result:
    ```json
    [
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "LivesIn",
            "source": "1",
            "destination": "3",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "MakeModel",
            "source": "4",
            "destination": "5",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Person",
            "vertex": "1",
            "properties": {
            "name": "Bob",
            "count": 2
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "WorksAs",
            "source": "1",
            "destination": "3",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "Drives",
            "source": "1",
            "destination": "4",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Vehicle",
            "vertex": "4",
            "properties": {
            "type": "Digger"
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Manufacturer",
            "vertex": "5",
            "properties": {
            "name": "JCB"
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Job",
            "vertex": "2",
            "properties": {
            "type": "Builder"
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Place",
            "vertex": "3",
            "properties": {
            "name": "Bobsville"
            }
        }
    ]
    ```

If we compare the results above to the `GetAllElements` [result below using the
`ConcanenateMergeFunction`](#concatenatemergefunction). We can see there is a
difference between them. In the `ApplyViewToElementsFunction` result above we
have just one entity of `Person` named `Bob` with a `count` of `2`. Whereas the
below result which used the `ConcanenateMergeFunction`,  we have two entities of
`Person` named `Bob`, each with a `count` of `1`. This is because the
`ApplyViewToElementsFunction` has re-aggregated and re-filtered the results from
both graphs into one.

#### CollectionIntersect

This returns the intersection of common store traits from the subgraphs in the
federated store.

??? Example "Example using the GetTraits operations."
    ```json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "GetTraits"
        },
        "graphIds": ["GraphA","GraphB"],
        "mergeFunction": {
            "class": "CollectionIntersect"
        }
    }
    
    ```
    Result:
    ```json
    [
        "MATCHED_VERTEX",
        "POST_TRANSFORMATION_FILTERING",
        "INGEST_AGGREGATION",
        "PRE_AGGREGATION_FILTERING",
        "TRANSFORMATION",
        "POST_AGGREGATION_FILTERING"
    ]
    ```


#### MergeSchema

This merge function returns an aggregated schema from the subgraphs, unless
there is a conflict. You may wish to use the `ConcatenateMergeFunction` if there
is a schema conflict.  


??? Example "Example using the GetSchema operation."
    ```json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "GetSchema"
        },
        "graphIds": ["GraphA","GraphB"],
        "mergeFunction": {
            "class": "uk.gov.gchq.gaffer.federatedstore.util.MergeSchema"
        }
    }
    ```
    Result:
    ```json
    {
        "edges": {
            "LivesIn": {
            "source": "id.person.string",
            "destination": "id.place.string",
            "directed": "true"
            },
            "Drives": {
            "source": "id.person.string",
            "destination": "id.vehicle.string",
            "directed": "true"
            },
            "WorksAs": {
            "source": "id.person.string",
            "destination": "id.job.string",
            "directed": "true"
            },
            "MakeModel": {
            "source": "id.vehicle.string",
            "destination": "id.manufacturer.string",
            "directed": "true"
            }
        },
        "entities": {
            "Vehicle": {
            "description": "Entity representing a vehicle vertex",
            "vertex": "id.vehicle.string",
            "properties": {
                "type": "property.string"
            },
            "aggregate": false
            },
            "Manufacturer": {
            "description": "Entity representing a manufacturer vertex",
            "vertex": "id.manufacturer.string",
            "properties": {
                "name": "property.string"
            },
            "aggregate": false
            },
            "Job": {
            "description": "Entity representing a job vertex",
            "vertex": "id.job.string",
            "properties": {
                "type": "property.string"
            },
            "aggregate": false
            },
            "Person": {
            "description": "Entity representing a person vertex",
            "vertex": "id.person.string",
            "properties": {
                "name": "property.string",
                "count": "count.integer"
            }
            },
            "Place": {
            "description": "Entity representing a place vertex",
            "vertex": "id.place.string",
            "properties": {
                "name": "property.string"
            },
            "aggregate": false
            }
        },
        "types": {
            "id.person.string": {
            "description": "A basic type to hold the string id of a person entity",
            "class": "java.lang.String"
            },
            "id.place.string": {
            "description": "A basic type to hold the string id of a place entity",
            "class": "java.lang.String"
            },
            "id.job.string": {
            "description": "A basic type to hold the string id of a job entity",
            "class": "java.lang.String"
            },
            "property.string": {
            "description": "A type to hold string properties of entities",
            "class": "java.lang.String",
            "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.First"
            }
            },
            "count.integer": {
            "description": "A long count that must be greater than or equal to 0.",
            "class": "java.lang.Integer",
            "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
            },
            "validateFunctions": [
                {
                "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                "orEqualTo": true,
                "value": 0
                }
            ]
            },
            "true": {
            "class": "java.lang.Boolean",
            "validateFunctions": [
                {
                "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
                }
            ]
            },
            "id.manufacturer.string": {
            "description": "A basic type to hold the string id of a manufacturer entity",
            "class": "java.lang.String"
            },
            "id.vehicle.string": {
            "description": "A basic type to hold the string id of a vehicle entity",
            "class": "java.lang.String"
            }
        }
    }
    ```

#### ConcatenateMergeFunction

This merge function is the default merge function for the majority of operations
as seen [in the table above](#default-store-configured-merge-functions). You can also
override the default merge functions for operations like we have below. It results
in concantinating all the results of the operations together.

Using the `ConcatenateMergeFunction` can give you duplicate results. This can be
seen with the `GetTraits` example below.

??? Example "Example using the GetSchema operation."
    ```json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "GetSchema"
        },
        "graphIds": ["GraphA","GraphB"],
        "mergeFunction": {
            "class": "uk.gov.gchq.gaffer.federatedstore.util.ConcatenateMergeFunction"
        }
    }
    ```
    Result:
    ```json
    [
        {
            "edges": {
            "Drives": {
                "source": "id.person.string",
                "destination": "id.vehicle.string",
                "directed": "true"
            },
            "MakeModel": {
                "source": "id.vehicle.string",
                "destination": "id.manufacturer.string",
                "directed": "true"
            }
            },
            "entities": {
            "Vehicle": {
                "description": "Entity representing a vehicle vertex",
                "vertex": "id.vehicle.string",
                "properties": {
                "type": "property.string"
                },
                "aggregate": false
            },
            "Manufacturer": {
                "description": "Entity representing a manufacturer vertex",
                "vertex": "id.manufacturer.string",
                "properties": {
                "name": "property.string"
                },
                "aggregate": false
            },
            "Person": {
                "description": "Entity representing a person vertex",
                "vertex": "id.person.string",
                "properties": {
                "name": "property.string",
                "count": "count.integer"
                }
            }
            },
            "types": {
            "id.person.string": {
                "description": "A basic type to hold the string id of a person entity",
                "class": "java.lang.String"
            },
            "id.manufacturer.string": {
                "description": "A basic type to hold the string id of a manufacturer entity",
                "class": "java.lang.String"
            },
            "id.vehicle.string": {
                "description": "A basic type to hold the string id of a vehicle entity",
                "class": "java.lang.String"
            },
            "property.string": {
                "description": "A type to hold string properties of entities",
                "class": "java.lang.String",
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.First"
                }
            },
            "count.integer": {
                "description": "A long count that must be greater than or equal to 0.",
                "class": "java.lang.Integer",
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
                },
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                    "orEqualTo": true,
                    "value": 0
                }
                ]
            },
            "true": {
                "class": "java.lang.Boolean",
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
                }
                ]
            }
            }
        },
        {
            "edges": {
            "WorksAs": {
                "source": "id.person.string",
                "destination": "id.job.string",
                "directed": "true"
            },
            "LivesIn": {
                "source": "id.person.string",
                "destination": "id.place.string",
                "directed": "true"
            }
            },
            "entities": {
            "Job": {
                "description": "Entity representing a job vertex",
                "vertex": "id.job.string",
                "properties": {
                "type": "property.string"
                },
                "aggregate": false
            },
            "Person": {
                "description": "Entity representing a person vertex",
                "vertex": "id.person.string",
                "properties": {
                "name": "property.string",
                "count": "count.integer"
                }
            },
            "Place": {
                "description": "Entity representing a place vertex",
                "vertex": "id.place.string",
                "properties": {
                "name": "property.string"
                },
                "aggregate": false
            }
            },
            "types": {
            "id.person.string": {
                "description": "A basic type to hold the string id of a person entity",
                "class": "java.lang.String"
            },
            "id.place.string": {
                "description": "A basic type to hold the string id of a place entity",
                "class": "java.lang.String"
            },
            "id.job.string": {
                "description": "A basic type to hold the string id of a job entity",
                "class": "java.lang.String"
            },
            "property.string": {
                "description": "A type to hold string properties of entities",
                "class": "java.lang.String",
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.First"
                }
            },
            "count.integer": {
                "description": "A long count that must be greater than or equal to 0.",
                "class": "java.lang.Integer",
                "aggregateFunction": {
                "class": "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
                },
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
                    "orEqualTo": true,
                    "value": 0
                }
                ]
            },
            "true": {
                "class": "java.lang.Boolean",
                "validateFunctions": [
                {
                    "class": "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
                }
                ]
            }
            }
        }
    ]
    ```

??? Example "Example using the GetTraits operation."
    ```json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "GetTraits"
        },
        "graphIds": ["GraphA","GraphB"],
            "mergeFunction": {
                "class": "uk.gov.gchq.gaffer.federatedstore.util.ConcatenateMergeFunction"
            }
    }
    
    ```
    Result:
    ```json
    [
        "MATCHED_VERTEX",
        "POST_TRANSFORMATION_FILTERING",
        "INGEST_AGGREGATION",
        "PRE_AGGREGATION_FILTERING",
        "TRANSFORMATION",
        "POST_AGGREGATION_FILTERING",
        "MATCHED_VERTEX",
        "POST_TRANSFORMATION_FILTERING",
        "INGEST_AGGREGATION",
        "PRE_AGGREGATION_FILTERING",
        "TRANSFORMATION",
        "POST_AGGREGATION_FILTERING"
    ]
    ```

??? Example "Example using the GetAllElements operation."
    ```json
    {
        "class": "uk.gov.gchq.gaffer.federatedstore.operation.FederatedOperation",
        "operation": {
            "class": "GetAllElements"
        },
        "graphIds": ["GraphA","GraphB"],
            "mergeFunction": {
                "class": "uk.gov.gchq.gaffer.federatedstore.util.ConcatenateMergeFunction"
            }
    }
    
    ```
    Result:
    ```json
    [
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "MakeModel",
            "source": "4",
            "destination": "5",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Person",
            "vertex": "1",
            "properties": {
            "name": "Bob",
            "count": 1
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "Drives",
            "source": "1",
            "destination": "4",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Vehicle",
            "vertex": "4",
            "properties": {
            "type": "Digger"
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Manufacturer",
            "vertex": "5",
            "properties": {
            "name": "JCB"
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "LivesIn",
            "source": "1",
            "destination": "3",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Person",
            "vertex": "1",
            "properties": {
            "name": "Bob",
            "count": 1
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Edge",
            "group": "WorksAs",
            "source": "1",
            "destination": "3",
            "directed": true,
            "matchedVertex": "SOURCE",
            "properties": {}
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Job",
            "vertex": "2",
            "properties": {
            "type": "Builder"
            }
        },
        {
            "class": "uk.gov.gchq.gaffer.data.element.Entity",
            "group": "Place",
            "vertex": "3",
            "properties": {
            "name": "Bobsville"
            }
        }
    ]
    ```