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

import com.google.common.collect.Lists;

import uk.gov.gchq.koryphe.impl.function.IterableFilter;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;

public class IterableFilterExample extends FunctionExample {
    public static void main(final String[] args) {
        new IterableFunctionExample().runAndPrint();
    }

    public IterableFilterExample() {
        super(IterableFilter.class, "An IterableFilter applies a given predicate to each element in an Iterable and returns the filtered iterable");
    }

    @Override
    protected void runExamples() {
        iterableFilter();
    }

    public void iterableFilter() {
        // ---------------------------------------------------------
        final IterableFilter<Integer> function = new IterableFilter<>(new IsMoreThan(5));
        // ---------------------------------------------------------

        runExample(function,
                null,
                Lists.newArrayList(1, 2, 3),
                Lists.newArrayList(5, 10, 15),
                Lists.newArrayList(7, 9, 11),
                Lists.newArrayList(1, null, 3),
                null);
    }

}
