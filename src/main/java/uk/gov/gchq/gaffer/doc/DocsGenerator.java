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
package uk.gov.gchq.gaffer.doc;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.doc.dev.walkthrough.DevWalkthroughRunner;
import uk.gov.gchq.gaffer.doc.operation.OperationExamplesRunner;
import uk.gov.gchq.gaffer.doc.operation.accumulo.AccumuloOperationExamplesRunner;
import uk.gov.gchq.gaffer.doc.operation.spark.SparkOperationExamplesRunner;
import uk.gov.gchq.gaffer.doc.predicate.PredicateExamplesRunner;
import uk.gov.gchq.gaffer.doc.properties.walkthrough.PropertiesWalkthroughRunner;
import uk.gov.gchq.gaffer.doc.user.walkthrough.UserWalkthroughRunner;

import java.io.File;
import java.io.IOException;

public class DocsGenerator implements DocGenerator {
    private static final DocGenerator[] GENERATORS = {
            new UserWalkthroughRunner(),
            new DevWalkthroughRunner(),
            new PropertiesWalkthroughRunner(),
            new OperationExamplesRunner(),
            new AccumuloOperationExamplesRunner(),
            new SparkOperationExamplesRunner(),
            new PredicateExamplesRunner()
    };

    public static void main(final String[] args) throws Exception {
        new DocsGenerator().generate();
    }

    @Override
    public void generate() {
        try {
            _generate();
        } catch (final IOException e) {
            throw new RuntimeException("Unable to generate documentation", e);
        }
    }

    protected void _generate() throws IOException {
        for (final DocGenerator generator : GENERATORS) {
            System.out.println("Generating " + generator.getClass().getSimpleName().replace("Runner", "") + " documentation");
            generator.generate();
        }

        final String summary = getSummary();
        FileUtils.writeStringToFile(new File(DocGenerator.DOC_FOLDER + "SUMMARY.md"), summary);
    }

    @Override
    public String getSummary() {
        System.out.println("Generating summary");

        final String template;
        try {
            template = StringUtils.join(IOUtils.readLines(StreamUtil.openStream(getClass(), "SUMMARY.md.template")), "\n");
        } catch (final IOException e) {
            throw new RuntimeException("Unable to generate full summary", e);
        }

        final StringBuilder gettingStartedSummaries = new StringBuilder();
        for (final DocGenerator summary : GENERATORS) {
            gettingStartedSummaries.append(summary.getSummary());
        }

        return template.replace("${GETTING_STARTED_SUMMARIES}", gettingStartedSummaries.toString()) + "\n";
    }
}
