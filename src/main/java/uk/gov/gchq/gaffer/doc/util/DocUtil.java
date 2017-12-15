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

import uk.gov.gchq.gaffer.cache.CacheServiceLoader;
import uk.gov.gchq.gaffer.cache.exception.CacheOperationException;

import java.util.Locale;

public final class DocUtil {
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
}
