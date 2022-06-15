# GetFromEndpoint
See javadoc - [uk.gov.gchq.gaffer.operation.impl.get.GetFromEndpoint](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/impl/get/GetFromEndpoint.html)

Available since Gaffer version 1.8.0

Gets data from an endpoint

## Required fields
The following fields are required: 
- endpoint


## Examples

### Get from endpoint


{% codetabs name="Java", type="java" -%}
final GetFromEndpoint get = new GetFromEndpoint.Builder()
        .endpoint("https://mydata.io:8443/mydata")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetFromEndpoint",
  "endpoint" : "https://mydata.io:8443/mydata"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.impl.get.GetFromEndpoint",
  "endpoint" : "https://mydata.io:8443/mydata"
}

{%- language name="Python", type="py" -%}
g.GetFromEndpoint( 
  endpoint="https://mydata.io:8443/mydata" 
)

{%- endcodetabs %}

-----------------------------------------------

