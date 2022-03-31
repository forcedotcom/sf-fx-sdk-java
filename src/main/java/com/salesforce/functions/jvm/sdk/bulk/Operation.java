/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

/** The type of processing operation for a bulk job. */
public enum Operation {
  @SuppressWarnings("unused")
  INSERT,

  @SuppressWarnings("unused")
  DELETE,

  @SuppressWarnings("unused")
  HARD_DELETE,

  @SuppressWarnings("unused")
  UPDATE,

  @SuppressWarnings("unused")
  UPSERT
}
