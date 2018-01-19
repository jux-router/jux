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
package jux.bodyparser.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Supplies an {@link ObjectMapper} instance to the {@link JacksonBodyReader} and {@link JacksonBodyWriter}.
 *
 * First checks if the {@link java.util.ServiceLoader} can load an instance of {@link ObjectMapperProvider}, if that is
 * the case, then it loads that, otherwise it supplies a default instance.
 *
 * @author Sandor Nemeth
 */
class ObjectMapperSupplier implements Supplier<ObjectMapper> {

    @Override
    public ObjectMapper get() {
        return get(ServiceLoader.load(ObjectMapperProvider.class).stream());
    }

    ObjectMapper get(Stream<ServiceLoader.Provider<ObjectMapperProvider>> providers) {
        return providers.findFirst()
                .map(provider -> provider.get().get())
                .orElseGet(this::getDefaultObjectMapper);
    }

    private ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }
}
