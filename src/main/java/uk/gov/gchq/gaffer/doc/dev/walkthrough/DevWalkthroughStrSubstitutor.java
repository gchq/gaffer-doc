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
package uk.gov.gchq.gaffer.doc.dev.walkthrough;

import uk.gov.gchq.gaffer.doc.dev.aggregator.VisibilityAggregator;
import uk.gov.gchq.gaffer.doc.dev.serialiser.VisibilitySerialiser;
import uk.gov.gchq.gaffer.doc.walkthrough.WalkthroughStrSubstitutor;

import java.util.HashMap;
import java.util.Map;

public abstract class DevWalkthroughStrSubstitutor extends WalkthroughStrSubstitutor {
    public static String substitute(final String text, final DevWalkthrough example) {
        return substitute(text, createParameterMap(example));
    }

    public static Map<String, String> createParameterMap(final DevWalkthrough example) {
        final Map<String, String> params = new HashMap<>();
        putParam(params, "VISIBILITY_AGGREGATOR_LINK", getGitHubCodeLink(VisibilityAggregator.class, ""));
        putParam(params, "VISIBILITY_SERIALISER_LINK", getGitHubCodeLink(VisibilitySerialiser.class, ""));
        putParam(params, "RESULT_CACHE_EXPORT_OPERATIONS", getJsonBlockFromResource("ResultCacheExportOperations.json", example.getClass()));
        putParam(params, "CACHE_STORE_PROPERTIES", getPropertiesBlockFromResource("cache-store.properties", example.getClass()));
        return params;
    }
}
