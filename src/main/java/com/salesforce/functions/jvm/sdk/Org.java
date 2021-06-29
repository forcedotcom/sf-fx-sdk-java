/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import com.salesforce.functions.jvm.sdk.data.DataApi;
import java.net.URI;
import javax.annotation.Nonnull;

/** Holds information about the invoking Salesforce organization and user. */
public interface Org {
  /**
   * Returns the Salesforce organization ID.
   *
   * @return The Salesforce organization ID.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /**
   * Returns the base URL of the Salesforce organization.
   *
   * @return The base URL of the Salesforce organization.
   */
  @Nonnull
  @SuppressWarnings("unused")
  URI getBaseUrl();

  /**
   * Returns the domain URL of the Salesforce organization.
   *
   * @return The domain URL of the Salesforce organization.
   */
  @Nonnull
  @SuppressWarnings("unused")
  URI getDomainUrl();

  /**
   * Returns the API version the Salesforce organization is currently using.
   *
   * @return The API version the Salesforce organization is currently using.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getApiVersion();

  /**
   * Returns an initialized data API client instance to interact with data in the org.
   *
   * @return An initialized data API client instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataApi getDataApi();

  /**
   * Returns the currently logged in user.
   *
   * @return The currently logged in user.
   */
  @Nonnull
  @SuppressWarnings("unused")
  User getUser();
}
