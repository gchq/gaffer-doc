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
package uk.gov.gchq.gaffer.doc.operation.spark;

import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfAllElements;

/**
 * An example showing how the {@link GetJavaRDDOfAllElements} operation is used from Java.
 */
public class GetJavaRDDOfAllElementsExample extends SparkOperationExample {
    public static void main(final String[] args) throws OperationException {
        new GetJavaRDDOfAllElementsExample().runAndPrint();
    }

    public GetJavaRDDOfAllElementsExample() {
        super(GetJavaRDDOfAllElements.class, getDescription());
    }

    private static String getDescription() {
        return "All the elements in a graph can be returned "
                + "as a JavaRDD by using the operation GetJavaRDDOfAllElements. "
                + "Some examples follow. \n"
                + RFILE_READER_DESCRIPTION;
    }

    @Override
    protected void _runExamples() {
        getJavaRddOfAllElements();
        getJavaRddOfAllElementsReturningEdgesOnly();
    }

    public void getJavaRddOfAllElements() {
        // ---------------------------------------------------------
        final GetJavaRDDOfAllElements operation = new GetJavaRDDOfAllElements();
        // ---------------------------------------------------------

        showExample(operation, null);
        print("The results are:\n");
        print("```");
        print("Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]\n" +
                "Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]\n" +
                "Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]\n" +
                "Entity[vertex=4,group=entity,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Entity[vertex=5,group=entity,properties=Properties[count=<java.lang.Integer>3]]");
        print("```");
    }

    public void getJavaRddOfAllElementsReturningEdgesOnly() {
        // ---------------------------------------------------------
        final GetJavaRDDOfAllElements operation = new GetJavaRDDOfAllElements.Builder()
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build();
        // ---------------------------------------------------------

        showExample(operation, null);
        print("The results are:\n");
        print("```");
        print("Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]\n" +
                "Edge[source=2,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=5,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=3,destination=4,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>4]]");
        print("```");
    }
}
