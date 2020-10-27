/*
 * Copyright 2019-2020 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.Increment;

public class IncrementExample extends FunctionExample {
    public static void main(final String[] args) {
        new IncrementExample().runAndPrint();
    }

    public IncrementExample() {
        super(uk.gov.gchq.koryphe.impl.function.Increment.class);
    }

    @Override
    protected void runExamples() {
        addToInt();
        addToDouble();
        addToFloat();
        addToLong();
    }

    private void addToInt() {
        // ---------------------------------------------------------
        final Increment increment = new Increment(3);
        // ---------------------------------------------------------

        runExample(increment, " returned value type will match the input type int", 2, 2.0d, 2.0f, 2L);
    }

    private void addToDouble() {
        // ---------------------------------------------------------
        final Increment increment = new Increment(3.0);
        // ---------------------------------------------------------

        runExample(increment, " returned value type will match the input type double", 2, 2.0d, 2.0f, 2L, "33", "three", null);
    }

    private void addToFloat() {
        // ---------------------------------------------------------
        final Increment increment = new Increment(3.0f);
        // ---------------------------------------------------------

        runExample(increment, " returned value type will match the input type float", 2, 2.0d, 2.0f, 2L, "33", "three", null);
    }

    private void addToLong() {
        // ---------------------------------------------------------
        final Increment increment = new Increment(3L);
        // ---------------------------------------------------------

        runExample(increment, " returned value type will match the input type long", 2, 2.0d, 2.0f, 2L, "33", "three", null);
    }
}
