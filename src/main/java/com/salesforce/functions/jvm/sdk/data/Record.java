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

/** Represents record in a Salesforce org. Implementations must be immutable. */
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
   * Returns the value of {@code key} as a {@link String}. Values that are not {@link String}s will
   * be automatically converted to Strings. For example, if the record contains the number 42 at the
   * given key, this method will return an {@link Optional} containing the String "42".
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as a {@link String} or empty {@link Optional} if the value is not
   *     present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<String> getStringValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Character}. Works exactly the same way as {@link
   * #getStringValue(String)}, but only returns the first character.
   *
   * @param key The key to obtain the value from.
   * @throws IndexOutOfBoundsException If the value, converted to a {@link String}, is of length 0.
   * @return The value of the key as a {@link Character} or empty {@link Optional} if the value is
   *     not present or null.
   * @see #getStringValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Character> getCharacterValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Boolean}.
   *
   * <p>If the value at the given key is not a boolean, it is converted to a {@link String} first,
   * and then parsed with {@link Boolean#parseBoolean(String)}. Effectively, this means that any
   * non-boolean value except the String "true" (compared case-insensitive) will result in false
   * being returned by this method.
   *
   * @param key The key to obtain the value from.
   * @return The value of the key as an {@link Boolean} or empty {@link Optional} if the value is
   *     not present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Boolean> getBooleanValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Byte}, which may involve rounding or truncation.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. This allows {@link String}s that represent numbers to be interpreted as numbers. If the
   * value cannot be parsed as a number, a {@link NumberFormatException} is thrown.
   *
   * <p>If the (if necessary, parsed) value cannot be directly represented as a {@link Byte} (i.e.
   * the value is too large or contains decimals), it will be converted to a {@link Byte} which may
   * involve rounding or truncation of the value.
   *
   * <p>The conversion is analogous to a narrowing primitive conversion or a widening primitive
   * conversion as defining in The Java® Language Specification for converting between primitive
   * types. Therefore, conversions may lose information about the overall magnitude of a numeric
   * value, may lose precision, and may even return a result of a different sign.
   *
   * <p>If full control over the conversion is required, {@link #getBigDecimalValue(String)} can be
   * used. {@link BigDecimal} offers an extensive suite of methods to convert from a {@link
   * BigDecimal} to primitive number types.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link Byte} value.
   * @return The value of the key as an {@link Byte} or empty {@link Optional} if the value is not
   *     present or null.
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html">The Java® Language
   *     Specification - Chapter 5. Conversions and Contexts</a>
   * @see #getBigDecimalValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Byte> getByteValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Short}, which may involve rounding or truncation.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. This allows {@link String}s that represent numbers to be interpreted as numbers. If the
   * value cannot be parsed as a number, a {@link NumberFormatException} is thrown.
   *
   * <p>If the (if necessary, parsed) value cannot be directly represented as a {@link Short} (i.e.
   * the value is too large or contains decimals), it will be converted to a {@link Short} which may
   * involve rounding or truncation of the value.
   *
   * <p>The conversion is analogous to a narrowing primitive conversion or a widening primitive
   * conversion as defining in The Java® Language Specification for converting between primitive
   * types. Therefore, conversions may lose information about the overall magnitude of a numeric
   * value, may lose precision, and may even return a result of a different sign.
   *
   * <p>If full control over the conversion is required, {@link #getBigDecimalValue(String)} can be
   * used. {@link BigDecimal} offers an extensive suite of methods to convert from a {@link
   * BigDecimal} to primitive number types.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link Short} value.
   * @return The value of the key as an {@link Short} or empty {@link Optional} if the value is not
   *     present or null.
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html">The Java® Language
   *     Specification - Chapter 5. Conversions and Contexts</a>
   * @see #getBigDecimalValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Short> getShortValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Integer}, which may involve rounding or
   * truncation.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. This allows {@link String}s that represent numbers to be interpreted as numbers. If the
   * value cannot be parsed as a number, a {@link NumberFormatException} is thrown.
   *
   * <p>If the (if necessary, parsed) value cannot be directly represented as a {@link Integer}
   * (i.e. the value is too large or contains decimals), it will be converted to a {@link Integer}
   * which may involve rounding or truncation of the value.
   *
   * <p>The conversion is analogous to a narrowing primitive conversion or a widening primitive
   * conversion as defining in The Java® Language Specification for converting between primitive
   * types. Therefore, conversions may lose information about the overall magnitude of a numeric
   * value, may lose precision, and may even return a result of a different sign.
   *
   * <p>If full control over the conversion is required, {@link #getBigDecimalValue(String)} can be
   * used. {@link BigDecimal} offers an extensive suite of methods to convert from a {@link
   * BigDecimal} to primitive number types.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link Integer}
   *     value.
   * @return The value of the key as an {@link Integer} or empty {@link Optional} if the value is
   *     not present or null.
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html">The Java® Language
   *     Specification - Chapter 5. Conversions and Contexts</a>
   * @see #getBigDecimalValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Integer> getIntValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Long}, which may involve rounding or truncation.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. This allows {@link String}s that represent numbers to be interpreted as numbers. If the
   * value cannot be parsed as a number, a {@link NumberFormatException} is thrown.
   *
   * <p>If the (if necessary, parsed) value cannot be directly represented as a {@link Long} (i.e.
   * the value is too large or contains decimals), it will be converted to a {@link Long} which may
   * involve rounding or truncation of the value.
   *
   * <p>The conversion is analogous to a narrowing primitive conversion or a widening primitive
   * conversion as defining in The Java® Language Specification for converting between primitive
   * types. Therefore, conversions may lose information about the overall magnitude of a numeric
   * value, may lose precision, and may even return a result of a different sign.
   *
   * <p>If full control over the conversion is required, {@link #getBigDecimalValue(String)} can be
   * used. {@link BigDecimal} offers an extensive suite of methods to convert from a {@link
   * BigDecimal} to primitive number types.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link Long} value.
   * @return The value of the key as an {@link Long} or empty {@link Optional} if the value is not
   *     present or null.
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html">The Java® Language
   *     Specification - Chapter 5. Conversions and Contexts</a>
   * @see #getBigDecimalValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Long> getLongValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Float}, which may involve rounding or truncation.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. This allows {@link String}s that represent numbers to be interpreted as numbers. If the
   * value cannot be parsed as a number, a {@link NumberFormatException} is thrown.
   *
   * <p>If the (if necessary, parsed) value cannot be directly represented as a {@link Float} (i.e.
   * the value is too large), it will be converted to a {@link Float} which may involve rounding or
   * truncation of the value.
   *
   * <p>The conversion is analogous to a narrowing primitive conversion or a widening primitive
   * conversion as defining in The Java® Language Specification for converting between primitive
   * types. Therefore, conversions may lose information about the overall magnitude of a numeric
   * value, may lose precision, and may even return a result of a different sign.
   *
   * <p>If full control over the conversion is required, {@link #getBigDecimalValue(String)} can be
   * used. {@link BigDecimal} offers an extensive suite of methods to convert from a {@link
   * BigDecimal} to primitive number types.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link Float} value.
   * @return The value of the key as an {@link Float} or empty {@link Optional} if the value is not
   *     present or null.
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html">The Java® Language
   *     Specification - Chapter 5. Conversions and Contexts</a>
   * @see #getBigDecimalValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Float> getFloatValue(String key);

  /**
   * Returns the value of {@code key} as a {@link Double}, which may involve rounding or truncation.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. This allows {@link String}s that represent numbers to be interpreted as numbers. If the
   * value cannot be parsed as a number, a {@link NumberFormatException} is thrown.
   *
   * <p>If the (if necessary, parsed) value cannot be directly represented as a {@link Double} (i.e.
   * the value is too large), it will be converted to a {@link Double} which may involve rounding or
   * truncation of the value.
   *
   * <p>The conversion is analogous to a narrowing primitive conversion or a widening primitive
   * conversion as defining in The Java® Language Specification for converting between primitive
   * types. Therefore, conversions may lose information about the overall magnitude of a numeric
   * value, may lose precision, and may even return a result of a different sign.
   *
   * <p>If full control over the conversion is required, {@link #getBigDecimalValue(String)} can be
   * used. {@link BigDecimal} offers an extensive suite of methods to convert from a {@link
   * BigDecimal} to primitive number types.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link Double}
   *     value.
   * @return The value of the key as an {@link Double} or empty {@link Optional} if the value is not
   *     present or null.
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html">The Java® Language
   *     Specification - Chapter 5. Conversions and Contexts</a>
   * @see #getBigDecimalValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Double> getDoubleValue(String key);

  /**
   * Returns the value of {@code key} as an {@link BigInteger} which may involve rounding.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. If that process fails, a {@link NumberFormatException} is thrown.
   *
   * <p>If the (if necessary, parsed) value cannot be directly represented as a {@link BigInteger}
   * (i.e. the value contains decimals), it will be converted to a {@link BigInteger} which may
   * involve rounding. Use {@link #getBigDecimalValue(String)} support for decimals is required.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link BigInteger}
   *     value.
   * @return The value of the key as an {@link BigInteger} or empty {@link Optional} if the value is
   *     not present or null.
   * @see #getBigDecimalValue(String)
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<BigInteger> getBigIntegerValue(String key);

  /**
   * Returns the value of {@code key} as an {@link BigDecimal}.
   *
   * <p>If the type of the value at the given key is not a number, this method will try to parse it
   * as one. If that process fails, or the number cannot be represented as a {@link BigDecimal}, a
   * {@link NumberFormatException} is thrown.
   *
   * @param key The key to obtain the value from.
   * @throws NumberFormatException If the value at key could not be parsed as a {@link BigDecimal}
   *     value.
   * @return The value of the key as an {@link BigInteger} or empty {@link Optional} if the value is
   *     not present or null.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<BigDecimal> getBigDecimalValue(String key);
}
