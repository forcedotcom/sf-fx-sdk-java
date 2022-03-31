/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

/** The current state of processing of a bulk job. */
public enum JobState {
  /** The job has been created, and job data can be uploaded to the job. */
  @SuppressWarnings("unused")
  OPEN,

  /**
   * All data for a job has been uploaded, and the job is ready to be queued and processed. No new
   * data can be added to this job. You can’t edit or save a closed job.
   */
  @SuppressWarnings("unused")
  UPLOAD_COMPLETE,

  /**
   * The job is being processed by Salesforce. This includes automatic optimized chunking of job
   * data and processing of job operations.
   */
  @SuppressWarnings("unused")
  IN_PROGRESS,

  /**
   * The job has been aborted. You can abort a job if you created it or if you have the "Manage Data
   * Integrations" permission.
   */
  @SuppressWarnings("unused")
  ABORTED,

  /** The job was processed by Salesforce. */
  @SuppressWarnings("unused")
  JOB_COMPLETE,

  /** Some records in the job failed. Job data that was successfully processed isn't rolled back. */
  @SuppressWarnings("unused")
  FAILED
}
