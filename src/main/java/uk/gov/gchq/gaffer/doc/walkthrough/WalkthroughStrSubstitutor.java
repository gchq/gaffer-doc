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
package uk.gov.gchq.gaffer.doc.walkthrough;

import com.google.common.base.CaseFormat;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import uk.gov.gchq.gaffer.accumulostore.AccumuloStore;
import uk.gov.gchq.gaffer.accumulostore.MockAccumuloStore;
import uk.gov.gchq.gaffer.accumulostore.key.AccumuloKeyPackage;
import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.data.element.Properties;
import uk.gov.gchq.gaffer.data.element.function.ElementTransformer;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.data.elementdefinition.view.ViewElementDefinition;
import uk.gov.gchq.gaffer.data.generator.ElementGenerator;
import uk.gov.gchq.gaffer.data.generator.ObjectGenerator;
import uk.gov.gchq.gaffer.doc.util.JavaSourceUtil;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.data.generator.EntityIdExtractor;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.export.resultcache.ExportToGafferResultCache;
import uk.gov.gchq.gaffer.operation.impl.export.set.ExportToSet;
import uk.gov.gchq.gaffer.operation.impl.export.set.GetSetExport;
import uk.gov.gchq.gaffer.operation.impl.generate.GenerateObjects;
import uk.gov.gchq.gaffer.operation.impl.get.GetAdjacentIds;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.store.Store;
import uk.gov.gchq.gaffer.store.schema.Schema;
import uk.gov.gchq.gaffer.user.User;
import uk.gov.gchq.koryphe.impl.binaryoperator.Sum;
import uk.gov.gchq.koryphe.impl.predicate.Exists;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public abstract class WalkthroughStrSubstitutor {
    public static final String REF_URL_PREFIX = "ref://";
    public static final String PARENT_URL = "../";
    public static final String JAVA_DOC_URL_PREFIX = "javadoc/gaffer/";
    public static final String KORYPHE_JAVA_DOC_URL_PREFIX = "javadoc/koryphe/";
    public static final String GITHUB_URL_PREFIX = "https://github.com/gchq/gaffer-doc/blob/master";
    public static final String GAFFER_GITHUB_URL_PREFIX = "https://github.com/gchq/Gaffer/blob/master";
    public static final String DOC_URL_PREFIX = "../";
    public static final String JAVA_SRC_PATH = "/src/main/java/";
    public static final String RESOURCES_SRC_PATH = "/src/main/resources/";

    public static final String START_JAVA_CODE_MARKER = "\n{% codetabs name=\"Java\", type=\"java\" -%}";
    public static final String JAVA_CODE_MARKER = "\n{%- language name=\"Java\", type=\"java\" -%}";

    public static final String START_JSON_CODE_MARKER = "\n{% codetabs name=\"JSON\", type=\"json\" -%}";
    public static final String JSON_CODE_MARKER = "\n{%- language name=\"JSON\", type=\"json\" -%}";
    public static final String FULL_JSON_CODE_MARKER = "\n{%- language name=\"Full JSON\", type=\"json\" -%}";

    public static final String START_PYTHON_CODE_MARKER = "\n{% codetabs name=\"Python\", type=\"python\" -%}";
    public static final String PYTHON_CODE_MARKER = "\n{%- language name=\"Python\", type=\"py\" -%}";

    public static final String END_MARKER_MARKER = "{%- endcodetabs %}\n";

    public static String substitute(final String walkthrough, final AbstractWalkthrough example) {
        return substitute(walkthrough, createParameterMapForExample(walkthrough, example));
    }

    public static String substitute(final String walkthrough) {
        return substitute(walkthrough, createParameterMap());
    }

    public static String substitute(final String walkthrough, final Map<String, String> paramMap) {
        return new StrSubstitutor(paramMap).replace(walkthrough);
    }

    public static void validateSubstitution(final String walkthrough, final String... allowed) {
        final int startIndex = walkthrough.indexOf("${");
        final List<String> allowedList = Arrays.asList(allowed);

        if (startIndex > -1) {
            final String tmp = walkthrough.substring(startIndex + 2);
            final int endIndex = tmp.indexOf("}");
            if (endIndex > -1 && !allowedList.contains(tmp.substring(0, endIndex))) {
                throw new RuntimeException("Parameter was not substituted: " + tmp.substring(0, endIndex));
            }
        }
    }

    public static Map<String, String> createParameterMapForExample(final String text, final AbstractWalkthrough example) {
        final Class<?> exampleClass = example.getClass();
        final Map<String, String> params = new HashMap<>();
        putParam(params, "HEADER", "# " + example.getHeader());
        putParam(params, "CODE_LINK", "The code for this example is " + getGitHubCodeLink(example.getClass(), "") + ".");
        putParam(params, "DATA", getBlockFromResource(example.dataPath, exampleClass));
        if (null != example.elementGenerator) {
            putParam(params, "ELEMENT_GENERATOR_JAVA", JavaSourceUtil.getJava(example.elementGenerator.getName(), null));
        }
        putParam(params, "STORE_PROPERTIES", getPropertiesBlockFromResource(example.storePropertiesPath, exampleClass));
        putParam(params, "GRAPH_CONFIG", getJsonBlockFromResource(example.graphConfigPath, exampleClass));
        putParam(params, "ELEMENTS_SCHEMA_LINK", getGitHubResourcesLink(example.schemaPath + "/elements.json", example.walkthroughId));
        putParam(params, "TYPES_SCHEMA_LINK", getGitHubResourcesLink(example.schemaPath + "/types.json", example.walkthroughId));
        putParam(params, "AGGREGATION_LINK", getGitHubResourcesLink(example.schemaPath + "/aggregation.json", example.walkthroughId));
        putParam(params, "STORE_PROPERTIES_LINK", getGitHubResourcesLink(example.storePropertiesPath, example.walkthroughId));
        putParam(params, "ELEMENTS_JSON", getJsonBlockFromResource(example.schemaPath + "/elements.json", exampleClass));
        putParam(params, "TYPES_JSON", getJsonBlockFromResource(example.schemaPath + "/types.json", exampleClass));
        putParam(params, "AGGREGATION_JSON", getJsonBlockFromResource(example.schemaPath + "/aggregation.json", exampleClass));
        putParam(params, "VALIDATION_JSON", getJsonBlockFromResource(example.schemaPath + "/validation.json", exampleClass));

        try {
            example.run();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        for (final Map.Entry<String, StringBuilder> log : example.getLogCache().entrySet()) {
            putParam(params, log.getKey(), log.getValue().toString() + "\n");
        }

        int position = 0;
        final int length = text.length();
        while (position < length) {
            final int startIndex = text.indexOf("${", position);
            position = length;
            if (startIndex > -1) {
                final String tmp = text.substring(startIndex + 2);
                final int endIndex = tmp.indexOf("}");
                if (endIndex > -1) {
                    position = startIndex + endIndex + 3;
                    final String param = tmp.substring(0, endIndex);
                    if (param.endsWith("_SNIPPET")) {
                        final String textId = param.replace("_SNIPPET", "").replaceAll("_", " ").toLowerCase(Locale.getDefault());
                        putParam(params, param, JavaSourceUtil.getJavaSnippet(example.getClass(), null, textId));
                    }
                }
            }
            putParam(params, "VALIDATION_JSON", getJsonBlockFromResource(example.schemaPath + "/validation.json", exampleClass));
        }

        putAllParams(params, createParameterMap());
        return params;
    }

    public static void putJavaDocParam(final Map<String, String> params, final Class<?> clazz) {
        final String name = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, clazz.getSimpleName()) + "_JAVADOC";
        putParam(params, name, getJavaDocLink(clazz));
        for (int i = 0; i < 4; i++) {
            putParam(params, name + i, getJavaDocLink(clazz, true, i));
        }
    }

    public static void putParam(final Map<String, String> params, final String data, final String value) {
        if (null != value) {
            params.put(data, value);
        }
    }

    public static void putAllParams(final Map<String, String> params, final Map<String, String> paramsToAdd) {
        for (final Map.Entry<String, String> entry : paramsToAdd.entrySet()) {
            putParam(params, entry.getKey(), entry.getValue());
        }
    }

    public static Map<String, String> createParameterMap() {
        final Map<String, String> params = new HashMap<>();

        putParam(params, "START_JAVA_CODE", START_JAVA_CODE_MARKER);
        putParam(params, "JAVA_CODE", JAVA_CODE_MARKER);
        putParam(params, "START_JSON_CODE", START_JSON_CODE_MARKER);
        putParam(params, "JSON_CODE", JSON_CODE_MARKER);
        putParam(params, "FULL_JSON_CODE", FULL_JSON_CODE_MARKER);
        putParam(params, "START_PYTHON_CODE", START_PYTHON_CODE_MARKER);
        putParam(params, "PYTHON_CODE", PYTHON_CODE_MARKER);
        putParam(params, "END_CODE", END_MARKER_MARKER);
        putJavaDocParam(params, Edge.class);
        putJavaDocParam(params, User.class);
        putJavaDocParam(params, Store.class);
        putJavaDocParam(params, AccumuloStore.class);
        putJavaDocParam(params, MockAccumuloStore.class);
        putJavaDocParam(params, Graph.class);
        putJavaDocParam(params, ElementGenerator.class);
        putJavaDocParam(params, ObjectGenerator.class);
        putJavaDocParam(params, Element.class);
        putJavaDocParam(params, Schema.class);
        putJavaDocParam(params, Properties.class);
        putJavaDocParam(params, AddElements.class);
        putJavaDocParam(params, Operation.class);
        putJavaDocParam(params, GetElements.class);
        putJavaDocParam(params, View.class);
        putJavaDocParam(params, Sum.class);
        putJavaDocParam(params, Exists.class);
        putJavaDocParam(params, ViewElementDefinition.class);
        putJavaDocParam(params, ElementTransformer.class);
        putJavaDocParam(params, Function.class);
        putJavaDocParam(params, GetAdjacentIds.class);
        putJavaDocParam(params, GenerateObjects.class);
        putJavaDocParam(params, EntityIdExtractor.class);
        putJavaDocParam(params, GetSetExport.class);
        putJavaDocParam(params, ExportToSet.class);
        putJavaDocParam(params, ExportToGafferResultCache.class);
        putParam(params, "ACCUMULO_USER_GUIDE", "[Accumulo Store User Guide](../../stores/accumulo-store)");
        putParam(params, "ACCUMULO_KEY_PACKAGE", getGafferGitHubCodeLink(AccumuloKeyPackage.class, "store-implementations/accumulo-store"));
        putParam(params, "OPERATION_EXAMPLES_LINK", "[Operation Examples](../operations/contents)");

        return params;
    }

    public static String getJsonBlockFromResource(final String resourcePath, final Class<?> clazz) {
        return getBlockFromResource(resourcePath, clazz, "json");
    }

    public static String getBlockFromResource(final String resourcePath, final Class<?> clazz) {
        return getBlockFromResource(resourcePath, clazz, "");
    }

    public static String getPropertiesBlockFromResource(final String resourcePath, final Class<?> clazz) {
        final String resource = getResource(resourcePath, clazz);
        return null == resource ? null : "\n```properties\n" + resource.replaceAll("#.*\\n", "") + "\n```\n";

    }

    private static String getBlockFromResource(final String resourcePath, final Class<?> clazz, final String type) {
        final String resource = getResource(resourcePath, clazz);
        return null == resource ? null : "\n```" + type + "\n" + resource + "\n```\n";
    }

    public static String getResource(final String resourcePath, final Class<?> clazz) {
        String resource;
        try (final InputStream stream = StreamUtil.openStream(clazz, resourcePath)) {
            if (null == stream) {
                throw new IllegalArgumentException("Resource was not found: " + resourcePath);
            } else {
                resource = new String(IOUtils.toByteArray(stream), CommonConstants.UTF_8);
            }
        } catch (final Exception e) {
            resource = null;
            // ignore
        }
        return resource;
    }

    public static String getJavaDocLink(final Class<?> clazz) {
        return getJavaDocLink(clazz, true);
    }

    public static String getJavaDocLink(final Class<?> clazz, final boolean simpleName) {
        return getJavaDocLink(clazz, simpleName, 1);
    }

    public static String getJavaDocLink(final Class<?> clazz, final boolean simpleName, final int nestedDepth) {
        final String javaDocUrlPrefix;
        if (clazz.getName().contains("uk.gov.gchq.koryphe")) {
            javaDocUrlPrefix = KORYPHE_JAVA_DOC_URL_PREFIX;
        } else {
            javaDocUrlPrefix = JAVA_DOC_URL_PREFIX;
        }

        final String fullJavaDocUrlPrefix = REF_URL_PREFIX + StringUtils.repeat(PARENT_URL, nestedDepth) + javaDocUrlPrefix;

        final String displayName = simpleName ? clazz.getSimpleName() : clazz.getName();
        return "[" + displayName + "](" + fullJavaDocUrlPrefix + clazz.getName().replaceAll("\\.", "/") + ".html)";
    }

    public static String getGitHubResourcesLink(final String resourcePath, final String modulePath) {
        final String resourceName = resourcePath.substring(resourcePath.lastIndexOf("/") + 1, resourcePath.length());
        if (StringUtils.isEmpty(modulePath)) {
            return "[" + resourceName + "](" + GITHUB_URL_PREFIX + RESOURCES_SRC_PATH + resourcePath + ")";
        }
        return "[" + resourceName + "](" + GITHUB_URL_PREFIX + "/" + modulePath + RESOURCES_SRC_PATH + resourcePath + ")";

    }

    public static String getGitHubPackageLink(final String displayName, final String packagePath, final String modulePath) {
        if (StringUtils.isEmpty(modulePath)) {
            return "[" + displayName + "](" + GITHUB_URL_PREFIX + JAVA_SRC_PATH + packagePath.replaceAll("\\.", "/") + ")";
        }
        return "[" + displayName + "](" + GITHUB_URL_PREFIX + "/" + modulePath + JAVA_SRC_PATH + packagePath.replaceAll("\\.", "/") + ")";
    }

    public static String getGitHubCodeLink(final Class<?> clazz, final String modulePath) {
        return getGitHubCodeLink(clazz.getName(), modulePath);
    }

    public static String getGitHubCodeLink(final String className, final String modulePath) {
        final String simpleClassName = className.substring(className.lastIndexOf(".") + 1, className.length());
        if (StringUtils.isEmpty(modulePath)) {
            return "[" + simpleClassName + "](" + GITHUB_URL_PREFIX + JAVA_SRC_PATH + className.replaceAll("\\.", "/") + ".java)";
        }

        return "[" + simpleClassName + "](" + GITHUB_URL_PREFIX + "/" + modulePath + JAVA_SRC_PATH + className.replaceAll("\\.", "/") + ".java)";
    }

    public static String getGitHubFileLink(final String displayName, final String path) {
        return "[" + displayName + "](" + GITHUB_URL_PREFIX + "/" + path + ")";
    }


    public static String getGafferGitHubResourcesLink(final String resourcePath, final String modulePath) {
        final String resourceName = resourcePath.substring(resourcePath.lastIndexOf("/") + 1, resourcePath.length());
        if (StringUtils.isEmpty(modulePath)) {
            return "[" + resourceName + "](" + GAFFER_GITHUB_URL_PREFIX + RESOURCES_SRC_PATH + resourcePath + ")";
        }
        return "[" + resourceName + "](" + GAFFER_GITHUB_URL_PREFIX + "/" + modulePath + RESOURCES_SRC_PATH + resourcePath + ")";

    }

    public static String getGafferGitHubPackageLink(final String displayName, final String packagePath, final String modulePath) {
        if (StringUtils.isEmpty(modulePath)) {
            return "[" + displayName + "](" + GAFFER_GITHUB_URL_PREFIX + JAVA_SRC_PATH + packagePath.replaceAll("\\.", "/") + ")";
        }
        return "[" + displayName + "](" + GAFFER_GITHUB_URL_PREFIX + "/" + modulePath + JAVA_SRC_PATH + packagePath.replaceAll("\\.", "/") + ")";
    }

    public static String getGafferGitHubCodeLink(final Class<?> clazz, final String modulePath) {
        return getGafferGitHubCodeLink(clazz.getName(), modulePath);
    }

    public static String getGafferGitHubCodeLink(final String className, final String modulePath) {
        final String simpleClassName = className.substring(className.lastIndexOf(".") + 1, className.length());
        if (StringUtils.isEmpty(modulePath)) {
            return "[" + simpleClassName + "](" + GAFFER_GITHUB_URL_PREFIX + JAVA_SRC_PATH + className.replaceAll("\\.", "/") + ".java)";
        }

        return "[" + simpleClassName + "](" + GAFFER_GITHUB_URL_PREFIX + "/" + modulePath + JAVA_SRC_PATH + className.replaceAll("\\.", "/") + ".java)";
    }

    public static String getGafferGitHubFileLink(final String displayName, final String path) {
        return "[" + displayName + "](" + GAFFER_GITHUB_URL_PREFIX + "/" + path + ")";
    }


}
