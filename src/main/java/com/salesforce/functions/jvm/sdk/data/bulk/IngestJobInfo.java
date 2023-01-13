/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import java.time.Duration;
import java.time.Instant;
import javax.annotation.concurrent.Immutable;

/**
 * Information about a bulk ingest job.
 *
 * @see <a
 *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/get_job_info.htm">API
 *     Reference</a>
 */
@Immutable
public interface IngestJobInfo {
  /**
   * Returns the number of milliseconds taken to process triggers and other processes related to the
   * job data. This doesn't include the time used for processing asynchronous and batch Apex
   * operations. If there are no triggers, the value is 0.
   *
   * @return The number of milliseconds taken to process triggers and other processes related to the
   *     job data.
   */
  @SuppressWarnings("unused")
  Duration getApexProcessingTime();

  /**
   * Returns the number of milliseconds taken to actively process the job and includes
   * apexProcessingTime, but doesn't include the time the job waited in the queue to be processed or
   * the time required for serialization and deserialization.
   *
   * @return The number of milliseconds taken to actively process the job and includes
   *     apexProcessingTime.
   */
  @SuppressWarnings("unused")
  Duration getApiActiveProcessingTime();

  /**
   * Returns the ID of the user who created the job.
   *
   * @return The ID of the user who created the job.
   */
  @SuppressWarnings("unused")
  String getCreatedById();

  /**
   * Returns the instant this job was created.
   *
   * @return The instant this job was created.
   */
  @SuppressWarnings("unused")
  Instant getCreatedDate();

  /**
   * Returns the error message shown for jobs with errors.
   *
   * @return The error message shown for jobs with errors.
   */
  @SuppressWarnings("unused")
  String getErrorMessage();

  /**
   * Returns the unique ID for this job.
   *
   * @return The unique ID for this job.
   */
  @SuppressWarnings("unused")
  String getId();

  /**
   * Returns the number of records that were not processed successfully in this job.
   *
   * @return The number of records that were not processed successfully in this job.
   */
  @SuppressWarnings("unused")
  long getFailedRecordsCount();

  /**
   * Returns the number of records already processed.
   *
   * @return The number of records already processed.
   */
  @SuppressWarnings("unused")
  long getProcessedRecordsCount();

  /**
   * Returns the object type for the data being processed.
   *
   * @return The object type for the data being processed.
   */
  @SuppressWarnings("unused")
  String getObjectType();

  /**
   * Returns the number of times that Salesforce attempted to save the results of an operation. The
   * repeated attempts are due to a problem, such as a lock contention.
   *
   * @return The number of times that Salesforce attempted to save the results of an operation.
   */
  @SuppressWarnings("unused")
  int getRetriesCount();

  /**
   * Returns the current state of processing for the job.
   *
   * @return The current state of processing for the job.
   */
  @SuppressWarnings("unused")
  IngestJobState getState();

  /**
   * Returns the time taken to process the job.
   *
   * @return The time taken to process the job.
   */
  @SuppressWarnings("unused")
  Duration getTotalProcessingTime();

  /**
   * Returns the instant the job was finished.
   *
   * @return The instant the job was finished.
   */
  @SuppressWarnings("unused")
  Instant getSystemModStamp();
}
