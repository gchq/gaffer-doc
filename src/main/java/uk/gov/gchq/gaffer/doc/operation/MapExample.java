/*
 * Copyright 2017 Crown Copyright
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
package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.graph.Walk;
import uk.gov.gchq.gaffer.data.graph.function.walk.ExtractWalkEdgesFromHop;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.GetWalks;
import uk.gov.gchq.gaffer.operation.impl.Map;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.operation.impl.output.ToSet;
import uk.gov.gchq.gaffer.operation.impl.output.ToVertices;
import uk.gov.gchq.koryphe.impl.function.FirstItem;
import uk.gov.gchq.koryphe.impl.function.IterableFunction;

import java.util.Set;

public class MapExample extends OperationExample {

    public static void main(final String[] args) {
        new MapExample().run();
    }

    public MapExample() {
        super(Map.class);
    }

    @Override
    protected void runExamples() {
        extractFromGetElements();
        extractFirstItemsFromWalks();
    }

    public Object extractFromGetElements() {
        // ---------------------------------------------------------
        final OperationChain<?> operationChain = new OperationChain.Builder()
                .first(new GetElements.Builder()
                        .view(new View.Builder()
                                .entity("entity")
                                .build())
                        .build())
                .then(new Map.Builder<Iterable<? extends Element>>()
                        .first(new FirstItem<>())
                        .build())
                .build();
        // ---------------------------------------------------------

        return runExample(operationChain, "This simple example demonstrates retrieving " +
                "elements from the \"entity\" group, from which the first item is " +
                "extracted.");
    }

    public Set<?> extractFirstItemsFromWalks() {
        // ---------------------------------------------------------
        final OperationChain<Set<?>> opChain = new OperationChain.Builder()
                .first(new GetWalks.Builder()
                        .operations(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .resultsLimit(100)
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new Map.Builder<Iterable<Walk>>()
                        .first(new IterableFunction.Builder<Walk>()
                                .first(new ExtractWalkEdgesFromHop(0))
                                .then(new FirstItem<>())
                                .build())
                        .build())
                .then(new ToVertices.Builder()
                        .edgeVertices(ToVertices.EdgeVertices.SOURCE)
                        .build())
                .then(new ToSet<>())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, "This example demonstrates the extraction of " +
                "the input seeds to a GetWalks operation, using the Map operation " +
                "with ExtractWalkEdgesFromHop, and FirstItem functions.");
    }
}
