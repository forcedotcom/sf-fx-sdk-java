/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Detailed information about a bulk job.
 *
 * @see BulkApi#getJobInfo(String)
 * @see BulkApi#createJob(Job)
 */
@Immutable
public interface JobInfo {
  /**
   * Returns the unique ID for this job.
   *
   * @return The unique ID for this job.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /**
   * Returns the object type for the data being processed.
   *
   * @return The object type for the data being processed.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getObjectType();

  /**
   * Returns the processing operation for the job.
   *
   * @return The processing operation for the job.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Operation getOperation();

  /**
   * Returns the current state of processing for the job.
   *
   * @return The current state of processing for the job.
   */
  @Nonnull
  @SuppressWarnings("unused")
  JobState getJobState();

  /**
   * Returns the format of the data being processed.
   *
   * @return The format of the data being processed.
   */
  @Nonnull
  @SuppressWarnings("unused")
  ContentType getContentType();

  /**
   * Returns the column delimiter used for CSV job data.
   *
   * @return The column delimiter used for CSV job data.
   */
  @Nonnull
  @SuppressWarnings("unused")
  ColumnDelimiter getColumnDelimiter();

  /**
   * Returns the line ending used for CSV job data.
   *
   * @return The line ending used for CSV job data.
   */
  @Nonnull
  @SuppressWarnings("unused")
  LineEnding getLineEnding();

  /**
   * Returns the amount of time taken to process triggers and other processes related to the job
   * data. This doesn't include the time used for processing asynchronous and batch Apex operations.
   * If there are no triggers, the value is 0.
   *
   * @return The amount of time taken to process triggers and other processes related to the job
   *     data.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Duration getApexProcessingTime();

  /**
   * Returns the amount of time taken to actively process the job and includes apexProcessingTime,
   * but doesn't include the time the job waited in the queue to be processed or the time required
   * for serialization and deserialization.
   *
   * @return The amount of time taken to actively process the job.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Duration getApiActiveProcessingTime();

  /**
   * Returns the amount of time taken to process the job.
   *
   * @return The amount of time taken to process the job.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Duration getTotalProcessingTime();

  /**
   * Returns the ID of an assignment rule to run for a case or a lead.
   *
   * @return the ID of an assignment rule to run for a case or a lead.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getAssignmentRuleId();

  /**
   * Returns the URL to use for Upload Job Data requests for this job. Only valid if the job is in
   * open state.
   *
   * <p>Users should use {@link BulkApi#uploadJobData(String, Iterable)} to upload data for a job
   * instead of using this URL directly.
   *
   * @return The URL to use for Upload Job Data requests for this job.
   */
  @Nonnull
  @SuppressWarnings("unused")
  URI getContentUrl();

  /**
   * Returns the ID of the user who created the job.
   *
   * @return The ID of the user who created the job.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getCreatedByUserId();

  /**
   * Returns the point in time when the job was created.
   *
   * @return The point in time when the job was created.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Instant getCreatedDate();

  /**
   * Returns the point in time when the job finished.
   *
   * @return The point in time when the job finished.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Instant getSystemModstamp();

  /**
   * Returns the name of the external ID field for an upsert.
   *
   * @return The name of the external ID field for an upsert.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getExternalIdFileName();

  /**
   * Returns the number of records that were not processed successfully in this job.
   *
   * @return The number of records that were not processed successfully in this job.
   */
  @SuppressWarnings("unused")
  long getNumberRecordsFailed();

  /**
   * Returns the number of records already processed.
   *
   * @return The number of records already processed.
   */
  @SuppressWarnings("unused")
  long getNumberRecordsProcessed();

  /**
   * Returns the number of times that Salesforce attempted to save the results of an operation. The
   * repeated attempts are due to a problem, such as a lock contention.
   *
   * @return The number of times that Salesforce attempted to save the results of an operation.
   */
  @SuppressWarnings("unused")
  int getRetries();
}
