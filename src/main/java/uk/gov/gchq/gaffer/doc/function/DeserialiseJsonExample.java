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

import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.koryphe.impl.function.DeserialiseJson;

import java.util.HashMap;

public class DeserialiseJsonExample extends FunctionExample {
    public static void main(final String[] args) {
        new DeserialiseJsonExample().runAndPrint();
    }

    public DeserialiseJsonExample() {
        super(DeserialiseJson.class);
    }

    @Override
    protected void runExamples() {
        parseJson();
    }

    private void parseJson() {
        // ---------------------------------------------------------
        final DeserialiseJson function = new DeserialiseJson();
        // ---------------------------------------------------------

        final HashMap<String, Double> map = new HashMap<>();
        map.put("key1", 1.0);
        map.put("key2", 2.2);
        map.put("key3", 3.3);

        try {
            runExample(function, null,
                    "{\"elements\": [{\"value\": \"value1\"}, {\"value\": \"value2\"}]}",
                    "[ \"ListValue1\", \"ListValue2\", \"ListValue3\" ]",
                    "{ \"number\":30 }",
                    "{ \"false\":true }",
                    new String(JSONSerialiser.serialise(new EntitySeed(1), true)),
                    new String(JSONSerialiser.serialise(Lists.newArrayList("listValue1", "listValue1", "listValue1"), true)),
                    new String(JSONSerialiser.serialise(map, true)));
        } catch (final SerialisationException e) {
            e.printStackTrace();
        }
    }

}
