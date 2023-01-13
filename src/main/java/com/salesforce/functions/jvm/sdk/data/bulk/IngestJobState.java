/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

/** The state of an ingest bulk job. */
public enum IngestJobState {
  /** The job has been created, and job data can be uploaded to the job. */
  OPEN,
  /**
   * All data for a job has been uploaded, and the job is ready to be queued and processed. No new
   * data can be added to this job. You can’t edit or save a closed job.
   */
  UPLOAD_COMPLETE,
  /**
   * The job is being processed by Salesforce. This includes automatic optimized chunking of job
   * data and processing of job operations.
   */
  IN_PROGRESS,
  /** The job has been aborted. */
  ABORTED,
  /** The job was processed by Salesforce. */
  COMPLETE,
  /** Some records in the job failed. Job data that was successfully processed isn’t rolled back. */
  FAILED
}
