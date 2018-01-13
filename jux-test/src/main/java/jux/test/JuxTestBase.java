package jux.test;

import jux.Router;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Base test class for JUX-tests.
 */
@ExtendWith(JuxExtension.class)
public abstract class JuxTestBase {

    protected HttpClient client = HttpClientBuilder.create().build();

    @ParameterizedTest
    @ArgumentsSource(RequestProvider.class)
    void testJux(HttpRequestBase request, @TestServerPort int port)
            throws Exception {
        configureRequest(request, port);
        validateResponse(client.execute(request));
    }

    abstract void validateResponse(HttpResponse response) throws Exception;

    abstract void configureRequest(HttpRequestBase request, int port)
            throws Exception;

    @SuppressWarnings("unused")
    protected abstract void configureRoutes(Router router);

}
