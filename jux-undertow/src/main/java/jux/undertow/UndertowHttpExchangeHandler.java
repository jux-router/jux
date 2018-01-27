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

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import jux.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

class UndertowHttpExchangeHandler implements HttpHandler {

    private static final Logger LOG =
            LogManager.getLogger(UndertowHttpExchangeHandler.class);

    private jux.Handler handler;

    UndertowHttpExchangeHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Exchange juxExchange = new Exchange();
        juxExchange.request(new UndertowRequest(exchange));

        handler.handle(juxExchange);

        Response response = juxExchange.response();
        populateHeaders(exchange, response);
        writePayload(exchange, response);
    }

    private void populateHeaders(HttpServerExchange exchange, Response resp) {
        HeaderMap headers = exchange.getResponseHeaders();

        resp.getHeaders().asMap().forEach(
                (key, value) ->
                        headers.addAll(HttpString.tryFromString(key), value));

        LOG.trace("Using Content-Type {}", resp.getMediaType());
        headers.put(Headers.CONTENT_TYPE, resp.getMediaType());
    }

    private void writePayload(HttpServerExchange exchange, Response resp)
            throws IOException {
        if (resp.hasPayload()) {
            LOG.trace("Writing body");
            BodyWriter writer = BodyWriters.forMediaType(resp.getMediaType());
            exchange.getResponseSender().send(writer.write(resp.getPayload()));
        } else {
            LOG.trace("No body to wrote, exchange is ending.");
            exchange.endExchange();
        }
    }
}
