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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    @Test
    void testOKResponse() {
        Response response = Response.ok(null);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @ParameterizedTest
    @EnumSource(HttpCode.class)
    void testSetsCorrectHttpCode(HttpCode code) {
        assertThat(Response.status(code).getStatus()).isEqualTo(code.getCode());
    }

    @ParameterizedTest
    @EnumSource(ContentType.class)
    void testSetCorrectMediaType(ContentType contentType) {
        Response response = Response.ok(null).as(contentType);
        assertThat(response.getMediaType()).isEqualTo(contentType.getContentType());
    }

    @Test
    void testSetCustomContenType() {
        Response response = Response.ok(null).as("custom/type");
        assertThat(response.getMediaType()).isEqualTo("custom/type");
    }

    @Test
    void testJsonContentType() {
        Response response = Response.ok(null).asJson();
        assertThat(response.getMediaType()).isEqualTo(ContentType.JSON.getContentType());
    }

    @Test
    void testPlainTextContentType() {
        Response response = Response.ok(null).asPlainText();
        assertThat(response.getMediaType()).isEqualTo(ContentType.PLAIN_TEXT.getContentType());
    }

    @Test
    void testCustomResponseBuilding() {
        Response errorResponse = Response.status(HttpCode.BAD_REQUEST)
                .as(ContentType.BINARY)
                .withPayload("test body");

        assertThat(errorResponse)
                .hasFieldOrPropertyWithValue("status", 400)
                .hasFieldOrPropertyWithValue("mediaType", "application/octet-stream")
                .hasFieldOrPropertyWithValue("payload", "test body");
    }

    @Test
    void testHasBodyReturnsEmptyWhenNoBodyIsSet() {
        Response okResponse = Response.ok();
        assertThat(okResponse.hasPayload()).isFalse();
    }

    @Test
    void testHasBodyReturnsTrueWhenPayloadIsPresent() {
        Response okResponse = Response.ok("hello");
        assertThat(okResponse.hasPayload()).isTrue();
    }
}