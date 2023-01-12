/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

/**
 * References a bulk query job that is asynchronously processed by Salesforce. It can be used to get
 * information about the running job, aborting it, deleting it and reading back the query results.
 *
 * <p>All implementations must be immutable and therefore thread-safe.
 *
 * @see BulkDataApi#getInfo(BulkQueryReference)
 * @see BulkDataApi#getQueryResults(BulkQueryReference, long)
 * @see BulkDataApi#getMoreQueryResults(BulkQueryResult)
 * @see BulkDataApi#abort(BulkQueryReference)
 * @see BulkDataApi#delete(BulkQueryReference)
 */
public interface BulkQueryReference {}
