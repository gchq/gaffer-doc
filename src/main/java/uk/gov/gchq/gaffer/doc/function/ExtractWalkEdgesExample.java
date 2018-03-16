/*
 * Copyright 2017-2018 Crown Copyright
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

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.graph.Walk;
import uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdges;

public class ExtractWalkEdgesExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractWalkEdgesExample().runAndPrint();
    }

    public ExtractWalkEdgesExample() {
        super(ExtractWalkEdges.class, "An ExtractWalkEdges will extract a List of ALL Sets of Edges, from a given Walk.");
    }

    @Override
    protected void runExamples() {
        extractEdgesFromWalk();
    }

    public void extractEdgesFromWalk() {
        // ---------------------------------------------------------
        final ExtractWalkEdges function = new ExtractWalkEdges();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Walk.Builder()
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("A")
                                .build())
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
                                .source("A")
                                .dest("B")
                                .directed(true)
                                .build())
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("B")
                                .build())
                        .edges(new Edge.Builder()
                                .group("BasicEdge")
                                .source("B")
                                .dest("C")
                                .directed(true)
                                .build(),
                                new Edge.Builder()
                                .group("EnhancedEdge")
                                .source("B")
                                .dest("C")
                                .directed(true)
                                .build())
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("C")
                                .build())
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
                                .source("C")
                                .dest("A")
                                .directed(true)
                                .build())
                        .entity(new Entity.Builder()
                                .group("BasicEntity")
                                .vertex("A")
                                .build())
                        .build());
    }
}
