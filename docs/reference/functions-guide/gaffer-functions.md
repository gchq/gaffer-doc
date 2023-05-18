# Gaffer Functions

Functions which are part of Gaffer.

## CsvGenerator

Generates a CSV string for each Element, based on the fields and constants provided. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/generator/CsvGenerator.html)

Input type: `java.lang.Iterable`

??? example "Example Elements to CSV"

    === "Java"

        ``` java
        final CsvGenerator function = new CsvGenerator.Builder()
                .group("Group Label")
                .vertex("Vertex Label")
                .source("Source Label")
                .property("count", "Count Label")
                .constant("A Constant", "Some constant value")
                .quoted(false)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "CsvGenerator",
          "fields" : {
            "GROUP" : "Group Label",
            "VERTEX" : "Vertex Label",
            "SOURCE" : "Source Label",
            "count" : "Count Label"
          },
          "constants" : {
            "A Constant" : "Some constant value"
          },
          "quoted" : false,
          "commaReplacement" : " "
        }
        ```

    === "Python"

        ``` python
        g.CsvGenerator( 
          fields={'GROUP': 'Group Label', 'VERTEX': 'Vertex Label', 'SOURCE': 'Source Label', 'count': 'Count Label'}, 
          constants={'A Constant': 'Some constant value'}, 
          quoted=False, 
          comma_replacement=" " 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [Entity[vertex=vertex1,group=Foo,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Entity[vertex=vertex2,group=Foo,properties=Properties[]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[]]] | uk.gov.gchq.gaffer.data.generator.OneToOneObjectGenerator$1 | [Foo,vertex1,,1,A Constant, Foo,vertex2,,,A Constant, Bar,,dest1,1,A Constant, Bar,,dest1,,A Constant]

??? example "Example Elements to quoted CSV"

    === "Java"

        ``` java
        final CsvGenerator function = new CsvGenerator.Builder()
                .group("Group Label")
                .vertex("Vertex Label")
                .source("Source Label")
                .property("count", "Count Label")
                .constant("A Constant", "Some constant value")
                .quoted(true)
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "CsvGenerator",
          "fields" : {
            "GROUP" : "Group Label",
            "VERTEX" : "Vertex Label",
            "SOURCE" : "Source Label",
            "count" : "Count Label"
          },
          "constants" : {
            "A Constant" : "Some constant value"
          },
          "quoted" : true,
          "commaReplacement" : " "
        }
        ```

    === "Python"

        ``` python
        g.CsvGenerator( 
          fields={'GROUP': 'Group Label', 'VERTEX': 'Vertex Label', 'SOURCE': 'Source Label', 'count': 'Count Label'}, 
          constants={'A Constant': 'Some constant value'}, 
          quoted=True, 
          comma_replacement=" " 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [Entity[vertex=vertex1,group=Foo,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Entity[vertex=vertex2,group=Foo,properties=Properties[]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[]]] | uk.gov.gchq.gaffer.data.generator.OneToOneObjectGenerator$1 | ["Foo","vertex1",,"1","A Constant", "Foo","vertex2",,,"A Constant", "Bar",,"dest1","1","A Constant", "Bar",,"dest1",,"A Constant"]

## ExtractGroup

Extracts a group from an element. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ExtractGroup.html)

Input type: `uk.gov.gchq.gaffer.data.element.Element`

??? example "Example ExtractGroup"

    The function will simply extract the group from a given Element.
    
    === "Java"

        ``` java
        final ExtractGroup function = new ExtractGroup();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractGroup"
        }
        ```

    === "Python"

        ``` python
        g.ExtractGroup()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.element.Edge | Edge[source=src,destination=dest,directed=true,group=EdgeGroup,properties=Properties[]] | java.lang.String | EdgeGroup
    uk.gov.gchq.gaffer.data.element.Entity | Entity[vertex=vertex,group=EntityGroup,properties=Properties[]] | java.lang.String | EntityGroup
    uk.gov.gchq.gaffer.operation.data.EntitySeed | EntitySeed[vertex=vertex] |  | ClassCastException: uk.gov.gchq.gaffer.operation.data.EntitySeed cannot be cast to uk.gov.gchq.gaffer.data.element.Element
     | null |  | null

## ExtractId

Extracts an identifier from an element. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ExtractId.html)

Input type: `uk.gov.gchq.gaffer.data.element.Element`

??? example "Example ExtractId with Edge"

    The function will simply extract the value of the provided Id, for a given Element. This Id can either be an IdentifierType, or a String representation, eg "SOURCE".
    
    === "Java"

        ``` java
        final ExtractId function = new ExtractId(IdentifierType.SOURCE);
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractId",
          "id" : "SOURCE"
        }
        ```

    === "Python"

        ``` python
        g.ExtractId( 
          id="SOURCE" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.element.Edge | Edge[source=src,destination=dest,directed=true,group=edge,properties=Properties[]] | java.lang.String | src
    uk.gov.gchq.gaffer.data.element.Edge | Edge[source=13.2,destination=15.642,directed=true,group=otherEdge,properties=Properties[]] | java.lang.Double | 13.2

??? example "Example ExtractId with Entity"

    This example simply demonstrates the same functionality but on an Entity.
    
    === "Java"

        ``` java
        final ExtractId function = new ExtractId(IdentifierType.VERTEX);
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractId",
          "id" : "VERTEX"
        }
        ```

    === "Python"

        ``` python
        g.ExtractId( 
          id="VERTEX" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.element.Entity | Entity[vertex=v1,group=entity,properties=Properties[]] | java.lang.String | v1

## ExtractProperty

Extracts a property from an element. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ExtractProperty.html)

Input type: `uk.gov.gchq.gaffer.data.element.Element`

??? example "Example ExtractProperty from Element"

    If present, the function will extract the value of the specified property, otherwise returning null.
    
    === "Java"

        ``` java
        final ExtractProperty function = new ExtractProperty("prop1");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractProperty",
          "name" : "prop1"
        }
        ```

    === "Python"

        ``` python
        g.ExtractProperty( 
          name="prop1" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.element.Edge | Edge[source=dest,destination=src,directed=false,group=edge,properties=Properties[prop2=&lt;java.lang.String&gt;test,prop1=&lt;java.lang.Integer&gt;3]] | java.lang.Integer | 3
    uk.gov.gchq.gaffer.data.element.Entity | Entity[vertex=vertex,group=entity,properties=Properties[prop2=&lt;java.lang.Integer&gt;2,prop1=&lt;java.lang.Integer&gt;12,prop3=&lt;java.lang.String&gt;test]] | java.lang.Integer | 12
    uk.gov.gchq.gaffer.data.element.Edge | Edge[directed=false,group=UNKNOWN,properties=Properties[]] |  | null

## ExtractWalkEdges

An ExtractWalkEdges will extract a List of ALL Sets of Edges, from a given Walk. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkEdges.html)

Input type: `uk.gov.gchq.gaffer.data.graph.Walk`

??? example "Example ExtractWalkEdges"

    === "Java"

        ``` java
        final ExtractWalkEdges function = new ExtractWalkEdges();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractWalkEdges"
        }
        ```

    === "Python"

        ``` python
        g.ExtractWalkEdges()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.graph.Walk | [[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]] | java.util.LinkedList | [[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]]

## ExtractWalkEdgesFromHop

An ExtractWalkEdgesFromHop will extract the Set of Edges at a given hop, from a provided Walk. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkEdgesFromHop.html)

Input type: `uk.gov.gchq.gaffer.data.graph.Walk`

??? example "Example ExtractWalkEdgesFromHop with single set of Edges"

    === "Java"

        ``` java
        final ExtractWalkEdgesFromHop function = new ExtractWalkEdgesFromHop(1);
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractWalkEdgesFromHop",
          "hop" : 1
        }
        ```

    === "Python"

        ``` python
        g.ExtractWalkEdgesFromHop( 
          hop=1 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.graph.Walk | [[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]] | java.util.HashSet | [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]]

## ExtractWalkEntitiesFromHop

Extracts the set of entities from a single hop in a Walk. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkEntitiesFromHop.html)

Input type: `uk.gov.gchq.gaffer.data.graph.Walk`

??? example "Example ExtractWalkEntitiesFromHop with single set of Entities"

    === "Java"

        ``` java
        final ExtractWalkEntitiesFromHop function = new ExtractWalkEntitiesFromHop(1);
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractWalkEntitiesFromHop",
          "hop" : 1
        }
        ```

    === "Python"

        ``` python
        g.ExtractWalkEntitiesFromHop( 
          hop=1 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.graph.Walk | [[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]] | java.util.HashSet | [Entity[vertex=B,group=BasicEntity,properties=Properties[]], Entity[vertex=B,group=EnhancedEntity,properties=Properties[]]]

## ExtractWalkVertex

Extracts the source vertex from a Walk. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/graph/function/walk/ExtractWalkVertex.html)

Input type: `uk.gov.gchq.gaffer.data.graph.Walk`

??? example "Example ExtractWalkVertex"

    === "Java"

        ``` java
        final ExtractWalkVertex function = new ExtractWalkVertex();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ExtractWalkVertex"
        }
        ```

    === "Python"

        ``` python
        g.ExtractWalkVertex()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.data.graph.Walk | [[Edge[source=A,destination=B,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=B,destination=C,directed=true,group=EnhancedEdge,properties=Properties[]], Edge[source=B,destination=C,directed=true,group=BasicEdge,properties=Properties[]]], [Edge[source=C,destination=A,directed=true,group=BasicEdge,properties=Properties[]]]] | java.lang.String | A

## FreqMapExtractor

Extracts a count from a frequency map for a given key. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/function/FreqMapExtractor.html)

Input type: `uk.gov.gchq.gaffer.types.FreqMap`

??? example "Example FreqMapExtractor"

    === "Java"

        ``` java
        final FreqMapExtractor function = new FreqMapExtractor("key1");
        ```

    === "JSON"

        ``` json
        {
          "class" : "FreqMapExtractor",
          "key" : "key1"
        }
        ```

    === "Python"

        ``` python
        g.FreqMapExtractor( 
          key="key1" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.types.FreqMap | {key1=1, key2=2, key3=3} | java.lang.Long | 1
    uk.gov.gchq.gaffer.types.FreqMap | {key2=2, key3=3} |  | null
    java.util.HashMap | {key1=1, key2=2, key3=3} |  | ClassCastException: java.util.HashMap cannot be cast to uk.gov.gchq.gaffer.types.FreqMap
     | null |  | null

## MapGenerator

Converts an iterable of elements into an Iterable of Maps. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/generator/MapGenerator.html)

Input type: `java.lang.Iterable`

??? example "Example MapGenerator"

    === "Java"

        ``` java
        final MapGenerator function = new MapGenerator.Builder()
                .group("Group Label")
                .vertex("Vertex Label")
                .source("Source Label")
                .property("count", "Count Label")
                .constant("A Constant", "Some constant value")
                .build();
        ```

    === "JSON"

        ``` json
        {
          "class" : "MapGenerator",
          "fields" : {
            "GROUP" : "Group Label",
            "VERTEX" : "Vertex Label",
            "SOURCE" : "Source Label",
            "count" : "Count Label"
          },
          "constants" : {
            "A Constant" : "Some constant value"
          }
        }
        ```

    === "Python"

        ``` python
        g.MapGenerator( 
          fields={'GROUP': 'Group Label', 'VERTEX': 'Vertex Label', 'SOURCE': 'Source Label', 'count': 'Count Label'}, 
          constants={'A Constant': 'Some constant value'} 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.ArrayList | [Entity[vertex=vertex1,group=Foo,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Entity[vertex=vertex2,group=Foo,properties=Properties[]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[count=&lt;java.lang.Integer&gt;1]], Edge[source=dest1,destination=source1,directed=false,group=Bar,properties=Properties[]]] | uk.gov.gchq.gaffer.data.generator.OneToOneObjectGenerator$1 | [{Group Label=Foo, Vertex Label=vertex1, Count Label=1, A Constant=Some constant value}, {Group Label=Foo, Vertex Label=vertex2, A Constant=Some constant value}, {Group Label=Bar, Source Label=dest1, Count Label=1, A Constant=Some constant value}, {Group Label=Bar, Source Label=dest1, A Constant=Some constant value}]

## MaskTimestampSetByTimeRange

Applies a mask to a timestamp set based on a start and end date. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/time/function/MaskTimestampSetByTimeRange.html)

Input type: `uk.gov.gchq.gaffer.time.RBMBackedTimestampSet`

??? example "Example MaskTimestampSetByTimeRange with start date"

    === "Java"

        ``` java
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10000L, null);
        ```

    === "JSON"

        ``` json
        {
          "class" : "MaskTimestampSetByTimeRange",
          "startTime" : 10000,
          "timeUnit" : "MILLISECOND"
        }
        ```

    === "Python"

        ``` python
        g.MaskTimestampSetByTimeRange( 
          start_time=10000, 
          time_unit="MILLISECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z] | uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]

??? example "Example MaskTimestampSetByTimeRange with end date"

    === "Java"

        ``` java
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(null, 20000L);
        ```

    === "JSON"

        ``` json
        {
          "class" : "MaskTimestampSetByTimeRange",
          "endTime" : 20000,
          "timeUnit" : "MILLISECOND"
        }
        ```

    === "Python"

        ``` python
        g.MaskTimestampSetByTimeRange( 
          end_time=20000, 
          time_unit="MILLISECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z] | uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z]

??? example "Example MaskTimestampSetByTimeRange with start and end date"

    === "Java"

        ``` java
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10000L, 20000L);
        ```

    === "JSON"

        ``` json
        {
          "class" : "MaskTimestampSetByTimeRange",
          "startTime" : 10000,
          "endTime" : 20000,
          "timeUnit" : "MILLISECOND"
        }
        ```

    === "Python"

        ``` python
        g.MaskTimestampSetByTimeRange( 
          start_time=10000, 
          end_time=20000, 
          time_unit="MILLISECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z] | uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:10Z,1970-01-01T00:00:20Z]

??? example "Example MaskTimestampSetByTimeRange without start or end date"

    === "Java"

        ``` java
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange();
        ```

    === "JSON"

        ``` json
        {
          "class" : "MaskTimestampSetByTimeRange",
          "timeUnit" : "MILLISECOND"
        }
        ```

    === "Python"

        ``` python
        g.MaskTimestampSetByTimeRange( 
          time_unit="MILLISECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z] | uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z]

??? example "Example MaskTimestampSetByTimeRange with time unit"

    === "Java"

        ``` java
        MaskTimestampSetByTimeRange function = new MaskTimestampSetByTimeRange(10L, 25L, TimeUnit.SECOND);
        ```

    === "JSON"

        ``` json
        {
          "class" : "MaskTimestampSetByTimeRange",
          "startTime" : 10,
          "endTime" : 25,
          "timeUnit" : "SECOND"
        }
        ```

    === "Python"

        ``` python
        g.MaskTimestampSetByTimeRange( 
          start_time=10, 
          end_time=25, 
          time_unit="SECOND" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:00Z,1970-01-01T00:00:10Z,1970-01-01T00:00:20Z,1970-01-01T00:00:30Z] | uk.gov.gchq.gaffer.time.RBMBackedTimestampSet | RBMBackedTimestampSet[timeBucket=SECOND,timestamps=1970-01-01T00:00:10Z,1970-01-01T00:00:20Z]

## ReduceRelatedElements

This function takes an Iterable of Elements and combines all related elements using the provided aggregator and related group. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/ReduceRelatedElements.html)

Input type: `java.lang.Iterable`

??? example "Example ReduceRelatedElements - Basic"

    In this small example, vertex 1a is related to vertex 1b, and vertex 2a is related to vertex 2b.  
    As well as this, vertex 1a is connected to vertex 2b with the basicEdge group.  
    We setup the function to do a few things.  
    Firstly, we set the visibility property name, then state we want to concatenate the visibility properties.  
    Next we set the vertex aggregator to the Max Binary Operator. This will be used to compare and reduce vertices with.  
    Finally, we assert the vertex groups that describe which vertices are related, in this case 'relatesTo'.  

    In our results we should expect to see that 1b and 2b are source and dest as they were aggregated with the Max operator.  
    The other properties should be listed in the related properties. As well as this, the visiblities should be concatenated together.
    
    === "Java"

        ``` java
        final List<Element> elements = Arrays.asList(
                new Edge.Builder()
                        .source("1a")
                        .dest("2b")
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("1a")
                        .dest("1b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("2a")
                        .dest("2b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build()
        );
        final ReduceRelatedElements function = new ReduceRelatedElements();
        function.setVisibilityProperty("visibility");
        function.setVisibilityAggregator(new CollectionConcat<>());
        function.setVertexAggregator(new Max());
        function.setRelatedVertexGroups(Collections.singleton("relatesTo"));
        ```

    === "JSON"

        ``` json
        {
          "class" : "ReduceRelatedElements",
          "vertexAggregator" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
          },
          "visibilityAggregator" : {
            "class" : "CollectionConcat"
          },
          "visibilityProperty" : "visibility",
          "relatedVertexGroups" : [ "relatesTo" ]
        }
        ```

    === "Python"

        ``` python
        g.ReduceRelatedElements( 
          visibility_aggregator=g.CollectionConcat(), 
          vertex_aggregator=g.Max(), 
          related_vertex_groups=[ 
            "relatesTo" 
          ], 
          visibility_property="visibility" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.Arrays$ArrayList | [Edge[source=1a,destination=2b,directed=false,group=basicEdge,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Edge[source=1a,destination=1b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Edge[source=2a,destination=2b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[private]]]] | uk.gov.gchq.koryphe.util.IterableUtil$ChainedIterable | [Edge[source=1b,destination=2b,directed=false,group=basicEdge,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public, private],sourceRelatedVertices=&lt;java.util.HashSet&gt;[1a],destinationRelatedVertices=&lt;java.util.HashSet&gt;[2a]]]]
     | null |  | null

??? example "Example ReduceRelatedElements - Complex"

    === "Java"

        ``` java
        final List<Element> elements = Arrays.asList(
                new Edge.Builder()
                        .source("1b")
                        .dest("2a")
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("1a")
                        .dest("3a")
                        .group("basicEdge")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Entity.Builder()
                        .vertex("2a")
                        .group("basicEntity")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("1b")
                        .dest("1a")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("2a")
                        .dest("2b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("3a")
                        .dest("3b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build(),
                new Edge.Builder()
                        .source("2a")
                        .dest("3b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build(),
                new Edge.Builder()
                        .source("2b")
                        .dest("3a")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build(),
                new Edge.Builder()
                        .source("3a")
                        .dest("4b")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("private"))
                        .build(),
                new Edge.Builder()
                        .source("5b")
                        .dest("4a")
                        .group("relatesTo")
                        .property("visibility", Sets.newHashSet("public"))
                        .build()
        );
        final ReduceRelatedElements function = new ReduceRelatedElements();
        function.setVisibilityProperty("visibility");
        function.setVisibilityAggregator(new CollectionConcat<>());
        function.setVertexAggregator(new Max());
        function.setRelatedVertexGroups(Collections.singleton("relatesTo"));
        ```

    === "JSON"

        ``` json
        {
          "class" : "ReduceRelatedElements",
          "vertexAggregator" : {
            "class" : "uk.gov.gchq.koryphe.impl.binaryoperator.Max"
          },
          "visibilityAggregator" : {
            "class" : "CollectionConcat"
          },
          "visibilityProperty" : "visibility",
          "relatedVertexGroups" : [ "relatesTo" ]
        }
        ```

    === "Python"

        ``` python
        g.ReduceRelatedElements( 
          visibility_aggregator=g.CollectionConcat(), 
          vertex_aggregator=g.Max(), 
          related_vertex_groups=[ 
            "relatesTo" 
          ], 
          visibility_property="visibility" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.util.Arrays$ArrayList | [Edge[source=1b,destination=2a,directed=false,group=basicEdge,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Edge[source=1a,destination=3a,directed=false,group=basicEdge,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Entity[vertex=2a,group=basicEntity,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Edge[source=1a,destination=1b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Edge[source=2a,destination=2b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Edge[source=3a,destination=3b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[private]]], Edge[source=2a,destination=3b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[private]]], Edge[source=2b,destination=3a,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]], Edge[source=3a,destination=4b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[private]]], Edge[source=4a,destination=5b,directed=false,group=relatesTo,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public]]]] | uk.gov.gchq.koryphe.util.IterableUtil$ChainedIterable | [Edge[source=1b,destination=4b,directed=false,group=basicEdge,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public, private],sourceRelatedVertices=&lt;java.util.HashSet&gt;[1a],destinationRelatedVertices=&lt;java.util.HashSet&gt;[2b, 3a, 2a, 3b]]], Edge[source=1b,destination=4b,directed=false,group=basicEdge,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public, private],sourceRelatedVertices=&lt;java.util.HashSet&gt;[1a],destinationRelatedVertices=&lt;java.util.HashSet&gt;[2b, 3a, 2a, 3b]]], Entity[vertex=4b,group=basicEntity,properties=Properties[visibility=&lt;java.util.HashSet&gt;[public, private],relatedVertices=&lt;java.util.HashSet&gt;[2b, 3a, 2a, 3b]]]]

## ToFreqMap

Creates a new FreqMap and upserts a given value. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/function/ToFreqMap.html)

Input type: `java.lang.Object`

??? example "Example ToFreqMap"

    === "Java"

        ``` java
        Function toFreqMap = new ToFreqMap();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToFreqMap"
        }
        ```

    === "Python"

        ``` python
        g.ToFreqMap()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | aString | uk.gov.gchq.gaffer.types.FreqMap | {aString=1}
    java.lang.Long | 100 | uk.gov.gchq.gaffer.types.FreqMap | {100=1}
    java.lang.Integer | 20 | uk.gov.gchq.gaffer.types.FreqMap | {20=1}
    uk.gov.gchq.gaffer.types.TypeValue | TypeValue[type=type1,value=value1] | uk.gov.gchq.gaffer.types.FreqMap | {TypeValue[type=type1,value=value1]=1}
     | null | uk.gov.gchq.gaffer.types.FreqMap | {null=1}

## ToTypeValue

Converts a value into a TypeValue. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/function/ToTypeValue.html)

Input type: `java.lang.Object`

??? example "Example ToTypeValue"

    === "Java"

        ``` java
        Function toTypeValue = new ToTypeValue();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToTypeValue"
        }
        ```

    === "Python"

        ``` python
        g.ToTypeValue()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | aString | uk.gov.gchq.gaffer.types.TypeValue | TypeValue[value=aString]
    java.lang.Long | 100 | uk.gov.gchq.gaffer.types.TypeValue | TypeValue[value=100]
    java.lang.Integer | 25 | uk.gov.gchq.gaffer.types.TypeValue | TypeValue[value=25]
    uk.gov.gchq.gaffer.types.TypeValue | TypeValue[type=type1,value=value1] | uk.gov.gchq.gaffer.types.TypeValue | TypeValue[value=TypeValue[type=type1,value=value1]]
     | null | uk.gov.gchq.gaffer.types.TypeValue | TypeValue[]

## ToTypeSubTypeValue

Converts a value into a TypeSubTypeValue. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/types/function/ToTypeSubTypeValue.html)

Input type: `java.lang.Object`

??? example "Example ToTypeSubTypeValue"

    === "Java"

        ``` java
        Function toTypeSubTypeValue = new ToTypeSubTypeValue();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToTypeSubTypeValue"
        }
        ```

    === "Python"

        ``` python
        g.ToTypeSubTypeValue()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | aString | uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[value=aString]
    java.lang.Long | 100 | uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[value=100]
    java.lang.Integer | 25 | uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[value=25]
    uk.gov.gchq.gaffer.types.TypeValue | TypeValue[type=type1,value=value1] | uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[value=TypeValue[type=type1,value=value1]]
     | null | uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[]

## ToTrailingWildcardPair

Converts an input value into a pair of EntityIds representing a range. The end of range is customisable. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/operation/function/ToTrailingWildcardPair.html)

Input type: `java.lang.String`

??? example "Example ToTrailingWildcardPair with default end of range"

    === "Java"

        ``` java
        final ToTrailingWildcardPair function = new ToTrailingWildcardPair();
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToTrailingWildcardPair",
          "endOfRange" : "~"
        }
        ```

    === "Python"

        ``` python
        g.ToTrailingWildcardPair( 
          end_of_range="~" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | value1 | uk.gov.gchq.gaffer.commonutil.pair.Pair | Pair[first=EntitySeed[vertex=value1],second=EntitySeed[vertex=value1~]]
    java.lang.String | value2 | uk.gov.gchq.gaffer.commonutil.pair.Pair | Pair[first=EntitySeed[vertex=value2],second=EntitySeed[vertex=value2~]]
     | null |  | null

??? example "Example ToTrailingWildcardPair with custom end of range"

    === "Java"

        ``` java
        final ToTrailingWildcardPair function = new ToTrailingWildcardPair();
        function.setEndOfRange("custom");
        ```

    === "JSON"

        ``` json
        {
          "class" : "ToTrailingWildcardPair",
          "endOfRange" : "custom"
        }
        ```

    === "Python"

        ``` python
        g.ToTrailingWildcardPair( 
          end_of_range="custom" 
        )
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    java.lang.String | value1 | uk.gov.gchq.gaffer.commonutil.pair.Pair | Pair[first=EntitySeed[vertex=value1],second=EntitySeed[vertex=value1custom]]
    java.lang.String | value2 | uk.gov.gchq.gaffer.commonutil.pair.Pair | Pair[first=EntitySeed[vertex=value2],second=EntitySeed[vertex=value2custom]]
     | null |  | null

## TypeSubTypeValueToTuple

Converts an TypeSubTypeValue into a Tuple. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/TypeSubTypeValueToTuple.html)

Input type: `uk.gov.gchq.gaffer.types.TypeSubTypeValue`

??? example "Example TypeSubTypeValueToTuple"

    === "Java"

        ``` java
        final TypeSubTypeValueToTuple function = new TypeSubTypeValueToTuple();
        ```

    === "JSON"

        ``` json
        {
          "class" : "TypeSubTypeValueToTuple"
        }
        ```

    === "Python"

        ``` python
        g.TypeSubTypeValueToTuple()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.types.TypeSubTypeValue | TypeSubTypeValue[type=type,subType=subType,value=value] | [java.lang.String, java.lang.String, java.lang.String] | [type, subType, value]
     | null |  | null

## TypeValueToTuple

Converts an TypeValue into a Tuple. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/TypeValueToTuple.html)

Input type: `uk.gov.gchq.gaffer.types.TypeSubTypeValue`

??? example "Example TypeValueToTuple"

    === "Java"

        ``` java
        final TypeValueToTuple function = new TypeValueToTuple();
        ```

    === "JSON"

        ``` json
        {
          "class" : "TypeValueToTuple"
        }
        ```

    === "Python"

        ``` python
        g.TypeValueToTuple()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.types.TypeValue | TypeValue[type=type,value=value] | [java.lang.String, java.lang.String] | [type, value]
     | null |  | null

## UnwrapEntityId

For input objects which are an EntityId, the vertex value will be unwrapped and returned, otherwise the original object will be returned. [Javadoc](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/data/element/function/UnwrapEntityId.html)

Input type: `java.lang.Object`

??? example "Example UnwrapEntityId"

    === "Java"

        ``` java
        final UnwrapEntityId function = new UnwrapEntityId();
        ```

    === "JSON"

        ``` json
        {
          "class" : "UnwrapEntityId"
        }
        ```

    === "Python"

        ``` python
        g.UnwrapEntityId()
        ```
    
    Example inputs:

    Input Type | Input | Result Type | Result
    ---------- | ----- | ----------- | ------
    uk.gov.gchq.gaffer.operation.data.EntitySeed | EntitySeed[vertex=vertex1] | java.lang.String | vertex1
    uk.gov.gchq.gaffer.operation.data.EntitySeed | EntitySeed[vertex=vertex2] | java.lang.String | vertex2
    uk.gov.gchq.gaffer.data.element.Entity | Entity[vertex=vertex2,group=group,properties=Properties[]] | java.lang.String | vertex2
    java.lang.String | a string | java.lang.String | a string
    java.lang.Integer | 10 | java.lang.Integer | 10
     | null |  | null
