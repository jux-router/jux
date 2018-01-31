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

import jux.Server;
import jux.errors.MissingServiceException;

import java.util.function.Supplier;

/**
 * {@link Supplier} supplying the application the embedded {@link Server} which
 * will run under the hood.
 *
 * If there is no {@link Server} implementation that can be loaded, this
 * implementation will thow an {@link MissingServiceException} to signal that
 * this should never fail.
 *
 * @author Sandor Nemeth
 * @see MissingServiceException
 */
public class JuxServerSupplier implements Supplier<Server> {

    @Override
    public Server get() {
        return Loader.loadOne(Server.class,
                () -> new MissingServiceException(Server.class));
    }
}
