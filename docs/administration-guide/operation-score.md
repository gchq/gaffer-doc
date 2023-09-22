# Operation Scores

!!! info "Under construction"
    This page is currently under construction and will be expanded on in future.

To use the `OperationChainLimiter` GraphHook then you will need to configure that GraphHook to use the `NamedOperationScoreResolver`. 
This will then allow you to have custom scores for each NamedOperation.

!!! example "Example hook configuration"
    
    Configuration of the hook should look something like this:

    ``` json
    {
        "class": "uk.gov.gchq.gaffer.graph.hook.OperationChainLimiter",
        "opScores": {
            "uk.gov.gchq.gaffer.operation.Operation": 1,
            "uk.gov.gchq.gaffer.operation.impl.add.AddElements": 2,
            "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements": 5,
            "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects": 0
        },
        "authScores": {
            "User": 2,
            "SuperUser": 5
        },
        "scoreResolvers": { 
            "uk.gov.gchq.gaffer.named.operation.NamedOperation": {
                "class": "uk.gov.gchq.gaffer.store.operation.resolver.named.NamedOperationScoreResolver"
            }
        }
    }
    ```

!!! example "Example operation declarations file"
    
    As a result, the operation declarations file for registering the `ScoreOperationChain` operation would then look like:

    ``` json
    {
        "operations": [
            {
                "operation": "uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain",
                "handler": {
                    "opScores": {
                        "uk.gov.gchq.gaffer.operation.Operation": 1,
                        "uk.gov.gchq.gaffer.operation.impl.add.AddElements": 2,
                        "uk.gov.gchq.gaffer.operation.impl.get.GetAllElements": 5,
                        "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects": 0
                    },
                    "authScores": {
                        "User": 2,
                        "SuperUser": 5
                    },
                    "scoreResolvers": {
                        "uk.gov.gchq.gaffer.named.operation.NamedOperation": {
                            "class": "uk.gov.gchq.gaffer.store.operation.resolver.named.NamedOperationScoreResolver"
                        }
                    }
                }
            }
        ]
    }
    ```
Example of a NamedOperation configured with a score of 2:
``` java
final AddNamedOperation addOperation = new AddNamedOperation.Builder()
        .operationChain(new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("RoadUse")
                                .build())
                        .build())
                .then(new Limit.Builder<>().resultLimit(10).build())
                .build())
        .description("named operation limit query")
        .name("2-limit")
        .readAccessRoles("read-user")
        .writeAccessRoles("write-user")
        .score(2)
        .overwrite()
        .build();

graph.execute(addOperation, user);
```

If you have the `OperationChainLimiter` GraphHook configured then this score will be used by
the hook to limit operation chains.