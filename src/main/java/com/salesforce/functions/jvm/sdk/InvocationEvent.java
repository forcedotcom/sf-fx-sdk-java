/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * Represents a Salesforce Function invocation event.
 *
 * @param <T> The type of the payload of this event.
 */
public interface InvocationEvent<T> {
  /** Returns the platform event occurrence id for event invocation. */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /**
   * Returns a value describing the type of invocation. The format of this is producer defined and
   * might include information such as the version of the type.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getType();

  /**
   * Returns an URI that Identifies the context for the event. This includes information such as the
   * type of the event source, the org publishing the event, and the process that produced the
   * event.
   */
  @Nonnull
  @SuppressWarnings("unused")
  URI getSource();

  /** Returns the payload of the event. */
  @Nonnull
  @SuppressWarnings("unused")
  T getData();

  /**
   * Returns the media type of the event payload that is accessible via {@link
   * InvocationEvent#getData()}. This is valid for the original data that was posted to the function
   * before it was automatically unmarshalled into {@link T}.
   *
   * <p>When using POJOs and other higher level types for {@link T}, this value is most likely not
   * very useful. When using raw bytes (by using byte[] for T), this value can be used to drive your
   * custom unmarshalling process.
   *
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#datacontenttype">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<String> getDataContentType();

  /**
   * Returns the schema that the event payload adheres to.
   *
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#dataschema">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<URI> getDataSchema();

  /**
   * Returns the timestamp of the event occurrence.
   *
   * <p>{@link InvocationEvent#getSource}
   *
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#time">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<OffsetDateTime> getTime();
}
