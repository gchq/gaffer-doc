# AddElementsFromSocket
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromSocket](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromSocket.html)

Available since Gaffer version 1.0.0

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

## Required fields
The following fields are required: 
- hostname
- port
- elementGenerator


## Examples

### Add elements from socket


{% codetabs name="Java", type="java" -%}
final AddElementsFromSocket op = new AddElementsFromSocket.Builder()
        .hostname("localhost")
        .port(8080)
        .delimiter(",")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddElementsFromSocket",
  "hostname" : "localhost",
  "port" : 8080,
  "elementGenerator" : "ElementGenerator",
  "delimiter" : ",",
  "parallelism" : 1,
  "skipInvalidElements" : false,
  "validate" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromSocket",
  "hostname" : "localhost",
  "port" : 8080,
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "delimiter" : ",",
  "parallelism" : 1,
  "skipInvalidElements" : false,
  "validate" : true
}

{%- language name="Python", type="py" -%}
g.AddElementsFromSocket( 
  hostname="localhost", 
  port=8080, 
  element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
  parallelism=1, 
  validate=True, 
  skip_invalid_elements=False, 
  delimiter="," 
)

{%- endcodetabs %}

-----------------------------------------------

