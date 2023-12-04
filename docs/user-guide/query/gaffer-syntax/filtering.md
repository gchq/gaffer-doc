# Filtering Data

One of the key parts of querying data is being able to filter out bits you may
not want. This page will cover some of the common ways you can filter the
returned data from an Operation to get the data you want.

In Gaffer the main way you filter data is by applying whats known as a `View` to
a returned set of elements. A `View`, as the name suggests, allows you to view
the data in a different way; this can be either via a filter, aggregation,
transformation or just general manipulation of the results.

Use cases with a `View` usually fall into one of the following catagories:

- **Filtering** - General filtering on elements based on predicates. Filtering
    can be applied pre-aggregation, post aggregation and post transformation.

- **Aggregation** - This is to control how similar elements are aggregated
    together. You can provide a subset of the schema `groupBy` properties and
    override existing aggregation functions.

- **Transformation** - Transformations can be applied by providing Functions to
    transform properties and vertex values. This is a powerful feature, you can
    override the existing values or you can transform and save the new value
    into a new transient property.

- **Property Removal** - The relevant properties you want to be returned can be
    controlled. You can use either `properties` or `excludeProperties` to define
    the list of properties to be included or excluded.

## Filtering in Practice

We will demonstrate general filtering on a query. Take the following graph as a
basic example, where we have a `Person` node with ID `John` that has a few
`Created` edges with a `weight` property on them.

```mermaid
graph LR
    A(["Person

        ID: John"])
    --
    "Created
     weight: 0.2"
    -->
    B(["Software

        ID: 1"])
    A
    --
    "Created
     weight: 0.6"
    -->
    C(["Software

        ID: 2"])
```

Lets say the `weight` property represents how much a `Person` contributed to
creating something and that we wanted to only find the most significant
contributions. To do this we can apply a filter to act as a threshold to only
get edges with a `weight` more than a specific value.

First we use a simple query to get the node with ID `John` and any edges
associated with it. Then we can apply a filter to include only edges where the
`weight` property is over a certain value.

!!! example ""
    In this scenario it is analogous to asking, *"Get all the `Created` edges on
    node `John` that have a `weight` greater than 0.4"*.

    === "Java"

        ```java
        // Define the View to use
        final View viewWithFilters = new View.Builder()
            .edge("Created", new ViewElementDefinition.Builder()
                    .preAggregationFilter(new ElementFilter.Builder()
                            .select("weight")
                            .execute(new IsMoreThan(0.4))
                            .build())
                    .build())
            .build();

        // Create the operation to execute
        final GetElements operation = new GetElements.Builder()
            .input(new EntitySeed("John"))
            .view(viewWithFilters)
            .build();

        graph.execute(operation, user);
        ```

    === "JSON"

        ```json
        {
            "class": "GetElements",
            "input": [
                {
                    "class": "EntitySeed",
                    "vertex": "John"
                }
            ],
            "view": {
                "edges": {
                    "Created": {
                        "preAggregationFilterFunctions": [
                            {
                                "selection": [
                                    "weight"
                                ],
                                "predicate": {
                                    "class": "IsMoreThan",
                                    "orEqualTo": false,
                                    "value": {
                                        "Float": 0.4
                                    }
                                }
                            }
                        ]
                    }
                }
            }
        }
        ```

    === "Python"

        ```python
        elements = g_connector.execute_operation(
            operation = gaffer.GetElements(
                input = [gaffer.EntitySeed(vertex = "John")]
                view = gaffer.View(
                    edges = [
                        gaffer.ElementDefinition(
                            group = 'Created',
                            pre_aggregation_filter_functions = [
                                gaffer.PredicateContext(
                                    selection = ['weight'],
                                    predicate = gaffer.IsMoreThan(
                                        value = {'java.lang.Float': 0.4},
                                        or_equal_to = False
                                    )
                                )
                            ]
                        )
                    ]
                )
            )
        )
        ```

To form relevant filters and queries it is usually required that you know the
graph schema in use. The schema determines what properties and elements you can
reference in your queries and the general structure of the data in the graph.
For an introduction and background on Gaffer schemas [please see the guide](../../schema.md).

!!! tip
    As you can see filtering is based around predicates which are similar to if
    else statements in traditional programming. For a full list of available
    predicates refer to the [reference documentation](../../../reference/predicates-guide/predicates.md).

### Filtering Properties

If you are only interested in specific properties then it is more efficient to
tell Gaffer to only return those properties. This can be easily achieved by
applying the `properties` or `excludeProperties` field to a `View`.

If we take a similar example to before but instead add a couple of properties
to both the `Person` and `Created` elements to give a graph like the following:

```mermaid
graph LR
    A(["Person

        ID: John
        age: 34"])
    --
    "Created
     weight: 0.2
     hours: 100"
    -->
    B(["Software

        ID: 1"])
    A
    --
    "Created
     weight: 0.6
     hours: 800"
    -->
    C(["Software

        ID: 2"])
```

Now as before we can run a query on this graph to get the elements relevant to
the `Person` node. However, this time we will filter so that only specific
properties are returned.

!!! example ""
    Here we are asking to only include the `hours` property from the `Created`
    edges in the output, and specifically excluding the `age` property from any
    returned `Person` entities.

    === "Java"

        ```java
        // Define the View to use
        final View viewWithFilters = new View.Builder()
            .edge("Created", new ViewElementDefinition.Builder()
                    .properties("hours")
                    .build())
            .entities("Person", new ViewElementDefinition.Builder()
                    .excludeProperties("age")
                    .build())
            .build();

        // Create the operation to execute
        final GetElements operation = new GetElements.Builder()
            .input(new EntitySeed("John"))
            .view(viewWithFilters)
            .build();

        graph.execute(operation, user);
        ```


    === "JSON"

        ```json
        {
            "class": "GetElements",
            "input": [
                {
                    "class": "EntitySeed",
                    "vertex": "John"
                }
            ],
            "view": {
                "edges": {
                    "Created": {
                        "properties" : [ "hours" ]
                    }
                },
                "entities" : {
                    "Person" : {
                        "excludeProperties" : [ "age" ]
                    }
                }
            }
        }
        ```

    === "Python"

        ```python
        elements = g_connector.execute_operation(
            operation = gaffer.GetElements(
                input = [gaffer.EntitySeed(vertex = "John")]
                view = gaffer.View(
                    edges = [
                        gaffer.ElementDefinition(
                            group = 'Created',
                            properties = [ "hours" ]
                        )
                    ]
                    entities = [
                        gaffer.ElementDefinition(
                            group = 'Person',
                            exclude_properties = [ "age" ]
                        )
                    ]
                )
            )
        )
        ```

## Transformation

It is possible to apply a transformation to the output of a query which then
gives you the opportunity to manipulate the results into a more useful output.

When a transform is applied the new results are saved into what is known as a
transient property. A transient property is just a property that is not
persisted; simply created at query time by a transform function.

To use a transform you must use a transform function, this is a Java class
that extends the [`java.util.Function`](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)
class to take some input and give a new output. Commonly you would want to
write your own transform function class as it can be quite specific to your
graph data and what analytics you want to get out of it. However, the
[Koryphe module](../../../reference/functions-guide/koryphe-functions.md)
(included by default with Gaffer) provides some default functions you can
make use of.

As an example of transformation we will use the same graph from the [previous
section](#filtering-properties) to transform the `hours` property into minutes
and save the returned information into a new `minutes` transient property.

!!! example ""
    Here you can see we select the inputs for the function as the `"hours"`
    property we then use the `MultiplyBy` Koryphe function to transform a
    property and project the result into a transient property named `"minutes"`.

    === "Java"

        ```java
        final GetElements getEdgesWithMinutes = new GetElements.Builder()
            .input(new EntitySeed("John"))
            .view(new View.Builder()
                    .edge("Created", new ViewElementDefinition.Builder()
                            .transientProperty("minutes", Integer.class)
                            .transformer(new MultiplyBy(60))
                            .build())
                    .build())
            .build();

        graph.execute(getEdgesWithMinutes, user);
        ```

    === "JSON"

        ```json
        {
            "class": "GetElements",
            "input": [
                {
                    "class": "EntitySeed",
                    "vertex": "John"
                }
            ],
            "view": {
                "edges": {
                    "Created": {
                        "transientProperties" : {
                            "minutes" : "java.lang.Integer"
                        },
                        "transformFunctions" : [
                            {
                                "selection" : [ "hours" ],
                                "function" : {
                                    "class" : "MultiplyBy",
                                    "by" : 60
                                },
                                "projection" : [ "minutes" ]
                            }
                        ]
                    }

                }
            }
        }
        ```

    === "Python"

        ```python
        elements = g_connector.execute_operation(
            operation = gaffer.GetElements(
                input = [gaffer.EntitySeed(vertex = "John")]
                view = gaffer.View(
                    edges = [
                        gaffer.ElementDefinition(
                            group = 'Created',
                            transient_properties = {'minutes': 'java.lang.Integer'},
                            transform_functions = [
                                gaffer.FunctionContext(
                                    selection = [ "hours" ],
                                    function = gaffer.MultiplyBy(by = 60),
                                    projection = [ "minutes" ]
                                )
                            ]
                        )
                    ]
                )
            )
        )
        ```

The `selection` in a transform is similar to the way we select properties and
identifiers in a filter and as demonstrated you can select (and also project)
any property but also any of these unique identifiers:

- `VERTEX` - This is the vertex on an Entity.
- `SOURCE` - This is the source vertex on an Edge.
- `DESTINATION` - This is the destination vertex on an Edge.
- `DIRECTED` - This is the directed field on an Edge.
- `MATCHED_VERTEX` - This is the vertex that was matched in the query, either
  the `SOURCE` or the `DESTINATION`.
- `ADJACENT_MATCHED_VERTEX` - This is the adjacent vertex that was matched in
  the query, either the `SOURCE` or the `DESTINATION`. For example, if your seed
  matches the source of the edge this would resolve to the `DESTINATION` value.

## Query-time Aggregation

Gaffer allows grouping results together based on their properties to form a new
result, this is known as aggregation. Aggregation can be applied at both data
ingest or query-time, this guide will focus on the latter but an overview of
both techniques is available in the [gaffer basics
guide](../../gaffer-basics/what-is-aggregation.md).

Generally to apply aggregation at query-time you must override what is known as
the `groupBy` property to create your own aggregator in the query. To demonstrate
this we will use the following example graph.

!!! example ""
    A simple graph representing commits by a `Person` to a `Repository` with
    each `Commit` being an edge with properties for the lines `added` and
    `removed`.

    ```mermaid
    graph LR
        A(["Person

            ID: John"])
        --
        "Commit
         added: 10
         removed: 3"
        -->
        B(["Repository

            ID: 1"])
        A
        --
        "Commit
         added: 3
         removed: 5"
        -->
        B
    ```

We will use aggregation to group the properties of the `Commit` edges to get a
total for all the `added` property.

!!! example ""
    Usually a result would contain all the edges on the `Person` node but instead
    we have applied aggregation so the result will contain an element with
    a `Sum` of all the `added` properties.

    === "Java"

        ```java
        final GetElements getEdgesAggregated = new GetElements.Builder()
            .input(new EntitySeed("John"))
            .view(new View.Builder()
                    .edge("Commit", new ViewElementDefinition.Builder()
                            .groupBy()
                            .aggregator(new ElementAggregator.Builder()
                                .select("added")
                                .execute(new Sum())
                                .build()))
                    .build())
            .build();

        graph.execute(getEdgesAggregated, user);
        ```
        
    === "JSON"

        ```json
        {
            "class": "GetElements",
            "input": [
                {
                    "class": "EntitySeed",
                    "vertex": "John"
                }
            ],
            "view": {
                "edges": {
                    "Commit": {
                        "groupBy" : [ ],
                        "aggregator" : {
                            "operators" : [
                                {
                                    "selection" : [ "added" ],
                                    "binaryOperator" : {
                                        "class" : "Sum"
                                    }
                                }
                            ]
                        }
                    }

                }
            }
        }
        ```

    === "Python"

        ```python
        elements = g_connector.execute_operation(
            operation = gaffer.GetElements(
                input = [gaffer.EntitySeed(vertex = "John")]
                view = gaffer.View(
                    edges = [
                        gaffer.ElementDefinition(
                            group = 'Commit',
                            group_by = [],
                            aggregate_functions = [
                                gaffer.BinaryOperatorContext(
                                    selection=[ "added" ],
                                    binary_operator = gaffer.Sum()
                                )
                            ]
                        )
                    ]
                )
            )
        )
        ```

!!! tip
    As with some of the other examples we again use a class from the Koryphe
    module to help with the aggregation, please see the [reference
    material](../../../reference/binary-operators-guide/koryphe-operators.md)
    for a full list and examples.