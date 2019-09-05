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

import uk.gov.gchq.gaffer.types.FreqMap;
import uk.gov.gchq.koryphe.impl.function.MapToTuple;

import java.util.HashMap;
import java.util.Map;

public class MapToTupleExample extends FunctionExample {
    public static void main(final String[] args) {
        new MapToTupleExample().runAndPrint();
    }

    public MapToTupleExample() {
        super(uk.gov.gchq.koryphe.impl.function.MapToTuple.class);
    }

    @Override
    protected void runExamples() {
        mapToTuple();
    }

    private void mapToTuple() {
        // ---------------------------------------------------------
        final MapToTuple<String> function = new MapToTuple<>();
        // ---------------------------------------------------------
        Map<String, Object> input = new HashMap<>();
        input.put("A", 1);
        input.put("B", 2);
        input.put("C", 3);

        final FreqMap freqMap = new FreqMap(3);
        freqMap.upsert("value1");
        freqMap.upsert("value1");
        freqMap.upsert("value2");

        runExample(function, null, input, freqMap);
    }
}
