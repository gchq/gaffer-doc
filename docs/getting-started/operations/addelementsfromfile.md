# AddElementsFromFile
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromFile.html)

Available since Gaffer version 1.0.0

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

## Required fields
The following fields are required: 
- filename
- elementGenerator


## Examples

### Add elements from file


{% codetabs name="Java", type="java" -%}
final AddElementsFromFile op = new AddElementsFromFile.Builder()
        .filename("filename")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddElementsFromFile",
  "filename" : "filename",
  "elementGenerator" : "ElementGenerator",
  "parallelism" : 1,
  "skipInvalidElements" : false,
  "validate" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromFile",
  "filename" : "filename",
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "parallelism" : 1,
  "skipInvalidElements" : false,
  "validate" : true
}

{%- language name="Python", type="py" -%}
g.AddElementsFromFile( 
  filename="filename", 
  element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
  parallelism=1, 
  validate=True, 
  skip_invalid_elements=False 
)

{%- endcodetabs %}

-----------------------------------------------

