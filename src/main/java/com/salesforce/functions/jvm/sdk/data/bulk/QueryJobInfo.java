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
 *     href="https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/query_get_one_job.htm">API
 *     Reference</a>
 */
@Immutable
public interface QueryJobInfo {
  /**
   * Returns the unique ID for this job.
   *
   * @return The unique ID for this job.
   */
  @SuppressWarnings("unused")
  String getId();

  /**
   * Returns the type of query.
   *
   * @return The type of query.
   */
  @SuppressWarnings("unused")
  QueryOperation getOperation();

  /**
   * Returns the object type being queried.
   *
   * @return The object type being queried.
   */
  @SuppressWarnings("unused")
  String getObjectType();

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
   * Returns the current state of processing for the job.
   *
   * @return The current state of processing for the job.
   */
  @SuppressWarnings("unused")
  QueryJobState getState();

  /**
   * Returns the number of records processed in this job.
   *
   * @return The number of records processed in this job.
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
  int getRetriesCount();

  /**
   * Returns the amount of time taken to process the job.
   *
   * @return The amount of time taken to process the job.
   */
  @SuppressWarnings("unused")
  Duration getTotalProcessingTime();
}
