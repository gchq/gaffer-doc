# Binary Operators Guide

Binary Operators are aggregation functions. Basic operators come from the Koryphe library, the rest are part of Gaffer itself.

## Table of all Gaffer Binary Operators

!!! note

    Binary Operators below which are missing a link have not been documented on this site yet.

    Some examples of Binary Operators in use can be found in the [properties guide](../properties-guide/properties.md).

Binary Operator | Origin
------------- | -------------
`binaryoperator.AdaptedBinaryOperator` | Koryphe
`binaryoperator.BinaryOperatorComposite` | Koryphe
`binaryoperator.BinaryOperatorMap` | Koryphe
[`impl.binaryoperator.And`](koryphe-operators.md#and) | Koryphe
[`impl.binaryoperator.CollectionConcat`](koryphe-operators.md#collectionconcat) | Koryphe
[`impl.binaryoperator.CollectionIntersect`](koryphe-operators.md#collectionintersect) | Koryphe
[`impl.binaryoperator.First`](koryphe-operators.md#first) | Koryphe
`impl.binaryoperator.Last` | Koryphe
[`impl.binaryoperator.Max`](koryphe-operators.md#max) | Koryphe
[`impl.binaryoperator.Min`](koryphe-operators.md#min) | Koryphe
[`impl.binaryoperator.Or`](koryphe-operators.md#or) | Koryphe
[`impl.binaryoperator.Product`](koryphe-operators.md#product) | Koryphe
[`impl.binaryoperator.StringConcat`](koryphe-operators.md#stringconcat) | Koryphe
[`impl.binaryoperator.StringDeduplicateConcat`](koryphe-operators.md#stringdeduplicateconcat) | Koryphe
[`impl.binaryoperator.Sum`](koryphe-operators.md#sum) | Koryphe
`tuple.binaryoperator.TupleAdaptedBinaryOperator` | Koryphe
`tuple.binaryoperator.TupleAdaptedBinaryOperatorComposite` | Koryphe
`data.element.function.ElementAggregator` | Gaffer
`types.function.FreqMapAggregator` | Gaffer
`bitmap.function.aggregate.RoaringBitmapAggregator` | Gaffer
`types.binaryoperator.CustomMapAggregator` | Gaffer
`time.binaryoperator.BoundedTimestampSetAggregator` | Gaffer
`time.binaryoperator.LongTimeSeriesAggregator` | Gaffer
`time.binaryoperator.RBMBackedTimestampSetAggregator` | Gaffer
`store.util.AggregatorUtil$IngestElementBinaryOperator` | Gaffer
`store.util.AggregatorUtil$IngestPropertiesBinaryOperator` | Gaffer
`store.util.AggregatorUtil$QueryElementBinaryOperator` | Gaffer
`store.util.AggregatorUtil$QueryPropertiesBinaryOperator` | Gaffer
`sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator` | Gaffer
`sketches.datasketches.cardinality.binaryoperator.HllSketchAggregator` | Gaffer
`sketches.datasketches.cardinality.binaryoperator.HllUnionAggregator` | Gaffer
`sketches.datasketches.frequencies.binaryoperator.LongsSketchAggregator` | Gaffer
`sketches.datasketches.frequencies.binaryoperator.StringsSketchAggregator` | Gaffer
`sketches.datasketches.quantiles.binaryoperator.DoublesSketchAggregator` | Gaffer
`sketches.datasketches.quantiles.binaryoperator.DoublesUnionAggregator` | Gaffer
`sketches.datasketches.quantiles.binaryoperator.KllFloatsSketchAggregator` | Gaffer
`sketches.datasketches.quantiles.binaryoperator.StringsSketchAggregator` | Gaffer
`sketches.datasketches.quantiles.binaryoperator.StringsUnionAggregator` | Gaffer
`sketches.datasketches.sampling.binaryoperator.ReservoirItemsSketchAggregator` | Gaffer
`sketches.datasketches.sampling.binaryoperator.ReservoirItemsUnionAggregator` | Gaffer
`sketches.datasketches.sampling.binaryoperator.ReservoirLongsSketchAggregator` | Gaffer
`sketches.datasketches.sampling.binaryoperator.ReservoirLongsUnionAggregator` | Gaffer
`sketches.datasketches.theta.binaryoperator.SketchAggregator` | Gaffer
`sketches.datasketches.theta.binaryoperator.UnionAggregator` | Gaffer