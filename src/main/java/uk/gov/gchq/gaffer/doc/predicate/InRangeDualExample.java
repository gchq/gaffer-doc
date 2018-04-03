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
package uk.gov.gchq.gaffer.doc.predicate;


import uk.gov.gchq.koryphe.impl.predicate.range.InRangeDual;
import uk.gov.gchq.koryphe.tuple.n.Tuple2;

public class InRangeDualExample extends PredicateExample {
    public static void main(final String[] args) {
        new InRangeDualExample().runAndPrint();
    }

    public InRangeDualExample() {
        super(InRangeDual.class, "The predicate tests 2 inputs (a start and an end) are within a defined range.");
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
        final InRangeDual function = new InRangeDual.Builder<Long>()
                .start(5L)
                .end(10L)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(-5L, -1L),
                new Tuple2<>(1L, 6L),
                new Tuple2<>(6L, 6L),
                new Tuple2<>(6L, 7L),
                new Tuple2<>(6L, 10L),
                new Tuple2<>(10L, 20L),
                new Tuple2<>(6, 7),
                new Tuple2<>("6", "7"),
                new Tuple2<>(null, null));
    }

    public void inLongRange5To10Exclusive() {
        // ---------------------------------------------------------
        final InRangeDual function = new InRangeDual.Builder<Long>()
                .start(5L)
                .end(10L)
                .startInclusive(false)
                .endInclusive(false)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(-5L, -1L),
                new Tuple2<>(1L, 6L),
                new Tuple2<>(6L, 6L),
                new Tuple2<>(6L, 7L),
                new Tuple2<>(6L, 10L),
                new Tuple2<>(10L, 20L),
                new Tuple2<>(6, 7),
                new Tuple2<>("5", "7"),
                new Tuple2<>(null, null));
    }

    public void inLongRangeLessThan10() {
        // ---------------------------------------------------------
        final InRangeDual function = new InRangeDual.Builder<Long>()
                .end(10L)
                .endInclusive(false)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                "If the start of the range is not specified then the start of the range is unbounded.",
                new Tuple2<>(-5L, -1L),
                new Tuple2<>(1L, 6L),
                new Tuple2<>(6L, 6L),
                new Tuple2<>(6L, 7L),
                new Tuple2<>(6L, 10L),
                new Tuple2<>(10L, 20L),
                new Tuple2<>(6, 7),
                new Tuple2<>("5", "7"),
                new Tuple2<>(null, null));
    }

    public void inIntegerRange5To10() {
        // ---------------------------------------------------------
        final InRangeDual function = new InRangeDual.Builder<Integer>()
                .start(5)
                .end(10)
                .build();
        // ---------------------------------------------------------

        runExample(function,
                null,
                new Tuple2<>(-5, -1),
                new Tuple2<>(1, 6),
                new Tuple2<>(6, 6),
                new Tuple2<>(6, 7),
                new Tuple2<>(6, 10),
                new Tuple2<>(10, 20),
                new Tuple2<>(6L, 7L),
                new Tuple2<>("6", "7"),
                new Tuple2<>(null, null));
    }

    public void inStringRangeBToD() {
        // ---------------------------------------------------------
        final InRangeDual function = new InRangeDual.Builder<String>()
                .start("B")
                .end("D")
                .build();
        // ---------------------------------------------------------

        runExample(
                function,
                null,
                new Tuple2<>("A", "B"),
                new Tuple2<>("B", "C"),
                new Tuple2<>("C", "D"),
                new Tuple2<>("b", "c"),
                new Tuple2<>(1, 2),
                new Tuple2<>(null, null)
        );
    }
}
