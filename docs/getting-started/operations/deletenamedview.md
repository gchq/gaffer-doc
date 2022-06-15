# DeleteNamedView
See javadoc - [uk.gov.gchq.gaffer.named.view.DeleteNamedView](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/named/view/DeleteNamedView.html)

Available since Gaffer version 1.3.0

See [NamedViews](../developer-guide/namedviews.md) for information on configuring NamedViews for your Gaffer graph, along with working examples.

## Required fields
The following fields are required: 
- name


## Examples

### Delete named view


{% codetabs name="Java", type="java" -%}
final DeleteNamedView op = new DeleteNamedView.Builder()
        .name("testNamedView")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "DeleteNamedView",
  "name" : "testNamedView"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.view.DeleteNamedView",
  "name" : "testNamedView"
}

{%- language name="Python", type="py" -%}
g.DeleteNamedView( 
  name="testNamedView" 
)

{%- endcodetabs %}

-----------------------------------------------

