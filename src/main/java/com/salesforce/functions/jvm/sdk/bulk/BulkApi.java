/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

import com.salesforce.functions.jvm.sdk.bulk.builder.JobBuilder;
import com.salesforce.functions.jvm.sdk.bulk.error.BulkApiException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/** Bulk API client to upload data in bulk to a Salesforce org. */
public interface BulkApi {
  /**
   * Creates a job representing a bulk operation and its associated data that is sent to Salesforce
   * for asynchronous processing. Provide job data via {@link #uploadJobData(String, InputStream)}.
   *
   * @param job The job to create.
   * @return The ID of the created job.
   * @throws BulkApiException If an error occurred during job creation.
   * @see #newJobBuilder(String, Operation)
   */
  @SuppressWarnings("unused")
  String createJob(Job job) throws BulkApiException;

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
   * @return A CSV file with successful records.
   * @throws BulkApiException If an error occurred while querying successful record results.
   */
  @SuppressWarnings("unused")
  File getJobSuccessfulRecordResults(String jobId) throws BulkApiException;

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
   * @return A CSV file with all failed records.
   * @throws BulkApiException If an error occurred while querying failed record results.
   */
  @SuppressWarnings("unused")
  File getJobFailedRecordResults(String jobId) throws BulkApiException;

  /**
   * Uploads data for a job using CSV data you provide.
   *
   * @param jobId The ID for the job to upload data for.
   * @param inputStream The CSV data as a stream to upload to the job.
   * @throws BulkApiException If an error occurred uploading the data.
   * @throws IOException If an error occurred reading data from the provided stream.
   */
  @SuppressWarnings("unused")
  void uploadJobData(String jobId, InputStream inputStream) throws BulkApiException, IOException;

  /**
   * Closes a job. If you close a job, Salesforce queues the job and uploaded data for processing,
   * and you can’t add any more job data.
   *
   * @param jobId The ID of the job to close.
   * @throws BulkApiException If an error occurred while closing the job.
   */
  @SuppressWarnings("unused")
  void closeJob(String jobId) throws BulkApiException;

  /**
   * Aborts a job. If you abort a job, the job doesn't get queued or processed.
   *
   * @param jobId The ID of the job to abort.
   * @throws BulkApiException If an error occurred while aborting the job.
   */
  @SuppressWarnings("unused")
  void abortJob(String jobId) throws BulkApiException;

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
