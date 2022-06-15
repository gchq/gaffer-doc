# InRangeDual
See javadoc - [uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/impl/predicate/range/InRangeDual.html)

Available since Koryphe version 1.1.0

The predicate tests 2 inputs (a start and an end) are within a defined range.

## Examples

### In long range 5 to 10


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<Long>()
        .start(5L)
        .end(10L)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
  "start" : {
    "Long" : 5
  },
  "end" : {
    "Long" : 10
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
  "start" : {
    "java.lang.Long" : 5
  },
  "end" : {
    "java.lang.Long" : 10
  }
}

{%- language name="Python", type="py" -%}
g.InRangeDual( 
  start={'java.lang.Long': 5}, 
  end={'java.lang.Long': 10} 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[-5, -1]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 10]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[10, 20]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[6, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In long range 5 to 10 exclusive


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<Long>()
        .start(5L)
        .end(10L)
        .startInclusive(false)
        .endInclusive(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
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
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
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
g.InRangeDual( 
  start={'java.lang.Long': 5}, 
  end={'java.lang.Long': 10}, 
  start_inclusive=False, 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[-5, -1]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 10]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[10, 20]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[5, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In long overlapping range


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<Long>()
        .start(5L)
        .end(10L)
        .startFullyContained(false)
        .endFullyContained(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
  "start" : {
    "Long" : 5
  },
  "end" : {
    "Long" : 10
  },
  "endFullyContained" : false,
  "startFullyContained" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
  "start" : {
    "java.lang.Long" : 5
  },
  "end" : {
    "java.lang.Long" : 10
  },
  "endFullyContained" : false,
  "startFullyContained" : false
}

{%- language name="Python", type="py" -%}
g.InRangeDual( 
  start={'java.lang.Long': 5}, 
  end={'java.lang.Long': 10}, 
  start_fully_contained=False, 
  end_fully_contained=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 4]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[7, 11]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[11, 20]</td><td>false</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In long end overlapping range


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<Long>()
        .start(5L)
        .end(10L)
        .startFullyContained(true)
        .endFullyContained(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
  "start" : {
    "Long" : 5
  },
  "end" : {
    "Long" : 10
  },
  "endFullyContained" : false,
  "startFullyContained" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
  "start" : {
    "java.lang.Long" : 5
  },
  "end" : {
    "java.lang.Long" : 10
  },
  "endFullyContained" : false,
  "startFullyContained" : true
}

{%- language name="Python", type="py" -%}
g.InRangeDual( 
  start={'java.lang.Long': 5}, 
  end={'java.lang.Long': 10}, 
  start_fully_contained=True, 
  end_fully_contained=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 4]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 7]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[7, 11]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[11, 20]</td><td>false</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In long non overlapping range


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<Long>()
        .start(5L)
        .end(10L)
        .startFullyContained(true)
        .endFullyContained(true)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
  "start" : {
    "Long" : 5
  },
  "end" : {
    "Long" : 10
  },
  "endFullyContained" : true,
  "startFullyContained" : true
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
  "start" : {
    "java.lang.Long" : 5
  },
  "end" : {
    "java.lang.Long" : 10
  },
  "endFullyContained" : true,
  "startFullyContained" : true
}

{%- language name="Python", type="py" -%}
g.InRangeDual( 
  start={'java.lang.Long': 5}, 
  end={'java.lang.Long': 10}, 
  start_fully_contained=True, 
  end_fully_contained=True 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 4]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 7]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[7, 11]</td><td>false</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[11, 20]</td><td>false</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In long range less than 10

If the start of the range is not specified then the start of the range is unbounded.


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<Long>()
        .end(10L)
        .endInclusive(false)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
  "end" : {
    "Long" : 10
  },
  "endInclusive" : false
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
  "end" : {
    "java.lang.Long" : 10
  },
  "endInclusive" : false
}

{%- language name="Python", type="py" -%}
g.InRangeDual( 
  end={'java.lang.Long': 10}, 
  end_inclusive=False 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[-5, -1]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[1, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 10]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[10, 20]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[5, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In integer range 5 to 10


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<Integer>()
        .start(5)
        .end(10)
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
  "start" : 5,
  "end" : 10
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
  "start" : 5,
  "end" : 10
}

{%- language name="Python", type="py" -%}
g.InRangeDual( 
  start=5, 
  end=10 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[-5, -1]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 6]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 7]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[6, 10]</td><td>true</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[10, 20]</td><td>true</td></tr>
<tr><td>[java.lang.Long, java.lang.Long]</td><td>[6, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[6, 7]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

### In string range b to d


{% codetabs name="Java", type="java" -%}
final InRangeDual function = new InRangeDual.Builder<String>()
        .start("B")
        .end("D")
        .build();

{%- language name="JSON", type="json" -%}
{
  "class" : "InRangeDual",
  "start" : "B",
  "end" : "D"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual",
  "start" : "B",
  "end" : "D"
}

{%- language name="Python", type="py" -%}
g.InRangeDual( 
  start="B", 
  end="D" 
)

{%- endcodetabs %}

Input type:

```
java.lang.Comparable, java.lang.Comparable
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[A, B]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[B, C]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[C, D]</td><td>true</td></tr>
<tr><td>[java.lang.String, java.lang.String]</td><td>[b, c]</td><td>false</td></tr>
<tr><td>[java.lang.Integer, java.lang.Integer]</td><td>[1, 2]</td><td>IllegalArgumentException: Input tuple values do not match the required function input types</td></tr>
<tr><td>[ ,]</td><td>[null, null]</td><td>false</td></tr>
</table>

-----------------------------------------------

