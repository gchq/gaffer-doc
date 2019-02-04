/*
 * Copyright 2018-2019 Crown Copyright
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

import uk.gov.gchq.gaffer.named.view.GetAllNamedViews;
import uk.gov.gchq.gaffer.operation.OperationException;

public class GetAllNamedViewsExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new GetAllNamedViewsExample().runAndPrint();
    }

    public GetAllNamedViewsExample() {
        super(GetAllNamedViews.class, "See [NamedViews](../developer-guide/namedviews.md) for information on configuring NamedViews for your Gaffer graph, along with working examples.");
    }

    @Override
    protected void runExamples() {
        getAllNamedViews();
    }

    public void getAllNamedViews() {
        // ---------------------------------------------------------
        final GetAllNamedViews op = new GetAllNamedViews();
        // ---------------------------------------------------------
        showExample(op, null);
    }
}
