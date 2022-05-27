/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

import com.salesforce.functions.jvm.sdk.Record;
import java.util.List;
import javax.annotation.Nonnull;

/** Bulk API client to upload data in bulk to a Salesforce org. */
public interface BulkApi {
  @SuppressWarnings("unused")
  @Nonnull
  List<String> submit(
      @Nonnull String objectType, @Nonnull Operation operation, @Nonnull Iterable<Record> records);
  //
  //  @Nonnull
  //  JobInfo getInfo(@Nonnull String jobId);
  //
  //  @Nonnull
  //  JobResults getResults(@Nonnull String jobId);
  //
  //  @Nonnull
  //  List<JobResults> getResults(@Nonnull Collection<String> jobIds);
}
