package jux.test;

import jux.Jux;
import jux.PortProvider;
import jux.Router;
import jux.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class JuxExtension
        implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static final Logger LOG = LogManager.getLogger(JuxExtension.class);

    private Server server;
    private int port;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (null != server) {
            server.stop();
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Router router = Jux.router();
        Object instance = context.getTestInstance().orElseThrow(
                () -> new IllegalStateException("No test instance!"));
        Method[] methods = context.getTestClass().get().getDeclaredMethods();
        for (Method method : methods) {
            LOG.debug("Checking {}", method.getName());
            if (method.getAnnotation(RouteProvider.class) != null &&
                    method.trySetAccessible()) {
                LOG.debug("Invoking router setup");
                method.invoke(instance, router);
            }
        }

        this.port = new PortProvider(0).get();
        this.server = Jux.start(port, router);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Parameter p = parameterContext.getParameter();
        TestServerPort injectionPoint = p.getAnnotation(TestServerPort.class);

        return null != injectionPoint &&
                Integer.class.isAssignableFrom(p.getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return port;
    }
}
