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

        printJavaJsonPython(operation, 3);
        print("The results are:\n");
        print("```");
        print("+------+------+-----+\n" +
                "| group|vertex|count|\n" +
                "+------+------+-----+\n" +
                "|entity|     1|    3|\n" +
                "|entity|     2|    1|\n" +
                "|entity|     3|    2|\n" +
                "|entity|     4|    1|\n" +
                "|entity|     5|    3|\n" +
                "+------+------+-----");
        print("```");

        // Restrict to entities involving certain vertices
        printJava("df.filter(\"vertex = 1 OR vertex = 2\").show();");
        print("The results are:\n");
        print("```");
        print("+------+------+-----+\n" +
                "| group|vertex|count|\n" +
                "+------+------+-----+\n" +
                "|entity|     1|    3|\n" +
                "|entity|     2|    1|\n" +
                "+------+------+-----");
        print("```");

        // Filter by property
        printJava("df.filter(\"count > 1\").show();");
        print("The results are:\n");
        print("```");
        print("+------+------+-----+\n" +
                "| group|vertex|count|\n" +
                "+------+------+-----+\n" +
                "|entity|     1|    3|\n" +
                "|entity|     3|    2|\n" +
                "|entity|     5|    3|\n" +
                "+------+------+-----");
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

        printJavaJsonPython(operation, 3);
        print("The results are:\n");
        print("```");
        print("+-----+---+---+--------+-------------+-----+\n" +
                "|group|src|dst|directed|matchedVertex|count|\n" +
                "+-----+---+---+--------+-------------+-----+\n" +
                "| edge|  1|  2|    true|         null|    3|\n" +
                "| edge|  1|  4|    true|         null|    1|\n" +
                "| edge|  2|  3|    true|         null|    2|\n" +
                "| edge|  2|  4|    true|         null|    1|\n" +
                "| edge|  2|  5|    true|         null|    1|\n" +
                "| edge|  3|  4|    true|         null|    4|\n" +
                "+-----+---+---+--------+-------------+-----");
        print("```");

        // Restrict to edges involving given vertices
        printJava("df.filter(\"src = 1 OR src = 3\").show();");
        print("The results are:\n");
        print("```");
        print("+-----+---+---+--------+-------------+-----+\n" +
                "|group|src|dst|directed|matchedVertex|count|\n" +
                "+-----+---+---+--------+-------------+-----+\n" +
                "| edge|  1|  2|    true|         null|    3|\n" +
                "| edge|  1|  4|    true|         null|    1|\n" +
                "| edge|  3|  4|    true|         null|    4|\n" +
                "+-----+---+---+--------+-------------+-----");
        print("```");

        // Filter by property
        printJava("df.filter(\"count > 1\").show();");
        print("The results are:\n");
        print("```");
        print("+-----+---+---+--------+-------------+-----+\n" +
                "|group|src|dst|directed|matchedVertex|count|\n" +
                "+-----+---+---+--------+-------------+-----+\n" +
                "| edge|  1|  2|    true|         null|    3|\n" +
                "| edge|  2|  3|    true|         null|    2|\n" +
                "| edge|  3|  4|    true|         null|    4|\n" +
                "+-----+---+---+--------+-------------+-----");
        print("```");
    }
}
