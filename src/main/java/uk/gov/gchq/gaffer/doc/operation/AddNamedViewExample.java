/*
 * Copyright 2018 Crown Copyright
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

import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewParameterDetail;
import uk.gov.gchq.gaffer.named.view.AddNamedView;
import uk.gov.gchq.gaffer.operation.OperationException;

import java.util.Map;

public class AddNamedViewExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new AddNamedViewExample().runAndPrint();
    }

    public AddNamedViewExample() {
        super(AddNamedView.class, "See [NamedViews](../developer-guide/namedviews.md) for information on configuring NamedViews for your Gaffer graph, along with working examples..");
    }

    @Override
    protected void runExamples() {
        addNamedView();
    }

    public void addNamedView() {
        // ---------------------------------------------------------
        final ViewParameterDetail param = new ViewParameterDetail.Builder()
                .defaultValue(1L)
                .description("Limit param")
                .valueClass(Long.class)
                .build();
        final Map<String, ViewParameterDetail> paramMap = Maps.newHashMap();
        paramMap.put("param1", param);

        final AddNamedView op = new AddNamedView.Builder()
                .name("testNamedView")
                .description("example test NamedView")
                .overwrite(true)
                .view(new View.Builder().edge("testEdge").build())
                .parameters(paramMap)
                .build();

        showExample(op, null);
        // ---------------------------------------------------------
    }
}
