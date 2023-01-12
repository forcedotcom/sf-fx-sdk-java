/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import com.salesforce.functions.jvm.sdk.data.Record;
import java.util.List;
import javax.annotation.Nonnull;

public interface BulkQueryResult {
  /**
   * If true, no additional records can be retrieved from the query result. If false, one or more
   * records remain to be retrieved. Use {@link BulkDataApi#getMoreQueryResults(BulkQueryResult)} to
   * receive the next batch of records from this query result.
   *
   * @return If there are no more rows to be retrieved.
   */
  @SuppressWarnings("unused")
  boolean isDone();

  @Nonnull
  @SuppressWarnings("unused")
  // Is Record the correct type here? Can a bulk query have sub query results?
  List<Record> getRecords();
}
