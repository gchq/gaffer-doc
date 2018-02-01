${HEADER}

${CODE_LINK}

This example explains how to configure your Gaffer Graph to allow named operations to be executed. 
Named operations enable encapsulation of an OperationChain into a new single NamedOperation.
The NamedOperation can be added to OperationChains and executed, just like
any other Operation. When run it executes the encapsulated OperationChain.
There are various possible uses for NamedOperations:
 * Making it simpler to run frequently used OperationChains
 * In a controlled way, allowing specific OperationChains to be run by a user that would not normally have permission to run them.

In addition to the NamedOperation there are a set of operations which manage named operations (AddNamedOperation, GetAllNamedOperations, DeleteNamedOperation).

## Configuration
You will need to configure what cache to use for storing NamedOperations. For more information on the cache service, see [Cache](cache.md).

Once you have configured the cache service, if you are using the OperationChainLimiter GraphHook then you will also need to configure
that GraphHook to use the NamedOperationScoreResolver, this will allow you to have custom scores for each named operation.
The hook configuration should look something like:

${START_JSON_CODE}
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
${END_CODE}

and the operation declarations file for registering the ScoreOperationChain operation would then look like:
${START_JSON_CODE}
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
${END_CODE}



## Using Named Operations
OK, now for some examples of using NamedOperations.

We will use the same basic schema and data from the first developer walkthrough.

Start by creating your user instance and graph as you will have done previously:

${START_JAVA_CODE}
${USER_SNIPPET}
${END_CODE}

${START_JAVA_CODE}
${GRAPH_SNIPPET}
${END_CODE}

Then add a named operation to the cache with the AddNamedOperation operation:

${START_JAVA_CODE}
${ADD_NAMED_OPERATION_SNIPPET}
${END_CODE}

The above named operation has been configured to have a score of 2. If you have
the OperationChainLimiter GraphHook configured then this score will be used by
the hook to limit operation chains.

Then create a NamedOperation and execute it

${START_JAVA_CODE}
${CREATE_NAMED_OPERATION_SNIPPET}
${END_CODE}

${START_JAVA_CODE}
${EXECUTE_NAMED_OPERATION_SNIPPET}
${END_CODE}

The results are:

```
${NAMED_OPERATION_RESULTS}
```

NamedOperations can take parameters, to allow the OperationChain executed to be configured. The parameter could be as
simple as specifying the resultLimit on a Limit operation, but specify a custom view to use in an operation, or the input to an operation.
When adding a NamedOperation with parameters the operation chain must be specified as a JSON string, with
parameter names enclosed '${' and '}'. For each parameter, a ParameterDetail object must be created which gives a description, a class type
and an optional default for the Parameter, and also indicates whether the parameter must be provided (ie. there is no default).

The following code adds a NamedOperation with a 'limitParam' parameter that allows the result limit for the OperationChain to be set:

${START_JAVA_CODE}
${ADD_NAMED_OPERATION_WITH_PARAMETERS_SNIPPET}
${END_CODE}

A NamedOperation can then be created, with a value provided for the 'limitParam' parameter:

${START_JAVA_CODE}
${CREATE_NAMED_OPERATION_WITH_PARAMETERS_SNIPPET}
${END_CODE}

and executed:

${START_JAVA_CODE}
${EXECUTE_NAMED_OPERATION_WITH_PARAMETERS_SNIPPET}
${END_CODE}

giving these results:

```
${NAMED_OPERATION_WITH_PARAMETER_RESULTS}
```

Details of all available NamedOperations can be fetched using the GetAllNamedOperations operation:

${START_JAVA_CODE}
${GET_ALL_NAMED_OPERATIONS_SNIPPET}
${END_CODE}

That gives the following result:

```
${ALL_NAMED_OPERATIONS}
```

For other named operation examples see [NamedOperation](../operations/namedoperation.md).
