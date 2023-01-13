/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * A simple table structure.
 *
 * @see DataTableBuilder
 */
@Immutable
public interface DataTable extends Collection<Map<String, String>> {
  /**
   * Returns the names of the fields in this table.
   *
   * @return The names of the fields in this table.
   */
  @Nonnull
  @SuppressWarnings("unused")
  List<String> getFieldNames();
}
