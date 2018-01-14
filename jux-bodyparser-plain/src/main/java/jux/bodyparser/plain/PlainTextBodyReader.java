package jux.bodyparser.plain;

import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import jux.BodyReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A {@link BodyReader body reader} to read {@code text/plain} request bodies.
 * <p>
 * This reader can only read {@link String strings}. Specifying any other class
 * will result in a {@link IllegalArgumentException}.
 *
 * @author Sandor Nemeth
 */
public class PlainTextBodyReader implements BodyReader {
    @Override
    public Collection<String> supportedMediaTypes() {
        return Lists.newArrayList("text/plain");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T read(InputStream is, Class<T> returnClass) throws IOException {
        checkNotNull(is, "The input stream is null");
        checkNotNull(returnClass, "The return class is null");

        if (!String.class.isAssignableFrom(returnClass)) {
            String msg = "Cannot use %s as return class.";
            String formatted = String.format(msg, returnClass.getName());
            throw new IllegalArgumentException(formatted);
        }

        return (T) CharStreams.toString(new InputStreamReader(is));
    }
}
