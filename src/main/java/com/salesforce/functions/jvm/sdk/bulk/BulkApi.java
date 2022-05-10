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

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/** Bulk API client to upload data in bulk to a Salesforce org. */
public interface BulkApi {
  /**
   * A convenience operator that manages the complete flow of a Bulk API job. It will create a job,
   * add the relevant data, close the job, and then wait for the successful, failed, and unprocessed
   * results to become available.
   *
   * @param job The job to create.
   * @param jobHandler A function that can be used to populate data and retrieve job information.
   * @return The successful, failed, and unprocessed results from the submitted job.
   */
  @SuppressWarnings("unused")
  @Nonnull
  JobResults submitJob(@Nonnull Job job, @Nonnull Consumer<JobHandler> jobHandler);

  /**
   * Creates a job representing a bulk operation and its associated data that is sent to Salesforce
   * for asynchronous processing. Provide job data via {@link #uploadJobData(JobInfo, Iterable)}.
   *
   * @param job The job to create.
   * @return Detailed information about the created job.
   * @throws BulkApiException If an error occurred during job creation.
   * @see #newJobBuilder(String, Operation)
   */
  @SuppressWarnings("unused")
  @Nonnull
  JobInfo createJob(@Nonnull Job job) throws BulkApiException;

  /**
   * Retrieves detailed information about a job.
   *
   * @param jobInfo A JobInfo instance containing the ID of the job to get information about.
   * @return Detailed information about a job.
   * @throws BulkApiException If an error occurred while querying job info.
   */
  @SuppressWarnings("unused")
  @Nonnull
  JobInfo getJobInfo(@Nonnull JobInfo jobInfo) throws BulkApiException;

  /**
   * Retrieves a list of successfully processed records for a completed job.
   *
   * <p>The returned file is a CSV file that all the records that encountered an error while being
   * processed by the job. Each row corresponds to a successfully processed record.
   *
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_successful_results.htm">Bulk
   *     API 2.0 and Bulk API Developer Guide</a>
   * @param jobInfo A JobInfo instance containing the ID of the job to get results from.
   * @return An iterable containing the records that were successfully processed.
   * @throws BulkApiException If an error occurred while querying successful record results.
   */
  @SuppressWarnings("unused")
  @Nonnull
  Iterable<Record> getJobSuccessfulRecordResults(@Nonnull JobInfo jobInfo) throws BulkApiException;

  /**
   * Retrieves a list of failed records for a completed job.
   *
   * <p>The returned file is a CSV file that all the records that encountered an error while being
   * processed by the job. Each row corresponds to a failed record.
   *
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_failed_results.htm">Bulk
   *     API 2.0 and Bulk API Developer Guide</a>
   * @param jobInfo A JobInfo instance containing the ID of the job to get results from.
   * @return An iterable containing the records that failed to be processed.
   * @throws BulkApiException If an error occurred while querying failed record results.
   */
  @SuppressWarnings("unused")
  @Nonnull
  Iterable<Record> getJobFailedRecordResults(@Nonnull JobInfo jobInfo) throws BulkApiException;

  /**
   * Retrieves a list of unprocessed records for a completed job.
   *
   * <p>The returned file is a CSV file that all the records that were not processed by the job.
   * Each row corresponds to an unprocessed record.
   *
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_failed_results.htm">Bulk
   *     API 2.0 and Bulk API Developer Guide</a>
   * @param jobInfo A JobInfo instance containing the ID of the job to get results from.
   * @return An iterable containing the records that were not processed.
   * @throws BulkApiException If an error occurred while querying unprocessed record results.
   */
  @SuppressWarnings("unused")
  @Nonnull
  Iterable<Record> getUnprocessedRecords(@Nonnull JobInfo jobInfo) throws BulkApiException;

  /**
   * Uploads data for a job using CSV data you provide.
   *
   * @param jobInfo A JobInfo instance containing the ID of the job to upload data for.
   * @param records The CSV data as a stream to upload to the job.
   * @throws BulkApiException If an error occurred uploading the data.
   */
  @SuppressWarnings("unused")
  void uploadJobData(@Nonnull JobInfo jobInfo, @Nonnull Iterable<Record> records)
      throws BulkApiException;

  /**
   * Closes a job. If you close a job, Salesforce queues the job and uploaded data for processing,
   * and you can’t add any more job data.
   *
   * @param jobInfo A JobInfo instance containing the ID of the job to close.
   * @return Detailed information about the closed job.
   * @throws BulkApiException If an error occurred while closing the job.
   */
  @SuppressWarnings("unused")
  @Nonnull
  JobInfo closeJob(@Nonnull JobInfo jobInfo) throws BulkApiException;

  /**
   * Aborts a job. If you abort a job, the job doesn't get queued or processed.
   *
   * @param jobInfo A JobInfo instance containing the ID of the job to abort.
   * @return Detailed information about the aborted job.
   * @throws BulkApiException If an error occurred while aborting the job.
   */
  @SuppressWarnings("unused")
  @Nonnull
  JobInfo abortJob(@Nonnull JobInfo jobInfo) throws BulkApiException;

  /**
   * Deletes a job.
   *
   * @param jobInfo A JobInfo instance containing the ID of the job to delete.
   * @throws BulkApiException If an error occurred while deleting the job.
   */
  @SuppressWarnings("unused")
  void deleteJob(@Nonnull JobInfo jobInfo) throws BulkApiException;

  /**
   * Creates a new JobBuilder that can be used to build a {@link Job} object for the {@link
   * #createJob(Job)} method.
   *
   * @param objectType The object type for the data being processed.
   * @param operation The processing operation for the job.
   * @return A new JobBuilder for the given object type and operation.
   */
  @SuppressWarnings("unused")
  @Nonnull
  JobBuilder newJobBuilder(@Nonnull String objectType, @Nonnull Operation operation);
}
