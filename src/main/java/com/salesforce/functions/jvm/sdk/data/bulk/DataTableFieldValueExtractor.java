/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * Allows extraction of field values for a {@link DataTable} from an object.
 *
 * @param <T> The data extract the field values from.
 * @see DataTableBuilder
 */
@FunctionalInterface
public interface DataTableFieldValueExtractor<T> {
  /**
   * Extracts the given field (by its name) from the given data object.
   *
   * @param data The data to extract the field value from.
   * @param fieldName The name of the field to extract the data for.
   * @return The extracted value of the field.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<String> extractFieldValue(T data, String fieldName);
}
