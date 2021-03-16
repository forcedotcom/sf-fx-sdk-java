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
    T setStringValue(String key, String value);

    @Nonnull
    T setShortValue(String key, short value);

    @Nonnull
    T setNumberValue(String key, Number value);

    @Nonnull
    T setLongValue(String key, long value);

    @Nonnull
    T setIntValue(String key, int value);

    @Nonnull
    T setFloatValue(String key, float value);

    @Nonnull
    T setDoubleValue(String key, double value);

    @Nonnull
    T setCharacterValue(String key, char value);

    @Nonnull
    T setByteValue(String key, byte value);

    @Nonnull
    T setBooleanValue(String key, boolean value);

    @Nonnull
    T setBigIntegerValue(String key, BigInteger value);

    @Nonnull
    T setBigDecimalValue(String key, BigDecimal value);

    @Nonnull
    T setReferenceIdValue(String key, ReferenceId fkId);
}
