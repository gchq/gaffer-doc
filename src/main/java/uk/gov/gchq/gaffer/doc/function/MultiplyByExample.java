/*
 * Copyright 2016-2019 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.MultiplyBy;

public class MultiplyByExample extends FunctionExample {
    public static void main(final String[] args) {
        new MultiplyByExample().runAndPrint();
    }

    public MultiplyByExample() {
        super(MultiplyBy.class, "Multiply the input integer by the provide number.");
    }

    @Override
    public void runExamples() {
        multiplyBy2();
    }

    public void multiplyBy2() {
        // ---------------------------------------------------------
        final MultiplyBy function = new MultiplyBy(2);
        // ---------------------------------------------------------

        runExample(function,
                null,
                6, 5, null, 6.1
        );
    }
}
