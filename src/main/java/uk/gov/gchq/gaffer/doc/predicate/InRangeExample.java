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
package uk.gov.gchq.gaffer.doc.predicate;


import uk.gov.gchq.koryphe.impl.predicate.range.InRange;

public class InRangeExample extends PredicateExample {
    public static void main(final String[] args) {
        new InRangeExample().runAndPrint();
    }

    public InRangeExample() {
        super(InRange.class);
    }

    @Override
    public void runExamples() {
        inLongRange5To10();
        inLongRange5To10Exclusive();
        inLongRangeLessThan10();
        inIntegerRange5To10();
        inStringRangeBToD();
    }

    public void inLongRange5To10() {
        // ---------------------------------------------------------
        final InRange function = new InRange.Builder<Long>()
                .start(5L)
                .end(10L)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                -5L, 1L, 5L, 7L, 10L, 20L, 7, "7", null);
    }

    public void inLongRange5To10Exclusive() {
        // ---------------------------------------------------------
        final InRange function = new InRange.Builder<Long>()
                .start(5L)
                .end(10L)
                .startInclusive(false)
                .endInclusive(false)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                -5L, 1L, 5L, 7L, 10L, 20L, 7, "7", null);
    }

    public void inLongRangeLessThan10() {
        // ---------------------------------------------------------
        final InRange function = new InRange.Builder<Long>()
                .end(10L)
                .endInclusive(false)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                "If the start of the range is not specified then the start of the range is unbounded.",
                -5L, 1L, 5L, 7L, 10L, 20L, 7, "7", null);
    }

    public void inIntegerRange5To10() {
        // ---------------------------------------------------------
        final InRange function = new InRange.Builder<Integer>()
                .start(5)
                .end(10)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                -5, 1, 5, 7, 10, 20, 7L, "7", null);
    }

    public void inStringRangeBToD() {
        // ---------------------------------------------------------
        final InRange function = new InRange.Builder<String>()
                .start("B")
                .end("D")
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                "A", "B", "C", "D", "c", 1, null);
    }
}
