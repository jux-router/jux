/**
 * Copyright © 2018 Sandor Nemeth (sandor.nemeth.1986 at gmail dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            if (method.getAnnotation(RouteProvider.class) != null &&
                    method.trySetAccessible()) {
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
