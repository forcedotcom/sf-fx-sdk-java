/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

/**
 * References a bulk ingest job that is asynchronously processed by Salesforce. It can be used to
 * get information about the running job, reading back successful, errored and unprocessed records.
 *
 * <p>All implementations must be immutable and therefore thread-safe.
 *
 * @see BulkApi#getJobInfo(IngestJobReference)
 * @see BulkApi#getSuccessfulRecordResults(IngestJobReference)
 * @see BulkApi#getFailedRecordResults(IngestJobReference)
 * @see BulkApi#getUnprocessedRecordResults(IngestJobReference)
 * @see BulkApi#abortJob(IngestJobReference)
 * @see BulkApi#deleteJob(IngestJobReference)
 */
public interface IngestJobReference {
  String getId();
}
