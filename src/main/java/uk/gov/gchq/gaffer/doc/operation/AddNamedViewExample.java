/*
 * Copyright 2018-2020 Crown Copyright
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

import uk.gov.gchq.gaffer.data.element.function.ElementFilter;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewParameterDetail;
import uk.gov.gchq.gaffer.named.view.AddNamedView;
import uk.gov.gchq.gaffer.named.view.DeleteNamedView;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

import java.util.Map;

public class AddNamedViewExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new AddNamedViewExample().runAndPrint();
    }

    public AddNamedViewExample() {
        super(AddNamedView.class, "See [NamedViews](../developer-guide/namedviews.md) for information on configuring NamedViews for your Gaffer graph, along with working examples.");
    }

    @Override
    protected void runExamples() {
        addNamedView();
        addNamedViewWithParameter();
    }

    public void addNamedView() {
        // ---------------------------------------------------------
        final AddNamedView op = new AddNamedView.Builder()
                .name("isMoreThan10")
                .description("example test NamedView")
                .overwrite(true)
                .view(new View.Builder()
                        .edge("testEdge", new ViewElementDefinition.Builder()
                                .preAggregationFilter(new ElementFilter.Builder()
                                        .select("count")
                                        .execute(new IsMoreThan(10))
                                        .build())
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------
        runExampleNoResult(op, null);

        try {
            getGraph().execute(new DeleteNamedView
                            .Builder()
                            .name("isMoreThan10").build(),
                    createContext());
        } catch (final OperationException e) {
            throw new RuntimeException("Unable to delete named view: isMoreThan", e);
        }
    }

    public void addNamedViewWithParameter() {
        // ---------------------------------------------------------
        final String viewJson = "{\"edges\" : {\n" +
                "  \"testEdge\" : {\n" +
                "    \"preAggregationFilterFunctions\" : [ {\n" +
                "      \"selection\" : [ \"count\" ],\n" +
                "      \"predicate\" : {\n" +
                "        \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsMoreThan\",\n" +
                "        \"orEqualTo\" : false,\n" +
                "        \"value\" : \"${countThreshold}\"\n" +
                "      }\n" +
                "    } ]\n" +
                "  }\n" +
                "}}";
        final ViewParameterDetail param = new ViewParameterDetail.Builder()
                .defaultValue(1L)
                .description("count threshold")
                .valueClass(Long.class)
                .build();
        final Map<String, ViewParameterDetail> paramMap = Maps.newHashMap();
        paramMap.put("countThreshold", param);

        final AddNamedView op = new AddNamedView.Builder()
                .name("isMoreThan")
                .description("example test NamedView")
                .overwrite(true)
                .view(viewJson)
                .parameters(paramMap)
                .writeAccessRoles("auth1")
                .build();
        // ---------------------------------------------------------
        runExampleNoResult(op, null);

        try {
            getGraph().execute(new DeleteNamedView
                            .Builder()
                            .name("isMoreThan").build(),
                    createContext());
        } catch (final OperationException e) {
            throw new RuntimeException("Unable to delete named view: isMoreThan", e);
        }
    }
}

