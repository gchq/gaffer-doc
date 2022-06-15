# Not
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.Not](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/Not.html)

Available since Koryphe version 1.0.0

Returns the inverse of a predicate

## Examples

### Does not exist


{% codetabs name="Java", type="java" -%}
final Not function = new Not<>(new Exists());

{%- language name="JSON", type="json" -%}
{
  "class" : "Not",
  "predicate" : {
    "class" : "Exists"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Not",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
  }
}

{%- language name="Python", type="py" -%}
g.Not( 
  predicate=g.Exists() 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td></td><td>null</td><td>true</td></tr>
<tr><td>java.lang.String</td><td></td><td>false</td></tr>
<tr><td>java.lang.String</td><td>abc</td><td>false</td></tr>
</table>

-----------------------------------------------

### Are not equal


{% codetabs name="Java", type="java" -%}
final Not function = new Not<>(new AreEqual());

{%- language name="JSON", type="json" -%}
{
  "class" : "Not",
  "predicate" : {
    "class" : "AreEqual"
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.Not",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.AreEqual"
  }
}

{%- language name="Python", type="py" -%}
g.Not( 
  predicate=g.AreEqual() 
)

{%- endcodetabs %}

Input type:

```
uk.gov.gchq.koryphe.signature.Signature$UnknownGenericType
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Double]</td><td>[1, 1.0]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td><td>true</td></tr>
<tr><td>[java.lang.Double, java.lang.Double]</td><td>[2.5, 2.5]</td><td>false</td></tr>
<tr><td>[java.lang.String, ]</td><td>[, null]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[abc, abc]</td><td>false</td></tr>
</table>

-----------------------------------------------

