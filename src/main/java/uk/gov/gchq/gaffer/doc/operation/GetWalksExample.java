/*
 * Copyright 2016 Crown Copyright
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

import uk.gov.gchq.gaffer.data.Walk;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.graph.SeededGraphFilters;
import uk.gov.gchq.gaffer.operation.impl.GetWalks;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;

public class GetWalksExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new GetWalksExample().run();
    }

    public GetWalksExample() {
        super(GetWalks.class);
    }

    @Override
    public void runExamples() {
        getWalksEdgesOnly();
    }

    public Iterable<Walk> getWalksEdgesOnly() {
        // ---------------------------------------------------------
        final OperationChain<Iterable<Walk>> opChain = new OperationChain.Builder()
                .first(new GetWalks.Builder()
                        .operations(new GetElements.Builder()
                                        .view(new View.Builder()
                                                .edge("edge")
                                                .build())
                                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                        .build(),
                                new GetElements.Builder()
                                        .view(new View.Builder()
                                                .edge("edge")
                                                .build())
                                        .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.OUTGOING)
                                        .build())
                        .input(new EntitySeed(1))
                        .build())
                .build();
        // ---------------------------------------------------------

        return runExample(opChain, "Gets all of the Walks of length 2" +
                "which start from vertex 1, with the added restriction that all " +
                "edges must be traversed using the source as the matched vertex.");
    }
}
