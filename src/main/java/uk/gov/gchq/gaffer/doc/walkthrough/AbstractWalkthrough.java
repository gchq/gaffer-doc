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

import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.cache.impl.HashMapCacheService;
import uk.gov.gchq.gaffer.cache.util.CacheProperties;
import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.data.generator.ElementGenerator;
import uk.gov.gchq.gaffer.doc.util.DocUtil;
import uk.gov.gchq.gaffer.federatedstore.FederatedStoreProperties;
import uk.gov.gchq.gaffer.graph.GraphConfig;
import uk.gov.gchq.gaffer.mapstore.MapStoreProperties;
import uk.gov.gchq.gaffer.mapstore.SingleUseMapStore;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.store.StoreProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWalkthrough {
    protected static final String DESCRIPTION_LOG_KEY = "description";
    protected final Class<? extends ElementGenerator> elementGenerator;
    protected final String dataPath;
    protected final String schemaPath;
    protected final String walkthroughId;
    protected final String storePropertiesPath;
    protected final String graphConfigPath;

    private final Map<String, StringBuilder> logCache = new HashMap<>();
    private final String exampleId;
    private final String header;

    protected boolean cacheLogs;

    public AbstractWalkthrough(final String header,
                               final String resourcePrefix,
                               final Class<? extends ElementGenerator> generatorClass,
                               final String walkthroughId) {
        this.header = header;
        this.dataPath = resourcePrefix + "/data.txt";
        this.schemaPath = resourcePrefix + "/schema";
        this.storePropertiesPath = resourcePrefix + "/store.properties";
        this.graphConfigPath = resourcePrefix + "/graphConfig.json";
        this.elementGenerator = generatorClass;
        this.walkthroughId = walkthroughId;
        this.exampleId = getClass().getSimpleName();
    }

    public abstract Object run() throws Exception;

    protected GraphConfig getDefaultGraphConfig() {
        return new GraphConfig.Builder()
                .graphId(getClass().getSimpleName())
                .build();
    }

    public static StoreProperties getDefaultStoreProperties() {
        return getMapStoreProperties();
    }

    public static StoreProperties getMapStoreProperties() {
        final MapStoreProperties properties = new MapStoreProperties();
        properties.setStoreClass(SingleUseMapStore.class);
        properties.set(CacheProperties.CACHE_SERVICE_CLASS, HashMapCacheService.class.getName());
        properties.setJobTrackerEnabled(true);
        properties.setOperationDeclarationPaths("sparkAccumuloOperationsDeclarations.json,ResultCacheExportOperations.json,ExportToOtherGraphOperationDeclarations.json,ScoreOperationChainDeclaration.json");
        return properties;
    }

    protected StoreProperties getFederatedStoreProperties() {
        final FederatedStoreProperties properties = new FederatedStoreProperties();
        properties.setCacheProperties(HashMapCacheService.class.getName());
        return properties;
    }

    protected String substituteParameters(final String walkthrough) {
        return substituteParameters(walkthrough, false);
    }

    protected String substituteParameters(final String walkthrough, final boolean skipValidation) {
        final String walkthroughFormatted = WalkthroughStrSubstitutor.substitute(walkthrough, this);
        if (!skipValidation) {
            WalkthroughStrSubstitutor.validateSubstitution(walkthroughFormatted);
        }
        return walkthroughFormatted;
    }

    public String getWalkthroughId() {
        return walkthroughId;
    }

    public void print(final String message) {
        print(DESCRIPTION_LOG_KEY, message);
    }

    public void print(final String key, final String message) {
        if (cacheLogs) {
            StringBuilder logs = logCache.get(key);
            if (null == logs) {
                logs = new StringBuilder();
                logCache.put(key, logs);
            } else {
                logs.append("\n");
            }
            logs.append(message);
        } else {
            System.out.println(message);
        }
    }

    public void printJson(final String key, final Object obj) {
        print(key + "_JSON", DocUtil.getJson(obj));
        print(key + "_FULL_JSON", DocUtil.getFullJson(obj));
    }

    public void printJsonAndPython(final String key, final Object obj) {
        printJsonAndPython(key, obj, null);
    }

    public void printJsonAndPython(final String key, final Object obj, final Boolean skipPythonErrors) {
        printJson(key, obj);
        print(key + "_PYTHON", DocUtil.getPython(obj, skipPythonErrors));
    }

    public void printJsonAndPythonWithClass(final String key, final Object obj) {
        printJsonAndPythonWithClass(key, obj, null);
    }

    public void printJsonAndPythonWithClass(final String key, final Object obj, final Boolean skipPythonErrors) {
        printJson(key, obj);
        print(key + "_PYTHON", DocUtil.getPython(obj, obj.getClass(), skipPythonErrors));
    }

    public Map<String, StringBuilder> getLogCache() {
        return logCache;
    }

    public String walkthrough() throws OperationException {
        cacheLogs = true;
        final String walkthrough;
        try (final InputStream stream = StreamUtil.openStream(getClass(), walkthroughId + "/walkthrough/" + exampleId + ".md")) {
            if (null == stream) {
                throw new RuntimeException("Missing walkthrough file");
            }
            walkthrough = new String(IOUtils.toByteArray(stream), CommonConstants.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        final String formattedWalkthrough = substituteParameters(walkthrough);
        cacheLogs = false;

        return formattedWalkthrough;
    }

    public String getHeader() {
        return header;
    }
}
