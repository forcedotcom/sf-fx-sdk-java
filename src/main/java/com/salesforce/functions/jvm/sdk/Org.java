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

/** Represents the invoking Salesforce organization and user in Customer 360. */
public interface Org {
  /** Returns the Salesforce Org ID. */
  @Nonnull
  @SuppressWarnings("unused")
  String getId();

  /** Returns the base URL of the Salesforce org. */
  @Nonnull
  @SuppressWarnings("unused")
  URI getBaseUrl();

  /** Returns the domain URL of the Salesforce org. */
  @Nonnull
  @SuppressWarnings("unused")
  URI getDomainUrl();

  /** Returns the API version the Salesforce org is on. */
  @Nonnull
  @SuppressWarnings("unused")
  String getApiVersion();

  /** Returns the API version the Salesforce org is on. */
  @Nonnull
  @SuppressWarnings("unused")
  DataApi getDataApi();

  /** Returns the currently logged in user. */
  @Nonnull
  @SuppressWarnings("unused")
  User getUser();
}
