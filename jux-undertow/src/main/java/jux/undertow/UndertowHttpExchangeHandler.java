package jux.undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import jux.BodyReader;
import jux.BodyWriter;
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
    }
}
