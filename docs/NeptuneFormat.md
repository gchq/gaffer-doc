# Neptune Data Format

AWS Neptune adheres to the openCypher standard. OpenCypher represents “the most widely adopted, fully-specified, and open query
language for property graph databases”. Providing this data format as a standard within Gaffer allows for the import and export of data
between Gaffer graphs and graphs built on top of Amazon Neptune with greater ease. For a more in depth guide to the openCypher format
supported by Neptune see [here](https://docs.aws.amazon.com/neptune/latest/userguide/bulk-load-tutorial-format-opencypher.html).
#### Entity (Node) System column headers
- :ID  –  (Required) An ID for the node -> VERTEX (Gaffer)
- :LABEL  –  A label for the node -> GROUP (Gaffer)

#### Edge (relationship) System column headers 
- :ID  –  An ID for the relationship. There doesn't exist a direct mapping between "ID" in openCypher and
  a dedicated attribute for an Edge with Gaffer, instead this is added as a property belonging to the Edge.
- :START_ID  –  (Required) The node ID of the node this relationship starts from -> SOURCE (Gaffer)
- :END_ID  –  (Required) The node ID of the node this relationship ends at -> DESTINATION (Gaffer)
- :TYPE  –  A type for the relationship -> GROUP (Gaffer)

#### Property column headers
Gaffer replaces "-" with "_" from column headers whilst other non-valid characters as outlined within the class
[PropertiesUtil](https://github.com/gchq/Gaffer/blob/f16de7c3eccfe7a800cad1d7eea5fbae4cf01d44/core/common-util/src/main/java/uk/gov/gchq/gaffer/commonutil/PropertiesUtil.java#L26) 
are stripped. Both Entities and Edges can have associated properties. It's possible to specify the type of
each property provided by using the proceeding format **propertyname:type**, the default is to
treat each property as a string if a type isn't supplied.
#### Supported Data Types and How Gaffer Handles Each
###### Serialised to Boolean
- Bool or Boolean  –  A Boolean field. Allowed values are true and false. Any value other than true is treated as false.
###### Serialised to Integer
- Byte  –  A whole number in the range -128 through 127. Converted to an integer value.
- Short  –  A whole number in the range -32,768 through 32,767. Converted to an integer value.
- Int  –  A whole number in the range -2^31 through 2^31 - 1.
###### Serialised to Long
- Long  –  A whole number in the range -2^63 through 2^63 - 1.
###### Serialised to Float
- Float  –  A 32-bit IEEE 754 floating point number. Decimal notation and scientific notation are both supported. Infinity, -Infinity, and NaN are all recognized, but INF is not.
  Values with too many digits to fit are rounded to the nearest value (a midway value is rounded to 0 for the last remaining digit at the bit level).
###### Serialised to Double
- Double  –  A 64-bit IEEE 754 floating point number. Decimal notation and scientific notation are both supported. Infinity, -Infinity, and NaN are all recognized, but INF is not.
  Values with too many digits to fit are rounded to the nearest value (a midway value is rounded to 0 for the last remaining digit at the bit level).
###### Serialised to TimeStamp
- DateTime  –  A Java date in one of the following ISO-8601 formats:
    - yyyy-MM-dd
    - yyyy-MM-ddTHH:mm
    - yyyy-MM-ddTHH:mm:ss
    - yyyy-MM-ddTHH:mm:ssZ
###### Serialised to String
- String  –  Quotation marks are optional. Comma, newline, and carriage return characters are automatically escaped if they are included in a string that is surrounded by double quotation marks (").
- Char  –  A Char field. Stored as a string.
- Date, LocalDate, and LocalDateTime  –  The values are loaded verbatim as strings, without validation.
- Duration  –  The values are loaded verbatim as strings, without validation.
- Point  –  A point field, for storing spatial data. See Neo4j Point format. The values are loaded verbatim as strings, without validation.

#### Example of the Neptune load format
```
:ID,  name:String,  age:Int,  lang:String,  :LABEL,  :START_ID,  :END_ID,   :TYPE,  weight:Double
 v1,      "marko",       29,             ,  person,           ,         ,        ,
 v2,        "lop",         ,       "java",software,           ,         ,        ,
 e1,             ,         ,             ,        ,         v1,       v2, created,            0.4
```
