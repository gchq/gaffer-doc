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

import com.google.common.collect.Lists;

import uk.gov.gchq.koryphe.impl.function.ToTuple;

import java.util.HashMap;
import java.util.Map;

public class ToTupleExample extends FunctionExample {
    public static void main(final String[] args) {
        new ToTupleExample().runAndPrint();
    }

    public ToTupleExample() {
        super(ToTuple.class);
    }

    @Override
    protected void runExamples() {
        toTuple();
    }

    private void toTuple() {
        // ---------------------------------------------------------
        final ToTuple function = new ToTuple();
        // ---------------------------------------------------------

        runExample(function, null, Lists.newArrayList(1, 2, 3, 4));
        runExample(function, null, new int[]{1, 2, 3, 4});
        runExample(function, null, new Integer[]{1, 2, 3, 4});
        Map<String, Object> input = new HashMap<>();
        input.put("A", 1);
        input.put("B", 2);
        input.put("C", 3);
        runExample(function, null, input);
    }
}
