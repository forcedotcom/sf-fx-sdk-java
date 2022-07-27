/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import com.salesforce.functions.jvm.sdk.data.builder.UnitOfWorkBuilder;
import javax.annotation.concurrent.Immutable;

/**
 * References a record that will be created, deleted or modified in the future. It can be used to
 * insert records that reference other records that are be created in the same {@link UnitOfWork}
 * transaction.
 *
 * <p>All implementations must be immutable and therefore thread-safe.
 *
 * @see UnitOfWorkBuilder#registerCreate(Record)
 * @see UnitOfWorkBuilder#registerUpdate(Record)
 * @see UnitOfWorkBuilder#registerDelete(String, String)
 */
@Immutable
public interface ReferenceId {}
