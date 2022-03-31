/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

/** The column delimiter used for CSV job data. */
public enum ColumnDelimiter {
  /** Backquote character (`) */
  @SuppressWarnings("unused")
  BACKQUOTE,

  /** Caret character (^) */
  @SuppressWarnings("unused")
  CARET,

  /** Comma character (,) */
  @SuppressWarnings("unused")
  COMMA,

  /** pipe character (|) */
  @SuppressWarnings("unused")
  PIPE,

  /** Semicolon character (;) */
  @SuppressWarnings("unused")
  SEMICOLON,

  /** Tab character (\t) */
  @SuppressWarnings("unused")
  TAB
}
