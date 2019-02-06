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
package uk.gov.gchq.gaffer.doc.walkthrough;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.doc.DocGenerator;
import uk.gov.gchq.gaffer.doc.util.DocUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static uk.gov.gchq.gaffer.doc.util.DocUtil.toFolderName;
import static uk.gov.gchq.gaffer.doc.util.DocUtil.toMdFileName;

public class AbstractWalkthroughRunner implements DocGenerator {
    protected final String title;
    protected final List<AbstractWalkthrough> examples;
    protected final String modulePath;
    protected final String resourcePrefix;
    protected final String outputPath;

    public AbstractWalkthroughRunner(final String title, final List<AbstractWalkthrough> examples, final String modulePath, final String resourcePrefix) {
        this.title = title;
        this.examples = examples;
        this.modulePath = modulePath;
        this.resourcePrefix = resourcePrefix;
        this.outputPath = toFolderName(GETTING_STARTED_FOLDER) + toFolderName(title);
    }

    @Override
    public void generate() {
        try {
            _generate();
        } catch (final Exception e) {
            throw new RuntimeException("Unable to generate documentation: " + title, e);
        }
    }

    protected void _generate() throws Exception {
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("contents")),
                getTableOfContents()
        );
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("introduction")),
                getIntro()
        );

        for (final AbstractWalkthrough example : examples) {
            DocUtil.clearCache();
            FileUtils.writeStringToFile(
                    new File(outputPath + toMdFileName(example.getHeader())),
                    example.walkthrough());
        }
    }

    protected String getIntro() {
        return loadFile("Intro.md");
    }

    protected String loadFile(final String filename) {
        final String intro;
        try (final InputStream stream = StreamUtil.openStream(getClass(), resourcePrefix + "/walkthrough/" + filename)) {
            intro = new String(IOUtils.toByteArray(stream), CommonConstants.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return WalkthroughStrSubstitutor.substitute(intro) + "\n";
    }

    protected String getTableOfContents() throws InstantiationException, IllegalAccessException {
        final StringBuilder tableOfContents = new StringBuilder();
        tableOfContents.append("# ").append(title).append("\n");

        int index = 1;

        tableOfContents.append(DocUtil.getLink(index, "Introduction"));
        index++;

        for (final AbstractWalkthrough example : examples) {
            final String header = example.getHeader();
            tableOfContents
                    .append(index).append(". [").append(header).append("](")
                    .append(toMdFileName(header))
                    .append(")\n");
            index++;
        }
        tableOfContents.append("\n");

        return tableOfContents.toString();
    }

    @Override
    public String getSummary() {
        final StringBuilder summary = new StringBuilder();
        final String folderPrefix = "getting-started/" + toFolderName(title);
        summary.append("  * [").append(title).append("](").append(folderPrefix).append(toMdFileName("contents")).append(")\n");
        summary.append("    * [Introduction](").append(folderPrefix).append(toMdFileName("introduction")).append(")\n");

        for (final AbstractWalkthrough example : examples) {
            final String header = example.getHeader();
            summary
                    .append("    * [").append(header).append("](")
                    .append(folderPrefix)
                    .append(toMdFileName(header))
                    .append(")\n");
        }
        return summary.toString();
    }
}
