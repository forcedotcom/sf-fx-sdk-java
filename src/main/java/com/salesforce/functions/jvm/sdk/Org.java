/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk;

import com.salesforce.functions.jvm.sdk.bulk.BulkApi;
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
   * @see #getBulkApi()
   * @return An initialized data API client instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  DataApi getDataApi();

  /**
   * Returns an initialized bulk API client instance to upload data in bulk to the org.
   *
   * @see #getDataApi()
   * @return An initialized bulk API client instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  BulkApi getBulkApi();

  /**
   * Returns the currently logged in user.
   *
   * @return The currently logged in user.
   */
  @Nonnull
  @SuppressWarnings("unused")
  User getUser();

  /**
   * Creates a new and empty RecordBuilder that can be used to build a {@link Record} objects.
   *
   * @param type The type of the record to build.
   * @return A new and empty RecordBuilder.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder newRecordBuilder(String type);

  /**
   * Creates a new RecordBuilder, pre-initialized from the given {@link Record}. The type and all
   * fields present in that Record will be set on the returned RecordBuilder.
   *
   * @param record The {@link Record} to copy the type and fields from.
   * @return A new RecordBuilder, pre-initialized from the given {@link Record}.
   * @throws IllegalArgumentException If the {@link Record} instance is incompatible with this
   *     {@link Org} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordBuilder newRecordBuilder(Record record);
}
