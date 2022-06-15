# GetAllNamedViews
See javadoc - [uk.gov.gchq.gaffer.named.view.GetAllNamedViews](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/named/view/GetAllNamedViews.html)

Available since Gaffer version 1.3.0

See [NamedViews](../developer-guide/namedviews.md) for information on configuring NamedViews for your Gaffer graph, along with working examples.

## Required fields
No required fields


## Examples

### Get all named views


{% codetabs name="Java", type="java" -%}
final GetAllNamedViews op = new GetAllNamedViews();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetAllNamedViews"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.named.view.GetAllNamedViews"
}

{%- language name="Python", type="py" -%}
g.GetAllNamedViews()

{%- endcodetabs %}

-----------------------------------------------

