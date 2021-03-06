/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.config.internal.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ratpack.ssl.SSLContexts;
import ratpack.util.ExceptionUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SSLContextDeserializer extends JsonDeserializer<SSLContext> {
  @Override
  public SSLContext deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    // TODO: add a signature to SSLContexts for Path objects
    ObjectNode node = jp.readValueAsTree();
    try {
      String keyStorePath = node.path("keyStorePath").asText();
      String keyStorePassword = node.path("keyStorePassword").asText();
      return SSLContexts.sslContext(new File(keyStorePath), keyStorePassword);
    } catch (GeneralSecurityException ex) {
      throw ExceptionUtils.uncheck(ex);
    }
  }
}
