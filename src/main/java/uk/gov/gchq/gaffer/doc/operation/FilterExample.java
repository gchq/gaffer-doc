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

import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.impl.function.Filter;
import uk.gov.gchq.koryphe.impl.predicate.IsLessThan;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

import java.util.HashMap;
import java.util.Map;

public class FilterExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new FilterExample().run();
    }

    public FilterExample() {
        super(Filter.class);
    }

    @Override
    public void runExamples() {
        filterEdgesByCount();
        filterByElementTypeAndCount();
        filterEntitesByGroupAndCount();
        filterUsingMap();
    }

    public void filterEdgesByCount() {
        // ---------------------------------------------------------
        final NamedOperation<Iterable<Element>, Iterable<? extends Element>> namedOp = new NamedOperation<>();
        namedOp.setOperationName("customOperation");

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(namedOp)
                .then(new Filter.Builder()
                        .globalEdges(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(2))
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(opChain, null);
    }

    public void filterByElementTypeAndCount() {
        // ---------------------------------------------------------
        final NamedOperation<Iterable<? extends Element>, Iterable<? extends Element>> namedOp = new NamedOperation<>();
        namedOp.setOperationName("testOp");

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(namedOp)
                .then(new Filter.Builder()
                        .globalElements(new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsMoreThan(1))
                                .build())
                        .edge("shortEdge")
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(opChain, "This first selects any elements that have a "
                + "count of more than 1, and then further selects only edges "
                + "that are in the group of \"shortEdge\".");
    }

    public void filterEntitesByGroupAndCount() {
        final Edge testEdge = new Edge.Builder()
                .source("src")
                .dest("dest")
                .build();
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<Edge, CloseableIterable<Element>>()
                        .name("path")
                        .input(testEdge)
                        .build())
                .then(new Filter.Builder()
                        .entity("entity", new ElementFilter.Builder()
                                .select("count")
                                .execute(new IsLessThan(3))
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(opChain, "Here we select all entities in the group of "
                + "\"entity\", then selecting only those with a count of less than 3.");
    }

    public void filterUsingMap() {
        // ---------------------------------------------------------
        final Map<String, ElementFilter> edges = new HashMap<>();
        edges.put("road", new ElementFilter.Builder()
                .select("count")
                .execute(new IsLessThan(3))
                .build());
        edges.put("motorway", new ElementFilter.Builder()
                .select("count")
                .execute(new IsMoreThan(10))
                .build());

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<Element, CloseableIterable<Element>>()
                        .name("path")
                        .build())
                .then(new Filter.Builder()
                        .edges(edges)
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(opChain, "A map can be utilised to define how each group "
                + "of a particular element type should be filtered - here, the "
                + "\"road\" group by an IsLessThan(3) predicate, and the \"motorway\" "
                + "group is filtered by an IsMoreThan(10) predicate.");
    }
}
