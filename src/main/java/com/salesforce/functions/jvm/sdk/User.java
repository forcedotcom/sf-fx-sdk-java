/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import java.util.Optional;
import javax.annotation.Nonnull;

/** Represents the Salesforce user in Customer 360 who is invoking the function. */
public interface User {
  /** Returns the user ID. */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /** Returns the username. */
  @Nonnull
  @SuppressWarnings("unused")
  String getUsername();

  @Nonnull
  @SuppressWarnings("unused")
  Optional<String> getOnBehalfOfUserId();
}
