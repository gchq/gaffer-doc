/*
 * Copyright 2017-2018 Crown Copyright
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


import com.google.common.collect.Lists;

import uk.gov.gchq.koryphe.impl.function.IterableConcat;

import java.util.ArrayList;
import java.util.List;

public class IterableConcatExample extends FunctionExample {
    public static void main(final String[] args) {
        new IterableConcatExample().runAndPrint();
    }

    public IterableConcatExample() {
        super(IterableConcat.class, "For a given Iterable of Iterables, an IterableConcat will essentially perform a FlatMap on the input, by concatenating each of the nested iterables into a single flattened iterable.");
    }

    @Override
    protected void runExamples() {
        concatenateNestedIterables();
    }

    public void concatenateNestedIterables() {
        // ---------------------------------------------------------
        final IterableConcat<Integer> function = new IterableConcat<>();
        // ---------------------------------------------------------

        final List<Integer> aa = Lists.newArrayList(2, 3, 5);
        final List<Integer> ab = Lists.newArrayList(7, 11, 13);
        final List<Integer> ac = Lists.newArrayList(17, 19, 23);
        final List<List<Integer>> input1 = Lists.newArrayList(aa, ab, ac);

        final List<Integer> ba = Lists.newArrayList(29, 31, 37);
        final List<List<Integer>> input2 = new ArrayList<>();
        input2.add(ba);

        final List<List<Integer>> input3 = Lists.newArrayList(aa, ab, null);

        runExample(function,
                null,
                input1,
                input2,
                input3,
                null);
    }
}
