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
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.named.operation.AddNamedOperation;
import uk.gov.gchq.gaffer.named.operation.DeleteNamedOperation;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.function.Filter;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

public class FilterExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new FilterExample().run();
    }

    public FilterExample() {
        super(Filter.class);
    }

    @Override
    public void runExamples() {
        try {
            getGraph().execute(new AddNamedOperation.Builder()
                            .name("1-hop")
                            .operationChain(new OperationChain.Builder()
                                    .first(new GetElements())
                                    .build())
                            .build(),
                    createContext());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }

        allElementsWithACountMoreThan2();
        allEdgesOfTypeEdgeWithCountMoreThan2();

        try {
            getGraph().execute(new DeleteNamedOperation.Builder()
                            .name("1-hop")
                            .build(),
                    createContext());
        } catch (final OperationException e) {
            throw new RuntimeException(e);
        }
    }

    public void allElementsWithACountMoreThan2() {
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                        .name("1-hop")
                        .input(new EntitySeed(1))
                        .build())
                .then(new Filter.Builder()
                        .globalElements(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(opChain, "The filter will only return elements (Entities and Edges) with a count more than 2. The results show the Edge 1->4 that has a count of 1 has been removed.");
    }

    public void allEdgesOfTypeEdgeWithCountMoreThan2() {
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<EntitySeed, Iterable<? extends Element>>()
                        .name("1-hop")
                        .input(new EntitySeed(1))
                        .build())
                .then(new Filter.Builder()
                        .edge("edge", new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(opChain, "Similar to the previous example but this will only return Edges with group 'edge' that have a count more than 2.");
    }
}
