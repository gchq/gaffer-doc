/*
 * Copyright 2018-2019 Crown Copyright
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


import com.beust.jcommander.internal.Lists;

import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.id.EntityId;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.graph.SeededGraphFilters.IncludeIncomingOutgoingType;
import uk.gov.gchq.gaffer.operation.impl.GetWalks;
import uk.gov.gchq.gaffer.operation.impl.GetWalks.Builder;
import uk.gov.gchq.gaffer.operation.impl.While;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.operation.impl.output.ToSet;
import uk.gov.gchq.koryphe.impl.predicate.CollectionContains;
import uk.gov.gchq.koryphe.impl.predicate.Not;

public class WhileExample extends OperationExample {

    public static void main(final String[] args) {
        new WhileExample().runAndPrint();
    }

    public WhileExample() {
        super(While.class, "Examples for the While Operation. " +
                "These examples use a modified, more complex graph.", true);
    }

    @Override
    protected void runExamples() {
        run3Times();
        runAWhileOperationWithinAGetWalks();
        runUntilAnEndResultIsFound();

        // TODO: run this example when the bug has been fixed
//        runGetWalksUntilAnEndResultIsFound();
    }

    public void run3Times() {
        // ---------------------------------------------------------
        final While<Iterable<EntityId>, CloseableIterable<? extends EntityId>> operation = new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
                .input(Lists.newArrayList(new EntitySeed(1)))
                .condition(true)
                .maxRepeats(3)
                .operation(new GetAdjacentIds.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(operation, "This example will run the GetAdjacentIds operation 3 times");
    }

    public void runAWhileOperationWithinAGetWalks() {
        // ---------------------------------------------------------
        final GetWalks operation = new Builder()
                .input(new EntitySeed(1))
                .operations(new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
                        .condition(true)
                        .maxRepeats(3)
                        .operation(new GetElements.Builder()
                                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(operation, "This example will run a GetWalks operation with 3 hops");
    }

    public void runUntilAnEndResultIsFound() {
        // ---------------------------------------------------------
        final While<Iterable<EntityId>, CloseableIterable<? extends EntityId>> operation = new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
                .input(Lists.newArrayList(new EntitySeed(1)))
                .conditional(new Not<>(new CollectionContains(new EntitySeed(7))), new ToSet<>())
                .maxRepeats(20)
                .operation(new GetAdjacentIds.Builder()
                        .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(operation, "This example will keep running GetAdjacentIds until the results contain a vertex with value 7");
    }

    public void runGetWalksUntilAnEndResultIsFound() {
        // ---------------------------------------------------------
        final GetWalks operation = new Builder()
                .input(new EntitySeed(1))
                .operations(new While.Builder<Iterable<EntityId>, CloseableIterable<? extends EntityId>>()
                        .conditional(new Not<>(new CollectionContains(7)), new ToSet<>())
                        .maxRepeats(20)
                        .operation(new GetElements.Builder()
                                .inOutType(IncludeIncomingOutgoingType.OUTGOING)
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(operation, "This example will walk around the graph until a vertex with value 7 is found");
    }
}
