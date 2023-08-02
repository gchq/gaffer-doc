# Data

The [data module](https://github.com/gchq/Gaffer/tree/master/core/data) contains Gaffer's data objects: `Element`, `Edge` and `Entity`.

It also contains the logic for processing these `Element`s - `ElementAggregator`, `ElementFilter` and `ElementTransformer`.

## Functions and Predicates

Gaffer makes use of Java 8's Function and Predicate interfaces to aggregate, transform and filter data. To allow these Function and Predicate classes to process tuples we make use of the [Koryphe](https://github.com/gchq/koryphe/tree/master) library. Koryphe allows us to wrap the Gaffer Elements in a tuple and pass it any Function or Predicate. 

You can use any of our implementations ([see reference pages](../../reference/intro.md)) or write your own.

All the following classes will act on one or more Element identifiers (vertex/source/destination/directed) or properties. If you implement the Java 8 interfaces directly, you would need to add the following `JsonType` annotation to your class:

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
```

Instead, we recommend you implement the Koryphe interfaces instead, which will add this annotation in for you.

### Aggregation
Aggregation is done using a `KorypheBinaryOperator<T>` (or just `BinaryOperator<T>`), where T is the type of the property you are aggregating. For example: [Max](https://github.com/gchq/koryphe/blob/master/core/src/main/java/uk/gov/gchq/koryphe/impl/binaryoperator/Max.java).

### Transforms
Transforms are applied using `KorypheFunction<I, O>` (or just `Function<I, O>`), where I is type of the input property and O is the type of the output value. If you want to transform multiple properties into a single new property then you can implement `KorypheFunction2<T, U, R>` or `KoryphePredicate3<T, U, V, R>`, etc. where R is the output value type.  For example: [Concat](https://github.com/gchq/koryphe/blob/master/core/src/main/java/uk/gov/gchq/koryphe/impl/function/Concat.java).

### Filtering
Filtering is applied using `KoryphePredicate<T>` (or just `Predicate<T>`), where T is type of the property you want to filter on. If you want to filter on multiple properties then you can implement `KoryphePredicate2<T, U>` or `KoryphePredicate3<T, U, V>` etc. For example: [Exists](https://github.com/gchq/koryphe/blob/master/core/src/main/java/uk/gov/gchq/koryphe/impl/predicate/Exists.java).
