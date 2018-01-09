package jux.undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import jux.BodyWriters;
import jux.Handler;
import jux.Response;

class UndertowHttpExchangeHandler implements HttpHandler {

    private jux.Handler handler;

    UndertowHttpExchangeHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Response r = handler.handle(null, new UndertowRequest(exchange));
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, r.getMediaType());
        exchange.getResponseSender().send(BodyWriters.forMediaType(r.getMediaType()).write(r.getBody()));
    }
}
