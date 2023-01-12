/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import com.salesforce.functions.jvm.sdk.data.Record;
import java.util.Map;
import javax.annotation.Nonnull;

public interface BulkIngestTableBuilder {
  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestTableBuilder addRow(Map<String, String> rowData);

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestTableBuilder addRow(Record record);

  @Nonnull
  @SuppressWarnings("unused")
  <T> BulkIngestTableBuilder addRow(T data, BulkDataMapper<T> mapper);

  @Nonnull
  @SuppressWarnings("unused")
  BulkIngestTable build();
}
