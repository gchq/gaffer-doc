# Join Operation

This operation joins two iterables together. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/impl/join/Join.html)

There are three different types of Join:

* FULL - returns all objects in the key, along with any matched objects from the other side
* INNER - returns all keys which matched with objects on the other side
* OUTER - returns all keys which didn't match with objects from the other side

A Join operation can key by the left (input) or right hand side (output of the operation specified) and outputs an iterable of MapTuples. These Tuples contain the left and right outputs.

A join operation must be supplied with a match method. This tells the operation how to determine what is and what isn't a match. There are two built in match methods:

* ElementMatch - Matches elements of the same id(s), group and group by properties
* KeyFunctionMatch - Matches any objects based on two key functions. The first key function applies to whatever the join type is (the object on the left hand side for Left keyed join and vice versa for the right).

Once matched, the left and right sides are outputted as MapTuples keyed by "LEFT" and "RIGHT". The output is flattened by default (one left value for each right value) but this can be turned off using the flatten flag. Setting the flatten flag to false will cause the non keyed side to be summarised in a list.

## Examples

The following Join examples use these input elements to query [this directed graph](https://gchq.github.io/gaffer-doc/latest/reference/operations-guide/core/):
``` json
[ {
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
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 5,
  "properties" : {
    "count" : 3
  }
}, {
  "class" : "uk.gov.gchq.gaffer.data.element.Entity",
  "group" : "entity",
  "vertex" : 6,
  "properties" : {
    "count" : 30
  }
} ]
```

### Left key inner join

??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "LEFT",
            "flatten" : false,
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="LEFT", 
            join_type="INNER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            } ]
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            },
            "RIGHT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            } ]
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            } ]
        }
        } ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "LEFT",
            "flatten" : false,
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
                operation=g.GetAllElements(), 
                match_method=g.KeyFunctionMatch(
                    first_key_function=g.FunctionChain([
                        g.ExtractProperty("count"),
                        g.Increment(increment=1)
                        ]),
                    second_key_function=g.ExtractProperty("count")
                ),
                input=[ 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex="1" 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 1}, 
                    vertex="4" 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex="5" 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 30}, 
                    vertex="6" 
                    ) 
            ], 
            flatten=False, 
            match_key="LEFT", 
            join_type="INNER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Edge[vertex=4,group=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]], Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 1,
                            "properties": {
                                "count": 3
                            }
                        },
                    "RIGHT": [
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Edge",
                            "group": "edge",
                            "source": 3,
                            "destination": 4,
                            "directed": true,
                            "properties": {
                                "count": 4
                            }
                        }
                    ]
                }
            },
            {
                "values": {
                    "LEFT": {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 4,
                            "properties": {
                                "count": 1
                            }
                        },
                    "RIGHT": [
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Edge",
                            "group": "edge",
                            "source": 2,
                            "destination": 3,
                            "directed": true,
                            "properties": {
                                "count": 2
                            }
                        },
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 3,
                            "properties": {
                                "count": 2
                            }
                        }
                    ]
            },
            {
                "values": {
                    "LEFT": {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 5,
                            "properties": {
                                "count": 3
                            }
                        },
                    "RIGHT": [
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Edge",
                            "group": "edge",
                            "source": 3,
                            "destination": 4,
                            "directed": true,
                            "properties": {
                                "count": 4
                            }
                        },
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 3,
                            "properties": {
                                "count": 2
                                }
                            }
                        ]
                    }
                }
            }
        ]
        ```

### Flattened left key inner join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            join_type="INNER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                        "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                            "count": 3
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                        "count": 1
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                            "count": 1
                        }
                        }
            }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                        "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                            "count": 3
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 6,
                        "properties": {
                        "count": 30
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 6,
                        "properties": {
                            "count": 30
                        }
                    }
                }
            }
        ]
        ```

??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "LEFT",
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
                operation=g.GetAllElements(), 
                match_method=g.KeyFunctionMatch(
                    first_key_function=g.FunctionChain([
                        g.ExtractProperty("count"),
                        g.Increment(increment=1)
                        ]),
                    second_key_function=g.ExtractProperty("count")
                ),
                input=[ 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex=1 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 1}, 
                    vertex=4 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex=5 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 30}, 
                    vertex=6 
                    ) 
            ], 
            match_key="LEFT", 
            join_type="INNER" 
            ) 
        ] 
        )   
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Edge[vertex=4,group=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ] 
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 3,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                            "count": 4
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                            "count": 1
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 3,
                        "directed": true,
                        "properties": {
                            "count": 2
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                            "count": 1
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 3,
                        "properties": {
                            "count": 2
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 3,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                            "count": 4
                        }
                    }
                }
            },
        ]
        ```


### Right key inner join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "RIGHT",
            "flatten" : false,
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="RIGHT", 
            join_type="INNER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        ```

    === "JSON"
        
        ``` json
        [ 
            {
            "values" : {
                "LEFT" : [
                    {
                    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                    "group" : "entity",
                    "vertex" : 5,
                    "properties" : {
                        "count" : 3
                        }
                    } 
                ],
                "RIGHT" : {
                    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                    "group" : "entity",
                    "vertex" : 5,
                    "properties" : {
                        "count" : 3
                    }
                }
            }
        }, {
        "values" : {
            "LEFT" : [ 
                {
                "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                "group" : "entity",
                "vertex" : 1,
                "properties" : {
                    "count" : 3
                    }
                }
            ],
            "RIGHT" : {
                "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                "group" : "entity",
                "vertex" : 1,
                "properties" : {
                    "count" : 3
                }
            }
        }
        }, {
            "values" : {
                "LEFT" : [
                    {
                    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                    "group" : "entity",
                    "vertex" : 4,
                    "properties" : {
                        "count" : 1
                        }
                    }
                ],
                "RIGHT" : {
                    "class" : "uk.gov.gchq.gaffer.data.element.Entity",
                    "group" : "entity",
                    "vertex" : 4,
                    "properties" : {
                        "count" : 1
                    }
                }
            }
        } 
        ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "RIGHT",
            "flatten": false,
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                first_key_function=g.FunctionChain([
                    g.ExtractProperty("count"),
                    g.Increment(increment=1)
                    ]),
                second_key_function=g.ExtractProperty("count")
                ), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="RIGHT", 
            join_type="INNER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3], Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> [Edge[roup=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3], Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": [
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 1,
                            "properties": {
                                "count": 3
                            }
                        },
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 5,
                            "properties": {
                                "count": 3
                            }
                        }
                    ],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 3,
                        "directed": true,
                        "properties": {
                            "count": 2
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 1,
                            "properties": {
                                "count": 3
                            }
                        },
                        {
                            "class": "uk.gov.gchq.gaffer.data.element.Entity",
                            "group": "entity",
                            "vertex": 5,
                            "properties": {
                                "count": 3
                            }
                        }
                    ],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 3,
                        "properties": {
                        "count": 2
                        }
                    }
                }
            }
        ]
        ```

### Flattened right key inner join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .matchKey(MatchKey.RIGHT)
                        .joinType(JoinType.INNER)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "RIGHT",
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="RIGHT", 
            join_type="INNER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }
        }
        } ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.INNER)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "RIGHT",
            "joinType" : "INNER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                first_key_function=g.FunctionChain([
                    g.ExtractProperty("count"),
                    g.Increment(increment=1)
                    ]),
                second_key_function=g.ExtractProperty("count")
                ), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="RIGHT", 
            join_type="INNER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[roup=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[roup=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]  
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 3,
                        "directed": true,
                        "properties": {
                        "count": 2
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 3,
                        "directed": true,
                        "properties": {
                        "count": 2
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 3,
                        "properties": {
                            "count": 2
                        }
                    },
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 3,
                        "properties": {
                            "count": 2
                        }
                    },
                }
            }
        ]
        ```

### Left key full join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "LEFT",
            "flatten" : false,
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="LEFT", 
            join_type="FULL" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> [] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            } ]
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            },
            "RIGHT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            } ]
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            } ]
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            },
            "RIGHT" : [ ]
        }
        } ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "LEFT",
            "flatten": false,
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
                operation=g.GetAllElements(), 
                match_method=g.KeyFunctionMatch(
                    first_key_function=g.FunctionChain([
                        g.ExtractProperty("count"),
                        g.Increment(increment=1)
                        ]),
                    second_key_function=g.ExtractProperty("count")
                ),
                input=[ 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex=1 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 1}, 
                    vertex=4 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex=5 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 30}, 
                    vertex=6 
                    ) 
            ], 
            flatten=False,
            match_key="LEFT",
            join_type="FULL" 
            ) 
        ] 
        )         ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Edge[group=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]], Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> [] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": [
                        {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": "4",
                        "destination": 4,
                        "directed": true,
                        "properties": {
                            "count": 4
                        }
                        }
                    ]
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                            "count": 1
                        }
                    },
                    "RIGHT": [
                        {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 3,
                        "directed": true,
                        "properties": {
                            "count": 2
                        }
                        },
                        {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 3,
                        "properties": {
                            "count": 2
                        }
                    }
                    ]
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                            "count": 3
                        }
                    },
                    "RIGHT": [
                        {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 3,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                            "count": 4
                        }
                        }
                    ]
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 6,
                        "properties": {
                            "count": 30
                        }
                    },
                    "RIGHT": []
                }
            }
        ]
        ```

### Flattened left key full join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "LEFT",
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="LEFT", 
            join_type="FULL" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> null ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            },
            "RIGHT" : null
        }
        } ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "LEFT",
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
                operation=g.GetAllElements(), 
                match_method=g.KeyFunctionMatch(
                    first_key_function=g.FunctionChain([
                        g.ExtractProperty("count"),
                        g.Increment(increment=1)
                        ]),
                    second_key_function=g.ExtractProperty("count")
                ),
                input=[ 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex=1
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 1}, 
                    vertex=4 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 3}, 
                    vertex=5 
                    ), 
                    g.Entity( 
                    group="entity", 
                    properties={"count": 30}, 
                    vertex=6 
                    ) 
            ], 
            match_key="LEFT", 
            join_type="FULL" 
            ) 
        ] 
        ) 
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Edge[group=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> null ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                            "count": 3
                        }
                    },
                "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 3,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                            "count": 4
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                            "count": 1
                        }
                    },
                "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 3,
                        "directed": true,
                        "properties": {
                            "count": 2
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                            "count": 1
                        }
                    },
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 3,
                        "properties": {
                        "   count": 2
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                            "count": 3
                        }
                    },
                "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 3,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                            "count": 4
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 6,
                        "properties": {
                            "count": 30
                        }
                    }
                }
            }
        ]
        ```

### Right key full join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "RIGHT",
            "flatten" : false,
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="RIGHT", 
            join_type="FULL" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ [] --> Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ [] --> Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]
        [ [] --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ [] --> Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
        [ [] --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
        [ [] --> Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ [] --> Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
        [ [] --> Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            } ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 3,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 4
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 2,
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            } ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            } ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 3,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 3,
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 2,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 5,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        } ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "RIGHT",
            "flatten": false,
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                    first_key_function=g.FunctionChain([
                        g.ExtractProperty("count"),
                        g.Increment(increment=1)
                        ]),
                    second_key_function=g.ExtractProperty("count")
                ),            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="RIGHT", 
            join_type="FULL" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ [] --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ [] --> [Edge[group=edge,source=1,destination=2,directed=true,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ [] --> [Edge[group=edge,source=1,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]],Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ [] --> [Edge[group=edge,source=2,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Edge[group=edge,source=2,destination=5,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]],Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ [] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ [] --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 1,
                    "properties": {
                    "count": 3
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 1,
                    "destination": 2,
                    "directed": true,
                    "properties": {
                    "count": 3
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": "1",
                    "destination": 4,
                    "directed": true,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 2,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [
                    {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 1,
                    "properties": {
                        "count": 3
                    }
                    },
                    {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 5,
                    "properties": {
                        "count": 3
                    }
                    }
                ],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 2,
                    "destination": 3,
                    "directed": true,
                    "properties": {
                    "count": 2
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 2,
                    "destination": 4,
                    "directed": true,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 2,
                    "destination": 5,
                    "directed": true,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [
                    {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 1,
                    "properties": {
                        "count": 3
                    }
                    },
                    {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 5,
                    "properties": {
                        "count": 3
                    }
                    }
                ],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 3,
                    "properties": {
                    "count": 2
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 3,
                    "destination": 4,
                    "directed": true,
                    "properties": {
                    "count": 4
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 4,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": [],
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 5,
                    "properties": {
                    "count": 3
                    }
                }
                }
            }
        ]
        ```

### Flattened right key full join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "RIGHT",
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="RIGHT", 
            join_type="FULL" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ null --> Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ null --> Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]
        [ null --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] ]
        [ Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] --> Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ null --> Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
        [ null --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
        [ null --> Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ null --> Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
        [ null --> Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 3,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 4
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 2,
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            },
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 3,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 3,
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 2,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 5,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        } ]
        ```

??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.FULL)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "RIGHT",
            "joinType" : "FULL"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                first_key_function=g.FunctionChain([
                    g.ExtractProperty("count"),
                    g.Increment(increment=1)
                    ]),
                second_key_function=g.ExtractProperty("count")
            ),
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="RIGHT", 
            join_type="FULL" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ null --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ null --> [Edge[group=edge,source=1,destination=2,directed=true,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ null --> [Edge[group=edge,source=1,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Edge[group=edge,source=2,destination=3,directed=true,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ null --> [Edge[group=edge,source=2,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Edge[group=edge,source=2,destination=5,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]] --> [Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]] ]
        [ null --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ null --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 1,
                    "properties": {
                    "count": 3
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 1,
                    "destination": 2,
                    "directed": true,
                    "properties": {
                    "count": 3
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 1,
                    "destination": 4,
                    "directed": true,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 2,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 1,
                    "properties": {
                    "count": 3
                    }
                },
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 2,
                    "destination": 3,
                    "directed": true,
                    "properties": {
                    "count": 2
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 5,
                    "properties": {
                    "count": 3
                    }
                },
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 2,
                    "destination": 3,
                    "directed": true,
                    "properties": {
                    "count": 2
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 2,
                    "destination": 4,
                    "directed": true,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 2,
                    "destination": 5,
                    "directed": true,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 1,
                    "properties": {
                    "count": 3
                    }
                },
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 3,
                    "properties": {
                    "count": 2
                    }
                }
                }
            },
            {
                "values": {
                "LEFT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 5,
                    "properties": {
                    "count": 3
                    }
                },
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 3,
                    "properties": {
                    "count": 2
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Edge",
                    "group": "edge",
                    "source": 3,
                    "destination": 4,
                    "directed": true,
                    "properties": {
                    "count": 4
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 4,
                    "properties": {
                    "count": 1
                    }
                }
                }
            },
            {
                "values": {
                "RIGHT": {
                    "class": "uk.gov.gchq.gaffer.data.element.Entity",
                    "group": "entity",
                    "vertex": 5,
                    "properties": {
                    "count": 3
                    }
                }
                }
            }
        ]
        ```

### Left key outer join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "LEFT",
            "flatten" : false,
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="LEFT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> [] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            },
            "RIGHT" : [ ]
        }
        } ]
        ```

??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.LEFT)
                        .flatten(false)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "LEFT",
            "flatten": false,
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                first_key_function=g.FunctionChain([
                    g.ExtractProperty("count"),
                    g.Increment(increment=1)
                    ]),
                second_key_function=g.ExtractProperty("count")
                ),
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="LEFT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> [] ]

        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 6,
                        "properties": {
                            "count": 30
                        }
                    },
                    "RIGHT": []
                }
            },
        ]
        ```

### Flattened left key outer join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "LEFT",
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="LEFT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> null ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            },
            "RIGHT" : null
        }
        } ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.LEFT)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "LEFT",
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                first_key_function=g.FunctionChain([
                    g.ExtractProperty("count"),
                    g.Increment(increment=1)
                    ]),
                second_key_function=g.ExtractProperty("count")
                ), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="LEFT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ Entity[vertex=6,group=entity,properties=Properties[count=<java.lang.Integer>30]] --> null ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 6,
                        "properties": {
                            "count": 30
                        }
                    }
                }
            }
        ]
        ```

### Right key outer join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "RIGHT",
            "flatten" : false,
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="RIGHT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ [] --> Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ [] --> Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]
        [ [] --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ [] --> Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
        [ [] --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
        [ [] --> Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ [] --> Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
        [ [] --> Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 3,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 4
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 2,
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 3,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 3,
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 2,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : [ ],
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 5,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        } ]
        ```
??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.RIGHT)
                        .flatten(false)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "RIGHT",
            "flatten": false,
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                first_key_function=g.FunctionChain([
                    g.ExtractProperty("count"),
                    g.Increment(increment=1)
                    ]),
                second_key_function=g.ExtractProperty("count")
                ), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            flatten=False, 
            match_key="RIGHT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ [] --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ [] --> [Edge[group=edge,source=1,destination=2,directed=true,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ [] --> [Edge[group=edge,source=1,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Edge[group=edge,source=2,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Edge[group=edge,source=2,destination=5,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ [] --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ [] --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                        "count": 3
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 1,
                        "destination": 2,
                        "directed": true,
                        "properties": {
                        "count": 3
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 1,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 2,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 5,
                        "directed": true,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 3,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                        "count": 4
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "LEFT": [],
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                        "count": 3
                        }
                    }
                }
            }
        ]
        ```

### Flattened right key outer join
??? example "ElementMatch example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new ElementMatch("count"))
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [ {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            } ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "ElementMatch"
            },
            "matchKey" : "RIGHT",
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.ElementMatch(), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="RIGHT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ null --> Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ null --> Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]] ]
        [ null --> Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]] ]
        [ null --> Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]] ]
        [ null --> Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]] ]
        [ null --> Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        [ null --> Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]] ]
        [ null --> Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]] ]
        ```

    === "JSON"
        
        ``` json
        [ {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 3,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 4
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 2,
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 3,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Entity",
            "group" : "entity",
            "vertex" : 3,
            "properties" : {
                "count" : 2
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 4,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 1,
            "destination" : 2,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 3
            }
            }
        }
        }, {
        "values" : {
            "LEFT" : null,
            "RIGHT" : {
            "class" : "uk.gov.gchq.gaffer.data.element.Edge",
            "group" : "edge",
            "source" : 2,
            "destination" : 5,
            "directed" : true,
            "matchedVertex" : "SOURCE",
            "properties" : {
                "count" : 1
            }
            }
        }
        } ]
        ```

??? example "KeyFunctionMatch with FunctionChain example"
    === "Java"

        ``` java
        final OperationChain<Iterable<? extends MapTuple>> opChain = new OperationChain.Builder()
                .first(new Join.Builder<>()
                        .input(inputElements)
                        .operation(new GetAllElements())
                        .joinType(JoinType.OUTER)
                        .matchKey(MatchKey.RIGHT)
                        .matchMethod(new KeyFunctionMatch.Builder()
                            .firstKeyFunction(new FunctionChain(new ExtractProperty("count"), new Increment(1)))
                            .secondKeyFunction(new ExtractProperty("count"))
                            .build())
                        .build())
                .build();
        ```

    === "JSON"
        
        ``` json
        {
        "class" : "OperationChain",
        "operations" : [ {
            "class" : "Join",
            "input" : [{
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 1,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 4,
            "properties" : {
                "count" : 1
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 5,
            "properties" : {
                "count" : 3
            }
            }, {
            "class" : "Entity",
            "group" : "entity",
            "vertex" : 6,
            "properties" : {
                "count" : 30
            }
            }
            ],
            "operation" : {
            "class" : "GetAllElements"
            },
            "matchMethod" : {
            "class" : "KeyFunctionMatch",
            "firstKeyFunction": {
            "class" : "FunctionChain",
            "functions" : [ {
                "class" : "ExtractProperty",
                "name": "count"
            }, {
            "class" : "Increment",
            "increment" : 1
            }
            ]
            },
            "secondKeyFunction": {
                "class": "ExtractProperty",
                "name": "count"  
                }
            },
            "matchKey": "RIGHT",
            "joinType" : "OUTER"
        } ]
        }
        ```

    === "Python"
        
        ``` python
        g.OperationChain( 
        operations=[ 
            g.Join( 
            operation=g.GetAllElements(), 
            match_method=g.KeyFunctionMatch(
                first_key_function=g.FunctionChain([
                    g.ExtractProperty("count"),
                    g.Increment(increment=1)
                    ]),
                second_key_function=g.ExtractProperty("count")
                ), 
            input=[ 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=1 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 1}, 
                vertex=4 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 3}, 
                vertex=5 
                ), 
                g.Entity( 
                group="entity", 
                properties={'count': 30}, 
                vertex=6 
                ) 
            ], 
            match_key="RIGHT", 
            join_type="OUTER" 
            ) 
        ] 
        )
        ```

    Results:

    === "Java"
        
        ``` java
        [ null --> [Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ null --> [Edge[group=edge,source=1,destination=2,directed=true,properties=Properties[count=<java.lang.Integer>3]]] ]
        [ null --> [Edge[group=edge,source=1,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Edge[group=edge,source=2,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Edge[group=edge,source=2,destination=5,directed=true,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Edge[group=edge,source=3,destination=4,directed=true,properties=Properties[count=<java.lang.Integer>4]]] ]
        [ null --> [Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]] ]
        [ null --> [Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]] ]
        ```

    === "JSON"
        
        ``` json
        [
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 1,
                        "properties": {
                        "count": 3
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 1,
                        "destination": 2,
                        "directed": true,
                        "properties": {
                        "count": 3
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 1,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 2,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 2,
                        "destination": 5,
                        "directed": true,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Edge",
                        "group": "edge",
                        "source": 3,
                        "destination": 4,
                        "directed": true,
                        "properties": {
                        "count": 4
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 4,
                        "properties": {
                        "count": 1
                        }
                    }
                }
            },
            {
                "values": {
                    "RIGHT": {
                        "class": "uk.gov.gchq.gaffer.data.element.Entity",
                        "group": "entity",
                        "vertex": 5,
                        "properties": {
                        "count": 3
                        }
                    }
                }
            }
        ]
        ```