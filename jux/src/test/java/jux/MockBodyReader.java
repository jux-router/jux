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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * Simple mock {@link BodyReader} implementation.
 */
public class MockBodyReader implements BodyReader {
    @Override
    public Collection<String> supportedMediaTypes() {
        return List.of("text/plain");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public <T> T read(InputStream is, Class<T> returnClass) throws IOException {
        return null;
    }
}
