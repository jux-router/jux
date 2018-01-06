package jux;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

class UndertowHttpExchangeHandler implements HttpHandler {

    private jux.Handler handler;

    UndertowHttpExchangeHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        handler.handle(null, new UndertowRequest(exchange));
    }
}
