/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import com.salesforce.functions.jvm.sdk.data.Record;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;

/** A builder for {@link DataTable} objects. */
public interface DataTableBuilder {
  /**
   * Adds a new row, using the given map as the source for the field values. Values at keys which
   * are not a field in the data table will be ignored. Missing values in the given map will result
   * in null values being written for the respective field.
   *
   * @param rowData The data for the row.
   * @return This {@link DataTableBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTableBuilder addRow(Map<String, String> rowData);

  /**
   * Adds a new row, using the given values as the source for the field values. Values are mapped by
   * position, so if the first field name is "zebra", the value for this field has to come first in
   * the argument list.
   *
   * @param rowData The data for the row.
   * @return This {@link DataTableBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTableBuilder addRow(String... rowData);

  /**
   * Adds a new row, using the given {@link Record} as the source for the field values. Fields in
   * the record which are not a field in the data table will be ignored. Missing fields in the given
   * record will result in null values being written for the respective field in the data table.
   *
   * @param record The record to source the data for the row from.
   * @return This {@link DataTableBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTableBuilder addRow(Record record);

  /**
   * Adds a new row, using the given object with the passed {@link DataTableFieldValueExtractor} as
   * the source for the field values.
   *
   * @param data The object to extract the field values from.
   * @param fieldValueExtractor The field value extractor to use.
   * @return This {@link DataTableBuilder} instance to allow method chaining.
   * @param <T> The object type to extract the field values from.
   */
  @Nonnull
  @SuppressWarnings("unused")
  <T> DataTableBuilder addRow(T data, DataTableFieldValueExtractor<T> fieldValueExtractor);

  /**
   * Adds a new row, using the given object with the passed map of single field extractor functions
   * as the source for the field values.
   *
   * @param data The object to extract the field values from.
   * @param fieldValueExtractors A map keyed by field names that contains extractor functions for
   *     the field's value.
   * @return This {@link DataTableBuilder} instance to allow method chaining.
   * @param <T> The object type to extract the field values from.
   */
  @Nonnull
  @SuppressWarnings("unused")
  <T> DataTableBuilder addRow(T data, Map<String, Function<T, String>> fieldValueExtractors);

  /**
   * Returns a new and immutable {@link DataTable} instance based on the information stored in this
   * builder.
   *
   * @return The new {@link DataTable} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataTable build();
}
