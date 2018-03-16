/*
 * Copyright 2016-2018 Crown Copyright
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

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.spark.operation.dataframe.GetDataFrameOfElements;

/**
 * An example showing how the {@link GetDataFrameOfElements} operation is used from Java.
 */
public class GetDataFrameOfElementsExample extends SparkOperationExample {
    public static void main(final String[] args) throws OperationException {
        new GetDataFrameOfElementsExample().runAndPrint();
    }

    public GetDataFrameOfElementsExample() {
        super(GetDataFrameOfElements.class, RFILE_READER_DESCRIPTION);
        skipEndOfMethodBreaks = true;
    }

    @Override
    public void _runExamples() {
        getDataFrameOfElementsWithEntityGroup();
        endOfMethod();
        getDataFrameOfElementsWithEdgeGroup();
        endOfMethod();
    }

    public void getDataFrameOfElementsWithEntityGroup() {
        // ---------------------------------------------------------
        final GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("entity")
                        .build())
                .build();
        // ---------------------------------------------------------

        final Dataset<Row> df = runExample(operation, null);

        // Restrict to entities involving certain vertices
        final Dataset<Row> seeded = df.filter("vertex = 1 OR vertex = 2");
        String result = seeded.showString(100, 20);
        printJava("df.filter(\"vertex = 1 OR vertex = 2\").show();");
        print("The results are:\n");
        print("```");
        print(result.substring(0, result.length() - 2));
        print("```");

        // Filter by property
        final Dataset<Row> filtered = df.filter("count > 1");
        result = filtered.showString(100, 20);
        printJava("df.filter(\"count > 1\").show();");
        print("The results are:\n");
        print("```");
        print(result.substring(0, result.length() - 2));
        print("```");
    }

    public void getDataFrameOfElementsWithEdgeGroup() {
        // ---------------------------------------------------------
        final GetDataFrameOfElements operation = new GetDataFrameOfElements.Builder()
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build();
        // ---------------------------------------------------------

        final Dataset<Row> df = runExample(operation, null);


        // Restrict to edges involving given vertices
        final Dataset<Row> seeded = df.filter("src = 1 OR src = 3");
        String result = seeded.showString(100, 20);
        printJava("df.filter(\"src = 1 OR src = 3\").show();");
        print("The results are:\n");
        print("```");
        print(result.substring(0, result.length() - 2));
        print("```");

        // Filter by property
        final Dataset<Row> filtered = df.filter("count > 1");
        result = filtered.showString(100, 20);
        printJava("df.filter(\"count > 1\").show();");
        print("The results are:\n");
        print("```");
        print(result.substring(0, result.length() - 2));
        print("```");
    }
}
