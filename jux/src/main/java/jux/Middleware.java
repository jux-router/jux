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
package jux;

/**
 * A middleware is sitting between the start of the request handling process and the final {@link Handler}, providing
 * access to the entire {@link Exchange}, and the ability to things before the request gets handled, or after the
 * request got already handled by the {@link Handler handler}.
 *
 * These middlewares are composable. For example, having the following code:
 *
 * <pre>{@code
 * Handler handler = (exchange) -> LOG.info("hello");
 * Middleware m1 = (next) -> (exchange) -> {
 *     LOG.info("executing m1 before");
 *     next.handle(exchange);
 *     LOG.info("executing m1 after");
 * };
 * Middleware m2 = (next) -> (exchange) -> {
 *     LOG.info("executing m2 before");
 *     next.handle(exchange);
 *     LOG.info("executing m2 after");
 * };
 *
 * Handler final = m2.around(m1.around(handler));
 * handler.handle(new Exchange());
 * }</pre>
 *
 * would result in the following log messages (in order):
 *
 * <pre>{@code
 * executing m2 before
 * executing m1 before
 * hello
 * executing m1 after
 * executing m2 after
 * }</pre>
 *
 * @author Sandor Nemeth
 */
@FunctionalInterface
public interface Middleware {

    /**
     * Wrap the middleware around the handler provided.
     *
     * Any implementation MUST do one of two things when returning the new handler. Either it has to call {@code
     * next.handle(exchange)}, or it MUST finalize the exchange.
     *
     * @param next the next handler in the chain
     * @return a new handler around the {@code next} handler
     */
    Handler around(Handler next);
}
