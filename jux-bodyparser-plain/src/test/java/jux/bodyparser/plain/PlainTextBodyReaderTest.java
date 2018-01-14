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
package jux.bodyparser.plain;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class PlainTextBodyReaderTest {

    private PlainTextBodyReader bodyReader = new PlainTextBodyReader();

    @Test
    void testSupportsPlainText() {
        Collection<String> supported = bodyReader.supportedMediaTypes();
        assertAll(
                () -> assertEquals(1, supported.size()),
                () -> assertTrue(supported.contains("text/plain"))
        );
    }

    @Test
    void testReadNormalBody() throws Exception {
        byte[] body = "hello".getBytes(UTF_8);
        try (ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            assertEquals("hello", bodyReader.read(in, String.class));
        }
    }

    @Test
    void testCannotHaveNullInput() {
        assertThrows(NullPointerException.class,
                () -> bodyReader.read(null, String.class));
    }

    @Test
    void testMustHaveReturnTypeSet() throws IOException {
        byte[] body = "hello".getBytes(UTF_8);
        try (ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            assertThrows(NullPointerException.class,
                    () -> bodyReader.read(in, null));
        }
    }

    @Test
    void testMustHaveStringAsReturnClass() throws IOException {
        byte[] body = "hello".getBytes(UTF_8);
        try (ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            assertThrows(IllegalArgumentException.class,
                    () -> bodyReader.read(in, Integer.class));
        }
    }
}