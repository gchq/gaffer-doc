# Type Properties

The `TypeValue` ([Javadoc](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/TypeValue.html)) and `TypeSubTypeValue` ([Javadoc]()) are special properties which are similar to `String`, but also store a secondary string ('type') or secondary and tertiary strings ('type' & 'subtype').

## Predicate Support

Both type properties support these predicates:

- `And`
- `Or`
- `Not`
- `If`
- `Exists`
- `IsA`
- `IsIn`
- `IsEqual`
- `PropertiesFilter`
- `InRange`

## Aggregator Support

The `First`, `Last`, `Min` and `Max` binary operators are supported by both type properties.

## Serialiser Support

Both type properties support the [`NullSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/implementation/NullSerialiser.html) and have their own specialised serialisers, [`TypeValueSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/TypeValueSerialiser.html) and [`TypeSubTypeValueSerialiser`](http://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/serialisation/TypeSubTypeValueSerialiser.html).
