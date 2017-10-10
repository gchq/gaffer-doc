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
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.data.element.id.EntityId;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.function.Filter;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.operation.impl.job.GetJobResults;
import uk.gov.gchq.koryphe.impl.predicate.IsLessThan;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation<Iterable<Element>, Iterable<? extends Element>>())
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

    public Iterable<? extends Element> filterByElementTypeAndCount() {
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation<Iterable<Element>, Iterable<? extends Element>>())
                .then(new Filter.Builder()
                      .globalElements(new ElementFilter.Builder()
                                      .select("count")
                                      .execute(new IsMoreThan(1))
                                      .build())
                      .edge("edge")
                      .build())
                .build();
        // ---------------------------------------------------------

        return runExample(opChain, "This first selects any elements that have a "
                + "count of more than 1, and then further selects only edges "
                + "that are in the group of \"edge\".");
    }

    public Iterable<? extends Element> filterEntitesByGroupAndCount() {
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<Entity, CloseableIterable<Element>>()
                        .name("path")
                        .input(new Entity.Builder().build())
                        .build())
                .then(new Filter.Builder()
                      .entity("entity", new ElementFilter.Builder()
                              .select("count")
                              .execute(new IsLessThan(3))
                              .build())
                      .build())
                .build();
        // ---------------------------------------------------------

        return runExample(opChain, "Here we select all entities in the group of "
        + "\"entity\", then selecting only those with a count of less than 3.");
    }

    public void filterUsingMap() {
        // ---------------------------------------------------------
        final Map<String, ElementFilter> edges = new HashMap<>();
        edges.put("edge", new ElementFilter.Builder()
                  .select("count")
                  .execute(new IsLessThan(3))
                  .build());
        edges.put("road", new ElementFilter.Builder()
                  .select("count")
                  .execute(new IsMoreThan(10))
                  .build());

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<Element, CloseableIterable<Element>>()
                        .name("path")
                        .input(new Edge.Builder().build())
                        .build())
                .then(new Filter.Builder()
                      .edges(edges)
                      .build())
                .build();
        // ---------------------------------------------------------

        showExample(opChain, "A map can be utilised to define how each group "
        + "of a particular element type should be filtered - here, the "
        + "\"edge\" group by an IsLessThan(3) predicate, and the \"road\" "
        + "group is filtered by an IsMoreThan(10) predicate.");
    }
}
