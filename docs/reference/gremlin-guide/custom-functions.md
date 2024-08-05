# GafferPop Custom Functions

This page details each additional function that is available in
the Gaffer Gremlin API (GafferPop). These functions may be exclusive
to GafferPop so are likely not available in other databases that
support the Gremlin query language.

All examples featured on this page assume the [Tinkerpop modern dataset](https://tinkerpop.apache.org/docs/current/images/tinkerpop-modern.png).

## OpenCypher Extensions

The following are additional functions provided by the [OpenCypher extensions](https://github.com/opencypher/cypher-for-gremlin/tree/master/tinkerpop/cypher-gremlin-extensions)
for Gremlin.

## cypherToString

Can be used to cast the input value to a `String` type.

!!! example

    ```groovy
    g.V().values('age').map(cypherToString())
    ```

    Result:

    ```text
    ['27', '29', '35', '32']
    ```

## cypherToBoolean

Can be used to cast the input value to a `Boolean` type. Will convert `String`
representations e.g. "true" or "false", otherwise return a Cypher null value.

!!! example

    ```groovy
    g.V().values('name').map(cypherToBoolean())
    ```

    Result:

    ```text
    ['  cypher.null',
     '  cypher.null',
     '  cypher.null',
     '  cypher.null',
     '  cypher.null',
     '  cypher.null']
    ```

## cypherToInteger

Can be used to cast the input value to an `Integer` type. Note if value is
a `Float` or `Double` rounding may be applied.

!!! example

    ```groovy
    g.E().values('weight').map(cypherToInteger())
    ```

    Result:

    ```text
    [0, 0, 0, 1, 1, 0]
    ```

## cypherToFloat

Can be used to cast the input value to a `Float` type.

!!! example

    ```groovy
    g.V().values('age').map(cypherToFloat())
    ```

    Result:

    ```text
    [27.0, 29.0, 35.0, 32.0]
    ```

## cypherRound

Can be used to round numerical input values.

!!! example

    ```groovy
    g.E().values('weight').map(cypherRound())
    ```

    Result:

    ```text
    [0, 0, 0, 1, 1, 1]
    ```

## cypherProperties

Used to extract only the properties from the input `Vertex` or `Edge`.
Similar to running an `elementMap()` or `propertyMap()` step.

!!! example

    ```groovy
    g.V().map(cypherProperties())
    ```

    Result:

    ```json
    [{'name': 'vadas', 'age': 27},
     {'name': 'ripple', 'lang': 'java'},
     {'name': 'marko', 'age': 29},
     {'name': 'peter', 'age': 35},
     {'name': 'josh', 'age': 32},
     {'name': 'lop', 'lang': 'java'}]
    ```

## cypherSize

Can be used to return the size of a `Collection` or `String`.

!!! example

    ```groovy
    g.E().values('weight').fold().map(cypherSize())
    ```

    Result:

    ```text
    [6]
    ```

## cypherPlus

Can be used to add two inputs together to act like the plus operator
in Cypher e.g. `RETURN 1 + 2`.

Note this requires a slightly more complex input of pair list e.g. `[1, 2]` to
use in a Gremlin query. The example below uses a `project()` step to pair each
'age' with the constant `1` so that each age is incremented by 1 in the result.

!!! example

    ```groovy
    g.V().hasLabel('person')
        .project('result')
            .by(__.project('a', 'b')
                .by(__.values('age'))
                .by(__.constant(1))
                .select(values)
                .map(cypherPlus()))
    ```

    Result:

    ```text
    [{'result': 28}, {'result': 30}, {'result': 36}, {'result': 33}]
    ```

## cypherReverse

Can be used to reverse a `Collection` or `String`.

!!! example

    ```groovy
    g.V().values('name').map(cypherReverse())
    ```

    Result:

    ```text
    ['sadav', 'elppir', 'okram', 'retep', 'hsoj', 'pol']
    ```

## cypherSubstring

Can be used to extract a substring from the result by using indexes for start
and or end point of the string.

Note requires complex input pairs by using the project step for the input string
and index. The example below cuts the first two chars off the start of the
`name`.

!!! example

    ```groovy
    g.V().hasLabel('person')
        .project('result')
            .by(__.project('a', 'b')
                .by(__.values('name'))
                .by(__.constant(2))
                .select(values)
                .map(cypherSubstring()))
    ```

    ```text
    [{'result': 'das'}, {'result': 'rko'}, {'result': 'ter'}, {'result': 'sh'}]
    ```

## cypherTrim

Can be used to run a [`trim()`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#trim())
on a given string input, basically all leading and trailing whitespace is
removed.

!!! example

    ```groovy
    g.V().values('name').map(cypherTrim())
    ```

    Result:

    ```text
    ['vadas', 'ripple', 'marko', 'peter', 'josh', 'lop']
    ```

## cypherToUpper

Can be used to uppercase the input string.

!!! example

    ```groovy
    g.V().values('name').map(cypherToUpper())
    ```

    Result:

    ```text
    ['VADAS', 'RIPPLE', 'MARKO', 'PETER', 'JOSH', 'LOP']
    ```

## cypherToLower

Can be used to lowercase the input string.

!!! example

    ```groovy
    g.V().values('name').map(cypherToLower())
    ```

    Result:

    ```text
    ['vadas', 'ripple', 'marko', 'peter', 'josh', 'lop']
    ```

## cypherSplit

Can be used to split an input string based on a given character.

Note requires complex input pairs by using the project step for the input string
and split character. The example below splits on an `e`.

!!! example

    ```groovy
    g.V().hasLabel('person')
        .project('result')
            .by(__.project('a', 'b')
                .by(__.values('name'))
                .by(__.constant('e'))
                .select(values)
                .map(cypherSplit()))
    ```

    ```text
    [{'result': ['vadas']},
     {'result': ['marko']},
     {'result': ['p', 't', 'r']},
     {'result': ['josh']}]
    ```
