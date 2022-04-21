/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

import com.salesforce.functions.jvm.sdk.Record;
import com.salesforce.functions.jvm.sdk.bulk.builder.JobBuilder;
import com.salesforce.functions.jvm.sdk.bulk.error.BulkApiException;

/** Bulk API client to upload data in bulk to a Salesforce org. */
public interface BulkApi {
  /**
   * Creates a job representing a bulk operation and its associated data that is sent to Salesforce
   * for asynchronous processing. Provide job data via {@link #uploadJobData(String, Iterable)}.
   *
   * @param job The job to create.
   * @return Detailed information about the created job.
   * @throws BulkApiException If an error occurred during job creation.
   * @see #newJobBuilder(String, Operation)
   */
  @SuppressWarnings("unused")
  JobInfo createJob(Job job) throws BulkApiException;

  /**
   * Retrieves detailed information about a job.
   *
   * @param jobId The ID of the job to get information about.
   * @return Detailed information about a job.
   * @throws BulkApiException If an error occurred while querying job info.
   */
  @SuppressWarnings("unused")
  JobInfo getJobInfo(String jobId) throws BulkApiException;

  /**
   * Retrieves a list of successfully processed records for a completed job.
   *
   * <p>The returned file is a CSV file that all the records that encountered an error while being
   * processed by the job. Each row corresponds to a successfully processed record.
   *
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_successful_results.htm">Bulk
   *     API 2.0 and Bulk API Developer Guide</a>
   * @param jobId The ID of the job to get successful record results for.
   * @return An iterable containing the records that were successfully processed.
   * @throws BulkApiException If an error occurred while querying successful record results.
   */
  @SuppressWarnings("unused")
  Iterable<Record> getJobSuccessfulRecordResults(String jobId) throws BulkApiException;

  /**
   * Retrieves a list of failed records for a completed job.
   *
   * <p>The returned file is a CSV file that all the records that encountered an error while being
   * processed by the job. Each row corresponds to a failed record.
   *
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_failed_results.htm">Bulk
   *     API 2.0 and Bulk API Developer Guide</a>
   * @param jobId The ID of the job to get failed record results for.
   * @return An iterable containing the records that failed to be processed.
   * @throws BulkApiException If an error occurred while querying failed record results.
   */
  @SuppressWarnings("unused")
  Iterable<Record> getJobFailedRecordResults(String jobId) throws BulkApiException;

  /**
   * Uploads data for a job using CSV data you provide.
   *
   * @param jobId The ID for the job to upload data for.
   * @param records The CSV data as a stream to upload to the job.
   * @throws BulkApiException If an error occurred uploading the data.
   */
  @SuppressWarnings("unused")
  void uploadJobData(String jobId, Iterable<Record> records) throws BulkApiException;

  /**
   * Closes a job. If you close a job, Salesforce queues the job and uploaded data for processing,
   * and you can’t add any more job data.
   *
   * @param jobId The ID of the job to close.
   * @return Detailed information about the closed job.
   * @throws BulkApiException If an error occurred while closing the job.
   */
  @SuppressWarnings("unused")
  JobInfo closeJob(String jobId) throws BulkApiException;

  /**
   * Aborts a job. If you abort a job, the job doesn't get queued or processed.
   *
   * @param jobId The ID of the job to abort.
   * @return Detailed information about the aborted job.
   * @throws BulkApiException If an error occurred while aborting the job.
   */
  @SuppressWarnings("unused")
  JobInfo abortJob(String jobId) throws BulkApiException;

  /**
   * Deletes a job.
   *
   * @param jobId The ID of the job to delete.
   * @throws BulkApiException If an error occurred while deleting the job.
   */
  @SuppressWarnings("unused")
  void deleteJob(String jobId) throws BulkApiException;

  /**
   * Creates a new BulkJobBuilder that can be used to build a {@link Job} object for the {@link
   * #createJob(Job)} method.
   *
   * @param objectType The object type for the data being processed.
   * @param operation The processing operation for the job.
   * @return A new BulkJobBuilder for the given object type and operation.
   */
  @SuppressWarnings("unused")
  JobBuilder newJobBuilder(String objectType, Operation operation);
}
