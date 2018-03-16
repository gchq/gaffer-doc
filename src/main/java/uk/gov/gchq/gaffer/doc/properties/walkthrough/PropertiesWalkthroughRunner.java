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
package uk.gov.gchq.gaffer.doc.properties.walkthrough;

import com.clearspring.analytics.stream.cardinality.HyperLogLogPlus;
import com.yahoo.sketches.frequencies.LongsSketch;
import com.yahoo.sketches.hll.HllSketch;
import com.yahoo.sketches.quantiles.DoublesSketch;
import com.yahoo.sketches.sampling.ReservoirItemsSketch;
import com.yahoo.sketches.theta.Sketch;
import org.apache.commons.io.FileUtils;

import uk.gov.gchq.gaffer.doc.util.DocUtil;
import uk.gov.gchq.gaffer.doc.walkthrough.AbstractWalkthrough;
import uk.gov.gchq.gaffer.doc.walkthrough.AbstractWalkthroughRunner;
import uk.gov.gchq.gaffer.time.BoundedTimestampSet;
import uk.gov.gchq.gaffer.time.RBMBackedTimestampSet;
import uk.gov.gchq.gaffer.types.FreqMap;
import uk.gov.gchq.gaffer.types.TypeSubTypeValue;
import uk.gov.gchq.gaffer.types.TypeValue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static uk.gov.gchq.gaffer.doc.util.DocUtil.toFolderName;
import static uk.gov.gchq.gaffer.doc.util.DocUtil.toMdFileName;

public class PropertiesWalkthroughRunner extends AbstractWalkthroughRunner {
    private static final String SIMPLE_PROPERTIES_TITLE = "Simple Properties";
    private static final String WALKTHROUGHS_TITLE = "Walkthroughs";

    private static final List<Class<?>> SIMPLE_PROPERTIES = Arrays.asList(
            String.class,
            Long.class,
            Integer.class,
            Double.class,
            Float.class,
            Byte[].class,
            Boolean.class,
            Date.class,
            TypeValue.class,
            TypeSubTypeValue.class,
            FreqMap.class,
            HashMap.class,
            TreeSet.class
    );

    private static final List<Class<?>> SKETCHES = Arrays.asList(
            HyperLogLogPlus.class,
            HllSketch.class,
            LongsSketch.class,
            DoublesSketch.class,
            ReservoirItemsSketch.class,
            Sketch.class
    );

    private static final List<Class<?>> TIMESTAMP_PROPERTIES = Arrays.asList(
            RBMBackedTimestampSet.class,
            BoundedTimestampSet.class
    );

    private static final List<AbstractWalkthrough> CLEARSPRING_SKETCHES_WALKTHROUGHS = Collections
            .singletonList(new HyperLogLogPlusWalkthrough());

    private static final List<AbstractWalkthrough> DATA_SKETCHES_WALKTHROUGHS = Arrays.asList(
            new HllSketchWalkthrough(),
            new LongsSketchWalkthrough(),
            new DoublesSketchWalkthrough(),
            new ReservoirItemsSketchWalkthrough(),
            new ThetaSketchWalkthrough()
    );

    private static final List<AbstractWalkthrough> TIMESTAMP_WALKTHROUGHS = Arrays.asList(
            new TimestampSetWalkthrough(),
            new BoundedTimestampSetWalkthrough()
    );

    private static final List<AbstractWalkthrough> EXAMPLES = getExamples();

    private static List<AbstractWalkthrough> getExamples() {
        final List<AbstractWalkthrough> examples = new ArrayList<>();
        SIMPLE_PROPERTIES.forEach(c -> examples.add(new SimpleProperty(c)));
        SKETCHES.forEach(c -> examples.add(new SimpleProperty(c)));
        TIMESTAMP_PROPERTIES.forEach(c -> examples.add(new SimpleProperty(c)));
        return examples;
    }

    private static List<AbstractWalkthrough> getAllWalkthroughs() {
        final List<AbstractWalkthrough> examples = new ArrayList<>();
        examples.addAll(CLEARSPRING_SKETCHES_WALKTHROUGHS);
        examples.addAll(DATA_SKETCHES_WALKTHROUGHS);
        examples.addAll(TIMESTAMP_WALKTHROUGHS);
        return examples;
    }

    public static void main(final String[] args) throws Exception {
        new PropertiesWalkthroughRunner().generate();
    }

    public PropertiesWalkthroughRunner() {
        super("Properties Guide", null, null, "properties");
    }

    @Override
    protected void _generate() throws Exception {
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("contents")),
                getTableOfContents()
        );
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("introduction")),
                getIntro()
        );
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("Simple Properties")),
                getSimpleProperties()
        );
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("Sketches")),
                getSketches()
        );
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("Timestamps")),
                getTimestamps()
        );

        generateWalkthroughs();
        generateSimpleProperties();
    }

    @Override
    protected String getTableOfContents() throws InstantiationException, IllegalAccessException {
        final StringBuilder tableOfContents = new StringBuilder();
        tableOfContents.append("# ").append(title).append("\n");

        int index = 1;
        tableOfContents.append(DocUtil.getLink(index, "Introduction"));
        index++;
        tableOfContents.append(DocUtil.getLink(index, "Simple properties"));
        index++;
        tableOfContents.append(DocUtil.getLink(index, "Sketches"));
        index++;
        tableOfContents.append(DocUtil.getLink(index, "Timestamps"));
        index++;
        tableOfContents.append(index).append(". " + WALKTHROUGHS_TITLE + "\n");
        index++;
        int subIndex = 1;
        for (final AbstractWalkthrough example : getAllWalkthroughs()) {
            final String header = example.getHeader();
            tableOfContents.append("   ")
                    .append(subIndex)
                    .append(". [").append(header).append("](")
                    .append(toFolderName(WALKTHROUGHS_TITLE)).append(toMdFileName(header))
                    .append(")\n");
            subIndex++;
        }
        tableOfContents.append(index).append(". " + SIMPLE_PROPERTIES_TITLE + "\n");
        subIndex = 1;
        for (final AbstractWalkthrough example : EXAMPLES) {
            final String header = example.getHeader();
            tableOfContents.append("   ")
                    .append(subIndex)
                    .append(". [").append(header).append("](")
                    .append(toFolderName(SIMPLE_PROPERTIES_TITLE)).append(toMdFileName(header))
                    .append(")\n");
            subIndex++;
        }
        return tableOfContents.toString();
    }

    @Override
    public String getSummary() {
        final StringBuilder summary = new StringBuilder();
        final String folderPrefix = "getting-started/" + toFolderName(title);
        summary.append("  * [").append(title).append("](").append(folderPrefix).append(toMdFileName("contents")).append(")\n");
        summary.append(DocUtil.getFullLink(folderPrefix, "Introduction"));
        summary.append(DocUtil.getFullLink(folderPrefix, "Simple properties"));
        summary.append(DocUtil.getFullLink(folderPrefix, "Sketches"));
        summary.append(DocUtil.getFullLink(folderPrefix, "Timestamps"));
        summary.append("    * " + WALKTHROUGHS_TITLE + " \n");
        for (final AbstractWalkthrough example : getAllWalkthroughs()) {
            final String header = example.getHeader();
            summary.append("       * [")
                    .append(header).append("](")
                    .append(folderPrefix)
                    .append(toFolderName(WALKTHROUGHS_TITLE)).append(toMdFileName(header))
                    .append(")\n");
        }
        summary.append("    * " + SIMPLE_PROPERTIES_TITLE + "\n");
        for (final AbstractWalkthrough example : EXAMPLES) {
            final String header = example.getHeader();
            summary.append("       * [")
                    .append(header).append("](")
                    .append(folderPrefix)
                    .append(toFolderName(SIMPLE_PROPERTIES_TITLE)).append(toMdFileName(header))
                    .append(")\n");
        }
        return summary.toString();
    }

    private String getSimpleProperties() {
        return loadFile("SimpleProperties.md");
    }

    private String getSketches() {
        return loadFile("Sketches.md");
    }

    private String getTimestamps() {
        return loadFile("Timestamps.md");
    }

    private void generateWalkthroughs() throws Exception {
        generateWalkthroughs(CLEARSPRING_SKETCHES_WALKTHROUGHS);
        generateWalkthroughs(DATA_SKETCHES_WALKTHROUGHS);
        generateWalkthroughs(TIMESTAMP_WALKTHROUGHS);
    }

    private void generateWalkthroughs(final List<AbstractWalkthrough> examples) throws Exception {
        for (final AbstractWalkthrough example : examples) {
            DocUtil.clearCache();
            FileUtils.writeStringToFile(
                    new File(outputPath + toFolderName(WALKTHROUGHS_TITLE) + toMdFileName(example.getHeader())),
                    example.walkthrough());
        }
    }

    private void generateSimpleProperties() throws Exception {
        for (final AbstractWalkthrough example : EXAMPLES) {
            // Clear the caches so the output is not dependent on what's been run before
            DocUtil.clearCache();
            FileUtils.writeStringToFile(
                    new File(outputPath + toFolderName(SIMPLE_PROPERTIES_TITLE) + toMdFileName(example.getHeader())),
                    example.walkthrough());
        }
    }
}
