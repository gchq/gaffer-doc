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
package uk.gov.gchq.gaffer.doc.function;

import uk.gov.gchq.koryphe.impl.function.DictionaryLookup;

import java.util.HashMap;

public class DictionaryLookupExample extends FunctionExample {

    public DictionaryLookupExample() {
        super(DictionaryLookup.class, "looks up a value in a Map");
    }

    @Override
    protected void runExamples() {
        dictionaryLookupExample();
    }

    public void dictionaryLookupExample() {
        final HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // ---------------------------------------------------------
        final DictionaryLookup<Integer, String> dictionaryLookup = new DictionaryLookup<>(map);
        // ---------------------------------------------------------

        runExample(dictionaryLookup, null, 1, null, 4, 2L);
    }
}
