/**
 * Copyright © 2018 Sandor Nemeth (sandor.nemeth.1986 at gmail dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package jux;

/**
 * @author Sandor Nemeth
 */
public interface Middleware {

    /**
     * Do something before the request gets executed.
     *
     * @param ctx the context
     * @param req the request
     */
    void doBefore(Context ctx, Request req);

    /**
     * Do something after the request was executed.
     *
     * @param ctx  the context
     * @param req  the request
     * @param resp the response
     */
    void doAfter(Context ctx, Request req, Response resp);


}
