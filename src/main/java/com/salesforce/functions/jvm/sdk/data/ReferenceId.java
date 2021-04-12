/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

/**
 * References a record that will be created or modified in the future. It can be used to insert
 * records that reference other records that will be created in the same {@link UnitOfWork}
 * transaction.
 */
public interface ReferenceId {}
