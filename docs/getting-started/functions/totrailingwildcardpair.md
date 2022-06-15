# ToTrailingWildcardPair
See javadoc - [uk.gov.gchq.gaffer.operation.function.ToTrailingWildcardPair](ref://../../javadoc/gaffer/uk/gov/gchq/gaffer/operation/function/ToTrailingWildcardPair.html)

Available since Gaffer version 1.19.0

Converts an input value into a pair of EntityIds representing a range.

## Examples

### With default end of range


{% codetabs name="Java", type="java" -%}
final ToTrailingWildcardPair function = new ToTrailingWildcardPair();

{%- language name="JSON", type="json" -%}
{
  "class" : "ToTrailingWildcardPair",
  "endOfRange" : "~"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.gaffer.operation.function.ToTrailingWildcardPair",
  "endOfRange" : "~"
}
{%- endcodetabs %}

Input type:

```
java.lang.String
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>value1</td><td>uk.gov.gchq.gaffer.commonutil.pair.Pair</td><td>Pair[first=EntitySeed[vertex=value1],second=EntitySeed[vertex=value1~]]</td></tr>
<tr><td>java.lang.String</td><td>value2</td><td>uk.gov.gchq.gaffer.commonutil.pair.Pair</td><td>Pair[first=EntitySeed[vertex=value2],second=EntitySeed[vertex=value2~]]</td></tr>
<tr><td></td><td>null</td><td></td><td>null</td></tr>
</table>

-----------------------------------------------

