# Misc Operations

These Operations don't fit under other pages and are not core operations.

This directed graph is used in all the examples on this page:

``` mermaid
graph TD
  1(1, count=3) -- count=3 --> 2
  1 -- count=1 --> 4
  2(2, count=1) -- count=2 --> 3
  2 -- count=1 --> 4(4, count=1)
  2 -- count=1 --> 5(5, count=3)
  3(3, count=2) -- count=4 --> 4
```

## GetFromEndpoint

Gets data from an endpoint. [Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetFromEndpoint.html)

### Example

=== "Java"

    ``` java
    final GetFromEndpoint get = new GetFromEndpoint.Builder()
            .endpoint("https://mydata.io:8443/mydata")
            .build();
    ```

=== "JSON"
    
    ``` json
    {
    "class" : "GetFromEndpoint",
    "endpoint" : "https://mydata.io:8443/mydata"
    }
    ```

=== "Python"
    
    ``` python
    g.GetFromEndpoint( 
    endpoint="https://mydata.io:8443/mydata" 
    )
    ```

## ScoreOperationChain

Determines a "score" for an OperationChain. This is used to determine whether a particular user has the required permissions to execute a given OperationChain. [Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/ScoreOperationChain.html)

### Example ScoreOperationChain

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

Results:

=== "Java"
    
    ``` java
    5
    ```

=== "JSON"
    
    ``` json
    5
    ```

### Example of ScoreOperationChain with custom named score

Here we have added a NamedOperation to the NamedOperationCache, with a custom score of 3. In our ScoreOperationChainDeclaration.json file, we have also declared that this should be resolved with a NamedOperationScoreResolver. With Limit declared as having a score of 2, the above chain correctly has a score of 5.

=== "Java"

    ``` java
    final ScoreOperationChain scoreOperationChain = new ScoreOperationChain.Builder()
            .operationChain(new OperationChain.Builder()
                    .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                            .name("1-hop")
                            .input(new EntitySeed(1))
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
        "class" : "NamedOperation",
        "input" : [ {
            "class" : "EntitySeed",
            "class" : "EntitySeed",
            "vertex" : 1
        } ],
        "operationName" : "1-hop"
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
        g.NamedOperation( 
            input=[ 
            g.EntitySeed( 
                vertex=1 
            ) 
            ], 
            operation_name="1-hop" 
        ), 
        g.Limit( 
            result_limit=3, 
            truncate=True 
        ) 
        ] 
    ) 
    )
    ```

Results:

=== "Java"
    
    ``` java
    5
    ```

=== "JSON"
    
    ``` json
    5
    ```