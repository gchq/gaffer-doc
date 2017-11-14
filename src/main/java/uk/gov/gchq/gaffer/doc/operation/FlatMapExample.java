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

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.FlatMap;
import uk.gov.gchq.gaffer.operation.impl.GetWalks;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.operation.impl.output.ToSet;
import uk.gov.gchq.gaffer.operation.impl.output.ToVertices;
import uk.gov.gchq.koryphe.impl.function.FirstItem;

import java.util.Set;

public class FlatMapExample extends OperationExample {

    public static void main(final String[] args) {
        new FlatMapExample().run();
    }

    public FlatMapExample() {
        super(FlatMap.class);
    }

    @Override
    protected void runExamples() {
        flattenNestedIterable();
    }

    public Set<?> flattenNestedIterable() {
        // ---------------------------------------------------------
        final OperationChain<Set<?>> opChain = new OperationChain.Builder()
                .first(new GetWalks.Builder()
                        .operation(new GetElements.Builder()
                                .view(new View.Builder()
                                        .edge("edge")
                                        .build())
                                .build())
                        .resultsLimit(100)
                        .input(new EntitySeed(1), new EntitySeed(2))
                        .build())
                .then(new FlatMap.Builder<Iterable<Edge>, Iterable<Edge>>()
                        .function(new FirstItem<>())
                        .build())
                .then(new FlatMap.Builder<Edge, Edge>()
                        .function(new FirstItem<>())
                        .build())
                .then(new ToVertices.Builder()
                        .useMatchedVertex(ToVertices.UseMatchedVertex.EQUAL)
                        .edgeVertices(ToVertices.EdgeVertices.SOURCE)
                        .build())
                .then(new ToSet<>())
                .build();
        // ---------------------------------------------------------

        return runExample(opChain, "This example details the usage of FlatMap, " +
                "to extract useful information from the GetWalks. " +
                "The FlatMap accepts a mapping function, in this case a FirstItem function.");
    }
}
