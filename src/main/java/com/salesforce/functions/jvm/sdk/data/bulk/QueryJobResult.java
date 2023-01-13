/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import javax.annotation.Nonnull;

public interface QueryJobResult {
  /**
   * If true, no additional records can be retrieved from the query result. If false, one or more
   * records remain to be retrieved. Use {@link BulkApi#getMoreQueryResults(QueryJobResult)} to
   * receive the next batch of records from this query result.
   *
   * @return If there are no more rows to be retrieved.
   */
  @SuppressWarnings("unused")
  boolean isDone();

  /**
   * Returns the queried data as {@link DataTable}.
   *
   * @return The queried data as {@link DataTable}.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTable getDataTable();
}
