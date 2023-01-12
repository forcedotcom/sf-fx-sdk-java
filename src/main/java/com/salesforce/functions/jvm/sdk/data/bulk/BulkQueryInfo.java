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
public interface BulkQueryInfo {
  String getId();

  BulkQueryOperation getOperation();

  String getObjectType();

  String getCreatedById();

  Instant getCreatedDate();

  BulkJobState getState();

  long getNumberRecordsProcessed();

  int getRetriesCount();

  Duration getTotalProcessingTime();
}
