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
 * @param <T> The type of payload {@link InvocationEvent}.
 * @param <R> The type of the response.
 */
@SuppressWarnings("unused")
public interface SalesforceFunction<T, R> extends BiFunction<InvocationEvent<T>, Context, R> {
  @Override
  R apply(InvocationEvent<T> tInvocationEvent, Context context);
}
