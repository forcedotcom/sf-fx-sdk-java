/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

/** Signals that a bulk API error occurred. */
public final class BulkApiException extends Exception {
  private final List<BulkApiError> errors;

  /**
   * Constructs a BulkApiException with the specified detail message.
   *
   * @param message The detail message (which is saved for later retrieval by the {@link
   *     Throwable#getMessage()} method)
   */
  @SuppressWarnings("unused")
  public BulkApiException(String message) {
    super(message);
    errors = Collections.unmodifiableList(new ArrayList<>());
  }

  /**
   * Constructs a BulkApiException with the specified detail message and cause.
   *
   * @param message The detail message (which is saved for later retrieval by the {@link
   *     Throwable#getMessage()} method)
   * @param cause The cause (which is saved for later retrieval by the {@link Throwable#getCause()}
   *     method). (A null value is permitted, and indicates that the cause is nonexistent or
   *     unknown.)
   */
  @SuppressWarnings("unused")
  public BulkApiException(String message, Throwable cause) {
    super(message, cause);
    errors = Collections.unmodifiableList(new ArrayList<>());
  }

  /**
   * Constructs a BulkApiException with the specified detail message and list of API errors.
   *
   * @param message The detail message (which is saved for later retrieval by the {@link
   *     Throwable#getMessage()} method)
   * @param errors The list API errors (which is saved for later retrieval by the {@link
   *     #getBulkApiErrors()} method)
   */
  @SuppressWarnings("unused")
  public BulkApiException(String message, List<BulkApiError> errors) {
    super(message);
    this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
  }

  /**
   * Returns the list of {@link BulkApiError}s that occurred. This list might be empty if the
   * exception wasn't caused by an API error.
   *
   * @return The list of {@link BulkApiError}s that occurred.
   */
  @Nonnull
  @SuppressWarnings("unused")
  public List<BulkApiError> getBulkApiErrors() {
    return errors;
  }
}
