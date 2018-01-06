package jux;

/**
 * The RouterConverter converts the {@link jux.Router} into a router which is
 * specific to the used embedded server.
 */
public interface RouterConverter<T> {

    T convert(jux.Router router);

}