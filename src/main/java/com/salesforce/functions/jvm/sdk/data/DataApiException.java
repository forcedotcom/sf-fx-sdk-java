/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

/** Signals that a data API error occurred. */
public final class DataApiException extends Exception {

  /**
   * Constructs a DataApiException with the specified detail message.
   *
   * @param message The detail message (which is saved for later retrieval by the {@link
   *     Throwable#getMessage()} method)
   */
  @SuppressWarnings("unused")
  public DataApiException(String message) {
    super(message);
  }

  /**
   * Constructs a DataApiException with the specified detail message and cause.
   *
   * @param message The detail message (which is saved for later retrieval by the {@link
   *     Throwable#getMessage()} method)
   * @param cause The cause (which is saved for later retrieval by the {@link Throwable#getCause()}
   *     method). (A null value is permitted, and indicates that the cause is nonexistent or
   *     unknown.)
   */
  @SuppressWarnings("unused")
  public DataApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
