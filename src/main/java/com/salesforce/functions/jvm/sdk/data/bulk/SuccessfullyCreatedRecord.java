/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import com.salesforce.functions.jvm.sdk.data.Record;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
public interface SuccessfullyCreatedRecord extends Record {
  @SuppressWarnings("unused")
  boolean isCreated();

  @Nonnull
  @SuppressWarnings("unused")
  String getId();
}
