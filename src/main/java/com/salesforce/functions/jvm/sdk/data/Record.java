/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import javax.annotation.Nonnull;

public interface Record {
  /**
   * Returns the type of the record.
   *
   * @return The type of the record.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getType();

  /**
   * Returns the value of key as a {@link String}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as a {@link String} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<String> getStringValue(String key);

  /**
   * Returns the value of key as a {@link Short}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as a {@link Short} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Short> getShortValue(String key);

  /**
   * Returns the value of key as a {@link Number}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as a {@link Number} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Number> getNumberValue(String key);

  /**
   * Returns the value of key as a {@link Long}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as a {@link Long} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Long> getLongValue(String key);

  /**
   * Returns the value of key as an {@link Integer}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link Integer} or empty {@link Optional} if the value is
   *     not present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Integer> getIntValue(String key);

  /**
   * Returns the value of key as a {@link Float}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link Float} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Float> getFloatValue(String key);

  /**
   * Returns the value of key as a {@link Double}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link Double} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Double> getDoubleValue(String key);

  /**
   * Returns the value of key as a {@link Character}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link Character} or empty {@link Optional} if the value is
   *     not present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Character> getCharacterValue(String key);

  /**
   * Returns the value of key as a {@link Byte}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link Byte} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Byte> getByteValue(String key);

  /**
   * Returns the value of key as a {@link Boolean}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link Boolean} or empty {@link Optional} if the value is
   *     not present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Boolean> getBooleanValue(String key);

  /**
   * Returns the value of key as an {@link BigInteger}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link BigInteger} or empty {@link Optional} if the value is
   *     not present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<BigInteger> getBigIntegerValue(String key);

  /**
   * Returns the value of key as an {@link BigDecimal}.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link BigInteger} or empty {@link Optional} if the value is
   *     not present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<BigDecimal> getBigDecimalValue(String key);
}
