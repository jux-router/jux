package jux.test;

import org.apache.http.client.methods.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

/**
 * {@link ArgumentsProvider} to provide new {@link HttpUriRequest} instances
 * for GET, POST, PUT, PATCH and DELETE requests, which can be populated later
 * with correct URL and request informations.
 */
public class RequestProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(
            ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(new HttpGet()),
                Arguments.of(new HttpPost()),
                Arguments.of(new HttpPut()),
                Arguments.of(new HttpPatch()),
                Arguments.of(new HttpDelete())
        );
    }
}
