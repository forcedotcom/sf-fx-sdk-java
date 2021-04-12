/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.Nonnull;

/**
 * UnitOfWork allows you access records and work with them in a transactional manner. You register
 * modifications on a UnitOfWork instance to eventually commit them via {@link
 * DataApi#commitUnitOfWork(UnitOfWork)}.
 *
 * <p>If any error occurs during the commit, the entire UnitOfWork request is rolled back.
 *
 * @see DataApi#newUnitOfWork()
 */
public interface UnitOfWork {
  /**
   * Registers a record creation on this {@link UnitOfWork} and returns a {@link ReferenceId} that
   * can be used to refer to the created record in subsequent operations in this UnitOfWork.
   *
   * @param recordCreate A RecordCreate instance that describes the record to be created.
   * @return The ReferenceId for the created record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  ReferenceId registerCreate(RecordCreate recordCreate);

  /**
   * Registers a record update on this {@link UnitOfWork} and returns a {@link ReferenceId} that can
   * be used to refer to the updated record in subsequent operations in this UnitOfWork.
   *
   * @param recordUpdate A RecordUpdate instance that describes the record to be updated.
   * @return The ReferenceId for the updated record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  ReferenceId registerUpdate(RecordUpdate recordUpdate);
}
