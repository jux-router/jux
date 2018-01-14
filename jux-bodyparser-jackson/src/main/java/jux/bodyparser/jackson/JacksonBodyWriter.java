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
import jux.BodyWriter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * {@link BodyWriter} implementation using Jackson as a JSON renderer.
 *
 * Supports {@code application/json} media type.
 *
 * @author Sandor Nemeth
 */
public class JacksonBodyWriter implements BodyWriter {

    private ObjectMapper objectMapper;

    public JacksonBodyWriter() {
        objectMapper = new ObjectMapperSupplier().get();
    }

    @Override
    public Collection<String> supportedMediaTypes() {
        return Collections.singletonList("application/json");
    }

    @Override
    public String write(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }
}
