package jux;

import java.util.Collection;
import java.util.List;

public class MockBodyWriter implements BodyWriter {
    @Override
    public Collection<String> supportedMediaTypes() {
        return List.of("text/plain");
    }

    @Override
    public String write(Object o) {
        return null;
    }
}
