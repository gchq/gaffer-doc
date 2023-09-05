# Operation Chains

Querying in Gaffer is largely centered around Operations that can be chained
together to form complex queries on the graph.

As an example of a simple operation, say we want to get all nodes and edges
based on their ID. To do this we can use the `GetElements` operation and set the
`Seed` to the entity (e.g. node) or edge where we want to start the search.

!!! example ""

    Assuming the entity ID we wish to search from is `"v1"`.

    ```json
    {
        "class": "GetElements",
        "input": [
            {
                "class": "EntitySeed",
                "vertex": "v1"
            }
        ]
    }
    ```

This can then be expanded into a chain by using the output from the
`GetElements` operation as the input to the `Count` operation to give a total of
how many entities the `GetElements` returned.

!!! example ""
    As you can see we have used the `OperationChain` to run two operations in a
    chain with the output of one being the input of the next.

    ```json
    {
        "class" : "OperationChain",
        "operations" : [
            {
                "class": "GetElements",
                "input": [
                    {
                        "class": "EntitySeed",
                        "vertex": "v1"
                    }
                ]
            },
            {
                "class" : "Count"
            }
        ]
    }
    ```