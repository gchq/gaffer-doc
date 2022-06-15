# Base64Decode
See javadoc - [uk.gov.gchq.koryphe.impl.function.Base64Decode](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/Base64Decode.html)

Available since Koryphe version 1.8.0

Decodes a base64 encoded byte array

## Examples

### Decode base 64


{% codetabs name="Java", type="java" -%}
final Base64Decode function = new Base64Decode();

{%- language name="JSON", type="json" -%}
{
  "class" : "Base64Decode"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.Base64Decode"
}

{%- language name="Python", type="py" -%}
g.Base64Decode()

{%- endcodetabs %}

Input type:

```
byte[]
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>byte[]</td><td>dGVzdCBzdHJpbmc=</td><td>byte[]</td><td>test string</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

