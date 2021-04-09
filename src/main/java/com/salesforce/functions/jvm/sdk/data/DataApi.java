/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;

public interface DataApi {
    /**
     * Creates a new and empty {@link UnitOfWork}.
     * <p>
     * Use the methods on UnitOfWork to add work to it. UnitOfWork instances can be committed by using the
     * {@link #commitUnitOfWork(UnitOfWork)} method on {@link DataApi}.
     *
     * @return a new and empty {@link UnitOfWork}.
     */
    @Nonnull
    UnitOfWork newUnitOfWork();

    /**
     * Creates a new {@link RecordCreate} for the given object type.
     *
     * @param type The type of record to create.
     * @return a new {@link RecordCreate}
     */
    @Nonnull
    RecordCreate newRecordCreate(String type);

    /**
     * Creates a new {@link RecordUpdate} for the given object type and record id.
     *
     * @param type The type of record to update.
     * @param id   The ID of the record to update.
     * @return a new {@link RecordUpdate}
     */
    @Nonnull
    RecordUpdate newRecordUpdate(String type, String id);

    /**
     * Commits a {@link UnitOfWork}, executing all operations registered with it. If any of these operations fail, the
     * whole unit is rolled back. To examine results for a single operation, inspect the returned map (which is keyed
     * with {@link ReferenceId} returned from {@link UnitOfWork#registerCreate(RecordCreate)} and
     * {@link UnitOfWork#registerUpdate(RecordUpdate)}.
     *
     * @param unitOfWork The {@link UnitOfWork} to commit.
     * @return A map of {@link RecordModificationResult}s.
     * @throws IOException If an IO related error occurred.
     */
    @Nonnull
    Map<ReferenceId, RecordModificationResult> commitUnitOfWork(UnitOfWork unitOfWork) throws IOException;

    /**
     * Creates a new record described by the given {@link RecordCreate}.
     *
     * @param create The record creation description. Must be obtained via {@link #newRecordCreate(String)}.
     * @return
     * @throws IOException If an IO related error occurred.
     */
    @Nonnull
    RecordModificationResult create(RecordCreate create) throws IOException;

    /**
     * Updates an existing record described by the given {@link RecordUpdate}.
     *
     * @param update The record update description. Must be obtained via {@link #newRecordUpdate(String, String)}.
     * @return
     * @throws IOException If an IO related error occurred.
     */
    @Nonnull
    RecordModificationResult update(RecordUpdate update) throws IOException;

    /**
     * Queries for records with a given SOQL string.
     *
     * @param soql The SOQL string.
     * @return a {@link RecordQueryResult} that contains the queried data.
     * @throws IOException If an IO related error occurred.
     * @see <a href="https://developer.salesforce.com/docs/atlas.en-us.soql_sosl.meta/soql_sosl/sforce_api_calls_soql.htm">Salesforce Object Query Language (SOQL)</a>
     */
    @Nonnull
    RecordQueryResult query(String soql) throws IOException;

    /**
     * Queries for more records, based on the given {@link RecordQueryResult}.
     *
     * @param queryResult The query result to query more data for.
     * @return A new {@link RecordQueryResult} with additional data.
     * @throws IOException If an IO related error occurred.
     */
    @Nonnull
    RecordQueryResult queryMore(RecordQueryResult queryResult) throws IOException;

    /**
     * Returns the access token used by this API client. Can be used to initialize a third-party API client
     * or to perform custom API calls with a HTTP library.
     *
     * @return the access token used by this API client.
     */
    @Nonnull
    String getAccessToken();
}

