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

package uk.gov.gchq.gaffer.doc.operation.generator;

import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.generator.OneToOneElementGenerator;

import java.util.Objects;

public class ElementWithVaryingGroupsGenerator implements OneToOneElementGenerator<String> {

    @Override
    public Element _apply(final String line) {
        final String[] t = line.split(",");

        final Element element;
        if (t.length > 2) {
            final String evenProp = Integer.toString(Integer.parseInt(t[2]) % 2);
            final String groupModifier = (Objects.equals("0", evenProp)) ? "" : evenProp;
            element = new Edge.Builder()
                    .group("edge" + groupModifier)
                    .source(Integer.parseInt(t[0]))
                    .dest(Integer.parseInt(t[1]))
                    .directed(true)
                    .property("count", Integer.parseInt(t[2]))
                    .build();
        } else {
            final String evenProp = Integer.toString(Integer.parseInt(t[1]) % 2);
            final String groupModifier = (Objects.equals("0", evenProp)) ? "" : evenProp;
            element = new Entity.Builder()
                    .group("entity" + groupModifier)
                    .vertex(Integer.parseInt(t[0]))
                    .property("count", Integer.parseInt(t[1]))
                    .build();
        }

        return element;
    }
}
