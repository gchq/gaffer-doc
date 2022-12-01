# Predicates Guide

Predicates are filter functions. Most come from the Koryphe library and some are part of Gaffer itself.

## Table of all Gaffer Predicates

!!! note

    Predicates below which are missing a link have not been documented on this site yet.

Predicate | Origin
------------- | -------------
[`impl.predicate.AgeOff`](koryphe-predicates.md#ageoff) | Koryphe
`impl.predicate.AgeOffFromDays` | Koryphe
[`impl.predicate.And`](koryphe-predicates.md#and) | Koryphe
[`impl.predicate.AreEqual`](koryphe-predicates#areequal) | Koryphe
[`impl.predicate.AreIn`](koryphe-predicates.md#arein) | Koryphe
[`impl.predicate.CollectionContains`](koryphe-predicates.md#collectioncontains) | Koryphe
[`impl.predicate.Exists`](koryphe-predicates.md#exists) | Koryphe
[`impl.predicate.If`](koryphe-predicates.md#if) | Koryphe
[`impl.predicate.Or`](koryphe-predicates.md#or) | Koryphe
[`impl.predicate.Not`](koryphe-predicates.md#not) | Koryphe
[`impl.predicate.range.InDateRange`](koryphe-predicates.md#indaterange) | Koryphe
[`impl.predicate.range.InDateRangeDual`](koryphe-predicates.md#indaterangedual) | Koryphe
[`impl.predicate.range.InRange`](koryphe-predicates.md#inrange) | Koryphe
[`impl.predicate.range.InRangeDual`](koryphe-predicates.md#inrangedual) | Koryphe
[`impl.predicate.range.InTimeRange`](koryphe-predicates.md#intimerange) | Koryphe
[`impl.predicate.range.InTimeRangeDual`](koryphe-predicates.md#intimerangedual) | Koryphe
[`impl.predicate.IsA`](koryphe-predicates.md#isa) | Koryphe
[`impl.predicate.IsEqual`](koryphe-predicates.md#isequal) | Koryphe
[`impl.predicate.IsFalse`](koryphe-predicates.md#isfalse) | Koryphe
[`impl.predicate.IsTrue`](koryphe-predicates.md#istrue) | Koryphe
[`impl.predicate.IsIn`](koryphe-predicates.md#isin) | Koryphe
[`impl.predicate.IsLessThan`](koryphe-predicates.md#islessthan) | Koryphe
[`impl.predicate.IsMoreThan`](koryphe-predicates.md#ismorethan) | Koryphe
[`impl.predicate.IsLongerThan`](koryphe-predicates.md#islongerthan) | Koryphe
[`impl.predicate.IsShorterThan`](koryphe-predicates.md#isshorterthan) | Koryphe
[`impl.predicate.IsXLessThanY`](koryphe-predicates.md#isxlessthany) | Koryphe
[`impl.predicate.IsXMoreThanY`](koryphe-predicates.md#isxmorethany) | Koryphe
[`impl.predicate.MapContains`](koryphe-predicates.md#mapcontains) | Koryphe
[`impl.predicate.MapContainsPredicate`](koryphe-predicates.md#mapcontainspredicate) | Koryphe
[`predicate.PredicateMap`](koryphe-predicates#predicatemap) | Koryphe
`predicate.AdaptedPredicate` | Koryphe
`predicate.PredicateComposite` | Koryphe
[`impl.predicate.StringContains`](koryphe-predicates.md#stringcontains) | Koryphe
[`impl.predicate.Regex`](koryphe-predicates.md#regex) | Koryphe
[`impl.predicate.MultiRegex`](koryphe-predicates.md#multiregex) | Koryphe
`tuple.predicate.IntegerTupleAdaptedPredicate` | Koryphe
`tuple.predicate.TupleAdaptedPredicate` | Koryphe
`tuple.predicate.TupleAdaptedPredicateComposite` | Koryphe
`access.predicate.user.DefaultUserPredicate` | Gaffer
`access.predicate.user.NoAccessUserPredicate` | Gaffer
`access.predicate.user.UnrestrictedAccessUserPredicate` | Gaffer
`data.element.comparison.ElementJoinComparator` | Gaffer
`data.element.function.ElementFilter` | Gaffer
`data.element.function.PropertiesFilter` | Gaffer
`data.elementdefinition.view.access.predicate.user.NamedViewWriteUserPredicate` | Gaffer
`federatedstore.access.predicate.user.FederatedGraphReadUserPredicate` | Gaffer
`federatedstore.access.predicate.user.FederatedGraphWriteUserPredicate` | Gaffer
`graph.hook.migrate.predicate.TransformAndFilter` | Gaffer
`rest.example.ExampleFilterFunction` | Gaffer
[`sketches.clearspring.cardinality.predicate.HyperLogLogPlusIsLessThan`](gaffer-predicates.md#hyperloglogplusislessthan) | Gaffer
`sketches.datasketches.cardinality.predicate.HllSketchIsLessThan` | Gaffer
`store.util.AggregatorUtil$IsElementAggregated` | Gaffer
`time.predicate.RBMBackedTimestampSetInRange` | Gaffer