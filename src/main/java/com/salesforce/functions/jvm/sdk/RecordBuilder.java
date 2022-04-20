/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Builder for {@link Record}.
 */
public interface RecordBuilder extends RecordAccessor {

  /**
   * Returns a new and immutable {@link Record} instance based on the information stored in this
   * builder.
   *
   * @return The new {@link Record} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Record build();

  /**
   * Removes a field from this record builder.
   *
   * <p>It will remove the field entirely and will not set it to null. To set a field's value to
   * {@code null}, use {@link #withNullField(String)}.
   *
   * @param name The name of the field.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withoutField(String name);

  /**
   * Sets the value of a field to {@code null}.
   *
   * @param name The name of the field.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withNullField(String name);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, @Nullable String value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, short value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, long value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, int value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, float value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, double value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, byte value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, boolean value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, @Nullable BigInteger value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, @Nullable BigDecimal value);

  /**
   * Sets the value of a field.
   *
   * @param name The name of the field.
   * @param value The value to set.
   * @return This {@link RecordBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder withField(String name, @Nullable ReferenceId value);
}
