/*
 * Copyright 2020 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.MultiplyLongBy;

public class MultiplyLongByExample extends FunctionExample {
    public static void main(final String[] args) {
        new MultiplyLongByExample().runAndPrint();
    }

    public MultiplyLongByExample() {
        super(MultiplyLongBy.class, "Multiply the input Long by the provide number.");
    }

    @Override
    public void runExamples() {
        MultiplyLongBy2();
    }

    public void MultiplyLongBy2() {
        // ---------------------------------------------------------
        final MultiplyLongBy function = new MultiplyLongBy(2L);
        // ---------------------------------------------------------

        runExample(function,
                null,
                6L, 5L, null, 6.1
        );
    }
}
