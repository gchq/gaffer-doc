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

import org.apache.hadoop.conf.Configuration;

import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.data.EdgeSeed;
import uk.gov.gchq.gaffer.spark.operation.javardd.GetJavaRDDOfElements;
import uk.gov.gchq.gaffer.sparkaccumulo.operation.handler.AbstractGetRDDHandler;

import java.io.IOException;

/**
 * An example showing how the {@link GetJavaRDDOfElements} operation is used from Java.
 */
public class GetJavaRDDOfElementsExample extends SparkOperationExample {
    public static void main(final String[] args) throws OperationException {
        new GetJavaRDDOfElementsExample().runAndPrint();
    }

    public GetJavaRDDOfElementsExample() {
        super(GetJavaRDDOfElements.class);
    }

    @Override
    protected void _runExamples() throws Exception {
        getJavaRddOfElements();
        getJavaRddOfElementsWithHadoopConf();
        getJavaRddOfElementsReturningEdgesOnly();
    }

    public void getJavaRddOfElements() {
        // ---------------------------------------------------------
        final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .build();
        // ---------------------------------------------------------

        printJavaJsonPython(operation, 2);
        print("The results are:\n");
        print("```");
        print("Entity[vertex=1,group=entity,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Entity[vertex=2,group=entity,properties=Properties[count=<java.lang.Integer>1]]\n" +
                "Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]\n" +
                "Entity[vertex=3,group=entity,properties=Properties[count=<java.lang.Integer>2]]");
        print("```");
    }

    public void getJavaRddOfElementsWithHadoopConf() {
        // ---------------------------------------------------------
        final Configuration conf = new Configuration();
        conf.set("AN_OPTION", "A_VALUE");

        final String encodedConf;
        try {
            encodedConf = AbstractGetRDDHandler.convertConfigurationToString(conf);
        } catch (final IOException e) {
            throw new RuntimeException("Unable to convert conf to string", e);
        }

        final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .option(AbstractGetRDDHandler.HADOOP_CONFIGURATION_KEY, encodedConf)
                .build();
        // ---------------------------------------------------------

        operation.addOption(AbstractGetRDDHandler.HADOOP_CONFIGURATION_KEY, "config removed for readability");
        showExample(operation, null);
    }

    public void getJavaRddOfElementsReturningEdgesOnly() {
        // ---------------------------------------------------------
        final GetJavaRDDOfElements operation = new GetJavaRDDOfElements.Builder()
                .input(new EdgeSeed(1, 2, true), new EdgeSeed(2, 3, true))
                .view(new View.Builder()
                        .edge("edge")
                        .build())
                .build();
        // ---------------------------------------------------------

        printJavaJsonPython(operation, 2);
        print("The results are:\n");
        print("```");
        print("Edge[source=1,destination=2,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>3]]\n" +
                "Edge[source=2,destination=3,directed=true,group=edge,properties=Properties[count=<java.lang.Integer>2]]");
        print("```");
    }
}
