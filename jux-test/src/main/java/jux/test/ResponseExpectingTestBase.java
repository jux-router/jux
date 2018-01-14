package jux.test;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public abstract class ResponseExpectingTestBase extends JuxTestBase {

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(new HttpGet()),
                Arguments.of(new HttpPost()),
                Arguments.of(new HttpPut()),
                Arguments.of(new HttpPatch()),
                Arguments.of(new HttpDelete())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void executeTest(HttpRequestBase request,
                            @TestServerPort int port,
                            HttpClient client) throws Exception {
        execute(request, port, client);
    }
}
