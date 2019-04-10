/*
 * Copyright 2019 Crown Copyright
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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat;

public class CollectionConcatExample extends BinaryOperatorExample {

    public CollectionConcatExample() {
        super(CollectionConcat.class, "Concatenates two collections together");
    }

    public static void main(final String[] args) {
        new CollectionConcatExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        collectionConcat();
    }

    private void collectionConcat() {
        // ---------------------------------------------------------
        final CollectionConcat collectionConcat = new CollectionConcat();
        // ---------------------------------------------------------

        runExample(collectionConcat, null,
                new Pair(Lists.newArrayList("test1"), Lists.newArrayList("test2", "test3")),
                new Pair(Lists.newArrayList(1), Lists.newArrayList("test2", "test3")),
                new Pair(Lists.newArrayList(), Lists.newArrayList("abc", "cde")),
                new Pair(Lists.newArrayList("test1"), null),
                new Pair(Sets.newHashSet("a", "b"), Sets.newHashSet("b", "c"))
        );
    }
}
