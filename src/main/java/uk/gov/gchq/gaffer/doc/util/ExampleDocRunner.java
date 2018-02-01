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
package uk.gov.gchq.gaffer.doc.util;

import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.doc.DocGenerator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static uk.gov.gchq.gaffer.doc.util.DocUtil.toFileName;
import static uk.gov.gchq.gaffer.doc.util.DocUtil.toFolderName;
import static uk.gov.gchq.gaffer.doc.util.DocUtil.toMdFileName;

/**
 * This runner will run a suite of examples.
 */
public abstract class ExampleDocRunner implements DocGenerator {
    private final String title;
    private final String outputPath;
    private final String resourcePrefix;
    private final LinkedHashSet<Example> examples;

    protected ExampleDocRunner(final String title, final Class<? extends Example> exampleParentClass) {
        this.title = title;
        this.outputPath = toFolderName(GETTING_STARTED_FOLDER + title);
        this.resourcePrefix = toFileName(title);

        this.examples = getSubClassInstances(exampleParentClass);
    }

    @Override
    public void generate() {
        try {
            _generate();
        } catch (final Exception e) {
            throw new RuntimeException("Unable to generate documentation for: " + title, e);
        }
    }

    public void _generate() throws Exception {
        FileUtils.writeStringToFile(
                new File(outputPath + toMdFileName("contents")),
                getTableOfContents()
        );
        for (final Example example : examples) {
            DocUtil.clearCache();
            example.run();
            FileUtils.writeStringToFile(
                    new File(outputPath + toMdFileName(example.getClass().getSimpleName().replace("Example", ""))),
                    example.getOutput());
        }
    }

    protected String getIntro() {
        return loadFile("Intro.md");
    }

    protected String loadFile(final String filename) {
        final String fileContents;
        try (final InputStream stream = StreamUtil.openStream(getClass(), toFolderName(resourcePrefix) + filename)) {
            fileContents = new String(IOUtils.toByteArray(stream), CommonConstants.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return fileContents + "\n";
    }

    protected String getTableOfContents() throws InstantiationException, IllegalAccessException {
        final StringBuilder contents = new StringBuilder();
        contents.append("# ").append(title).append("\n");
        contents.append(getIntro());

        int index = 1;
        for (final Example example : examples) {
            final String header = example.getClassForExample().getSimpleName().replace("Example", "");
            contents
                    .append(index).append(". [").append(header).append("](")
                    .append(toMdFileName(header))
                    .append(")\n");
            index++;
        }
        contents.append("\n");

        return contents.toString();
    }

    @Override
    public String getSummary() {
        final StringBuilder summary = new StringBuilder();

        final String folderPrefix = "getting-started/" + toFolderName(title);

        summary.append("  * [").append(title).append("](").append(folderPrefix).append(toMdFileName("contents")).append(")\n");
        for (final Example example : examples) {
            final String header;
            header = example.getClassForExample().getSimpleName().replace("Example", "");
            summary
                    .append("    * [").append(header).append("](")
                    .append(folderPrefix).append(toMdFileName(header))
                    .append(")\n");
        }
        return summary.toString();
    }

    private <CLASS> LinkedHashSet<CLASS> getSubClassInstances(final Class<?> exampleParentClass) {
        final LinkedHashSet<CLASS> instances = new LinkedHashSet<>();
        for (final Class<?> exampleClass : getSubClasses(exampleParentClass, getClass().getPackage().getName())) {
            try {
                instances.add((CLASS) exampleClass.newInstance());
            } catch (final InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Unable to instantiate example: " + exampleClass.getName(), e);
            }
        }
        return instances;
    }

    private <CLASS> LinkedHashSet<Class<? extends CLASS>> getSubClasses(final Class<?> clazz, final String packageName) {
        final Set<URL> urls = new HashSet<>(ClasspathHelper.forPackage("gaffer"));

        final List<Class<? extends CLASS>> classes = new ArrayList(new Reflections(urls).getSubTypesOf(clazz));
        keepPublicConcreteClasses(classes);
        keepClassesInPackage(classes, packageName);
        Collections.sort(classes, new Comparator<Class>() {
            @Override
            public int compare(final Class class1, final Class class2) {
                return class1.getName().compareTo(class2.getName());
            }
        });

        return Sets.newLinkedHashSet((Iterable) classes);
    }

    private static void keepClassesInPackage(final List classes, final String packageName) {
        if (null != classes && null != packageName) {
            final Iterator<Class> itr = classes.iterator();
            while (itr.hasNext()) {
                final Class clazz = itr.next();
                if (null != clazz) {
                    if (!clazz.getPackage().getName().equals(packageName)) {
                        itr.remove();
                    }
                }
            }
        }
    }

    private static void keepPublicConcreteClasses(final List classes) {
        if (null != classes) {
            final Iterator<Class> itr = classes.iterator();
            while (itr.hasNext()) {
                final Class clazz = itr.next();
                if (null != clazz) {
                    final int modifiers = clazz.getModifiers();
                    if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || Modifier.isPrivate(modifiers) || Modifier.isProtected(modifiers)) {
                        itr.remove();
                    }
                }
            }
        }
    }
}
