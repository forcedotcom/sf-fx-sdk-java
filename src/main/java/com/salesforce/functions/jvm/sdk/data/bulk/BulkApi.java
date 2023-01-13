/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import com.salesforce.functions.jvm.sdk.data.error.DataApiException;
import javax.annotation.Nonnull;

public interface BulkApi {
  /**
   * Creates a new bulk ingest job.
   *
   * @param objectType The object type for the data being processed. Use only a single object type
   *     per job.
   * @param operation The processing operation for the job.
   * @return A reference object to the created job.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/create_job.htm">API
   *     Reference</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  IngestJobReference createIngestJob(String objectType, IngestOperation operation)
      throws DataApiException;

  /**
   * Creates a new bulk query job.
   *
   * <p>SOQL queries that include any of these items are not supported:
   *
   * <p>- GROUP BY, OFFSET, or TYPEOF clauses. - Aggregate Functions such as COUNT(). - Date
   * functions in GROUP BY clauses. (Date functions in WHERE clauses are supported.) - Compound
   * address fields or compound geolocation fields. (Instead, query the individual components of
   * compound fields.) - Parent-to-child relationship queries. (Child-to-parent relationship queries
   * are supported.)
   *
   * @param operation The type of query to perform.
   * @param soql The query to be performed.
   * @return A reference object to the created job.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/query_create_job.htm">API
   *     Reference</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  QueryJobReference createQueryJob(QueryOperation operation, String soql) throws DataApiException;

  /**
   * Fetches current information about an ingest job.
   *
   * @param reference The reference of the job to fetch information for.
   * @return Information about the job.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_info.htm">API
   *     Reference</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  IngestJobInfo getJobInfo(IngestJobReference reference) throws DataApiException;

  /**
   * Fetches current information about a query job.
   *
   * @param reference The reference of the job to fetch information for.
   * @return Information about the job.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/query_get_one_job.htm">API
   *     Reference</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  QueryJobInfo getJobInfo(QueryJobReference reference) throws DataApiException;

  /**
   * Closes an ingest job, signalling that all data has been uploaded.
   *
   * @param reference The reference of the job to close.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/close_job.htm">API
   *     Reference</a>
   */
  @SuppressWarnings("unused")
  void closeJob(IngestJobReference reference) throws DataApiException;

  /**
   * Uploads data for an ingest job.
   *
   * @param reference The reference of the job to upload data for.
   * @param table The data to upload.
   * @throws DataApiException If an API error occurred during the operation.
   * @see BulkApi#createDataTableBuilder(String...)
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/upload_job_data.htm">API
   *     Reference</a>
   */
  @SuppressWarnings("unused")
  void uploadJobData(IngestJobReference reference, DataTable table) throws DataApiException;

  /**
   * Aborts an ingest job.
   *
   * @param reference The reference of the job to abort.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/close_job.htm">API
   *     Reference</a>
   */
  @SuppressWarnings("unused")
  void abortJob(IngestJobReference reference) throws DataApiException;

  /**
   * Aborts a query job.
   *
   * @param reference The reference of the job to abort.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/query_abort_job.htm">API
   *     Reference</a>
   */
  @SuppressWarnings("unused")
  void abortJob(QueryJobReference reference) throws DataApiException;

  /**
   * Deletes an ingest job.
   *
   * @param reference The reference of the job to delete.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/delete_job.htm">API
   *     Reference</a>
   */
  @SuppressWarnings("unused")
  void deleteJob(IngestJobReference reference) throws DataApiException;

  /**
   * Deletes a query job.
   *
   * @param reference The reference of the job to delete.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/query_delete_job.htm">API
   *     Reference</a>
   */
  @SuppressWarnings("unused")
  void deleteJob(QueryJobReference reference) throws DataApiException;

  /**
   * Retrieves a list of successfully processed records for a completed ingest job.
   *
   * @param reference The reference of the job to retrieve the list for.
   * @return A {@link DataTable} with the original uploaded data augmented with 'sf__Created' and
   *     'sf__Id' fields.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_successful_results.htm">API
   *     Reference</a>
   * @see BulkApi#getFailedRecordResults(IngestJobReference)
   * @see BulkApi#getUnprocessedRecordResults(IngestJobReference)
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTable getSuccessfulRecordResults(IngestJobReference reference) throws DataApiException;

  /**
   * Retrieves a list of failed records for a completed ingest job.
   *
   * @param reference The reference of the job to retrieve the list for.
   * @return A {@link DataTable} with the original uploaded data augmented with 'sf__Error' and
   *     'sf__Id' fields.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_failed_results.htm">API
   *     Reference</a>
   * @see BulkApi#getSuccessfulRecordResults(IngestJobReference)
   * @see BulkApi#getUnprocessedRecordResults(IngestJobReference)
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTable getFailedRecordResults(IngestJobReference reference) throws DataApiException;

  /**
   * Retrieves a list of unprocessed records for failed or aborted ingest jobs.
   *
   * @param reference The reference of the job to retrieve the list for.
   * @return A {@link DataTable} with the original uploaded data.
   * @throws DataApiException If an API error occurred during the operation.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_unprocessed_results.htm">API
   *     Reference</a>
   * @see BulkApi#getSuccessfulRecordResults(IngestJobReference)
   * @see BulkApi#getFailedRecordResults(IngestJobReference)
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTable getUnprocessedRecordResults(IngestJobReference reference) throws DataApiException;

  /**
   * Gets the results for a query job. The job must be in the complete state.
   *
   * @param reference The reference of the job to get the results for.
   * @param maxRecords The maximum number of records to retrieve.
   * @return A {@link QueryJobResult} that can be used to get the result data as well as to fetch
   *     the next page or results.
   * @throws DataApiException If an API error occurred during the operation.
   * @see BulkApi#getMoreQueryResults(QueryJobResult)
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/query_get_job_results.htm">API
   *     Reference</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  QueryJobResult getQueryJobResults(QueryJobReference reference, long maxRecords)
      throws DataApiException;

  /**
   * Gets the next results for a query job.
   *
   * @param result The previous query job result.
   * @return A {@link QueryJobResult} that can be used to get the result data as well as to fetch
   *     the next page or results.
   * @throws DataApiException If an API error occurred during the operation.
   * @see BulkApi#getQueryJobResults(QueryJobReference, long)
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/query_get_job_results.htm">API
   *     Reference</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  QueryJobResult getMoreQueryResults(QueryJobResult result) throws DataApiException;

  @SuppressWarnings("unused")
  void waitForJobState(QueryJobReference reference, IngestJobState state)
      throws DataApiException, InterruptedException;

  @SuppressWarnings("unused")
  void waitForJobState(IngestJobReference reference, IngestJobState state)
      throws DataApiException, InterruptedException;

  /**
   * Convenience method for small to mid-sized ingest jobs. Will create new ingest job, upload the
   * data to it and closes it afterwards. See the individual methods for details.
   *
   * @param objectType The object type for the data being processed. Use only a single object type
   *     per job.
   * @param operation The processing operation for the job.
   * @param table The data to upload.
   * @return A reference object to the created job.
   * @throws DataApiException If an API error occurred during the operation.
   * @see BulkApi#createIngestJob(String, IngestOperation)
   * @see BulkApi#uploadJobData(IngestJobReference, DataTable)
   * @see BulkApi#closeJob(IngestJobReference)
   */
  @Nonnull
  @SuppressWarnings("unused")
  IngestJobReference createSimpleIngestJob(
      String objectType, IngestOperation operation, DataTable table) throws DataApiException;

  /**
   * Creates a new {@link DataTableBuilder} that can be used to create instances of {@link
   * DataTable}.
   *
   * @param fieldNames The names of the fields for the data table.
   * @return A builder object for {@link DataTable} objects.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTableBuilder createDataTableBuilder(String... fieldNames);
}
