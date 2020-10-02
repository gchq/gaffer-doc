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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import uk.gov.gchq.koryphe.impl.function.Longest;
import uk.gov.gchq.koryphe.tuple.n.Tuple2;

public class LongestExample extends FunctionExample {

    public static void main(final String[] args) {
        new LongestExample().runAndPrint();
    }

    public LongestExample() {
        super(Longest.class);
    }

    @Override
    protected void runExamples() {
        getLongest();
    }

    public void getLongest() {
        // ---------------------------------------------------------
        final Longest function = new Longest();
        // ---------------------------------------------------------

        runExample(function, null,
                new Tuple2<>("smaller", "long string"),
                new Tuple2<>(Lists.newArrayList(1, 2), Sets.newHashSet(1.5)),
                new Tuple2<>(null, "value"),
                null
        );
    }
}
