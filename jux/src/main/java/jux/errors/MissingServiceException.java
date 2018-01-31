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
package jux.errors;

import java.text.MessageFormat;

/**
 * Represents an exception when jux tries to load a service via the {@link
 * java.util.ServiceLoader} utility, but there is no available implementations.
 *
 * This exception is a signal that the application assembly is not correct and
 * missing a required component.
 *
 * This exception is fatal, there is no recovery.
 *
 * @author Sandor Nemeth
 */
public class MissingServiceException extends RuntimeException {

    private static final String MSG = "Cannot load any implementation of " +
            "[{1}] via the ServiceLoader. Re-package the application with a" +
            " dependency which provides an implementation of [{1}].";

    /**
     * Constructs the exception from the service class which was loaded.
     *
     * @param serviceClass the service class
     */
    public MissingServiceException(Class<?> serviceClass) {
        super(MessageFormat.format(MSG, serviceClass));
    }
}
