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

import static java.util.stream.Collectors.toMap;

public class BodyWriters {

    private static final Logger LOG = LogManager.getLogger(BodyWriters.class);
    private static final Map<String, BodyWriter> writers;

    static {
        ServiceLoader<BodyWriter> loader = ServiceLoader.load(BodyWriter.class);
        writers = loader.stream()
                .map(ServiceLoader.Provider::get)
                .flatMap(r -> r.supportedMediaTypes().stream()
                        .map(m -> new AbstractMap.SimpleEntry<>(m, r)))
                .peek(e -> LOG.debug("Register {} to handle {}", e.getValue().getClass(), e.getKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static BodyWriter forMediaType(String mediaType) {
        if (writers.containsKey(mediaType)) {
            return writers.get(mediaType);
        }
        String msg = String.format("No body writer present for media type %s",
                mediaType);
        throw new IllegalStateException(msg);
    }

    /** Private constructor ensures no instantiation. **/
    private BodyWriters() {}
}
