/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.concurrent.Immutable;

/**
 * Represents a UnitOfWork. Instances can be created via {@link DataApi#newUnitOfWorkBuilder()}.
 *
 * <p>All implementations must be immutable and therefore thread-safe.
 *
 * @see DataApi#newUnitOfWorkBuilder()
 * @see DataApi#commitUnitOfWork(UnitOfWork)
 */
@Immutable
public interface UnitOfWork {}
