package jux;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Utility class for finding {@link BodyReader body readers}.
 */
public class BodyReaders {

    private static final Map<String, BodyReader> readers;

    static {
        // this is thread-safe, because we'll only read
        readers = new HashMap<>();
        ServiceLoader<BodyReader> loader = ServiceLoader.load(BodyReader.class);
        for (BodyReader reader : loader) {
            reader.supportedMediaTypes().forEach(mediaType -> {
                readers.put(mediaType, reader);
            });
        }
    }

    public static BodyReader forMediaType(String mediaType) {
        if (readers.containsKey(mediaType)) {
            return readers.get(mediaType);
        }
        String msg = String.format("No body reader present for media type %s",
                mediaType);
        throw new IllegalStateException(msg);
    }
}
