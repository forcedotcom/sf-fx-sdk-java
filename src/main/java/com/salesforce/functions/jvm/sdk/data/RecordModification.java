/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface RecordModification<T extends RecordModification<T>> {
    @Nonnull
    T setValue(String key, String value);

    @Nonnull
    T setValue(String key, short value);

    @Nonnull
    T setValue(String key, Number value);

    @Nonnull
    T setValue(String key, long value);

    @Nonnull
    T setValue(String key, int value);

    @Nonnull
    T setValue(String key, float value);

    @Nonnull
    T setValue(String key, double value);

    @Nonnull
    T setValue(String key, char value);

    @Nonnull
    T setValue(String key, byte value);

    @Nonnull
    T setValue(String key, boolean value);

    @Nonnull
    T setValue(String key, BigInteger value);

    @Nonnull
    T setValue(String key, BigDecimal value);

    @Nonnull
    T setValue(String key, ReferenceId fkId);
}
