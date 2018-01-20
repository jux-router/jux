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
 * A Response which can be later on converted to a HTTP response.
 *
 * @author Sandor Nemeth
 */
public class Response {

    /**
     * Create a {@link Response} with the HTTP response code {@code 200} and the given body.
     *
     * @param body the body of the response
     * @return the created response
     */
    public static Response ok(Object body) {
        return Response.status(HttpCode.OK).withBody(body);
    }

    /**
     * Creates a new {@link Response} with the passed {@link HttpCode} as status code.
     *
     * @param code the status of the response
     * @return the code
     */
    public static Response status(HttpCode code) {
        Response response = new Response();
        response.status = code.getCode();
        return response;
    }

    private Object body;
    private String mediaType;
    private int status;

    public Object getBody() {
        return body;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getStatus() {
        return status;
    }

    // Media type setters
    public Response as(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public Response as(ContentType contentType) {
        return as(contentType.getContentType());
    }

    public Response asJson() {
        return as(ContentType.JSON);
    }

    public Response asPlainText() {
        return as(ContentType.PLAIN_TEXT);
    }

    // Body setters
    public Response withBody(Object body) {
        this.body = body;
        return this;
    }
}
