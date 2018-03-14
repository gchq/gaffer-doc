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


import com.google.common.collect.Maps;

import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.data.element.id.EntityId;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.named.operation.AddNamedOperation;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.named.operation.ParameterDetail;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.GetWalks;
import uk.gov.gchq.gaffer.operation.impl.If;
import uk.gov.gchq.gaffer.operation.impl.Limit;
import uk.gov.gchq.gaffer.operation.impl.Map;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.operation.util.Conditional;
import uk.gov.gchq.koryphe.function.KorypheFunction;
import uk.gov.gchq.koryphe.impl.function.ExtractValue;
import uk.gov.gchq.koryphe.impl.function.FirstItem;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;
import uk.gov.gchq.koryphe.impl.predicate.IsShorterThan;
import uk.gov.gchq.koryphe.iterable.CloseableIterable;

import java.util.HashMap;

public class IfExample extends OperationExample {

    public static void main(final String[] args) {
        new IfExample().runAndPrint();
    }

    public IfExample() {
        super(If.class, "", true);
    }

    @Override
    protected void runExamples() {
//        conditionallyGetElementsOrLimitCurrentResults();
//        fullExample();
        namedOperationContainingIfOperationWithParameter();
    }

    public void conditionallyGetElementsOrLimitCurrentResults() {
        // ---------------------------------------------------------
        final OperationChain<Object> opChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed("2"))
                        .build())
                .then(new If.Builder<>()
                        .conditional(new Conditional(new IsShorterThan(5)))
                        .then(new GetElements())
                        .otherwise(new Limit<>(5))
                        .build())
                .build();
        // ---------------------------------------------------------

        runExampleNoResult(opChain, "This example will take the vertices adjacent to the Entity with id 2," +
                "and if there are fewer than 5 results, they will be passed into a GetElements operation." +
                "Otherwise, there will simply only be 5 results returned.");
    }

    public void fullExample() {
        // ---------------------------------------------------------
        final OperationChain<Object> opChain = new OperationChain.Builder()
                .first(new OperationChain.Builder()
                        .first(new GetElements.Builder()
                                .input("M32:1")
                                .view(new View.Builder()
                                        .entity("JunctionUse", new ViewElementDefinition.Builder()
                                                .preAggregationFilter(new ElementFilter.Builder()
                                                        .select("count")
                                                        .execute(new IsMoreThan(10000L))
                                                        .build())
                                                .groupBy()
                                                .build())
                                        .build())
                                .build())
                        .build())
                .then(new If.Builder<>()
                        .conditional(new Conditional(new IsMoreThan(5L, false),
                                new OperationChain.Builder()
                                        .first(new Map.Builder<>()
                                                .first(new FirstItem<>())
                                                .then(new ExtractProperty("countByVehicleType"))
                                                .then(new ExtractValue<>("PC"))
                                                .build())
                                        .build()))
                        .then(new OperationChain.Builder()
                                .first(new GetWalks.Builder()
                                        .operations(new GetAdjacentIds(),
                                                new GetAdjacentIds())
                                        .resultsLimit(5)
                                        .build())
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(opChain, "This example will find busy junctions,");
    }

    public static class ExtractProperty extends KorypheFunction<Element, Object> {
        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        private String name;

        public ExtractProperty() {
        }

        public ExtractProperty(final String name) {
            this.name = name;
        }

        @Override
        public Object apply(final Element element) {
            return null != element ? element.getProperty(name) : null;
        }
    }

    public CloseableIterable<? extends Element> namedOperationContainingIfOperationWithParameter() {
        // ---------------------------------------------------------
        final String opChainString = "{" +
                "    \"operations\" : [ {" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"," +
                "      \"input\" : {" +
                "        \"class\" : \"uk.gov.gchq.gaffer.operation.data.EntitySeed\"," +
                "        \"vertex\" : 6" +
                "      }," +
                "      \"includeIncomingOutGoing\" : \"BOTH\"" +
                "    }, {" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.If\"," +
                "      \"condition\" : \"${enableFiltering}\"," +
                "      \"then\" : {" +
                "        \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\"," +
                "        \"view\" : {" +
                "          \"entities\" : {" +
                "            \"entity\" : {" +
                "              \"preAggregationFilterFunctions\" : {" +
                "                \"selection\" : [ \"count\" ]," +
                "                \"predicate\" : {" +
                "                  \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsLessThan\"," +
                "                  \"value\" : 10," +
                "                  \"orEqualTo\" : true" +
                "                }" +
                "              }" +
                "            }" +
                "          }" +
                "        }" +
                "      }" +
                "    },  {" +
                "      \"class\" : \"uk.gov.gchq.operation.impl.get.GetElements\"" +
                "    }" +
                "  ]" +
                "}";

        final ParameterDetail param = new ParameterDetail.Builder()
                .defaultValue(true)
                .description("Flag for enabling filtering")
                .valueClass(boolean.class)
                .build();
        final java.util.Map<String, ParameterDetail> parameterMap = new HashMap<>();
        parameterMap.put("enableFiltering", param);

        final AddNamedOperation operation = new AddNamedOperation.Builder()
                .operationChain(opChainString)
                .description("2 hop query with optional filtering by count")
                .name("2-hop-with-optional-filtering")
                .readAccessRoles("read-user")
                .writeAccessRoles("write-user")
                .parameters(parameterMap)
                .overwrite()
                .score(4)
                .build();

        final java.util.Map<String, Object> parameterValues = Maps.newHashMap();
        parameterValues.put("enableFiltering", true);

        final NamedOperation<EntityId, CloseableIterable<? extends Element>> namedOp =
                new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                        .name("2-hop-with-optional-filtering")
                        .parameters(parameterValues)
                        .build();
        // ---------------------------------------------------------

        runExampleNoResult(operation, null);
        return runExample(namedOp, null);
    }
}
