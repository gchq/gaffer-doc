/*
 * Copyright 2017-2020 Crown Copyright
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

import uk.gov.gchq.koryphe.impl.function.ExtractKeys;

import java.util.HashMap;
import java.util.Map;

public class ExtractKeysExample extends FunctionExample {

    public static void main(final String[] args) {
        new ExtractKeysExample().runAndPrint();
    }

    public ExtractKeysExample() {
        super(ExtractKeys.class, "An ExtractKeys will return the Set of keys from the provided Java Map.");
    }

    @Override
    protected void runExamples() {
        extractKeysFromMap();
    }

    public void extractKeysFromMap() {
        // ---------------------------------------------------------
        final ExtractKeys<String, Integer> function = new ExtractKeys<>();
        // ---------------------------------------------------------

        final Map<String, Integer> input = new HashMap<>();
        input.put("firstKey", 2);
        input.put("secondKey", 4);
        input.put("thirdKey", 9);

        runExample(function,
                null,
                input);
    }
}
