/*
 * Copyright 2017 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.DivideBy;
import uk.gov.gchq.koryphe.impl.function.IterableFunction;
import uk.gov.gchq.koryphe.impl.function.MultiplyBy;
import uk.gov.gchq.koryphe.tuple.n.Tuple2;

import java.util.Arrays;

public class IterableFunctionExample extends FunctionExample {
    public static void main(final String[] args) {
        new IterableFunctionExample().runAndPrint();
    }

    public IterableFunctionExample() {
        super(IterableFunction.class, "An IterableFunction is useful for applying a provided function, or functions, to each entry of a supplied Iterable.");
    }

    @Override
    protected void runExamples() {
        applyFunctionIteratively();
        applyMultipleFunctions();
    }

    public void applyMultipleFunctions() {
        // ---------------------------------------------------------
        final IterableFunction<Integer, Tuple2<Integer, Integer>> function = new IterableFunction.Builder<Integer>()
                .first(new MultiplyBy(3))
                .then(new DivideBy(2))
                .build();
        // ---------------------------------------------------------

        runExample(function,
                "Here we build a chain of functions using the IterableFunction's Builder, whereby the output of one function is the input to the next.",
                Arrays.asList(2, 4, 10),
                Arrays.asList(3, 9, 11));
    }

    public void applyFunctionIteratively() {
        // ---------------------------------------------------------
        final IterableFunction<Integer, Integer> function = new IterableFunction<>(new MultiplyBy(2));
        // ---------------------------------------------------------

        runExample(function,
                null,
                Arrays.asList(1, 2, 3),
                Arrays.asList(5, 10, 15),
                Arrays.asList(7, 9, 11));
    }
}
