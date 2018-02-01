/*
 * Copyright 2016 Crown Copyright
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


import uk.gov.gchq.gaffer.types.FreqMap;
import uk.gov.gchq.gaffer.types.function.FreqMapExtractor;

import java.util.HashMap;
import java.util.Map;

public class FreqMapExtractorExample extends FunctionExample {
    public static void main(final String[] args) {
        new FreqMapExtractorExample().runAndPrint();
    }

    public FreqMapExtractorExample() {
        super(FreqMapExtractor.class, "Extracts a count from a frequency map for a given key.");
    }

    @Override
    public void runExamples() {
        multiplyAllMapValuesBy10();
    }

    public void multiplyAllMapValuesBy10() {
        // ---------------------------------------------------------
        final FreqMapExtractor function = new FreqMapExtractor("key1");
        // ---------------------------------------------------------

        final FreqMap map1 = new FreqMap();
        map1.put("key1", 1L);
        map1.put("key2", 2L);
        map1.put("key3", 3L);

        final FreqMap map2 = new FreqMap();
        map2.put("key2", 2L);
        map2.put("key3", 3L);

        final Map<String, Long> map3 = new HashMap<>();
        map3.put("key1", 1L);
        map3.put("key2", 2L);
        map3.put("key3", 3L);


        runExample(function,
                null,
                map1, map2, map3, null);
    }
}
