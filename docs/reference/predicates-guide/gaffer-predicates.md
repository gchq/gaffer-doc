# Gaffer Predicates

Predicates which are part of Gaffer.

## HyperLogLogPlusIsLessThan

Tests a HyperLogLogPlus cardinality is less than a provided value. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/predicate/HyperLogLogPlusIsLessThan.html)

Input type: `com.clearspring.analytics.stream.cardinality.HyperLogLogPlus`

??? example "Example HyperLogLogPlusIsLessThan Integer 2"

    === "Java"

        ``` java
        final HyperLogLogPlusIsLessThan function = new HyperLogLogPlusIsLessThan(2);
        ```

    === "JSON"

        ``` json
        {
          "class" : "HyperLogLogPlusIsLessThan",
          "orEqualTo" : false,
          "value" : 2
        }
        ```

    === "Python"

        ``` python
        g.HyperLogLogPlusIsLessThan( 
          value=2, 
          or_equal_to=False 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result
    ---------- | ----- | ------
    com.clearspring.analytics.stream.cardinality.HyperLogLogPlus | com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@4093e27d | true
    com.clearspring.analytics.stream.cardinality.HyperLogLogPlus | com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@51d8a4de | false
    com.clearspring.analytics.stream.cardinality.HyperLogLogPlus | com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3120d299 | false

??? example "Example HyperLogLogPlusIsLessThan or equal to Integer 2"

    === "Java"

        ``` java
        final HyperLogLogPlusIsLessThan function = new HyperLogLogPlusIsLessThan(2, true);
        ```

    === "JSON"

        ``` json
        {
          "class" : "HyperLogLogPlusIsLessThan",
          "orEqualTo" : true,
          "value" : 2
        }
        ```

    === "Python"

        ``` python
        g.HyperLogLogPlusIsLessThan( 
          value=2, 
          or_equal_to=True
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    com.clearspring.analytics.stream.cardinality.HyperLogLogPlus | com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@4093e27d | true
    com.clearspring.analytics.stream.cardinality.HyperLogLogPlus | com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@51d8a4de | true
    com.clearspring.analytics.stream.cardinality.HyperLogLogPlus | com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3120d299 | false
