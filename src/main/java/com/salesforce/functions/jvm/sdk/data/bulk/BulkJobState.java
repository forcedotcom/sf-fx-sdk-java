/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

public enum BulkJobState {
  UPLOAD_COMPLETE,
  IN_PROGRESS,
  ABORTED,
  JOB_COMPLETE,
  FAILED
}