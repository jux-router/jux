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
package jux.test;

import com.google.common.base.Joiner;
import com.google.common.io.CharStreams;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JuxAssertions extends Assertions {

    public static ResponseAssert assertThat(HttpResponse response) {
        return new ResponseAssert(response);
    }

    public static class ResponseAssert
            extends AbstractAssert<JuxAssertions.ResponseAssert, HttpResponse> {

        ResponseAssert(HttpResponse response) {
            super(response, ResponseAssert.class);
        }

        public static ResponseAssert assertThat(HttpResponse response) {
            return new ResponseAssert(response);
        }

        public ResponseAssert isOk() {
            isNotNull();

            int statusCode = actual.getStatusLine().getStatusCode();
            if (!Objects.equals(statusCode, 200)) {
                failWithMessage("Expected http status to be <200> but was <%s>",
                        statusCode);
            }

            return this;
        }

        public ResponseAssert hasContentType(String contentType) {
            isNotNull();

            String actualType =
                    actual.getFirstHeader("Content-Type").getValue();

            if (!Objects.equals(contentType, actualType)) {
                failWithMessage("Expected content-type to be <%s> but was <%s>",
                        contentType, actualType);
            }

            return this;
        }

        public ResponseAssert hasStringContent(String content) {
            isNotNull();

            String actualContent = null;
            try {
                actualContent = CharStreams.toString(
                        new InputStreamReader(actual.getEntity().getContent()));
            } catch (IOException e) {
                failWithMessage(e.getMessage());
            }

            if (!Objects.equals(content, actualContent)) {
                failWithMessage(
                        "Expected response content to be <%s> but was <%s>",
                        content, actualContent);
            }

            return this;
        }

        public ResponseAssert hasHeaderWithValues(String name,
                                                  String... values) {
            isNotNull();

            List<String> expected = Arrays.asList(values);
            List<String> headers = Arrays.stream(actual.getHeaders(name))
                    .map(Header::getValue)
                    .collect(Collectors.toList());

            if (!(expected.containsAll(headers) &&
                    headers.containsAll(expected))) {
                failWithMessage(
                        "Expected <[%s]> to have the same length as <[%s]>",
                        Joiner.on(',').join(values),
                        Joiner.on(',').join(headers));
            }

            return this;
        }
    }

}
