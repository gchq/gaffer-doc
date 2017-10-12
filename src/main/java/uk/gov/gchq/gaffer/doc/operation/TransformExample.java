/*
 * Copyright 2017 Crown Copyright
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
package uk.gov.gchq.gaffer.doc.operation;

import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.function.ElementTransformer;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.operation.OperationChain;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.impl.function.Transform;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.koryphe.impl.function.ToString;

import java.util.HashMap;
import java.util.Map;

public class TransformExample extends OperationExample {

    public static void main(final String[] args) {
        new TransformExample().run();
    }

    public TransformExample() {
        super(Transform.class);
    }

    @Override
    protected void runExamples() {
        transformToTransientProperty();
        transformUsingElementMap();
    }

    public Iterable<? extends Element> transformToTransientProperty() {
        // ---------------------------------------------------------
        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed(1))
                        .build())
                .then(new GetElements.Builder()
                        .view(new View.Builder()
                                .edge("edge", new ViewElementDefinition.Builder()
                                        .transientProperty("newProp", String.class)
                                        .build())
                                .build())
                        .build())
                .then(new Transform.Builder()
                        .edge("edge", new ElementTransformer.Builder()
                                .select("count")
                                .execute(new ToString())
                                .project("newProp")
                                .build())
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, "In this example, we have added a transient property to each " +
                "edge in the \"edge\" group, and then transformed the existing \"count\" property " +
                "into this new transient property.");
    }

    public Iterable<? extends Element> transformUsingElementMap() {
        // ---------------------------------------------------------
        final Map<String, ElementTransformer> entities = new HashMap<>();
        entities.put("entity", new ElementTransformer.Builder()
                .select("count")
                .execute(new ToString())
                .project("stringProp")
                .build());

        final OperationChain<Iterable<? extends Element>> opChain = new OperationChain.Builder()
                .first(new GetAdjacentIds.Builder()
                        .input(new EntitySeed(2))
                        .build())
                .then(new GetElements.Builder()
                        .view(new View.Builder()
                                .entity("entity", new ViewElementDefinition.Builder()
                                        .transientProperty("stringProp", String.class)
                                        .build())
                                .build())
                        .build())
                .then(new Transform.Builder()
                        .entities(entities)
                        .build())
                .build();
        // ---------------------------------------------------------
        return runExample(opChain, "Similar to the previous example, a transient property is added to each " +
                "entity in the \"entity\" group, and the count property is transformed into it. The main " +
                "difference is using the map of group to ElementTransformer, to perform complex transforms " +
                "on multiple groups with ease.");
    }
}
