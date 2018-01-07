package jux;

/**
 * Mock server implementation. Does nothing.
 */
public class MockServer implements Server {
    @Override
    public Server listenOn(int port) {
        return this;
    }

    @Override
    public Server use(Router router) {
        return this;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
