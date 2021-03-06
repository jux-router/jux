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
import jux.BodyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

/**
 * {@link BodyReader} implementation using Jackson as the JSON processing library.
 *
 * @author Sandor Nemeth
 */
public class JacksonBodyReader implements BodyReader {

    private ObjectMapper objectMapper;

    public JacksonBodyReader() {
        objectMapper = new ObjectMapperSupplier().get();
    }

    @Override
    public Collection<String> supportedMediaTypes() {
        return Collections.singletonList("application/json");
    }

    @Override
    public <T> T read(InputStream is, Class<T> returnClass) throws IOException {
        return objectMapper.readValue(is, returnClass);
    }
}
