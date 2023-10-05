# Operation Scores

Operation Scores can be used to give an OperationChains and NamedOperations a "score" which can then be used to determine whether a particular user has the required permissions to execute a given OperationChain.

These scores work on a credit-like system where user's configured "score" is the amount they can then spend on running an Operation/OperationChain. 
Users cannot spend more than their score and every time they run an Operation or OperationChain that has a configured "score" this will be 'subtracted' from their score 'credit'.

## Using Operation Scores

A `ScoreOperationChain` operation determines a "score" for an OperationChain.
To use this Operation you need to configure an `OperationsDeclarations.json`.

To configure this file you need to set the following handlers which configure how Operation Scores are used with a graph:

- `opScores` - required map of operation scores. These are the operation score values.
- `authScores` - required map of operation authorisation scores. These are the maximum scores allowed for a user with a given role. 
- `scoreResolver` - some operations may require a custom way of calculating an associated score, therefore an implementation of the [`ScoreResolver`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/operation/resolver/ScoreResolver.html) interface may be required. 
These map the operation class to its respective score resolver.
There is a `DefaultScoreResolver` to which the custom implementation should delegate, in a manner specific to the new Operation.

!!! example "Example OperationDeclarations.json for a NamedOperation"

    ```json
    {
        "operations": [
            {
                "operation": "uk.gov.gchq.gaffer.operation.impl.ScoreOperationChain",
                "handler": {
                    "class": "uk.gov.gchq.gaffer.store.operation.handler.ScoreOperationChainHandler",
                    "opScores": {
                        "uk.gov.gchq.gaffer.operation.Operation": 2,
                        "uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects": 0
                    },
                    "authScores": {
                        "User": 4,
                        "EnhancedUser": 10
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

!!! example "Example ScoreOperationChain"

    === "Java"
        ``` java
        final ScoreOperationChain scoreOpChain = new ScoreOperationChain.Builder()
            .operationChain(new OperationChain.Builder()
                    .first(new GetElements())
                    .then(new NamedOperation.Builder<Element, Iterable<? extends Element>>()
                            .name("namedOp")
                            .build())
                    .then(new Limit<>(3))
                    .build())
            .build();
        ```

    === "JSON"
        ``` json
        {
            "class" : "ScoreOperationChain",
            "operationChain" : {
                "class" : "OperationChain",
                "operations" : [ {
                    "class" : "GetElements"
                }, {
                    "class" : "NamedOperation",
                    "operationName" : "namedOp"
                }, {
                    "class" : "Limit",
                    "resultLimit" : 3,
                    "truncate" : true
                } ]
            }
        }
        ```

    === "Python"
        ``` python
        g.ScoreOperationChain( 
            operation_chain=g.OperationChain( 
                operations=[ 
                    g.GetElements(), 
                    g.NamedOperation( 
                        operation_name="namedOp" 
                    ), 
                    g.Limit( 
                        result_limit=3, 
                        truncate=True 
                    ) 
                ] 
            ) 
        )
        ```

For more examples of ScoreOperationChain refer to the [Misc Operations page in the Reference Guide](../reference/operations-guide/misc.md#scoreoperationchain).

## Score Resolver

A `ScoreResolver` is used to retreive the score associated with a provided Operation.
In most cases, the [`DefaultScoreResolver`](https://gchq.github.io/gaffer-doc/v1docs/javadoc/gaffer/uk/gov/gchq/gaffer/store/operation/resolver/DefaultScoreResolver.html) is suitable.
However, some operations require specific ways of calculating their score so will require the implementation of different scoreResolver handlers.

In the case of NamedOperations, the [`NamedOperationScoreResolver`](https://gchq.github.io/gaffer-doc/v1docs/javadoc/gaffer/uk/gov/gchq/gaffer/store/operation/resolver/named/NamedOperationScoreResolver.html) should be implemented in the OperationDeclarations.json.
This will resolve the custom Operation Score for a provided NamedOperation by looking for it in the cache. 

If choosing to score your `If` Operation, then you should implement the [`IfScoreResolver`](https://gchq.github.io/gaffer-doc/v1docs/javadoc/gaffer/uk/gov/gchq/gaffer/store/operation/resolver/IfScoreResolver.html).
This will provide the score as the maximum of the operations that are used within the `If` operation, regardless of which operation is actually executed.

The `While` Operation, if scored, will also require implementation of the specific [`WhileScoreResolver`](https://gchq.github.io/gaffer-doc/v1docs/javadoc/gaffer/uk/gov/gchq/gaffer/store/operation/resolver/WhileScoreResolver.html). 
The score will be the maximum of the transform operation and the delegate operation, multiplied by the minimum of the configured number of max repeats vs the global maximum number of allowed repeats.
This is simply because the number of actual repetitions is nondeterministic, therefore a "worst"-case scenario is considered.

## Operation Chain Limiters

`OperationChainLimiter` is a [GraphHook](../development-guide/project-structure/components/graph.md#graph-hooks) that checks a user is authorised to execute an operation chain based on that user's maximum chain score and the configured score value for each operation in the chain. 
If you wish to use the ScoreOperationChain operation and this graph hook, then both need to have the same score configuration.

To use the `OperationChainLimiter` GraphHook then you will need to configure that GraphHook to use the a Score Resolver interface. 
The ScoreOperationChainDeclaration.json declares that a NamedOperation should be resolved using a [`NamedOperationScoreResolver`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/store/operation/resolver/named/NamedOperationScoreResolver.html).
This will then allow you to have custom scores for each NamedOperation.

If you have the `OperationChainLimiter` GraphHook configured then this score will be used by
the hook to limit operation chains.

!!! example "Example hook configuration"
    
    [Configuration of the hook](../development-guide/project-structure/components/graph.md#graph-configuration) should look something like this and should be placed in your `graphConfig.json`.

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