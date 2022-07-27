/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Represents a Salesforce record with optional sub query results.
 *
 * <p>Implementations must be immutable and therefore thread-safe.
 */
@Immutable
public interface RecordWithSubQueryResults extends Record {
  /**
   * Returns the result of a sub query related to this record.
   *
   * <p>Records can have sub query results when the record is the result of a relationship query.
   *
   * @param objectName The object name of the sub query.
   * @return The result of a sub query related to this record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<RecordQueryResult> getSubQueryResult(String objectName);
}
