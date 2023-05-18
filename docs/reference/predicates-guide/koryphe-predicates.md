# Koryphe Predicates

Predicates from the Koryphe library.

## AgeOff

Checks if a timestamp is recent based on a provided age off time. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/AgeOff.html)

Input type: `java.lang.Long`

??? example "Example AgeOff in milliseconds"

    === "Java"

        ``` java
        final AgeOff function = new AgeOff(100000L);
        ```

    === "JSON"

        ``` json
        {
          "class" : "AgeOff",
          "ageOffTime" : 100000
        }
        ```

    === "Python"

        ``` python
        g.AgeOff( 
          age_off_time=100000 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | ClassCastException: java.lang.String cannot be cast to java.lang.Long
    java.lang.Long | 1667818781957 | true
    java.lang.Long | 1667818681957 | false
    java.lang.Long | 1667818881957 | true
    java.lang.String | 1667818781957 | ClassCastException: java.lang.String cannot be cast to java.lang.Long


## And

Returns true if all of its predicates are true. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html)

Input type: `uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType`

??? example "Example of is less than 3 *and* is more than 0"

    === "Java"

        ``` java
        final And function = new And<>(
                new IsLessThan(3),
                new IsMoreThan(0)
        );
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
          "predicates" : [ {
            "class" : "IsLessThan",
            "orEqualTo" : false,
            "value" : 3
          }, {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 0
          } ]
        }
        ```

    === "Python"

        ``` python
        g.And( 
          predicates=[ 
            g.IsLessThan( 
              value=3, 
              or_equal_to=False 
            ), 
            g.IsMoreThan( 
              value=0, 
              or_equal_to=False 
            ) 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 0 | false
    java.lang.Integer | 1 | true
    java.lang.Integer | 2 | true
    java.lang.Integer | 3 | false
    java.lang.Long | 1 | false
    java.lang.Long | 2 | false


??? example "Example of first item less than 2 *and* second item more than 5"

    === "Java"

        ``` java
        final And function = new And.Builder()
                .select(0)
                .execute(new IsLessThan(2))
                .select(1)
                .execute(new IsMoreThan(5))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
          "predicates" : [ {
            "class" : "IntegerTupleAdaptedPredicate",
            "selection" : [ 0 ],
            "predicate" : {
              "class" : "IsLessThan",
              "orEqualTo" : false,
              "value" : 2
            }
          }, {
            "class" : "IntegerTupleAdaptedPredicate",
            "selection" : [ 1 ],
            "predicate" : {
              "class" : "IsMoreThan",
              "orEqualTo" : false,
              "value" : 5
            }
          } ]
        }
        ```

    === "Python"

        ``` python
        g.And( 
          predicates=[ 
            g.NestedPredicate( 
              selection=[ 
                0 
              ], 
              predicate=g.IsLessThan( 
                value=2, 
                or_equal_to=False 
              ) 
            ), 
            g.NestedPredicate( 
              selection=[ 
                1 
              ], 
              predicate=g.IsMoreThan( 
                value=5, 
                or_equal_to=False 
              ) 
            ) 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Integer, java.lang.Integer] | [1, 10] | true
    [java.lang.Integer, java.lang.Integer] | [1, 1] | false
    [java.lang.Integer, java.lang.Integer] | [10, 10] | false
    [java.lang.Integer, java.lang.Integer] | [10, 1] | false
    [java.lang.Long, java.lang.Long] | [1, 10] | false
    [java.lang.Integer] | [1] | false


## AreEqual

Returns true if the two inputs are equal. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/AreEqual.html)

Input type: `java.lang.Object, java.lang.Object`

??? example "Example AreEqual"

    === "Java"

        ``` java
        final AreEqual function = new AreEqual();
        ```

    === "JSON"

        ``` json
        {
          "class" : "AreEqual"
        }
        ```

    === "Python"

        ``` python
        g.AreEqual()
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Integer, java.lang.Double] | [1, 1.0] | false
    [java.lang.Double, java.lang.Double] | [2.5, 2.5] | true
    [java.lang.String, ] | [, null] | false
    [java.lang.String, java.lang.String] | [abc, abc] | true


## AreIn

Checks if a provided collection contains all the provided input values. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/AreIn.html)

Input type: `java.util.Collection`

??? example "Example AreIn Set"

    === "Java"

        ``` java
        final AreIn function = new AreIn(1, 2, 3);
        ```

    === "JSON"

        ``` json
        {
          "class" : "AreIn",
          "values" : [ 1, 2, 3 ]
        }
        ```

    === "Python"

        ``` python
        g.AreIn( 
          values=[ 
            1, 
            2, 
            3 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.HashSet | [1, 2, 3] | true
    java.util.HashSet | [1, 2, 3, 4] | false
    java.util.HashSet | [4, 1] | false
    java.util.HashSet | [1, 2] | true
    java.util.HashSet | [] | true


## CollectionContains

Checks if a collection contains a provided value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/CollectionContains.html)

Input type: `java.util.Collection`

??? example "Example CollectionContains"

    === "Java"

        ``` java
        final CollectionContains function = new CollectionContains(1);
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
          "value" : 1
        }
        ```

    === "Python"

        ``` python
        g.CollectionContains( 
          value=1 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.HashSet | [1, 2, 3] | true
    java.util.HashSet | [1] | true
    java.util.HashSet | [2] | false
    java.util.HashSet | [] | false


## Exists

Checks the input exists. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html)

Input type: `java.lang.Object`

??? example "Example Exists"

    === "Java"

        ``` java
        final Exists function = new Exists();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Exists"
        }
        ```

    === "Python"

        ``` python
        g.Exists()
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | true
     | null | false
    java.lang.String | true
    java.lang.String | abc | true


## If

Conditionally applies a predicate. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/If.html)

Input type: `uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType`

??? example "Example conditionally applying predicates to input"

    This example tests first whether the input is an Integer. If so, it is then tested to see if the value is greater than 3. Otherwise, since it is not an Integer, we then test to see if it is NOT a String.
    
    === "Java"

        ``` java
        final If<Comparable> predicate = new If<>(new IsA(Integer.class), new IsMoreThan(3), new Not<>(new IsA(String.class)));
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.If",
          "predicate" : {
            "class" : "IsA",
            "type" : "java.lang.Integer"
          },
          "then" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 3
          },
          "otherwise" : {
            "class" : "Not",
            "predicate" : {
              "class" : "IsA",
              "type" : "java.lang.String"
            }
          }
        }
        ```

    === "Python"

        ``` python
        g.If( 
          predicate=g.IsA( 
            type="java.lang.Integer" 
          ), 
          then=g.IsMoreThan( 
            value=3, 
            or_equal_to=False 
          ), 
          otherwise=g.Not( 
            predicate=g.IsA( 
              type="java.lang.String" 
            ) 
          ) 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 2 | false
    java.lang.Integer | 3 | false
    java.lang.Integer | 5 | true
    java.lang.String | test | false
    java.util.HashMap | {} | true
    java.util.ArrayList | [] | true


## Or

Returns true if any of the predicates are true. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html)

When using an Or predicate with a single selected value you can just use the constructor new `Or(predicates))`'.

When using an Or predicate with multiple selected values, you need to use the `Or.Builder` to build your Or predicate, using `.select()` then `.execute()`. When selecting values in the `Or.Builder` you need to refer to the position in the input array. I.e to use the first value use position 0 - `select(0)`.You can select multiple values to give to a predicate like isXLessThanY, this is achieved by passing 2 positions to the select method - `select(0, 1)`.

Input type: `uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType`

??? example "Example is less than 2 equal to 5 *or* is more than 10"

    === "Java"

        ``` java
        final Or function = new Or<>(
                new IsLessThan(2),
                new IsEqual(5),
                new IsMoreThan(10)
        );
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
          "predicates" : [ {
            "class" : "IsLessThan",
            "orEqualTo" : false,
            "value" : 2
          }, {
            "class" : "IsEqual",
            "value" : 5
          }, {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 10
          } ]
        }
        ```

    === "Python"

        ``` python
        g.Or( 
          predicates=[ 
            g.IsLessThan( 
              value=2, 
              or_equal_to=False 
            ), 
            g.IsEqual( 
              value=5 
            ), 
            g.IsMoreThan( 
              value=10, 
              or_equal_to=False 
            ) 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | true
    java.lang.Integer | 2 | false
    java.lang.Integer | 3 | false
    java.lang.Integer | 5 | true
    java.lang.Integer | 15 | true
    java.lang.Long | 1 | false
    java.lang.Long | 3 | false
    java.lang.Long | 5 | false


??? example "Example is less than 2 equal to 5 *or* is more than 10"

    === "Java"

        ``` java
        final Or function = new Or<>(
                new IsLessThan(2),
                new IsEqual(5),
                new IsMoreThan(10)
        );
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
          "predicates" : [ {
            "class" : "IsLessThan",
            "orEqualTo" : false,
            "value" : 2
          }, {
            "class" : "IsEqual",
            "value" : 5
          }, {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 10
          } ]
        }
        ```

    === "Python"

        ``` python
        g.Or( 
          predicates=[ 
            g.IsLessThan( 
              value=2, 
              or_equal_to=False 
            ), 
            g.IsEqual( 
              value=5 
            ), 
            g.IsMoreThan( 
              value=10, 
              or_equal_to=False 
            ) 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | true
    java.lang.Integer | 2 | false
    java.lang.Integer | 3 | false
    java.lang.Integer | 5 | true
    java.lang.Integer | 15 | true
    java.lang.Long | 1 | false
    java.lang.Long | 3 | false
    java.lang.Long | 5 | false


??? example "Example first item is less than 2 *or* second item is more than 10"

    === "Java"

        ``` java
        final Or function = new Or.Builder()
                .select(0)
                .execute(new IsLessThan(2))
                .select(1)
                .execute(new IsMoreThan(10))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
          "predicates" : [ {
            "class" : "IntegerTupleAdaptedPredicate",
            "selection" : [ 0 ],
            "predicate" : {
              "class" : "IsLessThan",
              "orEqualTo" : false,
              "value" : 2
            }
          }, {
            "class" : "IntegerTupleAdaptedPredicate",
            "selection" : [ 1 ],
            "predicate" : {
              "class" : "IsMoreThan",
              "orEqualTo" : false,
              "value" : 10
            }
          } ]
        }
        ```

    === "Python"

        ``` python
        g.Or( 
          predicates=[ 
            g.NestedPredicate( 
              selection=[ 
                0 
              ], 
              predicate=g.IsLessThan( 
                value=2, 
                or_equal_to=False 
              ) 
            ), 
            g.NestedPredicate( 
              selection=[ 
                1 
              ], 
              predicate=g.IsMoreThan( 
                value=10, 
                or_equal_to=False 
              ) 
            ) 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Integer, java.lang.Integer] | [1, 15] | true
    [java.lang.Integer, java.lang.Integer] | [1, 1] | true
    [java.lang.Integer, java.lang.Integer] | [15, 15] | true
    [java.lang.Integer, java.lang.Integer] | [15, 1] | false
    [java.lang.Long, java.lang.Long] | [1, 15] | false
    [java.lang.Integer] | [1] | true


## Not

Returns the inverse of a predicate. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)

Input type: `uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType`

??? example "Example does *not* exist"

    === "Java"

        ``` java
        final Not function = new Not<>(new Exists());
        ```

    === "JSON"

        ``` json
        {
          "class" : "Not",
          "predicate" : {
            "class" : "Exists"
          }
        }
        ```

    === "Python"

        ``` python
        g.Not( 
          predicate=g.Exists() 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | false
     | null | true
    java.lang.String | false
    java.lang.String | abc | false


??? example "Example are *not* equal"

    === "Java"

        ``` java
        final Not function = new Not<>(new AreEqual());
        ```

    === "JSON"

        ``` json
        {
          "class" : "Not",
          "predicate" : {
            "class" : "AreEqual"
          }
        }
        ```

    === "Python"

        ``` python
        g.Not( 
          predicate=g.AreEqual() 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Integer, java.lang.Double] | [1, 1.0] | true
    [java.lang.Integer, java.lang.Integer] | [1, 2] | true
    [java.lang.Double, java.lang.Double] | [2.5, 2.5] | false
    [java.lang.String, ] | [, null] | true
    [java.lang.String, java.lang.String] | [abc, abc] | false


## InDateRange

Tests if a Comparable is within a provided range. By default the range is inclusive, this can be toggled using the startInclusive and endInclusive booleans. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InDateRange.html)

You can configure the start and end time strings using the following formats:

* timestamp in milliseconds
* yyyy/MM
* yyyy/MM/dd
* yyyy/MM/dd HH
* yyyy/MM/dd HH:mm
* yyyy/MM/dd HH:mm:ss

You can use a space, `-`, `/`, `_`, `:`, `|`, or `.` to separate the parts.

Input type: `java.util.Date`

??? example "Example InDateRange with day precision"

    === "Java"

        ``` java
        final InDateRange function = new InDateRange.Builder()
                .start("2017/01/01")
                .end("2017/02/01")
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRange",
          "start" : "2017/01/01",
          "end" : "2017/02/01"
        }
        ```

    === "Python"

        ``` python
        g.InDateRange( 
          start="2017/01/01", 
          end="2017/02/01" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.Date | Fri Jan 01 00:00:00 GMT 2016 | false
    java.util.Date | Sun Jan 01 00:00:00 GMT 2017 | true
    java.util.Date | Sun Jan 01 01:00:00 GMT 2017 | true
    java.util.Date | Sun Jan 01 23:59:59 GMT 2017 | true
    java.util.Date | Wed Feb 01 00:00:00 GMT 2017 | true
    java.util.Date | Wed Feb 01 00:00:01 GMT 2017 | false
     | null | false


??? example "Example InDateRange with second precision"

    === "Java"

        ``` java
        final InDateRange function = new InDateRange.Builder()
                .start("2017/01/01 01:30:10")
                .end("2017/01/01 01:30:50")
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRange",
          "start" : "2017/01/01 01:30:10",
          "end" : "2017/01/01 01:30:50"
        }
        ```

    === "Python"

        ``` python
        g.InDateRange( 
          start="2017/01/01 01:30:10", 
          end="2017/01/01 01:30:50" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.Date | Sun Jan 01 01:30:09 GMT 2017 | false
    java.util.Date | Sun Jan 01 01:30:10 GMT 2017 | true
    java.util.Date | Sun Jan 01 01:30:20 GMT 2017 | true
    java.util.Date | Sun Jan 01 01:30:50 GMT 2017 | true
    java.util.Date | Sun Jan 01 01:30:51 GMT 2017 | false
     | null | false


??? example "Example InDateRange with timestamps"

    === "Java"

        ``` java
        final InDateRange function = new InDateRange.Builder()
                .start("1483315200")
                .end("1485907200")
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRange",
          "start" : "1483315200",
          "end" : "1485907200"
        }
        ```

    === "Python"

        ``` python
        g.InDateRange( 
          start="1483315200", 
          end="1485907200" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.Date | Sun Jan 18 05:01:55 GMT 1970 | false
    java.util.Date | Sun Jan 18 05:01:55 GMT 1970 | true
    java.util.Date | Sun Jan 18 05:01:56 GMT 1970 | true
    java.util.Date | Sun Jan 18 05:45:07 GMT 1970 | true
    java.util.Date | Sun Jan 18 05:45:07 GMT 1970 | false
     | null | false


??? example "Example of range exclusive"

    === "Java"

        ``` java
        final InDateRange function = new InDateRange.Builder()
                .start("2017/01/01")
                .end("2017/02/01")
                .startInclusive(false)
                .endInclusive(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRange",
          "start" : "2017/01/01",
          "end" : "2017/02/01",
          "startInclusive" : false,
          "endInclusive" : false
        }
        ```

    === "Python"

        ``` python
        g.InDateRange( 
          start="2017/01/01", 
          end="2017/02/01", 
          start_inclusive=False, 
          end_inclusive=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.Date | Fri Jan 01 00:00:00 GMT 2016 | false
    java.util.Date | Sun Jan 01 00:00:00 GMT 2017 | false
    java.util.Date | Sun Jan 01 01:00:00 GMT 2017 | true
    java.util.Date | Sun Jan 01 23:59:59 GMT 2017 | true
    java.util.Date | Wed Feb 01 00:00:00 GMT 2017 | false
    java.util.Date | Wed Feb 01 00:00:01 GMT 2017 | false
     | null | false


??? example "Example of within the last week"

    If the end of the range is not specified then the end of the range is unbounded.
    
    === "Java"

        ``` java
        final InDateRange function = new InDateRange.Builder()
                .startOffset(-7L)
                // end is not set - it is unbounded
                .offsetUnit(TimeUnit.DAY)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRange",
          "startOffset" : -7,
          "offsetUnit" : "DAY"
        }
        ```

    === "Python"

        ``` python
        g.InDateRange( 
          start_offset=-7, 
          offset_unit="DAY" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.Date | Sun Oct 30 11:00:11 GMT 2022 | false
    java.util.Date | Tue Nov 01 11:00:11 GMT 2022 | true
    java.util.Date | Sun Nov 06 11:00:11 GMT 2022 | true
    java.util.Date | Mon Nov 07 11:00:11 GMT 2022 | true
     | null | false


??? example "Example of exactly 7 hours ago"

    === "Java"

        ``` java
        final InDateRange function = new InDateRange.Builder()
                .startOffset(-7L)
                .endOffset(-6L)
                .endInclusive(false)
                .offsetUnit(TimeUnit.HOUR)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRange",
          "startOffset" : -7,
          "endOffset" : -6,
          "endInclusive" : false,
          "offsetUnit" : "HOUR"
        }
        ```

    === "Python"

        ``` python
        g.InDateRange( 
          start_offset=-7, 
          end_offset=-6, 
          offset_unit="HOUR", 
          end_inclusive=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.Date | Mon Nov 07 03:00:12 GMT 2022 | false
    java.util.Date | Mon Nov 07 04:00:22 GMT 2022 | true
    java.util.Date | Mon Nov 07 05:00:02 GMT 2022 | true
    java.util.Date | Mon Nov 07 05:00:22 GMT 2022 | false
    java.util.Date | Mon Nov 07 11:00:12 GMT 2022 | false
     | null | false


## InDateRangeDual

Tests if a start Comparable and end Comparable are within a provided range. Specifically the start Comparable has to be greater than the start bound and the end Comparable has to be less than the end bound. By default the range is inclusive, this can be toggled using the startInclusive and endInclusive booleans. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InDateRangeDual.html)

This uses the same input formats as InDateRange.

Input type: `java.lang.Comparable, java.lang.Comparable`

??? example "Example with fully uncontained range"

    === "Java"

        ``` java
        final InDateRangeDual function = new InDateRangeDual.Builder()
                .start("2017/03/01")
                .end("2017/08/01")
                .startFullyContained(false)
                .endFullyContained(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRangeDual",
          "start" : "2017/03/01",
          "end" : "2017/08/01",
          "endFullyContained" : false,
          "startFullyContained" : false
        }
        ```

    === "Python"

        ``` python
        g.InDateRangeDual( 
          start="2017/03/01", 
          end="2017/08/01", 
          start_fully_contained=False, 
          end_fully_contained=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.util.Date, java.util.Date] | [Sun Jan 01 00:00:00 GMT 2017, Wed Feb 01 00:00:00 GMT 2017] | false
    [java.util.Date, java.util.Date] | [Sun Jan 01 00:00:00 GMT 2017, Sat Apr 01 00:00:00 BST 2017] | true
    [java.util.Date, java.util.Date] | [Sat Apr 01 00:00:00 BST 2017, Mon May 01 00:00:00 BST 2017] | true
    [java.util.Date, java.util.Date] | [Sat Apr 01 00:00:00 BST 2017, Fri Sep 01 00:00:00 BST 2017] | true
    [java.util.Date, java.util.Date] | [Fri Sep 01 00:00:00 BST 2017, Sun Oct 01 00:00:00 BST 2017] | false
    [ ,] | [null, null] | false


??? example "Example with start contained range"

    === "Java"

        ``` java
        final InDateRangeDual function = new InDateRangeDual.Builder()
                .start("2017/03/01")
                .end("2017/08/01")
                .startFullyContained(true)
                .endFullyContained(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRangeDual",
          "start" : "2017/03/01",
          "end" : "2017/08/01",
          "endFullyContained" : false,
          "startFullyContained" : true
        }
        ```

    === "Python"

        ``` python
        g.InDateRangeDual( 
          start="2017/03/01", 
          end="2017/08/01", 
          start_fully_contained=True, 
          end_fully_contained=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.util.Date, java.util.Date] | [Sun Jan 01 00:00:00 GMT 2017, Wed Feb 01 00:00:00 GMT 2017] | false
    [java.util.Date, java.util.Date] | [Sun Jan 01 00:00:00 GMT 2017, Sat Apr 01 00:00:00 BST 2017] | false
    [java.util.Date, java.util.Date] | [Sat Apr 01 00:00:00 BST 2017, Mon May 01 00:00:00 BST 2017] | true
    [java.util.Date, java.util.Date] | [Sat Apr 01 00:00:00 BST 2017, Fri Sep 01 00:00:00 BST 2017] | true
    [java.util.Date, java.util.Date] | [Fri Sep 01 00:00:00 BST 2017, Sun Oct 01 00:00:00 BST 2017] | false
    [ ,] | [null, null] | false


??? example "Example with fully contained range"

    === "Java"

        ``` java
        final InDateRangeDual function = new InDateRangeDual.Builder()
                .start("2017/03/01")
                .end("2017/08/01")
                .startFullyContained(true)
                .endFullyContained(true)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InDateRangeDual",
          "start" : "2017/03/01",
          "end" : "2017/08/01",
          "endFullyContained" : true,
          "startFullyContained" : true
        }
        ```

    === "Python"

        ``` python
        g.InDateRangeDual( 
          start="2017/03/01", 
          end="2017/08/01", 
          start_fully_contained=True, 
          end_fully_contained=True 
        )
        ```
    
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.util.Date, java.util.Date] | [Sun Jan 01 00:00:00 GMT 2017, Wed Feb 01 00:00:00 GMT 2017] | false
    [java.util.Date, java.util.Date] | [Sun Jan 01 00:00:00 GMT 2017, Sat Apr 01 00:00:00 BST 2017] | false
    [java.util.Date, java.util.Date] | [Sat Apr 01 00:00:00 BST 2017, Mon May 01 00:00:00 BST 2017] | true
    [java.util.Date, java.util.Date] | [Sat Apr 01 00:00:00 BST 2017, Fri Sep 01 00:00:00 BST 2017] | false
    [java.util.Date, java.util.Date] | [Fri Sep 01 00:00:00 BST 2017, Sun Oct 01 00:00:00 BST 2017] | false
    [ ,] | [null, null] | false


## InRange

Checks if a comparable is within a provided range. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InRange.html)

Input type: `java.lang.Comparable`

??? example "Example of Long 5 to 10"

    === "Java"

        ``` java
        final InRange function = new InRange.Builder<Long>()
                .start(5L)
                .end(10L)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRange",
          "start" : {
            "Long" : 5
          },
          "end" : {
            "Long" : 10
          }
        }
        ```

    === "Python"

        ``` python
        g.InRange( 
          start={'java.lang.Long': 5}, 
          end={'java.lang.Long': 10} 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Long | -5 | false
    java.lang.Long | 1 | false
    java.lang.Long | 5 | true
    java.lang.Long | 7 | true
    java.lang.Long | 10 | true
    java.lang.Long | 20 | false
    java.lang.Integer | 7 | ClassCastException: java.lang.Long cannot be cast to java.lang.Integer
    java.lang.String | 7 | ClassCastException: java.lang.Long cannot be cast to java.lang.String
     | null | false


??? example "Example of Long 5 to 10 exclusive"

    === "Java"

        ``` java
        final InRange function = new InRange.Builder<Long>()
                .start(5L)
                .end(10L)
                .startInclusive(false)
                .endInclusive(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRange",
          "start" : {
            "Long" : 5
          },
          "end" : {
            "Long" : 10
          },
          "startInclusive" : false,
          "endInclusive" : false
        }
        ```

    === "Python"

        ``` python
        g.InRange( 
          start={'java.lang.Long': 5}, 
          end={'java.lang.Long': 10}, 
          start_inclusive=False, 
          end_inclusive=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Long | -5 | false
    java.lang.Long | 1 | false
    java.lang.Long | 5 | false
    java.lang.Long | 7 | true
    java.lang.Long | 10 | false
    java.lang.Long | 20 | false
    java.lang.Integer | 7 | ClassCastException: java.lang.Long cannot be cast to java.lang.Integer
    java.lang.String | 7 | ClassCastException: java.lang.Long cannot be cast to java.lang.String
     | null | false


??? example "Example of Long 5 less than 10"

    If the start of the range is not specified then the start of the range is unbounded.
    
    === "Java"

        ``` java
        final InRange function = new InRange.Builder<Long>()
                .end(10L)
                .endInclusive(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRange",
          "end" : {
            "Long" : 10
          },
          "endInclusive" : false
        }
        ```

    === "Python"

        ``` python
        g.InRange( 
          end={'java.lang.Long': 10}, 
          end_inclusive=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Long | -5 | true
    java.lang.Long | 1 | true
    java.lang.Long | 5 | true
    java.lang.Long | 7 | true
    java.lang.Long | 10 | false
    java.lang.Long | 20 | false
    java.lang.Integer | 7 | ClassCastException: java.lang.Long cannot be cast to java.lang.Integer
    java.lang.String | 7 | ClassCastException: java.lang.Long cannot be cast to java.lang.String
     | null | false


??? example "Example of String 'B' to 'D'"

    === "Java"

        ``` java
        final InRange function = new InRange.Builder<String>()
                .start("B")
                .end("D")
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRange",
          "start" : "B",
          "end" : "D"
        }
        ```

    === "Python"

        ``` python
        g.InRange( 
          start="B", 
          end="D" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | A | false
    java.lang.String | B | true
    java.lang.String | C | true
    java.lang.String | D | true
    java.lang.String | c | false
    java.lang.Integer | 1 | ClassCastException: java.lang.String cannot be cast to java.lang.Integer
     | null | false


## InRangeDual

Checks if two comparables (a start and an end) are within a provided range. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InRangeDual.html)

Input type: `java.lang.Comparable, java.lang.Comparable`

??? example "Example of Long overlapping range"

    === "Java"

        ``` java
        final InRangeDual function = new InRangeDual.Builder<Long>()
                .start(5L)
                .end(10L)
                .startFullyContained(false)
                .endFullyContained(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRangeDual",
          "start" : {
            "Long" : 5
          },
          "end" : {
            "Long" : 10
          },
          "endFullyContained" : false,
          "startFullyContained" : false
        }
        ```

    === "Python"

        ``` python
        g.InRangeDual( 
          start={'java.lang.Long': 5}, 
          end={'java.lang.Long': 10}, 
          start_fully_contained=False, 
          end_fully_contained=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Long, java.lang.Long] | [1, 4] | false
    [java.lang.Long, java.lang.Long] | [1, 7] | true
    [java.lang.Long, java.lang.Long] | [6, 7] | true
    [java.lang.Long, java.lang.Long] | [7, 11] | true
    [java.lang.Long, java.lang.Long] | [11, 20] | false
    [ ,] | [null, null] | false


??? example "Example of Long end overlapping range"

    === "Java"

        ``` java
        final InRangeDual function = new InRangeDual.Builder<Long>()
                .start(5L)
                .end(10L)
                .startFullyContained(true)
                .endFullyContained(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRangeDual",
          "start" : {
            "Long" : 5
          },
          "end" : {
            "Long" : 10
          },
          "endFullyContained" : false,
          "startFullyContained" : true
        }
        ```

    === "Python"

        ``` python
        g.InRangeDual( 
          start={'java.lang.Long': 5}, 
          end={'java.lang.Long': 10}, 
          start_fully_contained=True, 
          end_fully_contained=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Long, java.lang.Long] | [1, 4] | false
    [java.lang.Long, java.lang.Long] | [1, 7] | false
    [java.lang.Long, java.lang.Long] | [6, 7] | true
    [java.lang.Long, java.lang.Long] | [7, 11] | true
    [java.lang.Long, java.lang.Long] | [11, 20] | false
    [ ,] | [null, null] | false


??? example "Example of Long non overlapping range"

    === "Java"

        ``` java
        final InRangeDual function = new InRangeDual.Builder<Long>()
                .start(5L)
                .end(10L)
                .startFullyContained(true)
                .endFullyContained(true)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRangeDual",
          "start" : {
            "Long" : 5
          },
          "end" : {
            "Long" : 10
          },
          "endFullyContained" : true,
          "startFullyContained" : true
        }
        ```

    === "Python"

        ``` python
        g.InRangeDual( 
          start={'java.lang.Long': 5}, 
          end={'java.lang.Long': 10}, 
          start_fully_contained=True, 
          end_fully_contained=True 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Long, java.lang.Long] | [1, 4] | false
    [java.lang.Long, java.lang.Long] | [1, 7] | false
    [java.lang.Long, java.lang.Long] | [6, 7] | true
    [java.lang.Long, java.lang.Long] | [7, 11] | false
    [java.lang.Long, java.lang.Long] | [11, 20] | false
    [ ,] | [null, null] | false


??? example "Example Long less than 10"

    If the start of the range is not specified then the start of the range is unbounded.
    
    === "Java"

        ``` java
        final InRangeDual function = new InRangeDual.Builder<Long>()
                .end(10L)
                .endInclusive(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InRangeDual",
          "end" : {
            "Long" : 10
          },
          "endInclusive" : false
        }
        ```

    === "Python"

        ``` python
        g.InRangeDual( 
          end={'java.lang.Long': 10}, 
          end_inclusive=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Long, java.lang.Long] | [-5, -1] | true
    [java.lang.Long, java.lang.Long] | [1, 6] | true
    [java.lang.Long, java.lang.Long] | [6, 6] | true
    [java.lang.Long, java.lang.Long] | [6, 7] | true
    [java.lang.Long, java.lang.Long] | [6, 10] | true
    [java.lang.Long, java.lang.Long] | [10, 20] | false
    [java.lang.Integer, java.lang.Integer] | [6, 7] | IllegalArgumentException: Input tuple values do not match the required function input types
    [java.lang.String, java.lang.String] | [5, 7] | IllegalArgumentException: Input tuple values do not match the required function input types
    [ ,] | [null, null] | false


## InTimeRange

Functionally identical to InDateRange, except that it uses Long as the timestamp input type. By default, checks are carried out assuming the data will be in milliseconds. If this is not the case, the time unit can be changed using the timeUnit property. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InTimeRange.html)

Input type: `java.lang.Long`

??? example "Example with time unit microseconds"

    === "Java"

        ``` java
        final InTimeRange function = new InTimeRange.Builder()
                .start("2017/01/01 01:30:10")
                .end("2017/01/01 01:30:50")
                .timeUnit(TimeUnit.MICROSECOND)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "InTimeRange",
          "start" : "2017/01/01 01:30:10",
          "end" : "2017/01/01 01:30:50",
          "timeUnit" : "MICROSECOND"
        }
        ```

    === "Python"

        ``` python
        g.InTimeRange( 
          start="2017/01/01 01:30:10", 
          end="2017/01/01 01:30:50", 
          time_unit="MICROSECOND" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Long | 1483234209000000 | false
    java.lang.Long | 1483234210000000 | true
    java.lang.Long | 1483234220000000 | true
    java.lang.Long | 1483234250000000 | true
    java.lang.Long | 1483234251000000 | false
     | null | false


## InTimeRangeDual

Functionally identical to InDateRangeDual. By default, checks are carried out assuming the data will be in milliseconds. If this is not the case, the time unit can be changed using the timeUnit property. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InTimeRangeDual.html)

## IsA

Checks if an input is an instance of a class. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html)

Input type: `java.lang.Object`

??? example "Example with String"

    === "Java"

        ``` java
        final IsA function = new IsA(String.class);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsA",
          "type" : "java.lang.String"
        }
        ```

    === "Python"

        ``` python
        g.IsA( 
          type="java.lang.String" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | false
    java.lang.Double | 2.5 | false
    java.lang.String | abc | true


??? example "Example with Number"

    === "Java"

        ``` java
        final IsA function = new IsA(Number.class);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsA",
          "type" : "java.lang.Number"
        }
        ```

    === "Python"

        ``` python
        g.IsA( 
          type="java.lang.Number" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | true
    java.lang.Double | 2.5 | true
    java.lang.String | abc | false


## IsEqual

Checks if an input is equal to a provided value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html)

Input type: `java.lang.Object`

??? example "Example equal to Integer 5"

    === "Java"

        ``` java
        final IsEqual function = new IsEqual(5);
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
          "value" : 5
        }
        ```

    === "Python"

        ``` python
        g.IsEqual( 
          value=5 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 5 | true
    java.lang.Long | 5 | false
    java.lang.String | 5 | false
    java.lang.Character | 5 | false


??? example "Example equal to String 5"

    === "Java"

        ``` java
        final IsEqual function = new IsEqual("5");
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsEqual",
          "value" : "5"
        }
        ```

    === "Python"

        ``` python
        g.IsEqual( 
          value="5" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 5 | false
    java.lang.Long | 5 | false
    java.lang.String | 5 | true
    java.lang.Character | 5 | false


## IsFalse

Checks if an input boolean is false. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsFalse.html)

Input type: `java.lang.Boolean`

??? example "Example IsFalse"

    === "Java"

        ``` java
        final IsFalse function = new IsFalse();
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsFalse"
        }
        ```

    === "Python"

        ``` python
        g.IsFalse()
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Boolean | true | false
    java.lang.Boolean | false | true
     | null | false
    java.lang.String | true | ClassCastException: java.lang.String cannot be cast to java.lang.Boolean


## IsTrue

Checks if an input boolean is true. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsTrue.html)

Input type: `java.lang.Boolean`

??? example "Example IsTrue"

    === "Java"

        ``` java
        final IsTrue function = new IsTrue();
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsTrue"
        }
        ```

    === "Python"

        ``` python
        g.IsTrue()
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Boolean | true | true
    java.lang.Boolean | false | false
     | null | false
    java.lang.String | true | ClassCastException: java.lang.String cannot be cast to java.lang.Boolean


## IsIn

Checks if an input is in a set of allowed values. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html)

Input type: `java.lang.Object`

??? example "Example IsIn"

    === "Java"

        ``` java
        final IsIn function = new IsIn(5, 5L, "5", '5');
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsIn",
          "values" : [ 5, {
            "Long" : 5
          }, "5", {
            "Character" : "5"
          } ]
        }
        ```

    === "Python"

        ``` python
        g.IsIn( 
          values=[ 
            5, 
            {'java.lang.Long': 5}, 
            "5", 
            {'java.lang.Character': '5'} 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 5 | true
    java.lang.Long | 5 | true
    java.lang.String | 5 | true
    java.lang.Character | 5 | true
    java.lang.Integer | 1 | false
    java.lang.Long | 1 | false
    java.lang.String | 1 | false
    java.lang.Character | 1 | false


## IsLessThan

Checks if a comparable is less than a provided value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsLessThan.html)

Input type: `java.lang.Comparable`

??? example "Example IsLessThan with Integer 5"

    === "Java"

        ``` java
        final IsLessThan function = new IsLessThan(5);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsLessThan",
          "orEqualTo" : false,
          "value" : 5
        }
        ```

    === "Python"

        ``` python
        g.IsLessThan( 
          value=5, 
          or_equal_to=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | true
    java.lang.Long | 1 | false
    java.lang.Integer | 5 | false
    java.lang.Long | 5 | false
    java.lang.Integer | 10 | false
    java.lang.Long | 10 | false
    java.lang.String | 1 | false


??? example "Example IsLessThan or equal with Integer 5"

    === "Java"

        ``` java
        final IsLessThan function = new IsLessThan(5, true);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsLessThan",
          "orEqualTo" : true,
          "value" : 5
        }
        ```

    === "Python"

        ``` python
        g.IsLessThan( 
          value=5, 
          or_equal_to=True 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | true
    java.lang.Long | 1 | false
    java.lang.Integer | 5 | true
    java.lang.Long | 5 | false
    java.lang.Integer | 10 | false
    java.lang.Long | 10 | false
    java.lang.String | 1 | false


??? example "Example IsLessThan with String 'B'"

    === "Java"

        ``` java
        final IsLessThan function = new IsLessThan("B");
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsLessThan",
          "orEqualTo" : false,
          "value" : "B"
        }
        ```

    === "Python"

        ``` python
        g.IsLessThan( 
          value="B", 
          or_equal_to=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | false
    java.lang.String | A | true
    java.lang.String | B | false
    java.lang.String | C | false


## IsMoreThan

Checks if a comparable is more than a provided value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsMoreThan.html)

Input type: `java.lang.Comparable`

??? example "Example IsMoreThan with Integer 5"

    === "Java"

        ``` java
        final IsMoreThan function = new IsMoreThan(5);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : 5
        }
        ```

    === "Python"

        ``` python
        g.IsMoreThan( 
          value=5, 
          or_equal_to=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | false
    java.lang.Integer | 5 | false
    java.lang.Integer | 10 | true


??? example "Example IsMoreThan or equal with Integer 5"

    === "Java"

        ``` java
        final IsMoreThan function = new IsMoreThan(5, true);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsMoreThan",
          "orEqualTo" : true,
          "value" : 5
        }
        ```

    === "Python"

        ``` python
        g.IsMoreThan( 
          value=5, 
          or_equal_to=True 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | false
    java.lang.Integer | 5 | true
    java.lang.Integer | 10 | true


??? example "Example IsMoreThan with String 'B'"

    === "Java"

        ``` java
        final IsMoreThan function = new IsMoreThan("B");
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsMoreThan",
          "orEqualTo" : false,
          "value" : "B"
        }
        ```

    === "Python"

        ``` python
        g.IsMoreThan( 
          value="B", 
          or_equal_to=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.Integer | 1 | false
    java.lang.String | A | false
    java.lang.String | B | false
    java.lang.String | C | true


## IsLongerThan

Checks if the length of an input is more than a value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsLongerThan.html)

Input type: `java.lang.Object`

??? example "Example testing size/length attribute is greater than 5"

    === "Java"

        ``` java
        final IsLongerThan predicate = new IsLongerThan(5);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsLongerThan",
          "minLength" : 5,
          "orEqualTo" : false
        }
        ```

    === "Python"

        ``` python
        g.IsLongerThan( 
          min_length=5, 
          or_equal_to=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | testString | true
    java.lang.String | aTest | false
    [Ljava.lang.String; | [null, null, null, null, null] | false
    [Ljava.lang.String; | [null, null, null, null, null, null, null, null, null, null] | true
    java.util.Arrays$ArrayList | [0, 1, 2, 3, 4, 5] | true


??? example "Example testing size/length attribute is greater than or equal to 5"

    === "Java"

        ``` java
        final IsLongerThan predicate = new IsLongerThan(5, true);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsLongerThan",
          "minLength" : 5,
          "orEqualTo" : true
        }
        ```

    === "Python"

        ``` python
        g.IsLongerThan( 
          min_length=5, 
          or_equal_to=True 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | test | false
    java.lang.String | testString | true
    java.lang.String | aTest | true
    [Ljava.lang.String; | [null, null, null, null, null] | true
    [Ljava.lang.String; | [null, null, null, null, null, null, null, null, null, null] | true
    java.util.Arrays$ArrayList | [0, 1, 2, 3, 4, 5] | true


## IsShorterThan

Checks if the length of an input is more than than a value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html)

Input type: `java.lang.Object`

??? example "Example testing size/length attribute is shorter than 4"

    === "Java"

        ``` java
        final IsShorterThan function = new IsShorterThan(4);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsShorterThan",
          "maxLength" : 4,
          "orEqualTo" : false
        }
        ```

    === "Python"

        ``` python
        g.IsShorterThan( 
          max_length=4, 
          or_equal_to=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | 123 | true
    java.lang.String | 1234 | false
    [Ljava.lang.Integer; | [1, 2, 3] | true
    [Ljava.lang.Integer; | [1, 2, 3, 4] | false
    java.util.ArrayList | [1, 2, 3] | true
    java.util.ArrayList | [1, 2, 3, 4] | false
    java.util.HashMap | {1=a, 2=b, 3=c} | true
    java.util.HashMap | {4=d} | true
    java.lang.Integer | 10000 | IllegalArgumentException: Could not determine the size of the provided value
    java.lang.Long | 10000 | IllegalArgumentException: Could not determine the size of the provided value


## IsXLessThanY

Checks the first comparable is less than the second comparable. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsXLessThanY.html)

Input type: `java.lang.Comparable, java.lang.Comparable`

??? example "Example IsXLessThanY"

    === "Java"

        ``` java
        final IsXLessThanY function = new IsXLessThanY();
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsXLessThanY"
        }
        ```

    === "Python"

        ``` python
        g.IsXLessThanY()
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Integer, java.lang.Integer] | [1, 5] | true
    [java.lang.Integer, java.lang.Integer] | [5, 5] | false
    [java.lang.Integer, java.lang.Integer] | [10, 5] | false
    [java.lang.Long, java.lang.Integer] | [1, 5] | false
    [java.lang.Long, java.lang.Long] | [1, 5] | true
    [java.lang.Long, java.lang.Long] | [5, 5] | false
    [java.lang.Long, java.lang.Long] | [10, 5] | false
    [java.lang.Integer, java.lang.Long] | [1, 5] | false
    [java.lang.String, java.lang.String] | [bcd, cde] | true
    [java.lang.String, java.lang.String] | [bcd, abc] | false
    [java.lang.String, java.lang.Integer] | [1, 5] | false


## IsXMoreThanY

Checks the first comparable is more than the second comparable. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsXMoreThanY.html)

Input type: `java.lang.Comparable, java.lang.Comparable`

??? example "Example IsXMoreThanY"

    === "Java"

        ``` java
        final IsXMoreThanY function = new IsXMoreThanY();
        ```

    === "JSON"

        ``` json
        {
          "class" : "IsXMoreThanY"
        }
        ```

    === "Python"

        ``` python
        g.IsXMoreThanY()
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    [java.lang.Integer, java.lang.Integer] | [1, 5] | false
    [java.lang.Integer, java.lang.Integer] | [5, 5] | false
    [java.lang.Integer, java.lang.Integer] | [10, 5] | true
    [java.lang.Long, java.lang.Integer] | [10, 5] | false
    [java.lang.Long, java.lang.Long] | [1, 5] | false
    [java.lang.Long, java.lang.Long] | [5, 5] | false
    [java.lang.Long, java.lang.Long] | [10, 5] | true
    [java.lang.Integer, java.lang.Long] | [10, 5] | false
    [java.lang.String, java.lang.String] | [bcd, cde] | false
    [java.lang.String, java.lang.String] | [bcd, abc] | true
    [java.lang.String, java.lang.Integer] | [10, 5] | false


## MapContains

Checks if a map contains a given key. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContains.html)

Input type: `java.util.Map`

??? example "Example MapContains"

    === "Java"

        ``` java
        final MapContains function = new MapContains("a");
        ```

    === "JSON"

        ``` json
        {
          "class" : "MapContains",
          "key" : "a"
        }
        ```

    === "Python"

        ``` python
        g.MapContains( 
          key="a" 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.HashMap | {a=1, b=2, c=3} | true
    java.util.HashMap | {b=2, c=3} | false
    java.util.HashMap | {a=null, b=2, c=3} | true


## MapContainsPredicate

Checks if a map contains a key that matches a predicate. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContainsPredicate.html)

Input type: `java.util.Map`

??? example "Example of MapContainsPredicate with Regex Pedicate"

    === "Java"

        ``` java
        final MapContainsPredicate function = new MapContainsPredicate(new Regex("a.*"));
        ```

    === "JSON"

        ``` json
        {
          "class" : "MapContainsPredicate",
          "keyPredicate" : {
            "class" : "Regex",
            "value" : {
              "java.util.regex.Pattern" : "a.*"
            }
          }
        }
        ```

    === "Python"

        ``` python
        {
          "class" : "uk.gov.gchq.koryphe.impl.predicate.MapContainsPredicate",
          "keyPredicate" : {
            "class" : "uk.gov.gchq.koryphe.impl.predicate.Regex",
            "value" : {
              "java.util.regex.Pattern" : "a.*"
            }
          }
        }
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.HashMap | {a1=1, a2=2, b=2, c=3} | true
    java.util.HashMap | {b=2, c=3} | false
    java.util.HashMap | {a=null, b=2, c=3} | true


## PredicateMap

Extracts a value from a map then applies the predicate to it. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/PredicateMap.html)

Input type: `java.util.Map`

??? example "Example FreqMap is more than 2"

    === "Java"

        ``` java
        final PredicateMap function = new PredicateMap("key1", new IsMoreThan(2L));
        ```

    === "JSON"

        ``` json
        {
          "class" : "PredicateMap",
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : {
              "Long" : 2
            }
          },
          "key" : "key1"
        }
        ```

    === "Python"

        ``` python
        g.PredicateMap( 
          key="key1", 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 2}, 
            or_equal_to=False 
          ) 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    uk.gov.gchq.gaffer.types.FreqMap | {key1=1} | false
    uk.gov.gchq.gaffer.types.FreqMap | {key1=2} | false
    uk.gov.gchq.gaffer.types.FreqMap | {key1=3} | true
    uk.gov.gchq.gaffer.types.FreqMap | {key1=3, key2=0} | true
    uk.gov.gchq.gaffer.types.FreqMap | {key2=3} | false


??? example "Example FreqMap is more than or equal to 2"

    === "Java"

        ``` java
        final PredicateMap function = new PredicateMap("key1", new IsMoreThan(2L, true));
        ```

    === "JSON"

        ``` json
        {
          "class" : "PredicateMap",
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : true,
            "value" : {
              "Long" : 2
            }
          },
          "key" : "key1"
        }
        ```

    === "Python"

        ``` python
        g.PredicateMap( 
          key="key1", 
          predicate=g.IsMoreThan( 
            value={'java.lang.Long': 2}, 
            or_equal_to=True 
          ) 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    uk.gov.gchq.gaffer.types.FreqMap | {key1=1} | false
    uk.gov.gchq.gaffer.types.FreqMap | {key1=2} | true
    uk.gov.gchq.gaffer.types.FreqMap | {key1=3} | true
    uk.gov.gchq.gaffer.types.FreqMap | {key1=3, key2=0} | true
    uk.gov.gchq.gaffer.types.FreqMap | {key2=3} | false


??? example "Example Map with date key having value that exists"

    === "Java"

        ``` java
        final PredicateMap function = new PredicateMap(new Date(0L), new Exists());
        ```

    === "JSON"

        ``` json
        {
          "class" : "PredicateMap",
          "predicate" : {
            "class" : "Exists"
          },
          "key" : {
            "Date" : 0
          }
        }
        ```

    === "Python"

        ``` python
        g.PredicateMap( 
          key={'java.util.Date': 0}, 
          predicate=g.Exists() 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.util.HashMap | {Thu Jan 01 01:00:00 GMT 1970=1} | true
    java.util.HashMap | {Mon Nov 07 11:00:16 GMT 2022=2} | false


## StringContains

Checks if a string contains some value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/StringContains.html)

!!! note
    
    The StringContains predicate is case sensitive by default, hence only exact matches are found.

Input type: `java.lang.String`

??? example "Example StringContains"

    === "Java"

        ``` java
        final StringContains function = new StringContains("test");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringContains",
          "value" : "test",
          "ignoreCase" : false
        }
        ```

    === "Python"

        ``` python
        g.StringContains( 
          value="test", 
          ignore_case=False 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | This is a Test | false
    java.lang.String | Test | false
    java.lang.String | test | true


??? example "Example StringContains ignoring case"

    === "Java"

        ``` java
        final StringContains function = new StringContains("test", true);
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringContains",
          "value" : "test",
          "ignoreCase" : true
        }
        ```

    === "Python"

        ``` python
        g.StringContains( 
          value="test", 
          ignore_case=True 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | This is a Test | true
    java.lang.String | Test | true
    java.lang.String | test | true



## Regex

Checks if a string matches a pattern. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Regex.html)

Input type: `java.lang.String`

??? example "Example abc"

    === "Java"

        ``` java
        final Regex function = new Regex("[a-d0-4]");
        ```

    === "JSON"

        ``` json
        {
          "class" : "Regex",
          "value" : {
            "java.util.regex.Pattern" : "[a-d0-4]"
          }
        }
        ```

    === "Python"

        ``` python
        g.Regex( 
          value={'java.util.regex.Pattern': '[a-d0-4]'} 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | a | true
    java.lang.String | z | false
    java.lang.String | az | false
    java.lang.Character | a | ClassCastException: java.lang.Character cannot be cast to java.lang.String
    java.lang.String | 2 | true
    java.lang.Integer | 2 | ClassCastException: java.lang.Integer cannot be cast to java.lang.String
    java.lang.Long | 2 | ClassCastException: java.lang.Long cannot be cast to java.lang.String


## MultiRegex

Checks if a string matches at least one pattern. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/MultiRegex.html)

Input type: `java.lang.String`

??? example "Example MultiRegex"

    === "Java"

        ``` java
        final MultiRegex function = new MultiRegex(new Pattern[]{Pattern.compile("[a-d]"), Pattern.compile("[0-4]")});
        ```

    === "JSON"

        ``` json
        {
          "class" : "MultiRegex",
          "value" : [ {
            "java.util.regex.Pattern" : "[a-d]"
          }, {
            "java.util.regex.Pattern" : "[0-4]"
          } ]
        }
        ```

    === "Python"

        ``` python
        g.MultiRegex( 
          value=[ 
            {'java.util.regex.Pattern': '[a-d]'}, 
            {'java.util.regex.Pattern': '[0-4]'} 
          ] 
        )
        ```
    
    Example inputs:
    
    Input Type | Input | Result
    ---------- | ----- | ------
    java.lang.String | a | true
    java.lang.String | z | false
    java.lang.String | az | false
    java.lang.Character | a | ClassCastException: java.lang.Character cannot be cast to java.lang.String
    java.lang.String | 2 | true
    java.lang.Integer | 2 | ClassCastException: java.lang.Integer cannot be cast to java.lang.String
    java.lang.Long | 2 | ClassCastException: java.lang.Long cannot be cast to java.lang.String

