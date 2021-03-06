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
package uk.gov.gchq.gaffer.doc.properties.generator;

import com.yahoo.sketches.hll.HllSketch;

import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.generator.OneToManyElementGenerator;

import java.util.ArrayList;
import java.util.List;

public class HllSketchElementGenerator implements OneToManyElementGenerator<String> {

    @Override
    public Iterable<Element> _apply(final String line) {
        final List<Element> elements = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            final HllSketch hllSketch = new HllSketch(10);
            hllSketch.update("B" + i);

            final Entity entity = new Entity.Builder()
                    .group("cardinality")
                    .vertex("A")
                    .property("approxCardinality", hllSketch)
                    .build();
            elements.add(entity);
        }
        return elements;
    }
}
