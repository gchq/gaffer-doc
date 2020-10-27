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

import uk.gov.gchq.gaffer.named.view.DeleteNamedView;
import uk.gov.gchq.gaffer.operation.OperationException;

public class DeleteNamedViewExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new DeleteNamedViewExample().runAndPrint();
    }

    public DeleteNamedViewExample() {
        super(DeleteNamedView.class, "See [NamedViews](../developer-guide/namedviews.md) for information on configuring NamedViews for your Gaffer graph, along with working examples.");
    }

    @Override
    protected void runExamples() {
        deleteNamedView();
    }

    public void deleteNamedView() {
        // ---------------------------------------------------------
        final DeleteNamedView op = new DeleteNamedView.Builder()
                .name("testNamedView")
                .build();
        // ---------------------------------------------------------
        showExample(op, null);
    }
}
