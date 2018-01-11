/**
 * Copyright 2018 Sandor Nemeth
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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BodyWritersTest {
    @Test
    void getsSupportedBodyWriter() {
        BodyWriter reader = BodyWriters.forMediaType("text/plain");
        Assertions.assertThat(reader)
                .isNotNull()
                .isInstanceOf(MockBodyWriter.class);
    }

    @Test
    void failsOnNotSupportedMediaType() {
        Assertions.assertThatThrownBy(() -> BodyWriters.forMediaType("a/b"))
                .isInstanceOf(IllegalStateException.class);
    }
}
