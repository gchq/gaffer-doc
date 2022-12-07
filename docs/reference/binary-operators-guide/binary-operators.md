# Binary Operators Guide

Binary Operators are aggregation functions. Basic operators come from the Koryphe library, the rest are part of Gaffer itself.

## Table of all Gaffer Binary Operators

!!! note

    Binary Operators below which are missing a link have not been documented on this site yet.

    Some examples of Binary Operators in use can be found in the [properties guide](../properties-guide/properties.md).

Binary Operator | Origin
------------- | -------------
`uk.gov.gchq.koryphe.binaryoperator.AdaptedBinaryOperator` | Koryphe
`uk.gov.gchq.koryphe.binaryoperator.BinaryOperatorComposite` | Koryphe
`uk.gov.gchq.koryphe.binaryoperator.BinaryOperatorMap` | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.And`](koryphe-operators.md#and) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat`](koryphe-operators.md#collectionconcat) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.CollectionIntersect`](koryphe-operators.md#collectionintersect) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.First`](koryphe-operators.md#first) | Koryphe
`uk.gov.gchq.koryphe.impl.binaryoperator.Last` | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.Max`](koryphe-operators.md#max) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.Min`](koryphe-operators.md#min) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.Or`](koryphe-operators.md#or) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.Product`](koryphe-operators.md#product) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat`](koryphe-operators.md#stringconcat) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.StringDeduplicateConcat`](koryphe-operators.md#stringdeduplicateconcat) | Koryphe
[`uk.gov.gchq.koryphe.impl.binaryoperator.Sum`](koryphe-operators.md#sum) | Koryphe
`uk.gov.gchq.koryphe.tuple.binaryoperator.TupleAdaptedBinaryOperator` | Koryphe
`uk.gov.gchq.koryphe.tuple.binaryoperator.TupleAdaptedBinaryOperatorComposite` | Koryphe
`uk.gov.gchq.gaffer.data.element.function.ElementAggregator` | Gaffer
`uk.gov.gchq.gaffer.types.function.FreqMapAggregator` | Gaffer
`uk.gov.gchq.gaffer.bitmap.function.aggregate.RoaringBitmapAggregator` | Gaffer
`uk.gov.gchq.gaffer.types.binaryoperator.CustomMapAggregator` | Gaffer
`uk.gov.gchq.gaffer.time.binaryoperator.BoundedTimestampSetAggregator` | Gaffer
`uk.gov.gchq.gaffer.time.binaryoperator.LongTimeSeriesAggregator` | Gaffer
`uk.gov.gchq.gaffer.time.binaryoperator.RBMBackedTimestampSetAggregator` | Gaffer
`uk.gov.gchq.gaffer.store.util.AggregatorUtil$IngestElementBinaryOperator` | Gaffer
`uk.gov.gchq.gaffer.store.util.AggregatorUtil$IngestPropertiesBinaryOperator` | Gaffer
`uk.gov.gchq.gaffer.store.util.AggregatorUtil$QueryElementBinaryOperator` | Gaffer
`uk.gov.gchq.gaffer.store.util.AggregatorUtil$QueryPropertiesBinaryOperator` | Gaffer
`uk.gov.gchq.gaffer.sketches.clearspring.cardinality.binaryoperator.HyperLogLogPlusAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.cardinality.binaryoperator.HllSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.cardinality.binaryoperator.HllUnionAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.frequencies.binaryoperator.LongsSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.frequencies.binaryoperator.StringsSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.DoublesSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.DoublesUnionAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.KllFloatsSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.StringsSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.quantiles.binaryoperator.StringsUnionAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.sampling.binaryoperator.ReservoirItemsSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.sampling.binaryoperator.ReservoirItemsUnionAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.sampling.binaryoperator.ReservoirLongsSketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.sampling.binaryoperator.ReservoirLongsUnionAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.theta.binaryoperator.SketchAggregator` | Gaffer
`uk.gov.gchq.gaffer.sketches.datasketches.theta.binaryoperator.UnionAggregator` | Gaffer