package jux.bodyparser.plain;

import jux.BodyWriter;

import java.util.Collection;
import java.util.List;

public class PlainTextBodyWriter implements BodyWriter {
    @Override
    public Collection<String> supportedMediaTypes() {
        return List.of("text/plain");
    }

    @Override
    public String write(Object o) {
        return null != o ? o.toString() : "";
    }
}
