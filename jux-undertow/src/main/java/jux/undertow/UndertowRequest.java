package jux.undertow;

import io.undertow.server.HttpServerExchange;
import jux.Request;

class UndertowRequest implements Request {

    private HttpServerExchange exchange;

    public UndertowRequest(HttpServerExchange exchange) {
        this.exchange = exchange;
    }

}
