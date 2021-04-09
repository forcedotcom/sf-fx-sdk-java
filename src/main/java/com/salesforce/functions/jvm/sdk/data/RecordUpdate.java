/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data;

import javax.annotation.Nonnull;

public interface RecordUpdate extends RecordModification<RecordUpdate> {
  @Nonnull
  @SuppressWarnings("unused")
  String getType();

  @Nonnull
  @SuppressWarnings("unused")
  String getId();
}
