# Koryphe Operators

Operators from the Koryphe library.

## And

Applies the logical AND operation to 2 booleans. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/And.html)

Input type: `java.lang.Boolean`

??? example "Example And with Booleans or Nulls"

    === "Java"

        ``` java
        final And and = new And();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.And"
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.Boolean | true and true | java.lang.Boolean | true
    java.lang.Boolean | true and false | java.lang.Boolean | false
    java.lang.Boolean | false and false | java.lang.Boolean | false
    java.lang.Boolean | false and null | java.lang.Boolean | false
    java.lang.Boolean | true and null | java.lang.Boolean | true
     | null and null |  | null
    
## Or

Applies the logical OR operation to 2 booleans. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Or.html)

Input type: `java.lang.Boolean`

??? example "Example Or with Booleans, Nulls or other"

    === "Java"

        ``` java
        final Or or = new Or();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Or"
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.Boolean | true and true | java.lang.Boolean | true
    java.lang.Boolean | true and false | java.lang.Boolean | true
    java.lang.Boolean | false and false | java.lang.Boolean | false
    java.lang.Boolean | false and null | java.lang.Boolean | false
    java.lang.Boolean | true and null | java.lang.Boolean | true
     | null and null |  | null
    java.lang.String | test and 3 |  | ClassCastException: java.lang.String cannot be cast to java.lang.Boolean
    java.lang.Integer | 0 and 0 |  | ClassCastException: java.lang.Integer cannot be cast to java.lang.Boolean
    java.lang.Integer | 1 and 0 |  | ClassCastException: java.lang.Integer cannot be cast to java.lang.Boolean
    
## First

Returns the first non-null value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/First.html)

Input type: `java.lang.Object`

??? example "Example First with String and Null"

    === "Java"

        ``` java
        final First first = new First();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.First"
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.String | first and second | java.lang.String | first
    java.lang.String | first and null | java.lang.String | first
     | null and second | java.lang.String | second
     | null and null |  | null
    
## Min

Returns the min value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Min.html)

Input type: `java.lang.Comparable`

??? example "Example Min with String, Integer and Null"

    === "Java"

        ``` java
        final Min function = new Min();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
        }
        ```

    === "Python"

        ``` python
        g.Min()
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.Integer | 5 and 6 | java.lang.Integer | 5
    java.lang.String | inputString and anotherInputString | java.lang.String | anotherInputString
     | null and 1 | java.lang.Integer | 1
    
## Max

Returns the max value. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Max.html)

Input type: `java.lang.Comparable`

??? example "Example Max with String, Integer and Null"

    === "Java"

        ``` java
        final Max function = new Max();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
        }
        ```

    === "Python"

        ``` python
        g.Max()
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.Integer | 5 and 6 | java.lang.Integer | 6
    java.lang.String | inputString and anotherInputString | java.lang.String | inputString
     | null and 1 | java.lang.Integer | 1
    
## Product

Calculates the product of 2 numbers. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Product.html)

Input type: `java.lang.Number`

??? example "Example Product with Numbers"

    === "Java"

        ``` java
        final Product product = new Product();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Product"
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.Long | 20 and 3 | java.lang.Long | 60
    java.lang.Integer | 300 and 400 | java.lang.Integer | 120000
    java.lang.Double | 0.0 and 3.0 | java.lang.Double | 0.0
    java.lang.Short | 50 and 50 | java.lang.Short | 2500
    java.lang.Short | 500 and 500 | java.lang.Short | 32767
    java.lang.Integer | -5 and 5 | java.lang.Integer | -25
    java.lang.Long | 20 and null | java.lang.Long | 20
    
## Sum

Calculates the sum of 2 numbers. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/Sum.html)

Input type: `java.lang.Number`

??? example "Example Sum with Numbers"

    === "Java"

        ``` java
        final Sum sum = new Sum();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Sum"
        }
        ```

    === "Python"

        ``` python
        g.Sum()
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.Long | 20 and 3 | java.lang.Long | 23
    java.lang.Integer | 300 and 400 | java.lang.Integer | 700
    java.lang.Double | 0.0 and 3.0 | java.lang.Double | 3.0
    java.lang.Short | 50 and 50 | java.lang.Short | 100
    java.lang.Short | 30000 and 10000 | java.lang.Short | 32767
    java.lang.Integer | -5 and 5 | java.lang.Integer | 0
    java.lang.Long | 20 and null | java.lang.Long | 20
    
## CollectionConcat

Concatenates two collections together. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/CollectionConcat.html)

Input type: `java.util.Collection`

??? example "Example CollectionConcat"

    === "Java"

        ``` java
        final CollectionConcat collectionConcat = new CollectionConcat();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat"
        }
        ```

    === "Python"

        ``` python
        g.CollectionConcat()
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.util.ArrayList | [test1] and [test2, test3] | java.util.ArrayList | [test1, test2, test3]
    java.util.ArrayList | [1] and [test2, test3] | java.util.ArrayList | [1, test2, test3]
    java.util.ArrayList | [] and [abc, cde] | java.util.ArrayList | [abc, cde]
    java.util.ArrayList | [test1] and null | java.util.ArrayList | [test1]
    java.util.HashSet | [a, b] and [b, c] | java.util.HashSet | [a, b, c]
    
## CollectionIntersect

Returns items common to two collections. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/CollectionIntersect.html)

Input type: `java.util.Collection`

??? example "Example CollectionIntersect"

    === "Java"

        ``` java
        final CollectionIntersect collectionIntersect = new CollectionIntersect();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.CollectionIntersect"
        }
        ```

    === "Python"

        ``` python
        g.CollectionIntersect()
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.util.ArrayList | [test1] and [test2, test3] | java.util.ArrayList | []
    java.util.ArrayList | [1] and [1, 2] | java.util.ArrayList | [1]
    java.util.ArrayList | [] and [abc, cde] | java.util.ArrayList | []
    java.util.ArrayList | [test1] and null | java.util.ArrayList | [test1]
    java.util.HashSet | [a, b] and [b, c] | java.util.HashSet | [b]
    
## StringConcat

Concatenates 2 strings. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/StringConcat.html)

Input type: `java.lang.String`

??? example "Example StringConcat with separator"

    === "Java"

        ``` java
        final StringConcat stringConcat = new StringConcat();
        stringConcat.setSeparator(" ");
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat",
          "separator" : " "
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.String | hello and world | java.lang.String | hello world
    java.lang.String | abc and null | java.lang.String | abc
     | null and null |  | null
    
??? example "Example StringConcat with default separator"

    === "Java"

        ``` java
        final StringConcat stringConcat = new StringConcat();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat",
          "separator" : ","
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.String | hello and world | java.lang.String | hello,world
    java.lang.String | abc and null | java.lang.String | abc
     | null and null |  | null
    
## StringDeduplicateConcat

Concatenates 2 strings and omits duplicates. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/binaryoperator/StringDeduplicateConcat.html)

Input type: `type`

??? example "Example StringDeduplicateConcat with separator"

    === "Java"

        ``` java
        final StringDeduplicateConcat stringDeduplicateConcat = new StringDeduplicateConcat();
        stringDeduplicateConcat.setSeparator(" ");
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.StringDeduplicateConcat",
          "separator" : " "
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.String | hello and world | java.lang.String | hello world
    java.lang.String | abc and null | java.lang.String | abc
     | null and null |  | null
    java.lang.String | abc, and abc | java.lang.String | abc, abc
    
??? example "Example StringDeduplicateConcat with default separator"

    === "Java"

        ``` java
        final StringDeduplicateConcat stringDeduplicateConcat = new StringDeduplicateConcat();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.StringDeduplicateConcat",
          "separator" : ","
        }
        ```
    
    Example inputs:

    Input Type | Inputs | Result Type | Results
    ---------- | ------ | ----------- | -------
    java.lang.String | hello and world | java.lang.String | hello,world
    java.lang.String | abc and null | java.lang.String | abc
     | null and null |  | null
    java.lang.String | abc, and abc | java.lang.String | abc
    </table>