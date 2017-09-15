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
package uk.gov.gchq.gaffer.doc.walkthrough;

import org.apache.commons.io.IOUtils;

import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.data.generator.ElementGenerator;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.gaffer.operation.OperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWalkthrough {
    protected static final String DESCRIPTION_LOG_KEY = "description";
    protected final Class<? extends ElementGenerator> elementGenerator;
    protected final String dataPath;
    protected final String schemaPath;
    protected final String modulePath;
    protected final String storePropertiesLocation;

    private final Map<String, StringBuilder> logCache = new HashMap<>();
    private final String exampleId;
    private final String header;
    private final String resourcePrefix;

    protected boolean cacheLogs;

    public AbstractWalkthrough(final String header,
                               final String dataPath,
                               final String schemaPath,
                               final Class<? extends ElementGenerator> generatorClass,
                               final String modulePath,
                               final String resourcePrefix) {
        this.header = header;
        this.dataPath = dataPath;
        this.schemaPath = schemaPath;
        this.elementGenerator = generatorClass;
        this.modulePath = modulePath;
        this.resourcePrefix = resourcePrefix;
        exampleId = getClass().getSimpleName();
        storePropertiesLocation = "/mockaccumulostore.properties";
    }

    public abstract Object run() throws Exception;

    protected String substituteParameters(final String walkthrough) {
        return substituteParameters(walkthrough, false);
    }

    protected String substituteParameters(final String walkthrough, final boolean skipValidation) {
        final String walkthroughFormatted = WalkthroughStrSubstitutor.substitute(walkthrough, this, modulePath, header, dataPath, schemaPath, elementGenerator);
        if (!skipValidation) {
            WalkthroughStrSubstitutor.validateSubstitution(walkthroughFormatted);
        }
        return walkthroughFormatted;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void log(final String message) {
        log(DESCRIPTION_LOG_KEY, message);
    }

    public void log(final String key, final String message) {
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

    public Map<String, StringBuilder> getLogCache() {
        return logCache;
    }

    public String walkthrough() throws OperationException {
        cacheLogs = true;
        final String walkthrough;
        try (final InputStream stream = StreamUtil.openStream(getClass(), resourcePrefix + "/walkthrough/" + exampleId + ".md")) {
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

    protected String getAsPython(final Object object) {
        return getAsPython(object, null);
    }

    protected String getAsPython(final Object object, final Class<?> clazz) {
        final String json = getRawJson(object);
        final ProcessBuilder pb;
        if (null == clazz) {
            pb = new ProcessBuilder("python3", "-u", "gaffer-python-shell/src/gafferpy/fromJson.py", json);
        } else {
            pb = new ProcessBuilder("python3", "-u", "gaffer-python-shell/src/gafferpy/fromJson.py", json, clazz.getName());
        }

        final Process p;
        try {
            p = pb.start();
        } catch (final IOException e) {
            throw new RuntimeException("Unable to run python3", e);
        }

        try {
            p.waitFor();
        } catch (final InterruptedException e) {
            throw new RuntimeException("Python failed to complete", e);
        }

        if (p.exitValue() > 0) {
            try {
                throw new RuntimeException("Error in python: " + IOUtils.toString(p.getErrorStream()) + "\nUnable to convert json: " + json);
            } catch (final IOException e) {
                throw new RuntimeException("Unable to read error from Python", e);
            }
        }
        final String python;
        try {
            python = IOUtils.toString(p.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException("Unable to read result from python", e);
        }

        return python;
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
}
