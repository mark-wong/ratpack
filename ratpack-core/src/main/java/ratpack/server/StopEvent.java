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

package ratpack.server;

import ratpack.registry.Registry;

/**
 * Meta information about a server stop event.
 */
public class StopEvent {

  private final Registry registry;
  private final boolean reload;

  private StopEvent(Registry registry, boolean reload) {
    this.registry = registry;
    this.reload = reload;
  }

  /**
   * Retrieves the server registry for this instance
   * @return the server's registry
   */
  public Registry getRegistry() {
    return registry;
  }

  /**
   * Indicates if this stop event is part of reloading the server.
   * @return true if this stop event is part of a server reload. False otherwise.
   */
  public boolean isReload() {
    return reload;
  }

  /**
   * Builder for stop events.
   *
   * @param registry the server registry for this server
   * @param reload true if this is stop event during a server reload
   * @return a new stop event
   */
  public static StopEvent build(Registry registry, boolean reload) {
    return new StopEvent(registry, reload);
  }
}
