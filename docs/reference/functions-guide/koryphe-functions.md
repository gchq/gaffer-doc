# Koryphe Functions

Functions from the Koryphe library.

## ApplyBiFunction

Applies the given BiFunction. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ApplyBiFunction.html)

Input type depends on the BiFunction given.

??? example "Example ApplyBiFunction using sum"

    === "Java"

        ``` java
        final ApplyBiFunction<Number, Number, Number> function = new ApplyBiFunction<>(new Sum());
        ```

    === "JSON"

        ``` json
        {
          "class" : "ApplyBiFunction",
          "function" : {
            "class" : "Sum"
          }
        }
        ```

    === "Python"

        ``` python
        g.ApplyBiFunction( 
          function=g.Sum() 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.Integer, java.lang.Integer] | [1, 2] | java.lang.Integer | 3
    [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer] | [1, 2, 3, 4] | java.lang.Integer | 3
    [java.lang.Double, java.lang.Double] | [1.1, 2.2] | java.lang.Double | 3.3000000000000003

??? example "Example ApplyBiFunction using max"

    === "Java"

        ``` java
        final ApplyBiFunction<Comparable, Comparable, Comparable> function = new ApplyBiFunction<>(new Max());
        ```

    === "JSON"

        ``` json
        {
          "class" : "ApplyBiFunction",
          "function" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
          }
        }
        ```

    === "Python"

        ``` python
        g.ApplyBiFunction( 
          function=g.Max() 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.Integer, java.lang.Integer] | [1, 2] | java.lang.Integer | 2
    [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer] | [1, 2, 3, 4] | java.lang.Integer | 2
    [java.lang.Double, java.lang.Double] | [1.1, 2.2] | java.lang.Double | 2.2

??? example "Example ApplyBiFunction using min"

    === "Java"

        ``` java
        final ApplyBiFunction<Comparable, Comparable, Comparable> function = new ApplyBiFunction<>(new Min());
        ```

    === "JSON"

        ``` json
        {
          "class" : "ApplyBiFunction",
          "function" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Min"
          }
        }
        ```

    === "Python"

        ``` python
        g.ApplyBiFunction( 
          function=g.Min() 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.Integer, java.lang.Integer] | [1, 2] | java.lang.Integer | 1
    [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer] | [1, 2, 3, 4] | java.lang.Integer | 1
    [java.lang.Double, java.lang.Double] | [1.1, 2.2] | java.lang.Double | 1.1

## Base64Decode

Decodes a base64 encoded byte array. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Base64Decode.html)

Input type: `byte[]`

??? example "Example Base64Decode"

    === "Java"

        ``` java
        final Base64Decode function = new Base64Decode();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Base64Decode"
        }
        ```

    === "Python"

        ``` python
        g.Base64Decode()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    byte[] | dGVzdCBzdHJpbmc= | byte[] | test string
     | null |  | null

## CallMethod

Allows you to call any public no-argument method on an object. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/CallMethod.html)

Input type: `java.lang.Object`

??? example "Example CallMethod with `toString`"

    === "Java"

        ``` java
        final CallMethod function = new CallMethod("toString");
        ```

    === "JSON"

        ``` json
        {
          "class" : "CallMethod",
          "method" : "toString"
        }
        ```

    === "Python"

        ``` python
        g.CallMethod( 
          method="toString" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | a string | java.lang.String | a string
    java.lang.Integer | 1 | java.lang.String | 1
    java.util.HashSet | [item2, item1] | java.lang.String | [item2, item1]
     | null |  | null

??? example "Example CallMethod with `toLowerCase`"

    === "Java"

        ``` java
        final CallMethod function = new CallMethod("toLowerCase");
        ```

    === "JSON"

        ``` json
        {
          "class" : "CallMethod",
          "method" : "toLowerCase"
        }
        ```

    === "Python"

        ``` python
        g.CallMethod( 
          method="toLowerCase" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | STRING1 | java.lang.String | string1
    java.lang.String | String2 | java.lang.String | string2
    java.lang.Integer | 10 |  | RuntimeException: Unable to invoke toLowerCase on object class class java.lang.Integer
     | null |  | null

## Cast

Casts input to specified class. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Cast.html)

Input type: `java.lang.Object`

??? example "Example Cast"

    === "Java"

        ``` java
        final Cast function = new Cast(String.class);
        ```

    === "JSON"

        ``` json
        {
          "class" : "Cast",
          "outputClass" : "String"
        }
        ```

    === "Python"

        ``` python
        g.Cast( 
          output_class="java.lang.String" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 5 |  | ClassCastException: Cannot cast java.lang.Integer to java.lang.String
    java.lang.String | inputString | java.lang.String | inputString
     | null |  | null

## Concat

Objects are concatenated by concatenating the outputs from calling `toString` on each object. The default separator is a comma. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Concat.html)

Input type: `java.lang.Object, java.lang.Object`

??? example "Example Concat"

    === "Java"

        ``` java
        final Concat function = new Concat(",");
        ```

    === "JSON"

        ``` json
        {
          "class" : "Concat",
          "separator" : ","
        }
        ```

    === "Python"

        ``` python
        g.Concat( 
          separator="," 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.String, java.lang.String] | [foo, bar] | java.lang.String | foo,bar
    [java.lang.String, ] | [foo, null] | java.lang.String | foo
    [java.lang.String, java.lang.String] | [foo, ] | java.lang.String | foo,
    [java.lang.String, java.lang.Double] | [foo, 1.2] | java.lang.String | foo,1.2
    [ ,java.lang.String] | [null, bar] | java.lang.String | bar
    [java.lang.Integer, java.lang.Integer] | [1, 2] | java.lang.String | 1,2

## CreateObject

Creates a new object of a given type. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/CreateObject.html)

Input type: `java.lang.Object`

??? example "Example CreateObject with String"

    === "Java"

        ``` java
        final CreateObject createObject = new CreateObject(String.class);
        ```

    === "JSON"

        ``` json
        {
          "class" : "CreateObject",
          "objectClass" : "String"
        }
        ```

    === "Python"

        ``` python
        g.CreateObject( 
          object_class="java.lang.String" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | a normal string | java.lang.String | a normal string
    byte[] | some bytes | java.lang.String | some bytes
     | null | java.lang.String | 
    java.lang.Integer | 123 |  | RuntimeException: Unable to create a new instance of java.lang.String. No constructors were found that accept a java.lang.Integer
    [C | [C@246c4b32 | java.lang.String | a char array

??? example "Example CreateObject with List"

    === "Java"

        ``` java
        final CreateObject createObject = new CreateObject(ArrayList.class);
        ```

    === "JSON"

        ``` json
        {
          "class" : "CreateObject",
          "objectClass" : "ArrayList"
        }
        ```

    === "Python"

        ``` python
        g.CreateObject( 
          object_class="java.util.ArrayList" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [list, example] | java.util.ArrayList | [list, example]
    java.util.HashSet | [set, example] | java.util.ArrayList | [set, example]
    java.util.HashMap | {} |  | RuntimeException: Unable to create a new instance of java.util.ArrayList. No constructors were found that accept a java.util.HashMap
     | null | java.util.ArrayList | []

## CsvLinesToMaps

Parses CSV lines into Maps. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/CsvLinesToMaps.html)

Input type: `java.lang.Iterable`

??? example "Example CsvLinesToMaps with delimiter"

    === "Java"

        ``` java
        final CsvLinesToMaps function = new CsvLinesToMaps()
                .header("header1", "header2", "header3")
                .firstRow(1)
                .delimiter('|');
        ```

    === "JSON"

        ``` json
        {
          "class" : "CsvLinesToMaps",
          "header" : [ "header1", "header2", "header3" ],
          "firstRow" : 1,
          "delimiter" : "|"
        }
        ```

    === "Python"

        ``` python
        g.CsvLinesToMaps( 
          delimiter="|", 
          header=[ 
            "header1", 
            "header2", 
            "header3" 
          ], 
          first_row=1 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.Arrays$ArrayList | [header1|header2|header3, value1|value2|value3] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}]
    java.util.Arrays$ArrayList | [header1|header2|header3, value1||value3] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=, header1=value1}]
    java.util.Arrays$ArrayList | [header1|header2|header3, value1|value2] |  | IllegalArgumentException: CSV has 2 columns, but there are 3 provided column names
    java.util.Arrays$ArrayList | [header1||header3, value1|value2|value3] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}]
    java.util.Arrays$ArrayList | [header1|header2, value1|value2|value3] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}]
    java.util.Arrays$ArrayList | [header1|header2|header3, value1|value2|value3, value4|value5|value6] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}, {header3=value6, header2=value5, header1=value4}]
    java.util.Arrays$ArrayList | [header1|header2|header3, , value4|value5|value6] |  | NoSuchElementException: No more CSV records available
    java.util.Arrays$ArrayList | [header1|header2|header3, null, value4|value5|value6] |  | NullPointerException: null
    java.util.Arrays$ArrayList | [value1|value2|value3, value4|value5|value6] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}, {header3=value6, header2=value5, header1=value4}]
    java.util.Arrays$ArrayList | [null, value1|value2|value3, value4|value5|value6] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}, {header3=value6, header2=value5, header1=value4}]

## CsvToMaps

Parses CSVs into Maps. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/CsvToMaps.html)

Input type: `java.lang.String`

??? example "Example CsvToMaps"

    === "Java"

        ``` java
        final CsvToMaps function = new CsvToMaps().header("header1", "header2", "header3").firstRow(1);
        ```

    === "JSON"

        ``` json
        {
          "class" : "CsvToMaps",
          "header" : [ "header1", "header2", "header3" ],
          "firstRow" : 1
        }
        ```

    === "Python"

        ``` python
        g.CsvToMaps( 
          header=[ 
            "header1", 
            "header2", 
            "header3" 
          ], 
          first_row=1 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | header1,header2,header3
    value1,value2,value3 | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}]
    java.lang.String | header1,header2,header3
    value1,value2,value3
    value4,value5,value6" | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=value2, header1=value1}, {header3=value6", header2=value5, header1=value4}]
    java.lang.String | header1,header2,header3
    ,,value3
    value4,value5,value6" | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [{header3=value3, header2=, header1=}, {header3=value6", header2=value5, header1=value4}]
    java.lang.String | header1,header2,header3,header4
    value1,value2,value3,value4
    value5,value6,value7,value8" |  | NoSuchElementException: null
     | null |  | null
    java.lang.String |  | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | []

## CurrentDate

Returns the current date and time, input is ignored. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/CurrentDate.html)

Input type (ignored): `java.lang.Object`

??? example "Example getting CurrentDate"

    === "Java"

        ``` java
        final CurrentDate currentDate = new CurrentDate();
        ```

    === "JSON"

        ``` json
        {
          "class" : "CurrentDate"
        }
        ```

    === "Python"

        ``` python
        g.CurrentDate()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
     | null | java.util.Date | Mon Nov 07 11:00:17 GMT 2022

## CurrentTime

Returns the current time in milliseconds, input is ignored. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/CurrentTime.html)

Input type (ignored): `java.lang.Object`

??? example "Example getting CurrentTime"

    === "Java"

        ``` java
        final CurrentTime currentTime = new CurrentTime();
        ```

    === "JSON"

        ``` json
        {
          "class" : "CurrentTime"
        }
        ```

    === "Python"

        ``` python
        g.CurrentTime()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
     | null | java.lang.Long | 1667818817897

## DefaultIfEmpty

Provides a default value if the input is empty. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/DefaultIfEmpty.html)

Input type: `java.lang.Object`

??? example "Example DefaultIfEmpty"

    === "Java"

        ``` java
        final DefaultIfEmpty function = new DefaultIfEmpty();
        ```

    === "JSON"

        ``` json
        {
          "class" : "DefaultIfEmpty"
        }
        ```

    === "Python"

        ``` python
        g.DefaultIfEmpty()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | String input | java.lang.String | String input
    java.lang.Long | 5 |  | IllegalArgumentException: Could not determine the size of the provided value
     | null |  | null
    java.lang.String |  |  | null

## DefaultIfNull

Provides a default value if the input is null. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/DefaultIfNull.html)

Input type: `java.lang.Object`

??? example "Example DefaultIfNull"

    === "Java"

        ``` java
        final DefaultIfNull function = new DefaultIfNull("DEFAULT");
        ```

    === "JSON"

        ``` json
        {
          "class" : "DefaultIfNull",
          "defaultValue" : "DEFAULT"
        }
        ```

    === "Python"

        ``` python
        g.DefaultIfNull( 
          default_value="DEFAULT" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | String input | java.lang.String | String input
    java.lang.Long | 5 | java.lang.Long | 5
     | null | java.lang.String | DEFAULT
    java.lang.String |  | java.lang.String | 

## DeserialiseJson

Parses a JSON string into Java objects. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/DeserialiseJson.html)

Input type: `java.lang.String`

??? example "Example DeserialiseJson"

    === "Java"

        ``` java
        final DeserialiseJson function = new DeserialiseJson();
        ```

    === "JSON"

        ``` json
        {
          "class" : "DeserialiseJson"
        }
        ```

    === "Python"

        ``` python
        g.DeserialiseJson()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | {"elements": [{"value": "value1"}, {"value": "value2"}]} | java.util.LinkedHashMap | {elements=[{value=value1}, {value=value2}]}
    java.lang.String | [ "ListValue1", "ListValue2", "ListValue3" ] | java.util.ArrayList | [ListValue1, ListValue2, ListValue3]
    java.lang.String | { "number":30 } | java.util.LinkedHashMap | {number=30}
    java.lang.String | { "false":true } | java.util.LinkedHashMap | {false=true}
    java.lang.String | {
      "class" : "uk.gov.gchq.gaffer.operation.data.EntitySeed",
      "vertex" : 1
    } | java.util.LinkedHashMap | {class=uk.gov.gchq.gaffer.operation.data.EntitySeed, vertex=1}
    java.lang.String | [ "listValue1", "listValue1", "listValue1" ] | java.util.ArrayList | [listValue1, listValue1, listValue1]
    java.lang.String | {
      "key1" : 1.0,
      "key2" : 2.2,
      "key3" : 3.3
    } | java.util.LinkedHashMap | {key1=1.0, key2=2.2, key3=3.3}

## DeserialiseXml

Parses an XML document into multiple Maps. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/DeserialiseXml.html)

Input type: `java.lang.String`

??? example "Example DeserialiseXml"

    === "Java"

        ``` java
        final DeserialiseXml function = new DeserialiseXml();
        ```

    === "JSON"

        ``` json
        {
          "class" : "DeserialiseXml"
        }
        ```

    === "Python"

        ``` python
        g.DeserialiseXml()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | &lt;element1&gt;value&lt;/element1&gt; | java.util.HashMap | {element1=value}
    java.lang.String | &lt;root&gt;&lt;element1&gt;value1&lt;/element1&gt;&lt;element2&gt;value2&lt;/element2&gt;&lt;/root&gt; | java.util.HashMap | {root={element1=value1, element2=value2}}
    java.lang.String | &lt;root&gt;&lt;element1&gt;&lt;element2&gt;value1&lt;/element2&gt;&lt;/element1&gt;&lt;element1&gt;&lt;element2&gt;value2&lt;/element2&gt;&lt;/element1&gt;&lt;/root&gt; | java.util.HashMap | {root={element1=[{element2=value1}, {element2=value2}]}}

## DictionaryLookup

Looks up a value in a Map. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/DictionaryLookup.html)

Input type: `java.lang.Object`

??? example "Example DictionaryLookup"

    === "Java"

        ``` java
        final DictionaryLookup<Integer, String> dictionaryLookup = new DictionaryLookup<>(map);
        ```

    === "JSON"

        ``` json
        {
          "class" : "DictionaryLookup",
          "dictionary" : {
            "1" : "one",
            "2" : "two",
            "3" : "three"
          }
        }
        ```

    === "Python"

        ``` python
        g.DictionaryLookup( 
          dictionary={'1': 'one', '2': 'two', '3': 'three'} 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 1 | java.lang.String | one
     | null |  | null
    java.lang.Integer | 4 |  | null
    java.lang.Long | 2 |  | null

## Divide

Divides the input integers, the resulting object is a Tuple2 containing the quotient and remainder. [x, y] -> [x/y, remainder]. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Divide.html)

Input type: `java.lang.Integer, java.lang.Integer`

??? example "Example Divide"

    === "Java"

        ``` java
        final Divide function = new Divide();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Divide"
        }
        ```

    === "Python"

        ``` python
        g.Divide()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.Integer, java.lang.Integer] | [6, 2] | [java.lang.Integer, java.lang.Integer] | [3, 0]
    [java.lang.Integer, java.lang.Integer] | [6, 4] | [java.lang.Integer, java.lang.Integer] | [1, 2]
    [java.lang.Integer, java.lang.Integer] | [6, 8] | [java.lang.Integer, java.lang.Integer] | [0, 6]
    [ ,java.lang.Integer] | [null, 2] |  | null
    [java.lang.Integer, ] | [6, null] | [java.lang.Integer, java.lang.Integer] | [6, 0]
    [java.lang.Double, java.lang.Double] | [6.1, 2.1] |  | IllegalArgumentException: Input tuple values do not match the required function input types

## DivideBy

Divide the input integer by the provided integer, the resulting object is a Tuple2 containing the quotient and remainder. x -> [x/divideBy, remainder]. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/DivideBy.html)

Input type: `java.lang.Integer`

??? example "Example DivideBy"

    === "Java"

        ``` java
        final DivideBy function = new DivideBy(2);
        ```

    === "JSON"

        ``` json
        {
          "class" : "DivideBy",
          "by" : 2
        }
        ```

    === "Python"

        ``` python
        g.DivideBy( 
          by=2 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 6 | [java.lang.Integer, java.lang.Integer] | [3, 0]
    java.lang.Integer | 5 | [java.lang.Integer, java.lang.Integer] | [2, 1]
     | null |  | null
    java.lang.Double | 6.1 |  | ClassCastException: java.lang.Double cannot be cast to java.lang.Integer

## ExtractKeys

An ExtractKeys will return the Set of keys from the provided Java Map. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ExtractKeys.html)

Input type: `java.util.Map`

??? example "Example ExtractKeys"

    === "Java"

        ``` java
        final ExtractKeys<String, Integer> function = new ExtractKeys<>();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractKeys"
        }
        ```

    === "Python"

        ``` python
        g.ExtractKeys()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {firstKey=2, thirdKey=9, secondKey=4} | java.util.HashMap$KeySet | [firstKey, thirdKey, secondKey]

## ExtractValue

An ExtractValue will return the value associated with the pre-configured key, from a supplied Java Map. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ExtractValue.html)

Input type: `java.util.Map`

??? example "Example ExtractValue"

    === "Java"

        ``` java
        final ExtractValue<String, Integer> function = new ExtractValue<>("blueKey");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractValue",
          "key" : "blueKey"
        }
        ```

    === "Python"

        ``` python
        g.ExtractValue( 
          key="blueKey" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {yellowKey=4, greenKey=9, redKey=5, blueKey=25} | java.lang.Integer | 25

## ExtractValues

An ExtractValues will return a Collection of the values from a provided Java Map. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ExtractValues.html)

Input type: `java.util.Map`

??? example "Example ExtractValues"

    === "Java"

        ``` java
        final ExtractValues<String, Integer> function = new ExtractValues<>();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractValues"
        }
        ```

    === "Python"

        ``` python
        g.ExtractValues()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {theKey=1, theWholeKey=2, nothingButTheKey=3} | java.util.HashMap$Values | [1, 2, 3]

## FirstItem

For a given Iterable, a FirstItem will extract the first item. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/FirstItem.html)

Input type: `java.lang.Iterable`

??? example "Example FirstItem"

    === "Java"

        ``` java
        final FirstItem<Integer> function = new FirstItem<>();
        ```

    === "JSON"

        ``` json
        {
          "class" : "FirstItem"
        }
        ```

    === "Python"

        ``` python
        g.FirstItem()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [2, 3, 5] | java.lang.Integer | 2
    java.util.ArrayList | [7, 11, 13] | java.lang.Integer | 7
    java.util.ArrayList | [17, 19, null] | java.lang.Integer | 17
    java.util.ArrayList | [null, 19, 27] |  | null
     | null |  | IllegalArgumentException: Input cannot be null

## FirstValid

Provides the first valid item from an iterable based on a predicate. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/FirstValid.html)

Input type: `java.lang.Iterable`

??? example "Example FirstValid with a predicate"

    === "Java"

        ``` java
        final FirstValid function = new FirstValid(new StringContains("my"));
        ```

    === "JSON"

        ``` json
        {
          "class" : "FirstValid",
          "predicate" : {
            "class" : "StringContains",
            "value" : "my",
            "ignoreCase" : false
          }
        }
        ```

    === "Python"

        ``` python
        g.FirstValid( 
          predicate=g.StringContains( 
            value="my", 
            ignore_case=False 
          ) 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [1, 2, 3] |  | ClassCastException: java.lang.Integer cannot be cast to java.lang.String
    java.util.ArrayList | [Hello, my, value] | java.lang.String | my
    java.util.ArrayList | [MY, tummy, my, My] | java.lang.String | tummy
     | null |  | null

??? example "Example FirstValid without a predicate"

    FirstValid always returns null if no predicate is specified
    
    === "Java"

        ``` java
        final FirstValid function = new FirstValid(null);
        ```

    === "JSON"

        ``` json
        {
          "class" : "FirstValid"
        }
        ```

    === "Python"

        ``` python
        g.FirstValid()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [a, b, c] |  | null
    java.util.ArrayList | [1, 2, 3] |  | null
    java.util.ArrayList | [] |  | null

## FunctionChain

Applies the given functions consecutively. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/FunctionChain.html)

Input type: `java.lang.Object`

??? example "Example FunctionChain using tuple adapted functions"

    === "Java"

        ``` java
        final FunctionChain function = new FunctionChain.Builder<>()
                .execute(new Integer[]{0}, new ToUpperCase(), new Integer[]{1})
                .execute(new Integer[]{1}, new ToSet(), new Integer[]{2})
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "FunctionChain",
          "functions" : [ {
            "class" : "TupleAdaptedFunction",
            "selection" : [ 0 ],
            "function" : {
              "class" : "ToUpperCase"
            },
            "projection" : [ 1 ]
          }, {
            "class" : "TupleAdaptedFunction",
            "selection" : [ 1 ],
            "function" : {
              "class" : "uk.gov.gchq.koryphe.impl.function.ToSet"
            },
            "projection" : [ 2 ]
          } ]
        }
        ```

    === "Python"

        ``` python
        g.FunctionChain( 
          functions=[ 
            g.TupleAdaptedFunction( 
              selection=[ 
                0 
              ], 
              function=g.ToUpperCase(), 
              projection=[ 
                1 
              ] 
            ), 
            g.TupleAdaptedFunction( 
              selection=[ 
                1 
              ], 
              function=g.ToSet(), 
              projection=[ 
                2 
              ] 
            ) 
          ] 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.String,  ,] | [someString, null, null] | [java.lang.String, java.lang.String, java.util.HashSet] | [someString, SOMESTRING, [SOMESTRING]]
    [java.lang.String,  ,] | [SOMESTRING, null, null] | [java.lang.String, java.lang.String, java.util.HashSet] | [SOMESTRING, SOMESTRING, [SOMESTRING]]
    [java.lang.String,  ,] | [somestring, null, null] | [java.lang.String, java.lang.String, java.util.HashSet] | [somestring, SOMESTRING, [SOMESTRING]]
    [java.lang.String,  ,] | [@&pound;$%, null, null] | [java.lang.String, java.lang.String, java.util.HashSet] | [@&pound;$%, @&pound;$%, [@&pound;$%]]
    [java.lang.String,  ,] | [1234, null, null] | [java.lang.String, java.lang.String, java.util.HashSet] | [1234, 1234, [1234]]
    [java.lang.String,  ,] | [, null, null] | [java.lang.String, java.lang.String, java.util.HashSet] | [, , []]
    [ , ,] | [null, null, null] | [ , ,java.util.HashSet] | [null, null, [null]]
    [java.lang.Integer,  ,] | [1234, null, null] | [java.lang.Integer, java.lang.String, java.util.HashSet] | [1234, 1234, [1234]]

??? example "Example FunctionChain using standard functions"

    === "Java"

        ``` java
        final FunctionChain function = new FunctionChain.Builder<>()
                .execute(new ToLowerCase())
                .execute(new ToTypeSubTypeValue())
                .execute(new ToEntityId())
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "FunctionChain",
          "functions" : [ {
            "class" : "ToLowerCase"
          }, {
            "class" : "ToTypeSubTypeValue"
          }, {
            "class" : "ToEntityId"
          } ]
        }
        ```

    === "Python"

        ``` python
        g.FunctionChain( 
          functions=[ 
            g.ToLowerCase(), 
            g.ToTypeSubTypeValue(), 
            g.ToEntityId() 
          ] 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | a string | uk.gov.gchq.gaffer.operation.data.EntitySeed | EntitySeed[vertex=TypeSubTypeValue[value=a string]]
    java.lang.String | UPPER | uk.gov.gchq.gaffer.operation.data.EntitySeed | EntitySeed[vertex=TypeSubTypeValue[value=upper]]
     | null | uk.gov.gchq.gaffer.operation.data.EntitySeed | EntitySeed[vertex=TypeSubTypeValue[]]
    java.lang.Integer | 12 | uk.gov.gchq.gaffer.operation.data.EntitySeed | EntitySeed[vertex=TypeSubTypeValue[value=12]]

## FunctionMap

Applies a function to all values in a map. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/function/FunctionMap.html)

Input type: `java.util.Map`

??? example "Example using FunctionMap to multiply all map values by 10"

    === "Java"

        ``` java
        final FunctionMap<String, Integer, Integer> function = new FunctionMap<>(new MultiplyBy(10));
        ```

    === "JSON"

        ``` json
        {
          "class" : "FunctionMap",
          "function" : {
            "class" : "MultiplyBy",
            "by" : 10
          }
        }
        ```

    === "Python"

        ``` python
        g.FunctionMap( 
          function=g.MultiplyBy( 
            by=10 
          ) 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {key1=1, key2=2, key3=3} | java.util.HashMap | {key1=10, key2=20, key3=30}
    java.util.HashMap | {key1=null, key2=2, key3=3} | java.util.HashMap | {key1=null, key2=20, key3=30}
     | null |  | null

## Gunzip

Decompresses gzipped data. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Gunzip.html)

Input type: `byte[]`

??? example "Example Gunzip"

    === "Java"

        ``` java
        final Gunzip gunzip = new Gunzip();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Gunzip"
        }
        ```

    === "Python"

        ``` python
        g.Gunzip()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    byte[] | �       +I-.Q(.)��K EG    | byte[] | test string

## Identity

Just returns the input. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Identity.html)

Input type: `java.lang.Object`

??? example "Example Identity"

    === "Java"

        ``` java
        final Identity function = new Identity();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Identity"
        }
        ```

    === "Python"

        ``` python
        g.Identity()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 6 | java.lang.Integer | 6
    java.lang.Double | 6.1 | java.lang.Double | 6.1
    java.lang.String | input1 | java.lang.String | input1
    java.util.ArrayList | [1, 2, 3] | java.util.ArrayList | [1, 2, 3]
     | null |  | null

## If

Conditionally applies a function. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/If.html)

Input type: `java.lang.Object`

??? example "Example If"

    This example tests first whether the input contains 'upper'. If so, then it is converted to upper case. Otherwise, it is converted to lower case.
    
    === "Java"

        ``` java
        final If<String, String> predicate = new If<String, String>()
                .predicate(new StringContains("upper"))
                .then(new ToUpperCase())
                .otherwise(new ToLowerCase());
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.function.If",
          "predicate" : {
            "class" : "StringContains",
            "value" : "upper",
            "ignoreCase" : false
          },
          "then" : {
            "class" : "ToUpperCase"
          },
          "otherwise" : {
            "class" : "ToLowerCase"
          }
        }
        ```

    === "Python"

        ``` python
        g.If( 
          predicate=g.StringContains( 
            value="upper", 
            ignore_case=False 
          ), 
          then=g.ToUpperCase(), 
          otherwise=g.ToLowerCase() 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
     | null |  | null
    java.lang.String | Convert me to upper case | java.lang.String | CONVERT ME TO UPPER CASE
    java.lang.String | Convert me to lower case | java.lang.String | convert me to lower case
    java.lang.String |  | java.lang.String | 

## Increment

Adds a given number to the input, returned value type will match the input type. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Increment.html)

Input type: `java.lang.Number`

??? example "Example Increment with Int"

    === "Java"

        ``` java
        final Increment increment = new Increment(3);
        ```

    === "JSON"

        ``` json
        {
          "class" : "Increment",
          "increment" : 3
        }
        ```

    === "Python"

        ``` python
        g.Increment( 
          increment=3 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 2 | java.lang.Integer | 5
    java.lang.Double | 2.0 | java.lang.Integer | 5
    java.lang.Float | 2.0 | java.lang.Integer | 5
    java.lang.Long | 2 | java.lang.Integer | 5

??? example "Example Increment with Double"

    === "Java"

        ``` java
        final Increment increment = new Increment(3.0);
        ```

    === "JSON"

        ``` json
        {
          "class" : "Increment",
          "increment" : 3.0
        }
        ```

    === "Python"

        ``` python
        g.Increment( 
          increment=3.0 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 2 | java.lang.Double | 5.0
    java.lang.Double | 2.0 | java.lang.Double | 5.0
    java.lang.Float | 2.0 | java.lang.Double | 5.0
    java.lang.Long | 2 | java.lang.Double | 5.0
    java.lang.String | 33 |  | ClassCastException: java.lang.String cannot be cast to java.lang.Number
    java.lang.String | three |  | ClassCastException: java.lang.String cannot be cast to java.lang.Number
     | null | java.lang.Double | 3.0

??? example "Example Increment with Float"

    === "Java"

        ``` java
        final Increment increment = new Increment(3.0f);
        ```

    === "JSON"

        ``` json
        {
          "class" : "Increment",
          "increment" : {
            "Float" : 3.0
          }
        }
        ```

    === "Python"

        ``` python
        g.Increment( 
          increment={'java.lang.Float': 3.0} 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 2 | java.lang.Float | 5.0
    java.lang.Double | 2.0 | java.lang.Float | 5.0
    java.lang.Float | 2.0 | java.lang.Float | 5.0
    java.lang.Long | 2 | java.lang.Float | 5.0
    java.lang.String | 33 |  | ClassCastException: java.lang.String cannot be cast to java.lang.Number
    java.lang.String | three |  | ClassCastException: java.lang.String cannot be cast to java.lang.Number
     | null | java.lang.Float | 3.0

## IterableConcat

For a given Iterable of Iterables, an IterableConcat will essentially perform a FlatMap on the input, by concatenating each of the nested iterables into a single flattened iterable. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/IterableConcat.html)

Input type: `java.lang.Iterable`

??? example "Example IterableConcat"

    === "Java"

        ``` java
        final IterableConcat<Integer> function = new IterableConcat<>();
        ```

    === "JSON"

        ``` json
        {
          "class" : "IterableConcat"
        }
        ```

    === "Python"

        ``` python
        g.IterableConcat()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [[2, 3, 5], [7, 11, 13], [17, 19, 23]] | uk.gov.gchq.koryphe.util.IterableUtil$ChainedIterable | [2, 3, 5, 7, 11, 13, 17, 19, 23]
    java.util.ArrayList | [[29, 31, 37]] | uk.gov.gchq.koryphe.util.IterableUtil$ChainedIterable | [29, 31, 37]
    java.util.ArrayList | [[2, 3, 5], [7, 11, 13], null] |  | NullPointerException: null
     | null |  | IllegalArgumentException: iterables are required

## IterableFilter

An IterableFilter applies a given predicate to each element in an Iterable and returns the filtered iterable. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/IterableFilter.html)

Input type: `java.lang.Iterable`

??? example "Example IterableFilter with IsMoreThan"

    === "Java"

        ``` java
        final IterableFilter<Integer> function = new IterableFilter<>(new IsMoreThan(5));
        ```

    === "JSON"

        ``` json
        {
          "class" : "IterableFilter",
          "predicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 5
          }
        }
        ```

    === "Python"

        ``` python
        g.IterableFilter( 
          predicate=g.IsMoreThan( 
            value=5, 
            or_equal_to=False 
          ) 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [1, 2, 3] | uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable | []
    java.util.ArrayList | [5, 10, 15] | uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable | [10, 15]
    java.util.ArrayList | [7, 9, 11] | uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable | [7, 9, 11]
    java.util.ArrayList | [1, null, 3] | uk.gov.gchq.koryphe.util.IterableUtil$FilteredIterable | []
     | null |  | null

## IterableFlatten

Combines the items in an iterable into a single item based on the supplied operator. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/IterableFlatten.html)

Input type: `java.lang.Iterable`

??? example "Example IterableFlatten without Binary Operator"

    === "Java"

        ``` java
        final IterableFlatten function = new IterableFlatten(null);
        ```

    === "JSON"

        ``` json
        {
          "class" : "IterableFlatten"
        }
        ```

    === "Python"

        ``` python
        g.IterableFlatten()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [a, b, c] |  | null

??? example "Example IterableFlatten with Binary Operator"

    === "Java"

        ``` java
        final IterableFlatten function = new IterableFlatten<>(new Max());
        ```

    === "JSON"

        ``` json
        {
          "class" : "IterableFlatten",
          "operator" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
          }
        }
        ```

    === "Python"

        ``` python
        g.IterableFlatten( 
          operator=g.Max() 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [1, 2, 3, 1] | java.lang.Integer | 3
    java.util.ArrayList | [1, null, 6] | java.lang.Integer | 6
    java.util.ArrayList | [] |  | null
     | null |  | null

## IterableFunction

An IterableFunction is useful for applying a provided function, or functions, to each entry of a supplied Iterable. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/IterableFunction.html)

Input type: `java.lang.Iterable`

??? example "Example IterableFunction with a single function"

    === "Java"

        ``` java
        final IterableFunction<Integer, Integer> function = new IterableFunction<>(new MultiplyBy(2));
        ```

    === "JSON"

        ``` json
        {
          "class" : "IterableFunction",
          "functions" : [ {
            "class" : "MultiplyBy",
            "by" : 2
          } ]
        }
        ```

    === "Python"

        ``` python
        g.IterableFunction( 
          functions=[ 
            g.MultiplyBy( 
              by=2 
            ) 
          ] 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [1, 2, 3] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [2, 4, 6]
    java.util.ArrayList | [5, 10, 15] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [10, 20, 30]
    java.util.ArrayList | [7, 9, 11] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [14, 18, 22]
    java.util.ArrayList | [1, null, 3] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [2, null, 6]
     | null |  | null

??? example "Example IterableFunction with multiple functions"

    Here we build a chain of functions using the IterableFunction's Builder, whereby the output of one function is the input to the next.
    
    === "Java"

        ``` java
        final IterableFunction<Integer, Integer> function = new IterableFunction.Builder<Integer>()
                .first(new MultiplyBy(2))
                .then(new MultiplyBy(4))
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "IterableFunction",
          "functions" : [ {
            "class" : "MultiplyBy",
            "by" : 2
          }, {
            "class" : "MultiplyBy",
            "by" : 4
          } ]
        }
        ```

    === "Python"

        ``` python
        g.IterableFunction( 
          functions=[ 
            g.MultiplyBy( 
              by=2 
            ), 
            g.MultiplyBy( 
              by=4 
            ) 
          ] 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [2, 4, 10] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [16, 32, 80]
    java.util.ArrayList | [3, 9, 11] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [24, 72, 88]
    java.util.ArrayList | [1, null, 3] | uk.gov.gchq.koryphe.util.IterableUtil$MappedIterable | [8, null, 24]
     | null |  | null

## IterableLongest

Returns the longest item in the provided iterable. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/IterableLongest.html)

Input type: `java.lang.Iterable`

??? example "Example IterableLongest"

    === "Java"

        ``` java
        final IterableLongest function = new IterableLongest();
        ```

    === "JSON"

        ``` json
        {
          "class" : "IterableLongest"
        }
        ```

    === "Python"

        ``` python
        g.IterableLongest()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [[1, 2], [1.5]] | java.util.ArrayList | [1, 2]
    java.util.ArrayList | [which, is, the, longest, word] | java.lang.String | longest
    java.util.ArrayList | [] |  | null
     | null |  | null

## LastItem

For a given Iterable, a LastItem will extract the last item. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/LastItem.html)

Input type: `java.lang.Iterable`

??? example "Example LastItem"

    === "Java"

        ``` java
        final LastItem<Integer> function = new LastItem<>();
        ```

    === "JSON"

        ``` json
        {
          "class" : "LastItem"
        }
        ```

    === "Python"

        ``` python
        g.LastItem()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [1, 2, 3] | java.lang.Integer | 3
    java.util.ArrayList | [5, 8, 13] | java.lang.Integer | 13
    java.util.ArrayList | [21, 34, 55] | java.lang.Integer | 55
    java.util.ArrayList | [1, null, 3] | java.lang.Integer | 3
    java.util.ArrayList | [1, 2, null] |  | null
     | null |  | IllegalArgumentException: Input cannot be null

## Length

Attempts to return the length of an object. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Length.html)

Input type: `java.lang.Object`

??? example "Example Length"

    === "Java"

        ``` java
        final Length function = new Length();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Length"
        }
        ```

    === "Python"

        ``` python
        g.Length()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [Entity[vertex=1,group=entity,properties=Properties[]], Entity[vertex=2,group=entity,properties=Properties[]], Entity[vertex=3,group=entity,properties=Properties[]], Entity[vertex=4,group=entity,properties=Properties[]], Entity[vertex=5,group=entity,properties=Properties[]]] | java.lang.Integer | 5
    java.util.HashMap | {option3=value3, option1=value1, option2=value2} | java.lang.Integer | 3
    uk.gov.gchq.gaffer.data.graph.Walk | [[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=A,destination=E,directed=true,group=BasicEdge,properties=Properties[]]]] | java.lang.Integer | 4
    java.lang.Integer | 5 |  | IllegalArgumentException: Could not determine the size of the provided value
    java.lang.String | some string | java.lang.Integer | 11
    java.lang.String[] | [1, 2] | java.lang.Integer | 2
     | null | java.lang.Integer | 0

## Longest

Determines which of two input objects is the longest. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Longest.html)

Input type: `java.lang.Object, java.lang.Object`

??? example "Example Longest"

    === "Java"

        ``` java
        final Longest function = new Longest();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Longest"
        }
        ```

    === "Python"

        ``` python
        g.Longest()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.String, java.lang.String] | [smaller, long string] | java.lang.String | long string
    [java.util.ArrayList, java.util.HashSet] | [[1, 2], [1.5]] | java.util.ArrayList | [1, 2]
    [ ,java.lang.String] | [null, value] | java.lang.String | value
     | null |  | IllegalArgumentException: Input tuple is required

## MapFilter

A Function which applies the given predicates to the keys and/or values. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/MapFilter.html)

Input type: `java.util.Map`

??? example "Example MapFilter on keys"

    MapFilter with key predicate.
    
    === "Java"

        ``` java
        final MapFilter keyFilter = new MapFilter().keyPredicate(
                new StringContains("a")
        );
        ```

    === "JSON"

        ``` json
        {
          "class" : "MapFilter",
          "keyPredicate" : {
            "class" : "StringContains",
            "value" : "a",
            "ignoreCase" : false
          }
        }
        ```

    === "Python"

        ``` python
        g.MapFilter(
          key_predicate=g.StringContains( 
            value="a", 
            ignore_case=False 
          ) 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {giraffe=0, cat=3, dog=2} | java.util.HashMap | {giraffe=0, cat=3}

??? example "Example MapFilter on values"

    MapFilter with value predicate.
    
    === "Java"

        ``` java
        final MapFilter valueFilter = new MapFilter().valuePredicate(
                new IsMoreThan(10)
        );
        ```

    === "JSON"

        ``` json
        {
          "class" : "MapFilter",
          "valuePredicate" : {
            "class" : "IsMoreThan",
            "orEqualTo" : false,
            "value" : 10
          }
        }
        ```

    === "Python"

        ``` python
        g.MapFilter( 
          value_predicate=g.IsMoreThan( 
            value=10, 
            or_equal_to=False 
          ) 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {Pizza=30, Casserole=4, Steak=12} | java.util.HashMap | {Pizza=30, Steak=12}

??? example "Example MapFilter on keys and values"

    MapFilter with key-value Predicate.
    
    === "Java"

        ``` java
        final MapFilter keyValueFilter = new MapFilter()
                .keyValuePredicate(new AreEqual());
        ```

    === "JSON"

        ``` json
        {
          "class" : "MapFilter",
          "keyValuePredicate" : {
            "class" : "AreEqual"
          }
        }
        ```

    === "Python"

        ``` python
        g.MapFilter( 
          key_value_predicate=g.AreEqual() 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {1=2, 3=3, 6=4} | java.util.HashMap | {3=3}

## MapToTuple

Converts a Map to a Tuple. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/MapToTuple.html)

Input type: `java.util.Map`

??? example "Example MapToTuple"

    === "Java"

        ``` java
        final MapToTuple<String> function = new MapToTuple<>();
        ```

    === "JSON"

        ``` json
        {
          "class" : "MapToTuple"
        }
        ```

    === "Python"

        ``` python
        g.MapToTuple()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.HashMap | {A=1, B=2, C=3} | uk.gov.gchq.koryphe.tuple.MapTuple | [1, 2, 3]
    uk.gov.gchq.gaffer.types.FreqMap | {value2=1, value1=2} | uk.gov.gchq.koryphe.tuple.MapTuple | [1, 2]

## Multiply

The input integers are multiplied together. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/Multiply.html)

Input type: `java.lang.Integer, java.lang.Integer`

??? example "Example Multiply"

    === "Java"

        ``` java
        final Multiply function = new Multiply();
        ```

    === "JSON"

        ``` json
        {
          "class" : "Multiply"
        }
        ```

    === "Python"

        ``` python
        g.Multiply()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    [java.lang.Integer, java.lang.Integer] | [2, 3] | java.lang.Integer | 6
    [ ,java.lang.Integer] | [null, 3] |  | null
    [java.lang.Integer, ] | [2, null] | java.lang.Integer | 2
    [java.lang.Double, java.lang.Double] | [2.1, 3.1] |  | IllegalArgumentException: Input tuple values do not match the required function input types

## MultiplyBy

Multiply the input integer by the provided number. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/MultiplyBy.html)

Input type: `java.lang.Integer`

??? example "Example MultiplyBy"

    === "Java"

        ``` java
        final MultiplyBy function = new MultiplyBy(2);
        ```

    === "JSON"

        ``` json
        {
          "class" : "MultiplyBy",
          "by" : 2
        }
        ```

    === "Python"

        ``` python
        g.MultiplyBy( 
          by=2 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 6 | java.lang.Integer | 12
    java.lang.Integer | 5 | java.lang.Integer | 10
     | null |  | null
    java.lang.Double | 6.1 |  | ClassCastException: java.lang.Double cannot be cast to java.lang.Integer

## MultiplyLongBy

Multiply the input Long by the provided number. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/MultiplyLongBy.html)

Input type: `java.lang.Long`

??? example "Example MultiplyLongBy"

    === "Java"

        ``` java
        final MultiplyLongBy function = new MultiplyLongBy(2L);
        ```

    === "JSON"

        ``` json
        {
          "class" : "MultiplyLongBy",
          "by" : 2
        }
        ```

    === "Python"

        ``` python
        g.MultiplyLongBy( 
          by=2 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Long | 6 | java.lang.Long | 12
    java.lang.Long | 5 | java.lang.Long | 10
     | null |  | null
    java.lang.Double | 6.1 |  | ClassCastException: java.lang.Double cannot be cast to java.lang.Long

## NthItem

For a given Iterable, an NthItem will extract the item at the Nth index, where n is a user-provided selection. (Consider that this is array-backed, so a selection of "1" will extract the item at index 1, ie the 2nd item). [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/NthItem.html)

Input type: `java.lang.Iterable`

??? example "Example NthItem"

    === "Java"

        ``` java
        final NthItem<Integer> function = new NthItem<>(2);
        ```

    === "JSON"

        ``` json
        {
          "class" : "NthItem",
          "selection" : 2
        }
        ```

    === "Python"

        ``` python
        g.NthItem( 
          selection=2 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [3, 1, 4] | java.lang.Integer | 4
    java.util.ArrayList | [1, 5, 9] | java.lang.Integer | 9
    java.util.ArrayList | [2, 6, 5] | java.lang.Integer | 5
    java.util.ArrayList | [2, null, 5] | java.lang.Integer | 5
    java.util.ArrayList | [2, 6, null] |  | null
     | null |  | IllegalArgumentException: Input cannot be null

## ParseDate

Parses a date string. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ParseDate.html)

Input type: `java.lang.String`

??? example "Example ParseDate with GMT+4"

    === "Java"

        ``` java
        final ParseDate parseDate = new ParseDate();
        parseDate.setFormat("yyyy-MM-dd HH:mm:ss.SSS");
        parseDate.setTimeZone("Etc/GMT+4");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ParseDate",
          "format" : "yyyy-MM-dd HH:mm:ss.SSS",
          "timeZone" : "Etc/GMT+4",
          "microseconds" : false
        }
        ```

    === "Python"

        ``` python
        g.ParseDate( 
          time_zone="Etc/GMT+4", 
          format="yyyy-MM-dd HH:mm:ss.SSS", 
          microseconds=False 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | 2015-10-21 16:29:00.000 | java.util.Date | Wed Oct 21 21:29:00 BST 2015
    java.lang.String | 1985-10-26 09:00:00.000 | java.util.Date | Sat Oct 26 14:00:00 BST 1985
    java.lang.String | 1885-01-01 12:00:00.000 | java.util.Date | Thu Jan 01 16:00:00 GMT 1885

??? example "Example ParseDate with GMT+0"

    === "Java"

        ``` java
        final ParseDate parseDate = new ParseDate();
        parseDate.setFormat("yyyy-MM-dd HH:mm");
        parseDate.setTimeZone("Etc/GMT+0");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ParseDate",
          "format" : "yyyy-MM-dd HH:mm",
          "timeZone" : "Etc/GMT+0",
          "microseconds" : false
        }
        ```

    === "Python"

        ``` python
        g.ParseDate( 
          time_zone="Etc/GMT+0", 
          format="yyyy-MM-dd HH:mm", 
          microseconds=False 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | 2015-10-21 16:29 | java.util.Date | Wed Oct 21 17:29:00 BST 2015
    java.lang.String | 1985-10-26 09:00 | java.util.Date | Sat Oct 26 10:00:00 BST 1985
    java.lang.String | 1885-01-01 12:00 | java.util.Date | Thu Jan 01 12:00:00 GMT 1885

## ParseTime

Parses a date string into a timestamp. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ParseTime.html)

Input type: `java.lang.String`

??? example "Example ParseTime"

    === "Java"

        ``` java
        final ParseTime parseTime = new ParseTime();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ParseTime",
          "timeUnit" : "MILLISECOND"
        }
        ```

    === "Python"

        ``` python
        g.ParseTime( 
          time_unit="MILLISECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | 2015-10-21 16:29:00.000 | java.lang.Long | 1445441340000
    java.lang.String | 1985-10-26 09:00:00.000 | java.lang.Long | 499161600000
    java.lang.String | 1885-01-01 12:00:00.000 | java.lang.Long | -2682244800000

??? example "Example ParseTime with format"

    === "Java"

        ``` java
        final ParseTime parseTime = new ParseTime().format("yyyy-MM hh:mm");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ParseTime",
          "format" : "yyyy-MM hh:mm",
          "timeUnit" : "MILLISECOND"
        }
        ```

    === "Python"

        ``` python
        g.ParseTime( 
          format="yyyy-MM hh:mm", 
          time_unit="MILLISECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | 2015-10 16:29 | java.lang.Long | 1443713340000
    java.lang.String | 1985-10 09:00 | java.lang.Long | 497001600000
    java.lang.String | 1885-01 12:00 | java.lang.Long | -2682288000000
    java.lang.String | 2015-10-21 16:29 |  | IllegalArgumentException: Date string could not be parsed: 2015-10-21 16:29

??? example "Example ParseTime with format and GMT"

    === "Java"

        ``` java
        final ParseTime parseTime = new ParseTime()
                .format("yyyy-MM-dd")
                .timeUnit("SECOND")
                .timeZone("GMT");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ParseTime",
          "format" : "yyyy-MM-dd",
          "timeZone" : "GMT",
          "timeUnit" : "SECOND"
        }
        ```

    === "Python"

        ``` python
        g.ParseTime( 
          time_zone="GMT", 
          format="yyyy-MM-dd", 
          time_unit="SECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | 2015-10-21 16:29:00.000 | java.lang.Long | 1445385600
    java.lang.String | 1985-10-26 09:00:00.000 | java.lang.Long | 499132800
    java.lang.String | 1885-01-01 12:00:00.000 | java.lang.Long | -2682288000

## ReverseString

Reverse characters in string. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ReverseString.html)

Input type: `java.lang.String`

??? example "Example ReverseString"

    === "Java"

        ``` java
        final ReverseString function = new ReverseString();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ReverseString"
        }
        ```

    === "Python"

        ``` python
        g.ReverseString()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | reverse | java.lang.String | esrever
    java.lang.String | esrever | java.lang.String | reverse
     | null |  | null
    java.lang.Long | 54 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

## SetValue

Returns a set value from any input. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/SetValue.html)

Input type: `java.lang.Object`

??? example "Example SetValue"

    === "Java"

        ``` java
        final SetValue function = new SetValue(5);
        ```

    === "JSON"

        ``` json
        {
          "class" : "SetValue",
          "value" : 5
        }
        ```

    === "Python"

        ``` python
        g.SetValue(
          value=5 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 4 | java.lang.Integer | 5
    java.lang.Integer | 5 | java.lang.Integer | 5
    java.lang.String | aString | java.lang.Integer | 5
    java.util.Arrays$ArrayList | [4, 5] | java.lang.Integer | 5
     | null | java.lang.Integer | 5

## StringAppend

Appends a provided suffix to a string. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringAppend.html)

Input type: `java.lang.String`

??? example "Example StringAppend"

    === "Java"

        ``` java
        final StringAppend function = new StringAppend("mySuffix");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringAppend",
          "suffix" : "mySuffix"
        }
        ```

    === "Python"

        ``` python
        g.StringAppend( 
          suffix="mySuffix" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | a string of some kind | java.lang.String | a string of some kindmySuffix
    java.lang.String |  | java.lang.String | mySuffix
     | null |  | null
    java.lang.Long | 54 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

## StringJoin

Joins together all strings in an iterable using the supplied delimiter. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringJoin.html)

Input type: `java.lang.Iterable`

??? example "Example StringJoin without a delimiter"

    === "Java"

        ``` java
        final StringJoin function = new StringJoin();
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringJoin"
        }
        ```

    === "Python"

        ``` python
        g.StringJoin()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [here, are, my, strings] | java.lang.String | herearemystrings
    java.util.ArrayList | [single] | java.lang.String | single
    java.util.ArrayList | [] | java.lang.String | 
     | null |  | null

??? example "Example StringJoin with a delimiter"

    === "Java"

        ``` java
        final StringJoin function = new StringJoin("-");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringJoin",
          "delimiter" : "-"
        }
        ```

    === "Python"

        ``` python
        g.StringJoin( 
          delimiter="-" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [here, are, my, strings] | java.lang.String | here-are-my-strings
    java.util.ArrayList | [single] | java.lang.String | single
    java.util.ArrayList | [] | java.lang.String | 
     | null |  | null

## StringPrepend

Prepends a string with the provided prefix. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringPrepend.html)

Input type: `java.lang.String`

??? example "Example StringPrepend"

    === "Java"

        ``` java
        final StringPrepend function = new StringPrepend("myPrefix");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringPrepend",
          "prefix" : "myPrefix"
        }
        ```

    === "Python"

        ``` python
        g.StringPrepend( 
          prefix="myPrefix" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | a string of some kind | java.lang.String | myPrefixa string of some kind
    java.lang.String |  | java.lang.String | myPrefix
     | null |  | null
    java.lang.Long | 54 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

## StringRegexReplace

Replace all portions of a string which match a regular expression. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringRegexReplace.html)

Input type: `java.lang.String`

??? example "Example StringRegexReplace"

    === "Java"

        ``` java
        final StringRegexReplace function = new StringRegexReplace("[tT]ea", "cake");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringRegexReplace",
          "regex" : "[tT]ea",
          "replacement" : "cake"
        }
        ```

    === "Python"

        ``` python
        g.StringRegexReplace( 
          regex="[tT]ea", 
          replacement="cake" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | tea | java.lang.String | cake
    java.lang.String | Tea | java.lang.String | cake
    java.lang.String | TEA | java.lang.String | TEA
    java.lang.String | brainteaser | java.lang.String | braincakeser
     | null |  | null
    java.lang.String | coffee | java.lang.String | coffee
    java.lang.Long | 5 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

## StringRegexSplit

Split a string using the provided regular expression. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringRegexSplit.html)

Input type: `java.lang.String`

??? example "Example StringRegexSplit"

    === "Java"

        ``` java
        final StringRegexSplit function = new StringRegexSplit("[ \\t]+");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringRegexSplit",
          "regex" : "[ \\t]+"
        }
        ```

    === "Python"

        ``` python
        g.StringRegexSplit( 
          regex="[ \t]+" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | no-delimiters-in-this-string | java.util.Arrays$ArrayList | [no-delimiters-in-this-string]
    java.lang.String | string  with  two  spaces | java.util.Arrays$ArrayList | [string, with, two, spaces]
    java.lang.String | string with one space | java.util.Arrays$ArrayList | [string, with, one, space]
    java.lang.String | tab	delimited	string | java.util.Arrays$ArrayList | [tab, delimited, string]
    java.lang.String |  | java.util.Arrays$ArrayList | []
     | null |  | null

## StringReplace

Replace all portions of a string which match a regular expression. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringReplace.html)

Input type: `java.lang.String`

??? example "Example StringReplace"

    === "Java"

        ``` java
        final StringReplace function = new StringReplace("[tea", "cake");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringReplace",
          "replacement" : "cake",
          "searchString" : "[tea"
        }
        ```

    === "Python"

        ``` python
        g.StringReplace( 
          search_string="[tea", 
          replacement="cake" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | tea | java.lang.String | tea
    java.lang.String | Tea | java.lang.String | Tea
    java.lang.String | TEA | java.lang.String | TEA
    java.lang.String | brainteaser | java.lang.String | brainteaser
     | null |  | null
    java.lang.String | coffee | java.lang.String | coffee
    java.lang.Long | 5 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

## StringSplit

Split a string using the provided regular expression. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringSplit.html)

Input type: `java.lang.String`

??? example "Example StringSplit"

    === "Java"

        ``` java
        final StringSplit function = new StringSplit(" ");
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringSplit",
          "delimiter" : " "
        }
        ```

    === "Python"

        ``` python
        g.StringSplit( 
          delimiter=" " 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | no-delimiters-in-this-string | java.util.Arrays$ArrayList | [no-delimiters-in-this-string]
    java.lang.String | string  with  two  spaces | java.util.Arrays$ArrayList | [string, with, two, spaces]
    java.lang.String | string with one space | java.util.Arrays$ArrayList | [string, with, one, space]
    java.lang.String | tab	delimited	string | java.util.Arrays$ArrayList | [tab	delimited	string]
    java.lang.String |  | java.util.Arrays$ArrayList | []
     | null |  | null

## StringTrim

Trims all whitespace around a string. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringTrim.html)

Input type: `java.lang.String`

??? example "Example StringTrim"

    === "Java"

        ``` java
        final StringTrim function = new StringTrim();
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringTrim"
        }
        ```

    === "Python"

        ``` python
        g.StringTrim()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | trailing spaces    | java.lang.String | trailing spaces
    java.lang.String |     leading spaces | java.lang.String | leading spaces
    java.lang.String | noSpaces | java.lang.String | noSpaces
     | null |  | null
    java.lang.Long | 54 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

## StringTruncate

Truncates a string, with optional ellipses. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/StringTruncate.html)

Input type: `java.lang.String`

??? example "Example StringTruncate without ellipses"

    === "Java"

        ``` java
        final StringTruncate function = new StringTruncate(5, false);
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringTruncate",
          "length" : 5,
          "ellipses" : false
        }
        ```

    === "Python"

        ``` python
        g.StringTruncate(
          length=5, 
          ellipses=False 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | no more than five | java.lang.String | no mo
    java.lang.String | four | java.lang.String | four
    java.lang.String |  | java.lang.String | 
     | null |  | null
    java.lang.Long | 54 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

??? example "Example StringTruncate with ellipses"

    === "Java"

        ``` java
        final StringTruncate function = new StringTruncate(5, true);
        ```

    === "JSON"

        ``` json
        {
          "class" : "StringTruncate",
          "length" : 5,
          "ellipses" : true
        }
        ```

    === "Python"

        ``` python
        g.StringTruncate(
          length=5, 
          ellipses=True 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | no more than five | java.lang.String | no mo...
    java.lang.String | four | java.lang.String | four
    java.lang.String |  | java.lang.String | 
     | null |  | null
    java.lang.Long | 54 |  | ClassCastException: java.lang.Long cannot be cast to java.lang.String

## ToArray

Converts an Object to an Array. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToArray.html)

Input type: `java.lang.Object`

??? example "Example ToArray"

    === "Java"

        ``` java
        final ToArray function = new ToArray();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.function.ToArray"
        }
        ```

    === "Python"

        ``` python
        g.ToArray()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | test | java.lang.Object[] | [test]
     | null | java.lang.Object[] | [null]
    java.lang.Long | 30 | java.lang.Object[] | [30]
    uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[type=t,subType=st,value=v] | java.lang.Object[] | [TypeSubTypeValue[type=t,subType=st,value=v]]
    java.util.Arrays$ArrayList | [a, b, c] | java.lang.Object[] | [a, b, c]
    java.util.HashSet | [1, 2] | java.lang.Object[] | [1, 2]

## ToBytes

Extracts the bytes from a string. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToBytes.html)

Input type: `java.lang.String`

??? example "Example ToBytes"

    === "Java"

        ``` java
        final ToBytes toBytes = new ToBytes(StandardCharsets.UTF_16);
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToBytes",
          "charset" : "UTF-16"
        }
        ```

    === "Python"

        ``` python
        g.ToBytes( 
          charset="UTF-16" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | example String | byte[] | �� e x a m p l e   S t r i n g
    java.lang.String |  | byte[] | 
     | null |  | null
    java.lang.Integer | 1 |  | ClassCastException: java.lang.Integer cannot be cast to java.lang.String

## ToDateString

Converts a date to a String. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToDateString.html)

Input type: `java.util.Date`

??? example "Example ToDateString with microsecond"

    === "Java"

        ``` java
        final ToDateString function = new ToDateString("yyyy-MM-dd HH:mm:ss.SSS");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToDateString",
          "format" : "yyyy-MM-dd HH:mm:ss.SSS"
        }
        ```

    === "Python"

        ``` python
        g.ToDateString( 
          format="yyyy-MM-dd HH:mm:ss.SSS" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.Date | Tue Jan 06 19:39:25 GMT 1970 | java.lang.String | 1970-01-06 19:39:25.200
    java.util.Date | Thu Jan 01 01:00:00 GMT 1970 | java.lang.String | 1970-01-01 01:00:00.000
    java.lang.Long | 1667818823612 |  | ClassCastException: java.lang.Long cannot be cast to java.util.Date
    java.util.Date | Fri Dec 26 06:20:34 GMT 1969 | java.lang.String | 1969-12-26 06:20:34.800

??? example "Example ToDateString with minute"

    === "Java"

        ``` java
        final ToDateString function = new ToDateString("yy-MM-dd HH:mm");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToDateString",
          "format" : "yy-MM-dd HH:mm"
        }
        ```

    === "Python"

        ``` python
        g.ToDateString( 
          format="yy-MM-dd HH:mm" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.Date | Tue Jan 06 19:39:25 GMT 1970 | java.lang.String | 70-01-06 19:39
    java.util.Date | Thu Jan 01 01:00:00 GMT 1970 | java.lang.String | 70-01-01 01:00
    java.lang.Long | 1667818823683 |  | ClassCastException: java.lang.Long cannot be cast to java.util.Date
    java.util.Date | Fri Dec 26 06:20:34 GMT 1969 | java.lang.String | 69-12-26 06:20
     | null |  | null

??? example "Example ToDateString with date only"

    === "Java"

        ``` java
        final ToDateString function = new ToDateString("yy-MM-dd");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToDateString",
          "format" : "yy-MM-dd"
        }
        ```

    === "Python"

        ``` python
        g.ToDateString( 
          format="yy-MM-dd" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.Date | Tue Jan 06 19:39:25 GMT 1970 | java.lang.String | 70-01-06
    java.util.Date | Thu Jan 01 01:00:00 GMT 1970 | java.lang.String | 70-01-01
    java.lang.Long | 1667818823752 |  | ClassCastException: java.lang.Long cannot be cast to java.util.Date
    java.util.Date | Fri Dec 26 06:20:34 GMT 1969 | java.lang.String | 69-12-26
     | null |  | null

## ToInteger

Returns any input as Integer. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToInteger.html)

Input type: `java.lang.Object`

??? example "Example ToInteger"

    === "Java"

        ``` java
        final ToInteger function = new ToInteger();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.function.ToInteger"
        }
        ```

    === "Python"

        ``` python
        g.ToInteger()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 4 | java.lang.Integer | 4
    java.lang.Long | 5 | java.lang.Integer | 5
    java.lang.String | 5 | java.lang.Integer | 5
    java.lang.String | aString |  | NumberFormatException: For input string: "aString"
    java.util.Arrays$ArrayList | [6, 3] |  | IllegalArgumentException: Could not convert value to Integer: [6, 3]
     | null |  | null

## ToList

Converts an Object to a List. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToList.html)

Input type: `java.lang.Object`

??? example "Example ToList"

    === "Java"

        ``` java
        final ToList function = new ToList();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.function.ToList"
        }
        ```

    === "Python"

        ``` python
        g.ToList()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | test | java.util.ArrayList | [test]
     | null | java.util.ArrayList | [null]
    java.lang.Long | 30 | java.util.ArrayList | [30]
    uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[type=t,subType=st,value=v] | java.util.ArrayList | [TypeSubTypeValue[type=t,subType=st,value=v]]
    java.lang.String[] | [a, b, c] | java.util.ArrayList | [a, b, c]
    java.util.HashSet | [1, 2] | java.util.ArrayList | [1, 2]

## ToLong

Returns any input as Long. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToLong.html)

Input type: `java.lang.Object`

??? example "Example ToLong"

    === "Java"

        ``` java
        final ToLong function = new ToLong();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToLong"
        }
        ```

    === "Python"

        ``` python
        g.ToLong()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 4 | java.lang.Long | 4
    java.lang.Long | 5 | java.lang.Long | 5
    java.lang.String | 5 | java.lang.Long | 5
    java.lang.String | aString |  | NumberFormatException: For input string: "aString"
    java.util.Arrays$ArrayList | [6, 3] |  | IllegalArgumentException: Could not convert value to Long: [6, 3]
     | null |  | null

## ToLowerCase

Performs toLowerCase on input object. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToLowerCase.html)

Input type: `java.lang.Object`

??? example "Example ToLowerCase"

    === "Java"

        ``` java
        final ToLowerCase function = new ToLowerCase();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToLowerCase"
        }
        ```

    === "Python"

        ``` python
        g.ToLowerCase()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 4 | java.lang.String | 4
    java.lang.Long | 5 | java.lang.String | 5
    java.lang.String | ACAPTIALISEDSTRING | java.lang.String | acaptialisedstring
    java.lang.String | alowercasestring | java.lang.String | alowercasestring
    java.lang.String | aString | java.lang.String | astring
    java.util.Arrays$ArrayList | [6, 3] | java.lang.String | [6, 3]
     | null |  | null

## ToNull

Returns `null` on any input object. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToNull.html)

Input type: `java.lang.Object`

??? example "Example ToNull"

    === "Java"

        ``` java
        final ToNull function = new ToNull();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToNull"
        }
        ```

    === "Python"

        ``` python
        g.ToNull()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 4 |  | null
    java.lang.Long | 5 |  | null
    java.lang.String | aString |  | null
    java.util.Arrays$ArrayList | [6, 3] |  | null
     | null |  | null

## ToSet

Converts an Object to a Set. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToSet.html)

Input type: `java.lang.Object`

??? example "Example ToSet"

    === "Java"

        ``` java
        final ToSet function = new ToSet();
        ```

    === "JSON"

        ``` json
        {
          "class" : "uk.gov.gchq.koryphe.impl.function.ToSet"
        }
        ```

    === "Python"

        ``` python
        g.ToSet()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | test | java.util.HashSet | [test]
     | null | java.util.HashSet | [null]
    java.lang.Long | 30 | java.util.HashSet | [30]
    uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[type=t,subType=st,value=v] | java.util.HashSet | [TypeSubTypeValue[type=t,subType=st,value=v]]
    java.lang.String[] | [a, b, c] | java.util.HashSet | [a, b, c]
    java.util.Arrays$ArrayList | [test1, test2] | java.util.HashSet | [test2, test1]

## ToString

Calls toString on each input. If the input is null, null is returned. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToString.html)

Input type: `java.lang.Object`

??? example "Example ToString"

    === "Java"

        ``` java
        final ToString function = new ToString();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToString"
        }
        ```

    === "Python"

        ``` python
        g.ToString()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 1 | java.lang.String | 1
    java.lang.Double | 2.5 | java.lang.String | 2.5
    java.lang.String | abc | java.lang.String | abc
     | null |  | null

## ToTuple

Converts an Object into a Tuple. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToTuple.html)

Input type: `java.lang.Object`

??? example "Example ToTuple"

    === "Java"

        ``` java
        final ToTuple function = new ToTuple();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToTuple"
        }
        ```

    === "Python"

        ``` python
        g.ToTuple()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [1, 2, 3, 4] | [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer] | [1, 2, 3, 4]
    java.lang.Integer[] | [1, 2, 3, 4] | [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer] | [1, 2, 3, 4]
    java.util.HashMap | {A=1, B=2, C=3} | uk.gov.gchq.koryphe.tuple.MapTuple | [1, 2, 3]

## ToUpperCase

Calls toString followed by toUpperCase on each input. If the input is null, null is returned. [Javadoc](https://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/function/ToUpperCase.html)

Input type: `java.lang.Object`

??? example "Example ToUpperCase"

    === "Java"

        ``` java
        final ToUpperCase function = new ToUpperCase();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToUpperCase"
        }
        ```

    === "Python"

        ``` python
        g.ToUpperCase()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.Integer | 4 | java.lang.String | 4
    java.lang.Long | 5 | java.lang.String | 5
    java.lang.String | ACAPTIALISEDSTRING | java.lang.String | ACAPTIALISEDSTRING
    java.lang.String | alowercasestring | java.lang.String | ALOWERCASESTRING
    java.lang.String | aString | java.lang.String | ASTRING
    java.util.Arrays$ArrayList | [6, 3] | java.lang.String | [6, 3]
     | null |  | null
