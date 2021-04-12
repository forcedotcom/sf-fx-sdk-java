/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.Nonnull;

/** Represents the result of a record modification such as a create or insert. */
public interface RecordModificationResult {
  /**
   * Returns the id of the modified record.
   *
   * @return The id of the modified record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();
}
