/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import com.salesforce.functions.jvm.sdk.Record;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Represents the result of a record query.
 *
 * <p>All implementations must be immutable and therefore thread-safe.
 *
 * @see DataApi#query(String)
 * @see DataApi#queryMore(RecordQueryResult)
 */
@Immutable
public interface RecordQueryResult {
  /**
   * If true, no additional records can be retrieved from the query result. If false, one or more
   * records remain to be retrieved. Use {@link DataApi#queryMore(RecordQueryResult)} to receive the
   * next batch of records from this query result.
   *
   * @return If there are no more rows to be retrieved.
   */
  @SuppressWarnings("unused")
  boolean isDone();

  /**
   * Returns the total amount of records returned by the query. This is not necessarily the same
   * amount of records returned by {@link #getRecords()}.
   *
   * @return The total amount of records returned by the query.
   * @see #isDone()
   */
  @SuppressWarnings("unused")
  long getTotalSize();

  /**
   * Returns the {@link Record}s in this query result. Use {@link #isDone()} to determine if there
   * are additional records to be queried with {@link DataApi#queryMore(RecordQueryResult)}.
   *
   * @return The {@link Record}s in this query result.
   */
  @Nonnull
  @SuppressWarnings("unused")
  List<Record> getRecords();
}
