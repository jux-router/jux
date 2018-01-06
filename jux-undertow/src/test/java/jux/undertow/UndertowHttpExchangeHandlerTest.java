package jux.undertow;

import io.undertow.server.HttpServerExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UndertowHttpExchangeHandlerTest {

    @Test
    void simplestRequestIsReturningCorrectly() {
        HttpServerExchange exchange = new HttpServerExchange(null, 0);
        HttpServerExchange spy = Mockito.spy(exchange);

    }
}