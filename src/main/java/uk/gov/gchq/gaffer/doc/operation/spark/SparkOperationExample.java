/*
 * Copyright 2016-2019 Crown Copyright
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

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import uk.gov.gchq.gaffer.doc.operation.OperationExample;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.spark.SparkConstants;
import uk.gov.gchq.gaffer.spark.SparkContextUtil;
import uk.gov.gchq.gaffer.store.Context;

public abstract class SparkOperationExample extends OperationExample {
    public static final String RFILE_READER_DESCRIPTION =
            "Note - there is an option to read the RFiles directly rather than the usual approach of obtaining them from Accumulo's tablet servers. "
                    + "This requires the Hadoop user, running the Spark job, to have read access to the RFiles in the Accumulo tablet. "
                    + "Note, however, that data which has not been minor compacted will not be read if this option is used. "
                    + "This functionality is enabled using the option: \"gaffer.accumulo.spark.directrdd.use_rfile_reader=true\"";

    private SparkConf sparkConf;
    private SparkSession sparkSession;

    public SparkOperationExample(final Class<? extends Operation> opClass, final String description) {
        super(opClass, createSparkDescription(description));
        sparkConf = new SparkConf()
                .setMaster("local")
                .setAppName(getClass().getSimpleName())
                .set(SparkConstants.SERIALIZER, SparkConstants.DEFAULT_SERIALIZER)
                .set(SparkConstants.KRYO_REGISTRATOR,
                        SparkConstants.DEFAULT_KRYO_REGISTRATOR);
        skipPython();
    }

    public SparkOperationExample(final Class<? extends Operation> opClass) {
        this(opClass, "");
    }

    private static String createSparkDescription(final String description) {
        String sparkDescription = description;
        if (!"".equals(sparkDescription)) {
            sparkDescription += "\n\n";
        }

        return sparkDescription + "When executing a spark operation you can either let " +
                "Gaffer create a SparkSession for you or you can add it yourself " +
                "to the Context object and provide it when you execute the operation.\n" +
                "e.g:\n" +
                "```java\n" +
                "Context context = SparkContextUtil.createContext(new User(\"User01\"), sparkSession);\n" +
                "graph.execute(operation, context);\n" +
                "```\n";
    }

    @Override
    public void runExamples() {
        try {
            _runExamples();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            sparkSession.stop();
            sparkSession = null;
        }
    }

    protected abstract void _runExamples() throws Exception;

    @Override
    protected Context createContext() {
        if (null == sparkSession) {
            createSparkSession();
        }
        final Context context = super.createContext();
        SparkContextUtil.addSparkSession(context, sparkSession);
        return context;
    }

    protected void createSparkSession() {
        sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
        sparkSession.sparkContext().setLogLevel("OFF");
    }
}
