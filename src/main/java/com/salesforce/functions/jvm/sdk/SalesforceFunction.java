/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import java.util.function.BiFunction;

/**
 * Main interface for Salesforce Functions written in Java.
 *
 * @param <T> The type of the {@link InvocationEvent} payload this function can handle.
 * @param <R> The type of the response of this function.
 */
@SuppressWarnings("unused")
public interface SalesforceFunction<T, R> extends BiFunction<InvocationEvent<T>, Context, R> {
  @Override
  R apply(InvocationEvent<T> tInvocationEvent, Context context);
}
