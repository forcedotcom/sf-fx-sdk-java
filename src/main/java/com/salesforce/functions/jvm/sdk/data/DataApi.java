/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import com.salesforce.functions.jvm.sdk.Record;
import com.salesforce.functions.jvm.sdk.ReferenceId;
import com.salesforce.functions.jvm.sdk.data.builder.UnitOfWorkBuilder;
import com.salesforce.functions.jvm.sdk.data.error.DataApiException;
import java.util.Map;
import javax.annotation.Nonnull;

/** Data API client to interact with data in a Salesforce org. */
public interface DataApi {

  /**
   * Queries for records with a given SOQL string.
   *
   * @param soql The SOQL string.
   * @return a {@link RecordQueryResult} that contains the queried data.
   * @throws DataApiException If error occurred during the query.
   * @see <a
   *     href="https://developer.salesforce.com/docs/atlas.en-us.soql_sosl.meta/soql_sosl/sforce_api_calls_soql.htm">Salesforce
   *     Object Query Language (SOQL)</a>
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordQueryResult query(String soql) throws DataApiException;

  /**
   * Queries for more records, based on the given {@link RecordQueryResult}.
   *
   * @param queryResult The query result to query more data for.
   * @return A new {@link RecordQueryResult} with additional data or an empty one if the given
   *     RecordQueryResult was already in the done state.
   * @throws DataApiException If error occurred during the query.
   * @throws IllegalArgumentException If the {@link RecordQueryResult} instance wasn't created by
   *     this {@link DataApi} instance.
   * @see RecordQueryResult#isDone()
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordQueryResult queryMore(RecordQueryResult queryResult) throws DataApiException;

  /**
   * Creates a new record of the given type with the given fields.
   *
   * @param record The record to create.
   * @return A {@link RecordModificationResult} for this operation.
   * @throws DataApiException If an API error occurred during record creation.
   * @throws IllegalArgumentException If the {@link Record} instance is incompatible with this
   *     {@link DataApi} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordModificationResult create(Record record) throws DataApiException;

  /**
   * Updates an existing record of the given type and id with the given fields.
   *
   * @param record The record to update.
   * @return A {@link RecordModificationResult} for this operation.
   * @throws DataApiException If an API error occurred during record update.
   * @throws IllegalArgumentException If the {@link Record} instance is incompatible with this
   *     {@link DataApi} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordModificationResult update(Record record) throws DataApiException;

  /**
   * Deletes an existing record of the given type and id.
   *
   * @param type The object type of the record to delete.
   * @param id The id of the record to delete.
   * @return A {@link RecordModificationResult} for this operation.
   * @throws DataApiException If an API error occurred during record deletion.
   */
  @Nonnull
  @SuppressWarnings("unused")
  RecordModificationResult delete(String type, String id) throws DataApiException;

  /**
   * Creates a new and empty UnitOfWorkBuilder that can be used to build a {@link UnitOfWork} object
   * for the {@link #commitUnitOfWork(UnitOfWork)} method.
   *
   * @return A new and empty FieldsBuilder.
   */
  @Nonnull
  @SuppressWarnings("unused")
  UnitOfWorkBuilder newUnitOfWorkBuilder();

  /**
   * Commits a {@link UnitOfWork}, executing all operations registered with it. If any of these
   * operations fail, the whole unit is rolled back. To examine results for a single operation,
   * inspect the returned map (which is keyed with {@link ReferenceId} returned from {@link
   * UnitOfWorkBuilder#registerCreate(Record)}, {@link UnitOfWorkBuilder#registerUpdate(Record)},
   * and {@link UnitOfWorkBuilder#registerDelete(String, String)}.
   *
   * @param unitOfWork The {@link UnitOfWork} to commit.
   * @return A map of {@link RecordModificationResult}s, indexed by their {@link ReferenceId}s.
   * @throws DataApiException If an error occurred while committing the UnitOfWork.
   * @throws IllegalArgumentException If the {@link UnitOfWork} instance wasn't created by a {@link
   *     UnitOfWorkBuilder} obtained from this {@link DataApi} instance.
   * @see #newUnitOfWorkBuilder()
   */
  @Nonnull
  @SuppressWarnings("unused")
  Map<ReferenceId, RecordModificationResult> commitUnitOfWork(UnitOfWork unitOfWork)
      throws DataApiException;

  /**
   * Returns the access token used by this API client. Can be used to initialize a third-party API
   * client or to perform custom API calls with a HTTP library.
   *
   * @return the access token used by this API client.
   */
  @Nonnull
  @SuppressWarnings("unused")
  String getAccessToken();
}
