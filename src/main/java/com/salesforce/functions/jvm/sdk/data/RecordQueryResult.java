/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import java.util.List;
import javax.annotation.Nonnull;

public interface RecordQueryResult {
  @SuppressWarnings("unused")
  boolean isDone();

  @SuppressWarnings("unused")
  long getTotalSize();

  @Nonnull
  @SuppressWarnings("unused")
  List<Record> getRecords();
}
