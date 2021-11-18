/*
 * Copyright 2016-2020 Crown Copyright
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
package uk.gov.gchq.gaffer.doc.operation.accumulo;

import uk.gov.gchq.gaffer.accumulostore.operation.impl.GetElementsInRanges;
import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.gaffer.data.element.id.DirectedType;
import uk.gov.gchq.gaffer.doc.operation.OperationExample;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EdgeSeed;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;

@SuppressWarnings("unchecked")
public class GetElementsInRangesExample extends OperationExample {
    public static void main(final String[] args) throws OperationException {
        new GetElementsInRangesExample().runAndPrint();
    }

    public GetElementsInRangesExample() {
        super(GetElementsInRanges.class);
    }

    @Override
    public void runExamples() {
        getAllElementsInTheRangeFromEntity1toEntity4();
        getAllElementsInTheRangeFromEntity4ToEdge4_5();
    }

    public void getAllElementsInTheRangeFromEntity1toEntity4() {
        // ---------------------------------------------------------
        final GetElementsInRanges operation = new GetElementsInRanges.Builder()
                .input(new Pair<>(new EntitySeed(1), new EntitySeed(4)))
                .build();
        // ---------------------------------------------------------

        showExample(operation, null);
        print("The results are:\n");
        print("```");
        print("Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=2,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=3,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>2]]\n" +
                "Edge[source=2,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=5,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=1,destination=2,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]\n" +
                "Edge[source=3,destination=4,directed=true,matchedVertex=SOURCE,group=edge,properties=Properties[count=<java.lang.Integer>4]]\n" +
                "Edge[source=2,destination=3,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>2]]\n" +
                "Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=1,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=3,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>4]]");
        print("```");
    }

    public void getAllElementsInTheRangeFromEntity4ToEdge4_5() {
        // ---------------------------------------------------------
        final GetElementsInRanges operation = new GetElementsInRanges.Builder()
                .input(new Pair<>(new EntitySeed(4), new EdgeSeed(4, 5, DirectedType.EITHER)))
                .build();
        // ---------------------------------------------------------

        showExample(operation, null);
        print("The results are:\n");
        print("```");
        print("Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=1,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=3,destination=4,directed=true,matchedVertex=DESTINATION,group=edge,properties=Properties[count=<java.lang.Integer>4]]\n" +
                "Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]");
        print("```");
    }
}
