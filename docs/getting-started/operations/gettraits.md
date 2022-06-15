# GetTraits
See javadoc - [uk.gov.gchq.gaffer.store.operation.GetTraits](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/store/operation/GetTraits.html)

Available since Gaffer version 1.5.0

Gets the traits of the current store.

## Required fields
No required fields


## Examples

### Get all traits

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final GetTraits operation = new GetTraits.Builder()
        .currentTraits(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetTraits",
  "currentTraits" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.store.operation.GetTraits",
  "currentTraits" : false
}

{%- language name="Python", type="py" -%}
g.GetTraits( 
  current_traits=False 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
INGEST_AGGREGATION
MATCHED_VERTEX
STORE_VALIDATION
TRANSFORMATION
QUERY_AGGREGATION
ORDERED
VISIBILITY
POST_TRANSFORMATION_FILTERING
POST_AGGREGATION_FILTERING
PRE_AGGREGATION_FILTERING

{%- language name="JSON", type="json" -%}
[ "INGEST_AGGREGATION", "MATCHED_VERTEX", "STORE_VALIDATION", "TRANSFORMATION", "QUERY_AGGREGATION", "ORDERED", "VISIBILITY", "POST_TRANSFORMATION_FILTERING", "POST_AGGREGATION_FILTERING", "PRE_AGGREGATION_FILTERING" ]
{%- endcodetabs %}

-----------------------------------------------

### Get current traits

This will only return traits that are applicable to your current schema. This schema doesn't have a visibility property, so the VISIBILITY trait is not returned.

Using this directed graph:

```

    --> 4 <--
  /     ^     \
 /      |      \
1  -->  2  -->  3
         \
           -->  5
```


{% codetabs name="Java", type="java" -%}
final GetTraits operation = new GetTraits.Builder()
        .currentTraits(true)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "GetTraits",
  "currentTraits" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.store.operation.GetTraits",
  "currentTraits" : true
}

{%- language name="Python", type="py" -%}
g.GetTraits( 
  current_traits=True 
)

{%- endcodetabs %}

Result:

{% codetabs name="Java", type="java" -%}
INGEST_AGGREGATION
MATCHED_VERTEX
STORE_VALIDATION
TRANSFORMATION
QUERY_AGGREGATION
ORDERED
POST_TRANSFORMATION_FILTERING
POST_AGGREGATION_FILTERING
PRE_AGGREGATION_FILTERING

{%- language name="JSON", type="json" -%}
[ "INGEST_AGGREGATION", "MATCHED_VERTEX", "STORE_VALIDATION", "TRANSFORMATION", "QUERY_AGGREGATION", "ORDERED", "POST_TRANSFORMATION_FILTERING", "POST_AGGREGATION_FILTERING", "PRE_AGGREGATION_FILTERING" ]
{%- endcodetabs %}

-----------------------------------------------

