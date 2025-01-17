/*
 *  Copyright (c) 2020 Sanju Thomas
 *
 *  Licensed under the MIT License (the "License");
 *  You may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at https://en.wikipedia.org/wiki/MIT_License
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *  either express or implied.
 *
 *  See the License for the specific language governing permissions
 *  and limitations under the License.
 */

package com.sanjuthomas.orientdb;

import com.sanjuthomas.orientdb.writer.OrientDBWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sanju Thomas
 */
class OrientDbSinkResourceProviderTest {

  @Test
  void shouldLoadConfig() {
    final OrientDbSinkResourceProvider config = OrientDbSinkResourceProvider.builder()
      .using(new String[]{"quote_request"}, "src/test/resource").build();
    assertEquals("quote_request", config.database("quote_request"));
    assertEquals("QuoteRequest", config.className("quote_request"));
    assertEquals("OrientDBWriter", config.writer("quote_request").getClass().getSimpleName());
    final OrientDBWriter orientDBWriter = config.writer("quote_request");
    assertEquals(orientDBWriter.hashCode(), config.writer("quote_request").hashCode());
    config.removeWriter("quote_request");
    assertNotEquals(orientDBWriter.hashCode(), config.writer("quote_request").hashCode());
    final OrientDBWriter writer = config.writer("quote_request");
    config.removeWriter("quote_request");
  }

  @Test
  void shouldBuildWriter() {
    final OrientDbSinkResourceProvider config = OrientDbSinkResourceProvider.builder()
      .using(new String[]{"open_weather_data"}, "src/test/resource").build();
    final OrientDBWriter orientDBWriter = config.writer("open_weather_data");
    assertEquals("open_weather_data", orientDBWriter.getConfiguration().getDatabase());
    assertEquals("memory:/tmp", orientDBWriter.getConfiguration().getConnectionString());
    assertEquals("admin", orientDBWriter.getConfiguration().getUsername());
    config.removeWriter("open_weather_data");
  }
}
