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
package uk.gov.gchq.gaffer.doc.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.gaffer.doc.walkthrough.WalkthroughStrSubstitutor;
import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.tuple.MapTuple;
import uk.gov.gchq.koryphe.tuple.Tuple;

import java.util.Locale;

public abstract class Example {
    public static final String CAPITALS_AND_NUMBERS_REGEX = "((?=[A-Z])|(?<=[0-9])(?=[a-zA-Z])|(?<=[a-zA-Z])(?=[0-9]))";
    public static final String DIVIDER = "-----------------------------------------------";
    public static final String METHOD_DIVIDER = DIVIDER + "\n";
    public static final String KORYPHE_JAVA_DOC_URL_PREFIX = "ref://../../javadoc/koryphe/";
    public static final String JAVA_DOC_URL_PREFIX = "ref://../../javadoc/gaffer/";
    private final Class<?> classForExample;
    private final String description;
    private StringBuilder output = new StringBuilder();
    private Boolean skipPythonErrors;

    public Example(final Class<?> classForExample) {
        this(classForExample, "");
    }

    public Example(final Class<?> classForExample, final String description) {
        this.classForExample = classForExample;
        this.description = description;
    }

    public void run() {
        print("# " + classForExample.getSimpleName());
        printJavaDocLink();
        printSince();
        printDescription();
        print("## Examples");
        print("");
        runExamples();
    }

    public void runAndPrint() {
        run();
        System.out.println(getOutput());
    }

    public void printJavaDocLink() {
        final String urlPrefix;
        if (classForExample.getName().contains("koryphe")) {
            urlPrefix = KORYPHE_JAVA_DOC_URL_PREFIX;
        } else {
            urlPrefix = JAVA_DOC_URL_PREFIX;
        }

        print("See javadoc - [" + classForExample.getName() + "](" + urlPrefix + classForExample.getName().replace(".", "/") + ".html).\n");
    }

    public void printSince() {
        final String packageIdentifier;
        if (getClassForExample().getName().startsWith("uk.gov.gchq.koryphe")) {
            packageIdentifier = "Koryphe";
        } else {
            packageIdentifier = "Gaffer";
        }
        final Since anno = getClassForExample().getAnnotation(Since.class);
        if (null != anno && StringUtils.isNotBlank(anno.value())) {
            print("Available since " + packageIdentifier + " version " + anno.value() + "\n");
        }
    }

    protected void printDescription() {
        if (StringUtils.isNotEmpty(description)) {
            print(description + "\n");
        }
    }

    public String getOutput() {
        return output.toString();
    }

    public Class<?> getClassForExample() {
        return classForExample;
    }

    protected void skipPython() {
        this.skipPythonErrors = true;
    }

    protected abstract void runExamples();

    protected String getMethodName(final int parentMethod) {
        return Thread.currentThread().getStackTrace()[parentMethod + 2].getMethodName();
    }

    protected String getMethodNameAsSentence(final int parentMethod) {
        final String[] words = getMethodName(parentMethod + 1).split(CAPITALS_AND_NUMBERS_REGEX);
        final StringBuilder sentence = new StringBuilder();
        for (final String word : words) {
            String adaptedWord = word.toLowerCase(Locale.getDefault());
            if ("rdd".equals(adaptedWord)) {
                adaptedWord = "RDD";
            }
            sentence.append(adaptedWord)
                    .append(" ");
        }
        sentence.replace(0, 1, sentence.substring(0, 1).toUpperCase(Locale.getDefault()));
        sentence.replace(sentence.length() - 1, sentence.length(), "");
        return sentence.toString();
    }

    protected Pair<String, String> getTypeValue(final Object value) {
        Pair<String, String> typeValue = new Pair<>();
        if (!(value instanceof Tuple) || value instanceof MapTuple) {
            if (null == value) {
                typeValue.setFirst("");
                typeValue.setSecond("null");
            } else {
                typeValue.setFirst(value.getClass().getName());
                if (value instanceof Iterable) {
                    final StringBuilder valueStr = new StringBuilder();
                    valueStr.append("[");
                    for (final Object obj : ((Iterable) value)) {
                        if (valueStr.length() > 1) {
                            valueStr.append(", ");
                        }
                        if (null == obj) {
                            valueStr.append("null");
                        } else {
                            valueStr.append(StringEscapeUtils.escapeHtml4(obj.toString()));
                        }
                    }
                    valueStr.append("]");
                    typeValue.setSecond(valueStr.toString());
                } else {
                    typeValue.setSecond(StringEscapeUtils.escapeHtml4(value.toString()));
                }
            }
        } else {
            final StringBuilder typeBuilder = new StringBuilder("[");
            final StringBuilder valueStringBuilder = new StringBuilder("[");
            for (final Object item : (Tuple) value) {
                if (null == item) {
                    typeBuilder.append(" ,");
                    valueStringBuilder.append("null, ");
                } else {
                    typeBuilder.append(item.getClass().getName());
                    typeBuilder.append(", ");

                    if (item instanceof Iterable) {
                        valueStringBuilder.append(Lists.newArrayList((Iterable) item));
                    } else {
                        valueStringBuilder.append(item);
                    }

                    valueStringBuilder.append(", ");
                }
            }
            typeValue.setFirst(typeBuilder.substring(0, typeBuilder.length() - 2) + "]");
            typeValue.setSecond(StringEscapeUtils.escapeHtml4(valueStringBuilder.substring(0, valueStringBuilder.length() - 2) + "]"));
        }
        return typeValue;
    }

    protected void printJavaJsonPython(final Object obj, final String java) {
        print(WalkthroughStrSubstitutor.START_JAVA_CODE_MARKER);
        print(java);
        print(WalkthroughStrSubstitutor.JSON_CODE_MARKER);
        print(DocUtil.getJson(obj));
        print(WalkthroughStrSubstitutor.FULL_JSON_CODE_MARKER);
        print(DocUtil.getFullJson(obj));

        final String python = DocUtil.getPython(obj, skipPythonErrors);
        if (StringUtils.isNoneBlank(python)) {
            print(WalkthroughStrSubstitutor.PYTHON_CODE_MARKER);
            print(python);
        }

        print(WalkthroughStrSubstitutor.END_MARKER_MARKER);
    }

    protected void printJavaJsonPython(final Object obj, final int parentMethodIndex) {
        printJavaJsonPython(obj, getJavaSnippet(parentMethodIndex));
    }

    protected String getJavaSnippet(final int parentMethodIndex) {
        return JavaSourceUtil.getRawJavaSnippet(getClass(), null, " " + getMethodName(parentMethodIndex) + "() {", String.format("---%n"), "// ----");
    }

    protected void printJava(final String java) {
        print("\n\n```java");
        print(java);
        print("```\n");
    }

    protected void print(final String message) {
        output.append(message).append("\n");
    }
}
