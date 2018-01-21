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
import io.undertow.util.Headers;
import jux.BodyWriters;
import jux.Exchange;
import jux.Handler;
import jux.Response;

class UndertowHttpExchangeHandler implements HttpHandler {

    private jux.Handler handler;

    UndertowHttpExchangeHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Exchange juxExchange = new Exchange();
        juxExchange.request(new UndertowRequest(exchange));

        handler.handle(juxExchange);

        Response r = juxExchange.response();
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, r.getMediaType());
        exchange.getResponseSender().send(BodyWriters.forMediaType(r.getMediaType()).write(r.getBody()));
    }
}
