# InRange
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.range.InRange](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InRange.html)

Available since Koryphe version 1.1.0

Checks if a comparable is within a provided range

## Examples

### In long range 5 to 10


{% codetabs name="Java", type="java" -%}
final InRange function = new InRange.Builder<Long>()
        .start(5L)
        .end(10L)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRange",
  "start" : {
    "Long" : 5
  },
  "end" : {
    "Long" : 10
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRange",
  "start" : {
    "java.lang.Long" : 5
  },
  "end" : {
    "java.lang.Long" : 10
  }
}

{%- language name="Python", type="py" -%}
g.InRange( 
  start={'java.lang.Long': 5}, 
  end={'java.lang.Long': 10} 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>-5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>7</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>20</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>7</td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.Integer</td></tr>
<tr><td>java.lang.String</td><td>7</td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In long range 5 to 10 exclusive


{% codetabs name="Java", type="java" -%}
final InRange function = new InRange.Builder<Long>()
        .start(5L)
        .end(10L)
        .startInclusive(false)
        .endInclusive(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRange",
  "start" : {
    "Long" : 5
  },
  "end" : {
    "Long" : 10
  },
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRange",
  "start" : {
    "java.lang.Long" : 5
  },
  "end" : {
    "java.lang.Long" : 10
  },
  "startInclusive" : false,
  "endInclusive" : false
}

{%- language name="Python", type="py" -%}
g.InRange( 
  start={'java.lang.Long': 5}, 
  end={'java.lang.Long': 10}, 
  start_inclusive=False, 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>-5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>7</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>20</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>7</td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.Integer</td></tr>
<tr><td>java.lang.String</td><td>7</td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In long range less than 10

If the start of the range is not specified then the start of the range is unbounded.


{% codetabs name="Java", type="java" -%}
final InRange function = new InRange.Builder<Long>()
        .end(10L)
        .endInclusive(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRange",
  "end" : {
    "Long" : 10
  },
  "endInclusive" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRange",
  "end" : {
    "java.lang.Long" : 10
  },
  "endInclusive" : false
}

{%- language name="Python", type="py" -%}
g.InRange( 
  end={'java.lang.Long': 10}, 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Long</td><td>-5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>1</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>7</td><td>true</td></tr>
<tr><td>java.lang.Long</td><td>10</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>20</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>7</td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.Integer</td></tr>
<tr><td>java.lang.String</td><td>7</td><td>ClassCastException: java.lang.Long cannot be cast to java.lang.String</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In integer range 5 to 10


{% codetabs name="Java", type="java" -%}
final InRange function = new InRange.Builder<Integer>()
        .start(5)
        .end(10)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRange",
  "start" : 5,
  "end" : 10
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRange",
  "start" : 5,
  "end" : 10
}

{%- language name="Python", type="py" -%}
g.InRange( 
  start=5, 
  end=10 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.Integer</td><td>-5</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>5</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>7</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>10</td><td>true</td></tr>
<tr><td>java.lang.Integer</td><td>20</td><td>false</td></tr>
<tr><td>java.lang.Long</td><td>7</td><td>ClassCastException: java.lang.Integer cannot be cast to java.lang.Long</td></tr>
<tr><td>java.lang.String</td><td>7</td><td>ClassCastException: java.lang.Integer cannot be cast to java.lang.String</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

### In string range b to d


{% codetabs name="Java", type="java" -%}
final InRange function = new InRange.Builder<String>()
        .start("B")
        .end("D")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRange",
  "start" : "B",
  "end" : "D"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRange",
  "start" : "B",
  "end" : "D"
}

{%- language name="Python", type="py" -%}
g.InRange( 
  start="B", 
  end="D" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.lang.String</td><td>A</td><td>false</td></tr>
<tr><td>java.lang.String</td><td>B</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>C</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>D</td><td>true</td></tr>
<tr><td>java.lang.String</td><td>c</td><td>false</td></tr>
<tr><td>java.lang.Integer</td><td>1</td><td>ClassCastException: java.lang.String cannot be cast to java.lang.Integer</td></tr>
<tr><td></td><td>null</td><td>false</td></tr>
</table>

-----------------------------------------------

