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

public interface RecordModification<T extends RecordModification<T>> {
  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, String value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, short value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, Number value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, long value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, int value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, float value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, double value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, char value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, byte value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, boolean value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, BigInteger value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, BigDecimal value);

  @Nonnull
  @SuppressWarnings("unused")
  T setValue(String key, ReferenceId fkId);
}
