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
package uk.gov.gchq.gaffer.doc.function;


import uk.gov.gchq.koryphe.function.FunctionMap;
import uk.gov.gchq.koryphe.impl.function.MultiplyBy;

import java.util.HashMap;
import java.util.Map;

public class FunctionMapExample extends FunctionExample {
    public static void main(final String[] args) {
        new FunctionMapExample().runAndPrint();
    }

    public FunctionMapExample() {
        super(FunctionMap.class, "Applies a function to all values in a map.");
    }

    @Override
    public void runExamples() {
        multiplyAllMapValuesBy10();
    }

    public void multiplyAllMapValuesBy10() {
        // ---------------------------------------------------------
        final FunctionMap<String, Integer, Integer> function = new FunctionMap<>(new MultiplyBy(10));
        // ---------------------------------------------------------

        final Map<String, Integer> map1 = new HashMap<>();
        map1.put("key1", 1);
        map1.put("key2", 2);
        map1.put("key3", 3);

        final Map<String, Integer> map2 = new HashMap<>();
        map2.put("key1", null);
        map2.put("key2", 2);
        map2.put("key3", 3);

        runExample(function,
                null,
                map1, map2, null);
    }
}
