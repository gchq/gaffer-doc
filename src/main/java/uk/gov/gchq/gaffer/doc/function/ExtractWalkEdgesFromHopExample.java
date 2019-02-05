/*
 * Copyright 2017-2019 Crown Copyright
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
import uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdgesFromHop;

public class ExtractWalkEdgesFromHopExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractWalkEdgesFromHopExample().runAndPrint();
    }

    public ExtractWalkEdgesFromHopExample() {
        super(ExtractWalkEdgesFromHop.class, "An ExtractWalkEdgesFromHop will extract the Set of Edges at a given hop, from a provided Walk");
    }

    @Override
    protected void runExamples() {
        extractSingleSetOfEdgesFromWalk();
    }

    public void extractSingleSetOfEdgesFromWalk() {
        // ---------------------------------------------------------
        final ExtractWalkEdgesFromHop function = new ExtractWalkEdgesFromHop(1);
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
