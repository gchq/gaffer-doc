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

import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.id.EntityId;
import uk.gov.gchq.gaffer.named.operation.AddNamedOperation;
import uk.gov.gchq.gaffer.named.operation.NamedOperation;
import uk.gov.gchq.gaffer.named.operation.ParameterDetail;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.If;
import uk.gov.gchq.gaffer.operation.impl.Limit;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.koryphe.impl.predicate.IsShorterThan;

public class IfExample extends OperationExample {

    public static void main(final String[] args) {
        new IfExample().runAndPrint();
    }

    public IfExample() {
        super(If.class, "Examples for the If Operation. " +
                "These examples use a modified, more complex graph.", true);
    }

    @Override
    protected void runExamples() {
        conditionallyGetElementsOrLimitCurrentResults();
        addNamedOperationContainingIfOperationWithParameter();
        runParameterisedNamedOperationContainingIfOperation();
    }

    public void conditionallyGetElementsOrLimitCurrentResults() {
        // ---------------------------------------------------------
        final OperationChain<Object> opChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed(2))
                        .build())
                .then(new If.Builder<>()
                        .conditional(new IsShorterThan(5))
                        .then(new OperationChain.Builder()
                                .first(new GetElements())
                                .then(new Limit<>(5))
                                .build())
                        .otherwise(new Limit<>(5))
                        .build())
                .build();
        // ---------------------------------------------------------

        runExample(opChain, "This example will take the vertices adjacent to the Entity with id 2, " +
                "and if there are fewer than 5 results, they will be passed into a GetElements operation." +
                "Otherwise, there will simply only be 5 results returned.");
    }

    public void addNamedOperationContainingIfOperationWithParameter() {
        // ---------------------------------------------------------
        final String opChainString = "{" +
                "\"operations\" : [ {\n" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds\"\n" +
                "    }, {\n" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.If\",\n" +
                "      \"condition\" : \"${enableFiltering}\",\n" +
                "      \"then\" : {\n" +
                "        \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\",\n" +
                "        \"view\" : {\n" +
                "          \"entities\" : {\n" +
                "            \"entity\" : {\n" +
                "              \"preAggregationFilterFunctions\" : [ {\n" +
                "                \"selection\" : [ \"count\" ],\n" +
                "                \"predicate\" : {\n" +
                "                  \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsLessThan\",\n" +
                "                  \"orEqualTo\" : true,\n" +
                "                  \"value\" : 10\n" +
                "                }\n" +
                "              } ]\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }, {\n" +
                "      \"class\" : \"uk.gov.gchq.gaffer.operation.impl.get.GetElements\"\n" +
                "    } ]" +
                "}";

        ParameterDetail param = new ParameterDetail.Builder()
                .defaultValue(true)
                .description("Flag for enabling filtering")
                .valueClass(boolean.class)
                .build();
        java.util.Map<String, ParameterDetail> parameterMap = Maps.newHashMap();
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
        // ---------------------------------------------------------
        runExampleNoResult(operation, "Here we create and add a NamedOperation, " +
                "containing an If operation with a parameter. " +
                "This parameter can be configured so that the optional GetElements with the filter can be executed, " +
                "otherwise it will just continue to the next GetElements.");
    }

    public CloseableIterable<? extends Element> runParameterisedNamedOperationContainingIfOperation() {
        // ---------------------------------------------------------
        final java.util.Map<String, Object> parameterValues = Maps.newHashMap();
        parameterValues.put("enableFiltering", true);

        final NamedOperation<EntityId, CloseableIterable<? extends Element>> namedOp =
                new NamedOperation.Builder<EntityId, CloseableIterable<? extends Element>>()
                        .name("2-hop-with-optional-filtering")
                        .input(new EntitySeed(6))
                        .parameters(parameterValues)
                        .build();
        // ---------------------------------------------------------
        return runExample(namedOp, "This example then runs the NamedOperation, " +
                "providing both the input, and the value of the parameter via a Map.");

    }
}
