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

import jux.BodyReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JacksonBodyReaderTest {

    private BodyReader reader = new JacksonBodyReader();

    @Test
    void testSupportedMediaTypes() {
        assertThat(reader.supportedMediaTypes())
                .hasSize(1)
                .containsExactlyInAnyOrder("application/json");
    }

    @Test
    void testReadsJsonStringIntoPojo() throws Exception {
        String json = "{\"stringValue\":\"str\",\"intValue\":1}";
        try (ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            TestPojo result = reader.read(in, TestPojo.class);
            assertThat(result).isEqualTo(new TestPojo("str", 1));
        }
    }

    @Test
    void testNullInputStreamCausesError() throws Exception {
        assertThatThrownBy(() -> reader.read(null, TestPojo.class)).isInstanceOf(IOException.class);
    }

    @Test
    void testEmptyInputStreamCausesIOException() {
        assertThatThrownBy(() -> {
            try (ByteArrayInputStream in = new ByteArrayInputStream(new byte[0])) {
                TestPojo result = reader.read(in, TestPojo.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).hasCauseInstanceOf(IOException.class);
    }
}