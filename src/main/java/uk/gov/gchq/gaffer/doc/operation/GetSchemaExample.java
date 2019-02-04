/*
 * Copyright 2017-2019 Crown Copyright
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

import uk.gov.gchq.gaffer.store.operation.GetSchema;

public class GetSchemaExample extends OperationExample {

    public static void main(final String[] args) {
        new GetSchemaExample().runAndPrint();
    }

    public GetSchemaExample() {
        super(GetSchema.class);
    }

    @Override
    protected void runExamples() {
        getFullSchema();
        getCompactSchema();
    }

    public void getFullSchema() {
        // ---------------------------------------------------------
        final GetSchema operation = new GetSchema();
        // ---------------------------------------------------------

        runExample(operation, "This operation defaults the compact field to false, " +
                "thereby returning the full Schema.");
    }

    public void getCompactSchema() {
        // ---------------------------------------------------------
        final GetSchema operation = new GetSchema.Builder()
                .compact(true)
                .build();
        // ---------------------------------------------------------

        runExample(operation, "This operation will retrieve the compact Schema from the store, " +
                "rather than the full schema.");
    }
}
