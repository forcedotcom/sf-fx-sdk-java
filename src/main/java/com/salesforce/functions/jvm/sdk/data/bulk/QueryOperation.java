/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

/** The processing operation of a query ingest job. */
public enum QueryOperation {
  /** Returns data that has not been deleted or archived. */
  QUERY,
  /**
   * Returns records that have been deleted because of a merge or delete, and returns information
   * about archived Task and Event records.
   */
  QUERY_ALL
}
