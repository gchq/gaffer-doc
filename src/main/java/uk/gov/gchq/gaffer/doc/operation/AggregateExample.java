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
package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.data.element.function.ElementAggregator;
import uk.gov.gchq.gaffer.operation.impl.function.Aggregate;
import uk.gov.gchq.gaffer.operation.util.AggregatePair;
import uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat;

public class AggregateExample extends OperationExample {

    public static void main(final String[] args) {
        new AggregateExample().runAndPrint();
    }

    public AggregateExample() {
        super(Aggregate.class, "The Aggregate operation would normally be used in an Operation Chain to aggregate the results of a previous operation.");
    }

    @Override
    public void runExamples() {
        simpleAggregateElementsExample();
        aggregateOnlyEdgesOfTypeEdgeWithATransientPropertyAndProvidedAggregator();
    }

    public void simpleAggregateElementsExample() {
        // ---------------------------------------------------------
        final Aggregate aggregate = new Aggregate();
        // ---------------------------------------------------------

        showExample(aggregate, null);
    }

    public void aggregateOnlyEdgesOfTypeEdgeWithATransientPropertyAndProvidedAggregator() {
        // ---------------------------------------------------------
        final String[] groupBy = {};
        final Aggregate aggregate = new Aggregate.Builder()
                .edge("edge", new AggregatePair(
                        groupBy,
                        new ElementAggregator.Builder()
                                .select("transientProperty1")
                                .execute(new StringConcat())
                                .build()))
                .build();
        // ---------------------------------------------------------

        showExample(aggregate, "The groupBy has been set to an empty array. This will override the groupBy value in the schema.");
    }
}
