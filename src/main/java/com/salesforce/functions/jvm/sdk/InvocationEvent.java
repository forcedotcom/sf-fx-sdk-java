/*
 * Copyright (c) 2022, salesforce.com, inc.
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
 * An Event is representative of the data associated with the occurrence of an event, and supporting
 * metadata about the source of that occurrence.
 *
 * @param <T> The type of the payload of this event.
 */
public interface InvocationEvent<T> {
  /**
   * Returns the platform event occurrence id for event invocation.
   *
   * @return The platform event occurrence id for event invocation.
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#id">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /**
   * Returns a value describing the type of invocation. The format of this is producer defined and
   * might include information such as the version of the type.
   *
   * @return A string describing the type of invocation.
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#type">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getType();

  /**
   * Returns an URI which identifies the context in which an event happened. Often this will include
   * information such as the type of the event source, the organization publishing the event or the
   * process that produced the event.
   *
   * @return An URI which Identifies the context in which an event happened.
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#source-1">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  URI getSource();

  /**
   * Returns the unmarshalled payload of the event.
   *
   * @return The payload of the event.
   */
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
   * @return The media type of the event payload.
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#datacontenttype">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<String> getDataContentType();

  /**
   * Returns the schema that the event payload adheres to.
   *
   * @return The schema that the event payload adheres to.
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#dataschema">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<URI> getDataSchema();

  /**
   * Returns the timestamp of when the occurrence happened. If the time of the occurrence cannot be
   * determined then this attribute may be set to some other time (such as the current time),
   * however all producers for the same source must be consistent in this respect. In other words,
   * either they all use the actual time of the occurrence or they all use the same algorithm to
   * determine the value used.
   *
   * <p>{@link InvocationEvent#getSource}
   *
   * @return The timestamp of when the occurrence happened
   * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#time">CloudEvent
   *     Specification</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<OffsetDateTime> getTime();
}
