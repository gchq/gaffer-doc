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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.Charsets;

import uk.gov.gchq.koryphe.impl.function.CreateObject;

import java.util.ArrayList;


public class CreateObjectExample extends FunctionExample {

    public CreateObjectExample() {
        super(CreateObject.class, "Creates a new object of a given type");
    }

    public static void main(final String[] args) {
        new CreateObjectExample().runAndPrint();
    }

    @Override
    protected void runExamples() {
        stringExample();
        listExample();
    }

    public void stringExample() {
        // ---------------------------------------------------------
        final CreateObject createObject = new CreateObject(String.class);
        // ---------------------------------------------------------

        runExample(createObject, null, "a normal string",
                "some bytes".getBytes(Charsets.UTF_8),
                null, 123, "a char array".toCharArray());
    }

    public void listExample() {
        // ---------------------------------------------------------
        final CreateObject createObject = new CreateObject(ArrayList.class);
        // ---------------------------------------------------------

        runExample(createObject, null, Lists.newArrayList("list", "example"),
                Sets.newHashSet("set", "example"), Maps.newHashMap(), null);
    }
}
