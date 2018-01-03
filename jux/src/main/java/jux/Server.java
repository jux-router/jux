package jux;

public interface Server {

    Server listenOn(int port);

    Server use(jux.Router router);

    void start();
}
