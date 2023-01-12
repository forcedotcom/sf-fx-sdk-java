/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.data.bulk;

import com.salesforce.functions.jvm.sdk.data.Record;
import javax.annotation.concurrent.Immutable;

@Immutable
public interface FailedRecord extends Record {
  // The contents of this field contains the ID and a message. The docs don't include the structure
  // of that string.
  // We need find out the structure by experimentation later and fix up this signature.
  String getError();
}
