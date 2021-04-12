/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.Nonnull;

/**
 * Represents a record that hasn't been created yet. Records can be created from instances of this
 * type by using the {@link DataApi#create(RecordCreate)} method.
 */
public interface RecordCreate extends RecordModification<RecordCreate> {
  /**
   * Returns the object type of the record that will be updated.
   *
   * @return The object type of the record that will be updated.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getType();
}
