/*
 * Copyright 2016-2018 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.ToString;

public class ToStringExample extends FunctionExample {
    public static void main(final String[] args) {
        new ToStringExample().runAndPrint();
    }

    public ToStringExample() {
        super(ToString.class, "toString is simply called on each input. If the input is null, null is returned.");
    }

    @Override
    public void runExamples() {
        objectToString();
    }

    public void objectToString() {
        // ---------------------------------------------------------
        final ToString function = new ToString();
        // ---------------------------------------------------------

        runExample(function,
                null,
                1, 2.5, "abc", null);
    }
}
