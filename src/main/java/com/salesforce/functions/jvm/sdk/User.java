/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import java.util.Optional;
import javax.annotation.Nonnull;

/** Holds information about the invoking Salesforce user in Customer 360. */
public interface User {
  /**
   * Returns the user's ID.
   *
   * @return The user's ID.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /**
   * Returns the name of the user.
   *
   * @return The name of the user.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getUsername();

  /**
   * Returns the id of the user this user operates in behalf of.
   *
   * @return The id of the user this user operates in behalf of.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Optional<String> getOnBehalfOfUserId();
}
