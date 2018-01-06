package jux;

import java.util.AbstractMap;
import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toMap;

public class BodyWriters {

    private static final Map<String, BodyWriter> writers;

    static {
        ServiceLoader<BodyWriter> loader = ServiceLoader.load(BodyWriter.class);
        writers = loader.stream()
                .map(ServiceLoader.Provider::get)
                .flatMap(r -> r.supportedMediaTypes().stream()
                        .map(m -> new AbstractMap.SimpleEntry<>(m, r)))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static BodyWriter forMediaType(String mediaType) {
        if (writers.containsKey(mediaType)) {
            return writers.get(mediaType);
        }
        String msg = String.format("No body writer present for media type %s",
                mediaType);
        throw new IllegalStateException(msg);
    }
}
