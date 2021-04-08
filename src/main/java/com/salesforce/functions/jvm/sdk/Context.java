/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * Represents the connection to the execution environment and the Customer 360 instance that the
 * function is associated with.
 */
public interface Context {
  /** Returns the unique identifier for a given execution of a function. */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /** Returns information about the connected Salesforce org and associated user in Customer 360. */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<Org> getOrg();
}
