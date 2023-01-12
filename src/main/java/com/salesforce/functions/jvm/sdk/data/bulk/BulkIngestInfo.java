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

@Immutable
public interface BulkIngestInfo {
  Duration getApexProcessingTime();

  Duration getApiActiveProcessingTime();

  String getCreatedById();

  Instant getCreatedDate();

  String getErrorMessage();

  String getId();

  long getFailedRecordsCount();

  long getProcessedRecordsCount();

  String getObjectType();

  int getRetriesCount();

  BulkJobState getState();

  Duration getTotalProcessingTime();

  Instant getSystemModstamp();
}
