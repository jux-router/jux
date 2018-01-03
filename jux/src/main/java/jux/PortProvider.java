package jux;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Supplier;

public class PortProvider implements Supplier<Integer> {

    private Integer originalPort;

    public PortProvider(Integer originalPort) {
        this.originalPort = originalPort;
    }

    @Override
    public Integer get() {
        if (0 < originalPort) {
            return originalPort;
        }

        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot aquire random local port.",
                    e);
        }
    }
}
