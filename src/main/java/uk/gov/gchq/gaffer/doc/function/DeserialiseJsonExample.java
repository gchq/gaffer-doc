/*
 * Copyright 2019 Crown Copyright
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

package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.koryphe.impl.function.DeserialiseJson;

public class DeserialiseJsonExample extends FunctionExample {
    public static void main(final String[] args) {
        new DeserialiseJsonExample().runAndPrint();
    }

    public DeserialiseJsonExample() {
        super(uk.gov.gchq.koryphe.impl.function.DeserialiseJson.class);
    }

    @Override
    protected void runExamples() {
        parseJson();
    }

    private void parseJson() {
        // ---------------------------------------------------------
        final DeserialiseJson function = new DeserialiseJson();
        // ---------------------------------------------------------

        final String input = "{\"elements\": [{\"value\": \"value1\"}, {\"value\": \"value2\"}]}";
        runExample(function, null, input);
    }

}
