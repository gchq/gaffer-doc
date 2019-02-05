/*
 * Copyright 2017-2019 Crown Copyright
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

import uk.gov.gchq.gaffer.data.element.function.ElementTransformer;
import uk.gov.gchq.gaffer.operation.impl.function.Transform;
import uk.gov.gchq.koryphe.impl.function.ToString;

public class TransformExample extends OperationExample {

    public static void main(final String[] args) {
        new TransformExample().runAndPrint();
    }

    public TransformExample() {
        super(Transform.class, "The Transform operation would normally be used in an Operation Chain to transform the results of a previous operation.");
    }

    @Override
    public void runExamples() {
        transformACountPropertyIntoACountStringPropertyOnlyForEdgesOfTypeEdge();
    }

    public void transformACountPropertyIntoACountStringPropertyOnlyForEdgesOfTypeEdge() {
        // ---------------------------------------------------------
        final Transform transform = new Transform.Builder()
                .edge("edge", new ElementTransformer.Builder()
                        .select("count")
                        .execute(new ToString())
                        .project("countString")
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(transform, null);
    }
}
