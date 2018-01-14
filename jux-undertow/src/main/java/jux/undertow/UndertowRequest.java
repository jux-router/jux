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
package jux.undertow;

import io.undertow.server.HttpServerExchange;
import jux.BodyReaders;
import jux.Request;

import java.io.IOException;
import java.util.Deque;
import java.util.Optional;

class UndertowRequest implements Request {

    private HttpServerExchange exchange;

    UndertowRequest(HttpServerExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public Optional<String> getParam(String param) {
        return Optional
                .ofNullable(exchange.getQueryParameters().get(param))
                .map(Deque::getFirst);
    }

    @Override
    public <T> T getBody(Class<T> returnClass) {
        try {
            exchange.startBlocking();
            return BodyReaders.forMediaType(getMediaType()).read(exchange.getInputStream(), returnClass);
        } catch (IOException e) {
            // TODO handle this exception properly! This should probably be a 4xx
            throw new RuntimeException(e);
        }
    }

    private String getMediaType() {
        return exchange.getRequestHeaders().getFirst("Content-Type");
    }
}
