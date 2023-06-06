# Using Sketches with the REST API

This page explains some nuances and special steps required when using classes from the Sketches library with the REST API.

## Sketches Library

To learn more about the Sketches library see [advanced properties](../../reference/properties-guide/advanced.md) reference page. 
The sketches library is included with the Map and Accumulo stores. The 
[SketchesJsonModules](https://github.com/gchq/Gaffer/blob/v2-alpha/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/serialisation/json/SketchesJsonModules.java) 
are returned in `String` format by the `getJsonSerialiserModules` method in the 
[Map](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/store-implementation/map-store/src/main/java/uk/gov/gchq/gaffer/mapstore/MapStoreProperties.java) 
and [Accumulo](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/store-implementation/accumulo-store/src/main/java/uk/gov/gchq/gaffer/accumulostore/AccumuloProperties.java) 
property stores. The modules are then loaded by the [JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html) 
and used during the deserialisation of the REST JSON queries.

## HyperLogLogPlus

The `HyperLogLogPlus` sketch can be used to store an approximation of 
cardinality of an element. The `JSON` of the query is converted to `Java` 
`objects` during desialisation using the `JSONSerialiser`. During the 
deserialisation the `HyperLogLogPlus` JSON representation is converted to a 
`HyperLogLogPlus` Java object using the `ObjectMapper` module which uses the 
[HyperLogLogPlusJsonDeserialiser](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/json/HyperLogLogPlusJsonDeserialiser.java). 
In order to convert the `offer` values (which are offered to the 
`HyperLogLogPlus` on instantiation) to `Java` objects, the `JSON` values 
need to contain the special `string` field **class** containing the class name 
of the object. The `deserialiser` uses this `class` field when deserialising 
using the [JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html) 
`deserialise` method. The `HyperLogLogPlus` object is instantiated and *offered* 
the values. The object can then be serialised and stored in the datastore. 
For Gaffer, at present only the [ClearSpring](https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/stream/cardinality/HyperLogLogPlus.java) 
algorithm is used which requires that the object is offered using its `toString` 
representation of the object.
???+ note
    As the algorithm uses the `toString` method, any user defined type 
    introduced must override the `toString` method returning meaningful string 
    value representing the object rather than the default class instance 
    identifier. User defined types can be introduced by either adding further 
    [types](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/package-summary.html) 
    to `Gaffer` or by adding a `JAR` with the extra type(s) to the `Gaffer` 
    classpath on startup.

The `HyperLogLogPlusJsonDesialiser` deserialises from `JSON` to `Java` using the 
[HyperLogLogPlusWithOffers](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/json/HyperLogLogPlusWithOffers.java) 
object. The `HyperLogLogPlusWithOffers` includes the following annotation on 
the `List` field: 

```
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
private List<?> offers = new ArrayList<>();
```

This signals to the `Jackson` `ObjectMapper` that it needs to look for the 
`class` field in each object and translate to the correct object type.

Java types are converted to the correct format by `Jackson` 
`ObjectMapper` automatically. Here are some examples of the values:

=== "String"
    `"offers": ["valueA", "value2",...]`

=== "Integer"
    `"offers": [1, 2,...]`

=== "Float"
    `"offers": [1.1, 2.2,...]`

=== "Double"
    `"offers": [1.1, 2.2,...]`

=== "Long"
    `"offers": [12345678910111121314,...]`

=== "Boolean"
    `"offers": [true,false,...]`

The user defined types require that the `class` field is added to the `JSON` 
object, so it knows how to convert to the correct format on deserialisation. 
Here are the `Gaffer` user defined types:

=== "FreqMap"
    ```
    "offers": [
      {
        "class": "uk.gov.gchq.gaffer.types.FreqMap",
        "test": 1
      },
      ...
    ]
    ```

=== "CustomMap"
    ```
    "offers": [
      {
        "class": "uk.gov.gchq.gaffer.types.CustomMap",
        "keySerialiser": {
          "class": "uk.gov.gchq.gaffer.serialisation.implementation.BooleanSerialiser"
        },
        "valueSerialiser": {
          "class": "uk.gov.gchq.gaffer.serialisation.implementation.BooleanSerialiser"
        },
        "jsonStorage": []
      },
      ...
    ]
    ```

=== "TypeValue"
    ```
    "offers": [
      {
        "class" : "uk.gov.gchq.gaffer.types.TypeValue",
        "type" : "type",
        "value" : "value"
      },
      ...
    ]
    ```

=== "TypeSubTypeValue"
    ```
    "offers": [
      {
        "class" : "uk.gov.gchq.gaffer.types.TypeSubTypeValue",
        "type" : "type",
        "subType" : "subType",
        "value" : "value"
      },
      ...
    ]
    ```

???+ note
    The subclass fields must also have the `class` field set (for 
    example, the `keySerialiser` `CustoMap` type) if not a standard Java Object 
    so that the Jackson `ObjectMapper` knows how to convert the correct values 
    to Java objects.

### Composing using Java

If you are composing the `HyperLogLogPlus` with offers using `Java` before 
converting to `JSON` and sending via `REST`, you need ensure that the `offer` 
objects are translated to `JSON` with the correct `class` field added. 
To make sure of this, you could add the `sketches-library` JAR and use the 
[HyperLogLogPlusWithOffers](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/json/HyperLogLogPlusWithOffers.java) 
object to construct your query. This way you know that all the objects have the 
correct field added. You can then convert the `HyperLogLogPlusWithOffers` to 
JSON using the 
[JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html)
`serialisation` method. If you want to create your own class instead, ensure 
that the `offers` list has the correct annotation so the `class` is added on 
conversion using by the `Jackson` `ObjectMapper`:

```
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
private List<?> offers = new ArrayList<>();
```

### Composing using Python

To be added.

### HyperLogLogPlus Example

For a HyperLogLogPlus example see [this section on the advanced properties](../../reference/properties-guide/advanced.md#hyperloglogplus) reference page.

## Adding user defined types

To add a user defined type you must ensure that:

- the type is on the `Gaffer` classpath
- the type must override the `toString` method
- the type contains the correct annotations if you are converting from `Java` to
  `JSON` before sending via `REST`

The following user defined type example features the annotation required as 
well as the `@Override` of the `toString` method:

```
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
public class ExampleType implements Comparable<ExampleType>, Serializable {
   
   private String value...;
   
   // getters and setters
   
   @Override
   public String toString() {
       return ...;
   }
}
```
