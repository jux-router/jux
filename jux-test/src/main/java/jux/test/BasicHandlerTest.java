package jux.test;

import com.google.common.io.CharStreams;
import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Stream;

import static jux.HttpMethod.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(JuxExtension.class)
class BasicHandlerTest {

    private HttpClient client = HttpClientBuilder.create().build();

    @SuppressWarnings("JUnit5MalformedParameterized")
    @ParameterizedTest
    @MethodSource("requests")
    void run(HttpRequestBase req, @TestServerPort Integer port)
            throws Exception {
        req.setURI(URI.create("http://localhost:" + port + "/foo"));
        final HttpResponse response = client.execute(req);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        String result = CharStreams.toString(new InputStreamReader(response.getEntity().getContent()));
        assertThat(result).isEqualTo("hello");
        assertThat(response.getFirstHeader("Content-Type").getValue()).isEqualTo("text/plain");
    }

    private static Stream<HttpRequestBase> requests() {
        return Stream.of(
                new HttpGet(),
                new HttpPost(),
                new HttpPut(),
                new HttpPatch(),
                new HttpDelete()
        );
    }

    @RouteProvider
    void routes(Router router) {
        router.handle("/foo", Handlers.BASIC_HELLO)
                .methods(GET, POST, PUT, PATCH, DELETE);
    }

}
