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

import static org.assertj.core.api.Assertions.assertThat;

class PlainTextBodyWriterTest {

    private PlainTextBodyWriter writer = new PlainTextBodyWriter();

    @Test
    void supportsOnlyPlainText() {
        assertThat(writer.supportedMediaTypes())
                .hasSize(1)
                .containsExactlyInAnyOrder("text/plain");
    }

    @Test
    void writesStringCorrectly() {
        String result = null;
        try {
            result = writer.write("hello");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        assertThat(result).isEqualTo("hello");
    }

    @Test
    void writesEmptyStringCorrectly() {
        try {
            assertThat(writer.write("")).isEqualTo("");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void writesNullAsEmptyString() {
        try {
            assertThat(writer.write(null)).isEqualTo("");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}