/*
 * Copyright 2016-2020 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.gaffer.doc.function;

import com.google.common.collect.Lists;

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.generator.MapGenerator;

public class MapGeneratorExample extends FunctionExample {
    public static void main(final String[] args) {
        new MapGeneratorExample().runAndPrint();
    }

    public MapGeneratorExample() {
        super(MapGenerator.class, "Converts an iterable of elements into an iterable of maps");
    }

    @Override
    public void runExamples() {
        elementsToMap();
    }

    public void elementsToMap() {
        // ---------------------------------------------------------
        final MapGenerator function = new MapGenerator.Builder()
                .group("Group Label")
                .vertex("Vertex Label")
                .source("Source Label")
                .property("count", "Count Label")
                .constant("A Constant", "Some constant value")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                Lists.newArrayList(
                        new Entity.Builder()
                                .group("Foo")
                                .vertex("vertex1")
                                .property("count", 1)
                                .build(),
                        new Entity.Builder()
                                .group("Foo")
                                .vertex("vertex2")
                                .build(),
                        new Edge.Builder()
                                .group("Bar")
                                .source("source1")
                                .dest("dest1")
                                .property("count", 1)
                                .build(),
                        new Edge.Builder()
                                .group("Bar")
                                .source("source1")
                                .dest("dest1")
                                .build()
                )
        );
    }
}
