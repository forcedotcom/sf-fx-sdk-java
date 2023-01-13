/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

/** The state of a query bulk job. */
public enum QueryJobState {
  /**
   * All job data has been uploaded and the job is ready to be processed. Salesforce has put the job
   * in the queue.
   */
  UPLOAD_COMPLETE,
  /** Salesforce is processing the job. */
  IN_PROGRESS,
  /** The job has been aborted. */
  ABORTED,
  /** Salesforce has finished processing the job. */
  COMPLETE,
  /** The job failed. */
  FAILED
}
