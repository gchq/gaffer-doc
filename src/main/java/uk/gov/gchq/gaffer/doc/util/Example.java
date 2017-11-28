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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.doc.walkthrough.WalkthroughStrSubstitutor;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public abstract class Example {
    public static final String CAPITALS_AND_NUMBERS_REGEX = "((?=[A-Z])|(?<=[0-9])(?=[a-zA-Z])|(?<=[a-zA-Z])(?=[0-9]))";
    public static final String DIVIDER = "-----------------------------------------------";
    public static final String METHOD_DIVIDER = DIVIDER + "\n";
    public static final String KORYPHE_JAVA_DOC_URL_PREFIX = "ref://../javadoc/koryphe/";
    public static final String JAVA_DOC_URL_PREFIX = "ref://../javadoc/gaffer/";
    public static final String SKIP_PYTHON_PROPERTY = "gaffer.doc.skipPython";
    private final Class<?> classForExample;
    private final String description;
    private StringBuilder output = new StringBuilder();

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

    protected void printJavaJsonPython(final Object obj, final String java) {
        print(WalkthroughStrSubstitutor.START_JAVA_CODE_MARKER);
        print(java);
        print(WalkthroughStrSubstitutor.JSON_CODE_MARKER);
        print(getJson(obj));

        final String python = getPython(obj);
        if (null != python) {
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

    protected String getPython(final Object object) {
        final boolean skipPythonOnError = Boolean.parseBoolean(System.getProperty(SKIP_PYTHON_PROPERTY));
        final String json = getRawJson(object);
        final ProcessBuilder pb = new ProcessBuilder("python3", "-u", "gaffer-python-shell/src/gafferpy/fromJson.py", json);

        final Process p;
        try {
            p = pb.start();
        } catch (final IOException e) {
            if (skipPythonOnError) {
                return "";
            }
            throw new RuntimeException("Unable to run python3", e);
        }

        try {
            p.waitFor();
        } catch (final InterruptedException e) {
            if (skipPythonOnError) {
                return "";
            }
            throw new RuntimeException("Python failed to complete", e);
        }

        if (p.exitValue() > 0) {
            try {
                throw new RuntimeException("Error in python: " + IOUtils.toString(p.getErrorStream()) + "\nUnable to convert json: " + json);
            } catch (final IOException e) {
                if (skipPythonOnError) {
                    return "";
                }
                throw new RuntimeException("Unable to read error from Python", e);
            }
        }
        try {
            return IOUtils.toString(p.getInputStream());
        } catch (final IOException e) {
            if (skipPythonOnError) {
                return "";
            }
            throw new RuntimeException("Unable to read result from python", e);
        }
    }

    protected String getJson(final Object object) {
        try {
            return new String(JSONSerialiser.serialise(object, true), CommonConstants.UTF_8);
        } catch (final SerialisationException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getRawJson(final Object object) {
        try {
            return new String(JSONSerialiser.serialise(object), CommonConstants.UTF_8);
        } catch (final SerialisationException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    protected void print(final String message) {
        output.append(message).append("\n");
    }
}
