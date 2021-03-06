/*
 * Copyright 2017-2020 Crown Copyright
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
import uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEntitiesFromHop;

public class ExtractWalkEntitiesFromHopExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractWalkEntitiesFromHopExample().runAndPrint();
    }

    public ExtractWalkEntitiesFromHopExample() {
        super(ExtractWalkEntitiesFromHop.class);
    }

    @Override
    protected void runExamples() {
        extractSingleSetOfEntitiesFromWalk();
    }

    public void extractSingleSetOfEntitiesFromWalk() {
        // ---------------------------------------------------------
        final ExtractWalkEntitiesFromHop function = new ExtractWalkEntitiesFromHop(1);
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
                        .entity(new Entity.Builder()
                                .group("EnhancedEntity")
                                .vertex("B")
                                .build())
                        .edge(new Edge.Builder()
                                .group("BasicEdge")
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
