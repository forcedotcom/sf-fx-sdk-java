/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.builder;

import com.salesforce.functions.jvm.sdk.data.DataApi;
import com.salesforce.functions.jvm.sdk.data.Record;
import com.salesforce.functions.jvm.sdk.data.ReferenceId;
import com.salesforce.functions.jvm.sdk.data.UnitOfWork;
import javax.annotation.Nonnull;

/**
 * Builder for {@link UnitOfWork}.
 *
 * @see DataApi#newUnitOfWorkBuilder()
 */
public interface UnitOfWorkBuilder {

  /**
   * Registers a record creation for the {@link UnitOfWork} and returns a {@link ReferenceId} that
   * can be used to refer to the created record in subsequent operations in this UnitOfWork.
   *
   * @param record The record to create.
   * @throws IllegalArgumentException If the {@link Record} instance wasn't created by a {@link
   *     RecordBuilder} obtained from same {@link DataApi} instance this UnitOfWorkBuilder was
   *     obtained from.
   * @return The ReferenceId for the created record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  ReferenceId registerCreate(Record record);

  /**
   * Registers a record update for the {@link UnitOfWork} and returns a {@link ReferenceId} that can
   * be used to refer to the updated record in subsequent operations in this UnitOfWork.
   *
   * @param record The record to update.
   * @throws IllegalArgumentException If the {@link Record} instance wasn't created by a {@link
   *     RecordBuilder} obtained from same {@link DataApi} instance this UnitOfWorkBuilder was
   *     obtained from.
   * @return The ReferenceId for the updated record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  ReferenceId registerUpdate(Record record);

  /**
   * Registers a deletion of an existing record of the given type and id.
   *
   * @param type The object type of the record to delete.
   * @param id The id of the record to delete.
   * @return The ReferenceId for the deleted record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  ReferenceId registerDelete(String type, String id);

  /**
   * Returns a new and immutable {@link UnitOfWork} instance based on the information stored in this
   * builder.
   *
   * @return The new {@link UnitOfWork} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  UnitOfWork build();
}
