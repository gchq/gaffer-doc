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

import com.clearspring.analytics.stream.cardinality.HyperLogLogPlus;
import com.google.common.collect.Lists;

import uk.gov.gchq.gaffer.commonutil.CollectionUtil;
import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.Entity;
import uk.gov.gchq.gaffer.data.generator.OneToManyElementGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElementWithVaryingGroupsGenerator implements OneToManyElementGenerator<String> {

    @Override
    public Iterable<Element> _apply(final String line) {
        final String[] t = line.split(",");

        final List<Element> elements = new ArrayList<>();
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

            elements.add(element);
            elements.addAll(createCardinalities(Lists.newArrayList((Edge) element)));
        } else {
            final String evenProp = Integer.toString(Integer.parseInt(t[1]) % 2);
            final String groupModifier = (Objects.equals("0", evenProp)) ? "" : evenProp;
            element = new Entity.Builder()
                    .group("entity" + groupModifier)
                    .vertex(Integer.parseInt(t[0]))
                    .property("count", Integer.parseInt(t[1]))
                    .build();
            elements.add(element);
        }

        return elements;
    }

    private List<Entity> createCardinalities(final List<Edge> edges) {
        final List<Entity> cardinalities = new ArrayList<>(edges.size() * 2);

        for (final Edge edge : edges) {
            cardinalities.add(createCardinality(edge.getSource(), edge.getDestination(), edge));
            cardinalities.add(createCardinality(edge.getDestination(), edge.getSource(), edge));
        }

        return cardinalities;
    }

    private Entity createCardinality(final Object source,
                                     final Object destination,
                                     final Edge edge) {
        final HyperLogLogPlus hllp = new HyperLogLogPlus(5, 5);
        hllp.offer(destination);

        return new Entity.Builder()
                .vertex(source)
                .group("cardinality")
                .property("edgeGroup", CollectionUtil.treeSet(edge.getGroup()))
                .property("hllp", hllp)
                .property("count", 1)
                .build();
    }
}
