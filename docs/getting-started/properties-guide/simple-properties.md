## Simple properties

Gaffer supports the storage of some common Java objects as properties on entities and edges. These include Integer, Long, Double, Float, Boolean, Date, String, byte[] and TreeSet<String>. Serialisers for these will automatically be added to your schema when you create a graph using a schema that uses these properties. Aggregators for these properties are provided by the [Koryphe](https://github.com/gchq/koryphe) library and include all the standard functions such as minimum, maximum, sum, etc.

Gaffer also provides a `FreqMap` property. This is a map from string to long.

The [Getting started](https://gchq.github.io/gaffer-doc/summaries/getting-started.html) documentation includes examples of how to use these properties.

