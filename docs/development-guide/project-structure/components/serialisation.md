# Serialisation

The [serialisation module](https://github.com/gchq/Gaffer/tree/master/core/serialisation) contains the logic for converting an Object into serialised objects (normally a byte array).

The main interface is Serialisation. We have provided a small set of serialisers for commonly used objects. The serialisers we have been designed to optimise speed and size. Serialisers can take arguments which may be mandatory depending on the serialiser used.

It is important to choose your serialisers wisely as once your data is persisted using a chosen serialiser, there is no easy way of migrating your data into a different format.
