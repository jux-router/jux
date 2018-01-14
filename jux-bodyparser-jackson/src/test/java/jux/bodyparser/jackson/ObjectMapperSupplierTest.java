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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ServiceLoader;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ObjectMapperSupplierTest {

    private static final ObjectMapper SUPPLIED_INSTANCE = new ObjectMapper();
    private ObjectMapperSupplier supplier = new ObjectMapperSupplier();

    @Test
    void testReturnsDefaultObjectMapperIfNoProvidersFound() {
        Assertions.assertThat(supplier.get(Stream.empty()))
                .isNotNull()
                .isNotEqualTo(SUPPLIED_INSTANCE);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testReturnsSuppliedInstance() {
        ServiceLoader.Provider loadedProvider = mock(ServiceLoader.Provider.class);
        ObjectMapperProvider provider = mock(ObjectMapperProvider.class);
        when(loadedProvider.get()).thenReturn(provider);
        when(provider.get()).thenReturn(SUPPLIED_INSTANCE);

        Assertions.assertThat(supplier.get(Stream.of(loadedProvider)))
                .isNotNull()
                .isEqualTo(SUPPLIED_INSTANCE);
    }
}