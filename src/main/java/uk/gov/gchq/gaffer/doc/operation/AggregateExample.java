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
import uk.gov.gchq.gaffer.data.element.function.ElementAggregator;
import uk.gov.gchq.gaffer.data.element.function.ElementTransformer;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.function.Aggregate;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.operation.util.AggregatePair;
import uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat;
import uk.gov.gchq.koryphe.impl.binaryoperator.Sum;
import uk.gov.gchq.koryphe.impl.function.ToString;

import java.util.HashMap;
import java.util.Map;

public class AggregateExample extends OperationExample {

    public static void main(final String[] args) {
        new AggregateExample().run();
    }

    public AggregateExample() {
        super(Aggregate.class, "Note - these examples are used for demonstration purposes only " +
                "with the example dataset.");
    }

    @Override
    public void runExamples() {
        aggregateElementsAfterTransformToTransientProperty();
        aggregateWithDataInAggregatePair();
    }

    public Iterable<? extends Element> aggregateElementsAfterTransformToTransientProperty() {
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed(1))
                        .build())
                .then(new GetAdjacentIds.Builder()
                        .build())
                .then(new GetAdjacentIds.Builder()
                        .build())
                .then(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("edge", new ViewElementDefinition.Builder()
                                        .transientProperty("newProp", String.class)
                                        .transformer(new ElementTransformer.Builder()
                                                .select("count")
                                                .execute(new ToString())
                                                .project("newProp")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .then(new Aggregate.Builder()
                        .edge("edge", new AggregatePair(new ElementAggregator.Builder()
                                .select("newProp")
                                .execute(new StringConcat())
                                .build()))
                        .build())
                .build();
        // ---------------------------------------------------------

        return runExample(opChain, "This example demonstrates the aggregation of elements " +
                "after a transform to a transient property has been applied.");
    }

    public Iterable<? extends Element> aggregateWithDataInAggregatePair() {
        // ---------------------------------------------------------
        final Map<String, AggregatePair> edges = new HashMap<>();

        final AggregatePair aggregatePair = new AggregatePair(
                new String[]{},
                new ElementAggregator.Builder()
                        .select("count")
                        .execute(new Sum())
                        .build());

        edges.put("edge", aggregatePair);

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed(1))
                        .build())
                .then(new GetAdjacentIds.Builder()
                        .build())
                .then(new GetAdjacentIds.Builder()
                        .build())
                .then(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("edge")
                                .build())
                        .build())
                .then(new Aggregate.Builder()
                        .edges(edges)
                        .build())
                .build();
        // ---------------------------------------------------------

        return runExample(opChain, "In this example, we define the ElementAggregator in the " +
                "constructor of the AggregatePair. The pair is matched to an element group " +
                "upon which it will operate, in the map named \"edges\". This can be used to " +
                "aggregate many groups in different ways, in a relatively simple fashion. ");
    }
}
