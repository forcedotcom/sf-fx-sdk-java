/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import com.salesforce.functions.jvm.sdk.data.error.DataApiException;
import javax.annotation.Nonnull;

public interface BulkDataApi {
  @Nonnull
  @SuppressWarnings("unused")
  BulkQueryReference query(String soql) throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestReference ingest(BulkIngestTable table) throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestInfo getInfo(BulkIngestReference reference) throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkQueryInfo getInfo(BulkQueryReference reference) throws DataApiException;

  @SuppressWarnings("unused")
  void abort(BulkIngestReference reference) throws DataApiException;

  @SuppressWarnings("unused")
  void abort(BulkQueryReference reference) throws DataApiException;

  @SuppressWarnings("unused")
  void delete(BulkIngestReference reference) throws DataApiException;

  @SuppressWarnings("unused")
  void delete(BulkQueryReference reference) throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestTable getSuccessfulRecordResults(BulkIngestReference reference) throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestTable getFailedRecordResults(BulkIngestReference reference) throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestTable getUnprocessedRecordResults(BulkIngestReference reference)
      throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkQueryResult getQueryResults(BulkQueryReference reference, long maxRecords)
      throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkQueryResult getMoreQueryResults(BulkQueryResult result) throws DataApiException;

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestTableBuilder createBulkIngestTableBuilder(String... fieldNames);
}
