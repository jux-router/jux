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
package jux.test;

import org.apache.http.client.methods.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

/**
 * {@link ArgumentsProvider} to provide new {@link HttpUriRequest} instances
 * for GET, POST, PUT, PATCH and DELETE requests, which can be populated later
 * with correct URL and request informations.
 */
public class RequestProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(
            ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(new HttpGet()),
                Arguments.of(new HttpPost()),
                Arguments.of(new HttpPut()),
                Arguments.of(new HttpPatch()),
                Arguments.of(new HttpDelete())
        );
    }
}
