package jux.undertow;

import io.undertow.Undertow;
import jux.PortProvider;
import jux.Router;
import jux.Server;

public class UndertowServer implements Server {

    private UndertowRoutingConverter routingConverter;

    private Undertow.Builder undertow;
    private Undertow server;

    public UndertowServer() {
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
        server = undertow.build();
        server.start();
    }

    @Override
    public void stop() {
        server.stop();
    }
}
