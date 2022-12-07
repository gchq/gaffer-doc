---
hide:
  - toc
---

# Basic Properties

Gaffer supports the storage of some common Java objects as properties on entities and edges. Serialisers for these will automatically be added to your schema when you create a graph using a schema that uses these properties.

The standard basic properties are built-in classes from Java. They do not require their serialisers to be specified in a schema.

- `String`
- `Integer`
- `Long`
- `Float`
- `Double`
- `Byte[]`
- `Boolean`
- `Date`

## Predicate Support

Basic properties all support these predicates:

- `And`
- `Or`
- `Not`
- `If`
- `Exists`
- `IsA`
- `IsIn`
- `IsEqual`
- `PropertiesFilter`

Some properties also support other specialised predicates as shown in the table below. For more information on predicates, [see the predicates guide.](../predicates-guide/predicates.md)

Predicate | `String` | `Integer` | `Long` | `Float` | `Double` | `Byte[]` | `Boolean` | `Date`
--------- | ------ | ------- | ---- | ----- | ------ | ------ | ------- | ----
`InRange` | ✅ | ✅ | ✅ | ✅ | ✅ | | ✅ | ✅
`InTimeRange` | | | ✅ | | | | | 
`InDateRange` | | | | | | | | ✅
`IsFalse` | | | | | | | ✅ | 
`IsTrue` | | | | | | | ✅ | 
`IsLongerThan` | ✅ | | | | | ✅ | | 
`IsShorterThan` | ✅ | | | | | ✅ | | 
`MultiRegex` | ✅ | | | | | | | 
`Regex` | ✅ | | | | | | | 
`StringContains` | ✅ | | | | | | | 
`AgeOff` | | | ✅ | | | | | 


## Aggregator Support

Aggregators are also called binary operators. The `First` and `Last` binary operators are supported by all basic properties.

Other operators are supported as shown in the table below. For more information on aggregators, [see the binary operators guide.](../binary-operators-guide/binary-operators.md)

Aggregator | `String` | `Integer` | `Long` | `Float` | `Double` | `Byte[]` | `Boolean` | `Date`
---------- | ------ | ------- | ---- | ----- | ------ | ------ | ------- | ----
`Max` | ✅ | ✅ | ✅ | ✅ | ✅ | | ✅ | ✅
`Min` | ✅ | ✅ | ✅ | ✅ | ✅ | | ✅ | ✅
`StringConcat` | ✅ | | | | | | | 
`StringDeduplicateConcat` | ✅ | | | | | | | 
`Product` | | ✅ | ✅ | ✅ | ✅ | | | 
`Sum` | | ✅ | ✅ | ✅ | ✅ | | | 
`And` | | | | | | | ✅ | 
`Or` | | | | | | | ✅ | 

## Serialiser Support

All properties support the [`NullSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/NullSerialiser.html).

The type of serialiser used for basic properties is automatically selected by Gaffer, based on the type of Gaffer store used. Specifying the exact serialiser to use is therefore optional.

For `String` there is a [`StringSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/StringSerialiser.html) and a [`StringToStringSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/tostring/StringToStringSerialiser.html).

The `Integer` and `Long` properties both have 'Ordered' and 'CompactRaw' serialisers (e.g. [`OrderedIntegerSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/ordered/OrderedIntegerSerialiser.html) & [`CompactRawIntegerSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/raw/CompactRawIntegerSerialiser.html)). The `Float` and `Double` properties only have a single 'Ordered' serialiser each.

The `Byte[]` and `Boolean` properties each have a specialised serialiser.
