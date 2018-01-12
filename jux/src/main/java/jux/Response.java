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

// TODO use a builder
public class Response<T> {

    public static <T> Response<T> ok(T body) {
        Response<T> response = new Response<>();
        response.status = 200;
        response.body = body;
        return response;
    }

    private T body;
    private String mediaType;
    private int status;

    public T getBody() {
        return body;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getStatus() {
        return status;
    }

    public Response<T> as(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }
}
