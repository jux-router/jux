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

import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Supplier;

public class PortProvider implements Supplier<Integer> {

    private Integer originalPort;

    public PortProvider(Integer originalPort) {
        this.originalPort = originalPort;
    }

    @Override
    public Integer get() {
        if (0 < originalPort) {
            return originalPort;
        }

        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot aquire random local port.",
                    e);
        }
    }
}
