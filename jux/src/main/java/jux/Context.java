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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * {@link Context} provides a carrier object, serving 2 purposes: to signal if a request at any point should be
 * cancelled, and a key-value store to pass on values to the main {@link Handler handler}.
 *
 * The context will always be properly cleared at the end of the request, so it cannot be used as a data store between
 * requests.
 *
 * @author Sandor Nemeth
 */
public class Context {

    private Map<String, Object> store;

    private boolean cancelled;

    public Context() {
        this.store = new HashMap<>();
        this.cancelled = false;
    }

    /**
     * @return {@code true} if the request is cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancel the request processing.
     *
     * {@link Middleware} implementations must honor this, and explicitly call {@code cancel()} to stop the request
     * processing.
     */
    public void cancel() {
        this.cancelled = true;
    }

    /**
     * Set a value in the context.
     *
     * If the {@code key} is already present in the {@link #store}, it will be overridden.
     *
     * @param key   the key
     * @param value the value to be stored
     */
    public void set(String key, Object value) {
        store.put(key, value);
    }

    /**
     * Get a value from the context.
     *
     * @param key the key to be looked up
     * @return the object stored in the context for the {@code key}, wrapped into an optional. The returned {@link
     * Optional} will be empty when the key is not present in the context
     */
    public Optional<Object> get(String key) {
        return Optional.ofNullable(store.get(key));
    }

    /**
     * Get a value from the context.
     *
     * @param key         the key to be looked up
     * @param returnClass the type to be returned
     * @param <T>         the type to be returned
     * @return the object stored in the context for the {@code key} with the correct {@code T} type, wrapped into an
     * optional. The returned {@link Optional} will be empty when the key is not present in the context
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String key, @SuppressWarnings("unused") Class<T> returnClass) {
        return Optional.ofNullable((T) store.get(key));
    }

}
