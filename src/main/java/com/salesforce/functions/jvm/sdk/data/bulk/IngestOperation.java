/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

/** The processing operation of a bulk ingest job. */
public enum IngestOperation {
  INSERT,
  DELETE,
  HARD_DELETE,
  UPDATE,
  UPSERT
}
