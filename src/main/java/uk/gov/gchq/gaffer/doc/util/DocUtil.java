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

import uk.gov.gchq.gaffer.cache.CacheServiceLoader;
import uk.gov.gchq.gaffer.cache.exception.CacheOperationException;
import uk.gov.gchq.gaffer.commonutil.CommonConstants;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.koryphe.serialisation.json.SimpleClassNameCache;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public final class DocUtil {
    public static final String SKIP_PYTHON_PROPERTY = "gaffer.doc.skipPython";

    private DocUtil() {
    }

    public static String getLink(final long index, final String name) {
        return index + ". [" + name + "](" + toMdFileName(name) + ")\n";
    }

    public static String getFullLink(final String folderPrefix, final String name) {
        return getFullLink(folderPrefix, name, name);
    }

    public static String getFullLink(final String folderPrefix, final String title, final String filename) {
        return "    * [" + title + "](" + folderPrefix + toMdFileName(filename) + ")\n";
    }

    public static String toMdFileName(final String input) {
        return toFileName(input) + ".md";
    }

    public static String toFileName(final String input) {
        if (null == input || "".equals(input)) {
            return "";
        }

        return input.trim().toLowerCase(Locale.getDefault()).replace(" ", "-");
    }

    public static String toFolderName(final String input) {
        String folderName = toFileName(input);
        if (!"".equals(folderName) && !folderName.endsWith("/")) {
            folderName += "/";
        }
        return folderName;
    }

    public static void clearCache() {
        // Clear the caches so the output is not dependent on what's been run before
        try {
            if (CacheServiceLoader.getService() != null) {
                CacheServiceLoader.getService().clearCache("NamedOperation");
                CacheServiceLoader.getService().clearCache("JobTracker");
            }
        } catch (final CacheOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getJson(final Object object) {
        final boolean fullNameOrig = SimpleClassNameCache.isUseFullNameForSerialisation();
        SimpleClassNameCache.setUseFullNameForSerialisation(false);
        try {
            return new String(JSONSerialiser.serialise(object, true), CommonConstants.UTF_8);
        } catch (final SerialisationException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } finally {
            SimpleClassNameCache.setUseFullNameForSerialisation(fullNameOrig);
        }
    }

    public static String getFullJson(final Object object) {
        final boolean fullNameOrig = SimpleClassNameCache.isUseFullNameForSerialisation();
        SimpleClassNameCache.setUseFullNameForSerialisation(true);
        try {
            return new String(JSONSerialiser.serialise(object, true), CommonConstants.UTF_8);
        } catch (final SerialisationException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } finally {
            SimpleClassNameCache.setUseFullNameForSerialisation(fullNameOrig);
        }
    }

    public static String getRawJson(final Object object) {
        final boolean fullNameOrig = SimpleClassNameCache.isUseFullNameForSerialisation();
        SimpleClassNameCache.setUseFullNameForSerialisation(true);
        try {
            return new String(JSONSerialiser.serialise(object), CommonConstants.UTF_8);
        } catch (final SerialisationException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } finally {
            SimpleClassNameCache.setUseFullNameForSerialisation(fullNameOrig);
        }
    }

    public static String getPython(final Object object) {
        return getPython(object, null, null);
    }

    public static String getPython(final Object object, final Boolean skipPythonErrors) {
        return getPython(object, null, skipPythonErrors);
    }

    public static String getPython(final Object object, final Class<?> clazz) {
        return getPython(object, clazz, null);
    }

    public static String getPython(final Object object, final Class<?> clazz, final Boolean skipPythonErrors) {
        final boolean skipPythonOnError = null != skipPythonErrors ? skipPythonErrors : Boolean.parseBoolean(System.getProperty(SKIP_PYTHON_PROPERTY));
        final String json = getRawJson(object);


        final ProcessBuilder pb;
        if (null == clazz) {
            pb = new ProcessBuilder("python3", "-u", "gaffer-python-shell/src/fromJson.py", json);
        } else {
            pb = new ProcessBuilder("python3", "-u", "gaffer-python-shell/src/fromJson.py", json, clazz.getName());
        }

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
            if (skipPythonOnError) {
                return "";
            }
            try {
                throw new RuntimeException("Error in python: " + IOUtils.toString(p.getErrorStream()) + "\nUnable to convert json: " + json);
            } catch (final IOException e) {
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
}
