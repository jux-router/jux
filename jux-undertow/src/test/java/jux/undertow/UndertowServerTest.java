package jux.undertow;

import com.google.common.io.CharStreams;
import jux.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the Undertow server.
 */
class UndertowServerTest {

    private Server server;
    private int port;
    private CloseableHttpClient client = HttpClientBuilder.create().build();

    @BeforeEach
    void setUp() {
        port = new PortProvider(0).get();
    }

    @AfterEach
    void cleanup() throws Exception {
        server.stop();
        client.close();
    }

    @Test
    void test() throws Exception {
        Router router = Jux.router();
        router.handle("/foo", (ctx, req) -> Response.ok("hello").as("text/plain")).methods(HttpMethod.GET);
        server = Jux.start(port, router);

        HttpGet get = new HttpGet("http://localhost:" + port + "/foo");
        CloseableHttpResponse response = client.execute(get);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        String result = CharStreams.toString(new InputStreamReader(response.getEntity().getContent()));
        assertThat(result).isEqualTo("hello");
        assertThat(response.getFirstHeader("Content-Type").getValue()).isEqualTo("text/plain");
    }

}
