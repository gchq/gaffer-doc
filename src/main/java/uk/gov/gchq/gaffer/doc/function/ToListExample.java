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

import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.koryphe.impl.function.ToList;

import java.util.HashSet;
import java.util.Set;

public class ToListExample extends FunctionExample {


    public static void main(final String[] args) {
        new ToListExample().runAndPrint();
    }

    public ToListExample() {
        super(ToList.class, "Converts an Object to a List");
    }

    @Override
    public void runExamples() {
        toList();
    }

    public void toList() {
        // ---------------------------------------------------------
        final ToList function = new ToList();
        // ---------------------------------------------------------


        String[] strArray = {"a", "b", "c"};
        Set<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
        runExample(function,
                null,
                "test", null, 30L, new TypeSubTypeValue("t", "st", "v"), strArray, set
                );
    }
}
