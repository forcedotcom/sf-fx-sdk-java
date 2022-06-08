/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

import com.salesforce.functions.jvm.sdk.Record;
import java.util.Optional;

public interface JobBatchResult {
  Optional<String> getJobId();

  Optional<Throwable> getError();

  boolean isSuccess();

  boolean isError();

  Iterable<Record> getUnsubmittedRecords();
}
