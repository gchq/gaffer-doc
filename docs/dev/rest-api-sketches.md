# Using Sketches with the REST API

This page explains some nuances and special steps required when using classes from the Sketches library with the REST API. If you just want to know how to use the sketches libraries to
use cardinality, see the [cardinality](../getting-started/guide/cardinality.md) docs page.

## Sketches Library

To learn more about the Sketches library see [advanced properties](../../reference/properties-guide/advanced.md) reference page. 
The sketches library is included by default with the Map and Accumulo stores. This is because the `sketches-library` is a dependency in each of
the respective store modules' poms. As well as this, the serialisation is handled by the fact the 
[SketchesJsonModules](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/serialisation/json/SketchesJsonModules.java) 
is returned by the `getJsonSerialiserModules` method in both the 
[Map](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/store-implementation/map-store/src/main/java/uk/gov/gchq/gaffer/mapstore/MapStoreProperties.java#L127) 
and [Accumulo](https://github.com/gchq/Gaffer/blob/gaffer2-2.0.0/store-implementation/accumulo-store/src/main/java/uk/gov/gchq/gaffer/accumulostore/AccumuloProperties.java#L468) 
property classes. The modules are then loaded by the [JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html) 
and used during the deserialisation of the REST JSON queries.

## HyperLogLog sketches

Gaffer currently supports the [Datasketches HllSketch](https://github.com/apache/datasketches-java/blob/master/src/main/java/org/apache/datasketches/hll/HllSketch.java) and [Clearspring HyperLogLogPlus](https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/stream/cardinality/HyperLogLogPlus.java) algorithms. The Clearspring HyperLogLogPlus has been **deprecated in Gaffer** and we recommend the Datasketches HllSketch to users for the reasons described in the [advanced properties guide](../reference/properties-guide/advanced.md#hyperloglogplus).  

The `HllSketch` and `HyperLogLogPlus` sketches can be used to store an approximation of 
cardinality of an element. The JSON of the query is converted to Java 
objects during deserialisation using the `JSONSerialiser`. During the 
deserialisation, the sketch's JSON representation is converted to a Java 
object using the `ObjectMapper` module which uses the relevant deserialiser (
[HyperLogLogPlusJsonDeserialiser](https://github.com/gchq/Gaffer/blob/develop/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/json/HyperLogLogPlusJsonDeserialiser.java) or [HllSketchJsonDeserialiser](https://github.com/gchq/Gaffer/blob/develop/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/serialisation/json/HllSketchJsonDeserialiser.java)).

## Creating cardinality values over JSON

When adding or updating a cardinality object over the rest api, you specify the vertex values to add to the sketch. 
This is done by either using the `offers` field with `HyperLogLogPlus`, or the `values` field with `HllSketch`. 
The HyperLogLog object is then instantiated and updated with
the values. The object can then be serialised and stored in the datastore. 
The vertex object is serialised using the `toString` representation of the object.
???+ note
    As the algorithms use the `toString` method, any user defined type 
    introduced must override the `toString` method returning meaningful string 
    value representing the object rather than the default class instance 
    identifier. User defined types can be introduced by either adding further 
    [types](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/package-summary.html) 
    to Gaffer or by adding a jar with the extra type(s) to the Gaffer 
    classpath on startup.

Depending on whether you are using `HyperLogLogPlus` or `HllSketch`, either the 
[`HyperLogLogPlusWithOffers`](https://github.com/gchq/Gaffer/blob/develop/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/clearspring/cardinality/serialisation/json/HyperLogLogPlusWithOffers.java) or the 
[`HllSketchWithValues`](https://github.com/gchq/Gaffer/blob/develop/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/serialisation/json/HllSketchWithValues.java) 
respectively is responsible for the JSON deserialisation.
The helper classes wrap the underlying sketch and includes the following annotation on 
the `offers`/`values` field:

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
```

This signals to the Jackson `ObjectMapper` that it needs to look for the 
`class` field in each object and translate to the correct object type.

### Primitive data types over JSON
Primitive types are converted to the correct format by Jackson 
`ObjectMapper` automatically. Here are some examples of the values:

=== "String"
    `"values": ["valueA", "value2",...]`

=== "Long"
    `"values": [1, 2,...]`

=== "Double"
    `"values": [1.1, 2.2,...]`

### Non-primitive data types over JSON
In order to convert non-primitive vertex values (like `TypeSubTypeValue`) to Java objects, the JSON values need to contain the special field **class** 
containing the class name of the object. The `deserialiser` uses this `class` 
field when deserialising using the [JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html) 
`deserialise` method. 

Here are the Gaffer user defined types:

=== "FreqMap"
    ```json
    "values": [
      {
        "class": "uk.gov.gchq.gaffer.types.FreqMap",
        "test": 1
      },
      ...
    ]
    ```

=== "CustomMap"
    ```json
    "values": [
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
    ```json
    "values": [
      {
        "class" : "uk.gov.gchq.gaffer.types.TypeValue",
        "type" : "type",
        "value" : "value"
      },
      ...
    ]
    ```

=== "TypeSubTypeValue"
    ```json
    "values": [
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
    example, the `keySerialiser` in the `CustomMap` type) if not a standard Java Object 
    so that the Jackson `ObjectMapper` knows how to convert the correct values 
    to Java objects.

#### Composing using Java

If you are composing the `HllSketch` with values using Java, before 
converting to JSON and sending via REST, you need ensure that the `values` 
objects are translated to JSON with the correct `class` field added. 
To make sure of this, you could add the `sketches-library` JAR and use the 
[HllSketchWithValues](https://github.com/gchq/Gaffer/blob/develop/library/sketches-library/src/main/java/uk/gov/gchq/gaffer/sketches/datasketches/cardinality/serialisation/json/HllSketchWithValues.java) 
object to construct your query (or the equivalent for HyperLogLogPlus). 
This way you know that all the objects have the 
correct field added. You can then convert the `HllSketchWithValues` to 
JSON using the 
[JSONSerialiser](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/jsonserialisation/JSONSerialiser.html)
`serialisation` method:
```java
final HllSketchWithValues hllSketchWithValues = JSONSerialiser.deserialise(treeNode.toString(), HllSketchWithValues.class);
```
If you want to create your own class instead, rather than using 
`HllSketchWithValues`, ensure 
that the `values` list has the correct annotation so the `class` is added on 
conversion using by the Jackson `ObjectMapper`:

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
private List<Object> values = new ArrayList<>();
```

#### Composing using Python

An example of using Python to add a `HyperLogLogPlus` property with a `TypeSubTypeValue` offer:
```python
g.AddElements(
            input=[
                g.Entity(
                    vertex="A",
                    group="cardinality",
                    properties={
                        "hllp": g.hyper_log_log_plus([
                            {
                                "class" : "uk.gov.gchq.gaffer.types.TypeSubTypeValue",
                                "type" : "t",
                                "subType" : "st",
                                "value" : "B"
                            }
                        ])
                    }
                )
            ]
        )
```

An example of using Python to add a `HllSketch` property with a `TypeSubTypeValue` offer:
```python
g.AddElements(
            input=[
                g.Entity(
                    vertex="A",
                    group="cardinality",
                    properties={
                        "hllSketch": g.hll_sketch([
                            {
                                "class" : "uk.gov.gchq.gaffer.types.TypeSubTypeValue",
                                "type" : "t",
                                "subType" : "st",
                                "value" : "B"
                            }
                        ])
                    }
                )
            ]
        )
```

### Adding user defined vertex types into offers

To add a user defined type you must ensure that:

- the type is on the Gaffer classpath
- the type must override the `toString` method
- the type contains the correct annotations if you are converting from Java to
  JSON before sending via REST

The following user defined type example features the annotation required as 
well as the `@Override` of the `toString` method:

```java
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
