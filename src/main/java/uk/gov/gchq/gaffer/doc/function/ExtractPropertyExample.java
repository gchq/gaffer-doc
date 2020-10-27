/*
 * Copyright 2017-2020 Crown Copyright
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

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.element.function.ExtractProperty;

public class ExtractPropertyExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractPropertyExample().runAndPrint();
    }

    public ExtractPropertyExample() {
        super(ExtractProperty.class);
    }

    @Override
    protected void runExamples() {
        extractPropertyFromElement();
    }

    public void extractPropertyFromElement() {
        // ---------------------------------------------------------
        final ExtractProperty function = new ExtractProperty("prop1");
        // ---------------------------------------------------------

        runExample(function,
                "If present, the function will extract the value of the specified property, otherwise returning null.",
                new Edge.Builder()
                        .group("edge")
                        .source("src")
                        .dest("dest")
                        .property("prop1", 3)
                        .property("prop2", "test")
                        .build(),
                new Entity.Builder()
                        .group("entity")
                        .vertex("vertex")
                        .property("prop1", 12)
                        .property("prop2", 2)
                        .property("prop3", "test")
                        .build(),
                new Edge.Builder()
                .build());
    }
}
