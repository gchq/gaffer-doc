# CreateObject
See javadoc - [uk.gov.gchq.koryphe.impl.function.CreateObject](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/function/CreateObject.html)

Available since Koryphe version 1.7.0

Creates a new object of a given type

## Examples

### String example


{% codetabs name="Java", type="java" -%}
final CreateObject createObject = new CreateObject(String.class);

{%- language name="JSON", type="json" -%}
{
  "class" : "CreateObject",
  "objectClass" : "String"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CreateObject",
  "objectClass" : "java.lang.String"
}

{%- language name="Python", type="py" -%}
g.CreateObject( 
  object_class="java.lang.String" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>a normal string</td><td>java.lang.String</td><td>a normal string</td></tr>
<tr><td>byte[]</td><td>some bytes</td><td>java.lang.String</td><td>some bytes</td></tr>
<tr><td></td><td>null</td><td>java.lang.String</td><td></td></tr>
<tr><td>java.lang.Integer</td><td>123</td><td></td><td>RuntimeException: Unable to create a new instance of java.lang.String. No constructors were found that accept a java.lang.Integer</td></tr>
<tr><td>[C</td><td>[C@434af2d5</td><td>java.lang.String</td><td>a char array</td></tr>
</table>

-----------------------------------------------

### List example


{% codetabs name="Java", type="java" -%}
final CreateObject createObject = new CreateObject(ArrayList.class);

{%- language name="JSON", type="json" -%}
{
  "class" : "CreateObject",
  "objectClass" : "ArrayList"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.function.CreateObject",
  "objectClass" : "java.util.ArrayList"
}

{%- language name="Python", type="py" -%}
g.CreateObject( 
  object_class="java.util.ArrayList" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Object
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result Type</th><th>Result</th></tr>
<tr><td>java.util.ArrayList</td><td>[list, example]</td><td>java.util.ArrayList</td><td>[list, example]</td></tr>
<tr><td>java.util.HashSet</td><td>[set, example]</td><td>java.util.ArrayList</td><td>[set, example]</td></tr>
<tr><td>java.util.HashMap</td><td>{}</td><td></td><td>RuntimeException: Unable to create a new instance of java.util.ArrayList. No constructors were found that accept a java.util.HashMap</td></tr>
<tr><td></td><td>null</td><td>java.util.ArrayList</td><td>[]</td></tr>
</table>

-----------------------------------------------

