/**
 * Copyright © 2018 Sandor Nemeth (sandor.nemeth.1986 at gmail dot com)
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
package jux;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;

/**
 * Utility class for finding {@link BodyReader body readers}.
 */
public class BodyReaders {

    private static final Logger LOG = LogManager.getLogger(BodyReaders.class);
    private static final Map<String, BodyReader> readers;

    static {
        // this is thread-safe, because we'll only read
        ServiceLoader<BodyReader> loader = ServiceLoader.load(BodyReader.class);
        LOG.debug("Loading available jux.BodyReader implementations");
        readers = loader.stream()
                .map(ServiceLoader.Provider::get)
                .flatMap(r -> r.supportedMediaTypes().stream().map(m -> new AbstractMap.SimpleEntry<>(m, r)))
                .peek(e -> LOG.debug("Registering {} to read {}", e.getKey(), e.getValue().getClass().getName()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static BodyReader forMediaType(String mediaType) {
        int charsetIndex = mediaType.indexOf(";");
        String type = charsetIndex > -1 ? mediaType.substring(0, charsetIndex) : mediaType;

        if (readers.containsKey(type)) {
            return readers.get(type);
        }
        String msg = String.format("No body reader present for media type %s", type);
        // TODO this should result in an error code - which one?
        throw new IllegalStateException(msg);
    }

    /** Private constructor ensures no instantiation. **/
    private BodyReaders() {}
}
