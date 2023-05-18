# Properties Guide

Gaffer allows properties to be stored on Entities and Edges. As well as simple properties, such as a String or Integer, Gaffer allows rich properties such as sketches and sets of timestamps to be stored on Elements. Gaffer's ability to continuously aggregate properties on elements allows interesting, dynamic data structures to be stored within the graph. Examples include storing a [`HyperLogLog`](advanced.md) sketch on an Entity to give a very quick estimate of the degree of a node or storing a uniform random sample of the timestamps that an edge was seen active.

Gaffer allows any Java object to be used as a property. If the property is not natively supported by Gaffer, then you will need to provide a serialiser, and possibly an aggregator.

The properties that Gaffer natively supports can be divided into three categories:

- [Standard basic Java properties](basic.md)
- [Type properties](type.md) - A special variant of String
- [Advanced properties](advanced.md) - These are sketches from the clearspring and datasketches libraries
- [Sets and Maps](map-set.md) - Including specialised sets for timestamps

This documentation provides reference information detailing the predicates, aggregators (binary operators) and serialisers supported for Gaffer properties. It also provides some examples of how to use the more advanced types of property.
