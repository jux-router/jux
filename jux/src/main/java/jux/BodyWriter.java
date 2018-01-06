package jux;

import java.util.Collection;

public interface BodyWriter {

    Collection<String> supportedMediaTypes();

    String write(Object o);

}
