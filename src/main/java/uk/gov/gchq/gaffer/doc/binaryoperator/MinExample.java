/*
 * Copyright 2018-2020 Crown Copyright
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

package uk.gov.gchq.gaffer.doc.binaryoperator;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.koryphe.impl.binaryoperator.Min;

public class MinExample extends BinaryOperatorExample {
    public static void main(final String[] args) {
        new MinExample().runAndPrint();
    }

    public MinExample() {
        super(Min.class, "Returns the min value.");
    }

    @Override
    protected void runExamples() {
        min();
    }

    private void min() {
        // ---------------------------------------------------------
        final Min function = new Min();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Pair<>(5, 6), new Pair<>("inputString", "anotherInputString"), new Pair<>(null, 1)
        );
    }
}
