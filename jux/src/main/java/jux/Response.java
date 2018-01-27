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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import java.util.*;

/**
 * A Response which can be later on converted to a HTTP response.
 *
 * @author Sandor Nemeth
 */
public class Response {

    /**
     * Create a {@link Response} with the HTTP response code {@code 200} and the
     * given body.
     *
     * @param body the body of the response
     * @return the created response
     */
    public static Response ok(Object body) {
        return Response.status(HttpCode.OK).withPayload(body);
    }

    /**
     * Create a {@link Response} with the HTTP response code {@code 200}.
     *
     * @return the created response
     */
    public static Response ok() {
        return Response.status(HttpCode.OK);
    }

    /**
     * Creates a new {@link Response} with the passed {@link HttpCode} as status
     * code.
     *
     * @param code the status of the response
     * @return the code
     */
    public static Response status(HttpCode code) {
        Response response = new Response();
        response.status = code.getCode();
        return response;
    }

    private Object payload;
    private String mediaType;
    private int status;
    private ListMultimap<String, String> headers = ArrayListMultimap.create();

    public Object getPayload() {
        return payload;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getStatus() {
        return status;
    }

    public boolean hasPayload() {
        return null != payload;
    }

    public ListMultimap<String, String> getHeaders() {
        return headers;
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
    public Response withPayload(Object payload) {
        this.payload = payload;
        return this;
    }

    // header setters
    public Response setHeader(String header, String... values) {
        return setHeader(header, Arrays.asList(values));
    }

    public Response setHeader(String header, List<String> values) {
        headers.replaceValues(header, values);
        return this;
    }

    public Response addHeader(String header, String... value) {
        return addHeader(header, Arrays.asList(value));
    }

    public Response addHeader(String header, List<String> values) {
        headers.putAll(header, values);
        return this;
    }

}
