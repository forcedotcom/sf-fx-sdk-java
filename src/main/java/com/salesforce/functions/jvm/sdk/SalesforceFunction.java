/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

/**
 * Main interface for Salesforce Functions written in Java.
 *
 * @param <T> The type of the {@link InvocationEvent} payload this function can handle.
 * @param <R> The type of the response of this function.
 * @see java.util.function.BiFunction
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface SalesforceFunction<T, R> {

  /**
   * Applies the function to the given arguments.
   *
   * @param event The invocation event for this function application.
   * @param context The context for this function application.
   * @return The result of this function application.
   * @throws Exception If an unrecoverable exception occurred during function application.
   */
  R apply(InvocationEvent<T> event, Context context) throws Exception;
}
