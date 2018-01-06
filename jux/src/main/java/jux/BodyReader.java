package jux;

import java.io.InputStream;

/**
 * The {@code BodyReader} reads the body of an input into a specified class.
 */
public interface BodyReader {

    /**
     * Returns all supported media types.
     *
     * @return true if it is supported
     */
    Iterable<String> supportedMediaTypes();

    /**
     * Read the input.
     *
     * @param is          the input
     * @param returnClass the type of the return value
     * @param <T>         the return value
     * @return the populated request object
     */
    <T> T read(InputStream is, Class<T> returnClass);

}
