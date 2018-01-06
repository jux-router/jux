package jux;

import io.undertow.server.HttpServerExchange;

class UndertowRequest<T> implements Request<T> {

    private HttpServerExchange exchange;

    public UndertowRequest(HttpServerExchange exchange) {
        this.exchange = exchange;
    }
}
