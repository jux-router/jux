package jux.undertow;

import io.undertow.Undertow;
import jux.PortProvider;
import jux.Router;
import jux.Server;

class UndertowServer implements Server {

    private UndertowRoutingConverter routingConverter;

    private Undertow.Builder undertow;

    UndertowServer() {
        undertow = Undertow.builder();
        routingConverter = new UndertowRoutingConverter();
    }

    @Override
    public Server listenOn(int port) {
        // TODO host could be a parameter later on
        PortProvider portProvider = new PortProvider(port);
        undertow.addHttpListener(portProvider.get(), "localhost");
        return this;
    }

    @Override
    public Server use(Router router) {
        undertow.setHandler(routingConverter.convert(router));
        return this;
    }

    @Override
    public void start() {
        undertow.build().start();
    }
}
