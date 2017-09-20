# Predicate Examples
_This page has been generated from code. To make any changes please update the example doc in the [doc](https://github.com/gchq/Gaffer/tree/master/doc/src/main/java/uk/gov/gchq/gaffer/doc) module, run it and replace the content of this page with the output._


1. [AgeOff](#ageoff-example)
2. [And](#and-example)
3. [AreEqual](#areequal-example)
4. [AreIn](#arein-example)
5. [CollectionContains](#collectioncontains-example)
6. [Exists](#exists-example)
7. [HyperLogLogPlusIsLessThan](#hyperloglogplusislessthan-example)
8. [IsA](#isa-example)
9. [IsEqual](#isequal-example)
10. [IsFalse](#isfalse-example)
11. [IsIn](#isin-example)
12. [IsLessThan](#islessthan-example)
13. [IsMoreThan](#ismorethan-example)
14. [IsShorterThan](#isshorterthan-example)
15. [IsTrue](#istrue-example)
16. [IsXLessThanY](#isxlessthany-example)
17. [IsXMoreThanY](#isxmorethany-example)
18. [MapContains](#mapcontains-example)
19. [MapContains](#mapcontains-example)
20. [MultiRegex](#multiregex-example)
21. [Not](#not-example)
22. [Or](#or-example)
23. [PredicateMap](#predicatemap-example)
24. [Regex](#regex-example)


AgeOff example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.AgeOff](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/AgeOff.html).

#### Age off in milliseconds

As Java:


```java
final AgeOff function = new AgeOff(100000L);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.AgeOff",
  "ageOffTime" : 100000
}
```

Input type:

```
java.lang.Long
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td></td><td>java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Long</td></tr>
<tr><td>java.lang.Long</td><td>1505915501498</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1505915401498</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1505915601498</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>1505915501498</td><td>java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Long</td></tr>
</table>

-----------------------------------------------




And example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.And](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/And.html).

#### Is less than 3 and is more than 0

As Java:


```java
final And function = new And<>(
        new IsLessThan(3),
        new IsMoreThan(0)
);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
    "orEqualTo" : false,
    "value" : 3
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 0
  } ]
}
```

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>0</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>false</td></tr>
</table>

-----------------------------------------------

#### First item is less than 2 and second item is more than 5

As Java:


```java
final And function = new And.Builder()
        .select(0)
        .execute(new IsLessThan(2))
        .select(1)
        .execute(new IsMoreThan(5))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.And",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
      "orEqualTo" : false,
      "value" : 2
    },
    "selection" : [ 0 ]
  }, {
    "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
      "orEqualTo" : false,
      "value" : 5
    },
    "selection" : [ 1 ]
  } ]
}
```

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 10]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 1]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 10]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 1]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 10]</td><td>false</td></tr>
<tr><td>[java.lang.Integer]</td><td>[1]</td><td>false</td></tr>
</table>

-----------------------------------------------




AreEqual example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.AreEqual](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/AreEqual.html).

#### Are equal

As Java:


```java
final AreEqual function = new AreEqual();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.AreEqual"
}
```

Input type:

```
java.lang.Object, java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Double]</td><td>[1, 1.0]</td><td>false</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[2.5, 2.5]</td><td>true</td></tr>
<tr><td>[java.lang.String, ]</td><td>[, null]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[abc, abc]</td><td>true</td></tr>
</table>

-----------------------------------------------




AreIn example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.AreIn](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/AreIn.html).

#### Are in set

As Java:


```java
final AreIn function = new AreIn(1, 2, 3);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.AreIn",
  "values" : [ 1, 2, 3 ]
}
```

Input type:

```
java.util.Collection
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashSet</td><td>[1, 2, 3]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[1, 2, 3, 4]</td><td>false</td></tr>
<tr><td>java.util.HashSet</td><td>[4, 1]</td><td>false</td></tr>
<tr><td>java.util.HashSet</td><td>[1, 2]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[]</td><td>true</td></tr>
</table>

-----------------------------------------------




CollectionContains example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.CollectionContains](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/CollectionContains.html).

#### Collection contains

As Java:


```java
final CollectionContains function = new CollectionContains(1);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.CollectionContains",
  "value" : 1
}
```

Input type:

```
java.util.Collection
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashSet</td><td>[1, 2, 3]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[1]</td><td>true</td></tr>
<tr><td>java.util.HashSet</td><td>[2]</td><td>false</td></tr>
<tr><td>java.util.HashSet</td><td>[]</td><td>false</td></tr>
</table>

-----------------------------------------------




Exists example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.Exists](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Exists.html).

#### Exists

As Java:


```java
final Exists function = new Exists();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
<tr><td>java.lang.String</td><td></td><td>true</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>true</td></tr>
</table>

-----------------------------------------------




HyperLogLogPlusIsLessThan example
-----------------------------------------------
See javadoc - [uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/predicate/HyperLogLogPlusIsLessThan.html).

#### Hyper log log plus is less than 2

As Java:


```java
final HyperLogLogPlusIsLessThan function = new HyperLogLogPlusIsLessThan(2);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan",
  "orEqualTo" : false,
  "value" : 2
}
```

Input type:

```
com.clearspring.analytics.stream.cardinality.HyperLogLogPlus
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@6123bf71</td><td>true</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@5f4db3e7</td><td>false</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3b048113</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Hyper log log plus is less than or equal to 2

As Java:


```java
final HyperLogLogPlusIsLessThan function = new HyperLogLogPlusIsLessThan(2, true);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.gaffer.sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan",
  "orEqualTo" : true,
  "value" : 2
}
```

Input type:

```
com.clearspring.analytics.stream.cardinality.HyperLogLogPlus
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@6123bf71</td><td>true</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@5f4db3e7</td><td>true</td></tr>
<tr><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus</td><td>com.clearspring.analytics.stream.cardinality.HyperLogLogPlus@3b048113</td><td>false</td></tr>
</table>

-----------------------------------------------




IsA example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsA](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsA.html).

#### Is a string

As Java:


```java
final IsA function = new IsA(String.class);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsA",
  "type" : "java.lang.String"
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Double</td><td>2.5</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>true</td></tr>
</table>

-----------------------------------------------

#### Is a number

As Java:


```java
final IsA function = new IsA(Number.class);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsA",
  "type" : "java.lang.Number"
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Double</td><td>2.5</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>false</td></tr>
</table>

-----------------------------------------------




IsEqual example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsEqual](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsEqual.html).

#### Is equal to 5

As Java:


```java
final IsEqual function = new IsEqual(5);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
  "value" : 5
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Is equal to string 5

As Java:


```java
final IsEqual function = new IsEqual("5");
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
  "value" : "5"
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Is equal long 5

As Java:


```java
final IsEqual function = new IsEqual(5L);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
  "value" : {
    "java.lang.Long" : 5
  }
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------




IsFalse example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsFalse](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsFalse.html).

#### Is false

As Java:


```java
final IsFalse function = new IsFalse();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsFalse"
}
```

Input type:

```
java.lang.Boolean
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Boolean</td><td>true</td><td>false</td></tr>
<tr><td>java.lang.Boolean</td><td>false</td><td>true</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>true</td><td>java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Boolean</td></tr>
</table>

-----------------------------------------------




IsIn example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsIn](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsIn.html).

#### Is in set

As Java:


```java
final IsIn function = new IsIn(5, 5L, "5", '5');
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsIn",
  "values" : [ 5, {
    "java.lang.Long" : 5
  }, "5", {
    "java.lang.Character" : "5"
  } ]
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Character</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------




IsLessThan example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsLessThan](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsLessThan.html).

#### Is less than 5

As Java:


```java
final IsLessThan function = new IsLessThan(5);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : false,
  "value" : 5
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Is less than or equal to 5

As Java:


```java
final IsLessThan function = new IsLessThan(5, true);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : true,
  "value" : 5
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Is less than a long 5

As Java:


```java
final IsLessThan function = new IsLessThan(5L);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : false,
  "value" : {
    "java.lang.Long" : 5
  }
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>1</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Is less than a string

As Java:


```java
final IsLessThan function = new IsLessThan("B");
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
  "orEqualTo" : false,
  "value" : "B"
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>A</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>B</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>C</td><td>false</td></tr>
</table>

-----------------------------------------------




IsMoreThan example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsMoreThan](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsMoreThan.html).

#### Is more than 5

As Java:


```java
final IsMoreThan function = new IsMoreThan(5);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
  "orEqualTo" : false,
  "value" : 5
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>true</td></tr>
</table>

-----------------------------------------------

#### Is more than or equal to 5

As Java:


```java
final IsMoreThan function = new IsMoreThan(5, true);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
  "orEqualTo" : true,
  "value" : 5
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>true</td></tr>
</table>

-----------------------------------------------

#### Is more than a long 5

As Java:


```java
final IsMoreThan function = new IsMoreThan(5L);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
  "orEqualTo" : false,
  "value" : {
    "java.lang.Long" : 5
  }
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Is more than a string

As Java:


```java
final IsMoreThan function = new IsMoreThan("B");
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
  "orEqualTo" : false,
  "value" : "B"
}
```

Input type:

```
java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>A</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>B</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>C</td><td>true</td></tr>
</table>

-----------------------------------------------




IsShorterThan example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsShorterThan](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsShorterThan.html).

#### Is shorter than 4

As Java:


```java
final IsShorterThan function = new IsShorterThan(4);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsShorterThan",
  "maxLength" : 4,
  "orEqualTo" : false
}
```

Input type:

```
java.lang.Object
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>123</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>1234</td><td>false</td></tr>
<tr><td>[Ljava.lang.Integer;</td><td>[Ljava.lang.Integer;@6be7aefa</td><td>true</td></tr>
<tr><td>[Ljava.lang.Integer;</td><td>[Ljava.lang.Integer;@74f4b68c</td><td>false</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3]</td><td>true</td></tr>
<tr><td>java.util.ArrayList</td><td>[1, 2, 3, 4]</td><td>false</td></tr>
<tr><td>java.util.HashMap</td><td>{1=a, 2=b, 3=c}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{4=d}</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>10000</td><td>java.lang.IllegalArgumentException: Could not determine the size of the provided value</td></tr>
<tr><td>java.lang.Long</td><td>10000</td><td>java.lang.IllegalArgumentException: Could not determine the size of the provided value</td></tr>
</table>

-----------------------------------------------




IsTrue example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsTrue](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsTrue.html).

#### Is true

As Java:


```java
final IsTrue function = new IsTrue();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsTrue"
}
```

Input type:

```
java.lang.Boolean
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Boolean</td><td>true</td><td>true</td></tr>
<tr><td>java.lang.Boolean</td><td>false</td><td>false</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>true</td><td>java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Boolean</td></tr>
</table>

-----------------------------------------------




IsXLessThanY example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsXLessThanY.html).

#### Is x less than y

As Java:


```java
final IsXLessThanY function = new IsXLessThanY();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY"
}
```

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 5]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[5, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Integer]</td><td>[1, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 5]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[5, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[10, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Long]</td><td>[1, 5]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[bcd, cde]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[bcd, abc]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.Integer]</td><td>[1, 5]</td><td>false</td></tr>
</table>

-----------------------------------------------




IsXMoreThanY example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.IsXMoreThanY](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/IsXMoreThanY.html).

#### Is x more than y

As Java:


```java
final IsXMoreThanY function = new IsXMoreThanY();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.IsXMoreThanY"
}
```

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[5, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 5]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Integer]</td><td>[10, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[5, 5]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[10, 5]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Long]</td><td>[10, 5]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[bcd, cde]</td><td>false</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[bcd, abc]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.Integer]</td><td>[10, 5]</td><td>false</td></tr>
</table>

-----------------------------------------------




MapContains example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.MapContains](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContains.html).

#### Map contains

As Java:


```java
final MapContains function = new MapContains("a");
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.MapContains",
  "key" : "a"
}
```

Input type:

```
java.util.Map
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{a=1, b=2, c=3}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{b=2, c=3}</td><td>false</td></tr>
<tr><td>java.util.HashMap</td><td>{a=null, b=2, c=3}</td><td>true</td></tr>
</table>

-----------------------------------------------




MapContains example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.MapContains](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/MapContains.html).

#### Map contains predicate

As Java:


```java
final MapContainsPredicate function = new MapContainsPredicate(new Regex("a.*"));
```

As JSON:


```json
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

Input type:

```
java.util.Map
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{a1=1, a2=2, b=2, c=3}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{b=2, c=3}</td><td>false</td></tr>
<tr><td>java.util.HashMap</td><td>{a=null, b=2, c=3}</td><td>true</td></tr>
</table>

-----------------------------------------------




MultiRegex example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.MultiRegex](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/MultiRegex.html).

#### Multi regex with pattern

As Java:


```java
final MultiRegex function = new MultiRegex(new Pattern[]{Pattern.compile("[a-d]"), Pattern.compile("[0-4]")});
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.MultiRegex",
  "value" : [ {
    "java.util.regex.Pattern" : "[a-d]"
  }, {
    "java.util.regex.Pattern" : "[0-4]"
  } ]
}
```

Input type:

```
java.lang.String
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>z</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>az</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>a</td><td>java.lang.ClassCastException: java.lang.Character cannot be cast to java.lang.String</td></tr>
<tr><td>java.lang.String</td><td>2</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>java.lang.ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------




Not example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.Not](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html).

#### Does not exist

As Java:


```java
final Not function = new Not<>(new Exists());
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Not",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
  }
}
```

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td></td><td>null</td><td>true</td></tr>
<tr><td>java.lang.String</td><td></td><td>false</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Are not equal

As Java:


```java
final Not function = new Not<>(new AreEqual());
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Not",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.AreEqual"
  }
}
```

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Double]</td><td>[1, 1.0]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td><td>true</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[2.5, 2.5]</td><td>false</td></tr>
<tr><td>[java.lang.String, ]</td><td>[, null]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[abc, abc]</td><td>false</td></tr>
</table>

-----------------------------------------------




Or example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.Or](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Or.html).

#### Is less than 2 equal to 5 or is more than 10

When using an Or predicate with a single selected value you can just use the constructor new Or(predicates))'

As Java:


```java
final Or function = new Or<>(
        new IsLessThan(2),
        new IsEqual(5),
        new IsMoreThan(10)
);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
    "orEqualTo" : false,
    "value" : 2
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
    "value" : 5
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  } ]
}
```

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>15</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Is less than 2 equal to 5 or is more than 10

When using an Or predicate with a single selected value you can just use the constructor new Or(predicates))'

As Java:


```java
final Or function = new Or<>(
        new IsLessThan(2),
        new IsEqual(5),
        new IsMoreThan(10)
);
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
    "orEqualTo" : false,
    "value" : 2
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsEqual",
    "value" : 5
  }, {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : 10
  } ]
}
```

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>15</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>3</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
</table>

-----------------------------------------------

#### First item is less than 2 or second item is more than 10

When using an Or predicate with multiple selected values, you need to use the Or.Builder to build your Or predicate, using .select() then .execute(). When selecting values in the Or.Builder you need to refer to the position in the input array. I.e to use the first value use position 0 - select(0).You can select multiple values to give to a predicate like isXLessThanY, this is achieved by passing 2 positions to the select method - select(0, 1)

As Java:


```java
final Or function = new Or.Builder()
        .select(0)
        .execute(new IsLessThan(2))
        .select(1)
        .execute(new IsMoreThan(10))
        .build();
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Or",
  "predicates" : [ {
    "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsLessThan",
      "orEqualTo" : false,
      "value" : 2
    },
    "selection" : [ 0 ]
  }, {
    "class" : "uk.gov.gchq.koryphe.tuple.predicate.IntegerTupleAdaptedPredicate",
    "predicate" : {
      "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
      "orEqualTo" : false,
      "value" : 10
    },
    "selection" : [ 1 ]
  } ]
}
```

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 15]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 1]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[15, 15]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[15, 1]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 15]</td><td>false</td></tr>
<tr><td>[java.lang.Integer]</td><td>[1]</td><td>true</td></tr>
</table>

-----------------------------------------------




PredicateMap example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.predicate.PredicateMap](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/predicate/PredicateMap.html).

#### Freq map is more than 2

As Java:


```java
final PredicateMap function = new PredicateMap("key1", new IsMoreThan(2L));
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : {
      "java.lang.Long" : 2
    }
  },
  "key" : "key1"
}
```

Input type:

```
java.util.Map
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=1}</td><td>false</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=2}</td><td>false</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3, key2=0}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key2=3}</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Freq map is more than or equal to 2

As Java:


```java
final PredicateMap function = new PredicateMap("key1", new IsMoreThan(2L, true));
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : true,
    "value" : {
      "java.lang.Long" : 2
    }
  },
  "key" : "key1"
}
```

Input type:

```
java.util.Map
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=1}</td><td>false</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=2}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3, key2=0}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key2=3}</td><td>false</td></tr>
</table>

-----------------------------------------------

#### Map with date key has a value that exists

As Java:


```java
final PredicateMap function = new PredicateMap(new Date(0L), new Exists());
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
  },
  "key" : {
    "java.util.Date" : 0
  }
}
```

Input type:

```
java.util.Map
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{Thu Jan 01 01:00:00 GMT 1970=1}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{Wed Sep 20 14:51:41 BST 2017=2}</td><td>false</td></tr>
</table>

-----------------------------------------------




Regex example
-----------------------------------------------
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.Regex](http://gchq.github.io/koryphe/uk/gov/gchq/koryphe/impl/predicate/Regex.html).

#### Regex with pattern

As Java:


```java
final Regex function = new Regex("[a-d0-4]");
```

As JSON:


```json
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Regex",
  "value" : {
    "java.util.regex.Pattern" : "[a-d0-4]"
  }
}
```

Input type:

```
java.lang.String
```

Example inputs:
<table>
<tr><th>Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>z</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>az</td><td>false</td></tr>
<tr><td>java.lang.Character</td><td>a</td><td>java.lang.ClassCastException: java.lang.Character cannot be cast to java.lang.String</td></tr>
<tr><td>java.lang.String</td><td>2</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>2</td><td>java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String</td></tr>
<tr><td>java.lang.Long</td><td>2</td><td>java.lang.ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
</table>

-----------------------------------------------




