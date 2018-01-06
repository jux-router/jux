package jux.undertow;

import io.undertow.server.HttpServerExchange;
import jux.Request;

class UndertowRequest<T> implements Request<T> {

    private HttpServerExchange exchange;

    public UndertowRequest(HttpServerExchange exchange) {
        this.exchange = exchange;
    }
}
