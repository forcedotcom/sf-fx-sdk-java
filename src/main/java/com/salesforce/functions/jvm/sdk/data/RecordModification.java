/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Nonnull;

/**
 * Represents a generic record modification that has not been persisted yet. Implementations of this
 * interface are immutable. To modify multiple values at once, chain {@link #setValue} calls to
 * construct the final {@link RecordModification}.
 *
 * @param <T> The concrete type of record modification.
 * @see RecordCreate
 * @see RecordUpdate
 */
public interface RecordModification<T extends RecordModification<T>> {
  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, String value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, short value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, Number value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, long value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, int value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, float value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, double value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, char value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, byte value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, boolean value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, BigInteger value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param value The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, BigDecimal value);

  /**
   * Sets a value for the given key. The existing value, if present, will be overwritten.
   *
   * @param key The key to set the value for.
   * @param fkId The value to set.
   * @return A new instance with the set value.
   */
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, ReferenceId fkId);
}
