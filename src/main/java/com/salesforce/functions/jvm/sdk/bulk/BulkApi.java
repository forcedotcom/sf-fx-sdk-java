/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

import com.salesforce.functions.jvm.sdk.Record;
import com.salesforce.functions.jvm.sdk.bulk.builder.JobBuilder;
import java.util.List;
import javax.annotation.Nonnull;

/** Bulk API client to upload data in bulk to a Salesforce org. */
public interface BulkApi {
  @SuppressWarnings("unused")
  @Nonnull
  List<JobBatchResult> submit(@Nonnull Job job);

  JobBuilder newJobBuilder(String objectType, Operation operation, Iterable<Record> records);
}
