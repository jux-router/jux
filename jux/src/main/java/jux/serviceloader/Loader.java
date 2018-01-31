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
package jux.serviceloader;

import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Loader is the class which loads all things from the {@link ServiceLoader}.
 *
 * @author Sandor Nemeth
 */
public class Loader {

    /**
     * Load all implementations of a <i>service</i> and unwrap it via the {@link
     * ServiceLoader.Provider#get()} call.
     *
     * @param serviceClass the service class
     * @param <T>          the type of the service
     * @return the (possibly empty) stream of implementations
     */
    public static <T> Stream<T> load(Class<T> serviceClass) {
        return ServiceLoader.load(serviceClass).stream()
                .map(ServiceLoader.Provider::get);
    }

    /**
     * Loads the <b>first</b> implementation of the given <i>service</i>, or
     * utilizes the given {@code errorSupplier} to throw an error.
     *
     * @param serviceClass  the service class
     * @param errorSupplier the error which is to be thrown in case there is no
     *                      implementation found
     * @param <T>           the type of the service
     * @return the implementation of the given service
     */
    public static <T> T loadOne(Class<T> serviceClass,
            Supplier<? extends RuntimeException> errorSupplier) {
        return load(serviceClass).findFirst().orElseThrow(errorSupplier);
    }

}
