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
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.function.ElementAggregator;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.function.Aggregate;
import uk.gov.gchq.gaffer.operation.util.AggregatePair;
import uk.gov.gchq.gaffer.store.schema.Schema;
import uk.gov.gchq.gaffer.store.schema.SchemaEdgeDefinition;
import uk.gov.gchq.gaffer.store.schema.SchemaEntityDefinition;
import uk.gov.gchq.koryphe.impl.binaryoperator.Max;
import uk.gov.gchq.koryphe.impl.binaryoperator.Sum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregateExample extends OperationExample {

    public static void main(final String[] args) {
        new AggregateExample().run();
    }

    public AggregateExample() {
        super(Aggregate.class);
    }

    @Override
    public void runExamples() {
        aggregateWithSchema();
        aggregateElementsInMultipleGroups();
        aggregateWithDataInAggregatePair();
    }

    public void aggregateWithSchema() {
        // ---------------------------------------------------------
        final Schema schema = new Schema.Builder()
                .edge("BasicEdge", new SchemaEdgeDefinition.Builder()
                        .aggregator(new ElementAggregator.Builder()
                                .select("count")
                                .execute(new Sum())
                                .build())
                        .groupBy("timestamp")
                        .build())
                .build();

        final List<Element> input = new ArrayList<>();
        final Map<String, AggregatePair> edges = new HashMap<>();

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<Element, CloseableIterable<Element>>()
                        .name("2-hop")
                        .input(input)
                        .build())
                .then(new Aggregate.Builder()
                        .edges(edges)
                        .build())
                .build();

        // ---------------------------------------------------------

        showExample(opChain, "For this example, we have one group of edges defined in the Schema, " +
                "with an aggregator and groupBy attached. The aggregator then operates on the output " +
                "iterable of the NamedOperation, itself in turn returning an iterable of aggregated " +
                "elements.");
    }

    public void aggregateElementsInMultipleGroups() {
        // ---------------------------------------------------------
        final Schema schema = new Schema.Builder()
                .edge("BasicEdge", new SchemaEdgeDefinition.Builder()
                        .aggregator(new ElementAggregator.Builder()
                                .select("count")
                                .execute(new Sum())
                                .build())
                        .groupBy("timestamp")
                        .build())
                .edge("BasicEdge2", new SchemaEdgeDefinition.Builder()
                        .aggregator(new ElementAggregator.Builder()
                                .select("turns")
                                .execute(new Max())
                                .build())
                        .groupBy("location")
                        .build())
                .build();

        final List<Element> input = new ArrayList<>();
        final Map<String, AggregatePair> edges = new HashMap<>();

        edges.put("BasicEdge", new AggregatePair());
        edges.put("BasicEdge2", new AggregatePair());

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<Element, CloseableIterable<Element>>()
                        .name("2-hop")
                        .input(input)
                        .build())
                .then(new Aggregate.Builder()
                        .edges(edges)
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(opChain, "Now we have two groups of edges defined, each with their own " +
                "groupBy and aggregators. The aggregate function will apply only the relevant " +
                "aggregators to the relevant groups.");
    }

    public void aggregateWithDataInAggregatePair() {
        // ---------------------------------------------------------
        final Schema schema = new Schema.Builder()
                .entity("BasicEntity", new SchemaEntityDefinition.Builder()
                        .build())
                .build();

        final List<Element> input = new ArrayList<>();
        final Map<String, AggregatePair> entities = new HashMap<>();
        final Map<String, String> options = new HashMap<>();

        final AggregatePair aggregatePair = new AggregatePair(
                new String[]{"timestamp"},
                new ElementAggregator.Builder()
                        .select("count")
                        .execute(new Sum())
                        .select("otherProperty")
                        .execute(new Max())
                        .build());

        entities.put("BasicEntity", aggregatePair);

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new NamedOperation.Builder<Element, Iterable<? extends Element>>()
                        .name("flowMeasure")
                        .input(input)
                        .options(options)
                        .build())
                .then(new Aggregate.Builder()
                        .entities(entities)
                        .build())
                .build();

        showExample(opChain, "In this example, we define both the groupBy property, and the " +
                "ElementAggregator in the constructor of the AggregatePair. The pair is matched " +
                "to an element group upon which it will operate, in the map named \"entities\".");
    }
}
