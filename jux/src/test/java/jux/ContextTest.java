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

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ContextTest {

    private Context context = new Context();

    @Test
    void testContextIsNotCancelledInitially() {
        assertThat(context.isCancelled()).isFalse();
    }

    @Test
    void testContextCanBeCancelled() {
        context.cancel();
        assertThat(context.isCancelled()).isTrue();
    }

    @Test
    void testStoreValueInContext() {
        context.set("test", "hello world");
        Object result = context.get("test").orElse(null);
        assertThat(result).isEqualTo("hello world");
    }

    @Test
    void testStoreValueInContextWithType() {
        context.set("test", "hello world");
        String result = context.get("test", String.class).orElse(null);
        assertThat(result).isEqualTo("hello world");
    }

    @Test
    void testGetNonExistentValue() {
        assertThat(context.get("test").isPresent()).isFalse();
    }

    @Test
    void testGetNonExistentValueWithType() {
        assertThat(context.get("test", String.class).isPresent()).isFalse();
    }
}