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

import uk.gov.gchq.koryphe.impl.function.DeserialiseXml;

public class DeserialiseXmlExample extends FunctionExample {
    public static void main(final String[] args) {
        new DeserialiseXmlExample().runAndPrint();
    }

    public DeserialiseXmlExample() {
        super(uk.gov.gchq.koryphe.impl.function.DeserialiseXml.class);
    }

    @Override
    protected void runExamples() {
        parseXML();
    }

    private void parseXML() {
        // ---------------------------------------------------------
        final DeserialiseXml function = new DeserialiseXml();
        // ---------------------------------------------------------

        runExample(function, null, "<element1>value</element1>",
                "<root><element1>value1</element1><element2>value2</element2></root>",
                "<root><element1><element2>value1</element2></element1><element1><element2>value2</element2></element1></root>");
    }
}
