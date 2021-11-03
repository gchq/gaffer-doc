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
package uk.gov.gchq.gaffer.doc.operation.spark;

import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.spark.operation.graphframe.GetGraphFrameOfElements;

public class GetGraphFrameOfElementsExample extends SparkOperationExample {

    public static void main(final String[] args) {
        new GetGraphFrameOfElementsExample().runAndPrint();
    }

    public GetGraphFrameOfElementsExample() {
        super(GetGraphFrameOfElements.class);
    }

    @Override
    protected void _runExamples() {
        getGraphFrameOfElements();
    }

    public void getGraphFrameOfElements() {
        // ---------------------------------------------------------
        final GetGraphFrameOfElements operation = new GetGraphFrameOfElements.Builder()
                .view(new View.Builder()
                        .entity("entity")
                        .edge("edge")
                        .build())
                .build();
        // ---------------------------------------------------------
        showExample(operation, null);

        print("Then with the graphFrame result you can execute things like:");
        printJava("graphFrame.vertices().filter(\"vertex = 1 OR vertex = 2\").showString(100, 20)");
        print("and results are: ");
        print("```");
        print("+-------------+--------+---+----+----+-----+------+\n" +
                "|matchedVertex|directed| id| dst| src|count| group|\n" +
                "+-------------+--------+---+----+----+-----+------+\n" +
                "|         null|    null|  1|null|null| null|  null|\n" +
                "|         null|    null|  2|null|null| null|  null|\n" +
                "|         null|    null|  1|null|null|    3|entity|\n" +
                "|         null|    null|  2|null|null|    1|entity|\n" +
                "+-------------+--------+---+----+----+-----+------+");
        print("```");

        print("Or you can inspect the edges like:");
        printJava("graphFrame.edges().filter(\"count > 1\").showString(100, 20)");
        print("and results are: ");
        print("```");
        print("+-----+------+-----+---+---+--------+-------------+---+\n" +
                "|group|vertex|count|src|dst|directed|matchedVertex| id|\n" +
                "+-----+------+-----+---+---+--------+-------------+---+\n" +
                "| edge|  null|    3|  1|  2|    true|         null|  1|\n" +
                "| edge|  null|    2|  2|  3|    true|         null|  3|\n" +
                "| edge|  null|    4|  3|  4|    true|         null|  6|\n" +
                "+-----+------+-----+---+---+--------+-------------+---+");
        print("```");

        print("There is a whole suite of nice things you can do with GraphFrames, including operations like PageRank.");
        print("");
    }
}
