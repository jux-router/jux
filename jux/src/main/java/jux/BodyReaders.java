package jux;

import java.util.AbstractMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;

/**
 * Utility class for finding {@link BodyReader body readers}.
 */
public class BodyReaders {

    private static final Map<String, BodyReader> readers;

    static {
        // this is thread-safe, because we'll only read
        ServiceLoader<BodyReader> loader = ServiceLoader.load(BodyReader.class);
        readers = loader.stream()
                .map(ServiceLoader.Provider::get)
                .flatMap(r -> r.supportedMediaTypes().stream()
                        .map(m -> new AbstractMap.SimpleEntry<>(m, r)))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
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
