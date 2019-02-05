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

import uk.gov.gchq.gaffer.store.operation.GetTraits;

public class GetTraitsExample extends OperationExample {

    public static void main(final String[] args) {
        new GetTraitsExample().runAndPrint();
    }

    public GetTraitsExample() {
        super(GetTraits.class, "Gets the traits of the current store.");
    }

    @Override
    protected void runExamples() {
        getAllTraits();
        getCurrentTraits();
    }

    public void getAllTraits() {
        // ---------------------------------------------------------
        final GetTraits operation = new GetTraits.Builder()
                .currentTraits(false)
                .build();
        // ---------------------------------------------------------

        runExample(operation, null);
    }

    public void getCurrentTraits() {
        // ---------------------------------------------------------
        final GetTraits operation = new GetTraits.Builder()
                .currentTraits(true)
                .build();
        // ---------------------------------------------------------

        runExample(operation, "This will only return traits that are applicable to your current schema. " +
                "This schema doesn't have a visibility property, so the VISIBILITY trait is not returned.");
    }
}
