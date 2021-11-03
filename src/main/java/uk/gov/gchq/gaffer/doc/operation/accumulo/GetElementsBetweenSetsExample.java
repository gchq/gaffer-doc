/*
 * Copyright 2016-2020 Crown Copyright
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
package uk.gov.gchq.gaffer.doc.operation.accumulo;

import uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsBetweenSets;
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.doc.operation.OperationExample;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

public class GetElementsBetweenSetsExample extends OperationExample {
    public static void main(final String[] args) {
        new GetElementsBetweenSetsExample().runAndPrint();
    }

    public GetElementsBetweenSetsExample() {
        super(GetElementsBetweenSets.class);
    }

    @Override
    public void runExamples() {
        getElementsWithinSetOfVertices1And2And4();
        getElementsWithinSetOfVertices1And2And4WithCountGreaterThan2();
    }

    public void getElementsWithinSetOfVertices1And2And4() {
        // ---------------------------------------------------------
        final GetElementsBetweenSets operation = new GetElementsBetweenSets.Builder()
                .input(new EntitySeed(1))
                .inputB(new EntitySeed(2), new EntitySeed(4))
                .build();
        // ---------------------------------------------------------

        showExample(operation, null);
        print("The results are:\n");
        print("```");
        print("Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]");
        print("```");
    }

    public void getElementsWithinSetOfVertices1And2And4WithCountGreaterThan2() {
        // ---------------------------------------------------------
        final GetElementsBetweenSets operation = new GetElementsBetweenSets.Builder()
                .input(new EntitySeed(1))
                .inputB(new EntitySeed(2), new EntitySeed(4))
                .view(new View.Builder()
                        .entity("entity", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(2))
                                        .build())
                                .build())
                        .edge("edge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(2))
                                        .build())
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(operation, null);
        print("The results are:\n");
        print("```");
        print("Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]");
        print("```");
    }
}
