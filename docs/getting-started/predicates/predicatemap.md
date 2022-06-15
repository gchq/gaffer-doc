# PredicateMap
See javadoc - [uk.gov.gchq.koryphe.predicate.PredicateMap](ref://../../javadoc/koryphe/uk/gov/gchq/koryphe/predicate/PredicateMap.html)

Available since Koryphe version 1.0.0

Extracts a value from a map then applies the predicate to it

## Examples

### Freq map is more than 2


{% codetabs name="Java", type="java" -%}
final PredicateMap function = new PredicateMap("key1", new IsMoreThan(2L));

{%- language name="JSON", type="json" -%}
{
  "class" : "PredicateMap",
  "predicate" : {
    "class" : "IsMoreThan",
    "orEqualTo" : false,
    "value" : {
      "Long" : 2
    }
  },
  "key" : "key1"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : false,
    "value" : {
      "java.lang.Long" : 2
    }
  },
  "key" : "key1"
}

{%- language name="Python", type="py" -%}
g.PredicateMap( 
  key="key1", 
  predicate=g.IsMoreThan( 
    value={'java.lang.Long': 2}, 
    or_equal_to=False 
  ) 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=1}</td><td>false</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=2}</td><td>false</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3, key2=0}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key2=3}</td><td>false</td></tr>
</table>

-----------------------------------------------

### Freq map is more than or equal to 2


{% codetabs name="Java", type="java" -%}
final PredicateMap function = new PredicateMap("key1", new IsMoreThan(2L, true));

{%- language name="JSON", type="json" -%}
{
  "class" : "PredicateMap",
  "predicate" : {
    "class" : "IsMoreThan",
    "orEqualTo" : true,
    "value" : {
      "Long" : 2
    }
  },
  "key" : "key1"
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.IsMoreThan",
    "orEqualTo" : true,
    "value" : {
      "java.lang.Long" : 2
    }
  },
  "key" : "key1"
}

{%- language name="Python", type="py" -%}
g.PredicateMap( 
  key="key1", 
  predicate=g.IsMoreThan( 
    value={'java.lang.Long': 2}, 
    or_equal_to=True 
  ) 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=1}</td><td>false</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=2}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key1=3, key2=0}</td><td>true</td></tr>
<tr><td>uk.gov.gchq.gaffer.types.FreqMap</td><td>{key2=3}</td><td>false</td></tr>
</table>

-----------------------------------------------

### Map with date key has a value that exists


{% codetabs name="Java", type="java" -%}
final PredicateMap function = new PredicateMap(new Date(0L), new Exists());

{%- language name="JSON", type="json" -%}
{
  "class" : "PredicateMap",
  "predicate" : {
    "class" : "Exists"
  },
  "key" : {
    "Date" : 0
  }
}

{%- language name="Full JSON", type="json" -%}
{
  "class" : "uk.gov.gchq.koryphe.predicate.PredicateMap",
  "predicate" : {
    "class" : "uk.gov.gchq.koryphe.impl.predicate.Exists"
  },
  "key" : {
    "java.util.Date" : 0
  }
}

{%- language name="Python", type="py" -%}
g.PredicateMap( 
  key={'java.util.Date': 0}, 
  predicate=g.Exists() 
)

{%- endcodetabs %}

Input type:

```
java.util.Map
```

Example inputs:
<table style="display: block;">
<tr><th>Input Type</th><th>Input</th><th>Result</th></tr>
<tr><td>java.util.HashMap</td><td>{Wed Dec 31 16:00:00 PST 1969=1}</td><td>true</td></tr>
<tr><td>java.util.HashMap</td><td>{Thu Sep 23 06:35:04 PDT 2021=2}</td><td>false</td></tr>
</table>

-----------------------------------------------

