package jux;

import java.io.InputStream;
import java.util.List;

/**
 * Simple mock {@link BodyReader} implementation.
 */
public class MockBodyReader implements BodyReader {
    @Override
    public Iterable<String> supportedMediaTypes() {
        return List.of("text/plain");
    }

    @Override
    public <T> T read(InputStream is, Class<T> returnClass) {
        return null;
    }
}
