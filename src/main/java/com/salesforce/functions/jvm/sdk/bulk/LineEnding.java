/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

/** The line ending used for CSV job data. */
public enum LineEnding {
  /** Line Feed */
  @SuppressWarnings("unused")
  LF,

  /** Carriage Return + Line Feed */
  @SuppressWarnings("unused")
  CRLF
}
