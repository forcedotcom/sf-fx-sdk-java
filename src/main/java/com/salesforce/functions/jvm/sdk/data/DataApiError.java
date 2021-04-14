/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import java.util.List;
import javax.annotation.Nonnull;

/**
 * Represents an error from the Data API. Contains detailed information about the underlying error.
 *
 * @see <a
 *     href="https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/errorcodes.htm">REST
 *     API Developer Guide - Status Codes and Error Responses</a>
 */
public interface DataApiError {
  /**
   * Returns the message of this error.
   *
   * @return The message of this error.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getMessage();

  /**
   * Returns the error code for this error.
   *
   * @return The error code for this error.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getErrorCode();

  /**
   * Returns the field names where the error occurred. Might be empty for errors that are not
   * related to a specific field.
   *
   * @return The field names where the error occurred.
   */
  @Nonnull
  @SuppressWarnings("unused")
  List<String> getFields();
}
