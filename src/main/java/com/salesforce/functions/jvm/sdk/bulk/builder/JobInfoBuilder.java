/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk.builder;

import com.salesforce.functions.jvm.sdk.bulk.*;
import javax.annotation.Nonnull;

/**
 * Builder for {@link JobInfo}.
 *
 * @see BulkApi#newJobInfoBuilder(String)
 */
public interface JobInfoBuilder {
  /**
   * Returns a new and immutable {@link JobInfo} instance based on the information stored in this
   * builder.
   *
   * @return The new {@link JobInfo} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  JobInfo build();
}
