/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.Nonnull;

/**
 * Represents an update for a record that has not been persisted yet. Records can be updated from
 * instances of this type by using the {@link DataApi#update(RecordUpdate)} method.
 */
public interface RecordUpdate extends RecordModification<RecordUpdate> {
  /**
   * Returns the object type of the record that will be updated.
   *
   * @return The object type of the record that will be updated.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getType();

  /**
   * Returns the ID of the record that will be updated.
   *
   * @return The ID of the record that will be updated.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();
}
