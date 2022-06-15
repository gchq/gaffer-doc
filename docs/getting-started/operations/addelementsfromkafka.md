# AddElementsFromKafka
See javadoc - [uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromKafka](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/add/AddElementsFromKafka.html)

Available since Gaffer version 1.0.0

This is not a core operation. To enable it to be handled by Apache Flink, see [flink-library/README.md](https://github.com/gchq/Gaffer/blob/master/library/flink-library/README.md)

## Required fields
The following fields are required: 
- topic
- groupId
- bootstrapServers
- elementGenerator


## Examples

### Add elements from kafka


{% codetabs name="Java", type="java" -%}
final AddElementsFromKafka op = new AddElementsFromKafka.Builder()
        .bootstrapServers("hostname1:8080,hostname2:8080")
        .groupId("groupId1")
        .topic("topic1")
        .generator(ElementGenerator.class)
        .parallelism(1)
        .validate(true)
        .skipInvalidElements(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "AddElementsFromKafka",
  "topic" : "topic1",
  "groupId" : "groupId1",
  "bootstrapServers" : [ "hostname1:8080,hostname2:8080" ],
  "elementGenerator" : "ElementGenerator",
  "parallelism" : 1
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.add.AddElementsFromKafka",
  "topic" : "topic1",
  "groupId" : "groupId1",
  "bootstrapServers" : [ "hostname1:8080,hostname2:8080" ],
  "elementGenerator" : "uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator",
  "parallelism" : 1
}

{%- language name="Python", type="py" -%}
g.AddElementsFromKafka( 
  topic="topic1", 
  group_id="groupId1", 
  bootstrap_servers=[ 
    "hostname1:8080,hostname2:8080" 
  ], 
  element_generator="uk.gov.gchq.gaffer.doc.operation.generator.ElementGenerator", 
  parallelism=1 
)

{%- endcodetabs %}

-----------------------------------------------

